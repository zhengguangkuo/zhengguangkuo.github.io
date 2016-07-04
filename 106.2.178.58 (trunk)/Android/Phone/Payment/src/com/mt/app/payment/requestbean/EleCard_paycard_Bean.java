package com.mt.app.payment.requestbean;

import java.io.Serializable;
/**
 *  电子卡包支付卡展示的请求对象
 * @author mzh
 *
 */
public class EleCard_paycard_Bean implements Serializable{
	//卡包中的卡图片（只显示一半）
	private String picture;
	//应用id
	private String id;
	/** 标记是否为默认卡 */
	private boolean flag ;
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
