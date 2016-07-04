package com.mt.android.frame.smart.config;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mt.android.R;
import com.mt.android.protocol.http.client.ImageHttpPadClient;
import com.mt.android.sys.mvc.controller.Controller;

public  class DrawGridAdapter extends ListViewAdapter<String[]>{
	private String[] textArr = null;
	private String[] imageArr = null;
	private String[] id = null;
	public  static List<String> list = new ArrayList<String>();
	private String[] checkstats = null;
	public DrawGridAdapter(List<String[]> datas , Context context){
		super(datas, context);
		
		if(datas == null){
			datas = new ArrayList<String[]>();
		}
		if(datas.size()>=1){
			textArr = (String[]) datas.get(0);
			imageArr = (String[]) datas.get(1);
			id = (String[]) datas.get(2);
			ImageThread thread = new ImageThread();
			thread.start();//使用 子线程下载应用图片 
			
			}
		checkstats = new String[imageArr.length];
		
		for(int i = 0; i < checkstats.length; i++){
			checkstats[i] = "0";
		}
		
	}
	private Handler handler = new Handler() {  //异步刷新图片
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				notifyDataSetChanged();
				break;
			}
		}
	};

	public class ImageThread extends Thread {

		public void run() {

			boolean hasNotify = false;

			for (int i = 0; i < imageArr.length; i++) {
				if (!ImageHttpPadClient.hasImage(imageArr[i])) {
					hasNotify = true;
				}
				ImageHttpPadClient.getBitmap(imageArr[i]);
			}
			if (hasNotify) {
				Message ms = Message.obtain();
				ms.what = 1;
				handler.sendMessage(ms);

			}
		}
	}
	
	@Override
	public int getCount()
	{
		return textArr.length;
	}
	@Override
	public Object getItem(int position)
	{
		return textArr[position];
	}
	// 实现该方法，该方法返回的View将作为列表框
	/* (non-Javadoc)
	 * @see com.mt.android.frame.smart.config.ListViewAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position
			, View convertView , ViewGroup parent){
   	 
		if(convertView == null){
			convertView = infalter.inflate(R.layout.sys_frame_smart_gridview_layout, null);
		}
		
		TextView tv1_help = (TextView)convertView.findViewById(R.id.gridlistdata);
		final ImageView imgview = (ImageView)convertView.findViewById(R.id.gridimage);
		final ImageView imgviewBack = (ImageView)convertView.findViewById(R.id.gridbackimage);
		tv1_help.setText(textArr[position]);
		tv1_help.setTextSize(25);
		imgview.setImageBitmap(ImageHttpPadClient.getBitmapPad(imageArr[position]));
		imgviewBack.setVisibility(View.GONE);
		imgview.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (checkstats[position].equalsIgnoreCase("0")){
					checkstats[position] = "1";
					list.add(id[position]);
					Controller.session.put("checkedList",list);
					imgviewBack.setVisibility(View.VISIBLE);
				}else{
					list.remove(id[position]);
					Controller.session.put("checkedList",list);
					checkstats[position] = "0";
					imgviewBack.setVisibility(View.GONE);
				}
			}
		});
		return convertView;
	}
}
