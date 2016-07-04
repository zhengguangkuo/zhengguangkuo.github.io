package com.mt.app.payment.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.platform.comapi.map.v;
import com.mt.android.R;
import com.mt.app.payment.activity.Discount_02_MainDetailActivity;
import com.mt.app.payment.requestbean.EleCard_business_Bean;
import com.mt.app.payment.test.ImageLoader;
import com.mt.app.payment.tools.DistanceUtil;
import com.mt.app.payment.tools.ImageBufferFree;
import com.mt.app.payment.tools.ImageTool;

public class Discount_dis_adapter extends BaseAdapter {
	protected static final String TAG_LOG = Discount_dis_adapter.class.getSimpleName() ;
	private LayoutInflater inflater;
	private List<EleCard_business_Bean> datalists;
	Map<String, SoftReference<Bitmap>> caches;
	private Context context;
	private ViewHolder viewHolder ;
	private ImageLoader imageLoader; 
	
	public Discount_dis_adapter(Context context , List<EleCard_business_Bean> datalists){
		caches = new HashMap<String, SoftReference<Bitmap>>();                                                                                                                                                                                                                                                  
		if(datalists != null){
			this.datalists = datalists;
		}else{
			this.datalists = new ArrayList<EleCard_business_Bean>();
		}	
		
		this.context = context;
		this.inflater = LayoutInflater.from(this.context);
		imageLoader = new ImageLoader(context) ;
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
			convertView = inflater.inflate(R.layout.elecard_buslist_adapter, null) ;
			viewHolder = new ViewHolder() ;
			viewHolder.linear = (LinearLayout)convertView.findViewById(R.id.eleBus_linear);
			viewHolder.image = (ImageView)convertView.findViewById(R.id.image_eleBusiness);
			viewHolder.tvDis = (TextView)convertView.findViewById(R.id.tv_elecard_buss_info);
			viewHolder.tvName = (TextView)convertView.findViewById(R.id.tv_elecard_buss_title);
			viewHolder.tvTime = (TextView)convertView.findViewById(R.id.tv_elecard_buss_time);
			viewHolder.tvCount = (TextView)convertView.findViewById(R.id.tv_elecard_buss_count);
			viewHolder.tvDistance = (TextView)convertView.findViewById(R.id.tv_elecard_buss_distance);
			convertView.setTag(viewHolder) ;
		} else {
			viewHolder = (ViewHolder) convertView.getTag();  
		}
		
		
		final EleCard_business_Bean bean = datalists.get(position);
		viewHolder.image.setTag(bean.getPicture()) ;
		//设置已领优惠券列表中每一项的图片
		if(bean.getPicture() != null){
			String imageName = bean.getPicture();
//			imageLoader.DisplayImage(imageName, (Activity)context, viewHolder.image) ;
			new ImageTool().setImagePath(viewHolder.image, imageName, caches, handler, R.drawable.xiao_t);
		}else{
			viewHolder.image.setImageResource(R.drawable.xiao_t) ;
		}
		
		Log.i(TAG_LOG, bean.getDiscount()+","+bean.getPicture()+","+position) ;
		
		//设置活动名称
		viewHolder.tvDis.setText(bean.getDiscount());
		//设置商家名称
		viewHolder.tvName.setText(bean.getName());
		//设置有效期
		viewHolder.tvTime.setText(bean.getTime());
		//设置领用次数
		viewHolder.tvCount.setText("已派发" + bean.getCount()+ "张");
		//设置距离
		viewHolder.tvDistance.setText(DistanceUtil.getDistance(bean.getDistance()));
		
		viewHolder.linear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context , Discount_02_MainDetailActivity.class);
				intent.putExtra("id", bean.getActId()); //根据优惠券活动id
				intent.putExtra("merchId", bean.getMerchId()) ;
				Log.i(TAG_LOG, "优惠券活动id --->点击传给server " + bean.getActId()) ;
				context.startActivity(intent);
			}
		});
		return convertView;
	}
	
	public void addItem(EleCard_business_Bean newItem){
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
	
	
	private class ViewHolder {
		private LinearLayout linear ;
		private ImageView image ;
		private TextView tvDis ;
		private TextView tvName ;
		private TextView tvTime ;
		private TextView tvCount ;
		private TextView tvDistance ;
	}

}
