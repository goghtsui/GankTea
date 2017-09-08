package com.gogh.afternoontea.adapter.holder;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.app.Initializer;
import com.gogh.afternoontea.utils.TintColor;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/19/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/19/2017 do fisrt create. </li>
 */
public class WelfareViewHolder extends RecyclerView.ViewHolder implements Initializer {

    private Context context;

    public AppCompatTextView mTitleName;
    public ImageView mWelfareImage;

    public WelfareViewHolder(Context context, View mItemView) {
        super(mItemView);
        this.context = context;
        initView();
    }

    @Override
    public void init() {
    }

    @Override
    public void initView() {
        mTitleName = (AppCompatTextView) itemView.findViewById(R.id.gank_welfare_item_title);
        mWelfareImage = (ImageView) itemView.findViewById(R.id.gank_welfare_item_imageview);
    }

    public void onUpdateByTheme() {
        int textColor = TintColor.getPrimaryColor(context);
        mTitleName.setTextColor(TintColor.getTintList(textColor));
    }

}
