package com.mt.app.payment.requestbean;

public class Discount_disResBean {
	private int page;
	private int rows;
	private String point;
	private String orderBy;
	private String orderType;
	private String merchantDistance;
	private String area;//区域
	//private String businessArea;//商圈
	private String Lv2Category;//类型
	private String merchId ;		// 商家id
	
	public String getMerchId() {
		return merchId;
	}
	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getLv2Category() {
		return Lv2Category;
	}
	public void setLv2Category(String lv2Category) {
		Lv2Category = lv2Category;
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
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getMerchantDistance() {
		return merchantDistance;
	}
	public void setMerchantDistance(String merchantDistance) {
		this.merchantDistance = merchantDistance;
	}
	
}
