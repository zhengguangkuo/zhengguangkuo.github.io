package com.mt.app.payment.responsebean;    
import java.util.List;

import com.miteno.coupon.entity.Act;
import com.miteno.coupon.vo.ActVo;
import com.mt.android.sys.bean.base.ResponseBean;
/**
 *   ���������Ż�ȯ�б�
 * @author Administrator
 *
 */
public class DiscountDisAllResult extends ResponseBean {
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
