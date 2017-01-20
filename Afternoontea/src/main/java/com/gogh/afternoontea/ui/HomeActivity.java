package com.gogh.afternoontea.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.listener.OnCachePageNumChangedListener;
import com.gogh.afternoontea.listener.OnMultipleClickListener;
import com.gogh.afternoontea.log.Logger;
import com.gogh.afternoontea.main.BaseAppCompatActivity;
import com.gogh.afternoontea.preference.PreferenceManager;
import com.gogh.afternoontea.utils.TintColor;
import com.gogh.afternoontea.view.FloatMenuButton;
import com.gogh.afternoontea.widget.FloatingMenuWidget;
import com.gogh.afternoontea.widget.HomePagerView;

public class HomeActivity extends BaseAppCompatActivity implements OnMultipleClickListener,
        FloatMenuButton.OnFloatingMenuClickListener, OnCachePageNumChangedListener {

    private static final String TAG = "HomeActivity";

    /**
     * tab标签
     */
    private TabLayout tabLayout;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private FloatMenuButton floatMenuButton;

    /**
     * 悬浮功能键
     */
    private FloatingActionButton floatingActionButton;

    private FloatingMenuWidget mFloatingMenuWidget;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG, "HomeActivity  onCreate.");
        setContentView(R.layout.home_activity_layout);
        PreferenceManager.newInstance().registerCachePageNumChangedListener(this);
        initView();
    }

    private void initView() {
        Logger.d(TAG, "HomeActivity  initView.");
         /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        CoordinatorLayout rootView = (CoordinatorLayout) findViewById(R.id.main_content);

        // 标签栏
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        // 悬浮功能键
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        TintColor.setBackgroundTintList(floatingActionButton, TintColor.getPrimaryColor(HomeActivity.this));

        // 悬浮菜单
        floatMenuButton = new FloatMenuButton(HomeActivity.this, rootView);
        floatMenuButton.setOnFloatingMenuClickListener(this);
        mFloatingMenuWidget = floatMenuButton.create();

        mViewPager = (ViewPager) findViewById(R.id.container);

        // 分页适配器
        new HomePagerView(this, tabLayout, mViewPager, floatingActionButton).setOnMultipleClickListener(this);

        // 设置悬浮菜单的背景颜色
        floatMenuButton.initFloatMenuBackground(TintColor.getPrimaryColor(HomeActivity.this));

    }

    @Override
    protected void updateThemeByChoice(int themeColor) {
        Logger.d(TAG, "HomeActivity updateThemeByChoice  : " + themeColor);
        // 动态设置滚动条的颜色
        //        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(HomeActivity.this, themeStyleId));
        floatMenuButton.resetFloatMenuBackground(themeColor);
        TintColor.setBackgroundTintList(floatingActionButton, ContextCompat.getColor(HomeActivity.this, themeColor));
        // 动态设置tab背景颜色
        tabLayout.setBackgroundColor(ContextCompat.getColor(HomeActivity.this, themeColor));
//        tabLayout.setSelectedTabIndicatorColor(TintColor.getAccentColor(HomeActivity.this));
    }

    @Override
    public void onClickListener(View view) {
        Logger.d(TAG, "onClickListener");
        mFloatingMenuWidget.autoBootMenu();
    }

    @Override
    public void onLongClickListener(View view) {
        Logger.d(TAG, "onLongClickListener");
        mFloatingMenuWidget.autoBootMenu();
    }

    /**
     * 主题切换菜单点击事件
     *
     * @param v
     */
    @Override
    public void onThemeChangedListener(View v) {
        mFloatingMenuWidget.closeMenu();
        chooseTheme();
    }

    /**
     * 搜索菜单点击事件
     *
     * @param v
     */
    @Override
    public void onSearchListener(View v) {
        mFloatingMenuWidget.closeMenu();
        Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this,
                        v, getResources().getString(R.string.app_name));// 不需要联动
        ActivityCompat.startActivity(HomeActivity.this, intent, options.toBundle());
    }

    /**
     * 投稿菜单点击事件
     *
     * @param v
     */
    @Override
    public void onContributeListener(View v) {
        mFloatingMenuWidget.closeMenu();
    }

    /**
     * 设置菜单点击事件
     *
     * @param v
     */
    @Override
    public void onSettingsListener(View v) {
        mFloatingMenuWidget.closeMenu();
        Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this,
                        v, getResources().getString(R.string.app_name));// 不需要联动
        ActivityCompat.startActivity(HomeActivity.this, intent, options.toBundle());
    }

    @Override
    public void onBackPressed() {
        if(mFloatingMenuWidget.isShowing()){
            mFloatingMenuWidget.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.newInstance().unRegisterCachePageNumChangedListener(this);
    }

    @Override
    public void onChanged(int count) {
        mViewPager.setOffscreenPageLimit(count);
    }
}
