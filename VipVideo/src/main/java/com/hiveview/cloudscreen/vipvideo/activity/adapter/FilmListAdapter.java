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
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.ContentShowType;
import com.hiveview.cloudscreen.vipvideo.common.VideoDetailInvoker;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResArea;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.Statistics;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.ListViewActionInfo;
import com.hiveview.cloudscreen.vipvideo.util.BitmapBlurUtil;
import com.hiveview.cloudscreen.vipvideo.util.CenterImageSpan;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.util.StringUtils;
import com.hiveview.cloudscreen.vipvideo.util.ToastUtil;
import com.hiveview.cloudscreen.vipvideo.view.MarqueeTextView;
import com.hiveview.cloudscreen.vipvideo.view.pageItemView.FilmPageItemView;

import java.io.Serializable;

/**
 * @author haozening
 * @ClassName FilmListAdapter
 * @Description
 * @date 2015-5-4 下午2:20:34
 */
public class FilmListAdapter extends BaseFilmListAdapter<AlbumEntity> {


    public FilmListAdapter(FilmPageItemView.OnItemKeyListener listener, FilmPageItemView.OnItemSelectedListener itemSelectedListener, Context context) {
        super(listener, itemSelectedListener, context);
    }

    @Override
    public void clear() {
        entities.clear();
        notifyDataSetChanged();
    }

    @Override
    protected void inflateData(FilmPageItemView view, View v, AlbumEntity entity, int itemPosition) {
        v.setTag(R.id.tag_listview_entity, entity);
        v.setTag(R.id.tag_listview_item_position, itemPosition);
        v.setOnClickListener(this);
        v.setVisibility(View.VISIBLE);
        ImageView cover = view.getImageView(v);
        ImageView corner = view.getCornerView(v);
        MarqueeTextView title = view.getTitleTextView(v);
        TextView score = view.getScoreTextView(v);

        if (null != entity.getCornerImage()) {
            DisplayImageUtil.getInstance().displayImage(entity.getCornerImage(), corner);
        }

        String image = entity.getAlbumXqyPic();
        if (null != image && !"null".equals(image)) {
            //如果有极清码流海报就使用的极清的图片不需要加后缀
            String urlSource = Uri.parse(image).getQueryParameter("source");
            if ("Qiyi".equals(urlSource)) {//如果是爱奇艺的片源才需要增加尺寸后缀
                image = DisplayImageUtil.createImgUrl(image, true);
            }
        }
        DisplayImageUtil.getInstance().setRound(COVER_ROUND).setEmptyUriImage(ResourceProvider.getInstance().getDefaultCoverListPortrait())
                .setErrorImage(ResourceProvider.getInstance().getDefaultCoverListPortrait()).setLoadingImage(ResourceProvider.getInstance().getDefaultCoverListPortrait()).displayImage(image, cover);

        title.setText(entity.getAlbumName());
        //当没有评分时，所有影片评分全部显示10分，别问我为什么，后台有些数据没评分他们让我这么干的By Spr_ypt
        String myScore = entity.getScore();
        int scores = 0;
        if (!TextUtils.isEmpty(myScore)) {
            scores = Integer.parseInt(myScore);
        }
        if (scores > 0) {
            Drawable d = context.getResources().getDrawable(R.drawable.ic_score_star);
            d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
            score.setCompoundDrawables(d, null, null, null);
            score.setText(myScore + "");
        } else {
            if (ResourceProvider.getInstance().getResArea() != ResArea.CH) {
                score.setCompoundDrawables(null, null, null, null);//移除周边图片
                CenterImageSpan span = new CenterImageSpan(context, R.drawable.ic_filmlist_item_ok);
                if (StringUtils.isZh(context)) {
                    SpannableString sp = new SpannableString(context.getResources().getString(R.string.view_film_details));
                    sp.setSpan(span, 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    score.setText(sp);
                } else {
                    SpannableString sp = new SpannableString(context.getResources().getString(R.string.view_film_details));
                    sp.setSpan(span, 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    score.setText(sp);
                }
            } else {
                Drawable d = context.getResources().getDrawable(R.drawable.ic_score_star);
                d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
                score.setCompoundDrawables(d, null, null, null);
                score.setText(context.getResources().getString(R.string.view_film_recommend));
            }
        }
    }


    @Override
    public void onClick(View v) {
        AlbumEntity entity = (AlbumEntity) v.getTag(R.id.tag_listview_entity);
        int itemPosition = (int) v.getTag(R.id.tag_listview_item_position);
        if (null != entity.getChnId()) {
            if (itemPosition < 20) {
                BaseActionInfo info = new ListViewActionInfo("03", "sde0302", entity.getChnId() + "", "3", null, (itemPosition + 1) + "");//统计埋点
                new Statistics(context, info).send();//统计埋点
            }
            if (null != onActionListener) {
                onActionListener.effectiveAction();
            }
            int showType = CloudScreenApplication.getInstance().getShowTypeByVideoTypeId(entity.getChnId());
            if (showType != ContentShowType.TYPE_SINGLE_VIDEO_NO_DETAIL) {
                View view = createBlur(v);
                Intent intent = new Intent();
                String action = VideoDetailInvoker.getInstance().getDetailActivityAction(entity.getChnId());
                intent.setAction(action);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra(AppConstants.EXTRA_ALBUM_ENTITY, (Serializable) entity);
                intent.putExtra(AppConstants.EXTRA_VIDEOSET_ID, entity.getProgramsetId());
                intent.putExtra(AppConstants.EXTRA_CID, entity.getChnId());
                intent.putExtra(AppConstants.EXTRA_SOURCE, "7");
                context.startActivity(intent);
                view.destroyDrawingCache(); // 在生成了模糊背景后及时回收view缓存
            } else {
                Intent intent = new Intent(VideoDetailInvoker.ACTION_PLAYER);
                intent.putExtra(AppConstants.EXTRA_ALBUM_ENTITY, JSONObject.toJSONString(entity));
                intent.putExtra(AppConstants.EXTRA_SOURCE, "7");
                context.startActivity(intent);
            }
        } else {
            ToastUtil.showToast(context, "影片id不能为null", 0);
        }
    }

    @NonNull
    private View createBlur(View v) {
        View view = v.getRootView();
        view.destroyDrawingCache(); // 如果之前创建过图画缓存，就先删除掉
        view.buildDrawingCache(); // 重新创建新的缓存 // FIXME used a recyled bitmap
        Bitmap bitmap = view.getDrawingCache(); // 通过图画缓存获取Bitmap对象
        if (null != CloudScreenApplication.getInstance().blurBitmap) {
            if (!CloudScreenApplication.getInstance().blurBitmap.isRecycled()) {
                CloudScreenApplication.getInstance().blurBitmap.recycle();
                CloudScreenApplication.getInstance().blurBitmap = null;
            }
        }
        if (bitmap != null) {
            Matrix matrix = new Matrix();
            matrix.postScale(0.3f, 0.3f);
            // 将Bitmap尺寸缩小，减少blur时间
            Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, view.getMeasuredWidth(), view.getMeasuredHeight(), matrix, true);
            // 将模糊过的图片保存在应用全局变量中
            CloudScreenApplication.getInstance().blurBitmap = BitmapBlurUtil.getInstance().blurBitmap(scaledBitmap, CloudScreenApplication.getInstance());
        }
        return view;
    }


}
