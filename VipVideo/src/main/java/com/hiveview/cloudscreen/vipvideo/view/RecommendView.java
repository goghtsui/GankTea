package com.hiveview.cloudscreen.vipvideo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;
import com.hiveview.cloudscreen.vipvideo.service.entity.RecommendListEntity;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.view.verticalViewPager.VerticalViewPager;

/**
 * @author xieyi
 * @ClassName RecommendView
 * @Description 点播首页推荐位视图组件
 * @date 2014-8-31 上午10:39:58
 */
@SuppressLint("NewApi")
public class RecommendView extends RelativeLayout {

    protected static final String TAG = "RecommendView";

    public RecommendView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttr(context, attrs);
        init();
    }

    public RecommendView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        init();
    }

    public RecommendView(Context context) {
        super(context);
        init();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "RecommendView.onLayout changed=" + changed + " l=" + l + " t=" + t + " r=" + r + " b=" + b);
        LayoutParams backgroundParams = (LayoutParams) background.getLayoutParams();
        backgroundParams.width = getWidth() - 2 * (int) getResources().getDimension(R.dimen.recommendview_focus_width);//48
        backgroundParams.height = getHeight() - 2 * (int) getResources().getDimension(R.dimen.recommendview_focus_width);//48
        backgroundParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        LayoutParams cornerParams = (LayoutParams) corner.getLayoutParams();
//        cornerParams.width = (int) getResources().getDimension(R.dimen.recommendview_corner_width);
        cornerParams.width = LayoutParams.WRAP_CONTENT;
        cornerParams.height = (int) getResources().getDimension(R.dimen.recommendview_corner_height);
        cornerParams.addRule(RelativeLayout.ALIGN_LEFT, background.getId());
        cornerParams.addRule(RelativeLayout.ALIGN_TOP, background.getId());
        cornerParams.leftMargin = (int) getResources().getDimension(R.dimen.recommendview_corner_margin_left);

        LayoutParams ivParams = (LayoutParams) bottomMask.getLayoutParams();
        ivParams.width = getWidth() - 2 * (int) getResources().getDimension(R.dimen.recommendview_focus_width);//48
        ivParams.height = (int) getResources().getDimension(R.dimen.recommendview_ivParams_height);//174
        ivParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        ivParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        LayoutParams subjectParams = (LayoutParams) subjectView.getLayoutParams();
        subjectParams.width = getWidth() - 2 * (int) getResources().getDimension(R.dimen.recommendview_focus_width);//48
        subjectParams.height = (int) getResources().getDimension(R.dimen.recommendview_subjectParams_height);//174
        subjectParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        subjectParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        LayoutParams topParams = (LayoutParams) topView.getLayoutParams();
        topParams.width = getWidth() - 2 * (int) getResources().getDimension(R.dimen.recommendview_focus_width);//48
        topParams.height = (int) getResources().getDimension(R.dimen.recommendview_topParams_height);//74
        topParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        topParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        super.onLayout(changed, l, t, r, b);
    }

    private SubjectRecommendTextView subjectView; // 底部文字区域
    private View bottomMask; // 底部蒙板
    private RecommendTopTextView topView;// 顶部文字区域
    private ImageView background; // 背景图
    private ImageView corner;//角标图
    private View focusView;
    private int defaultSrc = ResourceProvider.getInstance().getBgLauncherPortraitHigh();//设置默认值

    private void initAttr(Context context, AttributeSet attrs) {
        if (attrs != null && !isInEditMode()) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.launcher_recommend);
            for (int i = 0; i < typedArray.getIndexCount(); i++) {
                int attr = typedArray.getIndex(i);
                if (attr == R.styleable.launcher_recommend_src) {
                    defaultSrc = typedArray.getResourceId(attr, ResourceProvider.getInstance().getBgLauncherLandscapeLong());
                }
            }
            typedArray.recycle();
        }
    }

    private void init() {

        background = new ImageView(getContext());
        background.setScaleType(ImageView.ScaleType.FIT_XY);
        background.setImageResource(defaultSrc);
        addView(background);

        corner = new ImageView(getContext());
        addView(corner);


        bottomMask = new View(getContext());
        bottomMask.setBackgroundResource(R.drawable.recommend_bottom_mask);
        bottomMask.setVisibility(View.VISIBLE);
        bottomMask.setFocusable(false);
        addView(bottomMask);


        subjectView = new SubjectRecommendTextView(getContext());
        subjectView.setAlpha(0);
        subjectView.setFocusable(false);
        addView(subjectView);


        topView = new RecommendTopTextView(getContext());
        topView.setVisibility(INVISIBLE);
        topView.setFocusable(false);
        addView(topView);


        focusView = new View(getContext());
        LayoutParams focusParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        focusView.setLayoutParams(focusParams);
        addView(focusView);
        setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (null != changedListener) {
                    changedListener.onChanged(v, hasFocus);
                }
            }
        });
    }

    public void setDefaultImage(int resid) {
        background.setImageResource(resid);
        defaultSrc = resid;
    }

    public void setImageBitmap(Bitmap bitmap) {
        background.setImageBitmap(bitmap);
    }

    private OnRecommendFocusChangedListener changedListener;

    public void setOnRecommendFocusChangedListener(OnRecommendFocusChangedListener listener) {
        changedListener = listener;
    }

    public static interface OnRecommendFocusChangedListener {
        public void onChanged(View v, boolean hasFocus);
    }

    public void setData(RecommendListEntity entity) {
        String image = entity.getContentImg();
        if (!TextUtils.isEmpty(image)) {
            String urlSource = Uri.parse(image).getQueryParameter("source");
            if ("Qiyi".equals(urlSource)) {//如果是爱奇艺的片源才需要增加尺寸后缀
                image = DisplayImageUtil.createImgUrl(image, true);
            }
        }
        DisplayImageUtil.getInstance().setRound((int) getResources().getDimension(R.dimen.recommendview_cover_round)).setDuration(200).setEmptyUriImage(defaultSrc).setCacheInMemory(true).setErrorImage(defaultSrc)
                .setLoadingImage(defaultSrc).displayImage(image, background);
        if (null != entity.getCornerContentApiVo() && !TextUtils.isEmpty(entity.getCornerContentApiVo().getCornerPic())) {
            DisplayImageUtil.getInstance().displayImage(entity.getCornerContentApiVo().getCornerPic(), corner);
        }
        subjectView.setData(entity);
        topView.setData(entity);
    }

    /**
     * @Title displayOrHideBottom01
     * @author xieyi
     * @Description 显示底部文字区域
     */
    public void displayBottom() {
        subjectView.setViewChangeAsFocusDo(true);
//        topView.animate().alpha(1).setDuration(200);
        topView.setVisibility(VISIBLE);
    }

    /**
     * @Title hideBottom
     * @author xieyi
     * @Description 隐藏底部文字区域
     */
    public void hideBottom() {
        subjectView.setViewChangeAsFocusDo(false);
        topView.setVisibility(INVISIBLE);
    }
}
