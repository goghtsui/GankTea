package com.gogh.afternoontea.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.entity.gank.GankEntity;
import com.gogh.afternoontea.main.BaseAppCompatActivity;
import com.gogh.afternoontea.utils.StringUtil;
import com.squareup.picasso.Picasso;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/28/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/28/2016 do fisrt create. </li>
 */
public class GankDetailActivity extends BaseAppCompatActivity {

    private ImageView ivImage;
    private CollapsingToolbarLayout collapsing_toolbar;
    private ProgressBar progress;
    private WebView webView;
    private GankEntity.ResultsBean mData;//详情数据

    @Override
    protected void updateThemeByChoice(int resId) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 锁定竖屏
        setContentView(R.layout.gank_detail_layout);
        initView();
    }

    private void initView() {
        ivImage = (ImageView) findViewById(R.id.ivImage);
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        progress = (ProgressBar) findViewById(R.id.progress);
        webView = (WebView) findViewById(R.id.gank_detail_webview);
        setUpWebView(webView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* // 给左上角图标的左边加上一个返回的图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();//返回上一级
            }
        });*/
    }

    /**
     * 配置webview的相关属性
     *
     * @param webView 使用的webview
     */
    public void setUpWebView(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webView.setWebViewClient(new GankWebViewClient());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mData = (GankEntity.ResultsBean) getIntent().getSerializableExtra(Urls.GANK_URL.BUNDLE_KEY);
        if (mData != null) {
            collapsing_toolbar.setTitle(mData.getDesc());
            if (mData.getImages() != null && mData.getImages().size() > 0) {
                Picasso.with(getApplicationContext()).load(mData.getImages().get(0))
                        .config(Bitmap.Config.ARGB_8888).into(ivImage);
                collapsing_toolbar.setExpandedTitleColor(getColorPrimary());// 白色背景图片下，默认白色文字看不清，故设置成主题色
            }

            webView.loadUrl(mData.getUrl());
        }
    }

    /**
     * 获取主题颜色
     *
     * @return
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_copy_url:
                String copyDone = getString(R.string.toast_copy_done);
                StringUtil.copyToClipBoard(this, webView.getUrl(), copyDone);
                return true;
            case R.id.action_open_url:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(webView.getUrl());
                intent.setData(uri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(GankDetailActivity.this, R.string.toast_open_fail, Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class GankWebViewClient extends WebViewClient {

        /**
         * 是否使用原浏览器加载网页
         *
         * @param view
         * @param url
         * @return
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (TextUtils.isEmpty(url)) {
                return true;
            }
            if (Uri.parse(url).getHost().equals("github.com")) {
                return false;
            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progress.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            progress.setVisibility(View.GONE);
        }
    }

}
