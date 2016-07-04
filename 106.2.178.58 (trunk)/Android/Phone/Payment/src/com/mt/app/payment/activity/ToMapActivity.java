package com.mt.app.payment.activity;

import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Response;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ToMapActivity extends BaseActivity{
	
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		Intent intent = new Intent(ToMapActivity.this , DisDetailMapActivity.class);
		intent.putExtra("lat", 39.905);
		intent.putExtra("lon", 116.384);
		startActivity(intent);
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
