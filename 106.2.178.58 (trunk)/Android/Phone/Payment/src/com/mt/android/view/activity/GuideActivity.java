package com.mt.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.mt.android.R;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.activity.JazzyViewPager.TransitionEffect;
import com.mt.app.payment.activity.PaymentMainActivity;

public class GuideActivity extends BaseActivity {
	private  String SHAREDPREFERENCES_NAME = null;
	SharedPreferences preferences;
	private JazzyViewPager mJazzy;
	private Button jazzybtn;
	private int[] ids = {R.drawable.help1,R.drawable.help2};

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		super.onCreateContent(savedInstanceState);
		setContentView(R.layout.guide);
		setupJazziness(TransitionEffect.CubeOut);
		jazzybtn=(Button)findViewById(R.id.jazzy_btn);
		jazzybtn.setVisibility(View.GONE);
   	    jazzybtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					   Intent intent = new Intent(GuideActivity.this,PaymentMainActivity.class);
			   	       startActivity(intent);
			   	       finish();
				}
		});
	        preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
	        Editor editor = preferences.edit();
	        editor.putBoolean("isFirstIn", false);
	        // 提交修改
	       editor.commit();  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Toggle Fade");
		String[] effects = this.getResources().getStringArray(R.array.jazzy_effects);
		for (String effect : effects)
			menu.add(effect);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().toString().equals("Toggle Fade")) {
			mJazzy.setFadeEnabled(!mJazzy.getFadeEnabled());
		} else {
			TransitionEffect effect = TransitionEffect.valueOf(item.getTitle().toString());
			setupJazziness(effect);
		}
		return true;
	}

	private void setupJazziness(TransitionEffect effect) {
		mJazzy = (JazzyViewPager) findViewById(R.id.jazzy_pager);
		final MainAdapter adapter = new MainAdapter();
		mJazzy.setTransitionEffect(effect);
		mJazzy.setAdapter(adapter);
		mJazzy.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageSelected(int arg0) {
				if (arg0 == adapter.getCount()-1) {// 到最后一张了
					jazzybtn.setVisibility(View.VISIBLE);
			       }else{
			    	   jazzybtn.setVisibility(View.GONE);
			       }
				}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageScrollStateChanged(int arg0) {
			 }
		});
	  }

	private class MainAdapter extends PagerAdapter {
		
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			ImageView image = new ImageView(GuideActivity.this);
			image.setImageResource(ids[position]);
			image.setScaleType(ScaleType.FIT_XY);
			container.removeView(image);
			container.addView(image, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			mJazzy.setObjectForPosition(image, position);
			return image;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object obj) {
			container.removeView(mJazzy.findViewFromObject(position));
			
		}
		@Override
		public int getCount() {
			return ids.length;
		}
		@Override
		public boolean isViewFromObject(View view, Object obj) {
			if (view instanceof OutlineContainer) {
				return ((OutlineContainer) view).getChildAt(0) == obj;
			} else {
				return view == obj;
			}
		}		
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
