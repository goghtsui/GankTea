/**
 * @Title BaseRequest.java
 * @Package com.hiveview.domyphonemate.service.net
 * @author haozening
 * @date 2014年5月27日 下午12:56:50
 * @Description 
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.service.net;

/**
 * Get方式请求数据，未指定返回的数据类型，如果有不同的数据类型需要返回，需要如下操作<br>
 * 1、在不同的工具类包中实现此方法<br>
 * 2、根据具体返回类型填充泛型为具体类型<br>
 * 
 * @ClassName BaseRequest
 * @Description
 * @author yupengtong
 * @date 2014年5月27日 下午12:56:50
 * 
 */
public interface ImpRequestGet<T> extends ImpNetRequest<T> {

}
