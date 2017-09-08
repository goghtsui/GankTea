package com.gogh.fortest.dynamic.test;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.gogh.fortest.R;
import com.gogh.fortest.dynamic.DynamicAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 8/23/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 8/23/2017 do fisrt create. </li>
 */

public class TestAdapter implements DynamicAdapter<MyHolder> {

    private Context context;
    private List<TestEntity> datas;

    public TestAdapter(Context context, List<TestEntity> datas){
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dynamic_item;
    }

    @Override
    public MyHolder getViewHolder(View item) {
        return new MyHolder(item);
    }

    @Override
    public int getItemCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public void resetItemParams(View itemtView, int position) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(datas.get(position).getWidth(), datas.get(position).getHeight());
        params.leftMargin = datas.get(position).getPointX();
        params.topMargin = datas.get(position).getPointY();
        itemtView.setLayoutParams(params);
    }

    @Override
    public void onBindView(MyHolder myHolder, int position) {
        Picasso.with(context).load(datas.get(position).getImageUrl()).into(myHolder.imageView);
    }

}
