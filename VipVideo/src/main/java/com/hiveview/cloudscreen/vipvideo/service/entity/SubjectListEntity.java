package com.hiveview.cloudscreen.vipvideo.service.entity;

import java.util.List;

/**
 * 
 * @ClassName SubjectListEntity
 * @Description 专题列表实体
 * @author xieyi
 * @date 2014-9-8 下午5:43:22
 *
 */
public class SubjectListEntity extends HiveviewBaseEntity{

	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer pageNo; //当前页数
	private Integer pageSize; //每页所取数据条数
	private Integer pageCount; //根据服务器总数据条数算出的总页数
	private Integer recCount; //服务器总数据条数
	private List<SubjectEntity> result; //专题内容列表
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getRecCount() {
		return recCount;
	}
	public void setRecCount(Integer recCount) {
		this.recCount = recCount;
	}
	public List<SubjectEntity> getResult() {
		return result;
	}
	public void setResult(List<SubjectEntity> result) {
		this.result = result;
	}
	
	
}
