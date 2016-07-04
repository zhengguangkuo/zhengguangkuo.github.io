package com.mt.android.view.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.mt.android.R;
import com.mt.android.global.Globals;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.command.CommandExecutor;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.BaseActivityID;
import com.mt.app.padpayment.common.GlobalParameters;
import com.mt.app.padpayment.common.SysManager;
import com.mt.app.padpayment.service.DBService;

public class SplashActivity extends BaseActivity {
	private static Logger log = Logger.getLogger(SplashActivity.class);
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
		
		
	}

	@Override
	protected void onBeforeCreate(Bundle savedInstanceState) {
		/*Controller.session.clear();
		GlobalParameters.g_map_para.clear();
		Globals.map.clear();*/
		
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
		// request.setData(ActivityID.ACTIVITY_ID_MAIN);
		BaseActivityID ActivityID = (BaseActivityID) Globals.map
				.get("ActivityID");

		request.setData(ActivityID.map.get("ACTIVITY_ID_NOLOGININDEX"));
		go(CommandExecutor.COMMAND_ID_IDENTITY, request, false, true);

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
