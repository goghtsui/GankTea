package com.hiveview.cloudscreen.vipvideo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.ContentShowType;
import com.hiveview.cloudscreen.vipvideo.service.entity.RecommendListEntity;
import com.hiveview.cloudscreen.vipvideo.util.Logger;

/**
 * @author xieyi
 * @ClassName SubjectRecommendTextView
 * @Description 点播首页推荐位视图里的文字信息视图组件
 * @date 2014-8-29 下午9:00:22
 */
public class SubjectRecommendTextView extends RelativeLayout implements CloudScreenApplication.ShowTypeWather {

    private static final String TAG = "SubjectRecommendTextView";
    private Resources resoure;
    private RecommendListEntity entity;

    public SubjectRecommendTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SubjectRecommendTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SubjectRecommendTextView(Context context) {
        super(context);
        init();
    }

    private ImageView subjectColor; // 颜色条
    private TypeFaceTextView subjectText1; // 紧靠颜色条顶部文字区域
    private EffectiveMarqueeTextView subjectText2; // 紧靠颜色条中间文字区域，视频名称
    private EffectiveMarqueeTextView subjectText3; // 紧靠颜色条底部文字区域，视频看点
    private TypeFaceTextView subjectText4; // 紧靠布局右边上层文字区域，视频集数
    private TypeFaceTextView subjectText7; // "共"

    private void init() {
        resoure = getResources();
        LayoutInflater mLayoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = (RelativeLayout) mLayoutInflater.inflate(R.layout.cloud_single_launch_subject_layout, null);
        addView(view);
        subjectColor = (ImageView) view.findViewById(R.id.place_subject);
        subjectText1 = (TypeFaceTextView) view.findViewById(R.id.place_text1);
        subjectText2 = (EffectiveMarqueeTextView) view.findViewById(R.id.place_text2);
        subjectText3 = (EffectiveMarqueeTextView) view.findViewById(R.id.place_text3);
        subjectText4 = (TypeFaceTextView) view.findViewById(R.id.place_text4);
        subjectText7 = (TypeFaceTextView) view.findViewById(R.id.place_text7);
    }

    public void setData(RecommendListEntity entity) {
        this.entity = entity;
        if (entity.getContentType() >= 2) {//如果不是专题或专辑类型，当做单片无详情显示
            layoutSelector(ContentShowType.TYPE_SINGLE_VIDEO_NO_DETAIL, entity);
        } else {
            layoutSelector(CloudScreenApplication.getInstance().getShowTypeByVideoTypeId(entity.getCid(), this), entity);
        }
    }

    @SuppressLint("NewApi")
    private void layoutSelector(int layoutType, RecommendListEntity entity) {
        this.layoutType = layoutType;
        switch (layoutType) {
            case ContentShowType.TYPE_SINGLE_VIDEO_NO_DETAIL:
            case ContentShowType.TYPE_SINGLE_VIDEO_DETAIL:
            case ContentShowType.TYPE_MULTIPLE_VIDEO_DETAIL:
            case ContentShowType.TYPE_VARIETY_VIDEO_DETAIL:
                if (null != entity.getIsTxtShow() && entity.getIsTxtShow() == 1) {
                    textByMulite(entity);
                } else {
                    textByVideo(entity);
                }
                setAlpha(1);
                break;
            case ContentShowType.TYPE_SUBJECT_VIDEO_DETAIL:
                textBySubject(entity);
                setAlpha(1);
                break;
            default:
                break;
        }
    }

    @SuppressLint("NewApi")
    private void textByVideo(RecommendListEntity entity) {
        subjectColor.setVisibility(View.GONE);
        subjectText1.setVisibility(View.INVISIBLE);
        subjectText3.setVisibility(View.INVISIBLE);
        subjectText4.setVisibility(View.INVISIBLE);
        subjectText7.setVisibility(View.GONE);
        subjectText2.setVisibility(View.VISIBLE);
        subjectText2.setText(TextUtils.isEmpty(entity.getContentName()) ? resoure.getString(R.string.server_no_data) : entity.getContentName());
        subjectText2.setTranslationY(resoure.getDimension(R.dimen.subjectRecommendTextView_textByVideo_subjectText2_30));
    }

    @SuppressLint("NewApi")
    private void textByMulite(RecommendListEntity entity) {
        subjectColor.setVisibility(View.GONE);
        subjectText1.setVisibility(View.INVISIBLE);
        subjectText7.setVisibility(View.GONE);
        subjectText4.setVisibility(View.INVISIBLE);
        subjectText2.setVisibility(View.VISIBLE);
        subjectText3.setVisibility(View.VISIBLE);
        subjectText2.setText(TextUtils.isEmpty(entity.getContentName()) ? resoure.getString(R.string.server_no_data) : entity.getContentName());
        subjectText3.setText(TextUtils.isEmpty(entity.getContentFocus()) ? resoure.getString(R.string.server_no_data) : entity.getContentFocus());
        subjectText3.setAlpha(0);
        subjectText2.setTranslationY(resoure.getDimension(R.dimen.subjectRecommendTextView_textByMulite_subjectText2_30));

    }

    @SuppressLint("NewApi")
    private void textBySubject(RecommendListEntity entity) {
        subjectColor.setVisibility(View.VISIBLE);
        subjectText1.setVisibility(View.VISIBLE);
        subjectText2.setVisibility(View.VISIBLE);
        subjectText3.setVisibility(View.VISIBLE);
        subjectText4.setVisibility(View.VISIBLE);
        subjectText7.setVisibility(View.VISIBLE);
        subjectText2.setText(TextUtils.isEmpty(entity.getContentName()) ? resoure.getString(R.string.server_no_data) : entity.getContentName());
        subjectText3.setText(TextUtils.isEmpty(entity.getContentFocus()) ? resoure.getString(R.string.server_no_data) : entity.getContentFocus());
        subjectText3.setAlpha(0);
//by liulifeng 集数改为在顶部显示		subjectText4.setText(TextUtils.isEmpty(entity.getContent_update()) ? "0部影片" : entity.getContent_update() + "部影片");
        subjectText4.setAlpha(0);
        subjectText7.setAlpha(0);
        subjectText1.setTranslationY(resoure.getDimension(R.dimen.subjectRecommendTextView_textBySubject_subjectText1_15));
        subjectText2.setTranslationY(resoure.getDimension(R.dimen.subjectRecommendTextView_textBySubject_subjectText2_15));
    }

    /**
     * 用于保存当前推荐位类型By Spr_ypt
     */
    private int layoutType;

    /**
     * @param isOnFocus true 获取焦点，false 失去焦点
     * @Title: SubjectRecommendTextView
     * @author:yupengtong
     * @Description: 设置焦点获取与失去的变化
     */
    @SuppressLint("NewApi")
    public void setViewChangeAsFocusDo(boolean isOnFocus) {
        if (isOnFocus) {
            switch (layoutType) {
                case ContentShowType.TYPE_SINGLE_VIDEO_NO_DETAIL:
                case ContentShowType.TYPE_SINGLE_VIDEO_DETAIL:
                case ContentShowType.TYPE_MULTIPLE_VIDEO_DETAIL:
                case ContentShowType.TYPE_VARIETY_VIDEO_DETAIL:
                    if (null != entity&&null != entity.getIsTxtShow() && entity.getIsTxtShow() == 1) {
                        subjectText3.setAlpha(1);
                        subjectText2.setTranslationY(0);
                        subjectText2.setIsInFocusView(true);
                        subjectText3.setIsInFocusView(true);
                    } else {
                        subjectText2.setIsInFocusView(true);
                    }
                    break;
                case ContentShowType.TYPE_SUBJECT_VIDEO_DETAIL:
                    if (null != entity&&null != entity.getIsTxtShow() && entity.getIsTxtShow() == 1) {
                        subjectText3.setAlpha(1);
                        subjectText3.setIsInFocusView(true);
                        subjectText1.setTranslationY(0);
                        subjectText2.setTranslationY(0);
                    }
                    subjectText2.setIsInFocusView(true);
                    subjectText4.setAlpha(1);
                    subjectText7.setAlpha(1);
                    break;
                default:
                    break;
            }
        } else {
            switch (layoutType) {
                case ContentShowType.TYPE_SINGLE_VIDEO_NO_DETAIL:
                case ContentShowType.TYPE_SINGLE_VIDEO_DETAIL:
                case ContentShowType.TYPE_MULTIPLE_VIDEO_DETAIL:
                case ContentShowType.TYPE_VARIETY_VIDEO_DETAIL:
                    if (null != entity&&null != entity.getIsTxtShow() && entity.getIsTxtShow() == 1) {
                        subjectText3.setAlpha(0);
                        subjectText2.setIsInFocusView(false);
                        subjectText3.setIsInFocusView(false);
                        subjectText2.setTranslationY(resoure.getDimension(R.dimen.subjectRecommendTextView_focus_subjectText2_30));
                    } else {
                        subjectText2.setIsInFocusView(false);
                    }
                    break;
                case ContentShowType.TYPE_SUBJECT_VIDEO_DETAIL:
                    if (null != entity&&null != entity.getIsTxtShow() && entity.getIsTxtShow() == 1) {
                        subjectText3.setIsInFocusView(false);
                        subjectText1.setTranslationY(resoure.getDimension(R.dimen.subjectRecommendTextView_focus_subjectText1_15));
                        subjectText2.setTranslationY(resoure.getDimension(R.dimen.subjectRecommendTextView_focus_subjectText2_15));
                    }
                    subjectText3.setAlpha(0);
                    subjectText4.setAlpha(0);
                    subjectText7.setAlpha(0);
                    subjectText2.setIsInFocusView(false);

                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean isLayoutRequested() {
        return true;
    }

    @Override
    public void onShowTypeGet() {
        layoutSelector(CloudScreenApplication.getInstance().getShowTypeByVideoTypeId(entity.getContentType(), this), entity);
    }
}
