package com.mt.app.padpayment.activity;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.widget.EditText;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.AdminReqBean;

/**
 * 
 * @Description:输入柜员号界面

 * @author:dw

 * @time:2013-8-6 下午2:51:38
 */
public class SubmitUserActivity extends DemoSmartActivity {
	private static Logger log = Logger.getLogger(SubmitUserActivity.class);

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("INPURTUSER.SCREEN");
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public Request getRequestByCommandName(String commandName) {
		Request  request = new Request();
		AdminReqBean reqBean = new AdminReqBean();
		if (commandName.equals("SubmitUser")) {
			EditText et = (EditText) findViewById("USER_NUMBER");
			if(!MsgTools.checkEdit(et, this, "柜员号不能为空")){
				return null;
			}
			if(et != null && !(et.getText().equals(""))){
				reqBean.setUserId(et.getText().toString());
			}
			request.setData(reqBean);
		}
		
		return request;
	}

}
