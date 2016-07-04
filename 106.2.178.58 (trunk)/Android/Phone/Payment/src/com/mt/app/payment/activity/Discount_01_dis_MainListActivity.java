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
	private String flag = "first"; // 是否是第一次加载数据
	public static String flagAreafresh = "first"; // 是否是第一次地区条件过滤数据
	public static String flagTypefresh = "first"; // 是否是第一次类型条件过滤数据
	private int i = 1; // 当前所在的页码数
	private List<EleCard_business_Bean> datas;
	private View loadMoreView;
	private LinearLayout pbLinear;
	private int visibleLastIndex = 0; // 最后的可视项索引
	private int visibleItemCount; // 当前窗口可见项总数
	private Handler handler = new Handler();
	private int totalNum; // 总数
	private double lat;
	private double lon;
	private ListView listView;
	private Discount_dis_adapter adapter;
	private boolean flg = false;
	private int curSize = 0;
	private String area = "";
	private String Lv2Category = "";
	/*********************** 广告参数 start **************************/
	// private ViewPager viewPager; // android-support-v4中的滑动组件
	// private List<ImageView> imageViews; // 滑动的图片集合
	//
	// //private String[] titles; // 图片标题
	//
	// private List<View> dots; // 图片标题正文的那些点
	// private MyAdapter pageViewAdapter = new MyAdapter();
	// private TextView tv_title;
	// private int currentItem = 0; // 当前图片的索引号
	// private ArrayList<Bitmap> adMap = new ArrayList<Bitmap>();
	// // An ExecutorService that can schedule commands to run after a given
	// delay,
	// // or to execute periodically.
	// private ScheduledExecutorService scheduledExecutorService;
	// private AdThread adThread; //广告下载线程
	private String point;
	private View3Pagers mView3Pagers;

	/*********************** 广告参数 end **************************/
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
		/******************** 广告初始化start *************************/

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
		// // 设置一个监听器，当ViewPager中的页面改变时调用
		// viewPager.setOnPageChangeListener(new MyPageChangeListener());
		// viewPager.setAdapter(pageViewAdapter);// 设置填充ViewPager页面的适配器
		//
		// if ( Controller.session.get("adMap")!=null){//广告图片已经下载
		//
		// imageViews = new ArrayList<ImageView>();
		// adMap = (ArrayList<Bitmap>) Controller.session.get("adMap");
		// // 初始化图片资源
		// for (int i = 0; i < 5; i++) {
		// ImageView imageView = new ImageView(this);
		// if (i<adMap.size()) {
		// imageView.setImageBitmap(adMap.get(i));
		// } else {
		// imageView.setBackgroundResource(R.drawable.set_back);//因为后台传过来的数据小于5条，广告图片设为默认图片
		// }
		// imageView.setScaleType(ScaleType.CENTER_CROP);
		// imageViews.add(imageView);
		// }
		//
		// } else {//广告图片没有下载
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

		/******************** 广告初始化end *************************/

		// 用户坐标
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
		// 当前页数
		bean.setPage(i);
		// 每页条数
		bean.setRows(10);
		/*
		 * // 用户坐标 String point = ""; if (Controller.session.get("pointx") !=
		 * null) { point = Controller.session.get("pointx") + "," +
		 * Controller.session.get("pointy"); if
		 * (point.trim().equalsIgnoreCase(",")) { point = ""; } }
		 */
		bean.setPoint(point);
		// 排序条件
		bean.setOrderBy("");
		// 排序类型
		bean.setOrderType("");
		// 距离范围
		bean.setMerchantDistance("");
		// 设置城市
		bean.setArea(area);
		//
		bean.setLv2Category(Lv2Category);
		request.setData(bean);
		go(CommandID.map.get("DISCOUNT_DIS"), request, false);
//		go(CommandID.map.get("ADCOMMAND"), new Request(), false);
	}

	/**
	 * 检索功能设置查询条件
	 */
	public void setSearchDatas() {
		Request request = new Request();
		Discount_disSearchResBean bean = new Discount_disSearchResBean();
		// 当前页数
		bean.setPage(i);
		// 每页条数
		bean.setRows(10);

		String str = (String) Controller.session.get("condition");
		// 搜索条件
		bean.setMerchName(str);
		// 设置用户坐标
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
		// 发请求获取数据
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
		Log.i(TAG_LOG, "优惠券--Discount_Tab_Flag = " + Controller.session.get("Discount_Tab_Flag"));

		TabDiscount_01_MainListActivity.context = this;
		Controller.session.put("Discount_01_dis_MainListActivity", this);// 搜索功能用到
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
			// 初始化数据的方法
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
	// // 当前页数
	// bean.setPage(i);
	// // 每页条数
	// bean.setRows(10);
	// // 用户坐标
	// String point = "";
	// if (Controller.session.get("pointx") != null) {
	// point = Controller.session.get("pointx") + ","
	// + Controller.session.get("pointy");
	// if (point.trim().equalsIgnoreCase(",")) {
	// point = "";
	// }
	// }
	// // 城市
	// bean.setArea(Controller.session.get("AREA_CODE_LEVEL_1").toString());
	// bean.setPoint(point);
	// // 排序条件
	// bean.setOrderBy("");
	// // 排序类型
	// bean.setOrderType("");
	// // 距离范围
	// bean.setMerchantDistance("");
	// // 类型
	// bean.setLv2Category(type);
	// request.setData(bean);
	// go(CommandID.map.get("DISCOUNT_DIS"), request, false);
	// }

	public void refresh(String type, String code) {
		Request request = new Request();
		Discount_disResBean bean = new Discount_disResBean();
		// 当前页数
		bean.setPage(i);
		// 每页条数
		bean.setRows(10);
		// 用户坐标
		/*
		 * String point = ""; if (Controller.session.get("pointx") != null) {
		 * point = Controller.session.get("pointx") + "," +
		 * Controller.session.get("pointy"); if
		 * (point.trim().equalsIgnoreCase(",")) { point = ""; } }
		 */
		bean.setPoint(point);
		// 排序条件
		bean.setOrderBy("");
		// 排序类型
		bean.setOrderType("");
		// 距离范围
		bean.setMerchantDistance("");
		// 城市
		bean.setArea(Controller.session.get("AREA_CODE_LEVEL_1").toString());
		if (type.equalsIgnoreCase("0")) {// 商圈
			if (flagAreafresh.equalsIgnoreCase("first")) {
				flag = "first";
				flagAreafresh = "";
			}
			bean.setArea(code);
			if (Lv2Category != null) {
				bean.setLv2Category(Lv2Category);
			}
			area = code;
		} else {// 类型
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
		// 拿到的响应对象（每张优惠券信息）的列表
		List<ActVo> obtainList = ((DiscountDisAllResult) result.getRespBean()).getRows();
		Log.i(TAG_LOG, "falg == " + flag + "---x=" + (x++));
		if (flag.equals("first")) {
			if (obtainList != null && obtainList.size() == 0) {
				Toast.makeText(this, "未搜索到优惠券！", Toast.LENGTH_SHORT).show();
			}
			// 拿到已领优惠券列表的个数
			totalNum = (int) ((DiscountDisAllResult) result.getRespBean()).getTotal();
			datas = new ArrayList<EleCard_business_Bean>(); // 个数就是本次获得的个数
			for (ActVo b : obtainList) {
				EleCard_business_Bean bean = new EleCard_business_Bean();
				// 设置优惠券列表上的图片
				bean.setPicture(b.getPic_path());
				// 设置活动名称
				bean.setDiscount(b.getAct_name());

				// 设置商家名称
				/*
				 * Set<MerchAct> mm = b.getMerchActs(); MerchAct[] mmm = new
				 * MerchAct[] {}; MerchAct[] m = mm.toArray(mmm); if (m.length
				 * != 0) { bean.setName(m[0].getMerchant().getCname()); }
				 */
				bean.setName(b.getMerch_name());
				// 设置有效期（活动起始日期-活动终止日期）
				String Start_date = b.getStart_date().substring(0, 4) + "." + b.getStart_date().substring(4, 6) + "."
						+ b.getStart_date().substring(6, 8);
				String End_date = b.getEnd_date().substring(0, 4) + "." + b.getEnd_date().substring(4, 6) + "." + b.getEnd_date().substring(6, 8);
				bean.setTime(Start_date + "-" + End_date);
				// 设置已发放数量
				bean.setCount(String.valueOf(b.getIssued_cnt()));
				// 设置活动ID
				bean.setActId(b.getAct_id());// 活动ID
				Log.i(TAG_LOG, "优惠券活动id --->给adapter " + b.getAct_id());
				// 设置商家ID
				bean.setMerchId(b.getMerch_id());
				// 设置距离
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
				listView.addFooterView(loadMoreView); // 设置列表底部视图
				loadMoreView.setVisibility(View.VISIBLE);
			}

			listView.setOnScrollListener(this);
			adapter = new Discount_dis_adapter(this, datas);
			listView.setAdapter(adapter);

			curSize = curSize + datas.size();
		} else {
			if (obtainList != null && obtainList.size() == 0) {
				ToastFactory.getToast(this, "数据加载完毕！").show() ;
				loadMoreView.setVisibility(View.GONE);
			}
			for (int i = 0; i < obtainList.size(); i++) {
				EleCard_business_Bean bb = new EleCard_business_Bean();
				// 设置图片
				bb.setPicture(obtainList.get(i).getPic_path()); // 以后要改的
				// 设置活动名称
				bb.setDiscount(obtainList.get(i).getAct_name());
				// 设置商家名称
				/*
				 * Set<MerchAct> mm = obtainList.get(i).getMerchActs();
				 * MerchAct[] mmm = new MerchAct[] {}; MerchAct[] m =
				 * mm.toArray(mmm); // MerchAct[] m = //
				 * (MerchAct[])b.getAct().getMerchActs().toArray(); if (m.length
				 * != 0) { bb.setName(m[0].getMerchant().getCname()); }
				 */
				bb.setName(obtainList.get(i).getMerch_name());
				// 设置有效期
				String Start_date = obtainList.get(i).getStart_date().substring(0, 4) + "." + obtainList.get(i).getStart_date().substring(4, 6) + "."
						+ obtainList.get(i).getStart_date().substring(6, 8);
				String End_date = obtainList.get(i).getEnd_date().substring(0, 4) + "." + obtainList.get(i).getEnd_date().substring(4, 6) + "."
						+ obtainList.get(i).getEnd_date().substring(6, 8);
				bb.setTime(Start_date + "-" + End_date);
				// 设置发放数量
				bb.setCount(String.valueOf(obtainList.get(i).getIssued_cnt()));
				// 设置活动ID
				bb.setActId(obtainList.get(i).getAct_id()); // 优惠券ID
				// 设置商家ID
				bb.setMerchId(obtainList.get(i).getMerch_id());
				// 设置距离
				/*
				 * Set<MerchAct> s = obtainList.get(i).getMerchActs();
				 * MerchAct[] ss = new MerchAct[] {}; MerchAct[] sss =
				 * s.toArray(ss); if (sss.length != 0) {
				 * bb.setDistance(String.valueOf(sss[0].getMerchant()
				 * .getMpayUser_merchant_distance())); }
				 */
				bb.setDistance(String.valueOf(GetDistance.getDistance(point, obtainList.get(i).getCoordinate())));
				adapter.addItem(bb);
				adapter.notifyDataSetChanged(); // 更新界面

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
			Toast.makeText(this, "抱歉，未搜索到您要查看的优惠券！", Toast.LENGTH_SHORT).show();
		}
		if (pbLinear != null) {
			pbLinear.setVisibility(View.GONE);
		}
	}

	public void hideStatusBar() {
		// 隐藏标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 定义全屏参数
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		// 获得窗口对象
		Window curWindow = this.getWindow();
		// 设置Flag标示
		curWindow.setFlags(flag, flag);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/* menu.add(0, 100, 0, "关注商家列表"); */// 暂时注掉
		return super.onCreateOptionsMenu(menu);
	}

	// 菜单项被选择事件
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
			Toast.makeText(this, "请您先登录", Toast.LENGTH_SHORT).show();
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
	// viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
	// break;
	// case 1 :
	// /***************广告start********************/
	//
	// adMap = (ArrayList<Bitmap>) Controller.session.get("adMap");
	// for (int i = 0; i < 5; i++) {
	// imageViews.get(i).setImageBitmap(adMap.get(i));
	// }
	//
	// break;
	// /***************广告end************************/
	// default:
	// break;
	// }
	// }
	// };

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

		this.visibleItemCount = visibleItemCount;
		this.visibleLastIndex = firstVisibleItem + this.visibleItemCount - 1;
		// 如果所有的记录选项等于数据集的条数，则移除列表底部视图
		if (curSize == totalNum) {
			Log.i("info", "此时所有记录条数等于数据总条数，移除底部视图.............");
			try {
				flg = true;
				loadMoreView.setVisibility(View.GONE);
				ToastFactory.getToast(this, "数据加载完毕！").show() ;
			} catch (Exception e) {

			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		int itemsLastIndex = adapter.getCount() - 1;
		int lastIndex = itemsLastIndex + 1;
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex && !flg) {
			Log.i("info", "此时" + visibleLastIndex + "==" + lastIndex + "，此时自动加载数据....");
			// 如果是自动加载,可以在这里放置异步加载数据的代码
			pbLinear.setVisibility(View.VISIBLE);
			loadMoreView.setVisibility(View.VISIBLE);
			flg = true;
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					loadMoreData(); // 加载数据
				}

			}, 2000);
		}
	}

	/************** 广告start ********************/
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// scheduledExecutorService =
		// Executors.newSingleThreadScheduledExecutor();
		// // 当Activity显示出来后，每两秒钟切换一次图片显示
		// scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 3, 5,
		// TimeUnit.SECONDS);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		// scheduledExecutorService.shutdown();
		super.onStop();
	}

	// 切换当前显示的图片
	/*
	 * private Handler adHandler = new Handler() { public void
	 * handleMessage(Message msg) { viewPager.setCurrentItem(currentItem);//
	 * 切换当前显示的图片
	 * 
	 * }; };
	 */
	/**
	 * 换行切换任务
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
	// //mHandler.obtainMessage().sendToTarget(); // 通过Handler切换图片
	// mHandler.sendEmptyMessage(0);
	// }
	// }
	//
	// }

	/**
	 * 当ViewPager中页面的状态发生改变时调用
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
	 * 填充ViewPager页面的适配器
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
	/***************** 广告相关end *****************************/
}
