package com.gogh.afternoontea.widget;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.adapter.gank.BaseGankAdapter;
import com.gogh.afternoontea.adapter.gank.GankListAdapter;
import com.gogh.afternoontea.app.Initializer;
import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.entity.gank.GankEntity;
import com.gogh.afternoontea.listener.OnRefreshListener;
import com.gogh.afternoontea.listener.OnScrollListener;
import com.gogh.afternoontea.location.listener.OnLodingChangedListener;
import com.gogh.afternoontea.log.Logger;
import com.gogh.afternoontea.main.BaseFragment;
import com.gogh.afternoontea.presenter.imp.GankTechPresneter;
import com.gogh.afternoontea.ui.GankDetailActivity;
import com.gogh.afternoontea.utils.TintColor;

import java.util.ArrayList;
import java.util.List;

import static com.gogh.afternoontea.main.BaseFragment.ARG_SECTION_NUM;
import static com.gogh.afternoontea.main.BaseFragment.ARG_SECTION_PAGE;
import static com.gogh.afternoontea.main.BaseFragment.ARG_SECTION_TYPE;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/4/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/4/2017 do fisrt create. </li>
 */
public class SwipeRefreshView implements SwipeRefreshLayout.OnRefreshListener,
        OnRefreshListener, OnLodingChangedListener, Initializer {

    private static final String TAG = "SwipeRefreshView";

    private static final int SCROLL_NONE = 0x100010;
    private static final int SCROLL_TOP = 0x100011;

    private int verticalScrollOffset = SCROLL_NONE;

    private BaseFragment mFragment;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout mReloadLayout;
    @NonNull
    private List<GankEntity.ResultsBean> datas = new ArrayList<>();

    @Nullable
    private GankListAdapter gankListAdapter;
    private GankTechPresneter mPrensenter;

    private OnScrollListener onScrollListener;
    /**
     * 列表下拉刷新监听事件
     */
    @NonNull
    public RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int mLastVisibleItemPosition;//最后一个角标位置

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            Logger.d(TAG, "mOnScrollListener onScrolled");
            mLastVisibleItemPosition = getLastVisibleItemPosition(recyclerView);
            if (recyclerView.computeVerticalScrollOffset() == 0
                    && verticalScrollOffset == SCROLL_NONE) {// 滚动到顶部
                Logger.d(TAG, "mOnScrollListener SCROLL_TOP");
                verticalScrollOffset = SCROLL_TOP;
                if (onScrollListener != null) {
                    onScrollListener.onScrollToTop(mFragment.getCurrentPage());
                }
            } else {// 滚动中（已离开顶部）
                if (verticalScrollOffset == SCROLL_TOP) {
                    Logger.d(TAG, "mOnScrollListener SCROLL_NONE");
                    verticalScrollOffset = SCROLL_NONE;
                    if (onScrollListener != null) {
                        onScrollListener.onScrolling(mFragment.getCurrentPage());
                    }
                }
            }
        }

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Logger.d(TAG, "mOnScrollListener onScrollStateChanged");
            //正在滚动
            if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                onScrollListener.onScrollStart(mFragment.getCurrentPage());
            }
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && mLastVisibleItemPosition + 1 == recyclerView.getAdapter().getItemCount()) {
                onLoadMore();
            }
        }
    };

    /**
     * 列表项的点击事件，跳转到详情页
     */
    @NonNull
    private BaseGankAdapter.OnItemClickListener mOnItemClickListener = new BaseGankAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(@NonNull View view, int position, int resId) {
            Logger.d(TAG, "mOnItemClickListener position : " + position);
            GankEntity.ResultsBean data = gankListAdapter.getItem(position);
            Intent intent = new Intent(mFragment.getActivity(), GankDetailActivity.class);
            intent.putExtra(Urls.GANK_URL.BUNDLE_KEY, data);

            //            View intoView = view.findViewById(R.id.gank_item_image_bg);
            View intoView = view.findViewById(resId);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(mFragment.getActivity(),
                            intoView, mFragment.getResources().getString(R.string.translation_element_name));
            ActivityCompat.startActivity(mFragment.getActivity(), intent, options.toBundle());
        }
    };

    public SwipeRefreshView(@NonNull BaseFragment mFragment, @NonNull LinearLayout mReloadLayout,
                            @NonNull RecyclerView mRecyclerView, @NonNull SwipeRefreshLayout mSwipeRefreshLayout) {
        this.mFragment = mFragment;
        this.mReloadLayout = mReloadLayout;
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
        this.mRecyclerView = mRecyclerView;
        initView();
    }

    @Override
    public void init() {
    }

    @Override
    public void initView() {
        mSwipeRefreshLayout.setColorSchemeResources(TintColor.getColorSchemeResources());
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        gankListAdapter = new GankListAdapter(mFragment.getActivity(),
                mFragment.getArguments().getString(ARG_SECTION_TYPE));
        gankListAdapter.setData(datas);
        gankListAdapter.setOnItemClickListener(mOnItemClickListener);

        mRecyclerView.setAdapter(gankListAdapter);

        mPrensenter = new GankTechPresneter.Builder().adapter(gankListAdapter).fragment(mFragment).build();
        mPrensenter.setOnLoadingChangedListener(this);

        mReloadLayout.setOnClickListener(v -> requestData());
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }


    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        onSwipeRefresh();
    }

    @Override
    public void onSwipeRefresh() {
        mPrensenter.onSwipeRefresh();
    }

    @Override
    public void onLoadMore() {
        gankListAdapter.setLoadingError(false);
        gankListAdapter.setScrollToBottom(true);
        mPrensenter.onLoadMore();
    }

    @Override
    public void onLoadingStart() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onLoadingError(boolean isShowReload) {
        if (isShowReload) {
            resetVisible(View.VISIBLE);
        }
        gankListAdapter.setLoadingError(true);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadingComplete() {
        resetVisible(View.GONE);
        gankListAdapter.setScrollToBottom(false);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void requestData() {
        mPrensenter.loadData(mFragment.getArguments().getString(ARG_SECTION_TYPE),
                mFragment.getArguments().getInt(ARG_SECTION_NUM), mFragment.getArguments().getInt(ARG_SECTION_PAGE));
    }

    private void resetVisible(int visible) {
        mReloadLayout.setVisibility(visible);
    }

    private int getLastVisibleItemPosition(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else {
            //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
            //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
            return findMax(lastPositions);
        }
    }

    //找到数组中的最大值
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

}
