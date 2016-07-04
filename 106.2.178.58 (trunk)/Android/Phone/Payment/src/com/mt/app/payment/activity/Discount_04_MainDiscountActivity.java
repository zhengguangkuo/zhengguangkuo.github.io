package com.mt.app.payment.activity;

import java.util.ArrayList;
import java.util.List;
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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
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
import com.mt.app.payment.responsebean.DiscountBusQueryResult;
import com.mt.app.payment.responsebean.ImageResultBean;
import com.mt.app.payment.tools.AdThread;
import com.mt.app.payment.tools.GetDistance;
import com.mt.app.tab.activity.TabDiscount_01_MainListActivity;


public class Discount_04_MainDiscountActivity extends BaseActivity implements
		OnScrollListener {

	Gallery mGallery;
	GalleryAdapter mGalleryAdapter;
	// FlowIndicator mMyView;
	LinearLayout lay = null;
	Timer mTimer;

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
	// 用户坐标
	String point = "";

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		i = 1;

		TabDiscount_01_MainListActivity.jiemianType = "zhekoushangjia";

		setContentView(R.layout.discount_04_maindiscount);

		if (Controller.session.get("pointx") != null) {
			point = Controller.session.get("pointx") + ","
					+ Controller.session.get("pointy");
			if(point.trim().equalsIgnoreCase(",")){
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
		bean.setMerchId(SearchNearByActivity.merID);
		//设置城市
		bean.setArea(area);
		bean.setLv2Category(Lv2Category);
		request.setData(bean);
		go(CommandID.map.get("SearchNearBy_merchDiscount_Query"), request, false);
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

		
		bean.setPoint(point);

		String str = (String) Controller.session.get("shopCondition");
		// 搜索条件
		bean.setMerchName(str);

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
		// TODO Auto-generated method stub
		super.onResume();
		TabDiscount_01_MainListActivity.context = this;
		area = Controller.session.get("AREA_CODE_LEVEL_1").toString();
		Lv2Category = "";
		Controller.session.put("Discount_01_bus_MainListActivity",this);//搜索功能用到
		if(pbLinear != null){
			pbLinear.setVisibility(View.GONE);
			
		}
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
	
		// TODO Auto-generated method stub
		if (TabDiscount_01_MainListActivity.sousuoLinear.getVisibility() != View.GONE) {
			TabDiscount_01_MainListActivity.sousuoLinear
					.setVisibility(View.GONE);
		}
		ImageResultBean result = (ImageResultBean) response.getData();
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
			for (MerchMpayDiscount b : obtainList) {
//				if(SearchNearByActivity.merID.equals(b.getId().getMerch_id())) {		// 过滤
					DiscountBusQueryBean bean = new DiscountBusQueryBean();
					// 设置优惠券列表上的图片
					bean.setPic_path(b.getPic_path());
					// 设置商家名称
					bean.setCname(SearchNearByActivity.merName);
					// appID
					bean.setApp_id(b.getId().getApp_id());
					if(com.mt.app.payment.common.Globals.MERCH_FLAG_AYCOL.equals(b.getId().getApp_id())) {
						bean.setOtherMerchId(b.getOtherMerchId()) ;
					}
					// 折扣商家ID
					bean.setMerch_id(b.getId().getMerch_id());
					// 设置距离
					bean.setMpayUser_merchant_distance(GetDistance.getDistance(point, SearchNearByActivity.merLat + "," + SearchNearByActivity.merLon)) ;
					// 折扣名称
					bean.setApp_name(b.getMpayApp().getApp_name()) ;
					// 折扣率
					bean.setApp_discount(b.getDiscount()) ;
					datas.add(bean);
//				}
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
				
				Toast.makeText(this, "数据加载完毕！", Toast.LENGTH_LONG).show();
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

	@Override
	public void onError(Response response) {
		
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

}
