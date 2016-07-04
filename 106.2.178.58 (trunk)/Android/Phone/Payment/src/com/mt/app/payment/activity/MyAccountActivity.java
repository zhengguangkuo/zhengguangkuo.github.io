package com.mt.app.payment.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mt.android.R;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.common.RegCodeInput;
import com.mt.app.payment.requestbean.CheckRegCodeBean;
import com.mt.app.payment.requestbean.MyAccountReqBean;
import com.mt.app.payment.requestbean.ValidTextReqBean;
import com.mt.app.payment.responsebean.UserInfoResBean;
import com.mt.app.payment.responsebean.UserRegRespBean;

public class MyAccountActivity extends BaseActivity{
	private Button onoff;
	private ImageButton pwdbtn,phonenumbtn,emailbtn;
	private TextView username,pwd,phonenum,email,title;
	private String mobilenum,email1;
	private RegCodeInput regcode;
	private String funcName ;
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView(R.layout.myaccount_layout);
		onoff=(Button)findViewById(R.id.onoff);
		username=(TextView)findViewById(R.id.username_1);
		pwd=(TextView)findViewById(R.id.pwd_1);
		phonenum=(TextView)findViewById(R.id.phonenumber_1);
		email=(TextView)findViewById(R.id.email_1);
		pwdbtn=(ImageButton)findViewById(R.id.pwd_1Btn);
		phonenumbtn=(ImageButton)findViewById(R.id.phonenumber_1Btn);
		emailbtn=(ImageButton)findViewById(R.id.email_1Btn);
		title=(TextView)findViewById(R.id.titlewelcome);
		title.setText("我的账户");
		onoff.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			   finish();	
			}
		});
		pwdbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Request request = new Request();
				//验证码
				if(Controller.session.get("regcode") == null){//如果没有输入验证码,则返回
					funcName = "MODIFY_PASSWORD" ;
					regcode = new RegCodeInput(MyAccountActivity.this);
					regcode.showDialog();
					return ;
				}
				MyAccountReqBean myaccount = new MyAccountReqBean();
				myaccount.setType("0");//修改密码
				myaccount.setMobile(mobilenum);
				myaccount.setEmail(email1);
				myaccount.setUsername(username.getText().toString().trim()) ;
				request.setData(myaccount);
				go(CommandID.map.get("ResetMyaccount"), request, false);
				// TODO Auto-generated method stub
				
			}
		});
		phonenumbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*Request request = new Request();
				//验证码
				if(Controller.session.get("regcode") == null){//如果没有输入验证码,则返回
					regcode = new RegCodeInput(MyAccountActivity.this);
					regcode.showDialog();
					return ;
				}
				//验证码
				MyAccountReqBean myaccount = new MyAccountReqBean();
				myaccount.setType("1");//修改电话号码
				myaccount.setMobile(mobilenum);
				myaccount.setEmail(email1);
				request.setData(myaccount);
				go(CommandID.map.get("ResetMyaccount"), request, true);*/
				
				go(CommandID.map.get("ResetMyaccountOfPhone"), new Request(), false) ;
				
			}
		});
		emailbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Request request = new Request();
				//验证码
				if(Controller.session.get("regcode") == null){//如果没有输入验证码,则返回
					funcName = "MODIFY_EMAIL" ;
					regcode = new RegCodeInput(MyAccountActivity.this);
					regcode.showDialog();
					return ;
				}
				MyAccountReqBean myaccount = new MyAccountReqBean();
				myaccount.setType("2");//修改邮箱
				myaccount.setMobile(mobilenum);
				myaccount.setEmail(email1);
				request.setData(myaccount);
				go(CommandID.map.get("ResetMyaccount"), request, false);
				// TODO Auto-generated method stub
				
			}
		});
		Request request = new Request();
		go(CommandID.map.get("MYACCOUNTQUERY"), request, true);
	}
	/*验证码*/
    public void checkRegInfo(String vregCode){//核对验证码
    	Request request = new Request();
    	CheckRegCodeBean regcode = new CheckRegCodeBean();
    	regcode.setValidateCode(vregCode);
    	request.setData(regcode);
    	
		go(CommandID.map.get("RegCodeVerfi"), request, true);
	}
	public void getRegInfo(){
		ValidTextReqBean reqBean = new ValidTextReqBean();
		reqBean.setFuncName(funcName) ;
		Request request = new Request(reqBean);
		go(CommandID.map.get("RegCodeGet"), request, true);
	}
	/*验证码*/
	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		try {

			/*验证码*/
			if(regcode != null && regcode.isRegResponse(response)){
				return;
			}
			/*验证码*/
			
			if ((ResponseBean) response.getData() != null) {
				Toast.makeText(this, ((ResponseBean) response.getData()).getMessage(), Toast.LENGTH_SHORT).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
		//验证码
		if(regcode != null && regcode.isRegResponse(response)){
			return;
		}
		//验证码
		UserInfoResBean account =(UserInfoResBean)response.getData();
	
		try{
		username.setText(account.getRows().get(0).getUser_id());
		mobilenum=account.getRows().get(0).getMobile();
		email1=account.getRows().get(0).getEmail();
		pwd.setText("***********");
		phonenum.setText(mobilenum);
		email.setText(email1);
	   }catch(Exception e){
		   e.printStackTrace();
	   }
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Request request = new Request();
		go(CommandID.map.get("MYACCOUNTQUERY"), request, true);
	}

}
