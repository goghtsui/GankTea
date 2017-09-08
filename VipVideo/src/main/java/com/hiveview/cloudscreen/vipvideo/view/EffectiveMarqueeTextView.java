/**
 * @Title EffectiveMarqueeTextView.java
 * @Package com.hiveview.cloudscreen.video.view
 * @author haozening
 * @date 2014年12月25日 下午9:11:28
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.view;

import android.content.Context;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewDebug.ExportedProperty;

import com.hiveview.cloudscreen.vipvideo.util.EasySpan;

/**
 * 配合父View使用ParentView的isLayoutRequested设置为true，可以高效滚动
 *
 * @author haozening
 * @ClassName EffectiveMarqueeTextView
 * @Description 该view由于特殊的跑马灯实现方式，需要在view不展示的时候调用{@link #recycle()} 来防止内存泄露
 * @date 2014年12月25日 下午9:11:28
 */
public class EffectiveMarqueeTextView extends TypeFaceTextView {

    private boolean isInFocusView = false;

    private SpannableString spannableString;

    private EasySpan easySpan;


    public EffectiveMarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EffectiveMarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EffectiveMarqueeTextView(Context context) {
        super(context);
    }

    @Override
    @ExportedProperty(category = "focus")
    public boolean isFocused() {
        return isInFocusView;
    }

    public void setIsInFocusView(boolean isInFocusView) {
        this.isInFocusView = isInFocusView;
        /**
         * 该方法包含了跑马灯开始结束的内部方法，通过该方法可以通知跑马灯的启停，
         * 不过需要保证isFocused()返回的值与setSelected()设置的值相同，否则跑马灯启动会失效
         * 原理请查看TextView源码setSelected()方法与startMarquee()方法
         */
//        setSelected(isInFocusView);

        if (isInFocusView) {
            if (isOverFlowed()) {
                spannableString = new SpannableString(getText() + " ");//这里跑马灯在全英文的时候会无法滚动，需要加一个中文字符
                if (null != easySpan) {
                    easySpan.setView(this);
                    easySpan.setFirstX(0);
                } else {
                    easySpan = new EasySpan(getContext(), this, 0);
                }
                easySpan.setDuration((long) (getTextMeasureWidth() * 50));
                spannableString.setSpan(easySpan, 0, getText().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                setText(spannableString);
            }
        } else {
            if (null != spannableString && null != easySpan) {
                spannableString.removeSpan(easySpan);
                easySpan.recycle();
                setText(getText().toString().trim());
            }


        }

    }

    public boolean isOverFlowed() {
        float width = getTextMeasureWidth();
        if (width > getWidth() - getPaddingLeft() - getPaddingRight()) {
            return true;
        } else {
            return false;
        }
    }

    private float getTextMeasureWidth() {
        Paint paint = getPaint();
        return paint.measureText(getText().toString());
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/10
     * @Description 回收资源
     */
    public void recycle() {
        if (null != easySpan) {
            easySpan.recycle();
        }
    }
}
