package com.zylu.location.mvp.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Created by Larry on 2016/12/11.
 */

public interface IFragmentContentManage {

    /**
     * 需要传值的fragment
     * @param FragmentID
     * @param intent
     */
    void onChange(@NonNull int FragmentID, HashMap<String, Object> params);

    void changeMenu(@NonNull boolean addFriendFlag, @NonNull boolean registerFlag, @NonNull boolean saveFlag);

    /**
     * 改变标题
     * @param title
     */
    void changeTitle(@NonNull String title);
}
