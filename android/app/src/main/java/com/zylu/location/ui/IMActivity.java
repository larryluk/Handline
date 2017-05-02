package com.zylu.location.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.zylu.location.R;
import com.zylu.location.util.Constants;


import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * Created by Larry on 2017/2/18.
 */

public class IMActivity extends AppCompatActivity{

    @BindView(R.id.im_toolbar)
    Toolbar imToolbar;
    @BindDrawable(R.drawable.back)
    Drawable back;
    @BindString(R.string.imActivityTitle) String title;
    private Menu imMenu;
    private static final String TAG = "IMActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        SharedPreferences sp = getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE);
        String token = sp.getString(Constants.RONG_TOKEN, "");
        sendRongSystemMess();

        if (token.equals("")) {
            //跳转至登录页
        }
        //沉浸式效果
        setSupportActionBar(imToolbar);
        imToolbar.setNavigationIcon(back);
        imToolbar.setTitle("登录");

        ConversationListFragment fragment = new ConversationListFragment();

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();

        fragment.setUri(uri);

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.conversationlist, fragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void sendRongSystemMess() {
        final UserInfo userInfoSystem = new UserInfo("0", "好友消息", Uri.parse(Constants.BASE_URL + "static/image/logo.png"));
        RongIM.getInstance().setCurrentUserInfo(userInfoSystem);
        RongIM.getInstance().setMessageAttachedUserInfo(true);
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String userId) {
                return userInfoSystem;//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
            }
        }, true);

        final UserInfo getLocationInfo = new UserInfo("1", "请求位置信息", Uri.parse(Constants.BASE_URL + "static/image/logo.png"));
        RongIM.getInstance().setCurrentUserInfo(userInfoSystem);
        RongIM.getInstance().setMessageAttachedUserInfo(true);
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String userId) {
                return getLocationInfo;//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
            }
        }, true);

        final UserInfo sendLocationInfo = new UserInfo("2", "发送位置信息", Uri.parse(Constants.BASE_URL + "static/image/logo.png"));
        RongIM.getInstance().setCurrentUserInfo(userInfoSystem);
        RongIM.getInstance().setMessageAttachedUserInfo(true);
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String userId) {
                return sendLocationInfo;//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
            }
        }, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        imToolbar.setTitle(title);
        super.onResume();
    }
}
