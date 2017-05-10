package com.zylu.location.ui.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.zylu.location.R;
import com.zylu.location.bean.User;
import com.zylu.location.data.param.RegisterParam;
import com.zylu.location.event.FragmentMsgEvent;
import com.zylu.location.mvp.presenter.EditInfoPresenter;
import com.zylu.location.mvp.presenter.impl.EditInfoPresenterImpl;
import com.zylu.location.mvp.view.IFragmentContentManage;
import com.zylu.location.mvp.view.InfoView;
import com.zylu.location.util.Constants;
import com.zylu.location.widget.CleanableEditView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Larry on 2017/2/20.
 */

public class EditInfoFragment extends Fragment implements InfoView {
    @BindView(R.id.edit_value)
    CleanableEditView editValue;
    @BindView(R.id.info_submit)
    Button infoSubmit;
    @BindView(R.id.flag_img)
    CircleImageView flagImg;
    @BindString(R.string.editInfoFragmentTitle) String title;
    private IFragmentContentManage contentManage;
    private ProgressDialog dialog;
    private EditInfoPresenter editInfoPresenter;
    private FragmentMsgEvent fragmentMsgEvent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_info, container, false);

        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        if (contentManage == null) contentManage = (IFragmentContentManage) getActivity();

        contentManage.changeTitle(title);

        editValue.setAlpha(0.5f);
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(Constants.LOADING_MSG);

        editInfoPresenter = new EditInfoPresenterImpl();
        editInfoPresenter.attachView(this);

        Bundle arguments = getArguments();
        fragmentMsgEvent = new FragmentMsgEvent(arguments.getString("msg"), arguments.getString("type"));
        editValue.setHint(fragmentMsgEvent.getMsg());
        if (fragmentMsgEvent.getType().equals("2"))
            editValue.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
        super.onDetach();
        contentManage = null;
    }

    @OnClick(R.id.info_submit)
    public void onClick() {
        if (editValue.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        RegisterParam param = new RegisterParam();
        User user = new User();
        String s = editValue.getText().toString();
        String type = fragmentMsgEvent.getType();

        if (type.equals("0")) {
            user.setRealName(s);
            flagImg.setImageResource(R.drawable.real_name);
        }

        if (type.equals("1")) {
            flagImg.setImageResource(R.drawable.email);
            user.setEmail(s);
        }

        if (type.equals("2")) {
            user.setPassword(s);
            flagImg.setImageResource(R.drawable.pwd);
        }

        param.setUserId(getActivity().getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE).getString(Constants.USER_ID, ""));
        param.setUser(user);
        editInfoPresenter.changeInfo(param);
    }

    @Override
    public void changeSuccess() {
        Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor edit = getActivity().getSharedPreferences(Constants.USER_KEY, Constants.MODE_PRIVATE).edit();
        edit.clear();

        contentManage.onChange(R.layout.fragment_login, null);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        editInfoPresenter.detachView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (contentManage == null) contentManage = (IFragmentContentManage) getActivity();
        contentManage.changeMenu(false, false, false);
    }
}
