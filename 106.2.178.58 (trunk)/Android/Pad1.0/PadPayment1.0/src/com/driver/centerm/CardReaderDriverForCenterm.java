package com.driver.centerm;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.centerm.mid.imp.socketimp.DeviceFactory;
import com.centerm.mid.inf.RFIDDevInf;
import com.mt.app.padpayment.driver.CardReaderDriver;

public class CardReaderDriverForCenterm extends CardReaderDriver {
	
	public static final String TAG_LOG = CardReaderDriverForCenterm.class.getSimpleName() ;
	private RFIDDevInf mRf ;
	private Handler mHandler ;
	private Boolean mFlag = false ;
	private ReadCarThread mReadCarThread ;

	
	@Override
	public boolean openDevice() {
		try {
			mRf = DeviceFactory.getRFIDDev() ;			// 初始化设备
			mRf.open() ;									// 打开设备
			return true ;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean startRead(Handler handler) {
		this.mHandler = handler ;
		mFlag = true ;
		mReadCarThread = new ReadCarThread() ;
		mReadCarThread.start() ;
		return false;
	}
	
	
	

	@Override
	public boolean stopRead() {
		mFlag = false ;
		mReadCarThread.interrupt() ;
		return true ;
	}

	@Override
	public boolean closeDevice() {
		try {
			mRf.halt() ;
			mRf.close() ;
			return true ;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void reinit() {
		try {
			byte[] response = mRf.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void readCard() {
		while(mFlag){
			Message msg = new Message();
			msg.what = 0 ;
			Bundle data = new Bundle();
			try {
				boolean flag =true; 
				while(flag) {
					
					byte st = mRf.status();
					if((st&0xff) ==1){
						flag = false;
					}
//					Log.d("1", "状态=  "+(st&0xff));
				}
				mRf.reset();
				byte[] b1 = new byte[] { (byte) 0x00, (byte) 0xa4, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x10, (byte) 0x01 };
				byte[] b2 = new byte[] { (byte) 0x00, (byte) 0xa4, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x04 };
				byte[] b3 = new byte[] { (byte) 0x00, (byte) 0xb0, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
				byte[] v1 = mRf.send(b1);
				byte[] v2 = mRf.send(b2);
				byte[] v3 = mRf.send(b3);
				Log.i(TAG_LOG, "send()....") ;
				String strDisplay  = new String() ;
				for(int i=0; i<v3.length; i++) {
					strDisplay += String.format("%02X", v3[i]);		
				}
				Log.i(TAG_LOG, "获取到的卡号是----------->" + strDisplay.substring(0, 16)) ;
				msg.obj = strDisplay.substring(0, 16) ;
				msg.setData(data);
				mHandler.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					mRf.halt();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				try {
					mRf.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(1000);
				mRf = DeviceFactory.getRFIDDev() ;
				mRf.open() ;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	class ReadCarThread extends Thread {
		@Override
		public void run() {
			readCard() ;
		}
	}
	
	
	/*class ReadCarThread extends Thread {
		@Override
		public void run() {
			try {
				while(mFlag) {
					byte status = mRf.status();

					Message msg = new Message();
					msg.what = 0 ;
					Bundle data = new Bundle();

					if (status == 0) {
						msg.obj = "请将磁卡贴近设备.." ;
					} else if (status == 1) {
						// 00 a4 00 00 02 10 01
						byte[] b1 = new byte[] { (byte) 0x00, (byte) 0xa4, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x10, (byte) 0x01 };
						// 00 a4 00 00 02 00 04
						byte[] b2 = new byte[] { (byte) 0x00, (byte) 0xa4, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x04 };
						// 00 b0 00 00 00
						byte[] b3 = new byte[] { (byte) 0x00, (byte) 0xb0, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
						// byte[] b3 = new byte[] { (byte) 0x00, (byte) 0xB0, (byte) 0x84, (byte) 0x00, (byte) 0x00 };// 读取卡号命令
						byte[] v1 = mRf.send(b1);
						byte[] v2 = mRf.send(b2);
						byte[] v3 = mRf.send(b3);
//						data.putByteArray("value1", v1);
//						data.putByteArray("value2", v2);
//						data.putByteArray("value3", v3);
						String strDisplay  = new String() ;
						for(int i=0; i<v3.length; i++) {
							strDisplay += String.format("%02X", v3[i]);		
						}
						Log.i(TAG_LOG, "获取到的卡号是----------->" + strDisplay.substring(0, 16)) ;
						msg.obj = strDisplay.substring(0, 16) ;
					}

					data.putByte("status", status);
					msg.setData(data);
					mHandler.sendMessage(msg);
				}
				Thread.sleep(10000) ;
			} catch (Exception e) {
				e.printStackTrace() ;
			}
		}
	}*/
	
	
	

}
