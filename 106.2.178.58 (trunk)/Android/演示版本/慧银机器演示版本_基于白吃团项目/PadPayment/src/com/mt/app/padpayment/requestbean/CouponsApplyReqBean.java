package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;
/**
 * 

 * @Description:�Ż�ȯ�б�����bean

 * @author:dw

 * @time:2013-8-2 ����1:50:43
 */
public class CouponsApplyReqBean extends Request{
	private String merchantCode;//�̻����
	private String baseCardNo;//��������
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
