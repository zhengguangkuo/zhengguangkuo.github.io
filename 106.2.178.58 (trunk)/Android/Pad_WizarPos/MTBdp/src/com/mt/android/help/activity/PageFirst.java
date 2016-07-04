package com.mt.android.help.activity;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mt.android.R;
import com.mt.android.frame.entity.IPagerSlide;
import com.mt.android.sys.mvc.common.Response;

public class PageFirst implements IPagerSlide {
	private Context context;

	public PageFirst(Context context) {
		this.context = context;
	}

	@Override
	public int getViewPagerLayOut() {
		// TODO Auto-generated method stub
		return R.layout.help_page1_layout;
	}

	@Override
	public String getPageName() {
		// TODO Auto-generated method stub
		return "²Ëµ¥1";
	}

	@Override
	public void layOutListenerInit(View layout) {
		// TODO Auto-generated method stub
		TextView tx = (TextView) layout.findViewById(R.id.bindedcard);
		tx.setText("µÚÒ»Ò³");
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub

	}
}
