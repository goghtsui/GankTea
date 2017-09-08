package com.hiveview.cloudscreen.vipvideo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.activity.VipVideoDetailActivity;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;
import com.hiveview.manager.PingListener;

import java.lang.ref.WeakReference;


public class VipVideoDetailHeaderView extends RelativeLayout implements View.OnFocusChangeListener {
    private static final String TAG = VipVideoDetailHeaderView.class.getSimpleName();
    /**
     * 内容加载容器
     */
    private View container;
    /**
     * 用户背景图
     */
    LinearLayout userBgLayout;
    /**
     * 用户登录布局
     */
    LinearLayout userLoginLayout;
    /**
     * 开通会员布局
     */
    LinearLayout userGetvipLayout;
    /**
     * 用户名文本
     */
    TypeFaceTextView usernameTxt;

    /**
     * vip logo
     */
    private View viplogo;
    private OnFocusChangeListener focusChangeListener;

    public VipVideoDetailHeaderView(Context context) {
        super(context);
        init(context);
    }

    public VipVideoDetailHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VipVideoDetailHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 按键监听
     */
    private OnBtnClickListener listener;


    /**
     * 加载、初始化
     *
     * @param context
     */
    private void init(Context context) {
        //添加布局
        container = LayoutInflater.from(context).inflate(R.layout.activity_view_datil_header, null);
        addView(container);
        //获取布局控件以及设置控件监听
        findView();
        //设置用户状态
        setUserStatus(UserStateUtil.getInstance().getUserStatus());

    }


    /**
     * 获取布局控件
     */
    View bg_open_vip;
    private void findView() {
        userBgLayout = (LinearLayout) container.findViewById(R.id.ll_detail_header_user_bg);
        userLoginLayout = (LinearLayout) container.findViewById(R.id.ll_detail_header_user_login);
        userGetvipLayout = (LinearLayout) container.findViewById(R.id.ll_detail_header_user_getvip);
        //用户名
        usernameTxt = (TypeFaceTextView) container.findViewById(R.id.v_detail_header_username);
        viplogo = container.findViewById(R.id.v_detail_header_viplogo);
//        bg_open_vip=container.findViewById(R.id.bg_open_vip);

        //设置监听
        userLoginLayout.setOnClickListener(new UserLoginOnClickListener(this));
        userGetvipLayout.setOnClickListener(new UserGetvipOnClickListener(this));
        userLoginLayout.setOnFocusChangeListener(this);
        userGetvipLayout.setOnFocusChangeListener(this);
    }

    /**
     * 根据剧集打开状态头部是否获取焦点。
     *
     * @param flag
     */
    public void setUserLoginLayout(boolean flag) {
        userLoginLayout.setFocusable(flag);
        userGetvipLayout.setFocusable(flag);
    }


    /**
     * 设置用户状态
     *
     * @param userStatus 用户状态值
     */
    public void setUserStatus(UserStateUtil.UserStatus userStatus) {
        switch (userStatus) {
            case NOLOGIN:
                userLoginLayout.setVisibility(View.VISIBLE);
                userGetvipLayout.setVisibility(View.GONE);
                usernameTxt.setVisibility(View.GONE);
                viplogo.setVisibility(View.GONE);
                break;
            case NOMALUSER:
                userLoginLayout.setVisibility(View.GONE);
                userGetvipLayout.setVisibility(View.VISIBLE);
                usernameTxt.setVisibility(View.VISIBLE);
                usernameTxt.setText(UserStateUtil.getInstance().getUserInfo().userAccount);
                viplogo.setVisibility(View.GONE);
                break;
            case VIPUSER:
                userLoginLayout.setVisibility(View.GONE);
                userGetvipLayout.setVisibility(View.GONE);
                usernameTxt.setVisibility(View.VISIBLE);
                usernameTxt.setText(UserStateUtil.getInstance().getUserInfo().userAccount);
                viplogo.setVisibility(View.VISIBLE);
                break;
        }


    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (null != focusChangeListener) {
            focusChangeListener.onFocusChange(v, hasFocus);
        }
    }

    public void setOnFocusChangeListener(View.OnFocusChangeListener listener) {
        focusChangeListener = listener;
    }

    /**
     * 登录按钮监听
     */
    private static class UserLoginOnClickListener implements OnClickListener {

        private WeakReference<VipVideoDetailHeaderView> reference;

        private UserLoginOnClickListener(VipVideoDetailHeaderView headerView) {
            reference = new WeakReference<VipVideoDetailHeaderView>(headerView);
        }

        @Override
        public void onClick(View v) {
            VipVideoDetailHeaderView headerView = reference.get();
            headerView.listener.onLoginClick(UserStateUtil.getInstance().getUserStatus());
        }
    }

    /**
     * 开通会员按钮监听
     */
    private static class UserGetvipOnClickListener implements OnClickListener {

        private WeakReference<VipVideoDetailHeaderView> reference;

        private UserGetvipOnClickListener(VipVideoDetailHeaderView headerView) {
            this.reference = new WeakReference<VipVideoDetailHeaderView>(headerView);
        }

        @Override
        public void onClick(View v) {
            VipVideoDetailHeaderView headerView = reference.get();
            headerView.listener.onVipClick(UserStateUtil.getInstance().getUserStatus());
        }
    }

    public interface OnBtnClickListener {
        public void onLoginClick(UserStateUtil.UserStatus status);

        public void onVipClick(UserStateUtil.UserStatus status);
    }

    public void setBtnClickListener(OnBtnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
                return true;//顶部按钮不响应左右键
            }
        }
        return super.dispatchKeyEvent(event);
    }

}
