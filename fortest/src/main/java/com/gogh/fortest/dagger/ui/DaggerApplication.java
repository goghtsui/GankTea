package com.gogh.fortest.dagger.ui;

import android.app.Application;

import com.gogh.fortest.dagger.DaggerResourcesComponent;
import com.gogh.fortest.dagger.ResourceModule;
import com.gogh.fortest.dagger.ResourcesComponent;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 2/24/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 2/24/2017 do fisrt create. </li>
 */
public class DaggerApplication extends Application {

    ResourcesComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerResourcesComponent.builder()
                .resourceModule(new ResourceModule("DaggerApplication"))
                .build();
        component.inject(this);
    }

    public ResourcesComponent component() {
        return component;
    }

}
