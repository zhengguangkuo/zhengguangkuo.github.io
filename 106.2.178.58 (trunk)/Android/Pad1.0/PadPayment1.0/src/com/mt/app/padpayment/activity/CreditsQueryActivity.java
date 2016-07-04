package com.mt.app.padpayment.activity;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.adapter.DrawListViewsAdapter;
import com.mt.app.padpayment.message.iso.trans.PointsQueryBean;
import com.mt.app.padpayment.responsebean.CreditQueryResBean;
import com.mt.app.padpayment.tools.MoneyUtil;

/**
 * 积分查询界面
 * 
 * @author Administrator
 * 
 */
public class CreditsQueryActivity extends DemoSmartActivity {

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("CreditsQuery.SCREEN");
		ListView lv = (ListView) findViewById("CreditsQueryList");

		List<String> list = new ArrayList<String>();

		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		if (bundle != null) {
			ArrayList<CreditQueryResBean> creditList = (ArrayList<CreditQueryResBean>) bundle
					.getSerializable("creditList");
			if (creditList.size()==0) {
				Toast.makeText(this, "您还绑定支付应用！", Toast.LENGTH_SHORT);
			}
			for (int i = 0; i < creditList.size(); i++) {
				list.add(creditList.get(i).getIssCshort().trim()+"   " + "积分为 :"
						+ creditList.get(i).getCredits()+"   可兑换金额为:"+MoneyUtil.getMoney(creditList.get(i).getMoney()));
			}
			DrawListViewsAdapter adapter = new DrawListViewsAdapter(list, this);
			lv.setAdapter(adapter);
		}
	}

	@Override
	public Request getRequestByCommandName(String commandName) {
		if (commandName.equals("QUERY_BACK")) {
			Request request = new Request();
			return request;
		}
		return new Request();
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub

	}

}
