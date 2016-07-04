package com.mt.app.payment.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.content.Intent;

import com.miteno.mpay.entity.MerchMpayDiscount;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;
import com.mt.app.payment.common.DownLoadImage;
import com.mt.app.payment.responsebean.DiscountBusQueryResult;
import com.mt.app.payment.responsebean.ImageResultBean;

public class DiscountBusSearchCommand extends AbstractCommand {

	private static Logger log = Logger.getLogger(DiscountBusSearchCommand.class);
	private boolean isSessionOut = false;//判断session是否失效

	
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
			log.debug("go");
			Response response = new Response();
			List<ResponseBean> list = DispatchRequest.doHttpRequest(
					Constants.USR_MERCH_SRARCH, getRequest(), 
					DiscountBusQueryResult.class);

			if (list != null && list.size() > 0) {
				if (list.get(0).getRespcode().equals("-2")){//判断session是否失效
					isSessionOut = true;
				}

				ImageResultBean be = new ImageResultBean();
				be.setRespBean(list.get(0));
				Map<String , byte[]> mmap = new HashMap<String , byte[]>();
				DiscountBusQueryResult result = (DiscountBusQueryResult)list.get(0);
				for(MerchMpayDiscount mp : result.getRows()){
					DownLoadImage.getImageMap(mp.getPic_path(), mmap);
				}
				be.setImageMap(mmap);
				response.setData(be);
				if (list.get(0).getRespcode().equals("-1")) {
					response.setError(true);
				} else {
					int[] flags=new int[1];
					flags[0]=Intent.FLAG_ACTIVITY_NO_HISTORY;
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
	protected void onAfterExecute()
	{	
		Response response = getResponse();
		
		if(response.isError())
		{
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);  
		} else
		{
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		}
		if (isSessionOut) {//如果session失效，跳转登录界面
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_USER_LOGIN"));
		}

	}
}
