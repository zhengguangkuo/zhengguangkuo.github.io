package com.mt.android.view.common;

import com.mt.android.global.Globals;
import com.mt.android.help.activity.AgentActivity;
import com.mt.android.sys.mvc.command.CommandExecutor;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.activity.GuideActivity;
import com.mt.app.payment.activity.AboutMitenoAcitvity;
import com.mt.app.payment.activity.App_Manage1Activity;
import com.mt.app.payment.activity.App_Manage2Activity;
import com.mt.app.payment.activity.AuxiliaryFunctionActivity;
import com.mt.app.payment.activity.BaseCardActivity;
import com.mt.app.payment.activity.BaseCardSetActivity;
import com.mt.app.payment.activity.BasecardSelectActivity;
import com.mt.app.payment.activity.DiscountDisMerchantDetailsActivity;
import com.mt.app.payment.activity.DiscountDisMerchantDetailsOfYacolActivity;
import com.mt.app.payment.activity.Discount_01_MainListActivity;
import com.mt.app.payment.activity.DiscountDisMerchantDetailsActivity;
import com.mt.app.payment.activity.Discount_02_MainDetailActivity;
import com.mt.app.payment.activity.Discount_03_MainBusinessActivity;
import com.mt.app.payment.activity.Discount_04_MainDetailActivity;
import com.mt.app.payment.activity.EleCard_PaymentActivity;
import com.mt.app.payment.activity.GuideBaseCardActivity;
import com.mt.app.payment.activity.GuideBaseCardSetActivity;
import com.mt.app.payment.activity.GuideBasecardSelectActivity;
import com.mt.app.payment.activity.GuidePackage_SelectActivity;
import com.mt.app.payment.activity.MitenohelpActivity;
import com.mt.app.payment.activity.MyAccountActivity;
import com.mt.app.payment.activity.Package_SelectActivity;
import com.mt.app.payment.activity.PaymentMainActivity;
import com.mt.app.payment.activity.Point_QueryActivity;
import com.mt.app.payment.activity.Pwd_LostActivity;
import com.mt.app.payment.activity.ResetMyAccountActivity;
import com.mt.app.payment.activity.ResetMyAccountOfPhoneActivity;
import com.mt.app.payment.activity.UserForgotPasswordActivity;
import com.mt.app.payment.activity.UserLoginActivity;
import com.mt.app.payment.activity.UserRegisterActivity;
import com.mt.app.payment.command.AboutCommand;
import com.mt.app.payment.command.App_ManageCommand;
import com.mt.app.payment.command.App_ManagebindCommand;
import com.mt.app.payment.command.App_ManagequeryCommand;
import com.mt.app.payment.command.App_ManageunbindCommand;
import com.mt.app.payment.command.AuxiliaryFunctionCommand;
import com.mt.app.payment.command.BaseCardSelectTwoCommand;
import com.mt.app.payment.command.BaseCard_QueryCommand;
import com.mt.app.payment.command.BaseCard_SelectCommand;
import com.mt.app.payment.command.BaseCard_SetCommand;
import com.mt.app.payment.command.Base_Card_UpdateCommand;
import com.mt.app.payment.command.BindCardCommand;
import com.mt.app.payment.command.ChangePhoneCommand;
import com.mt.app.payment.command.CheckUpdateCommand;
import com.mt.app.payment.command.DiscountBusDetailsCommand;
import com.mt.app.payment.command.DiscountBusDetailsForYacolCommand;
import com.mt.app.payment.command.DiscountBusQueryCommand;
import com.mt.app.payment.command.DiscountBusSearchCommand;
import com.mt.app.payment.command.DiscountDisDetailsCommand;
import com.mt.app.payment.command.DiscountDisMerchantDetailsCommand;
import com.mt.app.payment.command.DiscountDisMerchantDetailsCommand;
import com.mt.app.payment.command.DiscountDisQueryCommand;
import com.mt.app.payment.command.DiscountDisReceiveCommand;
import com.mt.app.payment.command.DiscountDisSearchCommand;
import com.mt.app.payment.command.DownloadbitCommand;
import com.mt.app.payment.command.EleCardAllPayAppCommand;
import com.mt.app.payment.command.EleCardAppBindCommand;
import com.mt.app.payment.command.EleCardAppDefaultCommand;
import com.mt.app.payment.command.EleCardAppQueryCommand;
import com.mt.app.payment.command.EleCardAppUnbindCommand;
import com.mt.app.payment.command.EleCardMainCommand;
import com.mt.app.payment.command.EleDisDeleteCommand;
import com.mt.app.payment.command.GotoDiscountDisMerchDetailsCommand;
import com.mt.app.payment.command.EleDisDetailsCommand;
import com.mt.app.payment.command.EleDiscountQueryCommand;
import com.mt.app.payment.command.ElePayAppDetailsCommand;
import com.mt.app.payment.command.GotoDiscountDisMerchDetailsForYacolCommand;
import com.mt.app.payment.command.GuideBaseCardSelectTwoCommand;
import com.mt.app.payment.command.GuideBaseCard_SelectCommand;
import com.mt.app.payment.command.GotoDiscountDisMerchDetailsCommand;
import com.mt.app.payment.command.GuideBaseCard_SetCommand;
import com.mt.app.payment.command.GuidePackage_SelectCommand;
import com.mt.app.payment.command.HelpCommand;
import com.mt.app.payment.command.MapSearchResultCommand;
import com.mt.app.payment.command.MyAccountCommand;
import com.mt.app.payment.command.Package_SelectCommand;
import com.mt.app.payment.command.PaymentMainActivity_id_discountbtn_Command;
import com.mt.app.payment.command.PaymentMainActivity_id_listdiscount_Command;
import com.mt.app.payment.command.PaymentMainActivity_id_menu_Command;
import com.mt.app.payment.command.PointQueryCommand;
import com.mt.app.payment.command.Point_QueryCommand;
import com.mt.app.payment.command.Pwd_LostCommand;
import com.mt.app.payment.command.QuerMyAccountCommand;
import com.mt.app.payment.command.RegCodeGetCommand;
import com.mt.app.payment.command.RegCodeVerfiCommand;
import com.mt.app.payment.command.ResetMyAccountCommand;
import com.mt.app.payment.command.ResetMyAccountOfPhoneCommand;
import com.mt.app.payment.command.ResetMyaccountSubmitCommand;
import com.mt.app.payment.command.SearchNearBy_merchDiscount_QueryCommand;
import com.mt.app.payment.command.SearchNearBy_merchyouhui_QueryCommand;
import com.mt.app.payment.command.SeeMerchantCommand;
import com.mt.app.payment.command.SelectMerchListCommand;
import com.mt.app.payment.command.SelectPackageCommand;
import com.mt.app.payment.command.SkipBaseCardCommand;
import com.mt.app.payment.command.SkipGuideBaseCardCommand;
import com.mt.app.payment.command.TO_Discount_04_MainDetail_Command;
import com.mt.app.payment.command.UserDoForgotPassCommand;
import com.mt.app.payment.command.UserDoLoginCommand;
import com.mt.app.payment.command.UserDoRegisterCommand;
import com.mt.app.payment.command.UserForgotPassCommand;
import com.mt.app.payment.command.UserLoginCommand;
import com.mt.app.payment.command.UserRegisterCommand;
import com.mt.app.payment.command.UserVerificationCommand;
import com.mt.app.payment.test.ADCommand;
import com.mt.app.payment.test.TestActivity01;

public class Initializer extends BaseInitializer {
	public Initializer() {
		ensureInitialized();
	}

	@Override
	public void ensureInitialized() {
		superEnsureInitialized();

		BaseActivityID ActivityID = (BaseActivityID) Globals.map
				.get("ActivityID");

		// ×¢²áActivity
		Controller ctrl = Controller.getInstance();
		if (ctrl != null) {
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_NOLOGININDEX"),// ÒªÌøµ½µÄactivity
					PaymentMainActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_Point_Query"),
					Point_QueryActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_Package_Select"),
					Package_SelectActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_Pwd_Lost"),
					Pwd_LostActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_BaseCard_Set"),
					BaseCardSetActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_BaseCard"),
					BaseCardActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_AuxiliaryFunction"),
					AuxiliaryFunctionActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_App_Manage1Activity"),
					App_Manage1Activity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_App_Manage2Activity"),
					App_Manage2Activity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_EleCard_DemoTab"),
					AgentActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_MYACCOUNT"),
					MyAccountActivity.class);
			ctrl.registerActivity(ActivityID.map
					.get("ACTIVITY_ID_Discount_01_MainListActivity"),
					Discount_01_MainListActivity.class);
			ctrl.registerActivity(ActivityID.map
					.get("ACTIVITY_ID_Discount_02_MainDetailActivity"),
					Discount_02_MainDetailActivity.class);
			ctrl.registerActivity(ActivityID.map
					.get("ACTIVITY_ID_Discount_03_MainBusinessActivity"),
					Discount_03_MainBusinessActivity.class);
			ctrl.registerActivity(ActivityID.map
					.get("ACTIVITY_ID_Discount_04_MainDetailActivity"),
					Discount_04_MainDetailActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_USER_LOGIN"),
					UserLoginActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_USER_REGISTER"),
					UserRegisterActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_GUIDE_BASECARD"),
					GuideBaseCardActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_USER_FORGOTPASS"),
					UserForgotPasswordActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_ELEAPP_QUERY"),
					EleCard_PaymentActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_HELP"),
					MitenohelpActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_ABOUT"),
					AboutMitenoAcitvity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_ResetMyaccount"),
					ResetMyAccountActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_BaseCardSelect"),
					BasecardSelectActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_GuideBaseCard_Set"),
					GuideBaseCardSetActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_GuidePackage_Select"),
					GuidePackage_SelectActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_GuideBaseCardSelect"),
                                               GuideBasecardSelectActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_DISCOUNT_DIS_MERCHANT_DETAILS"),
					DiscountDisMerchantDetailsActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_DISCOUNT_DIS_MERCHANT_DETAILS"),
					DiscountDisMerchantDetailsActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_Guide"),
					GuideActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_ResetMyaccount_Phone"), ResetMyAccountOfPhoneActivity.class) ;
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_DISCOUNT_DIS_MERCHANT_DETAILS_YACOL"), DiscountDisMerchantDetailsOfYacolActivity.class) ;
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_TESTACTIVITY01"), TestActivity01.class) ;			
		}

		// ×¢²áCommandÃüÁî
		CommandExecutor ce = CommandExecutor.getInstance();
		if (ce != null) {
			ce.registerCommand(CommandID.map.get("POINTQUERY"),
					Point_QueryCommand.class);
			ce.registerCommand(CommandID.map.get("PACKAGESELECT"),
					Package_SelectCommand.class);
			ce.registerCommand(CommandID.map.get("SELECTPACKAGE"),
					SelectPackageCommand.class);
			ce.registerCommand(CommandID.map.get("PWDLOST"),
					Pwd_LostCommand.class);
			ce.registerCommand(CommandID.map.get("BASECARDSET"),
					BaseCard_QueryCommand.class);
			ce.registerCommand(CommandID.map.get("SKIPBASECARD"),
					SkipBaseCardCommand.class);
			ce.registerCommand(CommandID.map.get("AUXILIARYFOUNCTION"),
					AuxiliaryFunctionCommand.class);
			ce.registerCommand(CommandID.map.get("APPMANAGE1"),
					App_ManagequeryCommand.class);
			ce.registerCommand(CommandID.map.get("APPMANAGE3"),
					App_ManagebindCommand.class);
			ce.registerCommand(CommandID.map.get("APPMANAGE2"),
					App_ManageunbindCommand.class);
			ce.registerCommand(CommandID.map.get("ToEleCard_Main"),
					EleCardMainCommand.class);
			ce.registerCommand(CommandID.map.get("MYACCOUNT"),
					MyAccountCommand.class);
			ce.registerCommand(CommandID.map.get("TO_Discount_01_MainList"),
					PaymentMainActivity_id_discountbtn_Command.class);
			ce.registerCommand(CommandID.map.get("TO_Discount_02_MainDetail"),
					PaymentMainActivity_id_listdiscount_Command.class);
			ce.registerCommand(
					CommandID.map.get("TO_Discount_03_MainBusiness"),
					PaymentMainActivity_id_menu_Command.class);
			ce.registerCommand(CommandID.map.get("TO_Discount_04_MainDetail"),
					TO_Discount_04_MainDetail_Command.class);
			ce.registerCommand(CommandID.map.get("USER_LOGIN"),
					UserLoginCommand.class);
			ce.registerCommand(CommandID.map.get("USER_REGISTER"),
					UserRegisterCommand.class);
			ce.registerCommand(CommandID.map.get("USER_FORGOTPASS"),
					UserForgotPassCommand.class);
			ce.registerCommand(CommandID.map.get("TABAPPMANAGE"),
					App_ManageCommand.class);
			ce.registerCommand(CommandID.map.get("EleApp_Query"),
					EleCardAppQueryCommand.class);
			ce.registerCommand(CommandID.map.get("UserDoLogin"),
					UserDoLoginCommand.class);
			ce.registerCommand(CommandID.map.get("EleApp_unbind"),
					EleCardAppUnbindCommand.class);
			ce.registerCommand(CommandID.map.get("EleApp_Bind"),
					EleCardAppBindCommand.class);
			ce.registerCommand(CommandID.map.get("UserDoRegister"),
					UserDoRegisterCommand.class);
			ce.registerCommand(CommandID.map.get("HELP"), HelpCommand.class);
			ce.registerCommand(CommandID.map.get("ABOUT"), AboutCommand.class);
			ce.registerCommand(CommandID.map.get("UserVerification"),
					UserVerificationCommand.class);
			ce.registerCommand(CommandID.map.get("BINDCARD"),
					BindCardCommand.class);
			ce.registerCommand(CommandID.map.get("UPDATECARD"),
					Base_Card_UpdateCommand.class);
			ce.registerCommand(CommandID.map.get("POINTQUERY1"),
					PointQueryCommand.class);
			ce.registerCommand(CommandID.map.get("MYACCOUNTQUERY"),
					QuerMyAccountCommand.class);
			ce.registerCommand(CommandID.map.get("BASECARDSTART"),
					BaseCard_SetCommand.class);
			ce.registerCommand(CommandID.map.get("EleDiscount_Query"),
					EleDiscountQueryCommand.class);
	        ce.registerCommand(CommandID.map.get("EleDiscount_details"),
					EleDisDetailsCommand.class);
	        ce.registerCommand(CommandID.map.get("ElePayApp_details"),
	        		ElePayAppDetailsCommand.class);
	        ce.registerCommand(CommandID.map.get("EleApp_PayAll_Query"),
	        		EleCardAllPayAppCommand.class);
	        ce.registerCommand(CommandID.map.get("ResetMyaccount"),
	        		ResetMyAccountCommand.class);
	        ce.registerCommand(CommandID.map.get("ResetMyaccountOfPhone"),
	        		ResetMyAccountOfPhoneCommand.class);
	        ce.registerCommand(CommandID.map.get("BaseCardSelect"),
	        		BaseCard_SelectCommand.class);
	        ce.registerCommand(CommandID.map.get("DISCOUNT_DIS"),
	        		DiscountDisQueryCommand.class);
			ce.registerCommand(CommandID.map.get("Discount_dis_details"),
					DiscountDisDetailsCommand.class);
			ce.registerCommand(CommandID.map.get("Discount_dis_receive"),
					DiscountDisReceiveCommand.class);
			ce.registerCommand(CommandID.map.get("DISCOUNT_BUS"),
					DiscountBusQueryCommand.class);
			ce.registerCommand(CommandID.map.get("Discount_bus_details"),
					DiscountBusDetailsCommand.class);
			ce.registerCommand(CommandID.map.get("Downloadbit"),
					DownloadbitCommand.class);
			ce.registerCommand(CommandID.map.get("ResetMyaccountSubmit"),
					ResetMyaccountSubmitCommand.class);
	        ce.registerCommand(CommandID.map.get("MapSearchPoint"),
	        		MapSearchResultCommand.class);
	        ce.registerCommand(CommandID.map.get("SearchNearBy_merchDiscount_Query"),
	        		SearchNearBy_merchDiscount_QueryCommand.class);
	        ce.registerCommand(CommandID.map.get("SearchNearBy_merchyouhui_Query"),
	        		SearchNearBy_merchyouhui_QueryCommand.class);
	        ce.registerCommand(CommandID.map.get("SelectMerchList"),
	        		SelectMerchListCommand.class);
	        ce.registerCommand(CommandID.map.get("SeeMerchant"),
	        		SeeMerchantCommand.class);
	        ce.registerCommand(CommandID.map.get("DiscountDisSearch"), DiscountDisSearchCommand.class);
	        ce.registerCommand(CommandID.map.get("DiscountBusSearch"), DiscountBusSearchCommand.class);
	        ce.registerCommand(CommandID.map.get("RegCodeGet"),
	        		RegCodeGetCommand.class);
	        ce.registerCommand(CommandID.map.get("RegCodeVerfi"),
	        		RegCodeVerfiCommand.class);
	        ce.registerCommand(CommandID.map.get("BaseCardSelectTwo"),
					BaseCardSelectTwoCommand.class);
	        ce.registerCommand(CommandID.map.get("CheckUpdate"),
	        		CheckUpdateCommand.class);
	        ce.registerCommand(CommandID.map.get("UserDoForgetPwd"),
	        		UserDoForgotPassCommand.class);
	        ce.registerCommand(CommandID.map.get("GuideBASECARDSTART"),
	        		GuideBaseCard_SetCommand.class);
	        ce.registerCommand(CommandID.map.get("GuidePACKAGESELECT"),
	        		 GuidePackage_SelectCommand.class);
	        ce.registerCommand(CommandID.map.get("EleDisDelete"),EleDisDeleteCommand.class);
	        ce.registerCommand(CommandID.map.get("GuideBaseCardSelectTwo"),GuideBaseCardSelectTwoCommand.class);
	        ce.registerCommand(CommandID.map.get("GuideBaseCardSelect"),
	        		GuideBaseCard_SelectCommand.class);
	        ce.registerCommand(CommandID.map.get("Discount_dis_merchant_details"), DiscountDisMerchantDetailsCommand.class);
	        ce.registerCommand(CommandID.map.get("Goto_Discount_dis_merchant_details"), GotoDiscountDisMerchDetailsCommand.class);
	        ce.registerCommand(CommandID.map.get("Discount_dis_merchant_details"), DiscountDisMerchantDetailsCommand.class);
	        ce.registerCommand(CommandID.map.get("Goto_Discount_dis_merchant_details"), GotoDiscountDisMerchDetailsCommand.class);
	        ce.registerCommand(CommandID.map.get("EleApp_Default"),EleCardAppDefaultCommand.class);
	        ce.registerCommand(CommandID.map.get("ChangePhone"), ChangePhoneCommand.class) ;
	        ce.registerCommand(CommandID.map.get("Discount_bus_details_yacol"), DiscountBusDetailsForYacolCommand.class);
	        ce.registerCommand(CommandID.map.get("Goto_Discount_dis_merchant_details_yacol"), GotoDiscountDisMerchDetailsForYacolCommand.class) ;
	        ce.registerCommand(CommandID.map.get("SKIPBASECARDCOMMAND"), SkipGuideBaseCardCommand.class) ;
	        ce.registerCommand(CommandID.map.get("ADCOMMAND"), ADCommand.class) ;
		}
	}
}
