package com.zylu.location.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zylu.location.Adapter.FriendsMsgAdapter;
import com.zylu.location.R;
import com.zylu.location.bean.RongMsg;
import com.zylu.location.data.result.RongResult;
import com.zylu.location.mvp.presenter.SysMsgPresenter;
import com.zylu.location.mvp.presenter.impl.SysMsgPresenterImpl;
import com.zylu.location.mvp.view.SysMsgView;
import com.zylu.location.util.Constants;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/23.
 */

public class SysMsgFragment extends Fragment implements SysMsgView{
    private List<RongMsg> data;
    private static final String TAG = "SysMsgFragment";
    private List<RongResult> list;
    private RecyclerView friendList;
    private ProgressDialog dialog;
    private SysMsgPresenter sysMsgPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sys_msg, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("正在搜索中");

        sysMsgPresenter = new SysMsgPresenterImpl();
        sysMsgPresenter.attachView(this);

        friendList = (RecyclerView) view.findViewById(R.id.friends_msg_list);

        String userId = getActivity().getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE).getString(Constants.USER_ID, "");
        data = DataSupport.where("userId = ?", userId).order("id desc").find(RongMsg.class);
        Log.d(TAG, new Gson().toJson(data));

        list = new ArrayList<RongResult>();
        Gson gson = new Gson();

        //取出extra的内容
        for(RongMsg m : data) {
            RongResult rongResult = gson.fromJson(m.getExtra(), RongResult.class);
            rongResult.setStatus(m.getStatus());
            rongResult.setErrMsg(String.valueOf(m.getId()));
            list.add(rongResult);
        }

        FriendsMsgAdapter friendsMsgAdapter = new FriendsMsgAdapter(list, sysMsgPresenter);
        friendList.setLayoutManager(new LinearLayoutManager(getActivity()));
        friendList.setAdapter(friendsMsgAdapter);

    }

    public void setType(String mTargetId) {

    }

    @Override
    public void confirmSuccess() {
        Toast.makeText(getActivity(), "添加好友成功", Toast.LENGTH_SHORT).show();
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
    public void showErrorMessage(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
