package com.mt.app.payment.requestbean;

public class DisMerchMapReq {
	//��ǰҳ��
	private int page;
	//ÿҳ��������
	private int rows;
	//�û���ǰ����
	private String point;
	//���뷶Χ
	private double merchantDistance;
	/** ���ڱ�ǵ�ǰ�Ľ��������Ż�ȯ���� �������ۿ��̼ҽ��档���ھ����������߲�ͬ�ӿڽ������� */
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
