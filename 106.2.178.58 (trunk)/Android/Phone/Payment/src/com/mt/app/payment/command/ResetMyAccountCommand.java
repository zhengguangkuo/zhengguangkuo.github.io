package com.mt.app.payment.command;

import org.apache.log4j.Logger;

import android.os.Bundle;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.requestbean.MyAccountReqBean;

public class ResetMyAccountCommand extends AbstractCommand{
	private static Logger log = Logger.getLogger(ResetMyAccountCommand.class);

	   @Override
    protected void go() {
	// TODO Auto-generated method stub
		 Response response = new Response();
		 MyAccountReqBean myaccount = (MyAccountReqBean)getRequest().getData();
		 if(!myaccount.equals(null)){
	     Bundle bundle = new Bundle(); 
	     bundle.putString("mobilenum", myaccount.getMobile());
	     bundle.putString("password", myaccount.getPassword());
	     bundle.putString("email", myaccount.getEmail());
	     bundle.putString("type", myaccount.getType());
	     bundle.putString("username", myaccount.getUsername()) ;
	     
	     response.setBundle(bundle);
	     }
		 setResponse(response);
		 }

		@Override
		protected void onAfterExecute()
		{	
			Response response = getResponse();
	        response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_ResetMyaccount"));  
		
		}
}
