package com.mt.app.padpayment.activity;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.frame.smart.config.DrawComponent;
import com.mt.android.global.Globals;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.BaseCommandID;
/**
 * ��ʾ�������Ļ����ˢ���������˵����Ľ���
 * @author Administrator
 *
 */
public class GotoMainActivity extends DemoSmartActivity{
	private static Logger log = Logger.getLogger(GotoMainActivity.class);
	private TextView timeText;
	
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("GOTO_MAIN.SCREEN");
		timeText = (TextView)findViewById("TIME_COUNT");
	}
	
	/**
	 *   �����Activity�Ľ����������˼�ʱ����
	 *   ��ͨ��������д�÷�����ʵ�ּ�ʱ����ʱ����߼�����ʾ��ʱ������ĳ�����棩
	 */
	@Override
	public void onTimeOut() {
		// TODO Auto-generated method stub
		super.onTimeOut();
		timeText.setText("ʱ�䳬ʱ��");
		Toast.makeText(this, "ʱ�䳬ʱ......", 8000).show();
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
		if(commandName.equals("TO_MAIN")){
			Request request=new Request();
			return request;
		}
		return new Request();
	}

}
