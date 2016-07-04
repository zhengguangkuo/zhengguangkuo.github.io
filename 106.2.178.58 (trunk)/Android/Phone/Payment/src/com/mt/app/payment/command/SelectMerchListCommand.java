package com.mt.app.payment.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.content.Intent;

import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;
import com.mt.app.payment.responsebean.ImageResultBean;
import com.mt.app.payment.responsebean.SelectMerchListResult;

public class SelectMerchListCommand extends AbstractCommand {

	private static Logger log = Logger.getLogger(SelectMerchListCommand.class);
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
			log.debug("go");
			Response response = new Response();
			List<ResponseBean> list = DispatchRequest.doHttpRequest(
					Constants.MESSID_MERCHANT_LSIT_CARE, getRequest(),
					SelectMerchListResult.class);

			if (list != null && list.size() != 0) {
				if (list.get(0).getRespcode().equals("-2")){//�ж�session�Ƿ�ʧЧ
					isSessionOut = true;
				}

				ImageResultBean be = new ImageResultBean();
				be.setRespBean(list.get(0));
				Map<String , byte[]> mmap = new HashMap<String , byte[]>();
				SelectMerchListResult result = (SelectMerchListResult)list.get(0);
			/*	for(UserCareMerch mp : result.getRows()){
					DownLoadImage.getImageMap(mp.getMerchant().getTrademark_pic(), mmap);
				}*/
				be.setImageMap(mmap);
				response.setData(be);
				if (!list.get(0).getRespcode().equals("0")) {
					ResponseBean res = new ResponseBean();
					res.setMessage("������������");
					response.setData(res);
					response.setError(true);
				} else {
					int[] flags=new int[1];
					flags[0]=Intent.FLAG_ACTIVITY_NO_HISTORY;
					response.setFlags(flags);
					response.setError(false);
				}
			} else if(list.size() == 0){
				ResponseBean res = new ResponseBean();
				res.setMessage("δ���ҵ���ؼ�¼");
				response.setData(res);
				response.setError(true);
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
