package com.hiveview.cloudscreen.vipvideo.activity.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.activity.component.BaseFilmDetailEpisode.DataHolder;
import com.hiveview.cloudscreen.vipvideo.service.entity.EpisodeListEntity;
import com.hiveview.cloudscreen.vipvideo.util.ToastUtil;
import com.hiveview.cloudscreen.vipvideo.util.TypefaceUtil;
import com.hiveview.cloudscreen.vipvideo.view.TypeFaceTextView;
import com.hiveview.cloudscreen.vipvideo.view.wheelview.BaseWheelAdapter;


public abstract class BaseEpisodeGroupAdapter extends BaseWheelAdapter {

    private Context context;
    private List<DataHolder> list = new ArrayList<DataHolder>();
    private List<EpisodeListEntity> data;

    public BaseEpisodeGroupAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<EpisodeListEntity> data) {
        this.data = data;
        if (null != data) {
            list = formatData(data);
        }
    }

    public List<DataHolder> getData() {
        return list;
    }

    protected abstract List<DataHolder> formatData(List<EpisodeListEntity> data);

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 由于有的剧集不全，因此，得记录一个值，保存每20集之间一共有多少集存在的电视剧剧集
        int lastEpisodeCount = 0;
        for (EpisodeListEntity entity : data) {
            if (position * 20 == 0) {
                break;
            }
            lastEpisodeCount++;
            if (entity.getIsEffective() == 1) {
                if ((position * 20) % (position * 20) == 0) {
                    break;
                }
            } else {
                if (entity.getPhase() % (position * 20) == 0) {
                    break;
                }
            }
        }

        LayoutParams layoutParams = new LayoutParams((int) context.getResources().getDimension(
                R.dimen.baseEpisodeGroupAdapter_getView_LayoutParams_width), LayoutParams.WRAP_CONTENT);// 260

        TypeFaceTextView view = new TypeFaceTextView(context);
//        view.setTypeface(TypefaceUtil.TypefaceFile.YOUYUAN);
        view.setText(list.get(position).description);
        view.setTextSize(context.getResources().getDimension(R.dimen.baseEpisodeGroupAdapter_getView_TypeFaceTextView_TextSize));// 20
        view.setTextColor(Color.parseColor("#9a9a9a"));
        view.setPadding((int) context.getResources().getDimension(R.dimen.baseEpisodeGroupAdapter_getView_TypeFaceTextView_Padding_left),
                (int) context.getResources().getDimension(R.dimen.baseEpisodeGroupAdapter_getView_TypeFaceTextView_Padding_top), (int) context
                        .getResources().getDimension(R.dimen.baseEpisodeGroupAdapter_getView_TypeFaceTextView_Padding_reigh), (int) context
                        .getResources().getDimension(R.dimen.baseEpisodeGroupAdapter_getView_TypeFaceTextView_Padding_bottom));// 0,32,0,32
        view.setGravity(Gravity.CENTER);
        // 将实际存在的的电视剧剧集保存在TAG中，在发生滚动设置selection的时候使用
        view.setTag(lastEpisodeCount);
        view.setLayoutParams(layoutParams);
        return view;
    }

    @Override
    public void nextView(View nextView) {
        if (null != nextView) {
            TypeFaceTextView view = (TypeFaceTextView) nextView;
            view.setTextColor(Color.parseColor("#9a9a9a"));
            nextView.animate().scaleX(1f).scaleY(1f).start();
        }
    }

    @Override
    public void currentView(View currentView) {
        if (null != currentView) {
            TypeFaceTextView view = (TypeFaceTextView) currentView;
            view.setTextColor(Color.parseColor("#FFFFFF"));
            currentView.animate().scaleX(1.5f).scaleY(1.5f).start();
        }
    }

    @Override
    public void preView(View preView) {
        if (null != preView) {
            TypeFaceTextView view = (TypeFaceTextView) preView;
            view.setTextColor(Color.parseColor("#9a9a9a"));
            preView.animate().scaleX(1f).scaleY(1f).start();
        }
    }

}
