package com.zylu.location.mvp.presenter;


import com.zylu.location.data.param.LoginParam;
import com.zylu.location.mvp.view.LoginView;

/**
 * Created by Larry on 2017/2/12.
 */

public interface LoginPresenter {
    void attachView(LoginView view);
    void detachView();
    void login(LoginParam loginParam);
}
