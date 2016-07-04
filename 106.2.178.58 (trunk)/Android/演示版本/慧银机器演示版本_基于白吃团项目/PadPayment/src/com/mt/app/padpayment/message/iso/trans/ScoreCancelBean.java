package com.mt.app.padpayment.message.iso.trans;

import com.mt.android.message.annotation.MsgDirection;
import com.mt.android.message.mdo.MessageBody;
/**
 *   ���ֳ���
 * @author mzh
 *
 */
public class ScoreCancelBean extends MessageBody{
	
	@MsgDirection(direction = "double")
	private String  track2 = ""; // ��������
	
	@MsgDirection(direction = "double")
	private String processCode = ""; //���״�����
	
	@MsgDirection(direction = "double")
	private String transAmount = ""; // ���׽��
	
	@MsgDirection(direction = "double")
	private String sysTraceAuditNum = ""; //�ܿ���ϵͳ���ٺ�
	
	@MsgDirection(direction = "response")
	private String localTransTime = ""; //�ܿ������ڵ�ʱ��
	
	@MsgDirection(direction = "response")
	private String localTransDate = ""; //�ܿ������ڵ�����
	
	@MsgDirection(direction = "double")
	private String dateExpired = "";//����Ч��
	
	@MsgDirection(direction = "response")
	private String settleDate = ""; //��������
	
	@MsgDirection(direction = "request")
	private String serviceEntryMode = "";//��������뷽ʽ��
	
	@MsgDirection(direction = "double")
	private String serviceConditionMode = "";//�����������
	
	@MsgDirection(direction = "request")
	private String servicePINCaptureCode = "";//�����PIN��ȡ��
	
	@MsgDirection(direction = "response")
	private String acqInstIdentCode = "";//������ʶ��
	
	@MsgDirection(direction = "double")
	private String retReferNum = "";//�����ο���
	
	@MsgDirection(direction = "response")
	private String respCode = "";//Ӧ����
	
	@MsgDirection(direction = "double")
	private String cardAcceptTermIdent = "";//�ܿ����ն˱�ʶ��
	
	@MsgDirection(direction = "double")
	private String cardAcceptIdentcode = "";//�ܿ�����ʶ��
	
	@MsgDirection(direction = "response")
	private String additRespData = "";//������Ӧ����
	
	@MsgDirection(direction = "double")
	private String currencyTransCode = "";//���׻��Ҵ��� 
	
	@MsgDirection(direction = "request")
	private String pinData = "";//���˱�ʶ������
	
	@MsgDirection(direction = "double")
	private String securityRelatedControl = "";//��ȫ������Ϣ
	
	@MsgDirection(direction = "double")
	private String organId = "";//��ȯ������ʶ
	
	@MsgDirection(direction = "double")
	private String reservedPrivate2 = "";//���κ�
	
	@MsgDirection(direction = "double")
	private String couponsAdvertId = "";//��������ʶ
	
	@MsgDirection(direction = "request")
	private String originalMessage = "";//ԭʼ��Ϣ��
	
	@MsgDirection(direction = "response")
	private String reservedPrivate4 = "";//Ӧ����Ϣ
	
	@MsgDirection(direction = "double")
	private String messageAuthentCode = "";//MAC

	
	public String getCouponsAdvertId() {
		return couponsAdvertId;
	}

	public void setCouponsAdvertId(String couponsAdvertId) {
		this.couponsAdvertId = couponsAdvertId;
	}

	

	public String getTrack2() {
		return track2;
	}

	public void setTrack2(String track2) {
		this.track2 = track2;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getSysTraceAuditNum() {
		return sysTraceAuditNum;
	}
	

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
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

	public String getCurrencyTransCode() {
		return currencyTransCode;
	}

	public void setCurrencyTransCode(String currencyTransCode) {
		this.currencyTransCode = currencyTransCode;
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

	public String getReservedPrivate2() {
		return reservedPrivate2;
	}

	public void setReservedPrivate2(String reservedPrivate2) {
		this.reservedPrivate2 = reservedPrivate2;
	}

	public String getOriginalMessage() {
		return originalMessage;
	}

	public void setOriginalMessage(String originalMessage) {
		this.originalMessage = originalMessage;
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
