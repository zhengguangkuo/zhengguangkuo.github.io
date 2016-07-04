package com.mt.app.payment.adapter;

import java.lang.ref.SoftReference;
import java.math.BigDecimal;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mt.android.R;
import com.mt.app.payment.activity.Discount_02bus_MainDetailActivity;
import com.mt.app.payment.activity.Discount_02bus_MainDetail_YacolActivity;
import com.mt.app.payment.common.Globals;
import com.mt.app.payment.requestbean.DiscountBusQueryBean;
import com.mt.app.payment.tools.DistanceUtil;
import com.mt.app.payment.tools.ImageTool;

public class Discount_bus_adapter extends BaseAdapter {
	private static final String TAG_lOG = Discount_bus_adapter.class.getSimpleName();
	private LayoutInflater inflater;
	private List<DiscountBusQueryBean> datalists;
	Map<String, SoftReference<Bitmap>> caches;
	private Context context;

	public Discount_bus_adapter(Context context, List<DiscountBusQueryBean> datalists) {
		caches = new HashMap<String, SoftReference<Bitmap>>();
		if (datalists != null) {
			this.datalists = datalists;
		} else {
			this.datalists = new ArrayList<DiscountBusQueryBean>();
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
			convertView = inflater.inflate(R.layout.discount_buslist_adapter, null);
		}
		LinearLayout linear = (LinearLayout) convertView.findViewById(R.id.discount_bus_linear);
		ImageView image = (ImageView) convertView.findViewById(R.id.image_discount_bus);
		TextView tvName = (TextView) convertView.findViewById(R.id.tv_discount_bus_name);
		TextView tvDistance = (TextView) convertView.findViewById(R.id.tv_discount_bus_distance);
		TextView tvAppName = (TextView) convertView.findViewById(R.id.tv_app_name);
		TextView tvAppDiscount = (TextView) convertView.findViewById(R.id.tv_app_discount);

		Log.i("current position =", position + "");
		final DiscountBusQueryBean bean = datalists.get(position);
		// 设置已领优惠券列表中每一项的图片
		if (bean.getPic_path() != null) {
			String imageName = bean.getPic_path();

			new ImageTool().setImagePath(image, imageName, caches, handler, R.drawable.xiao_t);
		} else {
			image.setImageResource(R.drawable.xiao_t) ;
		}
		// 设置商家名称
		tvName.setText(bean.getCname());
		// 设置距离
		tvDistance.setText(DistanceUtil.getDistance(bean.getMpayUser_merchant_distance()));
		// 设置折扣名称
		Log.i(TAG_lOG, "tvAppName == null ? --> " + (tvAppName == null) + ",, bean == null ---> " + (bean == null));
		tvAppName.setText(bean.getApp_name());

		// 设置折扣率
		BigDecimal bd = new BigDecimal(bean.getApp_discount() + "");
		tvAppDiscount.setText(bd.multiply(new BigDecimal("10")) + "折");

		linear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (Globals.MERCH_FLAG_AYCOL.equals(bean.getApp_id())) {
					Intent intent = new Intent(context, Discount_02bus_MainDetail_YacolActivity.class);
					intent.putExtra("mid", bean.getOtherMerchId());
					context.startActivity(intent);
				} else {
					Intent intent = new Intent(context, Discount_02bus_MainDetailActivity.class);
					intent.putExtra("mid", bean.getMerch_id());
					intent.putExtra("appid", bean.getApp_id());
					intent.putExtra("distance", bean.getMpayUser_merchant_distance() + "");
					context.startActivity(intent);
				}

				/*
				 * final List<NameValuePair> parameters = new
				 * ArrayList<NameValuePair>() ; parameters.add(new
				 * BasicNameValuePair("id", "0146042")) ; parameters.add(new
				 * BasicNameValuePair("returnType", "json")) ;
				 * parameters.add(new BasicNameValuePair("type", "1")) ;
				 * parameters.add(new BasicNameValuePair("v", "1.3")) ;
				 * parameters.add(new BasicNameValuePair("callType", "android"))
				 * ; parameters.add(new BasicNameValuePair("uuid",
				 * GetDeviceInfoUtil.getImei(context))) ;
				 * 
				 * 
				 * final String url =
				 * "http://app.yacol.com/yacolApp/mobile/store.do?"; //
				 * JSONObject jsonObject = HttpUtil.getJSONObjFromUrlByPost(url,
				 * paramList, true) ;
				 * 
				 * new Thread(){ public void run() { try { String str =
				 * HttpUtil.getReturnStrFromUrl(0, url, parameters, true) ;
				 * System.out.println("yacol------------->" + str); } catch
				 * (Exception e) { e.printStackTrace(); } } }.start() ;
				 */
			}
		});
		return convertView;
	}

	public void addItem(DiscountBusQueryBean newItem) {
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
