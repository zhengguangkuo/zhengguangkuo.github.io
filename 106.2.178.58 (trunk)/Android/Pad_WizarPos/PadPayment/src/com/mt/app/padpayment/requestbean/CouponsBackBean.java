package com.mt.app.padpayment.requestbean;

//�Ż�ȯ��������Bean
public class CouponsBackBean {
	private String count = "";// ����
	private String actIds = "";// �ID
	private String issuerId;//��ȯ�����ʶ
	private String c_iss_id;//Ӧ��id
	private String money;//���ѽ��
	
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getActIds() {
		return actIds;
	}

	public void setActIds(String actIds) {
		this.actIds = actIds;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public String getC_iss_id() {
		return c_iss_id;
	}

	public void setC_iss_id(String c_iss_id) {
		this.c_iss_id = c_iss_id;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
}
