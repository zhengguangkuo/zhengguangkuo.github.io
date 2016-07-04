package com.mt.app.padpayment.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.OriginalDealNumReqBean;
import com.mt.app.padpayment.requestbean.ReadCardReqBean;
import com.mt.app.padpayment.requestbean.ReturnGoodsMoney;
/**
 *  退货金额输入界面
 * @author Administrator
 *
 */
public class InputBackAmountActivity extends DemoSmartActivity{
	EditText amountText = null;
	public static int aa = 1;
	String[] arr = new String[2];
  @Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("InputBackAmount.SCREEN");
		
	    amountText = (EditText) findViewById("BackAmount");
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
	@Override
	public Request getRequestByCommandName(String commandIDName) {
		if(commandIDName.equals("CANCELRESULT")){
			
			Request request = new Request();
     		ReturnGoodsMoney returngood = new ReturnGoodsMoney();
     		if(!MsgTools.checkEdit(amountText, this, "金额不能为空")){
    			return null;
    		}
     		hasWaitView();
     		if(amountText.getText() != null && !(amountText.getText().equals(""))){
     			returngood.setReturnmoney(amountText.getText().toString());
     		}
			request.setData(returngood);
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
		// TODO Auto-generated method stub
		
	}

}
