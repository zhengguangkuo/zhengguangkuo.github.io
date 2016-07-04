package com.mt.android.help.activity;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.mt.android.R;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.BaseCommandID;
/**
 * �����������˵����棬����һЩ�˵�
 * @author SKS
 *
 */
public class MainMenuActivity extends BaseActivity {
	private static Logger log = Logger.getLogger(MainMenuActivity.class);
	/*��������listView��Activity�İ�ť*/
	private Button  btn_busi_coupon;
	/*����󶥲�����tab��Activity�İ�ť*/
	private Button  btn_tab_top;
	/*�����ײ�����tab��Activity�İ�ť*/
	private Button  btn_tab_bottom;
	/*�������  ���˵����һ���Ч������*/
	private Button  btn_slide;
	/*��������Layout���õ�Ч���İ�ť*/
	private Button  btn_layout_reuse;
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView(R.layout.help_mainmenu);
		log.debug("MainMenuActivity onCreate");
		
		btn_busi_coupon = (Button) findViewById(R.id.btn_listview);
		
		btn_tab_top = (Button) findViewById(R.id.btn_tab_top);
		
		btn_tab_bottom = (Button)findViewById(R.id.btn_tab_bottom);
		
		btn_slide = (Button)findViewById(R.id.btn_slide);
		
		btn_layout_reuse = (Button)findViewById(R.id.btn_reuseLayout);
		
		log.debug("MainMenuActivity onEnd");
		setListener();
		
	}

	@Override
	public void onSuccess(Response response)
	{
		
	}
	
	@Override
	public void onError(Response response)
	{
		
	}
	private void setListener() {
		btn_busi_coupon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Toast.makeText(MainMenuActivity.this, "����ȯ��������", Toast.LENGTH_LONG).show();
				Request request = new Request();
				go(BaseCommandID.map.get("LISTVIEW_SHOW"), request, false);
			}
		});
		btn_tab_top.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Request request = new Request();
				go(BaseCommandID.map.get("TAB_TOP_SHOW"), request, false);
			}
		});
		btn_tab_bottom.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Request request = new Request();
				go(BaseCommandID.map.get("TAB_BOTTOM_SHOW"), request, false);
			}
		});
		btn_slide.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Request request = new Request();
				go(BaseCommandID.map.get("VIEW_SLIDE"), request, false);
				
				/*Intent intent = new Intent(MainMenuActivity.this ,
						SimpleViewPagerSlideMenuActivity.class);
				startActivity(intent);*/
			}
		});
		btn_layout_reuse.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Request request = new Request();
				go(BaseCommandID.map.get("SETMENU"), request, false);
			}
			
		});
	}
}
