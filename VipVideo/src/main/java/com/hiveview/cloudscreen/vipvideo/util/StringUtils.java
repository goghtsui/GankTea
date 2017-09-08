package com.hiveview.cloudscreen.vipvideo.util;


import android.content.Context;
import android.text.TextUtils;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtils {

    public static boolean currentTimeInStartTimeAndEndTime(String starTime, String endTime) {
        return currentTimeInStartTimeAndEndTime(starTime, new Date().getTime(), endTime);
    }

    /**
     * 判断当前时间是否在开始时间和结束时间范围内
     *
     * @param starTime
     * @param endTime
     * @return
     */
    public static boolean currentTimeInStartTimeAndEndTime(String starTime, long currentTime, String endTime) {
        boolean flag = false;
        SimpleDateFormat localTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date sdate = localTime.parse(starTime);
            Date edate = localTime.parse(endTime);
            Date date = new Date(currentTime);
            if (date.after(sdate) && date.before(edate)) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public static boolean currentTimeAfterOrderTime(String orderTime) {
        return currentTimeAfterOrderTime(new Date().getTime(), orderTime);
    }

    /**
     * 当前时间是否在指定时间之后
     *
     * @param orderTime
     * @return
     */
    public static boolean currentTimeAfterOrderTime(long crrentTime, String orderTime) {
        boolean flag = false;
        SimpleDateFormat localTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date sdate = localTime.parse(orderTime);
            Date date = new Date(crrentTime);
            if (date.after(sdate)) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public static String parseString(String content) {
        if (content == null)
            return null;
        if (content.length() != 2)
            return content;
        else {
            return content.substring(0, 1) + "\u3000" + content.substring(1);
        }
    }

    public static String parseCollectDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String newTime = format.format(time);
        return CloudScreenApplication.getInstance().getApplicationContext().getString(R.string.collect_time) + " " + newTime;
    }

    public static String parseDateAll(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newTime = format.format(time);
        return newTime;
    }

    /**
     * 把传递时间转换为对应毫秒
     *
     * @return
     */
    public static long getTimeMillis(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = 0;
        try {
            time = simpleDateFormat.parse(str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 获取2个时间的时间差
     * str1：开始时间
     * str2：结束时间
     *
     * @return
     */
    public static long getTimeInterval(String str1, String str2) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long duration = 0;
        try {
            Date data1 = sf.parse(str1);
            Date data2 = sf.parse(str2);
            duration = data2.getTime() - data1.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            duration = 0;
        }
        return duration;

    }

    /**
     * 判断是否为中文环境
     */
    public static boolean isZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/12/19
     * @Description 将系统时间语言转化为人类语言
     */
    public static String formatDate(String date) {
        String newDate = date;
        try {
            Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            newDate = new SimpleDateFormat("yyyy年MM月dd日HH:mm").format(time).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    /**
     * 使double为整数时不带小数点
     *
     * @param d
     * @return
     */
    public static String doubleTrans(double d) {
        if (Math.round(d) - d == 0) {
            return String.valueOf((long) d);
        }
        return String.valueOf(d);
    }

    /**
     * 把分钟转换为多少小时分钟数
     */
    public static String getFormatHourAndMin(String miniutes) {
        String str;
        if (TextUtils.isEmpty(miniutes)) {
            str = null;
        } else {
            try {
                int mins = Math.abs(Integer.parseInt(miniutes));
                if (mins == 0) {
                    str = null;
                } else {
                    int hours = mins / 60;
                    int min = mins % 60;
                    str = (min == 0 ? hours + "小时" : hours + "小时" + min + "分钟");
                }
            } catch (Exception e) {
                str = null;
            }
        }
        return str;

    }

}
