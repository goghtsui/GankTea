/**
 * @Title SubjectListActivity.java
 * @Package com.hiveview.cloudscreen.video.activity
 * @author haozening
 * @date 2014年9月5日 下午12:52:05
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.activity;

import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
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
import com.hiveview.cloudscreen.vipvideo.activity.adapter.SubjectListAdapter;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.service.CloudScreenService;
import com.hiveview.cloudscreen.vipvideo.service.OnRequestResultListener;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.SubjectEntity;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.Statistics;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.ListBrowseActionInfo;
import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.util.FeedbackTranslationAnimatorUtil;
import com.hiveview.cloudscreen.vipvideo.view.LoadingView;
import com.hiveview.cloudscreen.vipvideo.view.TypeFaceTextView;
import com.hiveview.cloudscreen.vipvideo.view.pageItemView.SubjectPageItemView;
import com.hiveview.cloudscreen.vipvideo.view.verticalViewPager.VerticalPagerAdapter;
import com.hiveview.cloudscreen.vipvideo.view.verticalViewPager.VerticalViewPager;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author haozening
 * @ClassName SubjectListActivity
 * @Description 专题列表
 * @date 2014-9-8 上午10:25:56
 */
@SuppressLint("NewApi")
public class SubjectListV2Activity extends BaseActivity implements SubjectPageItemView.OnItemKeyListener, VerticalViewPager.OnPageChangeListener, SubjectListAdapter.OnActionListener {

    public static final String TAG = SubjectListV2Activity.class.getSimpleName();
    /**
     * 是否正在加载
     */
    private boolean isLoading = false;
    private VerticalViewPager gridListView;
    private SubjectListAdapter adapter;
    private int currentPage = 1;
    private TextView titleView;
    private TextView countView;
    private int firstClass;
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
        setContentView(R.layout.activity_subject_list);
        container = findViewById(R.id.container);
        // 降低背景图色位深度
//        container.setBackground(new BitmapDrawable(getResources(), DisplayImageUtil.getInstance().decodeResource(getResources(), R.drawable.bg_list)));
        firstClass = getIntent().getIntExtra(AppConstants.LIST_CHANNEL_ID, 0);
        headerContainer = (FrameLayout) findViewById(R.id.fl_header);
        headerView = LayoutInflater.from(this).inflate(R.layout.subject_list_header_layout, null);

        titleView = (TypeFaceTextView) headerView.findViewById(R.id.subject_list_title);
        countView = (TypeFaceTextView) headerView.findViewById(R.id.subject_list_title2);
        pageCountView = (TextView) headerView.findViewById(R.id.tv_page);

        headerContainer.addView(headerView);

        loadingView = (LoadingView) findViewById(R.id.lv_onload);
        filterMask = findViewById(R.id.view_filter_mask);
        startLoading();
        isLoading = true;
        gridListView = (VerticalViewPager) findViewById(R.id.subject_list);
        gridListView.setOnPageChangeListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            feedbackAnimator = FeedbackTranslationAnimatorUtil.getInstance().getAnimationSet(gridListView, FeedbackTranslationAnimatorUtil.Orientation.VERTICAL, -50f);
        }
        int templetId = DeviceInfoUtil.getInstance().getDeviceInfo(this).templetId;
        CloudScreenService.getInstance().getSubjectListInfo(new SubjectListListener(this), templetId, this.getPackageName(), 1, 100);
    }


    private static class SubjectListListener implements OnRequestResultListener<ResultEntity> {

        private WeakReference<SubjectListV2Activity> reference;

        public SubjectListListener(SubjectListV2Activity activity) {
            reference = new WeakReference<SubjectListV2Activity>(activity);
        }

        @Override
        public void onSucess(ResultEntity resultEntity) {
            List<SubjectEntity> subListEntity = resultEntity.getList();
            if (subListEntity != null) {
                SubjectListV2Activity activity = reference.get();
                if (null != activity) {
                    activity.finishLoading();
                    activity.isLoading = false;
                    activity.pageCount = (int) Math.ceil((double) subListEntity.size() / 6);
                    activity.pageCountView.setText(String.format(activity.getResources().getString(R.string.current_page), 1+"", activity.pageCount+""));
                    activity.adapter = new SubjectListAdapter(activity, activity);
                    activity.adapter.addData(subListEntity);
                    activity.adapter.setOnActionListener(activity);
                    activity.titleView.getPaint().setFakeBoldText(true);
                    activity.countView.setText(String.format(activity.getString(R.string.total_special), "" + subListEntity.size()));
                    activity.gridListView.setAdapter(activity.adapter);
                }
            }
        }

        @Override
        public void onFail(Exception e) {
            SubjectListV2Activity activity = reference.get();
            if (null != activity) {
                activity.finishLoading();
                activity.isLoading = false;
                Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.load_data_failed_please_try_again), Toast.LENGTH_SHORT)
                        .show();
                activity = null;
            }
        }

        @Override
        public void onParseFail(HiveviewException e) {
            SubjectListV2Activity activity = reference.get();
            if (null != activity) {
                activity.finishLoading();
                activity.isLoading = false;
                Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.load_data_failed_please_try_again), Toast.LENGTH_SHORT)
                        .show();
                activity = null;
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
        private WeakReference<SubjectListV2Activity> activityRef;

        public LoadingHandler(SubjectListV2Activity activity) {
            activityRef = new WeakReference<SubjectListV2Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NEED_LOADING_VIEW:
                    SubjectListV2Activity activity = activityRef.get();
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
    @SuppressLint("NewApi")
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
    public static class AppendSubjectListener implements OnRequestResultListener<ResultEntity> {

        private WeakReference<SubjectListV2Activity> reference;

        public AppendSubjectListener(SubjectListV2Activity activity) {
            reference = new WeakReference<SubjectListV2Activity>(activity);
        }

        @Override
        public void onSucess(ResultEntity resultEntity) {
            SubjectListV2Activity activity = reference.get();
            if (null != activity) {
                activity.finishLoading();
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
                        SubjectPageItemView view = (SubjectPageItemView) adapter.getPrimaryItem();
                        if (view.secondRowView().findFocus() != null) {
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
    protected void onRestart() {
        super.onRestart();
        //刷新焦点框颜色
        if (null != adapter && null != adapter.getPrimaryItem()) {
            ((SubjectPageItemView) adapter.getPrimaryItem()).resetUserColor();
        }
    }

    @Override
    protected void onStart() {
        openTime = System.currentTimeMillis();
        super.onStart();
    }

    @Override
    protected void onStop() {
        BaseActionInfo info = new ListBrowseActionInfo("03", "vip03016", noAction, firstClass + "", Long.toString(System.currentTimeMillis() - openTime));//统计埋点
        new Statistics(this, info).send();//统计埋点
        super.onStop();
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

    }

    @Override
    public void onPageSelected(int position) {
        //刷新焦点框颜色
        if (null != adapter && null != adapter.getPrimaryItem()) {
            ((SubjectPageItemView) adapter.getPrimaryItem()).resetUserColor();
        }
        //刷新页数
        pageCountView.setText(String.format(getResources().getString(R.string.current_page), (position + 1)+"", pageCount+""));
        int lastPage = adapter.getCount() - 1;
        if (position == lastPage - 1) {
            isLoading = true;
            CloudScreenService.getInstance().getSubjectListInfo(new AppendSubjectListener(this), DeviceInfoUtil.getInstance().getDeviceInfo(this).templetId, this.getPackageName(), PAGE_SIZE, ++currentPage);
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
        SubjectPageItemView currentView = (SubjectPageItemView) adapter.getPrimaryItem();
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
                if (index == 5) {
                    if (adapter.getCount() - 1 > gridListView.getCurrentItem()) {
                        gridListView.setCurrentItem(gridListView.getCurrentItem() + 1, true);
                        return true;
                    }
                }
                break;
        }

        return false;
    }


    /**
     * @Author Spr_ypt
     * @Date 2015/12/8
     * @Description SubjectListAdapter.OnActionListener回调接口
     */
    @Override
    public void effectiveAction() {
        noAction = "0";
    }


}
