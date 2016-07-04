package com.mt.app.padpayment.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.RegisterReqBean;
import com.mt.app.padpayment.responsebean.ResultRespBean;

/**
 * 

 * @Description:用户注册

 * @author:dw

 * @time:2014-7-18 下午2:55:52
 */
public class RegisterResultActivity extends DemoSmartActivity {
	private TextView result;
    @Override
    protected void onCreateContent(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreateContent(savedInstanceState);
    	setContentView("REGISTER_RESULT.SCREEN");
    	
    	result = (TextView)findViewById("RESULT_MESSAGE");   //交易结果
    	
    	Bundle bundle = getIntent().getBundleExtra("bundleInfo");
    	if(bundle != null){
    		ResultRespBean bean = (ResultRespBean)bundle.getSerializable("ResultRespBean");
    		if(bean != null){
    			String message = bean.getMessage();
    			if(message != null && !(message.equals(""))){
    				result.setText(message);
    			}
    		}
    	}
    }
	@Override
	public Request getRequestByCommandName(String commandIDName) {
		if(commandIDName.equals("TO_MAIN")){
			Request request=new Request();
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