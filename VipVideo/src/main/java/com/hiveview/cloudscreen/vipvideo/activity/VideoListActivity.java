/**
 * @Title FourColumnListActivity.java
 * @Package com.hiveview.cloudscreen.video.activity
 * @author haozening
 * @date 2014年9月10日 下午8:42:22
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.activity;

import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.activity.adapter.VideoListAdapter;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.Invoker;
import com.hiveview.cloudscreen.vipvideo.service.CloudScreenService;
import com.hiveview.cloudscreen.vipvideo.service.OnRequestResultListener;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.Statistics;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.ListBrowseActionInfo;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.util.FeedbackTranslationAnimatorUtil;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;
import com.hiveview.cloudscreen.vipvideo.view.CloundMenuWindow;
import com.hiveview.cloudscreen.vipvideo.view.LoadingView;
import com.hiveview.cloudscreen.vipvideo.view.pageItemView.VideoPageItemView;
import com.hiveview.cloudscreen.vipvideo.view.verticalViewPager.VerticalPagerAdapter;
import com.hiveview.cloudscreen.vipvideo.view.verticalViewPager.VerticalViewPager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * @author haozening
 * @ClassName FourColumnListActivity
 * @Description
 * @date 2014年9月10日 下午8:42:22
 */
@SuppressLint("NewApi")
public class VideoListActivity extends BaseActivity implements VideoPageItemView.OnItemKeyListener, VerticalViewPager.OnPageChangeListener, VideoListAdapter.OnActionListener, UserStateUtil.UserStatusWatcher {

    public static final String TAG = "FilmGridActivity";
    /**
     * 是否正在加载
     */
    private boolean isLoading = false;
    private VerticalViewPager gridListView;
    private VideoListAdapter adapter;
    private int currentPage = 1;
    private TextView titleView;
    private TextView countView;
    private String title;
    private int channelId;
    private int channelType;
    private static final int PAGE_SIZE = 120;
    private LoadingView loadingView;// By Spr_ypt 加载框
    private View filterMask;// By Spr_ypt 蒙版
    private int loadingDelayeTime = 200;// loading画面出现延迟 By Spr_ypt
    private static final int NEED_LOADING_VIEW = 0x0001;// loading画面延时消息标示what
    // By Spr_ypt
    private AnimatorSet feedbackAnimator;
    private View headerView;
    private FrameLayout headerContainer;
    private View container;
    /**
     * 当前页数文本
     */
    private TextView pageCountView;
    /**
     * 用于记录总页数
     */
    private int pageCount;
    /**
     * 统计埋点
     * 页面跳出标识页面跳出标识
     * 1：直接跳出 0：做了其他操作
     */
    private String noAction = "1";
    /**
     * 统计埋点
     * 打开页面的时间，用于结束的时候计算持续时间
     */
    private long openTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        container = findViewById(R.id.container);
        // 降低背景图色位深度
//        container.setBackground(new BitmapDrawable(getResources(), DisplayImageUtil.getInstance().decodeResource(getResources(), R.drawable.bg_list)));
        int extraChannelId = getIntent().getIntExtra(AppConstants.LIST_CHANNEL_ID, -1);
        int extraChannelType = getIntent().getIntExtra(AppConstants.LIST_CHANNEL_TYPE, -1);
        title = getIntent().getStringExtra(AppConstants.LIST_TITLE);
        if (null == title) {
            title = "";
        }
        if (extraChannelId == -1 || extraChannelType == -1) {
            finish();
        } else {
            channelId = extraChannelId;
            channelType = extraChannelType;
        }

        headerContainer = (FrameLayout) findViewById(R.id.fl_header);
        headerView = LayoutInflater.from(this).inflate(R.layout.view_film_grid_header, null);
        gridListView = (VerticalViewPager) findViewById(R.id.grid_container);
        gridListView.setOnPageChangeListener(this);
        titleView = (TextView) headerView.findViewById(R.id.tv_title);
        countView = (TextView) headerView.findViewById(R.id.tv_film_count);
        pageCountView = (TextView) headerView.findViewById(R.id.tv_page);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            feedbackAnimator = FeedbackTranslationAnimatorUtil.getInstance().getAnimationSet(gridListView, FeedbackTranslationAnimatorUtil.Orientation.VERTICAL, -50f);
        }
        titleView.setText(title);
        headerContainer.addView(headerView);
        loadingView = (LoadingView) findViewById(R.id.lv_onload);
        filterMask = findViewById(R.id.view_filter_mask);
        startLoading();
        isLoading = true;
        CloudScreenService.getInstance().getFilm(new GetFilmListener(this), channelId, channelType, PAGE_SIZE, 1);
        // start by liulifeng 增加 单片无详情 的侧滑栏功能
        List<CloundMenuWindow.MenuItemEntity> menuItems = new ArrayList<CloundMenuWindow.MenuItemEntity>();
        CloundMenuWindow.MenuItemEntity menuCategoryFilter = null;

        menuCategoryFilter = new CloundMenuWindow.MenuItemEntity();
        menuCategoryFilter.setItemName(getString(R.string.my_collect));
        menuCategoryFilter.setItemPosition(101);
        menuCategoryFilter.setItemIconResId(R.drawable.ic_menu_category_collect_nomal);
        menuCategoryFilter.setItemIconFocusResId(R.drawable.ic_menu_category_collect_focus);
        menuItems.add(menuCategoryFilter);

        menuCategoryFilter = new CloundMenuWindow.MenuItemEntity();
        menuCategoryFilter.setItemName(getString(R.string.history_record));
        menuCategoryFilter.setItemPosition(102);
        menuCategoryFilter.setItemIconResId(R.drawable.ic_menu_category_history_nomal);
        menuCategoryFilter.setItemIconFocusResId(R.drawable.ic_menu_category_history_focus);
        menuItems.add(menuCategoryFilter);

        mWindow = new CloundMenuWindow(this, menuItems);
        mWindow.setSearchType(false);
        mWindow.setItemSelectedListener(new CloundMenuWindow.OnSelectedItemClickListener() {

            @Override
            public void selectedItemClick(CloundMenuWindow.MenuItemEntity entity) {
                if (entity.getItemPosition() == 101) {// 我的收藏
                    startActivity(new Intent(Invoker.ACTION_START_COLLECT));
                } else if (entity.getItemPosition() == 102) {// 历史记录
                    startActivity(new Intent(Invoker.ACTION_START_RECORD));
                }
            }
        });
        // end by liulifeng 2015-1-13 10:33:24
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/6/24
     * @Description UserStateUtil.UserStatusWatcher回调接口
     */
    @Override
    public void userStatusChanged() {
        if (null != adapter && null != adapter.getPrimaryItem()) {
            ((VideoPageItemView) adapter.getPrimaryItem()).resetUserColor();
        }
    }

    @Override
    public void userStatusNoChanged() {

    }


    /**
     * 获取电影列表
     *
     * @author haozening
     * @ClassName GetFilmListener
     * @Description
     * @date 2015年1月6日 下午3:03:01
     */
    public static class GetFilmListener implements OnRequestResultListener<ResultEntity> {

        private WeakReference<VideoListActivity> reference;

        public GetFilmListener(VideoListActivity activity) {
            reference = new WeakReference<VideoListActivity>(activity);
        }

        @Override
        public void onSucess(ResultEntity resultEntity) {
            VideoListActivity activity = reference.get();
            if (null != activity) {
                activity.finishLoading();
                Log.d(TAG, "onSuccess");
                activity.countView.setText(String.format(activity.getString(R.string.total_film), "" + resultEntity.arg1));
                activity.pageCount = (int) Math.ceil((double) resultEntity.arg1 / 12);
                activity.pageCountView.setText(String.format(activity.getResources().getString(R.string.current_page), 1 + "", activity.pageCount + ""));
                activity.adapter = new VideoListAdapter(activity, activity);
                activity.adapter.setData(resultEntity.getList());
                activity.adapter.setOnActionListener(activity);
                activity.gridListView.setAdapter(activity.adapter);
            }
        }

        @Override
        public void onFail(Exception e) {
            VideoListActivity activity = reference.get();
            if (null != activity) {
                activity.isLoading = false;
                activity.finishLoading();
                Toast.makeText(activity.getApplicationContext(), R.string.load_data_failed_please_try_again, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFail");
            }
        }

        @Override
        public void onParseFail(HiveviewException e) {
            VideoListActivity activity = reference.get();
            if (null != activity) {
                activity.isLoading = false;
                activity.finishLoading();
                Toast.makeText(activity.getApplicationContext(), R.string.load_data_failed_please_try_again, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onParseJsonFail");
            }
        }
    }

    /**
     * @Title: SixColumnListActivity
     * @author:yupengtong
     * @Description: 开始加载动画
     */
    private void startLoading() {
        loadingHandler.sendEmptyMessageDelayed(NEED_LOADING_VIEW, loadingDelayeTime);
    }

    static class LoadingHandler extends Handler {
        private WeakReference<VideoListActivity> activityRef;

        public LoadingHandler(VideoListActivity activity) {
            activityRef = new WeakReference<VideoListActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NEED_LOADING_VIEW:
                    VideoListActivity activity = activityRef.get();
                    if (null != activity) {
                        if (activity.isLoading) {
                            activity.loadingView.setVisibility(View.VISIBLE);
                            activity.filterMask.setAlpha(1);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 加载页面延时出现handler
     */
    Handler loadingHandler = new LoadingHandler(this);

    /**
     * @Title: SixColumnListActivity
     * @author:yupengtong
     * @Description: 结束加载
     */
    private void finishLoading() {
        loadingHandler.removeMessages(NEED_LOADING_VIEW);
        if (loadingView.getVisibility() == View.VISIBLE) {
            loadingView.setVisibility(View.GONE);
        }
        if (filterMask.getAlpha() != 0) {
            filterMask.animate().alpha(0).setDuration(600).start();
        }
    }

    /**
     * 追加数据用的监听器
     *
     * @author haozening
     * @ClassName AppendFilmListener
     * @Description
     * @date 2015年1月6日 下午3:07:19
     */
    public static class AppendFilmListener implements OnRequestResultListener<ResultEntity> {

        private WeakReference<VideoListActivity> reference;

        public AppendFilmListener(VideoListActivity activity) {
            reference = new WeakReference<VideoListActivity>(activity);
        }

        @Override
        public void onSucess(ResultEntity resultEntity) {
            VideoListActivity activity = reference.get();
            if (null != activity) {
                activity.adapter.setItemPosition(VerticalPagerAdapter.POSITION_UNCHANGED);
                activity.adapter.addData(resultEntity.getList());
            }
        }

        @Override
        public void onFail(Exception e) {

        }

        @Override
        public void onParseFail(HiveviewException e) {

        }
    }

    private int keyCode;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        keyCode = event.getKeyCode();
        if (KeyEvent.ACTION_DOWN == event.getAction()) {

            if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                if (null != gridListView && adapter != null) {
                    if (gridListView.getCurrentItem() == adapter.getCount() - 1) {
                        VideoPageItemView view = (VideoPageItemView) adapter.getPrimaryItem();
                        if (!view.hasNextDownView(view.getCurrentIndex())) {
                            if (null != feedbackAnimator && !feedbackAnimator.isRunning()) {
                                feedbackAnimator.start();
                            }
                        }
                    }
                }
            }

        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        if (null != loadingView) {
            loadingView.recycle();
        }
        FeedbackTranslationAnimatorUtil.getInstance().recycle();
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {//sdk版本低于4.4
            container.invalidate();
        }
    }

    @Override
    public void onPageSelected(int position) {
        //刷新焦点框颜色
        if (null != adapter && null != adapter.getPrimaryItem()) {
            ((VideoPageItemView) adapter.getPrimaryItem()).resetUserColor();
        }
        //设置页面页数
        pageCountView.setText(String.format(getResources().getString(R.string.current_page), (position + 1) + "", pageCount + ""));
        int lastPage = adapter.getCount() - 1;
        if (position == lastPage - 1) {
            isLoading = true;
            CloudScreenService.getInstance().getFilm(new AppendFilmListener(this), channelId, channelType, PAGE_SIZE, ++currentPage);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            DisplayImageUtil.getInstance().resume();
        } else {
            DisplayImageUtil.getInstance().pause();
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        VideoPageItemView currentView = (VideoPageItemView) adapter.getPrimaryItem();
        int index = 0;
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                index = currentView.getViewIndex(v);
                if (gridListView.getCurrentItem() == 0) {
                    if (index == 0) {
                        return true;
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                index = currentView.getViewIndex(v);
                if (index == 0) {
                    if (gridListView.getCurrentItem() > 0) {
                        gridListView.setCurrentItem(gridListView.getCurrentItem() - 1, true);
                        return true;
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                index = currentView.getViewIndex(v);
                if (index == 11) {
                    if (adapter.getCount() - 1 > gridListView.getCurrentItem()) {
                        gridListView.setCurrentItem(gridListView.getCurrentItem() + 1, true);
                        return true;
                    }
                }
                break;
        }

        return false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        UserStateUtil.getInstance().addWatcher(this);
        UserStateUtil.getInstance().notifyReFreshUserState(this);

    }

    @Override
    protected void onStart() {
        openTime = System.currentTimeMillis();
        super.onStart();
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/12/8
     * @Description VideoListAdapter.OnActionListener接口回调
     */
    @Override
    public void effectiveAction() {
        noAction = "0";
    }


    @Override
    protected void onStop() {
        UserStateUtil.getInstance().removeWatcher(this);
        BaseActionInfo info = new ListBrowseActionInfo("03", "vip03016", noAction, channelId + "", Long.toString(System.currentTimeMillis() - openTime));//统计埋点
        new Statistics(this, info).send();//统计埋点
        super.onStop();
    }

}
