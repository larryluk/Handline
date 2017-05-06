package com.zylu.location.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zylu.location.R;
import com.zylu.location.event.FragmentMsgEvent;
import com.zylu.location.mvp.view.IFragmentContentManage;
import com.zylu.location.util.Constants;

import java.util.HashMap;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Larry on 2017/2/15.
 */

public class PersonalFragment extends Fragment {

    @BindView(R.id.personal_real_name)
    CardView personalRealName;
    @BindView(R.id.personal_email)
    CardView personalEmail;
    @BindView(R.id.personal_pwd)
    CardView personalPwd;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindString(R.string.personalFragmentTitle)
    String title;
    private IFragmentContentManage fragmentContentManage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        SharedPreferences sp = getActivity().getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE);
        tvName.setText(sp.getString(Constants.REAL_NAME, ""));
        tvEmail.setText(sp.getString(Constants.EMAIL, ""));

        if (fragmentContentManage == null) {
            fragmentContentManage = (IFragmentContentManage) getActivity();
        }
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

    @OnClick({R.id.personal_real_name, R.id.personal_email, R.id.personal_pwd})
    public void onClick(View view) {
        FragmentMsgEvent fragmentMsgEvent = null;
        switch (view.getId()) {
            case R.id.personal_real_name:
                fragmentMsgEvent = new FragmentMsgEvent("请输入真名...", "0");
                break;
            case R.id.personal_email:
                fragmentMsgEvent = new FragmentMsgEvent("请输入邮箱...", "1");
                break;
            case R.id.personal_pwd:
                fragmentMsgEvent = new FragmentMsgEvent("请输入新密码...", "2");
                break;

            default:
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("msg", fragmentMsgEvent.getMsg());
        param.put("type", fragmentMsgEvent.getType());
        fragmentContentManage.onChange(R.layout.fragment_change_info, param);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(fragmentContentManage == null) fragmentContentManage = (IFragmentContentManage) getActivity();
        fragmentContentManage.changeMenu(true, false, false);
        fragmentContentManage.changeTitle(title);
    }
}
