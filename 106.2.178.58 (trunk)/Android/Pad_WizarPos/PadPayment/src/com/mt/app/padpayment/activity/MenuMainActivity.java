package com.mt.app.padpayment.activity;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.frame.smart.config.DrawComponent;
import com.mt.android.global.Globals;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.android.view.common.BaseCommandID;

/**
 * 主菜单界面
 * 
 * @author Administrator
 * 
 */
public class MenuMainActivity extends DemoSmartActivity {
	private static Logger log = Logger.getLogger(MenuMainActivity.class);

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("MENU_MAIN.SCREEN");
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
	if(id != null && id.equalsIgnoreCase("DISCOUNT_APP")){
				Controller.session.put("succForward", ActivityID.map.get("ACTIVITY_ID_CouponsInfoActivity"));
				/*如果刷卡失败，跳转其他 activity*/
				// Controller.session.put("erroForward", "ACTIVITY_ID_PayMentAppActivity");
			}
	}

	@Override
	public Request getRequestByCommandName(String commandName) {
		Request request=new Request();
		return request;
	}
}
