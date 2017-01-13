package com.gogh.afternoontea.utils;

import android.content.Context;
import android.os.Environment;

import com.gogh.afternoontea.entity.gank.GankEntity;
import com.gogh.afternoontea.log.Logger;

import java.io.File;
import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/5/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/5/2017 do fisrt create. </li>
 */
public class DataUtil {

    private static final String TAG = "DataUtil";

    public static List<GankEntity.ResultsBean> removeDuplicateData(
            List<GankEntity.ResultsBean> orignalDatas, List<GankEntity.ResultsBean> newDatas) {
        if (orignalDatas == null) {
            return newDatas;
        }

        if (newDatas == null) {
            return orignalDatas;
        }

        String orignalId = orignalDatas.get(0).get_id();

        for (GankEntity.ResultsBean resultsBean : newDatas) {
            if (resultsBean.get_id().equals(orignalId)) {
                return orignalDatas;
            }
        }

        orignalDatas.addAll(0, newDatas);

        return orignalDatas;
    }

    public static List<GankEntity.ResultsBean> getAvalibleData(
            List<GankEntity.ResultsBean> orignalDatas, List<GankEntity.ResultsBean> newDatas) {
        if (newDatas != null) {
            String lastOldId = orignalDatas.get(orignalDatas.size() - 1).get_id();
            int index = -1;

            for (int i = 0; i < newDatas.size(); i++) {
                if (newDatas.get(i).get_id().equals(lastOldId)) {
                    index = i;
                }
            }

            if (index != -1) {// 有重复数据
                Logger.d(TAG, "has Duplicate datas.");
                for (int i = index; i < newDatas.size(); i++) {
                    orignalDatas.add(newDatas.get(i));
                }
            } else {
                Logger.d(TAG, "has no Duplicate datas.");
                orignalDatas.addAll(newDatas);
            }
        }

        return orignalDatas;
    }

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
     * context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 按名字清除本应用数据库 * * @param context * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
     * context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * @param filePath
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * 清除本应用所有的数据 * * @param context * @param filepath
     */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

}
