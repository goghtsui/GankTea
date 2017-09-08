/**
 * @Title BitmapBlur.java
 * @Package com.hiveview.cloudscreen.video.utils
 * @author haozening
 * @date 2014年9月5日 下午5:02:33
 * @Description 
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;


import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName BitmapBlur
 * @Description
 * @author haozening
 * @date 2014年9月5日 下午5:02:33
 * 
 */
@SuppressLint("NewApi")
public class BitmapBlurUtil {

	private static final String PIC_PATH = Environment.getExternalStorageDirectory() + File.separator + "blur" + File.separator;
	public static class BitmapBlurUtilHolder {
		static final BitmapBlurUtil UTIL = new BitmapBlurUtil();
	}

	private BitmapBlurUtil() {
	}
	
	public static BitmapBlurUtil getInstance() {
		return BitmapBlurUtilHolder.UTIL;
	}
	
	/**
	 * 对bitmap进行高斯模糊
	 * 
	 * @Title blurBitmap
	 * @author haozening
	 * @Description
	 * @param bitmap
	 * @param context
	 * @param radius
	 * @return
	 */
	public Bitmap blurBitmap(Bitmap bitmap, Context context, float radius) {

		Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);

		RenderScript script = RenderScript.create(context);
		ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(script, Element.U8_4(script));

		// 传入输入输出的bitmap，创建配置类
		Allocation allIn = Allocation.createFromBitmap(script, bitmap);
		Allocation allOut = Allocation.createFromBitmap(script, outBitmap);

		// 设置模糊的角度
		blurScript.setRadius(radius);

		// 执行模糊操作，把结果设置到输出中
		blurScript.setInput(allIn);
		blurScript.forEach(allOut);

		// 通过输出配置类获取最终结果
		allOut.copyTo(outBitmap);

		// 回收资源
		bitmap.recycle();
		script.destroy();

		return outBitmap;

	}
	
	public void writeToFile(Bitmap bitmap, String fileName) {
		File dir = new File(PIC_PATH);
		File pic = new File(dir, fileName);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		BufferedOutputStream bof = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(pic);
			bof = new BufferedOutputStream(fos);
			bitmap.compress(CompressFormat.PNG, 100, bof);
			bof.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != bof) {
				try {
					bof.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Bitmap readFromFile(String fileName) {
		File file = new File(PIC_PATH + fileName);
		if (file.exists()) {
			return BitmapFactory.decodeFile(PIC_PATH + fileName);
		} else {
			return null;
		}
	}
	
	public void clearCache() {
		File file = new File(PIC_PATH);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 对bitmap进行高斯模糊
	 * 
	 * @Title blurBitmap
	 * @author haozening
	 * @Description
	 * @param bitmap
	 * @param context
	 * @return
	 */
	public Bitmap blurBitmap(Bitmap bitmap, Context context) {
		return blurBitmap(bitmap, context, 20.f);
	}

    public void cancelDisplayTask(ImageView imageView){
        CloudScreenApplication.getInstance().imageLoader.cancelDisplayTask(imageView);
    }
}
