package com.mt.app.padpayment.adapter;


import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mt.android.R;
import com.mt.android.frame.smart.config.ListViewAdapter;

public  class DrawListViewsAdapter extends ListViewAdapter{
	private String[] checkstats = null;
	public static List<Integer> list=new ArrayList<Integer>();
	public DrawListViewsAdapter(List datas , Context context){
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
			convertView = infalter.inflate(R.layout.sys_listview, null);
		}
		TextView tv1_help = (TextView)convertView.findViewById(R.id.data);
		tv1_help.setText(datas.get(position).toString());
		tv1_help.setTextSize(12);
		tv1_help.setTextColor(Color.BLACK);
		return convertView;
	}
}
