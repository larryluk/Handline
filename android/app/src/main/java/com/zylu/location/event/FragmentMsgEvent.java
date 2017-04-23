package com.zylu.location.event;

/**
 * Created by Larry on 2017/2/20.
 */

public class FragmentMsgEvent {
    private String type;
    private String msg;

    public FragmentMsgEvent(String msg, String type) {
        this.msg = msg;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
