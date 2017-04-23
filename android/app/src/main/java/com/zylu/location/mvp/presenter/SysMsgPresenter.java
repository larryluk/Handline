package com.zylu.location.mvp.presenter;

import com.zylu.location.data.param.AddFriendParam;
import com.zylu.location.mvp.view.SysMsgView;

/**
 * Created by Administrator on 2017/2/23.
 */

public interface SysMsgPresenter {
    void attachView(SysMsgView view);
    void detachView();
    void confirmFriend(AddFriendParam param);
}
