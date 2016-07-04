package com.wizarpos.mt;

import java.io.Serializable;

import com.wizarpos.apidemo.util.StringUtility;

public class PurchaseBill implements Serializable {

	private static final long serialVersionUID = 1L;

	private String merchantName="";// �̻�����

	private String merchantNo="";// �̻����

	private String terminalNo="";// �ն˱��

	private String operator="";// ��Ա��

	private String baseCardNumber="";// ��������

	private String txnType="";// ��������

	private String prepayCardName="";// ֧��������

	private String prepayCardNo="";// ֧������

	private String prepayCardExpdate="";// ֧������Ч��

	private String couponNo="";// �Ż�ȯ���

	private String couponExpdate="";// �Ż�ȯ��Ч��

	private String voucherNo="";// ƾ֤��

	private String batchNo="";// ���κ�

	private String dataTime="";// ����ʱ��

	private String authNo="";// ��Ȩ��

	private String refNo="";// �ο���

	private String couponType="";// �Ż�ȯ���

	private String couponDeduce="";// �Ż�ȯ�ֿ۽��

	private String prepayCardDis="";// ֧�����ۿ���

	private String disAmount="";// �ۿ۽��

	private String actuAmount="";// ʵ��֧�����

	private String reference="";// ��ע

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
