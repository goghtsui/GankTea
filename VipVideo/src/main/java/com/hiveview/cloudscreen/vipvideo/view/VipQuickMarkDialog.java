package com.hiveview.cloudscreen.vipvideo.view;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;

/**
 * Created by wangbei on 2016/3/17.
 */
public class VipQuickMarkDialog extends PopupWindow {
    /**
     * 布局view
     */
    private View container;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     *
     */
    private RelativeLayout relativeLayout;

    /**
     * 每个盒子的mac/sn会生成一个二维码
     */
    private ImageView mIcon;
    /**
     * 活动名称
     */
    private TypeFaceTextView mActivityName;

    /**
     * 提示扫描二维码文案
     */
    private TypeFaceTextView mActivityTips;

    private RelativeLayout mCompeleteLayout;
    private RelativeLayout mCancelLayout;

    /**
     * 完成按钮的提示文字
     */
    private TypeFaceTextView mCompeleteText;
    /**
     * 取消按钮的提示文字
     */
    private TypeFaceTextView mCancelText;

    /**
     * 接收活动名称
     */
    private String activityName;
    /**
     * 接收二维码的URL
     */
    private String activityUrl;

    /**
     * 二维码活动提示文字
     */
    private String activityTips;

    QuickMarkDialoglistener listener;

    public VipQuickMarkDialog(Context context, String activityUrl, String activityName,String activityTips) {
        this.mContext = context;
        this.activityUrl = activityUrl;
        this.activityName = activityName;
        this.activityTips=activityTips;
        //加载布局
        init();
        //设置属性
        setViewFeature(activityUrl, activityName,activityTips);
    }


    private void init() {
        container = LayoutInflater.from(mContext).inflate(R.layout.view_quickmark_dialog, null);
        relativeLayout= (RelativeLayout) container.findViewById(R.id.rl_white_mat_icon);
        mIcon= (ImageView) container.findViewById(R.id.iv_icon);
        mActivityName= (TypeFaceTextView) container.findViewById(R.id.tv_activity_name);
        mActivityTips= (TypeFaceTextView) container.findViewById(R.id.tv_activity_tip);
        mCompeleteLayout= (RelativeLayout) container.findViewById(R.id.btn_vipdialog_compelete_txt);
        mCancelLayout=(RelativeLayout)container.findViewById(R.id.btn_vipdialog_cancel_txt);
        mCompeleteText= (TypeFaceTextView) container.findViewById(R.id.tv_vipdialog_compelete_txt);
        mCancelText= (TypeFaceTextView) container.findViewById(R.id.tv_vipdialog_cancel_txt);

    }

    private void setViewFeature(String activityUrl, String activityName,String activityTips) {
        DisplayImageUtil.getInstance().setRound(20).displayImage(activityUrl, mIcon);
        mActivityName.setText(activityName);
        mActivityTips.setText(activityTips);

        mCompeleteLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    mCompeleteText.setTextColor(Color.parseColor("#fefefe"));
                } else {
                    mCompeleteText.setTextColor(Color.parseColor("#88FFFFFF"));
                }
            }
        });
        mCancelLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    mCancelText.setTextColor(Color.parseColor("#fefefe"));
                } else {
                    mCancelText.setTextColor(Color.parseColor("#88FFFFFF"));
                }
            }
        });


        this.setContentView(container);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.update();

    }

    public void showWindow() {
        if (!this.isShowing()) {
            this.showAtLocation(container, Gravity.CENTER, 0, 0);
        } else {
            this.dismiss();
        }

    }

    public interface QuickMarkDialoglistener {
        public void onDismissWithoutPressBtn();

        public void onCompeleteBtnClick();

        public void onCancelBtnClick();
    }

    public void setQuickMarkDialoglistener(final QuickMarkDialoglistener markDialoglistener) {
        this.listener = markDialoglistener;
        mCompeleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCompeleteBtnClick();
            }
        });

        mCancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCancelBtnClick();
            }
        });
    }

    @Override
    public void dismiss() {
        if (null != listener) {
            listener.onDismissWithoutPressBtn();
        }
        super.dismiss();
    }
}
