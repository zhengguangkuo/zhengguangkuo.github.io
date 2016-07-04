package com.mt.app.payment.responsebean;

public class SearnearByMeryouhuiBean {
	//图片
	private String pic ; 
	//活动名称
	private String name ; 
	//活动起始时间
	private String time;
	//发放张数
	private String count;
	//活动ID
	private String actID;
	//发卡机构标识
	private String couponIssuerID;
	/** 商家ID */
	private String merchId ;
	
	
	
	public String getMerchId() {
		return merchId;
	}
	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	public String getActID() {
		return actID;
	}
	public void setActID(String actID) {
		this.actID = actID;
	}
	public String getCouponIssuerID() {
		return couponIssuerID;
	}
	public void setCouponIssuerID(String couponIssuerID) {
		this.couponIssuerID = couponIssuerID;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
}
