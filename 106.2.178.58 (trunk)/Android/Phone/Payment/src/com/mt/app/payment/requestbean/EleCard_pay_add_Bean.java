package com.mt.app.payment.requestbean;

import java.io.Serializable;
/**
 *  电子卡包绑定新卡时的请求对象
 * @author mzh
 *
 */
public class EleCard_pay_add_Bean implements Serializable{
	//卡的图片
	private String picture;
	//卡的所属银行名字
	private String name;
	//卡的类型
	private String type;
	//卡的已绑或者未绑的标记
	private String icon;
	//应用的id
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
