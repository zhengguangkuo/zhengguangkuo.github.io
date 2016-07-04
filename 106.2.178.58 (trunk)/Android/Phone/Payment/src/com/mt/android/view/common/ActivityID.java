package com.mt.android.view.common;

import com.mt.android.sys.mvc.controller.Controller;

public class ActivityID extends BaseActivityID {
	public ActivityID(){
		registerBaseActivity();
		registerActivity();
	}
	@Override
	public void registerActivity() {
//		map.put("ACTIVITY_ID_SETMENU", Controller.IDistributer());
//		map.put("ACTIVITY_ID_SETPASSWORD", Controller.IDistributer());
//		map.put("ACTIVITY_ID_CHECK_BASECARD", Controller.IDistributer());
//		map.put("ACTIVITY_ID_SETCARD", Controller.IDistributer());
//		map.put("ACTIVITY_ID_BINDCARD", Controller.IDistributer());
//		map.put("ACTIVITY_ID_BINDINFO", Controller.IDistributer());
//		map.put("ACTIVITY_ID_LOGIN", Controller.IDistributer());
//		map.put("ACTIVITY_ID_REGISTER", Controller.IDistributer());
//		
//		map.put("ACTIVITY_ID_NOLOGININDEX", Controller.IDistributer());
//		map.put("ACTIVITY_ID_LOGININDEX", Controller.IDistributer());
//		map.put("ACTIVITY_ID_SETTING", Controller.IDistributer());
//		map.put("ACTIVITY_ID_SETMEAL", Controller.IDistributer());
//		map.put("ACTIVITY_ID_SETAPP", Controller.IDistributer());
//		map.put("ACTIVITY_ID_SETUNAPP", Controller.IDistributer());
//		map.put("ACTIVITY_ID_MYVIEW", Controller.IDistributer());
//		map.put("ACTIVITY_ID_CHECK_CODE", Controller.IDistributer());
		
		
		//以下是本应用中的ActivityID
		map.put("ACTIVITY_ID_NOLOGININDEX", Controller.IDistributer()); 
		map.put("ACTIVITY_ID_Point_Query", Controller.IDistributer());
		map.put("ACTIVITY_ID_Package_Select", Controller.IDistributer());
		map.put("ACTIVITY_ID_Pwd_Lost", Controller.IDistributer());
		map.put("ACTIVITY_ID_App_Manage", Controller.IDistributer());
		map.put("ACTIVITY_ID_BaseCard_Set", Controller.IDistributer());
		map.put("ACTIVITY_ID_BaseCard", Controller.IDistributer());
		map.put("ACTIVITY_ID_AuxiliaryFunction", Controller.IDistributer());
		map.put("ACTIVITY_ID_PaymentMainActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_App_Manage1Activity", Controller.IDistributer());
		map.put("ACTIVITY_ID_App_Manage2Activity", Controller.IDistributer());
		map.put("ACTIVITY_ID_EleCard_DemoTab", Controller.IDistributer());
		map.put("ACTIVITY_ID_HOME", Controller.IDistributer());
		map.put("ACTIVITY_ID_Discount_01_MainListActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_Discount_02_MainDetailActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_Discount_03_MainBusinessActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_Discount_04_MainDetailActivity", Controller.IDistributer());
		map.put("ACTIVITY_ID_MYACCOUNT", Controller.IDistributer());
		map.put("ACTIVITY_ID_USER_REGISTER", Controller.IDistributer());
		map.put("ACTIVITY_ID_USER_FORGOTPASS", Controller.IDistributer());
		map.put("ACTIVITY_ID_USER_LOGIN", Controller.IDistributer());
		map.put("ACTIVITY_ID_ELEAPP_QUERY", Controller.IDistributer());
		map.put("ACTIVITY_ID_HELP", Controller.IDistributer());
		map.put("ACTIVITY_ID_ABOUT", Controller.IDistributer());
		map.put("ACTIVITY_ID_ResetMyaccount", Controller.IDistributer());
		map.put("ACTIVITY_ID_BaseCardSelect", Controller.IDistributer());
		map.put("ACTIVITY_ID_GuidePackage_Select", Controller.IDistributer());
		map.put("ACTIVITY_ID_GuideBaseCard_Set", Controller.IDistributer());
		map.put("ACTIVITY_ID_GuideBaseCardSelect", Controller.IDistributer());
		map.put("ACTIVITY_ID_DISCOUNT_DIS_MERCHANT_DETAILS", Controller.IDistributer());
		map.put("ACTIVITY_ID_Guide", Controller.IDistributer());
		map.put("ACTIVITY_ID_ResetMyaccount_Phone", Controller.IDistributer()) ;
		map.put("ACTIVITY_ID_DISCOUNT_DIS_MERCHANT_DETAILS_YACOL", Controller.IDistributer()) ;
		map.put("ACTIVITY_ID_GUIDE_BASECARD", Controller.IDistributer()) ;
		map.put("ACTIVITY_TESTACTIVITY01", Controller.IDistributer()) ;
		
	}
}
