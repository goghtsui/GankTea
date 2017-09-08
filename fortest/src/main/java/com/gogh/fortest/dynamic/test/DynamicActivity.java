package com.gogh.fortest.dynamic.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gogh.fortest.R;
import com.gogh.fortest.dynamic.DynamicAdapter;
import com.gogh.fortest.dynamic.DynamicLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 8/23/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 8/23/2017 do fisrt create. </li>
 */

public class DynamicActivity extends Activity {

    DynamicLayout dynamicLayout;
    private String image = "http://pic.pthv.gitv.tv/blueray/2014/08/21/14/41/18/1408603278395.jpg?source=Hiveview";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_launcher);
        dynamicLayout = (DynamicLayout) findViewById(R.id.launcher_dynamiclayout);
        dynamicLayout.setAdapter(getAdapter());
    }

    private DynamicAdapter getAdapter(){
        List<TestEntity> entities = new ArrayList<>();
        // 宽  高 x y  地址
        entities.add(new TestEntity(300, 150, 0, 0, image));
        entities.add(new TestEntity(300, 150, 0, 150, image));
        entities.add(new TestEntity(300, 300, 300, 0, image));
        entities.add(new TestEntity(150, 150, 600, 0, image));
        entities.add(new TestEntity(150, 150, 750, 0, image));
        entities.add(new TestEntity(150, 150, 600,150, image));
        entities.add(new TestEntity(150, 150, 750, 150, image));

        entities.add(new TestEntity(300, 150, 900, 0, image));
        entities.add(new TestEntity(300, 150, 900, 150, image));

        entities.add(new TestEntity(300, 150, 1200, 0, image));
        entities.add(new TestEntity(300, 150, 1200, 150, image));

        entities.add(new TestEntity(300, 150, 1500, 0, image));
        entities.add(new TestEntity(300, 150, 1500, 150, image));

        DynamicAdapter adapter = new TestAdapter(this, entities);
         return adapter;
    }

}
