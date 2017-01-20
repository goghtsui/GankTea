package com.gogh.afternoontea.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.adapter.SpacesItemDecoration;
import com.gogh.afternoontea.listener.OnScrollListener;
import com.gogh.afternoontea.log.Logger;
import com.gogh.afternoontea.main.BaseFragment;
import com.gogh.afternoontea.widget.SwipeRefreshView;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/28/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/28/2016 do fisrt create. </li>
 */
public class GankListFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private SwipeRefreshView mSwipeRefreshView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private LinearLayout mReloadLayout;

    private OnScrollListener onScrollListener;

    public GankListFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    @NonNull
    public static GankListFragment newInstance(@NonNull Builder builder, int sectionNumber) {
        GankListFragment fragment = new GankListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString(ARG_SECTION_TYPE, builder.getType());
        args.putInt(ARG_SECTION_NUM, builder.getNum());
        args.putInt(ARG_SECTION_PAGE, builder.getPage());
        args.putInt(ARG_SECTION_LAYOUT_TYPE, builder.getLayoutType());
        Logger.d("GankListFragment", " layout_type : " + builder.getLayoutType());
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 获取页面的标题
     */
    @NonNull
    @Override
    public String getTitle() {
        return formatTitle(getArguments().getString(ARG_SECTION_TYPE));
    }

    @Override
    public int getCurrentPage() {
        return getArguments().getInt(ARG_SECTION_NUMBER);
    }

    @Override
    public int computeVerticalScrollOffset() {
        if(mRecyclerView == null){
            return 0;
        }
        return mRecyclerView.computeVerticalScrollOffset();
    }

    @Override
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    /**
     * 创建页面视图
     *
     * @param inflater
     * @param container
     */
    @Override
    protected View getContentLayout(@NonNull LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.gank_list_layout, container, false);

        mReloadLayout = (LinearLayout) rootView.findViewById(R.id.gank_list__reload_layout);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.gank_swipe_refresh_widget);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.gank_recycler_view);
        mRecyclerView.setLayoutManager(getLayouManager());

        mSwipeRefreshView = new SwipeRefreshView(this, mReloadLayout,  mRecyclerView, mSwipeRefreshLayout);
        mSwipeRefreshView.setOnScrollListener(onScrollListener);

        requestData();
        return rootView;
    }

    private RecyclerView.LayoutManager getLayouManager() {
        if(getArguments().getInt(ARG_SECTION_LAYOUT_TYPE) == LAYOUT_TYPE_LIST){
            return new  LinearLayoutManager(getActivity());
        } else {
            //设置item之间的间隔
            SpacesItemDecoration decoration=new SpacesItemDecoration(16);
            mRecyclerView.addItemDecoration(decoration);
            return new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        }
    }

    /**
     * 获取接口数据
     */
    @Override
    public void requestData() {
        mSwipeRefreshView.requestData();
    }

    @Override
    public void gotoTop() {
        if (mRecyclerView.getChildCount() >= 30) {
            mRecyclerView.scrollToPosition(0);
        } else {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void onChanged() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
