package com.mt.app.padpayment.message.iso.trans;

import com.mt.android.message.annotation.MsgDirection;
import com.mt.android.message.mdo.MessageBody;

/**
 * 参数下载交易
 * 
 * @author SKS
 * 
 */
public class LoadParameterBean extends MessageBody {


	@MsgDirection(direction = "response")
	private String localTransTime = "";// 受卡方所在地时间

	@MsgDirection(direction = "response")
	private String localTransDate = "";// 受卡发所在地日期

	@MsgDirection(direction = "response")
	private String retReferNum = "";// 检索参考号

	@MsgDirection(direction = "response")
	private String respCode = "";// 应答码

	@MsgDirection(direction = "double")
	private String cardAcceptTermIdent = "";// 受卡机终端标识码

	@MsgDirection(direction = "double")
	private String cardAcceptIdentcode = "";// 受卡方标识码

	@MsgDirection(direction = "double")
	private String reservedPrivate1 = "";// 终端参数信息

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

	public String getReservedPrivate1() {
		return reservedPrivate1;
	}

	public void setReservedPrivate1(String reservedPrivate1) {
		this.reservedPrivate1 = reservedPrivate1;
	}
}
