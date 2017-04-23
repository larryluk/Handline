package com.zylu.location.mvp.presenter.impl;

import com.zylu.location.data.param.RegisterParam;
import com.zylu.location.data.result.CommonResult;
import com.zylu.location.mvp.model.LoginModel;
import com.zylu.location.mvp.model.impl.LoginModelImpl;
import com.zylu.location.mvp.presenter.EditInfoPresenter;
import com.zylu.location.mvp.view.InfoView;

import rx.Subscriber;

/**
 * Created by Larry on 2017/2/20.
 */

public class EditInfoPresenterImpl implements EditInfoPresenter {
    private InfoView view;
    private LoginModel model;

    @Override
    public void attachView(InfoView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public EditInfoPresenterImpl() {
        this.model = new LoginModelImpl();
    }
    @Override
    public void changeInfo(RegisterParam param) {
        model.changeUser(getChangeInfoSubscriber(), param);
    }

    private Subscriber<CommonResult> getChangeInfoSubscriber() {
        return new Subscriber<CommonResult>() {
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
                view.hideProgressDialog();
                view.showErrorMessage("网络错误");
            }

            @Override
            public void onNext(CommonResult commonResult) {
                if(!commonResult.getStatus().equals("0")) {
                    view.showErrorMessage(commonResult.getErrMsg());
                    return ;
                }

                view.changeSuccess();
            }
        };
    }
}
