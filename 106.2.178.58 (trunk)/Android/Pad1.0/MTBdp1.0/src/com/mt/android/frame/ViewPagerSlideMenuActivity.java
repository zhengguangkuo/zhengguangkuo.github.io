package com.mt.android.frame;

import java.util.ArrayList;
import java.util.List;

import com.mt.android.R;
import com.mt.android.frame.entity.FrameDataSource;
import com.mt.android.frame.entity.IPagerSlide;
import com.mt.android.frame.entity.ViewPagerSlideMenuDataEntity;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Response;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Android实现导航菜单左右滑动效果
 * 
 * @Description: Android实现导航菜单左右滑动效果
 * 
 * @FileName: SlideMenuActivity.java
 * 
 * @Package com.slide.menu
 * 
 * @Author Hanyonglu
 * 
 * @Date 2012-4-20 上午09:15:11
 * 
 * @Version V1.0
 */
public abstract class ViewPagerSlideMenuActivity extends BaseActivity { // 该类被继承，子类自己写逻辑
	private int pageNo;// 当前页码
	private ViewPager mPager;// 页卡内容
	private List<View> listViews; // Tab页面列表
	private List<IPagerSlide> pageSlideList;

	/** Called when the activity is first created. */
	private String[][] menus = null;

	// 当前ViewPager索引
	private int pagerIndex = 0;
	private ArrayList<View> menuViews = null;

	private ViewGroup main = null;
	private ViewPager viewPager = null;
	// 左右导航图片按钮
	private ImageView imagePrevious = null;
	private ImageView imageNext = null;
	public ViewPagerSlideMenuLayout menu;

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// super.onCreateContent(savedInstanceState);

		pageSlideList = loadPages();
		
		int size = pageSlideList.size() % 4 > 0 ? pageSlideList.size() / 4 + 1
				: pageSlideList.size() / 4;
		this.menus = new String[size][4];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < 4; j++) {
				if (i * 4 + j < pageSlideList.size()) {
					menus[i][j] = pageSlideList.get(j + 4 * i).getPageName();
				}
			}
		}

		// 设置无标题窗口
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 取得窗口属性
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 窗口的宽度
		int screenWidth = dm.widthPixels;

		LayoutInflater inflater = getLayoutInflater();
		menuViews = new ArrayList<View>();
		menu = new ViewPagerSlideMenuLayout(this, pageSlideList);

		for (int i = 0; i < menus.length; i++) {
			menuViews.add(menu.getSlideMenuLinerLayout(menus[i], screenWidth));
		}

		main = (ViewGroup) inflater.inflate(
				R.layout.sys_frame_viewpager_slidemenu, null);

		setContentView(main);
		// 左右导航图片按钮
		imagePrevious = (ImageView) findViewById(R.id.ivPreviousButton);
		imageNext = (ImageView) findViewById(R.id.ivNextButton);
		imagePrevious.setOnClickListener(new ImagePreviousOnclickListener());
		imageNext.setOnClickListener(new ImageNextOnclickListener());

		if (menuViews.size() > 1) {
			imageNext.setVisibility(View.VISIBLE);
		}

		// 菜单背景
		// imageMenuBack = (ImageView)findViewById(R.id.ivMenuBackground);
		// imageMenuBack.setVisibility(View.INVISIBLE);

		// 加载移动菜单下内容
		viewPager = (ViewPager) main.findViewById(R.id.slideMenu);
		viewPager.setAdapter(new SlideMenuAdapter());
		viewPager.setOnPageChangeListener(new SlideMenuChangeListener());
		init();
	}

	/**
	 * 该方法用于让子类返回一个List<ViewPagerSlideMenuDataEntity>类型的数据； 这些数据是初始化界面用的数据；
	 * 
	 * @return
	 */
	public abstract List<IPagerSlide> loadPages();

	public void init() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getLayoutInflater();
		for (int i = 0; i < pageSlideList.size(); i++) {
			View view = mInflater.inflate(pageSlideList.get(i)
					.getViewPagerLayOut(), null);
			listViews.add(view);
		}

		mPager.setAdapter(new MyPagerAdapter(listViews));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
		PageInit(mPager);
	}

	public void PageInit(ViewGroup mPager) {
		for (int i = 0; i < pageSlideList.size(); i++) {
			IPagerSlide pager = pageSlideList.get(i);
			//LayoutInflater inflater = LayoutInflater.from(this);
			//View layout = inflater.inflate(pager.getViewPagerLayOut(), null);
			//pager.layOutListenerInit(mPager.(pageSlideList.get(i).getViewPagerLayOut()));
			pager.layOutListenerInit(listViews.get(i));
			pager.initialize();
		}
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
		IPagerSlide ipageslide = pageSlideList.get(this.pageNo);
		ipageslide.onSuccess(response);
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		IPagerSlide ipageslide = pageSlideList.get(this.pageNo);
		ipageslide.onError(response);
	}

	private class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			pageNo = arg0;
			pagerIndex = arg0 / 4;
			if (pagerIndex >= menus.length) {
				return;
			}

			viewPager.setCurrentItem(pagerIndex);
			menu.bgReset(arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	// 滑动菜单数据适配器
	class SlideMenuAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// View v =
			// menuViews.get(0).findViewWithTag(SlideMenuUtil.ITEM_MOBILE);
			// v.setBackgroundResource(R.drawable.menu_bg);
			return menuViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// TODO Auto-generated method stub
			((ViewPager) arg0).removeView(menuViews.get(arg1));
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			// TODO Auto-generated method stub
			((ViewPager) arg0).addView(menuViews.get(arg1));

			return menuViews.get(arg1);
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

		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}
	}

	// 滑动菜单更改事件监听器
	class SlideMenuChangeListener implements OnPageChangeListener {

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
			int pageCount = menuViews.size() - 1;
			pagerIndex = arg0;

			// 显示右边导航图片
			if (arg0 >= 0 && arg0 < pageCount) {
				imageNext.setVisibility(View.VISIBLE);
			} else {
				imageNext.setVisibility(View.INVISIBLE);
			}

			// 显示左边导航图片
			if (arg0 > 0 && arg0 <= pageCount) {
				imagePrevious.setVisibility(View.VISIBLE);
			} else {
				imagePrevious.setVisibility(View.INVISIBLE);
			}
		}
	}

	// 右导航图片按钮事件
	class ImageNextOnclickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			pagerIndex++;
			viewPager.setCurrentItem(pagerIndex);
		}
	}

	// 左导航图片按钮事件
	class ImagePreviousOnclickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			pagerIndex--;
			viewPager.setCurrentItem(pagerIndex);
		}
	}

	class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}
}