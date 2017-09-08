package com.hiveview.cloudscreen.vipvideo.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.text.style.ReplacementSpan;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/12/29
 * @Description 本类有一个大坑，如果文本信息全部为英文和数字会无法滚动，内容中必须包含汉语，所以建议文本拼接一些汉语字符后再使用该类
 */
public class EasySpan extends ReplacementSpan {


    private static final String TAG = EasySpan.class.getSimpleName();
    private TextView mView;
    private int firstX = 0;

    private int startX = 0;
    private final static int DEFULT_DURATION = 2000;
    private long duration = DEFULT_DURATION;

    private EasySpanListener mEasySpanListener;
    private ValueAnimator valueAnimator;

    private AnimatorSet animatorSet;
    private ValueAnimator firstAnimator;

    public EasySpan(Context context, final TextView view) {
        this.mView = view;
        startX = view.getLayoutParams().width > 0 ? -view.getLayoutParams().width : -418;//第一次的时候view还没measure出来，需要有个默认值
        firstX = startX;
        Logger.d(TAG, "startX=" + startX);
    }

    public EasySpan(Context context, final TextView view, int firstX) {
        this.mView = view;
        this.firstX = firstX;
        startX = view.getLayoutParams().width > 0 ? -view.getLayoutParams().width : -418;//第一次的时候view还没measure出来，需要有个默认值
        Logger.d(TAG, "firstX=" + firstX);
        Logger.d(TAG, "startX=" + startX);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/1/14
     * @Description 设置起始位置，需要在{@link #setView(android.widget.TextView)}方法后调用
     */
    public void setFirstX(int firstX) {
        this.firstX = firstX;
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/1/14
     * @Description 设置TextView
     */
    public void setView(TextView textView) {
        this.mView = textView;
        startX = mView.getLayoutParams().width > 0 ? -mView.getLayoutParams().width : -418;//第一次的时候view还没measure出来，需要有个默认值
        firstX = startX;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end,
                     float x, int top, int y, int bottom, Paint paint) {
        canvas.save();
        canvas.drawText(text.toString(), x - startX, y, paint);
        canvas.restore();
        int measureText = (int) paint.measureText(text, 0, text.length());
        if (valueAnimator == null) {
            Logger.d(TAG, "initAnimator(" + (measureText - mView.getWidth()) + ")");
            initAnimator(measureText - mView.getWidth());
        }
    }


    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end,
                       FontMetricsInt fm) {
        return 40;
    }

    private void initAnimator(int width) {
        valueAnimator = ValueAnimator.ofInt(startX, width + mView.getWidth());
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startX = (Integer) animation.getAnimatedValue();
                if (null != mView) {
                    mView.invalidate();
                }
            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                System.gc();//跑马灯会不断的invalidate导致内存增长，所以这里通知回收内存来保证内存正常
                if (mEasySpanListener != null) {
                    mEasySpanListener.over();
                }
            }
        });
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        if (firstX != startX) {
            long currentPlayTime = (firstX - startX) * duration / (width + mView.getWidth() - startX);
            animatorSet = new AnimatorSet();
            firstAnimator = ValueAnimator.ofInt(firstX, width + mView.getWidth());
            firstAnimator.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    startX = (Integer) animation.getAnimatedValue();
                    if (null != mView) {
                        mView.invalidate();
                    }
                }
            });
            firstAnimator.setInterpolator(new LinearInterpolator());
            firstAnimator.setDuration(duration - currentPlayTime);
            animatorSet.playSequentially(firstAnimator, valueAnimator);
            animatorSet.start();
        } else {
            valueAnimator.start();
        }

    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
        if (null != valueAnimator) {
            valueAnimator.setDuration(duration);
        }
    }


    public void setEasySpanListener(EasySpanListener mEasySpanListener) {
        this.mEasySpanListener = mEasySpanListener;
    }

    public interface EasySpanListener {
        public void over();
    }


    public void recycle() {
        resetAnimator();
        mEasySpanListener = null;
        valueAnimator = null;
        firstAnimator = null;
        animatorSet = null;
        mView = null;
    }

    public void resetAnimator() {
        if (null != valueAnimator) {
            valueAnimator.setRepeatCount(0);
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.removeAllListeners();
            valueAnimator.end();
            valueAnimator.cancel();
        }
        if (null != firstAnimator) {
            firstAnimator.removeAllUpdateListeners();
            firstAnimator.removeAllListeners();
            firstAnimator.end();
            firstAnimator.cancel();
        }
        if (null != animatorSet) {
            animatorSet.removeAllListeners();
            animatorSet.end();
            animatorSet.cancel();
        }
    }

}