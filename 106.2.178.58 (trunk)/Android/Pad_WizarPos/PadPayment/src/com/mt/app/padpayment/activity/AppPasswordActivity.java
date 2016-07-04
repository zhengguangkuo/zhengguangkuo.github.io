package com.mt.app.padpayment.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.message.iso.util.StrUtil;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.AdminPassReqBean;
import com.mt.app.padpayment.requestbean.ConsumeReqBean;
import com.wizarpos.apidemo.activity.PinPadDriver;
/**
 *   应用密码输入界面
 * @author Administrator
 *
 */
public class AppPasswordActivity extends DemoSmartActivity {
	private EditText etPass;
	private TextView error;
	private Button btnSure ; 
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				mima = StrUtil.ByteToHexString( (byte[])msg.obj , "");
				
				if(mima != null && !(mima.equals(""))){
					Request request = new Request();
					AdminPassReqBean bean = new AdminPassReqBean();
					bean.setPassWord(mima);
					request.setData(bean);
					hasWaitView();
					go(CommandID.map.get("VOUCHERS"), request, false);
				}
				break;
			}
		};
	};
	
	private String mima = " ";

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView("APPPWD.SCREEN");

		etPass = (EditText) findViewById("apppwd");
		error = (TextView) findViewById("PASS_ERROR");
		btnSure = (Button) findViewById("VOUCHERS_SURE_BTN");
		
		if (Controller.session.get("type") != null
				&& (Controller.session.get("type").equals("consume") 
						|| Controller.session.get("type").equals("credit")  
						|| Controller.session.get("type").equals("backGoods")
						)){
			
		}else if(Controller.session.get("type") != null 
				&& Controller.session.get("type").equals("consumeCheck")){
			etPass.setVisibility(View.GONE);
			btnSure.setVisibility(View.GONE);
			
			final PinPadDriver driver = new PinPadDriver();
			new Thread(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					String cardNum = (String)Controller.session.get("CardNum");
					byte[] b = new byte[8];
					if(cardNum != null && !(cardNum.equals(""))){
						driver.PinPadSelectKey(0);
						driver.pinpadCaculatePinblock( cardNum.getBytes() , cardNum.length(), b , 15000);
						Message msg = Message.obtain(handler);
						msg.what = 0 ;
						msg.obj = b ;
						msg.sendToTarget();
					}
				}
			}.start();
		}
		
		
	}

	@Override
	public Request getRequestByCommandName(String commandName) {
		Request request = new Request();
		if (commandName.equals("VOUCHERS")) {
			
			if(etPass != null && !(etPass.getText().toString().equals(""))){
				mima = etPass.getText().toString();
			}else{
				MsgTools.toast(AppPasswordActivity.this, "应用密码不能为空", "l");
				return null;
			}
			
			AdminPassReqBean bean = new AdminPassReqBean();
			bean.setPassWord(mima);
			request.setData(bean);
			hasWaitView();
		}

		return request;
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		error.setText("密码输入有误，请重新输入！");
		error.setTextColor(Color.RED);
	}
}
