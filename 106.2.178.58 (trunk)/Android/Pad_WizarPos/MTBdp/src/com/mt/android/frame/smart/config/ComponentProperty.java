package com.mt.android.frame.smart.config;

public class ComponentProperty {
	private String id="";// id 当某个控件中包含结果集合需要外部指定时，需要使用此属性
	private String type="";// BUTTON,TEXTVIEW,EDITTEXT,IMAGEBUTTON,IMAGE,LISTVIEW,SPINNER,RadioGroup
	private String left="";
	private String top="";
	private String width="";
	private String height="";
	private String text="";
	private String background="";
	private String commondinvoke="";
	private String second="";   //该属性只有定时器才会有
	private String adapter = "";//配置adapter的名字
	
	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}
	
	public String getAdapter() {
		return adapter;
	}

	public void setAdapter(String adapter) {
		this.adapter = adapter;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getCommondinvoke() {
		return commondinvoke;
	}

	public void setCommondinvoke(String commondinvoke) {
		this.commondinvoke = commondinvoke;
	}
}
