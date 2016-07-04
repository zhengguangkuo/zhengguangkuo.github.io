package com.mt.app.padpayment.adapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mt.android.R;
import com.mt.android.frame.smart.config.ListViewAdapter;
import com.mt.android.protocol.http.client.ImageHttpPadClient;

public  class CouponsAppGridViewAdapter extends ListViewAdapter<String[]>{
	private String[] textArr = null;
	private String[] imageArr = null;
	public  static int checked = -1;
	private Map<String,Bitmap> map = new HashMap<String,Bitmap>();
	public CouponsAppGridViewAdapter(List<String[]> datas , Context context){
		super(datas, context);
		
		if(datas == null){
			datas = new ArrayList<String[]>();
		}
		if(datas.size()>=1){
			textArr = (String[]) datas.get(0);
			imageArr = (String[]) datas.get(1);
			new ImageThread(imageArr).start();
		}
	}
	
	private Handler handler = new Handler() { // 异步刷新图片
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				notifyDataSetChanged();
				break;
			}
		}
	};
	public class ImageThread extends Thread{
		   String[] images = null;
	       public  ImageThread(String[] images){
	    	   this.images = images;
	       }
	       
	       public void run(){
	    	  
	    	   

				for (int i = 0; i < images.length; i++) {
					Bitmap bitmap = ImageHttpPadClient.getBitmap(images[i]);
					map.put(images[i], bitmap);
				}
				
				Message ms = Message.obtain();
				ms.what = 1;
				handler.sendMessage(ms);

	       }
	}
	@Override
	public int getCount()
	{
		if (textArr != null) {
			return textArr.length;
		}
		return 0;
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
		if (map.get(imageArr[position])==null) {
			Bitmap bitmap = ImageHttpPadClient.getBitmap(imageArr[position]);
			map.put(imageArr[position], bitmap);
		} else {
			imgview.setImageBitmap(map.get(imageArr[position]));
		}
		//imgview.setImageBitmap(ImageHttpPadClient.getBitmapPad(imageArr[position]));
		imgviewBack.setVisibility(View.GONE);
		/*imgview.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (checked != -1 && checked != position){
					Toast.makeText(context, "请先取消之前选中的应用",
							Toast.LENGTH_SHORT).show();
				} else if (checked == position){
					checked = -1;
					imgviewBack.setVisibility(View.GONE);
				} else {
					checked = position;
					imgviewBack.setVisibility(View.VISIBLE);
				}
			}
		});*/
		return convertView;
	}
	/**
	 * 释放map
	 */
	public void recycleBitmap(){
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, Bitmap> set= (Entry<String, Bitmap>) it.next();
			set.getValue().recycle();
		}
	}
	
}
