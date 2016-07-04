package com.mt.app.padpayment.activity;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import android.os.Bundle;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
/**
 *  应用选择界面
 * @author Administrator
 *
 */
public class AppselectActivity extends DemoSmartActivity{
	private static Logger log = Logger.getLogger(AppselectActivity.class);
	@Override
	public List getDataListById(String id){
		
		if(id.equals("APPLISTVIEW")){
			List<String> list=new ArrayList<String>();
			list.add("北京银行");
			list.add("工商银行");
			list.add("农商银行");
			list.add("交通银行");
			list.add("建设银行");
			list.add("招商银行");
			return list;
		}
		return null;
	}
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("APPSELECT.SCREEN");
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
		if(commandName.equals("BALANCE")){
			Request request=new Request();
			return request;
		}
		return new Request();
	}
}
