package com.zylu.location.mvp.model;

import com.zylu.location.data.param.AddFriendParam;
import com.zylu.location.data.param.CommonParam;
import com.zylu.location.data.param.LoginParam;
import com.zylu.location.data.param.RegisterParam;
import com.zylu.location.data.param.SearchUserParam;
import com.zylu.location.data.result.CommonResult;
import com.zylu.location.data.result.GetFriendsResult;
import com.zylu.location.data.result.LoginResult;

import rx.Subscriber;

/**
 * Created by Larry on 2017/2/12.
 */

public interface LoginModel {

    /**
     * 登录接口
     * @param subscriber
     */
    void login(Subscriber<LoginResult> subscriber , LoginParam user);

    /**
     * 注册接口
     * @param subscriber
     * @param param
     */
    void register(Subscriber<LoginResult> subscriber , RegisterParam param);

    /**
     * 查询用户
     * @param subscriber
     * @param param
     */
    void showUser(Subscriber<LoginResult> subscriber, SearchUserParam param);

    /**
     * 添加好友
     * @param subscriber
     * @param param
     */
    void addFriend(Subscriber<CommonResult> subscriber, AddFriendParam param);

    /**
     * 获取好友列表
     * @param subscriber
     * @param param
     */
    void getFriends(Subscriber<GetFriendsResult> subscriber, CommonParam param);

    /**
     * 修改个人信息
     * @param subscriber
     * @param param
     */
    void changeUser(Subscriber<CommonResult> subscriber, RegisterParam param);

    /**
     * 确认好友
     * @param subscriber
     * @param param
     */
    void confirmFriend(Subscriber<CommonResult> subscriber, AddFriendParam param);
}
