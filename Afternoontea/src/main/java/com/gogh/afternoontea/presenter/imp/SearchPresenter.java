package com.gogh.afternoontea.presenter.imp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.adapter.gank.GankSearchAdapter;
import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.entity.gank.BaseEntity;
import com.gogh.afternoontea.entity.gank.SearchEntity;
import com.gogh.afternoontea.listener.OnResponListener;
import com.gogh.afternoontea.request.RequestProxy;
import com.gogh.afternoontea.utils.Logger;
import com.gogh.afternoontea.utils.TintColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 9/12/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 9/12/2017 do fisrt create. </li>
 */

public class SearchPresenter {

    public static final int LOAD_INIT = 0x11012000;
    public static final int LOAD_REFRESH = 0x11012001;
    private static final String TAG = "SearchPresenter";
    private int mSelectedIndex = 0;

    private SearchEntity mResponse;

    private Context context;
    private RecyclerView recyclerView;
    private LinearLayout mTypeContainer;
    private GankSearchAdapter searchAdapter;

    private SearchPresenter() {
    }

    public static SearchPresenter newInstance() {
        return SingleHolder.HOLDER;
    }

    public void init(Context context, LinearLayout typeContainer, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.mTypeContainer = typeContainer;
        searchAdapter = new GankSearchAdapter(context);
    }

    public void load(int loadType, String year, String month, String day) {
        RequestProxy.newInstance().getSearchList(year, month, day, new OnResponListener<SearchEntity>() {

            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onResponse(SearchEntity response) {
                if (response != null && response.getCategory() != null && response.getCategory().size() > 0
                        && response.getResults() != null) {
                    mResponse = response;
                    // 添加分类标签
                    addType(response.getCategory());
                    // 加载或切换数据
                    switch (loadType) {
                        case LOAD_INIT:
                            searchAdapter.setData(getDatas(response.getCategory().get(0)));
                            recyclerView.setAdapter(searchAdapter);
                            break;
                        case LOAD_REFRESH:
                            searchAdapter.addRefreshData(getDatas(response.getCategory().get(0)));
                            break;
                        default:
                            break;
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.gank_search_input_error_alarm), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 添加类型到滚动列表
     *
     * @param typeList 数据源
     * @author 高晓峰
     * @date 9/13/2017
     * @ChangeLog: <li> 高晓峰 on 9/13/2017 </li>
     */
    private void addType(List<String> typeList) {
        mTypeContainer.removeAllViewsInLayout();
        for (int i = 0; i < typeList.size(); i++) {
            TextView typeView = new TextView(context);
            typeView.setTag("" + i);
            typeView.setPadding(context.getResources().getDimensionPixelOffset(R.dimen.gank_search_type_margin_horizontal),
                    context.getResources().getDimensionPixelOffset(R.dimen.gank_search_type_margin_vertical),
                    context.getResources().getDimensionPixelOffset(R.dimen.gank_search_type_margin_horizontal),
                    context.getResources().getDimensionPixelOffset(R.dimen.gank_search_type_margin_vertical));
            typeView.setText(typeList.get(i));
            typeView.setTextColor(Color.WHITE);
            typeView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.gank_search_type_text));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            String typeName = typeList.get(i);
            if (i != 0) {
                params.leftMargin = context.getResources().getDimensionPixelOffset(R.dimen.gank_search_type_margin_horizontal);
                typeView.setBackgroundColor(TintColor.getPrimaryColor(context));
            } else {
                mSelectedIndex = i;
                typeView.setBackgroundColor(TintColor.getAccentColor(context));
            }

            typeView.setOnClickListener(v -> {
                resetBackground(v, mSelectedIndex);
                mSelectedIndex = Integer.valueOf(String.valueOf(v.getTag()));
                searchAdapter.addRefreshData(getDatas(typeName));
            });
            mTypeContainer.addView(typeView, params);
        }

    }

    private List<BaseEntity> getDatas(String type) {
        List<BaseEntity> result = new ArrayList<>();
        switch (type) {
            case Urls.GANK_URL.ANDROID:
                result.addAll(mResponse.getResults().getAndroid());
                return result;
            case Urls.GANK_URL.IOS:
                result.addAll(mResponse.getResults().getIOS());
                return result;
            case Urls.GANK_URL.WEB:
                result.addAll(mResponse.getResults().get前端());
                return result;
            case Urls.GANK_URL.WELFARE:
                result.addAll(mResponse.getResults().get福利());
                return result;
            case Urls.GANK_URL.REST_VIDEO:
                result.addAll(mResponse.getResults().get休息视频());
                return result;
            case Urls.GANK_URL.MATERIAL:
                result.addAll(mResponse.getResults().get拓展资源());
                return result;
            case Urls.GANK_URL.RECOMMEND:
                result.addAll(mResponse.getResults().get瞎推荐());
                return result;
            default:
                Logger.d(TAG, "null");
                return null;
        }
    }

    /**
     * 重置类型按钮的背景颜色
     *
     * @param typeView
     * @param index
     * @author 高晓峰
     * @date 9/13/2017
     * @ChangeLog: <li> 高晓峰 on 9/13/2017 </li>
     */
    private void resetBackground(View typeView, int index) {
        // selected
        if (typeView != null) {
            typeView.setBackgroundColor(TintColor.getAccentColor(context));
        }

        if (index != -1) {
            // unselected
            mTypeContainer.getChildAt(index).setBackgroundColor(TintColor.getPrimaryColor(context));
        }

    }

    private static final class SingleHolder {
        private static final SearchPresenter HOLDER = new SearchPresenter();
    }


}
