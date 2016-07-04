package com.mt.app.payment.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.mt.android.R;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.requestbean.DiscountMerchantDetailsReqBean;
import com.mt.app.payment.responsebean.MerchantDetailsResult;

public class DiscountDisMerchantDetailsActivity extends BaseActivity {
	
	private Button btnBack;
	private WebView webView;
	private String merchantID;
	
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView(R.layout.discount_dis_merchant_details);
		
		merchantID = String.valueOf(Controller.session.get("dis_dis_merchantDetails"));
		
		btnBack = (Button) findViewById(R.id.btn_discountDis_merchant_details_back);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DiscountDisMerchantDetailsActivity.this.finish();
			}
		});
		webView = (WebView) findViewById(R.id.discountDis_merchant_details_webview);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Request request = new Request();
		DiscountMerchantDetailsReqBean bean = new DiscountMerchantDetailsReqBean();
		if(merchantID != null && !(merchantID.equals("null"))){
			bean.setMerchId(merchantID);
		}
		request.setData(bean);
		go(CommandID.map.get("Discount_dis_merchant_details"), request, true);
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
		MerchantDetailsResult result = (MerchantDetailsResult)response.getData();
		if(result != null){
			String details = result.getRows().get(0).getDetail();
			if(details != null){
				webView.getSettings().setDefaultTextEncodingName("utf-8");
				webView.loadData(details, "text/html; charset=UTF-8", null);
			}else{
				Toast.makeText(this, "±§Ç¸£¬ÔÝÎÞÊý¾Ý£¡", Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		if(response != null){
			ResponseBean b = (ResponseBean)response.getData();
			if(b != null && b.getMessage() != null){
				Toast.makeText(this, b.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
	}

}
