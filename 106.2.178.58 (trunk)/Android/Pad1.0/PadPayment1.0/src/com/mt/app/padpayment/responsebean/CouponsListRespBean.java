package com.mt.app.padpayment.responsebean;

import java.io.Serializable;
import java.util.Map;

import com.mt.android.sys.bean.base.ResponseBean;

/**
 * 

 * @Description:优惠券列表响应bean

 * @author:dw

 * @time:2013-8-2 下午1:53:13
 */
public class CouponsListRespBean extends ResponseBean implements Serializable{
	private String coupon_id;//优惠券编号
	private String coupon_name;//优惠券名称
	private String coupon_img_path;//优惠券图片路径
	private String instId;//应用所属机构id
	private String act_type;//优惠券类型 0:折扣券  1:代金券
	private String coupon_discount;//优惠券折扣率  如果是带金券折扣率为空 折扣率是小数 0.9表示9折
	private String coupon_voch_amt;//优惠券带代金额度  如果是折扣券该值为空
	private String issuerId;//发券对象标识
	private ActRuleRespBean useRule;//优惠券使用规则  startDate活动开始时间  detail使用规则   end_date结束时间 max_pieces最多使用张数  lowest_tran_amt最低消费金额
	private String c_iss_id;//应用id
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
