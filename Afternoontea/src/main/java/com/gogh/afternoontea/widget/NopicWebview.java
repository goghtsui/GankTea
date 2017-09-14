package com.gogh.afternoontea.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.app.WebWidget;
import com.gogh.afternoontea.utils.IntentUtils;
import com.gogh.afternoontea.utils.StringUtil;

import java.lang.ref.WeakReference;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/9/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/9/2017 do fisrt create. </li>
 */
public class NopicWebview implements WebWidget {

    private com.gogh.afternoontea.entity.gank.BaseEntity mData;//详情数据
    private WeakReference<Context> referenceContext;
    private WebView mWebview;
    private ProgressBar mProgressBar;

    public NopicWebview(Context context, com.gogh.afternoontea.entity.gank.BaseEntity mData) {
        this.referenceContext = new WeakReference<>(context);
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
    public void onCreateView(View rootView) {
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress);
        mWebview = (WebView) rootView.findViewById(R.id.gank_detail_webview);
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
        String copyDone = referenceContext.get().getResources().getString(R.string.toast_copy_done);
        StringUtil.copyToClipBoard(referenceContext.get(), mWebview.getUrl(), copyDone);
    }

    @Override
    public void openBySystemBrowser() {
        IntentUtils.openWithBrowser(mWebview.getUrl(), referenceContext.get());
    }

    @Override
    public void shareUrl() {
        IntentUtils.share(referenceContext.get(), mWebview.getUrl());
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
     * 配置webview的相关属性
     *
     * @param webView 使用的webview
     */
    public void setUpWebView(@NonNull WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webView.setWebViewClient(new GankWebViewClient());
    }

    @Override
    public void onDestroy() {
        mWebview.stopLoading();
        mWebview.cancelPendingInputEvents();
        mWebview.clearCache(true);
        mWebview.clearFormData();
        mWebview.clearHistory();
        mWebview.clearMatches();
        mWebview.clearSslPreferences();
        mWebview = null;
    }

    private class GankWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (TextUtils.isEmpty(request.getUrl().toString())) {
                return true;
            }
            if (Uri.parse(request.getUrl().toString()).getHost().equals(referenceContext.get().getResources().getString(R.string.gank_webview_url_host))) {
                return false;
            }
            view.loadUrl(request.getUrl().toString());
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
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            mProgressBar.setVisibility(View.GONE);
        }

    }

}
