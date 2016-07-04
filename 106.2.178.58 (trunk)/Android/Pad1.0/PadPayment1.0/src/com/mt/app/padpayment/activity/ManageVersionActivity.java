package com.mt.app.padpayment.activity;

import java.util.Map;

import android.os.Bundle;
import android.widget.TextView;

import com.mt.android.db.DbHandle;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
/**
 *   �汾����
 * @author Administrator
 *
 */
public class ManageVersionActivity extends DemoSmartActivity{
	//���ڵİ汾�ţ����µİ汾��
	private TextView nowVer , latestVer ; 
	
    @Override
    protected void onCreateContent(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreateContent(savedInstanceState);
    	setContentView("MANAGE_VERSION.SCREEN");
    	
    	nowVer = (TextView)findViewById("NOW_VERSION");
    	latestVer = (TextView)findViewById("LATEST_VERSION");
    	
    	//�����ݿ���������ǰ�汾�ţ���nowVer������ֵ
    	DbHandle handle = new DbHandle();
		Map<String , String> map = handle.rawQueryOneRecord("select * from TBL_PARAMETER where PARA_NAME=?", new String[]{"NOW_VERSION"});
		if(map != null){
			String number = map.get("PARA_VALUE");
			nowVer.setText(number);
		}
    	//�����̨������°汾�ţ���latestVer������ֵ
    }
	@Override
	public Request getRequestByCommandName(String commandIDName) {
		if(commandIDName.equals("TO_MANAGE_MAIN")){
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
