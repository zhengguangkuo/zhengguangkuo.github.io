package com.mt.app.payment.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mt.android.R;
import com.mt.android.global.Globals;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.adapter.FlowIndicator;
import com.mt.app.payment.adapter.GalleryAdapter;
import com.mt.app.payment.requestbean.ObtainDiscountReqBean;
import com.mt.app.payment.view.ExpandTabView;
import com.mt.app.payment.view.ViewLeft;
import com.mt.app.payment.view.ViewMiddle;
import com.mt.app.payment.view.ViewMiddle02;
import com.mt.app.payment.view.ViewRight;

public class Discount_01_MainListActivity extends BaseActivity {

	Gallery mGallery;
	GalleryAdapter mGalleryAdapter;
	// FlowIndicator mMyView;
	LinearLayout lay = null;
	Timer mTimer;

	// ---------------------------
	// private ExpandTabView expandTabView;
	// private ArrayList<View> mViewArray = new ArrayList<View>();
	// private ViewLeft viewLeft;
	// private ViewMiddle viewMiddle;
	// private ViewRight viewRight;
	//
	// private ExpandTabView expandTabView2;
	// private ArrayList<View> mViewArray2 = new ArrayList<View>();
	// private ViewLeft viewLeft2;
	// private ViewMiddle02 viewMiddle2;
	// private ViewRight viewRight2;

	// private ExpandTabView expandTabView3;
	// private ArrayList<View> mViewArray3 = new ArrayList<View>();
	// private ViewLeft viewLeft3;
	// private ViewMiddle viewMiddle3;
	// private ViewRight viewRight3;
	// ------------------------------------------

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);

		// 隐藏标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 定义全屏参数
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		// 获得窗口对象
		Window curWindow = this.getWindow();
		// 设置Flag标示
		curWindow.setFlags(flag, flag);

		setContentView(R.layout.discount_01_mainlist);

		prepareView();
		mTimer = new Timer();
		mTimer.scheduleAtFixedRate(new MyTask(), 0, 5000);
		// -------可弹出下拉布局选择框初始化-----------------------------------
		// initView();
		// initVaule();
		// initListener();
		Globals.AppManage = this;

		// //初始化数据的方法
		// setDatas();
	}

	// private void setDatas() {
	// Request request = new Request();
	// ObtainDiscountReqBean bean = new ObtainDiscountReqBean();
	// bean.setPage(i);
	// bean.setRows(10);
	// bean.setPoint(String.valueOf(lat) + "," + String.valueOf(lon));
	// request.setData(bean);
	// go(CommandID.map.get("EleDiscount_Query"), request, false);
	// }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Globals.AppManage = this;
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
		final ListView listView = (ListView) findViewById(R.id.id_listdiscount);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Request request = new Request();
				go(CommandID.map.get("TO_Discount_02_MainDetail"), request,
						false);
				// Intent intent = new Intent();
				// intent.setClass(MainActivity.this, XiangxiActivity.class);
				// intent.putExtra("itemText",
				// String.valueOf(listView.getItemAtPosition(position)));
				// Log.v("itemText",
				// String.valueOf(listView.getItemAtPosition(position)));
				// startActivity(intent);
			}
		});
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*menu.add(0, 100, 0, "关注商家列表");*/ // 暂时注掉
		return super.onCreateOptionsMenu(menu);
	}

	// 菜单项被选择事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 100:

			Request request = new Request();
			go(CommandID.map.get("TO_Discount_03_MainBusiness"), request, false);
			// Intent intent = new Intent();
			// intent.setClass(MainActivity.this, TuangouActivity.class);
			// startActivity(intent);
			Toast.makeText(this, "开始菜单被点击了", Toast.LENGTH_LONG).show();
			break;
		}

		return false;
	}

	private void prepareView() {/*
		lay = (LinearLayout) this.findViewById(R.id.lay);
		View header = LayoutInflater.from(this).inflate(R.layout.header_view,
				null);

		mGallery = (Gallery) header.findViewById(R.id.home_gallery);
		// mMyView = (FlowIndicator) header.findViewById(R.id.myView);
		mGalleryAdapter = new GalleryAdapter(this);
		// mMyView.setCount(mGalleryAdapter.getCount());
		mGallery.setAdapter(mGalleryAdapter);
		mGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				// mMyView.setSeletion(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		lay.addView(header);
	*/}

	private class MyTask extends TimerTask {
		@Override
		public void run() {
			mHandler.sendEmptyMessage(0);
		}
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				int curPos = mGallery.getSelectedItemPosition();
				if (curPos < mGalleryAdapter.getCount() - 1) {

					curPos++;

				} else {
					curPos = 0;
				}

				mGallery.setLayoutAnimation(new LayoutAnimationController(
						AnimationUtils.loadAnimation(
								Discount_01_MainListActivity.this,
								R.anim.gallery_in)));

				mGallery.setSelection(curPos, true);
				MotionEvent e1 = MotionEvent.obtain(SystemClock.uptimeMillis(),
						SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN,
						89.333336f, 265.33334f, 0);
				MotionEvent e2 = MotionEvent.obtain(SystemClock.uptimeMillis(),
						SystemClock.uptimeMillis(), MotionEvent.ACTION_UP,
						300.0f, 238.00003f, 0);

				mGallery.onFling(e1, e2, -1300, 0);
				break;

			default:
				break;
			}
		}
	};

	// ------可弹出下拉框与布局相关代码-----begin----------------------------------------------------

	// private void initView() {
	//
	// expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view1);
	// viewLeft = new ViewLeft(this);
	// viewMiddle = new ViewMiddle(this);
	// viewRight = new ViewRight(this);
	//
	// expandTabView2 = (ExpandTabView) findViewById(R.id.expandtab_view2);
	// viewLeft2 = new ViewLeft(this);
	// viewMiddle2 = new ViewMiddle02(this);
	// viewRight2 = new ViewRight(this);
	//
	// // expandTabView3 = (ExpandTabView) findViewById(R.id.expandtab_view3);
	// // viewLeft3 = new ViewLeft(this);
	// // viewMiddle3 = new ViewMiddle(this);
	// // viewRight3 = new ViewRight(this);
	//
	// }
	//
	// private void initVaule() {
	//
	// // mViewArray.add(viewLeft);
	// mViewArray.add(viewMiddle);
	// // mViewArray.add(viewRight);
	// ArrayList<String> mTextArray = new ArrayList<String>();
	// mTextArray.add("区域");
	// expandTabView.setValue(mTextArray, mViewArray);
	// // expandTabView.setTitle(viewLeft.getShowText(), 0);
	// // expandTabView.setTitle(viewMiddle.getShowText(), 1);
	// // expandTabView.setTitle(viewRight.getShowText(), 2);
	//
	// mViewArray2.add(viewMiddle2);
	// ArrayList<String> mTextArray2 = new ArrayList<String>();
	// mTextArray2.add("类别");
	// expandTabView2.setValue(mTextArray2, mViewArray2);
	//
	// // mViewArray3.add(viewMiddle3);
	// // ArrayList<String> mTextArray3 = new ArrayList<String>();
	// // mTextArray3.add("附近");
	// // expandTabView3.setValue(mTextArray3, mViewArray3);
	//
	// }
	//
	// private void initListener() {
	//
	// viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {
	//
	// @Override
	// public void getValue(String distance, String showText) {
	// onRefresh(viewLeft, showText);
	// }
	// });
	//
	// viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {
	//
	// @Override
	// public void getValue(String showText) {
	//
	// onRefresh(viewMiddle,showText);
	//
	// }
	// });
	//
	// viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {
	//
	// @Override
	// public void getValue(String distance, String showText) {
	// onRefresh(viewRight, showText);
	// }
	// });
	//
	// //-------------------------------------------------------------
	// viewLeft2.setOnSelectListener(new ViewLeft.OnSelectListener() {
	//
	// @Override
	// public void getValue(String distance, String showText) {
	// onRefresh2(viewLeft2, showText);
	// }
	// });
	//
	// viewMiddle2.setOnSelectListener(new ViewMiddle02.OnSelectListener() {
	//
	// @Override
	// public void getValue(String showText) {
	//
	// onRefresh2(viewMiddle2,showText);
	//
	// }
	// });
	//
	// viewRight2.setOnSelectListener(new ViewRight.OnSelectListener() {
	//
	// @Override
	// public void getValue(String distance, String showText) {
	// onRefresh2(viewRight2, showText);
	// }
	// });
	//
	// //--------------------------------------------------------------
	// // viewLeft3.setOnSelectListener(new ViewLeft.OnSelectListener() {
	// //
	// // @Override
	// // public void getValue(String distance, String showText) {
	// // onRefresh3(viewLeft3, showText);
	// // }
	// // });
	// //
	// // viewMiddle3.setOnSelectListener(new ViewMiddle.OnSelectListener() {
	// //
	// // @Override
	// // public void getValue(String showText) {
	// //
	// // onRefresh3(viewMiddle3,showText);
	// //
	// // }
	// // });
	// //
	// // viewRight3.setOnSelectListener(new ViewRight.OnSelectListener() {
	// //
	// // @Override
	// // public void getValue(String distance, String showText) {
	// // onRefresh3(viewRight3, showText);
	// // }
	// // });
	//
	// }
	//
	// private void onRefresh(View view, String showText) {
	//
	// expandTabView.onPressBack();
	// int position = getPositon(view);
	// if (position >= 0 && !expandTabView.getTitle(position).equals(showText))
	// {
	// expandTabView.setTitle(showText, position);
	// }
	// Toast.makeText(Discount_01_MainListActivity.this, showText,
	// Toast.LENGTH_SHORT).show();
	//
	// }
	//
	// private void onRefresh2(View view, String showText) {
	// expandTabView2.onPressBack();
	// int position = getPositon2(view);
	// if (position >= 0 && !expandTabView2.getTitle(position).equals(showText))
	// {
	// expandTabView2.setTitle(showText, position);
	// }
	// Toast.makeText(Discount_01_MainListActivity.this, showText,
	// Toast.LENGTH_SHORT).show();
	//
	// }
	//
	// // private void onRefresh3(View view, String showText) {
	// // expandTabView3.onPressBack();
	// // int position = getPositon3(view);
	// // if (position >= 0 &&
	// !expandTabView3.getTitle(position).equals(showText)) {
	// // expandTabView3.setTitle(showText, position);
	// // }
	// // Toast.makeText(Discount_01_MainListActivity.this, showText,
	// Toast.LENGTH_SHORT).show();
	// //
	// // }
	//
	// private int getPositon(View tView) {
	// for (int i = 0; i < mViewArray.size(); i++) {
	// if (mViewArray.get(i) == tView) {
	// return i;
	// }
	// }
	//
	// return -1;
	// }
	// private int getPositon2(View tView) {
	//
	// for (int i = 0; i < mViewArray2.size(); i++) {
	// if (mViewArray2.get(i) == tView) {
	// return i;
	// }
	// }
	// return -1;
	// }
	//
	// // private int getPositon3(View tView) {
	// //
	// // for (int i = 0; i < mViewArray3.size(); i++) {
	// // if (mViewArray3.get(i) == tView) {
	// // return i;
	// // }
	// // }
	// // return -1;
	// // }
	// //-------可弹出下拉框与布局相关代码------end--------------------
	//
	// @Override
	// public void onBackPressed() {
	//
	// if (!expandTabView.onPressBack()) {
	// finish();
	// }
	// if (!expandTabView2.onPressBack()) {
	// finish();
	// }
	// // if (!expandTabView3.onPressBack()) {
	// // finish();
	// // }
	//
	// }

}
