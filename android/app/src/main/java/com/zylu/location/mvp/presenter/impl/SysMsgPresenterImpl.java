package com.zylu.location.mvp.presenter.impl;

import com.zylu.location.data.param.AddFriendParam;
import com.zylu.location.data.result.CommonResult;
import com.zylu.location.mvp.model.LoginModel;
import com.zylu.location.mvp.model.impl.LoginModelImpl;
import com.zylu.location.mvp.presenter.LoginPresenter;
import com.zylu.location.mvp.presenter.SysMsgPresenter;
import com.zylu.location.mvp.view.SysMsgView;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/2/23.
 */

public class SysMsgPresenterImpl implements SysMsgPresenter {

    private final LoginModel model;
    private SysMsgView view;

    @Override
    public void attachView(SysMsgView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public SysMsgPresenterImpl() {
        this.model = new LoginModelImpl();
    }

    @Override
    public void confirmFriend(AddFriendParam param) {
        model.confirmFriend(new Subscriber<CommonResult>() {
            @Override
            public void onStart() {
                view.showProgressDialog();
            }

            @Override
            public void onCompleted() {
                view.hideProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                view.showErrorMessage("网络异常");
            }

            @Override
            public void onNext(CommonResult commonResult) {
                if(!commonResult.getStatus().equals("0")) {
                    view.hideProgressDialog();
                    view.showErrorMessage(commonResult.getErrMsg());
                    return;
                }

                view.confirmSuccess();
            }
        }, param);
    }
}
