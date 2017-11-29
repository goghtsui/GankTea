package com.gogh.afternoontea.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gogh.afternoontea.BuildConfig;
import com.gogh.afternoontea.R;
import com.gogh.afternoontea.constant.Constant;
import com.gogh.afternoontea.listener.OnCachePageNumChangedListener;
import com.gogh.afternoontea.listener.OnMultipleClickListener;
import com.gogh.afternoontea.main.BaseAppCompatActivity;
import com.gogh.afternoontea.preference.PreferenceManager;
import com.gogh.afternoontea.theme.ThemeManager;
import com.gogh.afternoontea.utils.DataUtil;
import com.gogh.afternoontea.utils.IntentUtils;
import com.gogh.afternoontea.utils.TintColor;
import com.gogh.afternoontea.view.FloatMenuButton;
import com.gogh.afternoontea.widget.FloatingMenuWidget;
import com.gogh.afternoontea.widget.HomePagerView;

import static com.gogh.afternoontea.R.id.container;

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
    protected void updateThemeByChoice(int themeColor) {
        // 动态设置滚动条的颜色
        //        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(HomeActivity.this, themeStyleId));
        //        tabLayout.setSelectedTabIndicatorColor(TintColor.getAccentColor(HomeActivity.this));
        floatMenuButton.resetFloatMenuBackground(themeColor);
        TintColor.setBackgroundTintList(floatingActionButton, ContextCompat.getColor(HomeActivity.this, themeColor));
        // 动态设置tab背景颜色
        tabLayout.setBackgroundColor(ContextCompat.getColor(HomeActivity.this, themeColor));
        // 设置页面背景色
        mViewPager.setBackgroundColor(ContextCompat.getColor(HomeActivity.this, themeColor));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);
        PreferenceManager.newInstance().registerCachePageNumChangedListener(this);
        initView();
        if (BuildConfig.DEBUG) {
            // 内存泄露检查案例
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectActivityLeaks()
                    .penaltyLog()
                    .build());

            // 资源引用没有关闭检查案例
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build());
        }
    }

    private void initView() {
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

        mViewPager = (ViewPager) findViewById(container);

        // 分页适配器
        new HomePagerView(this, tabLayout, mViewPager, floatingActionButton).setOnMultipleClickListener(this);

        // 设置悬浮菜单的背景颜色
        floatMenuButton.initFloatMenuBackground(TintColor.getPrimaryColor(HomeActivity.this));

        // 设置页面背景色
        mViewPager.setBackgroundColor(TintColor.getPrimaryColor(HomeActivity.this));
    }

    @Override
    public void onClickListener(View view) {
        mFloatingMenuWidget.autoBootMenu();
    }

    @Override
    public void onLongClickListener(View view) {
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
        mViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this,
                                v, getResources().getString(R.string.app_name));// 不需要联动
                ActivityCompat.startActivity(HomeActivity.this, intent, options.toBundle());
            }
        }, Constant.DURATION_SHORT);
    }

    /**
     * 投稿菜单点击事件
     *
     * @param v
     */
    @Override
    public void onContributeListener(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.home_contribute_dialog_title)
                .content(R.string.home_contribute_dialog_content)
                .positiveText(R.string.home_contribute_dialog_positive_button)
                .negativeText(R.string.home_contribute_dialog_negative_button)
                .onPositive((dialog, which) -> {
                    IntentUtils.contibuteByEmail(HomeActivity.this, getResources().getString(R.string.home_contribute_emial_title),
                            getResources().getString(R.string.home_contribute_email_content));
                })
                .show();
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
    public void onChanged(int count) {
        mViewPager.setOffscreenPageLimit(count);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThemeManager.newInstance().clear();
        PreferenceManager.newInstance().clear();
        DataUtil.cleanApplicationData(this);
    }

}
