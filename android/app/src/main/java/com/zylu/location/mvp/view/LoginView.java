package com.zylu.location.mvp.view;

import com.zylu.location.data.result.LoginResult;

/**
 * Created by Larry on 2017/2/12.
 */

public interface LoginView extends BaseView {

    void loginSuccess(LoginResult userbean);

}
