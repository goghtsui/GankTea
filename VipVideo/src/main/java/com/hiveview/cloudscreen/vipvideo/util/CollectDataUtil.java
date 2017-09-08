package com.hiveview.cloudscreen.vipvideo.util;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.service.CloudScreenService;
import com.hiveview.cloudscreen.vipvideo.service.OnRequestResultListener;
import com.hiveview.cloudscreen.vipvideo.service.dao.CollectionDao;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.CollectEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/5/19
 * @Description 该类单例，用于处理收藏内容与网络同步的复杂逻辑
 * 通过{@link #getCollectData(OnCollectDataListener)}方法获取数据并同步到服务器
 * 通过{@link #notifyCollectDataChanged(OnCollectDataListener)}方法获取本地数据
 * （不进行同步，listener可以为空，不过需要调用过上面方法才能正确返回值）
 * 通过{@link #addCollect(AlbumEntity)}方法添加收藏
 * 通过{@link #deleteCollect(Integer)}方法删除收藏
 */
public class CollectDataUtil {
    private static final String TAG = CollectDataUtil.class.getSimpleName();
    /**
     * 本地数据
     */
    private List<CollectEntity> localData = new ArrayList<>();

    /**
     * context
     */
    private Context ctx = CloudScreenApplication.getInstance().getApplicationContext();

    /**
     * 标记添加同步是否完成
     */
    private boolean isAsyAddComplete;

    /**
     * 标记删除同步是否完成
     */
    private boolean isAsyDeleteComplete;

    /**
     * 标记数据同步过程中是否有失败的情况
     */
    private AtomicBoolean asyFailed = new AtomicBoolean(false);
    /**
     * 数据获取回调监听
     */
    private OnCollectDataListener onCollectDataListener;

    /**
     * 单例该类
     */
    private static class UserHolder {
        private static final CollectDataUtil INSTANCE = new CollectDataUtil();
    }

    private CollectDataUtil() {
    }

    public static final CollectDataUtil getInstance() {
        return UserHolder.INSTANCE;
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/19
     * @Description 获取收藏数据, 包含同步数据
     * 展示数据时至少调用一次，之后如果只是获取本地数据可调用{@link #notifyCollectDataChanged(OnCollectDataListener)}方法
     */
    public void getCollectData(OnCollectDataListener listener) {
        Logger.d(TAG, "getCollectData");
        clearLocalData();
        onCollectDataListener = listener;
        //初始化标记位
        isAsyAddComplete = false;
        isAsyDeleteComplete = false;
        asyFailed.set(false);
        //认领不知道谁收藏的数据
        CollectionDao dao = new CollectionDao(ctx);
        dao.updateDataNoUser();
        //加载本地数据
        new AsynDBTask().execute(ctx);
        //同步待添加数据
        new AsynAddTask(true).execute(ctx);
        //同步待删除数据
        new AsyDeleteTask(true).execute(ctx);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/20
     * @Description 获取收藏数据，不会同步到服务器
     * 需要调用过{@link #getCollectData(OnCollectDataListener)}方法
     */
    public void notifyCollectDataChanged(OnCollectDataListener listener) {
        Logger.d(TAG, "notifyCollectDataChanged");
        if (null != listener) {
            clearLocalData();
            onCollectDataListener = listener;
        }
        //加载本地数据
        new AsynDBTask().execute(ctx);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/7/21
     * @Description 同步数据，不论成败，不返回结果
     */
    public void asynCollectData() {
        //同步待添加数据
        new AsynAddTask(false).execute(ctx);
        //同步待删除数据
        new AsyDeleteTask(false).execute(ctx);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/20
     * @Description 添加同步完成
     */
    private void asyAddComplete() {
        Logger.d(TAG, "asyAddComplete");
        isAsyAddComplete = true;
        if (isAsyDeleteComplete) {
            asyDataComplete();
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/20
     * @Description 删除同步完成
     */
    private void asyDeleteComplete() {
        Logger.d(TAG, "asyDeleteComplete");
        isAsyDeleteComplete = true;
        if (isAsyAddComplete) {
            asyDataComplete();
        }

    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/20
     * @Description 向服务器同步数据完成
     */
    private void asyDataComplete() {
        Logger.d(TAG, "asyDataComplete");
        String userId = UserStateUtil.getInstance().getUserInfo().userId;
        CloudScreenService.getInstance().postCollectGetData(userId, 1, 500, new OnRequestResultListener<ResultEntity<CollectEntity>>() {
            @Override
            public void onSucess(ResultEntity<CollectEntity> resultEntity) {
                List<CollectEntity> collect = resultEntity.getList();
                if (null != collect && !equalCollectData(localData, collect)) {
                    //通知回调
                    if (null != onCollectDataListener) {
                        onCollectDataListener.onSynData(collect);
                    }
                    //更新数据库
                    CollectionDao dao = new CollectionDao(ctx);
                    dao.deleteAll();
                    for (CollectEntity entity : collect) {
                        dao.insertCollectEntity(entity);
                    }
                }
            }

            @Override
            public void onFail(Exception e) {
                asyFailed.set(true);
                Logger.d(TAG, "postCollectGetData.onFail e=" + e.toString());

            }

            @Override
            public void onParseFail(HiveviewException e) {
                asyFailed.set(true);
                Logger.d(TAG, "postCollectGetData.onParseFail e=" + e.toString());
            }

            private boolean equalCollectData(List<CollectEntity> localData, List<CollectEntity> netData) {
                if (localData.size() != netData.size()) {
                    return false;
                } else {
                    List<Integer> loacalIds = new ArrayList<>();
                    List<Integer> netIds = new ArrayList<>();
                    for (CollectEntity entity : localData) {
                        loacalIds.add(entity.getCollectId());
                    }
                    for (CollectEntity entity : netData) {
                        netIds.add(entity.getCollectId());
                    }
                    Collections.sort(loacalIds);
                    Collections.sort(netIds);
                    if (loacalIds.equals(netIds)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        });
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/20
     * @Description 数据获取回调
     */
    public interface OnCollectDataListener {
        void onLocalData(List<CollectEntity> data);

        void onSynData(List<CollectEntity> data);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/19
     * @Description 添加收藏数据，并标记为添加未同步，
     * 会操作数据库请不要在主线程内调用
     * 会在调用{@link #getCollectData(OnCollectDataListener)}时被同步到服务器
     */
    public void addCollect(final AlbumEntity entity) {
        Logger.d(TAG, "addCollect id=" + entity.getProgramsetId());
        CollectionDao dao = new CollectionDao(ctx);
        if (isCollected(entity.getProgramsetId(), dao)) {
            dao.updateSynFromId(entity.getProgramsetId(), 1, UserStateUtil.getInstance().getUserInfo().userId, true);
        } else {
            dao.insert(entity);
        }
    }

    /**
     * @param collectId 为空表示全部删除
     * @Author Spr_ypt
     * @Date 2016/5/19
     * @Description 删除收藏数据
     * 这里不直接删除，只是设置标志位
     * *会操作数据库请不要在主线程内调用
     * 真正的删除在同步数据时进行，详见{@link #getCollectData(OnCollectDataListener)}方法
     */
    public void deleteCollect(final Integer collectId) {
        CollectionDao dao = new CollectionDao(ctx);
        dao.updateSynFromId(collectId, 2, UserStateUtil.getInstance().getUserInfo().userId, true);

    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/19
     * @Description 数据库获取可展示数据的异步过程
     */
    private class AsynDBTask extends AsyncTask<Context, String, List<CollectEntity>> {

        @Override
        protected List<CollectEntity> doInBackground(Context... params) {
            CollectionDao dao = new CollectionDao(params[0]);
            String userId = UserStateUtil.getInstance().getUserInfo().userId;
            String where = CollectionDao.COLUMN_SYN_STATE + " != ? AND " + CollectionDao.COLUMN_USER_ID + " = ? ";
            String[] selectionArgs = new String[]{2 + "", userId};
            List<CollectEntity> vipVideo = dao.query(where, selectionArgs, false);
            return vipVideo;
        }

        @Override
        protected void onPostExecute(List<CollectEntity> result) {
            super.onPostExecute(result);
            if (result.size() == 0 || !equalsList(result, localData)) {
                localData = result;
                if (null != onCollectDataListener) {
                    onCollectDataListener.onLocalData(result);
                }
            }
        }

        private boolean equalsList(List<CollectEntity> newList, List<CollectEntity> oldList) {
            if (newList.size() != oldList.size()) {
                return false;
            } else if (!listToString(newList).equals(listToString(oldList))) {
                return false;
            } else {
                return true;
            }
        }

        private String listToString(List<CollectEntity> list) {
            String str = "";
            for (CollectEntity entity : list) {
                str += entity.toString();
            }
            Logger.d(TAG, "str=" + str);
            return str;
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/20
     * @Description 本地数据同步添加到服务器端
     * 如果设置isNeedRefreshData为true同步成功后会回调{@link #asyAddComplete()}方法
     */
    private class AsynAddTask extends AsyncTask<Context, String, List<CollectEntity>> {

        /**
         * 是否需要刷新数据
         */
        private boolean isNeedRefreshData;

        public AsynAddTask(boolean isNeedRefreshData) {
            this.isNeedRefreshData = isNeedRefreshData;
        }

        @Override
        protected List<CollectEntity> doInBackground(Context... params) {
            CollectionDao dao = new CollectionDao(params[0]);
            String userId = UserStateUtil.getInstance().getUserInfo().userId;
            String where = CollectionDao.COLUMN_SYN_STATE + " = ? AND " + CollectionDao.COLUMN_USER_ID + " = ? ";
            String[] selectionArgs = new String[]{1 + "", userId};
            List<CollectEntity> vipVideo = dao.query(where, selectionArgs, false);
            return vipVideo;
        }

        @Override
        protected void onPostExecute(final List<CollectEntity> collectEntities) {
            super.onPostExecute(collectEntities);
            if (collectEntities.size() > 0) {
                for (int i = 0; i < collectEntities.size(); i++) {
                    final CollectEntity entity = collectEntities.get(i);
                    final String userId = UserStateUtil.getInstance().getUserInfo().userId;
                    final int finalI = i;
                    CloudScreenService.getInstance().postCollectAddData(userId, entity, new OnRequestResultListener<ResultEntity>() {
                        @Override
                        public void onSucess(ResultEntity resultEntity) {
                            if (resultEntity.errorCode.equals("N000000") || resultEntity.errorCode.equals("E000155")) {
                                CollectionDao dao = new CollectionDao(ctx);
                                dao.updateSynFromId(entity.getCollectId(), 0, userId, false);
                                if (finalI == collectEntities.size() - 1 && !asyFailed.get()) {
                                    if (isNeedRefreshData) {
                                        asyAddComplete();
                                    }
                                }
                            } else {
                                asyFailed.set(true);
                            }
                        }

                        @Override
                        public void onFail(Exception e) {
                            asyFailed.set(true);
                        }

                        @Override
                        public void onParseFail(HiveviewException e) {
                            asyFailed.set(true);
                        }
                    });
                }
            } else {
                if (isNeedRefreshData) {
                    asyAddComplete();
                }
            }
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/20
     * @Description 本地数据同步删除到服务器端
     * 如果设置isNeedRefreshData为true同步成功后会回调{@link #asyDeleteComplete()}方法
     */
    private class AsyDeleteTask extends AsyncTask<Context, String, List<CollectEntity>> {

        /**
         * 是否需要刷新数据
         */
        private boolean isNeedRefreshData;

        public AsyDeleteTask(boolean isNeedRefreshData) {
            this.isNeedRefreshData = isNeedRefreshData;
        }

        @Override
        protected List<CollectEntity> doInBackground(Context... params) {
            CollectionDao dao = new CollectionDao(params[0]);
            String userId = UserStateUtil.getInstance().getUserInfo().userId;
            String where = CollectionDao.COLUMN_SYN_STATE + " = ? AND " + CollectionDao.COLUMN_USER_ID + " = ? ";
            String[] selectionArgs = new String[]{2 + "", userId};
            List<CollectEntity> vipVideo = dao.query(where, selectionArgs, false);
            return vipVideo;
        }

        @Override
        protected void onPostExecute(final List<CollectEntity> collectEntities) {
            super.onPostExecute(collectEntities);
            if (collectEntities.size() > 0) {
                String userId = UserStateUtil.getInstance().getUserInfo().userId;
                if (localData.size() == 0) {//这说明所有的数据都被删除了
                    CloudScreenService.getInstance().postCollectDeleteData(userId, null, new OnRequestResultListener<ResultEntity>() {
                        @Override
                        public void onSucess(ResultEntity resultEntity) {
                            if (resultEntity.errorCode.equals("N000000") || resultEntity.errorCode.equals("E000156")) {
                                CollectionDao dao = new CollectionDao(ctx);
                                dao.deleteAll();
                                if (isNeedRefreshData) {
                                    asyDeleteComplete();
                                }
                            } else {
                                asyFailed.set(true);
                            }

                        }

                        @Override
                        public void onFail(Exception e) {
                            asyFailed.set(true);
                        }

                        @Override
                        public void onParseFail(HiveviewException e) {
                            asyFailed.set(true);
                        }
                    });
                } else {
                    for (int i = 0; i < collectEntities.size(); i++) {
                        final CollectEntity entity = collectEntities.get(i);
                        final int finalI = i;
                        CloudScreenService.getInstance().postCollectDeleteData(userId, entity.getCollectId(), new OnRequestResultListener<ResultEntity>() {
                            @Override
                            public void onSucess(ResultEntity resultEntity) {
                                if (resultEntity.errorCode.equals("N000000") || resultEntity.errorCode.equals("E000156")) {
                                    CollectionDao dao = new CollectionDao(ctx);
                                    dao.delete(entity);
                                    if (finalI == collectEntities.size() - 1 && !asyFailed.get()) {
                                        if (isNeedRefreshData) {
                                            asyDeleteComplete();
                                        }
                                    }
                                } else {
                                    asyFailed.set(true);
                                }
                            }

                            @Override
                            public void onFail(Exception e) {
                                asyFailed.set(true);
                            }

                            @Override
                            public void onParseFail(HiveviewException e) {
                                asyFailed.set(true);
                            }
                        });
                    }
                }
            } else {
                if (isNeedRefreshData) {
                    asyDeleteComplete();
                }
            }
        }
    }


    /**
     * @Author Spr_ypt
     * @Date 2016/6/8
     * @Description 是否已被收藏，根据影片id判断该影片是否收藏了
     */
    private boolean isCollected(int collectId, CollectionDao dao) {
        ArrayList<AlbumEntity> list = null;
        String where = CollectionDao.COLUMN_ID + " = ? AND " + CollectionDao.COLUMN_USER_ID + " = ? ";
        String[] selectionArgs = new String[]{collectId + "", UserStateUtil.getInstance().getUserInfo().userId};
        list = (ArrayList<AlbumEntity>) dao.query(null, where, selectionArgs, null);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/24
     * @Description 清理本地数据缓存
     */
    public void clearLocalData() {
        localData.clear();
    }
}
