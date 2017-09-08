package com.hiveview.cloudscreen.vipvideo.util;


import android.content.Context;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;

public class ExpiryTimeCountUtil {

    /**
     * 一天的秒数
     */
    private static final long theDayMs = 86400;
    /**
     * 一个月的秒数
     */
    private static final long theMonthMs = theDayMs * 31;
    /**
     * 一个季度的秒数
     */
    private static final long theQuarterMs = theDayMs * 92;
    /**
     * 一年的秒数
     */
    private static final long theYearMs = theDayMs * 366;

    /**
     * 计算使用时间
     *
     * @param expiryTime
     * @return
     * @Title: PackageListViewpagerView
     * @author:郭松胜
     * @Description: TODO
     */
    public static CharSequence getEffectTime(long expiryTime) {
        Context ctx = CloudScreenApplication.getInstance().getApplicationContext();
        String time = "";
        int perDit = 1;
        if (expiryTime == theYearMs) {
            // 年套餐
            perDit = (int) Math.ceil(expiryTime / (double) theYearMs);
            time = perDit == 1 ? ctx.getString(R.string.every_year) : perDit + ctx.getString(R.string.year);
            return time;
        } else if (expiryTime == theQuarterMs) {
            // 季套餐
            time = ctx.getString(R.string.every_quarter);
            return time;
        } else if (expiryTime == (theQuarterMs * 2)) {
            // 半年套餐
            time = ctx.getString(R.string.half_year);
            return time;
        } else if (expiryTime == theMonthMs) {
            // 月套餐
            perDit = (int) Math.ceil(expiryTime / (double) theMonthMs);
            time = perDit == 1 ? ctx.getString(R.string.every_month) : perDit + ctx.getString(R.string.month);
            return time;
        } else if (expiryTime == theDayMs) {
            // 天套餐
            perDit = (int) Math.ceil(expiryTime / (double) theDayMs);
            time = perDit == 1 ? ctx.getString(R.string.every_day) : perDit + ctx.getString(R.string.day);
            return time;
        } else if (expiryTime <= 0) {
            // 无限套餐
            time = ctx.getString(R.string.unlimiter);
            return time;
        } else {
            perDit = (int) Math.ceil(expiryTime / (double) theDayMs);
            time = perDit == 1 ? ctx.getString(R.string.every_day) : perDit + ctx.getString(
                    R.string.day);
            return time;
        }
    }

}
