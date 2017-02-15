package com.gogh.afternoontea.ui;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.app.Initializer;
import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.main.BaseAppCompatActivity;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 2/3/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 2/3/2017 do fisrt create. </li>
 */
public class ScaleImageActivity extends BaseAppCompatActivity implements Initializer{

    private PhotoView scaleView;
    private PhotoViewAttacher photoViewAttacher;

    @Override
    protected void updateThemeByChoice(int resId) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welfare_detail_layout);
        initView();
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Need to call clean-up
        photoViewAttacher.cleanup();
    }

    @Override
    public void init() {
        String imageUrl = getIntent().getStringExtra(Urls.GANK_URL.BUNDLE_KEY);
        Picasso.with(this).load(imageUrl)
                .config(Bitmap.Config.ARGB_8888)
                .into(scaleView);
    }

    @Override
    public void initView() {
        scaleView = (PhotoView) findViewById(R.id.welfare_detail_scaleview);
        photoViewAttacher = new PhotoViewAttacher(scaleView);
    }
}
