package com.mt.android.frame.entity;
/**
 *   初始化tab时，需要的数据bean
 * @author Administrator
 *
 */
public class TabDataBean{
	/*tab的菜单区要显示的文本*/
	private String textMess;
	/*tab的内容区要展示的不同的activity*/
	private Class classes;
	
	public String getTextMess() {
		return textMess;
	}
	public void setTextMess(String textMess) {
		this.textMess = textMess;
	}
	public Class getClasses() {
		return classes;
	}
	public void setClasses(Class classes) {
		this.classes = classes;
	}
	
}
