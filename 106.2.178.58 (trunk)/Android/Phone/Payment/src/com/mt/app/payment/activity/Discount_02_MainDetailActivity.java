package com.mt.app.payment.activity;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.miteno.coupon.entity.MerchAct;
import com.miteno.mpay.entity.Merchant;
import com.mt.android.R;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.requestbean.AllDisDetailReqBean;
import com.mt.app.payment.requestbean.DiscountMerchantDetailsReqBean;
import com.mt.app.payment.requestbean.ReceiveDiscountDisReqBean;
import com.mt.app.payment.requestbean.SeeMerchReqBean;
import com.mt.app.payment.responsebean.DiscountDetailResult;
import com.mt.app.payment.responsebean.DiscountDisAllResult;
import com.mt.app.payment.responsebean.ImageResultBean;
import com.mt.app.payment.tools.ImageTool;
import com.mt.app.payment.tools.OrderView;
import com.mt.app.payment.tools.OrderView.StayViewListener;

public class Discount_02_MainDetailActivity extends BaseActivity {
	private Button btnBack, btnReceive ,btnReceiveTop;
	private ImageView imageP , imagePhone;
	private TextView tvDetails, tvTime, tvAdd, tvTele   , tvRules  , tvMerDetails;
	private WebView webview;
	private ImageView imageSee;
	private LinearLayout tvMap;
	// 声明uri
	private Uri uri;
	// 坐标
	private String coordenite;
	// 经度
	private double lat;
	// 纬度
	private double lon;
	private String merchantName;
	private String imageName, mid;
	Map<String, SoftReference<Bitmap>> caches = new HashMap<String, SoftReference<Bitmap>>();
	private Handler imagehandler = new Handler() { // 异步刷新图片
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (caches.containsKey(imageName)) {

					SoftReference<Bitmap> sf = caches.get(imageName);
					Bitmap bitmap = sf.get();

					if (bitmap == null) {
						imageP.setImageResource(R.drawable.xiang);
					} else {
						imageP.setImageBitmap(bitmap);
					}
				}
				break;
			}
		}
	};

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView(R.layout.discount_02_maindetail);

		OrderView refreshableView = (OrderView) findViewById(R.id.refreshview_dis_details);
		
		final View viewStay = findViewById(R.id.theviewstay_dis_details);
		btnReceiveTop = (Button)viewStay.findViewById(R.id.discountDisDetails_btnReceive);

		refreshableView.setStayView(findViewById(R.id.theview_dis_details),
				(ScrollView) findViewById(R.id.scrollview_dis_details),
				new StayViewListener() {
					@Override
					public void onStayViewShow() {
						// 从下往上拉的时候回复显示
						viewStay.setVisibility(View.VISIBLE);

					}

					@Override
					public void onStayViewGone() {
						// 从上往下拉隐藏布局
						viewStay.setVisibility(View.GONE);

					}
				});

		final ScrollView sc = (ScrollView) findViewById(R.id.scrollview_dis_details);
		btnBack = (Button) findViewById(R.id.btn_discountDisDetails_back);
		imageP = (ImageView) findViewById(R.id.discountDisDetails_pic);
		imagePhone = (ImageView) findViewById(R.id.image_phone_discount_dis);
		// tvDetails = (TextView) findViewById(R.id.discountDisDetails_details);
		tvTime = (TextView) findViewById(R.id.discountDisDetails_time);
		tvAdd = (TextView) findViewById(R.id.discountDisDetails_address);
		tvMap = (LinearLayout) findViewById(R.id.tv_map_discountDisDetails);
		tvTele = (TextView) findViewById(R.id.discountDisDetails_call);
		tvRules = (TextView) findViewById(R.id.discountDisDetails_rules);
		tvRules.setSingleLine(false);
		tvMerDetails = (TextView) findViewById(R.id.discountDisDetails_merchant_details);
		btnReceive = (Button) findViewById(R.id.discountDisDetails_btnReceive);
		imageSee = (ImageView) findViewById(R.id.image_see_2_details);
		imageSee.setVisibility(View.GONE) ;
		webview = (WebView) findViewById(R.id.discountDisDetails_webview);
		webview.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int contentHeight = webview.getContentHeight();
				int height = webview.getHeight();
				
				
				if(contentHeight > height){//webview滑动事件为true
					sc.requestDisallowInterceptTouchEvent(true);
				}else{
					sc.requestDisallowInterceptTouchEvent(false);
				}
			/*	if (event.getAction() == MotionEvent.ACTION_UP)
					sc.requestDisallowInterceptTouchEvent(false);
				else
					sc.requestDisallowInterceptTouchEvent(true);*/

				return false;
			}
		});

		// 给返回按钮设置监听事件
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Discount_02_MainDetailActivity.this.finish();
			}
		});

		String id = getIntent().getStringExtra("id");
		String merchId = getIntent().getStringExtra("merchId") ;
		// 发请求
		Request request = new Request();
		AllDisDetailReqBean bean = new AllDisDetailReqBean();
		bean.setActId(id);
		bean.setMerchId(merchId) ;
		request.setData(bean);
		go(CommandID.map.get("Discount_dis_details"), request, true);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Controller.session.put("detailsFinished_dis02", "ok");
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
		if (response.getBussinessType() != null
				&& response.getBussinessType().equals("discount_dis_details")) {
			ImageResultBean bean = (ImageResultBean) response.getData();

			final DiscountDetailResult result = (DiscountDetailResult) bean
					.getRespBean();

			// 设置优惠券详情界面上的图片
			imageName = result.getRows().get(0).getPic_path();

			new ImageTool().setImagePath(imageP, imageName, caches,
					imagehandler, R.drawable.xiang);
			
			//设置活动规则
			/**每天最大发行数量**/
			String dayLimit = String.valueOf(result.getRows().get(0).getDay_limit());
			/**当天发行数量**/
			String dayCount = String.valueOf(result.getRows().get(0).getDay_cnt());  
			/**每人最多领用数量**/
			String personLimit = String.valueOf(result.getRows().get(0).getPersonal_limit());
			/**使用该优惠劵时最低消费金额**/
			String lowAmount = String.valueOf(result.getRows().get(0).getLowAmtCredits());
			/**一笔消费最多使用优惠劵张数**/
			String maxPieces = String.valueOf(result.getRows().get(0).getMax_pieces());  
			
			StringBuilder sb = new StringBuilder();
			if(dayLimit != null && !(dayLimit.equals("null"))){
				if(dayLimit.equals("-1")){
					sb.append("每天最大发行数量不限");
				}else{
					sb.append("每天最大发行数量为" + dayLimit + "张");
				}
			}
			if(dayCount != null && !(dayCount.equals("null"))){
				sb.append("；" + "\n" + "当天发行数量为" + dayCount + "张");
			}
			if(personLimit != null && !(personLimit.equals("null"))){
				sb.append("；" + "\n" + "每人最多能领用" + personLimit + "张 ");
			}
			if(lowAmount != null && !(lowAmount.equals("null"))){
				sb.append("；" + "\n" +  "使用该优惠劵时，最低消费金额为" + lowAmount + "元");
			}
			if(maxPieces != null && !(maxPieces.equals("null"))){
				sb.append("；" + "\n" + "一笔消费最多能使用优惠劵" + maxPieces + "张");
			}
			
			if(sb != null && !(sb.toString().equals(""))){
				tvRules.setText(sb.toString());
			}else{
				tvRules.setText("暂无活动规则");
			}
			
			// 设置有效期（活动起始日期-活动终止日期）
			String Start_date =result.getRows().get(0).getStart_date().substring(0,4)+"."+
					result.getRows().get(0).getStart_date().substring(4,6)+"."+
					result.getRows().get(0).getStart_date().substring(6, 8);
			String End_date=result.getRows().get(0).getEnd_date().substring(0,4)+"."+
					result.getRows().get(0).getEnd_date().substring(4,6)+"."+
		            result.getRows().get(0).getEnd_date().substring(6, 8);
			tvTime.setText(Start_date+ "-" + End_date);
			// 设置活动详情
			// tvDetails.setText(result.getRows().get(0).getContent());
			String data = result.getRows().get(0).getContent();
			webview.getSettings().setDefaultTextEncodingName("utf-8");

			// webview.loadDataWithBaseURL(null, data, "text/html", "utf-8",
			// null);
			// webview.loadData(data, "text/html", "utf-8");
			webview.loadData(data, "text/html; charset=UTF-8", null);
			/*Set<MerchAct> mm = result.getRows().get(0).getMerchActs();
			MerchAct[] mmm = new MerchAct[] {};
			MerchAct[] m = mm.toArray(mmm);
			if (m.length != 0) {
				// 设置商家地址
				tvAdd.setText(m[0].getMerchant().getAddress());
				// 设置联系方式
				tvTele.setText(m[0].getMerchant().getTelephone());
				// 设置商家坐标
				coordenite = (m[0].getMerchant().getCoordinate());
				// 商家id
				mid = m[0].getMerchant().getId();

				if (coordenite != null) {
					lat = Double.valueOf(coordenite.split(",")[0]);
					lon = Double.valueOf(coordenite.split(",")[1]);
				}
				// 设置商家名称
				merchantName = (m[0].getMerchant().getCname());
			}*/
			
			/*Merchant chant = result.getRows().get(0).getMerchant() ;*/
			// 设置商家地址
			tvAdd.setText(result.getRows().get(0).getAddress());
			// 设置联系方式
			tvTele.setText(result.getRows().get(0).getTelephone());
			// 设置商家坐标
			coordenite = (result.getRows().get(0).getCoordinate()) ;
			// 商家id
			mid = result.getRows().get(0).getMerch_id() ;
			if (coordenite != null) {
				lat = Double.valueOf(coordenite.split(",")[0]);
				lon = Double.valueOf(coordenite.split(",")[1]);
			}
			// 设置商家名称
			merchantName = (result.getRows().get(0).getMerch_name()) ;
			
			// 给打电话设置监听事件
			imagePhone.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (tvTele.getText() != null
							&& !tvTele.getText().equals("")) {
						uri = Uri.parse("tel:" + tvTele.getText().toString());
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_DIAL);
						intent.setData(uri);
						startActivity(intent);
					}
				}
			});
			// 给显示地图设置监听事件
			tvMap.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(
							Discount_02_MainDetailActivity.this,
							DisDetailMapActivity.class);
					intent.putExtra("lat", lat);
					intent.putExtra("lon", lon);
					intent.putExtra("merchantName", merchantName);
					startActivity(intent);
				}
			});
			//给查看商家详情介绍添加点击事件
			tvMerDetails.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Request request = new Request();
					Controller.session.put("dis_dis_merchantDetails", mid);
					go(CommandID.map.get("Goto_Discount_dis_merchant_details"), request, false);
				}
			});
			// 关注商商家监听事件
			imageSee.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (Controller.session.get("user") != null) { // 已经登录了
						// 发请求
						Request request = new Request();
						SeeMerchReqBean bean = new SeeMerchReqBean();
						// 商家ID
						bean.setMerchIds(mid);
						bean.setHandleType("1");
						request.setData(bean);
						go(CommandID.map.get("SeeMerchant"), request, true);
					} else {
						Toast.makeText(Discount_02_MainDetailActivity.this,
								"您还没有登录，不能关注该商家，请您先登录！", Toast.LENGTH_LONG)
								.show();
						Request request = new Request();// TO_USER_REGISTER
						go(CommandID.map.get("USER_LOGIN"), request, false);
					}
				}
			});
			// 领用优惠券
			btnReceive.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (Controller.session.get("user") != null) { // 说明已经登陆了，那么可以领用优惠券了
						Request request = new Request();
						ReceiveDiscountDisReqBean b = new ReceiveDiscountDisReqBean();
						b.setActId(result.getRows().get(0).getAct_id());
						b.setCouponIssuerId(result.getRows().get(0)
								.getCouponIssuerId());
						request.setData(b);
						go(CommandID.map.get("Discount_dis_receive"), request,
								true);
					} else {
						Toast.makeText(Discount_02_MainDetailActivity.this,
								"您还没有登录，不能领用该优惠券，请您先登录！", Toast.LENGTH_LONG)
								.show();
						Request request = new Request();// TO_USER_REGISTER
						go(CommandID.map.get("USER_LOGIN"), request, false);
					}
				}
			});
			btnReceiveTop.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (Controller.session.get("user") != null) { // 说明已经登陆了，那么可以领用优惠券了
						Request request = new Request();
						ReceiveDiscountDisReqBean b = new ReceiveDiscountDisReqBean();
						b.setActId(result.getRows().get(0).getAct_id());
						b.setCouponIssuerId(result.getRows().get(0)
								.getCouponIssuerId());
						request.setData(b);
						go(CommandID.map.get("Discount_dis_receive"), request,
								true);
					} else {
						Toast.makeText(Discount_02_MainDetailActivity.this,
								"您还没有登录，不能领用该优惠券，请您先登录！", Toast.LENGTH_LONG)
								.show();
						Request request = new Request();// TO_USER_REGISTER
						go(CommandID.map.get("USER_LOGIN"), request, false);
					}
				}
			});
		} else if (response.getBussinessType() != null
				&& response.getBussinessType().equals("discount_dis_receive")) {
			Toast.makeText(this, "领用成功！", Toast.LENGTH_LONG).show();
			btnReceive.setClickable(false);
		}
		try {

			if ((ResponseBean) response.getData() != null) {

				showToast(Discount_02_MainDetailActivity.this,
						((ResponseBean) response.getData()).getMessage());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		if (response.getData() != null) {
			ResponseBean respBean = new ResponseBean();
			respBean = (ResponseBean) response.getData();
			Toast.makeText(this, respBean.getMessage(), Toast.LENGTH_LONG)
					.show();
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		new ImageTool().imageFree(caches);
	}
}
