package com.wizarpos.mt;

import java.io.Serializable;

import com.wizarpos.apidemo.util.StringUtility;

public class PurchaseBill implements Serializable {

	private static final long serialVersionUID = 1L;

	private String merchantName="";// 商户名称

	private String merchantNo="";// 商户编号

	private String terminalNo="";// 终端编号

	private String operator="";// 柜员号

	private String baseCardNumber="";// 基卡卡号

	private String txnType="";// 交易类型

	private String prepayCardName="";// 支付卡名称

	private String prepayCardNo="";// 支付卡号

	private String prepayCardExpdate="";// 支付卡有效期

	private String couponNo="";// 优惠券编号

	private String couponExpdate="";// 优惠券有效期

	private String voucherNo="";// 凭证号

	private String batchNo="";// 批次号

	private String dataTime="";// 日期时间

	private String authNo="";// 授权码

	private String refNo="";// 参考号

	private String couponType="";// 优惠券类别

	private String couponDeduce="";// 优惠券抵扣金额

	private String prepayCardDis="";// 支付卡折扣率

	private String disAmount="";// 折扣金额

	private String actuAmount="";// 实际支付金额

	private String reference="";// 备注

	public PurchaseBill() {
	}

	public boolean checkValidity() {

		if (StringUtility.isEmpty(merchantNo)
				|| StringUtility.isEmpty(terminalNo)
				|| StringUtility.isEmpty(operator)
				|| StringUtility.isEmpty(baseCardNumber)
				|| StringUtility.isEmpty(txnType)
				|| StringUtility.isEmpty(batchNo)
				|| StringUtility.isEmpty(voucherNo)
				|| StringUtility.isEmpty(dataTime)
				|| StringUtility.isEmpty(refNo)
				|| StringUtility.isEmpty(actuAmount)) {
			return true;
		} else
			return true;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getBaseCardNumber() {
		return baseCardNumber;
	}

	public void setBaseCardNumber(String baseCardNumber) {
		this.baseCardNumber = baseCardNumber;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public String getAuthNo() {
		return authNo;
	}

	public void setAuthNo(String authNo) {
		this.authNo = authNo;
	}

	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getPrepayCardName() {
		return prepayCardName;
	}

	public void setPrepayCardName(String prepayCardName) {
		this.prepayCardName = prepayCardName;
	}

	public String getPrepayCardNo() {
		return prepayCardNo;
	}

	public void setPrepayCardNo(String prepayCardNo) {
		this.prepayCardNo = prepayCardNo;
	}

	public String getPrepayCardExpdate() {
		return prepayCardExpdate;
	}

	public void setPrepayCardExpdate(String prepayCardExpdate) {
		this.prepayCardExpdate = prepayCardExpdate;
	}

	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public String getCouponExpdate() {
		return couponExpdate;
	}

	public void setCouponExpdate(String couponExpdate) {
		this.couponExpdate = couponExpdate;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getCouponDeduce() {
		return couponDeduce;
	}

	public void setCouponDeduce(String couponDeduce) {
		this.couponDeduce = couponDeduce;
	}

	public String getPrepayCardDis() {
		return prepayCardDis;
	}

	public void setPrepayCardDis(String prepayCardDis) {
		this.prepayCardDis = prepayCardDis;
	}

	public String getDisAmount() {
		return disAmount;
	}

	public void setDisAmount(String disAmount) {
		this.disAmount = disAmount;
	}

	public String getActuAmount() {
		return actuAmount;
	}

	public void setActuAmount(String actuAmount) {
		this.actuAmount = actuAmount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
