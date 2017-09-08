package com.hiveview.cloudscreen.vipvideo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.activity.adapter.RecycleViewAdapter;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.VideoDetailInvoker;
import com.hiveview.cloudscreen.vipvideo.service.CloudScreenService;
import com.hiveview.cloudscreen.vipvideo.service.OnRequestResultListener;
import com.hiveview.cloudscreen.vipvideo.service.entity.DetailRecommendListEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.util.Logger;

import java.lang.ref.WeakReference;
import java.util.List;


public class DetailRecomment implements View.OnKeyListener, RecycleViewAdapter.RecommentOnItemListener {

    private static final String TAG = DetailRecomment.class.getSimpleName();

    private RelativeLayout rl_film_recomm;
    public RecyclerView recyclerView;
    public RecycleViewAdapter recycleViewAdapter;

    private View.OnKeyListener keyListener;
    private List<DetailRecommendListEntity> activityListEntity;

    private Activity activity;

    private boolean isRecommentDataCompleted = false;

    private OnRecommendDataEnableListener dataLisener;

    public DetailRecomment(Activity view, int id, int cid) {
        this.activity = view;
        rl_film_recomm = (RelativeLayout) view.findViewById(R.id.rl_film_recomm);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        recyclerView.setFocusable(false);
        recyclerView.setFilterTouchesWhenObscured(false);
        recyclerView.setOnKeyListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        int spacingInPixels = activity.getResources().getDimensionPixelSize(R.dimen.space);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

//        CloudScreenService.getInstance().getFilmDetailRecommend(new DetailRecommendListener(this), id, cid);
        resetData(id, cid);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/6/13
     * @Description 重置数据
     */
    public void resetData(int id, int cid) {
        isRecommentDataCompleted = false;
        if (null != dataLisener) {
            dataLisener.hasData(false);
        }
        CloudScreenService.getInstance().getFilmDetailRecommend(new DetailRecommendListener(this), id, cid);
    }

    public boolean isOpen = false;

    public boolean isRightOpen() {
        return isOpen;
    }

    public void openRecomment() {
        recyclerView.animate().translationX(0).setDuration(400).setInterpolator(new AccelerateInterpolator()).start();
        isOpen = true;
        recyclerView.setFocusable(true);
    }


    public void closeRecomment() {
        recyclerView.animate().translationX(200).setDuration(600).setInterpolator(new AccelerateInterpolator()).start();
        isOpen = false;
        recyclerView.setFocusable(false);
    }

    public boolean isRecommentData() {
        return isRecommentDataCompleted;
    }


    /**
     * 相关推荐接口
     */

    private static class DetailRecommendListener implements OnRequestResultListener<ResultEntity> {

        private WeakReference<DetailRecomment> ref;

        public DetailRecommendListener(DetailRecomment activity) {
            ref = new WeakReference<DetailRecomment>(activity);
        }

        @Override
        public void onSucess(ResultEntity resultEntity) {
            DetailRecomment activity = ref.get();
            if (activity != null) {
                activity.activityListEntity = resultEntity.getList();
                if (activity.activityListEntity != null && activity.activityListEntity.size() > 0) {
                    activity.recycleViewAdapter = new RecycleViewAdapter(activity.recyclerView);
                    activity.recycleViewAdapter.setData(activity.activityListEntity);
                    activity.isRecommentDataCompleted = true;
                    activity.recyclerView.setAdapter(activity.recycleViewAdapter);
                    activity.recycleViewAdapter.setOnItemClickListener(activity);
                    if (null != activity.dataLisener) {
                        activity.dataLisener.hasData(true);
                    }
                } else {
                    activity.isRecommentDataCompleted = false;
                    if (null != activity.dataLisener) {
                        activity.dataLisener.hasData(false);
                    }
                }
            }
        }

        @Override
        public void onFail(Exception e) {
            DetailRecomment activity = ref.get();
            if (null != activity && null != activity.dataLisener) {
                activity.dataLisener.hasData(false);
                Logger.e(activity.TAG, "Exception=" + e.toString() + "  " + CloudScreenApplication.getInstance().getString(R.string.album_detail_data_parse_error));
            }
        }

        @Override
        public void onParseFail(HiveviewException e) {
            DetailRecomment activity = ref.get();
            if (null != activity.dataLisener) {
                activity.dataLisener.hasData(false);
            }
            Logger.e(activity.TAG, "HiveviewException=" + e.toString() + "  " + CloudScreenApplication.getInstance().getString(R.string.album_detail_data_net_error));
        }
    }


    public void setOnKeyListener(View.OnKeyListener listener) {
        keyListener = listener;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return recyclerView.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (null != keyListener) {
            keyListener.onKey(v, keyCode, event);
        }
        return false;
    }


    @Override
    public void onItemClick(View view, int postion) {
        //跳转详情页。
        DetailRecommendListEntity entity = activityListEntity.get(postion);
        String action = VideoDetailInvoker.getInstance().getDetailActivityAction(entity.getCid());
        Intent intent = new Intent(action);
        intent.putExtra(AppConstants.EXTRA_VIDEOSET_ID, Integer.valueOf(entity.getId()));
        intent.putExtra(AppConstants.EXTRA_CID, Integer.valueOf(entity.getCid()));
        intent.putExtra(AppConstants.EXTRA_SOURCE, "8");//8为相关推荐
        activity.startActivity(intent);
    }


    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getChildPosition(view) != 0)
                outRect.top = space;
        }
    }

    public void setFocusable(boolean isFocus) {
        recyclerView.setFocusable(isFocus);
        recyclerView.setFocusable(false);
        recyclerView.setFocusableInTouchMode(false);
    }

    public void setOnRecommendDataEnableListener(OnRecommendDataEnableListener dataListener) {
        this.dataLisener = dataListener;
    }

    public interface OnRecommendDataEnableListener {
        void hasData(boolean hasData);
    }

}

