package com.mt.app.payment.activity;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.miteno.coupon.entity.Act;
import com.miteno.coupon.entity.CouponJournal;
import com.miteno.coupon.entity.MerchAct;
import com.miteno.mpay.entity.Merchant;
import com.mt.android.R;
import com.mt.android.protocol.http.client.ImageHttpClient;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.requestbean.ObtainDisDetailReqBean;
import com.mt.app.payment.responsebean.DiscountDetailResult;
import com.mt.app.payment.responsebean.ImageResultBean;
import com.mt.app.payment.responsebean.ObtainDisResult;
import com.mt.app.payment.tools.ImageBufferFree;
import com.mt.app.payment.tools.ImageTool;

public class EleCard_BusDetailsActivity extends BaseActivity {
	private Button btnBack;
	private ImageView imageP  , imagePhone;
	private TextView tvTime, tvAdd, tvTele , tvMerDetails;
	private WebView webview;
	private LinearLayout tvMap;
	// ����
	private Uri uri;
	// ����
	private String coordenite;
	//�̼�ID
	private String merID;
	// ����
	private double lat;
	// γ��
	private double lon;
	private String merchantName;
	private String imageName;
	Map<String, SoftReference<Bitmap>> caches = new HashMap<String, SoftReference<Bitmap>>();
	private Handler imagehandler = new Handler() { // �첽ˢ��ͼƬ
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
		String id = getIntent().getStringExtra("id");
		String merchid = getIntent().getStringExtra("merchId") ;

		setContentView(R.layout.elecard_bus_details);
		final ScrollView sc = (ScrollView) findViewById(R.id.esv);
		btnBack = (Button) findViewById(R.id.btn_eleBusDetails_back);
		imageP = (ImageView) findViewById(R.id.eleBusDetails_imageView);
		imagePhone  = (ImageView) findViewById(R.id.image_phone_ele_bus);
		// tvDetails = (TextView) findViewById(R.id.text_details_eleBusDetails);
		tvTime = (TextView) findViewById(R.id.text_time_eleBusDetails);
		tvAdd = (TextView) findViewById(R.id.text_address_eleBusDetails);
		tvMap = (LinearLayout) findViewById(R.id.tv_map_eleBusDetails);
		tvTele = (TextView) findViewById(R.id.text_tele_eleBusDetails);
		tvMerDetails = (TextView) findViewById(R.id.text_merchant_details_eleBusDetails);
		webview = (WebView) findViewById(R.id.eDetails_webview);
		webview.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int contentHeight = webview.getContentHeight();
				int height = webview.getHeight();
				if (contentHeight > height)
					sc.requestDisallowInterceptTouchEvent(true);
				else
					sc.requestDisallowInterceptTouchEvent(false);

				return false;
			}
		});
		// �����ذ�ť���ü����¼�
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				EleCard_BusDetailsActivity.this.finish();
			}
		});
		// ����绰���ü����¼�

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
		// ������
		Request request = new Request();
		ObtainDisDetailReqBean bean = new ObtainDisDetailReqBean();
		bean.setActId(id) ;
		bean.setMerchId(merchid) ;
		request.setData(bean);
		go(CommandID.map.get("EleDiscount_details"), request, true);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Controller.session.put("detailsFinished_elebus", "ok");
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
		ImageResultBean bean = (ImageResultBean) response.getData();

		DiscountDetailResult result = (DiscountDetailResult) bean.getRespBean();

		// �����Ż�ȯ��������ϵ�ͼƬ
		imageName = result.getRows().get(0).getPic_path();

		new ImageTool().setImagePath(imageP, imageName, caches, imagehandler,
				R.drawable.xiang);
		// ������Ч�ڣ����ʼ����-���ֹ���ڣ�
		
		String Start_date =result.getRows().get(0).getStart_date().substring(0,4)+"."+
				result.getRows().get(0).getStart_date().substring(4,6)+"."+
				result.getRows().get(0).getStart_date().substring(6, 8);
		String End_date=result.getRows().get(0).getEnd_date().substring(0,4)+"."+
				result.getRows().get(0).getEnd_date().substring(4,6)+"."+
				result.getRows().get(0).getEnd_date().substring(6, 8);
		tvTime.setText(Start_date+ "-"+ End_date);
		// ���û����
		// tvDetails.setText(result.getRows().get(0).getAct().getContent());
		String data = result.getRows().get(0).getContent();
		webview.getSettings().setDefaultTextEncodingName("utf-8");
		// webview.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
		// webview.loadData(data, "text/html", "utf-8");
		webview.loadData(data, "text/html; charset=UTF-8", null);
		/*Set<MerchAct> mm = result.getRows().get(0).getAct().getMerchActs();
		MerchAct[] mmm = new MerchAct[] {};
		MerchAct[] m = mm.toArray(mmm);
		if (m.length != 0) {
			//����̼�ID
			merID = m[0].getMerchant().getId();
			// �����̼ҵ�ַ
			tvAdd.setText(m[0].getMerchant().getAddress());
			// ������ϵ��ʽ
			tvTele.setText(m[0].getMerchant().getTelephone());
			// �����̼�����
			coordenite = (m[0].getMerchant().getCoordinate());
			lat = Double.valueOf(coordenite.split(",")[0]);
			lon = Double.valueOf(coordenite.split(",")[1]);
			//�����̼�����
			merchantName = m[0].getMerchant().getCname();
		}*/
		
		
		/*Merchant merchant = result.getRows().get(0).getAct().getMerchant() ;*/
		// ����̼�ID
		merID = result.getRows().get(0).getMerch_id() ;
		// �����̼ҵ�ַ
		tvAdd.setText(result.getRows().get(0).getAddress());
		// ������ϵ��ʽ
		tvTele.setText(result.getRows().get(0).getTelephone());
		// �����̼�����
		coordenite = (result.getRows().get(0).getCoordinate());
		lat = Double.valueOf(coordenite.split(",")[0]);
		lon = Double.valueOf(coordenite.split(",")[1]);
		//�����̼�����
		merchantName = result.getRows().get(0).getMerch_name() ;
					

		// ����ʾ��ͼ���ü����¼�
		tvMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(EleCard_BusDetailsActivity.this,
						DisDetailMapActivity.class);
				intent.putExtra("lat", lat);
				intent.putExtra("lon", lon);
				intent.putExtra("merchantName", merchantName);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
		
		tvMerDetails.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Request request = new Request();
				Controller.session.put("dis_dis_merchantDetails", merID);
				go(CommandID.map.get("Goto_Discount_dis_merchant_details"), request, false);
			}
		});
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		new ImageTool().imageFree(caches);
	}
}
