package com.mt.app.padpayment.command;

import java.util.Map;

import org.apache.log4j.Logger;

import android.os.Bundle;

import com.mt.android.db.DbHandle;
import com.mt.android.protocol.util.ProtocolRespCode;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.common.DbHelp;
import com.mt.app.padpayment.common.IsoCommHandler;
import com.mt.app.padpayment.message.iso.trans.ScoreConsumeBean;
import com.mt.app.padpayment.requestbean.CreditConsumeReqBean;
import com.mt.app.padpayment.responsebean.ResultRespBean;
import com.mt.app.padpayment.tools.PackUtil;
import com.mt.app.padpayment.tools.TransSequence;

/**
 * 积分消费的command
 * 
 * @author dw
 * 
 */
public class CreditsConsumeSubmitCommand extends AbstractCommand {
	Response response = new Response();

	private static Logger log = Logger
			.getLogger(CreditsConsumeSubmitCommand.class);

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

		CreditConsumeReqBean reqBean = (CreditConsumeReqBean) getRequest()
				.getData();
		ScoreConsumeBean consume = doConsumeRequest(reqBean);

		if (consume != null) {

			Bundle bundle = new Bundle();
			ResultRespBean resultRespBean = new ResultRespBean();
			if (!consume.getRespCode().equals("00")) {
				response.setError(true);
				if (!consume.getReservedPrivate4().equals("")) {
					resultRespBean.setMessage(consume.getReservedPrivate4());
				} else {
					Map<String, String> map = new DbHandle().selectOneRecord(
							"TBL_RESPONSE_CODE", new String[] { "RESP_CODE",
									"MESSAGE" }, "RESP_CODE = ?",
							new String[] { consume.getRespCode() }, null, null,
							null);
					if (map != null) {
						resultRespBean.setMessage(map.get("MESSAGE"));
					}
				}

			} else {
				resultRespBean.setMessage("交易成功！");

			}
			bundle.putSerializable("ResultRespBean", resultRespBean);
			response.setBundle(bundle);
		}
		setResponse(response);

	}

	@Override
	protected void onAfterExecute() {
		// TODO Auto-generated method stub
		super.onAfterExecute();
		Response response = getResponse();

		response.setTargetActivityID(ActivityID.map
				.get("ACTIVITY_ID_CANCELRESULT"));

		setResponse(response);

	}

	/**
	 * 组装积分消费请求，发送请求，接收响应，拆包
	 * 
	 * @param reqBean
	 * @return 返回响应的对象
	 */
	private ScoreConsumeBean doConsumeRequest(CreditConsumeReqBean reqBean) {

		ScoreConsumeBean consume = new ScoreConsumeBean();

		consume.setMsgId("0200"); // 设置交易类型

		String cardNum = (String) Controller.session.get("CardNum");
		consume.setTrack2(cardNum);// 设置卡号

		consume.setProcessCode("100000");// 设置处理码

		consume.setTransAmount(PackUtil.fillField(reqBean.getConsumeMoney(),
				12, true, "0")); // 设置交易金额

		consume.setSysTraceAuditNum(TransSequence.getSysTraceAuditNum()); // 受卡方系统跟踪号

		consume.setDateExpired(null);// 卡有效期

		consume.setServiceEntryMode("005");// 服务点输入方式码

		consume.setServicePINCaptureCode(null);// 服务点pin获取码

		consume.setServiceConditionMode("00");// 服务点条件码

		consume.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// 受卡机终端标识码41
		consume.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// 受卡方标识码42
		
		consume.setOrganId(reqBean.getSysInstId());//发券机构标识
		

		consume.setCurrencyTransCode("156");// 交易货币代码

		consume.setPinData(null);// 个人标识数据

		consume.setSecurityRelatedControl(null);// 安全控制信息

		consume.setReservedPrivate2("52"+DbHelp.getBatchNum()+"000");// 批次号

		consume.setCouponsAdvertId(reqBean.getIssId());// 发卷对象标识

		consume.setMessageAuthentCode("11111111");// mac

		DbHandle db = new DbHandle();
		db.insertObject("TBL_TMPFlOW", consume); // 插入临时流水表
		db.update("TBL_TMPFlOW", new String[] { "USER_ID" },
				new String[] {Controller.session.get("userID")+"" }, "SYS_TRACE_AUDIT_NUM = ?",
				new String[] { consume.getSysTraceAuditNum() });

		IsoCommHandler comm = new IsoCommHandler();
		ScoreConsumeBean bean = (ScoreConsumeBean) comm.sendIsoMsg(consume);// 打包并发送报文
		if (comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT) {
			db.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // 交易失败，将临时流水表中数据转存到冲正表
			db.update("TBL_REVERSAL", new String[] { "FLUSH_OCUNT",
					"FLUSH_RESULT" }, new String[] { "0", "-1" },
					"SYS_TRACE_AUDIT_NUM = ?",
					new String[] { consume.getSysTraceAuditNum() });
		}
		/**MAC校验失败 **/
		if (comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED) {
			db.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // 交易失败，将临时流水表中数据转存到冲正表
			db.update("TBL_REVERSAL", new String[]{"FLUSH_OCUNT", "FLUSH_RESULT"}, new String[]{"0","-1"}, "SYS_TRACE_AUDIT_NUM = ?", new String[] {consume.getSysTraceAuditNum() });
		} 
		if (bean != null) {
			db.updateRespObject("TBL_TMPFlOW", bean, "SYS_TRACE_AUDIT_NUM = ?", // 更新临时流水表
					new String[] { bean.getSysTraceAuditNum() });

			db.transferTable("TBL_TMPFlOW", "TBL_FlOW"); // 交易成功将临时流水表中数据转存到流水表

		} else {
			response.setError(true);
			Bundle bundle = new Bundle();
			ResultRespBean result = new ResultRespBean();
			result.setMessage("请检查网络连接！");
			bundle.putSerializable("ResultRespBean", result);
			response.setBundle(bundle);
		}
		return bean;
	}
}
