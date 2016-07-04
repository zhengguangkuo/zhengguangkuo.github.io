package com.mt.app.payment.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mt.android.R;
import com.mt.app.payment.activity.EleCard_BusDetailsActivity;
import com.mt.app.payment.activity.EleCard_BusinessCardActivity;
import com.mt.app.payment.requestbean.CouponsReturnReqBean;
import com.mt.app.payment.requestbean.EleCard_business_Bean;
import com.mt.app.payment.tools.DistanceUtil;
import com.mt.app.payment.tools.ImageTool;

public class EleCard_business_adapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<EleCard_business_Bean> datalists;
	Map<String, SoftReference<Bitmap>> caches;
	private Context context;
	
	public EleCard_business_adapter(Context context , List<EleCard_business_Bean> datalists){
		caches = new HashMap<String, SoftReference<Bitmap>>();
		if(datalists != null){
			this.datalists = datalists;
		}else{
			this.datalists = new ArrayList<EleCard_business_Bean>();
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
			convertView = inflater.inflate(R.layout.elecard_buslist_adapter, null);
		}
		LinearLayout linear = (LinearLayout)convertView.findViewById(R.id.eleBus_linear);
		ImageView image = (ImageView)convertView.findViewById(R.id.image_eleBusiness);
		TextView tvDis = (TextView)convertView.findViewById(R.id.tv_elecard_buss_info);
		TextView tvName = (TextView)convertView.findViewById(R.id.tv_elecard_buss_title);
		TextView tvTime = (TextView)convertView.findViewById(R.id.tv_elecard_buss_time);
		TextView tvCount = (TextView)convertView.findViewById(R.id.tv_elecard_buss_count);
		TextView tvDistance = (TextView)convertView.findViewById(R.id.tv_elecard_buss_distance);
		
		final EleCard_business_Bean bean = datalists.get(position);
		//设置已领优惠券列表中每一项的图片
		if(bean.getPicture() != null){
			String imageName = bean.getPicture();

			new ImageTool().setImagePath(image, imageName, caches, handler, R.drawable.xiao_t);
			
		}
		convertView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				AlertDialog.Builder builder = new Builder(
						context);
				
				builder.setMessage("确定要退领优惠券\""+(bean.getDiscount())+"\"?");
				
				builder.setTitle("退领优惠券");
				
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								CouponsReturnReqBean reqBean = new CouponsReturnReqBean();
								reqBean.setCouponIds(bean.getCouponId());
								((EleCard_BusinessCardActivity)context).deleteCoupon(reqBean,position);
								dialog.dismiss();
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				builder.create().show();
				return true;
			}
		});
		//设置活动名称
		tvDis.setText(bean.getDiscount());
		//设置商家名称
		tvName.setText(bean.getName());
		//设置有效期
		tvTime.setText(bean.getTime());
		//设置领用次数
		tvCount.setText("已派发" + bean.getCount()+ "张");
		//设置距离
		tvDistance.setText(DistanceUtil.getDistance(bean.getDistance()));
		
		linear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context , EleCard_BusDetailsActivity.class);
				intent.putExtra("id", bean.getCouponId());  //将查看的已领的优惠券的id传过去
				intent.putExtra("merchId", bean.getMerchId()) ;
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
}
