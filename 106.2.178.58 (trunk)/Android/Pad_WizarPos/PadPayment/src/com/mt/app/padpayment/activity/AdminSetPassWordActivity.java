package com.mt.app.padpayment.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.AdminReqBean;

/**
 * ���������޸Ľ���
 * @author lzw
 *
 */
public class AdminSetPassWordActivity extends DemoSmartActivity{
	
	private EditText oldPassword;
	private EditText newPassword;
	private EditText eqnewPassword;
	
	
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
			setContentView("SETPASSWORD.SCREEN");
			oldPassword=(EditText)findViewById("oldpassword");
			newPassword=(EditText)findViewById("newpassword");
			eqnewPassword=(EditText)findViewById("eqnewpassword");
//			te.getText().toString();
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
		if(commandName.equals("TO_AdminSetPassWordRuActivity")){
			if(!MsgTools.checkEdit(oldPassword, this, "ԭ���벻��Ϊ��")){
				return null;
			}
			if(!MsgTools.checkEdit(newPassword, this, "�����벻��Ϊ��")){
				return null;
			}
			if(!MsgTools.checkEdit(eqnewPassword, this, "ȷ�����벻��Ϊ��")){
				return null;
			}
			Request request=new Request();
			AdminReqBean ar=new AdminReqBean();
			ar.setPassword(oldPassword.getText().toString());
			ar.setNewpassword(newPassword.getText().toString());
			ar.setEqnewPassword(eqnewPassword.getText().toString());
			
			request.setData(ar);
			return request;
		}
		return new Request();
	}

}
