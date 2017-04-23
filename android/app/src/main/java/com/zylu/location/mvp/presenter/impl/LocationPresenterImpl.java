package com.zylu.location.mvp.presenter.impl;

import android.util.Log;
import android.view.View;

import com.zylu.location.data.param.GetLocationParam;
import com.zylu.location.data.param.SendLocationParam;
import com.zylu.location.data.result.CommonResult;
import com.zylu.location.mvp.model.LocationModel;
import com.zylu.location.mvp.model.impl.LocationModelImpl;
import com.zylu.location.mvp.presenter.LocationPresenter;
import com.zylu.location.mvp.view.FriendsView;

import rx.Subscriber;

/**
 * Created by Larry on 2017/2/22.
 */

public class LocationPresenterImpl implements LocationPresenter {
    private LocationModel locationModel;
    private Object view;
    private static final String TAG = "LocationPresenterImpl";

    public LocationPresenterImpl() {
        this.locationModel = new LocationModelImpl();
    }
    /**
     * 索要位置信息
     *
     * @param param
     */
    @Override
    public void getLocation(GetLocationParam param) {
        locationModel.getLocation(new Subscriber<CommonResult>() {
            @Override
            public void onStart() {
                if(view instanceof FriendsView) {
                    FriendsView friendsView = (FriendsView) view;
                    friendsView.showProgressDialog();
                }
            }

            @Override
            public void onCompleted() {
                Log.d(TAG, "getLocation:onCompleted");
                if(view instanceof FriendsView) {
                    FriendsView friendsView = (FriendsView) view;
                    friendsView.hideProgressDialog();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "getLocation:error");
                Log.d(TAG, e.getMessage());
                e.printStackTrace();

                if(view instanceof FriendsView) {
                    FriendsView friendsView = (FriendsView) view;
                    friendsView.hideProgressDialog();
                    friendsView.showErrorMessage("网络异常");
                }
            }

            @Override
            public void onNext(CommonResult commonResult) {
                Log.d(TAG, "getLocation:success");
                if(view instanceof FriendsView) {
                    FriendsView friendsView = (FriendsView) view;

                    if(!commonResult.getStatus().equals("0")) {
                        friendsView.hideProgressDialog();
                        friendsView.showErrorMessage(commonResult.getErrMsg());
                        return ;
                    }

                    friendsView.getFriendLocationSuccess();
                }
            }
        }, param);
    }

    /**
     * 发送位置信息
     *
     * @param param
     */
    @Override
    public void sendLocation(SendLocationParam param) {
        locationModel.sendLocation(new Subscriber<CommonResult>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "sendLocation:onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "sendLocation:error");
            }

            @Override
            public void onNext(CommonResult commonResult) {
                Log.d(TAG, "sendLocation:success");
            }
        }, param);
    }

    @Override
    public void attachView(Object view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

}
