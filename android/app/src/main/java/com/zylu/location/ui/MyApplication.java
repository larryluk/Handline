package com.zylu.location.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zylu.location.bean.RongMsg;
import com.zylu.location.data.param.SendLocationParam;
import com.zylu.location.data.result.RongResult;
import com.zylu.location.event.IMRefreshEvent;
import com.zylu.location.event.LocationEvent;
import com.zylu.location.mvp.presenter.LocationPresenter;
import com.zylu.location.mvp.presenter.impl.LocationPresenterImpl;
import com.zylu.location.util.Constants;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

import java.util.List;

import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;

/**
 * Created by Larry on 2017/2/19.
 */

public class MyApplication extends LitePalApplication implements AMapLocationListener {
    private static final String TAG = "MyApplication";
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private LocationPresenter locationPresenter;
    private Vibrator vibrate;
    private TextMessage textMessage;


    @Override
    public void onCreate() {
        super.onCreate();
        initRongIM();
    }

    private void initRongIM() {
        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
            Connector.getDatabase();

            //消息接收监听
            RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
                @Override
                public boolean onReceived(Message message, int i) {
                    if (message.getConversationType() == Conversation.ConversationType.PRIVATE) {
                        if (message.getTargetId().equals(Constants.SYETEM_NOMAL) || message.getTargetId().equals(Constants.SYETEM_OTHER)
                                || message.getTargetId().equals(Constants.SYSTEM_LOCATION)) {
                            MessageContent messageContent = message.getContent();
                            if (messageContent instanceof TextMessage) {//文本消息
                                textMessage = (TextMessage) messageContent;
                                Log.d(TAG, "Content:"+textMessage.getContent());
                                Log.d(TAG, "Extra:"+textMessage.getExtra());

                                if(message.getTargetId().equals(Constants.SYETEM_OTHER)) {
                                    //定位并发送位置信息
                                    locationPresenter = new LocationPresenterImpl();

                                    location();
                                }

                                if(message.getTargetId().equals(Constants.SYETEM_NOMAL)) {
                                    //好友申请

                                    RongMsg rongMsg = new RongMsg();
                                    rongMsg.setContext(textMessage.getContent());
                                    rongMsg.setExtra(textMessage.getExtra());
                                    rongMsg.setStatus("0");
                                    rongMsg.setUserId(getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE).getString(Constants.USER_ID, ""));

                                    //存到本地数据库
                                    rongMsg.save();

                                }

                                if(message.getTargetId().equals(Constants.SYSTEM_LOCATION)) {
                                    //显示好友地理位置
                                    SendLocationParam location = new Gson().fromJson(textMessage.getExtra(), SendLocationParam.class);
                                    LocationEvent event = new LocationEvent(location.getLatitude(), location.getLongitude(),
                                            location.getPoiName(), location.getCityName());

                                    EventBus.getDefault().post(event);
                                }



                            }
                            sendRongSystemMess();
                        }
                    }
                    return false;
                }
            });
        }
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

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 定位
     */
    private void location() {
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(10000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(false);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //只定位一次
        mLocationOption.setOnceLocation(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                double latitude = aMapLocation.getLatitude();//获取纬度
                double longitude = aMapLocation.getLongitude();//获取经度
                String poiName = aMapLocation.getPoiName();
                String cityName = aMapLocation.getCity();

                RongResult rongResult = new Gson().fromJson(textMessage.getExtra(), RongResult.class);
                SendLocationParam sendLocationParam = new SendLocationParam();
                sendLocationParam.setUserId(rongResult.getUserId());
                sendLocationParam.setLatitude(latitude);
                sendLocationParam.setLongitude(longitude);
                sendLocationParam.setPoiName(poiName);
                sendLocationParam.setCityName(cityName);

                if(locationPresenter == null) locationPresenter = new LocationPresenterImpl();

                locationPresenter.sendLocation(sendLocationParam);

            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }

            locationPresenter = null;
        }
    }
}
