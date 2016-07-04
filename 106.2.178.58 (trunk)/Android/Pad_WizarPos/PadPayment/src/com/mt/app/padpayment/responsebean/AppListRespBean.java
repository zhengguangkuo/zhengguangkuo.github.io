package com.mt.app.padpayment.responsebean;

import java.io.Serializable;

import com.mt.android.sys.bean.base.ResponseBean;

//应用列表Bean
public class AppListRespBean  extends ResponseBean implements Serializable{
	private String id;// 应用标识别
	private String instId;//应用所属机构id
	private String mpayAppName;// 应用名称
	private String discount;// 折扣
	private String pic_path;//图片路径
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getMpayAppName() {
		return mpayAppName;
	}
	public void setMpayAppName(String mpayAppName) {
		this.mpayAppName = mpayAppName;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getPic_path() {
		return pic_path;
	}
	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}

	
}
