package com.zylu.location.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zylu.location.R;
import com.zylu.location.event.TokenTimeOutEvent;
import com.zylu.location.util.Constants;

import org.greenrobot.eventbus.EventBus;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by Administrator on 2017/2/28.
 */
public class LaunchActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        init();
    }

    private void init() {
        RongIM.init(this);
        String token = getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE).getString(Constants.RONG_TOKEN, "");

        if(!token.equals("")) {
            RongIMClient.connect(token, getConnectCallback());
        } else {
            Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 连接融云的监听
     * @return
     */
    private RongIMClient.ConnectCallback getConnectCallback() {
        return new RongIMClient.ConnectCallback() {
            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                EventBus.getDefault().post(new TokenTimeOutEvent());

                Intent intent = new Intent(LaunchActivity.this, OtherActivity.class);
                startActivity(intent);
            }

            /**
             * 连接融云成功
             */
            @Override
            public void onSuccess(String s) {
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(intent);
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                EventBus.getDefault().post(new TokenTimeOutEvent());

                Intent intent = new Intent(LaunchActivity.this, OtherActivity.class);
                startActivity(intent);
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
