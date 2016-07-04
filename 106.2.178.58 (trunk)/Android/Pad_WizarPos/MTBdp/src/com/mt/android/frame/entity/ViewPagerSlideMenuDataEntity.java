package com.mt.android.frame.entity;

public class ViewPagerSlideMenuDataEntity {
	/*顶部菜单上显示的文本*/
	private String text;
	/*底下的viewPager加载的不同的layout */
	private int viewPagerLayout;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getViewPagerLayout() {
		return viewPagerLayout;
	}
	public void setViewPagerLayout(int viewPagerLayout) {
		this.viewPagerLayout = viewPagerLayout;
	}
	
}
