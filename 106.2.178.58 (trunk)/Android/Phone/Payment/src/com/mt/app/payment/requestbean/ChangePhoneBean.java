package com.mt.app.payment.requestbean;

import com.mt.android.sys.mvc.common.Request;

public class ChangePhoneBean extends Request{

	/** 手机号码 */
	private String mobile ;	
	/** 验证码 */
	private String valid_code ;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String phone) {
		this.mobile = phone;
	}
	public String getValid_code() {
		return valid_code;
	}
	public void setValid_code(String valid_code) {
		this.valid_code = valid_code;
	}

	
}
