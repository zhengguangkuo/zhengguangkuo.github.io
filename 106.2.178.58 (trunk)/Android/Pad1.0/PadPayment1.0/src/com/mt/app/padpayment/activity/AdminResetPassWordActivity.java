package com.mt.app.padpayment.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.AdminReqBean;

/**
 * 柜员号录入
 * @author lzw
 *
 */
public class AdminResetPassWordActivity extends DemoSmartActivity{

	
	private EditText userID;
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
			setContentView("RESETPASSWORD.SCREEN");
			userID=(EditText)findViewById("userID");
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
		if(commandName.equals("TO_AdminResetPassWordRuActivity")){
			if(!MsgTools.checkEdit(userID, this, "柜员号不能为空")){
				return null;
			}
			Request request=new Request();
			AdminReqBean ar=new AdminReqBean();
			ar.setUserId(userID.getText().toString());
			request.setData(ar);
			return request;
		}
		return new Request();
	}
}
