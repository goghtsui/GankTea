/**
 * @Title BaseFilmDetailEpisode.java
 * @Package com.hiveview.cloudscreen.video.activity.component
 * @author haozening
 * @date 2014年9月10日 下午1:43:26
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.activity.component;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.EpisodeListEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ProductInfoEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;
import com.hiveview.cloudscreen.vipvideo.view.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haozening
 * @ClassName BaseFilmDetailEpisode
 * @Description
 * @date 2014年9月10日 下午1:43:26
 */
public abstract class BaseFilmDetailEpisode implements OnKeyListener, WheelView.OnSelectedChangedListener, OnFocusChangeListener, OnItemClickListener {

    protected static final String TAG = "BaseFilmDetailEpisode";
    protected StartPlayClickListener startPlayClickListener = null;
    protected Activity activity;
    protected WheelView leftEpisodeView;
    protected WheelView rightEpisodeView;
    protected View leftBackground;
    protected View rightBackground;
    protected View background;
    protected View leftContainer;
    protected View rightContainer;
    protected OnKeyListener keyListener;
    private AlbumEntity albumEntity;
    LinearLayout linear_episode_layout;
    RelativeLayout rl_episode_layout;
    List<View> focusView = new ArrayList<>();
    //记录焦点在哪一边
    public boolean isEpisodeLeftShow = true;

    public BaseFilmDetailEpisode(Activity activity, AlbumEntity albumEntity) {
        this.activity = activity;
        this.albumEntity = albumEntity;
        rl_episode_layout = (RelativeLayout) activity.findViewById(R.id.rl_episode_layout);
        linear_episode_layout = (LinearLayout) activity.findViewById(R.id.linear_episode_layout);
        leftBackground = activity.findViewById(R.id.view_episode_left_background);
        rightBackground = activity.findViewById(R.id.view_episode_right_background);
        leftContainer = activity.findViewById(R.id.sv_episode_left_container);
        rightContainer = activity.findViewById(R.id.sv_episode_right_container);
        background = activity.findViewById(R.id.view_bg);
        //焦点集合
        focusView.add(leftBackground);
        focusView.add(rightBackground);

        leftEpisodeView = (WheelView) activity.findViewById(R.id.sv_episode_left);
        leftEpisodeView.setStartPosition(0.5f);
        leftEpisodeView.setOnItemClickListener(this);

        rightEpisodeView = (WheelView) activity.findViewById(R.id.sv_episode_right);
        rightEpisodeView.setStartPosition(0.5f);
        rightEpisodeView.setOnItemClickListener(this);

        leftEpisodeView.setOnFocusChangeListener(this);
        rightEpisodeView.setOnFocusChangeListener(this);
        leftEpisodeView.setOnKeyListener(this);
        rightEpisodeView.setOnKeyListener(this);
        leftEpisodeView.setOnSelectedChangedListener(this);
        rightEpisodeView.setOnSelectedChangedListener(this);

        resetFocusColor(UserStateUtil.getInstance().getUserStatus());
    }

    public void resetFocusColor(UserStateUtil.UserStatus userStatus) {
        switch (userStatus) {
            case VIPUSER:
                for (View v : focusView) {
                    v.setBackgroundResource(R.drawable.bg_episode_vip_focus);
                }
                break;
            default:
                for (View v : focusView) {
                    v.setBackgroundResource(R.drawable.bg_episode_focus);
                }
                break;
        }
    }


    /**
     * 设置点击事件
     *
     * @param keyListener
     * @Title setOnKeyListener
     * @author haozening
     * @Description
     */
    public void setOnKeyListener(OnKeyListener keyListener) {
        this.keyListener = keyListener;
    }

    /**
     * 设置左侧获取焦点
     *
     * @Title leftRequestFocus
     * @author haozening
     * @Description
     */
    public void leftRequestFocus() {
        leftEpisodeView.requestFocus();
        leftEpisodeView.requestFocusFromTouch();
        isEpisodeLeftShow = true;
    }

    /**
     * 右侧获取焦点
     *
     * @Title rightRequestFocus
     * @author haozening
     * @Description
     */
    public void rightRequestFocus() {
        rightEpisodeView.requestFocus();
        rightEpisodeView.requestFocusFromTouch();
        isEpisodeLeftShow = false;
    }

    /**
     * 设置数据
     *
     * @Title setData
     * @author haozening
     * @Description
     */
    public abstract void setData(ResultEntity entity);

    /**
     * 获取当前的Entity
     *
     * @param currentItem
     * @return
     * @Title getEntity
     * @author haozening
     * @Description
     */
    public abstract EpisodeListEntity getEntity(int currentItem);

    public abstract void setProductInfoEntity(ProductInfoEntity productInfoEntity);


    /**
     * 保存剧集信息
     *
     * @author haozening
     * @ClassName DataHolder
     * @Description
     * @date 2014年9月10日 上午10:19:56
     */
    public static class DataHolder {
        public final int index;
        public final String description;

        public DataHolder(int index, String description) {
            this.index = index;
            this.description = description;
        }
    }

    public interface StartPlayClickListener {
        public void setStarPlayClickCallBack(AdapterView<?> parent, View view, int position, EpisodeListEntity entity);
    }

    public void setStarPlayClickCallBack(StartPlayClickListener listener) {
        this.startPlayClickListener = listener;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.sv_episode_left) {
            if (startPlayClickListener != null) {
                startPlayClickListener.setStarPlayClickCallBack(parent, view, position, getEntity(position));
            }
        }
    }
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.d(TAG, "onKey " + event.getAction() + "  view : " + v);

        if (null != keyListener) {
            keyListener.onKey(v, keyCode, event);
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            rightBackground.setVisibility(View.VISIBLE);
            leftBackground.setVisibility(View.INVISIBLE);
            rightRequestFocus();
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            rightBackground.setVisibility(View.INVISIBLE);
            leftBackground.setVisibility(View.VISIBLE);
            leftRequestFocus();
            return true;
        }

        if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            return true;
        }

        return false;
    }

    /**
     * 设置剧集是否可获取焦点
     *
     * @param flag
     * @Title setFocusable
     * @author haozening
     * @Description
     */
    public void setFocusable(boolean flag) {
        leftEpisodeView.setFocusable(flag);
        rightEpisodeView.setFocusable(flag);
    }

    private boolean isOpen = false;


    public interface openEpisodeListner {
        void openTrue(boolean isHas);
    }

    openEpisodeListner listener;

    public void setOpenEpisodeListener(openEpisodeListner listener) {
        this.listener = listener;
    }

    /**
     * 显示剧集
     *
     * @Title show
     * @author haozening
     * @Description
     */
    public void open() {
        isOpen = true;
        rl_episode_layout.animate().alpha(1).translationX(this.activity.getResources().getDimension(R.dimen.animal_translationX)).setDuration(400)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (null != listener) {
                            listener.openTrue(true);
                        }
                        super.onAnimationEnd(animation);
                    }
                }).setInterpolator(new AccelerateInterpolator()).start();
        linear_episode_layout.animate().alpha(1).translationX(0).setDuration(400).setInterpolator(new AccelerateInterpolator()).start();
        background.animate().alpha(1).translationX(0).setDuration(400).setInterpolator(new AccelerateInterpolator()).start();
    }

    /**
     * 隐藏剧集
     *
     * @Title hide
     * @author haozening
     * @Description
     */
    public void close() {
        isOpen = false;
        rl_episode_layout.animate().alpha(0).translationX(450).setDuration(400).setInterpolator(new AccelerateInterpolator()).start();
        linear_episode_layout.animate().alpha(0).translationX(400).setDuration(400).setInterpolator(new AccelerateInterpolator()).start();
        background.animate().alpha(0).translationX(333)
                .setDuration(400).setInterpolator(new AccelerateInterpolator()).start();
        setFocusable(false);
    }

    /**
     * 关闭界面，同时返回关闭之前页面关闭状态
     *
     * @return
     * @Title onCloseActivity
     * @author haozening
     * @Description
     */
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if (v.getId() == R.id.sv_episode_left) {
                leftBackground.setVisibility(View.VISIBLE);
            }
            if (v.getId() == R.id.sv_episode_right) {
                rightBackground.setVisibility(View.VISIBLE);
            }
        } else {
            if (v.getId() == R.id.sv_episode_left) {
                leftBackground.setVisibility(View.INVISIBLE);
            }
            if (v.getId() == R.id.sv_episode_right) {
                rightBackground.setVisibility(View.INVISIBLE);
            }
        }
    }
}
