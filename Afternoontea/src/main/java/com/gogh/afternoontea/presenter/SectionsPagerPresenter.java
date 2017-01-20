package com.gogh.afternoontea.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.gogh.afternoontea.adapter.SectionsPagerAdapter;
import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.fragment.GankListFragment;
import com.gogh.afternoontea.listener.OnScrollListener;
import com.gogh.afternoontea.main.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/4/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/4/2017 do fisrt create. </li>
 */
public class SectionsPagerPresenter {

    private static SectionsPagerPresenter INSTANCE;

    private OnScrollListener onScrollListener;

    private SectionsPagerPresenter() {
    }

    public static SectionsPagerPresenter newInstance() {
        if (INSTANCE == null) {
            INSTANCE = SingleHolder.ADAPTER;
        }
        return INSTANCE;
    }

    @NonNull
    private List<BaseFragment> getFragmentList() {
        List<BaseFragment> fragments = new ArrayList<>();
        int i = 0;
        fragments.add(getFragment(Urls.GANK_URL.ALL, i, BaseFragment.LAYOUT_TYPE_LIST));
        i++;
        fragments.add(getFragment(Urls.GANK_URL.ANDROID, i, BaseFragment.LAYOUT_TYPE_LIST));
        i++;
        fragments.add(getFragment(Urls.GANK_URL.IOS, i, BaseFragment.LAYOUT_TYPE_LIST));
        i++;
        fragments.add(getFragment(Urls.GANK_URL.WEB, i, BaseFragment.LAYOUT_TYPE_LIST));
        i++;
        fragments.add(getFragment(Urls.GANK_URL.MATERIAL, i, BaseFragment.LAYOUT_TYPE_LIST));
        i++;
        fragments.add(getFragment(Urls.GANK_URL.RECOMMEND, i, BaseFragment.LAYOUT_TYPE_LIST));
        i++;
        fragments.add(getFragment(Urls.GANK_URL.REST_VIDEO, i, BaseFragment.LAYOUT_TYPE_LIST));
        i++;
        fragments.add(getFragment(Urls.GANK_URL.WELFARE, i, BaseFragment.LAYOUT_TYPE_FALLS));
        return fragments;
    }

    @NonNull
    private BaseFragment getFragment(String type, int index, int layoutType) {
        GankListFragment fragment = new GankListFragment().newInstance(new BaseFragment.Builder()
                .type(type).number(10).page(1).layoutType(layoutType).build(), index);
        fragment.setOnScrollListener(onScrollListener);
        return fragment;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @NonNull
    public SectionsPagerAdapter getPagerAdapter(Context context, FragmentManager fragmentManager) {
        return new SectionsPagerAdapter(context, getFragmentList(), fragmentManager);
    }

    private static final class SingleHolder {
        private static final SectionsPagerPresenter ADAPTER = new SectionsPagerPresenter();
    }

}
