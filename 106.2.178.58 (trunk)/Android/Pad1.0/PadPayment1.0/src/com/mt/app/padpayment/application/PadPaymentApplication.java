package com.mt.app.padpayment.application;

import java.util.Map;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.mt.android.global.Globals;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.activity.SplashActivity;
import com.mt.android.view.common.ActivityID;
import com.mt.android.view.common.CommandID;
import com.mt.android.view.common.Initializer;
import com.mt.app.padpayment.common.SysManager;
import com.mt.app.padpayment.driver.CardReaderDriver;
import com.mt.app.padpayment.driver.PinPadDriver;
import com.mt.app.padpayment.driver.PrinterDriver;
import com.mt.app.padpayment.tools.LoadConfigUtil;

public class PadPaymentApplication extends Controller /*implements Thread.UncaughtExceptionHandler*/ {
	
	private PinPadDriver mPinPadDriver ;
	private CardReaderDriver mCardReaderDriver ;
	private PrinterDriver mPrinterDriver ;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				currentActivity.onReadCard(msg.obj.toString());
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onCreate() {
		super.onCreate();
		
		Controller.session.clear();
		mContext = getApplicationContext() ;
		
		Map<String, String> driverPath = LoadConfigUtil.LoadDriverPath(this) ;
		String pinpad = driverPath.get("PinPadDriver") ;
		String cardReader = driverPath.get("CardReaderDriver") ;
		String printer = driverPath.get("PrinterDriver") ;
		String layout = driverPath.get("Layout") ;
		
		Controller.session.put("PinPadDriver", pinpad) ;
		Controller.session.put("CardReaderDriver", cardReader) ;
		Controller.session.put("PrinterDriver", printer) ;
		Controller.session.put("Layout", layout) ;
		
		try {
			mPinPadDriver = (PinPadDriver) Class.forName(pinpad).newInstance() ;
			mPinPadDriver.pinpadOpen() ;
			mCardReaderDriver = (CardReaderDriver) Class.forName(cardReader).newInstance() ;
			mCardReaderDriver.openDevice() ;
			mCardReaderDriver.startRead(handler) ;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// Command初始化
		Globals.setMap("ActivityID", new ActivityID());
		Globals.setMap("CommandID", new CommandID());
		Globals.setMap("Initializer", new Initializer());
		new SysManager(this).start();
		/*Thread.setDefaultUncaughtExceptionHandler(this);*/
	}

	/*public void uncaughtException(Thread thread, Throwable ex) {
		Intent intent = new Intent(this, SplashActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent m_restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10, m_restartIntent);
		System.exit(0);
	}*/
	
	public void close(){
		mCardReaderDriver.stopRead();
		mCardReaderDriver.closeDevice();
		mPinPadDriver.pinpadClose();
	}
	
	
	 /** 
     * 全局的上下文. 
     */  
    private static Context mContext ; 
    
    public static Context getContext(){  
        return mContext;  
    } 
}
