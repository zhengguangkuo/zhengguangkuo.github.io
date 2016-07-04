package com.mt.app.payment.responsebean;

import java.util.List;

import com.miteno.mpay.entity.MpayApp;
import com.mt.android.sys.bean.base.ResponseBean;
/**
 *   应用列表查询的响应对象
 * @author mzh
 *
 */
public class AppBind_DataBean extends ResponseBean{
	private long total;
	private List<MpayApp> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<MpayApp> getRows() {
		return rows;
	}
	public void setRows(List<MpayApp> rows) {
		this.rows = rows;
	}
	
	
}
