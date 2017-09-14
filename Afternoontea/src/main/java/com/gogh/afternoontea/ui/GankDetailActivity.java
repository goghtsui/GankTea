package com.gogh.afternoontea.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.app.WebWidget;
import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.entity.gank.BaseEntity;
import com.gogh.afternoontea.main.BaseAppCompatActivity;
import com.gogh.afternoontea.preference.imp.Configuration;
import com.gogh.afternoontea.widget.NopicWebview;
import com.gogh.afternoontea.widget.PicWebview;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/28/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/28/2016 do fisrt create. </li>
 */
public class GankDetailActivity extends BaseAppCompatActivity {

    private BaseEntity mData;//详情数据
    private WebWidget webWidget;

    private View rootView;

    @Override
    protected void updateThemeByChoice(int resId) {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 锁定竖屏
        loadContentView();
    }

    private void loadContentView() {
        mData = (BaseEntity) getIntent().getSerializableExtra(Urls.GANK_URL.BUNDLE_KEY);
        if (mData != null) {
            if (Configuration.newInstance().isNopicMode()) {
                setNoPicWebview();
            } else {
                setPicWebview();
            }

            webWidget.onCreateView(rootView);
            webWidget.onBindData();
        }
    }

    public void setNoPicWebview() {
        webWidget = new NopicWebview(GankDetailActivity.this, mData);
        rootView = LayoutInflater.from(this).inflate(webWidget.getLayoutResId(), null);
        setContentView(rootView);
        setTitle(mData.getDesc());
    }

    public void setPicWebview() {
        webWidget = new PicWebview(GankDetailActivity.this, mData);
        rootView = LayoutInflater.from(this).inflate(webWidget.getLayoutResId(), null);
        setContentView(rootView);
        setTitle(null);
    }

    private void setTitle(String title) {
        if(TextUtils.isEmpty(title)){
            return;
        }

        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mToolBar.setTitle(title);
        setSupportActionBar(mToolBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share_url:
                webWidget.shareUrl();
                return true;
            case R.id.action_copy_url:
                webWidget.copyContent();
                return true;
            case R.id.action_open_url:
                webWidget.openBySystemBrowser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 重新定义系统物理返回按键的事件，是不是webview需要返回
     *
     * @return
     */
    @Override
    public void onBackPressed() {
        if (webWidget.canGoBack()) {
            webWidget.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webWidget.onDestroy();
        webWidget = null;
    }
}
