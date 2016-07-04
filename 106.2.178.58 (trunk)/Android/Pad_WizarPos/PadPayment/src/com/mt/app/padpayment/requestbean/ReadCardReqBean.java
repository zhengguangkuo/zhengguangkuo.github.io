package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;

public class ReadCardReqBean extends Request{
	private String CardNum;
	
	

	public String getCardNum() {
		return CardNum;
	}

	public void setCardNum(String cardNum) {
		CardNum = cardNum;
	}

	
	
}
