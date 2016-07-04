package com.mt.app.padpayment.driver;

import android.content.Context;
import android.os.Handler;

public abstract class CardReaderDriver {

	/**
	 * 打开读卡器设备
	 */
	public abstract boolean openDevice() ;
	
	
	/**
	 * 开始寻卡,如果读到刷卡,异步方式通知handler
	 */
	public abstract boolean startRead(Handler handler) ;
	
	
	/**
	 * 停止读卡
	 */
	public abstract boolean stopRead() ;
	
	
	/**
	 * 关闭读卡器
	 */
	public abstract boolean closeDevice() ;
	
	
	/**
	 * 
	 */
	public abstract void reinit() ;
	
}
