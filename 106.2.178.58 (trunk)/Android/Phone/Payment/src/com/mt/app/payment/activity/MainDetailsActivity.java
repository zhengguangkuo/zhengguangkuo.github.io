package com.mt.app.payment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.mt.android.R;

public class MainDetailsActivity extends Activity {
	private Button btnBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main_details);
		btnBack = (Button) findViewById(R.id.btn_details_back);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MainDetailsActivity.this.finish();
			}
		});
	}
}
