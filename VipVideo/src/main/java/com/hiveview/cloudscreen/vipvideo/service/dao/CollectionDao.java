/**
 * @Title CollectionDao.java
 * @Package com.hiveview.cloudscreen.video.db
 * @author haozening
 * @date 2014年9月11日 下午4:05:57
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.service.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.CollectEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.HiveviewBaseEntity;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * @author haozening
 * @ClassName CollectionDao
 * @Description
 * @date 2014年9月11日 下午4:05:57
 */
public class CollectionDao extends BaseDAO {

    /**
     * 表名
     */
    private static final String TABLE_NAME = "album_collection";
    private static final String COLUMN_ID_ = "_id";
    private static final String COLUMN_COLLECT_TIME = "collectTime";
    private static final String COLUMN_ALBUM_DESC = "albumDesc";
    public static final String COLUMN_ALBUM_STREAM = "albumStream";
    private static final String COLUMN_ALBUM_TYPE = "albumType";
    private static final String COLUMN_BLUE_RAY_IMG = "blueRayImg";
    private static final String COLUMN_CID = "chnId";
    private static final String COLUMN_CP_ID = "cpId";
    private static final String COLUMN_CR_END_DATE = "crEndDate";
    private static final String COLUMN_DIRECTORS = "directors";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_EPISODE_TOTAL = "episodeTotal";
    private static final String COLUMN_EPISODE_UPDATED = "episodeUpdated";
    private static final String COLUMN_FOCUS = "focus";
    public static final String COLUMN_ID = "id";
    private static final String COLUMN_INIT_ISSUE_TIME = "issueTime";
    private static final String COLUMN_IS3D = "is3d";
    public static final String COLUMN_ISVIP = "isVip";
    private static final String COLUMN_KEYWORD = "keyword";
    private static final String COLUMN_LABELS = "labels";
    private static final String COLUMN_LAST_MODIFY_TIME = "lastModifyTime";
    private static final String COLUMN_MAIN_ACTORS = "mainActors";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PAGE = "page";
    private static final String COLUMN_PAGE_INDEX = "pageIndex";
    private static final String COLUMN_PAGE_SIZE = "pageSize";
    private static final String COLUMN_PIC_URL = "picUrl";
    public static final String COLUMN_PLAY_ADDRESS = "playAddress";
    private static final String COLUMN_PLAY_COUNT = "playCount";
    private static final String COLUMN_PROVIDE_MODE = "provideMode";
    private static final String COLUMN_ROWS = "rows";
    private static final String COLUMN_SCORE = "score";
    public static final String COLUMN_SEQ = "seq";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_SYN_STATE = "synState";//同步状态，0为已同步，1为需同步添加，2为需同步删除
    public static final String COLUMN_USER_ID = "userId";
    public static final String COLUMN_AQYID = "aqyId";
    public static final String COLUMN_AQYISEFFECTIVE = "aqyIsEffective";
    public static final String COLUMN_JQID = "jqId";
    public static final String COLUMN_JQISEFFECTIVE = "jqIsEffective";


    private Context context;

    public CollectionDao(Context context) {
        super();
        this.context = context;
    }

    @Override
    public String createTableString() {
        return new StringBuilder().append("CREATE TABLE " + TABLE_NAME + "(")
                .append(COLUMN_ID_).append(" interger auto_increment primary key,")
                .append(COLUMN_COLLECT_TIME).append(" long,")
                .append(COLUMN_ALBUM_DESC).append(" varchar(200),")
                .append(COLUMN_ALBUM_STREAM).append(" varchar(200),")
                .append(COLUMN_ALBUM_TYPE).append(" varchar(200),")
                .append(COLUMN_BLUE_RAY_IMG).append(" varchar(200),")
                .append(COLUMN_CID).append(" integer,")
                .append(COLUMN_CP_ID).append(" integer,")
                .append(COLUMN_CR_END_DATE).append(" varchar(30),")
                .append(COLUMN_DIRECTORS).append(" varchar(30),")
                .append(COLUMN_DURATION).append(" varchar(30),")
                .append(COLUMN_EPISODE_TOTAL).append(" integer,")
                .append(COLUMN_EPISODE_UPDATED).append(" integer,")
                .append(COLUMN_FOCUS).append(" varchar(100),")
                .append(COLUMN_ID).append(" integer,")
                .append(COLUMN_INIT_ISSUE_TIME).append(" varchar(30),")
                .append(COLUMN_IS3D).append(" integer,")
                .append(COLUMN_ISVIP).append(" integer,")
                .append(COLUMN_KEYWORD).append(" varchar(30),")
                .append(COLUMN_LABELS).append(" varchar(30),")
                .append(COLUMN_LAST_MODIFY_TIME).append(" varchar(30),")
                .append(COLUMN_MAIN_ACTORS).append(" varchar(100),")
                .append(COLUMN_NAME).append(" varchar(30),")
                .append(COLUMN_PAGE).append(" integer,")
                .append(COLUMN_PAGE_INDEX).append(" integer,")
                .append(COLUMN_PAGE_SIZE).append(" integer,")
                .append(COLUMN_PIC_URL).append(" varchar(200),")
                .append(COLUMN_PLAY_ADDRESS).append(" varchar(200),")
                .append(COLUMN_AQYID).append(" varchar(30),")
                .append(COLUMN_JQID).append(" varchar(30),")
                .append(COLUMN_PLAY_COUNT).append(" long,")
                .append(COLUMN_PROVIDE_MODE).append(" varchar(30),")
                .append(COLUMN_ROWS).append(" integer,")
                .append(COLUMN_SCORE).append(" integer,")
                .append(COLUMN_AQYISEFFECTIVE).append(" integer,")
                .append(COLUMN_JQISEFFECTIVE).append(" integer,")
                .append(COLUMN_SEQ).append(" integer,")
                .append(COLUMN_YEAR).append(" integer,")
                .append(COLUMN_SYN_STATE).append(" integer,")
                .append(COLUMN_USER_ID).append(" varchar(30))").toString();
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
        ContentResolver resolver = context.getContentResolver();
        AlbumEntity entity = (AlbumEntity) obj;

        if (null != entity) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_COLLECT_TIME, System.currentTimeMillis() + "");
            values.put(COLUMN_ALBUM_DESC, entity.getAlbumDesc());
            values.put(COLUMN_AQYID, entity.getAqyId());
            values.put(COLUMN_AQYISEFFECTIVE, entity.getAqyIsEffective());
            values.put(COLUMN_AQYID, entity.getJqId());
            values.put(COLUMN_JQISEFFECTIVE, entity.getJqIsEffective());
            values.put(COLUMN_ALBUM_TYPE, entity.getAlbumType());
            values.put(COLUMN_BLUE_RAY_IMG, entity.getAlbumHbPic());
            values.put(COLUMN_CID, entity.getChnId());
            values.put(COLUMN_DIRECTORS, entity.getDirectors());
            values.put(COLUMN_DURATION, entity.getDuration());
            values.put(COLUMN_EPISODE_TOTAL, entity.getEpisodeTotal());
            values.put(COLUMN_EPISODE_UPDATED, entity.getEpisodeUpdated());
            values.put(COLUMN_FOCUS, entity.getFocus());
            values.put(COLUMN_ID, entity.getProgramsetId());
            values.put(COLUMN_INIT_ISSUE_TIME, entity.getIssueTime());
            values.put(COLUMN_IS3D, entity.getIs3d());
            values.put(COLUMN_ISVIP, entity.getIsVip());
            values.put(COLUMN_KEYWORD, entity.getKeyword());
            values.put(COLUMN_LABELS, entity.getLabels());
            values.put(COLUMN_LAST_MODIFY_TIME, entity.getLabels());
            values.put(COLUMN_MAIN_ACTORS, entity.getMainActors());
            values.put(COLUMN_NAME, entity.getAlbumName());
            values.put(COLUMN_PAGE, entity.getPage());
            values.put(COLUMN_PAGE_INDEX, entity.getPageIndex());
            values.put(COLUMN_PAGE_SIZE, entity.getPageSize());
            values.put(COLUMN_PIC_URL, entity.getAlbumXqyPic());
            values.put(COLUMN_SCORE, entity.getScore());
            values.put(COLUMN_SEQ, entity.getSeq());
            values.put(COLUMN_YEAR, entity.getYear());
            values.put(COLUMN_SYN_STATE, 1);
            values.put(COLUMN_USER_ID, UserStateUtil.getInstance().getUserInfo().userId);
            resolver.insert(getCurrentTableUri(), values);
        }
    }


    @Override
    public void delete(String where, String[] selectionArgs) {
        ContentResolver resolver = context.getContentResolver();
        resolver.delete(getCurrentTableUri(), where, selectionArgs);
    }

    @Override
    public ArrayList<? extends HiveviewBaseEntity> query(String[] selections, String where, String[] selectionArgs, String sortOrder) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(getCurrentTableUri(), selections, where, selectionArgs, sortOrder);
        ArrayList<AlbumEntity> list = new ArrayList<AlbumEntity>();
        while (cursor.moveToNext()) {

            AlbumEntity entity = new AlbumEntity();
            entity.setAlbumDesc(cursor.getString(cursor.getColumnIndex(COLUMN_ALBUM_DESC)));
            entity.setAqyId(cursor.getString(cursor.getColumnIndex(COLUMN_AQYID)));
            entity.setAqyIsEffective(cursor.getInt(cursor.getColumnIndex(COLUMN_AQYISEFFECTIVE)));
            entity.setJqId(cursor.getString(cursor.getColumnIndex(COLUMN_JQID)));
            entity.setJqIsEffective(cursor.getInt(cursor.getColumnIndex(COLUMN_JQISEFFECTIVE)));
            entity.setAlbumType(cursor.getString(cursor.getColumnIndex(COLUMN_ALBUM_TYPE)));
            entity.setAlbumHbPic(cursor.getString(cursor.getColumnIndex(COLUMN_BLUE_RAY_IMG)));
            entity.setChnId(cursor.getInt(cursor.getColumnIndex(COLUMN_CID)));
            entity.setDirectors(cursor.getString(cursor.getColumnIndex(COLUMN_DIRECTORS)));
            entity.setDuration(cursor.getString(cursor.getColumnIndex(COLUMN_DURATION)));
            entity.setEpisodeTotal(cursor.getInt(cursor.getColumnIndex(COLUMN_EPISODE_TOTAL)));
            entity.setEpisodeUpdated(cursor.getInt(cursor.getColumnIndex(COLUMN_EPISODE_UPDATED)));
            entity.setFocus(cursor.getString(cursor.getColumnIndex(COLUMN_FOCUS)));
            entity.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            entity.setIssueTime(cursor.getString(cursor.getColumnIndex(COLUMN_INIT_ISSUE_TIME)));
            entity.setIs3d(cursor.getInt(cursor.getColumnIndex(COLUMN_IS3D)));
            entity.setIsVip(cursor.getInt(cursor.getColumnIndex(COLUMN_ISVIP)));
            entity.setKeyword(cursor.getString(cursor.getColumnIndex(COLUMN_KEYWORD)));
            entity.setLabels(cursor.getString(cursor.getColumnIndex(COLUMN_LABELS)));
            entity.setMainActors(cursor.getString(cursor.getColumnIndex(COLUMN_MAIN_ACTORS)));
            entity.setAlbumName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            entity.setPage(cursor.getInt(cursor.getColumnIndex(COLUMN_PAGE)));
            entity.setPageIndex(cursor.getInt(cursor.getColumnIndex(COLUMN_PAGE_INDEX)));
            entity.setPageSize(cursor.getInt(cursor.getColumnIndex(COLUMN_PAGE_SIZE)));
            entity.setAlbumXqyPic(cursor.getString(cursor.getColumnIndex(COLUMN_PIC_URL)));
            entity.setScore(cursor.getString(cursor.getColumnIndex(COLUMN_SCORE)));
            entity.setSeq(cursor.getInt(cursor.getColumnIndex(COLUMN_SEQ)));
            entity.setYear(cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR)));
            list.add(entity);
        }
        cursor.close();
        return list;
    }


    @Override
    public <T extends HiveviewBaseEntity> void update(T obj, String where, String[] selectionArgs) {
        ContentResolver resolver = context.getContentResolver();
        AlbumEntity entity = (AlbumEntity) obj;

        ContentValues values = new ContentValues();
        values.put(COLUMN_COLLECT_TIME, System.currentTimeMillis() + "");
        values.put(COLUMN_ALBUM_DESC, entity.getAlbumDesc());
        values.put(COLUMN_AQYID, entity.getAqyId());
        values.put(COLUMN_AQYISEFFECTIVE, entity.getAqyIsEffective());
        values.put(COLUMN_JQID, entity.getJqId());
        values.put(COLUMN_JQISEFFECTIVE, entity.getJqIsEffective());
        values.put(COLUMN_ALBUM_TYPE, entity.getAlbumType());
        values.put(COLUMN_BLUE_RAY_IMG, entity.getAlbumHbPic());
        values.put(COLUMN_CID, entity.getChnId());
        values.put(COLUMN_DIRECTORS, entity.getDirectors());
        values.put(COLUMN_DURATION, entity.getDuration());
        values.put(COLUMN_EPISODE_TOTAL, entity.getEpisodeTotal());
        values.put(COLUMN_EPISODE_UPDATED, entity.getEpisodeUpdated());
        values.put(COLUMN_FOCUS, entity.getFocus());
        values.put(COLUMN_ID, entity.getProgramsetId());
        values.put(COLUMN_INIT_ISSUE_TIME, entity.getIssueTime());
        values.put(COLUMN_IS3D, entity.getIs3d());
        values.put(COLUMN_ISVIP, entity.getIsVip());
        values.put(COLUMN_KEYWORD, entity.getKeyword());
        values.put(COLUMN_LABELS, entity.getLabels());
        values.put(COLUMN_LAST_MODIFY_TIME, entity.getLabels());
        values.put(COLUMN_MAIN_ACTORS, entity.getMainActors());
        values.put(COLUMN_NAME, entity.getAlbumName());
        values.put(COLUMN_PAGE, entity.getPage());
        values.put(COLUMN_PAGE_INDEX, entity.getPageIndex());
        values.put(COLUMN_PAGE_SIZE, entity.getPageSize());
        values.put(COLUMN_PIC_URL, entity.getAlbumXqyPic());
        values.put(COLUMN_SCORE, entity.getScore());
        values.put(COLUMN_SEQ, entity.getSeq());
        values.put(COLUMN_YEAR, entity.getYear());
        resolver.update(getCurrentTableUri(), values, where, selectionArgs);
    }

    /**
     * @param id                  需要变更的收藏记录id
     * @param synState            0已同步，1待同步添加，2待同步删除
     * @param userId              为空表示全部更新
     * @param isChangeCollectTime 是否需要变更收藏时间，增加删除操作时需要变更，同步操作无需变更
     * @Author Spr_ypt
     * @Date 2016/5/19
     * @Description 变更网络同步状态
     */
    public synchronized void updateSynFromId(Integer id, int synState, String userId, boolean isChangeCollectTime) {
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        if (isChangeCollectTime) {
            values.put(COLUMN_COLLECT_TIME, System.currentTimeMillis() + "");
        }
        values.put(COLUMN_SYN_STATE, synState);
        String where = "";
        String[] selectionArgs;
        if (null != id) {
            where = COLUMN_ID + " = ? AND " + COLUMN_USER_ID + " = ? ";
            selectionArgs = new String[]{id + "", userId};
        } else {
            where = COLUMN_USER_ID + " = ? ";
            selectionArgs = new String[]{userId};
        }
        resolver.update(getCurrentTableUri(), values, where, selectionArgs);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/24
     * @Description 认领不知道谁收藏的数据
     */
    public void updateDataNoUser() {
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, UserStateUtil.getInstance().getUserInfo().userId);
        String where = COLUMN_USER_ID + " IS NULL OR " + COLUMN_USER_ID + " =  '' ";
        resolver.update(getCurrentTableUri(), values, where, null);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/24
     * @Description 同步后插入数据
     */
    public void insertCollectEntity(CollectEntity entity) {
        ContentResolver resolver = context.getContentResolver();
        if (null != entity) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, entity.getCollectId());
            values.put(COLUMN_COLLECT_TIME, entity.getCollectTime());
            values.put(COLUMN_BLUE_RAY_IMG, entity.getBlueRayImg());
            values.put(COLUMN_CID, entity.getCid());
            values.put(COLUMN_CP_ID, entity.getCpId());
            values.put(COLUMN_EPISODE_TOTAL, entity.getEpisodeTotal());
            values.put(COLUMN_EPISODE_UPDATED, entity.getEpisodeUpdate());
            values.put(COLUMN_NAME, entity.getName());
            values.put(COLUMN_PIC_URL, entity.getPicUrl());
            values.put(COLUMN_SYN_STATE, 0);
            values.put(COLUMN_USER_ID, UserStateUtil.getInstance().getUserInfo().userId);
            resolver.insert(getCurrentTableUri(), values);
        }
    }

    /**
     * @return
     * @Title query
     * @author xieyi
     * @Description 点播收藏查询
     */
    public List<CollectEntity> query(String where, String[] selectionArgs, boolean isBlue) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(getCurrentTableUri(), null, where, selectionArgs, COLUMN_COLLECT_TIME + " desc");
        List<CollectEntity> list = new ArrayList<CollectEntity>();
        while (cursor.moveToNext()) {
            CollectEntity entity = new CollectEntity();
            if (isBlue) {
                entity.setSource(1);
            } else {
                entity.setSource(0);
            }
            entity.setCid(cursor.getInt(cursor.getColumnIndex(COLUMN_CID)));
            entity.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            entity.setEpisodeUpdate(cursor.getInt(cursor.getColumnIndex(COLUMN_EPISODE_UPDATED)));
            entity.setCpId(cursor.getInt(cursor.getColumnIndex(COLUMN_CP_ID)));
            entity.setCollectTime(cursor.getLong(cursor.getColumnIndex(COLUMN_COLLECT_TIME)));
            entity.setCollectId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            entity.setPicUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PIC_URL)));
            entity.setAlbumId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)) + "");
            entity.setEpisodeTotal(cursor.getInt(cursor.getColumnIndex(COLUMN_EPISODE_TOTAL)));
            if (TextUtils.isEmpty(entity.getPicUrl()) || "null".equals(entity.getPicUrl())) {
                entity.setBlueRayImg(cursor.getString(cursor.getColumnIndex(COLUMN_BLUE_RAY_IMG)));
            }
            list.add(entity);
        }
        cursor.close();
        return list;
    }

    public boolean delete(CollectEntity entity) {
        ContentResolver resolver = context.getContentResolver();
        String where = COLUMN_ID + " = ? AND " + COLUMN_USER_ID + " = ? ";
        String[] selectionArgs = new String[]{entity.getCollectId() + "", UserStateUtil.getInstance().getUserInfo().userId};
        int columnResult = resolver.delete(getCurrentTableUri(), where, selectionArgs);
        if (columnResult == 1)
            return true;
        return false;
    }

    public boolean deleteQiyi(CollectEntity entity) {
        return delete(entity);
    }

    public boolean deleteAll() {
        ContentResolver resolver = context.getContentResolver();
        String where = COLUMN_USER_ID + " = ? ";
        String[] selectionArgs = new String[]{UserStateUtil.getInstance().getUserInfo().userId};
        int columnResult = resolver.delete(getCurrentTableUri(), where, selectionArgs);
        if (columnResult == 1)
            return true;
        return false;
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


    /**
     * @Author Spr_ypt
     * @Date 2016/5/19
     * @Description 数据库升级到3版本时执行
     */
    public String updateDBto3(int step) {
        if (step == 0) {
            return new StringBuilder()
                    .append("ALTER TABLE " + TABLE_NAME + " ")
                    .append("ADD " + COLUMN_SYN_STATE + " integer ")
                    .append("NOT NULL DEFAULT " + 1)
                    .toString();
        } else if (step == 1) {
            return new StringBuilder()
                    .append("ALTER TABLE " + TABLE_NAME + " ")
                    .append("ADD " + COLUMN_USER_ID + " varchar(30) ")
                    .toString();
        } else {
            return "";
        }

    }
}
