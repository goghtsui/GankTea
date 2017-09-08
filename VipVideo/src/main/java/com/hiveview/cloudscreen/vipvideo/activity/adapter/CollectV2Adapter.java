package com.hiveview.cloudscreen.vipvideo.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.activity.VipVideoDetailActivity;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.ContentShowType;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;
import com.hiveview.cloudscreen.vipvideo.service.CloudScreenService;
import com.hiveview.cloudscreen.vipvideo.service.OnRequestResultListener;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.CollectEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.view.EffectiveMarqueeTextView;
import com.hiveview.cloudscreen.vipvideo.view.TypeFaceTextView;

import java.util.List;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/5/18
 * @Description
 */
public class CollectV2Adapter extends RecyclerView.Adapter<CollectV2Adapter.CollectViewHolder> {

    private static final String TAG = CollectV2Adapter.class.getSimpleName();
    /**
     * 数据
     */
    private List<CollectEntity> data;

    /**
     * 上下文
     */
    private Context mContext;


    /**
     * 是否删除模式
     */
    private boolean isDelete;
    /**
     * 事件监听
     */
    private OnActionListener listener;
    private DeviceInfoUtil.DeviceInfo info;

    public CollectV2Adapter(Context context, OnActionListener listener) {
        mContext = context;
        this.listener = listener;
        info = DeviceInfoUtil.getInstance().getDeviceInfo(CloudScreenApplication.getInstance().getApplicationContext());

    }

    /**
     * @Author Spr_ypt
     * @Date 2016/6/8
     * @Description 设置数据
     */
    public void setData(List<CollectEntity> data) {
        this.data = data;
    }


    /**
     * @Author Spr_ypt
     * @Date 2016/6/8
     * @Description 设置/取消删除模式
     */
    public void setMode(boolean isDelete) {
        this.isDelete = isDelete;
        notifyDataSetChanged();
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/6/8
     * @Description 获取当前的删除/正常模式
     */
    public boolean getMode() {
        return isDelete;
    }

    @Override
    public CollectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout itemView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.collect_item_layout, null);
        CollectViewHolder holder = new CollectViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CollectViewHolder holder, int position) {
        CollectEntity collectEntity = data.get(position);
        holder.itemView.setTag(R.id.tag_listview_entity, collectEntity);
        holder.itemView.setTag(R.id.tag_listview_item_position, position);
        if (isDelete) {
            holder.delete.setVisibility(View.VISIBLE);
        } else {
            holder.delete.setVisibility(View.INVISIBLE);
        }
        //加载图片
        String image = "";
        if (!TextUtils.isEmpty(collectEntity.getPicUrl())) {
            image = collectEntity.getPicUrl();
        }
        String urlSource = Uri.parse(image).getQueryParameter("source");
        if ("Qiyi".equals(urlSource)) {//如果是爱奇艺的片源才需要增加尺寸后缀
            image = DisplayImageUtil.createImgUrl(image, true);
        }
        holder.collect_item_bg.setImageResource(ResourceProvider.getInstance().getD260360());
        DisplayImageUtil.getInstance().setRound((int) mContext.getResources().getDimension(R.dimen.recommendview_cover_round)).setErrorImage(ResourceProvider.getInstance().getD260360()).setLoadingImage(ResourceProvider.getInstance().getD260360()).setEmptyUriImage(ResourceProvider.getInstance().getD260360()).setDuration(200).displayImage(image, holder.collect_item_bg);

        //加载内容
        holder.item_text2.setText(TextUtils.isEmpty(collectEntity.getName()) ? "" : collectEntity.getName());
        holder.item_text3.setText(collectEntity.getCollectTime() == 0 ? "" : collectEntity.getSimpleCollectTime());
        int videosetType = 00000;
        videosetType = CloudScreenApplication.getInstance().getShowTypeByVideoTypeId(collectEntity.getCid());
        if (videosetType == ContentShowType.TYPE_VARIETY_VIDEO_DETAIL) {// 综艺节目
            //获取更新期数与角标
            CloudScreenService.getInstance().getFilmDetail(new OnRequestResultListener<ResultEntity<AlbumEntity>>() {

                @Override
                public void onSucess(ResultEntity<AlbumEntity> ResultEntity) {
                    AlbumEntity entity = (AlbumEntity) ResultEntity.getEntity();
                    if (null != entity) {
                        holder.item_text4.setText(String.format(mContext.getString(R.string.update_expect), entity.getYear() + ""));
                        if (null != entity.getCornerImage()) {
                            DisplayImageUtil.getInstance().displayImage(entity.getCornerImage(), holder.collect_item_corner);
                        } else {
                            holder.collect_item_corner.setImageDrawable(null);
                        }
                    }
                }

                @Override
                public void onFail(Exception e) {

                }

                @Override
                public void onParseFail(HiveviewException e) {

                }
            }, info.templetId, collectEntity.getCollectId());
        } else if (videosetType == ContentShowType.TYPE_MULTIPLE_VIDEO_DETAIL || videosetType == ContentShowType.TYPE_SINGLE_VIDEO_DETAIL) {// 电视剧动漫
            //获取更新集数与角标
            CloudScreenService.getInstance().getFilmDetail(new OnRequestResultListener<ResultEntity<AlbumEntity>>() {

                @Override
                public void onSucess(ResultEntity<AlbumEntity> ResultEntity) {
                    AlbumEntity entity = (AlbumEntity) ResultEntity.getEntity();
                    if (null != entity) {
                        if (entity.getEpisodeUpdated() != 0) {
                            if (entity.getEpisodeUpdated() == entity.getEpisodeTotal()) {
                                holder.item_text4.setText(mContext.getString(R.string.collect_complete_works));
                            } else {
                                holder.item_text4.setText(String.format(mContext.getString(R.string.update_videoset), "" + entity.getEpisodeUpdated()));
                            }
                        } else {
                            holder.item_text4.setText("");
                        }
                        if (null != entity.getCornerImage()) {
                            DisplayImageUtil.getInstance().displayImage(entity.getCornerImage(), holder.collect_item_corner);
                        } else {
                            holder.collect_item_corner.setImageDrawable(null);
                        }
                    }
                }

                @Override
                public void onFail(Exception e) {

                }

                @Override
                public void onParseFail(HiveviewException e) {

                }
            }, info.templetId, collectEntity.getCollectId());
        } else {
            holder.item_text4.setText("");
        }

        //设置按键监听
        holder.itemView.setOnClickListener(click);
        holder.itemView.setOnKeyListener(keyListener);
    }


    @Override
    public int getItemCount() {
        if (null != data) {
            return data.size();
        } else {
            return 0;
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/18
     * @Description item的点击事件
     */
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CollectEntity entity = (CollectEntity) v.getTag(R.id.tag_listview_entity);
            int position = (int) v.getTag(R.id.tag_listview_item_position);
            if (isDelete) {
                int index = data.indexOf(entity);
                data.remove(entity);
                notifyItemRemoved(index);
                if (data.size() > 0) {
                    if (null != listener) {
                        listener.removeItem(entity);
                    }
                } else {
                    if (null != listener) {
                        listener.removeAll();
                    }
                }
            } else {
                if (null != listener) {
                    listener.openDetail(entity);
                }
            }
        }
    };

    /**
     * @Author Spr_ypt
     * @Date 2016/6/8
     * @Description item按键事件
     */
    private View.OnKeyListener keyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                    CollectEntity entity = (CollectEntity) v.getTag(R.id.tag_listview_entity);
                    int index = data.indexOf(entity);
                    if (data.size() - index <= 6 && data.size() % 6 > 0 && data.size() % 6 <= index % 6) {
                        if (null != listener) {
                            listener.needScrollToEnd();
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    };


    /**
     * @Author Spr_ypt
     * @Date 2016/5/20
     * @Description ViewHolder缓存类
     */
    public class CollectViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        /**
         * 焦点图
         */
        View collect_focus;
        /**
         * 背景图
         */
        ImageView collect_item_bg;
        /**
         * 角标
         */
        ImageView collect_item_corner;
        /**
         * 删除图标
         */
        ImageView delete;
        /**
         * 无焦点时的底部蒙版
         */
        ImageView collect_nopress;
        /**
         * 有焦点时的底部蒙版
         */
        ImageView collect_press;
        /**
         * 影片名称文本框
         */
        public EffectiveMarqueeTextView item_text2;
        /**
         * 收藏时间文本框
         */
        TypeFaceTextView item_text3;
        /**
         * 更新集数文本框
         */
        TypeFaceTextView item_text4;

        public CollectViewHolder(View itemView) {
            super(itemView);
            init(itemView);
            setPreListener(itemView);
        }

        /**
         * @Author Spr_ypt
         * @Date 2016/5/18
         * @Description 预设监听，设置不需要等待数据加载的监听
         */
        private void setPreListener(final View itemView) {
            itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        collect_focus.setVisibility(View.VISIBLE);
                        item_text2.setTextColor(Color.WHITE);
                        item_text3.setTextColor(Color.WHITE);
                        item_text4.setTextColor(Color.WHITE);
                        collect_nopress.setVisibility(View.INVISIBLE);
                        collect_press.setVisibility(View.VISIBLE);
                        item_text2.setIsInFocusView(true);
                        itemView.animate().scaleX(1.05f).scaleY(1.05f).setDuration(200).setInterpolator(new DecelerateInterpolator());
                    } else {
                        collect_focus.setVisibility(View.INVISIBLE);
                        item_text2.setTextColor(Color.rgb(100, 100, 100));
                        item_text3.setTextColor(Color.rgb(100, 100, 100));
                        item_text4.setTextColor(Color.rgb(100, 100, 100));
                        collect_nopress.setVisibility(View.VISIBLE);
                        collect_press.setVisibility(View.INVISIBLE);
                        item_text2.setIsInFocusView(false);
                        itemView.animate().scaleX(1.00f).scaleY(1.00f).setDuration(200).setInterpolator(new DecelerateInterpolator());
                    }
                }
            });
        }

        /**
         * @Author Spr_ypt
         * @Date 2016/5/18
         * @Description 加载控件
         */
        private void init(View itemView) {
            this.itemView = itemView;
            collect_focus = (View) itemView.findViewById(R.id.collect_focus);
            collect_item_bg = (ImageView) itemView.findViewById(R.id.collect_item_bg);
            collect_item_corner = (ImageView) itemView.findViewById(R.id.collect_item_corner);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            collect_nopress = (ImageView) itemView.findViewById(R.id.collect_nopress);
            collect_press = (ImageView) itemView.findViewById(R.id.collect_press);
            item_text2 = (EffectiveMarqueeTextView) itemView.findViewById(R.id.item_text2);
            item_text3 = (TypeFaceTextView) itemView.findViewById(R.id.item_text3);
            item_text4 = (TypeFaceTextView) itemView.findViewById(R.id.item_text4);
        }
    }


    /**
     * @Author Spr_ypt
     * @Date 2016/6/8
     * @Description 集成在adapter内的数据操作回调接口
     */
    public interface OnActionListener {
        /**
         * 打开详情页
         *
         * @param entity 被打开的实体类
         */
        void openDetail(CollectEntity entity);

        /**
         * 移除当前实体
         *
         * @param entity 被移除的实体
         */
        void removeItem(CollectEntity entity);

        /**
         * 移除所有实体
         */
        void removeAll();

        /**
         * 请将焦点移动到最后
         */
        void needScrollToEnd();
    }
}
