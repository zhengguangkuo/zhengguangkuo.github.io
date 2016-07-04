package com.mt.app.padpayment.message.iso.trans;

import com.mt.android.message.annotation.MsgDirection;
import com.mt.android.message.mdo.MessageBody;

/**
 * ��ʼ������
 * 
 * @author SKS
 * 
 */
public class InitialiseBean extends MessageBody {
	
	@MsgDirection(direction = "response")
	private String localTransTime = "";// �ܿ������ڵ�ʱ��
	
	@MsgDirection(direction = "response")
	private String localTransDate = "";// �ܿ������ڵ�����
	
	@MsgDirection(direction = "response")
	private String acqInstIdentCode = "";// ������ʶ��
	
	@MsgDirection(direction = "response")
	private String retReferNum = "";// �����ο���
	
	@MsgDirection(direction = "response")
	private String respCode = "";// Ӧ����
	
	@MsgDirection(direction = "response")
	private String cardAcceptTermIdent = "";// �ܿ����ն˱�ʶ��
	
	@MsgDirection(direction = "response")
	private String cardAcceptIdentcode = "";// �ܿ�����ʶ��
	
	@MsgDirection(direction = "request")
	private String reservedPrivate1 = "";// �ն˳�ʼ����Ϣ

	@MsgDirection(direction = "double")
	private String reservedPrivate2 = "";// �Զ�����
	
	@MsgDirection(direction = "response")
	private String reservedPrivate4 = "";// Ӧ����Ϣ

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

	public String getReservedPrivate4() {
		return reservedPrivate4;
	}

	public void setReservedPrivate4(String reservedPrivate4) {
		this.reservedPrivate4 = reservedPrivate4;
	}
	
}
