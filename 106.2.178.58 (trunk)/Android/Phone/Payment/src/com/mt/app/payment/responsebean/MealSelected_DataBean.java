package com.mt.app.payment.responsebean;

import java.util.List;

import com.miteno.mpay.entity.BusiPackage;
import com.miteno.mpay.entity.MpayApp;
import com.mt.android.sys.bean.base.ResponseBean;


//套餐选择界面上所用到的数据bean
public class MealSelected_DataBean extends ResponseBean{
	private long total;
	private List<BusiPackage> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<BusiPackage> getRows() {
		return rows;
	}
	public void setRows(List<BusiPackage> rows) {
		this.rows = rows;
	}
	
}
