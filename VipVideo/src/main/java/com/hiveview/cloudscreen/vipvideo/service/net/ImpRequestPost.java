/**
 * @Title RequestPost.java
 * @Package com.hiveview.domyphonemate.service.net
 * @author haozening
 * @date 2014年5月27日 下午1:00:04
 * @Description 
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.service.net;

import java.util.Map;

/**
 * 
 * @ClassName AbsRequestPost
 * @Description
 * @author yupengtong
 * @date 2014年5月27日 下午4:22:54
 * 
 */
public interface ImpRequestPost<T> extends ImpNetRequest<T> {

	/**
	 * 设置Post提交给服务器的参数
	 * 
	 * @Title setParams
	 * @author haozening
	 * @Description
	 * @param key
	 * @param value
	 * @return
	 */
	public ImpRequestPost<T> setParam(String key, String value);

	/**
	 * 设置Post提交给服务器的参数
	 * 
	 * @Title setParams
	 * @author haozening
	 * @Description
	 * @return
	 */
	public ImpRequestPost<T> setParams(Map<String, String> params);
}
