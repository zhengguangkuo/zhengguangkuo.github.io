package com.mt.app.payment.requestbean;

import com.mt.android.sys.mvc.common.Request;
/**
 * 

 * @Description:�Ż�ȯ����

 * @author:dw

 * @time:2013-8-2 ����1:50:43
 */
public class CouponsReturnReqBean extends Request{
	private String couponIds;
	private String couponIssuerId = "80000000";
	public String getCouponIds() {
		return couponIds;
	}
	public void setCouponIds(String couponIds) {
		this.couponIds = couponIds;
	}
	public String getCouponIssuerId() {
		return couponIssuerId;
	}
	public void setCouponIssuerId(String couponIssuerId) {
		this.couponIssuerId = couponIssuerId;
	}
	
}
