package com.mt.app.payment.responsebean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.miteno.coupon.entity.MpayUserCredits;
import com.mt.android.sys.bean.base.ResponseBean;

public class PointQueryResult extends ResponseBean implements Serializable{
	private long total;
	private List<MpayUserCredits> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<MpayUserCredits> getRows() {
		return rows;
	}
	public void setRows(List<MpayUserCredits> rows) {
		this.rows = rows;
	}

}
