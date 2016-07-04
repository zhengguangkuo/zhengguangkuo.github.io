package com.mt.app.payment.requestbean;

import com.mt.android.sys.bean.base.ResponseBean;


public class MealBindUnBindReqBean extends ResponseBean{
	private String mealName;  //套餐名称
	private int bindFlag;  //绑定或解绑标记   0为绑定，1为解绑
    private String id;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMealName() {
		return mealName;
	}
	public void setMealName(String mealName) {
		this.mealName = mealName;
	}
	public int getBindFlag() {
		return bindFlag;
	}
	public void setBindFlag(int bindFlag) {
		this.bindFlag = bindFlag;
	}
	
}
