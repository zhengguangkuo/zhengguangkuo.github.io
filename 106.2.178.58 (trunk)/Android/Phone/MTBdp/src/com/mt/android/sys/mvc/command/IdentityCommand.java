package com.mt.android.sys.mvc.command;

import org.apache.log4j.Logger;

import com.mt.android.global.Globals;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.BaseActivityID;
import com.mt.android.view.common.BaseCommandID;

public class IdentityCommand extends AbstractCommand implements ICommand {
	private static final Logger log = Logger.getLogger(IdentityCommand.class);

	@Override
	protected void go() {
		// TODO Auto-generated method stub
		Response response = new Response();
		response.setListener(getResponseListener());
		BaseActivityID ActivityID = (BaseActivityID) Globals.map.get("ActivityID");
		String isFirstIn=getRequest().getData().toString();
		if(isFirstIn.equals("true")){
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_Guide"));
		}else{
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_NOLOGININDEX"));
		}
			
			// response.setTargetActivityID(ActivityID.ACTIVITY_ID_NOLOGININDEX);
			// response.setTargetActivityID(ActivityID.ACTIVITY_ID_SETTING);
			setResponse(response);
	}

}
