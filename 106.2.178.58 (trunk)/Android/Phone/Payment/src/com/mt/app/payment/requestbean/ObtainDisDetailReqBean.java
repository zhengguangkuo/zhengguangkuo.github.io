package com.mt.app.payment.requestbean;
/**
 *  查询优惠券详情 的请求对象
 * @author mzh
 *
 */
public class ObtainDisDetailReqBean {
	//优惠券id
	private String actId;
	/** 商家ID */
	private String merchId ;
	
	
	
	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getActId() {
		return actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}


	
}
