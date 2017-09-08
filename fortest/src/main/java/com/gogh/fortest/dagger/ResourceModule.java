package com.gogh.fortest.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 2/23/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 2/23/2017 do fisrt create. </li>
 */
@Module
public class ResourceModule {

    private String name;

    public ResourceModule(String name) {
        this.name = name;
    }

    @Provides
    Resources provideResources(){
        return new Resources(name);
    }


}
