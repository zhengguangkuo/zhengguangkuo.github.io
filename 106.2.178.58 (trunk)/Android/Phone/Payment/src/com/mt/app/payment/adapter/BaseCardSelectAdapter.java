package com.mt.app.payment.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mt.android.R;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.payment.responsebean.Card_DataBean;
import com.mt.app.payment.tools.ImageBufferFree;
import com.mt.app.payment.tools.ImageTool;

/**
 * 

 * @Description:基卡类型修改界面用到的adapter

 * @author:dw

 * @time:2013-9-13 下午3:44:17
 */
public class BaseCardSelectAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	
	private ArrayList<Card_DataBean> datalists;
	Map<String, SoftReference<Bitmap>> caches;
	private Context context; 
	private boolean isBind = false;//是否有绑定的基卡
	
	public BaseCardSelectAdapter(Context context , ArrayList<Card_DataBean> list){
		caches = new HashMap<String, SoftReference<Bitmap>>();
		if(list != null){
			this.datalists = list;
		}else{
			this.datalists = new ArrayList<Card_DataBean>();
		}	
		this.context = context;
		this.inflater = LayoutInflater.from(this.context);
		
		for(int i= 0 ; i < datalists.size() ; i++){   
			if (datalists.get(i).getBind_flag().equals("1")) {
				isBind = true;
			} 
		}
		if (!isBind) {
			 Controller.session.remove("cardBind");
		} else {
			 Controller.session.put("cardBind","bind");
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datalists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datalists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.basecard_select_item_layout, null);
		}
	
		TextView name = (TextView) convertView.findViewById(R.id.cardtype);//卡片名称
		ImageView imageView = (ImageView) convertView.findViewById(R.id.cardimage);//卡片图片
		
		final Button set = (Button) convertView.findViewById(R.id.set);  //设置按钮
		
		name.setText(datalists.get(position).getCard_name());//设置卡片名字
		set.setText(datalists.get(position).getBind_flag().equals("1")?"取消设置":"设置");//按钮显示内容
		
		//设置图片
		if(datalists.get(position).getPic_path() != null){
			
			String imageName = datalists.get(position).getPic_path();

			new ImageTool().setImagePath(imageView, imageName, caches, handler, R.drawable.bank);
		}
		isBind = Controller.session.get("cardBind")==null?false:true;
		if (isBind) {  //已经有了绑定的基卡
			 if (datalists.get(position).getBind_flag().equals("1")) { //当前为绑定的基卡
				 set.setEnabled(true);    //绑定的可以点击，选择解除绑定
			 } else {
				 set.setEnabled(false);
			 }
		} else {  //未绑定基卡
			set.setEnabled(true);
		}
		
		set.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 if (datalists.get(position).getBind_flag().equals("1")) { //当前为绑定的基卡
					 datalists.get(position).setBind_flag("0");
					 Controller.session.remove("cardBind");
					 Controller.session.remove("bindInputDetails");
					 notifyDataSetChanged();
				 } else {  //当前为未绑定的基卡
					 datalists.get(position).setBind_flag("1");
					 Controller.session.put("cardBind","bind");
					 if(datalists.get(position).getInput_tip() != null 
							 && !(datalists.get(position).getInput_tip().equals(""))){
						 Controller.session.put("bindInputDetails", datalists.get(position).getInput_tip());
					 }
					 
					 notifyDataSetChanged();
				 }
			}
		});
		
		
		return convertView;
	}
	
	public void onAdapterDestroy() {
		new ImageTool().imageFree(caches);
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

}
