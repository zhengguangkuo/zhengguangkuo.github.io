package com.mt.app.payment.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewDebug.FlagToString;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.miteno.coupon.entity.TopContent;
import com.miteno.mpay.entity.MerchMpayDiscount;
import com.mt.android.R;
import com.mt.android.global.Globals;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.adapter.Discount_bus_adapter;
import com.mt.app.payment.adapter.GalleryAdapter;
import com.mt.app.payment.requestbean.DiscountBusQueryBean;
import com.mt.app.payment.requestbean.Discount_SearchResBean;
import com.mt.app.payment.requestbean.Discount_disResBean;
import com.mt.app.payment.responsebean.AdResult;
import com.mt.app.payment.responsebean.DiscountBusQueryResult;
import com.mt.app.payment.responsebean.ImageResultBean;
import com.mt.app.payment.test.View3PagerData;
import com.mt.app.payment.test.View3Pagers;
import com.mt.app.payment.tools.AdThread;
import com.mt.app.tab.activity.TabDiscount_01_MainListActivity;


public class Discount_01_bus_MainListActivity extends BaseActivity implements
		OnScrollListener {

	private static final String TAG_LOG = Discount_01_bus_MainListActivity.class.getSimpleName() ;
	Gallery mGallery;
	GalleryAdapter mGalleryAdapter;
	// FlowIndicator mMyView;
	LinearLayout lay = null;
//	Timer mTimer;

	private String flag = "first"; // 是否是第一次加载数据
	public static String flagAreafresh = "first"; // 是否是第一次地区条件过滤数据
	public static String flagTypefresh = "first"; // 是否是第一次类型条件过滤数据
	private int i = 1; // 当前所在的页码数
	private List<DiscountBusQueryBean> datas;
	private View loadMoreView;
	private LinearLayout pbLinear;
	private int visibleLastIndex = 0; // 最后的可视项索引
	private int visibleItemCount; // 当前窗口可见项总数
	private Handler handler = new Handler();
	private int totalNum; // 总数
	private int curSize = 0; 
	private double lat;
	private double lon;
	private ListView listView;
	private Discount_bus_adapter adapter;
	private boolean flg = false;
	private String area="";
	private String Lv2Category="";
	private boolean mFlag = false ;		// 保存上次加载状态
	/***********************广告参数 start**************************/
//	private ViewPager viewPager; // android-support-v4中的滑动组件
//	private List<ImageView> imageViews; // 滑动的图片集合
//
//	//private String[] titles; // 图片标题
//	
//	private List<View> dots; // 图片标题正文的那些点
//
//	private TextView tv_title;
//	private int currentItem = 0; // 当前图片的索引号
//	private ArrayList<Bitmap> adMap = new ArrayList<Bitmap>();
//	// An ExecutorService that can schedule commands to run after a given delay,
//	// or to execute periodically.
//	private ScheduledExecutorService scheduledExecutorService;
//	private AdThread adThread;  //广告下载线程
	private View3Pagers mView3Pagers;
	/***********************广告参数 end**************************/

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		i = 1;
		
		try {
			if (TabDiscount_01_MainListActivity.tvText != null
					&& TabDiscount_01_MainListActivity.tvText.size() != 0) {
				TabDiscount_01_MainListActivity.tvText.get(1).setTextColor(
						Color.WHITE);
				TabDiscount_01_MainListActivity.tvText.get(0).setTextColor(
						Color.GRAY);
				TabDiscount_01_MainListActivity.tvText.get(0).setShadowLayer(0,
						0, 0, 0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		TabDiscount_01_MainListActivity.jiemianType = "zhekoushangjia";

		hideStatusBar();
		setContentView(R.layout.discount_01_mainlist);

		prepareView();
//		mTimer = new Timer();
//		mTimer.scheduleAtFixedRate(new MyTask(), 0, 5000);
		Globals.AppManage = this;

		/********************广告初始化start*************************/
			
			
//			dots = new ArrayList<View>();
//			dots.add(findViewById(R.id.v_dot0));
//			dots.add(findViewById(R.id.v_dot1));
//			dots.add(findViewById(R.id.v_dot2));
//			dots.add(findViewById(R.id.v_dot3));
//			dots.add(findViewById(R.id.v_dot4));
//	
//			tv_title = (TextView) findViewById(R.id.tv_title);
//			//tv_title.setText(titles[0]);//
//	
//			viewPager = (ViewPager) findViewById(R.id.vp);
//			
//			// 设置一个监听器，当ViewPager中的页面改变时调用
//			viewPager.setOnPageChangeListener(new MyPageChangeListener());
//			viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
//			
//			if ( Controller.session.get("adMap")!=null){//广告图片已经下载
//				
//				imageViews = new ArrayList<ImageView>();
//				adMap = (ArrayList<Bitmap>) Controller.session.get("adMap");
//				// 初始化图片资源
//				for (int i = 0; i < 5; i++) {
//					ImageView imageView = new ImageView(this);
//					if (i<adMap.size()) {					
//						imageView.setImageBitmap(adMap.get(i));
//					} else {
//						imageView.setBackgroundResource(R.drawable.set_back);//因为后台传过来的数据小于5条，广告图片设为默认图片
//					}
//					imageView.setScaleType(ScaleType.CENTER_CROP);
//					imageViews.add(imageView);
//				}
//				
//			
//			} else {//广告图片没有下载
//				adMap.add(BitmapFactory.decodeResource(getResources(), R.drawable.adload));
//				adMap.add(BitmapFactory.decodeResource(getResources(), R.drawable.adload));
//				adMap.add(BitmapFactory.decodeResource(getResources(), R.drawable.adload));
//				adMap.add(BitmapFactory.decodeResource(getResources(), R.drawable.adload));
//				adMap.add(BitmapFactory.decodeResource(getResources(), R.drawable.adload));
//				imageViews = new ArrayList<ImageView>();
//				for (int i = 0; i < 5; i++) {
//					ImageView imageView = new ImageView(this);
//					imageView.setImageBitmap(adMap.get(i));
//					imageView.setScaleType(ScaleType.CENTER_CROP);
//					imageViews.add(imageView);
//				}
//				adThread = new AdThread(adMap, mHandler);
//				adThread.start();
//			}
		mView3Pagers = (View3Pagers) findViewById(R.id.widget);
		ArrayList<View3PagerData> data = new ArrayList<View3PagerData>();
		mView3Pagers.setData(data);
		/********************广告初始化end*************************/
	}

	private void setDatas() {
		Request request = new Request();
		Discount_disResBean bean = new Discount_disResBean();
		// 当前页数
		bean.setPage(i);
		// 每页条数
		bean.setRows(10);
		// 用户坐标
		String point = "";

		if (Controller.session.get("pointx") != null) {
			point = Controller.session.get("pointx") + ","
					+ Controller.session.get("pointy");
			if(point.trim().equalsIgnoreCase(",")){
				point = "";
			}
		}
		bean.setPoint(point);
		// 排序条件
		bean.setOrderBy("");
		// 排序类型
		bean.setOrderType("");
		// 距离范围
		bean.setMerchantDistance("");
		//设置城市
		bean.setArea(area);
		bean.setLv2Category(Lv2Category);
		request.setData(bean);
		go(CommandID.map.get("DISCOUNT_BUS"), request, false);
	}

	/**
	 * 检索功能设置查询条件
	 */
	public void setSearchDatas() {
		Request request = new Request();
		Discount_SearchResBean bean = new Discount_SearchResBean();
		// 当前页数
		bean.setPage(i);
		// 每页条数
		bean.setRows(10);

		// 用户坐标
		String point = "";

		if (Controller.session.get("pointx") != null) {
			point = Controller.session.get("pointx") + ","
					+ Controller.session.get("pointy");
			if(point.trim().equalsIgnoreCase(",")){
				point = "";
			}
		}
		bean.setPoint(point);

		String str = (String) Controller.session.get("shopCondition");
		// 搜索条件
		bean.setMerchName(str);	
		bean.setArea((String)Controller.session.get("AREA_CODE_LEVEL_1")) ;
		request.setData(bean);
		go(CommandID.map.get("DiscountBusSearch"), request, false);
	}

	private void loadMoreData() {
		i++;
		flag = "no";
		if (Controller.session.get("shopCondition") == null) {
			setDatas();
		} else {
			setSearchDatas();
		}
		// 发请求获取数据

	}
	
	public void initParams(){
		if(pbLinear != null){
			pbLinear.setVisibility(View.GONE);
			
		}
		flg = false; 
		flag = "first";
		curSize = 0;   
		i = 1;
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		Controller.session.put("Discount_Tab_Flag", "disMer") ;
		Log.i(TAG_LOG, "折扣商家 --- Discount_Tab_Flag = " + Controller.session.get("Discount_Tab_Flag")) ;		
		
		TabDiscount_01_MainListActivity.context = this;
		area = Controller.session.get("AREA_CODE_LEVEL_1").toString();
		Lv2Category = "";
		Controller.session.put("Discount_01_bus_MainListActivity",this);//搜索功能用到
		if(pbLinear != null){
			pbLinear.setVisibility(View.GONE);
			
		}
		
		if(!mFlag) {
			flg = false;    
			
			if(Controller.session.get("detailsFinished_02bus") != null){
				Controller.session.remove("detailsFinished_02bus");
			}else{
				flag = "first";
				
				curSize = 0;   
				
				TabDiscount_01_MainListActivity.jiemianType = "zhekoushangjia";
				Controller.currentActivity = this;
				if (TabDiscount_01_MainListActivity.tvText != null
						&& TabDiscount_01_MainListActivity.tvText.size() != 0) {
					TabDiscount_01_MainListActivity.tvText.get(1).setTextColor(
							Color.WHITE);
					TabDiscount_01_MainListActivity.tvText.get(0).setTextColor(
							Color.GRAY);
					TabDiscount_01_MainListActivity.tvText.get(0).setShadowLayer(0, 0,
							0, 0);
				}
				Globals.AppManage = this;
				// 初始化数据的方法
				i = 1;
				setDatas();
			}
		}
	}

//	public void sousuoRefresh(String type) {
//		i = 1;
//		flag = "first";
//		Request request = new Request();
//		Discount_disResBean bean = new Discount_disResBean();
//		// 当前页数
//		bean.setPage(i);
//		// 每页条数
//		bean.setRows(10);
//		// 用户坐标
//		String point = "";
//
//		if (Controller.session.get("pointx") != null) {
//			point = Controller.session.get("pointx") + ","
//					+ Controller.session.get("pointy");
//			
//			if(point.trim().equalsIgnoreCase(",")){
//				point = "";
//			}
//		}
//		//城市
//		bean.setArea(Controller.session.get("AREA_CODE_LEVEL_1").toString());
//		bean.setPoint(point);
//		// 排序条件
//		bean.setOrderBy("");
//		// 排序类型
//		bean.setOrderType("");
//		// 距离范围
//		bean.setMerchantDistance("");
//		// 类型
//		bean.setLv2Category(type);
//		request.setData(bean);
//		go(CommandID.map.get("DISCOUNT_BUS"), request, false);
//	}

	public void refresh(String type, String code) {

		Request request = new Request();
		Discount_disResBean bean = new Discount_disResBean();
		// 当前页数
		bean.setPage(i);
		// 每页条数
		bean.setRows(10);
		// 用户坐标
		String point = "";
		if (Controller.session.get("pointx") != null) {
			point = Controller.session.get("pointx") + ","
					+ Controller.session.get("pointy");
			
			if(point.trim().equalsIgnoreCase(",")){
				point = "";
			}
		}
		bean.setPoint(point);
		//城市
		bean.setArea(Controller.session.get("AREA_CODE_LEVEL_1").toString());
		// 排序条件
		bean.setOrderBy("");
		// 排序类型
		bean.setOrderType("");
		// 距离范围
		bean.setMerchantDistance("");

		if (type.equalsIgnoreCase("0")) {// 商圈

			if (flagAreafresh.equalsIgnoreCase("first")) {
				flag = "first";
				flagAreafresh = "";
			}
			bean.setArea(code);
			if (Lv2Category!=null) {
				bean.setLv2Category(Lv2Category);
			}
			area = code;
		} else {// 类型
			if (code.equalsIgnoreCase("00")){
				code = "";
			}
			if (flagTypefresh.equalsIgnoreCase("first")) {
				flag = "first";
				flagTypefresh = "";
			}
			bean.setLv2Category(code);
			if (area!=null) {
				bean.setArea(area);
			}
			Lv2Category = code;
		}

		request.setData(bean);
		go(CommandID.map.get("DISCOUNT_BUS"), request, true);
	}
	@Override
	public void onSuccess(Response response) {
		
		getAdResult(response) ;
	
		// TODO Auto-generated method stub
		if (TabDiscount_01_MainListActivity.sousuoLinear.getVisibility() != View.GONE) {
			TabDiscount_01_MainListActivity.sousuoLinear
					.setVisibility(View.GONE);
		}
		ImageResultBean result = (ImageResultBean) bundle.getSerializable("SALE_LIST");
		// 拿到的响应对象（每张优惠券信息）的列表
		List<MerchMpayDiscount> obtainList = ((DiscountBusQueryResult) result
				.getRespBean()).getRows();
		if (flag.equals("first")) {
			if (obtainList!= null && obtainList.size()==0) {
				Toast.makeText(this, "未搜索到商家！", Toast.LENGTH_LONG).show();
			}
			// 拿到已领优惠券列表的个数
			totalNum = (int) ((DiscountBusQueryResult) result.getRespBean())
					.getTotal();               
			datas = new ArrayList<DiscountBusQueryBean>(); // 个数就是本次获得的个数
			Log.i(TAG_LOG, "---->totalNum="+totalNum + ",,datas=" + datas.size()) ;
			for (MerchMpayDiscount b : obtainList) {
				DiscountBusQueryBean bean = new DiscountBusQueryBean();
				// 设置优惠券列表上的图片
				bean.setPic_path(b.getPic_path());
				// 设置商家名称
				bean.setCname(b.getMerchant().getCshort());
				// appID
				bean.setApp_id(b.getId().getApp_id());
				// 设置雅酷折扣ID
				if(com.mt.app.payment.common.Globals.MERCH_FLAG_AYCOL.equals(b.getId().getApp_id())) {
					bean.setOtherMerchId(b.getOtherMerchId()) ;
				}
				// 折扣商家ID
				bean.setMerch_id(b.getId().getMerch_id());
				// 设置距离
				bean.setMpayUser_merchant_distance(b.getMerchant()
						.getMpayUser_merchant_distance());
				// 折扣名称
				bean.setApp_name(b.getMpayApp().getApp_name()) ;
				// 折扣率
				bean.setApp_discount(b.getDiscount()) ;
				datas.add(bean);
			}
			
			listView = (ListView) findViewById(R.id.id_listdiscount);
			if(loadMoreView == null){
				loadMoreView = getLayoutInflater().inflate(R.layout.loadmore, null);
				pbLinear = (LinearLayout) loadMoreView.findViewById(R.id.pb_linear);
				listView.addFooterView(loadMoreView); // 设置列表底部视图
				loadMoreView.setVisibility(View.VISIBLE);
			}
			
			listView.setOnScrollListener(this);
			adapter = new Discount_bus_adapter(this, datas);
			listView.setAdapter(adapter);
			Log.i("adapter.size == ",  adapter.getCount()+"");
			Log.i("datas.size == ",  datas.size() + "");
			curSize = curSize + datas.size();  
		} else {
			if (obtainList!= null && obtainList.size()==0) {
				
				Toast.makeText(this, "数据加载完毕！", Toast.LENGTH_SHORT).show();
				loadMoreView.setVisibility(View.GONE);
			}
			for (int i = 0; i < obtainList.size(); i++) {
				DiscountBusQueryBean bb = new DiscountBusQueryBean();
				// 设置图片
				bb.setPic_path(obtainList.get(i).getPic_path());
				// 设置商家名称
				bb.setCname(obtainList.get(i).getMerchant().getCshort());
				// 设置距离
				bb.setMpayUser_merchant_distance(obtainList.get(i)
						.getMerchant().getMpayUser_merchant_distance());
				// appID
				bb.setApp_id(obtainList.get(i).getId().getApp_id());
				// 设置雅酷折扣ID
				if(com.mt.app.payment.common.Globals.MERCH_FLAG_AYCOL.equals(obtainList.get(i).getId().getApp_id())) {
					bb.setOtherMerchId(obtainList.get(i).getOtherMerchId()) ;
				}
				// 折扣商家ID
				bb.setMerch_id(obtainList.get(i).getId().getMerch_id());
				// 折扣名称
				bb.setApp_name(obtainList.get(i).getMpayApp().getApp_name()) ;
				// 折扣率
				bb.setApp_discount(obtainList.get(i).getDiscount()) ;
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
		AdResult adresult = (AdResult) bundle.getSerializable("adResult");

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
			TabDiscount_01_MainListActivity.sousuoLinear
					.setVisibility(View.GONE);
		}

		if (pbLinear != null) {			
			pbLinear.setVisibility(View.GONE);
		}
		
		if(curSize == 0){
			Toast.makeText(this, "抱歉，未搜索到您要查看的折扣商家！", Toast.LENGTH_LONG).show();
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
		/*menu.add(0, 100, 0, "关注商家列表");*/ // 暂时注掉
		return super.onCreateOptionsMenu(menu);
	}

	// 菜单项被选择事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (Controller.session.get("user") != null) {
			switch (item.getItemId()) {
			case 100:

				Request request = new Request();
				go(CommandID.map.get("TO_Discount_03_MainBusiness"), request,
						true);
				break;
			}
		} else {
			Toast.makeText(this, "请您先登录", Toast.LENGTH_LONG).show();
			Request request = new Request();//TO_USER_REGISTER
			go(CommandID.map.get("USER_LOGIN"), request, false);
		}
		return false;
	}

	private void prepareView() {
		/*lay = (LinearLayout) this.findViewById(R.id.lay);
		View header = LayoutInflater.from(this).inflate(R.layout.header_view,
				null);

		mGallery = (Gallery) header.findViewById(R.id.home_gallery);
		mGalleryAdapter = new GalleryAdapter(this);
		mGallery.setAdapter(mGalleryAdapter);
		mGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		lay.addView(header);*/
	}

	private class MyTask extends TimerTask {
		@Override
		public void run() {
			//mHandler.sendEmptyMessage(0);
		}
	}

//	Handler mHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			// TODO Auto-generated method stub
//			super.handleMessage(msg);
//			switch (msg.what) {
//			case 0:
//				viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
//				break;
//			case 1 :  
//				/***************广告start********************/
//				
//				adMap = (ArrayList<Bitmap>) Controller.session.get("adMap");
//				
//				for (int i = 0; i < 5; i++) {
//					imageViews.get(i).setImageBitmap(adMap.get(i));
//				}
//				
//				break;
//				/***************广告end************************/
//			default:
//				break;
//			}
//		}
//	};

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if(adapter != null) {
			Log.i("adapter.size == ",  "onScroll =" + adapter.getCount()+"");
			Log.i("datas.size == ",  "onScroll=" + datas.size() + "");
		}
        if(adapter != null){
			
			Log.i("ttt", + adapter.getCount() + ";" + totalItemCount + ";" + firstVisibleItem + ";" + visibleItemCount);
		}else {
			Log.i("ttt",  "null;" + totalItemCount + ";" + firstVisibleItem + ";" + visibleItemCount);
		}
		
		// TODO Auto-generated method stub
	
		this.visibleItemCount = visibleItemCount;
		this.visibleLastIndex = firstVisibleItem + this.visibleItemCount - 1;
		// 如果所有的记录选项等于数据集的条数，则移除列表底部视图
		if (curSize == totalNum ) {      
			try{
				//loadMoreView.setVisibility(Visibility.)
				//listView.removeFooterView(loadMoreView);
				flg = true;      
				loadMoreView.setVisibility(View.GONE);
				Toast.makeText(this, "数据加载完毕！", Toast.LENGTH_SHORT).show();
				}
				catch(Exception e) {
					
				}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
		if(adapter != null) {
			Log.i("adapter.size == ",  "onScrollStateChanged" + adapter.getCount()+"");
			Log.i("datas.size == ",  "onScrollStateChanged" + datas.size() + "");
		}
		
		int itemsLastIndex = adapter.getCount() - 1;
		int lastIndex = itemsLastIndex + 1;
		
		if(visibleLastIndex != lastIndex){
			Log.i("index", "visibleLastIndex="+visibleLastIndex+ "lastIndex" + lastIndex);
		}
		
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex  && !flg) {
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
	/**************广告start********************/
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//		// 当Activity显示出来后，每两秒钟切换一次图片显示
//		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 3, 5, TimeUnit.SECONDS);
	}
	
	@Override
	protected void onPause() {
		super.onPause() ;
		mFlag = true ;
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
//		scheduledExecutorService.shutdown();
		super.onStop();
	}
	/***************广告end***************************/
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		if(mTimer != null){
//			mTimer.cancel();
//			mTimer = null;
//		}
//		try {
//			
//			if (Controller.session.get("adMap")==null) {				
//				for (int i = 0; i < adMap.size(); i++) {
//					if(adMap.get(i) != null && !adMap.get(i).isRecycled()){
//						adMap.get(i).recycle();
//					}
//				}
//			}
//			System.gc();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {			
//			if (adThread != null) {			
//				adThread.interrupt();
//			}
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		if(adapter != null){
//			adapter.onAdapterDestroy();
//		}
	}
	/*****************广告相关start********************/
	// 切换当前显示的图片
		/*private Handler adHandler = new Handler() {
			public void handleMessage(Message msg) {
					viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
				
			};
		};*/
	/**
	 * 换行切换任务
	 * 
	 * @author Administrator
	 * 
	 */
//	private class ScrollTask implements Runnable {
//
//		public void run() {
//			synchronized (viewPager) {
//				System.out.println("currentItem: " + currentItem);
//				currentItem = (currentItem + 1) % imageViews.size();
//				mHandler.sendEmptyMessage(0);
//			}
//		}
//
//	}

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author Administrator
	 * 
	 */
//	private class MyPageChangeListener implements OnPageChangeListener {
//		private int oldPosition = 0;
//
//		/**
//		 * This method will be invoked when a new page becomes selected.
//		 * position: Position index of the new selected page.
//		 */
//		public void onPageSelected(int position) {
//			currentItem = position;
//			//tv_title.setText(titles[position]);
//			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
//			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
//			oldPosition = position;
//		}
//
//		public void onPageScrollStateChanged(int arg0) {
//
//		}
//
//		public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//		}
//	}

	/**
	 * 填充ViewPager页面的适配器
	 * 
	 * @author Administrator
	 * 
	 */
//	private class MyAdapter extends PagerAdapter {
//
//		
//		@Override
//		public int getCount() {
//			return imageViews.size();
//		}
//
//		@Override
//		public Object instantiateItem(View arg0, int arg1) {
//			((ViewPager) arg0).addView(imageViews.get(arg1));
//			return imageViews.get(arg1);
//		}
//
//		@Override
//		public void destroyItem(View arg0, int arg1, Object arg2) {
//			((ViewPager) arg0).removeView((View) arg2);
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			return arg0 == arg1;
//		}
//
//		@Override
//		public void restoreState(Parcelable arg0, ClassLoader arg1) {
//
//		}
//
//		@Override
//		public Parcelable saveState() {
//			return null;
//		}
//
//		@Override
//		public void startUpdate(View arg0) {
//
//		}
//
//		@Override
//		public void finishUpdate(View arg0) {
//
//		}
//	}
	/*****************广告相关end*****************************/
}
