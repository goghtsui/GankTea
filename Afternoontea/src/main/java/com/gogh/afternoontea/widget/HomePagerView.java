package com.gogh.afternoontea.widget;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.adapter.SectionsPagerAdapter;
import com.gogh.afternoontea.iinterface.OnMultipleClickListener;
import com.gogh.afternoontea.iinterface.OnScrollListener;
import com.gogh.afternoontea.iinterface.OnTopListener;
import com.gogh.afternoontea.main.BaseFragment;
import com.gogh.afternoontea.presenter.SectionsPagerPresenter;
import com.gogh.afternoontea.ui.HomeActivity;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/6/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/6/2017 do fisrt create. </li>
 */
public class HomePagerView implements OnScrollListener {

    private static final String TAG = "HomePagerView";

    private int currentPage = 0;

    private HomeActivity mContext;

    private TabLayout mTablayout;

    /**
     * tab页面适配器
     */
    private SectionsPagerAdapter pagerAdapter;

    /**
     * 悬浮功能键
     */
    private FloatingActionButton mFloatingActionButton;

    private OnMultipleClickListener onMultipleClickListener;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            currentPage = position;
        }

        /**
         *  SCROLL_STATE_DRAGGING - 正在滑动  ，pager处于正在拖拽中（整个的滑动过程该状态第一个被执行）
         *  SCROLL_STATE_SETTLING - 正在自动滑动，相当于松手后，pager恢复到一个自主的运动过程（过程中间执行，紧接着执行 onPageSelected（））
         *  SCROLL_STATE_IDLE - 空闲状态 ，pager处于空闲状态（整个的滑动过程该状态最后一个被执行）
         *
         * @param state 滑动的状态码
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:// start
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:// amongst scrolling
                    break;
                case ViewPager.SCROLL_STATE_IDLE:// end
                    resetFloatingMenuAction();
                    break;
            }
        }
    };

    public HomePagerView(Context mContext, TabLayout mTablayout, FloatingActionButton mFloatingActionButton) {
        this.mTablayout = mTablayout;
        this.mContext = (HomeActivity) mContext;
        this.mFloatingActionButton = mFloatingActionButton;
        initView();
    }

    private void initView() {
        mViewPager = (ViewPager) mContext.findViewById(R.id.container);

        SectionsPagerPresenter pagerPresenter = SectionsPagerPresenter.newInstance();
        pagerPresenter.setOnScrollListener(this);
        pagerAdapter = pagerPresenter.getPagerAdapter(mContext, mContext.getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mTablayout.setupWithViewPager(mViewPager);

        mFloatingActionButton.setOnClickListener(v -> {
            ((OnTopListener) pagerAdapter.getItem(mViewPager.getCurrentItem())).gotoTop();
        });

        mFloatingActionButton.setOnLongClickListener(v -> {
            if (onMultipleClickListener != null) {
                onMultipleClickListener.onLongClickListener(v);
            }
            return true;
        });

        mViewPager.addOnPageChangeListener(pageChangeListener);
    }

    public void setOnMultipleClickListener(OnMultipleClickListener onMultipleClickListener) {
        this.onMultipleClickListener = onMultipleClickListener;
    }

    /**
     * recyclerview已滚动到顶部
     */
    @Override
    public void onScrollToTop(int fragmentIndex) {
        if (currentPage == fragmentIndex) {
            mFloatingActionButton.setImageResource(R.drawable.ic_menu_button);
            mFloatingActionButton.setOnClickListener(v -> {
                if (onMultipleClickListener != null) {
                    onMultipleClickListener.onClickListener(v);
                }
            });
        }
    }

    /**
     * 两种情况下都会回调
     * 1. recyclerview滚动中
     * 2. 第一次进入页面，还未执行任何滚动操作
     */
    @Override
    public void onScrolling(int fragmentIndex) {
        if (currentPage == fragmentIndex) {
            mFloatingActionButton.setImageResource(R.drawable.ic_top);
            mFloatingActionButton.setOnClickListener(v ->
                    ((OnTopListener) pagerAdapter.getItem(mViewPager.getCurrentItem())).gotoTop());
        }
    }

    /**
     * 翻页之后，根据当前页面停留未知，重新设置悬浮按钮的UI及响应事件
     *
     * @author 高晓峰
     * @date 2017/01/06
     * ChangeLog:
     * <li> 高晓峰 on 2017/01/06 </li>
     */
    private void resetFloatingMenuAction() {
        if (pagerAdapter.getItem(currentPage) instanceof BaseFragment) {
            BaseFragment fragment = (BaseFragment) pagerAdapter.getItem(currentPage);
            if (fragment.computeVerticalScrollOffset() == 0) {
                onScrollToTop(currentPage);
            } else {
                onScrolling(currentPage);
            }
        }
    }

}
