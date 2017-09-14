package com.gogh.afternoontea.adapter.gank;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.adapter.holder.FooterViewHolder;
import com.gogh.afternoontea.adapter.holder.ItemViewHolder;
import com.gogh.afternoontea.adapter.holder.NoPicViewHolder;
import com.gogh.afternoontea.adapter.holder.WelfareViewHolder;
import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.entity.gank.GankEntity;
import com.gogh.afternoontea.preference.imp.Configuration;
import com.gogh.afternoontea.ui.GankDetailActivity;
import com.gogh.afternoontea.ui.HomeActivity;
import com.gogh.afternoontea.ui.ScaleImageActivity;
import com.gogh.afternoontea.utils.DataUtil;
import com.gogh.afternoontea.utils.ImageLoader;
import com.gogh.afternoontea.utils.Logger;
import com.gogh.afternoontea.utils.NetWorkInfo;
import com.gogh.afternoontea.utils.Resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.view.View.GONE;

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
     *  福利图片
     */
    private static final int VIEW_TYPE_WELFARE = 0x10012;

    /**
     * footer项
     */
    private static final int VIEW_TYPE_FOOTER = 0x10013;

    private Context context;
    private String resourceType;
    private boolean isScrollToBottom = false;
    private boolean isLoadingError = false;

    private NetWorkInfo netWorkInfo;

    @Nullable
    private List<GankEntity.ResultsBean> datas;

    public GankListAdapter(@NonNull Context context, String resourceType) {
        this.context = context;
        this.resourceType = resourceType;
        netWorkInfo = new NetWorkInfo(context);
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
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_NORMAL) {
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gank_list_item_layout, parent, false);
            return new ItemViewHolder(rootView, context);
        } else if (viewType == VIEW_TYPE_NO_PIC) {
            View noPicView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gank_list_item_nopic_layout, parent, false);
            return new NoPicViewHolder(noPicView, context);
        } else if (viewType == VIEW_TYPE_WELFARE) {
            View welfareView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gank_welfare_item_layout, parent, false);
            return new WelfareViewHolder(context, welfareView);
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
    @NonNull
    @Override
    public void onBindViewHolder(@Nullable RecyclerView.ViewHolder holder, int position) {
        if (holder != null && holder instanceof ItemViewHolder) {
            bindNormalData(holder, position);
        } else if (holder != null && holder instanceof NoPicViewHolder) {
            bindNopicData(holder, position);
        } else if (holder != null && holder instanceof WelfareViewHolder) {
            bindWelfareData(holder, position);
        } else {
            if (isScrollToBottom) {
                if (isLoadingError) {
                    ((FooterViewHolder) holder).itemView.setVisibility(GONE);
                } else {
                    ((FooterViewHolder) holder).itemView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 绑定数据到view，该数据是包含图片的
     *
     * @param holder
     * @param position
     */
    @NonNull
    private void bindNormalData(@NonNull RecyclerView.ViewHolder holder, int position) {
        GankEntity.ResultsBean entity = datas.get(position);
        if (entity != null && holder != null) {
            ((ItemViewHolder) holder).onUpdateByTheme();
            if (TextUtils.isEmpty(entity.getDesc())) {
                ((ItemViewHolder) holder).titleName.setText(" ");
            } else {
                ((ItemViewHolder) holder).titleName.setText(entity.getDesc());
            }

            if (TextUtils.isEmpty(entity.getWho())) {
                ((ItemViewHolder) holder).itemAuthor.setText(" ");
            } else {
                ((ItemViewHolder) holder).itemAuthor.setText(entity.getWho());
            }

            if (TextUtils.isEmpty(entity.getPublishedAt())) {
                ((ItemViewHolder) holder).itemCreateDate.setText(" ");
            } else {
                ((ItemViewHolder) holder).itemCreateDate.setText(entity.getPublishedAt().replace("T", " ").replace("Z", " "));
            }

            if (!TextUtils.isEmpty(resourceType) && resourceType.equals(Urls.GANK_URL.ALL)
                    && !TextUtils.isEmpty(entity.getType())) {
                ((ItemViewHolder) holder).itemType.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).itemType.setImageResource(Resource.getResIdByType(entity.getType()));
            }

            String imageUrl = null;

            if (entity.getImages() != null && entity.getImages().size() > 0) {
                if (!Configuration.newInstance().isNopicMode()) {
                    if (Configuration.newInstance().isWifiPicMode()) {
                        if (netWorkInfo.isWifi()) {
                            imageUrl = entity.getImages().get(0);
                        }
                    } else {
                        imageUrl = entity.getImages().get(0);
                    }
                }
            }

            // placeholder(R.mipmap.default_item_bg)
            ImageLoader.loadWithError(context, imageUrl, ((ItemViewHolder) holder).itemBgImage, R.mipmap.default_item_bg);

            ((ItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GankDetailActivity.class);
                    intent.putExtra(Urls.GANK_URL.BUNDLE_KEY, entity);

                    ActivityOptionsCompat options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation((HomeActivity) context,
                                    ((ItemViewHolder) holder).itemBgImage, context.getResources().getString(R.string.translation_element_name));
                    ActivityCompat.startActivity(context, intent, options.toBundle());
                }
            });

        }
    }

    /**
     * 绑定数据到view，该数据是不包含图片的
     *
     * @param holder
     * @param position
     */
    @NonNull
    private void bindNopicData(@NonNull RecyclerView.ViewHolder holder, int position) {
        GankEntity.ResultsBean entity = datas.get(position);
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

            if (!TextUtils.isEmpty(resourceType) && resourceType.equals(Urls.GANK_URL.ALL)
                    && !TextUtils.isEmpty(entity.getType())) {
                ((NoPicViewHolder) holder).itemType.setVisibility(View.VISIBLE);
                ((NoPicViewHolder) holder).itemType.setImageResource(Resource.getResIdByType(entity.getType()));
            }

            String imageUrl = null;

            if (entity.getImages() != null && entity.getImages().size() > 0) {
                if (!Configuration.newInstance().isNopicMode()) {
                    if (Configuration.newInstance().isWifiPicMode()) {
                        if (netWorkInfo.isWifi()) {
                            imageUrl = entity.getImages().get(0);
                        }
                    } else {
                        imageUrl = entity.getImages().get(0);
                    }
                }
            }

            ImageLoader.load(context, imageUrl, ((NoPicViewHolder) holder).itemImage, R.mipmap.gank_list_item_image_default,
                    R.mipmap.gank_list_item_image_default);

            ((NoPicViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GankDetailActivity.class);
                    intent.putExtra(Urls.GANK_URL.BUNDLE_KEY, entity);

                    ActivityOptionsCompat options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation((HomeActivity) context,
                                    ((NoPicViewHolder) holder).itemImage, context.getResources().getString(R.string.translation_element_name));
                    ActivityCompat.startActivity(context, intent, options.toBundle());
                }
            });
        }
    }

    private String getImageUrl(String imageUrl) {
        String result = null;

        if (Configuration.newInstance().isWifiPicMode()) {
            if (netWorkInfo.isWifi()) {
                result = imageUrl;
            }
        } else {
            result = imageUrl;
        }

        return result;
    }

    /**
     * 绑定数据到view，该数据是不包含图片的
     *
     * @param holder
     * @param position
     */
    @NonNull
    private void bindWelfareData(@NonNull RecyclerView.ViewHolder holder, int position) {
        GankEntity.ResultsBean entity = datas.get(position);
        if (entity != null && holder != null) {
            ((WelfareViewHolder) holder).onUpdateByTheme();
            if (!TextUtils.isEmpty(entity.getUrl())) {
                String imageUrl = getImageUrl(entity.getUrl());

                ImageLoader.loadWithReset(context, imageUrl, ((WelfareViewHolder) holder).mWelfareImage, R.mipmap.default_item_bg,
                        R.mipmap.default_item_bg);

                if (TextUtils.isEmpty(entity.getPublishedAt())) {
                    ((WelfareViewHolder) holder).mTitleName.setText(" ");
                    ((WelfareViewHolder) holder).mTitleName.setVisibility(GONE);
                } else {
                    ((WelfareViewHolder) holder).mTitleName.setVisibility(View.VISIBLE);
                    ((WelfareViewHolder) holder).mTitleName.setText(entity.getPublishedAt().replace("T", " ").replace("Z", " "));
                }

                ((WelfareViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(imageUrl)) {
                            Intent intent = new Intent(context, ScaleImageActivity.class);
                            intent.putExtra(Urls.GANK_URL.BUNDLE_KEY, imageUrl);

                            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((HomeActivity) context,
                                    ((WelfareViewHolder) holder).mWelfareImage, context.getResources().getString(R.string.translation_element_name));
                            ActivityCompat.startActivity(context, intent, options.toBundle());
                        }
                    }
                });
            }
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (isScrollToBottom) {
            if (isLoadingError) {
                return datas.size();
            } else {
                return datas.size() + 1;
            }
        }

        return datas.size();
    }

    @NonNull
    @Override
    public int getItemViewType(int position) {
        // 添加 加载更多 项
        if (position + 1 == getItemCount()) {
            return VIEW_TYPE_FOOTER;
        }

        if (!TextUtils.isEmpty(resourceType) && resourceType.equals(Urls.GANK_URL.ALL)) {// 推荐分类：混合排版
            // 有图片的项
            if (datas.get(position).getImages() != null
                    && datas.get(position).getImages().size() > 0) {
                return VIEW_TYPE_NORMAL;
            } else {// 没有图片
                return VIEW_TYPE_NO_PIC;
            }
        } else if (!TextUtils.isEmpty(resourceType) && resourceType.equals(Urls.GANK_URL.WELFARE)) {// 福利分类：图片瀑布流
            return VIEW_TYPE_WELFARE;
        } else {// 其他类别根据设置排版
            if (Configuration.newInstance().isCardMode()) {
                return VIEW_TYPE_NO_PIC;
            } else {
                return VIEW_TYPE_NORMAL;
            }
        }
    }

    @NonNull
    @Override
    public GankEntity.ResultsBean getItem(int position) {
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
        List<GankEntity.ResultsBean> resultsBeanList = DataUtil.removeDuplicateData(this.datas, datas);
        if (resultsBeanList != null && resultsBeanList.size() > 0) {
            for (int i = (resultsBeanList.size() - 1); i >= 0; i--) {
                this.datas.add(0, resultsBeanList.get(i));
                notifyItemInserted(0);
                notifyItemChanged(0);
            }
        }
    }

    @Override
    public void addLoadMoreData(List<GankEntity.ResultsBean> datas) {
        List<GankEntity.ResultsBean> resultsBeanList = DataUtil.getExtraDataWithNoDuplicated(this.datas, datas);
        if (resultsBeanList != null && resultsBeanList.size() > 0) {
            int oldSize = this.datas.size();
            for (int i = 0; i < resultsBeanList.size(); i++) {
                this.datas.add(resultsBeanList.get(i));
                notifyItemInserted(oldSize + i);
                notifyItemChanged(oldSize + i);
            }
        }
    }

    @Override
    public boolean isScrolledToBottom() {
        return isScrollToBottom;
    }

    @Override
    public void setScrollToBottom(boolean isBottom) {
        Logger.d(TAG, "setScrollToBottom : " + isBottom);
        isScrollToBottom = isBottom;
    }

    @Override
    public void setLoadingError(boolean isLoadingError) {
        Logger.d(TAG, "setLoadingError : " + isLoadingError);
        this.isLoadingError = isLoadingError;
    }

    @Override
    public void notifyByThemeChanged() {
        List<GankEntity.ResultsBean> tampDatas = copy(datas);
        for (int i = 0; i < tampDatas.size(); i++) {
            this.datas.remove(0);
            notifyItemRemoved(0);
        }
        for (int i = 0; i < tampDatas.size(); i++) {
            this.datas.add(tampDatas.get(i));
            notifyItemInserted(datas.size() - 1);
            notifyItemChanged(datas.size() - 1);
        }
    }

    private List<GankEntity.ResultsBean> copy(List<GankEntity.ResultsBean> oldDatas) {
        List<GankEntity.ResultsBean> tampDatas = new ArrayList<>();
        Iterator<GankEntity.ResultsBean> beanIterator = oldDatas.iterator();
        while (beanIterator.hasNext()) {
            tampDatas.add(beanIterator.next());
        }
        return tampDatas;
    }

}
