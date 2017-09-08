package com.hiveview.cloudscreen.vipvideo.util;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.common.ContentShowType;
import com.hiveview.cloudscreen.vipvideo.service.entity.VideoChannelEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 访问Launcher数据库中的视频频道数据
 *
 * @ClassName: VideoChannelUtils
 * @Description: TODO
 * @author: 陈丽晓
 * @date 2014-9-12 下午3:14:20
 */
@SuppressLint("UseSparseArrays")
public class VideoChannelUtils {

    private static final String TAG = "VideoChannelUtils";

    private static final Uri URI_VIDEO_CHANNEL_TV = Uri.parse("content://HiveViewCloudLauncherAuthorities/TABLE_CHANNEL");
    private static final Uri URI_VIDEO_CHANNEL_BOX = Uri.parse("content://HiveTVAuthorities/TABLE_CHANNEL");

    private static final String COLUMN_ID = "channel_id";
    private static final String COLUMN_CHANNEL_NAME = "channel_name";
    private static final String COLUMN_CHANNEL_TYPE = "channel_type";
    private static final String COLUMN_SHOW_CATEGORY = "show_category";
    private static final String COLUMN_ISMULTICHIP = "is_multi_chip";
    private static final String COLUMN_ISHASDETAIL = "is_has_detail";
    private static final String COLUMN_ISHORIZONTAL = "is_horizontal";
    private static final String COLUMN_ISSPECIFIC = "is_specific";
    private static final String COLUMN_PARENTID = "hotword_cid";
    private static final String COLUMN_PARENTCTYPE = "hotword_ctype";
    private static final String COLUMN_PARENTAPKNAME = "parent_apk_name";

    /**
     * 刷新选有有效频道(1--有效频道，-1--无效频道，0---虚拟频道）
     */
    public static List<VideoChannelEntity> getVideoChannelsFromLauncher(Context context) {

        if (null == context) {
            Log.e(TAG, "context is null");
            return null;
        }

        List<VideoChannelEntity> list = new ArrayList<VideoChannelEntity>();
        ContentResolver resolver = context.getContentResolver();

        try {
            // 根据Launcher共享数据URI的查询共享数据
            Cursor cursor = resolver.query(URI_VIDEO_CHANNEL_TV, null, null, null, null);
            // add by haozening
            if (null == cursor) {
                return list;
            }
            // end by haozening
            while (cursor.moveToNext()) {
                VideoChannelEntity entity = new VideoChannelEntity();
                entity.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                entity.setChannelName(cursor.getString(cursor.getColumnIndex(COLUMN_CHANNEL_NAME)));
                entity.setChannelType(cursor.getInt(cursor.getColumnIndex(COLUMN_CHANNEL_TYPE)));
                entity.setShowCategory(cursor.getInt(cursor.getColumnIndex(COLUMN_SHOW_CATEGORY)));
                entity.setIsMultichip(cursor.getInt(cursor.getColumnIndex(COLUMN_ISMULTICHIP)));
                entity.setIsHasDetail(cursor.getInt(cursor.getColumnIndex(COLUMN_ISHASDETAIL)));
                entity.setIsHorizontal(cursor.getInt(cursor.getColumnIndex(COLUMN_ISHORIZONTAL)));
                entity.setIsSpecific(cursor.getInt(cursor.getColumnIndex(COLUMN_ISSPECIFIC)));
                entity.setParentCid(cursor.getInt(cursor.getColumnIndex(COLUMN_PARENTID)));
                entity.setParentCtype(cursor.getInt(cursor.getColumnIndex(COLUMN_PARENTCTYPE)));
                entity.setParentApkName(cursor.getString(cursor.getColumnIndex(COLUMN_PARENTAPKNAME)));
                list.add(entity);
            }
            cursor.close();
            return list;
        } catch (Exception e) {
            Log.e(TAG, "e=" + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回Launcher中的视频频道的数据，构建视频频道类型的ID和Show_Type的对应关系的HashMap，建议在Application中调用
     *
     * @param context
     * @return
     * @Title: VideoChannelUtils
     * @author:陈丽晓
     * @Description: TODO
     */
    public static HashMap<Integer, Integer> getShowTypeAndVideoTypeMap(Context context) {
        if (null == context) {
            Log.e(TAG, "context is null");
            return null;
        }

        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

        ContentResolver resolver = context.getContentResolver();

        try {
            // 根据Launcher共享数据URI的查询共享数据
            Cursor cursor = null;
            if (null != resolver.getType(URI_VIDEO_CHANNEL_TV)) {
                cursor = resolver.query(URI_VIDEO_CHANNEL_TV, null, null, null, null);
            } else if (null != resolver.getType(URI_VIDEO_CHANNEL_BOX)) {
                cursor = resolver.query(URI_VIDEO_CHANNEL_BOX, null, null, null, null);
            }
            if (null != cursor) {
                while (cursor.moveToNext()) {
                    // 建立对应表关系
                    VideoChannelEntity entity = new VideoChannelEntity();
                    entity.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                    entity.setChannelName(cursor.getString(cursor.getColumnIndex(COLUMN_CHANNEL_NAME)));
                    entity.setChannelType(cursor.getInt(cursor.getColumnIndex(COLUMN_CHANNEL_TYPE)));
                    entity.setShowCategory(cursor.getInt(cursor.getColumnIndex(COLUMN_SHOW_CATEGORY)));
                    entity.setIsMultichip(cursor.getInt(cursor.getColumnIndex(COLUMN_ISMULTICHIP)));
                    entity.setIsHasDetail(cursor.getInt(cursor.getColumnIndex(COLUMN_ISHASDETAIL)));
                    entity.setIsHorizontal(cursor.getInt(cursor.getColumnIndex(COLUMN_ISHORIZONTAL)));
                    entity.setIsSpecific(cursor.getInt(cursor.getColumnIndex(COLUMN_ISSPECIFIC)));
                    entity.setParentCid(cursor.getInt(cursor.getColumnIndex(COLUMN_PARENTID)));
                    entity.setParentCtype(cursor.getInt(cursor.getColumnIndex(COLUMN_PARENTCTYPE)));
                    entity.setParentApkName(cursor.getString(cursor.getColumnIndex(COLUMN_PARENTAPKNAME)));
                    map.put(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)), getShowType(entity));
                }
                cursor.close();
            }
            return map;
        } catch (Exception e) {
            Log.e(TAG, "e=" + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    private static int getShowType(VideoChannelEntity e) {
        if (e.getChannelType() == 10) {
            if (e.getIsSpecific() == 1) {
                return ContentShowType.TYPE_SUBJECT_VIDEO_DETAIL;
            } else if (e.getIsSpecific() == 3) {
                return ContentShowType.TYPE_VARIETY_VIDEO_DETAIL;
            } else if (e.getIsHasDetail() == 0) {
                return ContentShowType.TYPE_SINGLE_VIDEO_NO_DETAIL;
            } else {
                if (e.getIsMultichip() == 1) {
                    return ContentShowType.TYPE_SINGLE_VIDEO_DETAIL;
                } else {
                    return ContentShowType.TYPE_MULTIPLE_VIDEO_DETAIL;
                }
            }
        } else if (e.getChannelType() == 9) {
            if (e.getIsSpecific() == 1) {
                return ContentShowType.TYPE_SUBJECT_VIDEO_DETAIL;
            } else {
                return ContentShowType.TYPE_SINGLE_VIDEO_DETAIL;
            }
        } else {
            return ContentShowType.TYPE_SINGLE_VIDEO_NO_DETAIL;
        }
    }

}
