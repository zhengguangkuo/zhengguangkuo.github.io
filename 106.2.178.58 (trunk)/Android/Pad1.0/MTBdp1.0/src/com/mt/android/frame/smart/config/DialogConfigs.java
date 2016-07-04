package com.mt.android.frame.smart.config;

import java.util.List;

public class DialogConfigs {
	/*用于标记每个界面的标记*/
	private String uniquemark;
	/*当前窗体的背景图片*/
	private String background;
	/*控件对象的list集合*/
	private List component;
	
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getUniquemark() {
		return uniquemark;
	}
	public void setUniquemark(String uniquemark) {
		this.uniquemark = uniquemark;
	}
	public List getComponent() {
		return component;
	}
	public void setComponent(List component) {
		this.component = component;
	}
}
