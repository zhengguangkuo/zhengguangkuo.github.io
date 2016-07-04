package com.mt.android.view.common;

import com.mt.android.sys.mvc.command.CommandExecutor;

public final class CommandID extends BaseCommandID {
	public CommandID() {
		registerBaseCommand();
		registerCommand();
	}

	@Override
	public void registerCommand() {
		// 登录请求
		map.put("LOGIN_DO", CommandExecutor.IDistributer());
		map.put("TEST", CommandExecutor.IDistributer());
		map.put("PAYSUCCESS", CommandExecutor.IDistributer());
		map.put("APPBINDPWD", CommandExecutor.IDistributer());
		map.put("APPPAYSUCCESS", CommandExecutor.IDistributer());
		map.put("VOUCHERS", CommandExecutor.IDistributer());
		map.put("ORIGINALDEAL", CommandExecutor.IDistributer());
		map.put("ADMINPWD", CommandExecutor.IDistributer());
		map.put("CANCELRESULT", CommandExecutor.IDistributer());
		map.put("APPSELECT", CommandExecutor.IDistributer());
		map.put("BALANCE", CommandExecutor.IDistributer());
		map.put("FLASH_OVER", CommandExecutor.IDistributer());
		map.put("TO_MAIN", CommandExecutor.IDistributer());
		map.put("TO_PAYMENT", CommandExecutor.IDistributer());
		map.put("TO_SWIP_CARD", CommandExecutor.IDistributer());
		map.put("TO_WAIT", CommandExecutor.IDistributer());
		map.put("TO_SURE_CONSUME", CommandExecutor.IDistributer());
		map.put("TO_SCORE", CommandExecutor.IDistributer());
		map.put("TO_CANCEL_RESULT", CommandExecutor.IDistributer());
		map.put("QUERY_BACK", CommandExecutor.IDistributer());
		map.put("TO_ADMINPW", CommandExecutor.IDistributer());
		map.put("ToConsumeRecord", CommandExecutor.IDistributer());
		// 提交刷卡请求
		map.put("SubmitCard", CommandExecutor.IDistributer());
		// 消费确认页面 确定消费
		map.put("SureConsume", CommandExecutor.IDistributer());
		// 积分消费
		map.put("CreditsConsumeSubmit", CommandExecutor.IDistributer());

		/*----------------19-24 start------------------------*/
		// 提交优惠券信息
		map.put("CouponsInfoSubmit", CommandExecutor.IDistributer());
		// 优惠券下发后返回
		map.put("CouponsSuccReturn", CommandExecutor.IDistributer());
		// 跳转到积分消费界面
		map.put("CreditsConsume", CommandExecutor.IDistributer());
		// 跳转到原交易凭参考号输入界面
		map.put("ToInputFlowNum", CommandExecutor.IDistributer());
		// 跳转到退货金额输入界面
		map.put("FlowNumSubmit", CommandExecutor.IDistributer());
		// 查询积分信息
		map.put("CreditsQuery", CommandExecutor.IDistributer());
		//跳转到参数配置页面
		map.put("TO_SETTING", CommandExecutor.IDistributer());
		//参数配置
		map.put("SETTING", CommandExecutor.IDistributer());
		//跳转到用户添加页面
		map.put("TO_ADDADMIN", CommandExecutor.IDistributer());
		//用户添加
		map.put("ADDADMIN", CommandExecutor.IDistributer());
		//跳转到柜员号输入界面
		map.put("TO_INPUTADMIN", CommandExecutor.IDistributer());
		//输入柜员号后到更新柜员信息页面
		map.put("SubmitUser", CommandExecutor.IDistributer());
		//修改柜员信息
		map.put("UPDATEADMIN", CommandExecutor.IDistributer());
		//跳转到柜员管理主菜单
		map.put("TO_ADMINMAIN", CommandExecutor.IDistributer());
		/*----------------19-24 end------------------------*/
		map.put("TO_SIGN_IN", CommandExecutor.IDistributer());
		map.put("TO_SIGN_OUT", CommandExecutor.IDistributer());
		map.put("SEARCH_COMMAND", CommandExecutor.IDistributer());
		map.put("TO_MANAGE_MAIN", CommandExecutor.IDistributer());
		map.put("TO_MANAGE_SUM", CommandExecutor.IDistributer());
		map.put("TO_MANAGE_LOCK", CommandExecutor.IDistributer());
		map.put("TO_MANAGE_VERSION", CommandExecutor.IDistributer());
		map.put("TO_PAYMENTQUERY", CommandExecutor.IDistributer());
		map.put("TO_PAYMENTQUERYRESULT", CommandExecutor.IDistributer());
		map.put("TO_AdminSetPassWordActivity", CommandExecutor.IDistributer());
		map.put("TO_AdminResetPassWordActivity", CommandExecutor.IDistributer());

		map.put("TO_AdminSetPassWordRuActivity", CommandExecutor.IDistributer());
		map.put("TO_AdminResetPassWordRuActivity", CommandExecutor.IDistributer());
		map.put("TO_DELETE_ADMIN", CommandExecutor.IDistributer());//柜员删除按钮command
		map.put("TO_DELETE_ADMIN_LIST", CommandExecutor.IDistributer());
		
		map.put("TO_QUERYADMIN", CommandExecutor.IDistributer());
		map.put("LOCKED_OVER", CommandExecutor.IDistributer());
		map.put("TO_ANDMINSIGN_OUT", CommandExecutor.IDistributer());
		map.put("TO_ORIGINALDATE", CommandExecutor.IDistributer());
		map.put("CouponsList",CommandExecutor.IDistributer());//优惠券列表查询
		map.put("FirstLogin", CommandExecutor.IDistributer());
		map.put("ToAdminPwdCommand", CommandExecutor.IDistributer());//跳转到主管密码输入界面
		map.put("ToAdminCommand", CommandExecutor.IDistributer());// 跳转到柜员管理界面
		map.put("CLEARING",CommandExecutor.IDistributer());//结算command
		map.put("ToAppPassword",CommandExecutor.IDistributer());//应用密码输入
		map.put("TO_REPRINT_LISTTIP", CommandExecutor.IDistributer());//重新打印签购单
	}

}
