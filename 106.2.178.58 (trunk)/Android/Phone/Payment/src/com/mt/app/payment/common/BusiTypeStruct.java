package com.mt.app.payment.common;

public class BusiTypeStruct {
	public String doUrl = "";// ������ʹ�õ�url
	public String josonResultType;// ���ؽ����ʹ�õĽṹ
	public int messId;// ��ҵ��������ʹ�õ���ϢID
	
	public String getDoUrl() {
		return doUrl;
	}

	public void setDoUrl(String doUrl) {
		this.doUrl = doUrl;
	}

	public int getMessId() {
		return messId;
	}

	public void setMessId(int messId) {
		this.messId = messId;
	}

	public String getJosonResultType() {
		return josonResultType;
	}

	public void setJosonResultType(String josonResultType) {
		this.josonResultType = josonResultType;
	}

	
}
