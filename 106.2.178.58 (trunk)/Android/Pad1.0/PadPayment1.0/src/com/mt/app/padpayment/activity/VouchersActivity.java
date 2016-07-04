package com.mt.app.padpayment.activity;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.ReadCardReqBean;
import com.mt.app.padpayment.requestbean.VouchersReqBean;

/**
 * 输入交易凭证号界面
 * 
 * @author Administrator
 * 
 */
public class VouchersActivity extends DemoSmartActivity {
	private EditText vouchersnumber;
	private String index;

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView("VOUCHERS.SCREEN");
		vouchersnumber = (EditText) findViewById("vouchersnum");

	}

	@Override
	public Request getRequestByCommandName(String commandIDName) {

		if (commandIDName.equals("ORIGINALDEAL")) {
			if (!MsgTools.checkEdit(vouchersnumber, this, "交易凭证号不能为空")) {
				return null;
			}
			Request request = new Request();
			VouchersReqBean vouchers = new VouchersReqBean();

			if (vouchersnumber.getText() != null
					&& !(vouchersnumber.getText().equals(""))) {
				vouchers.setVouchers(vouchersnumber.getText().toString());
			}

			request.setData(vouchers);
			return request;
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
