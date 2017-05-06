package com.zylu.location.ui.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.zylu.location.Adapter.FriendsAdapter;
import com.zylu.location.R;
import com.zylu.location.bean.User;
import com.zylu.location.data.param.CommonParam;
import com.zylu.location.data.param.GetLocationParam;
import com.zylu.location.event.WaitLocationEvent;
import com.zylu.location.mvp.presenter.FriendsPresenter;
import com.zylu.location.mvp.presenter.LocationPresenter;
import com.zylu.location.mvp.presenter.impl.FriendsPresenterImpl;
import com.zylu.location.mvp.presenter.impl.LocationPresenterImpl;
import com.zylu.location.mvp.view.FriendsView;
import com.zylu.location.mvp.view.IFragmentContentManage;
import com.zylu.location.util.Constants;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/16.
 */

public class FriendsFragment extends Fragment implements FriendsView, OnItemClickListener {

    @BindView(R.id.friends)
    ListView friends;
    @BindView(R.id.refreshView)
    XRefreshView refreshView;
    @BindString(R.string.friendsFragmentTitle)
    String title;

    private long lastRefreshTime;
    private ProgressDialog dialog;
    private IFragmentContentManage fragmentContentManage;
    private FriendsPresenter friendsPresenter;
    private LocationPresenter locationPresenter;
    private List<User> users;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {
        if (fragmentContentManage == null) {
            fragmentContentManage = (IFragmentContentManage) getActivity();
        }

        friendsPresenter = new FriendsPresenterImpl();
        friendsPresenter.attachView(this);

        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("获取好友位置...");


        refreshView.setXRefreshViewListener(myRefreshView);
        refreshView.setPullLoadEnable(true);
        refreshView.setPullRefreshEnable(true);

        String userId = getActivity().getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE).getString(Constants.USER_ID, "");
        if(!userId.equals("")) {
            CommonParam commonParam = new CommonParam();
            commonParam.setUserId(userId);
            locationPresenter = new LocationPresenterImpl();
            locationPresenter.attachView(this);

            friendsPresenter.showFriends(commonParam);
            friends.setOnItemClickListener(this);
        }

    }


    private XRefreshView.XRefreshViewListener myRefreshView = new XRefreshView.XRefreshViewListener() {
        @Override
        public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshView.stopRefresh();
                    lastRefreshTime = refreshView.getLastRefreshTime();
                }
            }, 2000);
        }

        @Override
        public void onLoadMore(boolean isSilence) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    refreshView.stopLoadMore();
                }
            }, 2000);
        }

        @Override
        public void onRelease(float direction) {

        }

        @Override
        public void onHeaderMove(double headerMovePercent, int offsetY) {

        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentContentManage) {
            fragmentContentManage = (IFragmentContentManage) context;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentContentManage = null;
    }

    @Override
    public void showFriends(List<User> users) {
        this.users = users;
        FriendsAdapter adapter = new FriendsAdapter(getActivity(), R.layout.list_friends, users);
        friends.setAdapter(adapter);
    }

    @Override
    public void getFriendLocationSuccess() {
        EventBus.getDefault().post(new WaitLocationEvent());

        fragmentContentManage.onChange(R.layout.activity_main, null);
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

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final User user = users.get(position);

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("确认");
        dialog.setMessage("查看" + user.getRealName() + "位置信息");
        dialog.setCancelable(false);

        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GetLocationParam getLocationParam = new GetLocationParam();
                getLocationParam.setToUserId(user.getId());
                getLocationParam.setUserId(getActivity().getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE).getString(Constants.USER_ID, ""));

                locationPresenter.getLocation(getLocationParam);
            }
        });

        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //nothing to do
            }
        });

        dialog.show();


    }

    @Override
    public void onResume() {
        super.onResume();
        if(fragmentContentManage == null) fragmentContentManage = (IFragmentContentManage) getActivity();
        fragmentContentManage.changeMenu(true, false, false);
        fragmentContentManage.changeTitle(title);
    }
}
