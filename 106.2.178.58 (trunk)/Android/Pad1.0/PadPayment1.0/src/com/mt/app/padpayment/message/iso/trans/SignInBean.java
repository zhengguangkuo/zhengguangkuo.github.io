package com.mt.app.padpayment.message.iso.trans;

import java.io.Serializable;

import com.mt.android.message.annotation.Encode;
import com.mt.android.message.annotation.MsgDirection;
import com.mt.android.message.mdo.MessageBody;

/**
 * 签到交易
 * @author SKS
 *
 */
public class SignInBean extends MessageBody implements Serializable{
	
	@MsgDirection(direction = "double")
	private String sysTraceAuditNum = "";//受卡方系统跟踪号
	
	@MsgDirection(direction = "response")
	private String localTransTime = "";// 受卡方所在地时间

	@MsgDirection(direction = "response")
	private String localTransDate = "";// 受卡发所在地日期
	
	@MsgDirection(direction = "response")
	private String acqInstIdentCode = "";//受理方标识码
	
	@MsgDirection(direction = "response")
	private String retReferNum = "";// 检索参考号

	@MsgDirection(direction = "response")
	private String respCode = "";// 应答码
	
	@MsgDirection(direction = "double")
	private String cardAcceptTermIdent = "";//受卡机终端标识码
	
	@MsgDirection(direction = "double")
	private String cardAcceptIdentcode = "";//受卡方标识码
	
	
	@MsgDirection(direction = "response")
	private String cardAcceptLocal = "";//受卡方名称地址
	
	@MsgDirection(direction = "double")
	private String reservedPrivate2 = "";//批次号
	
	@MsgDirection(direction = "response")
	@Encode(encode = "ISO-8859-1")
	private String reservedPrivate3 = "";//终端密钥
	
	@MsgDirection(direction = "response")
	private String reservedPrivate4 = "";//应答信息

	public String getReservedPrivate4() {
		return reservedPrivate4;
	}

	public void setReservedPrivate4(String reservedPrivate4) {
		this.reservedPrivate4 = reservedPrivate4;
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

	public String getCardAcceptLocal() {
		return cardAcceptLocal;
	}

	public void setCardAcceptLocal(String cardAcceptLocal) {
		this.cardAcceptLocal = cardAcceptLocal;
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
}
