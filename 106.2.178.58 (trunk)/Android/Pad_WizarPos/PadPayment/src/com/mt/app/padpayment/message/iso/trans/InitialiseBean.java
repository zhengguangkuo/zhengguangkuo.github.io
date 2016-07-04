package com.mt.app.padpayment.message.iso.trans;

import com.mt.android.message.annotation.MsgDirection;
import com.mt.android.message.mdo.MessageBody;

/**
 * 初始化交易
 * 
 * @author SKS
 * 
 */
public class InitialiseBean extends MessageBody {
	
	@MsgDirection(direction = "response")
	private String localTransTime = "";// 受卡方所在地时间
	
	@MsgDirection(direction = "response")
	private String localTransDate = "";// 受卡发所在地日期
	
	@MsgDirection(direction = "response")
	private String acqInstIdentCode = "";// 受理方标识码
	
	@MsgDirection(direction = "response")
	private String retReferNum = "";// 检索参考号
	
	@MsgDirection(direction = "response")
	private String respCode = "";// 应答码
	
	@MsgDirection(direction = "response")
	private String cardAcceptTermIdent = "";// 受卡机终端标识码
	
	@MsgDirection(direction = "response")
	private String cardAcceptIdentcode = "";// 受卡方标识码
	
	@MsgDirection(direction = "request")
	private String reservedPrivate1 = "";// 终端初始化信息

	@MsgDirection(direction = "double")
	private String reservedPrivate2 = "";// 自定义域
	
	@MsgDirection(direction = "response")
	private String reservedPrivate4 = "";// 应答信息

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
