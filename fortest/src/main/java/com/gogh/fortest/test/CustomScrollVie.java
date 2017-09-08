package com.gogh.fortest.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 6/21/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 6/21/2017 do fisrt create. </li>
 */

public class CustomScrollVie extends ViewGroup {

    public CustomScrollVie(Context context) {
        this(context, null);
    }

    public CustomScrollVie(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomScrollVie(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

}
