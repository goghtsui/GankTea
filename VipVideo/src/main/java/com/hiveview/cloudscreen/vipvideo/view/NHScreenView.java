package com.hiveview.cloudscreen.vipvideo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.OverScroller;

/**
 * Created by chenlixiao on 16/1/9.
 */
public class NHScreenView extends HorizontalScrollView {

    private final String TAG = "NHScreenView";

    private OverScroller mScroller;

    /**
     * 屏幕的宽度
     */
    private int mScreenWidth = 0;

    /**
     * 存放满屏View的容器
     */
    private LinearLayout mContainer = null;

    /***
     * 默认滑动每屏的时间
     */
    private final int DEFAULT_DURATION = 600;

    /**
     * 没滚动一屏的距离，所需要的滚动时间(bak-1500)
     */
    private int mDuration = DEFAULT_DURATION;

    /***
     * NScreenView监听器
     */
    private IScrollListener mScrollListener = null;

    private int MORE_THAN_DISTANCE = 30;

    public NHScreenView(Context context) {
        super(context);
    }

    public NHScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    /****
     * 初始化
     */
    private void init(Context context) {
        //设置滚动条不现实
        setHorizontalScrollBarEnabled(false);
        mScroller = new OverScroller(getContext());

        mContainer = new LinearLayout(getContext());
        //把容器添加进去ScrollView中
        mContainer.setOrientation(LinearLayout.HORIZONTAL);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(mContainer, params);

        //获取当前设备的屏幕的高度和宽度
        WindowManager wmManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        wmManager.getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        //滚动未完成不响应按键
        if (isScrolling()) {
            mDuration = DEFAULT_DURATION /3;
        }else {
            mDuration = DEFAULT_DURATION;
        }

        long startTime = System.currentTimeMillis();

        if (event.getAction() == KeyEvent.ACTION_DOWN && (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT)) {
            //根据用户按键判定，是否需要发生滚动
            return judgeScroll(event.getKeyCode());
        }

        return super.dispatchKeyEvent(event);
    }

    /**
     * 判定NScreenView是否需要进行滚动，如果需要就发生滚动
     *
     * @param keyCode 用户操作按键的键值
     * @return
     */
    private boolean judgeScroll(int keyCode) {

        //根据按键键值判定应该向那个方向获取下一个获得焦点的View
        int keyDirection = -111;
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            keyDirection = FOCUS_LEFT;
            Log.d(TAG, "left key");
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            keyDirection = FOCUS_RIGHT;
            Log.d(TAG, "right key");
        }

        //只对方向键做焦点处理，其他按键暂不处理
        if (keyDirection == -111) {
            return false;
        }

        //获取当前的获得焦点的View
        View currentFocused = findFocus();
        if (null == currentFocused) {
            Log.d(TAG, "currentFocused is null");
            return false;
        }

        //根据获取下个焦点View的方向和当前获得焦点的View，获取下一个可以获取焦点的View
        View nextFocused = FocusFinder.getInstance().findNextFocus(this,
                currentFocused, keyDirection);

        if (null == nextFocused) {
            Log.d(TAG, "nextFocused is null");
            return false;
        }


        //获取下一个可获得焦点View的在屏幕内的坐标
        int[] location = new int[2];
        nextFocused.getLocationOnScreen(location);

        Log.d(TAG, "nextFocused position (" + location[0] + "," + location[1] + "); w=" + nextFocused.getWidth() + " h=" + nextFocused.getHeight());

        //如果下一个焦点View的x坐标值在屏幕外(即：x大于屏..幕内可视的最大坐标)，则要发生向上滚动
        if (location[0] + nextFocused.getWidth() > mScreenWidth) {
            int scrollDistance = (location[0] + nextFocused.getWidth() - mScreenWidth) + MORE_THAN_DISTANCE;
            Log.d(TAG, "scroll left distance is " + scrollDistance);
            smoothScrollBySlow(scrollDistance, 0);
            return false;
        }

        //如果下一个焦点View的Y坐标值在屏幕外(即：Y小于屏幕内可视的最小坐标)，则要发生向上滚动
        if (location[0] < 0) {
            int scrollDistance = Math.abs(0 - location[0]) + MORE_THAN_DISTANCE;
            Log.d(TAG, "scroll right distance is " + (-scrollDistance));
            smoothScrollBySlow(-scrollDistance, 0);
            return false;
        }

        return false;
    }

    /**
     * 为NScreenView添加子View，每个子View的宽度和高度都是屏幕宽度和高度
     *
     * @param screenView
     */
    public void addScreenView(ViewGroup screenView) {
        mContainer.addView(screenView);
    }

    /***
     * 调用此方法滚动到目标位置
     *
     * @param fx 目标的x位置
     * @param fy 目标的y位置
     */
    private void smoothScrollToSlow(int fx, int fy) {
        int dx = fx - getScrollX();// mScroller.getFinalX(); 普通view使用这种方法
        int dy = fy - getScrollY(); // mScroller.getFinalY();
        smoothScrollBySlow(dx, dy);
    }

    /***
     * 调用此方法设置滚动的相对偏移
     *
     * @param dx 与目标x位置的偏移量
     * @param dy 与目标y位置的偏移量
     */
    private void smoothScrollBySlow(int dx, int dy) {
        if (null != mScrollListener) {
            mScrollListener.startScroll();
        }

        // 设置mScroller的滚动偏移量
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, mDuration);// scrollView使用的方法（因为可以触摸拖动）
        // mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(),
        // dx, dy, duration);
        invalidate();// 这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    /**
     * 设置滚动每屏滚动的需要的时间
     *
     * @param duration
     */
    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    @Override
    public void computeScroll() {

        // 先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            // 这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            // 必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();

            if (null != mScrollListener) {
                mScrollListener.scrolling();
            }

        } else {
            if (null != mScrollListener) {
                mScrollListener.endScroll();
            }
        }

        super.computeScroll();
    }

    /**
     * 滑动事件，这是控制手指滑动的惯性速度
     */
    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 4);
    }

    /***
     * 判定当前的NScreenView是否在滚动中
     *
     * @return true: 滚动进行中(滚定状态)；false 滚动停止(静止状态)
     */
    public boolean isScrolling() {
        return !mScroller.isFinished();
    }

    public void setScrollListener(IScrollListener listener) {
        this.mScrollListener = listener;
    }

    /**
     * 监听NScreenView滚动
     */
    public interface IScrollListener {

        public void startScroll();

        public void scrolling();

        public void endScroll();
    }

    /**
     * 在水平方向上的滚动
     *
     * @param offSetX 要滚动的距离
     */
    public void scrollX(int offSetX) {
        smoothScrollBySlow(offSetX, 0);
    }
}
