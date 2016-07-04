package com.mt.app.padpayment.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.widget.GridView;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.frame.smart.config.DrawGridViewAdapter;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.responsebean.AppListRespBean;

/**
 * ����ѯѡ��Ӧ��activity
 * @author lzw
 *
 */
public class SearchActivity extends DemoSmartActivity{
	private ArrayList cardAppList = null;
	private ArrayList payAppList = null;
	private static Logger log = Logger.getLogger(SearchActivity.class);
	private ArrayList<AppListRespBean> datas = null;
	private GridView gridView = null;

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {

		setContentView("SearchAppSelect.SCREEN");
		gridView = (GridView) findViewById("arrayList");
	}
	
	@Override
	public List getDataListById(String id) {
		List<String[]> list = new ArrayList<String[]>();
		String[] appName = null;
		String[] appIcon = null;
		if (id.equals("APP_SPINNER_SEARCH")) {

			Bundle bundle = getIntent().getBundleExtra("bundleInfo");

			if (bundle != null) {
				payAppList = (ArrayList) bundle
						.getSerializable("CARDBINDAPPLIST");
				if (payAppList != null) {
					appName = new String[payAppList.size()];
					appIcon = new String[payAppList.size()];

					for (int z = 0; z < payAppList.size(); z++) {
						AppListRespBean appBean = (AppListRespBean) (payAppList
								.get(z));
						appName[z] = appBean.getMpayAppName();

						appIcon[z] = appBean.getPic_path();

					}

					list.add(appName);
					list.add(appIcon);
				} else {
					MsgTools.toast(this, "Ӧ���б��ȡʧ�ܣ�������������", "l");
				}
			}
		}
		
		return list;
	}
	
	@Override
	public Request getRequestByCommandName(String commandName) {

		Request request=new Request();
		
		if (commandName.equals("ToAppPassword")){			
			if (Controller.session.get("checked") != null) {
				int pos = Integer.parseInt(Controller.session
						.get("checked").toString());
				AppListRespBean appList = (AppListRespBean) payAppList
						.get(pos);
				Controller.session.put("APP_ID", appList.getId());// Ӧ��id
				
			} else {
				MsgTools.toast(SearchActivity.this, "��ѡ��Ҫ��ѯ��Ӧ��", "l");
				return null;
			}
		}
		
		return request;

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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try{//�ͷ�bitmap
			((DrawGridViewAdapter)gridView.getAdapter()).recycleBitmap();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
