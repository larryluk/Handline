package com.zylu.location.mvp.presenter.impl;

import android.support.annotation.NonNull;

import com.zylu.location.data.param.LoginParam;
import com.zylu.location.data.result.LoginResult;
import com.zylu.location.mvp.model.LoginModel;
import com.zylu.location.mvp.model.impl.LoginModelImpl;
import com.zylu.location.mvp.presenter.LoginPresenter;
import com.zylu.location.mvp.view.BaseView;
import com.zylu.location.mvp.view.LoginView;
import com.zylu.location.util.Constants;

import rx.Subscriber;

/**
 * Created by Larry on 2017/2/12.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView baseView;
    private LoginModel loginModel;

    @Override
    public void attachView(@NonNull LoginView view) {
        this.baseView = view;
    }

    public LoginPresenterImpl() {
        this.loginModel = new LoginModelImpl();
    }

    @Override
    public void detachView() {
        baseView = null;
    }



    private Subscriber<LoginResult> getLoginInstance(){
        return new Subscriber<LoginResult>() {
            @Override
            public void onStart() {
                baseView.showProgressDialog();
                super.onStart();
            }

            @Override
            public void onCompleted() {
                baseView.hideProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                baseView.showErrorMessage("网络故障");
                baseView.hideProgressDialog();
            }

            @Override
            public void onNext(LoginResult loginResult) {
                if(loginResult.getStatus().equals(Constants.RESULT_ERR)) {
                    baseView.showErrorMessage(loginResult.getErrMsg());
                    return ;
                }
                baseView.loginSuccess(loginResult);
            }
        };
    }

    @Override
    public void login(LoginParam loginParam) {
        if(loginParam.getUserName().equals("")) {
            baseView.showErrorMessage("用户名不能为空。");
            return;
        }

        if(loginParam.getPassword().equals("")) {
            baseView.showErrorMessage("密码不能为空。");
            return ;
        }

        if(loginModel != null) {
            loginModel.login(getLoginInstance(), loginParam );
        }
    }

}
