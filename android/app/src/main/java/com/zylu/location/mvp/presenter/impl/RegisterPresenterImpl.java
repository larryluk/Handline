package com.zylu.location.mvp.presenter.impl;

import com.zylu.location.bean.User;
import com.zylu.location.data.param.RegisterParam;
import com.zylu.location.data.result.LoginResult;
import com.zylu.location.mvp.model.LoginModel;
import com.zylu.location.mvp.model.impl.LoginModelImpl;
import com.zylu.location.mvp.presenter.RegisterPresenter;
import com.zylu.location.mvp.view.LoginView;
import com.zylu.location.util.Constants;

import rx.Subscriber;

/**
 * Created by Larry on 2017/2/15.
 */

public class RegisterPresenterImpl implements RegisterPresenter {
    private LoginView baseView;
    private LoginModel loginModel;

    /**
     * 注册
     *
     * @param param
     */
    @Override
    public void register(RegisterParam param) {
        if(param.getUser().getUserName().equals("")) baseView.showErrorMessage("用户名必填");
        if(param.getUser().getPassword().equals("")) baseView.showErrorMessage("密码必填");
        if(param.getUser().getEmail().equals("")) baseView.showErrorMessage("邮箱必填");
        if(param.getUser().getRealName().equals("")) baseView.showErrorMessage("姓名必填");

        loginModel.register(getRegisterSubscriber(), param);
    }

    @Override
    public void attachView(LoginView view) {
        baseView = view;
    }

    @Override
    public void detachView() {
        baseView = null;
    }

    /**
     * 获取注册订阅者
     * @return
     */
    private Subscriber<LoginResult> getRegisterSubscriber() {
        return new Subscriber<LoginResult>() {
            @Override
            public void onStart() {
                super.onStart();
                baseView.showProgressDialog();
            }

            @Override
            public void onCompleted() {
                baseView.hideProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                baseView.showErrorMessage("网络错误");
            }

            @Override
            public void onNext(LoginResult loginResult) {
                baseView.hideProgressDialog();

                if(loginResult.getStatus().equals(Constants.RESULT_ERR)) {
                    baseView.showErrorMessage(loginResult.getErrMsg());
                    return ;
                }

                baseView.loginSuccess(loginResult);
            }
        };
    }

    public RegisterPresenterImpl() {
        this.loginModel = new LoginModelImpl();
    }

}
