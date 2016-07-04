package com.mt.app.payment.activity;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.R.anim;
import android.R.integer;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mt.android.R;
import com.mt.android.protocol.http.client.ImageHttpClient;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.common.RegCodeInput;
import com.mt.app.payment.requestbean.AppBindUnBindReqBean;
import com.mt.app.payment.requestbean.AppDefaultReqBean;
import com.mt.app.payment.requestbean.CheckRegCodeBean;
import com.mt.app.payment.requestbean.ObtainPayAppDetailReqBean;
import com.mt.app.payment.requestbean.ValidTextReqBean;
import com.mt.app.payment.responsebean.ImageResultBean;
import com.mt.app.payment.responsebean.ObtainAllPayAppResult;
import com.mt.app.payment.responsebean.ObtainAppDetailResult;
import com.mt.app.payment.tools.ImageBufferFree;
import com.mt.app.payment.tools.ImageTool;

public class EleCard_pay_detailsActivity extends BaseActivity {

	private ImageView image;
	private TextView tvType, tvNum, tvDefault ;
	private Button btnUnbind;
	private Button btnBack, btnDefault;
	private String appID; // Ӧ��ID
	private boolean de ;
	private RegCodeInput regcode;
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
						image.setImageResource(R.drawable.bank);
					} else {
						image.setImageBitmap(bitmap);
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
		appID = (String) getIntent().getSerializableExtra("data"); // �鿴�Ѱ󶨵�Ӧ�������ID

		de = (boolean) getIntent().getBooleanExtra("defaultCard", false);
		
		setContentView(R.layout.elecard_paymentcard_details);
		image = (ImageView) findViewById(R.id.image_elepay_Details);
		tvType = (TextView) findViewById(R.id.ele_pay_detail_type);
		tvNum = (TextView) findViewById(R.id.ele_pay_detail_num);
		btnUnbind = (Button) findViewById(R.id.ele_pay_detail_unbind);
		btnBack = (Button) findViewById(R.id.btn_elePayDetails_back);
		btnDefault = (Button) findViewById(R.id.bt_ele_pay_default);
		tvDefault = (TextView) findViewById(R.id.tv_ele_pay_default);
		
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				EleCard_pay_detailsActivity.this.finish();
			}
		});
		
		tvDefault.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(1) ;					
			}
		}) ;
		

		// ������
		Request request = new Request();
		ObtainPayAppDetailReqBean bean = new ObtainPayAppDetailReqBean();
		bean.setAppId(appID);
		request.setData(bean);
		go(CommandID.map.get("ElePayApp_details"), request, true);

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Controller.session.put("detailsFinished_elepay", "ok");
	}

	/* ��֤�� */
	public void checkRegInfo(String vregCode) {// �˶���֤��
		Request request = new Request();
		CheckRegCodeBean regcode = new CheckRegCodeBean();
		regcode.setValidateCode(vregCode);
		request.setData(regcode);

		go(CommandID.map.get("RegCodeVerfi"), request, true);
	}

	public void getRegInfo() {
		ValidTextReqBean reqBean = new ValidTextReqBean();
		reqBean.setFuncName("UNBIND_APP") ;
		Request request = new Request(reqBean);
		go(CommandID.map.get("RegCodeGet"), request, true);
	}

	/* ��֤�� */

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub

		/* ��֤�� */
		if (regcode != null && regcode.isRegResponse(response)) {
			return;
		}
		/* ��֤�� */

		if (response.getBussinessType() != null
				&& response.getBussinessType().equals("details")) { // ��ѯ���鷵�ص���Ӧ
			ImageResultBean bean = (ImageResultBean) response.getData();

			ObtainAppDetailResult result = (ObtainAppDetailResult) bean
					.getRespBean();

			if (bean != null) {
				// ��������ͼƬ
				imageName = result.getRows().get(0).getMpayApp().getPic_path();
				
				new ImageTool().setImagePath(image, imageName, caches, imagehandler, R.drawable.bank);
				
				// ���ÿ�Ƭ����;
				tvType.setText("֧����");
				// ���ÿ���
				String num = result.getRows().get(0).getApp_card_no() ;
				tvNum.setText(replace(num, num.length()-4-4, 4, "*"));
			}

			btnUnbind.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					// ��֤��
					if (Controller.session.get("regcode") == null) {// ���û��������֤��,�򷵻�
						regcode = new RegCodeInput(
								EleCard_pay_detailsActivity.this);
						regcode.showDialog();
						return;
					}
					// ��֤��

					Request request = new Request();
					AppBindUnBindReqBean b = new AppBindUnBindReqBean();
					b.setApp_id(appID);
					b.setAction("unBind");
					request.setData(b);
					go(CommandID.map.get("EleApp_unbind"), request, true);
				}
			});
			
			btnDefault.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(de){
						Toast.makeText(EleCard_pay_detailsActivity.this, "��ǰ���Ѿ���Ĭ�Ͽ��������ظ����ã�", Toast.LENGTH_LONG).show();
					}else{
						Request request = new Request() ;
						AppDefaultReqBean bean = new AppDefaultReqBean() ;
						bean.setAppId(appID) ;
						request.setData(bean) ;
						go(CommandID.map.get("EleApp_Default"), request, true) ;
					}
				}
			}) ;

		} else if (response.getBussinessType() != null
				&& response.getBussinessType().equals("appUnbind")) { // ���Ӧ�÷��ص���Ӧ
			this.finish();
		} else if(response.getBussinessType() != null && "appDefault".equals(response.getBussinessType())) {
			this.finish() ;
		}
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub

		/* ��֤�� */
		if (regcode != null && regcode.isRegResponse(response)) {
			return;
		}
		/* ��֤�� */
		if (response.getBussinessType() != null
				&& response.getBussinessType().equals("appUnbind")) {
			Toast.makeText(this, "���ʧ�ܣ�", Toast.LENGTH_LONG).show();
		} else if (response.getBussinessType() != null
				&& response.getBussinessType().equals("details")) {
			Toast.makeText(this, "�����������������", Toast.LENGTH_LONG).show();
		} else if(response.getBussinessType() != null && response.getBussinessType().equals("appDefault")) {
			ResponseBean bean = (ResponseBean) response.getData() ;
			String str = bean.getMessage() ;
			Toast.makeText(this, TextUtils.isEmpty(str) ? "����Ĭ��֧��Ӧ��ʧ�ܣ�" : str , Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		new ImageTool().imageFree(caches);
	}
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null ;
		switch (id) {
		case 1:
			dialog = new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.btn_star)
			.setTitle("Ĭ�Ͽ�����;")
			.setMessage("����ΪĬ�Ͽ������̼�����ʱ���Ը��ſ���Ϊ��ѡ֧�����������ѣ��Ӷ����������ն���ѡ��֧�����Ĳ��裬�������Ľ���ʱ�䡣").setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss() ;
				}
			}).create() ;
			
			break;

		default:
			break;
		}
		return dialog ;
	}

	/**
	 * ��һ���ַ����е�ĳ��λ�滻��ĳ������ 
	 * @param str Ҫ�滻���ַ���
	 * @param startIndex �Ӷ���λ��ʼ�滻
	 * @param num	�滻����λ
	 * @param value Ҫ�滻�ɵķ��� 
	 * @return
	 * @throws Throwable 
	 */
	private String replace(String str, int startIndex, int num, String value) {
		String s1 = "", s2 = "", s3= "" ;
		try {
			s1 = str.substring(0, startIndex) ;
//			s2 = str.substring(startIndex, startIndex+num).replaceAll("[a-zA-Z]*", value) ;
			for(int i=0; i<num; i++) {
				s2 += "*" ;
			}
			s3 = str.substring(startIndex+num) ;			
		} catch (Exception e) {
					
		}
		return s1+s2+s3 ;
	}
}
