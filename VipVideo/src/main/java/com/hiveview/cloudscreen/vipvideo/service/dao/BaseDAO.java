package com.hiveview.cloudscreen.vipvideo.service.dao;

import android.net.Uri;

import com.hiveview.cloudscreen.vipvideo.service.entity.HiveviewBaseEntity;

import java.util.ArrayList;


/**
 * base DAO for storing URI path & authorities
 * */
public abstract class BaseDAO {
	/**
	 * create a String for creating table in database
	 * 
	 * @return String , String for creating table.
	 * */
	public abstract String createTableString();

	/**
	 * drop table if exists
	 * 
	 * @return String , for executing ,remove table from database
	 * */
	public abstract String dropTable();

	/**
	 * insert data into database
	 * completely down-loaded entity info
	 * */
	public abstract void insert(ArrayList<? extends HiveviewBaseEntity> arrayList);

	/**
	 * insert data into database
	 * 
	 * @param obj
	 *            , completely down-loaded entity info
	 * */
	public abstract <T extends HiveviewBaseEntity> void insert(T obj);

	/**
	 * delete record with filter
	 * 
	 * @param where
	 *            , A filter to apply to rows before deleting, formatted as an
	 *            SQL WHERE clause (excluding the WHERE itself).
	 * @param selectionArgs
	 *            ,
	 * */
	public abstract void delete(String where, String[] selectionArgs);

	/**
	 * query records with filter
	 * 
	 * @param where
	 *            , A filter declaring which rows to return, formatted as an SQL
	 *            WHERE clause (excluding the WHERE itself). Passing null will
	 *            return all rows for the given URI.
	 * @param selectionArgs
	 *            , You may include ?s in selection, which will be replaced by
	 *            the values from selectionArgs, in the order that they appear
	 *            in the selection. The values will be bound as Strings.
	 * @return ArrayList<Object> , the list of records
	 * */
	public abstract ArrayList<? extends HiveviewBaseEntity> query(String[] selections, String where, String[] selectionArgs, String sortOrder);

	// public abstract <T extends BaseEntity> T query();

	/**
	 * update and reset the records
	 * 
	 * @param <T>
	 * @param obj
	 *            ,
	 * @param where
	 *            , A filter to apply to rows before deleting, formatted as an
	 *            SQL WHERE clause (excluding the WHERE itself).
	 * @param selectionArgs
	 *            ,
	 * */
	public abstract <T extends HiveviewBaseEntity> void update(T obj, String where, String[] selectionArgs);

	public abstract Uri getCurrentTableUri();

	public abstract String getTableName();
	
	public abstract int getURICode();

}
