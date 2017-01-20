package com.gogh.afternoontea.adapter.holder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.entity.gank.GankEntity;
import com.gogh.afternoontea.ui.GankDetailActivity;
import com.gogh.afternoontea.ui.HomeActivity;

import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 没有图片的项</p>
 * <p> Created by <b>高晓峰</b> on 1/18/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/18/2017 do fisrt create. </li>
 */
public class NoPicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;

    public ImageView itemImage;
    public AppCompatImageView itemType;
    public AppCompatTextView titleName;
    public AppCompatTextView itemAuthor;
    public AppCompatTextView itemCreateDate;

    @Nullable
    private List<GankEntity.ResultsBean> datas;


    public NoPicViewHolder(@NonNull View itemView, Context context, List<GankEntity.ResultsBean> datas) {
        super(itemView);
        this.context = context;
        this.datas = datas;
        itemImage = (ImageView) itemView.findViewById(R.id.gank_list_item_nopic_default_img);
        itemType = (AppCompatImageView) itemView.findViewById(R.id.gank_list_item_nopic_type);
        titleName = (AppCompatTextView) itemView.findViewById(R.id.gank_list_item_nopic_title);
        itemAuthor = (AppCompatTextView) itemView.findViewById(R.id.gank_list_item_nopic_author);
        itemCreateDate = (AppCompatTextView) itemView.findViewById(R.id.gank_list_item_nopic_time);
        itemView.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(@NonNull View v) {
        Intent intent = new Intent(context, GankDetailActivity.class);
        intent.putExtra(Urls.GANK_URL.BUNDLE_KEY, datas.get(getAdapterPosition()));

        View intoView = v.findViewById(R.id.gank_list_item_nopic_default_img);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation((HomeActivity) context,
                        intoView, context.getResources().getString(R.string.translation_element_name));// 不需要联动
        ActivityCompat.startActivity((HomeActivity) context, intent, options.toBundle());
    }
}
