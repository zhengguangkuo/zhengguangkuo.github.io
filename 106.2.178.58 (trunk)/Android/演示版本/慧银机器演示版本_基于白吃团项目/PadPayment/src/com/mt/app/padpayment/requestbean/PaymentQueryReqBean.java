package com.mt.app.padpayment.requestbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class PaymentQueryReqBean implements Serializable {
	private String serialnum;
	private String referencenum;
	private String time;
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private ArrayList<Map<String, String>> list;

	public String getSerialnum() {
		return serialnum;
	}

	public void setSerialnum(String serialnum) {
		this.serialnum = serialnum;
	}

	public String getReferencenum() {
		return referencenum;
	}

	public void setReferencenum(String referencenum) {
		this.referencenum = referencenum;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public ArrayList<Map<String, String>> getList() {
		return list;
	}

	public void setList(ArrayList<Map<String, String>> list) {
		this.list = list;
	}

}
