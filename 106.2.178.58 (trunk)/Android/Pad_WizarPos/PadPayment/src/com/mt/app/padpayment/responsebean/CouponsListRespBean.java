package com.mt.app.padpayment.responsebean;

import java.io.Serializable;
import java.util.Map;

import com.mt.android.sys.bean.base.ResponseBean;

/**
 * 

 * @Description:�Ż�ȯ�б���Ӧbean

 * @author:dw

 * @time:2013-8-2 ����1:53:13
 */
public class CouponsListRespBean extends ResponseBean implements Serializable{
	private String coupon_id;//�Ż�ȯ���
	private String coupon_name;//�Ż�ȯ����
	private String coupon_img_path;//�Ż�ȯͼƬ·��
	private String instId;//Ӧ����������id
	private String act_type;//�Ż�ȯ���� 0:�ۿ�ȯ  1:����ȯ
	private String coupon_discount;//�Ż�ȯ�ۿ���  ����Ǵ���ȯ�ۿ���Ϊ�� �ۿ�����С�� 0.9��ʾ9��
	private String coupon_voch_amt;//�Ż�ȯ��������  ������ۿ�ȯ��ֵΪ��
	private String issuerId;//��ȯ�����ʶ
	private ActRuleRespBean useRule;//�Ż�ȯʹ�ù���  startDate���ʼʱ��  detailʹ�ù���   end_date����ʱ�� max_pieces���ʹ������  lowest_tran_amt������ѽ��
	private String c_iss_id;//Ӧ��id
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}
	public String getCoupon_name() {
		return coupon_name;
	}
	
	public String getIssuerId() {
		return issuerId;
	}
	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}
	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}
	public String getCoupon_img_path() {
		return coupon_img_path;
	}
	public void setCoupon_img_path(String coupon_img_path) {
		this.coupon_img_path = coupon_img_path;
	}
	public String getAct_type() {
		return act_type;
	}
	public void setAct_type(String act_type) {
		this.act_type = act_type;
	}
	public String getCoupon_discount() {
		return coupon_discount;
	}
	public void setCoupon_discount(String coupon_discount) {
		this.coupon_discount = coupon_discount;
	}
	public String getCoupon_voch_amt() {
		return coupon_voch_amt;
	}
	public void setCoupon_voch_amt(String coupon_voch_amt) {
		this.coupon_voch_amt = coupon_voch_amt;
	}
	
	public ActRuleRespBean getUseRule() {
		return useRule;
	}
	public void setUseRule(ActRuleRespBean useRule) {
		this.useRule = useRule;
	}
	public String getC_iss_id() {
		return c_iss_id;
	}
	public void setC_iss_id(String c_iss_id) {
		this.c_iss_id = c_iss_id;
	}
	
	
	
}
