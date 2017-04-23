package com.zylu.location.mvp.presenter.impl;

import com.zylu.location.data.param.AddFriendParam;
import com.zylu.location.data.param.LoginParam;
import com.zylu.location.data.param.SearchUserParam;
import com.zylu.location.data.result.CommonResult;
import com.zylu.location.data.result.LoginResult;
import com.zylu.location.mvp.model.LoginModel;
import com.zylu.location.mvp.model.impl.LoginModelImpl;
import com.zylu.location.mvp.presenter.AddFriendPresenter;
import com.zylu.location.mvp.view.AddFriendView;

import rx.Subscriber;

/**
 * Created by Larry on 2017/2/18.
 */

public class AddFriendPresenterImpl implements AddFriendPresenter {
    private AddFriendView view;
    private LoginModel loginModel;

    @Override
    public void attachView(AddFriendView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public AddFriendPresenterImpl() {
        loginModel = new LoginModelImpl();
    }


    @Override
    public void showUser(SearchUserParam param) {
        if(param.getUserName().equals("")) {
            view.showErrorMessage("用户名不能为空");
            return ;
        }

        loginModel.showUser(getUserSub(), param);
    }

    /**
     * 加好友
     */
    @Override
    public void addFriend(AddFriendParam param) {
        loginModel.addFriend(addFriendSub(), param);
    }


    /**
     * 添加好友的订阅
     * @return
     */
    private Subscriber<CommonResult> addFriendSub() {
        return new Subscriber<CommonResult>() {
            @Override
            public void onStart() {
                view.showProgressDialog();
            }

            @Override
            public void onCompleted() {
                view.hideProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                view.hideProgressDialog();
                view.showErrorMessage("网络错误");
            }

            @Override
            public void onNext(CommonResult commonResult) {
                if(!commonResult.getStatus().equals("0")) {
                    view.showErrorMessage(commonResult.getErrMsg());
                    return ;
                }


                view.addFriendSuccess();
            }
        };
    }

    /**
     * 查询用户的订阅
     * @return
     */
    private Subscriber<LoginResult> getUserSub() {
        return new Subscriber<LoginResult>() {
            @Override
            public void onStart() {
                view.showProgressDialog();
            }

            @Override
            public void onCompleted() {
                view.hideProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                view.hideProgressDialog();
                view.showErrorMessage("网络错误");
            }

            @Override
            public void onNext(LoginResult loginResult) {
                if(!loginResult.getStatus().equals("0")) {
                    //处理错误
                    return;
                }

                view.searchSuccess(loginResult);
            }
        };
    }
}
