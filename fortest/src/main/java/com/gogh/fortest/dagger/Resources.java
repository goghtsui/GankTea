package com.gogh.fortest.dagger;

import android.util.Log;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 2/23/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 2/23/2017 do fisrt create. </li>
 */
public class Resources {

    private String name;

    public Resources(String name) {
        this.name = name;
    }

    public void name(){
        Log.d("Resources", "name : " + name);
    }

}
