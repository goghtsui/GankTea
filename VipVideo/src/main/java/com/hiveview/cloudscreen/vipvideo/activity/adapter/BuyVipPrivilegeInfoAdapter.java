package com.hiveview.cloudscreen.vipvideo.activity.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.service.entity.PrerogativeListEntity;
import com.hiveview.cloudscreen.vipvideo.view.TypeFaceTextView;


public class BuyVipPrivilegeInfoAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    List<PrerogativeListEntity> entityList = new ArrayList<PrerogativeListEntity>();

    int currentPosition;
    public int VIEW_COUNT = 7;
    public int index = 0;

    public BuyVipPrivilegeInfoAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }


    public void setData(List<PrerogativeListEntity> entityList) {
        if (null != entityList) {
            this.entityList = entityList;
        }
    }


    @Override
    public int getCount() {
        int ori = VIEW_COUNT * index;
        if (entityList.size() - ori < VIEW_COUNT) {
            return entityList.size() - ori;
        } else {
            return VIEW_COUNT;
        }
    }

    @Override
    public Object getItem(int position) {
        return entityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        currentPosition = position;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(
                    R.layout.fragment_vip_privilege_item, null);
            holder.view = convertView.findViewById(R.id.bg_item);
            holder.tv_info = (TextView) convertView.findViewById(R.id.tv_info);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.position = position;
        holder.tv_info.setText(entityList.get(position + index * VIEW_COUNT).getName());
        convertView.setFocusableInTouchMode(false);
        convertView.setFocusable(false);
        return convertView;
    }

    class ViewHolder {
        View view;
        TextView tv_info;
        int position;

    }

}
