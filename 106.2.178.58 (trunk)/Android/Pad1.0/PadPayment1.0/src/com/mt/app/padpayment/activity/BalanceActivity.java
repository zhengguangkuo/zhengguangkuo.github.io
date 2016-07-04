package com.mt.app.padpayment.activity;

import android.os.Bundle;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
/**
 *   ”‡∂Óœ‘ æΩÁ√Ê
 * @author Administrator
 *
 */
public class BalanceActivity extends DemoSmartActivity{
    @Override
    protected void onCreateContent(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreateContent(savedInstanceState);
    	setContentView("BALANCE.SCREEN");
    }
	@Override
	public Request getRequestByCommandName(String commandIDName) {
		if(commandIDName.equals("TO_MAIN")){
			Request request=new Request();
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
