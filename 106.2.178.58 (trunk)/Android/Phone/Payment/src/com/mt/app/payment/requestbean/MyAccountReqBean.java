package com.mt.app.payment.requestbean;

import com.mt.android.sys.mvc.common.Request;

public class MyAccountReqBean extends Request {
	private String type;// 用来区分当前的修改项 0修改密码  1 修改手机号码 2 修改email
	private String mobile;
	private String email;
	private String password;
	private String username ;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getType() {
			return type;
	}
	public void setType(String type) {
			this.type = type;
	}

}
