package com.mt.app.payment.requestbean;

import java.io.Serializable;

/**
 * ���ӿ�����ҵȯչʾ���������
 * 
 * @author mzh
 * 
 */
public class EleCard_business_Bean implements Serializable {
	//�Ż�ȯ�ID
	private String actId;
	// �Ż�ȯID
	private String couponId;
	// ��ҵȯ��ͼƬ
	private String picture;
	// �ۿ�����
	private String discount;
	// �ۿ��̼ҵ�����
	private String name;
	/** �̼�ID */
	private String merchId ;
	// �ۿ۵���Ч��
	private String time;
	// ��ҵȯ�������
	private String count;
	// ��ҵȯ�ѹ��ڻ���δ���ڵı�ǣ�ͼƬ��
	private int icon;
	// ����
	private String distance;
	
	
	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	
	public String getActId() {
		return actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "EleCard_business_Bean [discount=" + discount + ", name=" + name + "]";
	}

}
