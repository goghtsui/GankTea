package com.gogh.afternoontea.iinterface;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: recyclerview的滑动回调</p>
 * <p> Created by <b>高晓峰</b> on 1/5/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/5/2017 do fisrt create. </li>
 */
public interface OnScrollListener {

    /**
     *  recyclerview已滚动到顶部
     */
    void onScrollToTop(int currentPage);

    /**
     * 两种情况下都会回调
     * 1. recyclerview滚动中
     * 2. 第一次进入页面，还未执行任何滚动操作
     */
    void onScrolling(int currentPage);

}
