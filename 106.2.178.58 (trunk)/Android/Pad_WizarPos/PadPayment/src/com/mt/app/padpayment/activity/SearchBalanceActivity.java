package com.mt.app.padpayment.activity;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.widget.TextView;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;


/**
 * 余额查询最终金额显示Activity
 * @author lzw
 *
 */
public class SearchBalanceActivity extends DemoSmartActivity{
	private TextView amount;
	private static Logger log = Logger.getLogger(SearchBalanceActivity.class);

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("SearchBalance.SCREEN");
		amount = (TextView) findViewById("BALANCENUM");
		
		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		String am = bundle.getString("Amount");
		double result = Double.parseDouble(am.substring(1 , am.length()))/100;
		amount.setText(String.valueOf(result)); 
	}
	@Override
	public Request getRequestByCommandName(String commandName) {
		// TODO Auto-generated method stub
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
