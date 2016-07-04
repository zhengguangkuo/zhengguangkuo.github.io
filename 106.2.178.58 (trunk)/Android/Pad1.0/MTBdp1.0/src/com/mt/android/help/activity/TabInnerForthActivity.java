package com.mt.android.help.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.mt.android.R;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Response;

public class TabInnerForthActivity extends BaseActivity {
	private TextView text;
	
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		super.onCreateContent(savedInstanceState);
		
		setContentView(R.layout.help_tab_inner_activity_layout);
		text = (TextView)findViewById(R.id.text);
		text.setText("tab界面中第四个activity！");
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
