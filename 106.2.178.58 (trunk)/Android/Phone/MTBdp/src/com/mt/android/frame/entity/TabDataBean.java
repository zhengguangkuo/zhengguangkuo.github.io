package com.mt.android.frame.entity;
/**
 *   ��ʼ��tabʱ����Ҫ������bean
 * @author Administrator
 *
 */
public class TabDataBean{
	/*tab�Ĳ˵���Ҫ��ʾ���ı�*/
	private String textMess;
	/*tab��������Ҫչʾ�Ĳ�ͬ��activity*/
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
