package com.mt.app.padpayment.responsebean;

import java.io.Serializable;

import com.mt.android.sys.bean.base.ResponseBean;

public class ActRuleRespBean extends ResponseBean implements Serializable{
	//�Ż�ȯʹ�ù���       
	private String startDate;//���ʼʱ��
	private String detail;//�����
	private String end_date;//����ʱ��
	private String max_pieces;//���ʹ������
	private String lowest_tran_amt;//������ѽ��
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getMax_pieces() {
		return max_pieces;
	}
	public void setMax_pieces(String max_pieces) {
		this.max_pieces = max_pieces;
	}
	public String getLowest_tran_amt() {
		return lowest_tran_amt;
	}
	public void setLowest_tran_amt(String lowest_tran_amt) {
		this.lowest_tran_amt = lowest_tran_amt;
	}
	
}
