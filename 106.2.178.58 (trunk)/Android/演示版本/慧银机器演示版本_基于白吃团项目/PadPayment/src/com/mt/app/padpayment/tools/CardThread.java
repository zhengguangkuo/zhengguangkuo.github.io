package com.mt.app.padpayment.tools;

import android.os.Handler;
import android.util.Log;

import com.example.jni_test.jni.JniClient;

public class CardThread extends Thread {

	public static boolean isStart;
	public static boolean isRead;
	public static Handler mHandler;
	
	
	@Override
	public void run() {

		// String value = JniClient.AutoRun();
		// int iLen = value.length();

		while (true) {
			stop:
			while (isRead) {
				try {
					String value = JniClient.picc_card_connect(10);
					// mHandler.obtainMessage(MSG_clear, "ѭ������=" +
					// i+"�Σ�����ֵ="+value+":�����ɹ�="+j+"��").sendToTarget();
					// �������ӣ�ͬʱ���uid,aty,sak�м���"_"����
					if (value.equals("-1")) {
						Log.e("aa", "���Ӳ��ɹ���");
						int status1 = JniClient.picc_card_disconnect();
						Thread.sleep(200);
						int status2 = JniClient.picc_reader_close();
						Thread.sleep(200);
						int kkkkkkkkk = JniClient.picc_reader_open();//
						// ��ʼ��������0Ϊ�ɹ�
						Thread.sleep(200);// ���Ӳ��ɹ�����ͣ�£����ͷ���Դ
						continue;
					}

					// mHandler.obtainMessage(MSG_SUCCESS,
					// "jni���ý����ʽuid_qta_sak��" + value).sendToTarget();

					String[] receviedValue = value.split("_");

					/**
					 * ���һ��sector����ԿΪ�� keya1:='ABCD1234AABB';//60,60
					 * keyb1:='AABB11223344';//60,60 ����sector����ԿΪ��
					 * keya:='A1A2A3A4A5A6';//0,0 keyb:='B1B2B3B4B5B6';//0,0
					 **/

					// ��Ȩ����һ������m1�������ͣ�0��1�������ͣ����ڶ�����������Կ��Ϊ��1-f"���ַ��������������ǻ�õ�uid�����ĸ���������Ȩ������
					int n = JniClient.picc_mifare_auth(0,
							"ABCD1234AABB".toLowerCase(),
							receviedValue[0].toLowerCase(), 60);
					String readData = null;
					if (n != 0) {
						Log.e("aa", "��Ȩ���ɹ�");
					} else {
						
						readData = JniClient.picc_mifare_read(60, "ok");

						if (readData != null && !"".equals(readData)) {
							if (mHandler != null) {								
								mHandler.obtainMessage(2,readData).sendToTarget();
							} 
							isRead = false;
							// �Ͽ�����
							int status1 = JniClient.picc_card_disconnect();
							Thread.sleep(500);
							break stop;
						}
					}

					// �Ͽ�����
					int status1 = JniClient.picc_card_disconnect();
					Thread.sleep(500);

					// mHandler.obtainMessage(MSG_finish,
					// "over").sendToTarget();

				} catch (Exception ex) {
					ex.printStackTrace();
					// Log.i("", ex.getMessage());
					// mHandler.obtainMessage(MSG_FAILURE).sendToTarget();
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
