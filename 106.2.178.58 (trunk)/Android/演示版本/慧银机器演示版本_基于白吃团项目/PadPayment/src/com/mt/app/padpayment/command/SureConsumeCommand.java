package com.mt.app.padpayment.command;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.os.Bundle;

import com.mt.android.db.DbHandle;
import com.mt.android.protocol.util.ProtocolRespCode;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.adapter.CouponGridAdapter;
import com.mt.app.padpayment.common.DbHelp;
import com.mt.app.padpayment.common.DbInfoImpl;
import com.mt.app.padpayment.common.IsoCommHandler;
import com.mt.app.padpayment.message.iso.trans.CouponsConvertBean;
import com.mt.app.padpayment.requestbean.ConsumeReqBean;
import com.mt.app.padpayment.responsebean.ResultRespBean;
import com.mt.app.padpayment.tools.MoneyUtil;
import com.mt.app.padpayment.tools.PackUtil;
import com.mt.app.padpayment.tools.TransSequence;
import com.wizarpos.mt.PrinterHelper;
import com.wizarpos.mt.PurchaseBill;

/**
 * 优惠券消费command
 * 
 * @author dw
 * 
 */
public class SureConsumeCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(SureConsumeCommand.class);
	private Response response = new Response();
	private String respcode = "";
	private String flowNum = "";
	Bundle bundle = new Bundle();
	
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
		
		response.setError(false);

		ConsumeReqBean reqBean = (ConsumeReqBean) getRequest().getData();
		if (Controller.session.get("succForward").equals(
				ActivityID.map.get("ACTIVITY_ID_PAYSUCCESS"))||"0.00".equals(reqBean.getRealSum())) {// 优惠券兑换
			CouponsConvertBean couponsConvert = doCouponsRequest(reqBean);
			if (couponsConvert != null) {
				reqBean.setSwapCode(couponsConvert.getSwapCode());
				if (reqBean.getRealSum().equals("0.0")||reqBean.getRealSum().equals("0.00")) {
					ResultRespBean result = new ResultRespBean();
					if (respcode.equals("")) {
						result.setMessage("兑换成功！");
						
					} else {
						result.setMessage(respcode);
					}
					bundle.putSerializable("ResultRespBean", result);
					bundle.putString("sysnum", flowNum);
					Controller.session.put("succForward",ActivityID.map.get("ACTIVITY_ID_CANCELRESULT"));
				}
			}
		}

		
		
		if (!respcode.equals("")) {
			response.setError(true);
			ResultRespBean result = new ResultRespBean();
			result.setMessage(respcode);
			bundle.putSerializable("ResultRespBean", result);
			}
		reqBean.setFlowNum(flowNum);
		bundle.putSerializable("ConsumeReqBean", reqBean);
		response.setBundle(bundle);
		setResponse(response);
	}

	@Override
	protected void onAfterExecute() {
		Response response = getResponse();

		if (response != null && response.isError() == false) {
			response.setTargetActivityID((Integer) Controller.session
					.get("succForward"));
		} else {
			
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_CANCELRESULT"));
		}
	}

	/**
	 * 组装优惠券兑换请求，发送请求，接收响应，拆包
	 * 
	 * @param reqBean
	 * @return 返回响应的对象
	 */
	private CouponsConvertBean doCouponsRequest(ConsumeReqBean reqBean) {

		CouponsConvertBean couponsConvert = new CouponsConvertBean();

		couponsConvert.setMsgId("0200"); // 设置交易类型

		String cardNum = (String) Controller.session.get("CardNum");

		couponsConvert.setTrack2(cardNum);// 设置基卡卡号

		couponsConvert.setProcessCode("020000");// 设置处理码

		couponsConvert.setTransAmount(PackUtil.fillField(reqBean.getCouponsSum(),
				12, true, "0")); // 设置交易金额
//		couponsConvert.setTransAmount(PackUtil.fillField("0",
//				12, true, "0")); // 设置交易金额
		couponsConvert.setDiscountAmount(PackUtil.fillField(reqBean.getVipSum(), 12, true, "0")+
				PackUtil.fillField(reqBean.getCouponsSum(), 12, true, "0")+
				PackUtil.fillField(reqBean.getSum(), 12, true, "0"));//附加金额
		
		couponsConvert.setSysTraceAuditNum(TransSequence.getSysTraceAuditNum()); // 受卡方系统跟踪号

		couponsConvert.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// 受卡机终端标识码41
		couponsConvert.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// 受卡方标识码42
		if (CouponGridAdapter.apId!=null) {
			couponsConvert.setOrganId(CouponGridAdapter.apId);//发卷机构标识
			CouponGridAdapter.apId = null;
		}
		
		if (CouponGridAdapter.listChecked!= null){
			couponsConvert.setCouponsAdvertId(CouponGridAdapter.listChecked);//发券对象标识
			CouponGridAdapter.listChecked= null;
		} 

		String pici = DbHelp.getBatchNum();
		couponsConvert.setReservedPrivate2("62"+PackUtil.fillField(pici, 6, true, "0")+"000"); // 设置批次号
		
		String size = reqBean.getCouponsId().length + "";
		size = PackUtil.fillField(size, 3, true, "0");
		String body = "";
		String idsStr = "" ;
		for (int i = 0; i < reqBean.getCouponsId().length; i++) {
			body = body
					+ PackUtil.fillField(reqBean.getCouponsId()[i], 30, false,
							" ");
			idsStr = idsStr + reqBean.getCouponsId()[i]  + "      ";
		}
		couponsConvert.setReservedPrivate3(size + body);  //优惠券兑换信息
		couponsConvert.setMessageAuthentCode("11111111");

		DbHandle db = new DbHandle();
		db.delete("TBL_TMPFlOW", null, null);
		db.insertObject("TBL_TMPFlOW", couponsConvert); // 插入临时流水表

		Map map2 = db.selectOneRecord("TBL_TMPFlOW",new String[]{ "RESERVED_PRIVATE2"}, "SYS_TRACE_AUDIT_NUM = ?",
				new String[] { couponsConvert.getSysTraceAuditNum() },null,null,null);
		// 将其他字段插入流水表
		db.update("TBL_TMPFlOW", new String[] { 
				"VIP_AMOUNT", "ORIGIN_AMOUNT","USER_ID" },
				new String[] {  reqBean.getVipSum(),
						reqBean.getSum(),Controller.session.get("userID")+"" }, "SYS_TRACE_AUDIT_NUM = ?",
				new String[] { couponsConvert.getSysTraceAuditNum() });

		
		IsoCommHandler comm = new IsoCommHandler();
		
		CouponsConvertBean bean =(CouponsConvertBean) comm.sendIsoMsg(couponsConvert);
		if(comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT ){
			ResultRespBean result = new ResultRespBean();
			result.setMessage("未收到响应！");
			Bundle bundle = new Bundle();
			bundle.putSerializable("ResultRespBean", result);
			response.setBundle(bundle);
			db.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // 交易失败，将临时流水表中数据转存到冲正表
			db.update("TBL_REVERSAL", new String[]{"FLUSH_OCUNT", "FLUSH_RESULT"}, new String[]{"0","-1"}, "SYS_TRACE_AUDIT_NUM = ?", new String[] { couponsConvert.getSysTraceAuditNum() });
			
		}
		/**MAC校验失败 **/
		if (comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED) {
			db.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // 交易失败，将临时流水表中数据转存到冲正表
			db.update("TBL_REVERSAL", new String[]{"FLUSH_OCUNT", "FLUSH_RESULT"}, new String[]{"0","-1"}, "SYS_TRACE_AUDIT_NUM = ?", new String[] { couponsConvert.getSysTraceAuditNum() });
		} 
		
		if (bean != null) {
			db.updateRespObject("TBL_TMPFlOW", bean, "SYS_TRACE_AUDIT_NUM = ?", // 更新临时流水表
					new String[] { bean.getSysTraceAuditNum() });
				db.transferTable("TBL_TMPFlOW", "TBL_FlOW"); // 交易成功将临时流水表中数据转存到流水表
				
				db.update("TBL_FlOW", new String[]{"COUPONS_IDS","COUPONS_TYPES"}
				, new String[]{idsStr.substring(0, idsStr.length()) , "代金券"}
				, "SYS_TRACE_AUDIT_NUM = ?", new String[]{bean.getSysTraceAuditNum()});
			
				if (!bean.getRespCode().equals("00")) {
					if (bean.getReservedPrivate4() != null) {
						respcode = bean.getReservedPrivate4();
					} else {
						Map<String, String> map = db
								.rawQueryOneRecord(
										"select MESSAGE from TBL_RESPONSE_CODE where RESP_CODE = ?",
										new String[] { bean.getRespCode() });
						if (map.get("MESSAGE") != null) { // 对应的列数，这里取查询结果第3列
							respcode = map.get("MESSAGE");
						}
					}
				} else {
					flowNum =  bean.getSysTraceAuditNum();
				}
			
			  
		} else {
			ResultRespBean result = new ResultRespBean();
			if(comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED){
				result.setMessage("Mac校验失败！");
				respcode = "Mac校验失败！";
			}else{
				result.setMessage("请检查网络连接！");
				respcode = "请检查网络连接！";
			}
			
			
			bundle.putSerializable("ResultRespBean", result);
			Controller.session.put("succForward",ActivityID.map.get("ACTIVITY_ID_CANCELRESULT"));
			
		}
		return bean;
	}
	
}
