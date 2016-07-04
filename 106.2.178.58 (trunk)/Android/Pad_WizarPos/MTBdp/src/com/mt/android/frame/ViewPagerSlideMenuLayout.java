package com.mt.android.frame;

import java.util.ArrayList;
import java.util.List;

import com.mt.android.R;
import com.mt.android.frame.entity.IPagerSlide;
import com.mt.android.frame.entity.ViewPagerSlideMenuDataEntity;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 顶部滑动菜单布局设置
 * 
 * @Description: 顶部滑动菜单布局设置
 * 
 * @FileName: SlideMenuLayout.java
 * 
 * @Package com.slide.menu
 * 
 * @Author Hanyonglu
 * 
 * @Date 2012-4-20 上午11:17:31
 * 
 * @Version V1.0
 */
public class ViewPagerSlideMenuLayout {
	private int count = 0;

	// 包含菜单的ArrayList
	private ArrayList<TextView> menuList = null;
	private List<IPagerSlide> pageSlideList;
	private Activity activity;
	private TextView textView = null;

	public ViewPagerSlideMenuLayout(Activity activity,
			List<IPagerSlide> pageSlideList) {
		this.activity = activity;
		this.pageSlideList = pageSlideList;
		menuList = new ArrayList<TextView>();
	}

	/**
	 * 顶部滑动菜单布局
	 * 
	 * @param menuTextViews
	 * @param layoutWidth
	 */
	public View getSlideMenuLinerLayout(String[] menuTextViews, int layoutWidth) {
		// 包含TextView的LinearLayout
		LinearLayout menuLinerLayout = new LinearLayout(activity);
		menuLinerLayout.setOrientation(LinearLayout.HORIZONTAL);

		// 参数设置
		LinearLayout.LayoutParams menuLinerLayoutParames = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1);
		menuLinerLayoutParames.gravity = Gravity.CENTER_HORIZONTAL;

		// 添加TextView控件
		for (int i = 0; i < menuTextViews.length; i++) {
			TextView tvMenu = new TextView(activity);
			// 设置标识值
			tvMenu.setTag(menuTextViews[i]);
			tvMenu.setLayoutParams(new LayoutParams(layoutWidth / 4, 30));
			tvMenu.setPadding(22,10, 22, 10);
			tvMenu.setText(menuTextViews[i]);
			tvMenu.setTextColor(Color.WHITE);
			tvMenu.setGravity(Gravity.CENTER_HORIZONTAL);
			tvMenu.setOnClickListener(SlideMenuOnClickListener);

			// 菜单项计数
			count++;

			// 设置第一个菜单项背景
			if (count == 1) {
				tvMenu.setBackgroundResource(R.drawable.menu_bg);
			}

			menuLinerLayout.addView(tvMenu, menuLinerLayoutParames);
			menuList.add(tvMenu);
		}

		return menuLinerLayout;
	}

	public void bgReset(int index) {

		if (index >= menuList.size()) {
			return;
		}

		menuList.get(index).setBackgroundResource(R.drawable.menu_bg);

		for (int i = 0; i < menuList.size(); i++) {
			if (i != index) {
				menuList.get(i).setBackgroundDrawable(null);
			}
		}
	}

	// 单个菜单事件
	OnClickListener SlideMenuOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getTag() != null) {
				String menuTag = v.getTag().toString();

				if (v.isClickable()) {
					textView = (TextView) v;

					textView.setBackgroundResource(R.drawable.menu_bg);

					for (int i = 0; i < menuList.size(); i++) {
						if (!menuTag.equals(menuList.get(i).getText())) {
							menuList.get(i).setBackgroundDrawable(null);
						}
					}

					// 点击菜单时改变内容
					slideMenuOnChange(menuTag);
				}
			}
		}
	};

	// 点击时改内容
	private void slideMenuOnChange(String menuTag) {
		ViewPager mPager = (ViewPager) activity.findViewById(R.id.vPager);
		LayoutInflater inflater = activity.getLayoutInflater();

		for (int i = 0; i < pageSlideList.size(); i++) {
			if (menuTag.equals(pageSlideList.get(i).getPageName())) {
				mPager.setCurrentItem(i);
			}
		}
	}
}