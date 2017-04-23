package com.zylu.location.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.zylu.location.R;
import com.zylu.location.bean.User;
import com.zylu.location.widget.ArrowTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry on 2017/2/19.
 */

public class FriendsAdapter extends ArrayAdapter<User> {
    private final List<User> friends;
    private Context context;
    private int resource;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param friends  The objects to represent in the ListView.
     */
    public FriendsAdapter(Context context, int resource, List<User> friends) {
        super(context, resource, friends);
        this.context = context;
        this.friends = friends;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = friends.get(position);

        ViewHolder view = null;
        if (convertView != null) {
            view = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            view = new ViewHolder(convertView);
            convertView.setTag(view);
        }

        view.friendsName.setText(user.getRealName());

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.friends_name)
        ArrowTextView friendsName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
