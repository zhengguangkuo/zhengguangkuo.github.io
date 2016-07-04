package com.mt.app.padpayment.message.iso.trans;

import com.mt.android.message.annotation.MsgDirection;
import com.mt.android.message.mdo.MessageBody;

/**
 * 
 * 
 * @Description:领用优惠券bean
 * 
 * @author:dw
 * 
 * @time:2013-7-16 下午8:22:27
 */
public class ReceiveCouponsBean extends MessageBody {

	@MsgDirection(direction = "double")
	private String  track2 = ""; // 基卡卡号

	@MsgDirection(direction = "double")
	private String transAmount = "";// 交易金额4
	
	@MsgDirection(direction = "double")
	private String processCode = ""; // 交易处理码

	@MsgDirection(direction = "double")
	private String sysTraceAuditNum = ""; // 受卡方系统跟踪号

	@MsgDirection(direction = "response")
	private String localTransTime = ""; // 受卡方所在地时间

	@MsgDirection(direction = "response")
	private String localTransDate = ""; // 受卡发所在地日期

	@MsgDirection(direction = "response")
	private String settleDate = ""; // 清算日期

	@MsgDirection(direction = "response")
	private String retReferNum = ""; // 检索参考号

	@MsgDirection(direction = "response")
	private String authorIdentResp = ""; // 授权码
	
	@MsgDirection(direction = "response")
	private String respCode = ""; // 应答码

	@MsgDirection(direction = "double")
	private String cardAcceptTermIdent = ""; // 受卡机终端标识码

	@MsgDirection(direction = "double")
	private String cardAcceptIdentcode = ""; //受卡方标识码42
	
	@MsgDirection(direction = "response")
	private String additRespData = "";//发卷机构标识44
	
	@MsgDirection(direction = "double")
	private String organId = "";//发券机构标识
	
	@MsgDirection(direction = "double")
	private String couponsAdvertId = "";//发卷对象标识 58
	
	@MsgDirection(direction = "double")
	private String reservedPrivate2 = "";//批次号60

	@MsgDirection(direction = "request")
	private String reservedPrivate3= ""; //优惠卷领用
	
	@MsgDirection(direction = "response")
	private String reservedPrivate4 = ""; // 应答信息

	@MsgDirection(direction = "double")
	private String messageAuthentCode = ""; // mac
	
	

	public String getCouponsAdvertId() {
		return couponsAdvertId;
	}

	public void setCouponsAdvertId(String couponsAdvertId) {
		this.couponsAdvertId = couponsAdvertId;
	}

	

	public String getTrack2() {
		return track2;
	}

	public void setTrack2(String track2) {
		this.track2 = track2;
	}

	public String getAdditRespData() {
		return additRespData;
	}

	public void setAdditRespData(String additRespData) {
		this.additRespData = additRespData;
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

	public String getSysTraceAuditNum() {
		return sysTraceAuditNum;
	}

	public String getReservedPrivate2() {
		return reservedPrivate2;
	}

	public void setReservedPrivate2(String reservedPrivate2) {
		this.reservedPrivate2 = reservedPrivate2;
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

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	
	
}
