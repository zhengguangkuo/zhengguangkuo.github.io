package com.mt.app.payment.requestbean;
/**
 *  ��ѯ�Ż�ȯ���� ���������
 * @author mzh
 *
 */
public class PackageSelectReqBean {
	//����
	private String city;
	//��ǰҳ��
	private int page; 
    //ÿҳ�ϵ�����
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
