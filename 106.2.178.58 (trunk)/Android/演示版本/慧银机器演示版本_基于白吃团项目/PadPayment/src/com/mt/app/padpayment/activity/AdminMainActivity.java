package com.mt.app.padpayment.activity;

import android.os.Bundle;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
/**
 *   管理功能主界面
 * @author Administrator
 *
 */
public class AdminMainActivity extends DemoSmartActivity{
    @Override
    protected void onCreateContent(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreateContent(savedInstanceState);
    	setContentView("ADMIN_MAIN.SCREEN");
    }
	@Override
	public Request getRequestByCommandName(String commandIDName) {
		if (commandIDName.equals("TO_INPUTADMIN")) { //如果是修改请求
			Controller.session.put("succForward", ActivityID.map.get("ACTIVITY_ID_UpdateAdminActivity"));
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
