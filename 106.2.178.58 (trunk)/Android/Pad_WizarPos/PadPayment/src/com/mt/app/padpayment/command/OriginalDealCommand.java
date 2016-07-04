package com.mt.app.padpayment.command;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.os.Bundle;

import com.mt.android.db.DbHandle;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.requestbean.VouchersReqBean;
import com.mt.app.padpayment.responsebean.ResultRespBean;

public class OriginalDealCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(OriginalDealCommand.class);

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
		Response response = new Response();
		VouchersReqBean vouchers = (VouchersReqBean)getRequest().getData();
		Bundle bundle = new Bundle();
		
		if(vouchers != null){  //���û��ڽ���������Ľ���ƾ֤�Ŵ���session��
			Controller.session.put("Vouchers", vouchers.getVouchers());
			
			
			//�����ݿ���в�ѯ�����ݣ����ݿ��ź�ԭ����ƾ֤���ֶΣ�����ʼ������
	    	DbHandle handle = new DbHandle();
	    	//��ѯ�����ţ��Ż݄��ֿ۽�� ����Ա���ۿ۽�ʵ�ս�ԭ����ƾ֤�ţ�ԭ������ˮ�ţ������ײο��ţ������ο��ţ�
	    	String[] columns = new String[]{"TRACK2","DISCOUNT_AMOUNT", 
	    			"TRANS_AMOUNT", "SYS_TRACE_AUDIT_NUM", "RET_REFER_NUM_1"};
	    	List<Map<String , String>> list = null;
	    	List<Map<String , String>> list2 = null;
	    	if (Controller.session.get("type") != null
					&& Controller.session.get("type").equals("consume")) { //���ѳ������Ż�ȯ�һ�����
	    		list = handle.select("TBL_FlOW", columns, "RESP_CODE_1 = '00' and MSG_ID = '0200' and PROCESS_CODE = '000000' and SYS_TRACE_AUDIT_NUM=?", 
		    			new String[]{vouchers.getVouchers()}, null, null, null);
	    		
	    		list2 = handle.select("TBL_FlOW", columns, "RESP_CODE_1 = '00' and MSG_ID = '0200' and PROCESS_CODE = '200000' and ORIGINAL_MESSAGE like '%"+vouchers.getVouchers()+"%'", 
		    			null, null, null, null);
	    		if (list.size()==0&&list2.size()==0){ //�Ż�ȯ�һ�����
	    			Controller.session.put("type","coupon");
	    			list = handle.select("TBL_FlOW", columns, "RESP_CODE_1 = '00' and MSG_ID = '0200' and PROCESS_CODE = '020000' and SYS_TRACE_AUDIT_NUM=?", 
			    			new String[]{vouchers.getVouchers()}, null, null, null);
		    		
		    		list2 = handle.select("TBL_FlOW", columns, "RESP_CODE_1 = '00' and MSG_ID = '0200' and PROCESS_CODE = '021000' and ORIGINAL_MESSAGE like '%"+vouchers.getVouchers()+"%'", 
			    			null, null, null, null);
	    		}
			} else if (Controller.session.get("type") != null
					&& Controller.session.get("type").equals("credit")) {// ���ֳ���
				list = handle.select("TBL_FlOW", columns, "RESP_CODE_1 = '00' and MSG_ID = '0200' and PROCESS_CODE = '100000' and SYS_TRACE_AUDIT_NUM=?", 
		    			new String[]{vouchers.getVouchers()}, null, null, null);
	    		
	    		list2 = handle.select("TBL_FlOW", columns, "RESP_CODE_1 = '00' and MSG_ID = '0200' and PROCESS_CODE = '300000' and ORIGINAL_MESSAGE like '%"+vouchers.getVouchers()+"%'", 
		    			null, null, null, null);
			}
	    	
	    	
	    	if (list2!= null &&list2.size()>0) {
	    		ResultRespBean bean = new ResultRespBean();
				bean.setMessage("�ý����Ѿ������ɹ���");
				
				bundle.putSerializable("ResultRespBean", bean);
				response.setBundle(bundle);
				response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_CANCELRESULT"));
	    	} else {
	    		if (list  != null && list.size() > 0) {
		    		response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_ORIGINALDEAL"));
		    	} else {
		    		ResultRespBean bean = new ResultRespBean();
					bean.setMessage("δ�鵽��ʷ���ף�");
					
					bundle.putSerializable("ResultRespBean", bean);
					response.setBundle(bundle);
					response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_CANCELRESULT"));
		    	}
	    	}
	    	
		} else {
			ResultRespBean bean = new ResultRespBean();
			bean.setMessage("��δ���뽻��ƾ֤�ţ�");
			
			bundle.putSerializable("ResultRespBean", bean);
			response.setBundle(bundle);
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_CANCELRESULT"));
		}
		
		
		
		setResponse(response);
	}

}
