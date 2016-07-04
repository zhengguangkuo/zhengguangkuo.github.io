package com.mt.app.payment.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.miteno.coupon.entity.CouponJournal;
import com.miteno.coupon.entity.MerchAct;
import com.miteno.coupon.vo.ActVo;
import com.mt.android.R;
import com.mt.android.frame.DemoTabActivity;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.adapter.EleCard_business_adapter;
import com.mt.app.payment.requestbean.CouponsReturnReqBean;
import com.mt.app.payment.requestbean.EleCard_business_Bean;
import com.mt.app.payment.requestbean.ObtainDiscountReqBean;
import com.mt.app.payment.responsebean.ImageResultBean;
import com.mt.app.payment.responsebean.ObtainDisResult;
import com.mt.app.payment.tools.GetDistance;

public class EleCard_BusinessCardActivity extends BaseActivity implements
		OnScrollListener {
	private String flag = "first"; // 是否是第一次加载数据
	private int i = 1; // 当前所在的页码数
	private EleCard_business_adapter adapter;
	private ListView listView;
	private List<EleCard_business_Bean> datas;
	private View loadMoreView;
	private LinearLayout pbLinear;
	private int visibleLastIndex = 0; // 最后的可视项索引
	private int visibleItemCount; // 当前窗口可见项总数
	private Handler handler = new Handler();
	private int totalNum; // 总数
	private boolean flg = false;
	private int curSize = 0;
	private int deletePosition = -1;
	private int deleteCount = 0; 
	private String point ;
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		
		setContentView(R.layout.elecard_bussinesscard);
		
		if(DemoTabActivity.tvText != null && DemoTabActivity.tvText[0] != null
				&& DemoTabActivity.tvText[1] != null){
			DemoTabActivity.tvText[1].setTextColor(Color.WHITE);
			DemoTabActivity.tvText[0].setTextColor(Color.GRAY);
			DemoTabActivity.tvText[0].setShadowLayer(0, 0, 0, 0);
		}
		
		String px = Controller.session.get("pointx") == null?"":Controller.session.get("pointx").toString();
		String py = Controller.session.get("pointy") == null?"":Controller.session.get("pointy").toString();
		point = px + "," + py;
		if(point.trim().equalsIgnoreCase(",")){
			point = "";
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(pbLinear != null){
			pbLinear.setVisibility(View.GONE);
		}
		flg = false; 
		if(Controller.session.get("detailsFinished_elebus") != null){
			Controller.session.remove("detailsFinished_elebus");
		}else{
			if(DemoTabActivity.tvText != null && DemoTabActivity.tvText[0] != null
					&& DemoTabActivity.tvText[1] != null){
				DemoTabActivity.tvText[1].setTextColor(Color.WHITE);
				DemoTabActivity.tvText[0].setTextColor(Color.GRAY);
				DemoTabActivity.tvText[0].setShadowLayer(0, 0, 0, 0);
			}
			flag = "first";
			curSize = 0;
			i = 1;
			deletePosition = -1;
			deleteCount = 0;
			setDatas();
		}
	}

	private void setDatas() {
		Request request = new Request();
		ObtainDiscountReqBean bean = new ObtainDiscountReqBean();
		if (deleteCount>0) {
			bean.setPage(i-(deleteCount+10)/10);
			i =i-(deleteCount+10)/10;
		} else {
			bean.setPage(i);
		}
		bean.setRows(10);
		//先将坐标写成常量,后面从session里面取
		/*String px = Controller.session.get("pointx") == null?"":Controller.session.get("pointx").toString();
		String py = Controller.session.get("pointy") == null?"":Controller.session.get("pointy").toString();
		String point = px + "," + py;
		if(point.trim().equalsIgnoreCase(",")){
			point = "";
		}*/
		bean.setPoint(point);
		//bean.setPoint(String.valueOf(lat) + "," + String.valueOf(lon));
		request.setData(bean);
		go(CommandID.map.get("EleDiscount_Query"), request, true);
	}

	@Override
	public void onSuccess(Response response) {
		flg = false;
		/***************删除优惠券start********************/
		if (response!=null&&response.getBussinessType()!=null&&response.getBussinessType().equals("delete")) {
			if (deletePosition != -1 ) {
				datas.remove(deletePosition);
				deletePosition = -1;
				totalNum --;
				deleteCount ++;
				DemoTabActivity.tvText[1].setText("电子券（" + totalNum + "张）");
				adapter.notifyDataSetChanged();
				if (deleteCount%10 ==0) {
					int itemsLastIndex = adapter.getCount() - 1;
					int lastIndex = itemsLastIndex + 1;
					if (datas.size()==0) {
						deleteCount = 0;
						i = 0;
						// 如果是自动加载,可以在这里放置异步加载数据的代码
						pbLinear.setVisibility(View.VISIBLE);
						loadMoreView.setVisibility(View.VISIBLE);
						handler.postDelayed(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								loadMoreData(); // 加载数据
							}

						}, 2000);
					}
				}
			}
			Toast.makeText(this, "退领成功！", Toast.LENGTH_LONG).show();
			return ;
		}
		/***************删除优惠券end********************/
		// TODO Auto-generated method stub
		ImageResultBean result = (ImageResultBean) response.getData();
		// 拿到的响应对象（每张优惠券信息）的列表
		final List<ActVo> obtainList = ((ObtainDisResult) result
				.getRespBean()).getRows();

		if (flag.equals("first")) {
			if (obtainList!= null && obtainList.size()==0) {
				Toast.makeText(this, "未搜索到电子券！", Toast.LENGTH_LONG).show();
			}
			// 拿到已领优惠券列表的个数
			totalNum = (int) ((ObtainDisResult) result.getRespBean())
					.getTotal();
			DemoTabActivity.tvText[1].setText("电子券（" + totalNum + "张）");
			datas = new ArrayList<EleCard_business_Bean>(); // 个数就是本次获得的个数
			for (ActVo b : obtainList) {
				EleCard_business_Bean bean = new EleCard_business_Bean();
				// 设置优惠券列表上的图片
				bean.setPicture(b.getPic_path());
				// 设置活动名称
				bean.setDiscount(b.getAct_name());
				// 设置商家名称
				/*Set<MerchAct> mm = b.getAct().getMerchActs();
				MerchAct[] mmm = new MerchAct[] {};
				MerchAct[] m = mm.toArray(mmm);
				// MerchAct[] m =
				// (MerchAct[])b.getAct().getMerchActs().toArray();
				if (m.length != 0) {
					bean.setName(m[0].getMerchant().getCname());
				}*/
				bean.setName(b.getMerch_name()) ;
				
				// 设置有效期（活动起始日期-活动终止日期）
				
				String Start_date =b.getStart_date().substring(0,4)+"."+
						b.getStart_date().substring(4,6)+"."+
						b.getStart_date().substring(6, 8);
				String End_date=b.getEnd_date().substring(0,4)+"."+
						b.getEnd_date().substring(4,6)+"."+
						b.getEnd_date().substring(6, 8);
				bean.setTime(Start_date + "-"+ End_date);
				// 设置已发放数量
				bean.setCount(String.valueOf(b.getIssued_cnt()));
				// 设置优惠券ID
				bean.setCouponId(b.getAct_id()); // 优惠券ID
				// 设置距离
				/*Set<MerchAct> s = b.getAct().getMerchActs();
				MerchAct[] ss = new MerchAct[] {};
				MerchAct[] sss = s.toArray(ss);
				if (sss.length != 0) {
					bean.setDistance(String.valueOf(sss[0].getMerchant()
							.getMpayUser_merchant_distance()));
				}*/
				bean.setDistance(String.valueOf(GetDistance.getDistance(point, b.getCoordinate()))) ;
				bean.setMerchId(b.getMerch_id()) ;
				datas.add(bean);
			}
			
			listView = (ListView) findViewById(R.id.elecard_business_listView);
			if(loadMoreView == null){
				loadMoreView = getLayoutInflater().inflate(R.layout.loadmore, null);
				pbLinear = (LinearLayout) loadMoreView.findViewById(R.id.pb_linear);
				listView.addFooterView(loadMoreView); // 设置列表底部视图
				loadMoreView.setVisibility(View.VISIBLE);
			}
			
			listView.setOnScrollListener(this);
			adapter = new EleCard_business_adapter(this, datas);
			listView.setAdapter(adapter);
			curSize = datas.size(); 
		} else {
			if (deleteCount>0) {				
				for (int j = datas.size()-1,m=0 ;m < 10 - deleteCount%10;j--,m++) {
					datas.remove(j);
				}
			}
			deleteCount = 0;
			deletePosition = -1;
			if (obtainList!= null && obtainList.size()==0) {
				Toast.makeText(this, "数据加载完毕！", Toast.LENGTH_LONG).show();
			}
			for (int i = 0; i < obtainList.size(); i++) {
				EleCard_business_Bean bb = new EleCard_business_Bean();
				// 设置图片
				bb.setPicture(obtainList.get(i).getPic_path()); // 以后要改的
				// 设置活动名称
				bb.setDiscount(obtainList.get(i).getAct_name());
				// 设置商家名称
				/*Set<MerchAct> mm = obtainList.get(i).getAct().getMerchActs();
				MerchAct[] mmm = new MerchAct[] {};
				MerchAct[] m = mm.toArray(mmm);
				// MerchAct[] m =
				// (MerchAct[])b.getAct().getMerchActs().toArray();
				if (m.length != 0) {
					bb.setName(m[0].getMerchant().getCname());
				}*/
				bb.setName(obtainList.get(i).getAct_name()) ;
				// 设置有效期
				String Start_date =obtainList.get(i).getStart_date().substring(0,4)+"."+
						obtainList.get(i).getStart_date().substring(4,6)+"."+
						obtainList.get(i).getStart_date().substring(6, 8);
				String End_date=obtainList.get(i).getEnd_date().substring(0,4)+"."+
						obtainList.get(i).getEnd_date().substring(4,6)+"."+
						obtainList.get(i).getEnd_date().substring(6, 8);
				bb.setTime(Start_date + "-"+ End_date);
				// 设置发放数量
				bb.setCount(String.valueOf(obtainList.get(i)
						.getIssued_cnt()));
				// 设置优惠券ID
				bb.setCouponId(obtainList.get(i).getAct_id()); // 优惠券ID
				// 设置距离
				/*Set<MerchAct> s = obtainList.get(i).getAct().getMerchActs();
				MerchAct[] ss = new MerchAct[] {};
				MerchAct[] sss = s.toArray(ss);
				if (sss.length != 0) {
					bb.setDistance(String.valueOf(sss[0].getMerchant()
							.getMpayUser_merchant_distance()));
				}*/
				bb.setDistance(String.valueOf(GetDistance.getDistance(point, obtainList.get(i).getCoordinate()))) ;
				bb.setMerchId(obtainList.get(i).getMerch_id()) ;
				adapter.addItem(bb);
				adapter.notifyDataSetChanged(); // 更新界面
				pbLinear.setVisibility(View.GONE);
			}
			curSize = datas.size(); 
		}
	}

	@Override
	public void onError(Response response) {
		flg = false;
		/***************删除优惠券start********************/
		if (response!=null&&response.getBussinessType()!=null&&response.getBussinessType().equals("delete")) {
			if (response.getData()!=null&&((ResponseBean)response.getData()).getMessage()!=null) {
				Toast.makeText(this, ((ResponseBean)response.getData()).getMessage(), Toast.LENGTH_LONG).show();
			} else {				
				Toast.makeText(this, "退领失败！", Toast.LENGTH_LONG).show();
			}
			deletePosition = -1;
			return ;
		}
		/***************删除优惠券end********************/
		try{
			loadMoreView.setVisibility(View.GONE);
			}
			catch(Exception e) {
				
			}
		// TODO Auto-generated method stub

	}

	private void loadMoreData() {
		i++;
		flag = "no";
		// 发请求获取数据
		setDatas();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
		this.visibleItemCount = visibleItemCount;
		this.visibleLastIndex = firstVisibleItem + this.visibleItemCount - 1;
		// 如果所有的记录选项等于数据集的条数，则移除列表底部视图
		if (curSize == totalNum ) {
			try{
				loadMoreView.setVisibility(View.GONE);
				}
				catch(Exception e) {
					
				}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
		int itemsLastIndex = adapter.getCount() - 1;
		int lastIndex = itemsLastIndex + 1;
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex  && !flg&&adapter.getCount()<totalNum) {
			// 如果是自动加载,可以在这里放置异步加载数据的代码
			pbLinear.setVisibility(View.VISIBLE);
			loadMoreView.setVisibility(View.VISIBLE);
			flg = true;
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					loadMoreData(); // 加载数据
				}

			}, 2000);
		}
	}
	/**************优惠券删除start********************/
	public void deleteCoupon(CouponsReturnReqBean reqBean,int position){
		deletePosition = position;
		Request request = new Request();
		request.setData(reqBean);
		go(CommandID.map.get("EleDisDelete"), request, false);
	}
	/**************优惠券删除end********************/
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(adapter != null){
			adapter.onAdapterDestroy();
		}
	}
}
