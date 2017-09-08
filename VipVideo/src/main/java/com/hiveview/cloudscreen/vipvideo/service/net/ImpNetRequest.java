package com.hiveview.cloudscreen.vipvideo.service.net;

/**
 * 
 * @ClassName: AbsNetRequest
 * @Description: 网络请求方式基类，所有请求方式继承该方法
 * @author: yupengtong
 * @date 2015年4月9日 下午2:54:46
 * 
 */
public interface ImpNetRequest<T> {

	/**
	 * 创建网络请求并且提交到队列中
	 * 
	 * @Title submit
	 * @author haozening
	 * @Description
	 */
	public abstract void submit();

	/**
	 * 取消队列中的网络请求
	 * 
	 * @Title cancel
	 * @author haozening
	 * @Description
	 */
	public abstract void cancel();

	/**
	 * 请求相应结果监听器
	 * 
	 * @ClassName OnResponseListener
	 * @Description
	 * @author haozening
	 * @date 2014年5月27日 下午1:54:50
	 * 
	 */
	public static interface OnResponseListener<T> {
		/**
		 * 请求响应后触发的方法
		 * 
		 * @Title onResponse
		 * @author haozening
		 * @Description
		 * @param result
		 */
		public void onResponse(T result);
	}

	/**
	 * 请求异常后的监听器
	 * 
	 * @ClassName OnErrorListener
	 * @Description
	 * @author haozening
	 * @date 2014年5月27日 下午1:58:07
	 * 
	 */
	public static interface OnErrorListener {
		/**
		 * 获取请求异常信息
		 * 
		 * @Title onError
		 * @author haozening
		 * @Description
		 * @param exception
		 */
		public void onError(Exception exception);
	}
}
