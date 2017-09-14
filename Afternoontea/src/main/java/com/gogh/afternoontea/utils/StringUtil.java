package com.gogh.afternoontea.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/6/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/6/2017 do fisrt create. </li>
 */
public class StringUtil {

    public static void copyToClipBoard(@NonNull Context context, String text, String success) {
        ClipData clipData = ClipData.newPlainText("share_copy", text);
        ClipboardManager manager =
                (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(clipData);
        if(!TextUtils.isEmpty(success)){
            Toast.makeText(context.getApplicationContext(), success, Toast.LENGTH_SHORT).show();
        }
    }

    public static int url2groupid(@NonNull String url) {
        return Integer.parseInt(url.split("/")[3]);
    }

    @NonNull
    public static String[] formatUrl(@NonNull String url){
        String[] result = new String[2];
        if(url.endsWith("/")){
            url = url.substring(0, url.lastIndexOf("/"));
        }

        result[0] = url.substring(0, url.lastIndexOf("/") + 1);
        result[1] = url.substring(url.lastIndexOf("/") + 1, url.length());

        return result;
    }

    public static String[] formatInputDate(String inputText) {
        if (TextUtils.isEmpty(inputText)) {
            return null;
        }
        String date = Utility.format(inputText);

        if(isValidDate(date)){
            String month = date.substring(4, 6);
            if (month.startsWith("0")) {
                month.replace("0", "");
            }

            String day = date.substring(6, 8);
            if (day.startsWith("0")) {
                day.replace("0", "");
            }

            return new String[]{date.substring(0, 4), month, day};
        }

        return null;
    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            convertSuccess = false;
        }

        return convertSuccess;
    }

}
