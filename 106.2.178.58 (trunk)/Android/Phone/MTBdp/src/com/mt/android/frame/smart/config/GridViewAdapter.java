package com.mt.android.frame.smart.config;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class GridViewAdapter extends BaseAdapter{
	protected Map<Integer, Map<String, Integer>> datas;  
	protected LayoutInflater infalter;
	protected Context context;
	
	public GridViewAdapter(Map<Integer, Map<String, Integer>> datas , Context context){
		if(datas == null){
			this.datas = new HashMap<Integer, Map<String, Integer>>();
		}else{
			this.datas = datas;
		}
		infalter = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public int getCount()
	{
		return datas.size();
	}
	@Override
	public Object getItem(int position)
	{
		return datas.get(position);
	}
	// 重写该方法，该方法的返回值将作为列表项的ID
	@Override
	public long getItemId(int position)
	{
		return position;
	}
	// 实现该方法，该方法返回的View将作为列表框
	@Override
	public abstract View getView(int position
			, View convertView , ViewGroup parent);
}
