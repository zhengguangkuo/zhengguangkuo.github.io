package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;

public class OriginalDealNumReqBean extends Request{
	//交易日期
	private String Originalnum;
	public String getOriginalnum() {
		return Originalnum;
	}

	public void setOriginalnum(String originalnum) {
		Originalnum = originalnum;
	}

}
