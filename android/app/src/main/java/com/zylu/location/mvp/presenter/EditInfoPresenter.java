package com.zylu.location.mvp.presenter;

import com.zylu.location.data.param.RegisterParam;
import com.zylu.location.mvp.view.InfoView;

/**
 * Created by Larry on 2017/2/20.
 */

public interface EditInfoPresenter {
    void attachView(InfoView view);
    void detachView();
    void changeInfo(RegisterParam param);
}
