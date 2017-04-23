package com.zylu.location.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import com.zylu.location.R;

import butterknife.BindDrawable;
import butterknife.ButterKnife;

/**
 * Created by Larry on 2017/2/12.
 */
public class CleanableEditView extends EditText {
    private final static String TAG = "ClearEditView";
    private Context mContext;
    private int drawableHeigh = 30; //删除键高度
    private int drawableWidth = 30; //删除键宽度

    @BindDrawable(R.drawable.delete)
    Drawable delete;

    public CleanableEditView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public CleanableEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CleanableEditView);
        drawableHeigh = ta.getDimensionPixelSize(R.styleable.CleanableEditView_drawableHeight, 30);
        drawableWidth = ta.getDimensionPixelSize(R.styleable.CleanableEditView_drawableWidth, 30);
        ta.recycle();

        init();
    }

    public CleanableEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CleanableEditView);
        drawableHeigh = ta.getDimensionPixelSize(R.styleable.CleanableEditView_drawableHeight, 30);
        drawableWidth = ta.getDimensionPixelSize(R.styleable.CleanableEditView_drawableWidth, 30);
        ta.recycle();
        init();

    }

    private void init() {
        ButterKnife.bind(this);

        //设置drawable大小
        delete.setBounds(0, 0, drawableWidth, drawableHeigh);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });
        setDrawable();
    }

    //设置删除图片
    private void setDrawable() {
        if(length() < 1)
            setCompoundDrawables(null, null, null, null);
        else
            setCompoundDrawables(null, null, delete, null);
    }

    // 处理删除事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (delete != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 100;
            if(rect.contains(eventX, eventY))
                setText("");
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
