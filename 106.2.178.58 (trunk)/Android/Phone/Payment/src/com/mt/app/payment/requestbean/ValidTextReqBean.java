package com.mt.app.payment.requestbean;

public class ValidTextReqBean {
	public String mobile;//�绰����
	private String funcName ;	// �������
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String vertify(){
		String errmsg = "";
		
		if (mobile == null || mobile.length() == 0){
			errmsg = "������绰����";
		}else if (mobile.length() != 11){
			errmsg = "�绰����λ������";
		}
		
		return errmsg;
	}
}
