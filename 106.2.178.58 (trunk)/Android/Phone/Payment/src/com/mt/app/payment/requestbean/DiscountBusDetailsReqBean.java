package com.mt.app.payment.requestbean;

public class DiscountBusDetailsReqBean {
	//�ۿ��̼�ID
	private String merchMpayDiscountMerchId;
	//Ӧ��ID
	private String merchMpayDiscountAppId;
	
	private String mpayUserMerchantDistance;
	public String getMpayUserMerchantDistance() {
		return mpayUserMerchantDistance;
	}
	public void setMpayUserMerchantDistance(String mpayUserMerchantDistance) {
		this.mpayUserMerchantDistance = mpayUserMerchantDistance;
	}
	public String getMerchMpayDiscountMerchId() {
		return merchMpayDiscountMerchId;
	}
	public void setMerchMpayDiscountMerchId(String merchMpayDiscountMerchId) {
		this.merchMpayDiscountMerchId = merchMpayDiscountMerchId;
	}
	public String getMerchMpayDiscountAppId() {
		return merchMpayDiscountAppId;
	}
	public void setMerchMpayDiscountAppId(String merchMpayDiscountAppId) {
		this.merchMpayDiscountAppId = merchMpayDiscountAppId;
	}
	
}
