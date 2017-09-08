package com.gogh.afternoontea.adapter.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.utils.TintColor;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 没有图片的项</p>
 * <p> Created by <b>高晓峰</b> on 1/18/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/18/2017 do fisrt create. </li>
 */
public class NoPicViewHolder extends RecyclerView.ViewHolder  {

    private Context context;

    public ImageView itemImage;
    public AppCompatImageView itemType;
    public AppCompatImageView authorImage;
    public AppCompatImageView itemDateImage;
    public AppCompatTextView titleName;
    public AppCompatTextView itemAuthor;
    public AppCompatTextView itemCreateDate;

    public NoPicViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        itemImage = (ImageView) itemView.findViewById(R.id.gank_list_item_nopic_default_img);

        itemType = (AppCompatImageView) itemView.findViewById(R.id.gank_list_item_nopic_type);
        authorImage = (AppCompatImageView) itemView.findViewById(R.id.gank_list_item_nopic_author_img);
        itemDateImage = (AppCompatImageView) itemView.findViewById(R.id.gank_list_nopic_item_date_icon);

        titleName = (AppCompatTextView) itemView.findViewById(R.id.gank_list_item_nopic_title);
        itemAuthor = (AppCompatTextView) itemView.findViewById(R.id.gank_list_item_nopic_author);
        itemCreateDate = (AppCompatTextView) itemView.findViewById(R.id.gank_list_item_nopic_time);
    }

    public void onUpdateByTheme() {
        int textColor = TintColor.getPrimaryTextColor(context);
        titleName.setTextColor(TintColor.getTintList(textColor));
        itemAuthor.setTextColor(TintColor.getTintList(textColor));
        itemCreateDate.setTextColor(TintColor.getTintList(textColor));
        // icon
        itemType.setImageTintList(TintColor.getTintList(textColor));
        authorImage.setImageTintList(TintColor.getTintList(textColor));
        itemDateImage.setImageTintList(TintColor.getTintList(textColor));
    }

}
