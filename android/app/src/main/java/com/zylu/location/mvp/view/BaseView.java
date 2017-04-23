package com.zylu.location.mvp.view;

import com.zylu.location.bean.User;
import com.zylu.location.data.result.LoginResult;

/**
 * Created by Larry on 2017/2/12.
 */

public interface BaseView {
    void showProgressDialog();
    void hideProgressDialog();
    void showErrorMessage(String text);
}
