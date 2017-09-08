/**
 * @Title New.java
 * @Package com.hiveview.cloudscreen.player.utils
 * @author haozening
 * @date 2014年12月15日 下午1:49:52
 * @Description 
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.util;

import android.app.Activity;
import android.view.View;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

/**
 * 泛型容器建造工具类
 * @ClassName New
 * @Description 
 * @author haozening
 * @date 2014年12月15日 下午1:49:52
 * 
 */
public class New {
	
	/**
	 * 构造新的Entry对象
	 * @Title newEntry
	 * @author haozening
	 * @Description 
	 * @param k
	 * @param v
	 * @return
	 */
	public static <K, V> Entry<K, V> newEntry(K k, V v) {
		return new AbstractMap.SimpleEntry<K, V>(k, v);
	}
	
	/**
	 * 构造新的HashMap对象
	 * @Title newHashMap
	 * @author haozening
	 * @Description 
	 * @return
	 */
	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<K, V>();
	}
	
	/**
	 * 构造新的HashSet对象
	 * @Title newHashSet
	 * @author haozening
	 * @Description 
	 * @return
	 */
	public static <E> HashSet<E> newHashSet() {
		return new HashSet<E>();
	}
	
}
