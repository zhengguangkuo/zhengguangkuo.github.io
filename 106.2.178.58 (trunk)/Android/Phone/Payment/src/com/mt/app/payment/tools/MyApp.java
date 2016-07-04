package com.mt.app.payment.tools;

import android.app.Activity;
import android.os.Bundle;

public class MyApp extends Activity {

	private boolean isDownload;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		isDownload = false;
	}

	public boolean isDownload() {
		return isDownload;
	}

	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}
	
	
}
