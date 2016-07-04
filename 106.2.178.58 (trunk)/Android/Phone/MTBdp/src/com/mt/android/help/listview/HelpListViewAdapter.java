package com.mt.android.help.listview;


import java.util.List;

import com.mt.android.R;
import com.mt.android.frame.listview.ListViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public  class HelpListViewAdapter extends ListViewAdapter{
	public HelpListViewAdapter(List<Person> datas , Context context){
		super(datas, context);
	}
	// 实现该方法，该方法返回的View将作为列表框
	@Override
	public View getView(int position
			, View convertView , ViewGroup parent){
   	
		if(convertView == null){
			convertView = infalter.inflate(R.layout.help_listview_layout, null);
		}
		
		TextView tv1_help = (TextView)convertView.findViewById(R.id.tv1_help);
		TextView tv2_help = (TextView)convertView.findViewById(R.id.tv2_help);
		Person data = (Person)datas.get(position);

		tv1_help.setText("NO" + position + ":" + data.getName());
		tv2_help.setText("," + data.getAge());
		
		return convertView;
	}
}
