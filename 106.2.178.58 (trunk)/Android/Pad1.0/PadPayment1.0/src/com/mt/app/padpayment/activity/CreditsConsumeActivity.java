package com.mt.app.padpayment.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.frame.smart.config.DrawListViewAdapter;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.adapter.ListViewOneAdapter;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.CreditConsumeReqBean;
import com.mt.app.padpayment.responsebean.CreditQueryResBean;
import com.mt.app.padpayment.tools.MoneyUtil;

/**
 * 积分消费录入界面
 * 
 * @author Administrator
 * 
 */
public class CreditsConsumeActivity extends DemoSmartActivity {
	ArrayList<CreditQueryResBean> creditList = null;
	public static int aa = 1;
	private EditText et = null;
	String[] arr = new String[2];
	private ListView lv = null;
	
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("CreditsConsume.SCREEN");
		et = (EditText) findViewById("editsum");
		lv = (ListView) findViewById("CreditsConsumeList");
		
		List<String> list = new ArrayList<String>();
		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		if (bundle != null) {
			creditList = (ArrayList<CreditQueryResBean>) bundle.getSerializable("creditList");
			if (creditList.size()==0) {
				Toast.makeText(this, "您还绑定支付应用！", Toast.LENGTH_SHORT);
			}
			for (int i = 0; i < creditList.size(); i++) {
				
				list.add(creditList.get(i).getIssCshort().trim() +"    " + "积分余额:"
						+ creditList.get(i).getCredits()  +"  可兑换金额："+MoneyUtil.getMoney(creditList.get(i).getMoney()));
			}
			
		}
		
		lv.setAdapter(new ListViewOneAdapter(list,this));
		
		et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				9) });
		et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				/*
				 * if (amountText.getText().toString().equals("")) { aa = 1; }
				 */

				if (et.getText().toString().equals("")) {
					aa = 1;
				}
				aa++;
				if (aa % 2 != 1) {

					if (!et.getText().toString().equals("")) {
						String str = et.getText().toString();
						String ss = "";
						if (str.length() == 1) {
							ss = "0.0" + str;
						} else {
							arr = str.split("\\.");
							str = arr[0] + arr[1];
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
						et.setText(ss);
						et.setSelection(ss.length());

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

	public List getDataListById(String id) {
		List<String> list = new ArrayList<String>();
		
		return list;
	}

	@Override
	public Request getRequestByCommandName(String commandName) {

		Request request = new Request();
		CreditConsumeReqBean bean = new CreditConsumeReqBean();
		if (commandName.equals("CreditsConsumeSubmit")) {
			if (et != null && et.getText() != null&&!et.getText().toString().equals("")&&!et.getText().toString().equals("0.00")) {
				bean.setConsumeMoney(et.getText().toString());
			} else {
				MsgTools.toast(CreditsConsumeActivity.this, "请输入要消费的金额！",
						"l");
				return null;
			}

			// 该界面中的listView中被选中的数据项的内容
			List<Integer> list = ListViewOneAdapter.list;
			if (creditList!= null && creditList.size()>0&& list!= null&&list.size()>0) {
				
				bean.setIssId(creditList.get(list.get(0)).getIssId());
				bean.setSysInstId(creditList.get(list.get(0)).getSysInstId());
				
			} else {
				MsgTools.toast(CreditsConsumeActivity.this, "请选择积分应用！",
						"l");
				return null;
			}
			
			request.setData(bean);
			hasWaitView();
		}
		
		return request;

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
