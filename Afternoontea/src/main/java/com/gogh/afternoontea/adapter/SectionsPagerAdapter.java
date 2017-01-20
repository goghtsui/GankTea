package com.gogh.afternoontea.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.log.Logger;
import com.gogh.afternoontea.main.BaseFragment;

import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.</p>
 * <p> Created by <b>高晓峰</b> on 12/26/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/26/2016 do fisrt create. </li>
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    private List<BaseFragment> fragmentList;

    public SectionsPagerAdapter(Context context, List<BaseFragment> fragmentList, FragmentManager fm) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        if (fragmentList == null || fragmentList.size() == 0) {
            return 0;
        }
        return fragmentList.size();
    }

    @NonNull
    @Override
    public CharSequence getPageTitle(int position) {
        Logger.d("getPageTitle", "getPageTitle : " +fragmentList.get(position).getTitle() );
        switch (fragmentList.get(position).getTitle()) {
            case Urls.GANK_URL.ALL:
                return context.getResources().getString(R.string.home_activity_tab_tile_recognized);
            case Urls.GANK_URL.IOS:
                return context.getResources().getString(R.string.home_activity_tab_tile_ios);
            case Urls.GANK_URL.ANDROID:
                return context.getResources().getString(R.string.home_activity_tab_tile_android);
            case Urls.GANK_URL.WEB:
                return context.getResources().getString(R.string.home_activity_tab_tile_web);
            case Urls.GANK_URL.MATERIAL:
                return context.getResources().getString(R.string.home_activity_tab_tile_material);
            case Urls.GANK_URL.RECOMMEND:
                return context.getResources().getString(R.string.home_activity_tab_tile_recommend);
            case Urls.GANK_URL.WELFARE:
                return context.getResources().getString(R.string.home_activity_tab_tile_welfare);
            case Urls.GANK_URL.REST_VIDEO:
                return context.getResources().getString(R.string.home_activity_tab_tile_video);
        }
        return fragmentList.get(position).getTitle();
    }
}
