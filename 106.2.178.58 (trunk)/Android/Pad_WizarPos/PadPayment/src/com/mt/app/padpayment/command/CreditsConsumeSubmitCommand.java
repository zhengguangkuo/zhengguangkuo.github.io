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
 * �������ѵ�command
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
				resultRespBean.setMessage("���׳ɹ���");

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
	 * ��װ�����������󣬷������󣬽�����Ӧ�����
	 * 
	 * @param reqBean
	 * @return ������Ӧ�Ķ���
	 */
	private ScoreConsumeBean doConsumeRequest(CreditConsumeReqBean reqBean) {

		ScoreConsumeBean consume = new ScoreConsumeBean();

		consume.setMsgId("0200"); // ���ý�������

		String cardNum = (String) Controller.session.get("CardNum");
		consume.setTrack2(cardNum);// ���ÿ���

		consume.setProcessCode("100000");// ���ô�����

		consume.setTransAmount(PackUtil.fillField(reqBean.getConsumeMoney(),
				12, true, "0")); // ���ý��׽��

		consume.setSysTraceAuditNum(TransSequence.getSysTraceAuditNum()); // �ܿ���ϵͳ���ٺ�

		consume.setDateExpired(null);// ����Ч��

		consume.setServiceEntryMode("005");// ��������뷽ʽ��

		consume.setServicePINCaptureCode(null);// �����pin��ȡ��

		consume.setServiceConditionMode("00");// �����������

		consume.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// �ܿ����ն˱�ʶ��41
		consume.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// �ܿ�����ʶ��42
		
		consume.setOrganId(reqBean.getSysInstId());//��ȯ������ʶ
		

		consume.setCurrencyTransCode("156");// ���׻��Ҵ���

		consume.setPinData(null);// ���˱�ʶ����

		consume.setSecurityRelatedControl(null);// ��ȫ������Ϣ

		consume.setReservedPrivate2("52"+DbHelp.getBatchNum()+"000");// ���κ�

		consume.setCouponsAdvertId(reqBean.getIssId());// ��������ʶ

		consume.setMessageAuthentCode("11111111");// mac

		DbHandle db = new DbHandle();
		db.insertObject("TBL_TMPFlOW", consume); // ������ʱ��ˮ��
		db.update("TBL_TMPFlOW", new String[] { "USER_ID" },
				new String[] {Controller.session.get("userID")+"" }, "SYS_TRACE_AUDIT_NUM = ?",
				new String[] { consume.getSysTraceAuditNum() });

		IsoCommHandler comm = new IsoCommHandler();
		ScoreConsumeBean bean = (ScoreConsumeBean) comm.sendIsoMsg(consume);// ��������ͱ���
		if (comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT) {
			db.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // ����ʧ�ܣ�����ʱ��ˮ��������ת�浽������
			db.update("TBL_REVERSAL", new String[] { "FLUSH_OCUNT",
					"FLUSH_RESULT" }, new String[] { "0", "-1" },
					"SYS_TRACE_AUDIT_NUM = ?",
					new String[] { consume.getSysTraceAuditNum() });
		}
		/**MACУ��ʧ�� **/
		if (comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED) {
			db.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // ����ʧ�ܣ�����ʱ��ˮ��������ת�浽������
			db.update("TBL_REVERSAL", new String[]{"FLUSH_OCUNT", "FLUSH_RESULT"}, new String[]{"0","-1"}, "SYS_TRACE_AUDIT_NUM = ?", new String[] {consume.getSysTraceAuditNum() });
		} 
		if (bean != null) {
			db.updateRespObject("TBL_TMPFlOW", bean, "SYS_TRACE_AUDIT_NUM = ?", // ������ʱ��ˮ��
					new String[] { bean.getSysTraceAuditNum() });

			db.transferTable("TBL_TMPFlOW", "TBL_FlOW"); // ���׳ɹ�����ʱ��ˮ��������ת�浽��ˮ��

		} else {
			response.setError(true);
			Bundle bundle = new Bundle();
			ResultRespBean result = new ResultRespBean();
			result.setMessage("�����������ӣ�");
			bundle.putSerializable("ResultRespBean", result);
			response.setBundle(bundle);
		}
		return bean;
	}
}
