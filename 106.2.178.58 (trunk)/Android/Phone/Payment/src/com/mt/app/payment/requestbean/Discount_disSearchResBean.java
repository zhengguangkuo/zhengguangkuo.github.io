package com.mt.app.payment.requestbean;

import android.R.integer;

/**
 * 

 * @Description:�Ż�ȯ��������

 * @author:dw

 * @time:2013-9-10 ����5:33:45
 */
public class Discount_disSearchResBean {
	private int page;
	private int rows; 
	private String point ;
	private String merchName;//�̻�����
	/** ���� */
	private String area ;
	/** ��� */
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
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
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
	public String getMerchName() {
		return merchName;
	}
	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}
	
	
	
}
