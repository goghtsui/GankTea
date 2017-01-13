package com.gogh.afternoontea.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.app.WebWidget;
import com.gogh.afternoontea.entity.gank.GankEntity;
import com.gogh.afternoontea.ui.GankDetailActivity;
import com.gogh.afternoontea.utils.IntentUtils;
import com.gogh.afternoontea.utils.StringUtil;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/9/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/9/2017 do fisrt create. </li>
 */
public class NopicWebview implements WebWidget {

    private GankEntity.ResultsBean mData;//详情数据
    private Context context;
    private WebView mWebview;
    private ProgressBar mProgressBar;

    public NopicWebview(Context activity, GankEntity.ResultsBean mData) {
        this.context = activity;
        this.mData = mData;
    }

    /**
     * 根布局
     *
     * @return
     */
    @Override
    public int getLayoutResId() {
        return R.layout.gank_detail_layout_nopic;
    }

    /**
     * 创建view
     *
     * @return
     */
    @Override
    public void onCreateView() {
        mProgressBar = (ProgressBar) ((GankDetailActivity) context).findViewById(R.id.progress);
        mWebview = (WebView) ((GankDetailActivity) context).findViewById(R.id.gank_detail_webview);
        setUpWebView(mWebview);
    }

    /**
     * 绑定数据到view
     *
     * @return
     */
    @Override
    public void onBindData() {
        mWebview.loadUrl(mData.getUrl());
    }

    @Override
    public void copyContent() {
        String copyDone = context.getResources().getString(R.string.toast_copy_done);
        StringUtil.copyToClipBoard(context, mWebview.getUrl(), copyDone);
    }

    @Override
    public void openBySystemBrowser() {
        IntentUtils.openWithBrowser(mWebview.getUrl(), context);
    }

    @Override
    public void shareUrl() {
        IntentUtils.share(context, mWebview.getUrl());
    }

    @Override
    public void onBackPressed() {
        mWebview.goBack();
    }

    @Override
    public boolean canGoBack() {
        return mWebview.canGoBack();
    }


    /**
     * 获取主题颜色
     *
     * @return
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
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
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webView.setWebViewClient(new GankWebViewClient());
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
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            mProgressBar.setVisibility(View.GONE);
        }
    }

}
