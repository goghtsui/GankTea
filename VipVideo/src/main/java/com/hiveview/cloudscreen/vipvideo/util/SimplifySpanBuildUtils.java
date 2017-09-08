package com.hiveview.cloudscreen.vipvideo.util;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;

import com.hiveview.cloudscreen.vipvideo.R;

/**
 * 设置textview文本不同样式
 */
public class SimplifySpanBuildUtils {
    /**
     * 修改textview字体不同样式
     *
     * @param str1
     */
    public void buildSpecialTextStyle1(String str1, TextView textView, Context context) {
        String strs = str1 + "麦币";
        SpannableStringBuilder styledText1 = new SpannableStringBuilder(strs);
        styledText1.setSpan(new TextAppearanceSpan(context, R.style.style1), 0, str1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText1.setSpan(new TextAppearanceSpan(context, R.style.style2), str1.length(), strs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(styledText1, TextView.BufferType.SPANNABLE);
    }

    /**
     * 购买前textview字体样式
     *
     * @param str
     */
    public void buildSpecialTextStyle2(String str, TextView textView, Context context) {
        String strs = "购买后限 " + str + " 内观看";
        SpannableStringBuilder styledText1 = new SpannableStringBuilder(strs);

        styledText1.setSpan(new TextAppearanceSpan(context, R.style.style3), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText1.setSpan(new TextAppearanceSpan(context, R.style.style4), 5, strs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText1.setSpan(new TextAppearanceSpan(context, R.style.style3), (5 + str.length()), strs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(styledText1, TextView.BufferType.SPANNABLE);
    }

    /**
     * 购买后textview字体样式
     *
     * @param str
     */
    public void buildSpecialTextStyle3(String str, TextView textView, Context context) {
        String strs = "有效观看期至 " + str;
        SpannableStringBuilder styledText1 = new SpannableStringBuilder(strs);

        styledText1.setSpan(new TextAppearanceSpan(context, R.style.style3), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText1.setSpan(new TextAppearanceSpan(context, R.style.style4), 6, strs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(styledText1, TextView.BufferType.SPANNABLE);
    }
}
