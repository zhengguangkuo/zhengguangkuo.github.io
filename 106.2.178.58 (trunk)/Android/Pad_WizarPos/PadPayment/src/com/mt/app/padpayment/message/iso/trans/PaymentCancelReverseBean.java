package com.mt.app.padpayment.message.iso.trans;

import com.mt.android.message.annotation.MsgDirection;
import com.mt.android.message.mdo.MessageBody;

/**
 * 

 * @Description:消费撤销冲正bean

 * @author:liangmeng

 * @time:2013-7-16 下午7:37:04
 */
public class PaymentCancelReverseBean extends MessageBody{
	@MsgDirection(direction = "double")
	private String cardNo = "";// 机卡卡号2
	
	@MsgDirection(direction = "double")
	private String  track2 = ""; // 基卡卡号
	
	@MsgDirection(direction = "double")
	private String processCode = "";// 交易处理码3
	
	@MsgDirection(direction = "double")
	private String transAmount = "";// 交易金额4
	
	@MsgDirection(direction = "double")
	private String discountAmount = "";// 附加金额5
	
	@MsgDirection(direction = "double")
	private String sysTraceAuditNum = "";// 受卡方系统跟踪号11
	
	@MsgDirection(direction = "response")
	private String localTransTime = "";// 受卡方所在地时间12
	
	@MsgDirection(direction = "response")
	private String localTransDate = "";// 受卡方所在地日期13
	
	@MsgDirection(direction = "double")
	private String dateExpired = "";//卡有效期14
	
	@MsgDirection(direction = "response")
	private String settleDate = "";// 清算日期15
	
	@MsgDirection(direction = "request")
	private String serviceEntryMode = "";//服务点输入方式码22
	
	@MsgDirection(direction = "double")
	private String serviceConditionMode = "";// 服务点条件码25
	
	@MsgDirection(direction = "response")
	private String acqInstIdentCode = "";//受理方标识码32
	
	@MsgDirection(direction = "response")
	private String retReferNum = "";// 检索参考号37
	
	@MsgDirection(direction = "request")
	private String authorIdentResp = "";// 授权码38
	
	
	@MsgDirection(direction = "double")
	private String respCode = "";// 应答码39
	
	@MsgDirection(direction = "double")
	private String cardAcceptTermIdent = "";//受卡机终端标识码41
	
	@MsgDirection(direction = "double")
	private String cardAcceptIdentcode = "";// 受卡方标识码42
	
	@MsgDirection(direction = "response")
	private String additRespData = "";// 附加响应数据44
	
	@MsgDirection(direction = "double")
	private String currencyTransCode = "";//交易货币代码49
	
	@MsgDirection(direction = "double")
	private String securityRelatedControl = "";//安全控制信息53
	
	@MsgDirection(direction = "double")
	private String swapCode = ""; //兑换码
	
	@MsgDirection(direction = "double")
	private String organId = ""; //发券机构标识
	
	@MsgDirection(direction = "double")
	private String reservedPrivate1 = "";//应用标识59
	
	@MsgDirection(direction = "double")
	private String reservedPrivate2 = "";//批次号60
	
	@MsgDirection(direction = "request")
	private String originalMessage = "";//原始信息域61
	
//	@MsgDirection(direction = "request")
//	private String originalMessage = "";//原批号61.1
//	
//	@MsgDirection(direction = "request")
//	private String cardNo = "";//原POS流水号61.2
	
	@MsgDirection(direction = "response")
	private String reservedPrivate4 = "";//应答信息63
	
	@MsgDirection(direction = "double")
	private String messageAuthentCode = "";//MAC64


	
	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

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
	
	public String getSwapCode() {
		return swapCode;
	}

	
	
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public void setSwapCode(String swapCode) {
		this.swapCode = swapCode;
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

	
	
	public String getTrack2() {
		return track2;
	}

	public void setTrack2(String track2) {
		this.track2 = track2;
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

	public String getAuthorIdentResp() {
		return authorIdentResp;
	}

	public void setAuthorIdentResp(String authorIdentResp) {
		this.authorIdentResp = authorIdentResp;
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
