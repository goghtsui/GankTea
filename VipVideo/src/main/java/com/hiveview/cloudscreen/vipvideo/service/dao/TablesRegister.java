package com.hiveview.cloudscreen.vipvideo.service.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;

import java.util.HashMap;

@SuppressLint("UseSparseArrays")
public class TablesRegister {

	private static TablesRegister register = null;

	public final String DROP_TABLE = "DROP TABLE IF EXISTS ";
	public final String AUTHORITIES = "HiveViewCloudVideoAuthorities";
	protected final char CHARACTER_SLASH = '/';

	private HashMap<Integer, BaseDAO> map = new HashMap<Integer, BaseDAO>();

	private TablesRegister(Context context) {
		HomeRecommendDao recommendDao = new HomeRecommendDao(context);
		CollectionDao collectionDao = new CollectionDao(context);
//		BackgroundDao backgroundDao = new BackgroundDao(context);
		map.put(collectionDao.getURICode(), collectionDao);
		map.put(recommendDao.getURICode(), recommendDao);
//		map.put(backgroundDao.getURICode(), backgroundDao);
	}

	public static void init(Context context) {
		if (null == register) {
			register = new TablesRegister(context);
		}
	}

	public static TablesRegister getInstance() {
		return register;
	}

	public HashMap<Integer, BaseDAO> getRegisterMap() {
		return map;
	}

	/**
	 * 根据表名生成该表对应的URI
	 * 
	 * @Title: Tables
	 * @author:陈丽晓
	 * @Description: TODO
	 * @param tableName
	 * @return
	 */
	public Uri getUriByTableName(String tableName) {
		return Uri.parse(new StringBuffer().append("content://").append(AUTHORITIES).append(CHARACTER_SLASH).append(tableName).toString());
	}

}
