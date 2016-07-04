package com.mt.app.payment.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;

import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;
import com.mt.app.payment.responsebean.Card_DataBean;

/**
 * 
 * 
 * @Description:选择完基卡信息后跳转到基卡信息查询界面 
 * 
 * @author:dw
 * 
 * @time:2013-9-13 上午9:53:17
 */
public class GuideBaseCardSelectTwoCommand extends AbstractCommand {

	private static Logger log = Logger.getLogger(GuideBaseCardSelectTwoCommand.class);

	@Override
	protected void go() {

		log.debug("go");
		Response response = new Response();
		ArrayList arrList = (ArrayList) getRequest().getData();
		
		Bundle bundle = new Bundle();

		int[] flags = new int[1];
		flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
		response.setFlags(flags);
		
		bundle.putSerializable("list", arrList);
		response.setBundle(bundle);
		setResponse(response);


	}

	@Override
	protected void onAfterExecute() {
		Response response = getResponse();
		response.setTargetActivityID(ActivityID.map
				.get("ACTIVITY_ID_GuideBaseCard_Set"));

	}
}