package com.mt.app.payment.requestbean;

public class UserResetAccount {
     private String originalpwd;
     private String newpwd;
     private String email;
     private String mobile;
     
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOriginalpwd() {
		return originalpwd;
	}
	public void setOriginalpwd(String originalpwd) {
		this.originalpwd = originalpwd;
	}
	public String getNewpwd() {
		return newpwd;
	}
	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
