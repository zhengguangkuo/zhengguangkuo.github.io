package com.mt.app.payment.activity;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.mt.android.R;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.payment.common.BMapUtil;
import com.mt.app.payment.common.MapApplication;

/**
 * 演示覆盖物的用法
 */
public abstract class MapDetailsBaseActivity extends BaseActivity {

	/**
	 *  MapView 是地图主控件
	 */
	private MapView mMapView = null;
	/**
	 *  用MapController完成地图控制 
	 */
	private MapController mMapController = null;
	private MyOverlay mOverlay = null;
	private PopupOverlay   pop  = null;
	private ArrayList<OverlayItem>  mItems = null; 
	private TextView  popupText = null;
	private View viewCache = null;
	private View popupInfo = null;
	private Button button = null;
	private MapView.LayoutParams layoutParam = null;
	private OverlayItem mCurItem = null;
	private int icurrClick = 0;
	private Button btnBack;
	public String[] merchantNames ;
	public double centerLon , centerLat ;
	public String[] merchantLons ;
	public String[] merchantLats ;
    @Override
    public void onCreateContent(Bundle savedInstanceState) {
    	
        super.onCreateContent(savedInstanceState);
        /**
         * 使用地图sdk前需先初始化BMapManager.
         * BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
         * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
         */
        initData();
    } 
    public void showMap(){
        /**
         * 使用地图sdk前需先初始化BMapManager.
         * BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
         * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
         */
        try{
        	
        	centerLat = Double.valueOf(merchantLats[0]);
        	centerLon = Double.valueOf(merchantLons[0]);
        	
            
            MapApplication app = new MapApplication();
            if (app.mBMapManager == null) {
                app.mBMapManager = new BMapManager(this);
                /**
                 * 如果BMapManager没有初始化则初始化BMapManager
                 */
                app.mBMapManager.init(MapApplication.strKey,new MapApplication.MyGeneralListener());
            }
            /**
              * 由于MapView在setContentView()中初始化,所以它需要在BMapManager初始化之后
              */
            setContentView(R.layout.disdetailmap);
            btnBack = (Button)findViewById(R.id.btn_search_map_back);
            btnBack.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				MapDetailsBaseActivity.this.finish();
    			}
    		});
            mMapView = (MapView)findViewById(R.id.bmapView);
            /**
             * 获取地图控制器
             */
            mMapController = mMapView.getController();
            /**
             *  设置地图是否响应点击事件  .
             */
            mMapController.enableClick(true);
            /**
             * 设置地图缩放级别
             */
            mMapController.setZoom(14);
            /**
             * 显示内置缩放控件
             */
            mMapView.setBuiltInZoomControls(true);
            
            initOverlay();
            initLayOutViews();
            /**
             * 设定地图中心点
             */
            GeoPoint p = new GeoPoint((int)(centerLon * 1E6), (int)(centerLat* 1E6));
            mMapController.setCenter(p);
        }catch(Exception ex){
        	ex.printStackTrace();
        }
        
    
    }
    public abstract void initData();//初始化数据
    public abstract void onPointClick(int index);//初始化数据
    public void initLayOutViews(){
    	button = new Button(this);
        button.setBackgroundResource(R.drawable.popup);
        button.setText(merchantNames[0]);
        GeoPoint pt = new GeoPoint ((int)(centerLon*1E6),(int)(centerLat*1E6));
        //创建布局参数
        layoutParam  = new MapView.LayoutParams(
              //控件宽,继承自ViewGroup.LayoutParams
              MapView.LayoutParams.WRAP_CONTENT,
               //控件高,继承自ViewGroup.LayoutParams
              MapView.LayoutParams.WRAP_CONTENT,
              //使控件固定在某个地理位置
               pt,
               0,
               -32,
              //控件对齐方式
                MapView.LayoutParams.BOTTOM_CENTER);
        //添加View到MapView中
        mMapView.addView(button,layoutParam);
    }
    public void initOverlay(){
    	/**
    	 * 创建自定义overlay                                         
    	 */
         mOverlay = new MyOverlay(getResources().getDrawable(R.drawable.icon_gcoding),mMapView);	
         /**
          * 准备overlay 数据
          */
       //加入中心坐标
         GeoPoint cp = new GeoPoint ((int)(centerLon*1E6),(int)(centerLat*1E6));
         OverlayItem citem = new OverlayItem(cp,"","");
         citem.setMarker(getResources().getDrawable(R.drawable.icon_gcoding));
         mOverlay.addItem(citem);
         
         /**
          * 保存所有item，以便overlay在reset后重新添加
          */
         mItems = new ArrayList<OverlayItem>();
         mItems.addAll(mOverlay.getAllItem());
         /**
          * 将overlay 添加至MapView中
          */
         mMapView.getOverlays().add(mOverlay);
         /**
          * 刷新地图
          */
         mMapView.refresh();
         
         /**
          * 向地图添加自定义View.
          */
        
         
         viewCache = getLayoutInflater().inflate(R.layout.custom_text_view, null);
         popupInfo = (View) viewCache.findViewById(R.id.popinfo);
         popupText =(TextView) viewCache.findViewById(R.id.textcache);
         
         
         button = new Button(this);
         button.setBackgroundResource(R.drawable.popup);
         
         /**
          * 创建一个popupoverlay
          */
         PopupClickListener popListener = new PopupClickListener(){
			@Override
			public void onClickedPopup(int index) {
				if ( index == 0){
					//更新item位置icurrClick
					MapDetailsBaseActivity.this.onPointClick(icurrClick - 1);
				    mMapView.refresh();
				}
			}
         };
         pop = new PopupOverlay(mMapView,popListener);    
    }
    /**
     * 清除所有Overlay
     * @param view
     */
    public void clearOverlay(View view){
    	if(mOverlay != null){
    		mOverlay.removeAll();
    	}
    	
    	if (pop != null){
            pop.hidePop();
    	}
    	//mMapView.removeView(button);
    	if(mMapView != null){
    		mMapView.refresh();
    	}
    	
    }
    /**
     * 重新添加Overlay
     * @param view
     */
    public void resetOverlay(View view){
    	clearOverlay(null);
    	//重新add overlay
    	if(mOverlay != null){
    		mOverlay.addItem(mItems);
    	}
    	
    	if(mMapView != null){
    		mMapView.refresh();
    	}
    
    }
   
    @Override
    protected void onPause() {
    	/**
    	 *  MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
    	 */
    	if(mMapView != null){
    		mMapView.onPause();
    	}
        
        super.onPause();
    }
    
    @Override
    protected void onResume() {
    	/**
    	 *  MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
    	 */
    	if(mMapView != null){
    		 mMapView.onResume();
    	}
       
        super.onResume();
    }
    
    @Override
    protected void onDestroy() {
    	/**
    	 *  MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
    	 */
    	if(mMapView != null){
   		 mMapView.destroy();
   	    }
      
        super.onDestroy();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	
    	if(mMapView != null){
    		mMapView.onSaveInstanceState(outState);
    	}
    	
    	
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	
    	if(mMapView != null){
    		mMapView.onRestoreInstanceState(savedInstanceState);
    	}
    	
    }
    
    private class MyOverlay extends ItemizedOverlay{

		public MyOverlay(Drawable defaultMarker, MapView mapView) {
			super(defaultMarker, mapView);
		}
		

		@Override
		public boolean onTap(int index){
			OverlayItem item = getItem(index);
			mCurItem = item ;
			
			if (index != 0){
				   icurrClick = index;
				   popupText.setText(getItem(index).getTitle());
				   Bitmap[] bitMaps={		
					    BMapUtil.getBitmapFromView(popupInfo)		
				    };
				    pop.showPopup(bitMaps,item.getPoint(),32);
			}
			return true;
		}
		
		@Override
		public boolean onTap(GeoPoint pt , MapView mMapView){
			if (pop != null){
                pop.hidePop();
                //mMapView.removeView(button);
			}
			return false;
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
