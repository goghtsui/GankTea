package com.gogh.afternoontea.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.log.Logger;
import com.gogh.afternoontea.main.BaseAppCompatActivity;
import com.gogh.afternoontea.permission.imp.RequestPermissionsImp;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 欢迎页，向用户请求授权所需的权限，且必须授予所有权限。</p>
 * <p> Created by <b>高晓峰</b> on 12/22/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/22/2016 do fisrt create. </li>
 */
public class LauncherActivity extends BaseAppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback, RequestPermissionsImp.OnStartActivityListener {

    private static final String TAG = "LauncherActivity";

    /**
     * 跳转到主页的延时时长
     */
    private static final int DELAY_DURATION = 1000;

    private RequestPermissionsImp requestPermissionsImp;

    @Nullable
    private Handler handler = new Handler();

    @Override
    protected void updateThemeByChoice(int resId) {
        // do nothing.
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // 全屏模式
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_layout);
        requestPermissionsImp = new RequestPermissionsImp(LauncherActivity.this);
        requestPermissionsImp.setOnStartActivityListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 隐藏导航栏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermissionsImp.requestPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d(TAG, " onDestroy.");
        // 恢复到正常模式
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, @NonNull int[] paramArrayOfInt) {
        Logger.d(TAG, " onRequestPermissionsResult.requestCode : " + requestCode);
        requestPermissionsImp.onRequestPermissionsResult(requestCode, permissions, paramArrayOfInt);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Logger.d(TAG, "onKeyDown.KEYCODE_BACK : ");
            this.finish();
            return true;
        }
        Logger.d(TAG, "onKeyDown.return.");
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onStartActivity(boolean isNeedFinish) {
        handler.postDelayed(() -> {
            startActivity(new Intent(LauncherActivity.this, HomeActivity.class));
            if (isNeedFinish) {
                finish();
            }
        }, DELAY_DURATION);
    }

    @Override
    public void onFinish() {
        finish();
    }
}
