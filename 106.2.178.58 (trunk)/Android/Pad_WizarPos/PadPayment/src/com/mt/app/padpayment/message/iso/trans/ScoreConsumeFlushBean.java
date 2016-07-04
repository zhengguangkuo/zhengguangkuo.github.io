package com.mt.app.padpayment.message.iso.trans;

import com.mt.android.message.annotation.MsgDirection;
import com.mt.android.message.mdo.MessageBody;
/**
 *  积分消费冲正
 * @author mzh
 *
 */
public class ScoreConsumeFlushBean extends MessageBody{
	
	@MsgDirection(direction = "double")
	private String cardNo = ""; // 机卡卡号
	
	@MsgDirection(direction = "double")
	private String processCode = ""; //交易处理码
	
	@MsgDirection(direction = "double")
	private String transAmount = ""; // 交易金额
	
	@MsgDirection(direction = "double")
	private String sysTraceAuditNum = ""; //受卡方系统跟踪号
	
	@MsgDirection(direction = "response")
	private String localTransTime = ""; //受卡方所在地时间
	
	@MsgDirection(direction = "response")
	private String localTransDate = ""; //受卡发所在地日期
	
	@MsgDirection(direction = "double")
	private String dateExpired = "";//卡有效期
	
	@MsgDirection(direction = "response")
	private String settleDate = ""; //清算日期
	
	@MsgDirection(direction = "request")
	private String serviceEntryMode = "";//服务点输入方式码
	
	@MsgDirection(direction = "double")
	private String serviceConditionMode = "";//服务点条件码
	
	@MsgDirection(direction = "response")
	private String acqInstIdentCode = "";//受理方标识码
	
	@MsgDirection(direction = "response")
	private String retReferNum = "";//检索参考号
	
	@MsgDirection(direction = "double")
	private String respCode = "";//应答码
	
	@MsgDirection(direction = "double")
	private String cardAcceptTermIdent = "";//受卡机终端标识码
	
	@MsgDirection(direction = "double")
	private String cardAcceptIdentcode = "";//受卡方标识码
	
	@MsgDirection(direction = "response")
	private String additRespData = "";//附加响应数据
	
	@MsgDirection(direction = "double")
	private String currencyTransCode = "";//交易货币代码 
	
	@MsgDirection(direction = "double")
	private String reservedPrivate1 = "";//应用标识
	
	@MsgDirection(direction = "double")
	private String reservedPrivate2 = "";//批次号
	
	@MsgDirection(direction = "response")
	private String reservedPrivate3 = "";//卡名称
	
	@MsgDirection(direction = "response")
	private String reservedPrivate4 = "";//应答信息
	
	@MsgDirection(direction = "double")
	private String messageAuthentCode = "";//MAC

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

	public String getTransAmount() {
		return transAmount;
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
