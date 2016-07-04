package com.mt.app.padpayment.activity;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.widget.TextView;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.requestbean.ConsumeReqBean;

/**
 * ����ȷ�Ͻ���
 * 
 * @author Administrator
 * 
 */
public class SureConsumeActivity extends DemoSmartActivity {
	private static Logger log = Logger.getLogger(SureConsumeActivity.class);

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("SURE_CONSUME.SCREEN");

		TextView tvSum = (TextView) findViewById("TEXT_SHOULD_MONEY"); // Ӧ�ս��
		TextView tvCouponsSum = (TextView) findViewById("TEXT_DISCOUNT_MONEY"); // �Ż�ȯ���
		TextView tvVipSum = (TextView) findViewById("TEXT_VIP_MONEY"); // vip���
		TextView tvRealSum = (TextView) findViewById("TEXT_ACTUAL_MONEY"); // ʵ�ս��

		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		if (bundle != null) {
			ConsumeReqBean reqBean = (ConsumeReqBean) bundle
					.getSerializable("ConsumeReqBean");

			if(reqBean.getSum() == null || reqBean.getSum().equalsIgnoreCase("")){
				reqBean.setSum("0");
			}
			
			if(reqBean.getCouponsSum() == null || reqBean.getCouponsSum().equalsIgnoreCase("")){
				reqBean.setCouponsSum("0");
			}
			
			if(reqBean.getVipSum() == null || reqBean.getVipSum().equalsIgnoreCase("")){
				reqBean.setVipSum("0");
			}
			
			if(reqBean.getRealSum() == null || reqBean.getRealSum().equalsIgnoreCase("")){
				reqBean.setRealSum("0");
			}
			
			String tmp = tvSum.getText() + reqBean.getSum() + "Ԫ";
			tvSum.setText(tmp);
			if (reqBean.getCouponsSum().length()>0&&!reqBean.getCouponsSum().contains(".")&&!reqBean.getCouponsSum().equals("0.00")&&!reqBean.getCouponsSum().equals("0.0")) {
				tmp = tvCouponsSum.getText()  + reqBean.getCouponsSum() +".00" + "Ԫ";
			} else {
				tmp = tvCouponsSum.getText()  + reqBean.getCouponsSum() + "Ԫ";
			}
			
			tvCouponsSum.setText(tmp);
			
			if (reqBean.getVipSum().length()>0&&!reqBean.getVipSum().contains(".")&&!reqBean.getVipSum().equals("0.0")&&!reqBean.getVipSum().equals("0.00")) {
				tmp = tvVipSum.getText()  + reqBean.getVipSum() +".00" + "Ԫ";
			} else if(reqBean.getVipSum().equals("0.0")){
				tmp = tvVipSum.getText()  + "0.00" + "Ԫ";
			} else {
				tmp = tvVipSum.getText()  + reqBean.getVipSum() + "Ԫ";
			}
			
			tvVipSum.setText(tmp);
			tmp = tvRealSum.getText()  + reqBean.getRealSum() + "Ԫ";
			tvRealSum.setText(tmp);
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
	public Request getRequestByCommandName(String commandName) {
		if (Controller.session.get("succForward").equals(
				ActivityID.map.get("ACTIVITY_ID_BindAppDiscountActivity"))
				|| Controller.session.get("succForward").equals(
						ActivityID.map.get("ACTIVITY_ID_PAYSUCCESS"))) {
			// ���ֵȴ�����
			hasWaitView();
		}

		Request request = new Request();
		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		ConsumeReqBean reqBean = (ConsumeReqBean) bundle
				.getSerializable("ConsumeReqBean");
		request.setData(reqBean);

		return request;
	}

}
