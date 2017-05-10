package com.zylu.location.ui;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.zylu.location.R;
import com.zylu.location.event.TokenTimeOutEvent;
import com.zylu.location.mvp.view.IFragmentContentManage;
import com.zylu.location.ui.fragment.AboutMyself;
import com.zylu.location.ui.fragment.AddFriendFragment;
import com.zylu.location.ui.fragment.EditInfoFragment;
import com.zylu.location.ui.fragment.FriendsFragment;
import com.zylu.location.ui.fragment.LocalPersonalInfo;
import com.zylu.location.ui.fragment.LoginFragment;
import com.zylu.location.ui.fragment.PersonalFragment;
import com.zylu.location.ui.fragment.RegisterFragment;
import com.zylu.location.ui.fragment.SysSettingFragment;
import com.zylu.location.util.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry on 2017/1/2.
 */

public class OtherActivity extends AppCompatActivity implements IFragmentContentManage{

    @BindView(R.id.other_fragments)
    FrameLayout otherFragments;

    private static final int FRAGMENT_BODY = R.id.other_fragments;
    private static final String TAG = "OtherActivity";
    @BindView(R.id.other_toolbar) Toolbar otherToolbar;
    @BindDrawable(R.drawable.back) Drawable back;
    private Menu headMenu;
    private int fragmentID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        init();
    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        fragmentID = bundle.getInt("fragmentID");

        SharedPreferences sp = getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE);
        String userId = sp.getString(Constants.USER_ID, "");

        //初始化fragment页面
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (fragmentID) {
            case R.layout.fragment_login:
                if(userId.equals("")) {
                    //用户id为空，跳转至登录页
                    fragmentTransaction.add(FRAGMENT_BODY, new LoginFragment(), TAG);
                    break;
                }

                fragmentTransaction.add(FRAGMENT_BODY, new PersonalFragment(), TAG);
                break;

            case R.layout.fragment_sys_setting:
                fragmentTransaction.add(FRAGMENT_BODY, new SysSettingFragment(), TAG);
                break;

            case R.layout.fragment_friends:
                fragmentTransaction.add(FRAGMENT_BODY, new FriendsFragment(), TAG);
                break;

            default:
                break;
        }

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        back.setBounds(0, 0, 20, 20);
        back.setAlpha(240);

        //沉浸式效果
        setSupportActionBar(otherToolbar);
        otherToolbar.setNavigationIcon(back);
        otherToolbar.setTitle("登录");
    }

    @Subscribe
    public void onTokenTimeOutEvent(TokenTimeOutEvent event) {
        fragmentID = R.layout.fragment_login;
        Toast.makeText(OtherActivity.this, "登录过期，请重新登录。", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_other, menu);
        headMenu = menu;
        changeMenu(true, false, false);
        return true;
    }

    /**
     *
     * @param addFriendFlag 添加好友按钮显示标志
     * @param registerFlag  注册按钮显示标准
     */
    @Override
    public void changeMenu(boolean addFriendFlag, boolean registerFlag, boolean saveFlag){
        if(headMenu == null) return ;

        this.invalidateOptionsMenu();
        headMenu.getItem(0).setVisible(addFriendFlag);
        headMenu.getItem(1).setVisible(saveFlag);
        headMenu.getItem(2).setVisible(registerFlag);

        headMenu.getItem(0).setEnabled(addFriendFlag);
        headMenu.getItem(1).setEnabled(saveFlag);
        headMenu.getItem(2).setEnabled(registerFlag);
    }

    /**
     * 改变标题
     *
     * @param title
     */
    @Override
    public void changeTitle(@NonNull String title) {
        otherToolbar.setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment now = fragmentManager.findFragmentByTag(TAG);
        if(now != null) fragmentTransaction.hide(now);
        switch (item.getItemId()) {
            case 16908332: //toolbar 返回按钮
                if(now instanceof PersonalFragment || now instanceof LoginFragment
                        || now instanceof FriendsFragment || now instanceof SysSettingFragment) {
                    finish();
                }
                fragmentManager.popBackStack();
                break;

            case R.id.regist:
                //跳转至注册页面
                fragmentTransaction.add(FRAGMENT_BODY, new RegisterFragment(), TAG);
                break;

            case R.id.add_friend:
                SharedPreferences sp = getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE);
                String id = sp.getString(Constants.USER_ID, "");
                if("".equals(id)) {
                    Toast.makeText(OtherActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    fragmentTransaction.show(now);
                    break;
                }

                fragmentTransaction.add(FRAGMENT_BODY, new AddFriendFragment(), TAG);
                break;

            default:
        }

        //记录栈
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        return super.onOptionsItemSelected(item);
    }

    /**
     * 需要传值的fragment
     *  @Param FragmentID 需要跳转页面的ID
     */
    @Override
    public void onChange(@NonNull int FragmentID, HashMap<String, Object> params) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment now = fragmentManager.findFragmentByTag(TAG);
        if(now != null) fragmentTransaction.hide(now);

        switch (FragmentID) {
            case R.layout.activity_main:
                setResult(Constants.LOGIN_RESULT_CODE, new Intent());
                finish();
                break;

            case R.layout.fragment_personal:
                fragmentTransaction.add(FRAGMENT_BODY, new PersonalFragment(), TAG);
                break;

            case R.layout.fragment_register:
                fragmentTransaction.add(FRAGMENT_BODY, new RegisterFragment(), TAG);
                break;

            case R.layout.fragment_login:
                fragmentTransaction.add(FRAGMENT_BODY, new LoginFragment(), TAG);
                break;

            case R.layout.fragment_add_friend:
                String id = getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE).getString(Constants.USER_ID, "");
                if(id.equals("")) {
                    Toast.makeText(OtherActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    return ;
                }

                fragmentTransaction.add(FRAGMENT_BODY, new AddFriendFragment(), TAG);
                break;

            case R.layout.fragment_change_info:

                EditInfoFragment editInfoFragment = new EditInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", (String) params.get("type"));
                bundle.putString("msg", (String) params.get("msg"));
                editInfoFragment.setArguments(bundle);

                fragmentTransaction.add(FRAGMENT_BODY, editInfoFragment, TAG);
                break;

            case R.layout.fragment_about_myself:
                fragmentTransaction.add(FRAGMENT_BODY, new AboutMyself(), TAG);
                break;

            case R.layout.fragment_local_info:
                fragmentTransaction.add(FRAGMENT_BODY, new LocalPersonalInfo(), TAG);
                break;
            default:

        }

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
	
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
