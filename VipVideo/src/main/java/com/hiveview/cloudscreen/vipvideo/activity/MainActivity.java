package com.hiveview.cloudscreen.vipvideo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.Invoker;
import com.hiveview.cloudscreen.vipvideo.common.SizeConstant;
import com.hiveview.cloudscreen.vipvideo.common.VideoDetailInvoker;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;
import com.hiveview.cloudscreen.vipvideo.service.CloudScreenService;
import com.hiveview.cloudscreen.vipvideo.service.OnRequestResultListener;
import com.hiveview.cloudscreen.vipvideo.service.dao.HomeRecommendDao;
import com.hiveview.cloudscreen.vipvideo.service.entity.GetActivityEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.QuickMarkEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.RecommendListEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.VideoChannelEntity;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.Statistics;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.GetVIPActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.LauncherActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.LauncherBrowseActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.LoginDialogActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.QuickMarkBrowseInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.RenewDialogActionInfo;
import com.hiveview.cloudscreen.vipvideo.util.BitmapBlurUtil;
import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.util.Logger;
import com.hiveview.cloudscreen.vipvideo.util.StringUtils;
import com.hiveview.cloudscreen.vipvideo.util.ToastUtil;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;
import com.hiveview.cloudscreen.vipvideo.util.VideoChannelUtils;
import com.hiveview.cloudscreen.vipvideo.view.ChannelView;
import com.hiveview.cloudscreen.vipvideo.view.CloundMenuWindow;
import com.hiveview.cloudscreen.vipvideo.view.EffectiveMarqueeTextView;
import com.hiveview.cloudscreen.vipvideo.view.LauncherHeaderView;
import com.hiveview.cloudscreen.vipvideo.view.NHScreenView;
import com.hiveview.cloudscreen.vipvideo.view.RecommendView;
import com.hiveview.cloudscreen.vipvideo.view.VipQuickMarkDialog;
import com.hiveview.cloudscreen.vipvideo.view.VipVideoDialog;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Spr_ypt on 2015/11/3.
 */
public class MainActivity extends BaseActivity implements UserStateUtil.UserStatusWatcher {

    private static final String TAG = MainActivity.class.getSimpleName();
    /**
     * 顶部布局
     */
    private LauncherHeaderView headerView;
    /**
     * 包裹除背景图外的布局
     */
    private RelativeLayout upOrDown;
    /**
     * 推荐位滚动控件
     */
    private NHScreenView nhScreenView;
    /**
     * 推荐位滚动视图组件
     */
    private LinearLayout recommendview;
    /**
     * 除了背景推荐位外的其他推荐位布局
     */
    private RelativeLayout launchRecommend;
    /**
     * 第一个位置推荐位，位置以数字区分
     */
    private RecommendView place2;
    private RecommendView place3;
    private RecommendView place4;
    private RecommendView place5;
    private RecommendView place6;
    private RecommendView place7;
    private RecommendView place8;
    private RecommendView place9;
    /*
     * 推荐位的缓存集合,没有记录背景推荐位
     */
    private List<RecommendView> cachePics;
    /**
     * 用于存放按下应该跳到频道列表的view，用于channel记录
     */
    private List<View> channelUpViews;
    private ViewPropertyAnimator backgroundAnimator; // 背景的动画器
    private ViewPropertyAnimator backgroundBlurAnimator;//模糊背景动画器
    //    private ViewPropertyAnimator recommendAnimator; // 推荐试图动画器
    private ViewPropertyAnimator upOrDownAnimator; // 包裹视图动画器
    /**
     * 背景图左右移动比例
     * 【算法】位背景图超过屏幕的长度根据推荐位总数等分，移动了几个推荐位就平移几等分
     */
    private float ratio;
    /**
     * 尺寸工具,用于获取屏幕尺寸
     */
    private SizeConstant sizeConstant;
    /**
     * 推荐位滚动使用到的变量
     */
    private int lastId = 0; // 推荐位最后一位ID
    private int lastX;//最后一位的x坐标

    /**
     * 底部的分类视图
     */
    private ChannelView channelView;
    /**
     * 频道列表数据
     */
    private List<VideoChannelEntity> channelData;
    /**
     * Vip功能对话框
     */
    private VipVideoDialog vipVideoDialog;
    /**
     * 免费领取二维码弹框
     */
    private VipQuickMarkDialog vipQuickMarkDialog;
    /**
     * 提示登录对话框
     */
    private VipVideoDialog loginDialog;
    /**
     * 标记点击了登录，用于在回调中判断是否登录成功
     */
    private static boolean isLogining = false;
    /**
     * 续费弹窗
     */
    private VipVideoDialog renewDialog;
    /**
     * 标记点击了续费，用于在回调中判断是否续费成功
     */
    private static boolean isRenewing = false;
    /**
     * 所有可获取焦点View的集合
     */
    private List<View> focusableViews = new ArrayList<>();
    /**
     * 本地保存推荐数据
     */
    private HomeRecommendDao recommendDao = new HomeRecommendDao(this);
    /**
     * 整体布局，用于高斯模糊
     */
    private View mainView;
    /**
     * 背景推荐位相关
     */
    private ImageView background;
    /**
     * 模糊背景
     */
    private ImageView backgroundBlur;
    /**
     * 保存高斯模糊后的背景图
     */
    private Drawable blurBackground;
    /**
     * 保存背景图url
     */
    private String backgroundUrl;
    /**
     * 背景推荐位布局
     */
    private RelativeLayout place1;
    /**
     * 背景推荐位标题文本框
     */
    private EffectiveMarqueeTextView place1Title;
    /**
     * 背景推荐位内容文本框
     */
    private EffectiveMarqueeTextView place1Content;
    /**
     * 用于处理本activity的内部消息
     */
    private LauncherHandler launcherHandler;

    /**
     * 3.2 免费包数据类
     */
    private GetActivityEntity freeActivityEntity;


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
    /**
     * 标记该页面是否加载完成了，完成前控制按键事件防止页面错乱
     */
    private boolean isLauncherReady = false;
    /**
     * 用于记录第一次按返回键的时间，短时间内2次返回才推出程序
     */
    private long exitTime = 0;
    /**
     * 用于鉴别是否应该模糊背景，主要
     */
    private boolean shouldShowBlur = true;

    /**
     * 用于鉴别是否是二维码领取(埋点统计)
     */
    private boolean isQuickMark = false;

    /**
     * 用于鉴别用户是不是真做了扫码、关注的操作
     * 去做用户领取或者提示的操作。
     *
     * @param savedInstanceState
     */
    private boolean isCompelete = false;
    //用户的创建时间戳
    private Long beginUseTime = null;
    //判断某日之后的领取条件
    private String afterData = null;
    //判断某日之前的领取条件
    private String beforeData = null;
    //是否与服务端建立连接
    private boolean mBound = false;
    //服务端消息管理者
    private Messenger serviceMessenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sizeConstant = new SizeConstant(this);
        //加载布局
        init();
        //初始化动画器
        initAnimator();
        //初始化菜单页面
        initMenu();
        //初始化消息
        launcherHandler = new LauncherHandler(this);
        //注册成为用户状态变化观察者
        UserStateUtil.getInstance().addWatcher(this);
        //初始化提示登录，领取vip等对话框
        createDialogs();
        //初始化页面滚动相关数据
        initBgScrollIndexes();
        //设置监听，这里只设置了无需请求数据的监听，一些监听需要在数据请求下来后设置
        setPreListeners();
        //开始处理数据，天才第一步
        new AsynDBTask().execute(this);
        //处理数据的同时先加载缓存数据
        new AsyncLoadDataCacheTask().execute(new WeakReference<MainActivity>(this));
        openTime = System.currentTimeMillis();
        //设置会员状态,在所有布局加载完成后设置会员状态
        resetFocusColor(UserStateUtil.getInstance().getUserStatus());
    }

    public ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceMessenger = new Messenger(service);
            mBound = true;
            getVersionCode(true);
            Log.i(TAG, "onServiceConnected  mBound==" + mBound);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceMessenger = null;
            mBound = false;
            Log.i(TAG, "onServiceDisconnected  mBound==" + mBound);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        String userAppCode = UserStateUtil.getInstance().version;
        Log.i(TAG, "userAppCode=" + userAppCode);
        if ("03".equals(userAppCode)) {
            Log.i(TAG, "onStart");
            Intent intent = new Intent();
            intent.setAction("com.hiveview.cloudscreen.user.MESSENGER_SERVICE");
            intent.setPackage("com.hiveview.cloudscreen.user");
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop  mBound==" + mBound);
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
     * isTrue ： true 首次进入大麦影视
     * false 不需要用户中心刷新数据
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


    /**
     * @Author Spr_ypt
     * @Date 2015/11/30
     * @Description 初始化背景图滚动相关参数
     */
    private void initBgScrollIndexes() {
        lastId = cachePics.get(cachePics.size() - 1).getId();
        //推荐位横向滚动式背景图滚动参数,这里设置没用，需要等数据加载完成
        ratio = 0;
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/30
     * @Description 加载监听，只包含不需要等待请求数据的监听
     */
    private void setPreListeners() {
        //头布局内按钮监听
        headerView.setBtnClickListener(new LauncherHeaderView.OnBtnClickListener() {
            @Override
            public void onLoginClick(UserStateUtil.UserStatus status) {
                BaseActionInfo info = new LauncherActionInfo("03", "sde0301", "1", "1", "1", null, null); //统计埋点
                new Statistics(MainActivity.this, info).send(); //统计埋点
                UserStateUtil.getInstance().dealLogin(MainActivity.this);
            }

            @Override
            public void onVipClick(UserStateUtil.UserStatus status) {
                BaseActionInfo info = new LauncherActionInfo("03", "sde0301", "1", "1", "2", null, null);//统计埋点
                new Statistics(MainActivity.this, info).send(); //统计埋点
                UserStateUtil.getInstance().becomeVip(MainActivity.this, "11");
            }

            @Override
            public void onViptipClick() {
                BaseActionInfo info = new LauncherActionInfo("03", "sde0301", "1", "1", "2", null, null);//统计埋点
                new Statistics(MainActivity.this, info).send(); //统计埋点
                UserStateUtil.getInstance().becomeVip(MainActivity.this, "11");
            }

            @Override
            public void onFilmListClick() {
                try {
                    Intent intent = new Intent(AppConstants.INTENT_FILM_LIST);
                    intent.putExtra("templateId", DeviceInfoUtil.getInstance().getDeviceInfo(getApplicationContext()).templetId + "");
                    intent.putExtra("apkPkgName", AppConstants.APK_PACKAGE_NAME);
                    intent.putExtra("userId", UserStateUtil.getInstance().getUserInfo().userId);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, "e=" + e.toString());
                    ToastUtil.showToast(CloudScreenApplication.getInstance().getApplicationContext(), "敬请期待", Toast.LENGTH_SHORT);
                }
            }
        });
        headerView.setOnRenewAlertListener(new LauncherHeaderView.OnRenewAlertListener() {
            @Override
            public void needRenew() {
                Logger.d(TAG, "needRenew");
                launcherHandler.sendEmptyMessageDelayed(LauncherHandler.SHOW_RENEW_DIALOG, LauncherHandler.SHOW_RENEW_DIALOG_DELAY);
            }

            @Override
            public void refreshUserInfo() {
                getVersionCode(false);
            }
        });
        //背景推荐位焦点监听，用于控制跑马灯和背景模糊
        place1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Logger.d(TAG, "place1.setOnFocusChanged=" + hasFocus);
                int[] location = new int[2];
                place1Title.setIsInFocusView(hasFocus);
                place1Content.setIsInFocusView(hasFocus);
                if (hasFocus) {
                    recommendview.getLocationInWindow(location);
//                    recommendAnimator.translationXBy(-location[0]);
                    nhScreenView.scrollX(location[0]);
                    background.getLocationInWindow(location);
                    if (location[0] != 0) {
                        backgroundAnimator.translationXBy(-location[0]);
                        backgroundBlurAnimator.translationXBy(-location[0]);
                    }
                    shouldShowBlur = false;
                    if (backgroundBlur.getAlpha() > 0f) {
                        ObjectAnimator.ofFloat(backgroundBlur, "alpha", 0f).start();//用这种方式比下面的方式能明显改善闪屏的情况，但仍存在
//                        backgroundBlurAnimator.alpha(0);
                    }
                }
            }
        });
        //设置推荐位的焦点监听，主要用来滚动推荐位
        for (RecommendView rv : cachePics) {
            rv.setOnRecommendFocusChangedListener(new OnRecommendFocusChangedListener(this));
        }
        channelView.setUpFocusCallBack(upFocus);
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/26
     * @Description 初始化该界面弹窗
     */
    private void createDialogs() {
        int templetId = DeviceInfoUtil.getInstance().getDeviceInfo(this).templetId;
        CloudScreenService.getInstance().getActivity(new FreeActivityListner(this), templetId, this.getPackageName());

        loginDialog = new VipVideoDialog(this, getString(R.string.dialog_login_tip), getString(R.string.dialog_login_ok), getString(R.string.dialog_login_cancel), VipVideoDialog.DialogBg.D, false);
        loginDialog.setOnBtnClicklistener(new VipVideoDialog.OnBtnClickListener() {
            @Override
            public void onPositiveBtnClick() {
                showFocus();
                isLogining = true;
                noAction = "0";
                UserStateUtil.getInstance().dealLogin(MainActivity.this);
                loginDialog.dismiss();
            }

            @Override
            public void onNegativeBtnClick() {
                showFocus();
                BaseActionInfo info = new LoginDialogActionInfo("03", "vip0306", "1", "3", null);//统计埋点
                new Statistics(MainActivity.this, info).send();//统计埋点
                noAction = "0";
                loginDialog.dismiss();
            }

            @Override
            public void onDismissWithoutPressBtn() {
                showFocus();
                noAction = "0";
                BaseActionInfo info = new LoginDialogActionInfo("03", "vip0306", "1", "3", null);//统计埋点
                new Statistics(MainActivity.this, info).send();//统计埋点
            }
        });
        renewDialog = new VipVideoDialog(this, getString(R.string.dialog_renew_tip), getString(R.string.dialog_renew_ok), getString(R.string.dialog_renew_cancel), VipVideoDialog.DialogBg.VIP, false);
        renewDialog.setOnBtnClicklistener(new VipVideoDialog.OnBtnClickListener() {
            @Override
            public void onPositiveBtnClick() {
                showFocus();
                isRenewing = true;
                noAction = "0";
                BaseActionInfo info = new RenewDialogActionInfo("03", "vip0308", "1");//统计埋点
                new Statistics(MainActivity.this, info).send();//统计埋点
                UserStateUtil.getInstance().becomeVip(MainActivity.this, "15");
                renewDialog.dismiss();

            }

            @Override
            public void onNegativeBtnClick() {
                showFocus();
                BaseActionInfo info = new RenewDialogActionInfo("03", "vip0308", "2");//统计埋点
                new Statistics(MainActivity.this, info).send();//统计埋点
                noAction = "0";
                renewDialog.dismiss();

            }

            @Override
            public void onDismissWithoutPressBtn() {
                showFocus();
                noAction = "0";
                BaseActionInfo info = new RenewDialogActionInfo("03", "vip0308", "2");//统计埋点
                new Statistics(MainActivity.this, info).send();//统计埋点
            }
        });
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/30
     * @Description 获取免费包数据监听
     */
    private class FreeActivityListner implements OnRequestResultListener<GetActivityEntity> {

        private WeakReference<MainActivity> refrence;

        public FreeActivityListner(MainActivity activity) {
            refrence = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void onSucess(final GetActivityEntity entity) {
            final MainActivity activity = refrence.get();
            if (null != entity && null != entity.getName()) {
                Log.i(TAG, "FreeActivityListner onSucess");
                activity.freeActivityEntity = entity;
                if (null != loginDialog) {
                    activity.loginDialog.setViewFeature(activity, entity.getName() + activity.getString(R.string.dialog_login_tip_has_free), activity.getString(R.string.dialog_login_ok), activity.getString(R.string.dialog_login_cancel), VipVideoDialog.DialogBg.D, false);
                } else {
                    activity.loginDialog = new VipVideoDialog(activity, entity.getName() + activity.getString(R.string.dialog_login_tip_has_free), activity.getString(R.string.dialog_login_ok), activity.getString(R.string.dialog_login_cancel), VipVideoDialog.DialogBg.D, false);
                }
                activity.loginDialog.setOnBtnClicklistener(new VipVideoDialog.OnBtnClickListener() {
                    @Override
                    public void onPositiveBtnClick() {
                        activity.showFocus();
                        activity.isLogining = true;
                        activity.noAction = "0";
                        UserStateUtil.getInstance().dealLogin(MainActivity.this);
                        activity.loginDialog.dismiss();
                    }

                    @Override
                    public void onNegativeBtnClick() {
                        activity.showFocus();
                        BaseActionInfo info = new LoginDialogActionInfo("03", "vip0306", "1", "3", null);//统计埋点
                        new Statistics(MainActivity.this, info).send();//统计埋点
                        activity.noAction = "0";
                        activity.loginDialog.dismiss();
                    }

                    @Override
                    public void onDismissWithoutPressBtn() {
                        activity.showFocus();
                        activity.noAction = "0";
                        BaseActionInfo info = new LoginDialogActionInfo("03", "vip0306", "1", "3", null);//统计埋点
                        new Statistics(MainActivity.this, info).send();//统计埋点
                    }
                });
                activity.vipVideoDialog = new VipVideoDialog(activity, entity.getName(), activity.getString(R.string.dialog_free_vip_ok), activity.getString(R.string.dialog_free_vip_cancel), VipVideoDialog.DialogBg.VIP, true);
                activity.vipVideoDialog.setOnBtnClicklistener(new VipVideoDialog.OnBtnClickListener() {
                    @Override
                    public void onPositiveBtnClick() {
                        Log.i(TAG, "onPositiveBtnClick");
                        activity.showFocus();
                        ToastUtil.showToast(activity, activity.getString(R.string.getting_vip_free), Toast.LENGTH_LONG);
                        activity.noAction = "0";
                        UserStateUtil.getInstance().becomeVipFree(MainActivity.this, new BecomeVipFreeListener(MainActivity.this), activity.freeActivityEntity);
                        activity.vipVideoDialog.dismiss();
                    }

                    @Override
                    public void onNegativeBtnClick() {
                        activity.showFocus();
                        BaseActionInfo info = new GetVIPActionInfo("03", "sde0307", "1", "3", freeActivityEntity.getId() + "", freeActivityEntity.getName());//统计埋点
                        new Statistics(activity, info).send();//统计埋点
                        activity.vipVideoDialog.dismiss();
                        activity.noAction = "0";
                    }

                    @Override
                    public void onDismissWithoutPressBtn() {
                        activity.showFocus();
                        BaseActionInfo info = new GetVIPActionInfo("03", "sde0307", "1", "3", freeActivityEntity.getId() + "", freeActivityEntity.getName());//统计埋点
                        new Statistics(activity, info).send();//统计埋点
                        activity.noAction = "0";
                    }
                });
            } else {
                Logger.e(TAG, "免费活动信息配置有误！");
            }
            activity.launcherHandler.removeMessages(LauncherHandler.SHOW_DIALOG);
            activity.launcherHandler.sendEmptyMessageDelayed(LauncherHandler.SHOW_DIALOG, LauncherHandler.SHOW_DIALOG_DELAY);
        }

        @Override
        public void onFail(Exception e) {
            Logger.e(TAG, "免费活动信息获取失败！onFail=" + e.toString());
            MainActivity activity = refrence.get();
            activity.launcherHandler.removeMessages(LauncherHandler.SHOW_DIALOG);
            activity.launcherHandler.sendEmptyMessageDelayed(LauncherHandler.SHOW_DIALOG, LauncherHandler.SHOW_DIALOG_DELAY);
        }

        @Override
        public void onParseFail(HiveviewException e) {
            Logger.e(TAG, "免费活动信息获取失败！onParseFail=" + e.toString());
            MainActivity activity = refrence.get();
            activity.launcherHandler.removeMessages(LauncherHandler.SHOW_DIALOG);
            activity.launcherHandler.sendEmptyMessageDelayed(LauncherHandler.SHOW_DIALOG, LauncherHandler.SHOW_DIALOG_DELAY);
        }
    }

    /**
     * 请求二维码图片 弹框显示
     *
     * @param context
     * @param entity
     */
    public void UserQuickMark(Context context, GetActivityEntity entity) {
        Log.i(TAG, "UserQuickMark");
        UserStateUtil.UserInfo userInfo = UserStateUtil.getInstance().getUserInfo();
        DeviceInfoUtil.DeviceInfo deviceInfo = DeviceInfoUtil.getInstance().getDeviceInfo(context);
        CloudScreenService.getInstance().postQuickMark(new QuickMarkListener(MainActivity.this, entity), userInfo, deviceInfo);
    }

    /**
     * 二维码生成监听
     */
    private class QuickMarkListener implements OnRequestResultListener<ResultEntity> {

        private WeakReference<MainActivity> reference;
        GetActivityEntity freeActivityEntity;

        public QuickMarkListener(MainActivity activity, GetActivityEntity freeActivityEntity) {
            reference = new WeakReference<MainActivity>(activity);
            this.freeActivityEntity = freeActivityEntity;
        }


        @Override
        public void onSucess(ResultEntity resultEntity) {
            final MainActivity activity = reference.get();
            QuickMarkEntity entity = (QuickMarkEntity) resultEntity.getEntity();
            Log.i(TAG, "ImageURL：" + entity.getImageURL());
            if (!TextUtils.isEmpty(entity.getImageURL()) && !TextUtils.isEmpty(freeActivityEntity.getName()) && !TextUtils.isEmpty(freeActivityEntity.getDesc())) {
                final long time = System.currentTimeMillis();//得到当前时间
                vipQuickMarkDialog = new VipQuickMarkDialog(activity, entity.getImageURL(), freeActivityEntity.getName(), freeActivityEntity.getDesc());
                vipQuickMarkDialog.setQuickMarkDialoglistener(new VipQuickMarkDialog.QuickMarkDialoglistener() {
                    @Override
                    public void onDismissWithoutPressBtn() {
                        //统计用户操作 返回键
                        BaseActionInfo info = new QuickMarkBrowseInfo("qr0101", "1", null, null, "1", freeActivityEntity.getId() + "", freeActivityEntity.getName());
                        new Statistics(activity, info).send();
                    }

                    @Override
                    public void onCompeleteBtnClick() {
                        vipQuickMarkDialog.dismiss();
                        //主动调取用户中心区刷新数据
                        activity.getVersionCode(false);
                        Log.i(TAG, "QuickMarkListener hasOpenId:" + UserStateUtil.getInstance().getUserInfo().hasOpenId);
                        Log.i(TAG, "QuickMarkListener userPhone:" + UserStateUtil.getInstance().getUserInfo().userPhone);
                        isCompelete = true;
                    }

                    @Override
                    public void onCancelBtnClick() {
                        //统计用户操作 取消键
                        BaseActionInfo info = new QuickMarkBrowseInfo("qr0101", "1", null, null, "1", freeActivityEntity.getId() + "", freeActivityEntity.getName());
                        new Statistics(activity, info).send();
                        vipQuickMarkDialog.dismiss();
                    }
                });
                vipQuickMarkDialog.showWindow();
                BaseActionInfo info = new QuickMarkBrowseInfo("qr0101", "0", null, null, "1", freeActivityEntity.getId() + "", freeActivityEntity.getName());
                new Statistics(activity, info).send();
            }
        }

        @Override
        public void onFail(Exception e) {
            Logger.e(TAG, "QuickMarkListener.onFail e=" + e.toString());
        }

        @Override
        public void onParseFail(HiveviewException e) {
            Logger.e(TAG, "QuickMarkListener.onParseFail");
        }
    }


    /**
     * @Author Spr_ypt
     * @Date 2015/11/30
     * @Description 免费领取vip资格监听
     */
    private class BecomeVipFreeListener implements OnRequestResultListener<ResultEntity> {

        private WeakReference<MainActivity> reference;

        public BecomeVipFreeListener(MainActivity activity) {
            reference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void onSucess(ResultEntity entity) {
            MainActivity activity = reference.get();
            if (entity.str2.equals("N000000")) {
                Logger.i(TAG, "BecomeVipFreeListener.onSucess.N000000");
                activity.getVersionCode(false);
                launcherHandler.sendEmptyMessageDelayed(LauncherHandler.REFRESH_USER_STATUS, LauncherHandler.REFRESH_USER_STATUS_DELAY);
                ToastUtil.showBecomeVipSuccess(activity, Toast.LENGTH_LONG);
                if (isQuickMark) {
                    BaseActionInfo info = new GetVIPActionInfo("03", "sde0307", "16", "1", freeActivityEntity.getId() + "", freeActivityEntity.getName());//二维码统计埋点
                    new Statistics(activity, info).send();//统计埋点
                } else {
                    BaseActionInfo info = new GetVIPActionInfo("03", "sde0307", "1", "1", freeActivityEntity.getId() + "", freeActivityEntity.getName());//统计埋点
                    new Statistics(activity, info).send();//统计埋点
                }

            } else {
                Logger.i(TAG, "BecomeVipFreeListener.onSucess.NO.000 str2=" + entity.str2 + "||error=" + entity.errorCode);
                //领取vip免费包，重复领取失败提示
                ToastUtil.showToast(activity, activity.getString(R.string.get_vip_free_fail), Toast.LENGTH_SHORT);
                if (isQuickMark) {
                    BaseActionInfo info = new GetVIPActionInfo("03", "sde0307", "16", "2", freeActivityEntity.getId() + "", freeActivityEntity.getName());//二维码统计埋点
                    new Statistics(activity, info).send();//统计埋点
                } else {
                    BaseActionInfo info = new GetVIPActionInfo("03", "sde0307", "1", "2", freeActivityEntity.getId() + "", freeActivityEntity.getName());//统计埋点
                    new Statistics(activity, info).send();//统计埋点
                }
            }
//                //end by tianyejun
        }

        @Override
        public void onFail(Exception e) {
            Logger.e(TAG, "BecomeVipFreeListener.onFail e=" + e.toString());
            MainActivity activity = reference.get();
            ToastUtil.showToast(activity, activity.getString(R.string.get_vip_free_fail), Toast.LENGTH_SHORT);
        }

        @Override
        public void onParseFail(HiveviewException e) {
            Logger.e(TAG, "BecomeVipFreeListener.onParseFail");
            MainActivity activity = reference.get();
            ToastUtil.showToast(activity, activity.getString(R.string.get_vip_free_fail), Toast.LENGTH_SHORT);
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/18
     * @Description activity的方法，当这个方法被回调时，是真正的页面被显示出来的时刻，开场动画需要在这个开始
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Logger.d(TAG, "onWindowFocusChanged");

        if (launchRecommend.getTranslationX() != 0) {
            Logger.d(TAG, "onWindowFocusChanged.anim()");
            //延迟1秒再执行动画
            launcherHandler.removeMessages(LauncherHandler.START_RECOMMENG_ANIM);
            launcherHandler.sendEmptyMessageDelayed(LauncherHandler.START_RECOMMENG_ANIM, LauncherHandler.START_RECOMMENG_ANIM_DELAY_TIME);
            place1.requestFocus();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (!isLauncherReady) {
            return true;
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Logger.d(TAG, "MainActivity.KEYCODE_BACK");
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), getString(R.string.launcher_exit_tip), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    /**
     * @Author Spr_ypt
     * @Date 2015/11/9
     * @Description 初始化菜单，添加我的收藏、历史记录
     */
    private void initMenu() {
        List<CloundMenuWindow.MenuItemEntity> menuItems = new ArrayList<CloundMenuWindow.MenuItemEntity>();
        CloundMenuWindow.MenuItemEntity menuCategoryFilter;
        menuCategoryFilter = new CloundMenuWindow.MenuItemEntity();
        menuCategoryFilter.setItemName(getString(R.string.my_collect));
        menuCategoryFilter.setItemIconResId(R.drawable.ic_menu_category_collect_nomal);
        menuCategoryFilter.setItemIconFocusResId(R.drawable.ic_menu_category_collect_focus);
        menuItems.add(menuCategoryFilter);

        menuCategoryFilter = new CloundMenuWindow.MenuItemEntity();
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
     * @Author Spr_ypt
     * @Date 2015/11/5
     * @Description 加载布局，主要是findview的过程
     */
    private void init() {
        mainView = findViewById(R.id.mainview);
        mainView.setBackgroundResource(ResourceProvider.getInstance().getBgLauncher());

        headerView = (LauncherHeaderView) findViewById(R.id.v_launcher_header_view);

        background = (ImageView) findViewById(R.id.background);
        backgroundBlur = (ImageView) findViewById(R.id.background_blur);

        nhScreenView = (NHScreenView) findViewById(R.id.nh_main_recommend);
        recommendview = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_main_recommend, null);
        nhScreenView.addScreenView(recommendview);
        place1Title = (EffectiveMarqueeTextView) recommendview.findViewById(R.id.txt_launcher_place1_title);
        place1Content = (EffectiveMarqueeTextView) recommendview.findViewById(R.id.txt_launcher_place1_content);
        //今日推荐中英文环境适配
        ImageView recommendColor = (ImageView) recommendview.findViewById(R.id.recommend_color);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recommendColor.getLayoutParams();
        if (StringUtils.isZh(this)) {
            layoutParams.leftMargin = (int) getResources().getDimension(R.dimen.lanucher_today_recommend_leftMargin_zh);
        } else {
            layoutParams.leftMargin = (int) getResources().getDimension(R.dimen.lanucher_today_recommend_leftMargin_sea);
        }
        recommendColor.setLayoutParams(layoutParams);

        place1 = (RelativeLayout) recommendview.findViewById(R.id.place1);
        place1.setFocusable(true);//4test
        focusableViews.add(place1);
        cachePics = new ArrayList<>();
        place2 = (RecommendView) recommendview.findViewById(R.id.place2);
        place2.setDefaultImage(ResourceProvider.getInstance().getBgLauncherPortraitLow());
        focusableViews.add(place2);
        cachePics.add(place2);
        place3 = (RecommendView) recommendview.findViewById(R.id.place3);
        place3.setDefaultImage(ResourceProvider.getInstance().getBgLauncherLandscapeShort());
        focusableViews.add(place3);
        cachePics.add(place3);
        place4 = (RecommendView) recommendview.findViewById(R.id.place4);
        place4.setDefaultImage(ResourceProvider.getInstance().getBgLauncherPortraitHigh());
        focusableViews.add(place4);
        cachePics.add(place4);
        place5 = (RecommendView) recommendview.findViewById(R.id.place5);
        place5.setDefaultImage(ResourceProvider.getInstance().getBgLauncherCupe());
        focusableViews.add(place5);
        cachePics.add(place5);
        place6 = (RecommendView) recommendview.findViewById(R.id.place6);
        place6.setDefaultImage(ResourceProvider.getInstance().getBgLauncherPortraitHigh());
        focusableViews.add(place6);
        cachePics.add(place6);
        place7 = (RecommendView) recommendview.findViewById(R.id.place7);
        place7.setDefaultImage(ResourceProvider.getInstance().getBgLauncherLandscapeLong());
        focusableViews.add(place7);
        cachePics.add(place7);
        place8 = (RecommendView) recommendview.findViewById(R.id.place8);
        place8.setDefaultImage(ResourceProvider.getInstance().getBgLauncherLandscapeLong());
        focusableViews.add(place8);
        cachePics.add(place8);
        place9 = (RecommendView) recommendview.findViewById(R.id.place9);
        place9.setDefaultImage(ResourceProvider.getInstance().getBgLauncherPortraitHigh());
        focusableViews.add(place9);
        cachePics.add(place9);

        launchRecommend = (RelativeLayout) recommendview.findViewById(R.id.launch_recommend);
        upOrDown = (RelativeLayout) findViewById(R.id.upordown);

        headerView.setBtnDownView(place1);

        channelView = (ChannelView) findViewById(R.id.channel_view);
        channelUpViews = new ArrayList<>();
        channelUpViews.add(place1);
        channelUpViews.add(place3);
        channelUpViews.add(place4);
        channelUpViews.add(place5);
        channelUpViews.add(place6);
        channelUpViews.add(place8);
        channelUpViews.add(place9);
        channelView.setRecommendView(channelUpViews); //默认频道记忆(修复在ip失效时无频道记忆)

    }

    /**
     * @author xieyi
     * @Description 初始化动画器
     */
    private void initAnimator() {
        backgroundAnimator = background.animate().setDuration(700).setInterpolator(new DecelerateInterpolator());
        backgroundBlurAnimator = backgroundBlur.animate().setDuration(700).setInterpolator(new DecelerateInterpolator());
        upOrDownAnimator = upOrDown.animate().setDuration(400).setInterpolator(new DecelerateInterpolator());
        launchRecommend.setTranslationX(sizeConstant.getBoxWidth() - launchRecommend.getX());
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/28
     * @Description UserStateUtil.UserStatusWatcher回调接口，注册后，本类可以成为用户状态的观察者
     */
    @Override
    public void userStatusChanged() {
        Log.i(TAG, "MainActivity  userStatusChanged");
        View currentFocus = getCurrentFocus();//保持页面焦点不变
        resetFocusColor(UserStateUtil.getInstance().getUserStatus());
        //设置头控件vip状态
        if (null != headerView) {
            headerView.setUserStatus(UserStateUtil.getInstance().getUserStatus());
        }
        //用户状态改变时，关闭没有必要的弹窗
        if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
            if (null != loginDialog && loginDialog.isShowing()) {
                loginDialog.dismiss();
            }
            if (null != renewDialog && renewDialog.isShowing()) {
                renewDialog.dismiss();
            }
            if (null != vipVideoDialog && vipVideoDialog.isShowing()) {
                vipVideoDialog.dismiss();
            }
        } else if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.NOMALUSER) {
            if (null != loginDialog && loginDialog.isShowing()) {
                loginDialog.dismiss();
            }
        }
        //防止领取活动弹框重复弹出
        if (null != vipQuickMarkDialog && vipQuickMarkDialog.isShowing()) {
            vipQuickMarkDialog.dismiss();
        }
        launcherHandler.removeMessages(LauncherHandler.SHOW_DIALOG);
        launcherHandler.sendEmptyMessageDelayed(LauncherHandler.SHOW_DIALOG, LauncherHandler.SHOW_DIALOG_DELAY);
        if (isLogining) {
            isLogining = false;
            if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.NOLOGIN) {//登录失败
                BaseActionInfo info = new LoginDialogActionInfo("03", "vip0306", "1", "2", null);//统计埋点
                new Statistics(MainActivity.this, info).send();//统计埋点
            } else {//登录成功
                BaseActionInfo info = new LoginDialogActionInfo("03", "vip0306", "1", "1", null);//统计埋点
                new Statistics(MainActivity.this, info).send();//统计埋点
            }
        }
        if (null != currentFocus) {
            currentFocus.requestFocus();
        }
    }

    @Override
    public void userStatusNoChanged() {
        Log.i(TAG, "userStatusNoChanged");
        // 此回调方法是为了防止 在二维码活动当中，用户不扫描二维码而直接点击完成领取出现刷新数据
        notityUserInfo();
    }


    /**
     * 判断用户信息是否满足条件，满足并请求领取接口，开通vip
     */
    private void notityUserInfo() {
        if (isCompelete) {
            Log.i(TAG, "是否成功关注:" + UserStateUtil.getInstance().getUserInfo().hasOpenId);
            Log.i(TAG, "是否成功绑定:" + UserStateUtil.getInstance().getUserInfo().userPhone);
            isCompelete = false;
            if (freeActivityEntity.getConditionType() == 2) {
                //2  关注微信领取
                if (UserStateUtil.getInstance().getUserInfo().hasOpenId.equals("1")) {
                    vipQuickMarkDialog.dismiss();
                    isQuickMark = true;
                    ToastUtil.showToast(this, this.getString(R.string.getting_vip_free), Toast.LENGTH_LONG);

                    //统计用户操作 点击完成发送埋点 校验成功
                    BaseActionInfo info = new QuickMarkBrowseInfo("qr0101", "0", "1", "1", "1", freeActivityEntity.getId() + "", freeActivityEntity.getName());
                    new Statistics(this, info).send();

                    UserStateUtil.getInstance().becomeVipFree(MainActivity.this, new BecomeVipFreeListener(MainActivity.this), this.freeActivityEntity);
                } else {
                    ToastUtil.showToast(this, this.getString(R.string.get_vip_free_not_satisfy), Toast.LENGTH_SHORT);

                    //统计用户操作 点击完成发送埋点 校验失败
                    BaseActionInfo info = new QuickMarkBrowseInfo("qr0101", "0", "2", "1", "1", freeActivityEntity.getId() + "", freeActivityEntity.getName());
                    new Statistics(this, info).send();
                }
            } else if (freeActivityEntity.getConditionType() == 3) {
                // 3 关注手机领取
                if (!TextUtils.isEmpty(UserStateUtil.getInstance().getUserInfo().userPhone)) {
                    vipQuickMarkDialog.dismiss();
                    isQuickMark = true;
                    ToastUtil.showToast(this, this.getString(R.string.getting_vip_free), Toast.LENGTH_LONG);

                    //统计用户操作 点击完成发送埋点  校验成功
                    BaseActionInfo info = new QuickMarkBrowseInfo("qr0101", "0", "1", "2", "1", freeActivityEntity.getId() + "", freeActivityEntity.getName());
                    new Statistics(this, info).send();

                    UserStateUtil.getInstance().becomeVipFree(MainActivity.this, new BecomeVipFreeListener(MainActivity.this), this.freeActivityEntity);
                } else {
                    ToastUtil.showToast(this, this.getString(R.string.get_vip_free_not_satisfy), Toast.LENGTH_SHORT);

                    //统计用户操作 点击完成发送埋点 校验失败
                    BaseActionInfo info = new QuickMarkBrowseInfo("qr0101", "0", "2", "2", "1", freeActivityEntity.getId() + "", freeActivityEntity.getName());
                    new Statistics(this, info).send();
                }
            } else if (freeActivityEntity.getConditionType() == 4) {
                // 4 关注公众账号并且绑定手机号
                if (UserStateUtil.getInstance().getUserInfo().hasOpenId.equals("1") && !TextUtils.isEmpty(UserStateUtil.getInstance().getUserInfo().userPhone)) {
                    vipQuickMarkDialog.dismiss();
                    isQuickMark = true;
                    ToastUtil.showToast(this, this.getString(R.string.getting_vip_free), Toast.LENGTH_LONG);

                    //统计用户操作 点击完成发送埋点  校验成功
                    BaseActionInfo info = new QuickMarkBrowseInfo("qr0101", "0", "1", "3", "1", freeActivityEntity.getId() + "", freeActivityEntity.getName());
                    new Statistics(this, info).send();

                    UserStateUtil.getInstance().becomeVipFree(MainActivity.this, new BecomeVipFreeListener(MainActivity.this), this.freeActivityEntity);
                } else {
                    ToastUtil.showToast(this, this.getString(R.string.get_vip_free_not_satisfy), Toast.LENGTH_SHORT);

                    //统计用户操作 点击完成发送埋点 校验失败
                    BaseActionInfo info = new QuickMarkBrowseInfo("qr0101", "0", "2", "3", "1", freeActivityEntity.getId() + "", freeActivityEntity.getName());
                    new Statistics(this, info).send();
                }
            }
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/5
     * @Description 推荐位焦点监听，主要用于处理推荐位的滚动
     */
    private static class OnRecommendFocusChangedListener implements RecommendView.OnRecommendFocusChangedListener {

        private WeakReference<MainActivity> reference;

        public OnRecommendFocusChangedListener(MainActivity activity) {
            reference = new WeakReference<MainActivity>(activity);
        }

        @SuppressLint("NewApi")
        @Override
        public void onChanged(View v, boolean hasFocus) {
            MainActivity activity = reference.get();
            RecommendView recommend = (RecommendView) v;
            int[] location = new int[2];
            // 获取到焦点
            if (hasFocus) {
                float offset = 0; // 位移距离
                // 实例一个数组用来存放该图片在当前窗口里的绝对坐标
                v.getLocationInWindow(location);
                //移动到前2个推荐位的情况
                if (v.getId() == R.id.place2 || v.getId() == R.id.place3) {
                    //获取整个推荐位布局的横坐标，然后移动来达到初始化位置的作用
                    activity.launchRecommend.getLocationInWindow(location);
//                    activity.recommendAnimator.translationXBy(-location[0]);
                    //陈老师牌滚动控件滚动
                    activity.nhScreenView.scrollX(location[0]);

                    activity.background.getLocationInWindow(location);
                    //背景移动
                    if (location[0] != 0) {
                        activity.backgroundAnimator.translationXBy(-location[0]);
                        activity.backgroundBlurAnimator.translationXBy(-location[0]);
                    }
                    activity.shouldShowBlur = true;
                    if (activity.backgroundBlur.getAlpha() < 1f) {
                        if (null != activity.blurBackground) {
                            ObjectAnimator.ofFloat(activity.backgroundBlur, "alpha", 1f).start();
                        } else {
                            activity.buildBlurBg(activity.backgroundUrl);
                        }
                    }


                }
                //移动到的推荐位超出屏幕右边
                // 绝对X坐标+图片宽度>盒子宽度：说明图片超出了屏幕范围并且位于屏幕最右边，就要对组件进行向左位移
                else if (v.getWidth() + location[0] >= activity.sizeConstant.getBoxWidth()) {
                    // 焦点处于最后一个位置图片，位移出整张图片并且多余40宽度用于光圈展示
                    if (v.getId() == activity.lastId) {
                        Logger.i(activity.TAG, "lastId----> in OnRecommendFocusChangedListener :" + v.getId());
                        activity.backgroundAnimator.translationX(activity.sizeConstant.getBoxWidth() - activity.background.getWidth());
                        activity.backgroundBlurAnimator.translationX(activity.sizeConstant.getBoxWidth() - activity.background.getWidth());
                    } else {// 剩下的需要位移的图片，只需完整位移出图片
                        if (activity.background.getTranslationX() - activity.ratio > activity.sizeConstant.getBoxWidth() - activity.background.getWidth()) {
                            //确保背景滚动不会超出背景图
                            activity.backgroundAnimator.translationXBy(-activity.ratio);
                            activity.backgroundBlurAnimator.translationXBy(-activity.ratio);
                        }
                    }
                }
                //当向左移动焦点，焦点到达的推荐位超出左边屏幕，且不是2、3号推荐位
                // 绝对X坐标小于0：说明图片超出了左边屏幕，组件需要向右滚动
                else if (location[0] <= 0) {
                    // 焦点处于第一个位置图片
                    if (v.getId() == R.id.place1) {
                        activity.backgroundAnimator.translationXBy(-activity.background.getTranslationX());
                        activity.backgroundBlurAnimator.translationXBy(-activity.background.getTranslationX());
                    } else {
                        if (activity.background.getTranslationX() + activity.ratio < 0) {//确保背景滚动不会超出背景图
                            activity.backgroundAnimator.translationXBy(activity.ratio);
                            activity.backgroundBlurAnimator.translationXBy(activity.ratio);
                        }
                    }
                }
                //焦点移动到的推荐位全部展示在当前窗口，此时只需要移动背景图
                else {
                    int x = location[0];
                    if (x > activity.lastX) { // 如果当前视图X坐标大于上个位置的X坐标，说明焦点是向右移动
                        if (v.getId() != activity.lastId) {
                            if (activity.background.getTranslationX() + activity.ratio < 0) {//确保背景滚动不会超出背景图
                                activity.backgroundAnimator.translationXBy(activity.ratio);
                                activity.backgroundBlurAnimator.translationXBy(activity.ratio);
                            }
                        } else {
                            activity.backgroundAnimator.translationX(activity.sizeConstant.getBoxWidth() - activity.background.getWidth());
                            activity.backgroundBlurAnimator.translationX(activity.sizeConstant.getBoxWidth() - activity.background.getWidth());
                        }

                    } else if (x < activity.lastX) {
                        if (v.getId() != R.id.place1) {
                            if (activity.background.getTranslationX() + activity.ratio < 0) {//确保背景滚动不会超出背景图
                                activity.backgroundAnimator.translationXBy(activity.ratio);
                                activity.backgroundBlurAnimator.translationXBy(activity.ratio);
                            }
                        } else {
                            if (activity.background.getTranslationX() - activity.ratio > activity.sizeConstant.getBoxWidth() - activity.background.getWidth()) {
                                //确保背景滚动不会超出背景图
                                activity.backgroundAnimator.translationXBy(-activity.ratio);
                                activity.backgroundBlurAnimator.translationXBy(-activity.ratio);
                            }
                        }
                    }

                }
                recommend.displayBottom();
                /*************** 获取焦点时做放大动画 *****************/
                v.animate().scaleX(1.05f).scaleY(1.05f).setDuration(200).setInterpolator(new DecelerateInterpolator());
            } else {
                recommend.hideBottom();
                v.getLocationInWindow(location);
                activity.lastX = location[0];
                /*************** 失去焦点时做缩小动画 *****************/
                v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).setInterpolator(new DecelerateInterpolator());
            }

        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/18
     * @Description 推荐位的按键监听，主要处理向下按键时的焦点逻辑
     */
    private static class OnRecommendKeyListener implements View.OnKeyListener {
        private WeakReference<MainActivity> reference;

        public OnRecommendKeyListener(MainActivity activity) {
            reference = new WeakReference<MainActivity>(activity);

        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            MainActivity activity = reference.get();
            if (null != activity) {
                if (KeyEvent.ACTION_DOWN == event.getAction()) {
                    int[] location = new int[2];
                    int id = v.getId();
                    switch (event.getKeyCode()) {
                        case KeyEvent.KEYCODE_DPAD_DOWN:

                            Log.i(TAG, " id----> in onkey :" + id);

                            if (id != activity.cachePics.get(0).getId() && id != activity.cachePics.get(5).getId()) {

                                location = new int[2];
                                activity.channelView.getLocationInWindow(location);
                                float offset = (float) (location[1] + activity.channelView.getHeight() - activity.sizeConstant.getBoxHeight());
                                activity.channelView.setVisibility(View.VISIBLE);
                                activity.channelView.setFirstRowUpId(id);
                                activity.upOrDownAnimator.translationYBy(-offset);
                                activity.backgroundAnimator.translationY(activity.sizeConstant.getBoxHeight() - activity.background.getMeasuredHeight());
                                activity.backgroundBlurAnimator.translationY(activity.sizeConstant.getBoxHeight() - activity.background.getMeasuredHeight());
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_UP:
                            if (id == activity.place1.getId()) {
                                if (activity.headerView.setFocus()) {
                                    activity.headerView.setBtnDownView(v);
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                            //第2、7推荐位向上时并不会移动到headview中
                            if (id != activity.cachePics.get(1).getId() && id != activity.cachePics.get(6).getId()) {
                                Logger.d(TAG, "v=" + v.getId());
                                activity.headerView.setBtnDownView(v);
                            }
                            break;
                    }
                }

            }

            return false;
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/18
     * @Description 推荐位的点击事件监听，主要是打开详情页的逻辑
     */
    private static class OnRecommendClickListener implements View.OnClickListener {

        private WeakReference<MainActivity> reference;

        private OnRecommendClickListener(MainActivity activity) {
            reference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void onClick(View v) {
            MainActivity activity = reference.get();
            activity.noAction = "0";

            RecommendListEntity entity = (RecommendListEntity) v.getTag();

            if (null == entity) {
                ToastUtil.showToast(activity.getApplicationContext(), activity.getResources().getString(R.string.data_not_completed), Toast.LENGTH_LONG);
                return;
            }

            if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                BaseActionInfo info = new LauncherActionInfo("03", "sde0301", "3", "1", null, null, entity.getPosition() + "");//统计埋点
                new Statistics(activity, info).send();//统计埋点
            } else {
                BaseActionInfo info = new LauncherActionInfo("03", "sde0301", "3", "2", null, null, entity.getPosition() + "");//统计埋点
                new Statistics(activity, info).send();//统计埋点
            }

            //判定启动程序的action
            String action = "";
            switch (entity.getContentType()) {
                case VideoDetailInvoker.ContentType.TYPE_SUBJECT:
                    action = VideoDetailInvoker.ACTION_SUBJECTDETAIL;
                    Intent detail = new Intent(action);
                    detail.putExtra(AppConstants.EXTRA_SUBJECT_ID, entity.getContentId());
                    detail.putExtra(AppConstants.EXTRA_SUBJECT_BACKGROUND, entity.getContentImg());
                    activity.startActivity(detail);
                    break;
                case VideoDetailInvoker.ContentType.TYPE_VIDEO:
                    action = VideoDetailInvoker.getInstance().getDetailActivityAction(entity.getCid());
                    Logger.d(TAG, "detail action=" + action);
                    if (null != entity.getVideoId()) {//存在具体剧集ID的影片直接播放
                        action = VideoDetailInvoker.ACTION_PLAYER;
                    } else {
                        action = VideoDetailInvoker.getInstance().getDetailActivityAction(entity.getCid());
                    }
                    //根据返回的跳转类型跳转播放器或详情页
                    if (action != VideoDetailInvoker.ACTION_PLAYER) {
                        activity.BlurBackground();
                        Intent intentDetail = new Intent(action);
                        intentDetail.putExtra(AppConstants.EXTRA_VIDEOSET_ID, entity.getContentId());
                        intentDetail.putExtra(AppConstants.EXTRA_CID, entity.getCid());
                        intentDetail.putExtra(AppConstants.EXTRA_SOURCE, "1");
                        activity.startActivity(intentDetail);
                    } else {
                        Intent intent = new Intent(VideoDetailInvoker.ACTION_PLAYER);
                        Logger.d(TAG, "EXTRA_VIDEOSET_ID=" + entity.getContentId());
                        intent.putExtra(AppConstants.EXTRA_VIDEOSET_ID, entity.getContentId() + "");
                        if (null != entity.getVideoId()) {
                            Logger.d(TAG, "EXTRA_VIDEO_ID=" + entity.getVideoId());
                            intent.putExtra(AppConstants.EXTRA_VIDEO_ID, entity.getVideoId());
                        }
                        intent.putExtra(AppConstants.EXTRA_IS_FROM_ONLY_ID, true);
                        intent.putExtra(AppConstants.EXTRA_SOURCE, "1");
                        try {
                            activity.startActivity(intent);
                        } catch (Exception e) {
                            ToastUtil.showToast(activity, "找不到播放器控件", Toast.LENGTH_SHORT);
                        }
                    }
                    break;
                case VideoDetailInvoker.ContentType.TYPE_CAROUSEL:
                case VideoDetailInvoker.ContentType.TYPE_DLIVE:
                    action = VideoDetailInvoker.ACTION_DLIVE;
                    Intent intentDlive = new Intent(action);
                    intentDlive.putExtra(AppConstants.EXTRA_DLIVE_ID, entity.getContentId());
                    activity.startActivity(intentDlive);
                    break;
                case VideoDetailInvoker.ContentType.TYPE_ACTIVITY:
                    action = VideoDetailInvoker.ACTION_ACTIVITY;
                    Intent intentActivity = new Intent(action);
                    intentActivity.putExtra(AppConstants.EXTRA_ACTION_ID, entity.getContentId());
                    activity.startActivity(intentActivity);
                    break;
                case VideoDetailInvoker.ContentType.TYPE_APK:
                    action = VideoDetailInvoker.ACTION_APK;
                    Intent intentApk = new Intent(action);
                    intentApk.putExtra(AppConstants.ACTIVITY_DETAIL_ACTIVITY_ID, entity.getContentId() + "");
                    try {
                        activity.startActivity(intentApk);
                    } catch (Exception e) {
                        ToastUtil.showToast(activity, "找不到抽奖活动控件", Toast.LENGTH_SHORT);
                    }
                    break;
                case VideoDetailInvoker.ContentType.TYPE_HOT_WORLD:
                    action = VideoDetailInvoker.ACTION_HOT_WORD;
                    Intent intentHotWord = new Intent(action);
                    intentHotWord.putExtra(AppConstants.LIST_CHANNEL_ID, entity.getCid());
                    intentHotWord.putExtra(AppConstants.LIST_TITLE, entity.getCname());
                    intentHotWord.putExtra(AppConstants.LIST_HOTWORD_ID, entity.getContentId());
                    activity.startActivity(intentHotWord);
                    break;
                case VideoDetailInvoker.ContentType.TYPE_GOODS_PAG:
                    action = VideoDetailInvoker.ACTION_GOODS_PAG;
                    Intent intentGoods = new Intent(action);
                    intentGoods.putExtra("target", 0);
                    intentGoods.putExtra("userId", UserStateUtil.getInstance().getUserInfo().userId);
                    intentGoods.putExtra("packingId", entity.getContentId() + "");
                    intentGoods.putExtra("templateId", DeviceInfoUtil.getInstance().getDeviceInfo(activity).templetId + "");//模板id
                    intentGoods.putExtra("packageName", AppConstants.APK_PACKAGE_NAME);//包名
                    activity.startActivity(intentGoods);
                    break;
                case VideoDetailInvoker.ContentType.TYPE_TAG:
                    //TODO 跳转标签
                    ToastUtil.showToast(activity.getApplicationContext(), "需要筛选功能，目前还未上线，敬请期待", Toast.LENGTH_SHORT);
                    break;

            }

        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/11/7
     * @Description 模糊背景
     */
    public void BlurBackground() {
        if (null != CloudScreenApplication.getInstance().blurBitmap) {
            CloudScreenApplication.getInstance().blurBitmap.recycle();
            CloudScreenApplication.getInstance().blurBitmap = null;
        }
        mainView.destroyDrawingCache();
        mainView.buildDrawingCache();
        Bitmap bitmap = mainView.getDrawingCache();
        if (bitmap != null) {
            Matrix matrix = new Matrix();
            matrix.postScale(0.3f, 0.3f);
            Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, mainView.getMeasuredWidth(), mainView.getMeasuredHeight(), matrix,
                    true);
            CloudScreenApplication.getInstance().blurBitmap = BitmapBlurUtil.getInstance().blurBitmap(scaledBitmap,
                    this);
        }
        mainView.destroyDrawingCache();
    }


    /**
     * @Author Spr_ypt
     * @Date 2015/11/5
     * @Description 底部分类视图的回调
     */
    private ChannelView.UpFocusCallBack upFocus = new ChannelView.UpFocusCallBack() {

        @Override
        public void upFocused() {
            int[] location = new int[2];
            headerView.getLocationInWindow(location);
            upOrDownAnimator.translationYBy(-location[1]);
            backgroundAnimator.translationY(0);
            backgroundBlurAnimator.translationY(0);
        }
    };

    /**
     * @Author Spr_ypt
     * @Date 2015/11/5
     * @Description 加载频道信息的异步方法
     */
    private class AsynDBTask extends AsyncTask<Context, String, List<VideoChannelEntity>> {

        @Override
        protected List<VideoChannelEntity> doInBackground(Context... params) {
            return VideoChannelUtils.getVideoChannelsFromLauncher(params[0]);
        }

        @Override
        protected void onPostExecute(List<VideoChannelEntity> result) {
            super.onPostExecute(result);
            if (null == result || result.size() <= 0) {
                Log.e(TAG, "channel list empty");
            }
            channelData = result;
            loadData();
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/18
     * @Description 异步加载推荐缓存数据
     */
    private class AsyncLoadDataCacheTask extends AsyncTask<WeakReference<MainActivity>, String, ArrayList<RecommendListEntity>> {
        private WeakReference<MainActivity> reference;

        @Override
        protected ArrayList<RecommendListEntity> doInBackground(WeakReference<MainActivity>... params) {
            reference = params[0];
            return params[0].get().recommendDao.query(null, null, null, null);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<RecommendListEntity> recommendListEntities) {
            MainActivity activity = reference.get();
            //第一个数据放置在背景推荐位
            if (recommendListEntities.size() > 0) {
                setPlace1Data(recommendListEntities.get(0));
            } else {
                return;
            }
            Logger.d(TAG, "activity.cachePics.size=" + activity.cachePics.size() + "||recommendListEntities.size()=" + recommendListEntities.size());
            for (int i = 0; i < activity.cachePics.size(); i++) {
                if (i < recommendListEntities.size() - 1) {
                    activity.cachePics.get(i).setData(recommendListEntities.get(i + 1));
                    activity.cachePics.get(i).setTag(recommendListEntities.get(i + 1));
                }
            }
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/18
     * @Description 设置背景推荐位数据
     */
    private void setPlace1Data(RecommendListEntity entity) {
        place1.setTag(entity);
        if (!entity.getContentImg().equals(backgroundUrl)) {
            backgroundUrl = entity.getContentImg();
            DisplayImageUtil.getInstance().setCacheInMemory(true).displayImage(backgroundUrl, background);
        }
        String title = entity.getContentName();
        int titleLength = (int) place1Title.getPaint().measureText(title);
        if (titleLength > 240) {//根据文本长短变更布局
            RelativeLayout place1Inner = (RelativeLayout) findViewById(R.id.rl_launcher_place1_inner);
            ViewGroup.LayoutParams place1Params = place1Inner.getLayoutParams();
            place1Params.width = (int) (getResources().getDimension(R.dimen.place1_inner_layout_width) + (titleLength <= getResources().getDimension(R.dimen.place1_text_max_width) ? titleLength - getResources().getDimension(R.dimen.place1_text_default_width) : getResources().getDimension(R.dimen.place1_text_max_width) - getResources().getDimension(R.dimen.place1_text_default_width)));
            place1Inner.setLayoutParams(place1Params);

            ViewGroup.LayoutParams titleParams = place1Title.getLayoutParams();
            titleParams.width = (int) (getResources().getDimension(R.dimen.place1_text_default_width) + (titleLength <= getResources().getDimension(R.dimen.place1_text_max_width) ? titleLength - getResources().getDimension(R.dimen.place1_text_default_width) : getResources().getDimension(R.dimen.place1_text_max_width) - getResources().getDimension(R.dimen.place1_text_default_width)));
            place1Title.setLayoutParams(titleParams);

            ViewGroup.LayoutParams contentParams = place1Content.getLayoutParams();
            contentParams.width = (int) (getResources().getDimension(R.dimen.place1_text_default_width) + (titleLength <= getResources().getDimension(R.dimen.place1_text_max_width) ? titleLength - getResources().getDimension(R.dimen.place1_text_default_width) : getResources().getDimension(R.dimen.place1_text_max_width) - getResources().getDimension(R.dimen.place1_text_default_width)));
            place1Content.setLayoutParams(contentParams);

            LinearLayout.LayoutParams recommendParams = (LinearLayout.LayoutParams) launchRecommend.getLayoutParams();
            recommendParams.leftMargin = (int) (getResources().getDimension(R.dimen.launcher_recommend_left_margin) - (titleLength <= getResources().getDimension(R.dimen.place1_text_max_width) ? titleLength - getResources().getDimension(R.dimen.place1_text_default_width) : getResources().getDimension(R.dimen.place1_text_max_width) - getResources().getDimension(R.dimen.place1_text_default_width)));
        }
        place1Title.setText(title);
        if (null != entity.getIsTxtShow() && entity.getIsTxtShow() == 1) {//设置显示文本才显示
            place1Content.setText(entity.getContentFocus());
        } else {
            place1Content.setText("");
        }
        place1Title.setIsInFocusView(place1.hasFocus());
        place1Content.setIsInFocusView(place1.hasFocus());
    }


    /**
     * @Author Spr_ypt
     * @Date 2015/11/5
     * @Description 加载数据，目前逻辑是加载完频道数据后才会去加载推荐位数据
     * 数据加载第一步
     */
    private void loadData() {

        // end by haozening
        if (null != channelData && channelData.size() > 0) {
            channelView.setData(channelData);
            //这里需要确认频道列表已经有数据了才可以设置一些指向它的监听或事件
            for (RecommendView rv : cachePics) {
                //TODO 给推荐位设置监听
                rv.setOnKeyListener(new OnRecommendKeyListener(this));
                rv.setOnClickListener(new OnRecommendClickListener(this));
            }
            place1.setOnKeyListener(new OnRecommendKeyListener(this));
            place1.setOnClickListener(new OnRecommendClickListener(this));
            CloudScreenService.getInstance().getLaunchRecommendList(new RecommendListListener(this), 1, 20);
        } else {
            CloudScreenService.getInstance().getChannelList(new ChannelListListener(this), DeviceInfoUtil.getInstance().getDeviceInfo(this).templetId, AppConstants.APK_PACKAGE_NAME);
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/18
     * @Description 重置背景色
     */
    private void resetFocusColor(UserStateUtil.UserStatus userStatus) {
        switch (userStatus) {
            case VIPUSER:
                Logger.d(TAG, "VIPUSER");
                for (View v : focusableViews) {
                    v.setBackgroundResource(R.drawable.focused_view_vip_selector);
                }
                if (null != channelView) {
                    channelView.setChannelViewBg(R.drawable.focused_view_vip_selector);
                }
                break;
            default:
                Logger.d(TAG, "not is VIPUSER");
                for (View v : focusableViews) {
                    v.setBackgroundResource(R.drawable.focused_view_selector);
                }
                if (null != channelView) {
                    channelView.setChannelViewBg(R.drawable.focused_view_selector);
                }
                break;
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/16
     * @Description 获取频道列表的监听
     */
    private class ChannelListListener implements OnRequestResultListener<ResultEntity> {

        private WeakReference<MainActivity> reference;

        public ChannelListListener(MainActivity vipVideoLauncherActivity) {
            reference = new WeakReference<MainActivity>(vipVideoLauncherActivity);
        }

        @Override
        public void onSucess(ResultEntity entity) {
            MainActivity activity = reference.get();
            activity.channelData = entity.getList();
            if (null != entity.getList() && entity.getList().size() > 0) {
                activity.loadData();
            } else {
                ToastUtil.showToast(activity, getString(R.string.tip_no_channel_data), 1);
            }
        }

        @Override
        public void onFail(Exception e) {
            Logger.d(TAG, "onFail=" + e.toString());
            e.printStackTrace();
        }

        @Override
        public void onParseFail(HiveviewException e) {
            Logger.d(TAG, "onParseFail=" + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/18
     * @Description 获取推荐位数据，需要先加载完频道数据
     * 拿到数据后先更新数据库，再从数据库读取数据放到推荐位上
     * 数据加载第二步
     */
    private class RecommendListListener implements OnRequestResultListener<ResultEntity<RecommendListEntity>> {

        private WeakReference<MainActivity> reference;

        public RecommendListListener(MainActivity vipVideoLauncherActivity) {
            reference = new WeakReference<MainActivity>(vipVideoLauncherActivity);
        }

        @Override
        public void onSucess(ResultEntity<RecommendListEntity> entity) {
            Logger.i(TAG, "RecommendListListener.onSucess");
            MainActivity activity = reference.get();
            List<RecommendListEntity> entities = entity.getList();
            if (null != activity && null != entities) {
                if (entities.size() > 0) {
                    if (entities.size() > 9) {
                        activity.recommendSizeChanged(entities.size() - 9);
                    }
                    activity.lastId = activity.cachePics.get(activity.cachePics.size() - 1).getId();
                    activity.ratio = (activity.background.getWidth() - activity.sizeConstant.getBoxWidth()) / (activity.cachePics.size() - 2);
//                    ArrayList<RecommendListEntity> dbRecommend = activity.recommendDao.query(null, null, null, null);
                    for (int i = 0; i < activity.cachePics.size(); i++) {
                        if (entities.size() > 0 && i == 0) {
                            setPlace1Data(entities.get(0));
                        }
                        if (i < entities.size() - 1) {
                            activity.cachePics.get(i).setData(entities.get(i + 1));
                            activity.cachePics.get(i).setTag(entities.get(i + 1));
                        }

                    }
                }
                activity.recommendDao.delete(null, null);
                for (RecommendListEntity e : entities) {
                    activity.recommendDao.insert(e);
                }
                /**
                 * 设置起始焦点所在推荐位的焦点变化By Spr_ypt
                 */
                for (RecommendView rv : activity.cachePics) {
                    if (rv.hasFocus()) {
                        rv.displayBottom();
                    }
                }
                activity.channelView.setRecommendView(activity.channelUpViews); // by
            }

        }

        @Override
        public void onFail(Exception e) {
            Logger.i(TAG, "RecommendListListener.onFail");
        }

        @Override
        public void onParseFail(HiveviewException e) {
            Logger.i(TAG, "RecommendListListener.onParseFail");
        }

    }


    /**
     * @Author Spr_ypt
     * @Date 2015/11/30
     * @Description 根据推荐位数量调整布局添加10+推荐位布局
     */
    private void recommendSizeChanged(int changeSize) {
        Logger.d(TAG, "recommendSizeChanged changeSize=" + changeSize);
        //获得推荐位部分的布局
        ViewGroup.LayoutParams outParams = recommendview.getLayoutParams();
        outParams.width = (int) (recommendview.getWidth() + getResources().getDimension(R.dimen.recommend_size_changed_distance) * changeSize
                + recommendview.getWidth() + getResources().getDimension(R.dimen.recommend_size_changed_width) * changeSize
                + recommendview.getWidth() + getResources().getDimension(R.dimen.recommend_size_changed_distance));
        ViewGroup.LayoutParams out2Params = launchRecommend.getLayoutParams();
        out2Params.width = (int) (recommendview.getWidth() + getResources().getDimension(R.dimen.recommend_size_changed_distance) * changeSize
                + recommendview.getWidth() + getResources().getDimension(R.dimen.recommend_size_changed_width) * changeSize
                + recommendview.getWidth() + getResources().getDimension(R.dimen.recommend_size_changed_distance));
        //循环添加固定尺寸的推荐位
        for (int i = 0; i < changeSize; i++) {
            RecommendView review = new RecommendView(this);
            review.setDefaultImage(ResourceProvider.getInstance().getBgLauncherPortraitHigh());
            if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                review.setBackgroundResource(R.drawable.focused_view_vip_selector);
            } else {
                review.setBackgroundResource(R.drawable.focused_view_selector);
            }
            review.setFocusable(true);
            review.setOnRecommendFocusChangedListener(new OnRecommendFocusChangedListener(this));
            if (null != channelView.getFirstTextView()) {
                review.setNextFocusDownId(channelView.getFirstTextView().getId());
            }
            review.setOnKeyListener(new OnRecommendKeyListener(this));
            review.setOnClickListener(new OnRecommendClickListener(this));
            review.setId(4000 + i);
            if (i == 0) {
                place9.setNextFocusRightId(review.getId());
            }
            if (i == changeSize - 1) {
                review.setNextFocusRightId(review.getId());
            }
            review.invalidate();
            launchRecommend.addView(review);
            cachePics.add(review);
            channelUpViews.add(review);
            focusableViews.add(review);

            RelativeLayout.LayoutParams newParams = (RelativeLayout.LayoutParams) review.getLayoutParams();
            newParams.width = (int) getResources().getDimension(R.dimen.recommend_size_changed_review_width);// 550
            newParams.height = (int) getResources().getDimension(R.dimen.recommend_size_changed_review_height);// 749
            newParams.leftMargin = (int) getResources().getDimension(R.dimen.recommend_size_changed_review_leftMargin);// 16
            newParams.topMargin = (int) getResources().getDimension(R.dimen.recommend_size_changed_review_topMargin);// 10
            newParams.rightMargin = (int) getResources().getDimension(R.dimen.recommend_size_changed_review_rightMargin);// -36
//            newParams.bottomMargin = (int) getResources().getDimension(R.dimen.recommend_size_changed_review_bottomMargin);// -36
            newParams.addRule(RelativeLayout.RIGHT_OF, cachePics.get(cachePics.size() - 2).getId());
//            recommendList.add(review); // by liulifeng
            // 相关推荐集合，添加新增加的RecommendView
        }
        recommendview.requestLayout();
    }

    private class LauncherHandler extends Handler {
        private WeakReference<MainActivity> reference;

        /**
         * 展示弹窗，登录/免费领取
         */
        public static final int SHOW_DIALOG = 0x0001;
        /**
         * 展示弹窗延时
         * 由于弹出弹窗前需要先确定是否有免费活动，免费活动信息从接口获取，一次请求超时时间为5秒，所以这里的延时必须在5秒以上
         */
        public static final int SHOW_DIALOG_DELAY = 2000;
        /**
         * 刷新用户信息
         * 用于主动去跟用户中心确定当前的用户状态
         */
        public static final int REFRESH_USER_STATUS = 0x0002;
        /**
         * 刷新用户信息延迟
         */
        public static final int REFRESH_USER_STATUS_DELAY = 2000;//暂定2秒后再次刷新用户数据防止没接到广播
        /**
         * 展示续费弹窗
         */
        public static final int SHOW_RENEW_DIALOG = 0x0003;
        /**
         * 展示续费弹窗延时
         */
        public static final int SHOW_RENEW_DIALOG_DELAY = 2000;
        /**
         * 推荐位入场动画
         */
        public static final int START_RECOMMENG_ANIM = 0x0004;
        /**
         * 推荐位入场动画延时
         */
        public static final int START_RECOMMENG_ANIM_DELAY_TIME = 800;


        public LauncherHandler(MainActivity activity) {
            reference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final MainActivity activity = reference.get();
            switch (msg.what) {
                case SHOW_DIALOG:
                    if (UserStateUtil.getInstance().isUserStatusReady()) {//确保用户数据就位后再去弹窗
                        Log.i(TAG, "SHOW_DIALOG checkShowDialog");
                        activity.checkShowDialog();
                    }
                    break;
                case REFRESH_USER_STATUS:
                    UserStateUtil.getInstance().notifyReFreshUserState(activity);
                    break;
                case SHOW_RENEW_DIALOG:
                    if (null != activity.renewDialog) {
                        Logger.d(TAG, "renewDialog.showWindow()");
                        try {
                            activity.renewDialog.showWindow();
                            activity.hideFocus();
                        } catch (Exception e) {
                            Logger.e(TAG, "dialog exception e=" + e.toString());
                        }
                    }
                    break;
                case START_RECOMMENG_ANIM:
                    activity.launchRecommend.animate().setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            activity.isLauncherReady = true;
                            super.onAnimationEnd(animation);
                        }
                    }).translationXBy(-launchRecommend.getTranslationX()).setDuration(600).setInterpolator(new LinearInterpolator()).start();
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }

    /**
     * @Author wangbei
     * @Date 2016/9/7
     * @Description 根据用户状态展现对话框
     * 前提：只要用户没有领取过就可以参加活动，不管用户是不是vip的状态。
     * 1、判断用户是否登录
     * 2、判断有无领取过免费包
     * 4、判断领取二维码的条件是什么
     * 5、针对领取条件判断用户信息是否满足   ----  否：获取用户信息做出相应提示语
     */
    private void checkShowDialog() {
        if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.NOLOGIN) {
            if (null != loginDialog) {
                try {
                    hideFocus();
                    loginDialog.showWindow();
                } catch (Exception e) {
                    Logger.d(TAG, "dialog exception e=" + e.toString());
                }
            }
        } else {
            Logger.d(TAG, "should show vipVideoDialog");
            UserStateUtil.UserInfo userInfo = UserStateUtil.getInstance().getUserInfo();
            if (null != userInfo.createTime) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    beginUseTime = sdf.parse(userInfo.createTime).getTime() / 1000;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "格式化后的日期：" + beginUseTime + "   用户创建时间==" + userInfo.createTime);

                if (null == freeActivityEntity) {
                    return;
                }

                if (null != freeActivityEntity.getAfterDate()) {
                    afterData = freeActivityEntity.getAfterDate();
                    Log.i(TAG, "某日之后：" + afterData);
                }
                if (null != freeActivityEntity.getBeforeDate()) {
                    beforeData = freeActivityEntity.getBeforeDate();
                    Log.i(TAG, "某日之前：" + beforeData);
                }
                /**
                 * 判断用户是否满足 用户领取范围
                 */
                if (null != freeActivityEntity.getUserRange()) {
                    Log.i(TAG, "用户范围：" + freeActivityEntity.getUserRange());
                    if (freeActivityEntity.getUserRange() == 1) {
                        Log.i(TAG, "全部用户都可以领取");
                        getConditionType();
                    } else if (freeActivityEntity.getUserRange() == 2 && null != beforeData) {
                        Log.i(TAG, beforeData + " 之前的用户可以领取");
                        if (beginUseTime < Long.parseLong(beforeData)) {
                            getConditionType();
                        } else {
                            Log.i(TAG, "不满足 某日之前领取的条件");
                        }
                    } else if (freeActivityEntity.getUserRange() == 3 && null != afterData) {
                        Log.i(TAG, afterData + "之后的用户可以领取");
                        if (beginUseTime > Long.parseLong(afterData)) {
                            getConditionType();
                        } else {
                            Log.i(TAG, "不满足 某日之后领取的条件");
                        }
                    } else if (freeActivityEntity.getUserRange() == 4 && null != afterData && null != beforeData) {
                        Log.i(TAG, beforeData + " 之间的用户可以领取 " + afterData);
                        if (beginUseTime < Long.parseLong(beforeData) && beginUseTime > Long.parseLong(afterData)) {
                            getConditionType();
                        } else {
                            Log.i(TAG, "不满足 某日之间领取的条件");
                        }
                    }
                }
            } else {
                if (null != freeActivityEntity.getUserRange() && freeActivityEntity.getUserRange() == 1) {
                    getConditionType();
                    Log.i(TAG, "用户创建时间为空，但用户领取范围是全部用户则可以继续");
                }
            }
        }
    }

    private void getConditionType() {
        if (null != freeActivityEntity && 0 != freeActivityEntity.getId()) {
            if (!(freeActivityEntity.getId() + "").equals(UserStateUtil.getInstance().getUserInfo().hasFreePackage) && null != vipVideoDialog) { //如果没领取过免费包
                Log.i(TAG, "是否关注:" + UserStateUtil.getInstance().getUserInfo().hasOpenId);
                Log.i(TAG, "是否绑定:" + UserStateUtil.getInstance().getUserInfo().userPhone);
                Log.i(TAG, "满足条件:" + freeActivityEntity.getConditionType());
                /**
                 * 根据接口判断领取免费包需要满足什么条件
                 * 1：无条件领取
                 * 2：关注微信领取
                 * 3：绑定手机领取
                 * 4：关注加绑定
                 */
                if (freeActivityEntity.getConditionType() == 1) {
                    try {
                        vipVideoDialog.showWindow();
                        hideFocus();
                    } catch (Exception e) {
                        Logger.e(TAG, "dialog exception e=" + e.toString());
                    }
                } else if (freeActivityEntity.getConditionType() == 2) {
                    if (!"1".equals(UserStateUtil.getInstance().getUserInfo().hasOpenId)) {
                        UserQuickMark(this, freeActivityEntity);
                    } else {
                        isQuickMark = true;
                        vipVideoDialog.showWindow();
                        hideFocus();
                    }
                } else if (freeActivityEntity.getConditionType() == 3) {
                    if (!TextUtils.isEmpty(UserStateUtil.getInstance().getUserInfo().userPhone)) {
                        isQuickMark = true;
                        vipVideoDialog.showWindow();
                        hideFocus();
                    } else {
                        UserQuickMark(this, freeActivityEntity);
                    }
                } else if (freeActivityEntity.getConditionType() == 4) {
                    if (!"0".equals(UserStateUtil.getInstance().getUserInfo().hasOpenId) && !TextUtils.isEmpty(UserStateUtil.getInstance().getUserInfo().userPhone)) {
                        isQuickMark = true;
                        vipVideoDialog.showWindow();
                        hideFocus();
                    } else {
                        UserQuickMark(this, freeActivityEntity);
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        Logger.d(TAG, "onDestroy");
        isQuickMark = false;
        isCompelete = false;
        UserStateUtil.getInstance().removeWatcher(this);//反注册监听
        //统计埋点
        BaseActionInfo info;
        if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
            info = new LauncherBrowseActionInfo("03", "vip03014", noAction, Long.toString(System.currentTimeMillis() - openTime), "1");
        } else {
            info = new LauncherBrowseActionInfo("03", "vip03014", noAction, Long.toString(System.currentTimeMillis() - openTime), "2");
        }
        new Statistics(this, info).send();
        super.onDestroy();
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    protected void onRestart() {
        Logger.d(TAG, "onRestart");
        super.onRestart();
        noAction = "0";
        UserStateUtil.getInstance().notifyReFreshUserState(this);//从新回到该页面时需要刷新下用户信息,主要用于登录后
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        if (null != headerView) {
            headerView.setFreshContent();
        }
        if (getCurrentFocus() instanceof RecommendView) {
            ((RecommendView) getCurrentFocus()).displayBottom();
        }
        if (getCurrentFocus() == place1) {
            place1Content.setIsInFocusView(true);
            place1Title.setIsInFocusView(true);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (null != headerView) {
            headerView.recycle();//回收headerview
        }
        if (getCurrentFocus() instanceof RecommendView) {
            ((RecommendView) getCurrentFocus()).hideBottom();
        }
        if (getCurrentFocus() == place1) {
            place1Content.setIsInFocusView(false);
            place1Title.setIsInFocusView(false);
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/12/17
     * @Description 构建高斯模糊的背景并设置
     */
    private synchronized void buildBlurBg(String url) {
        //通过ImageLoader加载图片
        if (null != url) {
            CloudScreenApplication.getInstance().imageLoader.loadImage(url, new SimpleImageLoadingListener() {
                @SuppressLint("NewApi")
                @Override
                public void onLoadingComplete(String imageUri, View view, final Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    //通过异步任务设置高斯模糊图
                    new AsyncTask<Context, String, Bitmap>() {
                        @Override
                        protected Bitmap doInBackground(Context... params) {
                            Matrix matrix = new Matrix();
                            matrix.postScale(0.3f, 0.3f);
                            // 将Bitmap尺寸缩小，减少blur时间
                            if (null != loadedImage) {
                                Bitmap scaledBitmap = Bitmap.createBitmap(loadedImage, 0, 0, loadedImage.getWidth(), loadedImage.getHeight(), matrix, true);
                                Bitmap blurBitmap = BitmapBlurUtil.getInstance().blurBitmap(scaledBitmap, params[0]);
                                return blurBitmap;
                            } else {
                                return null;
                            }
                        }

                        @Override
                        protected void onPostExecute(Bitmap bitmap) {
                            if (null != bitmap) {
                                blurBackground = new BitmapDrawable(null, bitmap);
                                backgroundBlur.setImageDrawable(blurBackground);
                                if (shouldShowBlur) {
                                    ObjectAnimator.ofFloat(backgroundBlur, "alpha", 1f).start();
                                }
                            }
                            super.onPostExecute(bitmap);
                        }
                    }.execute(MainActivity.this);
                }
            });
        }

    }

    /**
     * @Author Spr_ypt
     * @Date 2016/1/12
     * @Description 隐藏页面上的焦点，为了满足弹窗后需要隐藏焦点的变态需求
     */
    private void hideFocus() {
        Logger.d(TAG, "hideFocus");
        if (null != getCurrentFocus()) {
            getCurrentFocus().setBackground(null);
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/1/12
     * @Description 展示页面上的焦点，为了满足弹窗后需要隐藏焦点的变态需求
     */
    private void showFocus() {
        if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
            if (null != getCurrentFocus()) {
                getCurrentFocus().setBackgroundResource(R.drawable.focused_view_vip_selector);
            }
        } else {
            if (null != getCurrentFocus()) {
                getCurrentFocus().setBackgroundResource(R.drawable.focused_view_selector);
            }
        }
    }


}






