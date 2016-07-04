package com.mt.app.padpayment.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.requestbean.ConsumeReqBean;
/**
 *   �Ż�ȯ�۳��ɹ�����
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
			
			String text = "�Ż�ȯ�۳��ɹ����ֿ۽��Ϊ" + reqBean.getCouponsSum() + "Ԫ��ʣ��" + reqBean.getRealSum() + "����POS��ˢ����֧���ֽ�";
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
