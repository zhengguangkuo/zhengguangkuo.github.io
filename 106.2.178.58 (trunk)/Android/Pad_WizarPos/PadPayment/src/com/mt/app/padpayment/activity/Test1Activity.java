package com.mt.app.padpayment.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import android.os.Bundle;

import com.mt.android.R;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;

public class Test1Activity extends DemoSmartActivity{
	private static Logger log = Logger.getLogger(Test1Activity.class);
	
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
			//setContentView("TEST.SCREEN");
			setContentView("TEST.SCREEN");
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
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List getDataListById(String id){
		if (id.equals("GRIDVIEW")) {
			List<String[]> list = new ArrayList<String[]>();
			String[] arr1 = new String[]{"第一张卡", "第二张卡" ,"第三张卡", "第四张卡", "第五张卡", "第六张卡", "第七张卡"};
			String[] arr2 = new String[]{R.drawable.ka1+"",R.drawable.ka2+"",R.drawable.ka3+"",R.drawable.ka4+"",R.drawable.ka5+"",R.drawable.ka6+"",R.drawable.ka7+""};
			list.add(0, arr1);
			list.add(1, arr2);
			return list;
		}
		return new ArrayList();
	}

}
