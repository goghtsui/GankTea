package com.gogh.afternoontea.utils;

import android.support.annotation.NonNull;

import com.gogh.afternoontea.entity.FloatMenu;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/10/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/10/2017 do fisrt create. </li>
 */
public class MathUtil {

    @NonNull
    public static FloatMenu.Pointer computePoint(int distance, int position, int num) {
        float myroate = (float) (Math.PI / 2 / num);
        float x = (float) (distance * Math.cos(myroate * position));
        float y = (float) (distance * Math.sin(myroate * position));
        Logger.d("MathUtil", String.format("Pointer [ x = %f, y = %f ].", x, y));
        return new FloatMenu.Pointer(Math.abs(x), Math.abs(y));
    }

}
