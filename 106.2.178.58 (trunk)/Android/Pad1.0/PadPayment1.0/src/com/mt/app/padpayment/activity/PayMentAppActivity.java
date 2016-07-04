package com.mt.app.padpayment.activity;

import org.apache.log4j.Logger;

import android.os.Bundle;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.android.view.common.BaseCommandID;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
/**
 * ֧��Ӧ�����˵�����
 * @author Administrator
 *
 */
public class PayMentAppActivity extends DemoSmartActivity{
	private static Logger log = Logger.getLogger(PayMentAppActivity.class);
	
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
			setContentView("PAYMENT_MAIN.SCREEN");
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
	public void setActivityIDById(String id){
	if(id != null && id.equalsIgnoreCase("PAY_CONSUM")){  //֧��Ӧ��������ת�Ľ���ID
				Controller.session.put("succForward", ActivityID.map.get("ACTIVITY_ID_NoteConsumeActivity"));
				/*���ˢ��ʧ�ܣ���ת���� activity*/
				// Controller.session.put("erroForward", "ACTIVITY_ID_PayMentAppActivity");
			}
	 if(id != null && id.equalsIgnoreCase("PAY_CANCEL")){  //���ѳ�����ת�Ľ���ID
			Controller.session.put("type", "consume");
			/*���ˢ��ʧ�ܣ���ת���� activity*/
			// Controller.session.put("erroForward", "ACTIVITY_ID_PayMentAppActivity");
		} 
	 if(id != null && id.equalsIgnoreCase("PAY_RETURN_GOODS")){   //�����˻���ת�Ľ���ID
			Controller.session.put("succForward", ActivityID.map.get("ACTIVITY_ID_OriginalDate"));
			Controller.session.put("type", "backGoods");
			/*���ˢ��ʧ�ܣ���ת���� activity*/
			// Controller.session.put("erroForward", "ACTIVITY_ID_PayMentAppActivity");
		} 
	 
	 if(id.equalsIgnoreCase("PAY_OBSERVE")){   //���Ѳ�ѯ��ת�Ľ���ID
			Controller.session.put("succForward", ActivityID.map.get("ACTIVITY_ID_SearchActivity"));
			Controller.session.put("type", "consumeCheck");
			/*���ˢ��ʧ�ܣ���ת���� activity*/
			// Controller.session.put("erroForward", "ACTIVITY_ID_PayMentAppActivity");
		}
	}

	@Override
	public Request getRequestByCommandName(String commandName) {
		Request request=new Request();
		return request;
	}
}
