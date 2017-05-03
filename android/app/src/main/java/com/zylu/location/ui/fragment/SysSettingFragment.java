package com.zylu.location.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zylu.location.R;
import com.zylu.location.mvp.view.IFragmentContentManage;
import com.zylu.location.widget.ArrowTextView;

import java.util.HashMap;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Larry on 2017/5/3.
 */

public class SysSettingFragment extends Fragment {

    @BindView(R.id.personal_setting)
    ArrowTextView personalSetting;
    @BindView(R.id.about_myself)
    ArrowTextView aboutMyself;
    @BindString(R.string.sysSettingFragmentTitle) String title;

    private IFragmentContentManage contentManage;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sys_setting, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    public void init() {
        contentManage.changeTitle(title);
    }

    @OnClick(R.id.personal_setting)
    public void goPersonalSetting() {
        contentManage.onChange(R.layout.fragment_local_info, null);
    }

    @OnClick(R.id.about_myself)
    public void goAboutMyself() {
        contentManage.onChange(R.layout.fragment_about_myself, null);
    }

    @Override
    public void onAttach(Context context) {
        if(context instanceof IFragmentContentManage) {
            contentManage = (IFragmentContentManage) context;
        }
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        contentManage = null;
        super.onDetach();
    }
}
