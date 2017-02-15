package com.gogh.afternoontea.adapter.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.app.Initializer;
import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.ui.HomeActivity;
import com.gogh.afternoontea.ui.ScaleImageActivity;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/19/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/19/2017 do fisrt create. </li>
 */
public class WelfareViewHolder extends RecyclerView.ViewHolder implements Initializer, View.OnClickListener {

    private String imageUrl;
    private Context context;
    public AppCompatTextView mTitleName;
    public ImageView mWelfareImage;

    public WelfareViewHolder(Context context, View mItemView) {
        super(mItemView);
        this.context = context;
        initView();
        itemView.setOnClickListener(this);
    }

    @Override
    public void init() {
    }

    @Override
    public void initView() {
        mTitleName = (AppCompatTextView) itemView.findViewById(R.id.gank_welfare_item_title);
        mWelfareImage = (ImageView) itemView.findViewById(R.id.gank_welfare_item_imageview);
    }

    public void setupImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, ScaleImageActivity.class);
        intent.putExtra(Urls.GANK_URL.BUNDLE_KEY, imageUrl);

        View intoView = view.findViewById(R.id.gank_welfare_item_imageview);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation((HomeActivity)context,
                        intoView, context.getResources().getString(R.string.translation_element_name));
        ActivityCompat.startActivity((HomeActivity)context, intent, options.toBundle());
    }
}
