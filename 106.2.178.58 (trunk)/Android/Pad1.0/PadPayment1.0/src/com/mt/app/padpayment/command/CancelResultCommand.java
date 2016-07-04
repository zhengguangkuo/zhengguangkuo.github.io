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
	private String respcode = "";// ���ڴ����Ӧ��
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
		/* ��ȡ����,�������� */
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
	 * �˻�
	 * 
	 * @return returngoodsresult�����
	 */
	private ReturnGoodsBean returngoodsresult(String cardnum) {
		ReturnGoodsMoney returnmoney = (ReturnGoodsMoney) getRequest()
				.getData();
		String str3 = "000000000000"
				+ PackUtil.fillField(originaldealdate, 4, true, "0");
		ReturnGoodsBean returngoods = new ReturnGoodsBean();
		returngoods.setMsgId("0220");
		//String str = PackUtil.fillField(cardnum, 19, true, "0");
		returngoods.setTrack2(cardnum);// ���ÿ���2
		returngoods.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// �ܿ����ն˱�ʶ��(�ն˺�)

		returngoods.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// �ܿ�����ʶ��
		returngoods.setProcessCode("200000");// ���״�����3
		String str1 = PackUtil.fillField(returnmoney.getReturnmoney(), 12,
				true, "0");
		returngoods.setTransAmount(str1);// ���׽��
		returngoods.setSysTraceAuditNum(TransSequence.getSysTraceAuditNum());// �ܿ���ϵͳ���ٺ�11
		returngoods.setDateExpired(null);// ����Ч��14��ˢ��ȡ��
		returngoods.setServiceEntryMode("070");// ��������뷽ʽ��22
		returngoods.setServiceConditionMode("00");// �����������25
		returngoods.setServicePINCaptureCode(null);// �����PIN��ȡ��26
		returngoods.setRetReferNum(originaldealdmun);// �����ο���37ԭ���ײο���
		returngoods.setCurrencyTransCode("156");// ���׻��Ҵ���49
		returngoods.setPinData(null);// ���˱�ʶ������52
		returngoods.setSecurityRelatedControl("1000000000000000");// ��ȫ������Ϣ53
		returngoods.setReservedPrivate2("25"+DbHelp.getBatchNum()+"000");// ���κ�
		returngoods.setOriginalMessage(str3);// ԭʼ��Ϣ��61
		returngoods.setMessageAuthentCode("11111111");// MAC64
		db.insertObject("TBL_TMPFlOW", returngoods); // ������ʱ��ˮ��
		
		db.update("TBL_TMPFlOW", new String[] { "USER_ID" },
				new String[] {Controller.session.get("userID")+"" }, "SYS_TRACE_AUDIT_NUM = ?",
				new String[] { returngoods.getSysTraceAuditNum() });
		
		IsoCommHandler comm = new IsoCommHandler();
		ReturnGoodsBean goodsbean = (ReturnGoodsBean) comm
				.sendIsoMsg(returngoods); // ��������ͱ���

		if (comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT) {
			respcode = "���������������ӣ�";
		} else {
			/**MACУ��ʧ�� **/
			if (comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED) {
				db.delete("TBL_TMPFlOW", null, null) ;
			}
			
			if (goodsbean != null) {
				db.updateRespObject("TBL_TMPFlOW", goodsbean,
						"SYS_TRACE_AUDIT_NUM = ?", // ������ʱ��ˮ��
						new String[] { goodsbean.getSysTraceAuditNum() });
				db.transferTable("TBL_TMPFlOW", "TBL_FlOW"); // ���׳ɹ�����ʱ��ˮ��������ת�浽��ˮ��
				if (goodsbean.getRespCode().equals("00")) {
					respcode = "�˻��ɹ�";
				} else {
					if (goodsbean.getReservedPrivate4() != null) {
						respcode = goodsbean.getReservedPrivate4();
					} else {
						Map<String, String> map = db
								.rawQueryOneRecord(
										"select MESSAGE from TBL_RESPONSE_CODE where RESP_CODE = ?",
										new String[] { goodsbean.getRespCode() });
						if (map.get("MESSAGE") != null) { // ��Ӧ������������ȡ��ѯ�����3��
							respcode = map.get("MESSAGE");
						}
					}
				}
			} else {
				if(comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED){
					respcode = "MACУ��ʧ�ܣ�";
				}else if(comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT){
					respcode = "���������������ӣ�";
				}else{
					respcode = "���������������ӣ�";
				}
			}
		}
		return goodsbean;
	}

}
