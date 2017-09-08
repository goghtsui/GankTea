/**
 * @Title CartoonDetailEpisode.java
 * @Package com.hiveview.cloudscreen.video.activity.component
 * @author haozening
 * @date 2014年9月10日 下午8:03:43
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.activity.component;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.activity.adapter.EpisodeTvAdapter;
import com.hiveview.cloudscreen.vipvideo.activity.adapter.EpisodeTvGroupAdapter;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.EpisodeListEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ProductInfoEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.view.VideoDetailHome;


/**
 * @author haozening
 * @ClassName TvDetailEpisode
 * @Description
 * @date 2014年9月10日 下午8:03:43
 */
public class TvDetailEpisode extends BaseFilmDetailEpisode {

    private static final String TAG = "TvDetailEpisode";
    List<EpisodeListEntity> list;
    private AlbumEntity albumEntity;

    public TvDetailEpisode(Activity activity, AlbumEntity albumEntity) {
        super(activity, albumEntity);
        this.albumEntity = albumEntity;
        leftAdapter = new EpisodeTvAdapter(activity);
        rightAdapter = new EpisodeTvGroupAdapter(activity);
    }

    private EpisodeTvAdapter leftAdapter;
    private EpisodeTvGroupAdapter rightAdapter;

    /**
     * 设置数据
     *
     * @Title setData
     * @author haozening
     * @Description
     */
    @Override
    public void setData(ResultEntity entity) {
        list = entity.getList();
        rightAdapter.setData(list);
        leftAdapter.setData(list, albumEntity);
        leftEpisodeView.setAdapter(leftAdapter);
        rightEpisodeView.setAdapter(rightAdapter);
        leftAdapter.notifyDataSetChanged();
        rightAdapter.notifyDataSetChanged();
        setFocusable(false);
    }

    @Override
    public EpisodeListEntity getEntity(int currentItem) {
        return leftAdapter.getEntity(currentItem);
    }

    @Override
    public void setProductInfoEntity(ProductInfoEntity productInfoEntity) {
        if (null!=leftAdapter) {
            leftAdapter.setProductInfo(productInfoEntity);
        }
    }

    @Override
    public void onChanged(View parent, View view, int position, boolean selected) {
        Log.d(TAG, "onChanged");
        if (selected) {
            if (parent.getId() == R.id.sv_episode_left) {// 如果是左侧剧集发生变化，就判断是否右侧剧集应该变换
                if (leftEpisodeView.hasFocus()) { // 如果是左侧剧集有焦点，就让右侧剧集发生改变，防止当左侧剧集没有焦点，但发生改变的时候影响右侧剧集
                    Integer selection = Integer.valueOf(view.getTag().toString()) - 1;

                    rightEpisodeView.setIsFastMode(leftEpisodeView.isFastMode());
                    rightEpisodeView.setSelection(selection / 20);
                }
            }
            if (parent.getId() == R.id.sv_episode_right) {// 如果右侧剧集发生变化，就判断是否左侧剧集应该变换
                if (rightEpisodeView.hasFocus()) { // 如果右侧剧集右焦点，就让左侧发生变化，防止当右侧剧集没有交点，但发生了改变影响到左侧剧集
                    Integer selection;
                    if (list.get(position).getIsEffective() == 1) {
                        selection = position * 20;
                    } else {
                        selection = Integer.valueOf(view.getTag().toString());
                    }
                    leftEpisodeView.setIsFastMode(rightEpisodeView.isFastMode());
                    leftEpisodeView.setSelection(selection);
                }
            }
        }
    }
}
