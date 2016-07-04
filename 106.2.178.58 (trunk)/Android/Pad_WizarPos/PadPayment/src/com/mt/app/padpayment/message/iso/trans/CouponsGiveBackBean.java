package com.mt.app.padpayment.message.iso.trans;

import com.mt.android.message.annotation.MsgDirection;
import com.mt.android.message.mdo.MessageBody;
/**
 * 

 * @Description:�Ż�ȯ����

 * @author:dw

 * @time:2013-7-23 ����10:16:02
 */
public class CouponsGiveBackBean extends MessageBody{

	@MsgDirection(direction = "double")
	private String cardNo = ""; // ��������
	
	@MsgDirection(direction = "double")
	private String  processCode = ""; // ���״�����
	
	@MsgDirection(direction = "double")
	private String  sysTraceAuditNum = ""; //�ܿ���ϵͳ���ٺ�
	
	@MsgDirection(direction = "response")
	private String localTransTime = ""; //�ܿ������ڵ�ʱ��
	
	@MsgDirection(direction = "response")
	private String  settleDate = ""; //�ܿ������ڵ�����
	
	@MsgDirection(direction = "response")
	private String  localTransDate = ""; //��������
	
	@MsgDirection(direction = "response")
	private String retReferNum = ""; //�����ο���
	
	@MsgDirection(direction = "response")
	private String authorIdentResp = ""; //��Ȩ��ʶӦ����
	
	@MsgDirection(direction = "response")
	private String respCode = ""; //Ӧ����
	
	@MsgDirection(direction = "double")
	private String cardAcceptTermIdent = ""; //�ܿ����ն˱�ʶ��
	
	@MsgDirection(direction = "double")
	private String cardAcceptIdentcode = ""; //�ܿ�����ʶ��
	
	@MsgDirection(direction = "request")
	private String reservedPrivate1 = ""; //�Żݾ�����
	
	@MsgDirection(direction = "response")
	private String reservedPrivate4 = ""; //Ӧ����Ϣ
	
	@MsgDirection(direction = "double")
	private String messageAuthentCode = ""; //mac

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

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getAuthorIdentResp() {
		return authorIdentResp;
	}

	public void setAuthorIdentResp(String authorIdentResp) {
		this.authorIdentResp = authorIdentResp;
	}

	public String getReservedPrivate1() {
		return reservedPrivate1;
	}

	public void setReservedPrivate1(String reservedPrivate1) {
		this.reservedPrivate1 = reservedPrivate1;
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
