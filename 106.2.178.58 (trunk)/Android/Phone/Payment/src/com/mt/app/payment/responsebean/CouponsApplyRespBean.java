package com.mt.app.payment.responsebean;

import java.util.List;

import com.miteno.coupon.entity.Act;
import com.mt.android.sys.bean.base.ResponseBean;


/**
 *   ���ط�ҳ��ѯȫ���Ż�ȯ�б�
 * @author lzw
 *
 */
public class CouponsApplyRespBean extends ResponseBean {
	private long total;
	private List<Act> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<Act> getRows() {
		return rows;
	}
	public void setRows(List<Act> rows) {
		this.rows = rows;
	}
	
	
	
}
