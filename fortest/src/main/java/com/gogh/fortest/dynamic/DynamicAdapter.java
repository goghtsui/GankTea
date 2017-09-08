package com.gogh.fortest.dynamic;

import android.view.View;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 8/23/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 8/23/2017 do fisrt create. </li>
 */

public interface DynamicAdapter<T extends DynamicHolder> {

    int getLayoutId();

    T getViewHolder(View item);

    int getItemCount();

    void resetItemParams(View itemtView, int position);

    void onBindView(T t, int position);

}
