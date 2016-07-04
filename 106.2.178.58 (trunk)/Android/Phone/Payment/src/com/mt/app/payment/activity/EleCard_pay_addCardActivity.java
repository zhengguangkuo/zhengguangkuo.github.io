package com.mt.app.payment.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.miteno.mpay.entity.MpayApp;
import com.mt.android.R;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.adapter.Discount_dis_adapter;
import com.mt.app.payment.adapter.EleCard_pay_add_adapter;
import com.mt.app.payment.common.RegCodeInput;
import com.mt.app.payment.requestbean.AppBindUnBindReqBean;
import com.mt.app.payment.requestbean.CheckRegCodeBean;
import com.mt.app.payment.requestbean.EleCard_pay_add_Bean;
import com.mt.app.payment.requestbean.ObtainPayAllAppReqBean;
import com.mt.app.payment.requestbean.ValidTextReqBean;
import com.mt.app.payment.responsebean.ImageResultBean;
import com.mt.app.payment.responsebean.ObtainAllPayAppResult;

public class EleCard_pay_addCardActivity extends BaseActivity implements
OnScrollListener{
	private String flag = "first"; // �Ƿ��ǵ�һ�μ�������
	private int i = 1; // ��ǰ���ڵ�ҳ����
	
	private EleCard_pay_add_adapter adapter;
	private ListView listView;
	private List<EleCard_pay_add_Bean> datas;
	private Button btnBack;
	private RegCodeInput regcode;
	private View loadMoreView;
	private LinearLayout pbLinear;
	private int visibleLastIndex = 0; // ���Ŀ���������
	private int visibleItemCount; // ��ǰ���ڿɼ�������
	private Handler handler = new Handler();

	private int totalNum; // ����
	private boolean flg = false;
	private int curSize = 0;
	
	private int bindPosi ; 

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView(R.layout.elecard_paymentcard_add);
		btnBack = (Button) findViewById(R.id.btn_eleBindNew_back);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EleCard_pay_addCardActivity.this.finish();
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(pbLinear != null){
			pbLinear.setVisibility(View.GONE);
			
		}
		flg = false; 
		i = 1;
		flag = "first";
		
		curSize = 0; 
		setDatas();
	}

	private void setDatas() {
		Request request = new Request();
		ObtainPayAllAppReqBean bean = new ObtainPayAllAppReqBean();
		bean.setPage(i);
		bean.setRows(10);
		bean.setAppType("0");   //֧�����Ӧ��
		if(Controller.session.get("AREA_CODE_LEVEL_1") != null){
			bean.setCity((String)Controller.session.get("AREA_CODE_LEVEL_1"));
		}
		request.setData(bean);
		go(CommandID.map.get("EleApp_PayAll_Query"), request, true);
		
//		datas = new ArrayList<EleCard_pay_add_Bean>();
//		EleCard_pay_add_Bean bean = new EleCard_pay_add_Bean();
//		bean.setPicture(R.drawable.tuan1);
//		bean.setName("��������");
//		bean.setType("ĵ����");
//		bean.setIcon("��");
//		datas.add(bean);
//		bean = new EleCard_pay_add_Bean();
//		bean.setPicture(R.drawable.tuan1);
//		bean.setName("��������");
//		bean.setType("ĵ����");
//		bean.setIcon("�Ѱ�");
//		datas.add(bean);
//		setContentView(R.layout.elecard_paymentcard_add);
//		btnBack = (Button) findViewById(R.id.btn_eleBindNew_back);
//		btnBack.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				EleCard_pay_addCardActivity.this.finish();
//			}
//		});
//		adapter = new EleCard_pay_add_adapter(this, datas);
//		listView = (ListView) findViewById(R.id.elecard_payAdd_list);
//		listView.setAdapter(adapter);
	}
	
	public void bindResult(final String appID , int bindPosition){
		
		this.bindPosi = bindPosition ;    //�󶨵�λ��
		
		//��֤��
		if(Controller.session.get("regcode") == null){//���û��������֤��,�򷵻�
			regcode = new RegCodeInput(EleCard_pay_addCardActivity.this);
			regcode.showDialog();
			return ;
		}
		//��֤��
		
		View view = LayoutInflater
				.from(EleCard_pay_addCardActivity.this)
				.inflate(
						R.layout.ele_card_bindapp_dialog, null);
		final EditText edit = (EditText)view.findViewById(R.id.edit_dialog);
		edit.setInputType(InputType.TYPE_CLASS_NUMBER);
		new AlertDialog.Builder(EleCard_pay_addCardActivity.this)
		.setView(view)
		.setPositiveButton("ȷ��",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub
						if(edit.getText() == null || edit.getText().toString().trim().equals("")){
							Toast.makeText(EleCard_pay_addCardActivity.this, "�����뿨�ţ�", Toast.LENGTH_LONG).show();
						}else{
							Request request = new Request();
							AppBindUnBindReqBean bean = new AppBindUnBindReqBean();
							bean.setApp_id(appID);
							bean.setAction("bind");
							bean.setApp_card_no(edit.getText().toString());
							request.setData(bean);
							go(CommandID.map.get("EleApp_Bind"), request, true);
							// �رնԻ���
							dialog.dismiss();
						}
					}
				})
		.setNegativeButton("ȡ��",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// �رնԻ���
						dialog.dismiss();
					}
				}).show();
	}

	/*��֤��*/
    public void checkRegInfo(String vregCode){//�˶���֤��
    	Request request = new Request();
    	CheckRegCodeBean regcode = new CheckRegCodeBean();
    	regcode.setValidateCode(vregCode);
    	request.setData(regcode);
    	
		go(CommandID.map.get("RegCodeVerfi"), request, true);
	}
	public void getRegInfo(){
		ValidTextReqBean reqBean = new ValidTextReqBean();
		reqBean.setFuncName("BIND_APP") ;
		Request request = new Request(reqBean);
		go(CommandID.map.get("RegCodeGet"), request, true);
	}
	/*��֤��*/
	
	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
		
		
		
		/*��֤��*/
		if(regcode != null && regcode.isRegResponse(response)){
			return;
		}
		/*��֤��*/
		
		if(response.getBussinessType().equals("appQuery")){
			ImageResultBean result = (ImageResultBean) response.getData();
			// �õ�����Ӧ����ÿ���Ż�ȯ��Ϣ�����б�
			List<MpayApp> obtainList = ((ObtainAllPayAppResult)result.getRespBean()).getRows();

			if (flag.equals("first")) {
				if (obtainList!= null && obtainList.size()==0) {
					Toast.makeText(this, "δ������֧������", Toast.LENGTH_LONG).show();
				}
				// �õ������Ż�ȯ�б�ĸ���
				totalNum = (int) ((ObtainAllPayAppResult)result.getRespBean()).getTotal();
				datas = new ArrayList<EleCard_pay_add_Bean>(); // �������Ǳ��λ�õĸ���
				for (MpayApp b : obtainList) {
					EleCard_pay_add_Bean bean = new EleCard_pay_add_Bean();
					//����ͼƬ
					bean.setPicture(b.getPic_path());
					//����Ӧ�����ƣ�����������
					bean.setName(b.getInstName());   
					//���ÿ�Ƭ����
					bean.setType(b.getApp_name());  
					//����Ӧ��ID
					bean.setId(b.getId());
					//�����Ѱ�δ�󶨱�ʶ
					bean.setIcon(b.getBind_flag());   
					datas.add(bean);
				}
				
				listView = (ListView) findViewById(R.id.elecard_payAdd_list);
				if(loadMoreView == null){
					loadMoreView = getLayoutInflater().inflate(R.layout.loadmore, null);
					pbLinear = (LinearLayout) loadMoreView.findViewById(R.id.pb_linear);
					listView.addFooterView(loadMoreView); // �����б�ײ���ͼ
					loadMoreView.setVisibility(View.VISIBLE);
				}
				
				listView.setOnScrollListener(this);
				adapter = new EleCard_pay_add_adapter(this, datas);
				listView.setAdapter(adapter);
				
				curSize = curSize + datas.size();
			} else {
				if (obtainList!= null && obtainList.size()==0) {
					Toast.makeText(this, "���ݼ�����ϣ�", Toast.LENGTH_LONG).show();
				}
				for (int i = 0; i < obtainList.size(); i++) {
					EleCard_pay_add_Bean bb = new EleCard_pay_add_Bean();
					//����ͼƬ
					bb.setPicture(obtainList.get(i).getPic_path());
					//����Ӧ������
					bb.setName(obtainList.get(i).getInstName());
					//���ÿ�Ƭ����
					bb.setType(obtainList.get(i).getApp_name());  
					//����Ӧ��ID
					bb.setId(obtainList.get(i).getId());
					//�����Ѱ�δ�󶨱�ʶ
					bb.setIcon(obtainList.get(i).getBind_flag()); 
					adapter.addItem(bb);
					adapter.notifyDataSetChanged(); // ���½���

					pbLinear.setVisibility(View.GONE);
				}
				curSize = curSize + obtainList.size();
				flg = false;
			}
		}else if(response.getBussinessType().equals("appBind")){
			Toast.makeText(this, "�󶨳ɹ���", Toast.LENGTH_LONG).show();
			datas.get(bindPosi).setIcon("1");
			adapter.notifyDataSetChanged();
		}
		
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		if(response != null){
			/*��֤��*/
			if(regcode != null && regcode.isRegResponse(response)){
				return;
			}
			if(response.getBussinessType() != null 
					&& response.getBussinessType().equals("appQuery")){
				Toast.makeText(this, "��������ʧ�ܣ�", Toast.LENGTH_LONG).show();
			}else if(response.getBussinessType() != null
					&& response.getBussinessType().equals("appBind")){
				if(response.getData()!= null){
					Toast.makeText(this, ((ResponseBean)response.getData()).getMessage(), Toast.LENGTH_LONG).show();
				}
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
