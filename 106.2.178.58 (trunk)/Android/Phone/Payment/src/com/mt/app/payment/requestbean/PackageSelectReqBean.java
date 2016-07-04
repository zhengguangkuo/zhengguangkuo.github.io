package com.mt.app.payment.requestbean;
/**
 *  查询优惠券详情 的请求对象
 * @author mzh
 *
 */
public class PackageSelectReqBean {
	//城市
	private String city;
	//当前页数
	private int page; 
    //每页上的数量
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
