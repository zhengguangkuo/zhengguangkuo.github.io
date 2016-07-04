package com.mt.app.payment.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;

import com.miteno.mpay.entity.MpayApp;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;
import com.mt.app.payment.common.DownLoadImage;
import com.mt.app.payment.responsebean.Card_DataBean;
import com.mt.app.payment.responsebean.ImageResultBean;
import com.mt.app.payment.responsebean.ObtainAllPayAppResult;

/**
 * 
 * 
 * @Description:跳转到基卡信息查询界面
 * 
 * @author:dw
 * 
 * @time:2013-9-13 上午9:53:17
 */
public class BaseCard_SetCommand extends AbstractCommand {

	private static Logger log = Logger.getLogger(BaseCard_SetCommand.class);
	private boolean isSessionOut = false;// 判断session是否失效

	@Override
	protected void go() {

		/*log.debug("go");
		Response response = new Response();
		List<ResponseBean> list = DispatchRequest.doHttpRequest(Constants.USR_QUERY_CARD, getRequest(), Card_DataBean.class);

		if (list != null && list.size() > 0) {
			if (list.get(0).getRespcode().equals("-2")) {// 判断session是否失效
				isSessionOut = true;
			}

			for (int i = 0; i < list.size(); i++) {
				Card_DataBean bean = (Card_DataBean) list.get(i);
				if (bean.getBind_flag().equals("1")) {
					Controller.session.put("isBind", "bind");
					break;
				}
			}
		}

		Bundle bundle = new Bundle();
		int[] flags = new int[1];
		flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
		response.setFlags(flags);
		ArrayList arrList = new ArrayList(list);
		bundle.putSerializable("list", arrList);
		response.setBundle(bundle);
		setResponse(response);*/	

		try {

			log.debug("go");
			Response response = new Response();
			String info = "";
			List<ResponseBean> list = DispatchRequest.doHttpRequest(Constants.USR_QUERY_CARD, getRequest(), Card_DataBean.class);

			response.setBussinessType("getAllBaseCardData") ;

			if (list != null && list.size() > 0) {
				if (list.get(0).getRespcode().equals("-2")) {// 判断session是否失效
					isSessionOut = true;
				}

				response.setData(list);
				if (list.get(0).getRespcode().equals("-1")) {
					response.setError(true);
				} else {
					int[] flags = new int[1];
					flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
					response.setFlags(flags);
					response.setError(false);
				}
			} else {
				ResponseBean res = new ResponseBean();
				res.setMessage("请检查网络连接");
				response.setError(true);
				response.setData(res);
			}
			setResponse(response);
		} catch (Exception e) {
			Response response = new Response();
			response.setError(true);
			setResponse(response);
		}		
	}

	@Override
	protected void onAfterExecute() {
		Response response = getResponse();
		response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		if (isSessionOut) {// 如果session失效，跳转登录界面
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_USER_LOGIN"));
		}

	}
}