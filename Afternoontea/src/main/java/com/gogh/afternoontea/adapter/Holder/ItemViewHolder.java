package com.gogh.afternoontea.adapter.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.utils.TintColor;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 有图片的项</p>
 * <p> Created by <b>高晓峰</b> on 1/18/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/18/2017 do fisrt create. </li>
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {

    private Context context;

    private CardView rootView;

    public ImageView itemBgImage;

    public AppCompatImageView itemType;
    public AppCompatImageView authorImage;
    public AppCompatImageView itemDateImage;

    public AppCompatTextView itemCreateDate;
    public AppCompatTextView titleName;
    public AppCompatTextView itemAuthor;

    public ItemViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        initView();
    }

    public void initView() {
        rootView = (CardView) itemView.findViewById(R.id.gank_list_item_root);
        itemBgImage = (ImageView) itemView.findViewById(R.id.gank_item_image_bg);

        itemType = (AppCompatImageView) itemView.findViewById(R.id.gank_list_item_type);
        itemDateImage = (AppCompatImageView) itemView.findViewById(R.id.gank_list_item_layout_date_icon);
        authorImage = (AppCompatImageView) itemView.findViewById(R.id.gank_list_item_author_img);

        titleName = (AppCompatTextView) itemView.findViewById(R.id.gank_item_title);
        itemAuthor = (AppCompatTextView) itemView.findViewById(R.id.gank_item_author);
        itemCreateDate = (AppCompatTextView) itemView.findViewById(R.id.gank_item_date);
    }

    public void onUpdateByTheme() {
        int primaryColor = TintColor.getPrimaryTextColor(context);
        // text
        titleName.setTextColor(TintColor.getTintList(TintColor.getPrimaryDarkColor(context)));
        itemAuthor.setTextColor(TintColor.getTintList(primaryColor));
        itemCreateDate.setTextColor(TintColor.getTintList(primaryColor));
        // icon
        itemType.setImageTintList(TintColor.getTintList(primaryColor));
        authorImage.setImageTintList(TintColor.getTintList(primaryColor));
        itemDateImage.setImageTintList(TintColor.getTintList(primaryColor));

        // root background
        rootView.setBackgroundTintList(TintColor.getTintList(TintColor.getPrimaryBackgroundColor(context)));
    }

}
