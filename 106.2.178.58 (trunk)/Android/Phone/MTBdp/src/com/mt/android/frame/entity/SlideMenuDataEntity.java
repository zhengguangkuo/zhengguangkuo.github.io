package com.mt.android.frame.entity;

public class SlideMenuDataEntity {
    /*菜单上滑动显示的文本项*/
	private String menuText;
	/*菜单界面文本项下面对应显示的布局名*/
	private int layout;
	
	public String getMenuText() {
		return menuText;
	}
	public void setMenuText(String menuText) {
		this.menuText = menuText;
	}
	public int getLayout() {
		return layout;
	}
	public void setLayout(int layout) {
		this.layout = layout;
	}
	
}