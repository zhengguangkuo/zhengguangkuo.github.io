package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;

/**
 * 

 * @Description:用户注册请求bean

 * @author:dw

 * @time:2014-7-18 下午3:09:25
 */
public class RegisterReqBean  extends Request{
//	private String merchantCode;//商户编号
	private String baseCardNo;//基卡卡号
	private String mobile;//电话号码
	private String code;//验证码
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
