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
 * �Ż݄�������
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
				response.setData("��ѡ��Ҫ�ɷ����Ż�ȯ��");
			} else if (bean.getCount().equals("")) {
				response.setError(true);
				response.setData("�������ɷ����Ż�ȯ������");
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
	 * �Ż݄�������
	 * 
	 * @return
	 */
	TransSequence trans = new TransSequence();
	private String respcode = "";// ���ڴ����Ӧ��
	private String reservedPrivate4;// Ӧ����Ϣ

	private void receiveCouponsBeanResult(CouponsBackBean bean) {

		try {
			String auditnum = trans.getSysTraceAuditNum();

			ReceiveCouponsBean receiveCoupons = new ReceiveCouponsBean();
			receiveCoupons.setMsgId("0220");// �Ż݄�����

			String cardNum = (String) Controller.session.get("CardNum");
			receiveCoupons.setTrack2(cardNum);// ��������
			receiveCoupons.setProcessCode("022000");// ���״����� 3
			receiveCoupons.setTransAmount(PackUtil.fillField(bean.getMoney(),
					12, true, "0"));//���׽��
			receiveCoupons.setSysTraceAuditNum(TransSequence
					.getSysTraceAuditNum());// �ܿ���ϵͳ���ٺ�11
			receiveCoupons.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// �ܿ����ն˱�ʶ��41
			receiveCoupons.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// �ܿ�����ʶ��42
			receiveCoupons.setCouponsAdvertId(bean.getIssuerId());// ��ȯ�����ʶ
			receiveCoupons.setOrganId(bean.getC_iss_id());//��ȯ������ʶ
			
			receiveCoupons.setReservedPrivate2("61"+DbHelp.getBatchNum()+"000"); // �������κ�
			
			String scount = bean.getCount();// ��ȡ����
			scount = PackUtil.fillField(scount, 3, true, "0");
			String actId = bean.getActIds();
			actId = PackUtil.fillField(actId, 30, false, " ");
			receiveCoupons.setReservedPrivate3(scount + actId);// �Ż݄�����
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
				db.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // ����ʧ�ܣ�����ʱ��ˮ��������ת�浽������
				db.update("TBL_REVERSAL", new String[]{"FLUSH_OCUNT", "FLUSH_RESULT"}, new String[]{"0","-1"}, "SYS_TRACE_AUDIT_NUM = ?", new String[] { receiveCoupons.getSysTraceAuditNum() });
			} 
			/**MACУ��ʧ�� **/
			if (comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED) {
				db.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // ����ʧ�ܣ�����ʱ��ˮ��������ת�浽������
				db.update("TBL_REVERSAL", new String[]{"FLUSH_OCUNT", "FLUSH_RESULT"}, new String[]{"0","-1"}, "SYS_TRACE_AUDIT_NUM = ?", new String[] { receiveCoupons.getSysTraceAuditNum() });
			} 
			if (rcb != null){
				db.updateRespObject("TBL_TMPFlOW", rcb, "SYS_TRACE_AUDIT_NUM = ?", // ������ʱ��ˮ��
						new String[] { rcb.getSysTraceAuditNum() });
				db.transferTable("TBL_TMPFlOW", "TBL_FlOW"); // ���׳ɹ�����ʱ��ˮ��������ת�浽��ˮ��
				
			}
			
			
			respcode = rcb.getRespCode();
			reservedPrivate4 = rcb.getReservedPrivate4();
			if (StringFormat.isNull(respcode).equalsIgnoreCase("00")) {// ��Ӧ�ɹ�

				log.info("�Ż݄�������Ϊ��" + reservedPrivate4);

				resultBean.setMessage("�Ż݄�����ɹ���");

				bundle.putSerializable("ResultRespBean", resultBean);

			} else {
				if (reservedPrivate4 != null) {
					resultBean.setMessage(reservedPrivate4);

					bundle.putSerializable("ResultRespBean", resultBean);
				}
			}

		} catch (Exception ex) {
			resultBean.setMessage("�����������ӣ�");

			bundle.putSerializable("ResultRespBean", resultBean);
			ex.printStackTrace();
		}

	}

}
