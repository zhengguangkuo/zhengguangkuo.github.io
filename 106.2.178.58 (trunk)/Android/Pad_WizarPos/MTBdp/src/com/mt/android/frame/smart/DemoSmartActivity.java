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
 * padӦ������activity��Ҫ�̳еĸ���
 * 
 *
 */
public abstract class DemoSmartActivity extends BaseActivity {  //���౻�̳У������Լ�д�߼�
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:  //��ʱ��û����
				DrawComponent.tvTime.setText("��ʱ��ʼ��" + DrawComponent.second + "��");
				DrawComponent.second -- ;
				break;
			case 1:  //��ʱ����
				DrawComponent.timer.cancel();
				DrawComponent.runFlag = false;
				onTimeOut();
				break;
			}
		};
	};
	
	/**
	 *  ��ʱ��ʱ�䳬ʱ���������Լ�ʵ�ֳ�ʱ����߼��ķ���
	 */
	public void onTimeOut(){
	}
	
	/**
	 * ��Ҫ����dialogName���룬�÷������Զ�������Ӧ�Ľ���
	 * @param dialogName
	 */
	protected void setContentView(String dialogName){
		if(DrawComponent.runFlag){  //������µĽ���ʱ����ʱ���������У���رռ�ʱ��
			DrawComponent.timer.cancel();
			DrawComponent.runFlag = false;
		}
		/*���ú���*/
		Log.i("info", "-------------> DemoSmartActivity�е� setContentView()����");
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		new DrawComponent(this , handler).drawDialog(dialogName);
	}
	/**
	 * ���ݲ�ͬ��commandName��װ��ͬ��request���󣬽�commandName��Ӧ��request�ķ���
	 * @param commandName
	 * @return
	 */
	public abstract Request getRequestByCommandName(String commandName);
	/**
	 * ��װ��ҪListView��ʾ��list
	 * @param id
	 * @return
	 */
	public List getDataListById(String id){
		return null;
	}
	/**
	 * ͨ��xml�ļ��е�id�ҵ���Ӧ�����
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
	 * ͨ������������id���ø����Ҫ��ת�Ķ�Ӧ��activityID
	 * @param id
	 */
	public void setActivityIDById(String id){
		
	}
	/**
	 * ��ת���Ƿ���ֵȴ�����  ���ø÷�������ֵȴ�����
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

					// ���������������Ҫ����
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
