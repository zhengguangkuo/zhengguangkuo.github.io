package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;
/**
 * 

 * @Description:优惠券申领请求bean

 * @author:dw

 * @time:2013-8-2 下午1:50:43
 */
public class CouponsReqBean extends Request{
	private String merchantCode;//商户编号
	private String baseCardNo;//卡号
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public String getBaseCardNo() {
		return baseCardNo;
	}
	public void setBaseCardNo(String baseCardNo) {
		this.baseCardNo = baseCardNo;
	}
	
}
