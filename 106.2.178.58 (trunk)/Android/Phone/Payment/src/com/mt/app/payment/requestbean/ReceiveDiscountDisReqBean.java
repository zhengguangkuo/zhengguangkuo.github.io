package com.mt.app.payment.requestbean;

public class ReceiveDiscountDisReqBean {
	//优惠券id
	private String actId;
	//发行机构标识
	private String couponIssuerId;
	public String getActId() {
		return actId;
	}
	public void setActId(String actId) {
		this.actId = actId;
	}
	public String getCouponIssuerId() {
		return couponIssuerId;
	}
	public void setCouponIssuerId(String couponIssuerId) {
		this.couponIssuerId = couponIssuerId;
	}
	
}
