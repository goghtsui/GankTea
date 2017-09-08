package com.hiveview.cloudscreen.vipvideo.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/11
 */
public class VipVideoDetailDialog extends PopupWindow {

    private Context ctx;
    /**
     * 提示文字
     */
    private String tips;
    /**
     * 确定键文本
     */
    private String positiveTxt;
    /**
     * 取消键文本
     */
    private String negativeTxt;
    /**
     * 布局view
     */
    private View container;
    /**
     * 提示TextView
     */
    private TypeFaceTextView tipsTextView;
    /**
     * 确定键TextView
     */
    private TypeFaceTextView positiveTextView;
    /**
     * 取消键TextView
     */
    private TypeFaceTextView negativeTextView;
    /**
     * 确定键布局，用于设定点击事件
     */
    private RelativeLayout positiveLayout;
    /**
     * 取消键布局，用于设定点击事件
     */
    private RelativeLayout negativeLayout;

    private RelativeLayout bg;
    private boolean isPressBtn = false;
    OnBtnClickListener listener;

    public enum DialogBg {
        VIP, D, W
    }

    public VipVideoDetailDialog(Context context, String tips, String positiveTxt, String negativeTxt, DialogBg bgRes, boolean isFirstWordColorful, boolean isSetLineSpacing) {
        this.ctx = context;
        this.tips = tips;
        this.positiveTxt = positiveTxt;
        this.negativeTxt = negativeTxt;
        //加载布局
        initView();

        //设置控件属性
        setViewFeature(tips, positiveTxt, negativeTxt, bgRes, isFirstWordColorful, isSetLineSpacing);

    }


    /**
     * @Author Spr_ypt
     * @Date 2015/11/11
     * @Description 设置控件属性
     */
    private void setViewFeature(String tips, String positiveTxt, String negativeTxt, DialogBg bgRes, boolean isFirstWordColorful, boolean isSetLineSpacing) {
        SpannableString spTips = new SpannableString(tips);
        if (isFirstWordColorful) {//设置首字母变大变黄
            spTips.setSpan(new ForegroundColorSpan(Color.parseColor("#CCBA0D")), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spTips.setSpan(new RelativeSizeSpan(1.25f), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tipsTextView.setText(spTips);
        if (isSetLineSpacing) {
            tipsTextView.setLineSpacing(20, 1.0f);
        }
        positiveTextView.setText(positiveTxt);
        negativeTextView.setText(negativeTxt);

        if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
            positiveLayout.setBackgroundResource(R.drawable.focused_view_vip_selector);
            negativeLayout.setBackgroundResource(R.drawable.focused_view_vip_selector);
        }
        positiveLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    positiveTextView.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    positiveTextView.setTextColor(Color.parseColor("#88FFFFFF"));
                }
            }
        });
        negativeLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    negativeTextView.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    negativeTextView.setTextColor(Color.parseColor("#88FFFFFF"));
                }
            }
        });
        switch (bgRes) {
            case VIP:
                bg.setBackgroundResource(R.drawable.view_dialog_vip_bg);
                break;
            case D:
                bg.setBackgroundResource(ResourceProvider.getInstance().getViewDialogBg());
                break;
            case W:
                bg.setBackgroundResource(R.drawable.view_dialog_warn_bg);
                break;
            default:
                bg.setBackgroundResource(ResourceProvider.getInstance().getViewDialogBg());
                break;
        }

        this.setContentView(container);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.update();
    }

    private void initView() {
        container = LayoutInflater.from(ctx).inflate(R.layout.view_dialog_vip_warn, null);
        tipsTextView = (TypeFaceTextView) container.findViewById(R.id.tv_vipdialog_tips);
        positiveTextView = (TypeFaceTextView) container.findViewById(R.id.tv_vipdialog_positive_txt);
        negativeTextView = (TypeFaceTextView) container.findViewById(R.id.tv_vipdialog_negative_txt);
        positiveLayout = (RelativeLayout) container.findViewById(R.id.rl_vipdialog_positive_txt);
        negativeLayout = (RelativeLayout) container.findViewById(R.id.rl_vipdialog_negative_txt);
        bg = (RelativeLayout) container.findViewById(R.id.rl_vipdialog_bg);
    }

    public void showWindow() {
        this.showAtLocation(container, Gravity.CENTER, 0, 0);
    }

    public void setOnBtnClicklistener(final OnBtnClickListener listener) {
        this.listener = listener;
        positiveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPressBtn = true;
                listener.onPositiveBtnClick();
            }
        });
        negativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPressBtn = true;
                listener.onNegativeBtnClick();
            }
        });
    }

    public interface OnBtnClickListener {
        public void onPositiveBtnClick();

        public void onNegativeBtnClick();

        //返回键执行操作
        public void onDismissWithoutPressBtn();
    }

    @Override
    public void dismiss() {
        if (!isPressBtn && null != listener) {
            listener.onDismissWithoutPressBtn();
        }
        super.dismiss();
    }
}
