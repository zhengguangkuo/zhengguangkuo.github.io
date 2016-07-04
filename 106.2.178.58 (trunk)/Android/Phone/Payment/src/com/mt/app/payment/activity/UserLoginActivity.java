package com.mt.app.payment.activity;

import org.apache.log4j.Logger;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.android.view.form.BaseFormImpl;
import com.mt.app.payment.requestbean.LoginReqBean;
import com.mt.app.payment.requestbean.PackageSelectReqBean;
import com.mt.app.payment.tools.EncoderMD5Tool;

public class UserLoginActivity extends BaseActivity {
	protected static final String TAG_lOG = UserLoginActivity.class.getSimpleName() ;
	private static Logger log = Logger.getLogger(UserLoginActivity.class);
	private Button regBut, btn_elePay_back, logBut;
	private TextView forgorTv;

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView(R.layout.user_login);
		
		TextView editText1 = (TextView) findViewById(R.id.editText1) ;
		if("true".equals(getIntent().getStringExtra("isShow"))) {
			editText1.setVisibility(View.GONE) ;
		}
		
		regBut = (Button) findViewById(R.id.to_register);
		logBut = (Button) findViewById(R.id.loginButton);
		btn_elePay_back = (Button) findViewById(R.id.btn_elePay_back);
		forgorTv = (TextView) findViewById(R.id.text_Check);
		setListener();//设置监听事件
	    Bundle bundle = getIntent().getBundleExtra("bundleInfo");
	    if(bundle!=null&&bundle.getString("return").equals("ok")){
				Toast.makeText(this, "注册成功", 3000).show();
				}
	}

	@Override
	public void addBottom() {

	}

	@Override
	public void onSuccess(Response response) {
		try 
		{		
	       ResponseBean responsebean= (ResponseBean)response.getData();
	        if(responsebean.getRespcode().equals("06")){
				Request request = new Request();
				PackageSelectReqBean city = new PackageSelectReqBean();
				city.setCity(Controller.session.get("AREA_CODE_LEVEL_1").toString());
				city.setRows(10);
				city.setPage(1);
				request.setData(city);
//				go(CommandID.map.get("GuidePACKAGESELECT"), request, false);
				go(CommandID.map.get("SKIPBASECARDCOMMAND"), request, false);

		        }
	        new Handler().postDelayed(new Runnable()
            {
                    public void run()
                    {
                           
                   finish();
                    }
            }, 1000);
			if ((ResponseBean) response.getData() != null) {
//				showToast(UserLoginActivity.this,
//						((ResponseBean) response.getData()).getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onError(Response response) {
		try {

			if ((ResponseBean) response.getData() != null) {
				
				showToast(UserLoginActivity.this,
						((ResponseBean) response.getData()).getMessage());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setListener() {
		logBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LoginReqBean reqBean = new LoginReqBean();
				new BaseFormImpl().form2Bean(reqBean,
						findViewById(R.id.linearLayout2)); //将数据装入bean

				String msg = reqBean.verify();// 调用lBean的校验类  

				if (msg.equals("")) {
					// 对密码进行加密
					reqBean.setJ_password(EncoderMD5Tool.MD5(reqBean.getJ_password()+"{"+reqBean.getJ_username()+"}")) ;					
					Log.i(TAG_lOG, reqBean.getJ_username()+"/"+reqBean.getJ_password()) ;
					Request request = new Request(reqBean);
					go(CommandID.map.get("UserDoLogin"), request, true);   //发送请求
				} else {
					showToast(UserLoginActivity.this, msg);
				}
			}

		});
		btn_elePay_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		regBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Request request = new Request();
				go(CommandID.map.get("USER_REGISTER"), request, false);
				finish();
			}
		});
		forgorTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Request request = new Request();
				go(CommandID.map.get("USER_FORGOTPASS"), request, false);
			}
		});
	}
}
