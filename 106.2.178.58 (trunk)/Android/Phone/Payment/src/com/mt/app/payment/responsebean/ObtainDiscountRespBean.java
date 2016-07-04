package com.mt.app.payment.responsebean;
import com.mt.android.sys.bean.base.ResponseBean;
/**
 * 查询已领的优惠券列表的响应对象
 * @author mzh
 *
 */
public class ObtainDiscountRespBean extends ResponseBean{
	//优惠券ID
	private String couponId;
	//图片路径
	private String pic_path;
	//活动名称
	private String act_name;
	//商家名称
	private String cname;
	//活动起始日期
	private String start_date;
	//活动终止日期
	private String end_date;
	//已发放数量
	private String issued_cnt;   //????????是什么类型的
	
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getPic_path() {
		return pic_path;
	}
	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}
	public String getAct_name() {
		return act_name;
	}
	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getIssued_cnt() {
		return issued_cnt;
	}
	public void setIssued_cnt(String issued_cnt) {
		this.issued_cnt = issued_cnt;
	}
	
}
