package com.hiveview.cloudscreen.vipvideo.activity.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.ContentShowType;
import com.hiveview.cloudscreen.vipvideo.common.SizeConstant;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;
import com.hiveview.cloudscreen.vipvideo.service.CloudScreenService;
import com.hiveview.cloudscreen.vipvideo.service.OnRequestResultListener;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.AppStoreEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.RecordEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.VideoRecordEntity;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.view.EffectiveMarqueeTextView;
import com.hiveview.cloudscreen.vipvideo.view.HistoryView;
import com.hiveview.cloudscreen.vipvideo.view.HorizontalListView;

import java.util.List;


/**
 * @author xieyi
 * @ClassName RecordUpAdapter
 * @Description 历史界面item上层滚动视图
 * @date 2014-9-26 下午12:15:24
 */
public class RecordUpAdapter extends BaseAdapter {

    protected static final String TAG = "RecordUpAdapter";
    private HorizontalListView listView;
    private HistoryView historyView;
    private Context ctx;
    private LayoutInflater inflater;
    private int parentPosition;
    private List<VideoRecordEntity> entitys;
    private RecordEntity recordEntity;
    private boolean isDelete;
    private boolean isHasDownView = true;
    private int startX;

    private SizeConstant sizeConstant;
    private DeviceInfoUtil.DeviceInfo info;

    public void setHasDownView(boolean isHasDownView) {
        this.isHasDownView = isHasDownView;
    }

    public RecordUpAdapter(Context context, HistoryView historyView, HorizontalListView listView, int parentPosition, boolean isDelete,
                           ClickCallBack callBack, RecordEntity recordEntity, SizeConstant sizeConstant) {
        this.ctx = context;
        info = DeviceInfoUtil.getInstance().getDeviceInfo(CloudScreenApplication.getInstance().getApplicationContext());
        this.historyView = historyView;
        this.listView = listView;
        this.parentPosition = parentPosition;
        this.isDelete = isDelete;
        this.callBack = callBack;
        this.recordEntity = recordEntity;
        this.sizeConstant = sizeConstant;
        inflater = LayoutInflater.from(ctx);
        startX = (int) ctx.getResources().getDimensionPixelSize(R.dimen.recordUpAdapter_startX);// 209
    }

    public void setData(List<VideoRecordEntity> entitys) {
        this.entitys = entitys;
    }

    public void remove(VideoRecordEntity entity) {
        entitys.remove(entity);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return entitys.size();
    }

    @Override
    public Object getItem(int position) {
        return entitys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "[getView]");
        View view = null;
        VideoRecordHolder holder = null;
        if (null == convertView) {
            holder = new VideoRecordHolder();
            view = inflater.inflate(R.layout.record_uplist_item_layout, null);
            holder.history_list1_bg = (ImageView) view.findViewById(R.id.history_list1_bg);
            holder.history_corner = (ImageView) view.findViewById(R.id.history_corner);
            holder.delete = (ImageView) view.findViewById(R.id.delete);
            holder.history_nopress = (ImageView) view.findViewById(R.id.history_nopress);
            holder.history_press = (ImageView) view.findViewById(R.id.history_press);
            holder.history_name = (EffectiveMarqueeTextView) view.findViewById(R.id.history_name);
            holder.history_tip = (EffectiveMarqueeTextView) view.findViewById(R.id.history_tip);
            view.setTag(holder);
        } else {
            view = convertView;
        }
        holder = (VideoRecordHolder) view.getTag();
        holder.position = position;
        final VideoRecordEntity entity = entitys.get(position);
        // 是否删除模式
        if (isDelete && entity.getSource() != 2) {
            holder.delete.setVisibility(View.VISIBLE);
        }

        //加载图片
        String image = "";
        if (!TextUtils.isEmpty(entity.getPicUrl())) {
            image = entity.getPicUrl();
        }
        String urlSource = Uri.parse(image).getQueryParameter("source");
        if (!TextUtils.isEmpty(urlSource)) {
            if ("Qiyi".equals(urlSource)) {//如果是爱奇艺的片源才需要增加尺寸后缀
                image = DisplayImageUtil.createImgUrl(image, true);
            }
        }
        holder.history_list1_bg.setImageResource(ResourceProvider.getInstance().getD260360());
        DisplayImageUtil.getInstance().setRound((int) ctx.getResources().getDimension(R.dimen.recommendview_cover_round)).setDuration(200).setLoadingImage(ResourceProvider.getInstance().getD260360()).setErrorImage(ResourceProvider.getInstance().getD260360()).setEmptyUriImage(ResourceProvider.getInstance().getD260360())
                .displayImage(image, holder.history_list1_bg);

        final VideoRecordHolder finalHolder = holder;
        CloudScreenService.getInstance().getFilmDetail(new OnRequestResultListener<ResultEntity<AlbumEntity>>() {

            @Override
            public void onSucess(ResultEntity<AlbumEntity> ResultEntity) {
                AlbumEntity albumEntity = (AlbumEntity) ResultEntity.getEntity();
                if (null != albumEntity) {
                    Log.d("4test", "corner=" + (null != albumEntity.getCornerImage() ? albumEntity.getCornerImage() : "null"));
                    if (null != albumEntity.getCornerImage()) {
                        DisplayImageUtil.getInstance().displayImage(albumEntity.getCornerImage(), finalHolder.history_corner);
                    } else {
                        finalHolder.history_corner.setImageBitmap(null);
                    }
                }
                int showType = CloudScreenApplication.getInstance().getShowTypeByVideoTypeId(albumEntity.getChnId());
                if (showType == ContentShowType.TYPE_SINGLE_VIDEO_NO_DETAIL && "1".equals(albumEntity.getAlbumType())) {
                    if (albumEntity.getStype() >= 1) {
                        finalHolder.history_tip.setText(String.format(ctx.getResources().getString(R.string.history_record_total_phase), entity.getCurrentEpisode().split("\\|")[0]));
                    } else {
                        finalHolder.history_tip.setText(String.format(ctx.getResources().getString(R.string.history_record_total), entity.getCurrentEpisode().split("\\|")[0]));
                    }

                }
            }

            @Override
            public void onFail(Exception e) {

            }

            @Override
            public void onParseFail(HiveviewException e) {

            }
        }, info.templetId, entity.getVideoset_id());

        //加载内容
        holder.history_name.setText(TextUtils.isEmpty(entity.getMovieName()) ? ctx.getText(R.string.server_no_data) : entity.getMovieName());
        if (!TextUtils.isEmpty(entity.getCurrentEpisode())) {
            int showType = CloudScreenApplication.getInstance().getShowTypeByVideoTypeId(entity.getVideoset_type());
            if (showType == ContentShowType.TYPE_MULTIPLE_VIDEO_DETAIL) {//电视剧
                holder.history_tip.setText(String.format(ctx.getResources().getString(R.string.history_record_total), entity.getCurrentEpisode().split("\\|")[0]));
            } else if (showType == ContentShowType.TYPE_VARIETY_VIDEO_DETAIL) {//综艺
                holder.history_tip.setText(String.format(ctx.getResources().getString(R.string.history_record_total_phase), entity.getCurrentEpisode().split("\\|")[0]));
            } else {
                holder.history_tip.setText("");
            }
        } else {
            holder.history_tip.setText("");
        }
        view.setOnFocusChangeListener(focusChange);
        view.setOnKeyListener(keyListener);
        view.setOnClickListener(onClick);
        return view;
    }

    private OnFocusChangeListener focusChange = new OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            VideoRecordHolder holder = (VideoRecordHolder) v.getTag();
            if (hasFocus) {
                focusChanged(holder, hasFocus);
                int[] location = new int[2];
                v.getLocationInWindow(location);
                // 屏幕最右边左移
                if (location[0] + v.getWidth() >= sizeConstant.getBoxWidth()) {
                    listView.scrollTo(location[0] + v.getWidth() - sizeConstant.getBoxWidth()
                            + (int) ctx.getResources().getDimension(R.dimen.recordUpAdapter_onFocusListener_scrollTo) + listView.getmNextX());
                }
                // 屏幕最左边右移
                else if (location[0] <= startX) {
                    listView.scrollTo(location[0] - startX - (int) ctx.getResources().getDimension(R.dimen.recordUpAdapter_onFocusListener_scrollTo)
                            + listView.getmNextX());
                }
            } else {
                focusChanged(holder, hasFocus);
            }
        }
    };

    @SuppressLint("NewApi")
    private void focusChanged(VideoRecordHolder holder, boolean hasFocus) {
        if (hasFocus) {
            holder.history_name.setTextColor(Color.WHITE);
            holder.history_tip.setTextColor(Color.WHITE);
            ObjectAnimator focus_zero = ObjectAnimator.ofFloat(holder.history_nopress, "alpha", 0).setDuration(200);
            ObjectAnimator focus_one = ObjectAnimator.ofFloat(holder.history_press, "alpha", 1).setDuration(200);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(focus_zero, focus_one);
            set.start();
            holder.history_name.setIsInFocusView(true);
            holder.history_tip.setIsInFocusView(true);
        } else {
            holder.history_name.setTextColor(Color.rgb(100, 100, 100));
            holder.history_tip.setTextColor(Color.rgb(100, 100, 100));
            ObjectAnimator focus_zero = ObjectAnimator.ofFloat(holder.history_nopress, "alpha", 1).setDuration(200);
            ObjectAnimator focus_one = ObjectAnimator.ofFloat(holder.history_press, "alpha", 0).setDuration(200);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(focus_zero, focus_one);
            set.start();
            holder.history_name.setIsInFocusView(false);
            holder.history_tip.setIsInFocusView(false);
        }
    }

    private OnKeyListener keyListener = new OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (!listView.getmScroller().isFinished() || !historyView.getmScroller().isFinished())
                return true;
            VideoRecordHolder holder = (VideoRecordHolder) v.getTag();
            if (KeyEvent.ACTION_DOWN == event.getAction()) {
                switch (event.getKeyCode()) {
                    case KeyEvent.KEYCODE_DPAD_UP:
                        View parentView = historyView.getChildAt(0);// 通过listview取到页面第一个显示的视图
                        RecordAdapter.RecordHolder holder1 = (RecordAdapter.RecordHolder) parentView.getTag();// 取到标记
                        if (holder1.position != parentPosition) {// 判断标记，当前焦点处是不是屏幕中第一个item
                            int[] location = new int[2];
                            parentView.getLocationInWindow(location);
                            if (holder1.position != 1) {// 不是第一条，目前仅在最底部时触发一次
                                historyView.scrollTo(location[1] + historyView.getmNextY(), 0, parentPosition);
                            } else {// 此时重置到页面顶部，程序正常时此处不触发
                                historyView.scrollTo(location[1] + parentView.getHeight() - sizeConstant.getBoxHeight() + historyView.getmNextY(), 0,
                                        parentPosition);
                            }
                        } else {// 大部分情况会保证焦点在屏幕中第一个item上
                            if (parentPosition == 2) {// 从第二项向上移动时直接回到顶部
                                historyView.scrollTo(-sizeConstant.getBoxHeight() + historyView.getmNextY(), 1, parentPosition);
                            } else {
                                RecordEntity entity = (RecordEntity) historyView.getItemAtPosition(parentPosition - 1);
                                if (null != entity.getMovies() && null != entity.getAppStores() && entity.getMovies().size() != 0
                                        && entity.getAppStores().size() != 0) {
                                    // 影片与游戏数据都存在的时候Item高度为888px
                                    historyView.scrollTo(
                                            -(int) ctx.getResources().getDimension(R.dimen.history_view_scroll_item_all) + historyView.getmNextY(), 1,
                                            parentPosition);
                                } else {
                                    // 只存在1项的时候Item高度为552px
                                    historyView.scrollTo(
                                            -(int) ctx.getResources().getDimension(R.dimen.history_view_scroll_item_one) + historyView.getmNextY(), 1,
                                            parentPosition);
                                }
                            }
                        }
                        break;

                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        View parentView1 = historyView.getChildAt(1);
                        RecordAdapter.RecordHolder holder2 = (RecordAdapter.RecordHolder) parentView1.getTag();
                        if (!isHasDownView) {//不存在应用部分
                            if (holder2.position == parentPosition) {
                                historyView.scrollTo(historyView.getChildAt(0).getHeight() + parentView1.getHeight() + historyView.getmNextY(), 2,
                                        parentPosition);
                            } else {
                                historyView.scrollTo(historyView.getChildAt(0).getHeight() + historyView.getmNextY(), 0, parentPosition);
                            }
                        } else {//存在应用部分
                            if (holder2.position == 1) {//仅当列表在底部时会触发，向下移动掉头信息部分
                                historyView.scrollTo(historyView.getChildAt(0).getHeight() + historyView.getmNextY(), 0, parentPosition);
                            }
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        if (holder.position == 0)
                            return true;
                        break;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        if (holder.position == entitys.size() - 1)
                            return true;
                        break;
                }
            }
            return false;
        }
    };

    private OnClickListener onClick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            VideoRecordHolder holder = (VideoRecordHolder) v.getTag();
            VideoRecordEntity entity = entitys.get(holder.position);
            int source = entity.getSource();
            if (!isDelete) {
                callBack.qiyi(entity);
            } else {
                entitys.remove(entity);
                recordEntity.getMovies().remove(entity);
                notifyDataSetChanged();
                callBack.deleteQiyi(entity, recordEntity);

            }
        }
    };

    public class VideoRecordHolder {
        ImageView history_list1_bg;
        ImageView delete;
        ImageView history_nopress;
        ImageView history_press;
        ImageView history_corner;
        public EffectiveMarqueeTextView history_name;
        public EffectiveMarqueeTextView history_tip;
        int position;
    }

    private ClickCallBack callBack;

    public interface ClickCallBack {
        void qiyi(VideoRecordEntity entity);

        void appGame(AppStoreEntity entity);

        void deleteQiyi(VideoRecordEntity entity, RecordEntity recordEntity);

        void deleteApp(AppStoreEntity entity, RecordEntity recordEntity);
    }
}
