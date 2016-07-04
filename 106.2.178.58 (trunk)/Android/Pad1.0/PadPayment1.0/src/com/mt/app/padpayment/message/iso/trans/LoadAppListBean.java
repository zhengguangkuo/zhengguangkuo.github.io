package com.mt.app.padpayment.message.iso.trans;

import com.mt.android.message.annotation.MsgDirection;
import com.mt.android.message.mdo.MessageBody;

/**
 * Ӧ���б�����
 * 
 * @author SKS
 * 
 */
public class LoadAppListBean extends MessageBody {

	@MsgDirection(direction = "double")
	private String cardNo = "";// ����

	@MsgDirection(direction = "response")
	private String localTransTime = "";// �ܿ������ڵ�ʱ��

	@MsgDirection(direction = "response")
	private String localTransDate = "";// �ܿ������ڵ�����

	@MsgDirection(direction = "double")
	private String cardSequenceNum = "";// Ӧ�����

	@MsgDirection(direction = "response")
	private String retReferNum = "";// �����ο���

	@MsgDirection(direction = "response")
	private String respCode = "";// Ӧ����

	@MsgDirection(direction = "double")
	private String cardAcceptTermIdent = "";// �ܿ����ն˱�ʶ��

	@MsgDirection(direction = "double")
	private String cardAcceptIdentcode = "";// �ܿ�����ʶ��

	@MsgDirection(direction = "double")
	private String reservedPrivate1 = "";// �ն˲�����Ϣ

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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

	public String getCardSequenceNum() {
		return cardSequenceNum;
	}

	public void setCardSequenceNum(String cardSequenceNum) {
		this.cardSequenceNum = cardSequenceNum;
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
}
