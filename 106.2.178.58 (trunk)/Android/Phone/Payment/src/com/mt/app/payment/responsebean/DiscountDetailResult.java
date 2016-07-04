package com.mt.app.payment.responsebean;    
import java.util.List;

import com.miteno.coupon.entity.Act;
import com.miteno.coupon.vo.ActDetail;
import com.miteno.coupon.vo.ActVo;
import com.mt.android.sys.bean.base.ResponseBean;
/**
 *   ”≈ª›»ØœÍ«ÈJavaBean
 * @author Administrator
 *
 */
public class DiscountDetailResult extends ResponseBean {
	private long total;
	private List<ActDetail> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<ActDetail> getRows() {
		return rows;
	}
	public void setRows(List<ActDetail> rows) {
		this.rows = rows;
	}
	
}
