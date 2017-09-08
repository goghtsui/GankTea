package com.hiveview.cloudscreen.vipvideo.common;

import android.app.Activity;
import android.os.Build;
import android.util.DisplayMetrics;

/**
 * @author xieyi
 * @ClassName SizeConstant
 * @Description 用来存放盒子、电视机等分辨率
 * @date 2014-8-28 下午2:37:30
 */
public class SizeConstant {
    //TODO 大麦盒子的宽度和高度，暂时不拿出来

    DisplayMetrics metric = new DisplayMetrics();

    public SizeConstant(Activity activity) {
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);

    }

    public int getBoxWidth() {
        return metric.widthPixels;
    }

    public int getBoxHeight() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1 && metric.heightPixels < 718) {//临时兼容方案，适配4.2一下android盒子
            return 720;//目前只有1.0盒子是android4.2，所以测量不准时返回720的高度
        } else {
            return metric.heightPixels;
        }
    }

}
