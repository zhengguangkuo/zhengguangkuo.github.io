package com.mt.app.payment.requestbean;

import java.io.Serializable;

/**
 * 电子卡包商业券展示的请求对象
 * 
 * @author mzh
 * 
 */
public class EleCard_business_Bean implements Serializable {
	//优惠券活动ID
	private String actId;
	// 优惠券ID
	private String couponId;
	// 商业券的图片
	private String picture;
	// 折扣名称
	private String discount;
	// 折扣商家的名字
	private String name;
	/** 商家ID */
	private String merchId ;
	// 折扣的有效期
	private String time;
	// 商业券已领次数
	private String count;
	// 商业券已过期或者未过期的标记（图片）
	private int icon;
	// 距离
	private String distance;
	
	
	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	
	public String getActId() {
		return actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
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

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "EleCard_business_Bean [discount=" + discount + ", name=" + name + "]";
	}

}
