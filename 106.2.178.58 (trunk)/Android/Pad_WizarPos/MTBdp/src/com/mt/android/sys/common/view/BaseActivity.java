package com.mt.android.sys.common.view;

import org.apache.log4j.Logger;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mt.android.frame.CustomProgressDialog;
import com.mt.android.sys.bean.base.RequestBean;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.sys.util.ExitApplication;
import com.mt.app.payment.tools.BottomMenu;

public abstract class BaseActivity extends Activity {
	private static Logger log = Logger.getLogger(BaseActivity.class);
	private int DIALOG_ID_PROGRESS_DEFAULT = 0x174980;
	ProgressDialog m_dlg = null;
	CustomProgressDialog myProgressDialog = null;
	private long mExitTime;
	private Toast mToast;

	public void showToast(BaseActivity a, String text) {
		if (mToast == null) {
			mToast = Toast.makeText(a, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_LONG);
			//mToast.cancel();
		}
		mToast.show();
	}
	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		//getWindow().getDecorView().setSystemUiVisibility(View.);
		onBeforeCreate(savedInstanceState);
		super.onCreate(savedInstanceState);
		Log.i(getClass().getName(), "---onCreate()");
		onCreateContent(savedInstanceState);
//		addBottom();
		notifiyControllerActivityCreated();

		onAfterCreate(savedInstanceState);
		ExitApplication.getInstance().addActivity(this);
	}
	
	public void TAB(){}

	/**
	 * ����
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try{
			removeDialog(DIALOG_ID_PROGRESS_DEFAULT);
			//dismissDialog(DIALOG_ID_PROGRESS_DEFAULT);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		Log.i(getClass().getName(), "---onDestroy()");
	}

	/**
	 * ��ͣ
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i(getClass().getName(), "---onPause()");
	}

	/**
	 * ����
	 */
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.i(getClass().getName(), "---onRestart()");
	}

	/**
	 * ����
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(getClass().getName(), "---onResume()");
	}

	/**
	 * ��ʼ
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i(getClass().getName(), "---onStart()");
	}

	/**
	 * ֹͣ
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		try{
			m_dlg.dismiss();
			dismissDialog(DIALOG_ID_PROGRESS_DEFAULT); 
		}catch(Exception ex){
			//ex.printStackTrace();
		}
		Log.i(getClass().getName(), "---onStop()");
	}
	
	
	public void addBottom() {
		BottomMenu bm = new BottomMenu(this);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		this.addContentView(bm.getView(), params);
	}

	public Controller getController() {
		return (Controller) getApplication();
	}

	private void notifiyControllerActivityCreated() {
		getController().onActivityCreated(this);
	}

	protected void onBeforeCreate(Bundle savedInstanceState) {
	}

	protected void onAfterCreate(Bundle savedInstanceState) {
	}

	protected void onCreateContent(Bundle savedInstanceState) {

	}

	public Handler listener = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.obj != null) {
				processResponse((Response) msg.obj);
			}
		}
	};
	
	public void onReadCard(String cardnumber){}

	public abstract void onSuccess(Response response);

	public abstract void onError(Response response);

	protected void showProgress() {
		showDialog(DIALOG_ID_PROGRESS_DEFAULT);
		//startProgressDialog();
	}

	protected void hideProgress() {
		try {
			removeDialog(DIALOG_ID_PROGRESS_DEFAULT);
		} catch (IllegalArgumentException iae) {
		}
	}

	public void checkRegInfo(String regflg){//�˶���֤��
		
	}
	public void getRegInfo(){//��ȡ��֤��
		
		
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0x174980:
			
			if(m_dlg != null){
				return m_dlg;
			}
			
			ProgressDialog dlg = new ProgressDialog(this);
			// dlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dlg.setMessage("���Ժ�...");
			dlg.setCancelable(false);
			dlg.setCanceledOnTouchOutside(false);
			m_dlg = dlg;
			return dlg;
		default:
			return super.onCreateDialog(id);

		}
	}

	public final void bottomMenus(int commandId) {
		go(commandId, new Request(), false);
	}

	public final void go(int commandID, Request request) {
		log.info("go with cmdid=" + commandID);
		go(commandID, request, true);
	}

	public final void go(int commandID, Request request, boolean showProgress) {
		log.debug("go with cmdid=" + commandID + ", request: " + request);
		go(commandID, request, showProgress, true);
	}

	public final void go(int commandID, Request request,
			boolean showProgress, boolean record) {
		if (showProgress) {
			showProgress();
		}
		log.debug("go with cmdid=" + commandID + ", record: " + record
				+ ", request: " + request);
		request.setListener(this.listener);
		getController().go(commandID, request, record);
	}

	/**
	 * 
	 * @param layoutId
	 * @param clazz
	 * @return
	 */
	public RequestBean getFormValues(int layoutId, Class clazz) {
		View view = this.findViewById(layoutId);// ����form2bean
		return null;
	}

	private void startProgressDialog() {
		if (myProgressDialog == null) {
			myProgressDialog = CustomProgressDialog.createDialog(this);
			myProgressDialog.setMessage("���Ժ�...");
		}

		myProgressDialog.show();
	}

	private void stopProgressDialog() {
		if (myProgressDialog != null) {
			myProgressDialog.dismiss();
			myProgressDialog = null;
		}
	}

	protected void processResponse(Response response) {
		System.out.println("Handle Message [isError]: " + response.isError());

		if (m_dlg != null) {
			try{
				
				dismissDialog(DIALOG_ID_PROGRESS_DEFAULT);
				//removeDialog(DIALOG_ID_PROGRESS_DEFAULT);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
		
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Controller.session.put("position", -1);
		}
		// else
		// {
		// // finish();
		// // System.exit(0);
		// int currentVersion = android.os.Build.VERSION.SDK_INT;
		// if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
		// Intent startMain = new Intent(Intent.ACTION_MAIN);
		// startMain.addCategory(Intent.CATEGORY_HOME);
		// startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// startActivity(startMain);
		// System.exit(0);
		// } else {// android2.1
		// ActivityManager am = (ActivityManager)
		// getSystemService(ACTIVITY_SERVICE);
		// am.restartPackage(getPackageName());
		// }
		// }
		//
		// return true;
		// }
		return super.onKeyDown(keyCode, event);
	}
}
