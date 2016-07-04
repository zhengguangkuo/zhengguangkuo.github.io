package com.mt.android.frame.listview;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class ListViewAdapter<T> extends BaseAdapter{
	protected List<T> datas;  
	protected LayoutInflater infalter;
	protected Context context;
	
	public ListViewAdapter(List<T> datas , Context context){
		if(datas == null){
			this.datas = new ArrayList<T>();
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
	// ��д�÷������÷����ķ���ֵ����Ϊ�б����ID
	@Override
	public long getItemId(int position)
	{
		return position;
	}
	// ʵ�ָ÷������÷������ص�View����Ϊ�б��
	@Override
	public abstract View getView(int position
			, View convertView , ViewGroup parent);
}
