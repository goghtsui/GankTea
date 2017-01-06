package com.gogh.afternoontea.iinterface;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 页面刷新加载回调</p>
 * <p> Created by <b>高晓峰</b> on 1/4/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/4/2017 do fisrt create. </li>
 */
public interface OnRefreshListener {

    /**
     *  页面下拉刷新
     */
    void onSwipeRefresh();

    /**
     * 页面上拉加载更多
     */
    void onLoadMore();

}
