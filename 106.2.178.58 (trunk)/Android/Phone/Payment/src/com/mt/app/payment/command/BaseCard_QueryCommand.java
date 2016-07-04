package com.mt.app.payment.command;

import java.util.List;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;

import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.sys.util.StringUtil;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;
import com.mt.app.payment.responsebean.Card_DataBean;

/**
 * 基卡信息查询
 */
public class BaseCard_QueryCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(BindCardCommand.class);
	private boolean isSessionOut = false;//判断session是否失效


	@Override
	protected void go() {
		try {
			log.debug("go");
			Response response = new Response();
			List<ResponseBean> list = DispatchRequest
					.doHttpRequest(Constants.USR_QUERY_CARD, getRequest(),
							Card_DataBean.class);
			/*
			 * 发数据到跳转到的界面(接收响应数据)， 此处需要的数据是用于初始化下一个跳转到的界面的，
			 * 数据应该装到response中的bundle中，下一个界面从bundle中取出
			 */
			if (list != null) {
				
				int[] flags = new int[1];
				flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
				response.setFlags(flags);
				response.setData(list);
				response.setError(false);
				setResponse(response);   
				if (list.size() > 0) {
					if (list.get(0).getRespcode().equals("-2")){//判断session是否失效
						isSessionOut = true;
					}

					if (list.get(0).getRespcode().equals("-1")) {
						ResponseBean res = new ResponseBean();
						res.setMessage(list.get(0).getMessage());
						response.setError(true);
						response.setData(res);
						setResponse(response);
					}
				}
				

			} else {
				ResponseBean res = new ResponseBean();
				res.setMessage("请检查网络连接");
				response.setError(true);
				response.setData(res);
				setResponse(response);
			}
		} catch (Exception e) {

			Response response = new Response();
			response.setError(true);
			setResponse(response);

		}
	}

	@Override
	protected void onAfterExecute() {
		Response response = getResponse();

		if (response.isError()) {
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		} else {
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		}
		if (isSessionOut) {//如果session失效，跳转登录界面
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_USER_LOGIN"));
		}

	}
}