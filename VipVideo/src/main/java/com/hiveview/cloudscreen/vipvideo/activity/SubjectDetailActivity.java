package com.hiveview.cloudscreen.vipvideo.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.service.CloudScreenService;
import com.hiveview.cloudscreen.vipvideo.service.OnRequestResultListener;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.SubjectEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.SubjectVideoSetVoEntity;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.Statistics;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.SubjectBrowseInfo;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.util.ToastUtil;
import com.hiveview.cloudscreen.vipvideo.view.GalleryView;
import com.hiveview.cloudscreen.vipvideo.view.LauncherFocusView;
import com.hiveview.cloudscreen.vipvideo.view.SubjectDetailInfoView;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author xieyi
 * @ClassName SubjectDetailActivity
 * @Description 点播专题详情界面
 * @date 2014-9-5 上午11:33:16
 */
public class SubjectDetailActivity extends BaseActivity implements GalleryView.OnActionListener {

    private ImageView background; //背景图
    private SubjectDetailInfoView textInfo; //左边文字区域
    private GalleryView gallery; //右边图片区域
    private LauncherFocusView focus; //焦点框
    private View subjectdetailview;
    private SubjectEntity subjectEntity;
    private String spareBgUrl;
    /**
     * 统计埋点
     * 页面跳出标识页面跳出标识
     * 1：直接跳出 0：做了其他操作
     */
    private String noAction = "1";
    /**
     * 统计埋点
     * 打开页面的时间，用于结束的时候计算持续时间
     */
    private long openTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_layout);
        int subjectId = getIntent().getIntExtra(AppConstants.EXTRA_SUBJECT_ID, 0);
        spareBgUrl = getIntent().getStringExtra(AppConstants.EXTRA_SUBJECT_BACKGROUND);
        subjectdetailview = findViewById(R.id.subjectdetailview);
        background = (ImageView) findViewById(R.id.subject_background);
        textInfo = (SubjectDetailInfoView) findViewById(R.id.subjectinfo);
        gallery = (GalleryView) findViewById(R.id.container3d);
        focus = (LauncherFocusView) findViewById(R.id.focus);
        gallery.setEventCallBack(eventCall); //设置回调
        gallery.setOnActionListener(this);//设置有效操作回调，统计埋点
        CloudScreenService.getInstance().getSubjectDetailInfo(new SubjectDataResponseListener(this), subjectId); //下载数据
    }

    GalleryView.EventCallBack eventCall = new GalleryView.EventCallBack() {

        @Override
        public void eventEnd(SubjectVideoSetVoEntity entity) {
            textInfo.setChange(entity); //图片切换时文字区域内容变化
        }
    };


    private static class SubjectDataResponseListener implements OnRequestResultListener<ResultEntity> {
        private WeakReference<SubjectDetailActivity> ref;

        public SubjectDataResponseListener(SubjectDetailActivity activity) {
            ref = new WeakReference<SubjectDetailActivity>(activity);
        }


        @Override
        public void onSucess(ResultEntity resultEntity) {
            SubjectDetailActivity activity = ref.get();
            if (null != activity) {
                SubjectEntity entity = (SubjectEntity) resultEntity.getEntity();
                activity.subjectEntity = entity;
                List<SubjectVideoSetVoEntity> subEntity = resultEntity.getList();
                //数据下载完成后初始化各个组件
                if (null != subEntity) {
                    if (subEntity.size() < 5) {
                        ToastUtil.showToast(activity, activity.getResources().getString(R.string.subject_data_error), Toast.LENGTH_SHORT);
                        activity.finish();
                    } else {
                        activity.textInfo.setVisibility(View.VISIBLE);
                        activity.textInfo.setInfo(entity);
                        DisplayImageUtil.getInstance().setRound((int) activity.getResources().getDimension(R.dimen.recommendview_cover_round)).setDuration(200).displayImage(entity.getSubjectHorBgImg(), activity.background, new OnImageLoadCompleteListenner(activity));
                        activity.textInfo.setData(subEntity);
                        activity.gallery.setFocus(activity.focus);
                        activity.gallery.setData(subEntity, activity.subjectdetailview, activity.subjectEntity);
                    }
                }
            }
        }

        @Override
        public void onFail(Exception e) {
            SubjectDetailActivity activity = ref.get();
            if (null != activity) {
                DisplayImageUtil.getInstance().setRound((int) activity.getResources().getDimension(R.dimen.recommendview_cover_round)).setDuration(200).displayImage(activity.spareBgUrl, activity.background, new OnImageLoadCompleteListenner(activity));
                Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.load_data_failed_please_try_again), Toast.LENGTH_SHORT)
                        .show();
                activity.finish();
            }
        }

        @Override
        public void onParseFail(HiveviewException e) {
            SubjectDetailActivity activity = ref.get();
            if (null != activity) {
                DisplayImageUtil.getInstance().setRound((int) activity.getResources().getDimension(R.dimen.recommendview_cover_round)).setDuration(200).displayImage(activity.spareBgUrl, activity.background, new OnImageLoadCompleteListenner(activity));
                Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.load_data_failed_please_try_again), Toast.LENGTH_SHORT)
                        .show();
                activity.finish();
            }
        }
    }


    @Override
    protected void onStart() {
        openTime = System.currentTimeMillis();
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        focus.resetFocusColor();
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/12/8
     * @Description GalleryView.OnActionListener, 统计埋点
     */
    @Override
    public void effectiveAction() {
        noAction = "0";
    }

    @Override
    protected void onStop() {
        if (null != subjectEntity) {
            BaseActionInfo info = new SubjectBrowseInfo("03", "vip0310", subjectEntity.getSubjectId() + "", subjectEntity.getSubjectName(), Long.toString(System.currentTimeMillis() - openTime), noAction);//统计埋点
            new Statistics(this, info).send();//统计埋点
        }

        super.onStop();
    }

    private static class OnImageLoadCompleteListenner extends SimpleImageLoadingListener {

        private WeakReference<SubjectDetailActivity> reference;

        public OnImageLoadCompleteListenner(SubjectDetailActivity activity) {
            this.reference = new WeakReference<SubjectDetailActivity>(activity);
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            final SubjectDetailActivity activity = reference.get();
            if (null != activity && null != activity.gallery) {
                activity.gallery.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity.gallery.setIsBgPrepared(true);
                    }
                }, 200);
            }
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            final SubjectDetailActivity activity = reference.get();
            if (null != activity && null != activity.gallery) {
                activity.gallery.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity.gallery.setIsBgPrepared(true);
                    }
                }, 200);
            }
        }
    }


}
