package com.gogh.afternoontea.presenter.imp;

import android.support.annotation.NonNull;

import com.gogh.afternoontea.adapter.gank.GankListAdapter;
import com.gogh.afternoontea.entity.gank.GankEntity;
import com.gogh.afternoontea.listener.OnRefreshListener;
import com.gogh.afternoontea.listener.OnResponListener;
import com.gogh.afternoontea.location.listener.OnLodingChangedListener;
import com.gogh.afternoontea.main.BaseFragment;
import com.gogh.afternoontea.presenter.BasePresenter;
import com.gogh.afternoontea.request.RequestProxy;
import com.gogh.afternoontea.utils.Logger;

import java.util.concurrent.atomic.AtomicInteger;

import static com.gogh.afternoontea.main.BaseFragment.ARG_SECTION_NUM;
import static com.gogh.afternoontea.main.BaseFragment.ARG_SECTION_TYPE;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/3/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/3/2017 do fisrt create. </li>
 */
public class GankTechPresneter implements BasePresenter, OnRefreshListener {

    private static final String TAG = "GankTechPresneter";

    private OnLodingChangedListener loadingChangedListener;

    private Builder mBuilder;

    private AtomicInteger count = new AtomicInteger(1);

    private boolean isRefreshing = false;

    private boolean isLoading = false;

    public GankTechPresneter(Builder mBuilder) {
        this.mBuilder = mBuilder;
    }

    public void setOnLoadingChangedListener (OnLodingChangedListener loadingChangedListener) {
        this.loadingChangedListener = loadingChangedListener;
    }

    @Override
    public void loadData(String baseUrl, int num, int page) {
        isRefreshing = true;
        if(loadingChangedListener != null){
            loadingChangedListener.onLoadingStart();
        }

        RequestProxy.newInstance().getDataByCategory(baseUrl, num, page, new OnResponListener<GankEntity>() {
            @Override
            public void onComplete() {
                isRefreshing = false;
                // 存储最大页角
                count.getAndSet(page);
                if(loadingChangedListener != null){
                    loadingChangedListener.onLoadingComplete();
                }
            }

            @Override
            public void onError(Throwable e) {
                isRefreshing = false;
                if(loadingChangedListener != null){
                    loadingChangedListener.onLoadingError(mBuilder.adapter.getItemCount() > 0 ? false : true);
                }
            }

            @Override
            public void onResponse(@NonNull GankEntity response) {
                if (response != null) {
                    mBuilder.adapter.setData(response.getResults());
                }
                isRefreshing = false;
            }
        });
    }

    @Override
    public void onSwipeRefresh() {
        if(isRefreshing || isLoading){
            Logger.d(TAG, "onSwipeRefresh is refreshing or loading. ");
            return;
        }

        if(loadingChangedListener != null){
            loadingChangedListener.onLoadingStart();
        }
        RequestProxy.newInstance().getDataByCategory(mBuilder.fragment.getArguments().getString(ARG_SECTION_TYPE),
                mBuilder.fragment.getArguments().getInt(ARG_SECTION_NUM), 1, new OnResponListener<GankEntity>() {
            @Override
            public void onComplete() {
                isRefreshing = false;
                if(loadingChangedListener != null){
                    loadingChangedListener.onLoadingComplete();
                }
            }

            @Override
            public void onError(Throwable e) {
                isRefreshing = false;
                if(loadingChangedListener != null){
                    loadingChangedListener.onLoadingError(mBuilder.adapter.getItemCount() > 0 ? false : true);
                }
            }

            @Override
            public void onResponse(@NonNull GankEntity response) {
                if (response != null) {
                    mBuilder.adapter.addRefreshData(response.getResults());
                }
                isRefreshing = false;
            }
        });
    }

    @Override
    public void onLoadMore() {
        if(isRefreshing || isLoading){
            Logger.d(TAG, "onLoadMore is refreshing or loading. ");
            return;
        }

        isLoading = true;
        if(loadingChangedListener != null){
            loadingChangedListener.onLoadingStart();
        }

        RequestProxy.newInstance().getDataByCategory(mBuilder.fragment.getArguments().getString(ARG_SECTION_TYPE),
                mBuilder.fragment.getArguments().getInt(ARG_SECTION_NUM), count.get(), new OnResponListener<GankEntity>() {
                    @Override
                    public void onComplete() {
                        isLoading = false;
                        count.getAndIncrement();
                        if(loadingChangedListener != null){
                            loadingChangedListener.onLoadingComplete();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading = false;
                        if(loadingChangedListener != null){
                            loadingChangedListener.onLoadingError(mBuilder.adapter.getItemCount() > 0 ? false : true);
                        }
                    }

                    @Override
                    public void onResponse(@NonNull GankEntity response) {
                        if (response != null) {
                            mBuilder.adapter.addLoadMoreData(response.getResults());
                        }
                        isLoading = false;
                    }
                });
    }

    public static class Builder {

        private GankListAdapter adapter;
        private BaseFragment fragment;

        @NonNull
        public Builder adapter(GankListAdapter adapter){
            this.adapter = adapter;
            return this;
        }

        @NonNull
        public Builder fragment(BaseFragment fragment){
            this.fragment = fragment;
            return this;
        }

        @NonNull
        public GankTechPresneter build() {
            return new GankTechPresneter(this);
        }

    }

}
