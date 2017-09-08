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

    private OnScrollListener onScrollListener;

    private SectionsPagerPresenter() {
    }

    public static SectionsPagerPresenter newInstance() {
        return SingleHolder.ADAPTER;
    }

    @NonNull
    private List<BaseFragment> getFragmentList() {
        List<BaseFragment> fragments = new ArrayList<>();
        int position = 0;
        fragments.add(getFragment(Urls.GANK_URL.ALL, position, BaseFragment.LAYOUT_TYPE_LIST));
        position++;
        fragments.add(getFragment(Urls.GANK_URL.ANDROID, position, BaseFragment.LAYOUT_TYPE_LIST));
        position++;
        fragments.add(getFragment(Urls.GANK_URL.IOS, position, BaseFragment.LAYOUT_TYPE_LIST));
        position++;
        fragments.add(getFragment(Urls.GANK_URL.WEB, position, BaseFragment.LAYOUT_TYPE_LIST));
        position++;
        fragments.add(getFragment(Urls.GANK_URL.MATERIAL, position, BaseFragment.LAYOUT_TYPE_LIST));
        position++;
        fragments.add(getFragment(Urls.GANK_URL.RECOMMEND, position, BaseFragment.LAYOUT_TYPE_LIST));
        position++;
        fragments.add(getFragment(Urls.GANK_URL.REST_VIDEO, position, BaseFragment.LAYOUT_TYPE_LIST));
        position++;
        fragments.add(getFragment(Urls.GANK_URL.WELFARE, position, BaseFragment.LAYOUT_TYPE_FALLS));
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
