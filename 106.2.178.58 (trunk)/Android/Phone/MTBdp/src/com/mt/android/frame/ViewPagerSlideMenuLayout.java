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
 * ���������˵���������
 * 
 * @Description: ���������˵���������
 * 
 * @FileName: SlideMenuLayout.java
 * 
 * @Package com.slide.menu
 * 
 * @Author Hanyonglu
 * 
 * @Date 2012-4-20 ����11:17:31
 * 
 * @Version V1.0
 */
public class ViewPagerSlideMenuLayout {
	private int count = 0;

	// �����˵���ArrayList
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
	 * ���������˵�����
	 * 
	 * @param menuTextViews
	 * @param layoutWidth
	 */
	public View getSlideMenuLinerLayout(String[] menuTextViews, int layoutWidth) {
		// ����TextView��LinearLayout
		LinearLayout menuLinerLayout = new LinearLayout(activity);
		menuLinerLayout.setOrientation(LinearLayout.HORIZONTAL);

		// ��������
		LinearLayout.LayoutParams menuLinerLayoutParames = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1);
		menuLinerLayoutParames.gravity = Gravity.CENTER_HORIZONTAL;

		// ���TextView�ؼ�
		for (int i = 0; i < menuTextViews.length; i++) {
			TextView tvMenu = new TextView(activity);
			// ���ñ�ʶֵ
			tvMenu.setTag(menuTextViews[i]);
			tvMenu.setLayoutParams(new LayoutParams(layoutWidth / 4, 30));
			tvMenu.setPadding(22,10, 22, 10);
			tvMenu.setText(menuTextViews[i]);
			tvMenu.setTextColor(Color.WHITE);
			tvMenu.setGravity(Gravity.CENTER_HORIZONTAL);
			tvMenu.setOnClickListener(SlideMenuOnClickListener);

			// �˵������
			count++;

			// ���õ�һ���˵����
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

	// �����˵��¼�
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

					// ����˵�ʱ�ı�����
					slideMenuOnChange(menuTag);
				}
			}
		}
	};

	// ���ʱ������
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