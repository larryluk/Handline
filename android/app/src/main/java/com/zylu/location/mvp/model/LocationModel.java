package com.zylu.location.mvp.model;

import com.zylu.location.data.param.GetLocationParam;
import com.zylu.location.data.param.SendLocationParam;
import com.zylu.location.data.result.CommonResult;

import org.greenrobot.eventbus.Subscribe;

import rx.Subscriber;

/**
 * Created by Larry on 2017/2/22.
 */

public interface LocationModel {
    void getLocation(Subscriber<CommonResult> subscriber, GetLocationParam param);

    void sendLocation(Subscriber<CommonResult> subscriber, SendLocationParam param);
}
