package com.mt.app.payment.requestbean;
/**
 *  查询已绑定的支付类应用详情 的请求对象
 * @author mzh
 *
 */
public class ObtainPayAppDetailReqBean {
	//支付类应用id
	private String appId;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
}
