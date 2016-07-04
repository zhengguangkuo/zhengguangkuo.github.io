package com.mt.app.padpayment.driver;

import android.content.Context;
import android.os.Handler;

public abstract class CardReaderDriver {

	/**
	 * �򿪶������豸
	 */
	public abstract boolean openDevice() ;
	
	
	/**
	 * ��ʼѰ��,�������ˢ��,�첽��ʽ֪ͨhandler
	 */
	public abstract boolean startRead(Handler handler) ;
	
	
	/**
	 * ֹͣ����
	 */
	public abstract boolean stopRead() ;
	
	
	/**
	 * �رն�����
	 */
	public abstract boolean closeDevice() ;
	
	
	/**
	 * 
	 */
	public abstract void reinit() ;
	
}
