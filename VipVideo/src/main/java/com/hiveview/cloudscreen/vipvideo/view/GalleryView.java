package com.hiveview.cloudscreen.vipvideo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.VideoDetailInvoker;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.SubjectEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.SubjectLiveVoEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.SubjectVideoSetVoEntity;
import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.Statistics;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.SubjectDetailActionInfo;
import com.hiveview.cloudscreen.vipvideo.util.BitmapBlurUtil;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xieyi
 * @ClassName GalleryView
 * @Description 点播专题详情页右边画廊视图
 * @date 2014-9-5 下午1:04:43
 */
@SuppressLint("NewApi")
public class GalleryView extends LinearLayout {

    private static final String TAG = GalleryView.class.getSimpleName();

    public GalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleryView(Context context) {
        super(context);
        init();
    }

    private RelativeLayout container; // 视图容器，采用相对布局
    private List<View> cacheViews; // 视图缓存
    private View mainView;
    private SubjectEntity subjectEntity;

    /**
     * 背景图是否准备好了
     */
    private boolean isBgPrepared;

    private void init() {
        container = new RelativeLayout(getContext());
        container.setClipChildren(false);
        addView(container);
        LayoutParams containerParams = (LayoutParams) container.getLayoutParams();
        containerParams.width = (int) getResources().getDimension(R.dimen.galleryview_containerParams_width);// 1600
        containerParams.height = LayoutParams.WRAP_CONTENT;
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/12/10
     * @Description 为埋点统计提供一个传入subject数据的方法
     */

    public void setData(List<SubjectVideoSetVoEntity> entitys, View mainView, SubjectEntity subjectEntity) {
        this.subjectEntity = subjectEntity;
        setData(entitys, mainView);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/6/26
     * @Description 设置背景图是否准备好
     */
    public void setIsBgPrepared(boolean isDataPrepared) {
        this.isBgPrepared = isDataPrepared;
    }

    /**
     * @param entitys 专辑实体集合
     * @Title setData
     * @author xieyi
     * @Description 初始化视图
     */
    public void setData(List<SubjectVideoSetVoEntity> entitys, View mainView) {
        this.mainView = mainView;
        if (cacheViews == null) {
            cacheViews = new ArrayList<View>();
        }
        for (int i = 0; i < entitys.size(); i++) {
            RelativeLayout rl = new RelativeLayout(getContext());
            rl.setFocusable(true);
            rl.setId(500 + i);
            rl.setTag(entitys.get(i));
            rl.setOnClickListener(onClickListener);
            rl.setOnKeyListener(eventKeyListener);
            ImageView iv = new ImageView(getContext());//背景图片
            iv.setBackgroundResource(ResourceProvider.getInstance().getD529733());
            ImageView corner = new ImageView(getContext());//角标图片
            corner.setFocusable(false);
            rl.addView(iv);
            rl.addView(corner);
            container.addView(rl);
            cacheViews.add(rl);
            RelativeLayout.LayoutParams rlParams = (RelativeLayout.LayoutParams) rl.getLayoutParams();
            rlParams.width = (int) getResources().getDimension(R.dimen.galleryview_ivParams_width);// 530
            rlParams.height = (int) getResources().getDimension(R.dimen.galleryview_ivParams_height);// 734

            RelativeLayout.LayoutParams ivParams = (RelativeLayout.LayoutParams) iv.getLayoutParams();
            ivParams.width = (int) getResources().getDimension(R.dimen.galleryview_ivParams_width);// 530
            ivParams.height = (int) getResources().getDimension(R.dimen.galleryview_ivParams_height);// 734

            RelativeLayout.LayoutParams cornerParams = (RelativeLayout.LayoutParams) corner.getLayoutParams();
            cornerParams.width = (int) getResources().getDimension(R.dimen.galleryview_cornerParams_width);
            cornerParams.height = (int) getResources().getDimension(R.dimen.galleryview_cornerParams_height);
            cornerParams.leftMargin = (int) getResources().getDimension(R.dimen.galleryview_cornerParams_margin_left);
            cornerParams.addRule(RelativeLayout.ALIGN_LEFT, iv.getId());
            cornerParams.addRule(RelativeLayout.ALIGN_TOP, iv.getId());
            /*
             * 由于页面只显示4个图片，所以多余的图片位置不用变动，位置和第5张重合，方便动画，前5张图片依次旋转、
			 * 缩小，只对前5个位置做动画，用视图缓存来保持图片切换后的正确的位序，永远只取当前缓存内的前5张图片 来做动画
			 */
            if (i < 4) {
                if (i == 0) { // 第一张 紧靠父视图左边
                    rlParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                } else { // 第2-4张，左间距240，从1.0依次减少0.1缩放
                    rlParams.addRule(RelativeLayout.ALIGN_LEFT, rl.getId() - 1);
                    rlParams.addRule(RelativeLayout.ALIGN_TOP, rl.getId() - 1);
                    rlParams.leftMargin = (int) getResources().getDimension(R.dimen.galleryview_setData_ivParams_leftMargin_240);
                    rl.setScaleX((float) (1 - 0.1 * i));
                    rl.setScaleY((float) (1 - 0.1 * i));
                    rl.setRotationY(-4);
                }
            } else {// 第5张及以后的图片，全部重叠在第5张的位置
                rlParams.leftMargin = (int) getResources().getDimension(R.dimen.galleryview_setData_ivParams_leftMargin_240);
                rlParams.addRule(RelativeLayout.ALIGN_LEFT, 503);
                rl.setScaleX(0.6f);
                rl.setScaleY(0.6f);
                rl.setRotationY(-4);
            }
            if (!TextUtils.isEmpty(entitys.get(i).getContentPic())) {
                DisplayImageUtil.getInstance().setRound((int) getResources().getDimension(R.dimen.recommendview_cover_round)).setDuration(200).displayImage(entitys.get(i).getContentPic(), iv);
            } else if (null != entitys.get(i).getVideoSetVo() && !TextUtils.isEmpty(entitys.get(i).getVideoSetVo().getAlbumXqyPic())) {
                String image = entitys.get(i).getVideoSetVo().getAlbumXqyPic();
                String urlSource = Uri.parse(image).getQueryParameter("source");
                if ("Qiyi".equals(urlSource)) {//如果是爱奇艺的片源才需要增加尺寸后缀
                    image = DisplayImageUtil.createImgUrl(image, true);
                }
                DisplayImageUtil.getInstance().setRound((int) getResources().getDimension(R.dimen.recommendview_cover_round)).setDuration(200).displayImage(image, iv);
            }
            //角标
            if (null != entitys.get(i).getLiveVo() && null != entitys.get(i).getLiveVo().getLiveCornerContent() && !TextUtils.isEmpty(entitys.get(i).getLiveVo().getLiveCornerContent().getCornerPic())) {
                DisplayImageUtil.getInstance().setDuration(200).displayImage(entitys.get(i).getLiveVo().getLiveCornerContent().getCornerPic(), corner);
            } else if (null != entitys.get(i).getVideoSetVo() && null != entitys.get(i).getVideoSetVo().getCornerImage() && !TextUtils.isEmpty(entitys.get(i).getVideoSetVo().getCornerImage())) {
                DisplayImageUtil.getInstance().setDuration(200).displayImage(entitys.get(i).getVideoSetVo().getCornerImage(), corner);
            } else {
                corner.setImageDrawable(null);
            }
        }

        // 给每个图片视图设置焦点向右ID
        for (int i = cacheViews.size() - 1; i >= 0; i--) {
            if (i == cacheViews.size() - 1) {
                cacheViews.get(i).setNextFocusRightId(500);
            } else {
                cacheViews.get(i).setNextFocusRightId(500 + i + 1);
            }

            if (i == 0) {
                cacheViews.get(i).setNextFocusLeftId(500 + cacheViews.size() - 1);
            } else {
                cacheViews.get(i).setNextFocusLeftId(500 + i - 1);
            }
        }
        currentView = cacheViews.get(0); // 初始化第一位置视图，默认为数据集合第一个
        /*********** 由于是从数据集合第一个开始布局，所以第一个处于最下方，用bring方法依次将1-4位置的视图向上提，展现正向层级效果 ************/
        for (int i = cacheViews.size() - 1; i >= 0; i--) {
            cacheViews.get(i).bringToFront();
        }
        cacheViews.get(0).requestFocus();
        post(new Runnable() {
            @Override
            public void run() {
                focus.initFocusView(currentView, false, 0);
            }
        });
    }

    private float place1X;// 第一个位置的X坐标
    private float place2X;
    private float place3X;
    private float place4X;
    private float place5X;

    private float place1ScaleX;// 第一个位置的X轴缩放值
    private float place2ScaleX = 0.9f;
    private float place3ScaleX = 0.8f;
    private float place4ScaleX = 0.7f;
    private float place5ScaleX = 0.6f;

    private float place1RotationY;// 第一个位置的Y轴偏移量
    private float place2RotationY = -4;
    private float place3RotationY = -4;
    private float place4RotationY = -4;
    private float place5RotationY = -4;

    private boolean isGetCoord = false; // 标识是否初始化了5个动画位置的必要参数，false:没有 true:有
    private boolean isRunning = false; // 标识当前是否有动画在发生，false:没有 true:有

    // private ObjectAnimator place1Alpha; //第一个位置的渐隐动画器
    // private ObjectAnimator place1Move;//第一个位置的位移动画器
    private ObjectAnimator place1Dismiss;
    private ObjectAnimator place2Move;// 第二个位置的渐隐动画器
    private ObjectAnimator place3Move;// 第三个位置的渐隐动画器
    private ObjectAnimator place4Move;// 第四个位置的渐隐动画器
    private ObjectAnimator place5Move;// 第无个位置的渐隐动画器

    private ObjectAnimator back1;
    private ObjectAnimator back2;
    private ObjectAnimator back3;
    private ObjectAnimator back4;
    private ObjectAnimator back5;

    private View currentView; // 缓存当前页面动画完成前的第一位置视图
    private LauncherFocusView focus; // 焦点框

//    private int position = 0;

    public void setFocus(LauncherFocusView focus) {
        this.focus = focus;
    }

    /**
     * @Title initAnimators
     * @author xieyi
     * @Description 初始化动画器，原理就是第一个位置的视图渐隐并移动到第五个位置，5移向4，4移向3，依次类推
     */
    private void initAnimators() {
        PropertyValuesHolder backScaleX = PropertyValuesHolder.ofFloat("scaleX", 0.1f, 1.0f);
        PropertyValuesHolder backScaleY = PropertyValuesHolder.ofFloat("scaleY", 0.1f, 1.0f);
        PropertyValuesHolder backRotationY = PropertyValuesHolder.ofFloat("rotationY", -90, 0);
        PropertyValuesHolder backholderX = PropertyValuesHolder.ofFloat("x", place1X - 290, place1X);
        back1 = ObjectAnimator.ofPropertyValuesHolder(cacheViews.get(cacheViews.size() - 1), backScaleX, backScaleY, backRotationY, backholderX)
                .setDuration(600);
        back1.setInterpolator(new DecelerateInterpolator());

        PropertyValuesHolder backHolderX2 = PropertyValuesHolder.ofFloat("x", place2X);
        PropertyValuesHolder backHolderScaleX2 = PropertyValuesHolder.ofFloat("scaleX", place2ScaleX);
        PropertyValuesHolder backHolderScaleY2 = PropertyValuesHolder.ofFloat("scaleY", place2ScaleX);
        PropertyValuesHolder backHolderRotationY2 = PropertyValuesHolder.ofFloat("rotationY", place2RotationY);
        back2 = ObjectAnimator.ofPropertyValuesHolder(cacheViews.get(0), backHolderX2, backHolderScaleX2, backHolderScaleY2, backHolderRotationY2)
                .setDuration(600);
        back2.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {

                focus.setAlpha(1); // 显示焦点框
                eventCall.eventEnd((SubjectVideoSetVoEntity) cacheViews.get(0).getTag()); // 回调，改变左边文字区域内容
                isRunning = false;
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {

                View lastView = cacheViews.get(cacheViews.size() - 1);
                cacheViews.remove(lastView);
                cacheViews.add(0, lastView);

                cacheViews.get(3).bringToFront();
                cacheViews.get(2).bringToFront();
                cacheViews.get(1).bringToFront();
                cacheViews.get(0).bringToFront();
                ((ViewGroup) cacheViews.get(0).getParent()).invalidate();//盒子上必须invalidate一下，否则层级显示会出错
                super.onAnimationStart(animation);
            }

        });

        PropertyValuesHolder backHolderX3 = PropertyValuesHolder.ofFloat("x", place3X);
        PropertyValuesHolder backHolderScaleX3 = PropertyValuesHolder.ofFloat("scaleX", place3ScaleX);
        PropertyValuesHolder backHolderScaleY3 = PropertyValuesHolder.ofFloat("scaleY", place3ScaleX);
        PropertyValuesHolder backHolderRotationY3 = PropertyValuesHolder.ofFloat("rotationY", place3RotationY);
        back3 = ObjectAnimator.ofPropertyValuesHolder(cacheViews.get(1), backHolderX3, backHolderScaleX3, backHolderScaleY3, backHolderRotationY3)
                .setDuration(600);

        PropertyValuesHolder backHolderX4 = PropertyValuesHolder.ofFloat("x", place4X);
        PropertyValuesHolder backHolderScaleX4 = PropertyValuesHolder.ofFloat("scaleX", place4ScaleX);
        PropertyValuesHolder backHolderScaleY4 = PropertyValuesHolder.ofFloat("scaleY", place4ScaleX);
        PropertyValuesHolder backHolderRotationY4 = PropertyValuesHolder.ofFloat("rotationY", place4RotationY);
        back4 = ObjectAnimator.ofPropertyValuesHolder(cacheViews.get(2), backHolderX4, backHolderScaleX4, backHolderScaleY4, backHolderRotationY4)
                .setDuration(600);

        PropertyValuesHolder backHolderX5 = PropertyValuesHolder.ofFloat("x", place5X);
        PropertyValuesHolder backHolderScaleX5 = PropertyValuesHolder.ofFloat("scaleX", place5ScaleX);
        PropertyValuesHolder backHolderScaleY5 = PropertyValuesHolder.ofFloat("scaleY", place5ScaleX);
        PropertyValuesHolder backHolderRotationY5 = PropertyValuesHolder.ofFloat("rotationY", place5RotationY);
        back5 = ObjectAnimator.ofPropertyValuesHolder(cacheViews.get(3), backHolderX5, backHolderScaleX5, backHolderScaleY5, backHolderRotationY5)
                .setDuration(600);

        PropertyValuesHolder scaleXHolder = PropertyValuesHolder.ofFloat("scaleX", 0.1f);
        PropertyValuesHolder scaleYHolder = PropertyValuesHolder.ofFloat("scaleY", 0.1f);
        PropertyValuesHolder holderRotationY = PropertyValuesHolder.ofFloat("rotationY", -90);
        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("x", place1X - 290);
        place1Dismiss = ObjectAnimator.ofPropertyValuesHolder(currentView, scaleXHolder, scaleYHolder, holderRotationY, holderX).setDuration(600);
        place1Dismiss.setInterpolator(new DecelerateInterpolator());

        // PropertyValuesHolder holderX1 = PropertyValuesHolder.ofFloat("x",
        // place5X);
        // PropertyValuesHolder holderScaleX1 =
        // PropertyValuesHolder.ofFloat("scaleX", place5ScaleX);
        // PropertyValuesHolder holderScaleY1 =
        // PropertyValuesHolder.ofFloat("scaleY", place5ScaleX);
        // PropertyValuesHolder holderRotationY1 =
        // PropertyValuesHolder.ofFloat("rotationY", place5RotationY);
        // place1Move =
        // ObjectAnimator.ofPropertyValuesHolder(currentView,holderX1,holderScaleX1,holderScaleY1,holderRotationY1).setDuration(300);
        // place1Move.setInterpolator(new DecelerateInterpolator());

        // place1Alpha = ObjectAnimator.ofFloat(currentView, "alpha",
        // 0).setDuration(300);
        // place1Alpha.setInterpolator(new DecelerateInterpolator());

        PropertyValuesHolder holderX2 = PropertyValuesHolder.ofFloat("x", place1X);
        PropertyValuesHolder holderScaleX2 = PropertyValuesHolder.ofFloat("scaleX", place1ScaleX);
        PropertyValuesHolder holderScaleY2 = PropertyValuesHolder.ofFloat("scaleY", place1ScaleX);
        PropertyValuesHolder holderRotationY2 = PropertyValuesHolder.ofFloat("rotationY", place1RotationY);
        place2Move = ObjectAnimator.ofPropertyValuesHolder(cacheViews.get(1), holderX2, holderScaleX2, holderScaleY2, holderRotationY2).setDuration(
                600);
        place2Move.setInterpolator(new DecelerateInterpolator());
        place2Move.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 动画结束，及时更新缓存队列的数据位序
                cacheViews.remove(currentView);
                cacheViews.add(currentView);
                // 依次将前4个位置的视图提到页面最顶端，保持正向层级感
                cacheViews.get(3).bringToFront();
                cacheViews.get(2).bringToFront();
                cacheViews.get(1).bringToFront();
                cacheViews.get(0).bringToFront();
                ((ViewGroup) cacheViews.get(0).getParent()).invalidate();//盒子上必须invalidate一下，否则层级显示会出错
                focus.setAlpha(1); // 显示焦点框
                eventCall.eventEnd((SubjectVideoSetVoEntity) cacheViews.get(0).getTag()); // 回调，改变左边文字区域内容
                isRunning = false;
            }

        });

        PropertyValuesHolder holderX3 = PropertyValuesHolder.ofFloat("x", place2X);
        PropertyValuesHolder holderScaleX3 = PropertyValuesHolder.ofFloat("scaleX", place2ScaleX);
        PropertyValuesHolder holderScaleY3 = PropertyValuesHolder.ofFloat("scaleY", place2ScaleX);
        PropertyValuesHolder holderRotationY3 = PropertyValuesHolder.ofFloat("rotationY", place2RotationY);
        place3Move = ObjectAnimator.ofPropertyValuesHolder(cacheViews.get(2), holderX3, holderScaleX3, holderScaleY3, holderRotationY3).setDuration(
                600);
        place3Move.setInterpolator(new DecelerateInterpolator());

        PropertyValuesHolder holderX4 = PropertyValuesHolder.ofFloat("x", place3X);
        PropertyValuesHolder holderScaleX4 = PropertyValuesHolder.ofFloat("scaleX", place3ScaleX);
        PropertyValuesHolder holderScaleY4 = PropertyValuesHolder.ofFloat("scaleY", place3ScaleX);
        PropertyValuesHolder holderRotationY4 = PropertyValuesHolder.ofFloat("rotationY", place3RotationY);
        place4Move = ObjectAnimator.ofPropertyValuesHolder(cacheViews.get(3), holderX4, holderScaleX4, holderScaleY4, holderRotationY4).setDuration(
                600);
        place4Move.setInterpolator(new DecelerateInterpolator());

        PropertyValuesHolder holderX5 = PropertyValuesHolder.ofFloat("x", place4X);
        PropertyValuesHolder holderScaleX5 = PropertyValuesHolder.ofFloat("scaleX", place4ScaleX);
        PropertyValuesHolder holderScaleY5 = PropertyValuesHolder.ofFloat("scaleY", place4ScaleX);
        PropertyValuesHolder holderRotationY5 = PropertyValuesHolder.ofFloat("rotationY", place4RotationY);
        place5Move = ObjectAnimator.ofPropertyValuesHolder(cacheViews.get(4), holderX5, holderScaleX5, holderScaleY5, holderRotationY5).setDuration(
                600);
        place5Move.setInterpolator(new DecelerateInterpolator());
    }

    private OnClickListener onClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            SubjectVideoSetVoEntity subjectVoEntity = (SubjectVideoSetVoEntity) v.getTag();
            if (subjectVoEntity.getContentCategory() == 1 && null != subjectVoEntity.getVideoSetVo()) {
                if (isBgPrepared) {//背景图准备好了才模糊
                    if (null != CloudScreenApplication.getInstance().blurBitmap) {
                        CloudScreenApplication.getInstance().blurBitmap.recycle();
                        CloudScreenApplication.getInstance().blurBitmap = null;
                    }
                    mainView.destroyDrawingCache();
                    mainView.buildDrawingCache();
                    Bitmap bitmap = mainView.getDrawingCache();
                    if (bitmap != null) {
                        Matrix matrix = new Matrix();
                        matrix.postScale(0.3f, 0.3f);
                        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, mainView.getMeasuredWidth(), mainView.getMeasuredHeight(), matrix, true);
                        CloudScreenApplication.getInstance().blurBitmap = BitmapBlurUtil.getInstance().blurBitmap(scaledBitmap, getContext());
                    }
                } else {
                    CloudScreenApplication.getInstance().blurBitmap = null;
                }

                AlbumEntity entity = subjectVoEntity.getVideoSetVo();
                if (null == entity) {
                    return;
                }

                String action = VideoDetailInvoker.getInstance().getDetailActivityAction(entity.getChnId());
                Intent detail = new Intent(action);
                if (action.equals(VideoDetailInvoker.ACTION_PLAYER)) {
//                    detail.putExtra(AppConstants.EXTRA_ALBUM_ENTITY, JSONObject.toJSONString(entity));
                    detail.putExtra(AppConstants.EXTRA_VIDEOSET_ID, entity.getProgramsetId() + "");
                    detail.putExtra(AppConstants.EXTRA_IS_FROM_ONLY_ID, true);
                } else {
                    if (null != entity.getVideoId()) {//不是启动播放器，但是有具体的videoId,则认为是该启动播放器
                        detail.setAction(VideoDetailInvoker.ACTION_PLAYER);
                        detail.putExtra(AppConstants.EXTRA_VIDEOSET_ID, entity.getProgramsetId() + "");
                        detail.putExtra(AppConstants.EXTRA_VIDEO_ID, entity.getVideoId());
                        detail.putExtra(AppConstants.EXTRA_IS_FROM_ONLY_ID, true);

                    } else {
                        detail.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        detail.putExtra(AppConstants.EXTRA_VIDEOSET_ID, entity.getProgramsetId());
                        detail.putExtra(AppConstants.EXTRA_CID, entity.getChnId());
                    }
                }
                if (null != onActionListener) {
                    onActionListener.effectiveAction();
                }

                //发送埋点统计
                BaseActionInfo info = new SubjectDetailActionInfo("03", "vip03101", subjectEntity.getSubjectId() + "", subjectEntity.getSubjectName(), "6", entity.getProgramsetId() + "", entity.getAlbumName());
                new Statistics(getContext(), info).send();

                //埋点统计用
                detail.putExtra(AppConstants.EXTRA_SOURCE, "2");
                if (null != subjectVoEntity) {
                    detail.putExtra(AppConstants.EXTRA_SUBJECT_ID, null != subjectEntity.getSubjectId() ? subjectEntity.getSubjectId() : 0);
                    detail.putExtra(AppConstants.EXTRA_SUBJECT_NAME, null != subjectEntity.getSubjectName() ? subjectEntity.getSubjectName() : "");
                }
                getContext().startActivity(detail);
            } else if (subjectVoEntity.getContentCategory() == 4 && null != subjectVoEntity.getLiveVo()) {
                SubjectLiveVoEntity entity = subjectVoEntity.getLiveVo();
                Intent intentDlive = new Intent(VideoDetailInvoker.ACTION_DLIVE);
                intentDlive.putExtra(AppConstants.EXTRA_DLIVE_ID, entity.getTvId());
                try {
                    getContext().startActivity(intentDlive);
                } catch (Exception e) {
                    ToastUtil.showToast(getContext(), "找不到播放器控件", Toast.LENGTH_SHORT);
                }
            } else {
                ToastUtil.showToast(getContext(), getContext().getString(R.string.subject_detail_data_error), Toast.LENGTH_SHORT);
            }


        }
    };

    /*
     * 事件监听器，只针对向右事件做处理
     */
    private OnKeyListener eventKeyListener = new OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (KeyEvent.ACTION_DOWN == event.getAction()) {
                if (!isGetCoord) {
                    place1X = cacheViews.get(0).getX();
                    place2X = cacheViews.get(1).getX();
                    place3X = cacheViews.get(2).getX();
                    place4X = cacheViews.get(3).getX();
                    place5X = cacheViews.get(4).getX();
                    place1ScaleX = cacheViews.get(0).getScaleX();
                    place1RotationY = cacheViews.get(0).getRotationY();
                    initAnimators();
                    isGetCoord = true;
                }
                switch (event.getKeyCode()) {
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        if (isRunning) {
                            return true;
                        } else {
                            isRunning = true;
                            focus.setAlpha(0); // 动画开始，隐藏焦点框
                            currentView = cacheViews.get(0); // 从缓存队列里取到当前第一个位置视图
                            cacheViews.get(4).setVisibility(View.VISIBLE); // 显示缓存队列里第5个位置的视图
                            AnimatorSet set = new AnimatorSet();
                            // place1Move.setTarget(currentView);
                            // place1Alpha.setTarget(currentView);
                            place1Dismiss.setTarget(currentView);
                            place2Move.setTarget(cacheViews.get(1));
                            place3Move.setTarget(cacheViews.get(2));
                            place4Move.setTarget(cacheViews.get(3));
                            place5Move.setTarget(cacheViews.get(4));
                            set.playTogether(place1Dismiss, place2Move, place3Move, place4Move, place5Move);
                            // set.play(place1Dismiss).with(place2Move).with(place3Move).with(place4Move).with(place5Move);
                            // set.play(place1Move).after(place1Alpha);
                            set.start();
//                            position = (position < cacheViews.size() - 1) ? position + 1 : 0;
//                            for (int i = position + 3; i >= position; i--) {
//                                int t = i < cacheViews.size() ? i : i - cacheViews.size();
//                                Log.d(TAG,"t=="+t);
//                                cacheViews.get(t).bringToFront();
//                            }
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        if (isRunning) {
                            return true;
                        } else {
                            isRunning = true;
                            focus.setAlpha(0); // 动画开始，隐藏焦点框
                            View lastView = cacheViews.get(cacheViews.size() - 1); // 从缓存队列里取到当前第一个位置视图
                            AnimatorSet set = new AnimatorSet();
                            back1.setTarget(lastView);
                            back2.setTarget(cacheViews.get(0));
                            back3.setTarget(cacheViews.get(1));
                            back4.setTarget(cacheViews.get(2));
                            back5.setTarget(cacheViews.get(3));
                            set.playTogether(back1, back2, back3, back4, back5);
                            set.start();
//                            position = (position == 0) ? cacheViews.size() - 1 : position - 1;
                        }
                        break; // 屏蔽向左事件
                }
            }
            return false;
        }
    };
    private EventCallBack eventCall;

    public void setEventCallBack(EventCallBack eventCall) {
        this.eventCall = eventCall;
    }

    /*
     * 向右动画后的回调接口
     */
    public interface EventCallBack {
        void eventEnd(SubjectVideoSetVoEntity entity);
    }

    private OnActionListener onActionListener;

    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
    }

    public interface OnActionListener {
        void effectiveAction();
    }
}
