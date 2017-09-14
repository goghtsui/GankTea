package com.gogh.afternoontea.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.app.Initializer;
import com.gogh.afternoontea.utils.TintColor;
import com.gogh.afternoontea.widget.FloatingMenuWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/10/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/10/2017 do fisrt create. </li>
 */
public class FloatMenuButton implements Initializer {

    private RelativeLayout floatMenuContainer;

    private Context context;
    private CoordinatorLayout rootView;
    private OnFloatingMenuClickListener onFloatingMenuClickListener;

    @NonNull
    private List<View.OnClickListener> onClickListeners = new ArrayList<>();
    @NonNull
    private List<View> floatMenus = new ArrayList<>();

    public FloatMenuButton(Context context, CoordinatorLayout rootView) {
        this.context = context;
        this.rootView = rootView;
        init();
    }

    @Override
    public void init() {
        floatMenuContainer = (RelativeLayout) rootView.findViewById(R.id.home_activity_float_menu_container);

        /**
         * 搜索
         */
        View.OnClickListener searchClickListener = v -> {
            if (onFloatingMenuClickListener != null) {
                onFloatingMenuClickListener.onSearchListener(v);
            }
        };

        /**
         * 投稿
         */
        View.OnClickListener contributeClickListener = v -> {
            if (onFloatingMenuClickListener != null) {
                onFloatingMenuClickListener.onContributeListener(v);
            }
        };

        /**
         * 主题
         */
        View.OnClickListener themeClickListener = v -> {
            if (onFloatingMenuClickListener != null) {
                onFloatingMenuClickListener.onThemeChangedListener(v);
            }
        };

        /**
         * 设置
         */
        View.OnClickListener settingClickListener = v -> {
            if (onFloatingMenuClickListener != null) {
                onFloatingMenuClickListener.onSettingsListener(v);
            }
        };

        onClickListeners.add(themeClickListener);
        onClickListeners.add(searchClickListener);
        onClickListeners.add(contributeClickListener);
        onClickListeners.add(settingClickListener);
    }

    @Override
    public void initView() {
    }

    @NonNull
    private List<View> createView() {

        for (int i = 0; i < onClickListeners.size(); i++) {
            FloatingActionButton floatMenu = (FloatingActionButton) floatMenuContainer.getChildAt(i);
            floatMenu.setOnClickListener(onClickListeners.get(i));
            floatMenus.add(floatMenu);
        }

        return floatMenus;
    }

    /**
     *  初始化悬浮菜单的背景色
     * @param themeColor 主题色值
     */
    public void initFloatMenuBackground(int themeColor) {
        for (int i = 0; i < floatMenus.size(); i++) {
            TintColor.setBackgroundTintList((FloatingActionButton) floatMenus.get(i), themeColor);
        }
    }

    /**
     * 重置悬浮菜单的背景色
     * @param themeColor 新的色值
     */
    public void resetFloatMenuBackground(int themeColor) {
        for (int i = 0; i < floatMenus.size(); i++) {
            TintColor.setBackgroundTintList((FloatingActionButton) floatMenus.get(i), ContextCompat.getColor(context, themeColor));
        }
    }

    @NonNull
    public FloatingMenuWidget create() {
        return new FloatingMenuWidget(context, floatMenuContainer, createView());
    }

    public void setOnFloatingMenuClickListener(OnFloatingMenuClickListener onFloatingMenuClickListener) {
        this.onFloatingMenuClickListener = onFloatingMenuClickListener;
    }

    public interface OnFloatingMenuClickListener {
        /**
         * 主题切换菜单点击事件
         *
         * @param v
         */
        void onThemeChangedListener(View v);

        /**
         * 搜索菜单点击事件
         *
         * @param v
         */
        void onSearchListener(View v);

        /**
         * 投稿菜单点击事件
         *
         * @param v
         */
        void onContributeListener(View v);

        /**
         * 设置菜单点击事件
         *
         * @param v
         */
        void onSettingsListener(View v);
    }

}
