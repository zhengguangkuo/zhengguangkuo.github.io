package com.mt.app.payment.requestbean;
/**
 *  
 * @author mzh
 *
 */
public class MerchListReqBean {
	//当前页数
	private int page;
	//数目
	private int rows;
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
	
}
