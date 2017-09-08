package com.gogh.fortest.dagger.ui;

import android.app.Activity;
import android.os.Bundle;

import com.gogh.fortest.dagger.Resources;

import javax.inject.Inject;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 2/23/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 2/23/2017 do fisrt create. </li>
 */
public class HomeActivity extends Activity {

    @Inject
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DaggerApplication)getApplication()).component().inject(this);
        resources.name();
    }
}
