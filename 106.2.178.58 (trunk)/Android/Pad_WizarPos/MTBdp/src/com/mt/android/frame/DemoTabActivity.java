package com.mt.android.frame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.mt.android.R;
import com.mt.android.frame.entity.FrameDataSource;
import com.mt.android.frame.entity.TabDataBean;
import com.mt.android.global.Globals;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.sys.util.InstanceTool;

public abstract class DemoTabActivity extends TabActivity {   //该类传不同的数据直接用
	public static TabHost mTabHost;
	private List<TabDataBean> datas;
	private int[] ids;
	private Button btn_elePay_back,unbind;
	public static TextView[] tvText;
	public static TextView  tvBindUnBind;
	public static ImageView imageSee;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		new InstanceTool().get(savedInstanceState);  
		
		setContentView(R.layout.elecard_payment);
		
		imageSee = (ImageView) findViewById(R.id.see_merch_image_parent);
		
		unbind=(Button)findViewById(R.id.btn_appManage_unBind);
		tvBindUnBind = (TextView) findViewById(R.id.tv_appManage_text);
		ImageButton discount_img=(ImageButton)findViewById(R.id.discount_img);
		discount_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
		btn_elePay_back=(Button)findViewById(R.id.btn_elePay_back);
		btn_elePay_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tvText = null;
				finish();
			}
		});
		unbind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
				if (Globals.AppManage  instanceof BaseActivity){
					((BaseActivity)Globals.AppManage).TAB();
				}
			}
		});
		mTabHost = getTabHost();
		/**
		 *  根据初始化数据，动态设置菜单界面的显示
		 */
		datas = obtainDatalist();
		
		ids = obtainIds();
		
		if(ids != null){
			for(int i=0 ; i< ids.length ; i++){
				View view = this.findViewById(ids[i]);
				view.setVisibility(View.VISIBLE);
				if(view.getId() == R.id.elePay_add){
					ImageView imageAdd = (ImageView)view;
					imageAdd.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Class cla = FrameDataSource.tabDataClass.get("bindNewCard");
							if(cla != null){
								Intent intent = new Intent(DemoTabActivity.this , cla);
								startActivity(intent);
							}
						}
					});
				}
			}
		}
		
		if(datas != null){
			setDataResource(datas);
		}
	}
	public abstract int[] obtainIds();
	public abstract List<TabDataBean> obtainDatalist();
	public void seeMerch(){}
	
	protected void setDataResource(List<TabDataBean> datas){
		if(datas.size()>0){
			for(int i=0 ; i<datas.size() ; i++){
					setTabIndicator_NoIcon(datas.get(i).getTextMess() , i+1 
							, new Intent(DemoTabActivity.this , datas.get(i).getClasses()));
			}
		}
	}
	
	/**
	 *  tab上没有图标的展示方法
	 * @param text
	 * @param itemId
	 * @param intent
	 */
	protected void setTabIndicator_NoIcon(String text , int itemId , Intent intent){
		View view = LayoutInflater.from(this.mTabHost.getContext())
				.inflate(R.layout.elecard_toptab, null);	
		TextView t = (TextView)view.findViewById(R.id.tab_label);
		t.setText(text);
		if(itemId != 1){
			t.setTextColor(Color.GRAY);
		}
		if(tvText == null){
			tvText = new TextView[datas.size()];
		}
		tvText[itemId-1] = t;
		
		String strId = String.valueOf(itemId);
		
		TabHost.TabSpec tabHostSpec = this.mTabHost.newTabSpec(strId)
				.setIndicator(view).setContent(intent);
		mTabHost.addTab(tabHostSpec);
	}
	
	@Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
		        super.onSaveInstanceState(savedInstanceState);
		        new InstanceTool().set(savedInstanceState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        new InstanceTool().get(savedInstanceState);  
	}
}
