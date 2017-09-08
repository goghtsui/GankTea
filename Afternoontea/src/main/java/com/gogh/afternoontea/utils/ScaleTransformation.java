package com.gogh.afternoontea.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.Target;
import com.gogh.afternoontea.main.ATApplication;

import java.security.MessageDigest;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/20/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/20/2017 do fisrt create. </li>
 */
public class ScaleTransformation extends BitmapTransformation {

    private static final String TAG = "ScaleTransformation";

    private static final String ID = "com.bumptech.glide.transformations.FillSpace";

    public ScaleTransformation(Context context) {
        super(context);
    }

    /**
     * Transforms the given {@link Bitmap} based on the given dimensions and returns the transformed
     * result.
     * <p>
     * <p>
     * The provided Bitmap, toTransform, should not be recycled or returned to the pool. Glide will automatically
     * recycle and/or reuse toTransform if the transformation returns a different Bitmap. Similarly implementations
     * should never recycle or return Bitmaps that are returned as the result of this method. Recycling or returning
     * the provided and/or the returned Bitmap to the pool will lead to a variety of runtime exceptions and drawing
     * errors. See #408 for an example. If the implementation obtains and discards intermediate Bitmaps, they may
     * safely be returned to the BitmapPool and/or recycled.
     * </p>
     * <p>
     * <p>
     * outWidth and outHeight will never be {@link Target#SIZE_ORIGINAL}, this
     * class converts them to be the size of the Bitmap we're going to transform before calling this method.
     * </p>
     *
     * @param pool        A {@link BitmapPool} that can be used to obtain and
     *                    return intermediate {@link Bitmap}s used in this transformation. For every
     *                    {@link Bitmap} obtained from the pool during this transformation, a
     *                    {@link Bitmap} must also be returned.
     * @param toTransform The {@link Bitmap} to transform.
     * @param outWidth    The ideal width of the transformed bitmap (the transformed width does not need to match exactly).
     * @param outHeight   The ideal height of the transformed bitmap (the transformed heightdoes not need to match
     */
    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        float targetWidth = ATApplication.getWidth() / 2.0f - (26.5f * ATApplication.getDensity());
        float scale = targetWidth / toTransform.getWidth();
        float targetHeight = scale * toTransform.getHeight();
        Bitmap newBitmap = Bitmap.createScaledBitmap(toTransform, (int) targetWidth, (int) targetHeight, true);
//        toTransform.recycle();
        return newBitmap;
    }

    /**
     * Adds all uniquely identifying information to the given digest.
     * <p>
     * <p> Note - Using {@link MessageDigest#reset()} inside of this method will result in undefined behavior. </p>
     *
     * @param messageDigest
     */
    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
       /* try {
            messageDigest.update(ID.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
    }
}
