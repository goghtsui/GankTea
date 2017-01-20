package com.gogh.afternoontea.ui;

import android.os.Bundle;
import android.view.View;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.app.Initializer;
import com.gogh.afternoontea.main.BaseAppCompatActivity;
import com.gogh.afternoontea.view.SearchEditText;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/17/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/17/2017 do fisrt create. </li>
 */
public class SearchActivity extends BaseAppCompatActivity implements Initializer {

    private SearchEditText mEditText;

    @Override
    protected void updateThemeByChoice(int resId) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gank_search_layout);
        initView();
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    @Override
    public void init() {
    }

    @Override
    public void initView() {
        mEditText = (SearchEditText) findViewById(R.id.gank_search_layout_edittext);
        mEditText.setOnSearchKeyListener(() -> {

        });
        mEditText.setOnKeyboardDismissedListener(() -> {

        });
    }
}
