package com.mt.app.payment.requestbean;
/**
 *   ��ѯ��������Ż�ȯ�б���������
 * @author mzh
 *
 */
public class ObtainDiscountReqBean {
	//��ǰҳ��
	private int page;  
	//ÿҳ�ϵ�����
	private int rows;  
	//����
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
