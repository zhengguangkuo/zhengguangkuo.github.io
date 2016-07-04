package com.mt.app.payment.activity;

import android.os.Bundle;
import android.text.TextUtils;
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

public class DiscountDisMerchantDetailsOfYacolActivity extends BaseActivity {
	
	private Button btnBack;
	private WebView webView;
	private String content ;
	
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		super.onCreateContent(savedInstanceState);
		setContentView(R.layout.discount_dis_merchant_details);
		content = String.valueOf(Controller.session.get("Goto_Discount_dis_merchant_details_yacol_content"));
		if(TextUtils.isEmpty(content) || "null".equals(content)) {
			content = "‘›Œﬁ…Ãº“ΩÈ…‹" ;
		}
		btnBack = (Button) findViewById(R.id.btn_discountDis_merchant_details_back);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DiscountDisMerchantDetailsOfYacolActivity.this.finish();
			}
		});
		webView = (WebView) findViewById(R.id.discountDis_merchant_details_webview);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		webView.getSettings().setDefaultTextEncodingName("utf-8");
		webView.loadData(content, "text/html; charset=UTF-8", null);
	}

	@Override
	public void onSuccess(Response response) {
		
	}

	@Override
	public void onError(Response response) {
		
	}

}
