package com.gogh.afternoontea.adapter.gank;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.entity.gank.GankEntity;
import com.gogh.afternoontea.log.Logger;
import com.gogh.afternoontea.ui.GankDetailActivity;
import com.gogh.afternoontea.ui.HomeActivity;
import com.gogh.afternoontea.utils.DataUtil;
import com.gogh.afternoontea.utils.Resource;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/28/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/28/2016 do fisrt create. </li>
 */
public class GankListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BaseGankAdapter<GankEntity.ResultsBean> {

    private static final String TAG = "GankListAdapter";

    /**
     * 普通项
     */
    private static final int VIEW_TYPE_NORMAL = 0x10010;

    /**
     * 没有图片的项
     */
    private static final int VIEW_TYPE_NO_PIC = 0x10011;

    /**
     * footer项
     */
    private static final int VIEW_TYPE_FOOTER = 0x10012;

    private Context context;
    private String resourceType;
    private boolean isScrollToBottom = false;
    private boolean isLoadingError = false;

    private List<GankEntity.ResultsBean> datas;
    private OnItemClickListener onItemClickListener;

    public GankListAdapter(Context context, String resourceType) {
        this.context = context;
        this.resourceType = resourceType;
    }

    /**
     * Called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #GankListAdapter#onBindViewHolder(RecyclerView.ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #GankListAdapter#onBindViewHolder(RecyclerView.ViewHolder, int)
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Logger.d("TAG", "onCreateViewHolder. ");
        if (viewType == VIEW_TYPE_NORMAL) {
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gank_list_item_layout, parent, false);
            return new ItemViewHolder(rootView);
        } else if (viewType == VIEW_TYPE_NO_PIC) {
            View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gank_list_item_nopic_layout, parent, false);
            return new NoPicViewHolder(footerView);
        } else {
            View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gank_list_item_footer, parent, false);
            return new FooterViewHolder(footerView);
        }
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link RecyclerView.ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #GankListAdapter#onBindViewHolder(RecyclerView.ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Logger.d("TAG", "onBindViewHolder. ");
        if (holder != null && holder instanceof ItemViewHolder) {
            GankEntity.ResultsBean entity = datas.get(position);
            if (entity != null) {
                if (TextUtils.isEmpty(entity.getDesc())) {
                    ((ItemViewHolder) holder).titleName.setText(" ");
                } else {
                    ((ItemViewHolder) holder).titleName.setText(entity.getDesc());
                }

                if (TextUtils.isEmpty(entity.getDesc())) {
                    ((ItemViewHolder) holder).itemAuthor.setText(" ");
                } else {
                    ((ItemViewHolder) holder).itemAuthor.setText(entity.getWho());
                }

                if (TextUtils.isEmpty(entity.getDesc())) {
                    ((ItemViewHolder) holder).itemCreateDate.setText(" ");
                } else {
                    ((ItemViewHolder) holder).itemCreateDate.setText(entity.getPublishedAt().replace("T", " ").replace("Z", " "));
                }

                if (!TextUtils.isEmpty(resourceType) && resourceType.equals("all")
                        && !TextUtils.isEmpty(entity.getType())) {
                    ((ItemViewHolder) holder).itemType.setVisibility(View.VISIBLE);
                    ((ItemViewHolder) holder).itemType.setImageResource(Resource.getResIdByType(entity.getType()));
                }

                String imageUrl = null;
                if (entity.getImages() != null && entity.getImages().size() > 0) {
                    imageUrl = entity.getImages().get(0);
                }

                Picasso.with(context).load(imageUrl)
                        .config(Bitmap.Config.ARGB_8888)
                        .placeholder(R.mipmap.tech_item_bg)
                        .error(R.mipmap.tech_item_bg)
                        .into(((ItemViewHolder) holder).itemBgImage);
            }
        } else if (holder != null && holder instanceof NoPicViewHolder) {
            GankEntity.ResultsBean entity = datas.get(position);
            if (entity != null) {
                if (TextUtils.isEmpty(entity.getDesc())) {
                    ((NoPicViewHolder) holder).titleName.setText(" ");
                } else {
                    ((NoPicViewHolder) holder).titleName.setText(entity.getDesc());
                }

                if (TextUtils.isEmpty(entity.getDesc())) {
                    ((NoPicViewHolder) holder).itemAuthor.setText(" ");
                } else {
                    ((NoPicViewHolder) holder).itemAuthor.setText(entity.getWho());
                }

                if (TextUtils.isEmpty(entity.getDesc())) {
                    ((NoPicViewHolder) holder).itemCreateDate.setText(" ");
                } else {
                    ((NoPicViewHolder) holder).itemCreateDate.setText(entity.getPublishedAt().replace("T", " ").replace("Z", " "));
                }

                if (!TextUtils.isEmpty(resourceType) && resourceType.equals("all")
                        && !TextUtils.isEmpty(entity.getType())) {
                    ((NoPicViewHolder) holder).itemType.setVisibility(View.VISIBLE);
                    ((NoPicViewHolder) holder).itemType.setImageResource(Resource.getResIdByType(entity.getType()));
                }
            }
        } else {
            ((FooterViewHolder) holder).itemView.setVisibility((isScrollToBottom && !isLoadingError) ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        Logger.d("TAG", "getItemCount. ");
        int isFooter = (isScrollToBottom && !isLoadingError) ? 1 : 0;
        return datas.size() + isFooter;
    }

    @Override
    public int getItemViewType(int position) {
        Logger.d("TAG", "getItemViewType. ");
        // 添加 加载更多 项
        if (position + 1 == getItemCount()) {
            return VIEW_TYPE_FOOTER;
        }

        // 有图片的项
        if (datas.get(position).getImages() != null
                && datas.get(position).getImages().size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {// 没有图片
            return VIEW_TYPE_NO_PIC;
        }

    }

    @Override
    public GankEntity.ResultsBean getItem(int position) {
        Logger.d("TAG", "getItem. ");
        return datas.get(position);
    }

    @Override
    public void setData(List<GankEntity.ResultsBean> datas) {
        if (this.datas != null) {
            this.datas.clear();
        }
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public void addRefreshData(List<GankEntity.ResultsBean> datas) {
        this.datas = DataUtil.removeDuplicateData(this.datas, datas);
        this.notifyDataSetChanged();
    }

    @Override
    public void addLoadMoreData(List<GankEntity.ResultsBean> datas) {
        this.datas = DataUtil.getAvalibleData(this.datas, datas);
        this.notifyDataSetChanged();
    }

    @Override
    public boolean isScrolledToBottom() {
        return isScrollToBottom;
    }

    @Override
    public void setScrollToBottom(boolean isBottom, boolean isLoadingError) {
        Logger.d(TAG, "setScrollToBottom : " + isBottom);
        isScrollToBottom = isBottom;
        this.isLoadingError = isLoadingError;
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    protected class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatImageView itemType;
        private ImageView itemBgImage;
        private AppCompatTextView itemCreateDate;
        private AppCompatTextView titleName;
        private AppCompatTextView itemAuthor;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemType = (AppCompatImageView) itemView.findViewById(R.id.gank_list_item_type);
            titleName = (AppCompatTextView) itemView.findViewById(R.id.gank_item_title);
            itemAuthor = (AppCompatTextView) itemView.findViewById(R.id.gank_item_author);
            itemBgImage = (ImageView) itemView.findViewById(R.id.gank_item_image_bg);
            itemCreateDate = (AppCompatTextView) itemView.findViewById(R.id.gank_item_date);
            itemView.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, this.getAdapterPosition(), R.id.gank_item_image_bg);
            }
        }
    }

    protected class NoPicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatImageView itemType;
        private AppCompatTextView titleName;
        private AppCompatTextView itemAuthor;
        private AppCompatTextView itemCreateDate;

        public NoPicViewHolder(View itemView) {
            super(itemView);
            itemType = (AppCompatImageView) itemView.findViewById(R.id.gank_list_item_nopic_type);
            titleName = (AppCompatTextView) itemView.findViewById(R.id.gank_list_item_nopic_title);
            itemAuthor = (AppCompatTextView) itemView.findViewById(R.id.gank_list_item_nopic_author);
            itemCreateDate = (AppCompatTextView) itemView.findViewById(R.id.gank_list_item_nopic_time);
            itemView.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, GankDetailActivity.class);
            intent.putExtra(Urls.GANK_URL.BUNDLE_KEY, datas.get(getAdapterPosition()));
            View intoView = v.findViewById(R.id.gank_list_item_nopic_default_img);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation((HomeActivity) context,
                            intoView, context.getString(R.string.app_name));// 不需要联动
            ActivityCompat.startActivity((HomeActivity) context, intent, options.toBundle());
        }
    }

    protected class FooterViewHolder extends RecyclerView.ViewHolder {

        private View itemView;

        public FooterViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

}
