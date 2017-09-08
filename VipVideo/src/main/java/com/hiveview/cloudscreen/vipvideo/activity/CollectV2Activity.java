package com.hiveview.cloudscreen.vipvideo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v17.leanback.widget.VerticalGridView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.activity.adapter.CollectV2Adapter;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.ContentShowType;
import com.hiveview.cloudscreen.vipvideo.common.Invoker;
import com.hiveview.cloudscreen.vipvideo.common.SizeConstant;
import com.hiveview.cloudscreen.vipvideo.common.VideoDetailInvoker;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;
import com.hiveview.cloudscreen.vipvideo.service.entity.CollectEntity;
import com.hiveview.cloudscreen.vipvideo.util.BitmapBlurUtil;
import com.hiveview.cloudscreen.vipvideo.util.CollectDataUtil;
import com.hiveview.cloudscreen.vipvideo.util.Logger;
import com.hiveview.cloudscreen.vipvideo.util.ToastUtil;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;
import com.hiveview.cloudscreen.vipvideo.view.CloundMenuWindow;
import com.hiveview.cloudscreen.vipvideo.view.DeleteWindow;
import com.hiveview.cloudscreen.vipvideo.view.NoResultView;
import com.hiveview.cloudscreen.vipvideo.view.TypeFaceTextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/5/18
 * @Description 收藏页面
 */
public class CollectV2Activity extends BaseActivity implements UserStateUtil.UserStatusWatcher {
    private static final String TAG = CollectV2Activity.class.getSimpleName();
    /**
     * 页面头布局
     */
    private RelativeLayout collectTitle;
    /**
     * 顶部显示收藏*部影片的文本框
     */
    private TypeFaceTextView txtTip;
    /**
     * 顶部显示用户名的文本框
     */
    private TypeFaceTextView txtUser;
    /**
     * 用户名前的标记
     */
    private ImageView userIcon;
    /**
     * 页面的整体布局
     */
    private RelativeLayout collectMain;
    /**
     * 收藏影视列表
     */
    private VerticalGridView collectList;
    /**
     * 无收藏占位图
     */
    private NoResultView noResultView;
    /**
     * 收藏列表adapter
     */
    private CollectV2Adapter adapter;
    /**
     * 是否有数据
     */
    private boolean hasData;
    /**
     * 全部删除的提醒框
     */
    private DeleteWindow deleteWindow;
    /**
     * 用与存放模糊图
     */
    private Bitmap blurBitmap;
    /**
     * 尺寸工具
     */
    private SizeConstant sizeConstant;

    private boolean isFirstStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_layout);
        //初始化尺寸工具
        sizeConstant = new SizeConstant(this);
        UserStateUtil.getInstance().addWatcher(this);
        //初始化用户信息
        UserStateUtil.getInstance().notifyReFreshUserState(getApplicationContext());
        //加载布局
        init();
        //设置列表属性
        collectList.setNumColumns(6);
        collectList.setColumnWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        collectList.setHorizontalSpacing((int) getResources().getDimension(R.dimen.collect_grid_item_margin));
        collectList.setVerticalSpacing((int) getResources().getDimension(R.dimen.collect_grid_item_margin));

        //构造菜单栏
        createMenu();
        //加载数据并同步
        if (!TextUtils.isEmpty(UserStateUtil.getInstance().getUserInfo().userId)) {
            CollectDataUtil.getInstance().getCollectData(new OnDataListener(this));
        } else {
            //如果不存在用户名，就只获取收藏数据，不进行同步操作
            CollectDataUtil.getInstance().notifyCollectDataChanged(new OnDataListener(this));
        }
        //标记第一次打开
        isFirstStart = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //设置用户名
        txtUser.setText(UserStateUtil.getInstance().getUserInfo().userAccount);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //刷新本地数据
        if (isFirstStart) {//首次打开数据已经加载过无需重复加载
            isFirstStart = false;
        } else {
            CollectDataUtil.getInstance().notifyCollectDataChanged(null);
        }
    }


    @Override
    protected void onDestroy() {
        UserStateUtil.getInstance().removeWatcher(this);
        CollectDataUtil.getInstance().asynCollectData();
        if (null != collectList.getFocusedChild()) {
            //回收影片名称跑马灯动画
            ((CollectV2Adapter.CollectViewHolder) collectList.getChildViewHolder(collectList.getFocusedChild())).item_text2.recycle();
        }
        super.onDestroy();
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/19
     * @Description 初始化菜单栏
     */
    private void createMenu() {
        List<CloundMenuWindow.MenuItemEntity> menus = new ArrayList<CloundMenuWindow.MenuItemEntity>();
        CloundMenuWindow.MenuItemEntity entity = new CloundMenuWindow.MenuItemEntity();
        entity.setItemName(getString(R.string.select_delete));
        entity.setItemPosition(0);
        entity.setItemIconResId(R.drawable.menu_delete);
        entity.setItemIconFocusResId(R.drawable.menu_delete);
        CloundMenuWindow.MenuItemEntity entity2 = new CloundMenuWindow.MenuItemEntity();
        entity2.setItemName(getString(R.string.all_delete));
        entity2.setItemPosition(1);
        entity2.setItemIconResId(R.drawable.trash);
        entity2.setItemIconFocusResId(R.drawable.trash);
        CloundMenuWindow.MenuItemEntity entity3 = new CloundMenuWindow.MenuItemEntity();
        entity3.setItemName(getString(R.string.history_record));
        entity3.setItemPosition(2);
        entity3.setItemIconResId(R.drawable.ic_menu_category_history_nomal);
        entity3.setItemIconFocusResId(R.drawable.ic_menu_category_history_focus);
        menus.add(entity);
        menus.add(entity2);
        menus.add(entity3);
        mWindow = new CloundMenuWindow(this, menus);
        mWindow.setItemSelectedListener(itemSelected);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/18
     * @Description 加载布局
     */
    private void init() {
        collectTitle = (RelativeLayout) findViewById(R.id.collect_title);
        collectMain = (RelativeLayout) findViewById(R.id.collect_mian);
        txtTip = (TypeFaceTextView) findViewById(R.id.txt_collect_tip);
        txtUser = (TypeFaceTextView) findViewById(R.id.collect_user);
        userIcon = (ImageView) findViewById(R.id.collect_user_image);
        userIcon.setImageResource(ResourceProvider.getInstance().getIcCollectUser());
        collectList = (VerticalGridView) findViewById(R.id.vgv_collect_list);
        noResultView = (NoResultView) findViewById(R.id.empty_title);
        //加载空白页内容
        noResultView.setData(R.drawable.collect_color, getString(R.string.collect_title), getString(R.string.no_collection_of_records));
    }


    /**
     * @Author Spr_ypt
     * @Date 2016/5/19
     * @Description 设置菜单栏
     */
    private CloundMenuWindow.OnSelectedItemClickListener itemSelected = new CloundMenuWindow.OnSelectedItemClickListener() {

        @Override
        public void selectedItemClick(CloundMenuWindow.MenuItemEntity entity) {
            if (entity.getItemName().equals(
                    getResources().getString(R.string.select_delete))) {
                if (hasData) {//有内容删除时才进入删除模式
                    adapter.setMode(true);
                } else {
                    ToastUtil.showToast(getApplicationContext(), getString(R.string.collect_activity_all_data_deleted), Toast.LENGTH_SHORT);
                }
            } else if (entity.getItemName().equals(getResources().getString(R.string.all_delete))) {
                if (hasData) {
                    if (null == deleteWindow) {
                        collectMain.destroyDrawingCache();
                        collectMain.buildDrawingCache();
                        Bitmap bitmap = collectMain.getDrawingCache();
                        if (bitmap != null) {
                            Matrix matrix = new Matrix();
                            matrix.postScale(0.3f, 0.3f);
                            Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                                    collectMain.getMeasuredWidth(),
                                    collectMain.getMeasuredHeight(), matrix, true);
                            blurBitmap = BitmapBlurUtil.getInstance().blurBitmap(scaledBitmap, getApplicationContext());
                            deleteWindow = new DeleteWindow(CollectV2Activity.this, blurBitmap, new OnItemActionListener(CollectV2Activity.this), DeleteWindow.DELETE_FAVOUR, getString(R.string.delete_window_tip), sizeConstant);
                        } else {
                            deleteWindow = new DeleteWindow(CollectV2Activity.this, null, new OnItemActionListener(CollectV2Activity.this), DeleteWindow.DELETE_FAVOUR, getString(R.string.delete_window_tip), sizeConstant);
                        }
                        collectMain.destroyDrawingCache();
                    }
                    deleteWindow.showWindow();
                } else {
                    ToastUtil.showToast(getApplicationContext(), getString(R.string.collect_activity_all_data_deleted), Toast.LENGTH_SHORT);
                }
            } else if (entity.getItemName().equals(getResources().getString(R.string.history_record))) {
                startActivity(new Intent(Invoker.ACTION_START_RECORD));
            }
        }
    };

    @Override
    public void userStatusChanged() {
        CollectDataUtil.getInstance().getCollectData(new OnDataListener(this));
        txtUser.setText(UserStateUtil.getInstance().getUserInfo().userAccount);
    }

    @Override
    public void userStatusNoChanged() {

    }

    /**
     * @Author Spr_ypt
     * @Date 2016/6/8
     * @Description 数据加载监听
     */
    private static class OnDataListener implements CollectDataUtil.OnCollectDataListener {

        private WeakReference<CollectV2Activity> reference;

        public OnDataListener(CollectV2Activity activity) {
            reference = new WeakReference<CollectV2Activity>(activity);
        }

        @Override
        public void onLocalData(List<CollectEntity> data) {
            resetData(data);
        }


        @Override
        public void onSynData(List<CollectEntity> data) {
            CollectV2Activity activity = reference.get();
            ToastUtil.showToast(activity, "发现您有云收藏信息，已为您同步~", Toast.LENGTH_SHORT);
            resetData(data);
        }

        /**
         * @Author Spr_ypt
         * @Date 2016/5/20
         * @Description 载入数据
         */
        private void resetData(List<CollectEntity> data) {
            CollectV2Activity activity = reference.get();
            Logger.d(activity.TAG, "resetData data.size=" + data.size());
            activity.txtTip.setText(String.format(activity.getString(R.string.total_film), "" + (data.size())));
            activity.txtUser.setText(UserStateUtil.getInstance().getUserInfo().userAccount);
            // 加载数据
            if (data.size() <= 0) {// 收藏无数据
                activity.hasData = false;
                activity.noResultView.setVisibility(View.VISIBLE);
                activity.collectTitle.setVisibility(View.INVISIBLE);
                activity.collectList.setAlpha(0f);
                activity.collectList.setVisibility(View.INVISIBLE);
            } else {
                activity.hasData = true;
                activity.noResultView.setVisibility(View.GONE);
                activity.collectTitle.setVisibility(View.VISIBLE);
                activity.collectList.setVisibility(View.VISIBLE);
                activity.adapter = new CollectV2Adapter(activity, new OnItemActionListener(activity));
                activity.adapter.setData(data);
                activity.collectList.setAdapter(activity.adapter);
                activity.collectList.animate().alpha(1f).setDuration(500).start();//这里的渐变效果是为了遮挡控件初始化偶尔会出现的item重叠现象
            }
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/20
     * @Description 对item进行操作的监听
     */
    private static class OnItemActionListener implements CollectV2Adapter.OnActionListener {
        private WeakReference<CollectV2Activity> reference;

        public OnItemActionListener(CollectV2Activity activity) {
            reference = new WeakReference<CollectV2Activity>(activity);
        }

        @Override
        public void openDetail(CollectEntity entity) {
            CollectV2Activity activity = reference.get();
            if (null != activity) {
                if (null != CloudScreenApplication.getInstance().blurBitmap) {
                    CloudScreenApplication.getInstance().blurBitmap.recycle();
                    CloudScreenApplication.getInstance().blurBitmap = null;
                }
                activity.collectMain.destroyDrawingCache();
                activity.collectMain.buildDrawingCache();
                Bitmap bitmap = activity.collectMain.getDrawingCache();
                if (bitmap != null) {
                    Matrix matrix = new Matrix();
                    matrix.postScale(0.3f, 0.3f);
                    Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, activity.collectMain.getMeasuredWidth(), activity.collectMain.getMeasuredHeight(),
                            matrix, true);
                    CloudScreenApplication.getInstance().blurBitmap = BitmapBlurUtil.getInstance().blurBitmap(scaledBitmap, activity);
                }
                activity.collectMain.destroyDrawingCache();

                String action = VideoDetailInvoker.getInstance().getDetailActivityAction(entity.getCid());
                int showType = CloudScreenApplication.getInstance().getShowTypeByVideoTypeId(entity.getCid());
                Intent detail = new Intent(action);
                if (showType == ContentShowType.TYPE_SINGLE_VIDEO_NO_DETAIL) {
                    Toast.makeText(activity.getApplicationContext(), R.string.collect_abnormal_data_collection, Toast.LENGTH_SHORT).show();
                } else {
                    detail.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    detail.putExtra(AppConstants.EXTRA_VIDEOSET_ID, entity.getCollectId());
                    detail.putExtra(AppConstants.EXTRA_CID, entity.getCid());
                    detail.putExtra(AppConstants.EXTRA_SOURCE, "3");
                    activity.startActivity(detail);

                }
            }
        }

        @Override
        public void removeItem(CollectEntity entity) {
            CollectV2Activity activity = reference.get();
            if (null != activity && null != activity.adapter) {
                activity.txtTip.setText(String.format(activity.getString(R.string.total_film), "" + (activity.adapter.getItemCount())));
            }
            CollectDataUtil.getInstance().deleteCollect(entity.getCollectId());
        }

        @Override
        public void removeAll() {
            CollectV2Activity activity = reference.get();
            if (null != activity) {
                activity.noResultView.setVisibility(View.VISIBLE);
                activity.collectTitle.setVisibility(View.INVISIBLE);
                activity.collectList.setVisibility(View.INVISIBLE);
                activity.hasData = false;
            }
            CollectDataUtil.getInstance().deleteCollect(null);
            CollectDataUtil.getInstance().clearLocalData();
        }

        @Override
        public void needScrollToEnd() {
            CollectV2Activity activity = reference.get();
            if (null != activity && null != activity.adapter) {
                activity.collectList.setSelectedPositionSmooth(activity.adapter.getItemCount() - 1);
            }
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (KeyEvent.ACTION_DOWN == event.getAction()) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    if (hasData && adapter.getMode()) {
                        adapter.setMode(false);
                        return true;
                    }
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }

}
