/**
 * @Title FilmListAdapter.java
 * @Package com.hiveview.cloudscreen.video.activity.adapter
 * @author haozening
 * @date 2015-5-4 下午2:20:34
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.VideoDetailInvoker;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResArea;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.Statistics;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.ListViewActionInfo;
import com.hiveview.cloudscreen.vipvideo.util.BitmapBlurUtil;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.util.Logger;
import com.hiveview.cloudscreen.vipvideo.view.MarqueeTextView;
import com.hiveview.cloudscreen.vipvideo.view.pageItemView.VideoPageItemView;
import com.hiveview.cloudscreen.vipvideo.view.verticalViewPager.VerticalPagerAdapter;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author haozening
 * @ClassName FilmListAdapter
 * @Description
 * @date 2015-5-4 下午2:20:34
 */
public class VideoListAdapter extends VerticalPagerAdapter implements OnClickListener {

    /**
     * FilmListAdapter
     */
    private static final String TAG = VideoListAdapter.class.getSimpleName();
    private List<AlbumEntity> entities = new ArrayList<AlbumEntity>();
    private VideoPageItemView.OnItemKeyListener itemKeyListener;
    private View mCurrentView;
    private final int COVER_ROUND;
    private Context context;
    //设置海外特例频道，这些频道的图片需要特殊处理
    private List<Integer> extraCids = new ArrayList<>();


    private int itemPosition = VerticalPagerAdapter.POSITION_UNCHANGED;

    public VideoListAdapter(VideoPageItemView.OnItemKeyListener listener, Context context) {
        this.context = context;
        itemKeyListener = listener;
        COVER_ROUND = (int) CloudScreenApplication.getInstance().getResources().getDimensionPixelSize(R.dimen.six_column_item_image_cover_round);
        //设置海外特例频道，这些频道的图片需要特殊处理
        extraCids.add(10);
        extraCids.add(15);
    }

    public void clear() {
        entities.clear();
        notifyDataSetChanged();
    }

    public void setData(List<AlbumEntity> entities) {
        if (null != entities) {
            this.entities = entities;
        }
    }

    public void addData(List<AlbumEntity> entities) {
        if (null != entities) {
            this.entities.addAll(entities);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return entities.size() / 12 + (entities.size() % 12 == 0 ? 0 : 1);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        VideoPageItemView view = (VideoPageItemView) object;
        for (int i = position * 12, j = 0; i < (position + 1) * 12; i++, j++) {
            View v = view.getSubviewAt(j);
            if (entities.size() > i) {
                BitmapBlurUtil.getInstance().cancelDisplayTask(view.getImageView(v));
                BitmapBlurUtil.getInstance().cancelDisplayTask(view.getCorner(v));
            }
        }
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        VideoPageItemView view = new VideoPageItemView(CloudScreenApplication.getInstance());
        container.addView(view);
        view.setOnItemKeyListener(itemKeyListener);
        setViewData(view, position);
        return view;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentView = (View) object;
    }

    public View getPrimaryItem() {
        return mCurrentView;
    }

    private void setViewData(VideoPageItemView view, int position) {
        for (int i = position * 12, j = 0; i < (position + 1) * 12; i++, j++) {
            View v = view.getSubviewAt(j);
            if (entities.size() > i) {
                inflateData(view, v, entities.get(i), i);
            } else {
                hideView(v);
            }
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    private void inflateData(VideoPageItemView view, View v, final AlbumEntity entity, int itemPosition) {
        v.setTag(R.id.tag_listview_entity, entity);
        v.setTag(R.id.tag_listview_item_position, itemPosition);
        v.setOnClickListener(this);
        v.setVisibility(View.VISIBLE);
        final ImageView cover = view.getImageView(v);
        ImageView corner = view.getCorner(v);
        MarqueeTextView title = view.getTitleTextView(v);
        MarqueeTextView descript = view.getDescriptView(v);
        TextView issueTime = view.getIssueTimeView(v);
        TextView timeLength = view.getTimeView(v);

        if (null != entity.getCornerImage()) {
            DisplayImageUtil.getInstance().displayImage(entity.getCornerImage(), corner);
        }

        String image = entity.getAlbumHbPic();
        if (ResourceProvider.getInstance().getResArea() == ResArea.US && extraCids.contains(entity.getChnId())) {
            //用于处理海外10、15 2个频道的特殊情况，先尝试getPicUrl图片，加载失败后尝试getBlueRayImg图片
            image = entity.getAlbumXqyPic();
            if (null != image && !"null".equals(image)) {
                String urlSource = Uri.parse(image).getQueryParameter("source");
                if (urlSource.equals("Qiyi")) {//如果是爱奇艺的片源才需要增加尺寸后缀
                    image = DisplayImageUtil.createImgUrl(image, false);
                }
            }
            DisplayImageUtil.getInstance().setRound(COVER_ROUND).setEmptyUriImage(ResourceProvider.getInstance().getDefaultCoverListLandscape())
                    .setErrorImage(ResourceProvider.getInstance().getDefaultCoverListLandscape()).setLoadingImage(ResourceProvider.getInstance().getDefaultCoverListLandscape())
                    .displayImage(image, cover, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            Logger.d(TAG, "onLoadingFailed imageUri=" + imageUri);
                            super.onLoadingFailed(imageUri, view, failReason);
                            String image = entity.getAlbumHbPic();
                            if (null != image && !"null".equals(image)) {
                                String urlSource = Uri.parse(image).getQueryParameter("source");
                                if (urlSource.equals("Qiyi")) {//如果是爱奇艺的片源才需要增加尺寸后缀
                                    image = DisplayImageUtil.createImgUrl(image, false);
                                }
                            }
                            Logger.d(TAG, " new image=" + image);
                            DisplayImageUtil.getInstance().setRound(COVER_ROUND).setEmptyUriImage(ResourceProvider.getInstance().getDefaultCoverListLandscape())
                                    .setErrorImage(ResourceProvider.getInstance().getDefaultCoverListLandscape()).setLoadingImage(ResourceProvider.getInstance().getDefaultCoverListLandscape())
                                    .displayImage(image, cover);
                        }
                    });
        } else {
            if (null != image && !"null".equals(image)) {
                String urlSource = Uri.parse(image).getQueryParameter("source");
                if (urlSource.equals("Qiyi")) {//如果是爱奇艺的片源才需要增加尺寸后缀
                    image = DisplayImageUtil.createImgUrl(image, false);
                }
            }
            DisplayImageUtil.getInstance().setRound(COVER_ROUND).setEmptyUriImage(ResourceProvider.getInstance().getDefaultCoverListLandscape())
                    .setErrorImage(ResourceProvider.getInstance().getDefaultCoverListLandscape()).setLoadingImage(ResourceProvider.getInstance().getDefaultCoverListLandscape())
                    .displayImage(image, cover);
        }

        title.setText(entity.getAlbumName());
        descript.setText(entity.getFocus());
        issueTime.setText(formatTime(entity.getIssueTime()));
        timeLength.setText(getDate(entity.getDuration()));
    }

    private void hideView(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    private String formatTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        SimpleDateFormat result = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date date = format.parse(time);
            return result.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getDate(String timeStr) {
        int timeLength = 0;
        if (!TextUtils.isEmpty(timeStr)) {
            timeLength = Integer.valueOf(timeStr);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.SECOND, timeLength);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        String hourStr = "";
        String minuteStr = "";
        String secondStr = "";
        if (hour > 0) {
            hourStr = hour + ":";
        }
        if (minute < 10) {
            minuteStr = "0" + minute + ":";
        } else {
            minuteStr = minute + ":";
        }
        if (second < 10) {
            secondStr = "0" + second;
        } else {
            secondStr = second + "";
        }
        return hourStr + minuteStr + secondStr;
    }

    @Override
    public void onClick(View v) {
        AlbumEntity entity = (AlbumEntity) v.getTag(R.id.tag_listview_entity);
        int itemPosition = (int) v.getTag(R.id.tag_listview_item_position);

        if (itemPosition < 20) {
            BaseActionInfo info = new ListViewActionInfo("03", "sde0302", entity.getChnId() + "", "3", null, (itemPosition + 1) + "");//统计埋点
            new Statistics(context, info).send();//统计埋点
        }
        if (null != onActionListener) {
            onActionListener.effectiveAction();
        }

        Intent intent = new Intent(VideoDetailInvoker.ACTION_PLAYER);
        intent.putExtra(AppConstants.EXTRA_ALBUM_ENTITY, JSONObject.toJSONString(entity));
        intent.putExtra(AppConstants.EXTRA_SOURCE, "7");
        context.startActivity(intent);
    }

    private OnActionListener onActionListener;

    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
    }

    public interface OnActionListener {
        void effectiveAction();
    }

}
