package com.hiveview.cloudscreen.vipvideo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.VideoDetailInvoker;
import com.hiveview.cloudscreen.vipvideo.service.CloudScreenService;
import com.hiveview.cloudscreen.vipvideo.service.OnRequestResultListener;
import com.hiveview.cloudscreen.vipvideo.service.entity.LiveDetailEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.LiveStatisticEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.LiveStatusEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.util.AlarmServiceUtil;
import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.util.StringUtils;
import com.hiveview.cloudscreen.vipvideo.util.ToastUtil;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;
import com.hiveview.cloudscreen.vipvideo.view.TypeFaceTextView;
import com.hiveview.cloudscreen.vipvideo.view.VipVideoDialog;

import java.lang.ref.WeakReference;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/10/31
 * @Description
 */
public class DliveDetailActivity extends BaseActivity implements UserStateUtil.UserStatusWatcher {

    private static final String TAG = DliveDetailActivity.class.getSimpleName();
    /**
     * 背景图片
     */
    private ImageView bg;
    /**
     * 开通文本
     */
    private TypeFaceTextView txtBespeak;
    /**
     * 开通布局
     */
    private RelativeLayout rlBespeak;
    /**
     * 取消文本
     */
    private TypeFaceTextView txtCancel;
    /**
     * 取消布局
     */
    private RelativeLayout rlCancel;
    /**
     * 直播id
     */
    private int tvId;
    /**
     * 直播详情实体类
     */
    private LiveDetailEntity liveDetailEntity;
    /**
     * 直播鉴权信息实体类
     */
    private LiveStatusEntity liveStatusEntity;
    /**
     * 支付广播接收
     */
    private PayReceiver payReceiver;

    private LiveState liveState = LiveState.ERROR;

    private VipVideoDialog preBuyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dlive_layout);
        //findview
        findViews();
        //绑定提醒服务
        AlarmServiceUtil.getInstant().bindService(this);
        //获取参数
        tvId = getIntent().getIntExtra(AppConstants.EXTRA_DLIVE_ID, 0);
        //设置按键监听
        rlBespeak.setOnClickListener(new OnDliveClickListener(this));
        rlCancel.setOnClickListener(new OnDliveClickListener(this));
        //获取详情信息，获取成功后进行鉴权
        CloudScreenService.getInstance().getLiveDetail(tvId + "", new GetLiveDetailListener(this));
        resetVipColor();
        //注册广播
        registReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册广播
        unregisterReceiver(payReceiver);
        //反绑定提醒服务
        AlarmServiceUtil.getInstant().unbindService(this);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/12/7
     * @Description 注册支付结果广播
     */
    private void registReceiver() {
        payReceiver = new PayReceiver();
        IntentFilter filter = new IntentFilter("com.hiveview.goodspay");
        registerReceiver(payReceiver, filter);//退出界面时记得反注册
    }

    private void findViews() {
        bg = (ImageView) findViewById(R.id.dlive_detail_bg);
        txtBespeak = (TypeFaceTextView) findViewById(R.id.tv_bespeak_txt);
        rlBespeak = (RelativeLayout) findViewById(R.id.rl_bespeak_txt);
        txtCancel = (TypeFaceTextView) findViewById(R.id.tv_cancel_txt);
        rlCancel = (RelativeLayout) findViewById(R.id.rl_cancel_txt);
    }

    @Override
    public void userStatusChanged() {
        if ("1".equals(liveDetailEntity.getIsVip()) && UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
            if (liveDetailEntity.getIsBooking() != 0) {
                if (StringUtils.currentTimeInStartTimeAndEndTime(liveDetailEntity.getBookingStartTime(), liveDetailEntity.getBookingEndTime())) {
                    liveState = LiveState.HASPREBUY;
                    buildAlarm();
                } else {
                    liveState = LiveState.CANSEE;
                }
            } else {
                liveState = LiveState.CANSEE;
            }
            resetBespeakBtn();
        }
    }

    @Override
    public void userStatusNoChanged() {

    }

    private static final class OnDliveClickListener implements View.OnClickListener {

        private WeakReference<DliveDetailActivity> reference;

        public OnDliveClickListener(DliveDetailActivity activity) {
            reference = new WeakReference<DliveDetailActivity>(activity);
        }

        @Override
        public void onClick(View v) {
            DliveDetailActivity activity = reference.get();
            if (null != activity) {
                switch (v.getId()) {
                    case R.id.rl_bespeak_txt:
                        switch (activity.liveState) {
                            case CANSEE:
                                activity.startLive(activity.liveStatusEntity, activity.liveDetailEntity.getSaleStyle());
                                break;
                            case PREBUY:
                                if (StringUtils.currentTimeInStartTimeAndEndTime(activity.liveDetailEntity.getBookingStartTime(), activity.liveDetailEntity.getBookingEndTime())) {
                                    activity.startPay();
                                } else {
                                    ToastUtil.showToast(activity.getApplicationContext(), "直播销售还未开始，请稍后~", Toast.LENGTH_SHORT);
                                }
                            case NEEDBUY:
                                if (StringUtils.currentTimeInStartTimeAndEndTime(activity.liveDetailEntity.getSaleStartTime(), activity.getDeadLineTime())) {
                                    activity.startPay();
                                } else {
                                    if (StringUtils.currentTimeAfterOrderTime(activity.getDeadLineTime())) {
                                        ToastUtil.showToast(activity.getApplicationContext(), "直播已接近尾声，无法入场，请勿购买~", Toast.LENGTH_SHORT);
                                    } else {
                                        ToastUtil.showToast(activity.getApplicationContext(), "直播销售还未开始，请稍后~", Toast.LENGTH_SHORT);
                                    }
                                }
                                break;
                            case NEEVIP:
                                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.NOLOGIN) {
                                    UserStateUtil.getInstance().dealLogin(activity);
                                } else {
                                    UserStateUtil.getInstance().becomeVip(activity, "9");
                                }
                                break;
                            case HASPREBUY:
                                if (activity.liveDetailEntity.getIsBooking() != 0) {
                                    if (StringUtils.currentTimeAfterOrderTime(activity.liveDetailEntity.getBookingEndTime())) {
                                        activity.liveState = LiveState.CANSEE;
                                        activity.startLive(activity.liveStatusEntity);
                                    } else {
                                        if (null != activity.preBuyDialog) {
                                            activity.preBuyDialog.showWindow();
                                        }
                                        activity.buildAlarm();
                                    }
                                }
                                break;
                            case ERROR:
                                ToastUtil.showToast(activity.getApplicationContext(), "直播数据出错，暂时无法观看", Toast.LENGTH_SHORT);
                                break;
                        }
                        break;
                    case R.id.rl_cancel_txt:
                        activity.finish();
                        break;
                }
            }

        }
    }

    private String getDeadLineTime() {
        String deadLine;
        if (null != liveDetailEntity.getProductPlayAfterMinutes()) {
            long startAfter;
            if (liveDetailEntity.getIsBooking() != 0) {
                startAfter = StringUtils.getTimeMillis(liveDetailEntity.getBookingEndTime()) + liveDetailEntity.getProductPlayAfterMinutes() * 60 * 1000;
            } else {
                startAfter = StringUtils.getTimeMillis(liveDetailEntity.getSaleStartTime()) + liveDetailEntity.getProductPlayAfterMinutes() * 60 * 1000;
            }
            deadLine = StringUtils.getTimeMillis(liveDetailEntity.getSaleEndTime()) > startAfter ? StringUtils.parseDateAll(startAfter) : liveDetailEntity.getSaleEndTime();
        } else {
            deadLine = liveDetailEntity.getSaleEndTime();
        }
        Log.d(TAG, "deadLine=" + deadLine);
        return deadLine;
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/11/3
     * @Description 跳转直播
     */

    private void startLive(LiveStatusEntity live) {
        startLive(live, "0");
    }

    private void startLive(LiveStatusEntity live, String buyType) {
        Log.d(TAG, "startLive");
        Intent intent = new Intent();
        intent.setAction("com.hiveview.cloudscreen.action.external_enter_videolive");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        String channelInfo = JSON.toJSONString(live);
        Log.d(TAG, "channelInfo=" + channelInfo);
        intent.putExtra("channelInfo", channelInfo);
        String statisticInfo = JSON.toJSONString(new LiveStatisticEntity(live.getTvName(), "1".equals(live.getSignalType()) ? 1 : 3, live.getTvId(), buyType));
        Log.d(TAG, "statisticInfo=" + statisticInfo);
        intent.putExtra("statisticInfo", statisticInfo); // 用json传
        intent.putExtra("source", "2"); // 来源1:首映 2:大麦影视
        intent.putExtra("videoType", "1"); //视频类型，预留 正常观看：1；试看.
        startActivity(intent);
        finish();
    }


    /**
     * @Author Spr_ypt
     * @Date 2016/12/7
     * @Description 跳转购买
     */
    private void startPay() {
        Intent intent = new Intent(VideoDetailInvoker.ACTION_GOODS_PAG);
        intent.putExtra("target", 1);//int 型，0表示传递商品包id，1表示传递影片id
        intent.putExtra("userId", UserStateUtil.getInstance().getUserInfo().userId + "");
        intent.putExtra("videoId", tvId + "");//影片id
        Log.d("test", "getSignalType=" + liveStatusEntity.getSignalType());
        intent.putExtra("videoType", "1".equals(liveStatusEntity.getSignalType()) ? "4" : "6");//直播4，轮播6
        intent.putExtra("templateId", DeviceInfoUtil.getInstance().getDeviceInfo(getApplicationContext()).templetId + "");//模板id
        intent.putExtra("packageName", AppConstants.APK_PACKAGE_NAME);//包名
        Log.i(TAG, "打开支付app");
        startActivity(intent);

    }

    /**
     * @Author Spr_ypt
     * @Date 2016/11/3
     * @Description 鉴权监听
     */
    private static final class CheckStatusListener implements OnRequestResultListener<ResultEntity<LiveStatusEntity>> {


        private WeakReference<DliveDetailActivity> reference;

        public CheckStatusListener(DliveDetailActivity activity) {
            reference = new WeakReference<>(activity);
        }


        @Override
        public void onSucess(ResultEntity<LiveStatusEntity> result) {
            DliveDetailActivity activity = reference.get();
            if (null != activity) {
                activity.liveStatusEntity = (LiveStatusEntity) result.getEntity();
                if (null != activity.liveStatusEntity) {
                    if ("1".equals(activity.liveStatusEntity.getIsPay())) {
                        activity.liveState = LiveState.CANSEE;
                    } else if ("1".equals(activity.liveStatusEntity.getOrderStatus())) {
                        activity.liveState = LiveState.CANSEE;
                    } else {
                        checkPrice0(activity);
                    }
                    //再次确认是否是预购买状态
                    if (activity.liveState == LiveState.CANSEE) {
                        if (activity.liveDetailEntity.getIsBooking() != 0) {
                            if (StringUtils.currentTimeInStartTimeAndEndTime(activity.liveDetailEntity.getBookingStartTime(), activity.liveDetailEntity.getBookingEndTime())) {
                                activity.liveState = LiveState.HASPREBUY;
                            }
                        }
                    } else if (activity.liveState == LiveState.NEEDBUY) {
                        if (activity.liveDetailEntity.getIsBooking() != 0) {
                            if (StringUtils.currentTimeInStartTimeAndEndTime(activity.liveDetailEntity.getBookingStartTime(), activity.liveDetailEntity.getBookingEndTime())) {
                                activity.liveState = LiveState.PREBUY;
                            }
                        }
                    }
                    //解析时如果鉴权返回影片的完整信息则arg1设置为1
                    if (result.arg1 != 1) {
                        activity.createLiveStatus();
                    }
                    activity.resetBespeakBtn();
                } else {
                    ToastUtil.showToast(CloudScreenApplication.getInstance().getApplicationContext(), "鉴权失败", Toast.LENGTH_SHORT);
                    activity.finish();
                }
            }
        }

        /**
         * @Author Spr_ypt
         * @Date 2017/3/2
         * @Description 鉴定是否存在限时免费的情况
         */
        private void checkPrice0(DliveDetailActivity activity) {
            if (null != activity.liveDetailEntity.getNowPrice() && activity.liveDetailEntity.getNowPrice() == 0) {
                //价格为0免费看
                activity.liveState = LiveState.CANSEE;
            } else if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER && null != activity.liveDetailEntity.getVipNowPrice() && activity.liveDetailEntity.getVipNowPrice() == 0) {
                //vip用户且vip价格为0免费看
                activity.liveState = LiveState.CANSEE;
            } else {
                activity.liveState = LiveState.NEEDBUY;
            }
        }

        @Override
        public void onFail(Exception e) {
            Log.e(TAG, "CheckStatusListener.onFail e=" + e.toString());
            DliveDetailActivity activity = reference.get();
            if (null != activity) {
                ToastUtil.showToast(CloudScreenApplication.getInstance().getApplicationContext(), "鉴权失败", Toast.LENGTH_SHORT);
                activity.finish();
            }
        }

        @Override
        public void onParseFail(HiveviewException e) {
            Log.e(TAG, "CheckStatusListener.onParseFail e=" + e.toString());
            DliveDetailActivity activity = reference.get();
            if (null != activity) {
                ToastUtil.showToast(CloudScreenApplication.getInstance().getApplicationContext(), "鉴权失败", Toast.LENGTH_SHORT);
                activity.finish();
            }
        }
    }


    /**
     * @Author Spr_ypt
     * @Date 2016/12/7
     * @Description 请求详情接口
     */
    private static final class GetLiveDetailListener implements OnRequestResultListener<ResultEntity<LiveDetailEntity>> {

        private WeakReference<DliveDetailActivity> reference;

        public GetLiveDetailListener(DliveDetailActivity activity) {
            reference = new WeakReference<DliveDetailActivity>(activity);
        }

        @Override
        public void onSucess(ResultEntity<LiveDetailEntity> resultEntity) {
            final DliveDetailActivity activity = reference.get();
            if (null != activity) {
                activity.liveDetailEntity = (LiveDetailEntity) resultEntity.getEntity();

                if (activity.liveDetailEntity.getIsBooking() != 0 && !StringUtils.currentTimeAfterOrderTime(activity.liveDetailEntity.getBookingEndTime())) {
                    //有预约且在预约期显示播放前的图片
                    DisplayImageUtil.getInstance().displayImage(activity.liveDetailEntity.getBeforePlayLogo(), activity.bg);
                } else {
                    //正常情况下都是显示播放中图片
                    DisplayImageUtil.getInstance().displayImage(activity.liveDetailEntity.getPlayLogo(), activity.bg);
                }
                if ("2".equals(activity.liveDetailEntity.getIsPay()) && "1".equals(activity.liveDetailEntity.getIsVip())) {
                    //购买√，VIP√，VIP直接看，否则去鉴权
                    if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                        activity.liveState = LiveState.CANSEE;
                        if (activity.liveDetailEntity.getIsBooking() != 0) {
                            if (!StringUtils.currentTimeAfterOrderTime(activity.liveDetailEntity.getBookingEndTime())) {
                                //还在预购期内的VIP权限直播，VIP用户直接算预定成功的
                                activity.liveState = LiveState.HASPREBUY;
                                activity.buildAlarm();
                            }
                        }
                        activity.createLiveStatus();
                        activity.resetBespeakBtn();
                    } else {
                        CloudScreenService.getInstance().checkLiveStatus(activity.tvId + "", activity.liveDetailEntity.getDamaiId(), new CheckStatusListener(activity));
                    }
                } else if (!"2".equals(activity.liveDetailEntity.getIsPay()) && "1".equals(activity.liveDetailEntity.getIsVip())) {
                    //购买×，VIP√ vip才能看
                    if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                        activity.liveState = LiveState.CANSEE;
                    } else {
                        activity.liveState = LiveState.NEEVIP;
                    }
                    activity.createLiveStatus();
                    activity.resetBespeakBtn();
                } else if ("2".equals(activity.liveDetailEntity.getIsPay()) && !"1".equals(activity.liveDetailEntity.getIsVip())) {
                    //购买√，VIP×，买了才能看，去鉴权
                    CloudScreenService.getInstance().checkLiveStatus(activity.tvId + "", activity.liveDetailEntity.getDamaiId(), new CheckStatusListener(activity));
                } else {
                    //购买×，VIP×，谁都可以看
                    activity.liveState = LiveState.CANSEE;
                    activity.createLiveStatus();
                    activity.resetBespeakBtn();
                }

                //预售情况需要创建提示窗
                if (activity.liveDetailEntity.getIsBooking() != 0) {
                    String tip = "直播将于" + StringUtils.formatDate(activity.liveDetailEntity.getBookingEndTime()) + "开始，欢迎届时观看。";
                    activity.preBuyDialog = new VipVideoDialog(activity, tip, null, "确定", VipVideoDialog.DialogBg.D, false);
                    activity.preBuyDialog.setOnBtnClicklistener(new VipVideoDialog.OnBtnClickListener() {
                        @Override
                        public void onPositiveBtnClick() {
                            activity.preBuyDialog.dismiss();
                        }

                        @Override
                        public void onNegativeBtnClick() {
                            activity.preBuyDialog.dismiss();
                        }

                        @Override
                        public void onDismissWithoutPressBtn() {

                        }
                    });
                }

            }
        }


        @Override
        public void onFail(Exception e) {
            Log.e(TAG, "onFail e=" + e.toString());
            DliveDetailActivity activity = reference.get();
            if (null != activity) {
                ToastUtil.showToast(CloudScreenApplication.getInstance().getApplicationContext(), "直播已下线", Toast.LENGTH_SHORT);
                activity.finish();
            }
        }

        @Override
        public void onParseFail(HiveviewException e) {
            Log.e(TAG, "onParseFail e=" + e.toString());
            DliveDetailActivity activity = reference.get();
            if (null != activity) {
                ToastUtil.showToast(CloudScreenApplication.getInstance().getApplicationContext(), "直播已下线", Toast.LENGTH_SHORT);
                activity.finish();
            }
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/12/14
     * @Description 当无需鉴权时通过该方法来构建鉴权后实体类
     */
    private void createLiveStatus() {
        String liveDetail = JSON.toJSONString(liveDetailEntity);
        String liveStatus = JSON.toJSONString(liveStatusEntity);
        Log.d("test", "liveDetail=" + liveDetail);
        Log.d("test", "liveStatus=" + liveStatus);

        liveStatusEntity = JSON.parseObject(JSON.toJSONString(liveDetailEntity), LiveStatusEntity.class);
        liveStatusEntity.setTvId(liveDetailEntity.getLiveId());
    }

    private class PayReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                try {
                    String json = intent.getStringExtra("payCashResult");
                    String code = JSON.parseObject(json).getString("code");
                    Log.d("test", "code=" + code);
                    String royaltyType = JSON.parseObject(json).getString("royalty_type");
                    String packageName = JSON.parseObject(json).getString("packageName");
                    if (AppConstants.APK_PACKAGE_NAME.equals(packageName)) {
                        if ("N000000".equals(code)) {
                            if (liveDetailEntity.getIsBooking() != 0) {
                                if (StringUtils.currentTimeAfterOrderTime(liveDetailEntity.getBookingEndTime())) {
                                    liveState = LiveState.CANSEE;
                                    startLive(liveStatusEntity, royaltyType);
                                    finish();
                                } else {
                                    liveState = LiveState.HASPREBUY;
                                    resetBespeakBtn();
                                    if (null != preBuyDialog) {
                                        preBuyDialog.showWindow();
                                    }
                                    if ("1".equals(royaltyType)) {
                                        buildAlarm();
                                    }
                                }
                            } else {
                                liveState = LiveState.CANSEE;
                                startLive(liveStatusEntity);
                                finish();
                            }

                        } else {
                            ToastUtil.showToast(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT);
                        }
                    }
                } catch (Exception e) {
                    Log.d(TAG, "支付数据接收失败！");
                }
            }
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/12/20
     * @Description 设置提醒
     */
    private void buildAlarm() {
        long preTime = 15 * 60 * 1000L;
        Bundle bundle = new Bundle();
        bundle.putString("title", liveDetailEntity.getTvName());
        bundle.putString("content", "您预约的直播" + liveDetailEntity.getTvName() + "将在" + StringUtils.formatDate(liveDetailEntity.getBookingEndTime()) + "正式放映,欢迎届时观看。");
        bundle.putString("imgUrl", liveDetailEntity.getTvlogo());
        bundle.putInt("location", AlarmServiceUtil.LOCATION_TOP);
        bundle.putLong("time", StringUtils.getTimeMillis(liveDetailEntity.getBookingEndTime()) - preTime);
        //提醒有效时长
        bundle.putLong("timeEffective", StringUtils.getTimeMillis(liveDetailEntity.getSaleEndTime()));
        try {
            AlarmServiceUtil.getInstant().remind(this, bundle);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    private void resetBespeakBtn() {
        switch (liveState) {
            case CANSEE:
                txtBespeak.setText("播 放");
                break;
            case NEEDBUY:
                txtBespeak.setText("购 买");
                break;
            case NEEVIP:
                txtBespeak.setText("开通VIP");
                break;
            case PREBUY:
                txtBespeak.setText("预 购");
                break;
            case HASPREBUY:
                txtBespeak.setText("播 放");
                break;
            case ERROR:
                txtBespeak.setText("出错了");
                break;
        }
    }

    private void resetVipColor() {
        if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
            rlBespeak.setBackgroundResource(R.drawable.focused_view_vip_selector);
            rlCancel.setBackgroundResource(R.drawable.focused_view_vip_selector);
        } else {
            rlBespeak.setBackgroundResource(R.drawable.focused_view_selector);
            rlCancel.setBackgroundResource(R.drawable.focused_view_selector);
        }
    }

    enum LiveState {
        CANSEE,
        NEEDBUY,
        NEEVIP,
        PREBUY,
        HASPREBUY,
        ERROR;
    }
}
