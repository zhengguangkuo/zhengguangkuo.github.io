package com.mt.android.help.activity;

import android.content.Intent;
import android.os.Bundle;

import com.mt.android.frame.DemoTabActivity;
import com.mt.android.frame.entity.FrameDataSource;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;

public class AgentActivity extends BaseActivity {

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		Class cla = FrameDataSource.tabDataClass.get("tabClass");
		if(cla != null){
			Intent intent = new Intent(this, cla);
			startActivity(intent);
			this.finish();
		}
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub

	}
}
