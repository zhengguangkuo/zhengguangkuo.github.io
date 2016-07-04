package com.mt.app.padpayment.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.padpayment.requestbean.AdminReqBean;

/**
 * 主管密码操作结果页面
 * @author lzw
 *
 */
public class AdminPassWordRuAllActivity extends DemoSmartActivity{

	private TextView resultMessageAdminPassWord;
	private String AdminPassWordRu=Controller.session.get("AdminPassWordRu").toString();
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
			setContentView("ADMINPASSWORDRU.SCREEN");
			resultMessageAdminPassWord=(TextView)findViewById("RESULT_MESSAGE_ADMINPASSWORD");
			resultMessageAdminPassWord.setText(AdminPassWordRu);
			
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
		if(commandName.equals("")){
			Request request=new Request();
			AdminReqBean ar=new AdminReqBean();
			request.setData(ar);
			return request;
		}
		return new Request();
	}
}
