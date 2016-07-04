package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;
/**
 * 

 * @Description:积分列表查询bean

 * @author:dw

 * @time:2013-11-8 下午2:25:31
 */
public class CreditReqBean extends Request {
	private String baseCardNo;//基卡卡号
	private String merchantCode;//商户编号
	public String getBaseCardNo() {
		return baseCardNo;
	}
	public void setBaseCardNo(String baseCardNo) {
		this.baseCardNo = baseCardNo;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
}
