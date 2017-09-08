package com.hiveview.cloudscreen.vipvideo.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.hiveview.cloudscreen.vipvideo.service.entity.AppStoreEntity;

import java.util.ArrayList;
import java.util.List;


public class AppStoreUtils {
	public static final Uri URI_APPSTORE_HISTORY = Uri.parse("content://HiveViewCloudAppStoreAuthorities/TABLE_APP_OPENRECOED");

	public static List<AppStoreEntity> getAppHistoryList(Context context, String startTime, String endTime) {
		if (null == context)
			return null;
		List<AppStoreEntity> appStore = new ArrayList<AppStoreEntity>();
		ContentResolver resolver = context.getContentResolver();
		Cursor cursor = resolver
				.query(URI_APPSTORE_HISTORY, null, "open_time between ? and ?", new String[] { startTime, endTime }, "open_time desc");
		// edit by haozening 增加null判断
		if (null != cursor) {
			while (cursor.moveToNext()) {
				AppStoreEntity entity = new AppStoreEntity();
				entity.setAppName(cursor.getString(cursor.getColumnIndex("app_name")));
				entity.setFormatTime(cursor.getLong(cursor.getColumnIndex("open_time")));
				entity.setIconId(cursor.getInt(cursor.getColumnIndex("app_icon_id")));
				entity.setIconUri(cursor.getString(cursor.getColumnIndex("app_icon_url")));
				entity.setLaunchTime(cursor.getLong(cursor.getColumnIndex("open_time")));
				entity.setPackageName(cursor.getString(cursor.getColumnIndex("package_name")));
				appStore.add(entity);
			}
			cursor.close();
		}
		return appStore;
	}

	public static boolean delete(Context context, AppStoreEntity entity) {
		if (null == context)
			return false;
		ContentResolver resolver = context.getContentResolver();
		int columnResult = 0;
		try {
			// 由于会因为apk没有安装而没有相关uri，所以捕获此异常，防止崩溃
			columnResult = resolver.delete(URI_APPSTORE_HISTORY, "package_name = ?", new String[] { entity.getPackageName() });
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (columnResult > 0)
			return true;
		return false;
	}

	public static boolean deleteAll(Context context) {
		if (null == context)
			return false;
		ContentResolver resolver = context.getContentResolver();
		int columnResult = 0;
		try {
			// 由于会因为apk没有安装而没有相关uri，所以捕获此异常，防止崩溃
			columnResult = resolver.delete(URI_APPSTORE_HISTORY, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (columnResult > 0)
			return true;
		return false;
	}
}
