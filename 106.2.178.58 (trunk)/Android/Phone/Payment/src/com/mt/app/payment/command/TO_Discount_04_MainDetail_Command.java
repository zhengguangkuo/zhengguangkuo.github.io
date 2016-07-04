package com.mt.app.payment.command;

import com.mt.android.frame.entity.FrameDataSource;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.activity.Discount_04_MainDetailActivity;
import com.mt.app.tab.activity.TabDiscount_04_MainListActivity;

public class TO_Discount_04_MainDetail_Command extends AbstractCommand{
	
	
	
	 @Override
    protected void go() {
	// TODO Auto-generated method stub
		  Response response= new Response();
		  setResponse(response);
 }
	   @Override
		protected void onAfterExecute()
		{
		   Response response = getResponse();
//			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_Discount_01_MainListActivity"));
		   	FrameDataSource.tabDataClass.put("tabClass", TabDiscount_04_MainListActivity.class);
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_EleCard_DemoTab"));
		}
	}
