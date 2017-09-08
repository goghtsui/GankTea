package com.hiveview.cloudscreen.vipvideo.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.activity.component.BaseFilmDetailEpisode;
import com.hiveview.cloudscreen.vipvideo.activity.component.TvDetailEpisode;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.Invoker;
import com.hiveview.cloudscreen.vipvideo.common.VideoDetailInvoker;
import com.hiveview.cloudscreen.vipvideo.fragment.DetailDescription;
import com.hiveview.cloudscreen.vipvideo.fragment.DetailRecomment;
import com.hiveview.cloudscreen.vipvideo.service.CloudScreenService;
import com.hiveview.cloudscreen.vipvideo.service.OnRequestResultListener;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.EpisodeListEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.FilmAuthenticationEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.OnDemandResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ProductInfoEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ServiceTimeEntity;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.Statistics;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.LoginDialogActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.VIPDialogActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.VIPFilmDetailActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.VIPFilmDetailBrowserInfo;
import com.hiveview.cloudscreen.vipvideo.util.AlarmServiceUtil;
import com.hiveview.cloudscreen.vipvideo.util.BitmapBlurUtil;
import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.util.Logger;
import com.hiveview.cloudscreen.vipvideo.util.StringUtils;
import com.hiveview.cloudscreen.vipvideo.util.ToastUtil;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;
import com.hiveview.cloudscreen.vipvideo.view.CloundMenuWindow;
import com.hiveview.cloudscreen.vipvideo.view.CloundMenuWindow.MenuItemEntity;
import com.hiveview.cloudscreen.vipvideo.view.LoadingView;
import com.hiveview.cloudscreen.vipvideo.view.VideoDetailHome;
import com.hiveview.cloudscreen.vipvideo.view.VipVideoDetailHeaderView;
import com.hiveview.cloudscreen.vipvideo.view.VipVideoDialog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class VipVideoDetailActivity extends BaseActivity implements
        UserStateUtil.UserStatusWatcher, View.OnClickListener, View.OnKeyListener, View.OnFocusChangeListener {
    /**
     * 产品信息实体
     */
    private ProductInfoEntity productInfoEntity;
    /**
     * 剧集信息实体
     */
    private EpisodeListEntity episodeListEntity;
    private int position = -1;
    private MyReceiver receiver;
    private static final String TAG = VipVideoDetailActivity.class.getSimpleName();
    private Handler handler = new AuthenticationHandler(this);
    private List<OnDemandResultEntity> dianboList;
    private static final int DELAY_REQUEST_DATA = 100;
    private int count = 0;//记录失败请求的次数，如果大于5次直接退出详情页面
    /**
     * 左侧详情描述
     */
    DetailDescription detailDescription;
    /**
     * 右侧相关推荐
     */
    DetailRecomment detailRecomment;

    /**
     * 根据生命周期设置不同操作。
     */
    private boolean isRestart = false;
    private boolean isResume = false;
    private boolean isOnCreateCalled = false;
    private boolean paused = false;
    /**
     * 保存模糊背景图
     */
    private ImageView blurBackgroundView;
    /**
     * 相关推荐提示语，根据相关推荐数据设置提示语显示隐藏。
     */
    private RelativeLayout recomment_tip;
    /**
     * 根据传入的变量设置背景图的高斯模糊
     */
    private boolean shouldShowOutsideBackground;
    /**
     * 数据加载过慢时的加载图
     */
    private LoadingView loadingView;
    /**
     * 专辑实体类
     */
    private AlbumEntity entity;
    /**
     * 操作Home键的广播
     */
    private HomeKeyReceiver keyReceiver = new HomeKeyReceiver(this);
    /**
     * 剧集加载是否完成的变量
     */
    public AtomicBoolean isEpisodeDataCompleted = new AtomicBoolean(false);

    public AtomicBoolean isRequestEpisodeData = new AtomicBoolean(false);
    /**
     * 详情页顶部的登陆、开通vip、显示用户名
     */
    private VipVideoDetailHeaderView headerView;
    /**
     * 详情页的主布局操作
     */
    private VideoDetailHome detailHome;

    /**
     * 剧集组件
     */
    private BaseFilmDetailEpisode episode;
    /**
     * 按钮事件的弹框
     */
    VipVideoDialog userStatusDialog;

    /**
     * 统计统计埋点
     * 进入的来源，按钮
     */
    private String source = "";
    /**
     * 统计埋点
     * 页面跳出标识页面跳出标识
     * 1：直接跳出 0：做了其他操作
     */
    public static boolean isAction = false;
    /**
     * 统计埋点
     * 推荐位点击传入的id
     */
    private int subjectId = 0;
    /**
     * 统计埋点
     * 推荐位点击传入的name
     */
    private String subjectName = "";
    /**
     * 统计埋点
     * 打开页面的时间，用于结束的时候计算持续时间
     */
    private long beforeTime;
    /**
     * 相关推荐接口传入参数
     */
    private int cid = -2;

    /**
     * 专辑id
     */
    private int videosetId;

    /**
     * 界面
     */
    private RelativeLayout mainView;

    /**
     * 标记加载是否已经完成，未完成则不响应按键避免异常情况
     */
    private boolean isInitComplete;

    private DeviceInfoUtil.DeviceInfo info;

    /**
     * 支付结果监听
     */
    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                try {
                    String json = intent.getStringExtra("payCashResult");
                    String code = (String) JSON.parseObject(json).get("code");
                    String packageName = (String) JSON.parseObject(json).get("packageName");
                    Log.d(TAG, "支付收到消息");
                    if ("com.hiveview.cloudscreen.vipvideo".equals(packageName)) {
                        if ("N000000".equals(code)) {
                            if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 1) {//支付成功
                                if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getBookingStartTime(), productInfoEntity.getLongTime(), productInfoEntity.getBookingEndTime())) {//预售支付成功
                                    Log.d(TAG, "预售支付成功！到期提醒");
                                    detailHome.showBuyedDiaLog("您购买的内容将在" + StringUtils.formatDate(productInfoEntity.getBookingEndTime()) + "上映，欢迎进入大麦影视观看。");

                                    Bundle bundle = new Bundle();
                                    bundle.putString("title", entity.getAlbumName());
                                    bundle.putString("content", "您购买的内容将在" + StringUtils.formatDate(productInfoEntity.getBookingEndTime()) + "正式放映,欢迎届时观看。");
                                    String url = "";
                                    if (!TextUtils.isEmpty(entity.getAlbumXqyPic())) {
                                        url = entity.getAlbumXqyPic();
                                    }
                                    String urlSource = Uri.parse(url).getQueryParameter("source");
                                    if (!TextUtils.isEmpty(urlSource)) {
                                        if ("Qiyi".equals(urlSource)) {
                                            url = DisplayImageUtil.createImgUrl(entity.getAlbumXqyPic(), true);
                                        }
                                    }
                                    bundle.putString("imgUrl", url);
                                    bundle.putInt("location", AlarmServiceUtil.LOCATION_TOP);
                                    bundle.putLong("time", StringUtils.getTimeMillis(productInfoEntity.getBookingEndTime()));
                                    //提醒有效时长
                                    bundle.putLong("timeEffective", StringUtils.getTimeMillis(productInfoEntity.getSaleEndTime()));

                                    try {
                                        AlarmServiceUtil.getInstant().remind(VipVideoDetailActivity.this, bundle);
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                    //设置支付成功之后标记
                                    productInfoEntity.setAuthenticationFlag(true);
                                } else if (StringUtils.currentTimeAfterOrderTime(productInfoEntity.getBookingEndTime())) {//非预售支付成功直接播放
                                    detailHome.openPlay(detailHome.clickBtnFlag);

                                    if (detailHome.clickFlag() == 3) {//选集支付成功播放
                                        Log.d(TAG, "选集支付成功播放");
                                        VipVideoDetailActivity.this.startPlay(VipVideoDetailActivity.this.episodeListEntity);
                                    }
                                    //设置支付成功之后标记
                                    productInfoEntity.setAuthenticationFlag(true);
                                } else {
                                    productInfoEntity.setAuthenticationFlag(false);
                                    Log.d(TAG, "预售支付异常！");
                                }
                            } else if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 0) {//销售期内支付成功
                                if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getSaleStartTime(), productInfoEntity.getLongTime(), productInfoEntity.getSaleEndTime())) {
                                    detailHome.openPlay(detailHome.clickBtnFlag);

                                    if (detailHome.clickFlag() == 3) {//选集支付成功播放
                                        Log.d(TAG, "选集支付成功播放");
                                        VipVideoDetailActivity.this.startPlay(VipVideoDetailActivity.this.episodeListEntity);
                                    }
                                    //设置支付成功之后标记
                                    productInfoEntity.setAuthenticationFlag(true);
                                }
                            }
                            Log.d(TAG, "非预售支付成功！");
                        } else {
                            //支付失败
                            productInfoEntity.setAuthenticationFlag(false);
                            Log.d(TAG, "支付失败！");
                        }
                    }
                } catch (Exception e) {
                    Log.d(TAG, "支付数据接收失败！");
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate------");
        // 设置全屏
        setContentView(R.layout.activiy_vipvideo_detail);
        info = DeviceInfoUtil.getInstance().getDeviceInfo(CloudScreenApplication.getInstance().getApplicationContext());
        AlarmServiceUtil.getInstant().bindService(this);
        beforeTime = System.currentTimeMillis();
        isOnCreateCalled = true;
        //设置加载标记位
        isInitComplete = false;
        //加载控件
        initView();
        //初始化菜单页面
        initMenu();
        //首次进入，需要刷新下，用户的状态，及时更新
        UserStateUtil.getInstance().notifyReFreshUserState(this);
        //接收传入的参数
        dealExtras(getIntent());
        //初始化页面
        createTab();
        //注册成为用户状态变化观察者
        UserStateUtil.getInstance().addWatcher(this);
        //给页面、控件添加监听事件
        setListener();
        //判断背景图片的显示与加载
        showBackground();
        //根据entity加载数据，当entity为空时，根据id请求数据，entity获取相关数据。
        requestData();
        //设置顶部监听
        setHearderLinstener();
        //注册home键广播
        IntentFilter homeKeyFilter = new IntentFilter();
        homeKeyFilter.addAction(HomeKeyReceiver.ACTION_HOME);
        registerReceiver(keyReceiver, homeKeyFilter);
        //注册支付是否成功广播监听
        registerPayMoneyReceiver();
    }

    /**
     * 注册支付是否成功广播监听
     */
    public void registerPayMoneyReceiver() {
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.hiveview.goodspay");
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Logger.d(TAG, "onNewIntent");
        super.onNewIntent(intent);
        //设置加载标记位
        isInitComplete = false;
        isOnCreateCalled = false;
        //接收传入的参数
        dealChangedExtras(intent);
        //重置2边数据
        resetComponeExtras();
        //根据entity加载数据，当entity为空时，根据id请求数据，entity获取相关数据。
        requestData();
        //如果剧集是开打的则先关闭
        if (null != episode && episode.isOpen()) {
            closeEpisode();
        }
        if (null != detailRecomment && detailRecomment.isRightOpen()) {
            closeRecomment();
        }
        if (null != detailRecomment && detailDescription.isLeftOpen()) {
            closeDescription();
        }
    }

    private void resetComponeExtras() {
        if (null != detailRecomment) {
            detailRecomment.resetData(videosetId, cid);
        }
        if (null != detailHome) {
            detailHome.setExtras(subjectId, subjectName, source);
            detailHome.productInfoEntity = null;
            this.productInfoEntity = null;
            detailHome.tv_watchTime.setVisibility(View.GONE);
            detailHome.filmPrice.setVisibility(View.GONE);
        }
    }

    /**
     * 加载布局
     */
    private void initView() {
        mainView = (RelativeLayout) findViewById(R.id.rl_all_detial);
        headerView = (VipVideoDetailHeaderView) findViewById(R.id.detail_header);
        loadingView = (LoadingView) findViewById(R.id.lv_onload);
        blurBackgroundView = (ImageView) findViewById(R.id.iv_blur_bg);
        recomment_tip = (RelativeLayout) findViewById(R.id.rl_right_tip);
    }

    /**
     * 初始化菜单，添加我的收藏，历史记录
     */
    private void initMenu() {
        List<CloundMenuWindow.MenuItemEntity> menuItems = new ArrayList<CloundMenuWindow.MenuItemEntity>();
        CloundMenuWindow.MenuItemEntity menuCategoryFilter;
        menuCategoryFilter = new CloundMenuWindow.MenuItemEntity();
        menuCategoryFilter.setItemName(getString(R.string.my_collect));
        menuCategoryFilter.setItemIconResId(R.drawable.ic_menu_category_collect_nomal);
        menuCategoryFilter.setItemIconFocusResId(R.drawable.ic_menu_category_collect_focus);
        menuItems.add(menuCategoryFilter);

        menuCategoryFilter = new MenuItemEntity();
        menuCategoryFilter.setItemName(getString(R.string.history_record));
        menuCategoryFilter.setItemIconResId(R.drawable.ic_menu_category_history_nomal);
        menuCategoryFilter.setItemIconFocusResId(R.drawable.ic_menu_category_history_focus);
        menuItems.add(menuCategoryFilter);

        mWindow = new CloundMenuWindow(this, menuItems);
        mWindow.setItemSelectedListener(new CloundMenuWindow.OnSelectedItemClickListener() {

            @Override
            public void selectedItemClick(CloundMenuWindow.MenuItemEntity entity) {
                if (entity.getItemPosition() == 0) { // 我的收藏
                    startActivity(new Intent(Invoker.ACTION_START_COLLECT));
                } else if (entity.getItemPosition() == 1) { // 历史记录
                    startActivity(new Intent(Invoker.ACTION_START_RECORD));
                }
            }
        });
    }

    /**
     * 接收首页传入的参数
     */
    private void dealExtras(Intent intent) {
        dealChangedExtras(intent);
        shouldShowOutsideBackground = intent.getBooleanExtra(AppConstants.EXTRA_SHOULD_SHOW_OUTSID_BG, false);
    }

    /**
     * 处理需要更换的参数
     *
     * @param intent
     */
    private void dealChangedExtras(Intent intent) {
        this.entity = (AlbumEntity) intent.getSerializableExtra(AppConstants.EXTRA_ALBUM_ENTITY);
        this.videosetId = intent.getIntExtra(AppConstants.EXTRA_VIDEOSET_ID, -1);
        cid = intent.getIntExtra(AppConstants.EXTRA_CID, 0);

        CloudScreenApplication.getInstance().source = intent.getStringExtra(AppConstants.EXTRA_SOURCE);
        source = CloudScreenApplication.getInstance().source;
        Log.i(TAG, "source:" + source);
        subjectId = intent.getIntExtra(AppConstants.EXTRA_SUBJECT_ID, 0);
        subjectName = intent.getStringExtra(AppConstants.EXTRA_SUBJECT_NAME);
        Log.e(TAG, "处理需要更换的参数  cid =" + cid + " subjectId = " + subjectId + " subjectName = " + subjectName + " videosetId = " + videosetId);
    }

    /**
     * 详情页我分为
     * 详情描述、主页描述、相关推荐
     * 分别对各页面进行监听等操作
     */
    private void createTab() {
        detailHome = new VideoDetailHome(VipVideoDetailActivity.this, subjectId, subjectName, source);
        detailDescription = new DetailDescription(this);
        detailRecomment = new DetailRecomment(this, this.videosetId, cid);
    }

    /**
     * 判断背景是否需要高斯模糊
     * 从搜索进入只显示背景图
     * 从列表也进入 获取背景图并进入模糊
     */
    private void showBackground() {
        if (shouldShowOutsideBackground || null == CloudScreenApplication.getInstance().blurBitmap) {
            backgroundStartActivity();
        } else {
            backgroundAnimationStartActivity();
        }
        if (!isEpisodeDetail()) {
            detailHome.hideEposide();
        }
    }

    /**
     * 给各控件添加监听事件，这里只是注册监听
     * 真正监听在dispatchKeyEvent、onKey事件中
     */
    private void setListener() {
        headerView.setOnFocusChangeListener(this);
        detailHome.setOnClickListener(this);
        detailHome.setOnKeyListener(this);
        detailDescription.setOnKeyListener(this);
        if (null != detailRecomment) {
            detailRecomment.setOnKeyListener(this);
        }
        //根据是否存在推荐数据来设置推荐数据是否显示
        detailRecomment.setOnRecommendDataEnableListener(new DetailRecomment.OnRecommendDataEnableListener() {
            @Override
            public void hasData(boolean hasData) {
                Logger.d(TAG, "Is Recommend has Data=" + hasData);
                if (hasData & !detailRecomment.isRightOpen()) {
                    recomment_tip.setVisibility(View.VISIBLE);
                } else {
                    recomment_tip.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    /**
     * 顶部用户登录和开通vip的跳转监听和埋点操作
     */
    private void setHearderLinstener() {
        headerView.setBtnClickListener(new VipVideoDetailHeaderView.OnBtnClickListener() {
            @Override
            public void onLoginClick(UserStateUtil.UserStatus status) {
                isAction = true;
                //                统计登陆点击行为
                if (entity != null) {
                    BaseActionInfo filmInfo = new VIPFilmDetailActionInfo("03",
                            "sde0303", entity.getChnId() + "", subjectId + "", subjectName, source, "1", null, null,
                            null, entity.getProgramsetId() + "", entity.getAlbumName());
                    new Statistics(getApplicationContext(), filmInfo).send();
                    //登录 到用户中心
                    UserStateUtil.getInstance().dealLogin(VipVideoDetailActivity.this);
                }
            }

            @Override
            public void onVipClick(UserStateUtil.UserStatus status) {
                isAction = true;
                UserStateUtil.getInstance().becomeVip(VipVideoDetailActivity.this,
                        "12");
                //                统计开通vip点击行为
                if (entity != null) {
                    BaseActionInfo filmInfo = new VIPFilmDetailActionInfo("03",
                            "sde0303", entity.getChnId() + "", subjectId + "", subjectName, source, "2", null,
                            null, null, entity.getProgramsetId() + "", entity.getAlbumName());
                    new Statistics(getApplicationContext(), filmInfo).send();

                }
            }
        });
    }

    /**
     * 设置详情页数据以及监听动画结束请求剧集接口
     *
     * @param entity
     */
    private void setData(final AlbumEntity entity) {
        if (null == entity) {
            return;
        }
        //加载数据完成
        isInitComplete = true;
        detailHome.setArguments(entity);
        detailHome.setData(entity);
        if (null != episode) {
            detailHome.setOpenActivityAnimatorListener(new VideoDetailHome.OpenActivityAnimatorListener() {
                @Override
                public void isEndAnimator(boolean ishas) {
                    if (ishas) {
                        isRequestEpisodeData.set(true);
                        CloudScreenService.getInstance().getEpisodeList(new GetEpisodeListListener(VipVideoDetailActivity.this), entity.getProgramsetId(), entity.getChnId(), entity.getStype() + "", 1, 500);
                        Log.e(TAG, " getEpisodeList");
                    }
                }
            });
        }
        Log.e(TAG, " if (null != episode)");
    }

    /**
     * 根据产品ID获取商品信息
     */
    private void productInfoRequest() {
        CloudScreenService.getInstance().getProductInfo(this.entity.getProgramsetId(), 1, new MyProductInfoListner(VipVideoDetailActivity.this));
    }

    /**
     * 请求鉴权接口，该用户是否购买了该影片,详情打开第一步
     */
    private void requestAuthentication() {
        CloudScreenService.getInstance().getFilmAuthentication("1:" + VipVideoDetailActivity.this.entity.getProgramsetId(), new MyFilmAuthenticationListener(VipVideoDetailActivity.this));
    }

    /**
     * entity 设置数据，id请求数据，否则，提示退出。
     * 详情打开第二部，前置{@link #requestAuthentication()}
     */
    private void requestData() {
        Log.e(TAG, "requestData ");
        detailHome.resetAllViewBeforeAnim();
        if (null != entity && isOnCreateCalled) {

            episode = getEpisodeComponent(entity);
            if (episode == null) {
                detailHome.hideEposide();
            }
            Log.e(TAG, "setData = " + entity);
            setData(entity);
        }
        if (this.videosetId > 0) {
            Log.e(TAG, "setDataById ---  videosetId = " + this.videosetId);
            setDataById(this.videosetId);
        } else {
            Toast.makeText(getApplicationContext(), R.string.video_data_error, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * 根据影片id请求数据，获得entity设置数据。
     *
     * @param videosetId 影片id
     */
    private void setDataById(int videosetId) {
        loadingView.setVisibilityDelay(100, View.VISIBLE);
        CloudScreenService.getInstance().getFilmDetail(new VideoDetailListener(this), info.templetId, videosetId);
    }

    @Override
    public void userStatusChanged() {
        if (null != detailHome) {
            detailHome.resetFocusColor(UserStateUtil.getInstance().getUserStatus());
            if (null != entity) {
                Log.d(TAG, "userStatusChanged");
                detailHome.setStreamHide(entity);
            }
        }
        if (null != detailDescription) {
            detailDescription.resetColor(UserStateUtil.getInstance().getUserStatus());
        }
        if (null != headerView) {
            headerView.setUserStatus(UserStateUtil.getInstance().getUserStatus());
        }
        if (null != episode) {
            episode.resetFocusColor(UserStateUtil.getInstance().getUserStatus());
        }
        if (null != detailRecomment.recycleViewAdapter) {
            detailRecomment.recycleViewAdapter.resetFocusColor(UserStateUtil.getInstance().getUserStatus());
        }
        //统计登陆成功失败的埋点
        if (isLogining) {
            isLogining = false;
            //登录失败
            if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.NOLOGIN) {
                BaseActionInfo info = new LoginDialogActionInfo("03", "vip0306", "6", "2", null);
                new Statistics(VipVideoDetailActivity.this, info).send();//统计埋点
            } else {
                //登录成功
                BaseActionInfo info = new LoginDialogActionInfo("03", "vip0306", "6", "1", null);
                new Statistics(VipVideoDetailActivity.this, info).send();//统计埋点
            }
        }
    }

    @Override
    public void userStatusNoChanged() {

    }

    @Override
    public boolean onKey(View view, int keycode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            switch (view.getId()) {
                case R.id.btn_film_detail_4play:
                    if (keycode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        openDescription();
                        return true;
                    } else if (keycode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        if (detailRecomment != null && detailRecomment.isRecommentData()) {
                            openRigthRecomment();
                        }
                        return true;
                    }
                    break;
                case R.id.btn_film_detail_play:
                    if (keycode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        openDescription();
                        return true;
                    } else if (keycode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        if (detailRecomment != null && detailRecomment.isRecommentData()) {
                            openRigthRecomment();
                        }
                        return true;
                    }
                    break;
                case R.id.btn_film_detail_collect:
                    if (keycode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        openDescription();
                        return true;
                    } else if (keycode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        if (detailRecomment != null && detailRecomment.isRecommentData()) {
                            openRigthRecomment();
                        }
                        return true;
                    }

                    break;
                case R.id.btn_film_detail_episode:
                    if (keycode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        openDescription();
                        return true;
                    } else if (keycode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        if (detailRecomment != null && detailRecomment.isRecommentData()) {
                            openRigthRecomment();
                        }
                        return true;
                    }
                case R.id.btn_film_detail_trySee:
                    if (keycode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        openDescription();
                        return true;
                    } else if (keycode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        if (detailRecomment != null && detailRecomment.isRecommentData()) {
                            openRigthRecomment();
                        }
                        return true;
                    }

                    break;
            }
        }

        return false;
    }

    /**
     * 打开左边详情描述
     */
    private void openDescription() {
        if (null != entity) {
            isAction = true;
            detailHome.openDescription();
            detailDescription.open(entity);
            if (null != episode) {
                episode.setFocusable(false);
            }

            //添加呼出左边的埋点
            BaseActionInfo filmInfo = new VIPFilmDetailActionInfo("03", "sde0303",
                    entity.getChnId() + "", subjectId + "", subjectName, source, null, "1", null, null,
                    entity.getProgramsetId() + "", entity.getAlbumName());
            new Statistics(getApplicationContext(), filmInfo).send();
        }
    }

    /**
     * 关闭左边详情描述
     */
    private void closeDescription() {
        detailDescription.close();
        detailHome.closeDescription();
        //设置主页面btn 获取焦点。
        detailHome.setBtnFocus();
        detailHome.lastFocusButtonRequestFocus();
    }

    /**
     * 打开右边相关推荐
     */
    private void openRigthRecomment() {
        if (detailRecomment != null) {
            if (detailRecomment.isRecommentData()) {
                detailHome.openRecomment();
                detailRecomment.openRecomment();
                detailRecomment.recyclerView.setFocusable(true);
                if (null != detailRecomment.recyclerView.getChildAt(0)) {
                    detailRecomment.recyclerView.getChildAt(0).requestFocus();
                }
                //添加呼出右边的埋点
                isAction = true;
                if (null != entity) {
                    BaseActionInfo filmInfo = new VIPFilmDetailActionInfo("03", "sde0303",
                            entity.getChnId() + "", subjectId + "", subjectName, source, null, "2", null, null,
                            entity.getProgramsetId() + "", entity.getAlbumName());
                    new Statistics(getApplicationContext(), filmInfo).send();
                }
            }
        } else {
            detailRecomment.recyclerView.setFocusable(false);
            detailHome.setBtnFocus();
            ToastUtil.showToast(VipVideoDetailActivity.this, getString(R.string.relate_recommend_data_error), 0);
            return;
        }
    }


    /**
     * 关闭右方相关推荐
     */
    private void closeRecomment() {
        recommentIsAnimationListener();
        detailHome.closeRecomment();
        detailRecomment.closeRecomment();
        detailHome.setBtnFocus();
        detailHome.lastFocusButtonRequestFocus();
    }

    /**
     * 监听主界面和相关推荐 运行动画结束后 显示右方提示。
     */
    private void recommentIsAnimationListener() {
        detailHome.setOnRecommentAnimationListener(new VideoDetailHome.isRecommentAnimationListener() {
            @Override
            public void animaEnd(boolean isEnd) {
                if (null != detailRecomment) {
                    Logger.d(TAG, "animaEnd isRightOpen=" + detailRecomment.isRightOpen() + "||isRecommentData=" + detailRecomment.isRecommentData());
                    if (isEnd && !detailRecomment.isRightOpen() && detailRecomment.isRecommentData()) {
                        detailHome.VisRightTip();
                    }
                }
            }
        });
    }

    /**
     * 关闭剧集
     */
    private void closeEpisode() {
        episode.setFocusable(false);
        episode.close();
        detailHome.setBtnFocus();
        detailHome.lastFocusButtonRequestFocus();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            switch (v.getId()) {
                case R.id.ll_detail_header_user_login://获得焦点判断相关推荐是否打开，打开则关闭
                    if (detailRecomment != null && detailRecomment.isRightOpen()) {
                        recommentIsAnimationListener();
                        detailHome.closeRecomment();
                        detailRecomment.closeRecomment();

                    }
                    break;
                case R.id.ll_detail_header_user_getvip://获得焦点判断相关推荐是否打开，打开则关闭
                    if (detailRecomment != null && detailRecomment.isRightOpen()) {
                        recommentIsAnimationListener();
                        detailHome.closeRecomment();
                        detailRecomment.closeRecomment();
                    }
                    break;
            }
        }
    }

    /**
     * 请求剧集接口
     */
    private static class GetEpisodeListListener implements OnRequestResultListener<ResultEntity> {

        private WeakReference<VipVideoDetailActivity> ref;

        private ResultEntity resultEntity;

        private int dataNum = 1;

        public GetEpisodeListListener(VipVideoDetailActivity activity) {
            ref = new WeakReference<VipVideoDetailActivity>(activity);
        }


        /**
         * 重组请求下来的数据，
         *
         * @param entity 重组后的数据
         */
        private void rebuildData(ResultEntity entity) {
            // 整合数据
            if (null != resultEntity) {
                List<EpisodeListEntity> list = resultEntity.getList();
                list.addAll(entity.getList());
                resultEntity.setList(list);
            } else {
                resultEntity = entity;
            }
        }

        @Override
        public void onSucess(ResultEntity entity) {
            VipVideoDetailActivity activity = ref.get();
            if (null != entity && null != activity) {
                if (entity.getList().size() == 0 || entity.getList().size() < 500) {
                    // 最后一条的情况
                    rebuildData(entity);
                    activity.isEpisodeDataCompleted.set(true);
                    activity.isRequestEpisodeData.set(false);
                    activity.episode.setData(resultEntity);
                    if (entity.getList().size() > 0) {
                        activity.detailHome.resetUpdateYear((EpisodeListEntity) entity.getList().get(0));
                    }
                } else {// 未加载完的情况
                    rebuildData(entity);
                    dataNum++;
                    CloudScreenService.getInstance().getEpisodeList(this, activity.entity.getProgramsetId(), activity.entity.getChnId(), activity.entity.getStype() + "", dataNum, 500);
                }
            } else {
            }
        }

        @Override
        public void onFail(Exception e) {
            VipVideoDetailActivity activity = ref.get();
            if (null != activity) {
                activity.isRequestEpisodeData.set(false);
            }
        }

        @Override
        public void onParseFail(HiveviewException e) {
            VipVideoDetailActivity activity = ref.get();
            if (null != activity) {
                activity.isRequestEpisodeData.set(false);
            }
        }
    }

    /**
     * 根据id请求专辑详情页接口数据
     */
    private static class VideoDetailListener implements OnRequestResultListener<ResultEntity> {

        private WeakReference<VipVideoDetailActivity> reference;

        private VideoDetailListener(VipVideoDetailActivity activity) {
            reference = new WeakReference<VipVideoDetailActivity>(activity);
        }

        @Override
        public void onSucess(ResultEntity resultEntity) {
            VipVideoDetailActivity activity = reference.get();
            if (null != activity) {

                activity.entity = (AlbumEntity) resultEntity.getEntity();
                if (null != activity.entity) {
                    Logger.e(TAG, "AlbumEntity.onSucess");
                    activity.episode = activity.getEpisodeComponent(activity.entity);
                    activity.setData(activity.entity);
                    if (null == activity.episode) {
                        activity.detailHome.hideEposide();
                    } else {
                        activity.episode.setOnKeyListener(activity);
                    }
                    //判断该影片是否为收费节目
                    if (null != activity.entity.getIsVip() && null != activity.entity.getChargingType()) {
                        if (activity.entity.getIsVip() == 0 && activity.entity.getChargingType() == 1 || activity.entity.getIsVip() == 1 && activity.entity.getChargingType() == 1) {

                            //获取商品信息
                            activity.productInfoRequest();
                        } else {
                            activity.loadingView.removeMessage();
                            if (activity.loadingView.getVisibility() == View.VISIBLE) {
                                activity.loadingView.setVisibility(View.GONE);
                            }
                            activity.detailHome.setStreamHide(activity.entity);
                            activity.detailHome.setBtnIcon();
                            //数据加载完成后再执行打开动画
                            activity.detailHome.activityOpenPosterAnimation();
                            activity.detailHome.activityOpenBtnAnimation();

                        }
                    }


                } else {
                    ToastUtil.showToast(CloudScreenApplication.getInstance(),
                            CloudScreenApplication.getInstance().getString
                                    (R.string.album_detail_data_null), Toast.LENGTH_LONG);
                    activity.finish();
                }
            }
        }

        @Override
        public void onFail(Exception e) {
            VipVideoDetailActivity activity = reference.get();
            if (null != activity) {
                activity.loadingView.removeMessage();
                if (activity.loadingView.getVisibility() == View.VISIBLE) {
                    activity.loadingView.setVisibility(View.GONE);
                }
                ToastUtil.showToast(CloudScreenApplication.getInstance(), CloudScreenApplication.getInstance().getString(R.string.album_detail_data_parse_error), Toast.LENGTH_LONG);
                activity.finish();
            }

        }

        @Override
        public void onParseFail(HiveviewException e) {
            VipVideoDetailActivity activity = reference.get();
            if (null != activity) {
                activity.loadingView.removeMessage();
                if (activity.loadingView.getVisibility() == View.VISIBLE) {
                    activity.loadingView.setVisibility(View.GONE);
                }
                ToastUtil.showToast(CloudScreenApplication.getInstance(), String.format(CloudScreenApplication.getInstance().getString(R.string.album_detail_data_net_error), e.getErrorCode()), Toast.LENGTH_LONG);
                activity.finish();
            }
        }
    }

    /**
     * 根据产品ID获取商品信息内部类
     */
    private class MyProductInfoListner implements OnRequestResultListener<ResultEntity<ProductInfoEntity>> {
        private WeakReference<VipVideoDetailActivity> reference;

        private MyProductInfoListner(VipVideoDetailActivity activity) {
            reference = new WeakReference<VipVideoDetailActivity>(activity);
        }

        @Override
        public void onSucess(ResultEntity<ProductInfoEntity> resultEntity) {
            VipVideoDetailActivity activity = reference.get();
            Log.e(TAG, "MyProductInfoListner   onSucess ");
            if (activity != null) {
                activity.loadingView.removeMessage();
                if (activity.loadingView.getVisibility() == View.VISIBLE) {
                    activity.loadingView.setVisibility(View.GONE);
                }
                activity.productInfoEntity = (ProductInfoEntity) resultEntity.getEntity();
                if (activity.productInfoEntity != null) {
                    if (null != episode) {
                        episode.setProductInfoEntity(productInfoEntity);
                    }
                    activity.detailHome.setProductInfo(activity.productInfoEntity);
                    activity.detailHome.setStreamHide(activity.entity);
                    //数据加载完成后再执行打开动画
                    activity.detailHome.activityOpenPosterAnimation();
                    activity.detailHome.activityOpenBtnAnimation();
                    //请求鉴权接口 
                    activity.requestAuthentication();

                } else {
                    ToastUtil.showToast(CloudScreenApplication.getInstance(), CloudScreenApplication.getInstance().getString(R.string.album_detail_data_null), Toast.LENGTH_LONG);
                    activity.finish();
                }
            }
        }

        @Override
        public void onFail(Exception e) {
            Log.e(TAG, "MyProductInfoListner   onFail(Exception e) ");
            VipVideoDetailActivity activity = reference.get();
            if (null != activity) {
                activity.loadingView.removeMessage();
                if (activity.loadingView.getVisibility() == View.VISIBLE) {
                    activity.loadingView.setVisibility(View.GONE);
                }
                ToastUtil.showToast(CloudScreenApplication.getInstance(), "付费信息获取失败，请稍候再试。", Toast.LENGTH_LONG);
                activity.finish();
            }
        }

        @Override
        public void onParseFail(HiveviewException e) {
            Log.e(TAG, "MyProductInfoListner   onParseFail(Exception e) ");
            VipVideoDetailActivity activity = reference.get();
            if (null != activity) {
                activity.loadingView.removeMessage();
                if (activity.loadingView.getVisibility() == View.VISIBLE) {
                    activity.loadingView.setVisibility(View.GONE);
                }
                ToastUtil.showToast(CloudScreenApplication.getInstance().getApplicationContext(), "付费信息获取失败，请稍候再试。", Toast.LENGTH_LONG);
                activity.finish();
            }
        }
    }

    private class MyFilmAuthenticationListener implements OnRequestResultListener<ResultEntity<FilmAuthenticationEntity>> {
        private WeakReference<VipVideoDetailActivity> reference;

        private MyFilmAuthenticationListener(VipVideoDetailActivity activity) {
            reference = new WeakReference<VipVideoDetailActivity>(activity);
        }

        @Override
        public void onSucess(ResultEntity resultEntity) {
            VipVideoDetailActivity activity = reference.get();
            if (activity != null && resultEntity != null) {
                FilmAuthenticationEntity authenticationEntity = (FilmAuthenticationEntity) resultEntity.getEntity();
                if (authenticationEntity != null) {
                    dianboList = authenticationEntity.getDianboList();
                }
                if (dianboList != null && dianboList.size() > 0) {//鉴权成功，该影片已购买
                    activity.productInfoEntity.setAuthenticationFlag(true);
                    activity.productInfoEntity.setDianboList(activity.dianboList);
                    Log.d(TAG, "鉴权成功，该影片已购买");
                } else {//该影片用户未买
                    activity.productInfoEntity.setAuthenticationFlag(false);
                    activity.productInfoEntity.setDianboList(null);
                    Log.d(TAG, "鉴权成功，该影片用户未买");

                }
                activity.detailHome.setUserBuySeeTime();
                activity.detailHome.setBtnIcon();

            }

        }

        @Override
        public void onFail(Exception e) {
            Log.d(TAG, "请求失败，鉴权失败");
            VipVideoDetailActivity activity = reference.get();
            if (null != activity && ++count >= 3) {
                ToastUtil.showToast(CloudScreenApplication.getInstance(), "鉴权失败，请重试", Toast.LENGTH_LONG);
                activity.finish();
            } else {
                handler.removeMessages(DELAY_REQUEST_DATA);
                handler.sendEmptyMessageDelayed(DELAY_REQUEST_DATA, 300);
            }
        }

        @Override
        public void onParseFail(HiveviewException e) {
            Log.d(TAG, "解析失败，鉴权失败");
            VipVideoDetailActivity activity = reference.get();
            if (null != activity) {
                ToastUtil.showToast(CloudScreenApplication.getInstance(), "鉴权失败，请重试", Toast.LENGTH_LONG);
                activity.finish();
            }
        }
    }

    public static class AuthenticationHandler extends Handler {
        private WeakReference<VipVideoDetailActivity> reference;

        public AuthenticationHandler(VipVideoDetailActivity activity) {
            reference = new WeakReference<VipVideoDetailActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            VipVideoDetailActivity activity = reference.get();
            if (null != activity) {
                switch (msg.what) {
                    case DELAY_REQUEST_DATA:
                        activity.requestAuthentication();
                        break;
                }
            }

        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == 2050) {//大麦影视快捷键，目前页面逻辑不好处理，这里先关闭该详情页
                finish();
            }
            if (detailHome.isOnAnim() && event.getKeyCode() != KeyEvent.KEYCODE_BACK) {
                //动画执行中不响应back键以外的按键
                return true;
            }
            if (!isInitComplete && event.getKeyCode() != KeyEvent.KEYCODE_BACK) {
                //加载未完成不响应back键以外的按键
                return true;
            }
            // 详细信息左键监听（打开）
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
                if (detailRecomment != null && detailRecomment.isRightOpen()) {
                    closeRecomment();
                    return true;
                } else if (null != episode && episode.isOpen()) {
                    if (episode.isEpisodeLeftShow) {
                        closeEpisode();
                        return true;
                    }
                }
            }

            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
                if (detailDescription != null && detailDescription.isLeftOpen()) {
                    closeDescription();
                    return true;
                }
            }

            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
                if (detailDescription != null && detailDescription.isLeftOpen()) {
                    detailDescription.dispatchKeyEvent(event);
                    return true;
                } else if (detailRecomment != null && detailRecomment.isRightOpen()) {
                    return detailRecomment.dispatchKeyEvent(event);
                }
            }
            if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                if (getCurrentFocus() == detailHome.fourPlayBtn) {
                    if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
                        return true;
                    }
                }
                if (!detailHome.isFourButtonGone()) {
                    if (getCurrentFocus() == detailHome.playBtn) {
                        if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
                            return true;
                        }
                    }
                }
            }

            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                if (null != episode && episode.isOpen()) {
                    closeEpisode();
                    return true;
                } else if (detailDescription != null && detailDescription.isLeftOpen()) {
                    closeDescription();
                    return true;
                } else if (detailRecomment != null && detailRecomment.isRightOpen()) {
                    closeRecomment();
                    return true;
                } else if (detailHome.isLeft || detailHome.isRight) {
                    return false;
                } else {
                    loadingView.removeMessage();
                    if (loadingView.getVisibility() == View.VISIBLE) {
                        loadingView.setVisibility(View.GONE);
                    }
                    finish();
                    detailHome.setLostBtnFocus();
                    //关闭详情页执行渐变动画
                    overridePendingTransition(-1, R.anim.list_anim_out);
                    return true;
                }

            }
        }
        return super.dispatchKeyEvent(event);
    }


    /**
     * 设置背景图片
     */

    private void backgroundStartActivity() {
        blurBackgroundView.setImageResource(R.drawable.bg_detail);
    }

    /**
     * 启动详情页背景动画
     */
    private void backgroundAnimationStartActivity() {
        if (null != CloudScreenApplication.getInstance().blurBitmap) {
            Drawable blurBg = new BitmapDrawable(null, CloudScreenApplication.getInstance().blurBitmap);
            blurBg.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            blurBackgroundView.setImageDrawable(blurBg);
        }
    }


    public BaseFilmDetailEpisode getEpisodeComponent(AlbumEntity entity) {
        return new TvDetailEpisode(this, entity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_film_detail_episode:
                isAction = true;
                Log.i("episode", "onclick");
                if (entity != null) {
                    openEpisode(true);
                    //点击剧集进入播放器
                    episode.setStarPlayClickCallBack(new StartPlayCallBack());
                    //  统计剧集点击行为
                    BaseActionInfo filmInfo = new VIPFilmDetailActionInfo("03",
                            "sde0303", entity.getChnId() + "", subjectId + "", subjectName, source, null, null, null, "4",
                            entity.getProgramsetId() + "", entity.getAlbumName());
                    new Statistics(getApplicationContext(), filmInfo).send();
                }
                break;
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2017/3/2
     * @Description 选集按钮回调
     */
    private class StartPlayCallBack implements BaseFilmDetailEpisode.StartPlayClickListener {

        @Override
        public void setStarPlayClickCallBack(AdapterView<?> parent, View view, int position, EpisodeListEntity episodeListEntity) {
            VipVideoDetailActivity.this.episodeListEntity = episodeListEntity;
            VipVideoDetailActivity.this.position = position;
            if (entity.getChargingType() == 1) {
                //如果是在销售的节目需要请求服务器比对当前时间
                requestServiceTime();
            } else {
                if (DeviceInfoUtil.getInstance().getDeviceInfo(getApplicationContext()).white == 1) {//白名单模式
                    whiteUserFreeSeeFilm();
                    return;
                }
                //1️，isVip =0,isBuy=0；
                if (entity.getIsVip() == 0 && entity.getChargingType() == 0) {
                    startPlay(episodeListEntity);
                }
                //2,isVip =1,isBuy=0
                if (entity.getIsVip() == 1 && entity.getChargingType() == 0) {
                    //获取当前用户状态，根据状态分别提示对话框
                    if (detailHome.isFourStream()) {//只有4k影片){
                        setUserStatus(UserStateUtil.getInstance().getUserStatus(), 1, episodeListEntity, position);
                    } else {//720免费观看
                        startPlay(episodeListEntity);
                    }
                }
            }
        }

        /**
         * 白名单模式免费观看影片
         */
        private void whiteUserFreeSeeFilm() {
            if (null != productInfoEntity) {
                if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 1) {//预售阶段
                    if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getBookingStartTime(), productInfoEntity.getLongTime(), productInfoEntity.getBookingEndTime())) {//在预售范围
                        detailHome.showBuyedDiaLog("影片将在" + StringUtils.formatDate(productInfoEntity.getBookingEndTime()) + "正式放映,欢迎届时观看。");
                    } else if (StringUtils.currentTimeAfterOrderTime(productInfoEntity.getBookingEndTime())) {//销售阶段直接观看
                        startPlay(episodeListEntity);
                    }
                } else if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 0) {//销售阶段
                    if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getBookingEndTime(), productInfoEntity.getLongTime(), productInfoEntity.getSaleEndTime())) {//销售阶段直接观看
                        startPlay(episodeListEntity);
                    } else {
                        Log.d(TAG, "后台配置参数有错误！");
                    }
                }
            } else {//非预售 销售情况，可以直接观看影片
                startPlay(episodeListEntity);
            }
        }

        /**
         * 获取当前访问服务器时间
         */
        private void requestServiceTime() {
            CloudScreenService.getInstance().getServiceTime(new ServiceTimeListener(VipVideoDetailActivity.this));
        }

        /**
         * @Author Spr_ypt
         * @Date 2017/3/2
         * @Description 服务器时间获取监听
         */
        private class ServiceTimeListener implements OnRequestResultListener<ResultEntity<ServiceTimeEntity>> {
            private WeakReference<Activity> reference;

            private ServiceTimeListener(Activity activity) {
                reference = new WeakReference<Activity>(activity);
            }

            @Override
            public void onSucess(ResultEntity<ServiceTimeEntity> resultEntity) {
                Activity acitivity = reference.get();
                if (acitivity != null) {
                    ServiceTimeEntity serviceTimeEntity = (ServiceTimeEntity) resultEntity.getEntity();
                    productInfoEntity.setLongTime(serviceTimeEntity.getLongTime());//设置服务器最新时间
                    Log.e(TAG, "getServiceTime Success *** serviceTimeEntity.getLongTime() = " + serviceTimeEntity.getLongTime());

                    if (DeviceInfoUtil.getInstance().getDeviceInfo(getApplicationContext()).white == 1) {//白名单模式
                        whiteUserFreeSeeFilm();
                        return;
                    }
                    //3,isVip =0,isBuy=1
                    if (entity.getIsVip() == 0 && entity.getChargingType() == 1) {
                        if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 1) {
                            preSaleLogic1(episodeListEntity, position);
                        } else if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 0) {
                            saleLogic1(episodeListEntity, position);
                        } else {
                            Log.d(TAG, "预售参数配置有错误");
                        }

                    }
                    //4,isVip =1,isBuy=1
                    if (entity.getIsVip() == 1 && entity.getChargingType() == 1) {
                        if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 1) {
                            preSaleLogic2(episodeListEntity, position);
                        } else if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 0) {
                            saleLogic2(episodeListEntity, position);
                        } else {
                            Log.d(TAG, "预售参数配置有错误");
                        }
                    }

                }
            }

            @Override
            public void onFail(Exception e) {
                ToastUtil.showToast(getApplicationContext(), "获取服务器时间失败，请重试！", 0);

            }

            @Override
            public void onParseFail(HiveviewException e) {
                ToastUtil.showToast(getApplicationContext(), "获取服务器时间失败，请重试！", 0);

            }
        }

    }

    /**
     * 预售（isVIP =0,isBug = 1）逻辑
     */
    private void preSaleLogic1(EpisodeListEntity episodeListEntity, int position) {
        if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getBookingStartTime(), productInfoEntity.getLongTime(), productInfoEntity.getBookingEndTime())) {
            //预售期内
            if (productInfoEntity.isAuthenticationFlag()) {//预售期已购
                Log.d(TAG, "选集（isVip =0,isBuy=1）预购期间已购买");
                if (productInfoEntity.getMultiSetNumbers() >= ++position) {//有试看直接播放
                    startPlay(episodeListEntity);
                } else {//无试看弹框提示
                    detailHome.showBuyedDiaLog("您购买的内容将在" + StringUtils.formatDate(productInfoEntity.getBookingEndTime()) + "正式放映,欢迎届时观看。");
                }
                return;
            } else {
                Log.d(TAG, "选集（isVip =0,isBuy=1）预购期间未购买");
                //4k
                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {//VIP用户
                    if (null != productInfoEntity.getVipNowPrice() && productInfoEntity.getVipNowPrice() == 0) {
                        if (productInfoEntity.getMultiSetNumbers() >= ++position) {//大于试看集数免费观看
                            Log.d(TAG, "VIP (isVip =0,isBuy=1) 有预售 有试看 观看");
                            startPlay(episodeListEntity);
                        } else {
                            detailHome.showBuyedDiaLog("影片将在" + StringUtils.formatDate(productInfoEntity.getBookingEndTime()) + "正式放映,欢迎届时观看。");
                        }
                        Log.d(TAG, "限时特惠，优惠价格0麦币");
                    } else {
                        if (productInfoEntity.getMultiSetNumbers() >= ++position) {//大于试看集数免费观看
                            Log.d(TAG, "VIP (isVip =0,isBuy=1) 有预售 有试看 观看");
                            startPlay(episodeListEntity);
                        } else {//直接购买
                            detailHome.openPayMoneyPage(3);
                            Log.d(TAG, "VIP (isVip =0,isBuy=1) 有预售 无试看 支付");
                        }
                    }

                } else {//非vip用户或者未登录用户
                    Log.d(TAG, "非VIP (isVip =0,isBuy=1) 有预售");
                    setUserStatus(UserStateUtil.getInstance().getUserStatus(), 3, episodeListEntity, position);
                }
            }

        } else if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getBookingEndTime(), productInfoEntity.getLongTime(), productInfoEntity.getSaleEndTime())) {//无预售
            if (productInfoEntity.isAuthenticationFlag()) {//预售期已购
                Log.d(TAG, "选集（isVip =0,isBuy=1）销售期间已购买");
                startPlay(episodeListEntity);
                return;
            } else {
                Log.d(TAG, "选集（isVip =0,isBuy=1）销售期间未购买");
                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {//VIP用户,直接购买
                    if (null != productInfoEntity.getVipNowPrice() && productInfoEntity.getVipNowPrice() == 0) {
                        startPlay(episodeListEntity);
                        Log.d(TAG, "限时特惠，优惠价格0麦币");
                    } else {
                        if (productInfoEntity.getMultiSetNumbers() >= ++position) {//有试看
                            startPlay(episodeListEntity);
                            Log.d(TAG, "VIP (isVip =0,isBuy=1) 无预售 有试看 观看");
                        } else {
                            detailHome.openPayMoneyPage(3);
                            Log.d(TAG, "VIP (isVip =0,isBuy=1) 无预售 无试看 支付");
                        }
                    }
                } else {//非vip用户或者未登录用户
                    Log.d(TAG, "非VIP (isVip =0,isBuy=1) 无预售");
                    setUserStatus(UserStateUtil.getInstance().getUserStatus(), 3, episodeListEntity, position);
                }
            }
        } else {
            if (productInfoEntity.isAuthenticationFlag()) {//已买
                startPlay(episodeListEntity);
            } else {
                ToastUtil.showToast(this, "销售期已过，此影片不能购买", 0);
            }
        }
    }

    /**
     * 销售（isVIP =0,isBug = 1）逻辑
     */
    private void saleLogic1(EpisodeListEntity episodeListEntity, int position) {
        if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getSaleStartTime(), productInfoEntity.getLongTime(), productInfoEntity.getSaleEndTime())) {//无预售
            if (productInfoEntity.isAuthenticationFlag()) {//预售期已购
                Log.d(TAG, "选集（isVip =0,isBuy=1）销售期间已购买");
                startPlay(episodeListEntity);
                return;
            } else {
                Log.d(TAG, "选集（isVip =0,isBuy=1）销售期间未购买");
                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {//VIP用户,直接购买
                    if (null != productInfoEntity.getVipNowPrice() && productInfoEntity.getVipNowPrice() == 0) {
                        startPlay(episodeListEntity);
                        Log.d(TAG, "限时特惠，优惠价格0麦币");
                    } else {
                        if (productInfoEntity.getMultiSetNumbers() >= ++position) {//有试看
                            startPlay(episodeListEntity);
                            Log.d(TAG, "VIP (isVip =0,isBuy=1) 无预售 有试看 观看");
                        } else {
                            detailHome.openPayMoneyPage(3);
                            Log.d(TAG, "VIP (isVip =0,isBuy=1) 无预售 无试看 支付");
                        }
                    }

                } else {//非vip用户或者未登录用户
                    Log.d(TAG, "非VIP (isVip =0,isBuy=1) 无预售");
                    setUserStatus(UserStateUtil.getInstance().getUserStatus(), 3, episodeListEntity, position);
                }
            }
        } else {
            if (productInfoEntity.isAuthenticationFlag()) {//已买
                startPlay(episodeListEntity);
            } else {
                ToastUtil.showToast(this, "销售期已过，此影片不能购买", 0);
            }
        }
    }

    /**
     * 预售（isVIP =1,isBug = 1）逻辑
     */
    private void preSaleLogic2(EpisodeListEntity episodeListEntity, int position) {
        if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getBookingStartTime(), productInfoEntity.getLongTime(), productInfoEntity.getBookingEndTime())) {//在预售时间范围内
            if (productInfoEntity.isAuthenticationFlag()) {//预售期已购
                Log.d(TAG, "选集（isVip =1,isBuy=1）预售期间已购买");
                if (productInfoEntity.getMultiSetNumbers() >= ++position) {//有试看直接播放
                    startPlay(episodeListEntity);
                } else {
                    detailHome.showBuyedDiaLog("您购买的内容将在" + StringUtils.formatDate(productInfoEntity.getBookingEndTime()) + "正式放映,欢迎届时观看。");
                }
                return;
            } else {
                Log.d(TAG, "选集（isVip =1,isBuy=1）预售期间未购买");
                //4k
                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {//VIP用户
                    if (null != productInfoEntity.getVipNowPrice() && productInfoEntity.getVipNowPrice() == 0) {
                        if (productInfoEntity.getMultiSetNumbers() >= ++position) {//大于试看集数免费观看
                            Log.d(TAG, "VIP (isVip =0,isBuy=1) 有预售 有试看 观看");
                            startPlay(episodeListEntity);
                        } else {
                            detailHome.showBuyedDiaLog("影片将在" + StringUtils.formatDate(productInfoEntity.getBookingEndTime()) + "正式放映,欢迎届时观看。");
                        }
                        Log.d(TAG, "限时特惠，优惠价格0麦币");
                    } else {
                        if (productInfoEntity.getMultiSetNumbers() >= ++position) {//大于试看集数免费观看
                            Log.d(TAG, "VIP (isVip =1,isBuy=1) 有预售 有试看 观看");
                            startPlay(episodeListEntity);
                        } else {
                            Log.d(TAG, "VIP (isVip =1,isBuy=1) 有预售 无试看 吐司提示");
                            detailHome.showBuyedDiaLog("该内容将在" + StringUtils.formatDate(productInfoEntity.getBookingEndTime()) + "正式放映,欢迎届时观看。");
                        }
                    }

                } else {//非vip用户或者未登录用户
                    Log.d(TAG, "非VIP (isVip =1,isBuy=1) 有预售");
                    setUserStatus(UserStateUtil.getInstance().getUserStatus(), 3, episodeListEntity, position);
                }
            }
        } else if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getBookingEndTime(), productInfoEntity.getLongTime(), productInfoEntity.getSaleEndTime())) {//非预售阶段
            if (productInfoEntity.isAuthenticationFlag()) {//预售期已购
                Log.d(TAG, "选集（isVip =1,isBuy=1）销售期间已购买");
                startPlay(episodeListEntity);
                return;
            } else {
                Log.d(TAG, "选集（isVip =1,isBuy=1）销售期间未购买");
                //4k
                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {//VIP用户直接进行观看
                    startPlay(episodeListEntity);
                    Log.d(TAG, "VIP (isVip =1,isBuy=1) 无预售 直接观看");
                } else {//非vip用户或者未登录用户
                    Log.d(TAG, "非VIP (isVip =1,isBuy=1) 无预售");
                    setUserStatus(UserStateUtil.getInstance().getUserStatus(), 3, episodeListEntity, position);
                }
            }
        } else {
            if (productInfoEntity.isAuthenticationFlag()) {//已买
                startPlay(episodeListEntity);
            } else {
                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                    startPlay(episodeListEntity);
                } else {
                    ToastUtil.showToast(this, "销售期已过，此影片不能购买", 0);

                }
            }
        }
    }

    /**
     * 销售（isVIP =1,isBug = 1）逻辑
     */
    private void saleLogic2(EpisodeListEntity episodeListEntity, int position) {
        if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getSaleStartTime(), productInfoEntity.getLongTime(), productInfoEntity.getSaleEndTime())) {//非预售阶段
            if (productInfoEntity.isAuthenticationFlag()) {//预售期已购
                Log.d(TAG, "选集（isVip =1,isBuy=1）销售期间已购买");
                startPlay(episodeListEntity);
                return;
            } else {
                Log.d(TAG, "选集（isVip =1,isBuy=1）销售期间未购买");
                //4k
                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {//VIP用户直接进行观看
                    startPlay(episodeListEntity);
                    Log.d(TAG, "VIP (isVip =1,isBuy=1) 无预售 直接观看");
                } else {//非vip用户或者未登录用户
                    Log.d(TAG, "非VIP (isVip =1,isBuy=1) 无预售");
                    setUserStatus(UserStateUtil.getInstance().getUserStatus(), 3, episodeListEntity, position);
                }
            }
        } else {
            if (productInfoEntity.isAuthenticationFlag()) {//已买
                startPlay(episodeListEntity);
            } else {
                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                    startPlay(episodeListEntity);
                } else {
                    ToastUtil.showToast(this, "销售期已过，此影片不能购买", 0);

                }
            }
        }
    }

    /**
     * 跳转到播放器
     */
    public void startPlay(EpisodeListEntity episodeListEntity) {
        String oldDetail = JSONObject.toJSONString(entity);
        String albumInfo = oldDetail;

        if (null != productInfoEntity) {
            String newDetail = oldDetail.substring(0, oldDetail.length() - 1);
            String oldProductInfo = JSONObject.toJSONString(productInfoEntity);
            String newProductInfo = oldProductInfo.substring(1, oldProductInfo.length());
            albumInfo = newDetail + "," + newProductInfo;
        }
        Intent playerIntent = new Intent(VideoDetailInvoker.ACTION_PLAYER);
        playerIntent.putExtra(AppConstants.EXTRA_VIDEO_ENTITY, JSONObject.toJSONString(episodeListEntity));
        playerIntent.putExtra(AppConstants.EXTRA_ALBUM_ENTITY, albumInfo);
        playerIntent.putExtra(AppConstants.EXTRA_SOURCE, CloudScreenApplication.getInstance().source);
        playerIntent.putExtra(AppConstants.EXTRA_IS_FROM_EPISODE, true);
        Log.i(TAG, "source:" + CloudScreenApplication.getInstance().source);
        startActivity(playerIntent);
    }

    /**
     * 标记点击了登录，用于在回调中判断是否登录成功
     */
    private static boolean isLogining = false;

    public void setUserStatus(UserStateUtil.UserStatus userStatus, int isVIPVideo, EpisodeListEntity episodeListEntity, int position) {
        if (isVIPVideo == 1) {
            switch (userStatus) {
                case NOLOGIN:
                    userStatusDialog = new VipVideoDialog
                            (VipVideoDetailActivity.this, getString(R.string.dialog_login_vip_film), getString(R.string.dialog_login_ok), getString(R.string.dialog_login_cancel), VipVideoDialog.DialogBg.D, false);
                    userStatusDialog.setOnBtnClicklistener(new VipVideoDialog.OnBtnClickListener() {
                        @Override
                        public void onPositiveBtnClick() {
                            isLogining = true;
                            UserStateUtil.getInstance().dealLogin
                                    (VipVideoDetailActivity.this);
                            userStatusDialog.dismiss();
                            if (!episode.isOpen()) {
                                detailHome.showFocus();
                            }
                        }

                        @Override
                        public void onNegativeBtnClick() {
                            userStatusDialog.dismiss();
                            if (!episode.isOpen()) {
                                detailHome.showFocus();
                            }
                            BaseActionInfo loginInfo = new LoginDialogActionInfo("03", "vip0306", "6", "3", "3");
                            new Statistics(getApplicationContext(), loginInfo).send();
                        }

                        @Override
                        public void onDismissWithoutPressBtn() {
                            if (!episode.isOpen()) {
                                detailHome.showFocus();
                            }
                            BaseActionInfo loginInfo = new LoginDialogActionInfo("03", "vip0306", "6", "3", "3");
                            new Statistics(getApplicationContext(), loginInfo).send();
                        }
                    });
                    userStatusDialog.showWindow();
                    break;
                case NOMALUSER:
                    userStatusDialog = new VipVideoDialog
                            (VipVideoDetailActivity.this, getString(R.string.dialog_open_vip_film), getString(R.string.dialog_open_vip), getString(R.string.dialog_wait_open), VipVideoDialog.DialogBg.D, false);
                    userStatusDialog.setOnBtnClicklistener(new VipVideoDialog.OnBtnClickListener() {
                        @Override
                        public void onPositiveBtnClick() {
                            userStatusDialog.dismiss();
                            if (!episode.isOpen()) {
                                detailHome.showFocus();
                            }
                            BaseActionInfo giveUp = new VIPDialogActionInfo("03", "vip0305", "0", null, "6", "1", null, null);
                            new Statistics(VipVideoDetailActivity.this, giveUp).send();

                            UserStateUtil.getInstance().becomeVip(VipVideoDetailActivity.this, "13");
                        }

                        @Override
                        public void onNegativeBtnClick() {
                            userStatusDialog.dismiss();
                            if (!episode.isOpen()) {
                                detailHome.showFocus();
                            }

                            BaseActionInfo giveUp = new VIPDialogActionInfo("03", "vip0305", "0", null, "6", "2", null, null);
                            new Statistics(VipVideoDetailActivity.this, giveUp).send();
                        }

                        @Override
                        public void onDismissWithoutPressBtn() {
                            if (!episode.isOpen()) {
                                detailHome.showFocus();
                            }
                            BaseActionInfo giveUp = new VIPDialogActionInfo("03", "vip0305", "0", null, "6", "2", null, null);
                            new Statistics(VipVideoDetailActivity.this, giveUp).send();
                        }
                    });
                    userStatusDialog.showWindow();
                    break;
                case VIPUSER:
                    startPlay(episodeListEntity);
                    break;
            }
        } else if (isVIPVideo == 2) {//暂时没有此状态，已弃用
            switch (userStatus) {
                case NOLOGIN:
                    userStatusDialog = new VipVideoDialog
                            (VipVideoDetailActivity.this, getString(R.string.dialog_video_stream_login), getString(R.string.dialog_login_ok), getString(R.string.dialog_login_cancel), VipVideoDialog.DialogBg.D, false);
                    userStatusDialog.setOnBtnClicklistener(new VipVideoDialog.OnBtnClickListener() {
                        @Override
                        public void onPositiveBtnClick() {
                            isLogining = true;
                            UserStateUtil.getInstance().dealLogin
                                    (VipVideoDetailActivity.this);
                            userStatusDialog.dismiss();
                            if (!episode.isOpen()) {
                                detailHome.showFocus();
                            }
                        }

                        @Override
                        public void onNegativeBtnClick() {
                            userStatusDialog.dismiss();
                            if (!episode.isOpen()) {
                                detailHome.showFocus();
                            }
                            BaseActionInfo loginInfo = new LoginDialogActionInfo("03", "vip0306", "6", "3", "3");
                            new Statistics(getApplicationContext(), loginInfo).send();
                        }

                        @Override
                        public void onDismissWithoutPressBtn() {
                            BaseActionInfo loginInfo = new LoginDialogActionInfo("03",
                                    "vip0306", "6", "3", "3");
                            new Statistics(getApplicationContext(), loginInfo).send();
                        }
                    });
                    userStatusDialog.showWindow();
                    break;
                case NOMALUSER:
                    userStatusDialog = new VipVideoDialog
                            (VipVideoDetailActivity.this, getString(R.string.dialog_video_stream_openvip), getString(R.string.dialog_open_vip), getString(R.string.dialog_wait_open), VipVideoDialog.DialogBg.D, false);
                    userStatusDialog.setOnBtnClicklistener(new VipVideoDialog.OnBtnClickListener() {
                        @Override
                        public void onPositiveBtnClick() {
                            userStatusDialog.dismiss();
                            if (!episode.isOpen()) {
                                detailHome.showFocus();
                            }
                            BaseActionInfo giveUp = new VIPDialogActionInfo("03", "vip0305", "0", null, "6", "1", null, null);
                            new Statistics(VipVideoDetailActivity.this, giveUp).send();

                            UserStateUtil.getInstance().becomeVip
                                    (VipVideoDetailActivity.this, "13");
                        }

                        @Override
                        public void onNegativeBtnClick() {
                            userStatusDialog.dismiss();
                            if (!episode.isOpen()) {
                                detailHome.showFocus();
                            }

                            BaseActionInfo giveUp = new VIPDialogActionInfo("03", "vip0305", "0", null, "6", "2", null, null);
                            new Statistics(VipVideoDetailActivity.this, giveUp).send();
                        }

                        @Override
                        public void onDismissWithoutPressBtn() {
                            if (!episode.isOpen()) {
                                detailHome.showFocus();
                            }
                            BaseActionInfo giveUp = new VIPDialogActionInfo("03", "vip0305", "0", null, "6", "2", null, null);
                            new Statistics(VipVideoDetailActivity.this, giveUp).send();
                        }
                    });
                    userStatusDialog.showWindow();
                    break;
                case VIPUSER:
                    startPlay(episodeListEntity);
                    break;
            }
        } else if (isVIPVideo == 3) {
            switch (userStatus) {
                case NOLOGIN:
                    userStatusDialog = new VipVideoDialog(VipVideoDetailActivity.this, getString(R.string.dialog_login_vip_film), getString(R.string.dialog_login_ok), getString(R.string.dialog_login_cancel), VipVideoDialog.DialogBg.D, false);
                    userStatusDialog.setOnBtnClicklistener(new VipVideoDialog.OnBtnClickListener() {
                        @Override
                        public void onPositiveBtnClick() {
                            isLogining = true;
                            UserStateUtil.getInstance().dealLogin
                                    (VipVideoDetailActivity.this);
                            userStatusDialog.dismiss();
                            if (!episode.isOpen()) {
                                detailHome.showFocus();
                            }
                        }

                        @Override
                        public void onNegativeBtnClick() {
                            userStatusDialog.dismiss();
                            if (!episode.isOpen()) {
                                detailHome.showFocus();
                            }
                            BaseActionInfo loginInfo = new LoginDialogActionInfo("03",
                                    "vip0306", "6", "3", "3");
                            new Statistics(getApplicationContext(), loginInfo).send();
                        }

                        @Override
                        public void onDismissWithoutPressBtn() {
                            if (!episode.isOpen()) {
                                detailHome.showFocus();
                            }
                            BaseActionInfo loginInfo = new LoginDialogActionInfo("03",
                                    "vip0306", "6", "3", "3");
                            new Statistics(getApplicationContext(), loginInfo).send();
                        }
                    });
                    userStatusDialog.showWindow();
                    break;
                case NOMALUSER:
                    if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getBookingStartTime(), productInfoEntity.getLongTime(), productInfoEntity.getBookingEndTime())) { //预售期间内
                        if (null != productInfoEntity.getNowPrice() && productInfoEntity.getNowPrice() == 0) {
                            if (productInfoEntity.getMultiSetNumbers() >= ++position) {//大于试看集数免费观看
                                startPlay(episodeListEntity);
                            } else {
                                detailHome.showBuyedDiaLog("影片将在" + StringUtils.formatDate(productInfoEntity.getBookingEndTime()) + "正式放映,欢迎届时观看。");
                            }
                        } else {
                            detailHome.openPayMoneyPage(3);
                        }
                    } else {
                        if (null != productInfoEntity.getNowPrice() && productInfoEntity.getNowPrice() == 0) {
                            startPlay(episodeListEntity);
                            Log.d(TAG, "限时特惠，优惠价格0麦币");
                        } else {
                            if (productInfoEntity.getMultiSetNumbers() >= ++position) {//大于试看集数免费观看
                                Log.d(TAG, "非VIP 有试看 ");
                                startPlay(episodeListEntity);
                            } else {//直接支付
                                detailHome.openPayMoneyPage(3);
                                Log.d(TAG, "非VIP 无试看 支付");
                            }
                        }
                    }
                    break;
            }
        }

    }

    protected boolean isEpisodeDetail() {
        return true;
    }

    public void openEpisode(final boolean requestFocus) {
        if (isEpisodeDataCompleted.get()) {
            if (null != episode) {
                episode.setFocusable(true);
                if (requestFocus) {
                    episode.leftRequestFocus();
                }
                episode.open();
                detailHome.setLostBtnFocus();
                detailRecomment.setFocusable(false);
            }
        } else {
            ToastUtil.showToast(getApplicationContext(), getResources().getString(R.string.data_not_completed), Toast.LENGTH_SHORT);
            if (!isRequestEpisodeData.get()) {
                isRequestEpisodeData.set(true);
                CloudScreenService.getInstance().getEpisodeList(new GetEpisodeListListener(VipVideoDetailActivity.this), entity.getProgramsetId(), entity.getChnId(), entity.getStype() + "", 1, 500);
            }
        }
    }

    private static class HomeKeyReceiver extends BroadcastReceiver {

        private static final String ACTION_HOME = "com.hiveview.cloudscreen.Action.HOME_CODE";
        private WeakReference<VipVideoDetailActivity> reference;

        public HomeKeyReceiver(VipVideoDetailActivity activity) {
            reference = new WeakReference<VipVideoDetailActivity>(activity);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "intent action = " + intent.getAction());
            if (intent.getAction().equals(ACTION_HOME)) {
                VipVideoDetailActivity activity = reference.get();
                if (null != activity) {
                    activity.finish();
                }
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
        if (paused) {
            if (shouldShowOutsideBackground) {
                blurBackgroundView.setBackgroundResource(R.drawable.bg_detail);
            } else {
                Bitmap blurBitmap = BitmapBlurUtil.getInstance().readFromFile(Integer.toHexString(hashCode()));
                if (null != blurBitmap) {
                    Drawable blurBg = new BitmapDrawable(null, CloudScreenApplication.getInstance().blurBitmap);
                    blurBg.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    blurBackgroundView.setImageDrawable(blurBg);
                }
            }
        }
        paused = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        paused = true;
        if (null != CloudScreenApplication.getInstance().blurBitmap) {
            BitmapBlurUtil.getInstance().writeToFile(CloudScreenApplication.getInstance().blurBitmap, Integer.toHexString(hashCode()));
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        isRestart = true;
        getVersionCode(false);
        UserStateUtil.getInstance().notifyReFreshUserState(this);//从新回到该页面时需要刷新下用户信息,主要用于登录后
        if (entity != null && entity.getChargingType() == 1) {
            detailHome.animalFlag = 1;
            requestAuthentication();//重新请求一下鉴权，刷新用户是否成功付费。
        }

    }

    //是否与服务端建立连接
    private boolean mBound = false;
    //服务端消息管理者
    private Messenger serviceMessenger;

    public ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected");
            serviceMessenger = new Messenger(service);
            mBound = true;
            getVersionCode(false);
            Log.i(TAG, "mConnection  mBound==" + mBound);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected");
            serviceMessenger = null;
            mBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
        String userAppCode = UserStateUtil.getInstance().version;
        Log.i(TAG, "userAppCode=" + userAppCode);
        if ("03".equals(userAppCode)) {
            Intent intent = new Intent();
            intent.setAction("com.hiveview.cloudscreen.user.MESSENGER_SERVICE");
            intent.setPackage("com.hiveview.cloudscreen.user");
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    public void getVersionCode(boolean isTrue) {
        String userAppCode = UserStateUtil.getInstance().version;
        Log.i(TAG, "userAppCode=" + userAppCode);
        if ("".equals(userAppCode)) {
            return;
        } else if ("02".equals(userAppCode)) {// 3.2开始： versionCode 为02 是刷新用户中心是通过广播，03 是通过 bindService 和 massage 获取用户数据。
            //发送广播告知用户中心大麦影视已经打开，他们会自行处理
            sendBroadcast(new Intent(AppConstants.ACTION_REFRESH_USER_INFO_ONCE));
            Log.d(TAG, "sendBroadcast REFRESH_VIP_INFO_ONCE");
            //初始化用户状态,该程序可能是系统应用，进程长期存在，application可能长期存在，所以需要在这里请求用户数据
            UserStateUtil.getInstance().notifyReFreshUserState(getApplicationContext());
        } else {//3.2 开始通过 massage 获取数据
            sendMsg2UserCenter(isTrue);
        }
    }


    private static final int REFRESH_VIP_USERINFO = 1000;
    private static final int REFRESH_VIP_USERINFO_SUCCESS = 2000;
    //客户端消息管理者
    private final Messenger clientMessenger = new Messenger(new MyHanlder());


    /**
     * 通知用户中心刷新用户信息以及vip信息
     */
    private void sendMsg2UserCenter(boolean isTrue) {
        if (!mBound) {
            return;
        }
        //发送一条信息给服务端
        Message msg = Message.obtain(null, REFRESH_VIP_USERINFO, 0, 0);
        msg.replyTo = clientMessenger;
        if (isTrue) {
            msg.arg1 = 1;
        }
        try {
            serviceMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收服务端返回的消息
     */
    private class MyHanlder extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_VIP_USERINFO_SUCCESS:
                    Log.i(TAG, "receiver msg");
                    //初始化用户状态,该程序可能是系统应用，进程长期存在，application可能长期存在，所以需要在这里请求用户数据
                    UserStateUtil.getInstance().notifyReFreshUserState(getApplicationContext());
                    //TODO vip 和 用户信息刷新成功
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        //预售提醒解绑
        AlarmServiceUtil.getInstant().unbindService(this);

        unregisterReceiver(keyReceiver);
        if (receiver != null) {//销毁支付成功广播监听
            unregisterReceiver(receiver);
        }
        UserStateUtil.getInstance().removeWatcher(this);//反注册监听

        if (null != loadingView) {
            loadingView.recycle();
        }
        Long currenTime = System.currentTimeMillis() - beforeTime;
        String stayTimeLength = Long.toString(currenTime);
        //统计影片也是否做了点击操作，直接按返回为flase； true：做了其他操作。
        if (null != entity) {
            if (isAction) {
                BaseActionInfo actionInfo = new VIPFilmDetailBrowserInfo("03",
                        "vip03017", "0", source, entity.getChnId() + "", subjectId + "", subjectName, stayTimeLength,
                        entity.getProgramsetId() + "", entity.getAlbumName() + "");
                new Statistics(VipVideoDetailActivity.this, actionInfo).send();
            } else {
                BaseActionInfo browserInfo = new VIPFilmDetailBrowserInfo("03",
                        "vip0317", "1", source, entity.getChnId() + "", subjectId + "", subjectName, stayTimeLength,
                        entity.getProgramsetId() + "", entity.getAlbumName() + "");
                new Statistics(VipVideoDetailActivity.this, browserInfo).send();
            }
        }
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.e(TAG, "onWindowFocusChanged------");
        boolean isFirstCreate = isOnCreateCalled && isResume;
        isOnCreateCalled = false;
        if (isFirstCreate && !isRestart && hasFocus) {
//            detailHome.setStreamHide(entity);
        }
        isResume = false;
        isRestart = false;
    }
}