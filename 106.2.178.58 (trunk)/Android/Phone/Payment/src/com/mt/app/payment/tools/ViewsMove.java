package com.mt.app.payment.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.mt.android.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

@SuppressLint("HandlerLeak")
public class ViewsMove extends ViewPager {

	public Context context;
	private View view;

	public View getView() {
		return view;
	}

	public ViewsMove(Context context) {
		super(context);

		this.context = context;
		this.view = LayoutInflater.from(context).inflate(
				R.layout.tool_viewpager_ad_main, null);
		initViewPager();
		// TODO Auto-generated constructor stub
	}

	private ImageView[] imageViews = null;
	private ImageView imageView = null;
	private ViewPager advPager = null;
	private AtomicInteger what = new AtomicInteger(0);
	private boolean isContinue = true;

	private void initViewPager() {
		try {
			advPager = (ViewPager) this.view.findViewById(R.id.adv_pager);
			ViewGroup group = (ViewGroup) this.view
					.findViewById(R.id.viewGroup);

			// 这里存放的是四张广告背景
			List<View> advPics = new ArrayList<View>();

			ImageView img1 = new ImageView(context);
			img1.setBackgroundResource(R.drawable.main_ad);
			advPics.add(img1);

			ImageView img2 = new ImageView(context);
			img2.setBackgroundResource(R.drawable.tu2);
			advPics.add(img2);

			ImageView img3 = new ImageView(context);
			img3.setBackgroundResource(R.drawable.tu3);
			advPics.add(img3);

			ImageView img4 = new ImageView(context);
			img4.setBackgroundResource(R.drawable.tu4);
			advPics.add(img4);

			// 对imageviews进行填充
			imageViews = new ImageView[advPics.size()];
			// 小图标
			for (int i = 0; i < advPics.size(); i++) {
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						12, 12);
				imageView = new ImageView(context);
				imageView.setLayoutParams(params);
				// imageView.set
				imageView.setPadding(5, 5, 5, 5);
				imageViews[i] = imageView;
				if (i == 0) {
					imageViews[i].setBackgroundResource(R.drawable.dot_orenge);
				} else {
					imageViews[i].setBackgroundResource(R.drawable.dot_black);
				}
				group.addView(imageViews[i]);
			}

			advPager.setAdapter(new AdvAdapter(advPics));
			advPager.setOnPageChangeListener(new GuidePageChangeListener());
			advPager.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
					case MotionEvent.ACTION_MOVE:
						isContinue = false;
						break;
					case MotionEvent.ACTION_UP:
						isContinue = true;
						break;
					default:
						isContinue = true;
						break;
					}
					return false;
				}
			});
			new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						if (isContinue) {
							viewHandler.sendEmptyMessage(what.get());
							whatOption();
						}
					}
				}

			}).start();
		} catch (Exception e) {

		}
	}

	private void whatOption() {
		try {
			what.incrementAndGet();
			if (what.get() > imageViews.length - 1) {
				what.getAndAdd(-4);
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {

			}
		} catch (Exception e) {

		}
	}

	private final Handler viewHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			advPager.setCurrentItem(msg.what);
			super.handleMessage(msg);
		}

	};

	private final class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			what.getAndSet(arg0);
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0].setBackgroundResource(R.drawable.dot_orenge);
				if (arg0 != i) {
					imageViews[i].setBackgroundResource(R.drawable.dot_black);
				}
			}

		}

	}

	private final class AdvAdapter extends PagerAdapter {
		private List<View> views = null;

		public AdvAdapter(List<View> views) {
			this.views = views;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {

		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1), 0);
			return views.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
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
