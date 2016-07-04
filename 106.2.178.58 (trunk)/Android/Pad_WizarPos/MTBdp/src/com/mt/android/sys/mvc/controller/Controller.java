package com.mt.android.sys.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.mt.android.db.DbHelper;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.command.CommandExecutor;
import com.mt.android.sys.mvc.common.Request;
import com.wizarpos.apidemo.activity.PinPadDriver;
import com.wizarpos.mt.CardReaderHandle;

public class Controller extends Application {
	private static Logger log = Logger.getLogger(Controller.class);
	private static Controller theInstance;
	public static boolean signin = false;
	public static final int ACTIVITY_ID_DO_NOTHING = 1;
	public static final int ACTIVITY_ID_UPDATE_SAME = 0;
	public static final int ACTIVITY_ID_BASE = 1000;
	private static int currentId = ACTIVITY_ID_BASE;
	public static BaseActivity currentActivity;
	public static Map<String, Integer> appmap = new HashMap<String, Integer>();
	public static Map<String,Object> session=new HashMap<String,Object>();
	public static String isCanExecute = "true";
	public static DbHelper appdbhelper = null;
	public static final HashMap<Integer, Class<? extends BaseActivity>> registeredActivities = new HashMap<Integer, Class<? extends BaseActivity>>();

	
	CardReaderHandle cardHandle = new CardReaderHandle();
	PinPadDriver driver = new PinPadDriver(); 
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
	
	public void close(){
		cardHandle.stopRead();
		cardHandle.closeDevice();
		driver.pinpadClose();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		driver.pinpadOpen();
		cardHandle.openDevice();
		cardHandle.startRead(handler);
		
		theInstance = this;

		registeredActivities.clear();
		// 1 to 10 are reserved. or better, use enums?
		/*
		 * registeredActivities.put(11, LoginActivity.class);
		 * registeredActivities.put(87945, HomeActivity.class);
		 */
		Controller.session.put("position", 0);
		Controller.session.put("0", 0);
		Controller.session.put("1", 1); 
		
		
		
		
		
		
		Controller.session.put("2", 2);
		Controller.session.put("3", 3);
		CommandExecutor.getInstance().ensureInitialized();
	}
 
	public void onActivityCreated(BaseActivity activity) {
		currentActivity = activity;
	}

	public void go(int commandID, Request request, boolean record) {
		Log.i("gocmd" ,"go with cmdid=" + commandID + ", record: " + record
				   + ", request: " + request);

		Log.i("gocmd", "Enqueue-ing command");
		CommandExecutor.getInstance().enqueueCommand(commandID, request);
		Log.i("gocmd" , "Enqueued command");
	}

	public void registerActivity(int id, Class<? extends BaseActivity> clz) {
		log.debug("registerActivity:  " + clz.getName());
		registeredActivities.put(id, clz);
	}

	public void unregisterActivity(int id) {
		registeredActivities.remove(id);
	}

	public static Controller getInstance() {
		return theInstance;
	}

	// Ϊÿ��Activity����һ��ActivityId
	public static int IDistributer() {

		synchronized (Controller.class) {
			return Controller.currentId++;
		}

	}
}
