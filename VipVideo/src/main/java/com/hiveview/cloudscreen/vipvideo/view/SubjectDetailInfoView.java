package com.hiveview.cloudscreen.vipvideo.view;

import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.SubjectEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.SubjectLiveVoEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.SubjectVideoSetVoEntity;

import java.util.List;

/**
 * @author xieyi
 * @ClassName SubjectDetailInfoView
 * @Description 点播专题详情页左边文字区域视图
 * @date 2014-9-5 下午1:02:57
 */
public class SubjectDetailInfoView extends RelativeLayout {

    private Resources resource;

    public SubjectDetailInfoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SubjectDetailInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SubjectDetailInfoView(Context context) {
        super(context);
        init();
    }

    private TypeFaceTextView sum_vedio; // 专题下影片总数
    private TypeFaceTextView info_title; // 专题标题
    private TypeFaceTextView info_content; // 专题简介
    private TypeFaceTextView info_little_title; // 影片标题
    private TypeFaceTextView number; // 评分
    private TypeFaceTextView director; // 导演
    private TypeFaceTextView daoyan;// "导演"文字
    private TypeFaceTextView actors; // 演员
    private TypeFaceTextView yanyuan; // “演员”文字
    private TypeFaceTextView labels; // 类型
    private TypeFaceTextView biaoqian; // “类型”文字
    private TypeFaceTextView introduce; // 影片介绍

    private void init() {
        resource = getResources();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.subject_textinfo_layout, null);
        addView(view);

        sum_vedio = (TypeFaceTextView) view.findViewById(R.id.sum_vedio);
        info_title = (TypeFaceTextView) view.findViewById(R.id.info_title);
        info_title.getPaint().setFakeBoldText(true);
        info_content = (TypeFaceTextView) view.findViewById(R.id.info_content);
        info_little_title = (TypeFaceTextView) view.findViewById(R.id.info_little_title);
        info_little_title.getPaint().setFakeBoldText(true);
        number = (TypeFaceTextView) view.findViewById(R.id.number);
        director = (TypeFaceTextView) view.findViewById(R.id.director);
        daoyan = (TypeFaceTextView) view.findViewById(R.id.daoyan);
        actors = (TypeFaceTextView) view.findViewById(R.id.actors);
        yanyuan = (TypeFaceTextView) view.findViewById(R.id.yanyuan);
        labels = (TypeFaceTextView) view.findViewById(R.id.labels);
        biaoqian = (TypeFaceTextView) view.findViewById(R.id.biaoqian);
        introduce = (TypeFaceTextView) view.findViewById(R.id.introduce);
    }

    public void setInfo(SubjectEntity entity) {
        info_title.setText(TextUtils.isEmpty(entity.getSubjectName()) ? "" : entity.getSubjectName());
        info_content.setText(TextUtils.isEmpty(entity.getSubjectDesc()) ? "" : entity.getSubjectDesc());
    }

    /**
     * @param entity
     * @Title setData
     * @author xieyi
     * @Description 初始化文字区域，专辑内容默认用第一张专辑
     */
    public void setData(List<SubjectVideoSetVoEntity> entity) {
        if (null != entity) {
            sum_vedio.setText(String.format(resource.getString(R.string.total_film), "" + entity.size()));
        }
        if (null != entity && entity.size() > 0) {
            setChange(entity.get(0));

        }
    }

    /**
     * @param voEntity 专辑实体
     * @Title setChange
     * @author xieyi
     * @Description 专辑内容变化
     */
    public void setChange(SubjectVideoSetVoEntity voEntity) {
        if (voEntity.getContentCategory() == 1 && null != voEntity.getVideoSetVo()) {//点播数据
            AlbumEntity entity = voEntity.getVideoSetVo();
            Log.d("4test","getVideoSetVo="+entity.toString());
            if (null == entity || TextUtils.isEmpty(entity.getAlbumName())) {
                info_little_title.setVisibility(View.INVISIBLE);
            } else {
                info_little_title.setVisibility(View.VISIBLE);
                info_little_title.setText(entity.getAlbumName());
            }
            int mySore = 0;
            if (!TextUtils.isEmpty(entity.getScore())) {
                mySore = Integer.parseInt(entity.getScore());
            }
            if (null == entity || null == entity.getScore() || mySore <= 0) {
                number.setVisibility(View.INVISIBLE);
            } else {
                number.setVisibility(View.VISIBLE);
                number.setText(entity.getScore() + "");
            }
            if (null == entity || TextUtils.isEmpty(entity.getDirectors())) {
                daoyan.setVisibility(View.GONE);
                director.setVisibility(View.GONE);
            } else {
                daoyan.setVisibility(View.VISIBLE);
                director.setVisibility(View.VISIBLE);
                director.setText(entity.getDirectors());
            }
            if (null == entity || TextUtils.isEmpty(entity.getMainActors())) {
                yanyuan.setVisibility(View.GONE);
                actors.setVisibility(View.GONE);
            } else {
                yanyuan.setVisibility(View.VISIBLE);
                actors.setVisibility(View.VISIBLE);
                actors.setText(entity.getMainActors());
            }
            if (null == entity || TextUtils.isEmpty(entity.getLabels())) {
                biaoqian.setVisibility(View.GONE);
                labels.setVisibility(View.GONE);
            } else {
                biaoqian.setVisibility(View.VISIBLE);
                labels.setVisibility(View.VISIBLE);
                labels.setText(entity.getLabels());
            }
            if (null == entity || TextUtils.isEmpty(entity.getAlbumDesc())) {
                introduce.setVisibility(View.GONE);
            } else {
                introduce.setVisibility(View.VISIBLE);
                introduce.setText(entity.getAlbumDesc());
            }
        } else if (voEntity.getContentCategory() == 4 && null != voEntity.getLiveVo()) {
            SubjectLiveVoEntity liveVoEntity = voEntity.getLiveVo();
            Log.d("4test","getLiveVo="+liveVoEntity.toString());
            if (TextUtils.isEmpty(liveVoEntity.getTvName())) {
                info_little_title.setVisibility(View.INVISIBLE);
            } else {
                info_little_title.setVisibility(View.VISIBLE);
                info_little_title.setText(liveVoEntity.getTvName());
            }
            number.setVisibility(View.INVISIBLE);
            daoyan.setVisibility(View.GONE);
            director.setVisibility(View.GONE);
            yanyuan.setVisibility(View.GONE);
            actors.setVisibility(View.GONE);
            biaoqian.setVisibility(View.GONE);
            labels.setVisibility(View.GONE);
            if (TextUtils.isEmpty(liveVoEntity.getDetails())) {
                introduce.setVisibility(INVISIBLE);
            } else {
                introduce.setVisibility(VISIBLE);
                introduce.setText(liveVoEntity.getDetails());
            }

        }

    }
}
