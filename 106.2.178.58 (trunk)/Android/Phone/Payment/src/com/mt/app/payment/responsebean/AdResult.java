package com.mt.app.payment.responsebean;    
import java.util.List;

import com.miteno.coupon.entity.TopContent;
import com.mt.android.sys.bean.base.ResponseBean;
/**
 *   ¹ã¸æÍ¼Æ¬bean
 * @author Administrator
 *
 */
public class AdResult extends ResponseBean {
	private long total;
	private List<TopContent> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<TopContent> getRows() {
		return rows;
	}
	public void setRows(List<TopContent> rows) {
		this.rows = rows;
	}
	
}
