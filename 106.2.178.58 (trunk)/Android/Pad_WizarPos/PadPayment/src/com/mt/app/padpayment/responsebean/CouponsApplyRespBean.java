package com.mt.app.padpayment.responsebean;

import java.io.Serializable;

import com.mt.android.sys.bean.base.ResponseBean;

/**
 * 

 * @Description:优惠券派发响应beanbean

 * @author:dw

 * @time:2013-8-2 下午1:53:13
 */
public class CouponsApplyRespBean extends ResponseBean implements Serializable{
	private String coupon_id;//优惠券编号
	private String coupon_name;//优惠券名称
	private String coupon_img_path;//优惠券图片路径
	private String issuerId;//发券对象标识
	private String c_iss_id;//应用id
	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}
	public String getCoupon_name() {
		return coupon_name;
	}
	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}
	public String getCoupon_img_path() {
		return coupon_img_path;
	}
	public void setCoupon_img_path(String coupon_img_path) {
		this.coupon_img_path = coupon_img_path;
	}
	public String getIssuerId() {
		return issuerId;
	}
	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}
	public String getC_iss_id() {
		return c_iss_id;
	}
	public void setC_iss_id(String c_iss_id) {
		this.c_iss_id = c_iss_id;
	}
	
	
}
