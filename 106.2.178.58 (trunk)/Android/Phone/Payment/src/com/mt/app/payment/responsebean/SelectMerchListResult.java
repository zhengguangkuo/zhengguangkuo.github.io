package com.mt.app.payment.responsebean;    
import java.util.List;

import com.miteno.coupon.entity.Act;
import com.miteno.mpay.entity.MerchMpayDiscount;
import com.miteno.mpay.entity.UserCareMerch;
import com.mt.android.sys.bean.base.ResponseBean;
/**
 *   返回所有折扣商家列表
 * @author Administrator
 *
 */
public class SelectMerchListResult extends ResponseBean {
	private long total;
	private List<UserCareMerch> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<UserCareMerch> getRows() {
		return rows;
	}
	public void setRows(List<UserCareMerch> rows) {
		this.rows = rows;
	}
	
}
