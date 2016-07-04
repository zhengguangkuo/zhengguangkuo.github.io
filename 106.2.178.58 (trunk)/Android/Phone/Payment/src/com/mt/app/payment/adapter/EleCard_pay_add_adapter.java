package com.mt.app.payment.adapter;

import java.io.ByteArrayOutputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.mt.android.protocol.http.client.ImageHttpClient;
import com.mt.app.payment.activity.EleCard_PaymentActivity;
import com.mt.app.payment.activity.EleCard_pay_addCardActivity;
import com.mt.app.payment.requestbean.EleCard_business_Bean;
import com.mt.app.payment.requestbean.EleCard_pay_add_Bean;
import com.mt.app.payment.tools.ImageBufferFree;
import com.mt.app.payment.tools.ImageTool;

public class EleCard_pay_add_adapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<EleCard_pay_add_Bean> datalists;
	Map<String, SoftReference<Bitmap>> caches;
	private Context context;
	
	public EleCard_pay_add_adapter(Context context , List<EleCard_pay_add_Bean> datalists){
		caches = new HashMap<String, SoftReference<Bitmap>>();
		if(datalists != null){
			this.datalists = datalists;
		}else{
			this.datalists = new ArrayList<EleCard_pay_add_Bean>();
		}	
		this.context = context;
		this.inflater = LayoutInflater.from(this.context);
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
		if(convertView == null){
			convertView = inflater.inflate(R.layout.elecard_pay_add_adapter, null);
		}
		ImageView image = (ImageView)convertView.findViewById(R.id.image_eleAdd);
		TextView tvName = (TextView)convertView.findViewById(R.id.tv_elecard_add_name);
		TextView tvType = (TextView)convertView.findViewById(R.id.tv_elecard_add_type);
		Button btn = (Button)convertView.findViewById(R.id.btn_elecard_add_icon);
		final EleCard_pay_add_Bean bean = datalists.get(position);
		//设置添加新的支付卡界面上的图片
		if(bean.getPicture() != null){
			String imageName = bean.getPicture();

			new ImageTool().setImagePath(image, imageName, caches, handler, R.drawable.bank);
			
		}
		//设置发卡机构
		tvName.setText(bean.getName());
		//设置卡片的类型
		tvType.setText(bean.getType());
		//设置已绑定未绑定标识
		if(bean.getIcon().equalsIgnoreCase("0")){   //未绑定
			btn.setText("绑定");
		}else if(bean.getIcon().equalsIgnoreCase("1")){    //已绑定
			btn.setText("已绑定");
		}
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(bean.getIcon().equals("0")){
					EleCard_pay_addCardActivity aa = (EleCard_pay_addCardActivity)(context);
					aa.bindResult(bean.getId() , position);
				}
			}
		});
		return convertView;
	}
	

	public void addItem(EleCard_pay_add_Bean newItem){
		this.datalists.add(newItem);
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
	
	public void onAdapterDestroy() {
		new ImageTool().imageFree(caches);
	}

}

