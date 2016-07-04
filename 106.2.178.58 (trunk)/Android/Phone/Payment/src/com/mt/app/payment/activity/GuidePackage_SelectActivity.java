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
	/** 第一次发送订购短信 */
	private static final int HANDLER_FIRST_SMS_SUCCESS = 0X00 ;
	private static final int HANDLER_FIRST_SMS_ERROR = 0X01 ;
	/** 二次确认短信成功 */
	private static final int HANDLER_SECOND_SMS = 0X02 ;
	/** 订购成功 */
	private static final int HANDLER_END_SMS_SUCCESS = 0X03 ;
	/** 订购失败 */
	private static final int HANDLER_END_SMS_ERROR = 0X04 ;
	/** 二次确认短信号码 */
	private static final String REPONSE_SMS_NUMBER_TWO = "10655134" ;
	/** 二次确认短信部分内容 */
	private static final String REPONSE_SMS_CONTENT_TWO = "确认订购请回复Y" ;
	/** 联通响应订购成功或者订购失败号码 */
	private static final String REPONSE_SMS_NUMBER_END = "10655134" ;
	/** 联通响应订购成功短信部分内容 */
	private static final String REPONSE_SMS_CONTENT_END_SUCCESS = "成功订购北京联通的电子卡包业务" ;
	/** 联通响应订购失败短信部分内容 */
	private static final String REPONSE_SMS_CONTENT_END_ERROR = "订购梅泰诺（北京）移动信息技术有限公司的电子卡包的业务失败" ;
	/** 联通响应退订成功信息*/
	private static final String REPONSE_SMS_CONTENT_CANCEL = "您已成功退订北京联通的电子卡包业务" ;
	/** 联通响应退订失败信息*/
	private static final String REPONSE_SMS_CONTENT_CANCEL_ERROR = "您取消订购梅泰诺（北京）移动信息技术有限公司提供的电子卡包业务失败" ;
	/** 联通响应重复订购信息*/
	private static final String REPONSE_SMS_CONTENT_REPEAT = "对不起，您已经订购" ;
	/** 收到二次确认短信显示给用户看到的信息 */
	private static final String USER_SMS_SECOND = "欢迎订阅梅泰诺（北京）移动信息技术有限公司的电子卡包，包月5元/月，不含通讯费，确认订购请按确定按钮，否则请点取消按钮。" ;
	/** 收到成功订购短信显示给用户看到的信息 */
	private static final String USER_SMS_SUCESS = "您已成功订购北京联通的电子卡包业务，当月订购当月生效，月功能费5元。退订发送TDDZKB到106558892，公司客服电话4000328666。" ;
	/** 收到订购失败短信显示给用户看到的信息 */
	private static final String USER_SMS_ERROR = "对不起，您定制梅泰诺（北京）移动信息技术有限公司的电子卡包产品失败，客服电话4000328666，咨费方案：每月功能费5元（本消息免费）" ;
	/** 收到成功退订短信显示给用户看到的信息 */
	private static final String USER_SMS_CANCEL_SUCESS = "您已成功退订北京联通的电子卡包业务，如需再次订购请发送DGDZKB到106558892。" ;
	/** 收到重复订购短信显示给用户看到的信息 */
	private static final String USER_SMS_CANCEL_SREPEAT = "对不起，您已经定购过北京联通的电子卡包业务，请勿重复定购。" ;
	/** 收到退订失败短信显示给用户看到的信息 */
	private static final String USER_SMS_CANCEL_SUCESS_ERROR = "对不起，您取消定制梅泰诺（北京）移动信息技术有限公司提供的电子卡包产品失败。" ;
	
	private ProgressDialog mDialog ;
	/** 阻塞用户操作的对话框 */
	private static final int DIALOG_1 = 0X11 ;
	/** 给用户提示内容的对话框 */
	private static final int DIALOG_2 = 0X12 ;
	private ListView listview;
	private SMAdapter adapter;
	private TextView title;
	public static Handler handler;
	public static AlertDialog dialog;
	private final int SMSFIRST = 1;// 短信第一次发送
	private final int SMSSECOND = 2;// 短信第二次发送
	private final int SMSTHRID = 3;// 短信第三次发送
	private final String TAG_LOG = GuidePackage_SelectActivity.class.getSimpleName();
	private Button onoff, selectpackage, cancelselectpackage;
	private ImageView image, imgFrame;
	Context mContext = null;
	boolean show = true;
	Runnable runnable = new Runnable() {
		public void run() {
			mDialog.dismiss();
			show = true;
			Toast.makeText(GuidePackage_SelectActivity.this, "接收短信超时，请注意查看短信", 3000).show();
		}
	};
	private int clickPosition = -1;
	private int a = 0;
	private String phoneNumber = "";
	public String packagespe, packagebus;
	private RegCodeInput regcode;
	MealSelected_DataBean bean;
	private boolean isBind = false;
	/** 发送与接收的广播 **/
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
		title.setText("套餐选择");
		onoff.setVisibility(View.GONE);
		image.setVisibility(View.GONE);
		Controller.session.put("liantongphonenum", phoneNumber);
		Controller.session.put("isGuide", "1");
		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		if (bundle.getSerializable("package") != null) {
			bean = (MealSelected_DataBean) bundle.getSerializable("package");
			if (bean.getRows() == null) {
				showToast(this, "请检查网络连接！");
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
				case HANDLER_FIRST_SMS_SUCCESS :		// 订购短信发送成功
					
					break;
				
				case HANDLER_FIRST_SMS_ERROR :			// 订购短信发送失败
					try {
						if(mDialog.isShowing()) {
							dismissDialog(DIALOG_1) ;															
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					Toast.makeText(GuidePackage_SelectActivity.this, "短信发送失败，请重新操作...", 0) ;
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
				
				case HANDLER_END_SMS_SUCCESS :			// 退订也是走的这里
				case HANDLER_END_SMS_ERROR :
					try {
						if(mDialog.isShowing()) {
							dismissDialog(DIALOG_1) ;															
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					String smscontent = (String) msg.obj ;		// 收到的短信内容 
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
							ShowDialog(SMSFIRST, "操作中请稍后！");
							this.postDelayed(runnable, 10000);
							show = false;
						}
					} else {
						Log.i(TAG_LOG, "ShowDialog()....1.2");
						ShowDialog(SMSFIRST, "操作中请稍后！");
						this.postDelayed(runnable, 10000);
						show = false;
					}
				}
				if (msg.what == SMSSECOND) {
					Log.i(TAG_LOG, "ShowDialog()....2");
					dialog.cancel();
					handler.removeCallbacks(runnable);
					if (String.valueOf(msg.obj).contains("欢迎订阅梅泰诺")) {
						ShowDialog(SMSSECOND, String.valueOf(msg.obj));
						show = true;
					}
					if (String.valueOf(msg.obj).contains("您已成功订购北京联通的电子卡包业务")) {
						ShowDialog(SMSTHRID, String.valueOf(msg.obj));
					}
					if (String.valueOf(msg.obj).contains("您已成功退订北京联通的电子卡包业务")) {
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
				// 解析完内容后分析具体参数
				for (SmsMessage msg : messages) {
					// 获取短信内容
					String content = msg.getMessageBody();
					String sender = msg.getOriginatingAddress();
					Date date = new Date(msg.getTimestampMillis());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sendTime = sdf.format(date);
					Log.i("SmsReceiver", "收到" + sender + "的短信");
					Message message = new Message() ;
					if(sender.contains(REPONSE_SMS_NUMBER_TWO) & content.contains(REPONSE_SMS_CONTENT_TWO)) {	// 二次确认短信
						abortBroadcast();
						Bundle data = new Bundle() ;
						data.putString("smscontent", content) ;
						data.putString("smsnumber", sender) ;
						message.setData(data) ;
						message.what = HANDLER_SECOND_SMS ;
						handler.sendMessage(message) ;
					} else if(sender.equals(REPONSE_SMS_NUMBER_END) /*& content.contains(REPONSE_SMS_CONTENT_END_SUCCESS)*/){		// 订购成功				
						abortBroadcast();
						message.obj = content ;
						message.what = HANDLER_END_SMS_SUCCESS ;
						handler.sendMessage(message) ;
					} /*else if(sender.contains(REPONSE_SMS_NUMBER_END) & content.contains(REPONSE_SMS_CONTENT_END_ERROR)){		// 订购失败
						abortBroadcast();
						message.obj = content ;
						message.what = HANDLER_END_SMS_ERROR ;
						handler.sendMessage(message) ;
					} else if(sender.contains(REPONSE_SMS_NUMBER_END) & content.contains(REPONSE_SMS_CONTENT_CANCEL)) {			// 退订
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
						Log.i("SmsReceiver", "收到" + sender + "的短信");
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
						Log.i("SmsReceiver", "收到:" + sender + "内容:" + content + "时间:" + sendTime.toString());
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
			case Activity.RESULT_OK: /* 发送短信成功 */

				Log.i(TAG_LOG, "MonitorSMS.....onReceive()....Activity.RESULT_OK");
				msg.what = HANDLER_FIRST_SMS_SUCCESS ;
				handler.sendMessage(msg);

				break;

			case SmsManager.RESULT_ERROR_GENERIC_FAILURE: /* 发送短信失败 */
				Toast.makeText(context, "短信发送失败", Toast.LENGTH_LONG).show();
				msg.what = HANDLER_FIRST_SMS_ERROR ;
				handler.sendMessage(msg);
				break;

			case SmsManager.RESULT_ERROR_RADIO_OFF:

				break;

			case SmsManager.RESULT_ERROR_NULL_PDU:

				break;
			case SmsManager.RESULT_ERROR_NO_SERVICE:// 当前服务不可用
				Toast.makeText(context, "当前服务不可用,短信发送失败", Toast.LENGTH_LONG).show();
				msg.what = HANDLER_FIRST_SMS_ERROR ;
				handler.sendMessage(msg);
				break;
			}
		}
	};

	/*public void ShowDialog(final int flag, String str) {
		AlertDialog.Builder builder = new AlertDialog.Builder(GuidePackage_SelectActivity.this);
		dialog = builder.create();
		if (str.contains("欢迎订阅梅泰诺")) {
			builder.setMessage("欢迎订阅梅泰诺（北京）移动信息技术有限公司的电子卡包，包月5元/月，不含通讯费，订购请按确定。退订发送TDDZKB到106558892，公司客服电话400328666。 ");
		} else {
			builder.setMessage(str);
		}
		if (flag == SMSFIRST) {
		} else {
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
	 * 参数说明 destinationAddress:收信人的手机号码 scAddress:发信人的手机号码 text:发送信息的内容
	 * sentIntent:发送是否成功的回执，用于监听短信是否发送成功。
	 * DeliveryIntent:接收是否成功的回执，用于监听短信对方是否接收成功。
	 */
	private void sendSMS(String phoneNumber, String message) {

		Log.i(TAG_LOG, "发送短信：：：：" + message);

		// ---sends an SMS message1 to another device---
		SmsManager sms = SmsManager.getDefault();

		// create the sentIntent parameter
		Intent sentIntent = new Intent(SENT_SMS_ACTION);
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, 0);

		// create the deilverIntent parameter
		Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
		PendingIntent deliverPI = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);

		// 如果短信内容超过70个字符 将这条短信拆成多条短信发送出去
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
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if(args.getBoolean("flag")) {			// 二次确认短信
							try {
								if(mDialog.isShowing()) {
									dismissDialog(DIALOG_1) ;															
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							showMyDialog(DIALOG_1, "请稍候...") ;
							handler.postDelayed(runnable, 20000) ;
							sendSMS(args.getString("number"), "Y") ;
						} else {
							setData() ;
							if(USER_SMS_CANCEL_SUCESS_ERROR.equals(args.getString("content"))) {
								isBind = true ;
								exit() ;		
							}
							if(USER_SMS_SUCESS.equals(args.getString("content"))) {		// 定制成功
								isBind = true ;
								exit() ;
							}
							if(USER_SMS_CANCEL_SUCESS.equals(args.getString("content"))) {		// 退订成功
								isBind = false ;
								exit() ;
							}
						}
					}
				}) ;
			if(args.getBoolean("flag")) {
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
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
	 * @param id 显示哪种对话框
	 * @param content 对话框显示的内容 
	 * @param number 发送给对话框的号码
	 * @param flag 发送给对话框的标记，用于标识是否显示取消按钮
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
	 * 发送请求 获取套餐信息
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
	
	/* 验证码 */
	public void checkRegInfo(String vregCode) {// 核对验证码
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
			selectpackage.setText("退订");
		} else {
			selectpackage.setText("订制");
		}
		
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Log.i("=====position===", arg2 + "");
				AlertDialog.Builder builder = new Builder(GuidePackage_SelectActivity.this);

				builder.setMessage((bean.getRows().get(arg2)).getDetail());

				builder.setTitle("套餐介绍");

				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

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
					showToast(GuidePackage_SelectActivity.this, "请先选择您要订制的套餐");
				} else {
					AlertDialog.Builder builder = new Builder(GuidePackage_SelectActivity.this);
					if (isBind) {
						builder.setMessage("亲，点击确定就会退订" + (bean.getRows().get(clickPosition).getPkg_name()) + "套餐了,请三思！！！仅限联通用户");
					} else {
						builder.setMessage((bean.getRows().get(clickPosition).getDetail()));
					}

					builder.setTitle("提示");
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

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
								Log.i("=========内容=====", message == null ? "null" : message);
								Log.i("=========号码=====", (bean.getRows().get(clickPosition).getSpecial_no()));
								// 这是获得联通服务端的号码，因为种种原因没法用，所以就用了同事的电话号码。
								phoneNumber = bean.getRows().get(clickPosition).getSpecial_no();
								Controller.session.put("liantongphonenum", phoneNumber);
								showMyDialog(DIALOG_1, "正在发送短信,请耐心等待...") ;
								handler.postDelayed(runnable, 20000) ;
								sendSMS(phoneNumber, message);
							} catch (Exception e) {
								showToast(GuidePackage_SelectActivity.this, "短信发送失败！");
							}

						}
					});

					builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

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
		 * // 验证码 if (Controller.session.get("regcode") == null) {//
		 * 如果没有输入验证码,则返回 regcode = new
		 * RegCodeInput(Package_SelectActivity.this); regcode.showDialog();
		 * return; } // 验证码
		 * 
		 * SmsUtil sms = new SmsUtil(); try { if (packagespe == null) {
		 * Toast.makeText(Package_SelectActivity.this, "请选择套餐", 3000).show(); }
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
		Log.i("com.mt.android", "启动服务");
		startService(intent);*/

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Controller.session.remove("isGuide");
		try {
			// 取消广播 发送消息
			unregisterReceiver(MonitorSMS);
			unregisterReceiver(SmsReceiver);
			Intent intent = new Intent(this, InterceptService.class);
			stopService(intent);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/* 验证码 */
	@Override
	public void onSuccess(Response response) {
		bean = (MealSelected_DataBean) response.getData() ;
		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		//if (bundle.getSerializable("package") != null) {
//			bean = (MealSelected_DataBean) bundle.getSerializable("package");
			if (bean.getRows().isEmpty()) {
				Toast.makeText(this, "网络连接失败，请重试", 3000).show();
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
					selectpackage.setText("退订");
				} else {
					selectpackage.setText("订制");
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
			/* 验证码 */
			if (regcode != null && regcode.isRegResponse(response)) {
				return;
			}
			/* 验证码 */
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

			/* 验证码 */
			if (regcode != null && regcode.isRegResponse(response)) {
				return;
			}
			/* 验证码 */

			if ((ResponseBean) response.getData() != null) {

				Toast.makeText(GuidePackage_SelectActivity.this, ((ResponseBean) response.getData()).getMessage(), Toast.LENGTH_SHORT).show();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 退出当前界面进入基卡设置界面
	 */
	private void exit() {
		Request request = new Request();
		go(CommandID.map.get("GuideBASECARDSTART"), request, true);
		if(!isBind) {
			Log.i(TAG_LOG, "isBind == " + isBind) ;
			Toast.makeText(GuidePackage_SelectActivity.this, "不选择套餐，将不能享受到电子卡包及优惠折扣的服务", Toast.LENGTH_LONG).show();					
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
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.package_psitem_layout, null);
			// 获取控件对象
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

					builder.setTitle("套餐介绍");

					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

					builder.create().show();
					return true;
				}
			});

			// 设置控件集到converView
			convertView.setTag(listItemView);
			return convertView;

		}
	}
}
