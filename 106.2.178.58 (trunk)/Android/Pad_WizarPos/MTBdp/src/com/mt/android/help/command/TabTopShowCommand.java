package com.mt.android.help.command;

import org.apache.log4j.Logger;

import android.os.Bundle;

import com.mt.android.help.datasource.InitDataSource;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.BaseActivityID;

public class TabTopShowCommand extends AbstractCommand{

	private static Logger log = Logger.getLogger(TabTopShowCommand.class);
	
	@Override
	protected void prepare() {
		log.debug("prepare");
	}

	@Override
	protected final void onBeforeExecute() {
		log.debug("onBeforeExecute");
	}

	@Override
	protected void go() {
		// TODO Auto-generated method stub
		log.debug("go");
		//初始化数据
		InitDataSource.initTabTopData();
		
		Response response = new Response();
		Bundle bundle = new Bundle();
		bundle.putString("tabKey", "tabTopSource");
		response.setBundle(bundle);
		
        response.setError(false);
        setResponse(response);
        //OpenCard();
	}
	
	@Override
	protected void onAfterExecute()
	{
		log.debug("onAfterExecute");
		
		Response response = getResponse();
		
		if(response.isError())
		{
			response.setTargetActivityID(BaseActivityID.map.get("ACTIVITY_ID_TAB_TOP_SHOW"));
		} else
		{
			
			response.setTargetActivityID(BaseActivityID.map.get("ACTIVITY_ID_TAB_TOP_SHOW"));
		}
	}


}
