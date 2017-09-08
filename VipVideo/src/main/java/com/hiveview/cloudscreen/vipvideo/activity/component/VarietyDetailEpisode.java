/**
 * @Title VarietyDetailEpisode.java
 * @Package com.hiveview.cloudscreen.video.activity.component
 * @author haozening
 * @date 2014年9月10日 下午3:08:53
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.activity.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.activity.adapter.EpisodeVarietyAdapter;
import com.hiveview.cloudscreen.vipvideo.activity.adapter.EpisodeVarietyGroupAdapter;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.EpisodeListEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ProductInfoEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;


/**
 * @ClassName VarietyDetailEpisode
 * @Description
 * @author haozening
 * @date 2014年9月10日 下午3:08:53
 *
 */
public class VarietyDetailEpisode extends BaseFilmDetailEpisode {

    private static final String TAG = "VarietyDetailEpisode";
    private List<EpisodeListEntity> left = new ArrayList<EpisodeListEntity>();
    private List<DataHolder> right = new ArrayList<DataHolder>();

    private EpisodeVarietyAdapter leftAdapter;
    private EpisodeVarietyGroupAdapter rightAdapter;
    private AlbumEntity albumEntity;

    public VarietyDetailEpisode(Activity activity, AlbumEntity albumEntity) {
        super(activity, albumEntity);
        this.albumEntity = albumEntity;
        leftAdapter = new EpisodeVarietyAdapter(activity);
        rightAdapter = new EpisodeVarietyGroupAdapter(activity);
    }

    private void selectRightItem(int position) {
        if (leftEpisodeView.hasFocus()) { // 如果是左侧剧集有焦点，就让右侧剧集发生改变，防止当左侧剧集没有焦点，但发生改变的时候影响右侧剧集
            EpisodeListEntity entity = left.get(position);
            int index = 0;
            for (DataHolder holder : right) {
                if (holder.description.equals(EpisodeVarietyGroupAdapter.getYear(entity.getYear()))) {
                    index = holder.index;
                    break;
                }
            }
            rightEpisodeView.setIsFastMode(leftEpisodeView.isFastMode());
            rightEpisodeView.setSelection(index);
        }
    }

    private void selectLeftItem(int position) {
        if (rightEpisodeView.hasFocus()) { // 如果右侧剧集右焦点，就让左侧发生变化，防止当右侧剧集没有交点，但发生了改变影响到左侧剧集
            DataHolder holder = right.get(position);
            int index = 0;
            for (int i = 0; i < left.size(); i++) {
                if (holder.description.equals(EpisodeVarietyGroupAdapter.getYear(left.get(i).getYear()))) {
                    index = i;
                    break;
                }
            }
            leftEpisodeView.setIsFastMode(rightEpisodeView.isFastMode());
            leftEpisodeView.setSelection(index);
        }
    }

    @Override
    public void setData(ResultEntity entity) {
        Log.i("fragment", "setData");
        left = entity.getList();
        leftAdapter.setData(left, albumEntity);
        rightAdapter.setData(left);
        leftEpisodeView.setAdapter(leftAdapter);
        rightEpisodeView.setAdapter(rightAdapter);
        leftAdapter.notifyDataSetChanged();
        rightAdapter.notifyDataSetChanged();
        right = rightAdapter.getData();
        setFocusable(false);
    }

    @Override
    public EpisodeListEntity getEntity(int currentItem) {
        return leftAdapter.getEntity(currentItem);
    }

    @Override
    public void setProductInfoEntity(ProductInfoEntity productInfoEntity) {
        leftAdapter.setProductInfo(productInfoEntity);
    }

    @Override
    public void onChanged(View parent, View view, int position, boolean selected) {
        if (selected) {
            if (parent.getId() == R.id.sv_episode_left) {// 如果是左侧剧集发生变化，就判断是否右侧剧集应该变换
                selectRightItem(position);
            }
            if (parent.getId() == R.id.sv_episode_right) {// 如果右侧剧集发生变化，就判断是否左侧剧集应该变换
                selectLeftItem(position);
            }
        }
    }

}
