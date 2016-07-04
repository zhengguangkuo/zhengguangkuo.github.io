package com.mt.app.padpayment.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.message.iso.util.StrUtil;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.ConsumeReqBean;
import com.wizarpos.apidemo.activity.PinPadDriver;

/**
 * 输入绑定应用密码界面
 * 
 * @author Administrator
 * 
 */
public class AppbindPassWordActivity extends DemoSmartActivity {
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				mima = StrUtil.ByteToHexString( (byte[])msg.obj , "");
				
				if(mima != null && !(mima.equals(""))){
					Request request = new Request();
					Bundle bundle = getIntent().getBundleExtra("bundleInfo");
					if (bundle != null) {
						ConsumeReqBean reqBean = (ConsumeReqBean) bundle
								.getSerializable("ConsumeReqBean");
						reqBean.setAppPwd(mima);
						request.setData(reqBean);
						hasWaitView() ;
						go(CommandID.map.get("APPPAYSUCCESS"), request, false);
					}
				}
				break;
			}
		};
	};
	
	private String mima;
	
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView("APPBINDPWD.SCREEN");
		
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

	@Override
	public Request getRequestByCommandName(String commandIDName) {

		Request request = new Request();
		return request;
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub

	}

}
