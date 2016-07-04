package com.mt.app.payment.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.mt.android.R;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Response;

public class Pwd_LostActivity extends BaseActivity{
	  private TextView title;
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
	    setContentView(R.layout.pwd_lost_layout);
	    title=(TextView)findViewById(R.id.titlewelcome);
    	title.setText("Íü¼ÇÃÜÂë");
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
