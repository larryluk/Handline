package com.zylu.location.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zylu.location.R;
import com.zylu.location.ui.fragment.SysMsgFragment;
import com.zylu.location.util.Constants;

import java.util.Locale;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by Larry on 2017/2/19.
 */

public class ConversationActivity extends AppCompatActivity {

    @BindView(R.id.conversation_toolbar)
    Toolbar conversationToolbar;
    @BindDrawable(R.drawable.back)
    Drawable back;
    private String mTargetId;
    private String mTargetIds;
    private String mTitle;
    private Conversation.ConversationType mConversationType;

    /**
     * Perform initialization of all fragments and loaders.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);

        init(getIntent());
    }

    private void init(Intent intent) {
        mTargetId = intent.getData().getQueryParameter("targetId");
        mTargetIds = intent.getData().getQueryParameter("targetIds");
        mTitle = intent.getData().getQueryParameter("title");
        setTitle(mTitle);
        String chatType = intent.getData().getLastPathSegment();//获得当前会话类型
        mConversationType = Conversation.ConversationType.valueOf(chatType.toUpperCase(Locale.getDefault()));
        enterFragment(mConversationType, mTargetId);

        //沉浸式效果
        setSupportActionBar(conversationToolbar);
        conversationToolbar.setNavigationIcon(back);
        conversationToolbar.setTitle("登录");
    }

    /**
     * 加载会话页面 ConversationFragment
     *
     * @param mConversationType 会话类型
     * @param mTargetId         目标 Id
     */
    private void enterFragment(final Conversation.ConversationType mConversationType, final String mTargetId) {
        ConversationFragment chatFragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();
        chatFragment.setUri(uri);

        SysMsgFragment sysMsgsFragment = (SysMsgFragment) getSupportFragmentManager().findFragmentById(R.id.sys_msg_fragment);

//        mConversationType == Conversation.ConversationType.SYSTEM
        if (mTargetId.equals(Constants.FRIEND_MSG)) {
            setTitle("系统消息");
            sysMsgsFragment.setType(mTargetId);
            getSupportFragmentManager().beginTransaction().hide(chatFragment).commit();
        } else if (mTargetId.equals(Constants.LOCATION_MSG)) {
            setTitle("位置请求");
            sysMsgsFragment.setType(mTargetId);
            getSupportFragmentManager().beginTransaction().hide(sysMsgsFragment).commit();
        } else {
            setTitle("位置信息");
            sysMsgsFragment.setType(mTargetId);
            getSupportFragmentManager().beginTransaction().hide(sysMsgsFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
