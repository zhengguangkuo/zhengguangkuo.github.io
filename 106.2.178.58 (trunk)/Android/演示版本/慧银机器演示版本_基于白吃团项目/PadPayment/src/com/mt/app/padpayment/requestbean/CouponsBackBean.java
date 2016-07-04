package com.mt.app.padpayment.requestbean;

//优惠券领用请求Bean
public class CouponsBackBean {
	private String count = "";// 数量
	private String actIds = "";// 活动ID
	private String issuerId;//发券对象标识
	private String c_iss_id;//应用id
	private String money;//消费金额
	
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getActIds() {
		return actIds;
	}

	public void setActIds(String actIds) {
		this.actIds = actIds;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public String getC_iss_id() {
		return c_iss_id;
	}

	public void setC_iss_id(String c_iss_id) {
		this.c_iss_id = c_iss_id;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
}
