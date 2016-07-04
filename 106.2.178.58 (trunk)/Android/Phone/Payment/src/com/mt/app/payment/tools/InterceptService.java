package com.mt.app.payment.tools;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.payment.activity.GuidePackage_SelectActivity;
import com.mt.app.payment.activity.Package_SelectActivity;
import android.app.Service;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
public class InterceptService extends Service {
	Messenger mService =null;
	private final String TAG_LOG = InterceptService.class.getSimpleName() ;
	private ContentObserver mObserver ;
	private String mSmsNumber ;
	@Override
	public void onCreate() {
		Log.i(TAG_LOG, "onCreate().");
		super.onCreate() ;
		mSmsNumber = Controller.session.get("liantongphonenum").toString();
		addSMSObserver() ;
	}
	
	private void addSMSObserver() {
		mObserver = new InterceptObserver(new Handler(), mSmsNumber) ;
		
		getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, mObserver) ;
		
	}
	
	/**
	 * �����ص���Ϣ���д���
	 * @param showSMSContent
	 */
	private void showSMSContent(String showSMSContent) {
		Log.i(TAG_LOG, "showSMSContent()") ;
//		Cursor cursor = getContentResolver().query(CONTENT_URI, null, "address=?", new String[]{mSmsNumber}, null);
		/*Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
		if(cursor.moveToFirst()) {
			String number = cursor.getString(cursor.getColumnIndex("address")) ;
			if(number.contains(mSmsNumber)) {
				String body = cursor.getString(cursor.getColumnIndex("body")) ;
				Log.i(TAG_LOG, ">>>>>>>>>>>number = +" + number +",,,BODY = " + body) ;
				Toast.makeText(InterceptService.this, ">>>>number = +" + number +",,,BODY = " + body, 0).show() ;				
			}
		}*/
		deleteSMS(showSMSContent) ;
	}
	

	@Override
	public IBinder onBind(Intent intent) {
		return null ;
	}

	
	@Override
	public void onDestroy() {
		super.onDestroy() ;
		// TODO :
	
		getContentResolver().unregisterContentObserver(mObserver) ;
	}
	
	
	public void deleteSMS(String smsNumber) {  
        try {  
        	mSmsNumber = Controller.session.get("liantongphonenum").toString();
        	Log.i(TAG_LOG, "deleteSMS()") ;
            // ׼��ϵͳ�����������uri��ַ  
            Uri uri = Uri.parse("content://sms/inbox");// ������  
            // ��ѯ�����������еĶ���  
            Cursor cursor = getContentResolver().query(uri, null, "read=" + 0, null, null);  
            while (cursor.moveToNext()) {  
                String body = cursor.getString(cursor.getColumnIndex("body")).trim();	// ��ȡ��Ϣ����  
                String number = cursor.getString(cursor.getColumnIndex("address")) ;
                if (number.contains(smsNumber)) {  
                	Log.i(TAG_LOG, "ɾ������....") ;
                	//�ڳ��ε�½��ʱ�����������ײͰ󶨽��棨GuidePackage_SelectActivity�������Ժ��ڵ�½��ʱ����ײ���򿪵����ײͰ󶨽��棨Package_SelectActivity��
                	//����������Ҫ�ж��Ƿ��ǵ�һ�ε�½
                	if(Controller.session.get("isGuide") != null && Controller.session.get("isGuide").toString().equalsIgnoreCase("1")){
                		
                		Message msg = Message.obtain(GuidePackage_SelectActivity.handler);
                		msg.what = 2;
                		msg.obj=body;
                		msg.sendToTarget();
                	}else{
                		if(Package_SelectActivity.dialog !=null){
    						if(Package_SelectActivity.dialog.isShowing()){
                		Message msg = Message.obtain(Package_SelectActivity.handler);
                		msg.what = 2;
                		msg.obj=body;
                		msg.sendToTarget();
                		   }
    					}
                	}
                    int id = cursor.getInt(cursor.getColumnIndex("_id"));  
                    getContentResolver().delete(Uri.parse("content://sms"), "_id=" + id, null) ;  
                  }
                }
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }    
	class InterceptObserver extends ContentObserver {

		private String smsNumber ;
		
		
		public InterceptObserver(Handler handler, String smsNumber) {
			super(handler) ;
			this.smsNumber = smsNumber ;
		}

		
		@Override
		public void onChange(boolean selfChange) {
			Log.i("InterceptObserver", "onChange") ;
			super.onChange(selfChange) ;
			// �����ص���Ϣ���д���
			showSMSContent(smsNumber) ;
			// ����������Ϣ�󣬷�ע�����ݹ۲���
//			getContentResolver().unregisterContentObserver(this) ;
		}
		
	}
	
}
