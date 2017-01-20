package com.gogh.afternoontea.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/6/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/6/2017 do fisrt create. </li>
 */
public class StringUtil {

    public static void copyToClipBoard(@NonNull Context context, String text, String success) {
        ClipData clipData = ClipData.newPlainText("meizhi_copy", text);
        ClipboardManager manager =
                (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(clipData);
        Toast.makeText(context.getApplicationContext(), success, Toast.LENGTH_SHORT).show();
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

}
