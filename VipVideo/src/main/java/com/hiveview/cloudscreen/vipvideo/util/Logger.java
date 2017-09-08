/**
 * @Title Logger.java
 * @Package com.hiveview.domyphonemate.utils
 * @author haozening
 * @date 2014年5月26日 下午5:03:30
 * @Description 
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.util;

import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.common.CloudScreenConstants;


/**
 * Log类的代理，CloudScreenConstants中配置
 * 
 * @ClassName Logger
 * @Description
 * @author haozening
 * @date 2014年5月26日 下午5:03:30
 * 
 */
public class Logger {

	public static final int PUBLISHED = 0;
	public static final int DEBUG = 1;

	public static void v(String tag, Object msg) {
		switch (CloudScreenConstants.ENVIRONMENT) {
		case DEBUG:
			Log.v(tag, msg.toString());
			break;
		}
	}

	public static void v(String tag, Object msg, Throwable throwable) {
		switch (CloudScreenConstants.ENVIRONMENT) {
		case DEBUG:
			Log.v(tag, msg.toString(), throwable);
			break;
		}
	}

	public static void d(String tag, Object msg) {
		switch (CloudScreenConstants.ENVIRONMENT) {
		case DEBUG:
			Log.d(tag, msg.toString());
			break;
		}
	}

	public static void d(String tag, Object msg, Throwable throwable) {
		switch (CloudScreenConstants.ENVIRONMENT) {
		case DEBUG:
			Log.d(tag, msg.toString(), throwable);
			break;
		}
	}

	public static void i(String tag, Object msg) {
		switch (CloudScreenConstants.ENVIRONMENT) {
		case DEBUG:
			Log.i(tag, msg.toString());
			break;
		}
	}

	public static void i(String tag, Object msg, Throwable throwable) {
		switch (CloudScreenConstants.ENVIRONMENT) {
		case DEBUG:
			Log.i(tag, msg.toString(), throwable);
			break;
		}
	}

	public static void w(String tag, Object msg) {
		switch (CloudScreenConstants.ENVIRONMENT) {
		case DEBUG:
			Log.w(tag, msg.toString());
			break;
		}
	}

	public static void w(String tag, Object msg, Throwable throwable) {
		switch (CloudScreenConstants.ENVIRONMENT) {
		case DEBUG:
			Log.w(tag, msg.toString(), throwable);
			break;
		}
	}

	public static void e(String tag, Object msg) {
		switch (CloudScreenConstants.ENVIRONMENT) {
		case DEBUG:
			Log.e(tag, msg.toString());
			break;
		}
	}

	public static void e(String tag, Object msg, Throwable throwable) {
		switch (CloudScreenConstants.ENVIRONMENT) {
		case DEBUG:
			Log.e(tag, msg.toString(), throwable);
			break;
		}
	}

	public static void wtf(String tag, Object msg) {
		switch (CloudScreenConstants.ENVIRONMENT) {
		case DEBUG:
			Log.wtf(tag, msg.toString());
			break;
		}
	}

	public static void wtf(String tag, Throwable throwable) {
		switch (CloudScreenConstants.ENVIRONMENT) {
		case DEBUG:
			Log.wtf(tag, throwable);
			break;
		}
	}

	public static void wtf(String tag, Object msg, Throwable throwable) {
		switch (CloudScreenConstants.ENVIRONMENT) {
		case DEBUG:
			Log.wtf(tag, msg.toString(), throwable);
			break;
		}
	}
}
