package com.mt.app.payment.responsebean;

public class SearnearByMeryouhuiBean {
	//ͼƬ
	private String pic ; 
	//�����
	private String name ; 
	//���ʼʱ��
	private String time;
	//��������
	private String count;
	//�ID
	private String actID;
	//����������ʶ
	private String couponIssuerID;
	/** �̼�ID */
	private String merchId ;
	
	
	
	public String getMerchId() {
		return merchId;
	}
	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	public String getActID() {
		return actID;
	}
	public void setActID(String actID) {
		this.actID = actID;
	}
	public String getCouponIssuerID() {
		return couponIssuerID;
	}
	public void setCouponIssuerID(String couponIssuerID) {
		this.couponIssuerID = couponIssuerID;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
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
	
}
