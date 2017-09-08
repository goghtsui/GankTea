/**
 * @Title RoundedFadeInBitmapDisplayer.java
 * @Package com.hiveview.cloudscreen.video.view
 * @author haozening
 * @date 2014年8月31日 下午11:22:15
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.util;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * @author haozening
 * @ClassName RoundedFadeInBitmapDisplayer
 * @Description
 * @date 2014年8月31日 下午11:22:15
 */
public class RoundedFadeInBitmapDisplayer implements BitmapDisplayer {

    private final int durationMillis;

    private final boolean animateFromNetwork;
    private final boolean animateFromDisk;
    private final boolean animateFromMemory;

    protected final int cornerRadius;
    protected final int margin;

    private boolean onlyLeftTop = false;

    public RoundedFadeInBitmapDisplayer(int durationMillis, int cornerRadiusPixels) {
        this(durationMillis, true, true, true, cornerRadiusPixels, 0);
    }

    public RoundedFadeInBitmapDisplayer(int durationMillis, int cornerRadiusPixels, boolean onlyLeftTop) {
        this(durationMillis, true, true, true, cornerRadiusPixels, 0);
        this.onlyLeftTop = onlyLeftTop;
    }

    public RoundedFadeInBitmapDisplayer(int durationMillis, int cornerRadiusPixels, int marginPixels) {
        this(durationMillis, true, true, true, cornerRadiusPixels, marginPixels);
    }

    /**
     * @param durationMillis     Duration of "fade-in" animation (in milliseconds)
     * @param animateFromNetwork Whether animation should be played if image is loaded from
     *                           network
     * @param animateFromDisk    Whether animation should be played if image is loaded from
     *                           disk cache
     * @param animateFromMemory  Whether animation should be played if image is loaded from
     *                           memory cache
     * @param cornerRadiusPixels
     * @param marginPixels
     */
    public RoundedFadeInBitmapDisplayer(int durationMillis, boolean animateFromNetwork, boolean animateFromDisk, boolean animateFromMemory,
                                        int cornerRadiusPixels, int marginPixels) {
        this.durationMillis = durationMillis;
        this.animateFromNetwork = animateFromNetwork;
        this.animateFromDisk = animateFromDisk;
        this.animateFromMemory = animateFromMemory;
        this.cornerRadius = cornerRadiusPixels;
        this.margin = marginPixels;
    }

    /**
     * @param durationMillis     Duration of "fade-in" animation (in milliseconds)
     * @param animateFromNetwork Whether animation should be played if image is loaded from
     *                           network
     * @param animateFromDisk    Whether animation should be played if image is loaded from
     *                           disk cache
     * @param animateFromMemory  Whether animation should be played if image is loaded from
     *                           memory cache
     * @param cornerRadiusPixels
     */
    public RoundedFadeInBitmapDisplayer(int durationMillis, boolean animateFromNetwork, boolean animateFromDisk, boolean animateFromMemory,
                                        int cornerRadiusPixels) {
        this(durationMillis, animateFromNetwork, animateFromDisk, animateFromMemory, cornerRadiusPixels, 0);
    }

    /**
     * Animates {@link android.widget.ImageView} with "fade-in" effect
     *
     * @param imageView      {@link android.widget.ImageView} which display image in
     * @param durationMillis The length of the animation in milliseconds
     */
    public static void animate(View imageView, int durationMillis) {
        if (imageView != null) {
            AlphaAnimation fadeImage = new AlphaAnimation(0, 1);
            fadeImage.setDuration(durationMillis);
            fadeImage.setInterpolator(new DecelerateInterpolator());
            imageView.startAnimation(fadeImage);
        }
    }

    @Override
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        if (!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
        }
        imageAware.setImageDrawable(new RoundedDrawable(bitmap, cornerRadius, margin, onlyLeftTop));

        if ((animateFromNetwork && loadedFrom == LoadedFrom.NETWORK) || (animateFromDisk && loadedFrom == LoadedFrom.DISC_CACHE)
                || (animateFromMemory && loadedFrom == LoadedFrom.MEMORY_CACHE)) {
            animate(imageAware.getWrappedView(), durationMillis);
        }
    }

    public void display(ImageView iv, Bitmap bitmap, boolean onlyLeftTop) {
        iv.setImageDrawable(new RoundedDrawable(bitmap, cornerRadius, margin, onlyLeftTop));
    }

    public static class RoundedDrawable extends Drawable {

        protected final float cornerRadius;
        protected final int margin;

        protected final RectF mRect = new RectF(), mBitmapRect;
        protected final BitmapShader bitmapShader;
        protected final Paint paint;
        protected final Path path = new Path();
        protected boolean onlyLeftTop;

        public RoundedDrawable(Bitmap bitmap, int cornerRadius, int margin, boolean onlyLeftTop) {
            this.cornerRadius = cornerRadius;
            this.margin = margin;
            this.onlyLeftTop = onlyLeftTop;
            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mBitmapRect = new RectF(margin, margin, bitmap.getWidth() - margin, bitmap.getHeight() - margin);

            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(bitmapShader);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            mRect.set(margin, margin, bounds.width() - margin, bounds.height() - margin);

            if (onlyLeftTop) {
                path.moveTo(margin, margin + cornerRadius);
                path.arcTo(new RectF(0, 0, 2 * cornerRadius, 2 * cornerRadius), 180, 90);
                path.lineTo(bounds.width() - margin, margin);
                path.lineTo(bounds.width() - margin, bounds.height() - margin);
                path.lineTo(margin, bounds.height() - margin);
                path.lineTo(margin, margin + cornerRadius);
            }

            // Resize the original bitmap to fit the new bound
            Matrix shaderMatrix = new Matrix();
            shaderMatrix.setRectToRect(mBitmapRect, mRect, Matrix.ScaleToFit.FILL);
            bitmapShader.setLocalMatrix(shaderMatrix);

        }

        @Override
        public void draw(Canvas canvas) {
            if (onlyLeftTop) {
                canvas.drawPath(path, paint);
            } else {
                canvas.drawRoundRect(mRect, cornerRadius, cornerRadius, paint);
            }
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            paint.setColorFilter(cf);
        }
    }

}
