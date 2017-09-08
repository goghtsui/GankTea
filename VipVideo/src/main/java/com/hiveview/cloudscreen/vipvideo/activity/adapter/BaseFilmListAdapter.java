package com.hiveview.cloudscreen.vipvideo.activity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.service.entity.HiveviewBaseEntity;
import com.hiveview.cloudscreen.vipvideo.util.BitmapBlurUtil;
import com.hiveview.cloudscreen.vipvideo.util.Logger;
import com.hiveview.cloudscreen.vipvideo.view.pageItemView.FilmPageItemView;
import com.hiveview.cloudscreen.vipvideo.view.verticalViewPager.VerticalPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/12/25
 * @Description
 */
public abstract class BaseFilmListAdapter<T extends HiveviewBaseEntity> extends VerticalPagerAdapter implements View.OnClickListener {


    private static final String TAG = FilmListAdapter.class.getSimpleName();
    protected FilmPageItemView.OnItemKeyListener itemKeyListener;
    protected FilmPageItemView.OnItemSelectedListener itemSelectedListener;
    protected List<T> entities = new ArrayList<>();
    private View mCurrentView;
    protected final int COVER_ROUND;
    protected Context context;
    private int countPerPage = 10;
    protected int itemPosition = VerticalPagerAdapter.POSITION_UNCHANGED;
    private View firstItem;

    public View getFirstItem() {
        return null!=mCurrentView? (View) mCurrentView.getTag() :firstItem;
    }

    public BaseFilmListAdapter(FilmPageItemView.OnItemKeyListener listener, FilmPageItemView.OnItemSelectedListener itemSelectedListener, Context context) {
        this.context = context;
        itemKeyListener = listener;
        this.itemSelectedListener = itemSelectedListener;
        COVER_ROUND = (int) CloudScreenApplication.getInstance().getResources().getDimensionPixelSize(R.dimen.six_column_item_image_cover_round);
    }

    public abstract void clear();

    public void setData(List<T> entities) {
        if (null != entities) {
            this.entities = entities;
        }
    }

    public void addData(List<T> entities) {
        if (null != entities) {
            this.entities.addAll(entities);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return (int) Math.ceil((double)entities.size() / countPerPage );
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        FilmPageItemView view = (FilmPageItemView) object;
        for (int i = position * countPerPage, j = 0; i < (position + 1) * countPerPage; i++, j++) {
            View v = view.getSubviewAt(j);
            Log.d(TAG, "destroyItem j = " + j + "  i =  " + i);
            if (entities.size() > i) {
                BitmapBlurUtil.getInstance().cancelDisplayTask(view.getImageView(v));
                BitmapBlurUtil.getInstance().cancelDisplayTask(view.getCornerView(v));
            }
        }
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        FilmPageItemView view = new FilmPageItemView(CloudScreenApplication.getInstance());
        container.addView(view);
        view.setOnItemKeyListener(itemKeyListener);
        view.setOnItemSelectedListener(itemSelectedListener);
        setViewData(view, position);
        return view;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentView = (View) object;
    }

    public View getPrimaryItem() {
        return mCurrentView;
    }

    private void setViewData(FilmPageItemView view, int position) {
        for (int i = position * countPerPage, j = 0; i < (position + 1) * countPerPage; i++, j++) {
            View v = view.getSubviewAt(j);
            if (j == 0) {
                view.setTag(v);
                firstItem=v;
            }
            Log.d(TAG, "setViewData j = " + j + "  i =  " + i);
            if (entities.size() > i) {
                inflateData(view, v, entities.get(i), i);
            } else {
                hideView(v);
            }
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    protected abstract void inflateData(FilmPageItemView view, View v, T entity, int itemPosition);

    private void hideView(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    protected OnActionListener onActionListener;

    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
    }

    public interface OnActionListener {
        void effectiveAction();
    }
}
