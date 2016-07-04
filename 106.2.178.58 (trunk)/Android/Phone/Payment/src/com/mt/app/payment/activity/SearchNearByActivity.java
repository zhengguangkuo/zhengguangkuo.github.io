package com.mt.app.payment.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.miteno.mpay.entity.Merchant;
import com.mt.android.R;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.requestbean.DisMerchMapReq;
import com.mt.app.payment.responsebean.MapSearchPointResult;
import com.mt.app.payment.tools.MapManager;
import com.mt.app.tab.activity.TabDiscount_04_MainListActivity;

public class SearchNearByActivity extends MapBaseActivity {
	MapManager mBMapManager = null;
	// 地图相关
	MapView mMapView = null; // 地图View
	// 搜索相关
	MKSearch mSearch = null; // 搜索模块
	//我的位置
	GeoPoint geoPoint;
	//用户选择的查询半径
	private String ra;
	//响应返回的坐标点
	private String[] ids = null;
	public static String merName = "";
	public static String merID = "";
	public static String merLat = "" ;
	public static String merLon = "" ;	

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		ra = getIntent().getStringExtra("radiu");
		// 发请求
		Request request = new Request();
		DisMerchMapReq re = new DisMerchMapReq();
		re.setPage(1);
		re.setRows(20);
		
		String point = "";
		point = Controller.session.get("pointx") + "," + Controller.session.get("pointy");
		
		if(point.trim().equalsIgnoreCase(",")){
			point = "";
		}
		
		re.setPoint(point);
		re.setMerchantDistance(Double.valueOf(ra));
		re.setActOrDisFlag((String)Controller.session.get("Discount_Tab_Flag")) ;
		request.setData(re);
		go(CommandID.map.get("MapSearchPoint"), request, true);
	}

	@Override
	public void onPointClick(int index) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(SearchNearByActivity.this , TabDiscount_04_MainListActivity.class);
		merID = ids[index];
		System.out.println(ids[index] + "被点击了");
		merName = this.merchantNames[index];
		merLat = this.merchantLats[index] ;
		merLon = this.merchantLons[index] ;
		startActivity(intent);
	}

	@Override
	public void onSuccess(Response response) {
		MapSearchPointResult result = (MapSearchPointResult)response.getData();
		List<Merchant> poList = result.getRows();
		String lons = "";
		String lats = "";
		String merchantNames = "";
		String tmp = "", ids = "";
		for(Merchant m : poList){
			String id = m.getId();   //商家ID
			String name = m.getCname();  //商家名称
			String co = m.getCoordinate();   //商家坐标
			if(co != null && !co.trim().equalsIgnoreCase("")){
				double lat = 0.0;
				double lon = 0.0;
				if(co != null && !co.trim().equalsIgnoreCase(""))
				 {
					lat = Double.valueOf(co.split(",")[0]);
					lon = Double.valueOf(co.split(",")[1]);
				 }
				lons = lons + tmp + (lon + "");
				lats = lats + tmp +(lat + "");
				merchantNames = merchantNames + tmp + name;
				ids = ids + tmp + id;
				if(tmp.equalsIgnoreCase("")){
					tmp = ",";
				}
			}
		}
		
		this.merchantNames = merchantNames.split(",");
		this.merchantLons = lons.split(",");
		this.merchantLats = lats.split(",");
		this.ids = ids.split(",");
		this.showMap();
	}
	
	@Override
	public void onError(Response response) {
		if(response != null){
			if (response != null && response.getData() != null) {
				showToast(this, ((ResponseBean) response.getData()).getMessage());
				finish() ;
			}
		}

	}
	
	


}
