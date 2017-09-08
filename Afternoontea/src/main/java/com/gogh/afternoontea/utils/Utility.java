package com.gogh.afternoontea.utils;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/22/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/22/2016 do fisrt create. </li>
 */
public class Utility {

    private static final String TAG = "Utility";

    @NonNull
    private static String DATE_FORMAT = "H:mm:ss";

    public static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Date date = new Date(System.currentTimeMillis());
        return format.format(date);
    }

    /**
     * 是否是夜间（时间从下午18：00 到凌成6：00）
     *
     * @author 高晓峰
     * @date 2016/12/22
     * ChangeLog:
     * <li> 高晓峰 on 2016/12/22 </li>
     */
    public static boolean isNight() {
        int hour = Integer.valueOf(getTime().split(":")[0]);
        Logger.d(TAG, "  time.hour : " + hour);
        if (hour >= 18 || hour < 6) {
            Logger.d(TAG, "is night time.");
            return true;
        }
        Logger.d(TAG, "is not night time.");
        return false;
    }

}
