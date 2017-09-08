package com.gogh.fortest.dynamic.test;

import android.view.View;
import android.widget.ImageView;

import com.gogh.fortest.R;
import com.gogh.fortest.dynamic.DynamicHolder;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 8/23/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 8/23/2017 do fisrt create. </li>
 */

public class MyHolder extends DynamicHolder {

    public ImageView imageView;

    public MyHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }

}
