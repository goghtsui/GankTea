package com.hiveview.cloudscreen.vipvideo.service.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.service.entity.HiveviewBaseEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.RecommendListEntity;

import java.util.ArrayList;


public class HomeRecommendDao extends BaseDAO {

    private String TAG = "HomeRecommendDao";
    private Context context;

    public HomeRecommendDao(Context context) {
        this.context = context;
    }

    private final String ID_ = "_id";
    private static final String ID = "id";
    private static final String CONTENT_TYPE = "contentType";
    private static final String CONTENT_ID = "contentId";
    private static final String POSITION = "position";
    private static final String CONTENT_FOCUS = "contentFocus";
    private static final String CONTENT_NAME = "contentName";
    private static final String CONTENT_IMG = "contentImg";
    private static final String CONTENT_UPDATE = "contentUpdate";
    private static final String IS_TXT_SHOW = "isTxtShow";
    private static final String CONTENT_TOTAL = "contentTotal";
    private static final String SEQ = "seq";
    private static final String IS_EFFECTIVE = "isEffective";
    private static final String CREATE_TIME = "createTime";
    private static final String UPDATE_TIME = "updateTime";
    private static final String CID = "cid";

    private static final String TABLE_NAME = "home_recommend";

    @Override
    public String createTableString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(ID_).append(" interger auto_increment primary key,")
                .append(ID).append(" integer,")
                .append(CONTENT_TYPE).append(" integer,")
                .append(CONTENT_ID).append(" integer,")
                .append(POSITION).append(" integer,")
                .append(CONTENT_FOCUS).append(" varchar(128),")
                .append(CONTENT_NAME).append(" varchar(128),")
                .append(CONTENT_IMG).append(" varchar(128),")
                .append(CONTENT_UPDATE).append(" varchar(10),")
                .append(IS_TXT_SHOW).append(" integer,")
                .append(CONTENT_TOTAL).append(" integer,")
                .append(SEQ).append(" integer,")
                .append(IS_EFFECTIVE).append(" integer,")
                .append(CREATE_TIME).append(" long,")
                .append(UPDATE_TIME).append(" long,")
                .append(CID).append(" integer")
                .append(")");
        return buffer.toString();
    }

    @Override
    public String dropTable() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(TablesRegister.getInstance().DROP_TABLE);
        buffer.append(TABLE_NAME);

        return buffer.toString();
    }

    @Override
    public void insert(ArrayList<? extends HiveviewBaseEntity> arrayList) {

    }


    @Override
    public <T extends HiveviewBaseEntity> void insert(T obj) {
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        RecommendListEntity entity = (RecommendListEntity) obj;
        values.put(CONTENT_FOCUS, entity.getContentFocus());
        values.put(CONTENT_ID, entity.getContentId());
        values.put(CONTENT_IMG, entity.getContentImg());
        values.put(CONTENT_NAME, entity.getContentName());
        values.put(CONTENT_TOTAL, entity.getContentTotal());
        values.put(CONTENT_TYPE, entity.getContentType());
        values.put(CONTENT_UPDATE, entity.getContentUpdate());
        values.put(IS_TXT_SHOW, entity.getIsTxtShow());
        values.put(POSITION, entity.getPosition());

        values.put(ID, entity.getId());
        values.put(SEQ, entity.getSeq());
        values.put(IS_EFFECTIVE, entity.getIsEffective());
        values.put(CREATE_TIME, entity.getCreateTime());
        values.put(UPDATE_TIME, entity.getUpdateTime());
        values.put(CID, entity.getCid());

        contentResolver.insert(getCurrentTableUri(), values);
    }

    @Override
    public void delete(String where, String[] selectionArgs) {
        ContentResolver resolver = context.getContentResolver();
        resolver.delete(getCurrentTableUri(), where, selectionArgs);
    }

    @Override
    public ArrayList<RecommendListEntity> query(String[] selections, String where, String[] selectionArgs, String sortOrder) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(getCurrentTableUri(), selections, where, selectionArgs, sortOrder);
        ArrayList<RecommendListEntity> arrayList = new ArrayList<RecommendListEntity>();
        RecommendListEntity entity = null;

        if (null == cursor) {
            Log.e(TAG, getTableName() + " query unknown error");
            return arrayList;
        }

        if (cursor.getCount() == 0) {
            Log.d(TAG, getTableName() + " query count is 0");
        }

        while (cursor.moveToNext()) {
            entity = new RecommendListEntity();
            entity.setContentFocus(cursor.getString(cursor.getColumnIndex(CONTENT_FOCUS)));
            entity.setContentId(cursor.getInt(cursor.getColumnIndex(CONTENT_ID)));
            entity.setContentImg(cursor.getString(cursor.getColumnIndex(CONTENT_IMG)));
            entity.setContentName(cursor.getString(cursor.getColumnIndex(CONTENT_NAME)));
            entity.setContentTotal(cursor.getInt(cursor.getColumnIndex(CONTENT_TOTAL)));
            entity.setContentType(cursor.getInt(cursor.getColumnIndex(CONTENT_TYPE)));
            entity.setContentUpdate(cursor.getString(cursor.getColumnIndex(CONTENT_UPDATE)));
            entity.setIsTxtShow(cursor.getInt(cursor.getColumnIndex(IS_TXT_SHOW)));
            entity.setPosition(cursor.getInt(cursor.getColumnIndex(POSITION)));

            entity.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            entity.setSeq(cursor.getInt(cursor.getColumnIndex(SEQ)));
            entity.setIsEffective(cursor.getInt(cursor.getColumnIndex(IS_EFFECTIVE)));
            entity.setCreateTime(cursor.getLong(cursor.getColumnIndex(CREATE_TIME)));
            entity.setUpdateTime(cursor.getLong(cursor.getColumnIndex(UPDATE_TIME)));
            entity.setCid(cursor.getInt(cursor.getColumnIndex(CID)));
            arrayList.add(entity);
        }

        cursor.close();

        return arrayList;
    }

    @Override
    public <T extends HiveviewBaseEntity> void update(T obj, String where, String[] selectionArgs) {
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        RecommendListEntity entity = (RecommendListEntity) obj;
        values.put(CONTENT_FOCUS, entity.getContentFocus());
        values.put(CONTENT_ID, entity.getContentId());
        values.put(CONTENT_IMG, entity.getContentImg());
        values.put(CONTENT_NAME, entity.getContentName());
        values.put(CONTENT_TOTAL, entity.getContentTotal());
        values.put(CONTENT_TYPE, entity.getContentType());
        values.put(CONTENT_UPDATE, entity.getContentUpdate());
        values.put(IS_TXT_SHOW, entity.getIsTxtShow());
        values.put(POSITION, entity.getPosition());

        values.put(ID, entity.getId());
        values.put(SEQ, entity.getSeq());
        values.put(IS_EFFECTIVE, entity.getIsEffective());
        values.put(CREATE_TIME, entity.getCreateTime());
        values.put(UPDATE_TIME, entity.getUpdateTime());
        values.put(CID, entity.getCid());
        resolver.update(getCurrentTableUri(), values, where, selectionArgs);
    }

    @Override
    public Uri getCurrentTableUri() {
        return TablesRegister.getInstance().getUriByTableName(TABLE_NAME);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public int getURICode() {
        return Math.abs(TABLE_NAME.hashCode());
    }

}
