package com.mt.app.payment.responsebean;    
import java.util.List;
import com.miteno.coupon.entity.CouponJournal;
import com.miteno.mpay.entity.MpayUserApp;
import com.mt.android.sys.bean.base.ResponseBean;
/**
 *   返回分页查询已绑定的应用
 * @author Administrator
 *
 */
public class ObtainPayAppResult extends ResponseBean {
	private long total;
	private List<MpayUserApp> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<MpayUserApp> getRows() {
		return rows;
	}
	public void setRows(List<MpayUserApp> rows) {
		this.rows = rows;
	}
	
}
