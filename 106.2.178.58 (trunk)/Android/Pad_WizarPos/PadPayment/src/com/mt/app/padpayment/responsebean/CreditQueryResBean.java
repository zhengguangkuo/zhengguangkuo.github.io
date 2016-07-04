package com.mt.app.padpayment.responsebean;

import java.io.Serializable;

import com.mt.android.sys.bean.base.ResponseBean;
/**
 * 

 * @Description:积分查询的列表

 * @author:dw

 * @time:2013-8-14 下午7:38:41
 */
public class CreditQueryResBean extends ResponseBean implements Serializable {
	private String issId;// 发卷对象标识
	private String credits; // 积分余额
	private String money;//人民币余额
	private String issCshort;//发卷机构名称(简称)
	private String sysInstId;//发券系统id
	
	

	public String getIssCshort() {
		return issCshort;
	}

	public void setIssCshort(String issCshort) {
		this.issCshort = issCshort;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getIssId() {
		return issId;
	}

	public void setIssId(String issId) {
		this.issId = issId;
	}

	public String getSysInstId() {
		return sysInstId;
	}

	public void setSysInstId(String sysInstId) {
		this.sysInstId = sysInstId;
	}

	
}
