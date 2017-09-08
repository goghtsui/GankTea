package com.gogh.afternoontea.preference;

import android.support.annotation.NonNull;

import com.gogh.afternoontea.app.Notification;
import com.gogh.afternoontea.listener.OnCachePageNumChangedListener;
import com.gogh.afternoontea.listener.OnCardModeChangedListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/12/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/12/2017 do fisrt create. </li>
 */
public class PreferenceManager implements Notification {

    @NonNull
    private static List<OnCardModeChangedListener> cardModeListeners = new ArrayList<>();

    @NonNull
    private static List<OnCachePageNumChangedListener> cachePageListeners = new ArrayList<>();

    private PreferenceManager() {
    }

    public static PreferenceManager newInstance() {
        return SingleHolder.MANAGER;
    }

    /**
     * 注册页面UI显示样式改变的监听
     *
     * @param onCardModeChangedListener
     */
    @Override
    public void registerCardModeChangedListener(OnCardModeChangedListener onCardModeChangedListener) {
        cardModeListeners.add(onCardModeChangedListener);
    }

    /**
     * 取消对页面样式UI改变的监听
     *
     * @param onCardModeChangedListener
     */
    @Override
    public void unRegisterCardModeChangedListener(OnCardModeChangedListener onCardModeChangedListener) {
        cardModeListeners.remove(onCardModeChangedListener);
    }

    /**
     * 注册缓存页面数量设置监听
     *
     * @param onCachePageNumChangedListener
     */
    @Override
    public void registerCachePageNumChangedListener(OnCachePageNumChangedListener onCachePageNumChangedListener) {
        cachePageListeners.add(onCachePageNumChangedListener);
    }

    /**
     * 取消缓存页面个数设置的监听
     *
     * @param onCachePageNumChangedListener
     */
    @Override
    public void unRegisterCachePageNumChangedListener(OnCachePageNumChangedListener onCachePageNumChangedListener) {
        cachePageListeners.remove(onCachePageNumChangedListener);
    }

    /**
     * 当页面UI样式设置发生改变，通知观察者刷新UI
     */
    @Override
    public void notifyCardModeChanged() {
        Iterator<OnCardModeChangedListener> iterator = cardModeListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onChanged();
        }
    }

    /**
     * 重置viewpager缓存的页面个数
     *
     * @param count 缓存的个数
     */
    @Override
    public void notifyCachePageChanged(int count) {
        Iterator<OnCachePageNumChangedListener> iterator = cachePageListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onChanged(count);
        }
    }

    public void clear() {
        cardModeListeners.clear();
        cachePageListeners.clear();
    }

    private static final class SingleHolder {
        private static final PreferenceManager MANAGER = new PreferenceManager();
    }

}
