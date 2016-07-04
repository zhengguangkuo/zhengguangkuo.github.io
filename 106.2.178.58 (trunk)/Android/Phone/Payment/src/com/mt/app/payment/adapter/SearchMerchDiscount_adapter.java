package com.mt.app.payment.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mt.android.R;
import com.mt.android.protocol.http.client.ImageHttpClient;
import com.mt.app.payment.activity.EleCard_BusDetailsActivity;
import com.mt.app.payment.requestbean.EleCard_business_Bean;
import com.mt.app.payment.responsebean.SearnearByMerDiscountBean;
import com.mt.app.payment.responsebean.SearnearByMeryouhuiBean;
import com.mt.app.payment.tools.ImageBufferFree;
import com.mt.app.payment.tools.ImageTool;

public class SearchMerchDiscount_adapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<SearnearByMerDiscountBean> datalists;
	Map<String, SoftReference<Bitmap>> caches;
	private Context context;
	
	public SearchMerchDiscount_adapter(Context context , List<SearnearByMerDiscountBean> datalists){
		caches = new HashMap<String, SoftReference<Bitmap>>();
		if(datalists != null){
			this.datalists = datalists;
		}else{
			this.datalists = new ArrayList<SearnearByMerDiscountBean>();
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
			convertView = inflater.inflate(R.layout.merch_discount_list_adapter, null);
		}
		ImageView image = (ImageView)convertView.findViewById(R.id.imageView_merchant_discount_map);
		TextView tvDetails = (TextView)convertView.findViewById(R.id.merchant_discount_map_details);
		TextView tvDis = (TextView)convertView.findViewById(R.id.merchant_discount_map_discount);
		
		final SearnearByMerDiscountBean bean = datalists.get(position);
		//…Ë÷√Õº∆¨
		if(bean.getPic() != null){
			String imageName = bean.getPic();

			new ImageTool().setImagePath(image, imageName, caches, handler, R.drawable.xiang);
		}
		//…Ë÷√’€ø€œÍ«È
		tvDetails.setText(bean.getDetails());
		//…Ë÷√’€ø€¬ 
		tvDis.setText(bean.getRate());
		return convertView;
	}
	
	
	public void addItem(SearnearByMerDiscountBean newItem){
		this.datalists.add(newItem);
	}
	
	private Handler handler = new Handler() { // “Ï≤ΩÀ¢–¬Õº∆¨
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
