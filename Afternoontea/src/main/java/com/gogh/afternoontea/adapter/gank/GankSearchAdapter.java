package com.gogh.afternoontea.adapter.gank;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.adapter.holder.NoPicViewHolder;
import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.entity.gank.BaseEntity;
import com.gogh.afternoontea.ui.GankDetailActivity;
import com.gogh.afternoontea.ui.SearchActivity;
import com.gogh.afternoontea.utils.Resource;

import java.util.List;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 9/13/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 9/13/2017 do fisrt create. </li>
 */

public class GankSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BaseGankAdapter<BaseEntity> {

    private Context context;
    private List<BaseEntity> datas;

    public GankSearchAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View noPicView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gank_list_item_nopic_layout, parent, false);
        return new NoPicViewHolder(noPicView, context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseEntity entity = datas.get(position);
        if (entity != null && holder != null) {

            ((NoPicViewHolder) holder).onUpdateByTheme();

            if (TextUtils.isEmpty(entity.getDesc())) {
                ((NoPicViewHolder) holder).titleName.setText(" ");
            } else {
                ((NoPicViewHolder) holder).titleName.setText(entity.getDesc());
            }

            if (TextUtils.isEmpty(entity.getWho())) {
                ((NoPicViewHolder) holder).itemAuthor.setText(" ");
            } else {
                ((NoPicViewHolder) holder).itemAuthor.setText(entity.getWho());
            }

            if (TextUtils.isEmpty(entity.getPublishedAt())) {
                ((NoPicViewHolder) holder).itemCreateDate.setText(" ");
            } else {
                ((NoPicViewHolder) holder).itemCreateDate.setText(entity.getPublishedAt().replace("T", " ").replace("Z", " "));
            }

            if (!TextUtils.isEmpty(entity.getType())) {
                ((NoPicViewHolder) holder).itemType.setVisibility(View.VISIBLE);
                ((NoPicViewHolder) holder).itemType.setImageResource(Resource.getResIdByType(entity.getType()));
            }

            ((NoPicViewHolder) holder).itemImage.setImageResource(R.mipmap.gank_list_item_image_default);

            ((NoPicViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GankDetailActivity.class);
                    intent.putExtra(Urls.GANK_URL.BUNDLE_KEY, entity);

                    // 不做跳转动画关联
                    ActivityOptionsCompat options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation((SearchActivity) context,
                                    ((NoPicViewHolder) holder).itemImage, context.getResources().getString(R.string.app_name));
                    ActivityCompat.startActivity(context, intent, options.toBundle());
                }
            });
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public BaseEntity getItem(int position) {
        return datas.get(position);
    }

    @Override
    public void setData(List<BaseEntity> datas) {
        this.datas = datas;
    }

    @Override
    public void addRefreshData(List<BaseEntity> newDatas) {
        if (newDatas != null && newDatas.size() > 0) {
            int oldSize = datas.size();
            for (int i = 0; i < oldSize; i++) {
                datas.remove(0);
                notifyItemRemoved(0);
            }

            int newSize = newDatas.size();
            for (int i = 0; i < newSize; i++) {
                this.datas.add(newDatas.get(i));
                notifyItemInserted(i);
                notifyItemChanged(i);
            }
        }
    }

    @Override
    public void addLoadMoreData(List<BaseEntity> datas) {

    }

    @Override
    public boolean isScrolledToBottom() {
        return false;
    }

    @Override
    public void setScrollToBottom(boolean isBottom) {

    }

    @Override
    public void setLoadingError(boolean isLoadingError) {

    }

    @Override
    public void notifyByThemeChanged() {

    }
}
