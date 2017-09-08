package com.gogh.afternoontea.main;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.listener.OnCardModeChangedListener;
import com.gogh.afternoontea.listener.OnScrollListener;
import com.gogh.afternoontea.listener.OnTopListener;
import com.gogh.afternoontea.preference.PreferenceManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/26/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/26/2016 do fisrt create. </li>
 */
public abstract class BaseFragment extends Fragment implements OnTopListener, OnCardModeChangedListener{

    /**
     * The fragment argument representing the section number for this ragment.
     * 标志位key
     */
    public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String ARG_SECTION_PAGE = "section_page";
    public static final String ARG_SECTION_NUM = "section_num";
    public static final String ARG_SECTION_TYPE = "section_type";
    public static final String ARG_SECTION_LAYOUT_TYPE = "section_layout_type";

    // 声明静态常量
    public static final int LAYOUT_TYPE_LIST = 0;
    public static final int LAYOUT_TYPE_FALLS = 1;

    public abstract int getCurrentPage();

    public abstract int computeVerticalScrollOffset();

    public abstract void setOnScrollListener(OnScrollListener onScrollListener);

    /**
     * 创建页面视图
     */
    protected abstract View getContentLayout(LayoutInflater inflater, ViewGroup container);

    /**
     * 获取接口数据
     */
    public abstract void requestData();

    /**
     * 获取页面的标题
     */
    @NonNull
    public abstract String getTitle();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        PreferenceManager.newInstance().registerCardModeChangedListener(this);
        return getContentLayout(inflater, container);
    }

    @NonNull
    protected String formatTitle(@NonNull String orignal) {
        switch (orignal) {
            case Urls.GANK_URL.ALL:
                return "Top";
        }
        return orignal;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PreferenceManager.newInstance().unRegisterCardModeChangedListener(this);
    }

    // 定义申请常量列表，声明NavigationMode形式的注解（三个注解是固定形式）
    @IntDef({LAYOUT_TYPE_LIST, LAYOUT_TYPE_FALLS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LayoutType {
    }

    public static class Builder {

        private int num;
        private int page;
        private String type;
        private int layoutType;

        public Builder() {
        }

        public String getType() {
            return type;
        }

        @NonNull
        public Builder type(String type){
            this.type = type;
            return this;
        }

        public int getNum() {
            return num;
        }

        @NonNull
        public Builder number(int num){
            this.num = num;
            return this;
        }

        public int getPage() {
            return page;
        }

        @NonNull
        public Builder page(int page){
            this.page = page;
            return this;
        }

        @LayoutType
        public int getLayoutType() {
            return layoutType;
        }

        @NonNull
        public Builder layoutType(@LayoutType int layoutType){
            this.layoutType = layoutType;
            return this;
        }

        @NonNull
        public Builder build(){
            return this;
        }

    }
}
