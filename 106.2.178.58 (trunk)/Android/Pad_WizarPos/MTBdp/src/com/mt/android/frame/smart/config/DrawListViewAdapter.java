package com.mt.android.frame.smart.config;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mt.android.R;

public  class DrawListViewAdapter extends ListViewAdapter{
	private String[] checkstats = null;
	public static List<Integer> list=new ArrayList<Integer>();
	public DrawListViewAdapter(List datas , Context context){
		super(datas, context);
		
		if(datas == null){
			datas = new ArrayList();
		}
		checkstats = new String[datas.size()];
		
		for(int i = 0; i < checkstats.length; i++){
			checkstats[i] = "0";
		}
	}
	// 实现该方法，该方法返回的View将作为列表框
	@Override
	public View getView(final int position
			, View convertView , ViewGroup parent){
   	 
		if(convertView == null){
			convertView = infalter.inflate(R.layout.sys_frame_smart_listview_layout, null);
		}
		
		TextView tv1_help = (TextView)convertView.findViewById(R.id.data);
		final ImageView imgview = (ImageView)convertView.findViewById(R.id.check_image);
		tv1_help.setText(datas.get(position).toString());
		tv1_help.setTextSize(25);
		tv1_help.setTextColor(Color.BLACK);
		imgview.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (checkstats[position].equalsIgnoreCase("0")){
					checkstats[position] = "1";
					list.add(position);
					imgview.setImageResource(R.drawable.checkbox_icon_p_h);
				}else if(checkstats[position].equalsIgnoreCase("1")){
					list.remove(list.indexOf(position));
					checkstats[position] = "0";
					imgview.setImageResource(R.drawable.checkbox_icon_u_h);
				}
			}
		});
		return convertView;
	}
}
