package com.mt.app.payment.requestbean;
/**
 *   ��ѯ�Ѱ󶨵�Ӧ���б���������
 * @author mzh
 *
 */
public class ObtainPayAppReqBean {
	//��ǰҳ��
	private int page; 
	//ÿҳ�ϵ�����
	private int rows;
	//Ӧ������
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
