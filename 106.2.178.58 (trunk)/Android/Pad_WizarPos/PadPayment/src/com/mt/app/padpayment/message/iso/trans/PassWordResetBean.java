package com.mt.app.padpayment.message.iso.trans;

import com.mt.android.message.annotation.MsgDirection;
import com.mt.android.message.mdo.MessageBody;

public class PassWordResetBean extends MessageBody {
	@MsgDirection(direction = "double")
	private String cardNo = "";// ��������2
	
	@MsgDirection(direction = "double")
	private String processCode = "";// ���״�����3
	
	@MsgDirection(direction = "double")
	private String sysTraceAuditNum = "";// �ܿ���ϵͳ���ٺ�11
	
	@MsgDirection(direction = "response")
	private String localTransTime = "";// �ܿ������ڵ�ʱ��12
	
	@MsgDirection(direction = "response")
	private String localTransDate = "";// �ܿ������ڵ�����13
	
	@MsgDirection(direction = "double")
	private String dateExpired = "";//����Ч��14
	
	@MsgDirection(direction = "response")
	private String settleDate = "";// ��������15
	
	@MsgDirection(direction = "request")
	private String serviceEntryMode = "";//��������뷽ʽ��22
	
	@MsgDirection(direction = "double")
	private String serviceConditionMode = "";// �����������25
	
	@MsgDirection(direction = "request")
	private String servicePINCaptureCode = "";// �����PIN��ȡ��26
	
	@MsgDirection(direction = "response")
	private String acqInstIdentCode = "";//������ʶ��32
	
	@MsgDirection(direction = "response")
	private String retReferNum = "";// �����ο���37
	
	@MsgDirection(direction = "response")
	private String respCode = "";// Ӧ����39
	
	@MsgDirection(direction = "double")
	private String cardAcceptTermIdent = "";//�ܿ����ն˱�ʶ��41
	
	@MsgDirection(direction = "double")
	private String cardAcceptIdentcode = "";// �ܿ�����ʶ��42
	
	@MsgDirection(direction = "response")
	private String additRespData = "";// ������Ӧ����44
	
	@MsgDirection(direction = "request")
	private String additDataPrivate = "";//��������48
	
	@MsgDirection(direction = "request")
	private String pinData = "";//���˱�ʶ������52
	
	@MsgDirection(direction = "double")
	private String securityRelatedControl = "";//��ȫ������Ϣ53
	
	@MsgDirection(direction = "double")
	private String reservedPrivate1 = "";//Ӧ�ñ�ʶ59
	
	@MsgDirection(direction = "double")
	private String reservedPrivate2 = "";//���κ�60
	

	@MsgDirection(direction = "response")
	private String reservedPrivate3 = "";//������62
	
	@MsgDirection(direction = "response")
	private String reservedPrivate4 = "";//Ӧ����Ϣ63
	
	@MsgDirection(direction = "double")
	private String messageAuthentCode = "";//MAC64

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public String getSysTraceAuditNum() {
		return sysTraceAuditNum;
	}

	public void setSysTraceAuditNum(String sysTraceAuditNum) {
		this.sysTraceAuditNum = sysTraceAuditNum;
	}

	public String getLocalTransTime() {
		return localTransTime;
	}

	public void setLocalTransTime(String localTransTime) {
		this.localTransTime = localTransTime;
	}

	public String getLocalTransDate() {
		return localTransDate;
	}

	public void setLocalTransDate(String localTransDate) {
		this.localTransDate = localTransDate;
	}

	public String getDateExpired() {
		return dateExpired;
	}

	public void setDateExpired(String dateExpired) {
		this.dateExpired = dateExpired;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getServiceEntryMode() {
		return serviceEntryMode;
	}

	public void setServiceEntryMode(String serviceEntryMode) {
		this.serviceEntryMode = serviceEntryMode;
	}

	public String getServiceConditionMode() {
		return serviceConditionMode;
	}

	public void setServiceConditionMode(String serviceConditionMode) {
		this.serviceConditionMode = serviceConditionMode;
	}

	public String getServicePINCaptureCode() {
		return servicePINCaptureCode;
	}

	public void setServicePINCaptureCode(String servicePINCaptureCode) {
		this.servicePINCaptureCode = servicePINCaptureCode;
	}

	public String getAcqInstIdentCode() {
		return acqInstIdentCode;
	}

	public void setAcqInstIdentCode(String acqInstIdentCode) {
		this.acqInstIdentCode = acqInstIdentCode;
	}

	public String getRetReferNum() {
		return retReferNum;
	}

	public void setRetReferNum(String retReferNum) {
		this.retReferNum = retReferNum;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getCardAcceptTermIdent() {
		return cardAcceptTermIdent;
	}

	public void setCardAcceptTermIdent(String cardAcceptTermIdent) {
		this.cardAcceptTermIdent = cardAcceptTermIdent;
	}

	public String getCardAcceptIdentcode() {
		return cardAcceptIdentcode;
	}

	public void setCardAcceptIdentcode(String cardAcceptIdentcode) {
		this.cardAcceptIdentcode = cardAcceptIdentcode;
	}

	public String getAdditRespData() {
		return additRespData;
	}

	public void setAdditRespData(String additRespData) {
		this.additRespData = additRespData;
	}

	public String getAdditDataPrivate() {
		return additDataPrivate;
	}

	public void setAdditDataPrivate(String additDataPrivate) {
		this.additDataPrivate = additDataPrivate;
	}

	public String getPinData() {
		return pinData;
	}

	public void setPinData(String pinData) {
		this.pinData = pinData;
	}

	public String getSecurityRelatedControl() {
		return securityRelatedControl;
	}

	public void setSecurityRelatedControl(String securityRelatedControl) {
		this.securityRelatedControl = securityRelatedControl;
	}

	public String getReservedPrivate1() {
		return reservedPrivate1;
	}

	public void setReservedPrivate1(String reservedPrivate1) {
		this.reservedPrivate1 = reservedPrivate1;
	}

	public String getReservedPrivate2() {
		return reservedPrivate2;
	}

	public void setReservedPrivate2(String reservedPrivate2) {
		this.reservedPrivate2 = reservedPrivate2;
	}

	public String getReservedPrivate3() {
		return reservedPrivate3;
	}

	public void setReservedPrivate3(String reservedPrivate3) {
		this.reservedPrivate3 = reservedPrivate3;
	}

	public String getReservedPrivate4() {
		return reservedPrivate4;
	}

	public void setReservedPrivate4(String reservedPrivate4) {
		this.reservedPrivate4 = reservedPrivate4;
	}

	public String getMessageAuthentCode() {
		return messageAuthentCode;
	}

	public void setMessageAuthentCode(String messageAuthentCode) {
		this.messageAuthentCode = messageAuthentCode;
	}
	
}

