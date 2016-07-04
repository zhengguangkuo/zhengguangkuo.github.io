package com.mt.app.payment.command;

import java.util.List;

import org.apache.log4j.Logger;

import android.content.Intent;

import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.sys.util.StringUtil;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.activity.App_Manage1Activity;
import com.mt.app.payment.activity.App_Manage2Activity;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;
import com.mt.app.payment.requestbean.ObtainPayAppReqBean;
import com.mt.app.payment.responsebean.AppBind_DataBean;
import com.mt.app.payment.responsebean.ImageResultBean;

public class App_ManageunbindCommand extends AbstractCommand{
	private static Logger log = Logger.getLogger(App_ManageunbindCommand.class);
	ImageResultBean be = new ImageResultBean();
	private boolean isSessionOut = false;// �ж�session�Ƿ�ʧЧ
	@Override
protected void go() {
	try {

		log.debug("go");
		Response response = new Response();
		List<ResponseBean> list = DispatchRequest.doHttpRequest(
				Constants.USR_CARD_APP_UNBIND, getRequest(),
				AppBind_DataBean.class);
		/*
		 * �����ݵ���ת���Ľ���(������Ӧ����)��
		 * �˴���Ҫ�����������ڳ�ʼ����һ����ת���Ľ���ģ�
		 * ����Ӧ��װ��response�е�bundle�У���һ�������bundle��ȡ��
		 */
		if (list != null && list.size() > 0) {
			if (list.get(0).getRespcode().equals("-2")){//�ж�session�Ƿ�ʧЧ
				isSessionOut = true;
			}
			if (list.get(0).getRespcode().equals("-1")) {
				ResponseBean res = new ResponseBean();
				res.setMessage(list.get(0).getMessage());
				response.setError(true);
				response.setData(res);
			   setResponse(response);
			} else {
				be.setMessage("���ɹ�");
				Request request = new Request();
				ObtainPayAppReqBean app = new ObtainPayAppReqBean();
				app.setPage(1);
				app.setRows(10);
				app.setAppType("3");
				request.setData(app);
				List<ResponseBean> list1 = DispatchRequest.doHttpRequest(
						Constants.USR_CARD_APP_QUERY, request,
						AppBind_DataBean.class);
				/*
				 * �����ݵ���ת���Ľ���(������Ӧ����)��
				 * �˴���Ҫ�����������ڳ�ʼ����һ����ת���Ľ���ģ�
				 * ����Ӧ��װ��response�е�bundle�У���һ�������bundle��ȡ��
				 */
				
				if (list1 != null && list1.size() > 0) {
					if (list1.get(0).getRespcode().equals("-2")){//�ж�session�Ƿ�ʧЧ
						isSessionOut = true;
					}
					if (list1.get(0).getRespcode().equals("-1")) {
						ResponseBean res1 = new ResponseBean();
						res1.setMessage(list1.get(0).getMessage());
						response.setError(true);
						response.setData(res1);
					   setResponse(response);
						
					} else {
						//��ʼ����ҳ��ѯ�б�ĳ�ʼ������
						
						response.setBussinessType("unbind_sort");
						be.setRespBean(list1.get(0));
						response.setData(be);
						int[] flags1=new int[1];
						flags1[0]=Intent.FLAG_ACTIVITY_NO_HISTORY;
						response.setFlags(flags1);
						response.setError(false);
						setResponse(response);
					}
		} else {
			ResponseBean res2 = new ResponseBean();
			res2.setMessage("������������");
			response.setError(true);
			response.setData(res2);
		    setResponse(response);
		}
		  }
			}else {
				ResponseBean res = new ResponseBean();
				res.setMessage("������������");
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
	// TODO Auto-generated method stub
	super.onAfterExecute();
	Response response = getResponse();
	if(response.isError()){
	response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
}else{
	response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);

}
	if (isSessionOut) {//���sessionʧЧ����ת��¼����
		response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_USER_LOGIN"));
	}
}
}
