package com.zylu.location.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zylu.location.R;
import com.zylu.location.bean.FriendRequest;
import com.zylu.location.bean.RongMsg;
import com.zylu.location.data.param.AddFriendParam;
import com.zylu.location.data.result.RongResult;
import com.zylu.location.mvp.presenter.SysMsgPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/23.
 */

public class FriendsMsgAdapter extends RecyclerView.Adapter<FriendsMsgAdapter.ViewHolder> {
    private final SysMsgPresenter presenter;
    private List<RongResult> result;
    private int position;

    public FriendsMsgAdapter(List<RongResult> result, SysMsgPresenter presenter) {
        this.result = result;
        this.presenter = presenter;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_friends_msg, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final RongResult rongResult = result.get(position);

        holder.msg.setText(rongResult.getMsg());
        if(rongResult.getStatus().equals("0")) {
            holder.ok.setVisibility(View.VISIBLE);
            holder.ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FriendRequest friendRequest = new FriendRequest();
                    friendRequest.setReceiver(rongResult.getToUserId());
                    friendRequest.setSponsor(rongResult.getUserId());

                    AddFriendParam addFriendParam = new AddFriendParam();
                    addFriendParam.setRequest(friendRequest);

                    presenter.confirmFriend(addFriendParam);
                    RongMsg msg = new RongMsg();
                    msg.setStatus("1");
                    long id = Long.valueOf(rongResult.getErrMsg());
                    msg.update(id);

                    holder.ok.setText("已同意");
                    holder.ok.setEnabled(false);
                }
            });
        } else {
            holder.ok.setEnabled(false);
            holder.ok.setText("已同意");
        }

    }


    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView msg;
        Button ok;

        public ViewHolder(View view) {
            super(view);
            msg = (TextView) view.findViewById(R.id.msg);
            ok = (Button) view.findViewById(R.id.ok);
        }
    }
}
