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
					// mHandler.obtainMessage(MSG_clear, "循环次数=" +
					// i+"次：返回值="+value+":读卡成功="+j+"次").sendToTarget();
					// 建立连接，同时获得uid,aty,sak中间用"_"隔开
					if (value.equals("-1")) {
						Log.e("aa", "连接不成功！");
						int status1 = JniClient.picc_card_disconnect();
						Thread.sleep(200);
						int status2 = JniClient.picc_reader_close();
						Thread.sleep(200);
						int kkkkkkkkk = JniClient.picc_reader_open();//
						// 初始化，返回0为成功
						Thread.sleep(200);// 连接不成功得暂停下，以释放资源
						continue;
					}

					// mHandler.obtainMessage(MSG_SUCCESS,
					// "jni调用结果格式uid_qta_sak：" + value).sendToTarget();

					String[] receviedValue = value.split("_");

					/**
					 * 最后一个sector的秘钥为： keya1:='ABCD1234AABB';//60,60
					 * keyb1:='AABB11223344';//60,60 其余sector的秘钥为：
					 * keya:='A1A2A3A4A5A6';//0,0 keyb:='B1B2B3B4B5B6';//0,0
					 **/

					// 授权，第一个参数m1卡的类型（0和1两种类型），第二个参数是密钥，为“1-f"的字符，第三个参数是获得的uid，第四个参数是授权的扇区
					int n = JniClient.picc_mifare_auth(0,
							"ABCD1234AABB".toLowerCase(),
							receviedValue[0].toLowerCase(), 60);
					String readData = null;
					if (n != 0) {
						Log.e("aa", "授权不成功");
					} else {
						
						readData = JniClient.picc_mifare_read(60, "ok");

						if (readData != null && !"".equals(readData)) {
							if (mHandler != null) {								
								mHandler.obtainMessage(2,readData).sendToTarget();
							} 
							isRead = false;
							// 断开连接
							int status1 = JniClient.picc_card_disconnect();
							Thread.sleep(500);
							break stop;
						}
					}

					// 断开连接
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
