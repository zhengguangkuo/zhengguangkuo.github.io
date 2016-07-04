package com.mt.app.padpayment.activity;

import android.os.Bundle;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
/**
 *   ��������������
 * @author Administrator
 *
 */
public class CreditsMainActivity extends DemoSmartActivity{

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("Credits.SCREEN");
	}
	@Override
	public Request getRequestByCommandName(String commandName) {
		Request request=new Request();
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
	public void setActivityIDById(String id) {
		// TODO Auto-generated method stub
		super.setActivityIDById(id);
		if(id != null && id.equalsIgnoreCase("CreditsConsume")){   //�������Ѱ�ť
			Controller.session.put("succForward", ActivityID.map.get("ACTIVITY_ID_CreditsConsumeActivity"));
			/*���ˢ��ʧ�ܣ���ת���� activity*/
			// Controller.session.put("erroForward", "ACTIVITY_ID_CreditsMainActivity");
		}
		if(id != null && id.equalsIgnoreCase("CreditsRevocation")){    //���ֳ�����ť
			Controller.session.put("type" , "credit");
			/*���ˢ��ʧ�ܣ���ת���� activity*/
			// Controller.session.put("erroForward", "ACTIVITY_ID_CreditsMainActivity");
		}
		if(id != null && id.equalsIgnoreCase("CreditsQuery")){   //���ֲ�ѯ��ť
			Controller.session.put("succForward", ActivityID.map.get("ACTIVITY_ID_CreditsQueryActivity"));
			/*���ˢ��ʧ�ܣ���ת���� activity*/
			// Controller.session.put("erroForward", "ACTIVITY_ID_CreditsMainActivity");
		}
	}

}
