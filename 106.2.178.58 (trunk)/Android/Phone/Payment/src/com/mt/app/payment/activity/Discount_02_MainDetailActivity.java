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
	// ����uri
	private Uri uri;
	// ����
	private String coordenite;
	// ����
	private double lat;
	// γ��
	private double lon;
	private String merchantName;
	private String imageName, mid;
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
		setContentView(R.layout.discount_02_maindetail);

		OrderView refreshableView = (OrderView) findViewById(R.id.refreshview_dis_details);
		
		final View viewStay = findViewById(R.id.theviewstay_dis_details);
		btnReceiveTop = (Button)viewStay.findViewById(R.id.discountDisDetails_btnReceive);

		refreshableView.setStayView(findViewById(R.id.theview_dis_details),
				(ScrollView) findViewById(R.id.scrollview_dis_details),
				new StayViewListener() {
					@Override
					public void onStayViewShow() {
						// ������������ʱ��ظ���ʾ
						viewStay.setVisibility(View.VISIBLE);

					}

					@Override
					public void onStayViewGone() {
						// �������������ز���
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
				
				
				if(contentHeight > height){//webview�����¼�Ϊtrue
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

		// �����ذ�ť���ü����¼�
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Discount_02_MainDetailActivity.this.finish();
			}
		});

		String id = getIntent().getStringExtra("id");
		String merchId = getIntent().getStringExtra("merchId") ;
		// ������
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

			// �����Ż�ȯ��������ϵ�ͼƬ
			imageName = result.getRows().get(0).getPic_path();

			new ImageTool().setImagePath(imageP, imageName, caches,
					imagehandler, R.drawable.xiang);
			
			//���û����
			/**ÿ�����������**/
			String dayLimit = String.valueOf(result.getRows().get(0).getDay_limit());
			/**���췢������**/
			String dayCount = String.valueOf(result.getRows().get(0).getDay_cnt());  
			/**ÿ�������������**/
			String personLimit = String.valueOf(result.getRows().get(0).getPersonal_limit());
			/**ʹ�ø��Ż݄�ʱ������ѽ��**/
			String lowAmount = String.valueOf(result.getRows().get(0).getLowAmtCredits());
			/**һ���������ʹ���Ż݄�����**/
			String maxPieces = String.valueOf(result.getRows().get(0).getMax_pieces());  
			
			StringBuilder sb = new StringBuilder();
			if(dayLimit != null && !(dayLimit.equals("null"))){
				if(dayLimit.equals("-1")){
					sb.append("ÿ���������������");
				}else{
					sb.append("ÿ�����������Ϊ" + dayLimit + "��");
				}
			}
			if(dayCount != null && !(dayCount.equals("null"))){
				sb.append("��" + "\n" + "���췢������Ϊ" + dayCount + "��");
			}
			if(personLimit != null && !(personLimit.equals("null"))){
				sb.append("��" + "\n" + "ÿ�����������" + personLimit + "�� ");
			}
			if(lowAmount != null && !(lowAmount.equals("null"))){
				sb.append("��" + "\n" +  "ʹ�ø��Ż݄�ʱ��������ѽ��Ϊ" + lowAmount + "Ԫ");
			}
			if(maxPieces != null && !(maxPieces.equals("null"))){
				sb.append("��" + "\n" + "һ�����������ʹ���Ż݄�" + maxPieces + "��");
			}
			
			if(sb != null && !(sb.toString().equals(""))){
				tvRules.setText(sb.toString());
			}else{
				tvRules.setText("���޻����");
			}
			
			// ������Ч�ڣ����ʼ����-���ֹ���ڣ�
			String Start_date =result.getRows().get(0).getStart_date().substring(0,4)+"."+
					result.getRows().get(0).getStart_date().substring(4,6)+"."+
					result.getRows().get(0).getStart_date().substring(6, 8);
			String End_date=result.getRows().get(0).getEnd_date().substring(0,4)+"."+
					result.getRows().get(0).getEnd_date().substring(4,6)+"."+
		            result.getRows().get(0).getEnd_date().substring(6, 8);
			tvTime.setText(Start_date+ "-" + End_date);
			// ���û����
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
				// �����̼ҵ�ַ
				tvAdd.setText(m[0].getMerchant().getAddress());
				// ������ϵ��ʽ
				tvTele.setText(m[0].getMerchant().getTelephone());
				// �����̼�����
				coordenite = (m[0].getMerchant().getCoordinate());
				// �̼�id
				mid = m[0].getMerchant().getId();

				if (coordenite != null) {
					lat = Double.valueOf(coordenite.split(",")[0]);
					lon = Double.valueOf(coordenite.split(",")[1]);
				}
				// �����̼�����
				merchantName = (m[0].getMerchant().getCname());
			}*/
			
			/*Merchant chant = result.getRows().get(0).getMerchant() ;*/
			// �����̼ҵ�ַ
			tvAdd.setText(result.getRows().get(0).getAddress());
			// ������ϵ��ʽ
			tvTele.setText(result.getRows().get(0).getTelephone());
			// �����̼�����
			coordenite = (result.getRows().get(0).getCoordinate()) ;
			// �̼�id
			mid = result.getRows().get(0).getMerch_id() ;
			if (coordenite != null) {
				lat = Double.valueOf(coordenite.split(",")[0]);
				lon = Double.valueOf(coordenite.split(",")[1]);
			}
			// �����̼�����
			merchantName = (result.getRows().get(0).getMerch_name()) ;
			
			// ����绰���ü����¼�
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
			// ����ʾ��ͼ���ü����¼�
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
			//���鿴�̼����������ӵ���¼�
			tvMerDetails.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Request request = new Request();
					Controller.session.put("dis_dis_merchantDetails", mid);
					go(CommandID.map.get("Goto_Discount_dis_merchant_details"), request, false);
				}
			});
			// ��ע���̼Ҽ����¼�
			imageSee.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (Controller.session.get("user") != null) { // �Ѿ���¼��
						// ������
						Request request = new Request();
						SeeMerchReqBean bean = new SeeMerchReqBean();
						// �̼�ID
						bean.setMerchIds(mid);
						bean.setHandleType("1");
						request.setData(bean);
						go(CommandID.map.get("SeeMerchant"), request, true);
					} else {
						Toast.makeText(Discount_02_MainDetailActivity.this,
								"����û�е�¼�����ܹ�ע���̼ң������ȵ�¼��", Toast.LENGTH_LONG)
								.show();
						Request request = new Request();// TO_USER_REGISTER
						go(CommandID.map.get("USER_LOGIN"), request, false);
					}
				}
			});
			// �����Ż�ȯ
			btnReceive.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (Controller.session.get("user") != null) { // ˵���Ѿ���½�ˣ���ô���������Ż�ȯ��
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
								"����û�е�¼���������ø��Ż�ȯ�������ȵ�¼��", Toast.LENGTH_LONG)
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
					if (Controller.session.get("user") != null) { // ˵���Ѿ���½�ˣ���ô���������Ż�ȯ��
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
								"����û�е�¼���������ø��Ż�ȯ�������ȵ�¼��", Toast.LENGTH_LONG)
								.show();
						Request request = new Request();// TO_USER_REGISTER
						go(CommandID.map.get("USER_LOGIN"), request, false);
					}
				}
			});
		} else if (response.getBussinessType() != null
				&& response.getBussinessType().equals("discount_dis_receive")) {
			Toast.makeText(this, "���óɹ���", Toast.LENGTH_LONG).show();
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
