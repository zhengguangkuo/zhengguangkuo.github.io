package com.mt.app.payment.responsebean;    
import java.util.List;
import com.miteno.coupon.entity.CouponJournal;
import com.miteno.mpay.entity.Merchant;
import com.miteno.mpay.entity.MpayApp;
import com.miteno.mpay.entity.MpayUserApp;
import com.mt.android.sys.bean.base.ResponseBean;
/**
 *   返回附近的商家坐标
 * @author Administrator
 *
 */
public class MapSearchPointResult extends ResponseBean {
	private long total;
	private List<Merchant> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<Merchant> getRows() {
		return rows;
	}
	public void setRows(List<Merchant> rows) {
		this.rows = rows;
	}
	
}
