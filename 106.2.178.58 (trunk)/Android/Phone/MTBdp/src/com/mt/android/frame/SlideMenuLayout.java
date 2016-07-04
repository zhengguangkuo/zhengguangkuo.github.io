package com.mt.android.frame;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.mt.android.R;
import com.mt.android.frame.entity.SlideMenuDataEntity;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SlideMenuLayout {
	/**
	 *  属性的定义
	 */
	private ArrayList<TextView> menuList;
	private Activity activity;
	private TextView textView;
	
	private List<SlideMenuDataEntity> menuEntityList;
	
	public SlideMenuLayout(Activity activity , List<SlideMenuDataEntity> menuEntityList){
		this.activity = activity;
		this.menuEntityList = menuEntityList;
		this.menuList = new ArrayList<TextView>();
	}
	
	public View getSlideMenuLinearLayout(String[] menuTextViews,int layoutWidth){
		LinearLayout menuLinearLayout = new LinearLayout(activity);
		menuLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		LinearLayout.LayoutParams menuLinearLayoutParams = 
				new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT, 
						LinearLayout.LayoutParams.WRAP_CONTENT, 
						1);
		menuLinearLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
		
		for(int i = 0 ; i < menuTextViews.length ; i++){
			TextView tvMenu = new TextView(activity);
			
			tvMenu.setTag(menuTextViews[i]);
			tvMenu.setLayoutParams(new LayoutParams(layoutWidth / 4,30)); 
			tvMenu.setPadding(30, 14, 30, 10);
			tvMenu.setText(menuTextViews[i]);
			tvMenu.setTextColor(Color.WHITE);
			tvMenu.setGravity(Gravity.CENTER_HORIZONTAL);
			tvMenu.setOnClickListener(SlideMenuOnClickListener);
			
			menuLinearLayout.addView(tvMenu, menuLinearLayoutParams);
			menuList.add(tvMenu);
		}
		
		return menuLinearLayout;
	}
	
	
	OnClickListener SlideMenuOnClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			String menuTag = v.getTag().toString();
			
			if(v.isClickable()){
				textView = (TextView)v;
				
				textView.setBackgroundResource(R.drawable.menu_bg);
				
				for(int i = 0 ; i < menuList.size() ; i++){
					if(!menuTag.equals(menuList.get(i).getTag())){
						menuList.get(i).setBackgroundDrawable(null);
					}
				}
			}
			
			slideMenuOnChange(menuTag);
		}
	};
	
	private void slideMenuOnChange(String menuTag){
		LayoutInflater inflater = activity.getLayoutInflater();
		ViewGroup llc = (ViewGroup)activity.findViewById(R.id.linearLayoutContent);
		llc.removeAllViews();
		
		try {
			if(menuEntityList != null){
				for(SlideMenuDataEntity entity : menuEntityList){
					if(menuTag.equals(entity.getMenuText())){
						int layout = entity.getLayout();
						llc.addView(inflater.inflate(layout, null));
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} 
	}
}