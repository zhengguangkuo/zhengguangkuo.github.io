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
		
		if(vouchers != null){  //将用户在界面上输入的交易凭证号存入session中
			Controller.session.put("Vouchers", vouchers.getVouchers());
			
			
			//从数据库表中查询出数据（根据卡号和原交易凭证号字段），初始化界面
	    	DbHandle handle = new DbHandle();
	    	//查询：卡号，优惠劵抵扣金额 ，会员卡折扣金额，实收金额，原交易凭证号（原交易流水号），交易参考号（检索参考号）
	    	String[] columns = new String[]{"TRACK2","DISCOUNT_AMOUNT", 
	    			"TRANS_AMOUNT", "SYS_TRACE_AUDIT_NUM", "RET_REFER_NUM_1"};
	    	List<Map<String , String>> list = null;
	    	List<Map<String , String>> list2 = null;
	    	if (Controller.session.get("type") != null
					&& Controller.session.get("type").equals("consume")) { //消费撤销或优惠券兑换撤销
	    		list = handle.select("TBL_FlOW", columns, "RESP_CODE_1 = '00' and MSG_ID = '0200' and PROCESS_CODE = '000000' and SYS_TRACE_AUDIT_NUM=?", 
		    			new String[]{vouchers.getVouchers()}, null, null, null);
	    		
	    		list2 = handle.select("TBL_FlOW", columns, "RESP_CODE_1 = '00' and MSG_ID = '0200' and PROCESS_CODE = '200000' and ORIGINAL_MESSAGE like '%"+vouchers.getVouchers()+"%'", 
		    			null, null, null, null);
	    		if (list.size()==0&&list2.size()==0){ //优惠券兑换撤销
	    			Controller.session.put("type","coupon");
	    			list = handle.select("TBL_FlOW", columns, "RESP_CODE_1 = '00' and MSG_ID = '0200' and PROCESS_CODE = '020000' and SYS_TRACE_AUDIT_NUM=?", 
			    			new String[]{vouchers.getVouchers()}, null, null, null);
		    		
		    		list2 = handle.select("TBL_FlOW", columns, "RESP_CODE_1 = '00' and MSG_ID = '0200' and PROCESS_CODE = '021000' and ORIGINAL_MESSAGE like '%"+vouchers.getVouchers()+"%'", 
			    			null, null, null, null);
	    		}
			} else if (Controller.session.get("type") != null
					&& Controller.session.get("type").equals("credit")) {// 积分撤销
				list = handle.select("TBL_FlOW", columns, "RESP_CODE_1 = '00' and MSG_ID = '0200' and PROCESS_CODE = '100000' and SYS_TRACE_AUDIT_NUM=?", 
		    			new String[]{vouchers.getVouchers()}, null, null, null);
	    		
	    		list2 = handle.select("TBL_FlOW", columns, "RESP_CODE_1 = '00' and MSG_ID = '0200' and PROCESS_CODE = '300000' and ORIGINAL_MESSAGE like '%"+vouchers.getVouchers()+"%'", 
		    			null, null, null, null);
			}
	    	
	    	
	    	if (list2!= null &&list2.size()>0) {
	    		ResultRespBean bean = new ResultRespBean();
				bean.setMessage("该交易已经撤销成功！");
				
				bundle.putSerializable("ResultRespBean", bean);
				response.setBundle(bundle);
				response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_CANCELRESULT"));
	    	} else {
	    		if (list  != null && list.size() > 0) {
		    		response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_ORIGINALDEAL"));
		    	} else {
		    		ResultRespBean bean = new ResultRespBean();
					bean.setMessage("未查到历史交易！");
					
					bundle.putSerializable("ResultRespBean", bean);
					response.setBundle(bundle);
					response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_CANCELRESULT"));
		    	}
	    	}
	    	
		} else {
			ResultRespBean bean = new ResultRespBean();
			bean.setMessage("您未输入交易凭证号！");
			
			bundle.putSerializable("ResultRespBean", bean);
			response.setBundle(bundle);
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_CANCELRESULT"));
		}
		
		
		
		setResponse(response);
	}

}
