package com.gogh.afternoontea.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 9/12/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 9/12/2017 do fisrt create. </li>
 */

public class TypeWidget extends android.support.v7.widget.AppCompatTextView {

    private static final String TAG = "TypeWidget";

    public TypeWidget(Context context) {
        this(context, null);
    }

    public TypeWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TypeWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setPadding(5, 5, 5, 5);
        setTextColor(Color.WHITE);
        setBackgroundColor(Color.RED);
//        TintColor.setBackgroundTintList(this, TintColor.getPrimaryColor(getContext()));
    }

}
