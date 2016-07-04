package com.mt.app.payment.requestbean;
/**
 *   绑定应用或解绑应用的请求对象
 * @author mzh
 *
 */
public class AppBindUnBindReqBean {
	public String app_id;// 应用Id
	public String action;// 绑定: bind 解绑: unBind
	public String app_card_no; //卡号
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getApp_card_no() {
		return app_card_no;
	}
	public void setApp_card_no(String app_card_no) {
		this.app_card_no = app_card_no;
	}
}
