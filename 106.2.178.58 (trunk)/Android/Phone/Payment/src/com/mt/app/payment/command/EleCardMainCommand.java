package com.mt.app.payment.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import android.view.LayoutInflater;

import com.mt.android.frame.entity.FrameDataSource;
import com.mt.android.frame.entity.TabDataBean;
import com.mt.android.sys.bean.base.RequestBean;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.activity.EleCard_BusinessCardActivity;
import com.mt.app.payment.activity.EleCard_PaymentActivity;
import com.mt.app.payment.activity.EleCard_pay_addCardActivity;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;
import com.mt.app.tab.activity.TabEleCardActivity;

public class EleCardMainCommand extends AbstractCommand {

	private static Logger log = Logger.getLogger(EleCardMainCommand.class);
	
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
		try {
			Response response = new Response();
			setResponse(response);
		} catch (Exception e) {
			Response response = new Response();
			response.setError(true);
			setResponse(response);
		}
	}
	
	@Override
	protected void onAfterExecute()
	{	
		Response response = getResponse();
		
		if(response.isError())
		{
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		} else {
			if(Controller.session.get("user") != null){  //说明已经登录了
				FrameDataSource.tabDataClass.put("tabClass", TabEleCardActivity.class);
				FrameDataSource.tabDataClass.put("bindNewCard", EleCard_pay_addCardActivity.class);
				response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_EleCard_DemoTab"));
			}else{    //没登录
				response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
				response.setError(true);
				response.setBussinessType("EleMainDetails");
			}
		}
	}
}
