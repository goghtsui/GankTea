package com.gogh.afternoontea.utils;

import android.graphics.Bitmap;

import com.gogh.afternoontea.main.ATApplication;
import com.squareup.picasso.Transformation;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/20/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/20/2017 do fisrt create. </li>
 */
public class ScaleTransformation implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
        float targetWidth = ATApplication.getScreenSize()[0] / 2.0f - (26.5f * ATApplication.getScreenSize()[1]);
        float scale = targetWidth / source.getWidth();
        float targetHeight = scale * source.getHeight();
        Bitmap newBitmap = Bitmap.createScaledBitmap(source, (int) targetWidth, (int) targetHeight, false);
        source.recycle();
        return newBitmap;
    }

    @Override
    public String key() {
        return "ScaleTransformation";
    }
}
