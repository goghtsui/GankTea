package com.hiveview.cloudscreen.vipvideo.service.entity;

import java.util.List;

/**
 * 解析网络返回的JSON数据的实体对象
 * 
 * @ClassName: ResultEntity
 * @Description: TODO
 * @author:陈丽晓
 * @param <T>
 * @date 2014-8-14 下午2:58:25
 * 
 */
public class ResultEntity<T extends HiveviewBaseEntity> {
	/**
	 * 额外带带回的参数arg1
	 */
	public int arg1;

	/**
	 * 额外带带回的参数arg2
	 */
	public int arg2;

	/**
	 * 错误码信息
	 */
	public String errorCode;

	/**
	 * 额外带带回的参数str2
	 */
	public String str2;

	/**
	 * 返回的数据集合
	 */
	private List<T> list = null;

	/**
	 * 返回的单个数据对象
	 */
	private HiveviewBaseEntity entity = null;

	/**
	 * 返回业务的列表数据对象
	 * 
	 * @Title: ResultEntity
	 * @author:陈丽晓
	 * @Description: TODO
	 * @return
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * 设置业务列表数据
	 * 
	 * @Title: ResultEntity
	 * @author:陈丽晓
	 * @Description: TODO
	 * @param list
	 */
	public void setList(List<T> list) {
		this.list = list;
	}

	public HiveviewBaseEntity getEntity() {
		return entity;
	}

	public void setEntity(HiveviewBaseEntity entity) {
		this.entity = entity;
	}

}
