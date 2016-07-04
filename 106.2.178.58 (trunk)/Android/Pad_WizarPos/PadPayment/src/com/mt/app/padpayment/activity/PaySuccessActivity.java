package com.mt.app.padpayment.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.requestbean.ConsumeReqBean;
/**
 *   优惠券扣除成功界面
 * @author Administrator
 *
 */
public class PaySuccessActivity extends DemoSmartActivity {
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("PAYSUCCESS.SCREEN");
		TextView tv = (TextView) findViewById("coupontext");
		
		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		if (bundle != null) {
			ConsumeReqBean reqBean = (ConsumeReqBean) bundle.getSerializable("ConsumeReqBean");
			
			String text = "优惠券扣除成功，抵扣金额为" + reqBean.getCouponsSum() + "元，剩余" + reqBean.getRealSum() + "请在POS机刷卡或支付现金";
			tv.setText(text);
		}
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public Request getRequestByCommandName(String commandIDName) {
		Request request = new Request();
		return request;

	}

}
