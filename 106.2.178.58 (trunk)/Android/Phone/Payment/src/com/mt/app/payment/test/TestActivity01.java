package com.mt.app.payment.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.miteno.coupon.entity.TopContent;
import com.mt.android.R;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.responsebean.AdResult;

public class TestActivity01 extends BaseActivity {

	private static final String TAG = TestActivity01.class.getSimpleName();

	private Context mContext = null;

	private ViewPager viewPager;
	private ArrayList<View> pageViews;
	private ImageView imageView;
	private ImageView[] imageViews;
	// 包裹滑动图片LinearLayout
	private ViewGroup main;
	// 包裹小圆点的LinearLayout
	private ViewGroup group;

	private View3Pagers mView3Pagers = null;

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		super.onCreateContent(savedInstanceState);
		// 设置无标题窗口
		mContext = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		initView3Pages();

	}

	private void initView3Pages() {
		setContentView(R.layout.testactivity01);
		mView3Pagers = (View3Pagers) findViewById(R.id.widget);

		ArrayList<View3PagerData> data = new ArrayList<View3PagerData>();
		//
		// Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
		// R.drawable.main_ad);
		// data.add(new View3PagerData(bitmap, "title 0" , "clickUrl 0"));
		//
		// bitmap = BitmapFactory.decodeResource(mContext.getResources(),
		// R.drawable.main_ad);
		// data.add(new View3PagerData(bitmap, "title 1" , "clickUrl 1"));
		//
		// bitmap = BitmapFactory.decodeResource(mContext.getResources(),
		// R.drawable.main_ad);
		// data.add(new View3PagerData(bitmap, "title 2" , "clickUrl 2"));
		//
		// bitmap = BitmapFactory.decodeResource(mContext.getResources(),
		// R.drawable.main_ad);
		// data.add(new View3PagerData(bitmap, "title 3" , "clickUrl 3"));
		//
		// bitmap = BitmapFactory.decodeResource(mContext.getResources(),
		// R.drawable.main_ad);
		// data.add(new View3PagerData(bitmap, "title 4" , "clickUrl 4"));
		//
		// bitmap = BitmapFactory.decodeResource(mContext.getResources(),
		// R.drawable.main_ad);
		// data.add(new View3PagerData(bitmap, "title 5" , "clickUrl 5"));
		//
		// bitmap = BitmapFactory.decodeResource(mContext.getResources(),
		// R.drawable.main_ad);
		// data.add(new View3PagerData(bitmap, "title 6" , "clickUrl 6"));
		//
		mView3Pagers.setData(data);
		
		go(CommandID.map.get("ADCOMMAND"), new Request(), false);
		
	}

	class BitMapThread implements Runnable {

		@Override
		public void run() {
			try {
				wait(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ArrayList<View3PagerData> data = new ArrayList<View3PagerData>();
//			Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.main_ad);
//			data.add(new View3PagerData(bitmap, "title 0", "clickUrl 0"));
//			data.add(new View3PagerData(bitmap, "title 1", "clickUrl 1"));
//			data.add(new View3PagerData(bitmap, "title 2", "clickUrl 2"));
//			data.add(new View3PagerData(bitmap, "title 3", "clickUrl 3"));
//			data.add(new View3PagerData(bitmap, "title 4", "clickUrl 4"));
//			mView3Pagers.setData(data);

			TestActivity01.this.runOnUiThread(this);
		}

	}

	@Override
	public void onSuccess(Response response) {
		
		AdResult adresult = (AdResult) response.getData() ;
		ArrayList<View3PagerData> datas = new ArrayList<View3PagerData>() ;
		
		if(adresult != null) {
			List<TopContent> list = adresult.getRows() ;			
			if(list != null && list.size()>0) {
				for(int i=0; i<list.size(); i++) {
					TopContent top = list.get(i) ;
					int order = Integer.parseInt(top.getDisplay_order()) ;
					String url = top.getImage_path() ;
					datas.add(new View3PagerData(url, order)) ;
				}
			}
		}
		
		Collections.sort(datas) ;
		
		mView3Pagers.setData(datas) ;
	}

	@Override
	public void onError(Response response) {
		ResponseBean bean = (ResponseBean) response.getData() ;
		Toast.makeText(TestActivity01.this, bean.getMessage(), 0).show() ;
	}

}
