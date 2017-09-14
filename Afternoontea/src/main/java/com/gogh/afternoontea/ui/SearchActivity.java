package com.gogh.afternoontea.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.adapter.SpacesItemDecoration;
import com.gogh.afternoontea.app.Initializer;
import com.gogh.afternoontea.main.BaseAppCompatActivity;
import com.gogh.afternoontea.presenter.imp.SearchPresenter;
import com.gogh.afternoontea.utils.Logger;
import com.gogh.afternoontea.utils.StringUtil;
import com.gogh.afternoontea.view.SearchEditText;

import java.util.Calendar;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/17/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/17/2017 do fisrt create. </li>
 */
public class SearchActivity extends BaseAppCompatActivity implements Initializer {

    private static final String TAG = "SearchActivity";

    private SearchEditText mSearchEditText;
    private RecyclerView mRecyclerView;
    private LinearLayout mTypeScrollContainer;

    @Override
    protected void updateThemeByChoice(int resId) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setEnterTransition(new Explode().setDuration(600));
        getWindow().setExitTransition(new Explode().setDuration(600));
        setContentView(R.layout.gank_search_layout);
        initView();
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
        findViewById(R.id.gank_search_back_action).setOnClickListener(v -> finish());
        mTypeScrollContainer = (LinearLayout) findViewById(R.id.gank_search_scroll_type_list);
        //
        mRecyclerView = (RecyclerView) findViewById(R.id.gank_search_result_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);
        //
        mSearchEditText = (SearchEditText) findViewById(R.id.gank_search_layout_edittext);
        mSearchEditText.setOnDrawableClickedListener(new SearchEditText.OnDrawableClickedListener() {
            @Override
            public void onDrawableLeftClick() {
                Logger.d(TAG, "onDrawableLeftClick");
            }

            @Override
            public void onDrawableRightClick() {
                Logger.d(TAG, "onDrawableRightClick");
                showDatePickerDialog();
            }
        });
        mSearchEditText.setOnSearchKeyListener(inputText -> {
            Logger.d(TAG, "onSearchKeyClicked " + inputText);
            String[] date = StringUtil.formatInputDate(inputText);
            if (date != null) {
                SearchPresenter.newInstance().load(getLoadType(), date[0], date[1], date[2]);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), 0);
            } else {
                Toast.makeText(this, "请输入正确的日期", Toast.LENGTH_SHORT).show();
            }
        });
        SearchPresenter.newInstance().init(this, mTypeScrollContainer, mRecyclerView);
    }

    private void showDatePickerDialog() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    SearchPresenter.newInstance().load(getLoadType(), String.valueOf(year), String.valueOf(month + 1),
                            String.valueOf(dayOfMonth));
                }
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();
        } else {
            DatePicker datePicker = new DatePicker(this);
            datePicker.init(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                        @Override
                        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            SearchPresenter.newInstance().load(getLoadType(), String.valueOf(year),
                                    String.valueOf(monthOfYear + 1), String.valueOf(dayOfMonth));
                        }
                    });
        }
    }

    private int getLoadType() {
        if (mTypeScrollContainer.getChildCount() > 0 || mRecyclerView.getChildCount() > 0) {
            return SearchPresenter.LOAD_REFRESH;
        }

        return SearchPresenter.LOAD_INIT;
    }

}
