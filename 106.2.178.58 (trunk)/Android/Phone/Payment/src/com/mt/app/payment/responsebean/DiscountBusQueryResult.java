package com.mt.app.payment.responsebean;    
import java.util.List;

import com.miteno.coupon.entity.Act;
import com.miteno.mpay.entity.MerchMpayDiscount;
import com.mt.android.sys.bean.base.ResponseBean;
/**
 *   返回所有折扣商家列表
 * @author Administrator
 *
 */
public class DiscountBusQueryResult extends ResponseBean {
	private long total;
	private List<MerchMpayDiscount> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<MerchMpayDiscount> getRows() {
		return rows;
	}
	public void setRows(List<MerchMpayDiscount> rows) {
		this.rows = rows;
	}
	
}
