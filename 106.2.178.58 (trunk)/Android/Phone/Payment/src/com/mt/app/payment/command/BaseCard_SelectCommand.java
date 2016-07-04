package com.mt.app.payment.command;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;

import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.responsebean.Card_DataBean;


/**
 * 
 * 
 * @Description:跳转到卡片类型的command
 * 
 * @author:dw
 * 
 * @time:2013-9-13 上午11:25:56
 */
public class BaseCard_SelectCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(BaseCard_SelectCommand.class);

	@Override
	protected void go() {

		Response response = new Response();
		try {
			ArrayList<Card_DataBean> list = (ArrayList<Card_DataBean>) getRequest()
					.getData();
			if (list ==null) {
				response.setError(true);
			}else{
				Bundle bundle = new Bundle();

				bundle.putSerializable("list", list);
				int[] flags = new int[1];
				flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
				response.setFlags(flags);
				response.setBundle(bundle);
				response.setError(false);
			}
			
		} catch (Exception e) {
			response.setError(true);
			e.printStackTrace();
		}
		

		setResponse(response);
	}

	@Override
	protected void onAfterExecute() {
		Response response = getResponse();

		if(response != null && response.isError())
			
		{	
			ResponseBean res = new ResponseBean();
			res.setMessage("请检查网络连接 ！");
			response.setData(res);
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);  
		} else if(response != null ){
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_BaseCardSelect"));
		}
		

	}
}
