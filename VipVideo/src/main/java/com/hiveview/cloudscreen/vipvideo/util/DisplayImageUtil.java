/**
 * @Title DisplayImage.java
 * @Package com.hiveview.cloudscreen.video.utils
 * @author haozening
 * @date 2014年9月5日 上午9:31:20
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.HashMap;

/**
 * DisplayImageOptions容器，淡入淡出有默认值，圆角没有默认值，需要手工指定
 *
 * @author haozening
 * @ClassName DisplayImageOptionsContainer
 * @Description
 * @date 2014年9月3日 下午1:19:41
 */
public class DisplayImageUtil extends HashMap<String, DisplayImageOptions> {

    private static final int DURATION = 200;
    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    static class DisplayImageOptionsContainerHolder {
        static final DisplayImageUtil OPTIONS_CONTAINER = new DisplayImageUtil();
    }

    private DisplayImageUtil() {

    }

    public static DisplayImageUtil getInstance() {
        return DisplayImageOptionsContainerHolder.OPTIONS_CONTAINER;
    }

    /**
     * 生成指定的Key
     *
     * @param params
     * @return
     * @Title generateKey
     * @author haozening
     * @Description
     */
    private String generateKey(int... params) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            builder.append(params[i] + "_");
        }
        return builder.toString();
    }

    private int round;
    private int duration;
    private int loadingImage;
    private int errorImage;
    private int emptyUriImage;
    private Drawable loadingDrawable;
    private Drawable errorDrawable;
    private Drawable emptyUriDrawable;
    private boolean cacheInMemory = false;
    private int roundLeftTop;

    private void reset() {
        round = 0;
        loadingImage = 0;
        errorImage = 0;
        emptyUriImage = 0;
        loadingDrawable = null;
        errorDrawable = null;
        emptyUriDrawable = null;
        duration = DURATION;
        roundLeftTop = 0;
    }

    public DisplayImageUtil setRound(int round) {
        this.round = round;
        return this;
    }

    public DisplayImageUtil setRoundOnlyLeftTop(int roundLeftTop) {
        this.roundLeftTop = roundLeftTop;
        return this;
    }

    public DisplayImageUtil setLoadingImage(int loadingImage) {
        this.loadingImage = loadingImage;
        return this;
    }

    public DisplayImageUtil setLoadingDrawable(Drawable loadingDrawable) {
        this.loadingDrawable = loadingDrawable;
        return this;
    }

    public DisplayImageUtil setErrorImage(int errorImage) {
        this.errorImage = errorImage;
        return this;
    }

    public DisplayImageUtil setErrorDrawable(Drawable errorDrawable) {
        this.errorDrawable = errorDrawable;
        return this;
    }

    public DisplayImageUtil setEmptyUriImage(int emptyUriImage) {
        this.emptyUriImage = emptyUriImage;
        return this;
    }

    public DisplayImageUtil setEmptyUriDrawable(Drawable emptyUriDrawable) {
        this.emptyUriDrawable = emptyUriDrawable;
        return this;
    }

    public DisplayImageUtil setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public DisplayImageUtil setCacheInMemory(boolean cacheInMemory) {
        this.cacheInMemory = cacheInMemory;
        return this;
    }


    public static class Builder {
        private DisplayImageUtil container = DisplayImageOptionsContainerHolder.OPTIONS_CONTAINER;

        public Builder() {
            container.reset();
        }

        public Builder setRound(int round) {
            container.round = round;
            return this;
        }

        public Builder setRoundOnlyLeftTop(int roundLeftTop) {
            container.roundLeftTop = roundLeftTop;
            return this;
        }

        public Builder setLoadingImage(int loadingImage) {
            container.loadingImage = loadingImage;
            return this;
        }

        public Builder setErrorImage(int errorImage) {
            container.errorImage = errorImage;
            return this;
        }

        public Builder setEmptyUriImage(int emptyUriImage) {
            container.emptyUriImage = emptyUriImage;
            return this;
        }

        public Builder setDuration(int duration) {
            container.duration = duration;
            return this;
        }

        public DisplayImageUtil build() {
            return container;
        }

    }

    /**
     * 生成DisplayImageOptions对象
     *
     * @return
     * @Title obtain
     * @author haozening
     * @Description
     */
    private DisplayImageOptions obtain() {
        int cacheInMemoryFlag = 0;
        if (cacheInMemory) {
            cacheInMemoryFlag = 1;
        }
        String key = generateKey(roundLeftTop, round, loadingImage, loadingDrawable == null ? 0 : loadingDrawable.hashCode(), errorImage, errorDrawable == null ? 0 : errorDrawable.hashCode(), emptyUriImage, emptyUriDrawable == null ? 0 : emptyUriDrawable.hashCode(), duration, cacheInMemoryFlag);
        if (containsKey(key)) {
            return get(key);
        } else {
            DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
            if (round != 0) {
                builder.displayer(new RoundedFadeInBitmapDisplayer(duration, round));
            } else if (roundLeftTop != 0) {
                builder.displayer(new RoundedFadeInBitmapDisplayer(duration, roundLeftTop, true));
            } else {
                builder.displayer(new FadeInBitmapDisplayer(duration));
            }
            if (loadingImage != 0) {
                builder.showImageOnLoading(loadingImage);
            }
            if (null != loadingDrawable) {
                builder.showImageOnLoading(loadingDrawable);
            }
            if (emptyUriImage != 0) {
                builder.showImageForEmptyUri(emptyUriImage);
            }
            if (null != emptyUriDrawable) {
                builder.showImageForEmptyUri(emptyUriDrawable);
            }
            if (errorImage != 0) {
                builder.showImageOnFail(errorImage);
            }
            if (null != errorDrawable) {
                builder.showImageOnFail(errorDrawable);
            }
            builder.cacheInMemory(cacheInMemory);
            DisplayImageOptions options = builder.cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY)
                    .resetViewBeforeLoading(false).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
            put(key, options);
            return options;
        }
    }

    /**
     * 显示图片，带有监听器
     *
     * @param url
     * @param view
     * @param listener
     * @Title displayImage
     * @author haozening
     * @Description
     */
    public void displayImage(String url, ImageView view, ImageLoadingListener listener) {
        String newUrl = ImageUrlReplaceUtils.getInstance().replace(url);
        ImageLoader loader = CloudScreenApplication.getInstance().imageLoader;
        loader.displayImage(newUrl, view, obtain(), listener);
        reset();
    }

    /**
     * 显示图片
     *
     * @param url
     * @param view
     * @Title displayImage
     * @author haozening
     * @Description
     */
    public void displayImage(String url, ImageView view) {
        String newUrl = ImageUrlReplaceUtils.getInstance().replace(url);
        ImageLoader loader = CloudScreenApplication.getInstance().imageLoader;
        loader.displayImage(newUrl, view, obtain());
        reset();
    }

    public void pause() {
        CloudScreenApplication.getInstance().imageLoader.pause();
    }

    public void resume() {
        CloudScreenApplication.getInstance().imageLoader.resume();
    }

    /**
     * 格式化图片URL，得到能够获取不同尺寸的图片的URL
     *
     * @param url
     * @param isPortrait
     * @return
     * @Title createImgUrl
     * @author haozening
     * @Description
     */
    public static String createImgUrl(String url, boolean isPortrait) {
        if (url == null || url.equals("") || url.equals("null")) {
            return "";
        }
        int position = url.lastIndexOf(".");
        if (position == -1)
            return "";
        String subString = url.substring(0, position);
        String subString1 = url.substring(position, url.length());
        StringBuilder newUrl;
        if (isPortrait) {
            newUrl = new StringBuilder(subString).append("_260_360").append(subString1);
        } else {
            newUrl = new StringBuilder(subString).append("_320_180").append(subString1);
        }
        return newUrl.toString();
    }

    public Bitmap decodeResource(Resources res, int drawable) {
        Options options = new Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Config.RGB_565;
        options.inDither = true;
        Bitmap bitmap = BitmapFactory.decodeResource(res, drawable, options);
        return bitmap;
    }
}
