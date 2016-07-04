package com.mt.app.padpayment.activity;

import android.os.Bundle;
import android.widget.EditText;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.requestbean.PaymentQueryReqBean;

public class PaymentQueryActivity extends DemoSmartActivity {
	private EditText serialnum, referencenum, time, user;
	PaymentQueryReqBean payquery = new PaymentQueryReqBean();

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView("PAYMENTQUERY.SCREEN");
		serialnum = (EditText) findViewById("SERIALNUMBER");
		referencenum = (EditText) findViewById("REFERENCENUMBER");
		time = (EditText) findViewById("TIME");
		user = (EditText) findViewById("USERNUMBER");
	}

	@Override
	public Request getRequestByCommandName(String commandName) {
		// TODO Auto-generated method stub
		if (commandName.equals("TO_PAYMENTQUERYRESULT")) {
			payquery.setUserId(user.getText().toString());
			payquery.setSerialnum(serialnum.getText().toString());
			payquery.setReferencenum(referencenum.getText().toString());
			payquery.setTime(time.getText().toString());
			Request request = new Request();
			request.setData(payquery);
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
