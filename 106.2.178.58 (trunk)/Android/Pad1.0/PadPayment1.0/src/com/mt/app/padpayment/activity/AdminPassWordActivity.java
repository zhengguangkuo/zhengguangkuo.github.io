package com.mt.app.padpayment.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.AdminPassReqBean;
/**
 *   主管密码输入界面
 * @author Administrator
 *
 */
public class AdminPassWordActivity extends DemoSmartActivity {
	private EditText etPass;
	private TextView error;

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView("ADMINPWD.SCREEN");

		etPass = (EditText) findViewById("appbindpwdedittext");
		error = (TextView) findViewById("PASS_ERROR");
	}

	@Override
	public Request getRequestByCommandName(String commandName) {
		Request request = new Request();
		if (commandName.equals("VOUCHERS")) {
			
			AdminPassReqBean bean = new AdminPassReqBean();

			if(etPass == null || etPass.getText().toString().equalsIgnoreCase("")){
				MsgTools.toast(AdminPassWordActivity.this, "主管密码不能为空", "l");
				return null;
			}
			
			if (etPass.getText() != null && !(etPass.getText().equals(""))) {
				bean.setPassWord(etPass.getText().toString());
			}
			
			request.setData(bean);
		}

		return request;
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		error.setText("密码输入有误，请重新输入！");
		error.setTextColor(Color.RED);
	}
}
