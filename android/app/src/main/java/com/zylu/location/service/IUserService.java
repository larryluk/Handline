package com.zylu.location.service;

import com.zylu.location.data.param.AddFriendParam;
import com.zylu.location.data.param.CommonParam;
import com.zylu.location.data.param.LoginParam;
import com.zylu.location.data.param.RegisterParam;
import com.zylu.location.data.param.SearchUserParam;
import com.zylu.location.data.result.CommonResult;
import com.zylu.location.data.result.GetFriendsResult;
import com.zylu.location.data.result.LoginResult;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Larry on 2017/2/12.
 */

public interface IUserService {
    @POST("api/login")
    Observable<LoginResult> getUser(@Body LoginParam user);

    @POST("api/regist")
    Observable<LoginResult> registUser(@Body RegisterParam param);

    @POST("api/searchUser")
    Observable<LoginResult> showUser(@Body SearchUserParam param);

    @POST("api/addFriend")
    Observable<CommonResult> addFriend(@Body AddFriendParam param);

    @POST("api/confirmFriend")
    Observable<CommonResult> confirmFriend(@Body AddFriendParam param);

    /**
     * 查询好友列表
     * @param param
     * @return
     */
    @POST("api/getFriends")
    Observable<GetFriendsResult> getFriends(@Body CommonParam param);

    @POST("api/updateInfo")
    Observable<CommonResult> updateInfo(@Body RegisterParam param);


}
