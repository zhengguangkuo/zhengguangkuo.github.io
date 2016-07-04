package com.mt.app.padpayment.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.frame.smart.config.DrawGridViewAdapter;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.CouponsBackBean;
import com.mt.app.padpayment.responsebean.CouponsApplyRespBean;
/**
 *   优惠劵列表界面
 * @author Administrator
 *
 */
public class CouponsInfoActivity extends DemoSmartActivity{

	ArrayList<ResponseBean> couponsList = null;
	String[] appName = null;
	String[] appIcon = null;
	EditText amountText = null;
	private static int aa = 1;
	private GridView gridView = null;
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("CouponsInfo.SCREEN");
		
		amountText = (EditText) findViewById("money");
		gridView = (GridView) findViewById("CouponsGridView");
		amountText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				9) });
		amountText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				/*
				 * if (amountText.getText().toString().equals("")) { aa = 1; }
				 */

				if (amountText.getText().toString().equals("")) {
					aa = 1;
				}
				aa++;
				if (aa % 2 != 1) {

					if (!amountText.getText().toString().equals("")) {
						String str = amountText.getText().toString();
						String ss = "";

						if (str.length() == 1) {
							ss = "0.0" + str;
						} else {

							String[] arr = new String[2];

							arr = str.split("\\.");
							if (arr.length == 2) {
								str = arr[0] + arr[1];
							} else if (arr.length == 1) {
								str = arr[0];
							}

							str = Integer.parseInt(str) + "";
							if (str.length() == 0) {
								ss = "";
							} else if (str.length() == 1) {
								ss = "0.0" + str;
							} else if (str.length() == 2) {
								ss = "0." + str;
							} else {
								ss = str.substring(0, str.length() - 2)
										+ "."
										+ str.substring(str.length() - 2,
												str.length());
							}

						}

						amountText.setText(ss);
						amountText.setSelection(ss.length());

					}

				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		
	}
	public List getDataListById(String id){
		List<String[]> list = new ArrayList<String[]>();
		if (id.equals("CouponsGridView")) {
			Bundle bundle = getIntent().getBundleExtra("bundleInfo");
			if (bundle != null) {
				couponsList = (ArrayList<ResponseBean>) bundle
						.get("gridView");
				
				appName = new String[couponsList.size()];
				appIcon = new String[couponsList.size()];

				for (int z = 0; z < couponsList.size(); z++) {
					CouponsApplyRespBean appBean = (CouponsApplyRespBean) (couponsList
							.get(z));
					appName[z] = appBean.getCoupon_name();

					appIcon[z] = appBean.getCoupon_img_path();

				}

				list.add(appName);
				list.add(appIcon);
			}
		}
		
		return list;
	}
	@Override
	public Request getRequestByCommandName(String commandName) {
		if(commandName.equals("CouponsSuccReturn")){
			Request request=new Request();
			CouponsBackBean bBean = new CouponsBackBean();
			
			
			if (Controller.session.get("checked")!=null) {
				int pos = Integer.parseInt(Controller.session.get("checked").toString());
				CouponsApplyRespBean bean = (CouponsApplyRespBean) couponsList
						.get(pos);
				
				bBean.setActIds(bean.getCoupon_id());//设置活动ID
				
				bBean.setC_iss_id(bean.getC_iss_id());//应用id
				
				bBean.setIssuerId(bean.getIssuerId());//发券对象id
				EditText number = (EditText) findViewById("NUMBER");
				
				bBean.setCount(number.getText().toString());
				
				
				if(bBean.getCount().equals("")){
					MsgTools.toast(this, "请输入要派发的优惠券数量", "l");
					return null;
				}
				if(!MsgTools.checkEdit(amountText, this, "请输入金额")){
	    			return null;
	    		}
				bBean.setMoney(amountText.getText().toString());
				
			} else {
				MsgTools.toast(this, "请选择要派发的优惠券", "l");
				return null;
			}
			request.setData(bBean);

			hasWaitView();
			return request;
		}
		
		return new Request();
	}
	
	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(Response response) {
		MsgTools.toast(this, response.getData().toString(), "l");
		/*finish();
		Intent intent= new Intent(this,CouponsInfoActivity.class);
		startActivity(intent);*/
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try{//释放bitmap
			((DrawGridViewAdapter)gridView.getAdapter()).recycleBitmap();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
