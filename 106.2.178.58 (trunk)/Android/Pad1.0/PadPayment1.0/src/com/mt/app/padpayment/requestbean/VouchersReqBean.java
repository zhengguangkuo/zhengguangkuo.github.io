package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;

public class VouchersReqBean extends Request{
	private String vouchers;   //�û������������ƾ֤��
	public String getVouchers() {
		return vouchers;
	}

	public void setVouchers(String vouchers) {
		this.vouchers = vouchers;
	}
}
