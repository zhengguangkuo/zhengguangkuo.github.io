package com.mt.app.payment.responsebean;

import com.mt.android.sys.bean.base.ResponseBean;

public class Card_DataBean extends ResponseBean{
	private String card_no;   //基卡的卡号
	private String card_name;    //基卡的类型
	private String card_type;    //基卡的类型
	private String bind_flag;   //绑定("1")或未绑定("0")标记   
	private String action;  //uBind 解绑  bind 绑定
    private String pic_path;
    private String input_tip ;  //提示信息

	
	public String getInput_tip() {
		return input_tip;
	}
	public void setInput_tip(String input_tip) {
		this.input_tip = input_tip;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getPic_path() {
		return pic_path;
	}
	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	
	public String getCard_name() {
		return card_name;
	}
	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}
	public String getBind_flag() {
		return bind_flag;
	}
	public void setBind_flag(String bind_flag) {
		this.bind_flag = bind_flag;
	}
	
}
