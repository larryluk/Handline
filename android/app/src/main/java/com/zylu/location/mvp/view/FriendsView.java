package com.zylu.location.mvp.view;

import com.zylu.location.bean.User;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */

public interface FriendsView extends BaseView {
    void showFriends(List<User> friends);

    void getFriendLocationSuccess();
}
