package com.gogh.afternoontea.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.gogh.afternoontea.main.BaseAppCompatActivity;
import com.gogh.afternoontea.utils.IntentUtils;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/18/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/18/2017 do fisrt create. </li>
 */
public class ShareActivity extends BaseAppCompatActivity {

    @Override
    protected void updateThemeByChoice(int resId) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TextView(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentUtils.share(this, "http://www.xiaofeng.site");
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.onBackPressed();
    }
}
