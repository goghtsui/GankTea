package com.gogh.afternoontea.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/10/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/10/2017 do fisrt create. </li>
 */
public class AndroidUtil {

    @NonNull
    public static float[] getScreenSize(@NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
        return new float[]{mDisplayMetrics.widthPixels * 1.0f, mDisplayMetrics.density};
    }

}
