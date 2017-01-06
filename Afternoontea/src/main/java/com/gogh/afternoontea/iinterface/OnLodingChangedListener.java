package com.gogh.afternoontea.iinterface;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 加载数据状态回调</p>
 * <p> Created by <b>高晓峰</b> on 1/4/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/4/2017 do fisrt create. </li>
 */
public interface OnLodingChangedListener {

    /**
     * 开始加载数据前
     */
    void onLoadingStart();

    /**
     * 数据加载失败
     * @param isShowReload true - 第一次加载且加载失败，需要展示reload页面， false - 已有数据，加载更多失败
     */
    void onLoadingError(boolean isShowReload);

    /**
     * 加载完成（正常加载数据，失败不会执行该回调）
     */
    void onLoadingComplete();

}
