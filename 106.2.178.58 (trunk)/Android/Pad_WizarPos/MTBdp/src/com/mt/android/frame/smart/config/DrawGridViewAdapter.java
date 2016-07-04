package com.mt.android.frame.smart.config;


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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mt.android.R;
import com.mt.android.protocol.http.client.ImageHttpPadClient;
import com.mt.android.sys.mvc.controller.Controller;

public  class DrawGridViewAdapter extends ListViewAdapter<String[]>{
	private String[] textArr = null;
	private String[] imageArr = null;
	public  int checked = -1;
	private Map<String,Bitmap> map = new HashMap<String,Bitmap>();
	public DrawGridViewAdapter(List<String[]> datas , Context context){
		super(datas, context);
		
		if(datas == null){
			datas = new ArrayList<String[]>();
		}
		if(datas.size()>=1){
			textArr = (String[]) datas.get(0);
			imageArr = (String[]) datas.get(1);
			new ImageThread().start();
		}
	}
	private Handler handler = new Handler() {  //�첽ˢ��ͼƬ
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

			

			for (int i = 0; i < imageArr.length; i++) {
				
				Bitmap bitmap = ImageHttpPadClient.getBitmap(imageArr[i]);
				map.put(imageArr[i], bitmap);
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
	// ʵ�ָ÷������÷������ص�View����Ϊ�б��
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
		imgviewBack.setVisibility(View.GONE);
		imgview.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (checked != -1 && checked != position){
					Toast.makeText(context, "����ȡ��֮ǰѡ�еĿ�",
							Toast.LENGTH_SHORT).show();
				} else if (checked == position){
					checked = -1;
					Controller.session.remove("checked");
					imgviewBack.setVisibility(View.GONE);
				} else {
					checked = position;
					Controller.session.put("checked", position);
					imgviewBack.setVisibility(View.VISIBLE);
				}
			}
		});
		return convertView;
	}
	/**
	 * �ͷ�map
	 */
	public void recycleBitmap(){
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, Bitmap> set= (Entry<String, Bitmap>) it.next();
			set.getValue().recycle();
		}
	}
}
