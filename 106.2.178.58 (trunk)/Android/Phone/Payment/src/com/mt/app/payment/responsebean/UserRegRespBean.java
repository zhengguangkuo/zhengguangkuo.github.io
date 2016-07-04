package com.mt.app.payment.responsebean;

import com.mt.android.sys.bean.base.ResponseBean;

public class UserRegRespBean extends ResponseBean{
	private String busiInfo;// 0 请求验证码的返回 1 验证信息的返回  

	public String getBusiInfo() {
		return busiInfo;
	}

	public void setBusiInfo(String busiInfo) {
		this.busiInfo = busiInfo;
	}

}
