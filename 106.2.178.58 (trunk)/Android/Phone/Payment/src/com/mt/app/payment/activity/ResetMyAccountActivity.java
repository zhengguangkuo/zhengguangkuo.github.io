package com.mt.app.payment.activity;

import java.util.regex.Pattern;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mt.android.R;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.requestbean.UserResetAccount;
import com.mt.app.payment.tools.EncoderMD5Tool;

public class ResetMyAccountActivity extends BaseActivity{
	private Button Myaccountsubmit,exitbtn;
	private TextView titlewelcome;
	private EditText mobilenum,email,opwd,npwd, npwd2;
	private TextView npwdTv1, npwdTv2,opwdtv1;
	private String type = "";
	private String username ;
	private LinearLayout ll_pwd, ll_phone, ll_email ;
	
@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
        setContentView(R.layout.resetmyaccount_layout);
        Bundle bundle = getIntent().getBundleExtra("bundleInfo");
        mobilenum=(EditText)findViewById(R.id.phonenumber_2);
        mobilenum.setInputType(InputType.TYPE_CLASS_NUMBER);
        email=(EditText)findViewById(R.id.email_2);
        opwd=(EditText)findViewById(R.id.Opwd);
        npwd=(EditText)findViewById(R.id.Npwd);
        npwd2=(EditText)findViewById(R.id.Npwd2);
        npwdTv1=(TextView)findViewById(R.id.xinmima);
        npwdTv2=(TextView)findViewById(R.id.xinmima2);
        opwdtv1=(TextView)findViewById(R.id.yuanmima);
        Myaccountsubmit=(Button)findViewById(R.id.Myaccountsubmit);
        exitbtn=(Button)findViewById(R.id.onoff);
        titlewelcome=(TextView)findViewById(R.id.titlewelcome);
        ll_pwd = (LinearLayout) findViewById(R.id.ll_pwd) ;
        ll_phone = (LinearLayout) findViewById(R.id.ll_phone) ;
        ll_email = (LinearLayout) findViewById(R.id.ll_email) ;
        
        mobilenum.setText(bundle.getString("mobilenum"));
        username = bundle.getString("username") ;
        email.setText(bundle.getString("email"));
        opwd.setText("******");
        titlewelcome.setText("修改账户");
        type = bundle.getString("type").toString();
        if(!bundle.getString("type").equalsIgnoreCase("0")){//只要不是修改密码
        	npwd.setVisibility(View.GONE);
        	npwd2.setVisibility(View.GONE);
        	npwdTv1.setVisibility(View.GONE);
        	npwdTv2.setVisibility(View.GONE);
        }
        
        if(bundle.getString("type").equalsIgnoreCase("0")){//修改密码
        	mobilenum.setEnabled(false);
        	email.setEnabled(false);
        	opwd.setText("");
        	ll_email.setVisibility(View.GONE) ;
        	ll_phone.setVisibility(View.GONE) ;
        }else if(bundle.getString("type").equalsIgnoreCase("1")){//修改手机号
        	opwdtv1.setText("密码");
        	opwd.setEnabled(false);
        	email.setEnabled(false);
        }else{//修改邮箱
        	titlewelcome.setText("修改邮箱");
        	opwdtv1.setText("密码");
        	mobilenum.setEnabled(false);
        	opwd.setEnabled(false);
        	ll_pwd.setVisibility(View.GONE) ;
        	ll_phone.setVisibility(View.GONE) ;
        }
        
        Myaccountsubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Request request = new Request();
				UserResetAccount resetAccount = new UserResetAccount();
				Object sopwd = opwd.getText();
				Object snpwd = npwd.getText();
				Object snpwd2 = npwd2.getText();
				Object semail = email.getText();
				Object phone = mobilenum.getText();
				
				if(type.equalsIgnoreCase("0")){
					if(!Verify(sopwd)){
						Toast.makeText(ResetMyAccountActivity.this, "信息填写不完整", Toast.LENGTH_SHORT).show();
						return;
					}
					if(String.valueOf(sopwd).length() < 6){
						Toast.makeText(ResetMyAccountActivity.this, "密码长度为6-20", Toast.LENGTH_SHORT).show();
						return;
					}
					if(!Verify(snpwd)){
						Toast.makeText(ResetMyAccountActivity.this, "信息填写不完整", Toast.LENGTH_SHORT).show();
						return;
					}
					if(String.valueOf(snpwd).length() < 6){
						Toast.makeText(ResetMyAccountActivity.this, "密码长度为6-20", Toast.LENGTH_SHORT).show();
						return;
					}
					if(!Verify(snpwd2)){
						Toast.makeText(ResetMyAccountActivity.this, "信息填写不完整", Toast.LENGTH_SHORT).show();
						return;
					}
					if(!snpwd2.toString().equalsIgnoreCase(snpwd.toString())){
						Toast.makeText(ResetMyAccountActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
						return;
				    	}
					}
				 if(type.equalsIgnoreCase("1")){
						if(!Verify(phone)){
							Toast.makeText(ResetMyAccountActivity.this, "信息填写不完整", Toast.LENGTH_SHORT).show();
							return;
						}
						String mobilenumber = mobilenum.getText().toString();
						if(mobilenumber.length() != 11){
							Toast.makeText(ResetMyAccountActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
							return;
						}
					}
				 if(type.equalsIgnoreCase("2")){
					if(!Verify(semail)){
						Toast.makeText(ResetMyAccountActivity.this, "信息填写不完整", Toast.LENGTH_SHORT).show();
						return;
					}
					/*if(!(Pattern.compile(
							"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
							).matcher(String.valueOf(semail)).matches())){
						Toast.makeText(ResetMyAccountActivity.this, "请输入正确的邮箱", Toast.LENGTH_SHORT).show();
						return;
					}*/
				}
				if(type.equalsIgnoreCase("0")){
					resetAccount.setOriginalpwd(EncoderMD5Tool.MD5(sopwd.toString()+"{"+username+"}"));
					resetAccount.setNewpwd(EncoderMD5Tool.MD5(snpwd.toString()+"{"+username+"}"));
				}else if(type.equalsIgnoreCase("1")){
					resetAccount.setMobile(mobilenum.getText().toString());
				}else if(type.equalsIgnoreCase("2")){
					resetAccount.setEmail(semail.toString());
				}
				
				request.setData(resetAccount);
				
				
				go(CommandID.map.get("ResetMyaccountSubmit"), request, true);
			}
			
			private boolean Verify(Object obj){
                if(obj == null || obj.toString().equalsIgnoreCase("")){
					return false;
				}
                
                return true;
			}
		});
        exitbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
}
@Override
public void onError(Response response) {
	// TODO Auto-generated method stub
	
}
@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
	ResponseBean resp = (ResponseBean)response.getData();
	
	if(resp.getRespcode().equalsIgnoreCase("0")){
		Toast.makeText(ResetMyAccountActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
		finish() ;
	}else{
		Toast.makeText(ResetMyAccountActivity.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
	}
 }
}
