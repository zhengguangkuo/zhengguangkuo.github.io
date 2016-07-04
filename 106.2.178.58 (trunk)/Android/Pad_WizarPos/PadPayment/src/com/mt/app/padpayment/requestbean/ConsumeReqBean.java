package com.mt.app.padpayment.requestbean;

import java.io.Serializable;

import com.mt.android.sys.mvc.common.Request;

/**
 * 消费请求
 * 
 * @author dw
 * 
 */
public class ConsumeReqBean extends Request implements Serializable {
	private String cardNum = ""; // 卡号
	private String sum = "";// 消费金额
	private String appId = "";// 选择的应用序号
	private boolean useCoupons;// 是否使用优惠券
	private String[] couponsId;// 选择的优惠券序号
	private String couponsSum = ""; //优惠券折扣金额
	private String vipSum = ""; //会员卡折扣金额
	private String realSum = ""; //实收金额
	
	private String swapCode = "";//兑换码

	private String appPwd ;//应用密码 
	
	private String flowNum = "";//优惠券兑换交易流水号。用于消费失败冲正
	
	private String appDiscount = "" ;  //支付卡折扣率
	private String[] couponsSeType ; //已选择的优惠券的类型
	private String[] couponsSeId;  //已选择的优惠券的编号
	
	

	public String[] getCouponsSeType() {
		return couponsSeType;
	}

	public void setCouponsSeType(String[] couponsSeType) {
		this.couponsSeType = couponsSeType;
	}

	public String[] getCouponsSeId() {
		return couponsSeId;
	}

	public void setCouponsSeId(String[] couponsSeId) {
		this.couponsSeId = couponsSeId;
	}

	public String getAppDiscount() {
		return appDiscount;
	}

	public void setAppDiscount(String appDiscount) {
		this.appDiscount = appDiscount;
	}

	

	public String getAppPwd() {
		return appPwd;
	}

	public void setAppPwd(String appPwd) {
		this.appPwd = appPwd;
	}

	public String getCouponsSum() {
		return couponsSum;
	}

	public void setCouponsSum(String couponsSum) {
		this.couponsSum = couponsSum;
	}

	public String getVipSum() {
		return vipSum;
	}

	public void setVipSum(String vipSum) {
		this.vipSum = vipSum;
	}

	public String getRealSum() {
		return realSum;
	}

	public void setRealSum(String realSum) {
		this.realSum = realSum;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public boolean isUseCoupons() {
		return useCoupons;
	}

	public void setUseCoupons(boolean useCoupons) {
		this.useCoupons = useCoupons;
	}

	
	public String getSwapCode() {
		return swapCode;
	}

	public void setSwapCode(String swapCode) {
		this.swapCode = swapCode;
	}

	public String[] getCouponsId() {
		return couponsId;
	}

	public void setCouponsId(String[] couponsId) {
		this.couponsId = couponsId;
	}

	public String getFlowNum() {
		return flowNum;
	}

	public void setFlowNum(String flowNum) {
		this.flowNum = flowNum;
	}
	

}
