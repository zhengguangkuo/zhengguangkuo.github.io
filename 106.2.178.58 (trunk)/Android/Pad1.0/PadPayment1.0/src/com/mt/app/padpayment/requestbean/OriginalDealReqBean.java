package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;

public class OriginalDealReqBean extends Request{
	// ����
	private String card_num;
	// ԭ����ƾ֤��
	private String original_num;
	// �Ż݄��ֿ۽��
	private String discount_money;
	// ��Ա���ۿ۽��
	private String vip_money;
	// ʵ�ս��
	private String real_money;
	// ���ײο���
	private String deal_num;
	
	public String getCard_num() {
		return card_num;
	}
	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}
	public String getOriginal_num() {
		return original_num;
	}
	public void setOriginal_num(String original_num) {
		this.original_num = original_num;
	}
	public String getDiscount_money() {
		return discount_money;
	}
	public void setDiscount_money(String discount_money) {
		this.discount_money = discount_money;
	}
	public String getVip_money() {
		return vip_money;
	}
	public void setVip_money(String vip_money) {
		this.vip_money = vip_money;
	}
	public String getReal_money() {
		return real_money;
	}
	public void setReal_money(String real_money) {
		this.real_money = real_money;
	}
	public String getDeal_num() {
		return deal_num;
	}
	public void setDeal_num(String deal_num) {
		this.deal_num = deal_num;
	}
	
}
