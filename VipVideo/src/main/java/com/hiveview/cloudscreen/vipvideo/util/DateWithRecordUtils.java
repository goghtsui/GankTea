package com.hiveview.cloudscreen.vipvideo.util;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.service.entity.AppStoreEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.RecordEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.VideoRecordEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateWithRecordUtils {


    private static final String TAG = "DateWithRecordUtils";
    public static final Uri URI_RECORD_DAILY = Uri.parse("content://HiveViewCloudPlayerAuthorities/RecordDaily");
    public static final Uri URI_RECORD_ALBUM = Uri.parse("content://HiveViewCloudPlayerAuthorities/RecordAlbum");
    public static final Uri URI_RECORD_EPISODE = Uri.parse("content://HiveViewCloudPlayerAuthorities/RecordEpisode");

    public static String getCurrentDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }

    public static List<String> getVideoTimeSlice(List<VideoRecordEntity> entitys) {
        List<String> timeSlice = new ArrayList<String>();
        for (int i = 0; i < entitys.size(); i++) {
            if (!timeSlice.contains(entitys.get(i).getFormatTime())) {
                timeSlice.add(entitys.get(i).getFormatTime());
            } else {
                continue;
            }
        }
        return timeSlice;
    }

    public static List<String> getAppTimeSlice(List<AppStoreEntity> entitys) {
        List<String> timeSlice = new ArrayList<String>();
        for (int i = 0; i < entitys.size(); i++) {
            if (!timeSlice.contains(entitys.get(i).getFormatTime())) {
                timeSlice.add(entitys.get(i).getFormatTime());
            } else {
                continue;
            }
        }
        return timeSlice;
    }

    /**
     * @param distance 往前推移的天数，负数往前移，正数往后移
     * @return
     * @Title getDistance
     * @author xieyi
     * @Description 得到偏移天数的日期
     */
    public static String getDistance(int distance) {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, distance);//把日期往后增加一天.整数往后推,负数往前移动
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    /*
     * 得到当天最后的时刻
     */
    public static String getToday() {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static long StringToLong(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        return date.getTime();
    }

    /**
     * @param list1
     * @param list2
     * @return
     * @Title mergeAndSort
     * @author xieyi
     * @Description 合并应用游戏和影片的时间片并且降序排列
     */
    public static List<String> mergeAndSort(List<String> list1, List<String> list2) {
        if (null == list1) {
            return list2;
        }
        if (null == list2) {
            return list1;
        }
        for (int i = 0; i < list2.size(); i++) {
            if (list1.contains(list2.get(i))) {
                continue;
            } else {
                list1.add(list2.get(i));
            }
        }
        Collections.sort(list1, new Comparator<String>() {

            @Override
            public int compare(String lhs, String rhs) {
                return rhs.compareTo(lhs);
            }
        });
        return list1;
    }

    public static List<RecordEntity> getRecordList(List<String> timeSlice, List<VideoRecordEntity> entity1, List<AppStoreEntity> entity2) {
        List<RecordEntity> recordEntitys = new ArrayList<RecordEntity>();
        if (entity1.size() == 0) {
            for (int i = 0; i < timeSlice.size(); i++) {
                RecordEntity entity = new RecordEntity();
                List<AppStoreEntity> appEntity = new ArrayList<AppStoreEntity>();
                for (int j = 0; j < entity2.size(); j++) {
                    if (entity2.get(j).getFormatTime().equals(timeSlice.get(i))) {
                        appEntity.add(entity2.get(j));
                    }
                }
                entity.setAppStores(appEntity);
                entity.setTime(timeSlice.get(i));
                recordEntitys.add(entity);
            }
        } else if (entity2.size() == 0) {
            for (int i = 0; i < timeSlice.size(); i++) {
                RecordEntity entity = new RecordEntity();
                List<VideoRecordEntity> videoEntity = new ArrayList<VideoRecordEntity>();
                for (int j = 0; j < entity1.size(); j++) {
                    Log.d(TAG, "entity1 " + (entity1.get(j).getFormatTime() == null));
                    if (entity1.get(j).getFormatTime().equals(timeSlice.get(i))) {
                        videoEntity.add(entity1.get(j));
                    }
                }
                entity.setMovies(videoEntity);
                entity.setTime(timeSlice.get(i));
                recordEntitys.add(entity);
            }
        } else {
            for (int i = 0; i < timeSlice.size(); i++) {
                RecordEntity entity = new RecordEntity();
                List<VideoRecordEntity> videoEntity = new ArrayList<VideoRecordEntity>();
                List<AppStoreEntity> appEntity = new ArrayList<AppStoreEntity>();

                for (int j = 0; j < entity1.size(); j++) {
                    if (entity1.get(j).getFormatTime().equals(timeSlice.get(i))) {
                        videoEntity.add(entity1.get(j));
                    }
                }
                entity.setMovies(videoEntity);

                for (int j = 0; j < entity2.size(); j++) {
                    if (entity2.get(j).getFormatTime().equals(timeSlice.get(i))) {
                        appEntity.add(entity2.get(j));
                    }
                }
                entity.setAppStores(appEntity);
                entity.setTime(timeSlice.get(i));
                recordEntitys.add(entity);
            }
        }
        return recordEntitys;
    }
//	private static List<QYTVAlbum> qiyiList = new ArrayList<QYTVAlbum>();
//	public static void getQiyiRecord(boolean init){
//		if(init){
//			QiyiTVApi.getInstance().getPlayRecords(new IApiResultHandler() {
//				@SuppressWarnings({ "unchecked", "unused" })
//				@Override
//				public void onSuccess(List<?> arg0) {
//					qiyiList = (List<QYTVAlbum>) arg0;
//				}
//
//				@Override
//				public void onFailure(int arg0) {
//					System.out.println("onFailure : this errorCode = " + arg0);
//				}
//			});
//		}
//	}

    public static void deleteAllQiYiRecord(Context context) {
        ContentResolver resolver = context.getContentResolver();
        try {
            resolver.delete(URI_RECORD_ALBUM, null, null);
            resolver.delete(URI_RECORD_DAILY, null, null);
            resolver.delete(URI_RECORD_EPISODE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 开启历史界面数据检索
     */
    public static List<RecordEntity> openRecordChannel(Context context, boolean init) {
        long endTime = StringToLong(getToday()); //得到当天最后一秒的时刻
        long startTime = StringToLong(getDistance(-29)); //得到30天前的开始一秒的时刻
//		getQiyiRecord(init); //获得爱奇艺的历史记录（全部）
        List<VideoRecordEntity> videoEntity = new ArrayList<>();
        List<AppStoreEntity> appEntity = AppStoreUtils.getAppHistoryList(context, startTime + "", endTime + "");//获得时间分段的应用市场的历史记录
        List<VideoRecordEntity> qiyiVideo = QIYIUtils.getHistoryList(context, startTime + "", endTime + "");
        if (videoEntity.size() == 0 && appEntity.size() == 0 && qiyiVideo.size() == 0) {
            return null;
        }
//		//将爱奇艺的记录转换成统一影片实体
//		if(qiyiList.size() > 0){
//			for(int i=0;i<qiyiList.size();i++){
//				VideoRecordEntity entity = new VideoRecordEntity();
//				long recordTime = ((long)qiyiList.get(i).getAddTime())*1000;
//				//满足时间分段
//				if(recordTime >= startTime && recordTime <= endTime){
//					entity.setSource(0);
//					entity.setContentId(qiyiList.get(i).getContentId());
//					entity.setMovieName(qiyiList.get(i).getAlbumName());
//					entity.setPicUrl(qiyiList.get(i).getAlbumPic());
//					entity.setVrsAlbumId(qiyiList.get(i).getVrsAlbumId());
//					entity.setVrsTvId(qiyiList.get(i).getVrsTvId());
//					entity.setRecordTime(recordTime);
//					entity.setFormatTime(recordTime);
//					qiyiVideo.add(entity);
//				}
//			}
//		}
        videoEntity.addAll(qiyiVideo);
        Log.d(TAG, "videoEntity " + videoEntity);
        //将影片整合队列向下排序
        Collections.sort(videoEntity, new Comparator<VideoRecordEntity>() {

            @Override
            public int compare(VideoRecordEntity lhs, VideoRecordEntity rhs) {
                if (rhs.getRecordTime() > lhs.getRecordTime())
                    return 1;
                else if (rhs.getRecordTime() < lhs.getRecordTime())
                    return -1;
                else
                    return 0;
            }
        });
        List<String> videoTimeSlice = null;
        List<String> appTimeSlice = null;
        //获取影片时间片队列
        if (videoEntity.size() > 0) {
            videoTimeSlice = getVideoTimeSlice(videoEntity);
        }
        //获取应用时间片队列
        if (appEntity.size() > 0) {
            appTimeSlice = getAppTimeSlice(appEntity);
        }
        //整个多个时间片队列并向下排序
        List<String> finalTimeSlice = mergeAndSort(videoTimeSlice, appTimeSlice);
        //整合出最终的历史实体
        List<RecordEntity> recordEntity = getRecordList(finalTimeSlice, videoEntity, appEntity);
        return recordEntity;
    }
}
