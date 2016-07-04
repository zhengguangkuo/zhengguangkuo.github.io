package com.mt.app.padpayment.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.frame.smart.config.DrawComponent;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.AdminReqBean;

/**
 * 
 * 
 * @Description:修改柜员菜单界面
 * 
 * @author:dw
 * 
 * @time:2013-8-6 下午1:41:47
 */
public class UpdateAdminActivity extends DemoSmartActivity {
	private static Logger log = Logger.getLogger(UpdateAdminActivity.class);

	@Override
	public List getDataListById(String id) {
		// 设置用户级别
		List<String> list = new ArrayList<String>();
		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		if (bundle == null) {
			list.add("柜员");
			list.add("主管");
		} else {
			AdminReqBean reqBean = (AdminReqBean) bundle
					.getSerializable("AdminReqBean");
			String limits = reqBean.getLimits().equals("1") ? "柜员" : "主管";
			list.add(limits);
			String noLimits = reqBean.getLimits().equals("1") ? "主管" : "柜员";
			list.add(noLimits);
		}

		return list;
	}

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("UPDATEADMIN.SCREEN");

		TextView userName = (TextView) findViewById("ID");
		TextView password = (TextView) findViewById("PASSWORD");
		TextView userId = (TextView) findViewById("USER_ID");
		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		if (bundle != null) {
			AdminReqBean reqBean = (AdminReqBean) bundle
					.getSerializable("AdminReqBean");
			if (reqBean != null) {
				userName.setText(reqBean.getUserName());
				password.setText(reqBean.getPassword());
				userId.setText(reqBean.getUserId());
			}
		}
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
		if (commandName.equals("UPDATEADMIN")) {
			
			Bundle bundle = getIntent().getBundleExtra("bundleInfo");
			if (bundle != null) {
				AdminReqBean reqBean = (AdminReqBean) bundle
						.getSerializable("AdminReqBean");
				if (!DrawComponent.spinnerChecked.equals("")) {
					String limits = DrawComponent.spinnerChecked.equals("柜员") ? "1"
							: "2";
					reqBean.setLimits(limits);
					DrawComponent.spinnerChecked = "";
				}
				Request request = new Request();
				request.setData(reqBean);
				return request;
			}
		}
		return new Request();
	}
}
