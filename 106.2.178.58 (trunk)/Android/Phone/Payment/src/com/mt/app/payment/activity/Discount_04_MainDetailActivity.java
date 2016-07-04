package com.mt.app.payment.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.miteno.mpay.entity.MerchMpayDiscount;
import com.miteno.mpay.entity.MpayApp;
import com.mt.android.R;
import com.mt.android.frame.DemoTabActivity;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.adapter.SearchMerchDiscount_adapter;
import com.mt.app.payment.requestbean.NearByMerchDisReqBean;
import com.mt.app.payment.requestbean.SeeMerchReqBean;
import com.mt.app.payment.responsebean.DiscountBusQueryResult;
import com.mt.app.payment.responsebean.ImageResultBean;
import com.mt.app.payment.responsebean.SearnearByMerDiscountBean;
/**
 *   �̼��ۿ���Ϣ
 * @author Administrator
 *
 */
public class Discount_04_MainDetailActivity extends BaseActivity implements
OnScrollListener{
	
	private String flag = "first"; // �Ƿ��ǵ�һ�μ�������
	private int i = 1; // ��ǰ���ڵ�ҳ����
	private SearchMerchDiscount_adapter adapter;
	private ListView listView;
	private List<SearnearByMerDiscountBean> datas;
	private View loadMoreView;
	private LinearLayout pbLinear;
	private int visibleLastIndex = 0; // ���Ŀ���������
	private int visibleItemCount; // ��ǰ���ڿɼ�������
	private Handler handler = new Handler();
	private int totalNum; // ����
	private boolean flg = false;
	private int curSize = 0;
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView(R.layout.discount_04_merchant_discount_detail);
		
		if(DemoTabActivity.tvText != null && DemoTabActivity.tvText[0] != null
				&& DemoTabActivity.tvText[1] != null){
			DemoTabActivity.tvText[1].setTextColor(Color.WHITE);
			DemoTabActivity.tvText[0].setTextColor(Color.GRAY);
			DemoTabActivity.tvText[0].setShadowLayer(0, 0, 0, 0);
		}
		
//		setDatas();
		
		DemoTabActivity.imageSee.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(Controller.session.get("user") != null){
					// ������
					Request request = new Request();
					SeeMerchReqBean bean = new SeeMerchReqBean();
					//�̼�ID
					bean.setMerchIds(SearchNearByActivity.merID);
					bean.setHandleType("1");
					request.setData(bean);
					go(CommandID.map.get("SeeMerchant"), request, true);
				}else{  //�û�δ��¼
					Toast.makeText(Discount_04_MainDetailActivity.this, "���ȵ�¼��", Toast.LENGTH_LONG).show();
					Request request = new Request();//TO_USER_REGISTER
					go(CommandID.map.get("USER_LOGIN"), request, false);
				}
			}
		});
	}
	
	private void setDatas() {
		Request request = new Request();
		NearByMerchDisReqBean bean = new NearByMerchDisReqBean();
		bean.setPage(i);
		bean.setRows(10);
		bean.setMerchId(SearchNearByActivity.merID);
		request.setData(bean);
		go(CommandID.map.get("SearchNearBy_merchDiscount_Query"), request, true);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(pbLinear != null){
			pbLinear.setVisibility(View.GONE);
			
		}
		flg = false;  
		curSize = 0;
		
		if(DemoTabActivity.tvText != null && DemoTabActivity.tvText[0] != null
				&& DemoTabActivity.tvText[1] != null){
			DemoTabActivity.tvText[1].setTextColor(Color.WHITE);
			DemoTabActivity.tvText[0].setTextColor(Color.GRAY);
			DemoTabActivity.tvText[0].setShadowLayer(0, 0, 0, 0);
		}
		flag = "first";
		i = 1;
		setDatas();
	}

	@Override
	public void onSuccess(Response response) { 
		if(response.getBussinessType() != null && response.getBussinessType().equals("SeeMerchant")){
			Toast.makeText(this, "��ע�ɹ���", Toast.LENGTH_LONG).show();
		}else{
			ImageResultBean result = (ImageResultBean) response.getData();
			List<MerchMpayDiscount> obtainList = ((DiscountBusQueryResult) result
					.getRespBean()).getRows();

			if (flag.equals("first")) {
				if (obtainList!= null && obtainList.size()==0) {
					Toast.makeText(this, "δ�������̼ң�", Toast.LENGTH_LONG).show();
				}
				// �õ������Ż�ȯ�б�ĸ���
				totalNum = (int) ((DiscountBusQueryResult) result.getRespBean())
						.getTotal();
				datas = new ArrayList<SearnearByMerDiscountBean>(); // �������Ǳ��λ�õĸ���
				for (MerchMpayDiscount b : obtainList) {
					SearnearByMerDiscountBean bean = new SearnearByMerDiscountBean();
					// �����Ż�ȯ�б��ϵ�ͼƬ
					bean.setPic(b.getPic_path());
					//��ϸ˵��
					bean.setDetails(b.getDescription());   
					//�ۿ���
					MpayApp mpayapp = b.getMpayApp();
					if(mpayapp !=null && mpayapp.getApp_name() != null){
						BigDecimal bd = new BigDecimal(b.getDiscount().toString());
						bean.setRate( mpayapp.getApp_name() + "        " + bd.multiply(new BigDecimal("10")) + "��");
					}else{
						bean.setRate("");
					}
					datas.add(bean);
				}
				
				listView = (ListView) findViewById(R.id.search_merch_discount_listView);
				if(loadMoreView == null){
					loadMoreView = getLayoutInflater().inflate(R.layout.loadmore, null);
					pbLinear = (LinearLayout) loadMoreView.findViewById(R.id.pb_linear);
					listView.addFooterView(loadMoreView); // �����б�ײ���ͼ
					loadMoreView.setVisibility(View.VISIBLE);
				}
				
				listView.setOnScrollListener(this);
				adapter = new SearchMerchDiscount_adapter(this, datas);
				listView.setAdapter(adapter);
				curSize = curSize + datas.size(); 
			} else {
				if (obtainList!= null && obtainList.size()==0) {
					Toast.makeText(this, "���ݼ�����ϣ�", Toast.LENGTH_LONG).show();
				}
				for (int i = 0; i < obtainList.size(); i++) {
					SearnearByMerDiscountBean bb = new SearnearByMerDiscountBean();
					// ����ͼƬ
					bb.setPic(obtainList.get(i).getPic_path());
					bb.setDetails(obtainList.get(i).getDescription());  
					BigDecimal bd = new BigDecimal(obtainList.get(i).getDiscount().toString());
					bb.setRate(bd.multiply(new BigDecimal("10")) + "��");
					adapter.addItem(bb);
					adapter.notifyDataSetChanged(); // ���½���

					pbLinear.setVisibility(View.GONE);
				}
				curSize = curSize + obtainList.size(); 
				flg = false;
			}
		}
	}
	
	private void loadMoreData() {
		i++;
		flag = "no";
		// �������ȡ����
		setDatas();
	}


	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		if(response.getBussinessType() != null && response.getBussinessType().equals("SeeMerchant")){
			if(response.getData() != null){
				String mga = ((ResponseBean)response.getData()).getMessage();
				if(mga != null){
					Toast.makeText(this, mga, Toast.LENGTH_LONG).show();
				}
			}
		}
		
		pbLinear.setVisibility(View.GONE);
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
		this.visibleItemCount = visibleItemCount;
		this.visibleLastIndex = firstVisibleItem + this.visibleItemCount - 1;
		// ������еļ�¼ѡ��������ݼ������������Ƴ��б�ײ���ͼ
		if (curSize == totalNum ) {
			try{
				flg = true;
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
				&& visibleLastIndex == lastIndex  && !flg) {
			// ������Զ�����,��������������첽�������ݵĴ���
			pbLinear.setVisibility(View.VISIBLE);
			loadMoreView.setVisibility(View.VISIBLE);
			flg = true;
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					loadMoreData(); // ��������
				}

			}, 2000);
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(adapter != null){
			adapter.onAdapterDestroy();
		}
	}
}
