package com.zylu.location.mvp.presenter;

import com.zylu.location.data.param.RegisterParam;
import com.zylu.location.mvp.view.LoginView;

/**
 * Created by Larry on 2017/2/15.
 */

public interface RegisterPresenter {
    /**
     * 注册
     * @param param
     */
    void register(RegisterParam param);

    void attachView(LoginView view);
    void detachView();
}
