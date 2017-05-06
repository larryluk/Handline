package com.zylu.location.ui.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zylu.location.R;
import com.zylu.location.mvp.view.IFragmentContentManage;
import com.zylu.location.util.Constants;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry on 2017/5/3.
 */

public class AboutMyself extends Fragment {
    @BindView(R.id.about_myself_web)
    WebView aboutMyselfWeb;
    private IFragmentContentManage contentManage;
    @BindString(R.string.aboutMyselfFragmentTitle)
    String title;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_myself, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("正在访问...");
        dialog.show();

        if (contentManage == null) contentManage = (IFragmentContentManage) getActivity();
        contentManage.changeTitle(title);
        aboutMyselfWeb.loadUrl(Constants.MYSELF_URL);
        aboutMyselfWeb.setWebViewClient(new MyClient());
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

    class MyClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            dialog.dismiss();
            super.onPageFinished(view, url);
        }
    }
}


