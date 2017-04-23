package com.zylu.handline.data.result;

import com.zylu.handline.bean.User;
import com.zylu.handline.util.Constants;

public class CommonResult {

    private String status = Constants.RESULT_OK;

    private String errMsg;

    public CommonResult() {
    }

    public CommonResult(String errMsg) {
        status = Constants.RESULT_ERR;
        this.errMsg = errMsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
