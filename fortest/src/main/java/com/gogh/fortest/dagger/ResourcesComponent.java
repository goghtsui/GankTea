package com.gogh.fortest.dagger;

import com.gogh.fortest.dagger.ui.DaggerApplication;
import com.gogh.fortest.dagger.ui.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 2/23/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 2/23/2017 do fisrt create. </li>
 */
@Singleton
@Component(modules = ResourceModule.class)
public interface ResourcesComponent {
    void inject(DaggerApplication application);
    void inject(HomeActivity homeActivity);
}