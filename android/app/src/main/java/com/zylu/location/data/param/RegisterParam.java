package com.zylu.location.data.param;

import com.zylu.location.bean.User;

/**
 * Created by Larry on 2017/2/15.
 */

public class RegisterParam extends CommonParam {
    private User user;

    public RegisterParam() {
    }

    public RegisterParam(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
