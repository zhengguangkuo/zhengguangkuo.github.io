package com.mt.android.help.activity;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.mt.android.R;
import com.mt.android.frame.entity.IPagerSlide;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.BaseCommandID;

public class PageFive implements IPagerSlide {
	private Context context;

	public PageFive(Context context) {
		this.context = context;
	}

	@Override
	public int getViewPagerLayOut() {
		// TODO Auto-generated method stub
		return R.layout.help_page2_layout;
	}

	@Override
	public String getPageName() {
		// TODO Auto-generated method stub
		return "菜单5";
	}

	@Override
	public void layOutListenerInit(View layout) {
		// TODO Auto-generated method stub
		Button btn = (Button) layout.findViewById(R.id.btn_page2);
		btn.setText("第五页");
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "第五页", Toast.LENGTH_LONG).show();
			}
		});
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
