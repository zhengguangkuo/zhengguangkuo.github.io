package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;

public class CreditConsumeReqBean extends Request{
	//积分消费录入界面上要提交到后台的消费金额
	private String consumeMoney;
	//发券方机构标识
	private String sysInstId;
	//发券方标识
	private String issId;
	public String getConsumeMoney() {
		return consumeMoney;
	}
	public void setConsumeMoney(String consumeMoney) {
		this.consumeMoney = consumeMoney;
	}
	public String getSysInstId() {
		return sysInstId;
	}
	public void setSysInstId(String sysInstId) {
		this.sysInstId = sysInstId;
	}
	public String getIssId() {
		return issId;
	}
	public void setIssId(String issId) {
		this.issId = issId;
	}
	
	
}
