package com.zylu.location.mvp.presenter;


import android.view.View;

import com.zylu.location.data.param.GetLocationParam;
import com.zylu.location.data.param.SendLocationParam;

/**
 * Created by Larry on 2017/2/22.
 */

public interface LocationPresenter {
    /**
     * 索要位置信息
     * @param param
     */
    void getLocation(GetLocationParam param);

    /**
     * 发送位置信息
     * @param param
     */
    void sendLocation(SendLocationParam param);

    void attachView(Object view);
    void detachView();
}
