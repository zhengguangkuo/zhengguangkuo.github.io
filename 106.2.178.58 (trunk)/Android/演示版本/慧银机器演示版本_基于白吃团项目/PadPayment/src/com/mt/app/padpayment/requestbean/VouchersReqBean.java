package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;

public class VouchersReqBean extends Request{
	private String vouchers;   //用户界面上输入的凭证号
	public String getVouchers() {
		return vouchers;
	}

	public void setVouchers(String vouchers) {
		this.vouchers = vouchers;
	}
}
