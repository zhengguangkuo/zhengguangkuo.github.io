package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;

public class CreditConsumeReqBean extends Request{
	//��������¼�������Ҫ�ύ����̨�����ѽ��
	private String consumeMoney;
	//��ȯ��������ʶ
	private String sysInstId;
	//��ȯ����ʶ
	private String issId;
	public String getConsumeMoney() {
		return consumeMoney;
	}
	public void setConsumeMoney(String consumeMoney) {
		this.consumeMoney = consumeMoney;
	}
	public String getSysInstId() {
		return sysInstId;
	}
	public void setSysInstId(String sysInstId) {
		this.sysInstId = sysInstId;
	}
	public String getIssId() {
		return issId;
	}
	public void setIssId(String issId) {
		this.issId = issId;
	}
	
	
}
