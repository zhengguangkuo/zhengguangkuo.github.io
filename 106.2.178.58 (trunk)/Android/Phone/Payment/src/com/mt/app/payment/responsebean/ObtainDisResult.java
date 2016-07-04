package com.mt.app.payment.responsebean;    
import java.util.List;
import com.miteno.coupon.entity.CouponJournal;
import com.miteno.coupon.vo.ActVo;
import com.mt.android.sys.bean.base.ResponseBean;
/**
 *   返回分页查询已领取的优惠券
 * @author Administrator
 *
 */
public class ObtainDisResult extends ResponseBean {
	private long total;
	private List<ActVo> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<ActVo> getRows() {
		return rows;
	}
	public void setRows(List<ActVo> rows) {
		this.rows = rows;
	}
	
}
