package com.zylu.location.ui.fragment;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.zylu.location.R;
import com.zylu.location.bean.User;
import com.zylu.location.data.param.RegisterParam;
import com.zylu.location.data.result.LoginResult;
import com.zylu.location.mvp.presenter.RegisterPresenter;
import com.zylu.location.mvp.presenter.impl.RegisterPresenterImpl;
import com.zylu.location.mvp.view.BaseView;
import com.zylu.location.mvp.view.IFragmentContentManage;
import com.zylu.location.mvp.view.LoginView;
import com.zylu.location.widget.CleanableEditView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Larry on 2017/2/15.
 */

public class RegisterFragment extends Fragment implements LoginView {
    @BindView(R.id.rUserName)
    CleanableEditView rUserName;
    @BindView(R.id.rPassWord)
    CleanableEditView rPassWord;
    @BindView(R.id.rEmail)
    CleanableEditView rEmail;
    @BindView(R.id.rRealName)
    CleanableEditView rRealName;
    @BindView(R.id.registBtn)
    Button registBtn;
    @BindString(R.string.registFragmentTitle)
    String title;

    private RegisterPresenter registerPresenter;
    private IFragmentContentManage fragmentContentManage;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("正在搜索中");

        registerPresenter = new RegisterPresenterImpl();
        registerPresenter.attachView(this);

        fragmentContentManage.changeTitle(title);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentContentManage) {
            fragmentContentManage = (IFragmentContentManage) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentContentManage = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        registerPresenter.detachView();
    }

    @Override
    public void showProgressDialog() {
        dialog.show();
    }

    @Override
    public void hideProgressDialog() {
        dialog.dismiss();
    }

    @Override
    public void loginSuccess(LoginResult result) {
        //回到登录页
        fragmentContentManage.onChange(R.layout.fragment_login, null);
    }

    @Override
    public void showErrorMessage(String text) {
        Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.registBtn)
    public void regist() {
        String userName = rUserName.getText().toString();
        String password = rPassWord.getText().toString();
        String email = rEmail.getText().toString();
        String realName = rRealName.getText().toString();

        User user = new User(userName, password, email, realName);
        RegisterParam rp = new RegisterParam(user);

        registerPresenter.register(rp);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(fragmentContentManage == null) fragmentContentManage = (IFragmentContentManage) getActivity();
        fragmentContentManage.changeMenu(false, false);
    }
}
