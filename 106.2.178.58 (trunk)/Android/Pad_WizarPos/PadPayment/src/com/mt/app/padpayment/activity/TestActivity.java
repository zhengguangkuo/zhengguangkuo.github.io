package com.mt.app.padpayment.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import android.os.Bundle;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.global.Globals;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.BaseCommandID;

public class TestActivity extends DemoSmartActivity{
	private static Logger log = Logger.getLogger(TestActivity.class);
	@Override
	public List getDataListById(String id){
		if(id.equals("SPINNERID")){
			List<String> list=new ArrayList<String>();
			list.add("招商银行");
			list.add("工商银行");
			list.add("建设银行");
			list.add("交通银行");
			list.add("中国人民银行");
			return list;
		}
		if(id.equals("LISTVIEWID")){
			List<String> list=new ArrayList<String>();
			list.add("免费");
			list.add("免费再送100元");
			list.add("9折");
			list.add("8折");
			list.add("7折");
			list.add("6折");
			list.add("5折");
			list.add("4折");
			list.add("3折");
			list.add("2折");
			list.add("1折");
			return list;
		}
		return null;
	}
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
			//setContentView("REUSE_FIRST.SCREEN");
		setContentView("REUSE_FIRST.SCREEN");
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
		if(commandName.equals("TEST")){
			Request request=new Request();
			request.setActivityID(1);
			return request;
		}
		return null;
	}

}
