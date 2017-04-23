package com.zylu.location.service;


import com.zylu.location.data.param.GetLocationParam;
import com.zylu.location.data.param.SendLocationParam;
import com.zylu.location.data.result.CommonResult;
import com.zylu.location.data.result.LoginResult;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Larry on 2017/2/22.
 */

public interface ILocationService {
    @POST("api/getLocation")
    Observable<CommonResult> getLocation(@Body GetLocationParam param);

    @POST("api/sendLocation")
    Observable<CommonResult> sendLocation(@Body SendLocationParam param);
}
