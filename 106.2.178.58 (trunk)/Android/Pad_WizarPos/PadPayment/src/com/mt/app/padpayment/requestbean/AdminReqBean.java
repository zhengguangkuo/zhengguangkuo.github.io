package com.mt.app.padpayment.requestbean;

import java.io.Serializable;

import com.mt.android.sys.mvc.common.Request;
/**
 * 

 * @Description:����û�bean

 * @author:dw

 * @time:2013-8-6 ����1:54:58
 */													 
public class AdminReqBean extends Request implements Serializable{
	private String userId;  //�û����
	private String userName ; //�û���
	private String password; //����
	private String newpassword;//������
	private String eqnewPassword;//ȷ��������
	private String limits; //Ȩ��  1 ��Ա    2����
	public void setUserId(String userId){
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public String getLimits() {
		return limits;
	}
	public void setLimits(String limits) {
		this.limits = limits;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getEqnewPassword() {
		return eqnewPassword;
	}
	public void setEqnewPassword(String eqnewPassword) {
		this.eqnewPassword = eqnewPassword;
	}
	
	
	
}
