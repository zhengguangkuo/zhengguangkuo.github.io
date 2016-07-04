package com.mt.android.view.activity;

import java.util.Timer;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.mt.android.R;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.command.CommandExecutor;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;

public class SplashActivity extends BaseActivity {
	private static Logger log = Logger.getLogger(SplashActivity.class);
	private boolean isFirstIn;
	private  String SHAREDPREFERENCES_NAME = null;
	private Handler maLauncher = new Handler() {
		public void handleMessage(Message msg) {
			launchMainActivity();
		}
	};

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView(R.layout.convenient_payment_start_index);
		Controller.session.put("IsCheckUpdate", "true");
	}

	@Override
	protected void onBeforeCreate(Bundle savedInstanceState) {
		
	}

	@Override
	protected void onAfterCreate(Bundle savedInstanceState) {
		
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				maLauncher.sendEmptyMessage(100);
			}
		};

		new Timer().schedule(task, 2000);
	}

	private void launchMainActivity() {
		Request request = new Request();
		SharedPreferences preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
	    isFirstIn=preferences.getBoolean("isFirstIn", true);
		if(isFirstIn){
			request.setData("true");
		}else{
			request.setData("false");
		}
		go(CommandExecutor.COMMAND_ID_IDENTITY, request, false, true);
		finish();

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
	public void addBottom() {

	}
}
