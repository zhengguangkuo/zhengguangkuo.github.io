package com.mt.app.payment.requestbean;

public class DiscountBusQueryBean {
	//图片
	private String pic_path;
	//商家名称
	private String cname;
	//折扣商家ID
	private String merch_id;
	//应用ID
	private String app_id;
	//距离
	private double mpayUser_merchant_distance;
	/** 应用名称 */
	private String app_name ;
	/** 应用折扣 */
	private double app_discount ;
	/** 映射前的商户id(雅酷)只有app_id是050的时候,这个字段才有值 */
	private String otherMerchId ;
	
	
	
	public String getOtherMerchId() {
		return otherMerchId;
	}
	public void setOtherMerchId(String otherMerchId) {
		this.otherMerchId = otherMerchId;
	}
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	public double getApp_discount() {
		return app_discount;
	}
	public void setApp_discount(double app_discount) {
		this.app_discount = app_discount;
	}
	public String getPic_path() {
		return pic_path;
	}
	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getMerch_id() {
		return merch_id;
	}
	public void setMerch_id(String merch_id) {
		this.merch_id = merch_id;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public double getMpayUser_merchant_distance() {
		return mpayUser_merchant_distance;
	}
	public void setMpayUser_merchant_distance(double mpayUser_merchant_distance) {
		this.mpayUser_merchant_distance = mpayUser_merchant_distance;
	}
}
