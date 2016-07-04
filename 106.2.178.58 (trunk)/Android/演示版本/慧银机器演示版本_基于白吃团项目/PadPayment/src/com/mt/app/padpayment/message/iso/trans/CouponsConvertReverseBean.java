package com.mt.app.padpayment.message.iso.trans;

import com.mt.android.message.annotation.MsgDirection;
import com.mt.android.message.mdo.MessageBody;
/**
 * 

 * @Description:优惠券兑换冲正

 * @author:dw

 * @time:2013-7-23 上午10:16:50
 */
public class CouponsConvertReverseBean extends MessageBody{
	@MsgDirection(direction = "double")
	private String  track2 = ""; // 基卡卡号
	
	@MsgDirection(direction = "double")
	private String processCode = "";// 交易处理码3
	
	@MsgDirection(direction = "double")
	private String transAmount = "";// 交易金额4
	
	@MsgDirection(direction = "double")
	private String discountAmount = "";//附加金额5
	
	@MsgDirection(direction = "double")
	private String sysTraceAuditNum = "";// 受卡方系统跟踪号11
	
	@MsgDirection(direction = "response")
	private String localTransTime = "";// 受卡方所在地时间12
	
	@MsgDirection(direction = "response")
	private String localTransDate = "";// 受卡方所在地日期13
	
	
	@MsgDirection(direction = "response")
	private String settleDate = "";// 清算日期15
	
	@MsgDirection(direction = "response")
	private String retReferNum = "";// 检索参考号37
	
	@MsgDirection(direction = "request")
	private String authorIdentResp = "";// 授权码38
	
	@MsgDirection(direction = "response")
	private String respCode = "";// 应答码39
	
	@MsgDirection(direction = "double")
	private String cardAcceptTermIdent = "";//受卡机终端标识码41
	
	@MsgDirection(direction = "double")
	private String cardAcceptIdentcode = "";// 受卡方标识码42
	
	@MsgDirection(direction = "response")
	private String additRespData = "";//附加响应数据44
	
	@MsgDirection(direction = "double")
	private String organId = "";//发券机构标识
	
	@MsgDirection(direction = "response")
	private String swapCode = "";//兑换码
	
	@MsgDirection(direction = "double")
	private String couponsAdvertId = "";//发卷对象标识58
	
	@MsgDirection(direction = "double")
	private String reservedPrivate2 = "";//批次号60
	
	@MsgDirection(direction = "response")
	private String reservedPrivate4 = "";//应答信息63
	
	@MsgDirection(direction = "double")
	private String messageAuthentCode = "";//MAC64

	

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

	public String getAdditRespData() {
		return additRespData;
	}

	public void setAdditRespData(String additRespData) {
		this.additRespData = additRespData;
	}
	

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getReservedPrivate2() {
		return reservedPrivate2;
	}

	public void setReservedPrivate2(String reservedPrivate2) {
		this.reservedPrivate2 = reservedPrivate2;
	}

	public String getCouponsAdvertId() {
		return couponsAdvertId;
	}

	
	
	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getSwapCode() {
		return swapCode;
	}

	public void setSwapCode(String swapCode) {
		this.swapCode = swapCode;
	}

	public void setCouponsAdvertId(String couponsAdvertId) {
		this.couponsAdvertId = couponsAdvertId;
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
