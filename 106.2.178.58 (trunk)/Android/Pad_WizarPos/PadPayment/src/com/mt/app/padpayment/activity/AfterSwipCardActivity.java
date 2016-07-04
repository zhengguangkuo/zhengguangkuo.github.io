package com.mt.app.padpayment.activity;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.widget.EditText;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.ReadCardReqBean;
/**
 * 刷完卡后的确认界面
 * @author Administrator
 *
 */
public class AfterSwipCardActivity extends DemoSmartActivity{
	private EditText etSure;
	
	private static Logger log = Logger.getLogger(AfterSwipCardActivity.class);
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("AFTER_SWIP_CARD.SCREEN");

		etSure = (EditText)findViewById("CARD_NUMBER_EDIT");
	}
	
	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Request getRequestByCommandName(String commandName) {
		if(commandName.equals("ToConsumeRecord")){
			if(!MsgTools.checkEdit(etSure, this, "卡号不能为空")){
				return null;
			}
			hasWaitView();
			
			Request request=new Request();
			ReadCardReqBean bean = new ReadCardReqBean();
			if(etSure.getText() != null && !(etSure.getText().equals(""))){
				bean.setCardNum(etSure.getText().toString());
			}
			request.setData(bean);
			return request;
		}
		return new Request();
	}

}
