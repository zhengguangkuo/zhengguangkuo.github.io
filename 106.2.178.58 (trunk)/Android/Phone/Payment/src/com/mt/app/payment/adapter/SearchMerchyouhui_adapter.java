package com.mt.app.payment.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.mt.android.R;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.activity.Discount_01_bus_MainListActivity;
import com.mt.app.payment.activity.Discount_01_dis_MainListActivity;
import com.mt.app.payment.activity.Discount_02_MainDetailActivity;
import com.mt.app.payment.activity.Discount_04_Main_youhui_DetailActivity;
import com.mt.app.payment.activity.SearchNearByActivity;
import com.mt.app.payment.requestbean.EleCard_paycard_Bean;
import com.mt.app.payment.requestbean.ReceiveDiscountDisReqBean;
import com.mt.app.payment.responsebean.SearnearByMeryouhuiBean;
import com.mt.app.payment.tools.ImageBufferFree;
import com.mt.app.payment.tools.ImageTool;
import com.mt.app.tab.activity.TabDiscount_01_MainListActivity;

public class SearchMerchyouhui_adapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<SearnearByMeryouhuiBean> datalists;
	Map<String, SoftReference<Bitmap>> caches;
	private Context context;

	public SearchMerchyouhui_adapter(Context context,
			List<SearnearByMeryouhuiBean> datalists) {
		caches = new HashMap<String, SoftReference<Bitmap>>();
		if (datalists != null) {
			this.datalists = datalists;
		} else {
			this.datalists = new ArrayList<SearnearByMeryouhuiBean>();
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
			convertView = inflater.inflate(R.layout.merch_youhui_list_adapter,
					null);
		}
		ImageView image = (ImageView) convertView
				.findViewById(R.id.imageView_merchant_youhui_map);
		TextView tvName = (TextView) convertView
				.findViewById(R.id.merchant_youhui_map_name);
		TextView tvTime = (TextView) convertView
				.findViewById(R.id.merchant_youhui_map_time);
		TextView tvCount = (TextView) convertView
				.findViewById(R.id.merchant_youhui_map_count);
		LinearLayout linear = (LinearLayout) convertView
				.findViewById(R.id.merch_youhui_adapter_linear);

		final SearnearByMeryouhuiBean bean = datalists.get(position);
		// 设置图片
		if (bean.getPic() != null) {
			String imageName = bean.getPic();

			new ImageTool().setImagePath(image, imageName, caches, handler, R.drawable.xiao_t);

		}
		// 设置活动名称
		tvName.setText(bean.getName());
		// 设置活动起始日期
		tvTime.setText(bean.getTime());
		// 设置发放张数
		tvCount.setText("已派发" + bean.getCount() + "张");

		linear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				final View view = LayoutInflater.from(context).inflate(
						R.layout.map_distance_dis_receive_dialog, null);
				/*new AlertDialog.Builder(context)
						.setView(view)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										((Discount_04_Main_youhui_DetailActivity)context).receiveYouhui(context , bean.getActID(), bean.getCouponIssuerID());
										// 关闭对话框
										dialog.dismiss();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 关闭对话框
										dialog.dismiss();
									}
								}).show();*/
				
				Intent intent = new Intent(context , Discount_02_MainDetailActivity.class);
				intent.putExtra("id", bean.getActID()); //根据优惠券活动id
				intent.putExtra("merchId", bean.getMerchId()) ;
				context.startActivity(intent);
			}
		});

		return convertView;
	}

	
	public void addItem(SearnearByMeryouhuiBean newItem){
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
