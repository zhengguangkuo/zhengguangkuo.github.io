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
			list.add("��������");
			list.add("��������");
			list.add("��������");
			list.add("��ͨ����");
			list.add("�й���������");
			return list;
		}
		if(id.equals("LISTVIEWID")){
			List<String> list=new ArrayList<String>();
			list.add("���");
			list.add("�������100Ԫ");
			list.add("9��");
			list.add("8��");
			list.add("7��");
			list.add("6��");
			list.add("5��");
			list.add("4��");
			list.add("3��");
			list.add("2��");
			list.add("1��");
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
