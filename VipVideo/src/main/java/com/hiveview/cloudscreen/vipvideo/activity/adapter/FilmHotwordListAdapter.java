package com.hiveview.cloudscreen.vipvideo.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.service.entity.HotWordEntity;
import com.hiveview.cloudscreen.vipvideo.view.TypeFaceTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/10
 * @TODO
 */
public class FilmHotwordListAdapter extends BaseAdapter {

    List<HotWordEntity> data = new ArrayList<>();

    HashMap<Integer, View> items = new HashMap<>();

    private LayoutInflater mInflater;


    public FilmHotwordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

    }

    public void setData(List<HotWordEntity> data) {
        this.data = data;

    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/3
     * @Description 根据热词排序获取热词实体
     */
    public HotWordEntity getData(int position) {
        return data.get(position);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/3
     * @Description 根据热词id获取热词排序
     */
    public int getPositionFromHotWordId(int hotWordId) {
        if (null != data) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getId() == hotWordId) {
                    return i;
                }
            }
            return 0;
        } else {
            return 0;
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/3
     * @Description 根据热词Id获取热词实体
     */
    public HotWordEntity getDataFromWordId(int hotWordId) {
        return getData(getPositionFromHotWordId(hotWordId));
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (null != items.get(position)) {
            return items.get(position).getId();
        } else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotwordHolder hotwordHolder = null;
        if (null == convertView) {
            hotwordHolder = new HotwordHolder();
            convertView = mInflater.inflate(R.layout.film_hotword_itemview, null);
            hotwordHolder.hotword = (TypeFaceTextView) convertView.findViewById(R.id.tv_filmlist_hortword_item);
            convertView.setTag(hotwordHolder);
        } else {
            hotwordHolder = (HotwordHolder) convertView.getTag();
        }
        hotwordHolder.hotword.setText(data.get(position).getName());
        items.put(position, convertView);
        return convertView;
    }


    public class HotwordHolder {
        TypeFaceTextView hotword;
    }


}
