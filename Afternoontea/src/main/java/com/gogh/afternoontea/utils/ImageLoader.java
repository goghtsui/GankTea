package com.gogh.afternoontea.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 9/7/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 9/7/2017 do fisrt create. </li>
 */

public class ImageLoader {

    /**
     * 福利原图下载
     *
     * @param context
     * @param imageUrl
     * @param imageView
     * @author 高晓峰
     * @date 9/8/2017
     * @ChangeLog: <li> 高晓峰 on 9/8/2017 </li>
     */
    public static void loadFull(Context context, String imageUrl, ImageView imageView) {
        GlideApp.with(context).load(imageUrl)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    /**
     * 详情页图片下载
     *
     * @param context
     * @param imageUrl
     * @param imageView
     * @ChangeLog: <li> 高晓峰 on 9/8/2017 </li>
     * @author 高晓峰
     * @date 9/8/2017
     */
    public static void load(Context context, String imageUrl, ImageView imageView) {
        GlideApp.with(context).load(imageUrl)
                .thumbnail(0.8f)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    /**
     * 用于列表中大图类型的图片下载
     *
     * @param context
     * @param imageUrl
     * @param imageView
     * @param errorRes
     * @ChangeLog: <li> 高晓峰 on 9/8/2017 </li>
     * @author 高晓峰
     * @date 9/8/2017
     */
    public static void loadWithError(Context context, String imageUrl, ImageView imageView, int errorRes) {
        GlideApp.with(context).load(imageUrl)
                .centerCrop()
                .thumbnail(0.6f)
                .error(errorRes)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    /**
     * 用于下载福利模块图片，需要重新设置宽高
     *
     * @param context
     * @param imageUrl
     * @param imageView
     * @author 高晓峰
     * @date 9/8/2017
     * @ChangeLog: <li> 高晓峰 on 9/8/2017 </li>
     */
    public static void loadWithReset(Context context, String imageUrl, ImageView imageView, int placeHolder, int resId) {
        GlideApp.with(context).load(imageUrl)
                .transform(new ScaleTransformation(context))
                .thumbnail(0.5f)
                .skipMemoryCache(true)
                .placeholder(placeHolder)
                .error(resId)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView);
    }

    /**
     * 用于卡片列表的图片下载
     *
     * @param context
     * @param imageUrl
     * @param imageView
     * @param placeHolder
     * @param errorRes
     * @author 高晓峰
     * @date 9/8/2017
     * @ChangeLog: <li> 高晓峰 on 9/8/2017 </li>
     */
    public static void load(Context context, String imageUrl, ImageView imageView, int placeHolder, int errorRes) {
        GlideApp.with(context).load(imageUrl)
                .centerCrop()
                .thumbnail(0.5f)
                .placeholder(placeHolder)
                .error(errorRes)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

}
