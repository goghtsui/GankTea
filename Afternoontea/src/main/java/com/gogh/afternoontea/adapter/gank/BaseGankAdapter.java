package com.gogh.afternoontea.adapter.gank;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/28/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/28/2016 do fisrt create. </li>
 */
public interface BaseGankAdapter<T> {

    @NonNull
    T getItem(int position);

    void setData(List<T> datas);

    void addRefreshData(List<T> datas);

    void addLoadMoreData(List<T> datas);

    boolean isScrolledToBottom();

    void setScrollToBottom(boolean isBottom);

    void setLoadingError(boolean isLoadingError);

    void notifyByThemeChanged();

}
