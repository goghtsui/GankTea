package com.gogh.afternoontea.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.iinterface.OnScrollListener;
import com.gogh.afternoontea.iinterface.OnTopListener;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/26/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/26/2016 do fisrt create. </li>
 */
public abstract class BaseFragment extends Fragment implements OnTopListener{

    /**
     * The fragment argument representing the section number for this ragment.
     * 标志位key
     */
    public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String ARG_SECTION_PAGE = "section_page";
    public static final String ARG_SECTION_NUM = "section_num";
    public static final String ARG_SECTION_TYPE = "section_type";

    /**
     * 获取页面的标题
     */
    public abstract String getTitle();

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return getContentLayout(inflater, container);
    }

    public static class Builder {

        public String type;
        public int num;
        public int page;

        public Builder() {
        }

        public Builder type(String type){
            this.type = type;
            return this;
        }

        public Builder number(int num){
            this.num = num;
            return this;
        }

        public Builder page(int page){
            this.page = page;
            return this;
        }

        public Builder build(){
            return this;
        }

    }

    protected String formatTitle(String orignal){
        switch (orignal) {
            case Urls.GANK_URL.ALL:
                return "推荐";
        }
        return orignal;
    }

}
