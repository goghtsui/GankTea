package com.hiveview.cloudscreen.vipvideo.service.entity;

import java.util.List;

/**  
 * @author li.sz
 * @version 
 * @date：2016年11月4日 下午7:16:47  
 * @类说明: VIP商品
 */

public class VipGoodsApiVo extends HiveviewBaseEntity{

	private String id;  			//VIP商品包id
	private String goodsName;	  	//线上vip商品包名称 
	private String hiveviewVip;  	//是否包含家视VIP：1含0不含  
	private String goodsType;  		//会员分类,格式：typeId,callBackUrl（分类ID,回调地址）  
	private Integer templetId;	  	//模板ID  
	private String saleStartTime;   //销售开始时间 
	private String saleEndTime;  	//销售结束时间 
	private String goodsPic;  		//线上vip商品海报图 
	private String goodsDesc;	  	//线上vip商品简介 
	
	private List<VIpGoodsContentApiVo> contentList; 	//价格以及策略信息

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getHiveviewVip() {
		return hiveviewVip;
	}

	public void setHiveviewVip(String hiveviewVip) {
		this.hiveviewVip = hiveviewVip;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public Integer getTempletId() {
		return templetId;
	}

	public void setTempletId(Integer templetId) {
		this.templetId = templetId;
	}

	public String getSaleStartTime() {
		return saleStartTime;
	}

	public void setSaleStartTime(String saleStartTime) {
		this.saleStartTime = saleStartTime;
	}

	public String getSaleEndTime() {
		return saleEndTime;
	}

	public void setSaleEndTime(String saleEndTime) {
		this.saleEndTime = saleEndTime;
	}

	public String getGoodsPic() {
		return goodsPic;
	}

	public void setGoodsPic(String goodsPic) {
		this.goodsPic = goodsPic;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public List<VIpGoodsContentApiVo> getContentList() {
		return contentList;
	}

	public void setContentList(List<VIpGoodsContentApiVo> contentList) {
		this.contentList = contentList;
	}
}
