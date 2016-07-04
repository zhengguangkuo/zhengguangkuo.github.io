package com.mt.app.payment.requestbean;


import java.io.Serializable;
import java.util.regex.Pattern;

import com.mt.android.sys.bean.base.RequestBean;

public class LoginReqBean extends RequestBean implements Serializable{
	
	public String j_username;// 用户名称
	public String j_password;// 用户密码

	
	public String getJ_username() {
		return j_username;
	}


	public void setJ_username(String j_username) {
		this.j_username = j_username;
	}


	public String getJ_password() {
		return j_password;
	}


	public void setJ_password(String j_password) {
		this.j_password = j_password;
	}


	@Override
	public String verify() {
		String errmsg = "";

		if (j_username == null || j_password == null || ("").equals(j_username)
				|| ("").equals(j_password)) {
			errmsg = "用户名和密码不能为空！";
		}else{
		if (j_password.length() < 6) {
			errmsg = "密码的长度为6-20，请重新输入密码！";
		}
		}
		return errmsg;
	}
}
