package com.hiveview.cloudscreen.vipvideo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.activity.adapter.CollectV2Adapter;
import com.hiveview.cloudscreen.vipvideo.activity.adapter.RecordAdapter;
import com.hiveview.cloudscreen.vipvideo.common.SizeConstant;
import com.hiveview.cloudscreen.vipvideo.util.AppStoreUtils;
import com.hiveview.cloudscreen.vipvideo.util.DateWithRecordUtils;


public class DeleteWindow extends PopupWindow {

    /**
     * @Fields _1
     */
    public static final int DELETE_HISTORY = 1;
    /**
     * @Fields _0
     */
    public static final int DELETE_FAVOUR = 0;

    private SizeConstant sizeConstant;
    private RecordAdapter.CollectCallBack callBack;
    private CollectV2Adapter.OnActionListener listener;

    public DeleteWindow(Context context, Bitmap bitmap, RecordAdapter.CollectCallBack callBack, int contentFlag, String notice, SizeConstant sizeConstant) {
        super(context);
        this.ctx = context;
        this.callBack = callBack;
        this.contentFlag = contentFlag;
        this.sizeConstant = sizeConstant;
        init(bitmap, notice);
    }

    public DeleteWindow(Context context, Bitmap bitmap, CollectV2Adapter.OnActionListener listener, int contentFlag, String notice, SizeConstant sizeConstant) {
        super(context);
        this.ctx = context;
        this.listener = listener;
        this.contentFlag = contentFlag;
        this.sizeConstant = sizeConstant;
        init(bitmap, notice);
    }

    private Context ctx;
    private View container;
    private TypeFaceTextView title;
    private TypeFaceTextView press;
    private TypeFaceTextView cancel;
    private RelativeLayout press_focus;
    private RelativeLayout cancel_focus;
    private int contentFlag;// 0表示作用于收藏 1表示历史

    private void init(Bitmap bitmap, String notice) {
        LayoutInflater mLayoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = mLayoutInflater.inflate(R.layout.delete_all_layout, null);
        title = (TypeFaceTextView) container.findViewById(R.id.tv_vipdialog_tips);
        if (null != notice) {

            title.setText(notice);
        }
        press = (TypeFaceTextView) container.findViewById(R.id.tv_vipdialog_positive_txt);
        cancel = (TypeFaceTextView) container.findViewById(R.id.tv_vipdialog_negative_txt);
        press_focus = (RelativeLayout) container.findViewById(R.id.rl_vipdialog_positive_txt);
        cancel_focus = (RelativeLayout) container.findViewById(R.id.rl_vipdialog_negative_txt);
        press_focus.setOnFocusChangeListener(changedFocus);
        cancel_focus.setOnFocusChangeListener(changedFocus);
        press_focus.setOnClickListener(onClick);
        cancel_focus.setOnClickListener(onClick);
        this.setContentView(container);
        this.setWidth(sizeConstant.getBoxWidth());
        this.setHeight(sizeConstant.getBoxHeight());
        this.setFocusable(true);
        this.setAnimationStyle(R.style.delete_popu_in_out_style);
        this.setOutsideTouchable(false);
        if (null != bitmap) {
            this.setBackgroundDrawable(new BitmapDrawable(bitmap));
        }
        this.update();
    }

    public void showWindow() {
        this.showAtLocation(container, Gravity.CENTER, 0, 0);
    }

    private OnFocusChangeListener changedFocus = new OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            onFocus(v, hasFocus);
        }
    };

    private void onFocus(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.rl_vipdialog_positive_txt:
                if (hasFocus) {
                    press.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    press.setTextColor(Color.parseColor("#88FFFFFF"));
                }
                break;
            case R.id.rl_vipdialog_negative_txt:
                if (hasFocus) {
                    cancel.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    cancel.setTextColor(Color.parseColor("#88FFFFFF"));
                }
                break;
        }
    }

    private OnClickListener onClick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_vipdialog_positive_txt:
                    if (contentFlag == DELETE_FAVOUR) {
                        //nothing need to do
                    } else if (contentFlag == DELETE_HISTORY) {
                        DateWithRecordUtils.deleteAllQiYiRecord(ctx);
                        AppStoreUtils.deleteAll(ctx);
                    }
                    DeleteWindow.this.dismiss();
                    if (null != callBack) {
                        callBack.deleteAll(true);
                    }
                    if (null != listener) {
                        listener.removeAll();
                    }
                    break;
                case R.id.rl_vipdialog_negative_txt:
                    DeleteWindow.this.dismiss();
                    break;
            }
        }
    };

}
