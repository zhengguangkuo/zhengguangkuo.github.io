package com.mt.app.payment.test;

import android.graphics.Bitmap;

public class View3PagerData implements Comparable<View3PagerData> {
	private String mBitmapUrl = null;
	private String mTitle = null;
	private String mClickUrl = null;
	private Integer mOrder ;

	public View3PagerData() {

	}
	
	public View3PagerData(String bitmapurl, int order) {
		mOrder = order ;
		mBitmapUrl = bitmapurl ;
	}

	public View3PagerData(String bitmapurl, String title, String clickUrl) {
		mBitmapUrl = bitmapurl;
		mTitle = title;
		mClickUrl = clickUrl;
	}



	public String getmBitmapUrl() {
		return mBitmapUrl;
	}

	public void setmBitmapUrl(String mBitmapUrl) {
		this.mBitmapUrl = mBitmapUrl;
	}

	public String getmTitle() {
		return mTitle;
	}

	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public String getmClickUrl() {
		return mClickUrl;
	}

	public void setmClickUrl(String mClickUrl) {
		this.mClickUrl = mClickUrl;
	}

	public Integer getmOrder() {
		return mOrder;
	}

	public void setmOrder(Integer mOrder) {
		this.mOrder = mOrder;
	}

	@Override
	public int compareTo(View3PagerData another) {
		return this.getmOrder().compareTo(another.getmOrder()) ;
	}
	
	
}
