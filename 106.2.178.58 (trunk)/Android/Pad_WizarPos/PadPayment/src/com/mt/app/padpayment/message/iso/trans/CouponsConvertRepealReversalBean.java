package com.mt.app.padpayment.message.iso.trans;

import com.mt.android.message.annotation.MsgDirection;
import com.mt.android.message.mdo.MessageBody;

/**
 * 

 * @Description:�Ż�ȯ�����һ�����

 * @author:dw

 * @time:2013-11-7 ����11:37:05
 */
public class CouponsConvertRepealReversalBean extends MessageBody {
	
	
	@MsgDirection(direction = "double")
	private String processCode = "";// ���״�����3
	
	@MsgDirection(direction = "double")
	private String transAmount = "";// ���׽��4
	
	@MsgDirection(direction = "double")
	private String discountAmount = "";// �ֿ۽��5
	
	@MsgDirection(direction = "double")
	private String sysTraceAuditNum = "";// �ܿ���ϵͳ���ٺ�11
	
	@MsgDirection(direction = "response")
	private String localTransTime = "";// �ܿ������ڵ�ʱ��12
	
	@MsgDirection(direction = "response")
	private String localTransDate = "";// �ܿ������ڵ�����13
	
	
	@MsgDirection(direction = "response")
	private String settleDate = "";// ��������15
	
	@MsgDirection(direction = "double")
	private String track2 = "";// ��������35
	
	@MsgDirection(direction = "response")
	private String retReferNum = "";// �����ο���37
	
	@MsgDirection(direction = "response")
	private String respCode = "";// Ӧ����39
	
	@MsgDirection(direction = "double")
	private String cardAcceptTermIdent = "";//�ܿ����ն˱�ʶ��41
	
	@MsgDirection(direction = "double")
	private String cardAcceptIdentcode = "";// �ܿ�����ʶ��42
	
	@MsgDirection(direction = "response")
	private String additRespData = "";//���������ʶ44
	
	@MsgDirection(direction = "double")
	private String organId = "";//��ȯ������ʶ
	
	@MsgDirection(direction = "double")
	private String swapCode = "";//�һ���57
	
	@MsgDirection(direction = "double")
	private String couponsAdvertId = "";//��������ʶ58
	

	@MsgDirection(direction = "double")
	private String reservedPrivate2 = "";//���κ�60
	
	@MsgDirection(direction = "request")
	private String originalMessage = "";//ԭʼ��Ϣ��
	
	
	@MsgDirection(direction = "response")
	private String reservedPrivate4 = "";//Ӧ����Ϣ63
	
	@MsgDirection(direction = "double")
	private String messageAuthentCode = "";//MAC64
	
	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getAdditRespData() {
		return additRespData;
	}

	public void setAdditRespData(String additRespData) {
		this.additRespData = additRespData;
	}

	public String getSwapCode() {
		return swapCode;
	}

	public void setSwapCode(String swapCode) {
		this.swapCode = swapCode;
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

	
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getTransAmount() {
		return transAmount;
	}
	

	public String getOriginalMessage() {
		return originalMessage;
	}

	public void setOriginalMessage(String originalMessage) {
		this.originalMessage = originalMessage;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
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
	

	public String getCouponsAdvertId() {
		return couponsAdvertId;
	}

	public void setCouponsAdvertId(String couponsAdvertId) {
		this.couponsAdvertId = couponsAdvertId;
	}

	public void setLocalTransDate(String localTransDate) {
		this.localTransDate = localTransDate;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
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


	public String getReservedPrivate4() {
		return reservedPrivate4;
	}

	public void setReservedPrivate4(String reservedPrivate4) {
		this.reservedPrivate4 = reservedPrivate4;
	}

	public String getReservedPrivate2() {
		return reservedPrivate2;
	}

	public void setReservedPrivate2(String reservedPrivate2) {
		this.reservedPrivate2 = reservedPrivate2;
	}

	public String getMessageAuthentCode() {
		return messageAuthentCode;
	}

	public void setMessageAuthentCode(String messageAuthentCode) {
		this.messageAuthentCode = messageAuthentCode;
	}

	
}