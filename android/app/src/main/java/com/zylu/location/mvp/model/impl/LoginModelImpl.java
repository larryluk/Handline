package com.zylu.location.mvp.model.impl;

import com.zylu.location.data.param.AddFriendParam;
import com.zylu.location.data.param.CommonParam;
import com.zylu.location.data.param.LoginParam;
import com.zylu.location.data.param.RegisterParam;
import com.zylu.location.data.param.SearchUserParam;
import com.zylu.location.data.result.CommonResult;
import com.zylu.location.data.result.GetFriendsResult;
import com.zylu.location.data.result.LoginResult;
import com.zylu.location.mvp.model.LoginModel;
import com.zylu.location.service.IUserService;
import com.zylu.location.util.HttpMethods;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Larry on 2017/2/12.
 */

public class LoginModelImpl implements LoginModel {

    private IUserService userService;

    public LoginModelImpl() {
        userService = HttpMethods.getInstance().create(IUserService.class);
    }

    /**
     * 登录接口
     *
     */
    @Override
    public void login(Subscriber<LoginResult> subscriber, LoginParam user) {
        userService.getUser(user)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 注册接口
     *
     * @param subscriber
     * @param param
     */
    @Override
    public void register(Subscriber<LoginResult> subscriber, RegisterParam param) {
        userService.registUser(param)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 查询用户
     *
     * @param subscriber
     * @param param
     */
    @Override
    public void showUser(Subscriber<LoginResult> subscriber, SearchUserParam param) {
        userService.showUser(param)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 添加好友
     *
     * @param subscriber
     * @param param
     */
    @Override
    public void addFriend(Subscriber<CommonResult> subscriber, AddFriendParam param) {
        userService.addFriend(param)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取好友列表
     *
     * @param subscriber
     * @param param
     */
    @Override
    public void getFriends(Subscriber<GetFriendsResult> subscriber, CommonParam param) {
        userService.getFriends(param)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 修改个人信息
     *
     * @param subscriber
     * @param param
     */
    @Override
    public void changeUser(Subscriber<CommonResult> subscriber, RegisterParam param) {
        userService.updateInfo(param)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void confirmFriend(Subscriber<CommonResult> subscriber, AddFriendParam param) {
        userService.confirmFriend(param)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
