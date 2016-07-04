package com.mt.app.payment.requestbean;

public class DisMerchMapReq {
	//当前页数
	private int page;
	//每页数据条数
	private int rows;
	//用户当前坐标
	private String point;
	//距离范围
	private double merchantDistance;
	/** 用于标记当前的界面是在优惠券界面 还是在折扣商家界面。用于距离搜索中走不同接口进行搜索 */
	private String actOrDisFlag ;
	
	public String getActOrDisFlag() {
		return actOrDisFlag;
	}
	public void setActOrDisFlag(String actOrDisFlag) {
		this.actOrDisFlag = actOrDisFlag;
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
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public double getMerchantDistance() {
		return merchantDistance;
	}
	public void setMerchantDistance(double merchantDistance) {
		this.merchantDistance = merchantDistance;
	}
	
}
