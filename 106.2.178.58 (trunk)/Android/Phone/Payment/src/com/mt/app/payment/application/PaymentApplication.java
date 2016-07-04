package com.mt.app.payment.application;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.mt.android.global.Globals;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.activity.SplashActivity;
import com.mt.android.view.common.ActivityID;
import com.mt.android.view.common.CommandID;
import com.mt.android.view.common.Initializer;
import com.mt.app.payment.common.SystemInit;

public class PaymentApplication extends Controller /*implements  
Thread.UncaughtExceptionHandler*/{
	@Override
	public void onCreate() {
		super.onCreate();
		//Command≥ı ºªØ
		Controller.session.clear();
		Controller.session.put("AREA_CODE_LEVEL_1", "");
		Globals.setMap("ActivityID", new ActivityID());
		Globals.setMap("CommandID", new CommandID());
		Globals.setMap("Initializer", new Initializer());
		new SystemInit(this.getApplicationContext()).init();
//		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	/*public void uncaughtException(Thread thread, Throwable ex) {
		Intent intent = new Intent(this, SplashActivity.class);  
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  
        Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent   m_restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10, m_restartIntent);
        System.exit(0);
	}*/
}
