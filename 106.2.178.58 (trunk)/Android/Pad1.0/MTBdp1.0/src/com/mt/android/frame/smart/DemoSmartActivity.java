package com.mt.android.frame.smart;

import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.mt.android.frame.smart.config.DrawComponent;
import com.mt.android.frame.smart.config.RegisterId;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
/**
 * 
 * pad应用所有activity需要继承的父类
 * 
 *
 */
public abstract class DemoSmartActivity extends BaseActivity {  //该类被继承，子类自己写逻辑
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:  //计时还没结束
				DrawComponent.tvTime.setText("计时开始：" + DrawComponent.second + "秒");
				DrawComponent.second -- ;
				break;
			case 1:  //计时结束
				DrawComponent.timer.cancel();
				DrawComponent.runFlag = false;
				onTimeOut();
				break;
			}
		};
	};
	
	/**
	 *  计时器时间超时后，让子类自己实现超时后的逻辑的方法
	 */
	public void onTimeOut(){
	}
	
	/**
	 * 将要画的dialogName传入，该方法会自动画出对应的界面
	 * @param dialogName
	 */
	protected void setContentView(String dialogName){
		if(DrawComponent.runFlag){  //如果打开新的界面时，计时器还在运行，则关闭计时器
			DrawComponent.timer.cancel();
			DrawComponent.runFlag = false;
		}
		/*设置横屏*/
		Log.i("info", "-------------> DemoSmartActivity中的 setContentView()方法");
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		new DrawComponent(this , handler).drawDialog(dialogName);
	}
	/**
	 * 根据不同的commandName组装不同的request对象，将commandName对应的request的返回
	 * @param commandName
	 * @return
	 */
	public abstract Request getRequestByCommandName(String commandName);
	/**
	 * 组装需要ListView显示的list
	 * @param id
	 * @return
	 */
	public List getDataListById(String id){
		return null;
	}
	/**
	 * 通过xml文件中的id找到对应的组件
	 * @param idName
	 * @return
	 */
	public View findViewById(String idName){
		if(RegisterId.map.get(idName)!= null 
				&& RegisterId.map.get(idName)!=0){
			return this.findViewById(RegisterId.map.get(idName));
		}
		return null;
	}
	/**
	 * 通过被点击组件的id设置该组件要跳转的对应的activityID
	 * @param id
	 */
	public void setActivityIDById(String id){
		
	}
	/**
	 * 跳转是是否出现等待画面  调用该方法则出现等待画面
	 */
	public void hasWaitView(){
			this.setContentView("REQUEST_WAIT.SCREEN");
	}
	@Override
	public void addBottom(){
	}
	@Override
	protected void processResponse(Response response) {
		System.out.println("Handle Message [isError]: " + response.isError());

		if (response != null) {
			int targetActivityID = response.getTargetActivityID();
			// stopProgressDialog();
			// self-update
			if (targetActivityID == Controller.ACTIVITY_ID_UPDATE_SAME) {
				if (!response.isError()) {
					onSuccess(response);
				} else {
					onError(response);
				}
			} else if (targetActivityID == Controller.ACTIVITY_ID_DO_NOTHING) {

			} else {
				Class<? extends BaseActivity> cls = Controller.registeredActivities
						.get(targetActivityID);

				if (cls != null) {
					Intent launcherIntent = new Intent(
							Controller.currentActivity, cls);
					int[] flags = response.getFlags();
					launcherIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					if (flags != null) {
						for (int i = 0; i < flags.length; i++) {
							launcherIntent.addFlags(flags[i]);
						}
					}

					// 如果请求中有数据要传输
					if (response.getBundle() != null) {
						launcherIntent.putExtra("bundleInfo",
								response.getBundle());
					}

					Controller.currentActivity.startActivity(launcherIntent);
				}
			}
		}
	}
}
