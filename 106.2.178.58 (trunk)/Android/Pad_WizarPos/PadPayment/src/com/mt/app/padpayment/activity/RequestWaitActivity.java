package com.mt.app.padpayment.activity;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.global.Globals;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.BaseCommandID;
/**
 * ����ȴ�����
 * @author Administrator
 *
 */
public class RequestWaitActivity extends DemoSmartActivity{
	private static Logger log = Logger.getLogger(RequestWaitActivity.class);
	
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
			setContentView("REQUEST_WAIT.SCREEN");
			//ͨ��go()����������Ӧ ��command�з������ڸý����յ���Ӧ�ɹ�������Ӧ����ת�Ľ���
	
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
		if(commandName.equals("FLASH_OVER")){
			Request request=new Request();
			return request;
		}
		return new Request();
	}

}
