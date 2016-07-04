package com.mt.android.frame;

import java.util.ArrayList;
import java.util.List;

import com.mt.android.R;
import com.mt.android.frame.entity.FrameDataSource;
import com.mt.android.frame.entity.SlideMenuDataEntity;
import com.mt.android.sys.common.view.BaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public abstract class SlideMenuActivity extends BaseActivity {  //���̳У������Լ�д�߼�
	// ���ڴ洢�����˵��ĳ�ʼ�����ݶ����list
	private List<SlideMenuDataEntity> menuEntityList;
	//�洢�����˵����ı�������
	private String[][] menus = null;
	
	private ArrayList<View> menuViews = null;
	private ViewGroup main = null;
	private ViewPager viewPager = null;
	
	//��ǰViewPager������
	private int pagerIndex = 0;
	
	//���ҵ���ͼƬ��ť
	private ImageView imagePrevious;
	private ImageView imageNext;

	@Override
		protected void onCreateContent(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
//			super.onCreateContent(savedInstanceState);
			
			/**
			 *  ������������Ӧ�����������ݣ�
			 *  ͨ��һ����ǣ��ҵ���Ӧ���صĳ�ʼ�����ݣ�
			 *  �����ݴ洢��menuEntityList��
			 */
			
			menuEntityList = initData();
			
			this.menus = new String[menuEntityList.size()/4][4]; 
			
			for(int i = 0 ; i < menuEntityList.size()/4 ;  i++){
				for(int j = 0 ; j < 4 ; j++){
					menus[i][j] = menuEntityList.get(j+4*i).getMenuText();
				}
			}
			
			//��ȡ��������
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			//��ȡ���ڿ��
			int screenWidth =  dm.widthPixels;
			
			menuViews = new ArrayList<View>();
			
			LayoutInflater inflater = getLayoutInflater();
			SlideMenuLayout menu = new SlideMenuLayout(this, menuEntityList);
			
			for(int i = 0 ; i < menus.length ; i++){
				menuViews.add(menu.getSlideMenuLinearLayout(menus[i], screenWidth));
			}
			
			main = (ViewGroup)inflater.inflate(R.layout.sys_frame_slide_menu_main, null);
			this.setContentView(main);
			
			//�����ҵ���ͼƬ��Ӽ����¼�
			imagePrevious = (ImageView)findViewById(R.id.ivPreviousButton);
			imageNext = (ImageView)findViewById(R.id.ivNextButton);
			imagePrevious.setOnClickListener(new ImagePreviousOnClickListener());
			imageNext.setOnClickListener(new ImageNextOnClickListener());
			
			if(menuViews.size() > 1){
				imageNext.setVisibility(View.VISIBLE);
			}
			
			// �����ƶ��˵�������
			ViewGroup llc = (ViewGroup)findViewById(R.id.linearLayoutContent);
			int firstLayout = menuEntityList.get(0).getLayout();
			llc.addView(inflater.inflate(firstLayout, null));
			
			viewPager = (ViewPager)main.findViewById(R.id.slideMenu);  
	        viewPager.setAdapter(new SlideMenuAdapter());  
	        viewPager.setOnPageChangeListener(new SlideMenuChangeListener());
		}
	/**
	 *  �÷������������෵��һ��List<SlideMenuDataEntity>���͵����ݣ�
	 *  ��Щ�����ǳ�ʼ�������õ����ݣ�
	 * @return
	 */
	public abstract List<SlideMenuDataEntity> initData();
	
	class SlideMenuAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return menuViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
		
		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager)container).removeView(menuViews.get(position));
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			((ViewPager)container).addView(menuViews.get(position));
			return menuViews.get(position);
		}
		
	}
	
	//ע�Ử���˵����滬���ļ�����
	class SlideMenuChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			int pageCount = menuViews.size()-1;
			pagerIndex = arg0;
			if(arg0 >= 0 && arg0 < pageCount){
				imageNext.setVisibility(View.VISIBLE);
			}else{
				imageNext.setVisibility(View.INVISIBLE);
			}
			
			if(arg0 > 0 && arg0 <= pageCount){
				imagePrevious.setVisibility(View.VISIBLE);
			}else{
				imagePrevious.setVisibility(View.INVISIBLE);
			}
		}
	}
	//ע��previous�ĵ���ͼƬ�ļ�����
	class ImagePreviousOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			pagerIndex--;
			viewPager.setCurrentItem(pagerIndex);
		}
		
	}
	//ע��next�ĵ���ͼƬ�ļ�����
	class ImageNextOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			pagerIndex++;
			viewPager.setCurrentItem(pagerIndex);
		}
		
	}

}
