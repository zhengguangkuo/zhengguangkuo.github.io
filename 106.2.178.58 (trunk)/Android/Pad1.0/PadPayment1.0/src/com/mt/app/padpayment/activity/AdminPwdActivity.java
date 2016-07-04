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
 * d���������������
 * 
 * @author Administrator
 * 
 */
public class AdminPwdActivity extends DemoSmartActivity {
	
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView("ADPAS.SCREEN");
	}

	@Override
	public Request getRequestByCommandName(String commandName) {
		Request request = new Request();
		AdminPassReqBean bean = new AdminPassReqBean();
		if (commandName.equals("ToAdminCommand")) {
		EditText etPass = (EditText) findViewById("adpwd");
		
		if(etPass == null || etPass.getText().toString().trim().equalsIgnoreCase("")){
			MsgTools.toast(AdminPwdActivity.this, "�������벻��Ϊ��", "l");
			return null;
		}
		
		if (etPass.getText() != null && !(etPass.getText().equals(""))) {
			bean.setPassWord(etPass.getText().toString());
		}
		}
		request.setData(bean);

		return request;
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		TextView error = (TextView) findViewById("PASSERROR");
		error.setText("���������������������룡");
		error.setTextColor(Color.RED);
	}
}
