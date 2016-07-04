package com.mt.app.payment.requestbean;

public class NearByMerchDisReqBean {
	//当前页数
	private int page;
	//每页数量
	private int rows;
	//商户ID
	private String merchId;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getMerchId() {
		return merchId;
	}
	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	
}
