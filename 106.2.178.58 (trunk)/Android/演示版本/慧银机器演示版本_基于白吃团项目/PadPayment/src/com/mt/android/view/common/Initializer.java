package com.mt.android.view.common;

import com.mt.android.global.Globals;
import com.mt.android.sys.mvc.command.CommandExecutor;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.padpayment.activity.AdFlashActivity;
import com.mt.app.padpayment.activity.AddAdminActivity;
import com.mt.app.padpayment.activity.AdminDeleteActivity;
import com.mt.app.padpayment.activity.AdminMainActivity;
import com.mt.app.padpayment.activity.AdminPassWordActivity;
import com.mt.app.padpayment.activity.AdminPassWordRuAllActivity;
import com.mt.app.padpayment.activity.AdminPwdActivity;
import com.mt.app.padpayment.activity.AdminResetPassWordActivity;
import com.mt.app.padpayment.activity.AdminSetPassWordActivity;
import com.mt.app.padpayment.activity.AfterSwipCardActivity;
import com.mt.app.padpayment.activity.AppPasswordActivity;
import com.mt.app.padpayment.activity.AppbindPassWordActivity;
import com.mt.app.padpayment.activity.ApppaySuccessActivity;
import com.mt.app.padpayment.activity.AppselectActivity;
import com.mt.app.padpayment.activity.BalanceActivity;
import com.mt.app.padpayment.activity.BindAppDiscountActivity;
import com.mt.app.padpayment.activity.CancelResultActivity;
import com.mt.app.padpayment.activity.CouponsInfoActivity;
import com.mt.app.padpayment.activity.CouponsSuccActivity;
import com.mt.app.padpayment.activity.CreditsConsumeActivity;
import com.mt.app.padpayment.activity.CreditsMainActivity;
import com.mt.app.padpayment.activity.CreditsQueryActivity;
import com.mt.app.padpayment.activity.GotoMainActivity;
import com.mt.app.padpayment.activity.InputBackAmountActivity;
import com.mt.app.padpayment.activity.InputFlowNumActivity;
import com.mt.app.padpayment.activity.ManageLockActivity;
import com.mt.app.padpayment.activity.ManageMainActivity;
import com.mt.app.padpayment.activity.ManageSumActivity;
import com.mt.app.padpayment.activity.ManageVersionActivity;
import com.mt.app.padpayment.activity.MenuMainActivity;
import com.mt.app.padpayment.activity.NoteConsumeActivity;
import com.mt.app.padpayment.activity.OriginalDateActivity;
import com.mt.app.padpayment.activity.OriginalDealActivity;
import com.mt.app.padpayment.activity.PayMentAppActivity;
import com.mt.app.padpayment.activity.PaySuccessActivity;
import com.mt.app.padpayment.activity.PaymentQueryActivity;
import com.mt.app.padpayment.activity.PaymentQueryResultActivity;
import com.mt.app.padpayment.activity.PrintActivity;
import com.mt.app.padpayment.activity.QueryAdminActivity;
import com.mt.app.padpayment.activity.RePrintListTipActivity;
import com.mt.app.padpayment.activity.RegisterActivity;
import com.mt.app.padpayment.activity.RegisterResultActivity;
import com.mt.app.padpayment.activity.RequestWaitActivity;
import com.mt.app.padpayment.activity.SearchActivity;
import com.mt.app.padpayment.activity.SearchBalanceActivity;
import com.mt.app.padpayment.activity.SettingActivity;
import com.mt.app.padpayment.activity.SubmitUserActivity;
import com.mt.app.padpayment.activity.SureConsumeActivity;
import com.mt.app.padpayment.activity.SwipCardActivity;
import com.mt.app.padpayment.activity.Test1Activity;
import com.mt.app.padpayment.activity.UpdateAdminActivity;
import com.mt.app.padpayment.activity.VouchersActivity;
import com.mt.app.padpayment.command.AdFlashCommand;
import com.mt.app.padpayment.command.AddAdminCommand;
import com.mt.app.padpayment.command.AdminDeleteCommand;
import com.mt.app.padpayment.command.AdminDeleteListCommand;
import com.mt.app.padpayment.command.AdminPassWordCommand;
import com.mt.app.padpayment.command.AdminResetPassWordCommand;
import com.mt.app.padpayment.command.AdminResetPassWordRuCommand;
import com.mt.app.padpayment.command.AdminSetPassWordCommand;
import com.mt.app.padpayment.command.AdminSetPassWordRuCommand;
import com.mt.app.padpayment.command.AppbindPassWordCommand;
import com.mt.app.padpayment.command.ApppaySuccessCommand;
import com.mt.app.padpayment.command.AppselectCommand;
import com.mt.app.padpayment.command.BalanceCommand;
import com.mt.app.padpayment.command.CancelResultCommand;
import com.mt.app.padpayment.command.ClearingCommand;
import com.mt.app.padpayment.command.CouponsBackCommand;
import com.mt.app.padpayment.command.CouponsInfoSubmitCommand;
import com.mt.app.padpayment.command.CouponsListCommand;
import com.mt.app.padpayment.command.CreditsConsumeSubmitCommand;
import com.mt.app.padpayment.command.LockOverCommand;
import com.mt.app.padpayment.command.OriginalDealCommand;
import com.mt.app.padpayment.command.PaySuccessCommand;
import com.mt.app.padpayment.command.PaymentQueryResultCommand;
import com.mt.app.padpayment.command.QueryBackCommand;
import com.mt.app.padpayment.command.RegisterResultCommand;
import com.mt.app.padpayment.command.SettingCommand;
import com.mt.app.padpayment.command.SubmitCardCommand;
import com.mt.app.padpayment.command.SubmitUserCommand;
import com.mt.app.padpayment.command.SureConsumeCommand;
import com.mt.app.padpayment.command.TestCommand;
import com.mt.app.padpayment.command.ToAddAdminCommand;
import com.mt.app.padpayment.command.ToAdminCommand;
import com.mt.app.padpayment.command.ToAdminMainCommand;
import com.mt.app.padpayment.command.ToAdminPwCommand;
import com.mt.app.padpayment.command.ToAdminPwdCommand;
import com.mt.app.padpayment.command.ToAdminSignoutCommand;
import com.mt.app.padpayment.command.ToAppPasswordCommand;
import com.mt.app.padpayment.command.ToConsumeRecordCommand;
import com.mt.app.padpayment.command.ToCreditsConsumeCommand;
import com.mt.app.padpayment.command.ToFirstLoginCommand;
import com.mt.app.padpayment.command.ToInputBackAmountCommand;
import com.mt.app.padpayment.command.ToInputFlowNumCommand;
import com.mt.app.padpayment.command.ToMainCommand;
import com.mt.app.padpayment.command.ToManageLockCommand;
import com.mt.app.padpayment.command.ToManageMainCommand;
import com.mt.app.padpayment.command.ToManageSumCommand;
import com.mt.app.padpayment.command.ToManageVersionCommand;
import com.mt.app.padpayment.command.ToOriginalDateCommand;
import com.mt.app.padpayment.command.ToPayMentCommand;
import com.mt.app.padpayment.command.ToPaymentQueryCommand;
import com.mt.app.padpayment.command.ToPrintCommand;
import com.mt.app.padpayment.command.ToQueryAdminCommand;
import com.mt.app.padpayment.command.ToRePrintListTipCommand;
import com.mt.app.padpayment.command.ToRegisterCommand;
import com.mt.app.padpayment.command.ToScoreMainCommand;
import com.mt.app.padpayment.command.ToSettingCommand;
import com.mt.app.padpayment.command.ToSignInCommand;
import com.mt.app.padpayment.command.ToSignOutCommand;
import com.mt.app.padpayment.command.ToSureConsumeCommand;
import com.mt.app.padpayment.command.ToSwipAdminCommand;
import com.mt.app.padpayment.command.ToSwipCardCommand;
import com.mt.app.padpayment.command.ToWaitCommand;
import com.mt.app.padpayment.command.UpdateAdminCommand;
import com.mt.app.padpayment.command.VouchersCommand;

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
					ActivityID.map.get("ACTIVITY_ID_NOLOGININDEX"),
					AdFlashActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_Test1Activity"),
					Test1Activity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_PAYSUCCESS"),
					PaySuccessActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_APPBINDPWD"),
					AppbindPassWordActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_APPPAYSUCCESS"),
					ApppaySuccessActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_VOUCHERS"),
					VouchersActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_ORIGINALDEAL"),
					OriginalDealActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_ADMINPWD"),
					AdminPassWordActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_CANCELRESULT"),
					CancelResultActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_APPSELECT"),
					AppselectActivity.class);
			ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_BALANCE"),
					BalanceActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_GotoMainActivity"),
					GotoMainActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_MenuMainActivity"),
					MenuMainActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_PayMentAppActivity"),
					PayMentAppActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_SwipCardActivity"),
					SwipCardActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_RequestWaitActivity"),
					RequestWaitActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_AfterSwipCardActivity"),
					AfterSwipCardActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_NoteConsumeActivity"),
					NoteConsumeActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_SureConsumeActivity"),
					SureConsumeActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_BindAppDiscountActivity"),
					BindAppDiscountActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_CouponsInfoActivity"),
					CouponsInfoActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_CouponsSuccActivity"),
					CouponsSuccActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_CreditsMainActivity"),
					CreditsMainActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_CreditsConsumeActivity"),
					CreditsConsumeActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_InputFlowNumActivity"),
					InputFlowNumActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_InputBackAmountActivity"),
					InputBackAmountActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_CreditsQueryActivity"),
					CreditsQueryActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_SearchActivity"),
					SearchActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_SearchBalanceActivity"),
					SearchBalanceActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_ManageMainActivity"),
					ManageMainActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_ManageSumActivity"),
					ManageSumActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_ManageLockActivity"),
					ManageLockActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_ManageVersionActivity"),
					ManageVersionActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_PaymentQueryActivity"),
					PaymentQueryActivity.class);
			ctrl.registerActivity(ActivityID.map
					.get("ACTIVITY_ID_PaymentQueryResultActivity"),
					PaymentQueryResultActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_AddAdminActivity"),
					AddAdminActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_SettingActivity"),
					SettingActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_SubmitUserActivity"),
					SubmitUserActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_UpdateAdminActivity"),
					UpdateAdminActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_AdminMainActivity"),
					AdminMainActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_AdminSetPassWordActivity"),
					AdminSetPassWordActivity.class);
			ctrl.registerActivity(ActivityID.map
					.get("ACTIVITY_ID_AdminResetPassWordActivity"),
					AdminResetPassWordActivity.class);

			ctrl.registerActivity(ActivityID.map
					.get("ACTIVITY_ID_AdminPassWordRuAllActivity"),
					AdminPassWordRuAllActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_AdminDeleteActivity"),
					AdminDeleteActivity.class);

			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_QueryAdminActivity"),
					QueryAdminActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_OriginalDate"),
					OriginalDateActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_AdminPwdActivity"),
					AdminPwdActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_AppPasswordActivity"),
					AppPasswordActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_toRePrintListTipActivity"),
					RePrintListTipActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_PrintActivity"),
					PrintActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_RegisterActivity"),
					RegisterActivity.class);
			ctrl.registerActivity(
					ActivityID.map.get("ACTIVITY_ID_RegisterResultActivity"),
					RegisterResultActivity.class);
		}
		// ×¢²áCommandÃüÁî
		CommandExecutor ce = CommandExecutor.getInstance();
		if (ce != null) {
			BaseCommandID CommandID = (BaseCommandID) Globals.map
					.get("CommandID");

			ce.registerCommand(CommandID.map.get("TEST"), TestCommand.class);
			ce.registerCommand(CommandID.map.get("PAYSUCCESS"),
					PaySuccessCommand.class);
			ce.registerCommand(CommandID.map.get("APPBINDPWD"),
					AppbindPassWordCommand.class);
			ce.registerCommand(CommandID.map.get("APPPAYSUCCESS"),
					ApppaySuccessCommand.class);
			ce.registerCommand(CommandID.map.get("VOUCHERS"),
					VouchersCommand.class);
			ce.registerCommand(CommandID.map.get("ORIGINALDEAL"),
					OriginalDealCommand.class);
			ce.registerCommand(CommandID.map.get("ADMINPWD"),
					AdminPassWordCommand.class);
			ce.registerCommand(CommandID.map.get("CANCELRESULT"),
					CancelResultCommand.class);
			ce.registerCommand(CommandID.map.get("APPSELECT"),
					AppselectCommand.class);
			ce.registerCommand(CommandID.map.get("BALANCE"),
					BalanceCommand.class);
			ce.registerCommand(CommandID.map.get("FLASH_OVER"),
					AdFlashCommand.class);
			ce.registerCommand(CommandID.map.get("TO_MAIN"),
					ToMainCommand.class);
			ce.registerCommand(CommandID.map.get("TO_PAYMENT"),
					ToPayMentCommand.class);
			ce.registerCommand(CommandID.map.get("TO_SWIP_CARD"),
					ToSwipCardCommand.class);
			ce.registerCommand(CommandID.map.get("TO_WAIT"),
					ToWaitCommand.class);
			ce.registerCommand(CommandID.map.get("TO_SURE_CONSUME"),
					ToSureConsumeCommand.class);
			ce.registerCommand(CommandID.map.get("TO_SCORE"),
					ToScoreMainCommand.class);
			ce.registerCommand(CommandID.map.get("TO_CANCEL_RESULT"),
					CancelResultCommand.class);
			ce.registerCommand(CommandID.map.get("QUERY_BACK"),
					QueryBackCommand.class);
			ce.registerCommand(CommandID.map.get("TO_ADMINPW"),
					ToAdminPwCommand.class);
			ce.registerCommand(CommandID.map.get("ToConsumeRecord"),
					ToConsumeRecordCommand.class);

			ce.registerCommand(CommandID.map.get("CouponsInfoSubmit"),
					CouponsInfoSubmitCommand.class);
			ce.registerCommand(CommandID.map.get("CouponsSuccReturn"),
					CouponsBackCommand.class);
			ce.registerCommand(CommandID.map.get("CreditsConsume"),
					ToCreditsConsumeCommand.class);
			ce.registerCommand(CommandID.map.get("ToInputFlowNum"),
					ToInputFlowNumCommand.class);
			ce.registerCommand(CommandID.map.get("FlowNumSubmit"),
					ToInputBackAmountCommand.class);
			ce.registerCommand(CommandID.map.get("SubmitCard"),
					SubmitCardCommand.class);
			ce.registerCommand(CommandID.map.get("SureConsume"),
					SureConsumeCommand.class);
			ce.registerCommand(CommandID.map.get("CreditsConsumeSubmit"),
					CreditsConsumeSubmitCommand.class);
			ce.registerCommand(CommandID.map.get("TO_MANAGE_MAIN"),
					ToManageMainCommand.class);
			ce.registerCommand(CommandID.map.get("TO_MANAGE_SUM"),
					ToManageSumCommand.class);
			ce.registerCommand(CommandID.map.get("TO_MANAGE_LOCK"),
					ToManageLockCommand.class);
			ce.registerCommand(CommandID.map.get("TO_MANAGE_VERSION"),
					ToManageVersionCommand.class);
			ce.registerCommand(CommandID.map.get("TO_SIGN_IN"),
					ToSignInCommand.class);
			ce.registerCommand(CommandID.map.get("TO_SIGN_OUT"),
					ToSignOutCommand.class);
			ce.registerCommand(CommandID.map.get("TO_PAYMENTQUERY"),
					ToPaymentQueryCommand.class);
			ce.registerCommand(CommandID.map.get("TO_PAYMENTQUERYRESULT"),
					PaymentQueryResultCommand.class);

			ce.registerCommand(CommandID.map.get("TO_SETTING"),
					ToSettingCommand.class);
			ce.registerCommand(CommandID.map.get("SETTING"),
					SettingCommand.class);
			ce.registerCommand(CommandID.map.get("TO_ADDADMIN"),
					ToAddAdminCommand.class);
			ce.registerCommand(CommandID.map.get("ADDADMIN"),
					AddAdminCommand.class);
			ce.registerCommand(CommandID.map.get("TO_INPUTADMIN"),
					ToSwipAdminCommand.class);
			ce.registerCommand(CommandID.map.get("SubmitUser"),
					SubmitUserCommand.class);
			ce.registerCommand(CommandID.map.get("UPDATEADMIN"),
					UpdateAdminCommand.class);
			ce.registerCommand(CommandID.map.get("TO_ADMINMAIN"),
					ToAdminMainCommand.class);
			ce.registerCommand(
					CommandID.map.get("TO_AdminSetPassWordActivity"),
					AdminSetPassWordCommand.class);
			ce.registerCommand(
					CommandID.map.get("TO_AdminResetPassWordActivity"),
					AdminResetPassWordCommand.class);

			ce.registerCommand(
					CommandID.map.get("TO_AdminSetPassWordRuActivity"),
					AdminSetPassWordRuCommand.class);
			ce.registerCommand(
					CommandID.map.get("TO_AdminResetPassWordRuActivity"),
					AdminResetPassWordRuCommand.class);
			ce.registerCommand(CommandID.map.get("TO_DELETE_ADMIN"),
					AdminDeleteCommand.class);
			ce.registerCommand(CommandID.map.get("TO_DELETE_ADMIN_LIST"),
					AdminDeleteListCommand.class);

			ce.registerCommand(CommandID.map.get("LOCKED_OVER"),
					LockOverCommand.class);

			ce.registerCommand(CommandID.map.get("TO_QUERYADMIN"),
					ToQueryAdminCommand.class);
			ce.registerCommand(CommandID.map.get("TO_ANDMINSIGN_OUT"),
					ToAdminSignoutCommand.class);
			ce.registerCommand(CommandID.map.get("TO_ORIGINALDATE"),
					ToOriginalDateCommand.class);
			ce.registerCommand(CommandID.map.get("CouponsList"),
					CouponsListCommand.class);
			
			ce.registerCommand(CommandID.map.get("FirstLogin"),
					ToFirstLoginCommand.class);
			ce.registerCommand(CommandID.map.get("ToAdminPwdCommand"),
					ToAdminPwdCommand.class);
			ce.registerCommand(CommandID.map.get("ToAdminCommand"),
					ToAdminCommand.class);
			ce.registerCommand(CommandID.map.get("CLEARING"),
					ClearingCommand.class);
			ce.registerCommand(CommandID.map.get("ToAppPassword"),
					ToAppPasswordCommand.class);
			ce.registerCommand(CommandID.map.get("TO_REPRINT_LISTTIP"),
					ToRePrintListTipCommand.class);
			ce.registerCommand(CommandID.map.get("ToPrintCommand"),
					ToPrintCommand.class);
			ce.registerCommand(CommandID.map.get("TO_REGISTER"),
					ToRegisterCommand.class);
			ce.registerCommand(CommandID.map.get("REGISTER_RESULT"),
					RegisterResultCommand.class);
		}
	}
}
