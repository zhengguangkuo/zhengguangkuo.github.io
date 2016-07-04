package com.mt.app.payment.requestbean;

/**
 * 

 * @Description:折扣商家搜索功能

 * @author:dw

 * @time:2013-9-10 下午8:21:42
 */
public class Discount_SearchResBean {
	private int page;
	private int rows;
	private String point;  //坐标
	private String merchName;//商户名称
	/** 区域 */
	private String area ;
	/** 类别 */
	private String lv2Category ;
	
	
	public String getLv2Category() {
		return lv2Category;
	}
	public void setLv2Category(String lv2Category) {
		this.lv2Category = lv2Category;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
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
	public String getMerchName() {
		return merchName;
	}
	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}
	

	
}
