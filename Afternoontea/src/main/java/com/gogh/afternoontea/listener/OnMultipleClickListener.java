package com.gogh.afternoontea.listener;

import android.view.View;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 悬浮按钮的点击和长按事件回调</p>
 * <p> Created by <b>高晓峰</b> on 1/6/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/6/2017 do fisrt create. </li>
 */
public interface OnMultipleClickListener {

    /**
     *  点击事件
     * @param view
     */
    void onClickListener(View view);

    /**
     * 长按事件
     * @param view
     */
    void onLongClickListener(View view);
}
