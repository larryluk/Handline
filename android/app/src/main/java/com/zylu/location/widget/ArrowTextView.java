package com.zylu.location.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zylu.location.R;

import butterknife.BindDrawable;
import butterknife.ButterKnife;

/**
 * Created by Larry on 2017/2/15.
 */

public class ArrowTextView extends TextView {
    @BindDrawable(R.drawable.arrow) Drawable arrow;
    private Context mContext;

    private int drawableHeigh = 30; //删除键高度
    private int drawableWidth = 30; //删除键宽度

    public ArrowTextView(Context context) {
        super(context);
        this.mContext = context;

        init();
    }

    public ArrowTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CleanableEditView);
        drawableWidth = ta.getDimensionPixelSize(R.styleable.ArrowTextView_arrowWidth, 30);
        drawableHeigh = ta.getDimensionPixelSize(R.styleable.ArrowTextView_arrowHeight, 30);
        ta.recycle();

        init();
    }

    private void init() {
        ButterKnife.bind(this);

        //设置drawable大小
        arrow.setBounds(0, 0, drawableWidth, drawableHeigh);

        //设置drawable在view中的位置
        setCompoundDrawables(null, null, arrow, null);
    }
}
