package com.hiveview.cloudscreen.vipvideo.service.dao;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.util.Logger;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;

import java.util.Iterator;
import java.util.Map.Entry;

/**
 * data base
 */
public class HiveViewProvider extends ContentProvider {
    private final String DB_NAME = "hiveview_cloud_video";
    private final int DB_VERSION = 4;

    private final String TAG = "HiveViewProvider";

    private SQLiteDatabase db;

    private UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    ;

    /**
     * set URI id in for created table
     * */ {
        TablesRegister.init(getContext());
        Iterator<Entry<Integer, BaseDAO>> iter = TablesRegister.getInstance().getRegisterMap().entrySet().iterator();
        while (iter.hasNext()) {
            Entry<Integer, BaseDAO> entry = iter.next();
            Integer key = entry.getKey();
            BaseDAO dao = entry.getValue();
            mUriMatcher.addURI(TablesRegister.getInstance().AUTHORITIES, dao.getTableName(), key);
        }

    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {

			/* create database */
            super(context, DB_NAME, null, DB_VERSION);

        }

        /**
         * create table
         */
        public void onCreate(SQLiteDatabase db) {
            Iterator<Entry<Integer, BaseDAO>> iter = TablesRegister.getInstance().getRegisterMap().entrySet().iterator();
            while (iter.hasNext()) {
                Entry<Integer, BaseDAO> entry = iter.next();
                BaseDAO dao = entry.getValue();
                db.execSQL(dao.createTableString());
            }

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            for (int i = oldVersion; i < newVersion; i++) {
                switch (i) {
                    case 1:
                        upgrade1To2(db);
                        break;
                    case 2:
                        upgrade2To3(db);
                        break;
                    case 3:
                        upgrade3To4(db);
                        break;
                }
            }
        }

        private void upgrade2To3(SQLiteDatabase db) {
            CollectionDao dao = new CollectionDao(getContext());
            db.execSQL(dao.updateDBto3(0));
            db.execSQL(dao.updateDBto3(1));
            db.execSQL(dao.updateDBto3(2));
        }

        private void upgrade1To2(SQLiteDatabase db) {
            CollectionDao dao = new CollectionDao(getContext());
            db.execSQL(dao.dropTable());
            db.execSQL(dao.createTableString());
            HomeRecommendDao recommendDao = new HomeRecommendDao(getContext());
            db.execSQL(recommendDao.dropTable());
            db.execSQL(recommendDao.createTableString());
        }

    }

    private void upgrade3To4(SQLiteDatabase db) {
        CollectionDao dao = new CollectionDao(getContext());
        db.execSQL(dao.dropTable());
        db.execSQL(dao.createTableString());
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int matchCode = mUriMatcher.match(uri);

        String tableName = TablesRegister.getInstance().getRegisterMap().get(matchCode).getTableName();

        int effect = db.delete(tableName, selection, selectionArgs);

        Log.d(TAG, "delete effect row count " + effect);

        return effect;
    }

    public String getType(Uri uri) {

        return uri.toString();

    }

    public Uri insert(Uri uri, ContentValues values) {

        int matchCode = mUriMatcher.match(uri);

        String tableName = TablesRegister.getInstance().getRegisterMap().get(matchCode).getTableName();

        long effect = db.insert(tableName, null, values);

        Log.d(TAG, "insert effect row count " + effect);

        return uri;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        int matchCode = mUriMatcher.match(uri);

        String tableName = TablesRegister.getInstance().getRegisterMap().get(matchCode).getTableName();

        Cursor cursor = db.query(tableName, null, selection, selectionArgs, null, null, sortOrder);

        Log.d(TAG, "cursor query row count " + cursor.getCount());

        return cursor;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int matchCode = mUriMatcher.match(uri);

        String tableName = TablesRegister.getInstance().getRegisterMap().get(matchCode).getTableName();

        int effect = db.update(tableName, values, selection, selectionArgs);

        Log.d(TAG, "update effect row count " + effect);

        return 0;
    }

    @Override
    public boolean onCreate() {

        db = new DBHelper(getContext()).getWritableDatabase();

        return false;
    }

}
