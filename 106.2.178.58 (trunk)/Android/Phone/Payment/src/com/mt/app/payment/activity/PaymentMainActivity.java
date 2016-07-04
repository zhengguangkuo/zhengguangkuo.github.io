package com.mt.app.payment.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.mt.android.R;
import com.mt.android.db.DbHandle;
import com.mt.android.frame.entity.FrameDataSource;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.activity.GuideActivity;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.requestbean.LoginReqBean;
import com.mt.app.payment.requestbean.PackageSelectReqBean;
import com.mt.app.payment.requestbean.UpdateReqBean;
import com.mt.app.payment.responsebean.UpdateResBean;
import com.mt.app.payment.test.TestActivity01;
import com.mt.app.payment.tools.DownloadService;
import com.mt.app.payment.tools.DownloadService.DownloadBinder;
import com.mt.app.payment.tools.InterceptService;

public class PaymentMainActivity extends BaseActivity implements OnClickListener {
	/* baidu */
	public LocationClient mLocationClient = null;
	public MyLocationListenner myListener = new MyLocationListenner();
	public static String TAG = "LocTestDemo";
	public Vibrator mVibrator01;
	/* baidu */
	private SlideMenu slideMenu;
	private ListView list;
	private MenuAdapter adapter;
	private Button RegisterBtn;
	private Button LoginBtn;
	private Button ExitBtn;
	private TextView welUserText;
	String[] listitem = new String[] { "我的账户", "积分查询", "基卡设置", "套餐选择", "应用管理", "帮助", "检查更新", "关于" };
	private TextView welusername;
	private Spinner spinner;
	private ArrayAdapter<SpinnerData> adapterSp;
	private boolean isExit;
	private boolean isBinded;// 下载
	private DownloadBinder binder;// 下载
	private static DbHandle dbhandle = new DbHandle();

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		super.onCreateContent(savedInstanceState);
		setContentView(R.layout.activitymain_layout);
		RegisterBtn = (Button) findViewById(R.id.RegisterBtn);
		LoginBtn = (Button) findViewById(R.id.login_bt);
		welUserText = (TextView) findViewById(R.id.welUserText);
		welUserText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (slideMenu.getCurrentScreen() == 0) {
					slideMenu.closeMenu();
				} else if (slideMenu.getCurrentScreen() == 1) {
					slideMenu.openMenu();
				}
			}
		});
		RegisterBtn.setVisibility(View.VISIBLE);
		RegisterBtn.setText("注册");
		welusername = (TextView) findViewById(R.id.welusername);
		ExitBtn = (Button) findViewById(R.id.ExitBtn);
		LoginBtn.setOnClickListener(new OnClickListener() {// 登录
			@Override
			public void onClick(View v) {
				/*
				 * Request request = new Request();//TO_USER_REGISTER
				 * go(CommandID.map.get("USER_LOGIN"), request, false);
				 */
				Intent intent = new Intent(PaymentMainActivity.this, UserLoginActivity.class);
				intent.putExtra("isShow", "true");
				startActivity(intent);
			}
		});
		RegisterBtn.setOnClickListener(new OnClickListener() {// 注册
					@Override
					public void onClick(View v) {
						Request request = new Request();
						go(CommandID.map.get("USER_REGISTER"), request, false);
					}
				});
		ExitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder exit = new AlertDialog.Builder(PaymentMainActivity.this);// 退出提示
				exit.setTitle("温馨提示");
				exit.setMessage("是否退出?");
				exit.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (Controller.session != null && Controller.session.get("adMap") != null) {
							ArrayList<Bitmap> adMap = (ArrayList<Bitmap>) Controller.session.get("adMap");
							for (int i = 0; i < adMap.size(); i++) {
								try {
									adMap.get(i).recycle();
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						}
						Controller.session.clear();
						finish();
						System.exit(0);
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});
				exit.show();
			}
		});
		list = (ListView) findViewById(R.id.list);
		slideMenu = (SlideMenu) findViewById(R.id.slide_menu);
		Button menuImg = (Button) findViewById(R.id.onoff);
		ImageView menuImgB = (ImageView) findViewById(R.id.onoffB);
		ImageView button_discount = (ImageView) findViewById(R.id.discountbtn);
		button_discount.setOnClickListener(new OnClickListener() {// 折扣优惠
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Request request = new Request();
						go(true, CommandID.map.get("TO_Discount_01_MainList"), request, false);
					}
				});

		ImageView button_ecard = (ImageView) findViewById(R.id.ecardbagbtn);
		button_ecard.setOnClickListener(new OnClickListener() {// 电子卡包

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Request request = new Request();
						go(true, CommandID.map.get("ToEleCard_Main"), request, false);
					}
				});
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < listitem.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", listitem[i]);
			listmap.add(map);
		}
		adapter = new MenuAdapter(this, listmap);
		list.setAdapter(adapter);
		Controller.session.put("loginflag", "nologin");
		menuImg.setOnClickListener(this);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				if (listitem[position].equals("我的账户")) {
					Request request = new Request();
					go(CommandID.map.get("MYACCOUNT"), request, false);
				}
				if (listitem[position].equals("帮助")) {
					Request request = new Request();
					go(CommandID.map.get("HELP"), request, false);
				}
				if (listitem[position].equals("关于")) {
					Request request = new Request();
					go(CommandID.map.get("ABOUT"), request, false);
				}
				if (listitem[position].equals("积分查询")) {
					Request request = new Request();
					go(CommandID.map.get("POINTQUERY"), request, false);
				}
				if (listitem[position].equals("套餐选择")) {
					/*
					 * Request request = new Request(); PackageSelectReqBean
					 * city = new PackageSelectReqBean();
					 * city.setCity(Controller
					 * .session.get("AREA_CODE_LEVEL_1").toString());
					 * city.setRows(10); city.setPage(1); request.setData(city);
					 * go(CommandID.map.get("PACKAGESELECT"), request, true);
					 */
					startActivity(new Intent(PaymentMainActivity.this, Package_SelectActivity.class));
				}
				// if(listitem[position].equals("辅助功能")){
				// Request request = new Request();
				// //低版本不支持 go(CommandID.map.get("AUXILIARYFOUNCTION"), request,
				// true);
				// }
				if (listitem[position].equals("应用管理")) {
					Request request = new Request();
					go(CommandID.map.get("TABAPPMANAGE"), request, false);
				}
				if (listitem[position].equals("基卡设置")) {
					Request request = new Request();
					//go(CommandID.map.get("BASECARDSTART"), request, false);
					go(CommandID.map.get("SKIPBASECARD"), request, false);
				}
				if (listitem[position].equals("检查更新")) {
					try {
						Request request = new Request();
						UpdateReqBean update = new UpdateReqBean();
						String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
						update.setVersionNo(version);
						request.setData(update);
						go(CommandID.map.get("CheckUpdate"), request, false);
					} catch (NameNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});
		menuImg.setVisibility(View.GONE);
		menuImgB.setVisibility(View.GONE);
		spinner = (Spinner) findViewById(R.id.Spinner01);
		List<Map<String, String>> list = dbhandle.rawQuery("select * from AREA_CODE where AREA_LEVEL =2", null);
		List<SpinnerData> lst = new ArrayList<SpinnerData>();
		for (int i = 0; i < list.size(); i++) {
			SpinnerData c = new SpinnerData(list.get(i).get("AREA_CODE"), list.get(i).get("AREA_NAME"));
			// 为了测试方便，此处只加载北京和上海两个城市
			if (list.get(i).get("AREA_NAME").equals("北京") || list.get(i).get("AREA_NAME").equals("上海") || list.get(i).get("AREA_NAME").equals("西安市")) {
				lst.add(c);
			}
		}
		// 将可选内容与ArrayAdapter连接起来
		adapterSp = new ArrayAdapter<SpinnerData>(this, android.R.layout.simple_spinner_item, lst);

		// 设置下拉列表的风格
		adapterSp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		spinner.setAdapter(adapterSp);

		// 添加事件Spinner事件监听
		spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

		// 设置默认值
		spinner.setVisibility(View.VISIBLE);
		loadbaidu();
		// 启动程序检查更新
		try {
			if (Controller.session.get("IsCheckUpdate") != null && Controller.session.get("IsCheckUpdate").toString().equalsIgnoreCase("true")) {
				Request request = new Request();
				UpdateReqBean update = new UpdateReqBean();
				String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
				update.setVersionNo(version);
				request.setData(update);
				go(CommandID.map.get("CheckUpdate"), request, false);
				Controller.session.remove("IsCheckUpdate");
			}
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 点击两次退出
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (Controller.session != null && Controller.session.get("adMap") != null) {
				ArrayList<Bitmap> adMap = (ArrayList<Bitmap>) Controller.session.get("adMap");
				for (int i = 0; i < adMap.size(); i++) {
					try {
						adMap.get(i).recycle();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			exit();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	// 点击两次退出的方法
	public void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			System.exit(0);
			finish();
		}
	}

	// 点击两次退出控制处理
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			isExit = false;
		}

	};

	public void loadbaidu() {
		mLocationClient = new LocationClient(this);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(10000);/* 小于1秒时说明是 */
		option.setServiceName("com.baidu.location.service_v2.2");
		mLocationClient.registerLocationListener(myListener);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
		mLocationClient.requestLocation();
		mLocationClient.stop();
	}

	/* baidu */
	/**
	 * 监听函数，有更新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			// 将坐标存储到session中116.374123

			if (Controller.session.get("pointx") == null) {
				Controller.session.put("pointx", "");
				Controller.session.put("pointy", "");
				Controller.session.put("cityName", "");
			}

			String lat = location.getLatitude() + "";
			String gitu = location.getLongitude() + "";
			System.out.println(lat);
			System.out.println(gitu);

			if (lat.length() > 8) {
				Controller.session.put("pointx", gitu);
				Controller.session.put("pointy", lat);
				Controller.session.put("cityName", location.getCity());
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}

		public class NotifyLister extends BDNotifyListener {
			public void onNotify(BDLocation mlocation, float distance) {
				mVibrator01.vibrate(2000);
			}
		}
	}

	/* baidu */
	class SpinnerData {

		private String value = "";
		private String text = "";

		public SpinnerData() {
			value = "";
			text = "";
		}

		public SpinnerData(String _value, String _text) {
			value = _value;
			text = _text;
		}

		@Override
		public String toString() {

			return text;
		}

		public String getValue() {
			return value;
		}

		public String getText() {
			return text;
		}
	}

	// 使用数组形式操作
	class SpinnerSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

			Log.i("选择的城市为：", ((SpinnerData) spinner.getSelectedItem()).getText());
			Log.i("选择的城市值为：", ((SpinnerData) spinner.getSelectedItem()).getValue());

			// Controller.session.remove("AREA_CODE_LEVEL_1");
			Controller.session.put("AREA_CODE_LEVEL_1", ((SpinnerData) spinner.getSelectedItem()).getValue());
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.onoff:
			if (Controller.session.get("IsLogin") != null && Controller.session.get("IsLogin").toString().equalsIgnoreCase("true")) {
				if (slideMenu.isMainScreenShowing()) {
					slideMenu.openMenu();
				} else {
					slideMenu.closeMenu();
				}
			}
			break;
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		// 调试时使用,解决每次登陆后需要输入验证码
		// Controller.session.put("regcode", "test");
		// 调试时使用,解决每次登陆后需要输入验证码
		super.onResume();
		if (Controller.session.get("IsLogin") != null && Controller.session.get("IsLogin").toString().equalsIgnoreCase("true")) {
			LoginBtn.setVisibility(View.GONE);
			RegisterBtn.setVisibility(View.GONE);
			welUserText.setVisibility(View.VISIBLE);
			LoginReqBean reqBean = new LoginReqBean();
			reqBean = (LoginReqBean) Controller.session.get("user");
			// welUserText.setText("hello,\n"+ reqBean.getJ_username());
			welUserText.setSingleLine(false);
			welusername.setText(reqBean.getJ_username());

		}
		FrameDataSource.tabDataClass.remove("bindNewCard");
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
		UpdateResBean update = (UpdateResBean) response.getData();// 获取更新url
		if (update != null) {
			final String url = update.getLinkAddress();
			Controller.session.put("url", url);
			showUpdateDialog();
		}
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		// Toast.makeText(this, "请您先登录！", Toast.LENGTH_LONG);

		if (Controller.session.get("IsLogin") != null && Controller.session.get("IsLogin").toString().equalsIgnoreCase("true")) {
			ResponseBean msg = (ResponseBean) response.getData();
			Toast.makeText(this, msg.getMessage(), 3000).show();
		}
		if (response != null && response.getBussinessType() != null && response.getBussinessType().equals("EleMainDetails")) {
			Intent intent = new Intent(PaymentMainActivity.this, MainDetailsActivity.class);
			startActivity(intent);
		}
	}

	private void showUpdateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("检测到新版本");
		builder.setMessage("是否下载更新?");
		builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent it = new Intent(PaymentMainActivity.this, DownloadService.class);
				startService(it);
				bindService(it, conn, Context.BIND_AUTO_CREATE);
				finish();
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		builder.show();
	}

	ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			isBinded = false;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			binder = (DownloadBinder) service;
			System.out.println("服务启动!!!");
			// 开始下载
			isBinded = true;
			binder.start();
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (isBinded) {
			System.out.println(" onDestroy   unbindservice");
			unbindService(conn);
		}
		if (binder != null && binder.isCanceled()) {
			System.out.println(" onDestroy  stopservice");
			Intent it = new Intent(this, DownloadService.class);
			stopService(it);
		}
		Intent intent = new Intent(this, InterceptService.class);
		stopService(intent);
	}

	class MenuAdapter extends BaseAdapter {
		private Context context;
		private List<Map<String, Object>> listItems;
		private LayoutInflater listContainer;

		public final class ListItemView {
			public TextView text;

		}

		public MenuAdapter(Context context, List<Map<String, Object>> listItems) {
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
			ListItemView listItemView = null;
			listItemView = new ListItemView();
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.menu_list_item_layout, null);
			// 获取控件对象
			listItemView.text = (TextView) convertView.findViewById(R.id.menu_list_item);
			listItemView.text.setText((String) listItems.get(position).get("text"));
			// 设置控件集到converView
			convertView.setTag(listItemView);
			return convertView;
		}
	}
}