package com.mt.app.payment.requestbean;
/**
 *   ��Ӧ�û���Ӧ�õ��������
 * @author mzh
 *
 */
public class AppBindUnBindReqBean {
	public String app_id;// Ӧ��Id
	public String action;// ��: bind ���: unBind
	public String app_card_no; //����
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
