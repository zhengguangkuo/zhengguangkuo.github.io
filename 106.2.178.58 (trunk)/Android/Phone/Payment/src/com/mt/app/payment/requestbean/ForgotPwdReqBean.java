package com.mt.app.payment.requestbean;

import com.mt.android.sys.mvc.common.Request;

public class ForgotPwdReqBean extends Request {

	/**
	 * @param args
	 */
private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}
