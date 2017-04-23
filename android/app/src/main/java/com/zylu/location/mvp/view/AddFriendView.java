package com.zylu.location.mvp.view;

import com.zylu.location.data.result.LoginResult;

/**
 * Created by Larry on 2017/2/18.
 */

public interface AddFriendView extends BaseView{
    void searchSuccess(LoginResult result);

    /**
     * 加好友
     */
    void addFriendSuccess();
}
