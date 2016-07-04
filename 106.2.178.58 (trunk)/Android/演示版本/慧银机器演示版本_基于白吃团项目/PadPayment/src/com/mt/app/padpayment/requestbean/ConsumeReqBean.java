package com.mt.app.padpayment.requestbean;

import java.io.Serializable;

import com.mt.android.sys.mvc.common.Request;

/**
 * ��������
 * 
 * @author dw
 * 
 */
public class ConsumeReqBean extends Request implements Serializable {
	private String cardNum = ""; // ����
	private String sum = "";// ���ѽ��
	private String appId = "";// ѡ���Ӧ�����
	private boolean useCoupons;// �Ƿ�ʹ���Ż�ȯ
	private String[] couponsId;// ѡ����Ż�ȯ���
	private String couponsSum = ""; //�Ż�ȯ�ۿ۽��
	private String vipSum = ""; //��Ա���ۿ۽��
	private String realSum = ""; //ʵ�ս��
	
	private String swapCode = "";//�һ���

	private String appPwd ;//Ӧ������ 
	
	private String flowNum = "";//�Ż�ȯ�һ�������ˮ�š���������ʧ�ܳ���
	
	private String appDiscount = "" ;  //֧�����ۿ���
	private String[] couponsSeType ; //��ѡ����Ż�ȯ������
	private String[] couponsSeId;  //��ѡ����Ż�ȯ�ı��
	
	

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
