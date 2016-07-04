package com.mt.android.view.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mt.android.global.Globals;
import com.mt.android.view.activity.SplashActivity;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Globals.setMap("ActivityID", ActivityID.class);
		Globals.setMap("CommandID", CommandID.class);
		Globals.setMap("Initializer", Initializer.class);
		Intent intent=new Intent(MainActivity.this,SplashActivity.class);
		startActivity(intent);
	}
}
