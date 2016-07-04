package com.mt.app.payment.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;

import com.miteno.mpay.entity.MerchMpayDiscount;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;
import com.mt.app.payment.common.DownLoadImage;
import com.mt.app.payment.requestbean.Discount_disResBean;
import com.mt.app.payment.responsebean.AdResult;
import com.mt.app.payment.responsebean.DiscountBusQueryResult;
import com.mt.app.payment.responsebean.ImageResultBean;

public class DiscountBusQueryCommand extends AbstractCommand {

	private static Logger log = Logger.getLogger(DiscountBusQueryCommand.class);
	private boolean isSessionOut = false;// 判断session是否失效

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
			List<ResponseBean> list = DispatchRequest.doHttpRequest(Constants.USR_SALE_LIST, getRequest(), DiscountBusQueryResult.class);

			Bundle bundle = new Bundle() ;				
			Discount_disResBean reqData = (Discount_disResBean) getRequest().getData() ;
			List<ResponseBean> adList = null ;
			if(reqData.getPage()<2) {
				adList = DispatchRequest.doHttpRequest(Constants.USR_AD, new Request(), AdResult.class);				
				if(adList!= null) {
					if (!adList.get(0).getRespcode().equals("-1")) {
						AdResult adResult = (AdResult) adList.get(0);
						bundle.putSerializable("adResult", adResult) ;
					}					
				}
			}
			
			if (list != null && list.size() > 0) {
				if (list.get(0).getRespcode().equals("-2")) {// 判断session是否失效
					isSessionOut = true;
				}
				

				ImageResultBean be = new ImageResultBean();
				be.setRespBean(list.get(0));
				Map<String, byte[]> mmap = new HashMap<String, byte[]>();
				DiscountBusQueryResult result = (DiscountBusQueryResult) list.get(0);
				for (MerchMpayDiscount mp : result.getRows()) {
					DownLoadImage.getImageMap(mp.getPic_path(), mmap);
				}
				be.setImageMap(mmap);
				bundle.putSerializable("SALE_LIST", be) ;
				response.setBundle(bundle) ;
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
				response.setBundle(bundle) ;
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

		if (response.isError()) {
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		} else {
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		}
		if (isSessionOut) {// 如果session失效，跳转登录界面
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_USER_LOGIN"));
		}

	}
}
