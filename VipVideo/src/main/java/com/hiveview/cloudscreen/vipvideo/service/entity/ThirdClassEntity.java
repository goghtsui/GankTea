/**
 * @Title ThirdClassEntity.java
 * @Package com.hiveview.cloudscreen.video.entity
 * @author haozening
 * @date 2014年9月17日 下午3:29:03
 * @Description 
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.service.entity;

import java.util.List;

/**
 * @ClassName ThirdClassEntity
 * @Description 
 * @author haozening
 * @date 2014年9月17日 下午3:29:03
 * 
 */
public class ThirdClassEntity extends HiveviewBaseEntity {

	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	
	private int firstclass_id;
	private int secondclass_id;
	private String secondclass_name;
	private List<ThirdClass> classThirdList;
	
	public List<ThirdClass> getClassThirdList() {
		return classThirdList;
	}

	public void setClassThirdList(List<ThirdClass> classThirdList) {
		this.classThirdList = classThirdList;
	}

	public int getFirstclass_id() {
		return firstclass_id;
	}

	public void setFirstclass_id(int firstclass_id) {
		this.firstclass_id = firstclass_id;
	}

	public int getSecondclass_id() {
		return secondclass_id;
	}

	public void setSecondclass_id(int secondclass_id) {
		this.secondclass_id = secondclass_id;
	}

	public String getSecondclass_name() {
		return secondclass_name;
	}

	public void setSecondclass_name(String secondclass_name) {
		this.secondclass_name = secondclass_name;
	}

	public static class ThirdClass{
		private int thirdclass_id;
		private String thirdclass_name;
		public int getThirdclass_id() {
			return thirdclass_id;
		}
		public void setThirdclass_id(int thirdclass_id) {
			this.thirdclass_id = thirdclass_id;
		}
		public String getThirdclass_name() {
			return thirdclass_name;
		}
		public void setThirdclass_name(String thirdclass_name) {
			this.thirdclass_name = thirdclass_name;
		}
	}

}
