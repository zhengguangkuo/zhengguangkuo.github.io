package com.mt.app.padpayment.activity;

import java.util.Map;

import android.os.Bundle;
import android.widget.TextView;

import com.mt.android.db.DbHandle;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
/**
 *   版本界面
 * @author Administrator
 *
 */
public class ManageVersionActivity extends DemoSmartActivity{
	//现在的版本号，最新的版本号
	private TextView nowVer , latestVer ; 
	
    @Override
    protected void onCreateContent(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreateContent(savedInstanceState);
    	setContentView("MANAGE_VERSION.SCREEN");
    	
    	nowVer = (TextView)findViewById("NOW_VERSION");
    	latestVer = (TextView)findViewById("LATEST_VERSION");
    	
    	//从数据库里面查出当前版本号，给nowVer设置上值
    	DbHandle handle = new DbHandle();
		Map<String , String> map = handle.rawQueryOneRecord("select * from TBL_PARAMETER where PARA_NAME=?", new String[]{"NOW_VERSION"});
		if(map != null){
			String number = map.get("PARA_VALUE");
			nowVer.setText(number);
		}
    	//请求后台获得最新版本号，给latestVer设置上值
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
