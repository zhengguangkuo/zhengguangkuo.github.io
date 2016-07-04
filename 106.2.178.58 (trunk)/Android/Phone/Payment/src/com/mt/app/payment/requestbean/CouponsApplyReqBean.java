package com.mt.app.payment.requestbean;

import com.mt.android.sys.mvc.common.Request;
/**
 * 

 * @Description:优惠券列表请求bean

 * @author:dw

 * @time:2013-8-2 下午1:50:43
 */
public class CouponsApplyReqBean extends Request{
	
	
	public String merchantCode;//商户编号
	
	public String page; //当前页数;	   
	public String rows;//每页的数据条数
	public String point;// 用户当前坐标
	public String orderBy;// 排序条件
	public String orderType;// 排序类型
	public String merchantDistance;// 距离范围
	public String area;// 区域
	public String businessArea;// 商圈
	public String lv1Category;// 类型
	public String Lv2Category;// 类型二级分类
	
	
}
