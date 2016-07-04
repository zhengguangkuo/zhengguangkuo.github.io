package com.mt.app.payment.requestbean;
/**
 *   查询已绑定的应用列表的请求对象
 * @author mzh
 *
 */
public class ObtainPayAppReqBean {
	//当前页数
	private int page; 
	//每页上的数量
	private int rows;
	//应用类型
	private String appType;    
	
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
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
