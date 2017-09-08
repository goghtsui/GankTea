/**
 * @Title FilmListAdapter.java
 * @Package com.hiveview.cloudscreen.video.activity.adapter
 * @author haozening
 * @date 2015-5-4 下午2:20:34
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.activity.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.activity.SubjectDetailActivity;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;
import com.hiveview.cloudscreen.vipvideo.service.entity.SubjectEntity;
import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.Statistics;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.SubjectListActionInfo;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.view.pageItemView.SubjectPageItemView;
import com.hiveview.cloudscreen.vipvideo.view.verticalViewPager.VerticalPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author haozening
 * @ClassName FilmListAdapter
 * @Description
 * @date 2015-5-4 下午2:20:34
 */
public class SubjectListAdapter extends VerticalPagerAdapter implements OnClickListener {

    /**
     * FilmListAdapter
     */
    private static final String TAG = SubjectListAdapter.class.getSimpleName();
    private List<SubjectEntity> entities = new ArrayList<SubjectEntity>();
    private SubjectPageItemView.OnItemKeyListener itemKeyListener;
    private View mCurrentView;
    private Activity context;

    private int itemPosition = VerticalPagerAdapter.POSITION_UNCHANGED;

    public SubjectListAdapter(SubjectPageItemView.OnItemKeyListener listener, Activity context) {
        this.context = context;
        itemKeyListener = listener;
    }

    public void clear() {
        entities.clear();
        notifyDataSetChanged();
    }

    public void setData(List<SubjectEntity> entities) {
        if (null != entities) {
            this.entities = entities;
        }
    }

    public void addData(List<SubjectEntity> entities) {
        if (null != entities) {
            this.entities.addAll(entities);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return entities.size() / 6 + (entities.size() % 6 == 0 ? 0 : 1);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        SubjectPageItemView view = new SubjectPageItemView(CloudScreenApplication.getInstance());
        container.addView(view);
        view.setOnItemKeyListener(itemKeyListener);
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

    private void setViewData(SubjectPageItemView view, int position) {
        for (int i = position * 6, j = 0; i < (position + 1) * 6; i++, j++) {
            View v = view.getSubviewAt(j);
            Log.d(TAG, "j = " + j + "  i =  " + i);
            if (entities.size() > i) {
                inflateData(view, v, entities.get(i));
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

    private void inflateData(SubjectPageItemView view, View v, SubjectEntity entity) {
        v.setTag(entity);
        v.setOnClickListener(this);
        v.setVisibility(View.VISIBLE);
        ImageView cover = view.getImageView(v);
        cover.setBackgroundResource(ResourceProvider.getInstance().getDefaultCoverListLandscape());
        ImageView corner = view.getCorner(v);
        TextView title = view.getTitleTextView(v);
        TextView descript = view.getDescriptTextView(v);
        TextView count = view.getCountTextView(v);
        if (null != entity.getCornerContentApiVo() && null != entity.getCornerContentApiVo().getCornerPic()) {
            DisplayImageUtil.getInstance().displayImage(entity.getCornerContentApiVo().getCornerPic(), corner);
        }
        String back = entity.getSubjectHorPic();
        if (null != back && !"null".equals(back)) {
            DisplayImageUtil.getInstance().setRound((int) context.getResources().getDimension(R.dimen.recommendview_cover_round)).setDuration(200).displayImage(back, cover);
        }
        title.setText(entity.getSubjectName());
        if (null != entity.getSubjectDesc()) {
            descript.setText(entity.getSubjectDesc().toCharArray().length > 15 ? entity.getSubjectDesc().substring(0, 15) + "..." : entity
                    .getSubjectDesc());
        }
        if (null != entity.getSubjectSize() && entity.getSubjectSize() > 0) {
            count.setVisibility(View.VISIBLE);
            count.setText(String.format(context.getString(R.string.total_set), "" + entity.getSubjectSize()));
        } else {
            count.setVisibility(View.INVISIBLE);
        }
    }

    private void hideView(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        SubjectEntity entity = (SubjectEntity) v.getTag();

        BaseActionInfo info = new SubjectListActionInfo("03", "sde0302", "2009", null, entity.getSubjectId() + "", entity.getSubjectName());//统计埋点
        new Statistics(context, info).send();//统计埋点
        if (null != onActionListener) {
            onActionListener.effectiveAction();
        }

        Intent detailIntent = new Intent(context, SubjectDetailActivity.class);
        detailIntent.putExtra(AppConstants.EXTRA_SUBJECT_ID, entity.getSubjectId());
        detailIntent.putExtra(AppConstants.EXTRA_SUBJECT_BACKGROUND, entity.getSubjectHorBgImg());
        context.startActivity(detailIntent);
        context.overridePendingTransition(R.anim.film_detail_in, R.anim.film_detail_out);
    }

    private OnActionListener onActionListener;

    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
    }

    public interface OnActionListener {
        void effectiveAction();
    }

}
