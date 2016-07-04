package com.mt.app.payment.view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mt.android.R;
import com.mt.android.db.DbHandle;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.payment.adapter.TextAdapter;



public class ViewMiddle02 extends LinearLayout implements ViewBaseAction {
	
	private ListView regionListView;
	private ListView plateListView;
	private ArrayList<String> groups = new ArrayList<String>();
	private LinkedList<String> childrenItem = new LinkedList<String>();
	private LinkedList<String> childrenCodeItem = new LinkedList<String>();
	private SparseArray<LinkedList<String>> children = new SparseArray<LinkedList<String>>();
	private SparseArray<LinkedList<String>> childrenCode = new SparseArray<LinkedList<String>>();
	private TextAdapter plateListViewAdapter;
	private TextAdapter earaListViewAdapter;
	private OnSelectListener mOnSelectListener;
	private int tEaraPosition = 0;
	private int tBlockPosition = 0;
	private String showString = "涓嶉檺";
	private String showCode = "涓嶉檺";
	
	private static DbHandle dbhandle = new DbHandle();

	public ViewMiddle02(Context context) {
		super(context);
		init(context);
	}

	public ViewMiddle02(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	

	public void updateShowText(String showArea, String showBlock) {
		if (showArea == null || showBlock == null) {
			return;
		}
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).equals(showArea)) {
				earaListViewAdapter.setSelectedPosition(i);
				childrenItem.clear();
				childrenCodeItem.clear();
				if (i < children.size()) {
					childrenItem.addAll(children.get(i));
				}
				if (i < childrenCode.size()) {
					childrenCodeItem.addAll(childrenCode.get(i));
				}
				tEaraPosition = i;
				break;
			}
		}
		for (int j = 0; j < childrenItem.size(); j++) {
			if (childrenItem.get(j).replace("涓嶉檺", "").equals(showBlock.trim())) {
				plateListViewAdapter.setSelectedPosition(j);
				tBlockPosition = j;
				break;
			}
		}
		setDefaultSelect();
	}
	
	
	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_region, this, true);
		regionListView = (ListView) findViewById(R.id.listView);
		plateListView = (ListView) findViewById(R.id.listView2);
		setBackgroundDrawable(getResources().getDrawable(
				R.drawable.choosearea_bg_right));

		//从数据库中获得内容，组织两级循环
		List<Map<String, String>> list1 = dbhandle.rawQuery(
						"select * from MERCHANT_BUSSINESS_TYPE where type_level =1",null);
		for(int i=0;i<list1.size();i++){
			groups.add(list1.get(i).get("TYPE_NAME"));
			LinkedList<String> tItem = new LinkedList<String>();
			LinkedList<String> tCodeItem = new LinkedList<String>();
			List<Map<String, String>> list2 = dbhandle.rawQuery(
					"select * from MERCHANT_BUSSINESS_TYPE where type_level =2 and super_type=?",new String[] {list1.get(i).get("TYPE_CODE")});
			for(int j=0;j<list2.size();j++){
				
				tItem.add(list2.get(j).get("TYPE_NAME"));
				tCodeItem.add(list2.get(j).get("TYPE_CODE"));
			}
			children.put(i, tItem);
			childrenCode.put(i, tCodeItem);
		}
		

		earaListViewAdapter = new TextAdapter(context, groups,
				R.drawable.choose_item_selected,
				R.drawable.choose_eara_item_selector);
		earaListViewAdapter.setTextSize(17);
		earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);
		regionListView.setAdapter(earaListViewAdapter);
		earaListViewAdapter
				.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(View view, int position) {
						if (position < children.size()) {
							childrenItem.clear();
							childrenCodeItem.clear();
							childrenItem.addAll(children.get(position));
							childrenCodeItem.addAll(childrenCode.get(position));
							plateListViewAdapter.notifyDataSetChanged();
						}
					}
				});
		if (tEaraPosition < children.size())
			childrenItem.addAll(children.get(tEaraPosition));
		    childrenCodeItem.addAll(childrenCode.get(tEaraPosition));
		plateListViewAdapter = new TextAdapter(context, childrenItem,
				R.drawable.choose_item_right,
				R.drawable.choose_plate_item_selector);
		plateListViewAdapter.setTextSize(15);
		plateListViewAdapter.setSelectedPositionNoNotify(tBlockPosition);
		plateListView.setAdapter(plateListViewAdapter);
		plateListViewAdapter
				.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(View view, final int position) {
						
						showString = childrenItem.get(position);
						showCode = childrenCodeItem.get(position);
						if (mOnSelectListener != null) {
							
							mOnSelectListener.getValue(showCode,showString);
						}

					}
				});
		if (tBlockPosition < childrenItem.size()){
			showString = childrenItem.get(tBlockPosition);
			showCode = childrenCodeItem.get(tBlockPosition);
		}
			
		if(showString!=null&&!showString.equals(""))
		{
		if (showString.contains("涓嶉檺")) {
			showString = showString.replace("涓嶉檺", "");
		}
		}
		setDefaultSelect();

	}
	
	//原方法

//	private void init(Context context,String contextString01,String contextString02) {
//		LayoutInflater inflater = (LayoutInflater) context
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		inflater.inflate(R.layout.view_region, this, true);
//		regionListView = (ListView) findViewById(R.id.listView);
//		plateListView = (ListView) findViewById(R.id.listView2);
//		setBackgroundDrawable(getResources().getDrawable(
//				R.drawable.choosearea_bg_left));
//
//		//
//		for(int i=0;i<10;i++){
//			groups.add(i+contextString01);
//			LinkedList<String> tItem = new LinkedList<String>();
//			for(int j=0;j<15;j++){
//				
//				tItem.add(i+contextString02);
//				
//			}
//			children.put(i, tItem);
//		}
//
//		earaListViewAdapter = new TextAdapter(context, groups,
//				R.drawable.choose_item_selected,
//				R.drawable.choose_eara_item_selector);
//		earaListViewAdapter.setTextSize(17);
//		earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);
//		regionListView.setAdapter(earaListViewAdapter);
//		earaListViewAdapter
//				.setOnItemClickListener(new TextAdapter.OnItemClickListener() {
//
//					@Override
//					public void onItemClick(View view, int position) {
//						if (position < children.size()) {
//							childrenItem.clear();
//							childrenItem.addAll(children.get(position));
//							plateListViewAdapter.notifyDataSetChanged();
//						}
//					}
//				});
//		if (tEaraPosition < children.size())
//			childrenItem.addAll(children.get(tEaraPosition));
//		plateListViewAdapter = new TextAdapter(context, childrenItem,
//				R.drawable.choose_item_right,
//				R.drawable.choose_plate_item_selector);
//		plateListViewAdapter.setTextSize(15);
//		plateListViewAdapter.setSelectedPositionNoNotify(tBlockPosition);
//		plateListView.setAdapter(plateListViewAdapter);
//		plateListViewAdapter
//				.setOnItemClickListener(new TextAdapter.OnItemClickListener() {
//
//					@Override
//					public void onItemClick(View view, final int position) {
//						
//						showString = childrenItem.get(position);
//						if (mOnSelectListener != null) {
//							
//							mOnSelectListener.getValue(showString);
//						}
//
//					}
//				});
//		if (tBlockPosition < childrenItem.size())
//			showString = childrenItem.get(tBlockPosition);
//		if (showString.contains("涓嶉檺")) {
//			showString = showString.replace("涓嶉檺", "");
//		}
//		setDefaultSelect();
//
//	}

	public void setDefaultSelect() {
		regionListView.setSelection(tEaraPosition);
		plateListView.setSelection(tBlockPosition);
	}

	public String getShowText() {
		return showString;
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String code,String showText);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
}
