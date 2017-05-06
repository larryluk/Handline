package com.zylu.location.ui.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zylu.location.R;
import com.zylu.location.bean.FriendRequest;
import com.zylu.location.bean.User;
import com.zylu.location.data.param.AddFriendParam;
import com.zylu.location.data.param.SearchUserParam;
import com.zylu.location.data.result.LoginResult;
import com.zylu.location.mvp.presenter.AddFriendPresenter;
import com.zylu.location.mvp.presenter.impl.AddFriendPresenterImpl;
import com.zylu.location.mvp.view.AddFriendView;
import com.zylu.location.mvp.view.IFragmentContentManage;
import com.zylu.location.util.Constants;
import com.zylu.location.widget.CleanableEditView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Larry on 2017/2/18.
 */

public class AddFriendFragment extends Fragment implements AddFriendView {

    @BindView(R.id.add_user_name)
    CleanableEditView addUserName;
    @BindView(R.id.search_friend_btn)
    Button searchFriendBtn;
    @BindString(R.string.addFriendFragmentTitle) String title;

    private IFragmentContentManage fragmentContentManage;

    private AddFriendPresenter addFriendPresenter;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_friend, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        if (fragmentContentManage == null) {
            fragmentContentManage = (IFragmentContentManage) getActivity();
        }

        fragmentContentManage.changeTitle(title);

        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请稍等...");

        addFriendPresenter = new AddFriendPresenterImpl();
        addFriendPresenter.attachView(this);
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
        addFriendPresenter.detachView();
    }

    @Override
    public void searchSuccess(LoginResult result) {
        //更新UI
        User u = result.getUser();

        StringBuffer sb = new StringBuffer("姓名：")
                .append(u.getRealName())
                .append("\n")
                .append("邮箱：")
                .append(u.getEmail());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("用户信息");
        builder.setMessage(sb.toString());
        builder.setCancelable(false);
        builder.setPositiveButton("添加好友", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddFriendParam addFriendParam = new AddFriendParam();
                FriendRequest friendRequest = new FriendRequest();
                friendRequest.setReceiver(addUserName.getText().toString());
                addFriendParam.setUserId(getActivity().getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE).getString(Constants.USER_ID, ""));
                friendRequest.setSponsor(getActivity().getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE).getString(Constants.USER_NAME, ""));
                addFriendParam.setRequest(friendRequest);

                addFriendPresenter.addFriend(addFriendParam);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //nothink to do
            }
        });

        builder.show();

    }

    /**
     * 加好友
     */
    @Override
    public void addFriendSuccess() {
        Toast.makeText(getActivity(), "已发送好友请求", Toast.LENGTH_SHORT).show();
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

    @OnClick({R.id.search_friend_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_friend_btn:
                SearchUserParam searchUserParam = new SearchUserParam();
                searchUserParam.setUserId(getActivity().getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE).getString(Constants.USER_ID, ""));
                searchUserParam.setUserName(addUserName.getText().toString());

                addFriendPresenter.showUser(searchUserParam);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(fragmentContentManage == null) fragmentContentManage = (IFragmentContentManage) getActivity();
        fragmentContentManage.changeMenu(false, false,false);
    }
}
