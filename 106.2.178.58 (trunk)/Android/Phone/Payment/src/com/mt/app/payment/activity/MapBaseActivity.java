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
 * ��ʾ��������÷�
 */
public abstract class MapBaseActivity extends BaseActivity {

	/**
	 *  MapView �ǵ�ͼ���ؼ�
	 */
	private MapView mMapView = null;
	/**
	 *  ��MapController��ɵ�ͼ���� 
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
	public String[] merchantNames = new String[]{"�̻�1", "�̻�2","�̻�3"};
	public double centerLon = 116.411244, centerLat = 34.963175;
	public String[] merchantLons = new String[]{"116.400244", "116.169199", "116.125541"};
	public String[] merchantLats = new String[]{"34.963175", "34.942821", "34.939723"};
    @Override
    public void onCreateContent(Bundle savedInstanceState) {
    	
        super.onCreateContent(savedInstanceState);
        /**
         * ʹ�õ�ͼsdkǰ���ȳ�ʼ��BMapManager.
         * BMapManager��ȫ�ֵģ���Ϊ���MapView���ã�����Ҫ��ͼģ�鴴��ǰ������
         * ���ڵ�ͼ��ͼģ�����ٺ����٣�ֻҪ���е�ͼģ����ʹ�ã�BMapManager�Ͳ�Ӧ������
         */
        initData();
    } 
    public void showMap(){
        /**
         * ʹ�õ�ͼsdkǰ���ȳ�ʼ��BMapManager.
         * BMapManager��ȫ�ֵģ���Ϊ���MapView���ã�����Ҫ��ͼģ�鴴��ǰ������
         * ���ڵ�ͼ��ͼģ�����ٺ����٣�ֻҪ���е�ͼģ����ʹ�ã�BMapManager�Ͳ�Ӧ������
         */
        try{
        	
        	if(Controller.session.get("pointy") == null || Controller.session.get("pointy").toString().equalsIgnoreCase("")){
        		centerLat = 39.959995;
        		centerLon = 116.380685;
        	}else{
        		centerLat = Double.valueOf(Controller.session.get("pointy").toString());
            	centerLon = Double.valueOf(Controller.session.get("pointx").toString());
        	}
        	
            
            MapApplication app = new MapApplication();
            if (app.mBMapManager == null) {
                app.mBMapManager = new BMapManager(this);
                /**
                 * ���BMapManagerû�г�ʼ�����ʼ��BMapManager
                 */
                app.mBMapManager.init(MapApplication.strKey,new MapApplication.MyGeneralListener());
            }
            /**
              * ����MapView��setContentView()�г�ʼ��,��������Ҫ��BMapManager��ʼ��֮��
              */
            setContentView(R.layout.disdetailmap);
            btnBack = (Button)findViewById(R.id.btn_search_map_back);
            btnBack.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				MapBaseActivity.this.finish();
    			}
    		});
            mMapView = (MapView)findViewById(R.id.bmapView);
            /**
             * ��ȡ��ͼ������
             */
            mMapController = mMapView.getController();
            /**
             *  ���õ�ͼ�Ƿ���Ӧ����¼�  .
             */
            mMapController.enableClick(true);
            /**
             * ���õ�ͼ���ż���
             */
            mMapController.setZoom(12);
            /**
             * ��ʾ�������ſؼ�
             */
            mMapView.setBuiltInZoomControls(true);
            
            initOverlay();
            initLayOutViews();
            /**
             * �趨��ͼ���ĵ�
             */
            GeoPoint p = new GeoPoint((int)(centerLat * 1E6), (int)(centerLon* 1E6));
            mMapController.setCenter(p);
        }catch(Exception ex){
        	ex.printStackTrace();
        }
        
    
    }
    public abstract void initData();//��ʼ������
    public abstract void onPointClick(int index);//��ʼ������
    public void initLayOutViews(){
    	button = new Button(this);
        button.setBackgroundResource(R.drawable.popup);
        button.setText("����ǰ��λ��");
        GeoPoint pt = new GeoPoint ((int)(centerLat*1E6),(int)(centerLon*1E6));
        //�������ֲ���
        layoutParam  = new MapView.LayoutParams(
              //�ؼ���,�̳���ViewGroup.LayoutParams
              MapView.LayoutParams.WRAP_CONTENT,
               //�ؼ���,�̳���ViewGroup.LayoutParams
              MapView.LayoutParams.WRAP_CONTENT,
              //ʹ�ؼ��̶���ĳ������λ��
               pt,
               0,
               -32,
              //�ؼ����뷽ʽ
                MapView.LayoutParams.BOTTOM_CENTER);
        //���View��MapView��
        mMapView.addView(button,layoutParam);
    }
    public void initOverlay(){
    	/**
    	 * �����Զ���overlay
    	 */
         mOverlay = new MyOverlay(getResources().getDrawable(R.drawable.icon_gcoding),mMapView);	
         /**
          * ׼��overlay ����
          */
       //������������
         GeoPoint cp = new GeoPoint ((int)(centerLat*1E6),(int)(centerLon*1E6));
         OverlayItem citem = new OverlayItem(cp,"","");
         citem.setMarker(getResources().getDrawable(R.drawable.icon_gcoding2));
         mOverlay.addItem(citem);
         //��������
         for(int i = 0; i < merchantLats.length; i ++){
        	 try{
        		  double lat = Double.valueOf(merchantLats[i]);
            	  double lon = Double.valueOf(merchantLons[i]);
            	  GeoPoint p = new GeoPoint ((int)(lon*1E6),(int)(lat*1E6));
                  OverlayItem item = new OverlayItem(p,merchantNames[i],"");
                  item.setMarker(getResources().getDrawable(R.drawable.icon_gcoding));
                  mOverlay.addItem(item);
        	 }catch(Exception ex){
        		 ex.printStackTrace();
        	 }
        	
         }    
         /**
          * ��������item���Ա�overlay��reset���������
          */
         mItems = new ArrayList<OverlayItem>();
         mItems.addAll(mOverlay.getAllItem());
         /**
          * ��overlay �����MapView��
          */
         mMapView.getOverlays().add(mOverlay);
         /**
          * ˢ�µ�ͼ
          */
         mMapView.refresh();
         
         /**
          * ���ͼ����Զ���View.
          */
        
         
         viewCache = getLayoutInflater().inflate(R.layout.custom_text_view, null);
         popupInfo = (View) viewCache.findViewById(R.id.popinfo);
         popupText =(TextView) viewCache.findViewById(R.id.textcache);
         
         
         button = new Button(this);
         button.setBackgroundResource(R.drawable.popup);
         
         /**
          * ����һ��popupoverlay
          */
         PopupClickListener popListener = new PopupClickListener(){
			@Override
			public void onClickedPopup(int index) {
				if ( index == 0){
					//����itemλ��icurrClick
					MapBaseActivity.this.onPointClick(icurrClick - 1);
				    mMapView.refresh();
				}
			}
         };
         pop = new PopupOverlay(mMapView,popListener);    
    }
    /**
     * �������Overlay
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
     * �������Overlay
     * @param view
     */
    public void resetOverlay(View view){
    	clearOverlay(null);
    	//����add overlay
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
    	 *  MapView������������Activityͬ������activity����ʱ�����MapView.onPause()
    	 */
    	if(mMapView != null){
    		mMapView.onPause();
    	}
        
        super.onPause();
    }
    
    @Override
    protected void onResume() {
    	/**
    	 *  MapView������������Activityͬ������activity�ָ�ʱ�����MapView.onResume()
    	 */
    	if(mMapView != null){
    		 mMapView.onResume();
    	}
       
        super.onResume();
    }
    
    @Override
    protected void onDestroy() {
    	/**
    	 *  MapView������������Activityͬ������activity����ʱ�����MapView.destroy()
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
