package com.gogh.afternoontea.app;

import android.view.View;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/9/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/9/2017 do fisrt create. </li>
 */
public interface WebWidget {

    /**
     * 根布局
     * @return
     */
    int getLayoutResId();

    /**
     * 创建view
     * @return
     */
    void onCreateView(View rootView);

    /**
     * 绑定数据到view
     * @return
     */
    void onBindData();

    void copyContent();

    void openBySystemBrowser();

    void shareUrl();

    void onBackPressed();

    boolean canGoBack();

    void onDestroy();
}
