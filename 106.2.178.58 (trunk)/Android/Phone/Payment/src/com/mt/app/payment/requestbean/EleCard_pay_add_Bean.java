package com.mt.app.payment.requestbean;

import java.io.Serializable;
/**
 *  ���ӿ������¿�ʱ���������
 * @author mzh
 *
 */
public class EleCard_pay_add_Bean implements Serializable{
	//����ͼƬ
	private String picture;
	//����������������
	private String name;
	//��������
	private String type;
	//�����Ѱ����δ��ı��
	private String icon;
	//Ӧ�õ�id
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
