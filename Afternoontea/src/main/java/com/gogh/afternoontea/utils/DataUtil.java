package com.gogh.afternoontea.utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gogh.afternoontea.entity.gank.GankEntity;

import java.io.File;
import java.util.Iterator;
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

    @Nullable
    public static List<GankEntity.ResultsBean> removeDuplicateData(@Nullable List<GankEntity.ResultsBean> orignalDatas,
                                                                   @Nullable List<GankEntity.ResultsBean> newDatas) {

        if (newDatas == null || newDatas.size() == 0) {
            return null;
        }

        String firstId = orignalDatas.get(0).get_id();

        Iterator<GankEntity.ResultsBean> iterator = newDatas.iterator();
        while (iterator.hasNext()) {
            if(firstId.equals(iterator.next().get_id())){
                return null;
            }
        }

        return newDatas;
    }

    public static List<GankEntity.ResultsBean> getExtraDataWithNoDuplicated(@NonNull List<GankEntity.ResultsBean> orignalDatas,
                                                            @Nullable List<GankEntity.ResultsBean> newDatas) {
        if (newDatas == null || newDatas.size() == 0) {
            return null;
        }

        String lastOldId = orignalDatas.get(orignalDatas.size() - 1).get_id();

        Iterator<GankEntity.ResultsBean> iterator = newDatas.iterator();
        while (iterator.hasNext()) {
            if(lastOldId.equals(iterator.next().get_id())){
                iterator.remove();
            }
        }

        return newDatas;

    }

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     * @author 高晓峰
     * @date 9/8/2017
     * @param context
     * ChangeLog:
     * <li> 高晓峰 on 9/8/2017 </li>
     */
    public static void cleanInternalCache(@NonNull Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
     * @author 高晓峰
     * @date 9/8/2017
     * @param context
     * ChangeLog:
     * <li> 高晓峰 on 9/8/2017 </li>
     */
    public static void cleanDatabases(@NonNull Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     * @author 高晓峰
     * @date 9/8/2017
     * @param context
     * ChangeLog:
     * <li> 高晓峰 on 9/8/2017 </li>
     */
    public static void cleanSharedPreference(@NonNull Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 按名字清除本应用数据库
     * @author 高晓峰
     * @date 9/8/2017
     * @param context
     * @param dbName
     * ChangeLog:
     * <li> 高晓峰 on 9/8/2017 </li>
     */
    public static void cleanDatabaseByName(@NonNull Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容
     * @author 高晓峰
     * @date 9/8/2017
     * @param context
     * ChangeLog:
     * <li> 高晓峰 on 9/8/2017 </li>
     */
    public static void cleanFiles(@NonNull Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     * @author 高晓峰
     * @date 9/8/2017
     * @param context
     * ChangeLog:
     * <li> 高晓峰 on 9/8/2017 </li>
     */
    public static void cleanExternalCache(@NonNull Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
     * @author 高晓峰
     * @date 9/8/2017
     * @param filePath
     * ChangeLog:
     * <li> 高晓峰 on 9/8/2017 </li>
     */
    public static void cleanCustomCache(@NonNull String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * 清除本应用所有的数据
     * @author 高晓峰
     * @date 9/8/2017
     * @param context
     * @param filepath
     * ChangeLog:
     * <li> 高晓峰 on 9/8/2017 </li>
     */
    public static void cleanApplicationData(@NonNull Context context, @NonNull String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        if (filepath != null && filepath.length > 0) {
            for (String filePath : filepath) {
                cleanCustomCache(filePath);
            }
        }
        Logger.d(TAG, "cleanApplicationData successfullty.");
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     * @author 高晓峰
     * @date 9/8/2017
     * @param directory
     * ChangeLog:
     * <li> 高晓峰 on 9/8/2017 </li>
     */
    private static void deleteFilesByDirectory(@Nullable File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

}
