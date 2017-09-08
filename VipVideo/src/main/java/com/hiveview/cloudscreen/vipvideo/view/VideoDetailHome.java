package com.hiveview.cloudscreen.vipvideo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.activity.VipVideoDetailActivity;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.ContentShowType;
import com.hiveview.cloudscreen.vipvideo.common.VideoDetailInvoker;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;
import com.hiveview.cloudscreen.vipvideo.service.CloudScreenService;
import com.hiveview.cloudscreen.vipvideo.service.OnRequestResultListener;
import com.hiveview.cloudscreen.vipvideo.service.dao.CollectionDao;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.EpisodeListEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ProductInfoEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ServiceTimeEntity;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.Statistics;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.LoginDialogActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.VIPDialogActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.VIPFilmDetailActionInfo;
import com.hiveview.cloudscreen.vipvideo.util.CollectDataUtil;
import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.util.SimplifySpanBuildUtils;
import com.hiveview.cloudscreen.vipvideo.util.StringUtils;
import com.hiveview.cloudscreen.vipvideo.util.ToastUtil;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class VideoDetailHome implements OnFocusChangeListener, OnClickListener, OnKeyListener, UserStateUtil.UserStatusWatcher {
    private static final String TAG = VideoDetailHome.class.getSimpleName();
    public ProductInfoEntity productInfoEntity;
    public int animalFlag = 0;//蒙层动画标示
    /**
     * 1：代表4k,4k预售；2：超清，超清预售
     */
    public int clickBtnFlag = -1;

    private CollectionDao dao;
    private AnimatorSet collectAnimator;
    private SimplifySpanBuildUtils simpSpanUtil = new SimplifySpanBuildUtils();
    /**
     * tvVipPrice：vip价格；
     * tvCurrentPrice：原价；
     * tvNonMemberPrice ：非会员价格。
     */
    private TextView tvVipPrice, tvCurrentPrice, tvNonMemberPrice;

    /**
     * TypeFaceTextView 文字
     */
    private TypeFaceTextView fourPlayText;

    private TypeFaceTextView playText;
    private TypeFaceTextView collectText;
    private TypeFaceTextView eposideText;
    private TypeFaceTextView trySeeText;

    /**
     * 海报容器
     */
    private RelativeLayout coverContainer;
    /**
     * 海报图
     */
    private ImageView filmImageView;
    /**
     * 海报图标
     */
    private ImageView filmCorner;
    private RelativeLayout rl_tv_dec;


    /**
     * 中间影片名
     */
    private TextView filmNameView;
    /**
     * 全部集数
     */
    private TextView filmUpdateView;
    /**
     * 更新集数
     */
    private TextView filmUpdateNumberView;
    /**
     * 第一行 导演
     */
    private TypeFaceTextView filmDirectorView;
    /**
     * 第二行 演员
     */
    private TypeFaceTextView filmActorView;
    /**
     * 第三行 专辑类型（言情等）
     */
    private TypeFaceTextView filmTypeView;
    /**
     * 中间详情
     */
    public TextView filmDescription, tv_watchTime;

    /**
     * 收藏图片
     */
    private View collectHeartImage;
    /**
     * 4K
     */
    public View fourPlayBtn;
    /**
     * 播放
     */
    public View playBtn;
    /**
     * 试看
     */
    public View trySeeBtn;
    /**
     * 收藏
     */
    private View collectBtn;
    /**
     * 剧集
     */
    private View eposideBtn;

    /**
     * 右侧提示语
     */
    private RelativeLayout rl_right_tip;
    /**
     * 按左右键操作提示
     */
    private NoticeView noticeView;
    /**
     * 需要登录、开通vip的弹框
     */
    VipVideoDialog userStatusDialog;

    /**
     * 左侧提示语,影片价格
     */
    public RelativeLayout rl_left_tip, filmPrice;

    /**
     * 是否第一次启动
     */
    private boolean isFirstDisplay = true;
    /**
     * 收藏状态
     */
    private boolean isCollected = false;

    private OnClickListener clickListener;
    private OnFocusChangeListener focusChangeListener;
    private OnKeyListener keyListener;

    private Activity activity;
    private AlbumEntity entity;
    List<View> focusView = new ArrayList<>();
    private String source = "";
    private int subjectId = 0;
    private String subjectName = "";
    private RelativeLayout all_content;
    /**
     * 中间容器
     */
    private RelativeLayout rl_film_middle;

    private boolean isOnAnim;

    /**
     * 标记普通播放按钮是否已经设置过为 true
     */
    private boolean isplayShow = false;
    /**
     * 标记4K按钮是否已经设置过为 true
     */
    private boolean isFourPlayShow = false;
    /**
     * 标示点击哪个按钮
     */
    private int clickFlag = -1;
    private VipVideoDialog dialog;

    public VideoDetailHome(Activity activity, int subjectId, String subjectName, String source) {
        this.activity = activity;
        setExtras(subjectId, subjectName, source);

        dao = new CollectionDao(activity);
        //影片价格
        filmPrice = (RelativeLayout) activity.findViewById(R.id.rl_film_price);
        tvVipPrice = (TextView) activity.findViewById(R.id.tv_vip_price);
        tvCurrentPrice = (TextView) activity.findViewById(R.id.tv_original_price);
        tvNonMemberPrice = (TextView) activity.findViewById(R.id.tv_nonMember_price);
        tv_watchTime = (TextView) activity.findViewById(R.id.detail_watch_time);
        // 中间的数据
        all_content = (RelativeLayout) activity.findViewById(R.id.all_content);
        rl_film_middle = (RelativeLayout) activity.findViewById(R.id.rl_film_middle);
        rl_tv_dec = (RelativeLayout) activity.findViewById(R.id.rl_tv_dec);
        coverContainer = (RelativeLayout) activity.findViewById(R.id.rl_film_detail_cover);
        filmImageView = (ImageView) activity.findViewById(R.id.iv_film_detail_cover);
        filmImageView.setImageResource(ResourceProvider.getInstance().getDefaultCoverDetail());
        filmCorner = (ImageView) activity.findViewById(R.id.mark);
        filmNameView = (TextView) activity.findViewById(R.id.tv_film_detail_filmname);
        filmUpdateView = (TextView) activity.findViewById(R.id.detail_update);
        filmUpdateNumberView = (TextView) activity.findViewById(R.id.detail_update_number);
        filmDirectorView = (TypeFaceTextView) activity.findViewById(R.id.tv_film_detail_line_one);
        filmActorView = (TypeFaceTextView) activity.findViewById(R.id.tv_film_detail_line_two);
        filmTypeView = (TypeFaceTextView) activity.findViewById(R.id.tv_film_detail_line_three);
        filmDescription = (TextView) activity.findViewById(R.id.tv_film_detail_description);

        // 按钮view
        fourPlayBtn = activity.findViewById(R.id.btn_film_detail_4play);
        playBtn = activity.findViewById(R.id.btn_film_detail_play);
        trySeeBtn = activity.findViewById(R.id.btn_film_detail_trySee);
        collectBtn = activity.findViewById(R.id.btn_film_detail_collect);
        eposideBtn = activity.findViewById(R.id.btn_film_detail_episode);
        collectHeartImage = activity.findViewById(R.id.iv_collect_ic);
        focusView.add(fourPlayBtn);
        focusView.add(playBtn);
        focusView.add(eposideBtn);
        focusView.add(collectBtn);
        focusView.add(trySeeBtn);

        // 按钮text
        fourPlayText = (TypeFaceTextView) activity.findViewById(R.id.tv_film_detail_4play);

        playText = (TypeFaceTextView) activity.findViewById(R.id.tv_film_detail_play);

        collectText = (TypeFaceTextView) activity.findViewById(R.id.tv_film_detail_collect);
        eposideText = (TypeFaceTextView) activity.findViewById(R.id.tv_film_detail_episode);
        trySeeText = (TypeFaceTextView) activity.findViewById(R.id.tv_film_detail_trySee);

        // 右侧提示
        rl_right_tip = (RelativeLayout) activity.findViewById(R.id.rl_right_tip);
        //左侧提示
        rl_left_tip = (RelativeLayout) activity.findViewById(R.id.rl_left_tip);

        noticeView = (NoticeView) activity.findViewById(R.id.ll_notice_container);

        noticeView.addImageRes(R.drawable.ic_notice_notice)
                .addText(activity.getString(R.string.view_press))
                .addImageRes(R.drawable.ic_notice_up_down)
                .addText(activity.getString(R.string.view_flip))
                .addImageRes(R.drawable.ic_notice_right)
                .addText(activity.getString(R.string.view_recommentation)).display();

        //获取布局控件以及设置控件监听
        setFocusColors();
        setListener();
        makeCollectAnimator();
        resetFocusColor(UserStateUtil.getInstance().getUserStatus());
    }


    public void setExtras(int subjectId, String subjectName, String source) {
        if (source != null) {
            this.source = source;
        }
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public void setArguments(AlbumEntity entity) {
        this.entity = entity;
    }

    // 设置主页面的数据（FilmDetailHome），分别判断数据的有无，根据是否是多片详情来判断是否显示剧集。
    public void setData(AlbumEntity albumEntity) {
        this.entity = albumEntity;

        if (entity == null) {
            return;
        }
        //显示海报和vip图标
        setCoverImage(entity);

        filmNameView.setText(entity.getAlbumName());
        if (null != entity.getDirectors() && !"".equals(entity.getDirectors()) && !"null".equals(entity.getDirectors())) {
            filmDirectorView.setVisibility(View.VISIBLE);
            filmDirectorView.setText(activity.getResources().getString(R.string.subject_textinfo_director) + entity.getDirectors());
        } else {
            filmDirectorView.setVisibility(View.GONE);
        }
        if (null != entity.getMainActors() && !"".equals(entity.getMainActors()) && !"null".equals(entity.getMainActors())) {
            filmActorView.setVisibility(View.VISIBLE);
            filmActorView.setText(activity.getResources().getString(R.string.subject_textinfo_actor) + entity.getMainActors());
        } else {
            filmActorView.setVisibility(View.GONE);
        }
        if (null != entity.getLabels() && !"".equals(entity.getLabels()) && !"null".equals(entity.getLabels())) {
            filmTypeView.setVisibility(View.VISIBLE);
            filmTypeView.setText(activity.getResources().getString(R.string.subject_textinfo_label) + entity.getLabels());
        } else {
            filmTypeView.setVisibility(View.GONE);
        }

        if (entity.getAlbumDesc() != null && !"".equals(entity.getAlbumDesc()) && !"null".equals(entity.getAlbumDesc())) {
            filmDescription.setVisibility(View.VISIBLE);
            filmDescription.setText(entity.getAlbumDesc());
        } else {
            filmDescription.setVisibility(View.INVISIBLE);
        }

        int showType = CloudScreenApplication.getInstance().getShowTypeByVideoTypeId(entity.getChnId());
        // 如果是电视剧或动漫则显示集数
        if (showType == ContentShowType.TYPE_MULTIPLE_VIDEO_DETAIL) {
            filmUpdateView.setVisibility(View.VISIBLE);
            if (entity.getEpisodeTotal() == entity.getEpisodeUpdated()) {
                filmUpdateView.setText(String.format(activity.getResources().getString(R.string.all_episode), entity.getEpisodeTotal() + ""));
                filmUpdateNumberView.setVisibility(View.INVISIBLE);
            } else {
                filmUpdateNumberView.setVisibility(View.VISIBLE);
                filmUpdateView.setText(String.format(activity.getResources().getString(R.string.all_episode), entity.getEpisodeTotal() + ""));
                filmUpdateNumberView.setText(String.format(activity.getResources().getString(R.string.update_videoset), entity.getEpisodeUpdated() + ""));
            }
        }
        // 如果是综艺则显示集数
        else if (showType == ContentShowType.TYPE_VARIETY_VIDEO_DETAIL) {
            Log.d(TAG, "entity==" + entity.toString());
            filmUpdateView.setVisibility(View.GONE);
            filmUpdateNumberView.setText(String.format(activity.getResources().getString(R.string.update_expect), entity.getYear() + ""));
        } else if (showType == ContentShowType.TYPE_SINGLE_VIDEO_DETAIL) {
            filmUpdateView.setVisibility(View.GONE);
            filmUpdateNumberView.setVisibility(View.GONE);
        }
        new Thread() {
            public void run() {
                isCollected = isCollected();
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        changeCollectionButtonState(isCollected);
                    }
                });
            }

            ;
        }.start();
    }

    /**
     * 设置预售价格，海报底部
     */
    public void setSalePrice() {
        if (productInfoEntity == null) {
            return;
        }
        //设置原价和非会员 显示中间横杠
        tvCurrentPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvNonMemberPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
            if (null != productInfoEntity.getVipNowPrice() && productInfoEntity.getVipNowPrice() >= 0) {//vip有优惠策略
                simpSpanUtil.buildSpecialTextStyle1(StringUtils.doubleTrans(productInfoEntity.getVipNowPrice()), tvVipPrice, activity.getApplicationContext());
                tvCurrentPrice.setText("原价" + StringUtils.doubleTrans(productInfoEntity.getPrice()) + "麦币");
                if (productInfoEntity.getNowPrice() >= 0) {
                    tvNonMemberPrice.setText("非会员价" + StringUtils.doubleTrans(productInfoEntity.getNowPrice()) + "麦币");
                } else {
                    tvNonMemberPrice.setVisibility(View.GONE);
                }
            } else if (null != productInfoEntity.getNowPrice() && productInfoEntity.getNowPrice() >= 0) {//vip无优惠策略,取普通用户优惠价格
                simpSpanUtil.buildSpecialTextStyle1(StringUtils.doubleTrans(productInfoEntity.getNowPrice()), tvVipPrice, activity.getApplicationContext());
                tvCurrentPrice.setText("原价" + StringUtils.doubleTrans(productInfoEntity.getPrice()) + "麦币");
                tvNonMemberPrice.setVisibility(View.GONE);
            } else {//没有任何优惠策略只显示原价
                if (productInfoEntity.getPrice() >= 0) {
                    simpSpanUtil.buildSpecialTextStyle1(StringUtils.doubleTrans(productInfoEntity.getPrice()), tvVipPrice, activity.getApplicationContext());
                    tvCurrentPrice.setVisibility(View.GONE);
                    tvNonMemberPrice.setVisibility(View.GONE);
                } else {
                    tvVipPrice.setVisibility(View.GONE);
                    tvCurrentPrice.setVisibility(View.GONE);
                    tvNonMemberPrice.setVisibility(View.GONE);
                }
            }
        } else {//设置普通用户价格
            if (null != productInfoEntity.getNowPrice() && productInfoEntity.getNowPrice() >= 0) {//普通用户有优惠策略
                simpSpanUtil.buildSpecialTextStyle1(StringUtils.doubleTrans(productInfoEntity.getNowPrice()), tvVipPrice, activity.getApplicationContext());
                tvCurrentPrice.setText("原价" + StringUtils.doubleTrans(productInfoEntity.getPrice()) + "麦币");
                if (null != productInfoEntity.getVipNowPrice() && productInfoEntity.getVipNowPrice() >= 0) {
                    tvNonMemberPrice.setText("会员价" + StringUtils.doubleTrans(productInfoEntity.getVipNowPrice()) + "麦币");
                } else {
                    tvNonMemberPrice.setVisibility(View.GONE);
                }
            } else {//普通用户无优惠策略显示原价
                if (null != productInfoEntity.getPrice() && productInfoEntity.getPrice() >= 0) {
                    simpSpanUtil.buildSpecialTextStyle1(StringUtils.doubleTrans(productInfoEntity.getPrice()), tvVipPrice, activity.getApplicationContext());
                    tvCurrentPrice.setVisibility(View.GONE);
                    if (null != productInfoEntity.getVipNowPrice() && productInfoEntity.getVipNowPrice() >= 0) {
                        tvNonMemberPrice.setText("会员价" + StringUtils.doubleTrans(productInfoEntity.getVipNowPrice()) + "麦币");
                    } else {
                        tvNonMemberPrice.setVisibility(View.GONE);
                    }
                } else {
                    tvVipPrice.setVisibility(View.GONE);
                    tvCurrentPrice.setVisibility(View.GONE);
                    tvNonMemberPrice.setVisibility(View.GONE);
                }

            }
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/6/25
     * @Description 查询剧集接口后更新最新更新到多少期的方法
     * 目前只有综艺更新，其他的不管
     */
    public void resetUpdateYear(EpisodeListEntity entity) {
        int showType = CloudScreenApplication.getInstance().getShowTypeByVideoTypeId(entity.getChnId());
        if (showType == ContentShowType.TYPE_VARIETY_VIDEO_DETAIL) {
            filmUpdateView.setVisibility(View.GONE);
            filmUpdateNumberView.setText(String.format(activity.getResources().getString(R.string.update_expect),
                    entity.getYear()));
        }
    }

    public boolean isFourButtonGone() {
        return isFourPlayShow;
    }

    /**
     * 获取显示的海报和vip图标
     */
    public void setCoverImage(AlbumEntity entity) {
        if (null != entity) {
            //显示海报图小角标
            if (null != entity.getCornerImage() && null != entity.getCornerImage()) {
                DisplayImageUtil.getInstance().displayImage(entity.getCornerImage(), filmCorner);
            } else {
                filmCorner.setImageDrawable(null);
            }
            String url = "";
            if (!TextUtils.isEmpty(entity.getAlbumXqyPic())) {
                url = entity.getAlbumXqyPic();
            }
            String urlSource = Uri.parse(url).getQueryParameter("source");
            if (!TextUtils.isEmpty(urlSource)) {
                if ("Qiyi".equals(urlSource)) {
                    url = DisplayImageUtil.createImgUrl(entity.getAlbumXqyPic(), true);
                }
            }
            if (null != filmImageView) {
                new DisplayImageUtil.Builder().setRound(20).setLoadingImage(ResourceProvider.getInstance().getDefaultCoverDetail())
                        .setEmptyUriImage(ResourceProvider.getInstance().getDefaultCoverDetail()).setErrorImage(ResourceProvider.getInstance().getDefaultCoverDetail()).build()
                        .displayImage(url, filmImageView);
            }
        }
    }

    /**
     * 根据极清码流和爱奇艺码流设置按钮是否显示和隐藏
     */
    public void setStreamHide(AlbumEntity albumEntity) {
        this.entity = albumEntity;
        Log.d(TAG, "entity = " + entity);
        if (entity == null) {
            return;
        }

        if (this.entity != null) {
            if (isFourStream() && isNomalStream()) {
                isFourPlayShow = true;
                isplayShow = true;
                Log.i(TAG, "All Steam");
            } else if (isFourStream()) {
                isFourPlayShow = true;
                isplayShow = false;
                Log.i(TAG, "极清 4k");

            } else if (isNomalStream()) {
                isFourPlayShow = false;
                isplayShow = true;
                Log.i(TAG, "AQY 720p");
            } else {
                isFourPlayShow = false;
                isplayShow = false;
                Log.i(TAG, "No Steam ");
            }
            if (isFourStream()) {
                isFourPlayShow = true;
                if (!isplayShow) {//标清按钮为true则不设置隐藏
                    isplayShow = false;
                }
                Log.i(TAG, "1080p_10M");
            }
            if (null != entity.getJqIsEffective() && null != entity.getAqyIsEffective()) {
                if (entity.getJqIsEffective() == 0 && entity.getAqyIsEffective() == 0) {
                    isFourPlayShow = false;
                    isplayShow = false;
                    collectBtn.requestFocus();
                    Toast.makeText(activity, R.string.data_exception, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "无码流");
                }
            }
            /**
             * 设置播放按钮显示，隐藏
             */
            if (isFourPlayShow && isplayShow) {//4k，超清都显示
                getFourButtonState(true);
                getNoneButtonState(true);
                initFourPlayFocus();
            } else if (isFourPlayShow) {//4k
                getFourButtonState(true);
                getNoneButtonState(false);
                initFourPlayFocus();
            } else if (isplayShow) {//超清
                getFourButtonState(false);
                getNoneButtonState(true);
                initPlayFocus();
            } else {//收藏
                getFourButtonState(false);
                getNoneButtonState(false);
                collectBtn.requestFocus();
                Toast.makeText(activity, R.string.data_exception,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 设置用户购买后可以观看时间
     */
    public void setUserBuySeeTime() {
        if (null != productInfoEntity) {
            tv_watchTime.setVisibility(View.VISIBLE);
            Log.d(TAG, "productInfoEntity = " + productInfoEntity);
            if (!productInfoEntity.isAuthenticationFlag()) {
                if (this.entity.getIsVip() == 1 && this.entity.getChargingType() == 1) {//vip不显示价格的，不开启动画
                    if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                    } else {
                        if (animalFlag == 0) {
                            filmPrice.startAnimation(animation);
                        }
                    }
                }
            }
            //显示价格
            if (null != this.entity.getIsVip() && null != this.entity.getChargingType()) {
                if (this.entity.getIsVip() == 0 && this.entity.getChargingType() == 1) {
                    if (productInfoEntity.isAuthenticationFlag()) {
                    } else {//未买
                        filmPrice.setVisibility(View.VISIBLE);
                        setSalePrice();
                    }

                } else if (this.entity.getIsVip() == 1 && this.entity.getChargingType() == 1) {
                    if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                        filmPrice.setVisibility(View.INVISIBLE);
                        tv_watchTime.setVisibility(View.GONE);
                    } else {
                        if (productInfoEntity.isAuthenticationFlag()) {
                            filmPrice.setVisibility(View.INVISIBLE);
                        } else {//未买
                            filmPrice.setVisibility(View.VISIBLE);
                            setSalePrice();
                        }
                    }
                } else {
                    filmPrice.setVisibility(View.INVISIBLE);
                }
            } else {
                filmPrice.setVisibility(View.INVISIBLE);
            }
            if (productInfoEntity.getPriceStyle() == 1) {//单片
                if (!productInfoEntity.isAuthenticationFlag()) {//未买
                    if (productInfoEntity.getSaleStyle() == 1) {//单一商品包
                        if (!TextUtils.isEmpty(productInfoEntity.getEffectiveHours())) {
                            simpSpanUtil.buildSpecialTextStyle2(productInfoEntity.getEffectiveHours() + "小时", tv_watchTime, activity);
                        }
                    } else if (productInfoEntity.getSaleStyle() == 2) {//集合产品包
                        if (!TextUtils.isEmpty(productInfoEntity.getSaleEndTime())) {
                            simpSpanUtil.buildSpecialTextStyle2(productInfoEntity.getSaleEndTime(), tv_watchTime, activity);
                        }
                    }
                } else {//已买
                    if (productInfoEntity.getSaleStyle() == 1) {//单一商品包
                        if (!TextUtils.isEmpty(productInfoEntity.getDurationEndTime())) {
                            simpSpanUtil.buildSpecialTextStyle3(productInfoEntity.getDurationEndTime(), tv_watchTime, activity);
                        }
                    } else if (productInfoEntity.getSaleStyle() == 2) {//集合产品包
                        if (!TextUtils.isEmpty(productInfoEntity.getDurationEndTime())) {
                            simpSpanUtil.buildSpecialTextStyle3(productInfoEntity.getDurationEndTime(), tv_watchTime, activity);
                        }
                    }
                }
            } else if (productInfoEntity.getPriceStyle() == 2) {//包月包年
                if (productInfoEntity.isAuthenticationFlag()) {//已买
                    if (!TextUtils.isEmpty(productInfoEntity.getDurationEndTime())) {
                        simpSpanUtil.buildSpecialTextStyle3(productInfoEntity.getDurationEndTime(), tv_watchTime, activity);
                    }
                } else {//未买
                    if (productInfoEntity.getDays() > 0) {
                        simpSpanUtil.buildSpecialTextStyle2(productInfoEntity.getDays() + "天", tv_watchTime, activity);
                    }
                }
            }
        }

    }

    /**
     * 设置试看按钮显示或隐藏
     */
    public void setTrySeeBtnStatus() {
        if (productInfoEntity != null) {
            if (productInfoEntity.getMultiSetNumbers() > 0 || productInfoEntity.getSingleSetMinutes() > 0) {//试看按钮
                trySeeText.setVisibility(View.VISIBLE);
                trySeeBtn.setVisibility(View.VISIBLE);
            } else {
                hideTrySeeBtn();
            }
        } else {
            hideTrySeeBtn();
        }

    }

    /**
     * @Author Spr_ypt
     * @Date 2017/3/2
     * @Description 设置试看按钮隐藏
     */
    private void hideTrySeeBtn() {
        if (trySeeBtn.hasFocus()) {
            //如果焦点还在试看上，隐藏之前让大麦极清按钮获取焦点，防止焦点丢失
            fourPlayBtn.requestFocus();
        }
        trySeeText.setVisibility(View.GONE);
        trySeeBtn.setVisibility(View.GONE);
    }

    /**
     * 设置按钮角标显示
     */
    public void setBtnIcon() {

        if (null != entity.getIsVip() && null != entity.getChargingType()) {
            //1️，isVip =0,isBuy=0；
            if (entity.getIsVip() == 0 && entity.getChargingType() == 0) {
                setTrySeeBtnStatus();
                setBtnStatues(R.drawable.button_default, R.drawable.button_default);
            }
            //2,isVip =1,isBuy=0
            if (entity.getIsVip() == 1 && entity.getChargingType() == 0) {
                setTrySeeBtnStatus();
                setBtnStatues(R.drawable.btn_4k_vip, R.drawable.button_default);
            }
            //3,isVip =0,isBuy=1
            if (entity.getIsVip() == 0 && entity.getChargingType() == 1) {
                if (!productInfoEntity.isAuthenticationFlag()) {
                    setTrySeeBtnStatus();
                }
                if (null != productInfoEntity) {
                    if (productInfoEntity.isAuthenticationFlag()) {
                        setBtnStatues(R.drawable.button_default, R.drawable.button_default);
                        return;
                    }
                    if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 1) {//预售
                        if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getBookingStartTime(), productInfoEntity.getLongTime(), productInfoEntity.getBookingEndTime())) {//在预售时间范围内
                            setBtnStatues(R.drawable.prepay, R.drawable.button_default);
                        } else if (StringUtils.currentTimeAfterOrderTime(productInfoEntity.getBookingEndTime())) {//非预售
                            setBtnStatues(R.drawable.paymoney, R.drawable.button_default);
                        }
                    } else if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 0) {//非预售
                        setBtnStatues(R.drawable.paymoney, R.drawable.button_default);
                    }
                }


            }
            //4,isVip =1,isBuy=1 vip用户免费 非Vip用户买后可以看
            if (entity.getIsVip() == 1 && entity.getChargingType() == 1) {
                if (null != productInfoEntity) {
                    if (productInfoEntity.isAuthenticationFlag()) {
                        setBtnStatues(R.drawable.button_default, R.drawable.button_default);
                        //购买后设置试看按钮消失
                        hideTrySeeBtn();
                        return;
                    }
                    if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 1) {//预售
                        if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getBookingStartTime(), productInfoEntity.getLongTime(), productInfoEntity.getBookingEndTime())) {//在预售时间范围内
                            setTrySeeBtnStatus();
                            setBtnStatues(R.drawable.prepay, R.drawable.button_default);
                        } else if (StringUtils.currentTimeAfterOrderTime(productInfoEntity.getBookingEndTime())) {//非预售
                            if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                                setBtnStatues(R.drawable.btn_4k_vip, R.drawable.button_default);
                            } else {
                                if (!productInfoEntity.isAuthenticationFlag()) {
                                    setTrySeeBtnStatus();
                                }
                                if (productInfoEntity.getNowPrice() != null && productInfoEntity.getNowPrice() == 0) {//限时优惠现实免费图标
                                    setBtnStatues(R.drawable.button_default, R.drawable.button_default);
                                    //限时免费试看按钮消失
                                    hideTrySeeBtn();
                                } else {
                                    setBtnStatues(R.drawable.paymoney, R.drawable.button_default);
                                }
                            }
                        }
                    } else if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 0) {
                        if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                            setBtnStatues(R.drawable.btn_4k_vip, R.drawable.button_default);
                        } else {
                            if (!productInfoEntity.isAuthenticationFlag()) {
                                setTrySeeBtnStatus();
                            }
                            if (productInfoEntity.getNowPrice() != null && productInfoEntity.getNowPrice() == 0) {//限时优惠现实免费图标
                                setBtnStatues(R.drawable.button_default, R.drawable.button_default);
                                //限时免费试看按钮消失
                                hideTrySeeBtn();
                            } else {
                                setBtnStatues(R.drawable.paymoney, R.drawable.button_default);
                            }
                        }
                    }
                }

            }

        }
    }

    /**
     * 设置按钮角标
     */
    private void setBtnStatues(int fourBtnDrawable, int normalBtnDrawable) {
        fourPlayText.setBackgroundResource(fourBtnDrawable);
        playText.setBackgroundResource(normalBtnDrawable);
    }

    /**
     * 是否有4k流
     */
    public boolean isFourStream() {
        return null != entity.getJqIsEffective() && entity.getJqIsEffective() == 1 && !TextUtils.isEmpty(entity.getJqId());
    }

    /**
     * 是否有标清流
     *
     * @return
     */
    public boolean isNomalStream() {
        return null != entity.getAqyIsEffective() && entity.getAqyIsEffective() == 1 && !TextUtils.isEmpty(entity.getAqyId());
    }

    public void VisRightTip() {
        rl_right_tip.setVisibility(View.VISIBLE);
    }

    Animation animation;
    private static final int DELAY_ONE = 100;
    private static final int DELAY_TWO = 200;
    private static final int DELAY_THREE = 300;
    private static final int DELAY_FOUR = 400;

    /**
     * 详情页打开按钮动画
     */
    public void activityOpenBtnAnimation() {
        //按钮动画
        fourPlayBtn.animate().alpha(1f).translationX(0).setStartDelay(DELAY_ONE + 100).start();

        playBtn.animate().alpha(1f).translationX(0).setStartDelay(DELAY_TWO + 150).start();
        eposideBtn.animate().alpha(1f).translationX(0).setStartDelay(DELAY_THREE + 200).start();
        trySeeBtn.animate().alpha(1f).translationX(0).setStartDelay(DELAY_FOUR + 250).start();
        collectBtn.animate().alpha(1f).translationX(0).setStartDelay(DELAY_FOUR + 300).start();

        //按钮文本动画
        fourPlayText.animate().alpha(1f).translationX(0).setStartDelay(DELAY_ONE + 100).start();
        playText.animate().alpha(1f).translationX(0).setStartDelay(DELAY_TWO + 150).start();
        eposideText.animate().alpha(1f).translationX(0).setStartDelay(DELAY_THREE + 200).start();
        trySeeText.animate().alpha(1f).translationX(0).setStartDelay(DELAY_FOUR + 250).start();
        collectText.animate().alpha(1f).translationX(0).setStartDelay(DELAY_FOUR + 300).start();
    }

    /**
     * 详情页打开海报和箭头动画
     */
    public void activityOpenPosterAnimation() {
        isOnAnim = true;
        noticeView.animate().alpha(1f).translationX(0).setStartDelay(DELAY_THREE + 200).start();
        //提醒箭头移动动画
        rl_right_tip.animate().translationX(0).setStartDelay(400 + 200).start();
        rl_left_tip.animate().translationX(0).setStartDelay(400 + 200).start();
        rl_tv_dec.animate().alpha(1).setDuration(800).setInterpolator(new AccelerateInterpolator()).start();

        //海报图动画
        animation = AnimationUtils.loadAnimation(activity, R.anim.detail_conver_in);
        coverContainer.setVisibility(View.VISIBLE);
        coverContainer.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                DisplayImageUtil.getInstance().pause();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isOnAnim = false;
                if (null != openlistener) {
                    openlistener.isEndAnimator(true);
                }
                DisplayImageUtil.getInstance().resume();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    /**
     * @Author Spr_ypt
     * @Date 2016/6/23
     * @Description 将所有view重置为动画开始前的样子
     */
    public void resetAllViewBeforeAnim() {
        //初始化按钮位置
        fourPlayBtn.setTranslationX(400);
        fourPlayBtn.setAlpha(0f);

        playBtn.setTranslationX(400);
        playBtn.setAlpha(0f);
        collectBtn.setTranslationX(400);
        collectBtn.setAlpha(0f);
        eposideBtn.setTranslationX(400);
        eposideBtn.setAlpha(0f);
        trySeeBtn.setTranslationX(400);
        trySeeBtn.setAlpha(0f);

        //初始化按钮文本位置
        fourPlayText.setTranslationX(400);
        fourPlayText.setAlpha(0f);
        playText.setTranslationX(400);
        playText.setAlpha(0f);
        collectText.setTranslationX(400);
        collectText.setAlpha(0f);
        noticeView.setTranslationX(400);
        noticeView.setAlpha(0f);
        eposideText.setTranslationX(400);
        eposideText.setAlpha(0f);
        trySeeText.setTranslationX(400);
        trySeeText.setAlpha(0f);

        //初始化箭头
        rl_right_tip.setTranslationX(150);
        rl_left_tip.setTranslationX(-150);
        rl_tv_dec.setAlpha(0f);

        //初始化海报图
        filmImageView.setImageResource(ResourceProvider.getInstance().getDefaultCoverDetail());
        coverContainer.setVisibility(View.INVISIBLE);
    }

    public boolean isOnAnim() {
        return isOnAnim;
    }

    OpenActivityAnimatorListener openlistener;

    public void setOpenActivityAnimatorListener(OpenActivityAnimatorListener openlistener) {
        this.openlistener = openlistener;
    }

    /**
     * 4k按钮是否显示
     *
     * @return
     */
    public boolean isFourPlayShow() {
        return isFourPlayShow;
    }

    /**
     * 720按钮是否显示
     *
     * @return
     */
    public boolean isplayShow() {
        return isplayShow;
    }

    /**
     * 返回点击哪个播放按钮
     *
     * @return
     */
    public int clickFlag() {
        return clickFlag;
    }

    /**
     * 设置商品信息bean
     */
    public void setProductInfo(ProductInfoEntity productInfoEntity) {
        this.productInfoEntity = productInfoEntity;
    }

    public interface OpenActivityAnimatorListener {
        void isEndAnimator(boolean ishas);
    }

    public void resetFocusColor(UserStateUtil.UserStatus userStatus) {
        switch (userStatus) {
            case VIPUSER:
                for (View v : focusView) {
                    v.setBackgroundResource(R.drawable.focused_view_vip_selector);
                }
                break;
            default:
                for (View v : focusView) {
                    v.setBackgroundResource(R.drawable.focused_selector);
                }
                break;
        }
    }

    /**
     * 初始化焦点位置
     */
    public void initFourPlayFocus() {
        fourPlayBtn.requestFocus();
        fourPlayBtn.requestFocusFromTouch();
    }

    /**
     * 初始化焦点位置
     */
    public void initPlayFocus() {
        playBtn.requestFocus();
        playBtn.requestFocusFromTouch();
    }

    /**
     * 提供方法【根据是否获取焦点改变字体颜色 】
     */
    private void setFocusColors() {
        int focusColor = Color.parseColor("#FFFFFF");
        int lostFocusColor = Color.parseColor("#9a9a9a");

        fourPlayText.setFocusColor(focusColor);
        fourPlayText.setLostFocusColor(lostFocusColor);

        playText.setFocusColor(focusColor);
        playText.setLostFocusColor(lostFocusColor);
        collectText.setFocusColor(focusColor);
        collectText.setLostFocusColor(lostFocusColor);
        eposideText.setFocusColor(focusColor);
        eposideText.setLostFocusColor(lostFocusColor);
        trySeeText.setFocusColor(focusColor);
        trySeeText.setLostFocusColor(lostFocusColor);
    }


    private void setListener() {
        // 按钮监听事件
        fourPlayBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        collectBtn.setOnClickListener(this);
        trySeeBtn.setOnClickListener(this);
        eposideBtn.setOnClickListener(this);

        // 按钮焦点监听，改变字体颜色
        fourPlayBtn.setOnFocusChangeListener(this);
        playBtn.setOnFocusChangeListener(this);
        collectBtn.setOnFocusChangeListener(this);
        trySeeBtn.setOnFocusChangeListener(this);
        eposideBtn.setOnFocusChangeListener(this);

        fourPlayBtn.setOnKeyListener(this);
        playBtn.setOnKeyListener(this);
        collectBtn.setOnKeyListener(this);
        trySeeBtn.setOnKeyListener(this);
        eposideBtn.setOnKeyListener(this);
    }


    public void setLostBtnFocus() {
        fourPlayBtn.setFocusable(false);
        playBtn.setFocusable(false);
        collectBtn.setFocusable(false);
        trySeeBtn.setFocusable(false);
        eposideBtn.setFocusable(false);
    }

    public void setBtnFocus() {
        fourPlayBtn.setFocusable(true);
        playBtn.setFocusable(true);
        collectBtn.setFocusable(true);
        trySeeBtn.setFocusable(true);
        eposideBtn.setFocusable(true);
    }

    private int lastFocusedButton;

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (focusChangeListener != null) {
            focusChangeListener.onFocusChange(v, hasFocus);
        }
        if (hasFocus) {
            switch (v.getId()) {

                case R.id.btn_film_detail_4play:
                    fourPlayText.setFocusFlag(true);
                    Log.d(TAG, "4k获取焦点");
                    break;
                case R.id.btn_film_detail_play:
                    playText.setFocusFlag(true);
                    Log.d(TAG, "720获取焦点");
                    break;
                case R.id.btn_film_detail_collect:
                    collectText.setFocusFlag(true);
                    break;
                case R.id.btn_film_detail_episode:
                    eposideText.setFocusFlag(true);
                    break;
                case R.id.btn_film_detail_trySee:
                    trySeeText.setFocusFlag(true);
                    break;
                default:
                    break;
            }
        } else {
            switch (v.getId()) {
                case R.id.btn_film_detail_4play:
                    lastFocusedButton = R.id.btn_film_detail_4play;
                    fourPlayText.setFocusFlag(false);
                    break;
                case R.id.btn_film_detail_play:
                    lastFocusedButton = R.id.btn_film_detail_play;
                    playText.setFocusFlag(false);
                    break;
                case R.id.btn_film_detail_collect:
                    lastFocusedButton = R.id.btn_film_detail_collect;
                    collectText.setFocusFlag(false);
                    break;
                case R.id.btn_film_detail_episode:
                    lastFocusedButton = R.id.btn_film_detail_episode;
                    eposideText.setFocusFlag(false);
                    break;
                case R.id.btn_film_detail_trySee:
                    lastFocusedButton = R.id.btn_film_detail_trySee;
                    trySeeText.setFocusFlag(false);
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * 最后一个失去焦点的按钮，重新获取焦点
     */
    public void lastFocusButtonRequestFocus() {
        switch (lastFocusedButton) {
            case R.id.btn_film_detail_4play:
                fourPlayBtn.requestFocus();
                fourPlayBtn.requestFocusFromTouch();
                fourPlayText.setFocusFlag(true);
                break;
            case R.id.btn_film_detail_play:
                playBtn.requestFocus();
                playBtn.requestFocusFromTouch();
                playText.setFocusFlag(true);
                break;
            case R.id.btn_film_detail_collect:
                collectBtn.requestFocus();
                collectBtn.requestFocusFromTouch();
                collectText.setFocusFlag(true);
                break;
            case R.id.btn_film_detail_episode:
                eposideBtn.requestFocus();
                eposideBtn.requestFocusFromTouch();
                eposideText.setFocusFlag(true);
                break;
            case R.id.btn_film_detail_trySee:
                trySeeBtn.requestFocus();
                trySeeBtn.requestFocusFromTouch();
                trySeeText.setFocusFlag(true);
                break;

        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        clickListener = listener;
    }

    public void setOnFocusChangeListener(View.OnFocusChangeListener listener) {
        focusChangeListener = listener;
    }

    public void setOnKeyListener(View.OnKeyListener listener) {
        keyListener = listener;
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (null != keyListener) {
            keyListener.onKey(v, keyCode, event);
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClick(v);
        }
        if (v.getId() == R.id.btn_film_detail_4play) {
            clickBtnFlag = 1;
            VipVideoDetailActivity.isAction = true;
            //  统计4k点击行为
            if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                BaseActionInfo filmInfo = new VIPFilmDetailActionInfo("03", "sde0303", entity.getChnId() + "", subjectId + "", subjectName, "6", null, null, "1", "1", entity.getProgramsetId() + "", entity.getAlbumName());
                new Statistics(activity, filmInfo).send();
            } else {
                BaseActionInfo filmInfo = new VIPFilmDetailActionInfo("03", "sde0303", entity.getChnId() + "", subjectId + "", subjectName, "6", null, null, "2", "1", entity.getProgramsetId() + "", entity.getAlbumName());
                new Statistics(activity, filmInfo).send();
            }

            if (entity.getChargingType() == 1) {

                requestServiceTime();

            } else {

                if (DeviceInfoUtil.getInstance().getDeviceInfo(activity).white == 1) {//开启白名单模式
                    whiteUserFreeSeeFilm();
                    return;
                }
                //1,isVip =0,isBuy=0
                if (entity.getIsVip() == 0 && entity.getChargingType() == 0) {
                    openPlay(clickFlag);
                }
                //2,isVip =1,isBuy=0
                if (entity.getIsVip() == 1 && entity.getChargingType() == 0) {
                    buttonStatusShowDialog(UserStateUtil.getInstance().getUserStatus(), 1);
                }
            }
        }

        if (v.getId() == R.id.btn_film_detail_play) {//超清
            VipVideoDetailActivity.isAction = true;
            clickBtnFlag = 2;

            //  统计播放点击行为
            if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                BaseActionInfo filmInfo = new VIPFilmDetailActionInfo("03", "sde0303", entity.getChnId() + "", subjectId + "", subjectName, "6", null, null, "1", "2", entity.getProgramsetId() + "", entity.getAlbumName());
                new Statistics(activity, filmInfo).send();
            } else {
                BaseActionInfo filmInfo = new VIPFilmDetailActionInfo("03", "sde0303", entity.getChnId() + "", subjectId + "", subjectName, "6", null, null, "2", "2", entity.getProgramsetId() + "", entity.getAlbumName());
                new Statistics(activity, filmInfo).send();
            }

            openPlay(clickBtnFlag);
        }

        if (v.getId() == R.id.btn_film_detail_collect) {
            VipVideoDetailActivity.isAction = true;
            new Thread() {
                public void run() {
                    if (isCollected) {
                        if (null != entity) {
                            CollectDataUtil.getInstance().deleteCollect(entity.getProgramsetId());

                            isCollected = false;
                        } else {
                            // TODO 提示
                        }
                    } else {
                        if (null != entity) {
                            CollectDataUtil.getInstance().addCollect(entity);

                            isCollected = true;
                        } else {
                            // TODO 提示
                        }
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            changeCollectionButtonState(isCollected);
                        }
                    });
                }
            }.start();
            //  统计收藏点击行为
            if (null != entity) {
                BaseActionInfo filmInfo = new VIPFilmDetailActionInfo("03", "sde0303", entity.getChnId() + "", subjectId + "", subjectName, source, null, null, null, "3", entity.getProgramsetId() + "", entity.getAlbumName());
                new Statistics(activity, filmInfo).send();
            }
        }
        if (v.getId() == R.id.btn_film_detail_trySee) {//试看
            clickBtnFlag = 4;
            openPlay(clickBtnFlag);

        }


    }

    /**
     * 获取当前访问服务器时间
     */
    private void requestServiceTime() {
        CloudScreenService.getInstance().getServiceTime(new ServiceTimeListener(activity));
    }

    private class ServiceTimeListener implements OnRequestResultListener<ResultEntity<ServiceTimeEntity>> {
        private WeakReference<Activity> reference;

        private ServiceTimeListener(Activity activity) {
            reference = new WeakReference<Activity>(activity);
        }

        @Override
        public void onSucess(ResultEntity<ServiceTimeEntity> resultEntity) {
            Activity acitivity = reference.get();
            if (acitivity != null) {
                ServiceTimeEntity serviceTimeEntity = (ServiceTimeEntity) resultEntity.getEntity();
                productInfoEntity.setLongTime(serviceTimeEntity.getLongTime());//设置服务器最新时间
                Log.e(TAG, "getServiceTime Success *** serviceTimeEntity.getLongTime() = " + serviceTimeEntity.getLongTime());
                if (clickBtnFlag == 1) {//点击极清btn
                    if (DeviceInfoUtil.getInstance().getDeviceInfo(activity).white == 1) {//白名单模式
                        whiteUserFreeSeeFilm();
                        return;
                    }
                    //2,isVip =0,isBuy=1  vip买 普通用户买
                    if (entity.getIsVip() == 0 && entity.getChargingType() == 1) {
                        if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 1) {
                            preSaleLogic1();
                        } else if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 0) {
                            saleLogic1();
                        } else {
                            Log.d(TAG, "预售参数配置错误");
                        }
                    }
                    //3,isVip =1,isBuy=1  VIP用户看普通用户买
                    if (entity.getIsVip() == 1 && entity.getChargingType() == 1) {
                        if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 1) {
                            preSaleLogic2();
                        } else if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 0) {
                            saleLogic2();
                        } else {
                            Log.d(TAG, "预售参数配置错误");
                        }
                    }

                } else if (clickBtnFlag == 2) {//点击超清btn
                    if (DeviceInfoUtil.getInstance().getDeviceInfo(activity).white == 1) {//开启白名单模式
                        whiteUserFreeSeeFilm();
                        return;
                    }
                    //3,已购买显示普通按钮 isVip =0,isBuy=1  vip买 普通用户买
                    if (entity.getIsVip() == 0 && entity.getChargingType() == 1) {
                        if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 1) {
                            preSaleLogic1();
                        } else if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 0) {
                            saleLogic1();
                        } else {
                            Log.d(TAG, "预售参数配置错误");
                        }
                    }
                    //4,已购买显示普通按钮 isVip =1,isBuy=1  VIP用户看普通用户买
                    if (entity.getIsVip() == 1 && entity.getChargingType() == 1) {
                        if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 1) {
                            preSaleLogic2();
                        } else if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 0) {
                            saleLogic2();
                        } else {
                            Log.d(TAG, "预售参数配置错误");
                        }
                    }
                }
            }
        }

        @Override
        public void onFail(Exception e) {
            ToastUtil.showToast(activity, "获取服务器时间失败，请重试！", 0);

        }

        @Override
        public void onParseFail(HiveviewException e) {
            ToastUtil.showToast(activity, "获取服务器时间失败，请重试！", 0);
        }
    }

    /**
     * 销售售期逻辑代码(isvip = 0, isbuy = 1)
     */
    private void saleLogic1() {
        if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getSaleStartTime(), productInfoEntity.getLongTime(), productInfoEntity.getSaleEndTime())) {//非预售
            if (productInfoEntity.isAuthenticationFlag()) {//该用户销售期已购买
                Log.d(TAG, "4k 720（isVip =0,isBuy=1）销售期已购买");
                openPlay(clickBtnFlag);
                return;
            } else {
                Log.d(TAG, "4k 720（isVip =0,isBuy=1）销售期未购买");
                //4k
                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {//VIP用户
                    if (null != productInfoEntity.getVipNowPrice() && productInfoEntity.getVipNowPrice() == 0) {
                        Log.d(TAG, "限时免费，优惠价格0麦币");
                        openPlay(clickFlag);

                    } else {
                        Log.d(TAG, "4k 720（isVip =0,isBuy=1）VIP用户无试看，支付");
                        openPayMoneyPage(-1);
                    }

                } else {//非vip用户或者未登录用户
                    Log.d(TAG, "4k 720 非预售（isVip =0,isBuy=1）非VIP用户");
                    buttonStatusShowDialog(UserStateUtil.getInstance().getUserStatus(), 2);
                }
            }
        } else {
            if (productInfoEntity.isAuthenticationFlag()) {//已买
                openPlay(clickBtnFlag);
            } else {
                ToastUtil.showToast(activity, "销售期已过，此影片不能购买", 0);
            }
        }
    }

    /**
     * 销售售期逻辑代码(isvip = 1, isbuy = 1)
     */
    private void saleLogic2() {
        if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getSaleStartTime(), productInfoEntity.getLongTime(), productInfoEntity.getSaleEndTime())) {//非预售
            if (productInfoEntity.isAuthenticationFlag()) {//该用户销售期已购买
                Log.d(TAG, "4k 720（isVip =1,isBuy=1）销售期已购买");
                openPlay(clickBtnFlag);
                return;
            } else {
                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                    openPlay(clickBtnFlag);
                    Log.d(TAG, "4k 720 非预售（isVip =1,isBuy=1）VIP用户直接观看");
                } else {
                    Log.d(TAG, "4k 720 非预售（isVip =0,isBuy=1）非VIP用户");
                    buttonStatusShowDialog(UserStateUtil.getInstance().getUserStatus(), 2);
                }
            }
        } else {
            if (productInfoEntity.isAuthenticationFlag()) {//已买
                openPlay(clickBtnFlag);
            } else {
                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                    openPlay(clickBtnFlag);
                } else {
                    ToastUtil.showToast(activity, "销售期已过，此影片不能购买", 0);

                }
            }
        }
    }

    /**
     * 白名单模式免费观看影片
     */
    private void whiteUserFreeSeeFilm() {
        if (null != productInfoEntity) {
            if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 1) {//预售阶段
                if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getBookingStartTime(), productInfoEntity.getLongTime(), productInfoEntity.getBookingEndTime())) {//在预售范围
                    showBuyedDiaLog("影片将在" + StringUtils.formatDate(productInfoEntity.getBookingEndTime()) + "正式放映,欢迎届时观看。");
                } else if (StringUtils.currentTimeAfterOrderTime(productInfoEntity.getBookingEndTime())) {//无预购直接观看
                    openPlay(clickBtnFlag);
                }
            } else if (null != productInfoEntity.getIsBooking() && productInfoEntity.getIsBooking() == 0) {//销售阶段
                openPlay(clickBtnFlag);
            } else {
                Log.e(TAG, "null == productInfoEntity.getIsBooking()");
            }
        } else {
            openPlay(clickBtnFlag);
        }
    }

    /**
     * 预售期逻辑代码(isvip = 0, isbuy = 1)
     */
    private void preSaleLogic1() {
        if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getBookingStartTime(), productInfoEntity.getLongTime(), productInfoEntity.getBookingEndTime())) { //有预售
            if (productInfoEntity.isAuthenticationFlag()) {//该用户销售期已购买
                Log.d(TAG, "4k 720（isVip =0,isBuy=1）销售期已购买");
                showBuyedDiaLog("您购买的内容将在" + StringUtils.formatDate(productInfoEntity.getBookingEndTime()) + "正式放映,欢迎届时观看。");
                return;
            } else {
                Log.d(TAG, "4k 720（isVip =0,isBuy=1）销售期未购买");
                //4k
                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {//VIP用户
                    if (null != productInfoEntity.getVipNowPrice() && productInfoEntity.getVipNowPrice() == 0) {
                        showBuyedDiaLog("影片将在" + StringUtils.formatDate(productInfoEntity.getBookingEndTime()) + "正式放映,欢迎届时观看。");
                    } else {
                        openPayMoneyPage(-1);
                    }
                } else {
                    Log.d(TAG, "4k 720 预售（isVip =0,isBuy=1）非VIP用户");
                    buttonStatusShowDialog(UserStateUtil.getInstance().getUserStatus(), 2);
                }
            }
        } else if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getBookingEndTime(), productInfoEntity.getLongTime(), productInfoEntity.getSaleEndTime())) {//非预售
            if (productInfoEntity.isAuthenticationFlag()) {//该用户销售期已购买
                Log.d(TAG, "4k 720（isVip =0,isBuy=1）销售期已购买");
                openPlay(clickBtnFlag);
                return;
            } else {
                Log.d(TAG, "4k 720（isVip =0,isBuy=1）销售期未购买");
                //4k
                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {//VIP用户
                    if (null != productInfoEntity.getVipNowPrice() && productInfoEntity.getVipNowPrice() == 0) {
                        openPlay(clickFlag);
                        Log.d(TAG, "限时免费优惠价格0麦币");
                    } else {
                        Log.d(TAG, "4k 720（isVip =0,isBuy=1）VIP用户，支付");
                        openPayMoneyPage(-1);
                    }
                } else {//非vip用户或者未登录用户
                    Log.d(TAG, "4k 720 非预售（isVip =0,isBuy=1）非VIP用户");
                    buttonStatusShowDialog(UserStateUtil.getInstance().getUserStatus(), 2);
                }
            }
        } else {
            if (productInfoEntity.isAuthenticationFlag()) {//已买
                openPlay(clickBtnFlag);
            } else {
                ToastUtil.showToast(activity, "销售期已过，此影片不能购买", 0);
            }
        }
    }

    /**
     * 预售期逻辑代码(isvip = 1, isbuy = 1)
     */
    private void preSaleLogic2() {
        if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getBookingStartTime(), productInfoEntity.getLongTime(), productInfoEntity.getBookingEndTime())) { //预售情况
            if (productInfoEntity.isAuthenticationFlag()) {//该用户销售期已购买
                showBuyedDiaLog("您购买的内容将在" + StringUtils.formatDate(productInfoEntity.getBookingEndTime()) + "正式放映,欢迎届时观看。");

                return;
            } else {
                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                    showBuyedDiaLog("影片将在" + StringUtils.formatDate(productInfoEntity.getBookingEndTime()) + "正式放映,欢迎届时观看。");
                } else {//非会员或者用户未登录
                    Log.d(TAG, "4k 720 预售（isVip =1,isBuy=1）非VIP用户");
                    buttonStatusShowDialog(UserStateUtil.getInstance().getUserStatus(), 2);
                }
            }

        } else if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getBookingEndTime(), productInfoEntity.getLongTime(), productInfoEntity.getSaleEndTime())) {//非预售
            if (productInfoEntity.isAuthenticationFlag()) {//该用户销售期已购买
                Log.d(TAG, "4k 720（isVip =1,isBuy=1）销售期已购买");
                openPlay(clickBtnFlag);
                return;
            } else {
                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                    openPlay(clickBtnFlag);
                    Log.d(TAG, "4k 720 非预售（isVip =1,isBuy=1）VIP用户直接观看");
                } else {
                    Log.d(TAG, "4k 720 非预售（isVip =0,isBuy=1）非VIP用户");
                    buttonStatusShowDialog(UserStateUtil.getInstance().getUserStatus(), 2);
                }
            }

        } else {
            if (productInfoEntity.isAuthenticationFlag()) {//已买
                openPlay(clickBtnFlag);
            } else {
                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                    openPlay(clickBtnFlag);
                } else {
                    ToastUtil.showToast(activity, "销售期已过，此影片不能购买", 0);

                }
            }
        }
    }

    /**
     * 显示购买成功的弹框
     */
    public void showBuyedDiaLog(String tip) {
        dialog = new VipVideoDialog(activity, tip, null, "确定", VipVideoDialog.DialogBg.D, false);
        dialog.setOnBtnClicklistener(new VipVideoDialog.OnBtnClickListener() {
            @Override
            public void onPositiveBtnClick() {
                dialog.dismiss();
            }

            @Override
            public void onNegativeBtnClick() {
                dialog.dismiss();
            }

            @Override
            public void onDismissWithoutPressBtn() {

            }
        });
        dialog.showWindow();
    }

    /**
     * 打开支付app
     */
    public void openPayMoneyPage(int clickFlag) {
        this.clickFlag = clickFlag;
        Intent intent = new Intent();
        intent.putExtra("target", 1);//int 型，0表示传递商品包id，1表示传递影片id
        intent.putExtra("userId", UserStateUtil.getInstance().getUserInfo().userId + "");
        intent.putExtra("videoId", entity.getProgramsetId() + "");//影片id
        intent.putExtra("videoType", "1");//影片类型(标记此影片的类型)
        intent.putExtra("templateId", DeviceInfoUtil.getInstance().getDeviceInfo(activity).templetId + "");//模板id
        intent.putExtra("packageName", "com.hiveview.cloudscreen.vipvideo");//包名
        intent.setAction("com.hiveview.cloudscreen.paycenter.RELATED_GOODS");
        Log.i(TAG, "打开支付app");
        activity.startActivity(intent);
    }


    @Override
    public void userStatusChanged() {
        if (nologin) {
            nologin = false;
            if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.NOLOGIN) {
                //登录失败
                BaseActionInfo info = new LoginDialogActionInfo("03", "vip0306", "2", "2", null);
                new Statistics(activity, info).send();//统计埋点
            } else {
                //登录成功
                BaseActionInfo info = new LoginDialogActionInfo("03", "vip0306", "2", "1", null);
                new Statistics(activity, info).send();//统计埋点
            }
        }

    }

    @Override
    public void userStatusNoChanged() {

    }

    /**
     * 预售4K按钮 的显示与隐藏
     */

    public void getFourButtonState(boolean isFourBtn) {
        Log.i(TAG, "4k播放是否显示：" + isFourBtn);
        if (isFourBtn) {
            fourPlayBtn.setVisibility(View.VISIBLE);
            fourPlayText.setVisibility(View.VISIBLE);
        } else {
            fourPlayBtn.setVisibility(View.GONE);
            fourPlayText.setVisibility(View.GONE);
        }
    }

    /**
     * 标清的显示与隐藏
     */
    public void getNoneButtonState(boolean flag) {
        Log.i(TAG, "标清播放是否显示：" + flag);
        if (flag) {
            playBtn.setVisibility(View.VISIBLE);
            playText.setVisibility(View.VISIBLE);
        } else {
            playBtn.setVisibility(View.GONE);
            playText.setVisibility(View.GONE);
        }
    }

    /**
     * 是否显示剧集
     */
    public boolean isEpisode = true;

    public void hideEposide() {
        eposideBtn.setVisibility(View.GONE);
        eposideText.setVisibility(View.GONE);
        isEpisode = false;
    }

    /**
     * 是否收藏
     */
    private boolean isCollected() {
        if (null != dao) {
            ArrayList<AlbumEntity> list = null;
            String where = CollectionDao.COLUMN_ID + " = ? AND " + CollectionDao.COLUMN_USER_ID + " = ? AND " + CollectionDao.COLUMN_SYN_STATE + "!= ? ";
            String[] selectionArgs = new String[]{entity.getProgramsetId() + "", UserStateUtil.getInstance().getUserInfo().userId, 2 + ""};
            list = (ArrayList<AlbumEntity>) dao.query(null, where, selectionArgs, null);
            isFirstDisplay = true;
            if (list.size() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 改变收藏按钮状态
     */
    private void changeCollectionButtonState(boolean isCollected) {
        if (isCollected) {
            if (!isFirstDisplay) {
                collectAnimator.start();
            }
            collectText.setText(R.string.film_detail_collecting);
        } else {
            collectHeartImage.setScaleX(0.5f);
            collectHeartImage.setScaleY(0.5f);
            collectText.setText(R.string.film_detail_collect);
        }
        isFirstDisplay = false;
    }

    /**
     * 收藏动画
     */
    private void makeCollectAnimator() {
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f, 0f);
        PropertyValuesHolder scaleXUp = PropertyValuesHolder.ofFloat("scaleX", 2f);
        PropertyValuesHolder scaleYUp = PropertyValuesHolder.ofFloat("scaleY", 2f);
        ObjectAnimator alphaAni = ObjectAnimator.ofPropertyValuesHolder(collectHeartImage, alpha);
        ObjectAnimator scaleAni = ObjectAnimator.ofPropertyValuesHolder(collectHeartImage, scaleXUp, scaleYUp);

        collectAnimator = new AnimatorSet();
        collectAnimator.setInterpolator(new LinearInterpolator());
        collectAnimator.setDuration(400);
        collectAnimator.playTogether(alphaAni, scaleAni);
    }

    private boolean nologin = false;

    /**
     * 启动播放器
     */
    public void openPlay(int clickFlag) {
        Log.d(TAG, "clickBtnFlag = " + clickFlag);
        String oldDetail = JSONObject.toJSONString(entity);
        String albumInfo = oldDetail;

        if (null != productInfoEntity) {
            String newDetail = oldDetail.substring(0, oldDetail.length() - 1);
            String oldProductInfo = JSONObject.toJSONString(productInfoEntity);
            String newProductInfo = oldProductInfo.substring(1, oldProductInfo.length());
            albumInfo = newDetail + "," + newProductInfo;
        }
        Intent intent = new Intent(VideoDetailInvoker.ACTION_PLAYER);
        intent.putExtra(AppConstants.EXTRA_ALBUM_ENTITY, albumInfo);
        switch (clickFlag) {
            case 1:
                intent.putExtra(AppConstants.EXTRA_IS_FROM_VIPPLAY, true);
                break;
            case 2:
                intent.putExtra(AppConstants.EXTRA_IS_FROM_BTN, true);
                break;
            case 3:
                intent.putExtra(AppConstants.EXTRA_IS_FROM_EPISODE, true);
                break;
            case 4:
                intent.putExtra(AppConstants.EXTRA_IS_FROM_TRYSEE, true);
                break;
        }
        intent.putExtra(AppConstants.EXTRA_SOURCE, source);
        intent.putExtra(AppConstants.EXTRA_SUBJECT_ID, subjectId);
        intent.putExtra(AppConstants.EXTRA_SUBJECT_NAME, subjectName);
        Log.i(TAG, "qiyi source:" + source);
        activity.startActivity(intent);
    }

    /**
     * 打开详情描述动画
     */
    public void openDescription() {
        all_content.animate().translationX(-40).setDuration(600).setInterpolator(new
                AccelerateInterpolator()).start();
        rl_left_tip.setVisibility(View.INVISIBLE);
        rl_left_tip.requestFocus();
    }

    /**
     * 值不可改，即监听动画结束又用做返回事件的处理
     */
    public boolean isLeft = false;

    //关闭详情描述动画
    public void closeDescription() {
        isLeft = true;
        all_content.animate().translationX(activity.getResources().getDimension
                (R.dimen.all_content_translationX_returnMid)).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isLeft) {
                    rl_left_tip.setVisibility(View.VISIBLE);
                    rl_left_tip.animate().translationX(0).setDuration(300).setInterpolator(new AccelerateInterpolator()).start();
                    isLeft = false;
                }
                super.onAnimationEnd(animation);
            }
        }).setDuration(600).setInterpolator(new
                AccelerateInterpolator()).start();
    }

    /**
     * 关闭相关推荐动画接口监听
     */
    public isRecommentAnimationListener isRecommentAnimationListener;

    public void setOnRecommentAnimationListener(isRecommentAnimationListener isRecommentAnimationListener) {
        this.isRecommentAnimationListener = isRecommentAnimationListener;
    }

    public interface isRecommentAnimationListener {
        void animaEnd(boolean isEnd);
    }

    /**
     * 值不可该，即监听动画结束又用做返回事件的处理
     */
    public boolean isRight = false;

    //关闭相关推荐执行动画操作
    public void closeRecomment() {
        isRight = true;
        rl_film_middle.animate().translationXBy(-rl_film_middle.getTranslationX()).setDuration
                (400).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (null != isRecommentAnimationListener) {
                    isRight = false;
                    isRecommentAnimationListener.animaEnd(true);
                }
                super.onAnimationEnd(animation);
            }
        }).setInterpolator(new AccelerateInterpolator()).start();
    }

    /**
     * 打开相关推荐动画
     */
    public void openRecomment() {
        rl_film_middle.animate().translationX(activity.getResources().getDimension
                (R.dimen.all_content_translationX_toRight)).setDuration(400).setInterpolator(new
                AccelerateInterpolator()).start();
        rl_right_tip.setVisibility(View.GONE);
    }

    /**
     * 获取收藏按钮
     */
    public View getCollectionBtn() {
        return collectBtn;
    }

    /**
     * 显示 弹框消失后的按钮焦点
     */
    public void showFocus() {
        if (null != activity.getCurrentFocus()) {
            if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                activity.getCurrentFocus().setBackgroundResource(R.drawable.focused_view_vip_selector);
            } else {
                activity.getCurrentFocus().setBackgroundResource(R.drawable.focused_view_selector);
            }

        }
    }

    /**
     * 详情页内，用户需登录、需开通vip的弹框文案及统计埋点
     *
     * @param userStatus 用户状态
     * @param idStatus
     */
    private void buttonStatusShowDialog(UserStateUtil.UserStatus userStatus, int idStatus) {
        if (idStatus == 1) {
            switch (userStatus) {
                case NOLOGIN:
                    userStatusDialog = new VipVideoDialog(activity, activity.getString(R.string.dialog_vip_log_statue), activity.getString(R.string.dialog_login_ok), activity.getString(R.string.dialog_login_cancel), VipVideoDialog.DialogBg.D, false);
                    userStatusDialog.setOnBtnClicklistener(new VipVideoDialog.OnBtnClickListener() {
                        @Override
                        public void onPositiveBtnClick() {
                            nologin = true;
                            userStatusDialog.dismiss();
                            showFocus();
                            //跳转登录
                            UserStateUtil.getInstance().dealLogin(activity);

                        }

                        @Override
                        public void onNegativeBtnClick() {
                            userStatusDialog.dismiss();
                            showFocus();
                            //VIP登录弹出框埋点（4K点击行为  取消登录）
                            BaseActionInfo loginInfo = new LoginDialogActionInfo("03", "vip0306", "6", "3", "1");
                            new Statistics(activity, loginInfo).send();
                        }

                        @Override
                        public void onDismissWithoutPressBtn() {
                            showFocus();
                            //VIP登录弹出框埋点（4K点击行为  取消登录）
                            BaseActionInfo loginInfo = new LoginDialogActionInfo("03", "vip0306", "6", "3", "1");
                            new Statistics(activity, loginInfo).send();
                        }
                    });
                    userStatusDialog.showWindow();
                    break;
                case NOMALUSER:
                    userStatusDialog = new VipVideoDialog(activity, activity.getString(R.string.dialog_opening_vip), activity.getString(R.string.dialog_open_vip), activity.getString(R.string.dialog_wait_open), VipVideoDialog.DialogBg.D, false);
                    userStatusDialog.setOnBtnClicklistener(new VipVideoDialog.OnBtnClickListener() {
                        @Override
                        public void onPositiveBtnClick() {
                            userStatusDialog.dismiss();
                            showFocus();
                            BaseActionInfo giveUp = new VIPDialogActionInfo("03", "vip0305", "0", null, "6", "1", null, null);
                            new Statistics(activity, giveUp).send();
                            UserStateUtil.getInstance().becomeVip(activity, "13");
                        }

                        @Override
                        public void onNegativeBtnClick() {
                            //统计 暂不开通
                            BaseActionInfo giveUp = new VIPDialogActionInfo("03", "vip0305", "0", null, "6", "2", null, null);
                            new Statistics(activity, giveUp).send();
                            userStatusDialog.dismiss();
                            showFocus();
                        }

                        @Override
                        public void onDismissWithoutPressBtn() {
                            //统计 返回键执行统计
                            showFocus();
                            Log.i("test", "onDismissWithoutPressBtn");
                            BaseActionInfo giveUp = new VIPDialogActionInfo("03", "vip0305", "0", null, "6", "2", null, null);
                            new Statistics(activity, giveUp).send();


                        }
                    });
                    userStatusDialog.showWindow();
                    break;
                case VIPUSER:
                    openPlay(clickBtnFlag);
                    break;
            }
        } else if (idStatus == 2) {
            switch (userStatus) {
                case NOLOGIN:
                    userStatusDialog = new VipVideoDialog(activity, activity.getString(R.string.dialog_login_vip_film), activity.getString(R.string.dialog_login_ok), activity.getString(R.string.dialog_login_cancel), VipVideoDialog.DialogBg.D, false);
                    userStatusDialog.setOnBtnClicklistener(new VipVideoDialog.OnBtnClickListener() {
                        @Override
                        public void onPositiveBtnClick() {
                            //统计埋点  登录
                            nologin = true;
                            //跳转登录
                            UserStateUtil.getInstance().dealLogin(activity);
                            userStatusDialog.dismiss();
                            showFocus();
                        }

                        @Override
                        public void onNegativeBtnClick() {
                            //VIP登录弹出框埋点（播放点击行为  取消登录）
                            userStatusDialog.dismiss();
                            showFocus();
                        }

                        @Override
                        public void onDismissWithoutPressBtn() {
                            showFocus();
                        }
                    });
                    userStatusDialog.showWindow();
                    break;
                case NOMALUSER:
                    if (StringUtils.currentTimeInStartTimeAndEndTime(productInfoEntity.getBookingStartTime(), productInfoEntity.getLongTime(), productInfoEntity.getBookingEndTime())) { //预售期间内
                        if (null != productInfoEntity.getNowPrice() && productInfoEntity.getNowPrice() == 0) {
                            showBuyedDiaLog("影片将在" + StringUtils.formatDate(productInfoEntity.getBookingEndTime()) + "正式放映,欢迎届时观看。");
                        } else {
                            openPayMoneyPage(-1);
                        }
                    } else {//非预售期内
                        if (null != productInfoEntity.getNowPrice() && productInfoEntity.getNowPrice() == 0) {//限时优惠免费
                            openPlay(clickBtnFlag);
                            Log.d(TAG, "限时优惠，优惠价格0麦币");
                        } else {
                            openPayMoneyPage(-1);
                        }
                    }
                    break;
            }
        }
    }


}
