package com.mt.app.padpayment.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.RegisterReqBean;
import com.mt.app.padpayment.tools.CardThread;

/**
 * 

 * @Description:用户注册

 * @author:dw

 * @time:2014-7-18 下午2:55:52
 */
public class RegisterActivity  extends DemoSmartActivity{

	private EditText CardNumber;
	private static final int MSG_SUCCESS = 0;// 成功的标识
	private static final int MSG_FAILURE = 1;// 失败的标识
	private static final int MSG_READ = 2;// 读取到的数据
	private static final int MSG_finish = 3;// 线程结束

	private static final String Flag_mk1 = "mk1";
	private static final String Flag_apdu = "apdu";

	private String flags = "";
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("REGISTER.SCREEN");
		
		CardNumber = (EditText) findViewById("cardNo");
		
		//mThread = new Thread(runnable);
		if (!CardThread.isStart) {
			new CardThread().start();
			CardThread.isStart = true;
		}
		
		CardThread.isRead = true;
		CardThread.mHandler = mHandler;
		
		Button button = (Button)findViewById("getCode");
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(RegisterActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public Request getRequestByCommandName(String commandIDName) {
		if(commandIDName.equals("REGISTER_RESULT")){
			
     		
     		EditText phone = (EditText)findViewById("phone");
     		EditText code = (EditText)findViewById("code");
     		EditText cardNo = (EditText)findViewById("cardNo");
     		
     		if(!MsgTools.checkEdit(phone, this, "手机号不能为空")){
    			return null;
    		}
     		if(!MsgTools.checkEdit(code, this, "验证码不能为空")){
    			return null;
    		}
     		if(!MsgTools.checkEdit(cardNo, this, "卡号不能为空")){
    			return null;
    		}
     		if(phone.getText().length() != 11) {
     			MsgTools.toast(this, "手机号格式错误", "s");
     			return null;
     		}
     		if(code.getText().length() != 6) {
     			MsgTools.toast(this, "验证码格式错误", "s");
     			return null;
     		}
     		Request request = new Request();
			RegisterReqBean bean = new RegisterReqBean();
     		bean.setMobile(phone.getText().toString());
     		bean.setCode(code.getText().toString());
     		bean.setBaseCardNo(cardNo.getText().toString());
			request.setData(bean);
			return request;
		}
		return new Request();
	}
	@Override
	public void onReadCard(String cardnumber) {
		// TODO Auto-generated method stub
		super.onReadCard(cardnumber);
		
//		 Random r = new Random();
//		CardNumber.setText(cardnumber + r.nextInt(10));
		CardNumber.setText(cardnumber);
	}
	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		
	}
	
	private Thread mThread;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {// 此方法在ui线程运行

			switch (msg.what) {
			case MSG_SUCCESS:
				//CardNumber.setText("" + msg.obj);
				break;

			case MSG_READ:
				if (msg.obj != null) {
					String card = (String) msg.obj;
					if (card.length() > 30) {
						card = card.substring(14,26);
						CardNumber.setText(card);
					}
				}
				break;

			case MSG_finish:
				if (mThread != null) {
					try {
						// mThread.stop();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
//				mk1.setEnabled(true);
//				apdu.setEnabled(true);
				break;

			case MSG_FAILURE:
//				text.setText("jni调用结果:失败!");
				break;
			}
		}
	};


	private void startThread(String type) {
		

		flags = type;
		if (mThread != null) {
			mThread.start();
		}
	}
}