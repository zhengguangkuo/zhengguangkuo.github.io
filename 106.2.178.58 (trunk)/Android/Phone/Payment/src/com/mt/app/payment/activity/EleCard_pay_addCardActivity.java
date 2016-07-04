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
	private String flag = "first"; // 是否是第一次加载数据
	private int i = 1; // 当前所在的页码数
	
	private EleCard_pay_add_adapter adapter;
	private ListView listView;
	private List<EleCard_pay_add_Bean> datas;
	private Button btnBack;
	private RegCodeInput regcode;
	private View loadMoreView;
	private LinearLayout pbLinear;
	private int visibleLastIndex = 0; // 最后的可视项索引
	private int visibleItemCount; // 当前窗口可见项总数
	private Handler handler = new Handler();

	private int totalNum; // 总数
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
		bean.setAppType("0");   //支付类的应用
		if(Controller.session.get("AREA_CODE_LEVEL_1") != null){
			bean.setCity((String)Controller.session.get("AREA_CODE_LEVEL_1"));
		}
		request.setData(bean);
		go(CommandID.map.get("EleApp_PayAll_Query"), request, true);
		
//		datas = new ArrayList<EleCard_pay_add_Bean>();
//		EleCard_pay_add_Bean bean = new EleCard_pay_add_Bean();
//		bean.setPicture(R.drawable.tuan1);
//		bean.setName("工商银行");
//		bean.setType("牡丹卡");
//		bean.setIcon("绑定");
//		datas.add(bean);
//		bean = new EleCard_pay_add_Bean();
//		bean.setPicture(R.drawable.tuan1);
//		bean.setName("招商银行");
//		bean.setType("牡丹卡");
//		bean.setIcon("已绑定");
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
		
		this.bindPosi = bindPosition ;    //绑定的位置
		
		//验证码
		if(Controller.session.get("regcode") == null){//如果没有输入验证码,则返回
			regcode = new RegCodeInput(EleCard_pay_addCardActivity.this);
			regcode.showDialog();
			return ;
		}
		//验证码
		
		View view = LayoutInflater
				.from(EleCard_pay_addCardActivity.this)
				.inflate(
						R.layout.ele_card_bindapp_dialog, null);
		final EditText edit = (EditText)view.findViewById(R.id.edit_dialog);
		edit.setInputType(InputType.TYPE_CLASS_NUMBER);
		new AlertDialog.Builder(EleCard_pay_addCardActivity.this)
		.setView(view)
		.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub
						if(edit.getText() == null || edit.getText().toString().trim().equals("")){
							Toast.makeText(EleCard_pay_addCardActivity.this, "请输入卡号！", Toast.LENGTH_LONG).show();
						}else{
							Request request = new Request();
							AppBindUnBindReqBean bean = new AppBindUnBindReqBean();
							bean.setApp_id(appID);
							bean.setAction("bind");
							bean.setApp_card_no(edit.getText().toString());
							request.setData(bean);
							go(CommandID.map.get("EleApp_Bind"), request, true);
							// 关闭对话框
							dialog.dismiss();
						}
					}
				})
		.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// 关闭对话框
						dialog.dismiss();
					}
				}).show();
	}

	/*验证码*/
    public void checkRegInfo(String vregCode){//核对验证码
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
	/*验证码*/
	
	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
		
		
		
		/*验证码*/
		if(regcode != null && regcode.isRegResponse(response)){
			return;
		}
		/*验证码*/
		
		if(response.getBussinessType().equals("appQuery")){
			ImageResultBean result = (ImageResultBean) response.getData();
			// 拿到的响应对象（每张优惠券信息）的列表
			List<MpayApp> obtainList = ((ObtainAllPayAppResult)result.getRespBean()).getRows();

			if (flag.equals("first")) {
				if (obtainList!= null && obtainList.size()==0) {
					Toast.makeText(this, "未搜索到支付卡！", Toast.LENGTH_LONG).show();
				}
				// 拿到已领优惠券列表的个数
				totalNum = (int) ((ObtainAllPayAppResult)result.getRespBean()).getTotal();
				datas = new ArrayList<EleCard_pay_add_Bean>(); // 个数就是本次获得的个数
				for (MpayApp b : obtainList) {
					EleCard_pay_add_Bean bean = new EleCard_pay_add_Bean();
					//设置图片
					bean.setPicture(b.getPic_path());
					//设置应用名称（发卡机构）
					bean.setName(b.getInstName());   
					//设置卡片类型
					bean.setType(b.getApp_name());  
					//设置应用ID
					bean.setId(b.getId());
					//设置已绑定未绑定标识
					bean.setIcon(b.getBind_flag());   
					datas.add(bean);
				}
				
				listView = (ListView) findViewById(R.id.elecard_payAdd_list);
				if(loadMoreView == null){
					loadMoreView = getLayoutInflater().inflate(R.layout.loadmore, null);
					pbLinear = (LinearLayout) loadMoreView.findViewById(R.id.pb_linear);
					listView.addFooterView(loadMoreView); // 设置列表底部视图
					loadMoreView.setVisibility(View.VISIBLE);
				}
				
				listView.setOnScrollListener(this);
				adapter = new EleCard_pay_add_adapter(this, datas);
				listView.setAdapter(adapter);
				
				curSize = curSize + datas.size();
			} else {
				if (obtainList!= null && obtainList.size()==0) {
					Toast.makeText(this, "数据加载完毕！", Toast.LENGTH_LONG).show();
				}
				for (int i = 0; i < obtainList.size(); i++) {
					EleCard_pay_add_Bean bb = new EleCard_pay_add_Bean();
					//设置图片
					bb.setPicture(obtainList.get(i).getPic_path());
					//设置应用名称
					bb.setName(obtainList.get(i).getInstName());
					//设置卡片类型
					bb.setType(obtainList.get(i).getApp_name());  
					//设置应用ID
					bb.setId(obtainList.get(i).getId());
					//设置已绑定未绑定标识
					bb.setIcon(obtainList.get(i).getBind_flag()); 
					adapter.addItem(bb);
					adapter.notifyDataSetChanged(); // 更新界面

					pbLinear.setVisibility(View.GONE);
				}
				curSize = curSize + obtainList.size();
				flg = false;
			}
		}else if(response.getBussinessType().equals("appBind")){
			Toast.makeText(this, "绑定成功！", Toast.LENGTH_LONG).show();
			datas.get(bindPosi).setIcon("1");
			adapter.notifyDataSetChanged();
		}
		
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		if(response != null){
			/*验证码*/
			if(regcode != null && regcode.isRegResponse(response)){
				return;
			}
			if(response.getBussinessType() != null 
					&& response.getBussinessType().equals("appQuery")){
				Toast.makeText(this, "加载数据失败！", Toast.LENGTH_LONG).show();
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
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(adapter != null){
			adapter.onAdapterDestroy();
		}
	}
}
