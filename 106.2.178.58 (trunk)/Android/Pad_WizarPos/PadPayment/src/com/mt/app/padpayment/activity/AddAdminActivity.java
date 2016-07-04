package com.mt.app.padpayment.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.frame.smart.config.DrawComponent;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.AdminReqBean;

/**
 * 
 * 
 * @Description:���ӹ�Ա�˵�����
 * 
 * @author:dw
 * 
 * @time:2013-8-6 ����1:41:47
 */
public class AddAdminActivity extends DemoSmartActivity {
	private static Logger log = Logger.getLogger(AddAdminActivity.class);
	EditText userName;

	@Override
	public List getDataListById(String id) {
		// �����û�����
		List<String> list = new ArrayList<String>();
		list.add("��Ա");
		list.add("����");

		return list;
	}

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("ADDADMIN.SCREEN");
		userName = (EditText) findViewById("USER_NAME");
		userName.setInputType(InputType.TYPE_CLASS_TEXT);
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

		AdminReqBean reqBean = new AdminReqBean();

		if (commandName.equals("ADDADMIN")) {

			EditText password = (EditText) findViewById("PASSWORD");
			EditText userId = (EditText) findViewById("USER_ID");

			if (!MsgTools.checkEdit(password, this, "�û�������Ϊ��")) {
				return null;
			}

			if (!MsgTools.checkEdit(userId, this, "���벻��Ϊ��")) {
				return null;
			}

			if (userName != null && userName.getText() != null) {
				reqBean.setUserName(userName.getText().toString());
			}
			if (password != null && password.getText() != null) {
				reqBean.setPassword(password.getText().toString());
			}
			if (userId != null && userId.getText() != null) {
				reqBean.setUserId(userId.getText().toString());
			}
			if (!DrawComponent.spinnerChecked.equals("")) {
				String limits = DrawComponent.spinnerChecked.equals("��Ա") ? "1"
						: "2";
				reqBean.setLimits(limits);
				DrawComponent.spinnerChecked = "";
			}
		}
		Request request = new Request();
		request.setData(reqBean);
		return request;

	}
}
