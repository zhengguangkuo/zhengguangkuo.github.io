package com.mt.app.payment.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView.ScaleType;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.miteno.coupon.entity.Act;
import com.miteno.coupon.entity.MerchAct;
import com.miteno.coupon.entity.TopContent;
import com.miteno.coupon.vo.ActVo;
import com.mt.android.R;
import com.mt.android.global.Globals;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.adapter.Discount_dis_adapter;
import com.mt.app.payment.adapter.GalleryAdapter;
import com.mt.app.payment.requestbean.Discount_disResBean;
import com.mt.app.payment.requestbean.Discount_disSearchResBean;
import com.mt.app.payment.requestbean.EleCard_business_Bean;
import com.mt.app.payment.responsebean.AdResult;
import com.mt.app.payment.responsebean.DiscountDisAllResult;
import com.mt.app.payment.responsebean.ImageResultBean;
import com.mt.app.payment.test.TestActivity01;
import com.mt.app.payment.test.View3PagerData;
import com.mt.app.payment.test.View3Pagers;
import com.mt.app.payment.tools.AdThread;
import com.mt.app.payment.tools.GetDistance;
import com.mt.app.payment.tools.ToastFactory;
import com.mt.app.tab.activity.TabDiscount_01_MainListActivity;

public class Discount_01_dis_MainListActivity extends BaseActivity implements OnScrollListener {

	private static final String TAG_LOG = Discount_01_dis_MainListActivity.class.getSimpleName();
	Gallery mGallery;
	GalleryAdapter mGalleryAdapter;
	LinearLayout lay = null;
	// Timer mTimer;
	private String flag = "first"; // �Ƿ��ǵ�һ�μ�������
	public static String flagAreafresh = "first"; // �Ƿ��ǵ�һ�ε���������������
	public static String flagTypefresh = "first"; // �Ƿ��ǵ�һ������������������
	private int i = 1; // ��ǰ���ڵ�ҳ����
	private List<EleCard_business_Bean> datas;
	private View loadMoreView;
	private LinearLayout pbLinear;
	private int visibleLastIndex = 0; // ���Ŀ���������
	private int visibleItemCount; // ��ǰ���ڿɼ�������
	private Handler handler = new Handler();
	private int totalNum; // ����
	private double lat;
	private double lon;
	private ListView listView;
	private Discount_dis_adapter adapter;
	private boolean flg = false;
	private int curSize = 0;
	private String area = "";
	private String Lv2Category = "";
	/*********************** ������ start **************************/
	// private ViewPager viewPager; // android-support-v4�еĻ������
	// private List<ImageView> imageViews; // ������ͼƬ����
	//
	// //private String[] titles; // ͼƬ����
	//
	// private List<View> dots; // ͼƬ�������ĵ���Щ��
	// private MyAdapter pageViewAdapter = new MyAdapter();
	// private TextView tv_title;
	// private int currentItem = 0; // ��ǰͼƬ��������
	// private ArrayList<Bitmap> adMap = new ArrayList<Bitmap>();
	// // An ExecutorService that can schedule commands to run after a given
	// delay,
	// // or to execute periodically.
	// private ScheduledExecutorService scheduledExecutorService;
	// private AdThread adThread; //��������߳�
	private String point;
	private View3Pagers mView3Pagers;

	/*********************** ������ end **************************/
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		i = 1;
		try {
			if (TabDiscount_01_MainListActivity.tvText != null && TabDiscount_01_MainListActivity.tvText.size() != 0) {
				TabDiscount_01_MainListActivity.tvText.get(0).setTextColor(Color.WHITE);
				if (TabDiscount_01_MainListActivity.tvText.size() == 2) {
					TabDiscount_01_MainListActivity.tvText.get(1).setTextColor(Color.GRAY);
					TabDiscount_01_MainListActivity.tvText.get(1).setShadowLayer(0, 0, 0, 0);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		TabDiscount_01_MainListActivity.jiemianType = "youhuiquan";

		hideStatusBar();
		setContentView(R.layout.discount_01_mainlist);

		prepareView();
		// mTimer = new Timer();
		// mTimer.scheduleAtFixedRate(new MyTask(), 0, 5000);
		Globals.AppManage = this;
		/******************** ����ʼ��start *************************/

		// dots = new ArrayList<View>();
		// dots.add(findViewById(R.id.v_dot0));
		// dots.add(findViewById(R.id.v_dot1));
		// dots.add(findViewById(R.id.v_dot2));
		// dots.add(findViewById(R.id.v_dot3));
		// dots.add(findViewById(R.id.v_dot4));
		//
		// tv_title = (TextView) findViewById(R.id.tv_title);
		// //tv_title.setText(titles[0]);//
		//
		// viewPager = (ViewPager) findViewById(R.id.vp);
		//
		// // ����һ������������ViewPager�е�ҳ��ı�ʱ����
		// viewPager.setOnPageChangeListener(new MyPageChangeListener());
		// viewPager.setAdapter(pageViewAdapter);// �������ViewPagerҳ���������
		//
		// if ( Controller.session.get("adMap")!=null){//���ͼƬ�Ѿ�����
		//
		// imageViews = new ArrayList<ImageView>();
		// adMap = (ArrayList<Bitmap>) Controller.session.get("adMap");
		// // ��ʼ��ͼƬ��Դ
		// for (int i = 0; i < 5; i++) {
		// ImageView imageView = new ImageView(this);
		// if (i<adMap.size()) {
		// imageView.setImageBitmap(adMap.get(i));
		// } else {
		// imageView.setBackgroundResource(R.drawable.set_back);//��Ϊ��̨������������С��5�������ͼƬ��ΪĬ��ͼƬ
		// }
		// imageView.setScaleType(ScaleType.CENTER_CROP);
		// imageViews.add(imageView);
		// }
		//
		// } else {//���ͼƬû������
		// adMap.add(BitmapFactory.decodeResource(getResources(),
		// R.drawable.adload));
		// adMap.add(BitmapFactory.decodeResource(getResources(),
		// R.drawable.adload));
		// adMap.add(BitmapFactory.decodeResource(getResources(),
		// R.drawable.adload));
		// adMap.add(BitmapFactory.decodeResource(getResources(),
		// R.drawable.adload));
		// adMap.add(BitmapFactory.decodeResource(getResources(),
		// R.drawable.adload));
		// imageViews = new ArrayList<ImageView>();
		// for (int i = 0; i < 5; i++) {
		// ImageView imageView = new ImageView(this);
		// imageView.setImageBitmap(adMap.get(i));
		// imageView.setScaleType(ScaleType.CENTER_CROP);
		// imageViews.add(imageView);
		// }
		// adThread = new AdThread(adMap, mHandler);
		// adThread.start();
		// }

		mView3Pagers = (View3Pagers) findViewById(R.id.widget);
		ArrayList<View3PagerData> data = new ArrayList<View3PagerData>();
		mView3Pagers.setData(data);

		/******************** ����ʼ��end *************************/

		// �û�����
		if (Controller.session.get("pointx") != null) {
			point = Controller.session.get("pointx") + "," + Controller.session.get("pointy");
			if (point.trim().equalsIgnoreCase(",")) {
				point = "";
			}
		}
	}

	private void setDatas() {
		Request request = new Request();
		Discount_disResBean bean = new Discount_disResBean();
		// ��ǰҳ��
		bean.setPage(i);
		// ÿҳ����
		bean.setRows(10);
		/*
		 * // �û����� String point = ""; if (Controller.session.get("pointx") !=
		 * null) { point = Controller.session.get("pointx") + "," +
		 * Controller.session.get("pointy"); if
		 * (point.trim().equalsIgnoreCase(",")) { point = ""; } }
		 */
		bean.setPoint(point);
		// ��������
		bean.setOrderBy("");
		// ��������
		bean.setOrderType("");
		// ���뷶Χ
		bean.setMerchantDistance("");
		// ���ó���
		bean.setArea(area);
		//
		bean.setLv2Category(Lv2Category);
		request.setData(bean);
		go(CommandID.map.get("DISCOUNT_DIS"), request, false);
//		go(CommandID.map.get("ADCOMMAND"), new Request(), false);
	}

	/**
	 * �����������ò�ѯ����
	 */
	public void setSearchDatas() {
		Request request = new Request();
		Discount_disSearchResBean bean = new Discount_disSearchResBean();
		// ��ǰҳ��
		bean.setPage(i);
		// ÿҳ����
		bean.setRows(10);

		String str = (String) Controller.session.get("condition");
		// ��������
		bean.setMerchName(str);
		// �����û�����
		/*
		 * String point = ""; if (Controller.session.get("pointx") != null) {
		 * point = Controller.session.get("pointx") + "," +
		 * Controller.session.get("pointy");
		 * if(point.trim().equalsIgnoreCase(",")){ point = ""; } }
		 */
		bean.setPoint(point);
		bean.setArea((String) Controller.session.get("AREA_CODE_LEVEL_1"));
		request.setData(bean);
		go(CommandID.map.get("DiscountDisSearch"), request, false);
	}

	private void loadMoreData() {
		i++;
		flag = "no";
		// �������ȡ����
		if (Controller.session.get("condition") == null) {
			setDatas();
		} else {
			setSearchDatas();
		}

	}

	public void initParams() {
		if (pbLinear != null) {
			pbLinear.setVisibility(View.GONE);

		}
		flg = false;
		flag = "first";
		curSize = 0;
		i = 1;
	}

	@Override
	protected void onResume() {

		Controller.session.put("Discount_Tab_Flag", "actMer");
		Log.i(TAG_LOG, "�Ż�ȯ--Discount_Tab_Flag = " + Controller.session.get("Discount_Tab_Flag"));

		TabDiscount_01_MainListActivity.context = this;
		Controller.session.put("Discount_01_dis_MainListActivity", this);// ���������õ�
		area = Controller.session.get("AREA_CODE_LEVEL_1").toString();
		Lv2Category = "";
		// TODO Auto-generated method stub
		super.onResume();

		// adMap = (ArrayList<Bitmap>) Controller.session.get("adMap");
		// if(adMap != null) {
		// for(int i = 0; i < adMap.size(); i++) {
		// imageViews.get(i).setImageBitmap(adMap.get(i));
		// }
		// }

		if (pbLinear != null) {
			pbLinear.setVisibility(View.GONE);

		}
		flg = false;

		if (Controller.session.get("detailsFinished_dis02") != null) {
			Controller.session.remove("detailsFinished_dis02");
		} else {
			flag = "first";

			curSize = 0;

			TabDiscount_01_MainListActivity.jiemianType = "youhuiquan";
			Controller.currentActivity = this;
			if (TabDiscount_01_MainListActivity.tvText != null && TabDiscount_01_MainListActivity.tvText.size() != 0) {
				TabDiscount_01_MainListActivity.tvText.get(0).setTextColor(Color.WHITE);
				TabDiscount_01_MainListActivity.tvText.get(1).setTextColor(Color.GRAY);
				TabDiscount_01_MainListActivity.tvText.get(1).setShadowLayer(0, 0, 0, 0);
			}
			Globals.AppManage = this;
			// ��ʼ�����ݵķ���
			i = 1;
			setDatas();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// if (mTimer != null) {
		// mTimer.cancel();
		// mTimer = null;
		// }
		// try {
		//
		// if (Controller.session.get("adMap")==null) {
		// for (int i = 0; i < adMap.size(); i++) {
		// if(adMap.get(i) != null && !adMap.get(i).isRecycled()){
		// adMap.get(i).recycle();
		// }
		// }
		// }
		// System.gc();
		// }catch(Exception e) {
		// e.printStackTrace();
		// }
		// try {
		// if (adThread != null) {
		// adThread.interrupt();
		// }
		// }catch(Exception e) {
		// e.printStackTrace();
		// }
		if (adapter != null) {
			adapter.onAdapterDestroy();
		}
	}

	// public void sousuoRefresh(String type) {
	// i = 1;
	// flag = "first";
	// Request request = new Request();
	// Discount_disResBean bean = new Discount_disResBean();
	// // ��ǰҳ��
	// bean.setPage(i);
	// // ÿҳ����
	// bean.setRows(10);
	// // �û�����
	// String point = "";
	// if (Controller.session.get("pointx") != null) {
	// point = Controller.session.get("pointx") + ","
	// + Controller.session.get("pointy");
	// if (point.trim().equalsIgnoreCase(",")) {
	// point = "";
	// }
	// }
	// // ����
	// bean.setArea(Controller.session.get("AREA_CODE_LEVEL_1").toString());
	// bean.setPoint(point);
	// // ��������
	// bean.setOrderBy("");
	// // ��������
	// bean.setOrderType("");
	// // ���뷶Χ
	// bean.setMerchantDistance("");
	// // ����
	// bean.setLv2Category(type);
	// request.setData(bean);
	// go(CommandID.map.get("DISCOUNT_DIS"), request, false);
	// }

	public void refresh(String type, String code) {
		Request request = new Request();
		Discount_disResBean bean = new Discount_disResBean();
		// ��ǰҳ��
		bean.setPage(i);
		// ÿҳ����
		bean.setRows(10);
		// �û�����
		/*
		 * String point = ""; if (Controller.session.get("pointx") != null) {
		 * point = Controller.session.get("pointx") + "," +
		 * Controller.session.get("pointy"); if
		 * (point.trim().equalsIgnoreCase(",")) { point = ""; } }
		 */
		bean.setPoint(point);
		// ��������
		bean.setOrderBy("");
		// ��������
		bean.setOrderType("");
		// ���뷶Χ
		bean.setMerchantDistance("");
		// ����
		bean.setArea(Controller.session.get("AREA_CODE_LEVEL_1").toString());
		if (type.equalsIgnoreCase("0")) {// ��Ȧ
			if (flagAreafresh.equalsIgnoreCase("first")) {
				flag = "first";
				flagAreafresh = "";
			}
			bean.setArea(code);
			if (Lv2Category != null) {
				bean.setLv2Category(Lv2Category);
			}
			area = code;
		} else {// ����
			if (code.equalsIgnoreCase("00")) {
				code = "";
			}
			if (flagTypefresh.equalsIgnoreCase("first")) {
				flag = "first";
				flagTypefresh = "";
			}

			bean.setLv2Category(code);
			if (area != null) {
				bean.setArea(area);
			}
			Lv2Category = code;
		}

		request.setData(bean);
		go(CommandID.map.get("DISCOUNT_DIS"), request, true);
	}

	int x = 0;

	@Override
	public void onSuccess(Response response) {

		getAdResult(response) ;

		// TODO Auto-generated method stub
		if (TabDiscount_01_MainListActivity.sousuoLinear.getVisibility() != View.GONE) {
			TabDiscount_01_MainListActivity.sousuoLinear.setVisibility(View.GONE);
		}
		ImageResultBean result = (ImageResultBean) bundle.getSerializable("COUPON_LIST");
		// �õ�����Ӧ����ÿ���Ż�ȯ��Ϣ�����б�
		List<ActVo> obtainList = ((DiscountDisAllResult) result.getRespBean()).getRows();
		Log.i(TAG_LOG, "falg == " + flag + "---x=" + (x++));
		if (flag.equals("first")) {
			if (obtainList != null && obtainList.size() == 0) {
				Toast.makeText(this, "δ�������Ż�ȯ��", Toast.LENGTH_SHORT).show();
			}
			// �õ������Ż�ȯ�б�ĸ���
			totalNum = (int) ((DiscountDisAllResult) result.getRespBean()).getTotal();
			datas = new ArrayList<EleCard_business_Bean>(); // �������Ǳ��λ�õĸ���
			for (ActVo b : obtainList) {
				EleCard_business_Bean bean = new EleCard_business_Bean();
				// �����Ż�ȯ�б��ϵ�ͼƬ
				bean.setPicture(b.getPic_path());
				// ���û����
				bean.setDiscount(b.getAct_name());

				// �����̼�����
				/*
				 * Set<MerchAct> mm = b.getMerchActs(); MerchAct[] mmm = new
				 * MerchAct[] {}; MerchAct[] m = mm.toArray(mmm); if (m.length
				 * != 0) { bean.setName(m[0].getMerchant().getCname()); }
				 */
				bean.setName(b.getMerch_name());
				// ������Ч�ڣ����ʼ����-���ֹ���ڣ�
				String Start_date = b.getStart_date().substring(0, 4) + "." + b.getStart_date().substring(4, 6) + "."
						+ b.getStart_date().substring(6, 8);
				String End_date = b.getEnd_date().substring(0, 4) + "." + b.getEnd_date().substring(4, 6) + "." + b.getEnd_date().substring(6, 8);
				bean.setTime(Start_date + "-" + End_date);
				// �����ѷ�������
				bean.setCount(String.valueOf(b.getIssued_cnt()));
				// ���ûID
				bean.setActId(b.getAct_id());// �ID
				Log.i(TAG_LOG, "�Ż�ȯ�id --->��adapter " + b.getAct_id());
				// �����̼�ID
				bean.setMerchId(b.getMerch_id());
				// ���þ���
				/*
				 * Set<MerchAct> s = b.getMerchActs(); MerchAct[] ss = new
				 * MerchAct[] {}; MerchAct[] sss = s.toArray(ss); if (sss.length
				 * != 0) { bean.setDistance(String.valueOf(sss[0].getMerchant()
				 * .getMpayUser_merchant_distance())); }
				 */
				bean.setDistance(String.valueOf(GetDistance.getDistance(point, b.getCoordinate())));
				datas.add(bean);
			}

			listView = (ListView) findViewById(R.id.id_listdiscount);
			if (loadMoreView == null) {
				loadMoreView = getLayoutInflater().inflate(R.layout.loadmore, null);
				pbLinear = (LinearLayout) loadMoreView.findViewById(R.id.pb_linear);
				listView.addFooterView(loadMoreView); // �����б�ײ���ͼ
				loadMoreView.setVisibility(View.VISIBLE);
			}

			listView.setOnScrollListener(this);
			adapter = new Discount_dis_adapter(this, datas);
			listView.setAdapter(adapter);

			curSize = curSize + datas.size();
		} else {
			if (obtainList != null && obtainList.size() == 0) {
				ToastFactory.getToast(this, "���ݼ�����ϣ�").show() ;
				loadMoreView.setVisibility(View.GONE);
			}
			for (int i = 0; i < obtainList.size(); i++) {
				EleCard_business_Bean bb = new EleCard_business_Bean();
				// ����ͼƬ
				bb.setPicture(obtainList.get(i).getPic_path()); // �Ժ�Ҫ�ĵ�
				// ���û����
				bb.setDiscount(obtainList.get(i).getAct_name());
				// �����̼�����
				/*
				 * Set<MerchAct> mm = obtainList.get(i).getMerchActs();
				 * MerchAct[] mmm = new MerchAct[] {}; MerchAct[] m =
				 * mm.toArray(mmm); // MerchAct[] m = //
				 * (MerchAct[])b.getAct().getMerchActs().toArray(); if (m.length
				 * != 0) { bb.setName(m[0].getMerchant().getCname()); }
				 */
				bb.setName(obtainList.get(i).getMerch_name());
				// ������Ч��
				String Start_date = obtainList.get(i).getStart_date().substring(0, 4) + "." + obtainList.get(i).getStart_date().substring(4, 6) + "."
						+ obtainList.get(i).getStart_date().substring(6, 8);
				String End_date = obtainList.get(i).getEnd_date().substring(0, 4) + "." + obtainList.get(i).getEnd_date().substring(4, 6) + "."
						+ obtainList.get(i).getEnd_date().substring(6, 8);
				bb.setTime(Start_date + "-" + End_date);
				// ���÷�������
				bb.setCount(String.valueOf(obtainList.get(i).getIssued_cnt()));
				// ���ûID
				bb.setActId(obtainList.get(i).getAct_id()); // �Ż�ȯID
				// �����̼�ID
				bb.setMerchId(obtainList.get(i).getMerch_id());
				// ���þ���
				/*
				 * Set<MerchAct> s = obtainList.get(i).getMerchActs();
				 * MerchAct[] ss = new MerchAct[] {}; MerchAct[] sss =
				 * s.toArray(ss); if (sss.length != 0) {
				 * bb.setDistance(String.valueOf(sss[0].getMerchant()
				 * .getMpayUser_merchant_distance())); }
				 */
				bb.setDistance(String.valueOf(GetDistance.getDistance(point, obtainList.get(i).getCoordinate())));
				adapter.addItem(bb);
				adapter.notifyDataSetChanged(); // ���½���

				pbLinear.setVisibility(View.GONE);
			}
			curSize = curSize + obtainList.size();
			flg = false;
		}
	}

	private Bundle bundle ;
	private void getAdResult(Response response) {
		bundle = response.getBundle();
		AdResult adresult = (AdResult) bundle.getSerializable("adResult") ;

		if (adresult != null) {
			ArrayList<View3PagerData> datas = new ArrayList<View3PagerData>();

			if (adresult != null) {
				List<TopContent> list = adresult.getRows();
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						TopContent top = list.get(i);
						int order = Integer.parseInt(top.getDisplay_order());
						String url = top.getImage_path();
						datas.add(new View3PagerData(url, order));
					}
				}
			}

			Collections.sort(datas);

			mView3Pagers.setData(datas);
		}
	}

	@Override
	public void onError(Response response) {

		getAdResult(response) ;

		if (TabDiscount_01_MainListActivity.sousuoLinear.getVisibility() != View.GONE) {
			TabDiscount_01_MainListActivity.sousuoLinear.setVisibility(View.GONE);
		}

		if (curSize == 0) {
			Toast.makeText(this, "��Ǹ��δ��������Ҫ�鿴���Ż�ȯ��", Toast.LENGTH_SHORT).show();
		}
		if (pbLinear != null) {
			pbLinear.setVisibility(View.GONE);
		}
	}

	public void hideStatusBar() {
		// ���ر���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ����ȫ������
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		// ��ô��ڶ���
		Window curWindow = this.getWindow();
		// ����Flag��ʾ
		curWindow.setFlags(flag, flag);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/* menu.add(0, 100, 0, "��ע�̼��б�"); */// ��ʱע��
		return super.onCreateOptionsMenu(menu);
	}

	// �˵��ѡ���¼�
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (Controller.session.get("user") != null) {
			switch (item.getItemId()) {
			case 100:

				Request request = new Request();
				go(CommandID.map.get("TO_Discount_03_MainBusiness"), request, true);
				break;
			}
		} else {
			Toast.makeText(this, "�����ȵ�¼", Toast.LENGTH_SHORT).show();
			Request request = new Request();// TO_USER_REGISTER
			go(CommandID.map.get("USER_LOGIN"), request, false);
		}
		return false;
	}

	private void prepareView() {/*
								 * lay = (LinearLayout)
								 * this.findViewById(R.id.lay); View header =
								 * LayoutInflater
								 * .from(this).inflate(R.layout.header_view,
								 * null);
								 * 
								 * mGallery = (Gallery)
								 * header.findViewById(R.id.home_gallery);
								 * mGalleryAdapter = new GalleryAdapter(this);
								 * mGallery.setAdapter(mGalleryAdapter);
								 * mGallery.setOnItemSelectedListener(new
								 * OnItemSelectedListener() {
								 * 
								 * @Override public void
								 * onItemSelected(AdapterView<?> arg0, View
								 * arg1, int arg2, long arg3) { }
								 * 
								 * @Override public void
								 * onNothingSelected(AdapterView<?> arg0) { }
								 * }); lay.addView(header);
								 */
	}

	private class MyTask extends TimerTask {
		@Override
		public void run() {
			// mHandler.sendEmptyMessage(0);
		}
	}

	// Handler mHandler = new Handler() {
	// @Override
	// public void handleMessage(Message msg) {
	// // TODO Auto-generated method stub
	// super.handleMessage(msg);
	// switch (msg.what) {
	// case 0:
	// viewPager.setCurrentItem(currentItem);// �л���ǰ��ʾ��ͼƬ
	// break;
	// case 1 :
	// /***************���start********************/
	//
	// adMap = (ArrayList<Bitmap>) Controller.session.get("adMap");
	// for (int i = 0; i < 5; i++) {
	// imageViews.get(i).setImageBitmap(adMap.get(i));
	// }
	//
	// break;
	// /***************���end************************/
	// default:
	// break;
	// }
	// }
	// };

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

		this.visibleItemCount = visibleItemCount;
		this.visibleLastIndex = firstVisibleItem + this.visibleItemCount - 1;
		// ������еļ�¼ѡ��������ݼ������������Ƴ��б�ײ���ͼ
		if (curSize == totalNum) {
			Log.i("info", "��ʱ���м�¼���������������������Ƴ��ײ���ͼ.............");
			try {
				flg = true;
				loadMoreView.setVisibility(View.GONE);
				ToastFactory.getToast(this, "���ݼ�����ϣ�").show() ;
			} catch (Exception e) {

			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		int itemsLastIndex = adapter.getCount() - 1;
		int lastIndex = itemsLastIndex + 1;
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex && !flg) {
			Log.i("info", "��ʱ" + visibleLastIndex + "==" + lastIndex + "����ʱ�Զ���������....");
			// ������Զ�����,��������������첽�������ݵĴ���
			pbLinear.setVisibility(View.VISIBLE);
			loadMoreView.setVisibility(View.VISIBLE);
			flg = true;
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					loadMoreData(); // ��������
				}

			}, 2000);
		}
	}

	/************** ���start ********************/
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// scheduledExecutorService =
		// Executors.newSingleThreadScheduledExecutor();
		// // ��Activity��ʾ������ÿ�������л�һ��ͼƬ��ʾ
		// scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 3, 5,
		// TimeUnit.SECONDS);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		// scheduledExecutorService.shutdown();
		super.onStop();
	}

	// �л���ǰ��ʾ��ͼƬ
	/*
	 * private Handler adHandler = new Handler() { public void
	 * handleMessage(Message msg) { viewPager.setCurrentItem(currentItem);//
	 * �л���ǰ��ʾ��ͼƬ
	 * 
	 * }; };
	 */
	/**
	 * �����л�����
	 * 
	 * @author Administrator
	 * 
	 */
	// private class ScrollTask implements Runnable {
	//
	// public void run() {
	// synchronized (viewPager) {
	// System.out.println("currentItem: " + currentItem);
	// currentItem = (currentItem + 1) % imageViews.size();
	// //mHandler.obtainMessage().sendToTarget(); // ͨ��Handler�л�ͼƬ
	// mHandler.sendEmptyMessage(0);
	// }
	// }
	//
	// }

	/**
	 * ��ViewPager��ҳ���״̬�����ı�ʱ����
	 * 
	 * @author Administrator
	 * 
	 */
	// private class MyPageChangeListener implements OnPageChangeListener {
	// private int oldPosition = 0;
	//
	// /**
	// * This method will be invoked when a new page becomes selected.
	// * position: Position index of the new selected page.
	// */
	// public void onPageSelected(int position) {
	// //position = position%5;
	// currentItem = position;
	// //tv_title.setText(titles[position]);
	// dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
	// dots.get(position).setBackgroundResource(R.drawable.dot_focused);
	// oldPosition = position;
	// }
	//
	// public void onPageScrollStateChanged(int arg0) {
	//
	// }
	//
	// public void onPageScrolled(int arg0, float arg1, int arg2) {
	//
	// }
	// }

	/**
	 * ���ViewPagerҳ���������
	 * 
	 * @author Administrator
	 * 
	 */
	// private class MyAdapter extends PagerAdapter {
	//
	//
	// @Override
	// public int getCount() {
	// return imageViews.size();
	// }
	//
	// @Override
	// public Object instantiateItem(View arg0, int arg1) {
	// ((ViewPager) arg0).addView(imageViews.get(arg1));
	// return imageViews.get(arg1);
	// }
	//
	// @Override
	// public void destroyItem(View arg0, int arg1, Object arg2) {
	// ((ViewPager) arg0).removeView((View) arg2);
	// }
	//
	// @Override
	// public boolean isViewFromObject(View arg0, Object arg1) {
	// return arg0 == arg1;
	// }
	//
	// @Override
	// public void restoreState(Parcelable arg0, ClassLoader arg1) {
	//
	// }
	//
	// @Override
	// public Parcelable saveState() {
	// return null;
	// }
	//
	// @Override
	// public void startUpdate(View arg0) {
	//
	// }
	//
	// @Override
	// public void finishUpdate(View arg0) {
	//
	// }
	//
	// @Override
	// public int getItemPosition(Object object) {
	// // TODO Auto-generated method stub
	// return POSITION_NONE;
	// }
	//
	//
	// }
	/***************** ������end *****************************/
}
