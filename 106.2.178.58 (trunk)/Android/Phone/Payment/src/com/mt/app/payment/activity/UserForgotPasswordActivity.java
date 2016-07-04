package com.mt.app.payment.activity;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mt.android.R;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.requestbean.ForgotPwdReqBean;
import com.mt.app.payment.requestbean.UserRegisterReqBean;

public class UserForgotPasswordActivity extends BaseActivity {
	private static Logger log = Logger.getLogger(UserForgotPasswordActivity.class);
	private Button btnBack , btnSure;
	private EditText edit ; 

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		
			setContentView(R.layout.user_forgot_password);
			edit = (EditText)findViewById(R.id.editText3);
			btnBack = (Button) findViewById(R.id.btn_forgetPass_back);
			btnBack.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					UserForgotPasswordActivity.this.finish();
				}
			});
			btnSure = (Button) findViewById(R.id.loginButton);
			btnSure.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(edit.getText() != null && !(edit.getText().toString().trim().equals(""))){
						Request request =new Request();
						ForgotPwdReqBean user = new ForgotPwdReqBean();
						user.setUserId(edit.getText().toString());
						request.setData(user);
						go(CommandID.map.get("UserDoForgetPwd"), request, true);
						
					}else{
						Toast.makeText(UserForgotPasswordActivity.this, "用户名不能为空！", Toast.LENGTH_LONG).show();
					}
				}
			});
	}

	@Override
	public void addBottom(){
		
	}
	@Override
	public void onSuccess(Response response)
	{
		try{
			   
			if ((ResponseBean) response.getData() != null) {
			    showToast(UserForgotPasswordActivity.this, "找回密码链接已发送至您的邮箱："+((ResponseBean) response.getData()).getMessage());
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onError(Response response)
	{
		try{
			  
			if ((ResponseBean) response.getData() != null) {
				//Toast.makeText(this, ((ResponseBean) response.getData()).getMessage(), 9000).show();
			    showToast(UserForgotPasswordActivity.this, ((ResponseBean) response.getData()).getMessage());

			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
