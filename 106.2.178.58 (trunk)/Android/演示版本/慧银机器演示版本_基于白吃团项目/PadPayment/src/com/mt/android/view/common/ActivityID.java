package com.mt.android.view.common;

import com.mt.android.sys.mvc.controller.Controller;

public class ActivityID extends BaseActivityID {
	public ActivityID(){
		registerBaseActivity();
		registerActivity();
	}
	@Override
	public void registerActivity() {
		map.put("ACTIVITY_ID_NOLOGININDEX", Controller.IDistributer());
		map.put("ACTIVITY_ID_Test1Activity", Controller.IDistributer());
		map.put("ACTIVITY_ID_PAYSUCCESS", Controller.IDistributer());
		map.put("ACTIVITY_ID_APPBINDPWD", Controller.IDistributer());
		map.put("ACTIVITY_ID_APPPAYSUCCESS", Controller.IDistributer());
		map.put("ACTIVITY_ID_VOUCHERS", Controller.IDistributer());
		map.put("ACTIVITY_ID_ORIGINALDEAL", Controller.IDistributer());
		map.put("ACTIVITY_ID_ADMINPWD", Controller.IDistributer());
		map.put("ACTIVITY_ID_CANCELRESULT", Controller.IDistributer());
		map.put("ACTIVITY_ID_APPSELECT", Controller.IDistributer());
		map.put("ACTIVITY_ID_BALANCE", Controller.IDistributer());
		map.put("ACTIVITY_ID_GotoMainActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_MenuMainActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_PayMentAppActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_SwipCardActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_RequestWaitActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_AfterSwipCardActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_NoteConsumeActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_SureConsumeActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_BindAppDiscountActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_CouponsInfoActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_CouponsSuccActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_CreditsMainActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_CreditsConsumeActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_InputFlowNumActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_InputBackAmountActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_CreditsQueryActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_SearchActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_SearchBalanceActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_ManageMainActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_ManageSumActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_ManageLockActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_ManageVersionActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_PaymentQueryActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_PaymentQueryResultActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_AddAdminActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_SettingActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_SubmitUserActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_UpdateAdminActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_AdminMainActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_AdminSetPassWordActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_AdminResetPassWordActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_AdminPassWordRuAllActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_QueryAdminActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_OriginalDate", Controller.IDistributer());
	    map.put("ACTIVITY_ID_AdminDeleteActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_AdminPwdActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_AppPasswordActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_toRePrintListTipActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_PrintActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_RegisterActivity", Controller.IDistributer());
	    map.put("ACTIVITY_ID_RegisterResultActivity", Controller.IDistributer());
	}
}
