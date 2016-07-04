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
import com.mt.app.payment.activity.BaseCardActivity;
import com.mt.app.payment.activity.EleCard_PaymentActivity;
import com.mt.app.payment.activity.EleCard_pay_addCardActivity;
import com.mt.app.payment.requestbean.EleCard_business_Bean;
import com.mt.app.payment.requestbean.EleCard_pay_add_Bean;
import com.mt.app.payment.responsebean.Card_DataBean;
import com.mt.app.payment.tools.ImageBufferFree;
import com.mt.app.payment.tools.ImageTool;

public class BaseCardAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Card_DataBean> datalists;
	Map<String, SoftReference<Bitmap>> caches;
	private Context context;
	
	public BaseCardAdapter(Context context , List<Card_DataBean> datalists){
		caches = new HashMap<String, SoftReference<Bitmap>>();
		if(datalists != null){
			this.datalists = datalists;
		}else{
			this.datalists = new ArrayList<Card_DataBean>();
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
			convertView = inflater.inflate(R.layout.basecard_item, null);
		}
		ImageView image = (ImageView)convertView.findViewById(R.id.iv_pic);
		TextView tvName = (TextView)convertView.findViewById(R.id.tv_basecard_name);
		TextView tvNumber = (TextView)convertView.findViewById(R.id.tv_basecard_number);
		Button btn = (Button)convertView.findViewById(R.id.bt_set);
		final Card_DataBean bean = datalists.get(position);
		if(bean.getPic_path() != null){
			String imageName = bean.getPic_path();
			new ImageTool().setImagePath(image, imageName, caches, handler, R.drawable.bank);
		}
		tvName.setText(bean.getCard_name());
		tvNumber.setText(bean.getCard_no());
		if(bean.getBind_flag().equalsIgnoreCase("0")){   //未绑定
			btn.setText("绑定");
		}else if(bean.getBind_flag().equalsIgnoreCase("1")){    //已绑定
			btn.setText("解绑");
		}
		
		btn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				BaseCardActivity aa = (BaseCardActivity)(context);
				if(bean.getBind_flag().equals("0")){
					aa.bindResult(bean.getCard_type() , position, bean.getInput_tip());
				} else if(bean.getBind_flag().equals("1")) {
					aa.unbindResult() ;
				}
			}
		});
		return convertView;
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

