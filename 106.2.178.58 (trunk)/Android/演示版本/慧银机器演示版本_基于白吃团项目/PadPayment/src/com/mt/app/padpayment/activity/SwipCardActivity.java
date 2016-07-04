package com.mt.app.padpayment.activity;

import org.apache.log4j.Logger;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jni_test.jni.JniClient;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.ReadCardReqBean;
import com.mt.app.padpayment.tools.CardThread;

/**
 * ˢ���ȴ�����
 * 
 * @author Administrator
 * 
 */
public class SwipCardActivity extends DemoSmartActivity {
	private static Logger log = Logger.getLogger(SwipCardActivity.class);
	private EditText CardNumber;
	private TextView cardError;

	
	private static final int MSG_SUCCESS = 0;// �ɹ��ı�ʶ
	private static final int MSG_FAILURE = 1;// ʧ�ܵı�ʶ
	private static final int MSG_READ = 2;// ��ȡ��������
	private static final int MSG_finish = 3;// �߳̽���

	private static final String Flag_mk1 = "mk1";
	private static final String Flag_apdu = "apdu";

	private String flags = "";
	
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("SWIP_CARD.SCREEN");

		CardNumber = (EditText) findViewById("EDIT_CARD_NUMBER");
		cardError = (TextView) findViewById("card_error");
		//mThread = new Thread(runnable);
		/*if (!CardThread.isStart) {
			new CardThread().start();
			CardThread.isStart = true;
		}
		
		CardThread.isRead = true;
		CardThread.mHandler = mHandler;*/
		//startThread(Flag_mk1);
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
		cardError.setText("���Ŵ���");
		cardError.setTextColor(Color.RED);
	}

	
	/*@Override
	public void onDestroy(){
		super.onDestroy();
		CardThread.isRead = false;
//		if (mThread != null && mThread.isAlive()) {
//			mThread = null;
//		}
//		
	}*/
	
	@Override
	public Request getRequestByCommandName(String commandName) {
		CardThread.isRead = false;
		Request request = new Request();
		if (commandName.equals("SubmitCard")) {
			ReadCardReqBean readcard = new ReadCardReqBean();
			String cardNo = CardNumber.getText() == null ? "" : CardNumber
					.getText().toString();

			if (cardNo.trim().equalsIgnoreCase("")) {
				MsgTools.toast(SwipCardActivity.this, "���Ų���Ϊ��", "l");
				return null;
			}

			if (CardNumber.getText() != null
					&& !(CardNumber.getText().equals(""))) {
				readcard.setCardNum(CardNumber.getText().toString());
			}
			request.setData(readcard);

			if (Controller.session.get("succForward") != null) {
				// ---֧��Ӧ��------------------------------------------------------
				if (Controller.session.get("succForward").equals(
						ActivityID.map.get("ACTIVITY_ID_NoteConsumeActivity"))) {
					// ���ֵȴ�����
					hasWaitView();
				}
				if (Controller.session.get("succForward").equals(
						ActivityID.map
								.get("ACTIVITY_ID_CreditsConsumeActivity"))) {
					hasWaitView();
				}
				if (Controller.session.get("succForward").equals(
						ActivityID.map.get("ACTIVITY_ID_CreditsQueryActivity"))) {
					hasWaitView();
				}

				if (Controller.session.get("succForward").equals(
						ActivityID.map.get("ACTIVITY_ID_CANCELRESULT"))) {
					hasWaitView();
				}
				if (Controller.session.get("succForward").equals(
						ActivityID.map.get("ACTIVITY_ID_InputFlowNumActivity"))) {
					hasWaitView();
				}
				// ֧��Ӧ��-��ѯ��
				if (Controller.session.get("succForward").equals(
						ActivityID.map.get("ACTIVITY_ID_SearchActivity"))) {
					hasWaitView();
				}
				// --------------------------------------------------------------------

				// -----�Ż݄�����-------------
				if (Controller.session.get("succForward").equals(
						ActivityID.map.get("ACTIVITY_ID_CouponsInfoActivity"))) {
					hasWaitView();
				}
				// --------------------------------------------------------------------

			}
		}

		return request;
	}

	
	
	

	/*private Thread mThread;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {// �˷�����ui�߳�����

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
//				text.setText("jni���ý��:ʧ��!");
				break;
			}
		}
	};*/


//	Runnable runnable = new Runnable() {
//
//		@SuppressWarnings("unused")
//		@Override
//		public void run() {// run()���µ��߳�������
//			try {
//				// String value = JniClient.AutoRun();
//				// int iLen = value.length();
//
//				int kkkkkkkkk = JniClient.picc_reader_open();// ��ʼ��������0Ϊ�ɹ�
//				Thread.sleep(150);
//				String value = JniClient.picc_card_connect(10);// �������ӣ�ͬʱ���uid,aty,sak�м���"_"����
//
//				mHandler.obtainMessage(MSG_SUCCESS,value).sendToTarget();
//
//				String[] receviedValue = value.split("_");
//				Thread.sleep(150);
//
//				
//				
//				if (flags.equals(Flag_mk1)) {// ��APP_TAG_MIFARE_1K��
//					// ��Ȩ����һ������m1�������ͣ�0��1�������ͣ����ڶ�����������Կ��Ϊ��1-f"���ַ��������������ǻ�õ�uid�����ĸ���������Ȩ������
//					//���һ��sector����ԿΪ��
//
//			        //keya1:='ABCD1234AABB';
//
//			        //keyb1:='AABB11223344';
//					
//					int n = JniClient.picc_mifare_auth(1, "AABB11223344".toLowerCase(),
//							receviedValue[0], 60);
//					Thread.sleep(100);
//					// д�룬��һ��������д��������Ŀ��ţ��ڶ���������д������ݣ�Ϊʮ��λ��1-f"���ַ�
//					//int writeFlag = JniClient.picc_mifare_write(1,
//							//"12345678912345671234567891234567");
//					//Thread.sleep(1000);
//					// ��������һ�������Ƕ����������Ŀ��ţ��ڶ��������Ƕ��������ݣ�Ϊʮ��λ��1-f"���ַ�
//					String readData = JniClient.picc_mifare_read(60, "ok");
//					Thread.sleep(100);
//					mHandler.obtainMessage(MSG_READ, readData)
//							.sendToTarget();
//				} else if (flags.equals(Flag_apdu)) {// ��apdu��
//					String cpduValue = JniClient.picc_iso_apdu();
//					mHandler.obtainMessage(MSG_READ,
//							cpduValue).sendToTarget();
//				}
//				// �Ͽ�����
//				int status1 = JniClient.picc_card_disconnect();
//				// �ر�
//				int status2 = JniClient.picc_reader_close();
//				mHandler.obtainMessage(MSG_finish, "over").sendToTarget();
//			} catch (Exception ex) {
//				ex.printStackTrace();
//				Log.i("SwipCardActivity", ex.getMessage());
//				mHandler.obtainMessage(MSG_FAILURE).sendToTarget();
//			}
//		}
//	};

//	Runnable runnable = new Runnable() {
//
//		@Override
//		public void run() {}
//	};
	/*private void startThread(String type) {
		

		flags = type;
		if (mThread != null) {
			mThread.start();
		}
	}*/
	
}
