package com.mt.android.driver.card;

import com.mt.android.sys.bean.base.ResponseBean;

public class CardReadBean extends ResponseBean{
       private String cardNo = "";

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
}
