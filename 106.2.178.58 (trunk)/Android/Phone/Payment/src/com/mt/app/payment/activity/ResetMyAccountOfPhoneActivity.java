package com.mt.app.payment.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mt.android.R;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.requestbean.ChangePhoneBean;
import com.mt.app.payment.requestbean.ValidTextReqBean;

/**
 * 我的账户 -- 修改手机号码
 * @author hl
 */
public class ResetMyAccountOfPhoneActivity extends BaseActivity {

	private EditText et_newphone, et_captcha ;
	private Button bt_getcaptcha, bt_ok ;
	
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		super.onCreateContent(savedInstanceState) ;
        setContentView(R.layout.changephonenumber) ;
        
        ((TextView)findViewById(R.id.titlewelcome)).setText("修改手机号码") ;
        ((Button)findViewById(R.id.onoff)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish() ;
			}
		}) ;
        
        et_newphone = (EditText)findViewById(R.id.et_newphone) ;
        et_captcha = (EditText)findViewById(R.id.et_captcha) ;
        bt_getcaptcha = (Button)findViewById(R.id.bt_getcaptcha) ;
        bt_ok = (Button)findViewById(R.id.bt_ok) ;
        
        bt_getcaptcha.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String newPhone = et_newphone.getText().toString() ;
				if(TextUtils.isEmpty(newPhone)) {
					Toast.makeText(ResetMyAccountOfPhoneActivity.this, "请输入新手机号码", 0).show() ;
					return ;
				} else {
					if(newPhone.length() != 11){
						Toast.makeText(ResetMyAccountOfPhoneActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
						return;
					} else {
						Toast.makeText(ResetMyAccountOfPhoneActivity.this, "请求已发送...", Toast.LENGTH_LONG).show();
						ValidTextReqBean reqBean = new ValidTextReqBean();
						Request request = new Request(reqBean);
						reqBean.setMobile(newPhone);
						reqBean.setFuncName("MODIFY_MOBILE") ;
						go(CommandID.map.get("UserVerification"), request, false); // 发送请求
					}
				}
			}
        }) ;
        
        bt_ok.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		String captcha = et_captcha.getText().toString() ;
        		String newPhone = et_newphone.getText().toString() ;
        		if(TextUtils.isEmpty(newPhone)) {
        			Toast.makeText(ResetMyAccountOfPhoneActivity.this, "请输入新手机号码", 0).show() ;
        			return ;
        		} else if(newPhone.length() != 11){
					Toast.makeText(ResetMyAccountOfPhoneActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
					return;
				}
        		if(TextUtils.isEmpty(captcha)) {
        			Toast.makeText(ResetMyAccountOfPhoneActivity.this, "请输入验证码", 0).show() ;
        			return ;
        		} 
        		
				ChangePhoneBean cPhoneBean = new ChangePhoneBean() ;
				cPhoneBean.setMobile(newPhone) ;
				cPhoneBean.setValid_code(captcha) ;
				
				Request request = new Request(cPhoneBean) ;
				
				go(CommandID.map.get("ChangePhone"), request, false) ;
        	}
        }) ;
	}
	
	
	@Override
	public void onSuccess(Response response) {
		ResponseBean reBean = (ResponseBean) response.getData() ;
		if("0".equals(reBean.getRespcode())) {
			Toast.makeText(ResetMyAccountOfPhoneActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
			finish() ;
		} else {
			Toast.makeText(ResetMyAccountOfPhoneActivity.this, reBean.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onError(Response response) {
		try {
			if ((ResponseBean) response.getData() != null) {
				Toast.makeText(this, ((ResponseBean) response.getData()).getMessage(), Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
