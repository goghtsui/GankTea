/**
 * @Title UrlFormatter.java
 * @Package com.hiveview.cloudscreen.video.utils
 * @author haozening
 * @date 2014年8月29日 上午11:44:33
 * @Description 
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.util;

/**
 * @ClassName UrlFormatter
 * @Description 
 * @author haozening
 * @date 2014年8月29日 上午11:44:33
 * 
 */
public class UrlFormatter {

	public static String formatUrl(String url, Object...params) {
		return String.format(url, params);
	}
	
	/**
	 * 
	 * @Title matchImgUrl
	 * @author xieyi
	 * @Description 重新匹配图片资源路径
	 * @param url 原始图片路径
	 * @param width 需要的图片宽度
	 * @param height 需要的图片高度
	 * @return
	 */
	public static String matchImgUrl(String url, int width, int height){
		int index = url.lastIndexOf(".");
		if (index > 0) {
			return url.substring(0, index)+"_"+width+"_"+height+url.substring(index);
		} else {
			return "";
		}
	}
}
