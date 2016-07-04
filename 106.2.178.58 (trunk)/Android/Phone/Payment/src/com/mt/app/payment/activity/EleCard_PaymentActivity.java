package com.mt.app.payment.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.miteno.coupon.entity.CouponJournal;
import com.miteno.coupon.entity.MerchAct;
import com.miteno.mpay.entity.MpayUserApp;
import com.mt.android.R;
import com.mt.android.frame.DemoTabActivity;
import com.mt.android.protocol.http.client.ImageHttpClient;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.sys.util.StringUtil;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.adapter.Discount_dis_adapter;
import com.mt.app.payment.adapter.EleCard_business_adapter;
import com.mt.app.payment.adapter.EleCard_payment_adapter;
import com.mt.app.payment.requestbean.EleCard_business_Bean;
import com.mt.app.payment.requestbean.EleCard_paycard_Bean;
import com.mt.app.payment.requestbean.ObtainDiscountReqBean;
import com.mt.app.payment.requestbean.ObtainPayAppReqBean;
import com.mt.app.payment.responsebean.AppBind_DataBean;
import com.mt.app.payment.responsebean.ImageResultBean;
import com.mt.app.payment.responsebean.ObtainDisResult;
import com.mt.app.payment.responsebean.ObtainPayAppResult;
import com.mt.app.tab.activity.TabEleCardActivity;

public class EleCard_PaymentActivity extends BaseActivity implements  
		OnScrollListener {
	private String flag = "first"; // �Ƿ��ǵ�һ�μ�������
	private int i = 1; // ��ǰ���ڵ�ҳ����

	private EleCard_payment_adapter adapter;
	private ListView listView;
	private List<EleCard_paycard_Bean> datas;

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
		
		setContentView(R.layout.elecard_paymentcard);
		
		if(DemoTabActivity.tvText != null && DemoTabActivity.tvText[0] != null
				&& DemoTabActivity.tvText[1] != null){
			DemoTabActivity.tvText[0].setTextColor(Color.WHITE);
			DemoTabActivity.tvText[1].setTextColor(Color.GRAY);
			DemoTabActivity.tvText[1].setShadowLayer(0, 0, 0, 0);
		}

		// �������ʼ������
//		setDatas();
	}

	private void setDatas() {
		Request request = new Request();
		ObtainPayAppReqBean bean = new ObtainPayAppReqBean();
		bean.setPage(i);
		bean.setRows(10);
		bean.setAppType("0");   //֧�����Ӧ��
		request.setData(bean);
		go(CommandID.map.get("EleApp_Query"), request, true);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if(Controller.session.get("unbind_success_to_tab") != null 
				&& Controller.session.get("unbind_success_to_tab").equals("ok")){
			Toast.makeText(this, "���ɹ���", Toast.LENGTH_LONG).show();
			Controller.session.remove("unbind_success_to_tab");
			Controller.session.remove("detailsFinished_elepay"); 
		}
		
		if(Controller.session.get("set_default_success_to_tab") != null
				&& Controller.session.get("set_default_success_to_tab").equals("ok")){
			Toast.makeText(this, "����Ĭ�Ͽ��ɹ� ��", Toast.LENGTH_LONG).show();
			Controller.session.remove("set_default_success_to_tab");
			Controller.session.remove("detailsFinished_elepay");
		}
		
		if(pbLinear != null){
			pbLinear.setVisibility(View.GONE);
			
		}
		flg = false;
		if(Controller.session.get("detailsFinished_elepay") != null){
			Controller.session.remove("detailsFinished_elepay");
		}else{
			if(DemoTabActivity.tvText != null && DemoTabActivity.tvText[0] != null
					&& DemoTabActivity.tvText[1] != null){
				DemoTabActivity.tvText[0].setTextColor(Color.WHITE);
				DemoTabActivity.tvText[1].setTextColor(Color.GRAY);
				DemoTabActivity.tvText[1].setShadowLayer(0, 0, 0, 0);
			}
			i = 1;
			curSize = 0;
			flag = "first";
			setDatas();
		}
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		ImageResultBean result = (ImageResultBean) response.getData();
		// �õ�����Ӧ����ÿ���Ż�ȯ��Ϣ�����б�
		List<MpayUserApp> obtainList = ((ObtainPayAppResult)result.getRespBean()).getRows();

		if (flag.equals("first")) {
			if (obtainList!= null && obtainList.size()==0) {
				Toast.makeText(this, "δ������֧������", Toast.LENGTH_LONG).show();
			}
			// �õ������Ż�ȯ�б�ĸ���
			totalNum = (int) ((ObtainPayAppResult)result.getRespBean()).getTotal();
			DemoTabActivity.tvText[0].setText("֧������" + totalNum + "�ţ�");
			datas = new ArrayList<EleCard_paycard_Bean>(); // �������Ǳ��λ�õĸ���
			for (MpayUserApp b : obtainList) {
				EleCard_paycard_Bean bean = new EleCard_paycard_Bean();
				// ����Ӧ��ID
				bean.setId(b.getId().getApp_id()); // Ӧ��ID
				// ����ȫͼ��·��
				bean.setPicture(b.getMpayApp().getPic_path());
				// Ĭ��֧������ʶ 
				if(b.getIsDefault() != null && "1".equals(b.getIsDefault())) {		// 1��ʾΪĬ�Ͽ�
					bean.setFlag(true) ;
				}else{
					bean.setFlag(false);
				}
				datas.add(bean);
			}
			
			listView = (ListView) findViewById(R.id.elecard_payment_listView);
			if(loadMoreView == null){
				loadMoreView = getLayoutInflater().inflate(R.layout.loadmore, null);
				pbLinear = (LinearLayout) loadMoreView.findViewById(R.id.pb_linear);
				listView.addFooterView(loadMoreView); // �����б�ײ���ͼ
				loadMoreView.setVisibility(View.VISIBLE);
			}
			
			listView.setOnScrollListener(this);
			adapter = new EleCard_payment_adapter(this, datas);
			listView.setAdapter(adapter);
			curSize = curSize + datas.size(); 
		} else {
			if (obtainList!= null && obtainList.size()==0) {
				Toast.makeText(this, "���ݼ�����ϣ�", Toast.LENGTH_LONG).show();
			}
			for (int i = 0; i < obtainList.size(); i++) {
				EleCard_paycard_Bean bb = new EleCard_paycard_Bean();
				// ����ͼƬ
				bb.setPicture(obtainList.get(i).getMpayApp().getPic_path());
				// ����Ӧ��ID
				bb.setId(obtainList.get(i).getId().getApp_id());
				adapter.addItem(bb);
				adapter.notifyDataSetChanged(); // ���½���

				pbLinear.setVisibility(View.GONE);
			}
			curSize = curSize + obtainList.size(); 
			flg = false;
		}
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub

	}

	private void loadMoreData() {
		i++;
		flag = "no";
		// �������ȡ����
		setDatas();
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
