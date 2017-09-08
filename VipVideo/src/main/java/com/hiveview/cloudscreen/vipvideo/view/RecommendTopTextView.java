package com.hiveview.cloudscreen.vipvideo.view;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.ContentShowType;
import com.hiveview.cloudscreen.vipvideo.service.entity.RecommendListEntity;

/**
 * @ClassName: RecommendTopTextView
 * @Description: 在线视频推荐位顶部蒙版View
 * @author: yupengtong
 * @date 2014年12月5日 下午1:55:02
 */
public class RecommendTopTextView extends RelativeLayout implements CloudScreenApplication.ShowTypeWather {

    private TypeFaceTextView topTextView;
    private Resources resource;
    private RecommendListEntity entity;

    public RecommendTopTextView(Context context) {
        super(context);
        init();
    }

    public RecommendTopTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RecommendTopTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        resource = getResources();
        LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());
        View view = (RelativeLayout) mLayoutInflater.inflate(R.layout.cloud_singel_launch_top_layout, null);
        addView(view);
        topTextView = (TypeFaceTextView) view.findViewById(R.id.top_text);
        setBackgroundResource(R.drawable.recommend_top_mask);
    }

    public void setData(RecommendListEntity entity) {
        this.entity = entity;
        if (entity.getContentType() >= 2||null!=entity.getVideoId()) {//如果不是专题或专辑类型，当做单片无详情显示
            layoutSelector(ContentShowType.TYPE_SINGLE_VIDEO_NO_DETAIL, entity);
        } else {
            layoutSelector(CloudScreenApplication.getInstance().getShowTypeByVideoTypeId(entity.getCid(), this), entity);
        }
    }

    private void layoutSelector(int layoutType, RecommendListEntity entity) {
        switch (layoutType) {
            case ContentShowType.TYPE_SINGLE_VIDEO_NO_DETAIL:
            case ContentShowType.TYPE_SINGLE_VIDEO_DETAIL:
                this.setVisibility(View.GONE);
                break;
            case ContentShowType.TYPE_MULTIPLE_VIDEO_DETAIL:
                this.setVisibility(View.INVISIBLE);
                textByMulite(entity, 2);
                break;
            case ContentShowType.TYPE_VARIETY_VIDEO_DETAIL:
                this.setVisibility(View.INVISIBLE);
                textByMulite(entity, 3);
                break;
            case ContentShowType.TYPE_SUBJECT_VIDEO_DETAIL:
                this.setVisibility(View.INVISIBLE);
                textByMulite(entity, 4);
                // this.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void textByMulite(RecommendListEntity entity, int type) {

        if (type == 3) {
            topTextView.setText(String.format(resource.getString(R.string.update_expect), ""
                    + (TextUtils.isEmpty(entity.getContentUpdate()) ? "0" : entity.getContentUpdate())));
        } else if (type == 4) {
            topTextView.setText(entity.getContentTotal() > 0 ? String.format(
                    resource.getString(R.string.all_film), "" + entity.getContentTotal()) : "");
        } else {
            if (entity.getContentUpdate().equalsIgnoreCase(entity.getContentTotal() + "")) {
                topTextView.setText(R.string.all_videoset);
            } else {
                topTextView.setText(String.format(resource.getString(R.string.update_videoset), ""
                        + ((TextUtils.isEmpty(entity.getContentUpdate()) ? "0" : entity.getContentUpdate()))));
            }
        }

    }

    @Override
    public void onShowTypeGet() {
        layoutSelector(CloudScreenApplication.getInstance().getShowTypeByVideoTypeId(entity.getContentType(), this), entity);
    }

    @Override
    public void setVisibility(int visibility) {
        if (getVisibility() == GONE) {
            super.setVisibility(GONE);
        } else {
            super.setVisibility(visibility);
        }

    }
}
