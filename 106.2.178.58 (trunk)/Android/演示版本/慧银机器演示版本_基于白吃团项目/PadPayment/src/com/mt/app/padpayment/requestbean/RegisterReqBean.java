package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;

/**
 * 

 * @Description:�û�ע������bean

 * @author:dw

 * @time:2014-7-18 ����3:09:25
 */
public class RegisterReqBean  extends Request{
//	private String merchantCode;//�̻����
	private String baseCardNo;//��������
	private String mobile;//�绰����
	private String code;//��֤��
	public String getBaseCardNo() {
		return baseCardNo;
	}
	public void setBaseCardNo(String baseCardNo) {
		this.baseCardNo = baseCardNo;
	}

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
