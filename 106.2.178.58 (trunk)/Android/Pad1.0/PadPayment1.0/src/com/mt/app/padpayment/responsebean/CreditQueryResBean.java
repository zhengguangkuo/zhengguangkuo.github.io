package com.mt.app.padpayment.responsebean;

import java.io.Serializable;

import com.mt.android.sys.bean.base.ResponseBean;
/**
 * 

 * @Description:���ֲ�ѯ���б�

 * @author:dw

 * @time:2013-8-14 ����7:38:41
 */
public class CreditQueryResBean extends ResponseBean implements Serializable {
	private String issId;// ��������ʶ
	private String credits; // �������
	private String money;//��������
	private String issCshort;//�����������(���)
	private String sysInstId;//��ȯϵͳid
	
	

	public String getIssCshort() {
		return issCshort;
	}

	public void setIssCshort(String issCshort) {
		this.issCshort = issCshort;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getIssId() {
		return issId;
	}

	public void setIssId(String issId) {
		this.issId = issId;
	}

	public String getSysInstId() {
		return sysInstId;
	}

	public void setSysInstId(String sysInstId) {
		this.sysInstId = sysInstId;
	}

	
}
