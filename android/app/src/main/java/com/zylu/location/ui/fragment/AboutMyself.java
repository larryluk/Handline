package com.zylu.location.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zylu.location.R;
import com.zylu.location.mvp.view.IFragmentContentManage;

import butterknife.BindString;

/**
 * Created by Larry on 2017/5/3.
 */

public class AboutMyself extends Fragment {
    private IFragmentContentManage contentManage;
    @BindString(R.string.aboutMyselfFragmentTitle)
    String title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_myself, container, false);
        if(contentManage == null) contentManage = (IFragmentContentManage) getActivity();
        contentManage.changeTitle(title);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof IFragmentContentManage) {
            contentManage = (IFragmentContentManage) context;
        }
    }

    @Override
    public void onDetach() {
        contentManage = null;
        super.onDetach();
    }
}
