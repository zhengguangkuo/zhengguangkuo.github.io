package com.mt.app.padpayment.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.mt.android.db.DbHandle;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.frame.smart.config.DrawListViewAdapter;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.padpayment.adapter.DrawListViewsAdapter;

/**
 * 选择要删除的柜员界面
 * 
 * @author lzw
 * 
 */
public class AdminDeleteActivity extends DemoSmartActivity {

	DbHandle dbhandle = new DbHandle();
	private String[] a;

	List<Map<String, String>> listAdmin;
	List<Map<String, String>> listDelete=new ArrayList();
	@Override
	public List getDataListById(String id) {
		List ls = new ArrayList();
		if (id.equals("deleteadminlist")) {
			
			
			listAdmin = dbhandle.rawQuery(
					"select * from TBL_ADMIN where LIMITS = '1'", null);
			
			if (listAdmin.size() != 0) {
				for (int j = 0; j < listAdmin.size(); j++) {
					String zw = listAdmin.get(j).get("LIMITS");
					if (zw.equals("1")) {
						zw = "柜员";
					} else {
						zw = "主管";
					}
					a = new String[] { "柜员号：" + listAdmin.get(j).get("USER_ID") + "   "
							+ "柜员姓名：" + listAdmin.get(j).get("USER_NAME") + "   "
							+ "职位：" + zw };
					for (int i = 0; i < a.length; i++) {
						ls.add(a[i]);
					}
				}
			}
//			listAdmin = dbhandle.rawQuery(
//					"select * from TBL_ADMIN where LIMITS=1", null);
		}
		return ls;
	}

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("DELETEADMIN.SCREEN");
		DrawListViewAdapter.list.clear();
		ListView lv = (ListView) findViewById("deleteadminlist");

//		List<Map<String, String>> list = dbhandle.rawQuery(
//				"select * from TBL_ADMIN ", null);
//		List<String> ls = new ArrayList<String>();
//		if (list.size() != 0) {
//			for (int j = 0; j < list.size(); j++) {
//				String zw = list.get(j).get("LIMITS");
//				if (zw.equals("1")) {
//					zw = "柜员";
//				} else {
//					zw = "主管";
//				}
//				a = new String[] { "柜员号：" + list.get(j).get("USER_ID") + "   "
//						+ "柜员姓名：" + list.get(j).get("USER_NAME") + "   "
//						+ "职位：" + zw };
//				for (int i = 0; i < a.length; i++) {
//					ls.add(a[i]);
//				}
//			}
//		}
//
//		DrawListViewsAdapter adapter = new DrawListViewsAdapter(ls, this);
//		lv.setAdapter(adapter);

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
		if (commandName.equals("TO_DELETE_ADMIN_LIST")) {
			List<Integer> list = DrawListViewAdapter.list;
			if (list != null && list.size() > 0) {
				if (list != null) {
					String str = "";
					for (int i = 0; i < list.size(); i++) 
					{
						str = str + " USER_ID = '" + listAdmin.get(list.get(i)).get("USER_ID") + "' or ";
					}
					str = str.substring(0, str.length()-4);
					Request request = new Request();
					request.setData(str);
					return request;
				}
				
			}
		}
		return new Request();
	}

}
