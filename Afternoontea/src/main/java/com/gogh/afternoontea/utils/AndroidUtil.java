package com.gogh.afternoontea.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import com.gogh.afternoontea.main.BaseAppCompatActivity;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/10/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/10/2017 do fisrt create. </li>
 */
public class AndroidUtil {

    public static int[] getScreenSize(Context context) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        ((BaseAppCompatActivity)context).getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        return new int[]{mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels};
    }

}
