package com.gogh.afternoontea.utils;

import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;

import com.gogh.afternoontea.R;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/4/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/4/2017 do fisrt create. </li>
 */
public class TintColor {

    public static void setBackgroundTintList(@NonNull FloatingActionButton floatingActionButton, @ColorInt int color) {
        int[] colors = new int[]{color, color, color, color, color, color};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        floatingActionButton.setBackgroundTintList(new ColorStateList(states, colors));
    }

    /**
     * 根据设置的颜色值，取深色的色值作为点击的颜色
     *
     * @param orignal
     * @return
     */
    private static int getDarkColor(int orignal) {
        switch (orignal) {
            case R.color.colorBluePrimary:
                return R.color.colorBluePrimaryDark;
            case R.color.colorRedPrimary:
                return R.color.colorRedPrimaryDark;
            case R.color.colorBrownPrimary:
                return R.color.colorBrownPrimaryDark;
            case R.color.colorGreenPrimary:
                return R.color.colorGreenPrimaryDark;
            case R.color.colorPurplePrimary:
                return R.color.colorPurplePrimaryDark;
            case R.color.colorTealPrimary:
                return R.color.colorTealPrimaryDark;
            case R.color.colorPinkPrimary:
                return R.color.colorPinkPrimaryDark;
            case R.color.colorDeepPurplePrimary:
                return R.color.colorDeepPurplePrimaryDark;
            case R.color.colorOrangePrimary:
                return R.color.colorOrangePrimaryDark;
            case R.color.colorIndigoPrimary:
                return R.color.colorIndigoPrimaryDark;
            case R.color.colorLightGreenPrimary:
                return R.color.colorLightGreenPrimaryDark;
            case R.color.colorDeepOrangePrimary:
                return R.color.colorDeepOrangePrimaryDark;
            case R.color.colorLimePrimary:
                return R.color.colorLimePrimaryDark;
            case R.color.colorBlueGreyPrimary:
                return R.color.colorBlueGreyPrimaryDark;
            case R.color.colorCyanPrimary:
                return R.color.colorCyanPrimaryDark;
        }
        return orignal;
    }

    public static int[] getColorSchemeResources() {
        return new int[]{R.color.colorRedPrimary, R.color.colorBrownPrimary,
                R.color.colorOrangePrimary, R.color.colorLightGreenPrimary, R.color.colorIndigoPrimary,
                R.color.colorDeepPurplePrimaryCenter, R.color.colorPurplePrimary};
    }

}
