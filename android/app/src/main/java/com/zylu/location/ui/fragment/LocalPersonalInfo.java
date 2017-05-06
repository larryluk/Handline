package com.zylu.location.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zylu.location.R;
import com.zylu.location.mvp.view.IFragmentContentManage;
import com.zylu.location.util.Constants;
import com.zylu.location.widget.CleanableEditView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnFocusChange;

/**
 * Created by Larry on 2017/5/3.
 */

public class LocalPersonalInfo extends Fragment{
    @BindView(R.id.local_real_name)
    TextView localRealName;
    @BindView(R.id.local_age)
    CleanableEditView localAge;
    @BindView(R.id.local_address)
    CleanableEditView localAddress;
    @BindView(R.id.local_help_phone)
    CleanableEditView localHelpPhone;
    @BindView(R.id.local_blood)
    CleanableEditView localBlood;
    @BindView(R.id.local_ill_history)
    CleanableEditView localIllHistory;
    private IFragmentContentManage contentManage;

    @BindString(R.string.localInfoFragmentTitle)
    String title;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_info, container, false);

        ButterKnife.bind(this, view);
        init();
        return view;
    }

    /**
     * 初始化
     */
    private void init() {
        if(contentManage == null) contentManage = (IFragmentContentManage) getActivity();
        contentManage.changeTitle(title);
        sp = getActivity().getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE);
        edit = sp.edit();
        String realName = sp.getString(Constants.REAL_NAME, "请先登录");
        String addr = sp.getString(Constants.LOCAL_ADDRESS, "");
        String age = sp.getString(Constants.LOCAL_AGE, "");
        String phone = sp.getString(Constants.LOCAL_HELP_PHONE, "");
        String blood = sp.getString(Constants.LOCAL_BLOOD, "");
        String illHistory = sp.getString(Constants.LOCAL_ILL_HISTORY, "");

        localRealName.setText(realName);
        localAge.setText(age);
        localAddress.setText(addr);
        localHelpPhone.setText(phone);
        localBlood.setText(blood);
        localIllHistory.setText(illHistory);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentContentManage) {
            contentManage = (IFragmentContentManage) context;
        }
    }

    @Override
    public void onDetach() {
        contentManage = null;
        super.onDetach();
    }

    @OnFocusChange(R.id.local_age)
    public void fcAge(View v, boolean hasFocus) {
        if(hasFocus) return;

        String s = localAge.getText().toString();
        edit.putString(Constants.LOCAL_AGE, s);
        edit.commit();
    }

    @OnFocusChange(R.id.local_address)
    public void fcAddr(View v, boolean hasFocus) {
        if(hasFocus) return;

        String s = localAddress.getText().toString();
        edit.putString(Constants.LOCAL_ADDRESS, s);
        edit.commit();
    }

    @OnFocusChange(R.id.local_help_phone)
    public void fcPhone(View v, boolean hasFocus) {
        if(hasFocus) return;

        String s = localHelpPhone.getText().toString();
        edit.putString(Constants.LOCAL_HELP_PHONE, s);
        edit.commit();
    }

    @OnFocusChange(R.id.local_blood)
    public void fcBlood(View v, boolean hasFocus) {
        if(hasFocus) return;

        String s = localBlood.getText().toString();
        edit.putString(Constants.LOCAL_BLOOD, s);
        edit.commit();
    }

    @OnFocusChange(R.id.local_ill_history)
    public void fcIllHistory(View v, boolean hasFocus) {
        if(hasFocus) return;

        String s = localIllHistory.getText().toString();
        edit.putString(Constants.LOCAL_ILL_HISTORY, s);
        edit.commit();
    }
}
