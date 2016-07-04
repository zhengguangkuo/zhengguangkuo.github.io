package com.mt.app.padpayment.activity;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.sys.util.ExitApplication;
import com.mt.android.view.common.CommandID;
import com.mt.app.padpayment.common.SysManager;
/**
 * 广告界面
 * @author Administrator
 *
 */
public class AdFlashActivity extends DemoSmartActivity{
	private static Logger log = Logger.getLogger(AdFlashActivity.class);
	public static boolean isLoginDisplay = false;
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
			setContentView("AD.SCREEN");
			Controller.session.clear();
			
			Request request = new Request();
			if(!isLoginDisplay){
				go(CommandID.map.get("FirstLogin"), request, false);
			}
	}
	
	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Request getRequestByCommandName(String commandName) {
		if(commandName.equals("FLASH_OVER")){
			Request request=new Request();
			return request;
		}
		return null;
	}
	
	private long mExitTime;
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
		try{

		     if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
		     {
		             
				     if((System.currentTimeMillis()-mExitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
				     {
				      Toast.makeText(getApplicationContext(), "再按一次退出程序",Toast.LENGTH_SHORT).show();                                
				    	 mExitTime = System.currentTimeMillis();
				     }
				     else
				     {
				    	 ((Controller)getApplication()).close();
				    	 
				    	 stopService(SysManager.intentservice);
				    	 
				    	 ExitApplication.getInstance().exit();
				    	 
				    	 /*android.os.Process.killProcess(android.os.Process.myPid());  
				    	 ActivityManager activityMgr= (ActivityManager) getSystemService(ACTIVITY_SERVICE );  
				         activityMgr.killBackgroundProcesses(getPackageName()); */
				     }
				             
				     return true;
		     }
		     return super.onKeyDown(keyCode, event);

		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

}
