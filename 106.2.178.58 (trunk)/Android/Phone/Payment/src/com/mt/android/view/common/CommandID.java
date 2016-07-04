package com.mt.android.view.common;

import com.mt.android.sys.mvc.command.CommandExecutor;
import com.mt.android.sys.mvc.controller.Controller;

public final class CommandID extends BaseCommandID
{
	public CommandID(){
		registerBaseCommand();
		registerCommand();
	}
	@Override
	public void registerCommand() {
//		// 登录请求
//		map.put("LOGIN_DO",CommandExecutor.IDistributer());
//		// 设置菜单
//		map.put("SETMENU",CommandExecutor.IDistributer());
//		// 设置密码
//		map.put("SETPASSWORD",CommandExecutor.IDistributer());
//		// 设置基卡
//		map.put("SETCARD",CommandExecutor.IDistributer());
//		// 我的界面中点击了查看基卡信息按钮后要发出的command
//		map.put("CHECK_BASE_CARD",CommandExecutor.IDistributer());
//		// 绑定基卡
//		map.put("BINDCARD",CommandExecutor.IDistributer());
//		// 绑定成功信息
//		map.put("BINDINFO",CommandExecutor.IDistributer());
//		// 跳转到设置密码界面
//		map.put("TO_SETPASSWORD",CommandExecutor.IDistributer());
//		// 跳转到登录页面
//		map.put("TO_LOGIN",CommandExecutor.IDistributer());
//		// 跳转到注册页面
//		map.put("TO_REGISTER",CommandExecutor.IDistributer());
//		// 注册请求
//		map.put("REGISTER_DO",CommandExecutor.IDistributer());
//		// 跳转到设置页面
//		map.put("TO_SETTING",CommandExecutor.IDistributer());
//		// 用户绑定应用后要发出的command（用于更新当前页面）
//		map.put("SURE_APP_BIND",CommandExecutor.IDistributer());
//		// 用户解绑应用后要发出的command（用于更新界面）
//		map.put("SURE_APP_UNBIND",CommandExecutor.IDistributer());
//		// 用户选定套餐后要发出的command（用于更新页面）
//		map.put("SURE_MEAL_SELECT",CommandExecutor.IDistributer());
//		// 用户解绑套餐后要发出的command（用于更新界面）；
//		map.put("SURE_MEAL_UNBIND",CommandExecutor.IDistributer());
//		// 设置界面中的套餐选择
//		map.put("SET_MEAL",CommandExecutor.IDistributer());
//		// 设置界面中的绑定应用
//		map.put("SET_APP",CommandExecutor.IDistributer());
//		// 设置界面中的解绑应用
//		map.put("SET_UNAPP",CommandExecutor.IDistributer());
//		// 获取验证码请求
//		map.put("GET_VERIFICATION",CommandExecutor.IDistributer());
//		// 登录后获取验证码
//		map.put("LOGIN_VERIFICATION",CommandExecutor.IDistributer());
//		// 跳转到我的界面
//		map.put("TO_MYVIEW",CommandExecutor.IDistributer());
//		// 跳转未登录首页
//		map.put("TO_NOLOGININDEX",CommandExecutor.IDistributer());
//		// 跳转到登录后的首页
//		map.put("TO_LOGININDEX",CommandExecutor.IDistributer());
//		// 绑定成功后，再次请求查询应用列表
//		map.put("SECOND_REQUEST_APP",CommandExecutor.IDistributer());
//		// 登录后第一次点击设置时，需要跳到输入验证码的界面
//		map.put("CHECK_CODE_SETTING",CommandExecutor.IDistributer());
//		// 提交验证码时的command
//		map.put("SUBMIT_CHECK_CODE",CommandExecutor.IDistributer());
//		// 登录后获取验证码的请求
//		map.put("GETCHECKCODE",CommandExecutor.IDistributer());
		
		
		//以下是本应用中的commandID
		
		map.put("POINTQUERY",CommandExecutor.IDistributer());
		map.put("PWDLOST",CommandExecutor.IDistributer());
		map.put("PACKAGESELECT",CommandExecutor.IDistributer());
		map.put("SELECTPACKAGE",CommandExecutor.IDistributer());
		map.put("TABAPPMANAGE",CommandExecutor.IDistributer());
		map.put("BASECARDSET",CommandExecutor.IDistributer());
		map.put("SKIPBASECARD",CommandExecutor.IDistributer());
		map.put("AUXILIARYFOUNCTION",CommandExecutor.IDistributer());
		map.put("APPMANAGE1",CommandExecutor.IDistributer());
		map.put("APPMANAGE2",CommandExecutor.IDistributer());
		map.put("APPMANAGE3",CommandExecutor.IDistributer());
		map.put("ToEleCard_Main", CommandExecutor.IDistributer());
		map.put("HOME", CommandExecutor.IDistributer());
		map.put("TO_Discount_01_MainList", CommandExecutor.IDistributer());
		map.put("TO_Discount_02_MainDetail", CommandExecutor.IDistributer());
		map.put("TO_Discount_03_MainBusiness", CommandExecutor.IDistributer());
		map.put("TO_Discount_04_MainDetail", CommandExecutor.IDistributer());
		
		map.put("MYACCOUNT", CommandExecutor.IDistributer());
		map.put("USER_LOGIN", CommandExecutor.IDistributer());//跳转注册页面
		map.put("USER_REGISTER", CommandExecutor.IDistributer());//跳转登录页面
		map.put("USER_FORGOTPASS", CommandExecutor.IDistributer());//跳转忘记密码页面
		map.put("EleApp_Query", CommandExecutor.IDistributer());  //电子卡包中的应用查询
		map.put("UserDoLogin", CommandExecutor.IDistributer()); //登录请求
		map.put("EleApp_unbind", CommandExecutor.IDistributer()); //电子卡包中的应用解绑
		map.put("EleApp_Bind", CommandExecutor.IDistributer()); //电子卡包中的应用绑定
		map.put("EleApp_Default", CommandExecutor.IDistributer()) ;	// 设置应用为默认支付应用
		map.put("UserDoRegister", CommandExecutor.IDistributer());//注册请求
		map.put("EleDiscount_Query", CommandExecutor.IDistributer()); //电子卡包中已领优惠券的列表查询
		map.put("EleDiscount_details", CommandExecutor.IDistributer());//电子卡包中已领优惠券的详情
		map.put("HELP", CommandExecutor.IDistributer());
		map.put("ABOUT", CommandExecutor.IDistributer());		
		map.put("UserVerification",CommandExecutor.IDistributer());//注册请求验证码
		map.put("BINDCARD",CommandExecutor.IDistributer());
		map.put("UPDATECARD",CommandExecutor.IDistributer());
		map.put("POINTQUERY1",CommandExecutor.IDistributer());
		map.put("MYACCOUNTQUERY",CommandExecutor.IDistributer());
		map.put("BASECARDSTART",CommandExecutor.IDistributer());
		map.put("ElePayApp_details", CommandExecutor.IDistributer()); //电子卡包中已绑支付应用的详情
		map.put("EleApp_PayAll_Query", CommandExecutor.IDistributer()); //查询所有支付类应用的列表
		map.put("ResetMyaccount", CommandExecutor.IDistributer());
		map.put("ResetMyaccountOfPhone", CommandExecutor.IDistributer());
		map.put("BaseCardSelect", CommandExecutor.IDistributer());
		map.put("DISCOUNT_DIS", CommandExecutor.IDistributer());
		map.put("Discount_dis_details", CommandExecutor.IDistributer());
		map.put("Discount_dis_receive", CommandExecutor.IDistributer());
		map.put("DISCOUNT_BUS", CommandExecutor.IDistributer());
		map.put("Discount_bus_details", CommandExecutor.IDistributer());
		map.put("Downloadbit", CommandExecutor.IDistributer());
		map.put("ResetMyaccountSubmit", CommandExecutor.IDistributer());
		map.put("MapSearchPoint", CommandExecutor.IDistributer());
		map.put("SearchNearBy_merchDiscount_Query", CommandExecutor.IDistributer());
		map.put("SearchNearBy_merchyouhui_Query", CommandExecutor.IDistributer());
		map.put("SelectMerchList", CommandExecutor.IDistributer());
		map.put("SeeMerchant", CommandExecutor.IDistributer());
		map.put("DiscountDisSearch",CommandExecutor.IDistributer());//搜索优惠券列表
		map.put("DiscountBusSearch",CommandExecutor.IDistributer());//搜索折扣商家列表
		map.put("RegCodeGet", CommandExecutor.IDistributer());
		map.put("RegCodeVerfi", CommandExecutor.IDistributer());
		map.put("BaseCardSelectTwo",CommandExecutor.IDistributer());//基卡选择完后，跳转到基卡信息界面
		map.put("CheckUpdate",CommandExecutor.IDistributer());//检查更新
		map.put("UserDoForgetPwd",CommandExecutor.IDistributer());//忘记密码
		map.put("EleDisDelete",CommandExecutor.IDistributer());//删除优惠券
		map.put("GuideBASECARDSTART", CommandExecutor.IDistributer());
		map.put("GuidePACKAGESELECT", CommandExecutor.IDistributer());
		map.put("GuideBaseCardSelect",CommandExecutor.IDistributer());//基卡选择完后，跳转到基卡信息界面
		map.put("GuideBaseCardSelectTwo",CommandExecutor.IDistributer());//基卡选择完后，跳转到基卡信息界面
		map.put("Discount_dis_merchant_details", CommandExecutor.IDistributer());
		map.put("Goto_Discount_dis_merchant_details", CommandExecutor.IDistributer());
		map.put("ChangePhone", CommandExecutor.IDistributer()) ;
		map.put("Discount_bus_details_yacol", CommandExecutor.IDistributer()) ;
		map.put("Goto_Discount_dis_merchant_details_yacol", CommandExecutor.IDistributer()) ;
		map.put("SKIPBASECARDCOMMAND", CommandExecutor.IDistributer()) ;
		map.put("ADCOMMAND", CommandExecutor.IDistributer()) ;
		Controller.appmap = map;
	}
}
