/**
 * @Title QIYIUtils.java
 * @Package com.hiveview.cloudscreen.video.utils
 * @author haozening
 * @date 2014年11月25日 上午11:27:01
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.hiveview.cloudscreen.vipvideo.service.entity.VideoRecordEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author haozening
 * @ClassName QIYIUtils
 * @Description
 * @date 2014年11月25日 上午11:27:01
 */
public class QIYIUtils {

    public static final Uri RECORD_QIYI = Uri.parse("content://HiveViewCloudPlayerAuthorities/RecordDaily");
    public static final Uri RECORD_CONTROLLER = Uri.parse("content://HiveViewCloudPlayerAuthorities/RecordController");

    public static List<VideoRecordEntity> getHistoryList(Context context, String startTime, String endTime) {
        List<VideoRecordEntity> qiyiList = new ArrayList<VideoRecordEntity>();
        if (null == context) {
            return qiyiList;
        }
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(RECORD_QIYI, null, "recordTime between ? and ?", new String[]{startTime, endTime}, "recordTime");
        if (null != cursor) {
            while (cursor.moveToNext()) {
                VideoRecordEntity entity = new VideoRecordEntity();
                entity.setMovieName(cursor.getString(cursor.getColumnIndex("movieName")));
                entity.setPicUrl(cursor.getString(cursor.getColumnIndex("picUrl")));
                entity.setRecordTime(cursor.getLong(cursor.getColumnIndex("recordTime")));
                entity.setVideoset_type(cursor.getInt(cursor.getColumnIndex("videoset_type")));
                entity.setAlbumId(cursor.getString(cursor.getColumnIndex("albumId")));
                entity.setVrsAlbumId(cursor.getString(cursor.getColumnIndex("vrsAlbumId")));
                entity.setFormatTime(cursor.getLong(cursor.getColumnIndex("recordTime")));
                entity.setVideoset_id(cursor.getInt(cursor.getColumnIndex("videoset_id")));
                try {
                    entity.setCurrentEpisode(cursor.getString(cursor.getColumnIndex("currentEpisode")));
                } catch (Exception | Error e) {
                    e.printStackTrace();
                }
                qiyiList.add(entity);
            }
            cursor.close();
        }
        return qiyiList;
    }

    public static boolean delete(Context context, VideoRecordEntity entity) {
        Bundle extras = new Bundle();
        extras.putInt("programsetId", entity.getVideoset_id());
        extras.putInt("videoId", entity.getVideoset_id());
        // TODO 删除数据库内指定的历史记录
        ContentResolver resolver = context.getContentResolver();
        resolver.call(RECORD_QIYI, "deleteRelations", null, extras);
        return true;
    }

}
