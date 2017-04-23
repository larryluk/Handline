package com.zylu.location.mvp.presenter;

import com.zylu.location.data.param.AddFriendParam;
import com.zylu.location.data.param.SearchUserParam;
import com.zylu.location.mvp.view.AddFriendView;

/**
 * Created by Larry on 2017/2/18.
 */

public interface AddFriendPresenter {
    void attachView(AddFriendView view);
    void detachView();
    void showUser(SearchUserParam param);

    /**
     * 加好友
     */
    void addFriend(AddFriendParam param);
}
