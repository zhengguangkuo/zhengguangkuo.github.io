package com.mt.app.padpayment.activity;

import android.os.Bundle;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
/**
 *   积分消费主界面
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
		if(id != null && id.equalsIgnoreCase("CreditsConsume")){   //积分消费按钮
			Controller.session.put("succForward", ActivityID.map.get("ACTIVITY_ID_CreditsConsumeActivity"));
			/*如果刷卡失败，跳转其他 activity*/
			// Controller.session.put("erroForward", "ACTIVITY_ID_CreditsMainActivity");
		}
		if(id != null && id.equalsIgnoreCase("CreditsRevocation")){    //积分撤销按钮
			Controller.session.put("type" , "credit");
			/*如果刷卡失败，跳转其他 activity*/
			// Controller.session.put("erroForward", "ACTIVITY_ID_CreditsMainActivity");
		}
		if(id != null && id.equalsIgnoreCase("CreditsQuery")){   //积分查询按钮
			Controller.session.put("succForward", ActivityID.map.get("ACTIVITY_ID_CreditsQueryActivity"));
			/*如果刷卡失败，跳转其他 activity*/
			// Controller.session.put("erroForward", "ACTIVITY_ID_CreditsMainActivity");
		}
	}

}
