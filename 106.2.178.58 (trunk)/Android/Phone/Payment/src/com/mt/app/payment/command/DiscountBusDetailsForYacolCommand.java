package com.mt.app.payment.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.miteno.coupon.entity.Act;
import com.miteno.coupon.entity.CouponJournal;
import com.miteno.mpay.entity.MerchMpayDiscount;
import com.miteno.mpay.entity.MpayUserApp;
import com.mt.android.frame.entity.FrameDataSource;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.sys.util.StringUtil;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.activity.EleCard_PaymentActivity;
import com.mt.app.payment.activity.EleCard_pay_addCardActivity;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;
import com.mt.app.payment.common.DownLoadImage;
import com.mt.app.payment.responsebean.AppBind_DataBean;
import com.mt.app.payment.responsebean.DiscountBusQueryResult;
import com.mt.app.payment.responsebean.DiscountDisAllResult;
import com.mt.app.payment.responsebean.ImageResultBean;
import com.mt.app.payment.responsebean.ObtainAppDetailResult;
import com.mt.app.payment.responsebean.ObtainDisResult;
import com.mt.app.payment.responsebean.yacol.StoreSingleRespBean;
import com.mt.app.tab.activity.TabEleCardActivity;

public class DiscountBusDetailsForYacolCommand extends AbstractCommand {

	private static Logger log = Logger.getLogger(DiscountBusDetailsForYacolCommand.class);
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
			Response response = new Response();
			List<ResponseBean> list = DispatchRequest.doHttpRequestForYaCol(getRequest(), StoreSingleRespBean.class);

			if (list != null && list.size() > 0) {
				if (list.get(0).getRespcode().equals("-2")){//判断session是否失效
					isSessionOut = true;
				}
				ImageResultBean be = new ImageResultBean();
				be.setRespBean(list.get(0));
				Map<String , byte[]> mmap = new HashMap<String , byte[]>();
				StoreSingleRespBean result = (StoreSingleRespBean)list.get(0);
				for(String mp : result.getImages()){
					DownLoadImage.getImageMap(mp, mmap);
				}
				be.setImageMap(mmap);
				response.setData(be);
				if (list.get(0).getRespcode().equals("-1")) {
					response.setError(true);
				} else {
					response.setError(false);
				}
			}else if(list != null && list.size() == 0){
				ResponseBean res = new ResponseBean();
				res.setMessage("抱歉，没有搜索到数据");
				response.setError(true);
				response.setData(res);
			}else {
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
