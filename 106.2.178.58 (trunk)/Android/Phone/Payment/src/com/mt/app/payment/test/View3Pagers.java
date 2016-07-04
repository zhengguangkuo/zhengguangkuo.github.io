package com.mt.app.payment.test;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.mt.android.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class View3Pagers extends FrameLayout {
	private static final String TAG = View3Pagers.class.getSimpleName();

	private Context mContext = null;
	private ArrayList<View> mPageViews = null;
	private ViewGroup mPointsViewGroup = null;
	private ViewPager mViewPager = null;
	private ImageLoader imageLoader; 

	public View3Pagers(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		Log.d(TAG, "View3Pagers(context, attrs) init() ");
		init();
	}

	public View3Pagers(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		Log.d(TAG, "View3Pagers(context)  init() ");
		init();
	}

	private void init() {
		LayoutInflater.from(mContext).inflate(R.layout.view3pagers, this, true);

		// 圆点
		mPointsViewGroup = (ViewGroup) findViewById(R.id.just_use_for_widget_view3pagers_PointsLayout);

		mViewPager = (ViewPager) findViewById(R.id.just_use_for_widget_view3pagers_ViewPager);
		mViewPager.setAdapter(getGuidePageAdapter());
		mViewPager.setOnPageChangeListener(getGuidePageChangeListener());

		startTimer();
		
		imageLoader = new ImageLoader(mContext); 
	}

	private GuidePageAdapter mGuidePageAdapter = null;

	private GuidePageAdapter getGuidePageAdapter() {
		if (mGuidePageAdapter == null) {
			mGuidePageAdapter = new GuidePageAdapter();
		}
		return mGuidePageAdapter;
	}

	private GuidePageChangeListener mGuidePageChangeListener = null;

	private GuidePageChangeListener getGuidePageChangeListener() {
		if (mGuidePageChangeListener == null) {
			mGuidePageChangeListener = new GuidePageChangeListener();
		}
		return mGuidePageChangeListener;
	}

	private ArrayList<View3PagerData> mView3PagerDataList = null;
	private ImageView[] mPointViews = null;

	public void setData(ArrayList<View3PagerData> view3PagerDataList) {
		mView3PagerDataList = view3PagerDataList;
		if (mView3PagerDataList == null || mView3PagerDataList.size() == 0) {
//			Log.e(TAG, "error !");
//			return;
			mView3PagerDataList.add(new View3PagerData()) ;
		}

		init();

		mPageViews = new ArrayList<View>();
		for (int m = 0; m < mView3PagerDataList.size(); m++) {
			View3PagerData data = mView3PagerDataList.get(m);

			View view = LayoutInflater.from(mContext).inflate(R.layout.view3pagers_subview, null);
			ImageView img = (ImageView) view.findViewById(R.id.just_use_for_widget_view3pagers_Img);
			TextView titleTv = (TextView) view.findViewById(R.id.just_use_for_widget_view3pagers_Title);
			titleTv.setVisibility(View.GONE) ;
			// 下面的暂时用不到
//			img.setImageBitmap(data.getBitmap());
//			titleTv.setText(data.getTitle());
//			final String clickUrl = data.getClickUrl();
//
//			view.setClickable(true);
//			view.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					openUrl(clickUrl);
//				}
//			});
			
			if(!TextUtils.isEmpty(data.getmBitmapUrl())) {
				imageLoader.DisplayImage(data.getmBitmapUrl(),(Activity)mContext, img);				
			}
			
			mPageViews.add(view);
		}

		mPointsViewGroup.removeAllViews() ;
		mPointViews = new ImageView[mView3PagerDataList.size()];
		for (int i = 0; i < mView3PagerDataList.size(); i++) {
			ImageView imageView = new ImageView(mContext);
			imageView.setLayoutParams(new LayoutParams(10, 10));
			imageView.setPadding(20, 0, 20, 0);
			mPointViews[i] = imageView;

			if (i == 0) {
				// 默认选中第一张图片
				mPointViews[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				mPointViews[i].setBackgroundResource(R.drawable.page_indicator);
			}

			mPointsViewGroup.addView(mPointViews[i]);
		}
		if(mPageViews.size()<2) {
			mPointsViewGroup.setVisibility(View.GONE) ;
		}
	}

	private void openUrl(String url) {
		Toast.makeText(mContext, "openUrl " + url, Toast.LENGTH_SHORT).show();
	}

	// 指引页面数据适配器
	class GuidePageAdapter extends PagerAdapter {

		private int mCurPos = 0;
		private int mPagesCount = 0;

		@Override
		public int getCount() {
			mPagesCount = mPageViews == null ? 0 : mPageViews.size();
			return mPagesCount;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return POSITION_NONE;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// TODO Auto-generated method stub
			((ViewPager) arg0).removeView(mPageViews == null ? null : mPageViews.get(arg1));
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			// TODO Auto-generated method stub
			Log.d(TAG, "instantiateItem() arg1 = " + arg1);
			((ViewPager) arg0).addView(mPageViews == null ? null : mPageViews.get(arg1));
			return mPageViews == null ? null : mPageViews.get(arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub
			Log.d(TAG, "startUpdate()");
		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}
	}

	// 指引页面更改事件监听器
	class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			if (mPointViews == null || mPointViews.length == 0) {
				return;
			}
			mIsSwitched = true;

			Log.d(TAG, "onPageSelected() arg0 = " + arg0);

			for (int i = 0; i < mPointViews.length; i++) {
				mPointViews[arg0].setBackgroundResource(R.drawable.page_indicator_focused);

				if (arg0 != i) {
					mPointViews[i].setBackgroundResource(R.drawable.page_indicator);
				}
			}
		}
	}

	// 定时器
	private boolean mIsSwitched = false;
	private Timer mTimer = null;

	private void startTimer() {
		if (mTimer == null) {
			mTimer = new Timer(true);
		}
		mTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(TOAST_MSG_TIMER);
			}
		}, 1000, 3000);
	}

	private void stopTimer() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
	}

	private final int TOAST_MSG_TIMER = 0;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TOAST_MSG_TIMER:
				Log.d(TAG, "mIsSwitched = " + mIsSwitched);
				if (mIsSwitched) {
					mIsSwitched = false;
				} else {
					int cur = mViewPager.getCurrentItem();
					Log.d(TAG, "cur = " + cur);
					int count = mPageViews == null ? 0 : mPageViews.size();
					if (cur < count - 1) {
						++cur;
					} else {
						cur = 0;
					}
					mViewPager.setCurrentItem(cur, true);
					getGuidePageAdapter().notifyDataSetChanged() ;
				}
			}
		}
	};

	protected void finalize() throws Throwable {
		stopTimer();
	};

}
