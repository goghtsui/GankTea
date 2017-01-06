package com.gogh.afternoontea.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.iinterface.OnMultipleClickListener;
import com.gogh.afternoontea.location.LocationClient;
import com.gogh.afternoontea.log.Logger;
import com.gogh.afternoontea.main.ATApplication;
import com.gogh.afternoontea.main.BaseAppCompatActivity;
import com.gogh.afternoontea.utils.TintColor;
import com.gogh.afternoontea.widget.HomePagerView;

public class HomeActivity extends BaseAppCompatActivity implements OnMultipleClickListener {

    private static final String TAG = "HomeActivity";

    /**
     * tab标签
     */
    private TabLayout tabLayout;

    private HomePagerView mHomePagerView;

    /**
     * 悬浮功能键
     */
    private FloatingActionButton floatingActionButton;

    @Override
    protected void updateThemeByChoice(int themeStyleId) {
        Logger.d(TAG, "HomeActivity updateThemeByChoice  : " + themeStyleId);
        // 动态设置滚动条的颜色
        //        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(HomeActivity.this, themeStyleId));
        TintColor.setBackgroundTintList(floatingActionButton, ContextCompat.getColor(HomeActivity.this, themeStyleId));
        // 动态设置tab背景颜色
        tabLayout.setBackgroundColor(ContextCompat.getColor(HomeActivity.this, themeStyleId));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG, "HomeActivity onCreate weather : " + ATApplication.WEATHER);
        setContentView(R.layout.home_activity_layout);
        Logger.d(TAG, "HomeActivity  onCreate.");
        initView();
//        ThemeManager.newInstance().setTheme(ATApplication.WEATHER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationClient.newInstance().build(this.getApplicationContext()).stopLocation();
    }

    private void initView() {
        Logger.d(TAG, "HomeActivity  initView.");
         /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        // 悬浮功能键
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        mHomePagerView = new HomePagerView(this, tabLayout, floatingActionButton);
        mHomePagerView.setOnMultipleClickListener(this);
    }

    @Override
    public void onClickListener(View view) {
        chooseTheme();
    }

    @Override
    public void onLongClickListener(View view) {
        chooseTheme();
    }
}
