package com.mt.android.sys.bean.base;

import java.io.Serializable;

public class ResponseBean implements Serializable{
	private String respcode = "0";// 为响应码定义默认值，响应信息中如果不设置该值则默认响应码是成功
	private String message;

	public String getRespcode() {
		return respcode;
	}

	public void setRespcode(String respcode) {
		this.respcode = respcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
