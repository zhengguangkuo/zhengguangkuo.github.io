package com.mt.app.padpayment.activity;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.widget.EditText;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.padpayment.common.MsgTools;

public class OriginalDateActivity extends DemoSmartActivity {
	private static Logger log = Logger.getLogger(OriginalDateActivity.class);
	private EditText date;
	private String originaldate;

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("ORIGINALDATE.SCREEN");
		date = (EditText) findViewById("date");
	}

	@Override
	public Request getRequestByCommandName(String commandName) {
		if (commandName.equals("ToInputFlowNum")) {
			if (!MsgTools.checkEdit(date, this, "日期不能为空")) {
				return null;
			}
			originaldate = date.getText().toString();
			Controller.session.put("date", originaldate);
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
