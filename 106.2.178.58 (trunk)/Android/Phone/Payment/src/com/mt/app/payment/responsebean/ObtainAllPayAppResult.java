package com.mt.app.payment.responsebean;    
import java.util.List;
import com.miteno.coupon.entity.CouponJournal;
import com.miteno.mpay.entity.MpayApp;
import com.miteno.mpay.entity.MpayUserApp;
import com.mt.android.sys.bean.base.ResponseBean;
/**
 *   返回所有应用列表
 * @author Administrator
 *
 */
public class ObtainAllPayAppResult extends ResponseBean {
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
