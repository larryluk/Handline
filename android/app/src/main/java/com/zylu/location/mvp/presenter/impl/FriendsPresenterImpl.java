package com.zylu.location.mvp.presenter.impl;

import com.zylu.location.bean.User;
import com.zylu.location.data.param.CommonParam;
import com.zylu.location.data.result.GetFriendsResult;
import com.zylu.location.mvp.model.LoginModel;
import com.zylu.location.mvp.model.impl.LoginModelImpl;
import com.zylu.location.mvp.presenter.FriendsPresenter;
import com.zylu.location.mvp.view.FriendsView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Larry on 2017/2/19.
 */

public class FriendsPresenterImpl implements FriendsPresenter {
    private FriendsView view;
    private LoginModel model;
    @Override
    public void attachView(FriendsView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    public FriendsPresenterImpl() {
        model = new LoginModelImpl();
    }

    @Override
    public void showFriends(CommonParam param) {
        model.getFriends(getFriendsResultSubscriber(), param);
    }

    private Subscriber<GetFriendsResult> getFriendsResultSubscriber(){
        return new Subscriber<GetFriendsResult>() {
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
            public void onNext(GetFriendsResult getFriendsResult) {
                if(!getFriendsResult.getStatus().equals("0")) {
                    view.showErrorMessage(getFriendsResult.getErrMsg());
                    return ;
                }

                List<User> friends = getFriendsResult.getFriends();
                view.showFriends(friends);
            }
        };
    }
}
