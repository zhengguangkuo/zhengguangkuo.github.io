package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;
/**
 * 

 * @Description:Ӧ���б��ѯbean

 * @author:dw

 * @time:2013-8-26 ����10:55:33
 */
public class AppReqBean extends Request {
	private String baseCardNo;//��������
	private String merchantCode;//�̻����
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
