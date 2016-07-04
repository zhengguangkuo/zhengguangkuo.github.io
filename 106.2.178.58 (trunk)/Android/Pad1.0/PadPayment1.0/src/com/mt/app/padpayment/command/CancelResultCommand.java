package com.mt.app.padpayment.command;

import java.util.Map;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;

import com.mt.android.db.DbHandle;
import com.mt.android.protocol.util.ProtocolRespCode;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.common.DbHelp;
import com.mt.app.padpayment.common.GlobalParameters;
import com.mt.app.padpayment.common.IsoCommHandler;
import com.mt.app.padpayment.message.iso.trans.ReturnGoodsBean;
import com.mt.app.padpayment.requestbean.ReadCardReqBean;
import com.mt.app.padpayment.requestbean.ReturnGoodsMoney;
import com.mt.app.padpayment.responsebean.ResultRespBean;
import com.mt.app.padpayment.tools.PackUtil;
import com.mt.app.padpayment.tools.SystemUtil;
import com.mt.app.padpayment.tools.TransSequence;

public class CancelResultCommand extends AbstractCommand {
	SystemUtil sysutil = new SystemUtil();
	DbHandle db = new DbHandle();
	private String originaldealdmun = Controller.session.get("ODnum")
			.toString();;
	private String originaldealdate = Controller.session.get("date").toString();;
	private static Logger log = Logger.getLogger(CancelResultCommand.class);
	private String respcode = "";// 用于存放响应码
	private String cardnum = Controller.session.get("CardNum").toString();
	ReadCardReqBean readcard = new ReadCardReqBean();
	ReturnGoodsMoney returnmoney = new ReturnGoodsMoney();

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
		/* 获取卡号,交易类型 */
		Response response = new Response();
		try {
			ReturnGoodsBean returngoodsresult = returngoodsresult(cardnum);
			if (returngoodsresult == null
					|| !returngoodsresult.getRespCode().equals("00")) {
				response.setError(true);
			}

			Bundle bundle = new Bundle();
			ResultRespBean result = new ResultRespBean();
			result.setMessage(respcode);
			bundle.putSerializable("ResultRespBean", result);
			response.setBundle(bundle);
			int[] flags = new int[1];
			flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
			response.setFlags(flags);
			setResponse(response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onAfterExecute() {
		super.onAfterExecute();
		Response response = getResponse();

		if (response.isError()) {
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_CANCELRESULT"));
		} else {
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_CANCELRESULT"));
		}
		setResponse(response);
	}

	/**
	 * 退货
	 * 
	 * @return returngoodsresult结果集
	 */
	private ReturnGoodsBean returngoodsresult(String cardnum) {
		ReturnGoodsMoney returnmoney = (ReturnGoodsMoney) getRequest()
				.getData();
		String str3 = "000000000000"
				+ PackUtil.fillField(originaldealdate, 4, true, "0");
		ReturnGoodsBean returngoods = new ReturnGoodsBean();
		returngoods.setMsgId("0220");
		//String str = PackUtil.fillField(cardnum, 19, true, "0");
		returngoods.setTrack2(cardnum);// 设置卡号2
		returngoods.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// 受卡机终端标识码(终端号)

		returngoods.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// 受卡方标识码
		returngoods.setProcessCode("200000");// 交易处理码3
		String str1 = PackUtil.fillField(returnmoney.getReturnmoney(), 12,
				true, "0");
		returngoods.setTransAmount(str1);// 交易金额
		returngoods.setSysTraceAuditNum(TransSequence.getSysTraceAuditNum());// 受卡方系统跟踪号11
		returngoods.setDateExpired(null);// 卡有效期14由刷卡取得
		returngoods.setServiceEntryMode("070");// 服务点输入方式码22
		returngoods.setServiceConditionMode("00");// 服务点条件码25
		returngoods.setServicePINCaptureCode(null);// 服务点PIN获取码26
		returngoods.setRetReferNum(originaldealdmun);// 检索参考号37原交易参考号
		returngoods.setCurrencyTransCode("156");// 交易货币代码49
		returngoods.setPinData(null);// 个人标识码数据52
		returngoods.setSecurityRelatedControl("1000000000000000");// 安全控制信息53
		returngoods.setReservedPrivate2("25"+DbHelp.getBatchNum()+"000");// 批次号
		returngoods.setOriginalMessage(str3);// 原始信息域61
		returngoods.setMessageAuthentCode("11111111");// MAC64
		db.insertObject("TBL_TMPFlOW", returngoods); // 插入临时流水表
		
		db.update("TBL_TMPFlOW", new String[] { "USER_ID" },
				new String[] {Controller.session.get("userID")+"" }, "SYS_TRACE_AUDIT_NUM = ?",
				new String[] { returngoods.getSysTraceAuditNum() });
		
		IsoCommHandler comm = new IsoCommHandler();
		ReturnGoodsBean goodsbean = (ReturnGoodsBean) comm
				.sendIsoMsg(returngoods); // 打包并发送报文

		if (comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT) {
			respcode = "请检查您的网络连接！";
		} else {
			/**MAC校验失败 **/
			if (comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED) {
				db.delete("TBL_TMPFlOW", null, null) ;
			}
			
			if (goodsbean != null) {
				db.updateRespObject("TBL_TMPFlOW", goodsbean,
						"SYS_TRACE_AUDIT_NUM = ?", // 更新临时流水表
						new String[] { goodsbean.getSysTraceAuditNum() });
				db.transferTable("TBL_TMPFlOW", "TBL_FlOW"); // 交易成功将临时流水表中数据转存到流水表
				if (goodsbean.getRespCode().equals("00")) {
					respcode = "退货成功";
				} else {
					if (goodsbean.getReservedPrivate4() != null) {
						respcode = goodsbean.getReservedPrivate4();
					} else {
						Map<String, String> map = db
								.rawQueryOneRecord(
										"select MESSAGE from TBL_RESPONSE_CODE where RESP_CODE = ?",
										new String[] { goodsbean.getRespCode() });
						if (map.get("MESSAGE") != null) { // 对应的列数，这里取查询结果第3列
							respcode = map.get("MESSAGE");
						}
					}
				}
			} else {
				if(comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED){
					respcode = "MAC校验失败！";
				}else if(comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT){
					respcode = "请检查您的网络连接！";
				}else{
					respcode = "请检查您的网络连接！";
				}
			}
		}
		return goodsbean;
	}

}
