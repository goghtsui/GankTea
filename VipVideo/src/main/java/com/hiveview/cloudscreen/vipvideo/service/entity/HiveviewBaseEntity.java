package com.hiveview.cloudscreen.vipvideo.service.entity;

import java.io.Serializable;

/**
 * 项目中所有数据实体对象的基类
 * 
 * @ClassName: HiveBaseEntity
 * @Description: TODO
 * @author: 陈丽晓
 * @date 2014-8-14 下午2:50:01
 * 
 */
public class HiveviewBaseEntity implements Serializable {

	/**
	 * @Fields serialVersionUID:TODO
	 */
	private static final long serialVersionUID = 6384410026324821924L;

	/**
	 * 数据在总数据列表中的位置
	 */
	private int dataPositionInList = 0;

	/**
	 * 数据在某页面中的位置
	 */
	private int dataPositionInPage = 0;

	public int getDataPosition() {
		return dataPositionInList;
	}

	public void setDataPosition(int dataPosition) {
		this.dataPositionInList = dataPosition;
	}

	public int getDataPositionInPage() {
		return dataPositionInPage;
	}

	public void setDataPositionInPage(int dataPositionInPage) {
		this.dataPositionInPage = dataPositionInPage;
	}

}
