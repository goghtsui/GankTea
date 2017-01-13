package com.gogh.afternoontea.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.adapter.meizi.BaseCardAdapter;
import com.gogh.afternoontea.adapter.meizi.MeiziAdapter;
import com.gogh.afternoontea.entity.meizi.MeiziBean;
import com.gogh.afternoontea.listener.OnResponListener;
import com.gogh.afternoontea.listener.OnScrollListener;
import com.gogh.afternoontea.main.BaseFragment;
import com.gogh.afternoontea.parser.html.HtmlParser;
import com.gogh.afternoontea.request.RequestProxy;
import com.gogh.afternoontea.view.SwipeCardsView;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/22/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/22/2016 do fisrt create. </li>
 */
public class MeizituFragment extends BaseFragment {

    private SwipeCardsView cardsView;

    public MeizituFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MeizituFragment newInstance(int sectionNumber) {
        MeizituFragment fragment = new MeizituFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 获取页面的标题
     */
    @Override
    public String getTitle() {
        return "妹子图";
    }

    @Override
    public int getCurrentPage() {
        return getArguments().getInt(ARG_SECTION_NUMBER);
    }

    @Override
    public int computeVerticalScrollOffset() {
        return 0;
    }

    @Override
    public void setOnScrollListener(OnScrollListener onScrollListener) {

    }

    /**
     * 创建页面视图
     *
     * @param inflater
     */
    @Override
    protected View getContentLayout(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.meizipic_layout, container, false);
        cardsView = (SwipeCardsView) rootView.findViewById(R.id.swipCardsView);
        requestData();
        return rootView;
    }

    /**
     * 获取接口数据
     */
    @Override
    public void requestData() {
        RequestProxy.newInstance().getMeiziPic(new OnResponListener<String>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onResponse(String response) {
                setAdapter(getAdapter(response));
            }
        });
    }

    public void setAdapter(BaseCardAdapter<MeiziBean> adapter) {
        cardsView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return getContentLayout(inflater, container);
    }

    private MeiziAdapter getAdapter(String response) {
        return new MeiziAdapter(HtmlParser.parserHtml(response), getActivity().getApplicationContext());
    }

    @Override
    public void gotoTop() {
    }

    @Override
    public void onChanged() {

    }
}