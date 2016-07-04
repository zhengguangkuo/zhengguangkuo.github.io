package com.mt.app.payment.requestbean;
/**
 *   查询已领过的优惠券列表的请求对象
 * @author mzh
 *
 */
public class ObtainDiscountReqBean {
	//当前页数
	private int page;  
	//每页上的数量
	private int rows;  
	//坐标
	private String point;
	
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
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
