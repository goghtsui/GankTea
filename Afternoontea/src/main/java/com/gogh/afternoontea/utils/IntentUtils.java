package com.gogh.afternoontea.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.gogh.afternoontea.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/9/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/9/2017 do fisrt create. </li>
 */
public class IntentUtils {

    /**
     * 使用系统发送分享数据
     *
     * @param context 上下文
     * @param text    要分享的文本
     */
    public static void share(@NonNull Context context, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.action_share_to));
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, context.getResources().getString(R.string.action_share_to)));
    }

    public static void contibuteByEmail(Context context, String emailTitle, String emailContent){
        Intent intent=new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(context.getResources().getString(R.string.action_mailto)));
        intent.putExtra(Intent.EXTRA_SUBJECT, emailTitle);
        intent.putExtra(Intent.EXTRA_TEXT, emailContent);
        context.startActivity(intent);
    }

    public static void openWithBrowser(String url, @NonNull Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.toast_open_fail), Toast.LENGTH_SHORT).show();
        }
    }

    public void sendMultiple(@NonNull Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, getUriListForImages(context));
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "你好 ");
        intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "请选择"));
    }

    private ArrayList<Uri> getUriListForImages(@NonNull Context context) {
        ArrayList<Uri> myList = new ArrayList<>();
        String imageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/100ANDRO/";
        File imageDirectory = new File(imageDirectoryPath);
        String[] fileList = imageDirectory.list();
        if (fileList.length != 0) {
            for (int i = 0; i < 5; i++) {
                try {
                    ContentValues values = new ContentValues(7);
                    values.put(MediaStore.Images.Media.TITLE, fileList[i]);
                    values.put(MediaStore.Images.Media.DISPLAY_NAME, fileList[i]);
                    values.put(MediaStore.Images.Media.DATE_TAKEN, new Date().getTime());
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                    values.put(MediaStore.Images.ImageColumns.BUCKET_ID, imageDirectoryPath.hashCode());
                    values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, fileList[i]);
                    values.put("_data", imageDirectoryPath + fileList[i]);
                    ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
                    Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    myList.add(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return myList;

    }
}
