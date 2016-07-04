package com.mt.app.payment.requestbean;

public class ValidTextReqBean {
	public String mobile;//电话号码
	private String funcName ;	// 标记请求
	
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
			errmsg = "请输入电话号码";
		}else if (mobile.length() != 11){
			errmsg = "电话号码位数错误";
		}
		
		return errmsg;
	}
}
