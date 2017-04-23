package com.zylu.location.mvp.model.impl;

import com.zylu.location.data.param.GetLocationParam;
import com.zylu.location.data.param.SendLocationParam;
import com.zylu.location.data.result.CommonResult;
import com.zylu.location.mvp.model.LocationModel;
import com.zylu.location.service.ILocationService;
import com.zylu.location.util.HttpMethods;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Larry on 2017/2/22.
 */

public class LocationModelImpl implements LocationModel {
    private ILocationService locationService;

    public LocationModelImpl() {
        locationService = HttpMethods.getInstance().create(ILocationService.class);
    }
    @Override
    public void getLocation(Subscriber<CommonResult> subscriber, GetLocationParam param) {
        locationService.getLocation(param)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void sendLocation(Subscriber<CommonResult> subscriber, SendLocationParam param) {
        locationService.sendLocation(param)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
