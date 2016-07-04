package com.mt.app.padpayment.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import android.os.Bundle;
import android.widget.ListView;
import com.mt.android.db.DbHandle;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.adapter.DrawListViewsAdapter;
import com.mt.app.padpayment.requestbean.PaymentQueryReqBean;
public class QueryAdminActivity extends DemoSmartActivity{
	private String[] a;
	DbHandle dbhandle = new DbHandle();
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView("QUERYADMIN.SCREEN");
		
		ListView lv = (ListView) findViewById("queryadminlist");
   
		List<Map<String, String>> list =dbhandle.rawQuery("select * from TBL_ADMIN ", null);
		List<String> ls = new ArrayList<String>();
       if(list.size()!=0){        
		for (int j = 0; j < list.size(); j++) {
			   String zw = list.get(j).get("LIMITS");
			   if(zw.equals("1")){
				   zw="柜员";
			   }else{
				   zw="主管";
			   }
			a = new String[] { "柜员号：" + list.get(j).get("USER_ID")+"   "+"柜员姓名："+list.get(j).get("USER_NAME")
					+ "   "+"职位："+ zw};
			for (int i = 0; i < a.length; i++) {
				ls.add(a[i]);
			}
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

}
