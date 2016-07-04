package com.mt.app.padpayment.activity;

import android.os.Bundle;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;

/**
 * 管理功能主界面
 * 
 * @author Administrator
 * 
 */
public class ManageMainActivity extends DemoSmartActivity {
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView("MANAGE_MAIN.SCREEN");
	}

	@Override
	public Request getRequestByCommandName(String commandIDName) {
		if (commandIDName != null && commandIDName.equals("TO_MANAGE_VERSION")) {
			hasWaitView();
		}
		if (commandIDName != null && commandIDName.equals("TO_SIGN_IN")) {
			hasWaitView();
		}
		return new Request();
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub

	}
}
