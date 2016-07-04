package com.mt.app.padpayment.common;

public class Constants {
	public static final int USR_COUPONS = 1;//优惠券列表下载
	public static final int USR_COUPONS_APPLY = 2;//优惠券申领查询
	public static final int USR_PAY_APP = 3;//支付应用列表查询
	public static final int USR_COUPONS_APP = 4;//优惠券应用列表查询
	public static final int USR_CREDITS = 5;//积分列表查询
	public static final int USR_REGISTER = 6;//注册
 	
 	
	public static final String JSONTYPE_OBJECT="object";//json传回为object类型
 	public static final String JSONTYPE_ARRAY="array";//json传回为array类型
 	
    public static final int MESSID_USR_COUPONS = 1;//优惠券列表下载
    public static final int MESSID_USR_COUPONS_APPLY = 2;//优惠券申领
    public static final int MESSID_USR_PAY_APP = 3;//支付应用列表查询
	public static final int MESSID_USR_COUPONS_APP = 4;//优惠券应用列表查询
	public static final int MESSID_USR_CREDITS = 5;//积分列表查询
	public static final int MESSID_USR_REGISTER = 6;//注册
 		
    public static final String URL_USR_COUPONS = "coupon/beforePay/list";//优惠券列表下载
    public static final String URL_USR_COUPONS_APPLY = "coupon/afterPay/list";//优惠券申领
    public static final String URL_USR_PAY_APP = "coupon/userMpayAppList/list";//支付应用列表查询
	public static final String URL_USR_COUPONS_APP = "coupon/couponAppList/list";//优惠券应用列表查询
	public static final String URL_USR_CREDITS = "coupon/userCredits";//积分列表查询
	public static final String URL_USR_REGISTER = "postNewUser";//注册
    /*public static final String URL_USR_COUPONS = "Busi";//优惠券列表下载
    public static final String URL_USR_COUPONS_APPLY = "Card";//优惠券申领
    public static final String URL_USR_PAY_APP = "Mpay";//支付应用列表查询
	public static final String URL_USR_COUPONS_APP = "Regi";//优惠券应用列表查询
 	*/
 	
}
