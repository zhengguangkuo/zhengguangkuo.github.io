package com.mt.app.payment.requestbean;

import java.io.Serializable;
/**
 *  ���ӿ���֧����չʾ���������
 * @author mzh
 *
 */
public class EleCard_paycard_Bean implements Serializable{
	//�����еĿ�ͼƬ��ֻ��ʾһ�룩
	private String picture;
	//Ӧ��id
	private String id;
	/** ����Ƿ�ΪĬ�Ͽ� */
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
