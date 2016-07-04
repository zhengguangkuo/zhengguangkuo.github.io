package com.mt.app.padpayment.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.adapter.DrawListViewsAdapter;
import com.mt.app.padpayment.common.TypeUtil;
import com.mt.app.padpayment.requestbean.PaymentQueryReqBean;
import com.mt.app.padpayment.tools.MoneyUtil;
import com.mt.app.padpayment.tools.PackUtil;

public class PaymentQueryResultActivity extends DemoSmartActivity {
	PaymentQueryReqBean querybean = new PaymentQueryReqBean();
	private String[] a;

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView("PAYMENTQUERYRESULT.SCREEN");
		ListView lv = (ListView) findViewById("Paymentlist");

		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		querybean = (PaymentQueryReqBean) bundle.getSerializable("list");
		ArrayList<Map<String, String>> list = querybean.getList();
		List<String> ls = new ArrayList<String>();

		for (int j = 0; j < list.size(); j++) {
			if (list.get(j).get("MSG_ID").equals("0200") && list.get(j).get("PROCESS_CODE").equals("020000")) {
				a = new String[] {list.get(j).get("USER_ID") +"        "+list.get(j).get("TRACK2") +"         "+ list.get(j).get("SYS_TRACE_AUDIT_NUM")+"          "+ TypeUtil.getType(list.get(j).get("MSG_ID"), list.get(j).get("PROCESS_CODE"))+"          "+ list.get(j).get("LOCAL_TRANS_DATE_1")+"            "+  MoneyUtil.getMoney(list.get(j).get("TRANS_AMOUNT"))};
			} else {
				a = new String[] {list.get(j).get("USER_ID") +"        "+list.get(j).get("TRACK2") +"         "+ list.get(j).get("SYS_TRACE_AUDIT_NUM")+"          "+ TypeUtil.getType(list.get(j).get("MSG_ID"), list.get(j).get("PROCESS_CODE"))+"          "+ list.get(j).get("LOCAL_TRANS_DATE_1")+"            "+  MoneyUtil.getMoney(list.get(j).get("TRANS_AMOUNT"))};	
			}
			
			for (int i = 0; i < a.length; i++) {
				ls.add(a[i]);
			}
		}

		DrawListViewsAdapter adapter = new DrawListViewsAdapter(ls, this);
		lv.setAdapter(adapter);

	}

	@Override
	public Request getRequestByCommandName(String commandName) {
		// TODO Auto-generated method stub
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

	public List getDataListById(String id) {

		return new ArrayList();
	}
}
