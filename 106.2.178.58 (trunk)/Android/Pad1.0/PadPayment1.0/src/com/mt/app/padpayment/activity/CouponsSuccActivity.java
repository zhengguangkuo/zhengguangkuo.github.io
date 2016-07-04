package com.mt.app.padpayment.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
/**
 *   优惠劵申请成功界面
 * @author Administrator
 *
 */
public class CouponsSuccActivity extends DemoSmartActivity{

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("CouponsSucc.SCREEN");
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
	public Request getRequestByCommandName(String commandIDName) {
		Request request = new Request();
		return request;
	}
	

}
