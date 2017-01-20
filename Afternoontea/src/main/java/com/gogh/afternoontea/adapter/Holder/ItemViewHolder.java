package com.gogh.afternoontea.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.adapter.gank.BaseGankAdapter;
import com.gogh.afternoontea.app.Initializer;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 有图片的项</p>
 * <p> Created by <b>高晓峰</b> on 1/18/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/18/2017 do fisrt create. </li>
 */
public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Initializer {

    public AppCompatImageView itemType;
    public ImageView itemBgImage;
    public AppCompatTextView itemCreateDate;
    public AppCompatTextView titleName;
    public AppCompatTextView itemAuthor;

    private BaseGankAdapter.OnItemClickListener onItemClickListener;

    public ItemViewHolder(@NonNull View itemView, BaseGankAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        this.onItemClickListener = onItemClickListener;
        initView();
    }

    @Override
    public void init() {
    }

    @Override
    public void initView() {
        itemType = (AppCompatImageView) itemView.findViewById(R.id.gank_list_item_type);
        titleName = (AppCompatTextView) itemView.findViewById(R.id.gank_item_title);
        itemAuthor = (AppCompatTextView) itemView.findViewById(R.id.gank_item_author);
        itemBgImage = (ImageView) itemView.findViewById(R.id.gank_item_image_bg);
        itemCreateDate = (AppCompatTextView) itemView.findViewById(R.id.gank_item_date);
        itemView.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v, this.getAdapterPosition(), R.id.gank_item_image_bg);
        }
    }

}
