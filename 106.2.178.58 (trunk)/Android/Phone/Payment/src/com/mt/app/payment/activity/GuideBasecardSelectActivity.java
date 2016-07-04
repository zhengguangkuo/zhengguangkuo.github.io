package com.mt.app.payment.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mt.android.R;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.adapter.BaseCardSelectAdapter;
import com.mt.app.payment.responsebean.Card_DataBean;

/**
 * 
 * 
 * @Description:����ѡ�����
 * 
 * @author:dw
 * 
 * @time:2013-9-13 ����3:42:58
 */
public class GuideBasecardSelectActivity extends BaseActivity {
	private ListView basecardselectlist;
	private Button onoff;
	private TextView titlewelcome;
	private ArrayList<Card_DataBean> list;// ��������
	private BaseCardSelectAdapter adapter;

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView(R.layout.basecard_select_layout);
		basecardselectlist = (ListView) findViewById(R.id.basecardselectlist);
		onoff = (Button) findViewById(R.id.onoff); // ���ذ�ť
		titlewelcome = (TextView) findViewById(R.id.titlewelcome);
		titlewelcome.setText("��������ѡ��");
		Bundle bundle = getIntent().getBundleExtra("bundleInfo");

		if (bundle != null) { // ��command��������
			list = (ArrayList<Card_DataBean>) bundle.getSerializable("list");
		}else{
			Toast.makeText(this, "��������ʧ�ܣ��������������", 3000).show();
		}

		onoff.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Request request = new Request();
				request.setData(list);
				go(CommandID.map.get("GuideBaseCardSelectTwo"), request, false);
				Controller.session.put("guideClose", "ok");
				finish();
			}
		});
		adapter = new BaseCardSelectAdapter(this, list);
		basecardselectlist.setAdapter(adapter);
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (adapter != null) {
			adapter.onAdapterDestroy();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Request request = new Request();
		request.setData(list);
		go(CommandID.map.get("GuideBaseCardSelectTwo"), request, false);
		Controller.session.put("guideClose", "ok");
		finish();
		return true;
	}
}
