package com.mt.app.payment.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.miteno.coupon.entity.CouponJournal;
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
import com.mt.app.payment.responsebean.ImageResultBean;
import com.mt.app.payment.responsebean.ObtainAppDetailResult;
import com.mt.app.payment.responsebean.ObtainDisResult;
import com.mt.app.tab.activity.TabEleCardActivity;
/**
 * 

 * @Description:�Ż�ȯ����

 * @author:dw

 * @time:2013-10-30 ����4:14:07
 */
public class EleDisDeleteCommand extends AbstractCommand {

	private static Logger log = Logger.getLogger(EleDisDeleteCommand.class);
	private boolean isSessionOut = false;//�ж�session�Ƿ�ʧЧ

	
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
			// TODO Auto-generated method stub
			log.debug("go");

			Response response = new Response();
			List<ResponseBean> list = DispatchRequest.doHttpRequest(
					Constants.USR_COUPONR_RETURN, getRequest(),
					ResponseBean.class);
			response.setBussinessType("delete");
			if (list != null && list.size() > 0) {
				if (list.get(0).getRespcode().equals("-2")){//�ж�session�Ƿ�ʧЧ
					isSessionOut = true;
				}
				response.setData(list.get(0));
				if (list.get(0).getRespcode().equals("-1")||list.get(0).getRespcode().equals("0")) {
					response.setError(true);
				} else {
					response.setError(false);
				}
			} else {
				ResponseBean res = new ResponseBean();
				res.setMessage("������������");
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
		if (isSessionOut) {//���sessionʧЧ����ת��¼����
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_USER_LOGIN"));
		}

	}
}
