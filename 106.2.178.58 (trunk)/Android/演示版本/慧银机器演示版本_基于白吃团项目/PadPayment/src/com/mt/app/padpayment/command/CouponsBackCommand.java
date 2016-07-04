package com.mt.app.padpayment.command;

import java.util.Map;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mt.android.db.DbHandle;
import com.mt.android.protocol.util.ProtocolRespCode;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.sys.util.StringFormat;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.common.DbHelp;
import com.mt.app.padpayment.common.GlobalParameters;
import com.mt.app.padpayment.common.IsoCommHandler;
import com.mt.app.padpayment.message.iso.trans.ReceiveCouponsBean;
import com.mt.app.padpayment.requestbean.CouponsBackBean;
import com.mt.app.padpayment.responsebean.ResultRespBean;
import com.mt.app.padpayment.tools.PackUtil;
import com.mt.app.padpayment.tools.TransSequence;

/**
 * 优惠劵申领结果
 * 
 * @author lzw
 * 
 */
public class CouponsBackCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(TestCommand.class);
	private ResultRespBean resultBean = new ResultRespBean();
	private Bundle bundle = new Bundle();

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
		try {
			CouponsBackBean bean = (CouponsBackBean) getRequest().getData();
			if (bean.getActIds().equals("")) {
				response.setError(true);
				response.setData("请选择要派发的优惠券！");
			} else if (bean.getCount().equals("")) {
				response.setError(true);
				response.setData("请输入派发的优惠券数量！");
			} else {
				receiveCouponsBeanResult(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		int[] flags = new int[1];
		flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
		response.setFlags(flags);
		response.setBundle(bundle);
		setResponse(response);

		// Response response = new Response();
		// int[] flags=new int[1];
		// flags[0]=Intent.FLAG_ACTIVITY_NO_HISTORY;
		// response.setFlags(flags);
		// response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_CouponsSuccActivity"));
		// setResponse(response);
	}

	@Override
	protected void onAfterExecute() {
		super.onAfterExecute();
		Response response = getResponse();

		if (response.isError()) {
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		} else {

			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_CANCELRESULT"));
		}
		setResponse(response);
	}

	/**
	 * 优惠劵申领结果
	 * 
	 * @return
	 */
	TransSequence trans = new TransSequence();
	private String respcode = "";// 用于存放响应码
	private String reservedPrivate4;// 应答信息

	private void receiveCouponsBeanResult(CouponsBackBean bean) {

		try {
			String auditnum = trans.getSysTraceAuditNum();

			ReceiveCouponsBean receiveCoupons = new ReceiveCouponsBean();
			receiveCoupons.setMsgId("0220");// 优惠劵领用

			String cardNum = (String) Controller.session.get("CardNum");
			receiveCoupons.setTrack2(cardNum);// 机卡卡号
			receiveCoupons.setProcessCode("022000");// 交易处理码 3
			receiveCoupons.setTransAmount(PackUtil.fillField(bean.getMoney(),
					12, true, "0"));//交易金额
			receiveCoupons.setSysTraceAuditNum(TransSequence
					.getSysTraceAuditNum());// 受卡方系统跟踪号11
			receiveCoupons.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// 受卡机终端标识码41
			receiveCoupons.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// 受卡方标识码42
			receiveCoupons.setCouponsAdvertId(bean.getIssuerId());// 发券对象标识
			receiveCoupons.setOrganId(bean.getC_iss_id());//发券机构标识
			
			receiveCoupons.setReservedPrivate2("61"+DbHelp.getBatchNum()+"000"); // 设置批次号
			
			String scount = bean.getCount();// 领取数量
			scount = PackUtil.fillField(scount, 3, true, "0");
			String actId = bean.getActIds();
			actId = PackUtil.fillField(actId, 30, false, " ");
			receiveCoupons.setReservedPrivate3(scount + actId);// 优惠劵领用
			receiveCoupons.setMessageAuthentCode("11111111");// mac 64
			IsoCommHandler comm = new IsoCommHandler();

			DbHandle db = new DbHandle();

			db.insertObject("TBL_TMPFlOW", receiveCoupons);
			db.update("TBL_TMPFlOW", new String[] { "USER_ID" },
					new String[] {Controller.session.get("userID")+"" }, "SYS_TRACE_AUDIT_NUM = ?",
					new String[] { receiveCoupons.getSysTraceAuditNum() });
			ReceiveCouponsBean rcb = (ReceiveCouponsBean) comm
					.sendIsoMsg(receiveCoupons);
			if(comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT){
				db.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // 交易失败，将临时流水表中数据转存到冲正表
				db.update("TBL_REVERSAL", new String[]{"FLUSH_OCUNT", "FLUSH_RESULT"}, new String[]{"0","-1"}, "SYS_TRACE_AUDIT_NUM = ?", new String[] { receiveCoupons.getSysTraceAuditNum() });
			} 
			/**MAC校验失败 **/
			if (comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED) {
				db.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // 交易失败，将临时流水表中数据转存到冲正表
				db.update("TBL_REVERSAL", new String[]{"FLUSH_OCUNT", "FLUSH_RESULT"}, new String[]{"0","-1"}, "SYS_TRACE_AUDIT_NUM = ?", new String[] { receiveCoupons.getSysTraceAuditNum() });
			} 
			if (rcb != null){
				db.updateRespObject("TBL_TMPFlOW", rcb, "SYS_TRACE_AUDIT_NUM = ?", // 更新临时流水表
						new String[] { rcb.getSysTraceAuditNum() });
				db.transferTable("TBL_TMPFlOW", "TBL_FlOW"); // 交易成功将临时流水表中数据转存到流水表
				
			}
			
			
			respcode = rcb.getRespCode();
			reservedPrivate4 = rcb.getReservedPrivate4();
			if (StringFormat.isNull(respcode).equalsIgnoreCase("00")) {// 响应成功

				log.info("优惠劵申领结果为：" + reservedPrivate4);

				resultBean.setMessage("优惠劵申领成功！");

				bundle.putSerializable("ResultRespBean", resultBean);

			} else {
				if (reservedPrivate4 != null) {
					resultBean.setMessage(reservedPrivate4);

					bundle.putSerializable("ResultRespBean", resultBean);
				}
			}

		} catch (Exception ex) {
			resultBean.setMessage("请检查网络连接！");

			bundle.putSerializable("ResultRespBean", resultBean);
			ex.printStackTrace();
		}

	}

}
