package com.zylu.location.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zylu.location.R;
import com.zylu.location.event.LocationEvent;
import com.zylu.location.mvp.view.IFragmentContentManage;
import com.zylu.location.ui.fragment.MainFragment;
import com.zylu.location.util.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IFragmentContentManage, View.OnClickListener {


    private static final String TAG = "MainActivity";
    @BindView(R.id.nav_view) NavigationView navView;
    private TextView userName;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        init();
    }

    /**
     * 初始化方法
     */
    private void init() {
        EventBus.getDefault().register(this);
        navView.setNavigationItemSelectedListener(this);
        Resources resource=(Resources)getBaseContext().getResources();
        ColorStateList csl=(ColorStateList)resource.getColorStateList(R.color.line);
        navView.setItemTextColor(csl);

        SharedPreferences sp = getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE);
        String strUserName = sp.getString(Constants.REAL_NAME, "点击logo登录");

        //获取导航栏头部控件
        View header = navView.getHeaderView(0);
        ImageView imgHeader = (ImageView) header.findViewById(R.id.imageHeader);
        userName = (TextView) header.findViewById(R.id.userName);
        imgHeader.setOnClickListener(this);
        userName.setText(strUserName);

        navView.getMenu();

        //fragment管理
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, new MainFragment());
        fragmentTransaction.commit();



    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Bundle bundle = null;
        Intent intent = null;

        switch ( item.getItemId() ) {
            case R.id.nav_friend:
                bundle = new Bundle();
                bundle.putInt("fragmentID", R.layout.fragment_friends);

                intent = new Intent(MainActivity.this, OtherActivity.class);
                intent.putExtras(bundle);

                startActivityForResult(intent, Constants.LOGIN_REQUEST_CODE);
                break;
            case R.id.nav_system:
                Toast.makeText(this, "还没想好干啥...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_exit:
                finish();
                break;
            case R.id.nav_log_out:
                SharedPreferences.Editor spe = getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE).edit();
                spe.clear();
                spe.commit();
                userName.setText("点击logo登录.");
                break;

            case R.id.nav_sys_notice:
                //融云消息推送
                bundle = new Bundle();

                intent = new Intent(MainActivity.this, IMActivity.class);
                intent.putExtras(bundle);

                startActivityForResult(intent, Constants.LOGIN_REQUEST_CODE);
                break;

            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 切换fragment
     */
    @Override
    public void onChange(@NonNull int FragmentID, HashMap<String, Object> params) {



    }

    @Override
    public void changeMenu(@NonNull boolean addFriendFlag, @NonNull boolean registerFlag) {

    }

    /**
     * 改变标题
     *
     * @param title
     */
    @Override
    public void changeTitle(@NonNull String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageHeader:
                Bundle bundle = new Bundle();
                bundle.putInt("fragmentID", R.layout.fragment_login);

                Intent intent = new Intent(MainActivity.this, OtherActivity.class);
                intent.putExtras(bundle);

                startActivityForResult(intent, Constants.LOGIN_REQUEST_CODE);
                break;

            default:

        }
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(Constants.LOGIN_RESULT_CODE == resultCode && Constants.LOGIN_REQUEST_CODE == requestCode) {
            SharedPreferences sp = getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE);
            String un = sp.getString(Constants.REAL_NAME, "这个人很懒");

            //设置人名
            userName.setText(un);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LocationEvent event) {
        Log.d(TAG, "onEvent");
        Log.d(TAG, new Gson().toJson(event));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}