package com.hiveview.cloudscreen.vipvideo.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.activity.adapter.RecordAdapter;
import com.hiveview.cloudscreen.vipvideo.activity.adapter.RecordUpAdapter;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.ContentShowType;
import com.hiveview.cloudscreen.vipvideo.common.Invoker;
import com.hiveview.cloudscreen.vipvideo.common.SizeConstant;
import com.hiveview.cloudscreen.vipvideo.common.VideoDetailInvoker;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.AppStoreEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.RecordEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.VideoRecordEntity;
import com.hiveview.cloudscreen.vipvideo.util.BitmapBlurUtil;
import com.hiveview.cloudscreen.vipvideo.util.DateWithRecordUtils;
import com.hiveview.cloudscreen.vipvideo.util.Logger;
import com.hiveview.cloudscreen.vipvideo.util.ToastUtil;
import com.hiveview.cloudscreen.vipvideo.view.CloundMenuWindow;
import com.hiveview.cloudscreen.vipvideo.view.DeleteWindow;
import com.hiveview.cloudscreen.vipvideo.view.HistoryView;
import com.hiveview.cloudscreen.vipvideo.view.NoResultView;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends BaseActivity {

    protected static final String TAG = "RecordActivity";
    private HistoryView historyView;
    private RecordAdapter recordAdapter;
    private DeleteWindow deleteWindow;
    private NoResultView emptyView;
    private View mainView;
    private boolean hasData;
    private SizeConstant sizeConstant;
    private boolean isFirstStart = true;//标记是否第一次启动
    //start by wangbei 20160725 对比之前数据
    private static List<RecordEntity> lastEntity;// 本地保存
    //用于第一次进入oncreate使用。
    private boolean firstAsy = true;
    //end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_layout);
        mainView = findViewById(R.id.history_view);
        historyView = (HistoryView) findViewById(R.id.main_history);
        emptyView = (NoResultView) findViewById(R.id.empty_title);
        emptyView.setData(R.drawable.history_color, getString(R.string.history_title), getString(R.string.no_history_record));
        sizeConstant = new SizeConstant(this);
        recordAdapter = new RecordAdapter(RecordActivity.this, historyView, clickBack, sizeConstant);
        createMenuWindow();
        new AsynDBTask().execute(this);//打开都更新下数据
        isFirstStart = false;
    }

    @Override
    protected void onResume() {
        if (!firstAsy) {
            Log.i(TAG, "onResume firstAsy：" + !firstAsy);
            new AsynDBTask().execute(this);//每次重新打开都更新下数据
            isFirstStart = false;
        }
        super.onResume();
    }

    private class AsynDBTask extends AsyncTask<Context, String, List<RecordEntity>> {

        @Override
        protected List<RecordEntity> doInBackground(Context... params) {
            if (!isFirstStart) {//首次读取数据库无需延迟
                try {
                    Thread.sleep(800);//这里读取过程延迟一会是为了防止数据库还未更新过来就读取造成顺序出错
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            List<RecordEntity> recordEntity = DateWithRecordUtils.openRecordChannel(params[0], qiYiInit);
            return recordEntity;
        }

        @Override
        protected void onPostExecute(List<RecordEntity> result) {
            if (result != null && result.size() > 0) {
                hasData = true;
                if (firstAsy || (null != lastEntity && !equalsList(result, lastEntity))) {
                    Log.i(TAG, " firstAsy:" + firstAsy + "  lastEntity==result:" + (null != lastEntity && !equalsList(result, lastEntity)));
                    firstAsy = false;
                    lastEntity = result;
                    recordAdapter.setData(result);
                    historyView.setAdapter(recordAdapter);
                    myHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            if (null != historyView && null != historyView.getChildAt(1)) {
                                if (null != historyView.getChildAt(1)) {
                                    historyView.getChildAt(1).requestFocus();
                                }
                            }
                        }
                    });
                }
            } else {
                hasData = false;
                historyView.setEmptyView(emptyView);
            }

            super.onPostExecute(result);
        }

        private boolean equalsList(List<RecordEntity> newList, List<RecordEntity> oldList) {
            if (newList.size() != oldList.size()) {
                return false;
            } else if (!listToString(newList).equals(listToString(oldList))) {
                return false;
            } else {
                return true;
            }
        }

        private String listToString(List<RecordEntity> list) {
            String str = "";
            for (RecordEntity entity : list) {
                str += entity.toString();
            }
            return str;
        }
    }

    private Handler myHandler = new Handler();

    private boolean qiYiInit = true;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (KeyEvent.ACTION_DOWN == event.getAction()) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    if (recordAdapter != null && recordAdapter.getMode()) {
                        selectModeAndFocus(historyView, false);
                        return true;
                    }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/6/13
     * @Description 组装菜单栏
     */
    private void createMenuWindow() {
        List<CloundMenuWindow.MenuItemEntity> menus = new ArrayList<CloundMenuWindow.MenuItemEntity>();
        CloundMenuWindow.MenuItemEntity entity2 = new CloundMenuWindow.MenuItemEntity();
        entity2.setItemName(getString(R.string.select_delete));
        entity2.setItemPosition(0);
        entity2.setItemIconResId(R.drawable.menu_delete);
        entity2.setItemIconFocusResId(R.drawable.menu_delete);
        menus.add(entity2);
        entity2 = new CloundMenuWindow.MenuItemEntity();
        entity2.setItemName(getString(R.string.all_delete));
        entity2.setItemPosition(1);
        entity2.setItemIconResId(R.drawable.trash);
        entity2.setItemIconFocusResId(R.drawable.trash);
        menus.add(entity2);
        entity2 = new CloundMenuWindow.MenuItemEntity();
        entity2.setItemName(getString(R.string.my_collect));
        entity2.setItemPosition(2);
        entity2.setItemIconResId(R.drawable.ic_menu_category_collect_nomal);
        entity2.setItemIconFocusResId(R.drawable.ic_menu_category_collect_focus);
        menus.add(entity2);
        mWindow = new CloundMenuWindow(RecordActivity.this, menus);
        mWindow.setItemSelectedListener(itemSelected);
    }

    private Bitmap blurBitmap;
    private CloundMenuWindow.OnSelectedItemClickListener itemSelected = new CloundMenuWindow.OnSelectedItemClickListener() {

        @Override
        public void selectedItemClick(CloundMenuWindow.MenuItemEntity entity) {
            if (entity.getItemName().equals(getResources().getString(R.string.all_delete))) {
                if (hasData) {
                    if (null == deleteWindow) {
                        mainView.destroyDrawingCache();
                        mainView.buildDrawingCache();
                        Bitmap bitmap = mainView.getDrawingCache();
                        if (bitmap != null) {
                            Matrix matrix = new Matrix();
                            matrix.postScale(0.3f, 0.3f);
                            Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, mainView.getMeasuredWidth(), mainView.getMeasuredHeight(), matrix,
                                    true);
                            blurBitmap = BitmapBlurUtil.getInstance().blurBitmap(scaledBitmap, RecordActivity.this);
                            deleteWindow = new DeleteWindow(RecordActivity.this, blurBitmap, callBack, DeleteWindow.DELETE_HISTORY,
                                    getString(R.string.delete_window_tip_history), sizeConstant);
                        } else {
                            deleteWindow = new DeleteWindow(RecordActivity.this, null, callBack, DeleteWindow.DELETE_HISTORY,
                                    getString(R.string.delete_window_tip_history), sizeConstant);
                        }
                    }
                    deleteWindow.showWindow();
                } else {
                    ToastUtil.showToast(getApplicationContext(), getString(R.string.collect_activity_all_data_deleted), Toast.LENGTH_SHORT);
                }
            } else if (entity.getItemName().equals(getResources().getString(R.string.my_collect))) {
                startActivity(new Intent(Invoker.ACTION_START_COLLECT));
            } else if (entity.getItemName().equals(getResources().getString(R.string.select_delete))) {
                if (hasData) {
                    selectModeAndFocus(historyView, true);
                } else {
                    ToastUtil.showToast(getApplicationContext(), getString(R.string.collect_activity_all_data_deleted), Toast.LENGTH_SHORT);
                }
            }
        }

    };

    /**
     * @param historyView 界面historyView
     * @param mode        true-删除模式，false-正常模式
     * @Title:
     * @author:yupengtong
     * @Description: 处理删除模式与正常模式的切换与焦点逻辑
     */
    private void selectModeAndFocus(final HistoryView historyView, boolean mode) {

        recordAdapter.setMode(mode);
        historyView.post(new Runnable() {

            @SuppressLint("NewApi")
            @Override
            public void run() {
                if (historyView.getChildAt(1) != null) {
                    historyView.getChildAt(1).requestFocus();
                }
            }
        });
    }

    /**
     * @return index
     * @Title:
     * @author:yupengtong
     * @Description: 获取某个view在父view中child的index, 暂时弃用
     */
    private int getFocusViewIndex(ViewGroup parent, View child) {
        int position = 1;
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (historyView.getChildAt(i).equals(child)) {
                position = i;
            }
        }
        return position;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //回收自定义跑马灯动画，防止内存泄露
        if (null != getCurrentFocus() && getCurrentFocus().getTag() instanceof RecordUpAdapter.VideoRecordHolder) {
            Logger.d(TAG, "history_name.recycle");
            ((RecordUpAdapter.VideoRecordHolder) getCurrentFocus().getTag()).history_name.recycle();
        }
        isFirstStart = true;
        firstAsy = true;
        if (null != blurBitmap) {
            if (!blurBitmap.isRecycled()) {
                blurBitmap.recycle();
            }
            blurBitmap = null;
        }
    }

    ;

    private RecordAdapter.CollectCallBack callBack = new RecordAdapter.CollectCallBack() {

        @Override
        public boolean deleteAll(boolean isDeleteAll) {
            hasData = false;
            historyView.setAdapter(null);
            historyView.setEmptyView(emptyView);
            return true;
        }

        @Override
        public void delete(Object obj, int div) {

        }

        @Override
        public void animation(int div) {

        }
    };

    private RecordAdapter.ClickToActivity clickBack = new RecordAdapter.ClickToActivity() {

        @Override
        public void qiyi(VideoRecordEntity entity) {
            int showType = CloudScreenApplication.getInstance().getShowTypeByVideoTypeId(entity.getVideoset_type());
            String action = VideoDetailInvoker.getInstance().getDetailActivityAction(entity.getVideoset_type());
            Intent intent = new Intent(action);
            AlbumEntity albumEntity = new AlbumEntity();
            switch (showType) {
                case ContentShowType.TYPE_MULTIPLE_VIDEO_DETAIL:
                case ContentShowType.TYPE_SINGLE_VIDEO_DETAIL:
                case ContentShowType.TYPE_VARIETY_VIDEO_DETAIL:
                    if (null == blurBitmap) {
                        mainView.destroyDrawingCache();
                        mainView.buildDrawingCache();
                        Bitmap bitmap = mainView.getDrawingCache();
                        if (bitmap != null) {
                            Matrix matrix = new Matrix();
                            matrix.postScale(0.3f, 0.3f);
                            Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, mainView.getMeasuredWidth(), mainView.getMeasuredHeight(), matrix,
                                    true);
                            CloudScreenApplication.getInstance().blurBitmap = BitmapBlurUtil.getInstance().blurBitmap(scaledBitmap, RecordActivity.this);
                        }
                    } else {
                        CloudScreenApplication.getInstance().blurBitmap = blurBitmap;
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra(AppConstants.EXTRA_VIDEOSET_ID, entity.getVideoset_id());
                    intent.putExtra(AppConstants.EXTRA_CID, entity.getVideoset_type());
                    intent.putExtra(AppConstants.EXTRA_SOURCE, "4");
                    break;
                case ContentShowType.TYPE_SINGLE_VIDEO_NO_DETAIL:
                    intent.putExtra(AppConstants.EXTRA_IS_FROM_ONLY_ID, true);
                    intent.putExtra(AppConstants.EXTRA_VIDEOSET_ID, entity.getVideoset_id() + "");
                    intent.putExtra(AppConstants.EXTRA_SOURCE, "4");
                    break;
            }
            RecordActivity.this.startActivity(intent);
        }

        @Override
        public void appGame(AppStoreEntity entity) {
            try {
                Intent intent = RecordActivity.this.getPackageManager().getLaunchIntentForPackage(entity.getPackageName().trim());// 防止包名前后多了空格
                RecordActivity.this.startActivity(intent);
                sendBroadCastToAppStoreForRecord(entity.getPackageName().trim());//发送应用打开广播更新应用数据库
            } catch (Exception e) {
                ToastUtil.showToast(getApplicationContext(), getString(R.string.recordactivity_app_starterror), Toast.LENGTH_SHORT);
            }

        }

        @Override
        public void delete() {
            if (null == historyView || null == historyView.getFocusedChild() || null == historyView.getFocusedChild().getTag()) {
                Log.d(TAG, "null == historyView || null == historyView.getFocusedChild() || null == historyView.getFocusedChild().getTag()");
                return;
            }
            final int position = ((RecordAdapter.RecordHolder) (historyView.getFocusedChild().getTag())).position;
            // 删除后让原焦点行获取焦点
            historyView.post(new Runnable() {

                @SuppressLint("NewApi")
                @Override
                public void run() {
                    if (null != historyView.getChildAt(1)) {
                        if (null != historyView.getChildAt(position)) {// 原焦点行行存在
                            historyView.getChildAt(position).requestFocus();
                        } else {// 原焦点行已经被删掉且不存在下一行的情况焦点移动到上一行
                            historyView.getChildAt(position - 1).requestFocus();
                        }
                        // historyView.animate().translationYBy(-historyView.getChildAt(1).getTranslationY()).setDuration(300)
                        // .setInterpolator(new DecelerateInterpolator());
                    } else {// 没有child（1）说明内容已经全部被删掉了
                        if (recordAdapter.getMode()) {
                            recordAdapter.setMode(false);
                        }
                        hasData = false;
                        historyView.setAdapter(null);
                        historyView.setEmptyView(emptyView);
                    }
                }
            });

        }

    };

    private void sendBroadCastToAppStoreForRecord(String packageName) {

        Intent intent = new Intent(Invoker.OPEN_APP_RECORDER);
        intent.putExtra("packageName", packageName);
        intent.putExtra("startupSource", "historiesRecord");
        sendBroadcast(intent);
        Log.d(TAG, "sendBroadCastToAppStoreForRecord: " + packageName);
    }

}
