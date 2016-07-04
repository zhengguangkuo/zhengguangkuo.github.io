package com.mt.android.help.activity;

import java.util.ArrayList;
import java.util.List;

import com.mt.android.frame.ViewPagerSlideMenuActivity;
import com.mt.android.frame.entity.IPagerSlide;
/**
 * 导航菜单左右滑动效果 示例类
 * @author dw
 *
 */
public class SimpleViewPagerSlideMenuActivity extends
		ViewPagerSlideMenuActivity {

	@Override
	public List<IPagerSlide> loadPages() {
		
		//init data
		List<IPagerSlide> pageSlideList = new ArrayList<IPagerSlide>();
		pageSlideList.add(new PageFirst(this));
		pageSlideList.add(new PageSecond(this));
		pageSlideList.add(new PageThird(this));
		pageSlideList.add(new PageForth(this));
		pageSlideList.add(new PageFive(this));
		/*List<ViewPagerSlideMenuDataEntity> list=new ArrayList<ViewPagerSlideMenuDataEntity>();
		ViewPagerSlideMenuDataEntity view1=new ViewPagerSlideMenuDataEntity();
		view1.setText("test1");
		view1.setViewPagerLayout(R.layout.simple_view1);
		ViewPagerSlideMenuDataEntity view2=new ViewPagerSlideMenuDataEntity();
		view2.setText("test2");
		view2.setViewPagerLayout(R.layout.simple_view2);
		ViewPagerSlideMenuDataEntity view3=new ViewPagerSlideMenuDataEntity();
		view3.setText("test3");
		view3.setViewPagerLayout(R.layout.simple_view2);
		ViewPagerSlideMenuDataEntity view4=new ViewPagerSlideMenuDataEntity();
		view4.setText("test4");
		view4.setViewPagerLayout(R.layout.simple_view2);
		ViewPagerSlideMenuDataEntity view5=new ViewPagerSlideMenuDataEntity();
		view5.setText("test5");
		view5.setViewPagerLayout(R.layout.simple_view2);
		list.add(view5);
		list.add(view1);
		list.add(view2);
		list.add(view3);
		list.add(view4);*/
		return pageSlideList;
	}
}
