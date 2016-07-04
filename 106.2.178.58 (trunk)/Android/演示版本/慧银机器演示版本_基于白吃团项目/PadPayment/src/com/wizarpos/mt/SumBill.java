package com.wizarpos.mt;

import java.io.Serializable;

/**
 * 

 * @Description:交易汇总打印对象

 * @author:dw

 * @time:2014-4-2 下午2:24:00
 */
public class SumBill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7143120206034757387L;
	
	private String type;//类型
	private String number = "0";//笔数
	private String amount = "0.00";//卡交易额
	private String couponsAmount = "0.00";//券金额
	private String couponsNum = "0";//券张数
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCouponsAmount() {
		return couponsAmount;
	}
	public void setCouponsAmount(String couponsAmount) {
		this.couponsAmount = couponsAmount;
	}
	public String getCouponsNum() {
		return couponsNum;
	}
	public void setCouponsNum(String couponsNum) {
		this.couponsNum = couponsNum;
	}
	
}
