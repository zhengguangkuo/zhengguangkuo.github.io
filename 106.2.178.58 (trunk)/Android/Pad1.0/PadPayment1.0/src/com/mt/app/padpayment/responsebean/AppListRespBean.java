package com.mt.app.padpayment.responsebean;

import java.io.Serializable;

import com.mt.android.sys.bean.base.ResponseBean;

//Ӧ���б�Bean
public class AppListRespBean  extends ResponseBean implements Serializable{
	private String id;// Ӧ�ñ�ʶ��
	private String instId;//Ӧ����������id
	private String mpayAppName;// Ӧ������
	private String discount;// �ۿ�
	private String pic_path;//ͼƬ·��
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
