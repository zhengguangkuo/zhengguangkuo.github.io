package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;
/**
 * 

 * @Description:优惠券列表请求bean

 * @author:dw

 * @time:2013-8-2 下午1:50:43
 */
public class CouponsApplyReqBean extends Request{
	private String merchantCode;//商户编号
	private String baseCardNo;//基卡卡号
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getBaseCardNo() {
		return baseCardNo;
	}
	public void setBaseCardNo(String baseCardNo) {
		this.baseCardNo = baseCardNo;
	}
	
}
