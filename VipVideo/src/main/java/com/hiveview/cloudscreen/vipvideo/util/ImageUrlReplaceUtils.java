/**
 * @Title ImageUrlReplaceUtils.java
 * @Package com.hiveview.cloudscreen.video.utils
 * @author haozening
 * @date 2015-1-29 下午3:32:17
 * @Description 
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.util;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName ImageUrlReplaceUtils
 * @Description
 * @author haozening
 * @date 2015-1-29 下午3:32:17
 * 
 */
public class ImageUrlReplaceUtils extends HashMap<String, String> {

	private static final long serialVersionUID = 1L;
	private static final String REGEX_HTTP = "http:\\/\\/([^/]+)";
	private static final Pattern PATTERN_HTTP = Pattern.compile(REGEX_HTTP);
	private static final String TAG = "ImageUrlReplaceUtils";

	private static class ImageUrlReplaceUtilsHolder {
		public static ImageUrlReplaceUtils utils = new ImageUrlReplaceUtils();
	}

	private ImageUrlReplaceUtils() {
		put("static.qiyi.com", "static.ptqy.gitv.tv");
		put("www.qiyipic.com", "pic.ptqy.gitv.tv");
		put("pic0.qiyipic.com", "pic0.ptqy.gitv.tv");
		put("pic1.qiyipic.com", "pic1.ptqy.gitv.tv");
		put("pic2.qiyipic.com", "pic2.ptqy.gitv.tv");
		put("pic3.qiyipic.com", "pic3.ptqy.gitv.tv");
		put("pic4.qiyipic.com", "pic4.ptqy.gitv.tv");
		put("pic5.qiyipic.com", "pic5.ptqy.gitv.tv");
		put("pic6.qiyipic.com", "pic6.ptqy.gitv.tv");
		put("pic7.qiyipic.com", "pic7.ptqy.gitv.tv");
		put("pic8.qiyipic.com", "pic8.ptqy.gitv.tv");
		put("pic9.qiyipic.com", "pic9.ptqy.gitv.tv");
		put("meta.video.qiyi.com", "meta.video.ptqy.gitv.tv");
		put("cache.m.iqiyi.com", "cache.m.ptqy.gitv.tv");
		put("s3.qiyipic.com", "s3.ptqy.gitv.tv");
		put("s4.qiyipic.com", "s4.ptqy.gitv.tv");
		put("qiyipic.com", "ptqy.gitv.tv");

	}

	public static ImageUrlReplaceUtils getInstance() {
		return ImageUrlReplaceUtilsHolder.utils;
	}

	public String replace(String url) {
		if (null == url) {
			return url;
		}
		Matcher matcher = PATTERN_HTTP.matcher(url);
		if (matcher.find()) {
			String result = matcher.group();
			String domain = result.replace("http://", "");
			if ("".equals(domain) || "null".equals(domain)) {
				return url;
			} else {
				if (null == get(domain)) {
					return url.replace(result, "http://" + replaceContains(domain));
				} else {
					Log.d(TAG, "replace : " + url.replace(result, "http://" + get(domain)));
					return url.replace(result, "http://" + get(domain));
				}
			}
		} else {
			Log.d(TAG, "no replace not match : " + url);
			return url;
		}
	}
	
	/**
	 * 替换掉字符串中包含在map中的部分内容
	 * @Title replaceContains
	 * @author haozening
	 * @Description 
	 * @param domain
	 * @return
	 */
	private String replaceContains(String domain) {
		for (Map.Entry<String, String> entry : entrySet()) {
			if (domain.contains(entry.getKey())) {
				return domain.replace(entry.getKey(), entry.getValue());
			}
		}
		return domain;
	}
}
