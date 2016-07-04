package com.mt.app.payment.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;

import com.miteno.mpay.entity.MpayApp;
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
import com.mt.app.payment.responsebean.ObtainAllPayAppResult;
import com.mt.app.payment.responsebean.ObtainPayAppResult;
import com.mt.app.tab.activity.TabEleCardActivity;

public class EleCardAllPayAppCommand extends AbstractCommand {

	private static Logger log = Logger.getLogger(EleCardAllPayAppCommand.class);
	private boolean isSessionOut = false;// �ж�session�Ƿ�ʧЧ

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
			String info = "";
			List<ResponseBean> list = DispatchRequest.doHttpRequest(Constants.USR_CARD_APP_QUERY, getRequest(), // ����Ӧ���б��ѯ��url
					ObtainAllPayAppResult.class);
			/*
			 * �����ݵ���ת���Ľ���(������Ӧ����)�� �˴���Ҫ�����������ڳ�ʼ����һ����ת���Ľ���ģ�
			 * ����Ӧ��װ��response�е�bundle�У���һ�������bundle��ȡ��
			 */
			// Bundle bundle = new Bundle();
			// ArrayList<ResponseBean> bl = (ArrayList<ResponseBean>)list;
			// bundle.putSerializable("app", bl);
			// response.setBundle(bundle);
			response.setBussinessType("appQuery");

			if (list != null && list.size() > 0) {
				if (list.get(0).getRespcode().equals("-2")) {// �ж�session�Ƿ�ʧЧ
					isSessionOut = true;
				}

				ImageResultBean be = new ImageResultBean();
				be.setRespBean(list.get(0));
				Map<String, byte[]> mmap = new HashMap<String, byte[]>();
				ObtainAllPayAppResult result = (ObtainAllPayAppResult) list.get(0);
				for (MpayApp mp : result.getRows()) {
					DownLoadImage.getImageMap(mp.getPic_path(), mmap);
				}
				be.setImageMap(mmap);
				response.setData(be);
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
	protected void onAfterExecute() {
		Response response = getResponse();

		if (response.isError()) {
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		} else {
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		}
		if (isSessionOut) {// ���sessionʧЧ����ת��¼����
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_USER_LOGIN"));
		}

	}
}
