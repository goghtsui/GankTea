package com.hiveview.cloudscreen.vipvideo.view.verticalViewPager;

import android.support.v4.view.PagerAdapter;

public abstract class VerticalPagerAdapter extends PagerAdapter {
    public float getPageHeight(int position) {
        return 1.f;
    }
}
