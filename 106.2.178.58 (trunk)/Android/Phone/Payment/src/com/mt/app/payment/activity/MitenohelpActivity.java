package com.mt.app.payment.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.mt.android.R;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Response;

public class MitenohelpActivity extends BaseActivity{
	private TextView title;
	private Button onoff;
    @Override
    protected void onCreateContent(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreateContent(savedInstanceState);
    	setContentView(R.layout.help_layout);
    	onoff=(Button)findViewById(R.id.onoff);
    	onoff.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View arg0) {
    			// TODO Auto-generated method stub
    			finish();	
    		}
    	});
    	title=(TextView)findViewById(R.id.titlewelcome);
    	title.setText("°ïÖú");
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
