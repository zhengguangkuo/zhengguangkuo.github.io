package com.mt.app.padpayment.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.OriginalDealNumReqBean;
import com.mt.app.padpayment.requestbean.ReadCardReqBean;
/**
 *   原交易凭参考号输入界面
 * @author Administrator
 *
 */
public class InputFlowNumActivity extends DemoSmartActivity{
    private EditText Originalnum;
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("InputFlowNum.SCREEN");
		Originalnum=(EditText)findViewById("FlowNum");
		
	}

	@Override
	public Request getRequestByCommandName(String commandIDName) {
		if(commandIDName.equals("FlowNumSubmit")){
			Request request = new Request();
     		OriginalDealNumReqBean bean = new OriginalDealNumReqBean();
     		if(!MsgTools.checkEdit(Originalnum, this, "参考号不能为空")){
    			return null;
    		}
     		if(Originalnum.getText() != null && !(Originalnum.getText().equals(""))){
     			Controller.session.put("ODnum",Originalnum.getText().toString());
     			bean.setOriginalnum(Originalnum.getText().toString());
     		}
			request.setData(bean);
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
