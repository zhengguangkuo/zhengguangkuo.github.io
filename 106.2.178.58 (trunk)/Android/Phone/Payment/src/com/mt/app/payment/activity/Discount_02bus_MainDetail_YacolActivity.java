package com.mt.app.payment.activity;

import java.io.UnsupportedEncodingException;
import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miteno.mpay.entity.MpayApp;
import com.mt.android.R;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.requestbean.DiscountBusDetailsReqBean;
import com.mt.app.payment.requestbean.SeeMerchReqBean;
import com.mt.app.payment.requestbean.yacol.YaColReqBean;
import com.mt.app.payment.responsebean.DiscountBusQueryResult;
import com.mt.app.payment.responsebean.ImageResultBean;
import com.mt.app.payment.responsebean.yacol.StoreSingleRespBean;
import com.mt.app.payment.tools.GetDeviceInfoUtil;
import com.mt.app.payment.tools.ImageBufferFree;
import com.mt.app.payment.tools.ImageTool;

public class Discount_02bus_MainDetail_YacolActivity extends BaseActivity {
	private Button btnBack;
	private ImageView imageP, imageSee , imagePhone, iv_heat;
	private TextView tvLine, tvAdd, tvTele, tvDiscount , tvMerchantDetails, tv_merchName ;
	private LinearLayout tvMap;
	// 声明uri
	private Uri uri;
	// 坐标
	private String coordenite;
	//商家ID
	private String merID;
	// 经度
	private double lat;
	// 纬度
	private double lon;
	private String mid;
	private String imageName;
	private String merchantName;
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
		setContentView(R.layout.discount_02bus_maindetail_yacol);

		btnBack = (Button) findViewById(R.id.btn_discountBusDetails_back);
		imageP = (ImageView) findViewById(R.id.discountBusDetails_pic);
		imageSee = (ImageView) findViewById(R.id.image_see_details);
		imagePhone = (ImageView) findViewById(R.id.image_phone_discount_bus);
		tvLine = (TextView) findViewById(R.id.discountBusDetails_line);
		tvAdd = (TextView) findViewById(R.id.discountBusDetails_address);
		tvMap = (LinearLayout) findViewById(R.id.tv_map_discountBusDetails);
		tvTele = (TextView) findViewById(R.id.discountBusDetails_call);
		tvDiscount = (TextView) findViewById(R.id.tv_discount);
		tvMerchantDetails = (TextView) findViewById(R.id.discountBUSDetails_merchant_details);
		tv_merchName = (TextView) findViewById(R.id.tv_merchName);
		iv_heat = (ImageView) findViewById(R.id.iv_heat);

		// 给返回按钮设置监听事件
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Discount_02bus_MainDetail_YacolActivity.this.finish();
			}
		});

		mid = getIntent().getStringExtra("mid");
		// 发请求
		Request request = new Request();
		YaColReqBean bean = new YaColReqBean() ;
		bean.setId(mid) ;
		bean.setUuid(GetDeviceInfoUtil.getImei(this)) ;
		request.setData(bean);
		go(CommandID.map.get("Discount_bus_details_yacol"), request, true);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Controller.session.put("detailsFinished_02bus", "ok"); 
	}

	@Override
	public void onSuccess(Response response) {
		if (response.getBussinessType() != null
				&& response.getBussinessType().equals("SeeMerchant")) {
			Toast.makeText(this, "关注商家成功！", Toast.LENGTH_LONG).show();
		} else {
			ImageResultBean bean = (ImageResultBean) response.getData();

			final StoreSingleRespBean result = (StoreSingleRespBean) bean.getRespBean();

			// 设置优惠券详情界面上的图片
			if(result.getImages().size() > 0) {
				imageName = result.getImages().get(0) ;				
			}
			
			new ImageTool().setImagePath(imageP, imageName, caches, imagehandler, R.drawable.xiang);
			// 商户评分
			int score = result.getScore() ;
			switch (score/20) {
			case 0:
				iv_heat.setImageResource(R.drawable.star0) ;
				break;
			case 1:
				iv_heat.setImageResource(R.drawable.star1) ;
				break;
			case 2:
				iv_heat.setImageResource(R.drawable.star2) ;
				break;
			case 3:
				iv_heat.setImageResource(R.drawable.star3) ;
				break;
			case 4:
				iv_heat.setImageResource(R.drawable.star4) ;
				break;
			case 5:
				iv_heat.setImageResource(R.drawable.star5) ;
				break;
			}
			
			// 设置折扣
			tvDiscount.setText(result.getDisDescription());
			// 设置商家地址
			tvAdd.setText(result.getAddr());
			// 设置乘车路线
			tvLine.setText(GetBase64Decode(result.getBusline())) ;
			// 设置联系方式
			tvTele.setText(result.getContact().getPhone());
			// 设置商家坐标
			coordenite = (result.getXpos()+","+result.getYpos()) ;

			if (coordenite != null) {
				lat = Double.valueOf(coordenite.split(",")[0]);
				lon = Double.valueOf(coordenite.split(",")[1]);
			}
			
			merID = result.getId() ;
			
			// 设置商家名称
			merchantName = (result.getName());
			tv_merchName.setText(merchantName) ;
			// 给打电话设置监听事件

			imagePhone.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (tvTele.getText() != null
							&& !tvTele.getText().toString().trim().equals("")) {
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
							Discount_02bus_MainDetail_YacolActivity.this,
							DisDetailMapActivity.class);
					intent.putExtra("lat", lat);
					intent.putExtra("lon", lon);
					intent.putExtra("merchantName", merchantName);
					startActivity(intent);
				}
			});
			
			// 设置商家信息
			
			tvMerchantDetails.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Request request = new Request();
					Controller.session.put("Goto_Discount_dis_merchant_details_yacol_content", GetBase64Decode(result.getIntro()));
					go(CommandID.map.get("Goto_Discount_dis_merchant_details_yacol"), request, false);
				}
			});
			
			
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
						Toast.makeText(Discount_02bus_MainDetail_YacolActivity.this,
								"您还没有登录，不能关注该商家，请您先登录！", Toast.LENGTH_LONG)
								.show();
						Request request = new Request();//TO_USER_REGISTER
						go(CommandID.map.get("USER_LOGIN"), request, false);
					}
				}
			});
		}
	}

	/**
	 * 对字符串进行BASE64解码
	 * @param str
	 * @return
	 */
	private String GetBase64Decode(String str) {
		String string = "" ;
		byte[] deStr = Base64.decode(str, Base64.DEFAULT) ;
		try {
			string = new String(deStr, "GBK") ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return string ;
	}

	@Override
	public void onError(Response response) {
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
