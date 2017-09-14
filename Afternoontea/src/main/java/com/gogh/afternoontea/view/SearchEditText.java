package com.gogh.afternoontea.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.gogh.afternoontea.app.Initializer;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/17/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/17/2017 do fisrt create. </li>
 */
public class SearchEditText extends android.support.v7.widget.AppCompatEditText implements Initializer {

    private static final String TAG = "SearchEditText";

    private OnKeyboardSearchKeyClickListener mSearchKeyListener;

    private OnDrawableClickedListener onDrawableClickedListener;

    private OnKeyboardDismissedListener mOnKeyboardDismissedListener;

    public SearchEditText(Context context) {
        this(context, null);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void init() {
        setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (mSearchKeyListener != null) {
                        mSearchKeyListener.onSearchKeyClicked(v.getText().toString());
                    }
                }
                return false;
            }
        });

    }

    @Override
    public void initView() {
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent ev) {
        if (ev.getKeyCode() == KeyEvent.KEYCODE_BACK && mOnKeyboardDismissedListener != null) {
            mOnKeyboardDismissedListener.onKeyboardDismissed();
        }
        return super.onKeyPreIme(keyCode, ev);
    }

    public void setOnKeyboardDismissedListener(OnKeyboardDismissedListener onKeyboardDismissedListener) {
        mOnKeyboardDismissedListener = onKeyboardDismissedListener;
    }

    public void setOnSearchKeyListener(OnKeyboardSearchKeyClickListener searchKeyListener) {
        mSearchKeyListener = searchKeyListener;
    }

    public void setOnDrawableClickedListener(OnDrawableClickedListener onDrawableClickedListener) {
        this.onDrawableClickedListener = onDrawableClickedListener;
    }

    public interface OnKeyboardDismissedListener {
        void onKeyboardDismissed();
    }

    /**
     * 判断DrawableLeft/DrawableRight是否被点击
     *
     * @param event
     * @author 高晓峰
     * @date 9/12/2017
     * @ChangeLog: <li> 高晓峰 on 9/12/2017 </li>
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 触摸状态
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // 监听DrawableLeft
            if (onDrawableClickedListener != null) {
                // 判断DrawableLeft是否被点击
                Drawable drawableLeft = getCompoundDrawables()[0];
                // 当按下的位置 < 在EditText的到左边间距+图标的宽度+Padding
                if (drawableLeft != null && event.getRawX() <= (getLeft() + getTotalPaddingLeft() + drawableLeft.getBounds().width())) {
                    // 执行DrawableLeft点击事件
                    onDrawableClickedListener.onDrawableLeftClick();
                    return true;
                }
            }

            // 监听DrawableRight
            if (onDrawableClickedListener != null) {
                Drawable drawableRight = getCompoundDrawables()[2];
                // 当按下的位置 > 在EditText的到右边间距-图标的宽度-Padding
                if (drawableRight != null && event.getRawX() >= (getRight() - getTotalPaddingRight() - drawableRight.getBounds().width())) {
                    // 执行DrawableRight点击事件
                    onDrawableClickedListener.onDrawableRightClick();
                    return true;
                }
            }
        }

        return super.onTouchEvent(event);
    }

    public interface OnKeyboardSearchKeyClickListener {
        void onSearchKeyClicked(String searchText);
    }


    /**
     * 开头或者结尾设置的图标的点击事件
     *
     * @author 高晓峰
     * @date 9/12/2017
     * @ChangeLog: <li> 高晓峰 on 9/12/2017 </li>
     */
    public interface OnDrawableClickedListener {

        /**
         * 左侧图标点击事件
         *
         * @author 高晓峰
         * @date 9/12/2017
         * @ChangeLog: <li> 高晓峰 on 9/12/2017 </li>
         */
        void onDrawableLeftClick();

        /**
         * 右侧图标点击事件
         *
         * @author 高晓峰
         * @date 9/12/2017
         * @ChangeLog: <li> 高晓峰 on 9/12/2017 </li>
         */
        void onDrawableRightClick();
    }

}
