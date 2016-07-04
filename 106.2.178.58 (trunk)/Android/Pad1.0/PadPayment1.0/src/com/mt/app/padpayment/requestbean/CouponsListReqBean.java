package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;
/**
 * 

 * @Description:优惠券列表查询bean

 * @author:dw

 * @time:2013-8-26 上午10:55:33
 */
public class CouponsListReqBean extends Request {
	private String baseCardNo;//基卡卡号
	private String merchantCode;//商户编号
	private String instId;//优惠券应用id
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
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	
	
}
