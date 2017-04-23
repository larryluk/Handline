package com.zylu.location.mvp.presenter;

import com.zylu.location.data.param.CommonParam;
import com.zylu.location.mvp.view.FriendsView;

/**
 * Created by Larry on 2017/2/19.
 */

public interface FriendsPresenter {
    void attachView(FriendsView view);
    void detachView();
    void showFriends(CommonParam param);
}
