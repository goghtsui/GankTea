package com.hiveview.cloudscreen.vipvideo.fragment;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;


public class DetailDescription implements View.OnKeyListener {

    private TextView leftFilmNameView;
    private TextView leftFileDetailView;
    private TextView leftVipFilmDetailView;
    private View.OnKeyListener keyListener;

    private LinearLayout ll;

    public DetailDescription(Activity view) {
        ll = (LinearLayout) view.findViewById(R.id.ll);
        leftFilmNameView = (TextView) view.findViewById(R.id.film_name);
        leftFileDetailView = (TextView) view.findViewById(R.id.tv_film_detail);
        leftVipFilmDetailView = (TextView) view.findViewById(R.id.tv_vip_film_detail);


        resetColor(UserStateUtil.getInstance().getUserStatus());
        //1.0S 设置行间距  2748 【1.0S】【大麦影视】详情也文字展示一半
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            leftFileDetailView.setLineSpacing(11.8f, 1f);
            leftVipFilmDetailView.setLineSpacing(11.8f, 1f);
        }
    }

    public void setOnKeyListener(View.OnKeyListener listener) {
        keyListener = listener;
    }


    public boolean dispatchKeyEvent(KeyEvent event) {
        if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
            return leftVipFilmDetailView.dispatchKeyEvent(event);
        } else {
            return leftFileDetailView.dispatchKeyEvent(event);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyListener != null) {
            keyListener.onKey(v, keyCode, event);
        }
        return false;
    }

    public boolean isOpen = false;

    public boolean isLeftOpen() {
        return isOpen;
    }

    public void open(AlbumEntity entity) {
        isOpen = true;
        if (null != entity && null != entity.getAlbumName()) {
            leftFilmNameView.setText(entity.getAlbumName());
        }
        if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
            if (!TextUtils.isEmpty(entity.getAlbumDesc())) {
                leftVipFilmDetailView.setFocusable(true);
                leftVipFilmDetailView.setFocusableInTouchMode(true);
                leftVipFilmDetailView.setText(entity.getAlbumDesc());
            } else {
                leftVipFilmDetailView.setFocusable(false);
                leftVipFilmDetailView.setFocusableInTouchMode(false);
            }
            //GONE=8，INVISIBLE=4，VISIBLE=0
            leftVipFilmDetailView.setVisibility(View.VISIBLE);
            leftFileDetailView.setVisibility(View.GONE);
            Log.i("DetailDescription", "leftVipFilmDetailView:" + leftVipFilmDetailView.getVisibility() + "  leftFileDetailView:" + leftFileDetailView.getVisibility() + " leftVipFilmDetailView:" + entity.getAlbumDesc());
            leftFilmNameView.animate().translationX(50).setDuration(600).setInterpolator(new AccelerateInterpolator()).start();
            leftVipFilmDetailView.animate().translationX(50).setDuration(600).setInterpolator(new AccelerateInterpolator()).start();
        } else {
            if (!TextUtils.isEmpty(entity.getAlbumDesc())) {
                leftFileDetailView.setFocusable(true);
                leftFileDetailView.setFocusableInTouchMode(true);
                leftFileDetailView.setText(entity.getAlbumDesc());
            } else {
                leftFileDetailView.setFocusable(false);
                leftFileDetailView.setFocusableInTouchMode(false);
            }
            leftFileDetailView.setVisibility(View.VISIBLE);
            leftVipFilmDetailView.setVisibility(View.GONE);
            Log.i("DetailDescription", "else  leftFileDetailView:" + leftFileDetailView.getVisibility() + "  leftVipFilmDetailView:" + leftVipFilmDetailView.getVisibility() + "  leftFileDetailView:" + entity.getAlbumDesc());
            leftFilmNameView.animate().translationX(50).setDuration(600).setInterpolator(new AccelerateInterpolator()).start();
            leftFileDetailView.animate().translationX(50).setDuration(600).setInterpolator(new AccelerateInterpolator()).start();
        }
    }

    public void close() {
        isOpen = false;
        if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
            leftFilmNameView.animate().translationX(-200).setDuration(600).setInterpolator(new AccelerateInterpolator()).start();
            leftVipFilmDetailView.animate().translationX(-200).setDuration(600).setInterpolator(new AccelerateInterpolator()).start();
        } else {
            leftFilmNameView.animate().translationX(-200).setDuration(600).setInterpolator(new AccelerateInterpolator()).start();
            leftFileDetailView.animate().translationX(-200).setDuration(600).setInterpolator(new AccelerateInterpolator()).start();
        }
    }

    public void resetColor(UserStateUtil.UserStatus userStatus) {
        switch (userStatus) {
            case VIPUSER:
                leftVipFilmDetailView.setFocusable(true);
                leftVipFilmDetailView.setFocusableInTouchMode(true);

                leftFileDetailView.setFocusable(false);
                leftFileDetailView.setFocusableInTouchMode(false);
                // 实现textview上下滑动的功能
                leftVipFilmDetailView.setMovementMethod(ScrollingMovementMethod
                        .getInstance());
                leftVipFilmDetailView.setOnKeyListener(this);
                leftVipFilmDetailView.setVisibility(View.VISIBLE);


                leftFileDetailView.setVisibility(View.GONE);

                break;
            default:

                leftFileDetailView.setFocusable(true);
                leftFileDetailView.setFocusableInTouchMode(true);

                leftVipFilmDetailView.setFocusable(false);
                leftVipFilmDetailView.setFocusableInTouchMode(false);

                // 实现textview上下滑动的功能
                leftFileDetailView.setMovementMethod(ScrollingMovementMethod
                        .getInstance());
                leftFileDetailView.setOnKeyListener(this);
                leftFileDetailView.setVisibility(View.VISIBLE);
                leftVipFilmDetailView.setVisibility(View.GONE);
                break;
        }
    }
}