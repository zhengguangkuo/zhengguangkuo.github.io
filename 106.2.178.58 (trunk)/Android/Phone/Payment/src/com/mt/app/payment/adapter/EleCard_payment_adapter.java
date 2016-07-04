package com.mt.app.payment.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mt.android.R;
import com.mt.app.payment.activity.EleCard_pay_detailsActivity;
import com.mt.app.payment.requestbean.EleCard_paycard_Bean;
import com.mt.app.payment.tools.ImageTool;

public class EleCard_payment_adapter extends BaseAdapter {
	private static final String TAG_LOG = EleCard_payment_adapter.class.getSimpleName() ;
	private LayoutInflater inflater;
	private List<EleCard_paycard_Bean> datalists;
	Map<String, SoftReference<Bitmap>> caches;
	private Context context;

	public EleCard_payment_adapter(Context context,
			List<EleCard_paycard_Bean> datalists) {
		caches = new HashMap<String, SoftReference<Bitmap>>();
		if (datalists != null) {
			this.datalists = datalists;
		} else {
			this.datalists = new ArrayList<EleCard_paycard_Bean>();
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
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.elecard_paymentcard_adapter, null);
		}
		ImageView image = (ImageView) convertView
				.findViewById(R.id.image_elecard_pay);
		ImageView imageDefault = (ImageView)convertView.findViewById(R.id.image_ele_default_card);
		final EleCard_paycard_Bean bean = datalists.get(position);
		if (bean.getPicture() != null) {
			
			String imageName = bean.getPicture();

			Bitmap bitmap = new ImageTool().getImage(imageName, caches, handler);
			
			if(bitmap != null){
				int wei = bitmap.getWidth();
				int hei = bitmap.getHeight();
				Bitmap bitRe = Bitmap.createBitmap(bitmap, 0, 0, wei, hei / 3);
				image.setImageBitmap(bitRe);
			}else{
				image.setImageResource(R.drawable.bank_x);
			}
		}
		
		if(bean.isFlag()) {  //显示默认卡的图标
			imageDefault.setVisibility(View.VISIBLE);
		}else{
			imageDefault.setVisibility(View.GONE);
		}
		
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context,
						EleCard_pay_detailsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				intent.putExtra("data", datalists.get(position).getId());
				intent.putExtra("defaultCard", bean.isFlag());
				context.startActivity(intent);
			}

		});
		return convertView;
	}


	public void addItem(EleCard_paycard_Bean newItem){
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
