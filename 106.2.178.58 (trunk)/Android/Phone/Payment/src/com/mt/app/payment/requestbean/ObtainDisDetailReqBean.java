package com.mt.app.payment.requestbean;
/**
 *  ��ѯ�Ż�ȯ���� ���������
 * @author mzh
 *
 */
public class ObtainDisDetailReqBean {
	//�Ż�ȯid
	private String actId;
	/** �̼�ID */
	private String merchId ;
	
	
	
	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getActId() {
		return actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}


	
}
