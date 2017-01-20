package com.gogh.afternoontea.app;

import com.gogh.afternoontea.listener.OnCachePageNumChangedListener;
import com.gogh.afternoontea.listener.OnCardModeChangedListener;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/18/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/18/2017 do fisrt create. </li>
 */

public interface Notification {

    /**
     * 注册页面UI显示样式改变的监听
     *
     * @param onCardModeChangedListener
     */
    void registerCardModeChangedListener(OnCardModeChangedListener onCardModeChangedListener);

    /**
     * 取消对页面样式UI改变的监听
     *
     * @param onCardModeChangedListener
     */
    void unRegisterCardModeChangedListener(OnCardModeChangedListener onCardModeChangedListener);

    /**
     * 注册缓存页面数量设置监听
     *
     * @param onCachePageNumChangedListener
     */
    void registerCachePageNumChangedListener(OnCachePageNumChangedListener onCachePageNumChangedListener);

    /**
     * 取消缓存页面个数设置的监听
     *
     * @param onCachePageNumChangedListener
     */
    void unRegisterCachePageNumChangedListener(OnCachePageNumChangedListener onCachePageNumChangedListener);

    /**
     * 当页面UI样式设置发生改变，通知观察者刷新UI
     */
    void notifyCardModeChanged();

    /**
     * 重置viewpager缓存的页面个数
     *
     * @param count 缓存的个数
     */
    void notifyCachePageChanged(int count);
}
