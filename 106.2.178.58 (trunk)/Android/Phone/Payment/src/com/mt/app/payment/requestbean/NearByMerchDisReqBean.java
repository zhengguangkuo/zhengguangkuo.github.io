package com.mt.app.payment.requestbean;

public class NearByMerchDisReqBean {
	//��ǰҳ��
	private int page;
	//ÿҳ����
	private int rows;
	//�̻�ID
	private String merchId;
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
	public String getMerchId() {
		return merchId;
	}
	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	
}
