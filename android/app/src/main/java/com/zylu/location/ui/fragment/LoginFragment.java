package com.zylu.location.ui.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zylu.location.R;
import com.zylu.location.bean.User;
import com.zylu.location.data.param.LoginParam;
import com.zylu.location.data.result.LoginResult;
import com.zylu.location.event.TokenTimeOutEvent;
import com.zylu.location.mvp.presenter.LoginPresenter;
import com.zylu.location.mvp.presenter.impl.LoginPresenterImpl;
import com.zylu.location.mvp.view.IFragmentContentManage;
import com.zylu.location.mvp.view.LoginView;
import com.zylu.location.util.Constants;
import com.zylu.location.widget.CleanableEditView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Larry on 2017/1/2.
 */

public class LoginFragment extends Fragment implements LoginView {


    @BindView(R.id.userName)
    CleanableEditView userName;
    @BindView(R.id.password)
    CleanableEditView password;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindString(R.string.loginFragmentTitle)
    String title;

    private ProgressDialog dialog;
    private LoginPresenter loginPresenter;
    private IFragmentContentManage fragmentContentManage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        if (fragmentContentManage == null) {
            fragmentContentManage = (IFragmentContentManage) getActivity();
        }

        fragmentContentManage.changeMenu(false, false, false);
        userName.setAlpha(0.8f);
        password.setAlpha(0.8f);

        SharedPreferences sp = getActivity().getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE);
        userName.setText(sp.getString(Constants.USER_NAME, ""));

        loginPresenter = new LoginPresenterImpl();
        loginPresenter.attachView(this);

        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(Constants.LOADING_MSG);
    }

    @OnClick(R.id.loginBtn)
    public void login() {
        String un = userName.getText().toString();
        String pwd = password.getText().toString();

        LoginParam lp = new LoginParam(un, pwd);

        loginPresenter.login(lp);

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
        Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();

        User user = result.getUser();
        SharedPreferences.Editor edit = getActivity().getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE).edit();
        edit.putString(Constants.USER_ID, user.getId());
        edit.putString(Constants.USER_NAME, user.getUserName());
        edit.putString(Constants.REAL_NAME, user.getRealName());
        edit.putString(Constants.ROLE, user.getRole());
        edit.putString(Constants.EMAIL, user.getEmail());
        edit.putString(Constants.RONG_TOKEN, result.getToken());
        edit.commit();

        fragmentContentManage.onChange(R.layout.activity_main, null);
    }


    @Override
    public void showErrorMessage(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
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
    public void onResume() {
        super.onResume();
        if (fragmentContentManage == null)
            fragmentContentManage = (IFragmentContentManage) getActivity();
        fragmentContentManage.changeMenu(false, false, true);
        fragmentContentManage.changeTitle(title);

    }

    @OnClick(R.id.tv_register)
    public void register() {
        fragmentContentManage.onChange(R.layout.fragment_register, null);
    }
}
