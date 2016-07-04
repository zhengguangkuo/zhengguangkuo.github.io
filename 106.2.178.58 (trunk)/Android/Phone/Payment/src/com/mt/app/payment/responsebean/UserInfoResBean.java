package com.mt.app.payment.responsebean;

import java.util.ArrayList;

import com.miteno.mpay.entity.MpayUser;
import com.mt.android.sys.bean.base.ResponseBean;

public class UserInfoResBean extends ResponseBean{
	private long total;
	private ArrayList<MpayUser> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public ArrayList<MpayUser> getRows() {
		return rows;
	}
	public void setRows(ArrayList<MpayUser> rows) {
		this.rows = rows;
	}
	

}
