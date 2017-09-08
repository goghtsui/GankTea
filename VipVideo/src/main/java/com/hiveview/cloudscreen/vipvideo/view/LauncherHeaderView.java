package com.hiveview.cloudscreen.vipvideo.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;
import com.hiveview.cloudscreen.vipvideo.service.CloudScreenService;
import com.hiveview.cloudscreen.vipvideo.service.OnRequestResultListener;
import com.hiveview.cloudscreen.vipvideo.service.entity.MessageEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.RemindStrategyEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.util.EasySpan;
import com.hiveview.cloudscreen.vipvideo.util.Logger;
import com.hiveview.cloudscreen.vipvideo.util.NetworkUtil;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;
import com.hiveview.cloudscreen.vipvideo.view.drawable.EthernetDrawable;
import com.hiveview.cloudscreen.vipvideo.view.drawable.WifiDrawable;
import com.hiveview.manager.PingListener;
import com.hiveview.manager.PingManager;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Spr_ypt on 2015/11/4.
 * 用于处理首页底部控件的逻辑
 */
public class LauncherHeaderView extends RelativeLayout implements PingListener {
    private static final String TAG = LauncherHeaderView.class.getSimpleName();
    /**
     * 内容加载容器
     */
    private View container;
    /**
     * 右上角logo
     */
    private View headerLogo;
    /**
     * 用户背景图
     */
    private LinearLayout userBgLayout;
    /**
     * 用户登录布局
     */
    private LinearLayout userLoginLayout;
    /**
     * 开通会员布局
     */
    private LinearLayout userGetvipLayout;
    /**
     * 会员状态隐藏续费功能
     */
    private RelativeLayout userViptipLayout;
    /**
     * vip logo
     */
    private View vipLogoView;
    /**
     * vip剩余时间文本
     */
    private TypeFaceTextView vipTimeTxt;

    private LinearLayout myFilmLayout;
    /**
     * 整体背景图
     */
    private View bgView;
    /**
     * 网络状态
     */
    private ImageView netStatusView;
    /**
     * 显示时间文本
     */
    private TypeFaceTextView timeTxt;
    /**
     * Context
     */
    private Context context;
    /**
     * wifi状态等级图
     */
    private WifiDrawable wifiDrawable;
    /**
     * 有线网络等级图
     */
    private EthernetDrawable ethernetDrawable;
    /**
     * 是否显示WiFi状态
     */
    private boolean isWifiDrawable = false;
    /**
     * 更新时间的标记位
     */
    private static final int UPDATE_TIME = 0x1001;
    /**
     * 时间更新Handler
     */
    private Handler timerHandler = new TimerHandler(this);
    /**
     * 需要弹出续费弹窗回调接口
     */
    private OnRenewAlertListener onRenewAlertListener;


    /**
     * 按键监听
     */
    private OnBtnClickListener listener;
    /**
     * 滚动信息文本
     */
    private TypeFaceTextView marqueeMessage;
    /**
     * 滚动信息图片
     */
    private ImageView marqueeCrash;
    /**
     * 记录信息滚动到第几条了
     */
    private static int count;

    private EasySpan easySpan;
    private EasySpan nextEasySpan;
    private SpannableString message;
    private SpannableString nextMessage;

    /**
     * 一天的时间
     */
    private static final long DAY = 24 * 60 * 60 * 1000L;


    public LauncherHeaderView(Context context) {
        super(context);
        init(context);
    }

    public LauncherHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LauncherHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 加载、初始化
     *
     * @param context
     */
    private void init(final Context context) {
        //添加布局
        this.context = context;
        container = LayoutInflater.from(context).inflate(R.layout.view_launcher_header, null);
        addView(container);
        //获取布局控件以及设置控件监听
        findView();
        //设置用户状态
        setUserStatus(UserStateUtil.getInstance().getUserStatus());
        //设置网络状态等级图
        setNetStatus(context);
        //请求提醒策略
        CloudScreenService.getInstance().getRemindStrategy(new RemindStrategyListener(this));
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/10/31
     * @Description 到期提醒策略请求接口
     */
    private static final class RemindStrategyListener implements OnRequestResultListener<ResultEntity<RemindStrategyEntity>> {

        private WeakReference<LauncherHeaderView> reference;

        public RemindStrategyListener(LauncherHeaderView headerView) {
            reference = new WeakReference<LauncherHeaderView>(headerView);
        }

        @Override
        public void onSucess(ResultEntity<RemindStrategyEntity> entity) {
            LauncherHeaderView headerView = reference.get();
            RemindStrategyEntity remindEntity = (RemindStrategyEntity) entity.getEntity();
            if (null != remindEntity && null != remindEntity.getEndBeforeRemindDays()) {
                long notifyLine = remindEntity.getEndBeforeRemindDays() * DAY;
                long space = remindEntity.getAvgRemindDays() * DAY / remindEntity.getRemindNum();
                try {
                    headerView.checkRenew(notifyLine, space);
                } catch (ParseException e) {
                    Logger.e(TAG, "ParseException=" + e.toString() + " strategy time parse failed");
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFail(Exception e) {
            Logger.e(TAG, "Exception=" + e.toString() + " Can not get strategy");
        }

        @Override
        public void onParseFail(HiveviewException e) {
            Logger.e(TAG, "HiveviewException=" + e.toString() + " strategy can not be formated");
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/1/14
     * @Description 加载所有会更新的数据，这些数据更新会暂用CPU，页面不被看到时应调用{@link #recycle()}进行回收
     */
    public void setFreshContent() {
        //设置初始网络状态
        resetNetstatus(PingManager.getPingStatus());
        //注册网络状态监听
        PingManager.regCallBackListener(this);
        //发送更新时间消息
        timerHandler.sendEmptyMessage(UPDATE_TIME);
        //加载消息
        CloudScreenService.getInstance().getMessageVipList(new MessageListener(this));
    }

    private static final class MessageListener implements OnRequestResultListener<ResultEntity<MessageEntity>> {

        private WeakReference<LauncherHeaderView> reference;

        private MessageListener(LauncherHeaderView headerView) {
            reference = new WeakReference<LauncherHeaderView>(headerView);
        }

        @Override
        public void onSucess(ResultEntity<MessageEntity> messages) {
            LauncherHeaderView headerView = reference.get();
            if (null != headerView && null != messages.getList()) {
                headerView.createMarquee(messages.getList());
            }
        }

        @Override
        public void onFail(Exception e) {
            Logger.e(TAG, "Exception=" + e.toString() + " Can not get messages");
        }

        @Override
        public void onParseFail(HiveviewException e) {
            Logger.e(TAG, "HiveviewException=" + e.toString() + " Messages can not be formated");
        }
    }


    private void createMarquee(final List<MessageEntity> messages) {
        count = 0;
        final int total = messages.size();
        if (total <= 0) {
            return;
        }
        if (null != messages.get(0).getPic() && !"".equals(messages.get(0).getPic())) {
            marqueeCrash.setVisibility(View.VISIBLE);
            DisplayImageUtil.getInstance().setDuration(200).displayImage(messages.get(0).getPic(), marqueeCrash);
        } else {
            marqueeCrash.setVisibility(View.GONE);
        }
        Logger.d(TAG, "messages.get(0)=" + messages.get(0).getMessageDesc());
        String text = "" + messages.get(0).getMessageDesc() + "　";
        message = new SpannableString(text);
        if (null != easySpan) {
            easySpan.setView(marqueeMessage);
        } else {
            easySpan = new EasySpan(context, marqueeMessage);
        }
        easySpan.setDuration(30000);
        Logger.d(TAG, "total=" + total);
        if (total > 1) {
            easySpan.setEasySpanListener(new EasySpan.EasySpanListener() {
                @Override
                public void over() {
                    count++;
                    if (count >= total) {
                        count = count - total;
                    }
                    if (null != messages.get(count).getPic() && !"".equals(messages.get(count).getPic())) {
                        marqueeCrash.setVisibility(View.VISIBLE);
                        DisplayImageUtil.getInstance().setDuration(200).displayImage(messages.get(count).getPic(), marqueeCrash);
                    } else {
                        marqueeCrash.setVisibility(View.GONE);
                    }
                    String nextText = "" + messages.get(count).getMessageDesc() + "　";
                    Logger.d(TAG, "count=" + count + "||message=" + nextText);
                    if (null != nextEasySpan) {
                        if (null != nextMessage) {
                            nextMessage.removeSpan(nextEasySpan);
                        }
                        nextEasySpan.recycle();
                    }
                    nextMessage = new SpannableString(nextText);
                    if (null != nextEasySpan) {
                        nextEasySpan.setView(marqueeMessage);
                    } else {
                        nextEasySpan = new EasySpan(context, marqueeMessage);//这个不需要
                    }
                    nextEasySpan.setDuration(30000);
                    nextMessage.setSpan(nextEasySpan, 0, nextText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    marqueeMessage.setText(nextMessage);
                }
            });
        }
        message.setSpan(easySpan, 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        marqueeMessage.setText(message);
    }

    /**
     * 初始化网络状态
     *
     * @param context
     */
    private void setNetStatus(Context context) {
        ethernetDrawable = new EthernetDrawable(context);
        wifiDrawable = new WifiDrawable(context);
        netStatusView.setImageDrawable(ethernetDrawable);
        isWifiDrawable = false;

    }

    /**
     * 获取布局控件
     */
    private void findView() {
        headerLogo = container.findViewById(R.id.v_launcher_header_logo);
        headerLogo.setBackgroundResource(ResourceProvider.getInstance().getLauncherHeaderviewLogo());
        userBgLayout = (LinearLayout) container.findViewById(R.id.ll_launcher_header_user_bg);
        userLoginLayout = (LinearLayout) container.findViewById(R.id.ll_launcher_header_user_login);
        userGetvipLayout = (LinearLayout) container.findViewById(R.id.ll_launcher_header_user_getvip);
        userViptipLayout = (RelativeLayout) container.findViewById(R.id.rl_launcher_header_viplogo);
        vipLogoView = container.findViewById(R.id.v_launcher_header_viplogo);
        vipTimeTxt = (TypeFaceTextView) container.findViewById(R.id.v_launcher_header_viptime);
        myFilmLayout = (LinearLayout) container.findViewById(R.id.ll_launcher_header_myfilm);
        bgView = container.findViewById(R.id.v_launcher_header_bg);
        netStatusView = (ImageView) container.findViewById(R.id.iv_launcher_header_net);
        timeTxt = (TypeFaceTextView) container.findViewById(R.id.txt_launcher_header_time);
        marqueeMessage = (TypeFaceTextView) container.findViewById(R.id.tv_launcher_marquee_message);
        marqueeCrash = (ImageView) container.findViewById(R.id.txt_launcher_marquee_crash);

        //设置监听
        userLoginLayout.setOnClickListener(new UserLoginOnClickListener(this));
        userGetvipLayout.setOnClickListener(new UserGetvipOnClickListener(this));
        userViptipLayout.setOnClickListener(new UserViptipOnClickListener(this));
        myFilmLayout.setOnClickListener(new MyFilmOnClickListener(this));
    }

    public void setBtnDownView(View view) {
        userLoginLayout.setNextFocusDownId(view.getId());
        userLoginLayout.setOnKeyListener(onKeyListener);
        userGetvipLayout.setNextFocusDownId(view.getId());
        userGetvipLayout.setOnKeyListener(onKeyListener);
        userViptipLayout.setNextFocusDownId(view.getId());
        userViptipLayout.setOnKeyListener(onKeyListener);
        myFilmLayout.setNextFocusDownId(view.getId());
        myFilmLayout.setOnKeyListener(onKeyListener);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/10/31
     * @Description 控制按键逻辑
     */
    private OnKeyListener onKeyListener = new OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            switch (v.getId()) {
                case R.id.ll_launcher_header_user_login:
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                            return true;//顶部按钮不响应左右键
                        }
                    }
                    break;

                case R.id.ll_launcher_header_user_getvip:
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                            return true;
                        }
                    }
                    break;

                case R.id.rl_launcher_header_viplogo:
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                            return true;
                        }
                    }
                    break;
                case R.id.ll_launcher_header_myfilm:
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                            return true;
                        }
                    }
                    break;
            }

            return false;
        }
    };

    /**
     * 设置用户状态
     *
     * @param userStatus 用户状态值
     */
    public void setUserStatus(UserStateUtil.UserStatus userStatus) {
        switch (userStatus) {
            case NOLOGIN:
                Logger.d(TAG, "header view user is NOLOGIN");
                userLoginLayout.setVisibility(View.VISIBLE);
                userGetvipLayout.setVisibility(View.GONE);
                userViptipLayout.setVisibility(GONE);
                vipLogoView.setVisibility(View.GONE);
                vipTimeTxt.setVisibility(View.GONE);
                myFilmLayout.setVisibility(GONE);
                bgView.setBackgroundResource(R.drawable.launcher_headerview_bg);
                break;
            case NOMALUSER:
                Logger.d(TAG, "header view user is NOMALUSER");
                userLoginLayout.setVisibility(View.GONE);
                userGetvipLayout.setVisibility(View.VISIBLE);
                userGetvipLayout.requestFocus();
                userViptipLayout.setVisibility(GONE);
                vipLogoView.setVisibility(View.GONE);
                vipTimeTxt.setVisibility(View.GONE);
                myFilmLayout.setVisibility(VISIBLE);
                myFilmLayout.setBackgroundResource(R.drawable.focused_view_selector);
                bgView.setBackgroundResource(R.drawable.launcher_headerview_bg);
                break;
            case VIPUSER:
                Logger.d(TAG, "header view user is VIPUSER");
                userLoginLayout.setVisibility(View.GONE);
                userGetvipLayout.setVisibility(View.GONE);
                userViptipLayout.setVisibility(VISIBLE);
                if (!"".equals(UserStateUtil.getInstance().getUserInfo().expiredDate)) {
                    vipTimeTxt.setVisibility(View.VISIBLE);
                    vipTimeTxt.setText(UserStateUtil.getInstance().getUserInfo().expiredDate.substring(0, 10) + "到期");
                } else {
                    vipTimeTxt.setVisibility(INVISIBLE);
                }
                vipLogoView.setVisibility(View.VISIBLE);
                myFilmLayout.setVisibility(VISIBLE);
                myFilmLayout.setBackgroundResource(R.drawable.focused_view_vip_selector);
                bgView.setBackgroundResource(R.drawable.launcher_headerview_vip_bg);
                break;
        }
    }

    /**
     * 登录按钮监听
     */
    private static class UserLoginOnClickListener implements OnClickListener {

        private WeakReference<LauncherHeaderView> reference;

        private UserLoginOnClickListener(LauncherHeaderView headerView) {
            reference = new WeakReference<LauncherHeaderView>(headerView);
        }

        @Override
        public void onClick(View v) {
            LauncherHeaderView headerView = reference.get();
            headerView.listener.onLoginClick(UserStateUtil.getInstance().getUserStatus());
        }
    }

    /**
     * 开通会员按钮监听
     */
    private static class UserGetvipOnClickListener implements OnClickListener {

        private WeakReference<LauncherHeaderView> reference;

        private UserGetvipOnClickListener(LauncherHeaderView headerView) {
            this.reference = new WeakReference<LauncherHeaderView>(headerView);
        }

        @Override
        public void onClick(View v) {
            LauncherHeaderView headerView = reference.get();
            headerView.listener.onVipClick(UserStateUtil.getInstance().getUserStatus());
        }
    }

    /**
     * 开通会员按钮监听
     */
    private static class UserViptipOnClickListener implements OnClickListener {

        private WeakReference<LauncherHeaderView> reference;

        private UserViptipOnClickListener(LauncherHeaderView headerView) {
            this.reference = new WeakReference<LauncherHeaderView>(headerView);
        }

        @Override
        public void onClick(View v) {
            LauncherHeaderView headerView = reference.get();
            headerView.listener.onViptipClick();
        }
    }

    /**
     * 我的影片按钮监听
     */
    private static class MyFilmOnClickListener implements OnClickListener {

        private WeakReference<LauncherHeaderView> reference;

        private MyFilmOnClickListener(LauncherHeaderView headerView) {
            this.reference = new WeakReference<LauncherHeaderView>(headerView);
        }

        @Override
        public void onClick(View v) {
            LauncherHeaderView headerView = reference.get();
            headerView.listener.onFilmListClick();

        }
    }


    /**
     * onPingChanged（）方法并不是在主线程进行的
     * 这里通过handler.post方法把更新UI的操作抛到主线程处理
     * 避免android.view.ViewRoot$CalledFromWrongThreadException
     */
    private Handler handler = new Handler();

    /**
     * PingListener接口方法实现
     *
     * @param pingStatus
     */
    @Override
    public void onPingChanged(final int pingStatus) {
        Log.d(TAG, "onPingChanged() pingStatus : " + pingStatus);
        handler.post(new Runnable() {

            @Override
            public void run() {
                resetNetstatus(pingStatus);
            }
        });
    }

    private void resetNetstatus(int pingStatus) {
        Logger.d(TAG, "resetNetstatus() pingStatus : " + pingStatus);
//        mPingState = pingStatus;
        NetworkUtil util = new NetworkUtil(context);
        if (pingStatus == 0) { // 网络连接成功
            if (util.isWifi()) {
                resetWifiLevel();
            } else {
                setEthernetConnectedState();
            }
        } else if (pingStatus == -999) { // 外网连接超时，就是那个感叹号
            if (util.isWifi()) {
                setWifiDisconnectState();
            } else {
                setEthernetDisconnectState();
            }
        } else { // 其他 表示网线未连接
            setEthernetDisableState();
        }
    }

    /**
     * 更改wifi信号强度
     *
     * @Title resetWifiLevel
     * @author haozening
     * @Description
     */
    private void resetWifiLevel() {
        NetworkUtil util = new NetworkUtil(context);
        int level = util.getWifiLevel();
        Log.d(TAG, "level : " + level);
        if (null != wifiDrawable) {
            Log.d(TAG, "befor setLevel : " + level);
            if (!isWifiDrawable && null != netStatusView) {
                netStatusView.setImageDrawable(wifiDrawable);
                isWifiDrawable = true;
            }
            wifiDrawable.setLevel(level);
        }
    }

    /**
     * 设置wifi断开状态
     *
     * @Title setWifiDisconnectState
     * @author haozening
     * @Description
     */
    private void setWifiDisconnectState() {
        Log.d(TAG, "disconnectedWifi");
        if (null != wifiDrawable) {
            if (!isWifiDrawable && null != netStatusView) {
                netStatusView.setImageDrawable(wifiDrawable);
                isWifiDrawable = true;
            }
            wifiDrawable.setLevel(WifiDrawable.STATUS_INTERNET_DISCONNECT);
        }
    }

    /**
     * 网络不可用
     *
     * @Title setEthernetDisableState
     * @author haozening
     * @Description
     */
    private void setEthernetDisableState() {
        Log.d(TAG, "ethDisable");
        if (null != ethernetDrawable) {
            Log.d(TAG, " before setImage ethDisable");
            if (isWifiDrawable && null != netStatusView) {
                netStatusView.setImageDrawable(ethernetDrawable);
                isWifiDrawable = false;
            }
            Log.d(TAG, " before set ethDisable");
            ethernetDrawable.setLevel(EthernetDrawable.STATUS_DISCONNECT);
        }
    }

    /**
     * 网络未连接
     *
     * @Title setEthernetDisconnectState
     * @author haozening
     * @Description
     */
    private void setEthernetDisconnectState() {
        Log.d(TAG, "disconnectedEth");
        if (null != ethernetDrawable) {
            if (isWifiDrawable && null != netStatusView) {
                netStatusView.setImageDrawable(ethernetDrawable);
                isWifiDrawable = false;
            }
            ethernetDrawable.setLevel(EthernetDrawable.STATUS_INTERNET_DISCONNECT);
        }
    }

    /**
     * 有线已连接
     *
     * @Title setEthernetConnectedState
     * @author haozening
     * @Description
     */
    private void setEthernetConnectedState() {
        Log.d(TAG, "ethConnected");
        if (null != ethernetDrawable) {
            ethernetDrawable.setLevel(EthernetDrawable.STATUS_CONNECTED);
            if (isWifiDrawable && null != netStatusView) {
                netStatusView.setImageDrawable(ethernetDrawable);
                isWifiDrawable = false;
            }
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/28
     * @Description 更新时间的Handler
     */
    private static class TimerHandler extends Handler {
        private WeakReference<LauncherHeaderView> reference;

        private TimerHandler(LauncherHeaderView headerView) {
            reference = new WeakReference<LauncherHeaderView>(headerView);
        }

        @Override
        public void handleMessage(Message msg) {
            LauncherHeaderView headerView = reference.get();
            if (null != headerView) {
                switch (msg.what) {
                    case UPDATE_TIME:
                        headerView.timeTxt.setText(headerView.formateTime());
                        headerView.timerHandler.sendEmptyMessageDelayed(UPDATE_TIME, 1000 * 60);
                        break;
                }

            }
            super.handleMessage(msg);
        }
    }

    /**
     * 格式化时间
     *
     * @return
     * @Title formateTime
     * @author haozening
     * @Description
     */
    private String formateTime() {
        return android.text.format.DateFormat.getTimeFormat(context).format(new Date().getTime());
    }

    /**
     * @param notifyLine 提前几天开始提醒
     * @param space      2次提醒间隔
     * @Author Spr_ypt
     * @Date 2016/10/30
     * @Description 根据接口返回数据弹出续费弹簧
     */
    private void checkRenew(long notifyLine, long space) throws ParseException {
        if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat df_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date deadLine = df.parse(UserStateUtil.getInstance().getUserInfo().expiredDate);
            Date now = new Date();
            Logger.d(TAG, "deadline=" + (deadLine.getTime() - now.getTime()));
            if (deadLine.getTime() - now.getTime() < -DAY) {//会员已到期则刷新数据
                if (null != onRenewAlertListener) {
                    onRenewAlertListener.refreshUserInfo();
                }
            } else if (deadLine.getTime() - now.getTime() < notifyLine) {
                SharedPreferences preferences = context.getSharedPreferences("vipvideo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                String date = preferences.getString("date", "1970-01-01 00:00:00");
                Logger.d(TAG, "date=" + date);
                Logger.d(TAG, "space=" + space);
                Logger.d(TAG, "now=" + now.getTime());
                Logger.d(TAG, "old=" + df_time.parse(date).getTime());
                if (now.getTime() - df_time.parse(date).getTime() > space) {
                    if (null != onRenewAlertListener) {
                        onRenewAlertListener.needRenew();
                    }
                    editor.putString("date", df_time.format(now));
                    editor.commit();
                }
            }

        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/1/14
     * @Description 回收所有会占用CPU的数据刷新功能
     */
    public void recycle() {
        Logger.i(TAG, "recycle");
        PingManager.unregCallBackListener(this);
        timerHandler.removeMessages(UPDATE_TIME);
        if (null != nextEasySpan) {
            if (null != nextMessage) {
                nextMessage.removeSpan(nextEasySpan);
            }
            nextEasySpan.recycle();
        }
        if (null != easySpan) {
            if (null != message) {
                message.removeSpan(easySpan);
            }
            easySpan.recycle();
        }
        if (null != marqueeMessage) {
            marqueeMessage.clearAnimation();
            marqueeMessage.setText("");
        }
    }


    /**
     * @Author Spr_ypt
     * @Date 2015/11/13
     * @Description headView 按键监听
     */
    public interface OnBtnClickListener {
        void onLoginClick(UserStateUtil.UserStatus status);

        void onVipClick(UserStateUtil.UserStatus status);

        void onViptipClick();

        void onFilmListClick();
    }

    public void setBtnClickListener(OnBtnClickListener listener) {
        this.listener = listener;
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/12/7
     * @Description 需要提醒续费的回调监听
     */
    public interface OnRenewAlertListener {
        void needRenew();

        void refreshUserInfo();
    }

    public void setOnRenewAlertListener(OnRenewAlertListener onRenewAlertListener) {
        this.onRenewAlertListener = onRenewAlertListener;
    }


    public boolean setFocus() {
        if (userLoginLayout.getVisibility() == View.VISIBLE) {
            return userLoginLayout.requestFocus();
        } else if (userGetvipLayout.getVisibility() == View.VISIBLE) {
            return userGetvipLayout.requestFocus();
        } else if (userViptipLayout.getVisibility() == VISIBLE) {
            return userViptipLayout.requestFocus();
        } else if (myFilmLayout.getVisibility() == VISIBLE) {
            return myFilmLayout.requestFocus();
        } else {
            return true;
        }
    }

}
