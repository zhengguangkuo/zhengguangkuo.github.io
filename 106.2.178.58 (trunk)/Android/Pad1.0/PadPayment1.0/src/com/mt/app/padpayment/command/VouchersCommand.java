 package com.mt.app.padpayment.command;

import java.util.Map;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;

import com.mt.android.db.DbHandle;
import com.mt.android.message.iso.util.StrUtil;
import com.mt.android.protocol.util.ProtocolRespCode;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.sys.util.StringFormat;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.common.DbHelp;
import com.mt.app.padpayment.common.GlobalParameters;
import com.mt.app.padpayment.common.IsoCommHandler;
import com.mt.app.padpayment.message.iso.trans.QueryBalanceBean;
import com.mt.app.padpayment.requestbean.AdminPassReqBean;
import com.mt.app.padpayment.responsebean.ResultRespBean;
import com.mt.app.padpayment.tools.TransSequence;

public class VouchersCommand extends AbstractCommand {

	private static Logger log = Logger.getLogger(VouchersCommand.class);
	private String respcode = "";// ���ڴ����Ӧ��
	private boolean bError = false;// ���ڱ�Ǳ��������Ƿ������Error
	private Bundle bundle = new Bundle();
	private String message = "";

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
		int[] flags = new int[1];
		flags[0] = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
		response.setFlags(flags);

		// ��������ѳ����ͻ��ֳ���
		if (Controller.session.get("type") != null
				&& (Controller.session.get("type").equals("consume") || Controller.session
						.get("type").equals("credit"))) {
			// �ӽ����ϻ�����ܵ�����
			AdminPassReqBean bean = (AdminPassReqBean) getRequest().getData();
			String pass = bean.getPassWord();
			DbHandle handle = new DbHandle();
			Map<String, String> map = handle.rawQueryOneRecord(
					"select PASSWORD from TBL_ADMIN where PASSWORD=?  and LIMITS = 2",
					new String[] { pass });
			if (map.size() != 0) {
				response.setTargetActivityID(ActivityID.map
						.get("ACTIVITY_ID_VOUCHERS"));
			} else { // ������� ���ص�ǰ����
				response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
				response.setError(true);
			}
		} else if (Controller.session.get("type") != null // �����֧���˻�
				&& Controller.session.get("type").equals("backGoods")) {
			// �ӽ����ϻ�����ܵ�����
			AdminPassReqBean bean = (AdminPassReqBean) getRequest().getData();
			String pass = bean.getPassWord();
			DbHandle handle = new DbHandle();
			Map<String, String> map = handle.rawQueryOneRecord(
					"select PASSWORD from TBL_ADMIN where PASSWORD=?  and LIMITS = 2",
					new String[] { pass });
			if (map.size() != 0) {
				response.setTargetActivityID(ActivityID.map
						.get("ACTIVITY_ID_SwipCardActivity"));
			} else { // ������� ���ص�ǰ����
				response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
				response.setError(true);
			}
		} else if (Controller.session.get("type") != null // �����֧����ѯ
				&& Controller.session.get("type").equals("consumeCheck")) {
			// �ӽ����ϻ�ÿ�������
			AdminPassReqBean bean = (AdminPassReqBean) getRequest().getData();
			String pass = bean.getPassWord();
			byte[] btpass = StrUtil.HexStringToByte(pass, "");
			QueryBalanceBean queryBalance = new QueryBalanceBean();
			queryBalance.setMsgId("0200");// ����ѯ
			queryBalance
					.setTrack2(Controller.session.get("CardNum").toString());// ��������
			queryBalance.setProcessCode("310000"); // ���״�����
			queryBalance.setSysTraceAuditNum(TransSequence
					.getSysTraceAuditNum());// �ܿ���ϵͳ���ٺ�
			queryBalance.setDateExpired(null); // ����Ч��
			queryBalance.setServiceEntryMode("071"); // ��������뷽ʽ��
			queryBalance.setServiceConditionMode("00"); // �����������
			queryBalance.setServicePINCaptureCode("06"); // �����PIN��ȡ��
			queryBalance.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// �ܿ����ն˱�ʶ��41
			queryBalance.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// �ܿ�����ʶ��42
			queryBalance.setCurrencyTransCode("156"); // ���׻��Ҵ���
			
			try{
//				queryBalance.setPinData(new String(btpass, "ISO-8859-1"));// ���˱�ʶ������
				queryBalance.setPinData(pass);// ���˱�ʶ������
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			queryBalance.setSecurityRelatedControl("2600000000000000"); // ��ȫ������Ϣ
			queryBalance.setReservedPrivate1(Controller.session.get("APP_ID")
					.toString()); // Ӧ�ñ�ʶ
			queryBalance.setReservedPrivate2("01"+DbHelp.getBatchNum()+"000"); // ���κ�
			queryBalance.setMessageAuthentCode("11111111"); // mac

			log.info("----------����begin----------");
			log.info("��ǰ�û���");
			log.info("�������ͣ�");
			log.info("����ʱ�䣺");
			log.info("�̻����ƣ�");
			log.info("���ͱ��ģ�");
			log.info("-----------����end---------");

			// ��������
			IsoCommHandler comm = new IsoCommHandler();
			QueryBalanceBean qbb = (QueryBalanceBean) comm
					.sendIsoMsg(queryBalance);

			log.info("----------��Ӧbegin----------");
			log.info("��ǰ�û���");
			log.info("�������ͣ�");
			log.info("����ʱ�䣺");
			log.info("�̻����ƣ�");
			log.info("���ͱ��ģ�");
			log.info("-----------��Ӧend---------");
			// SimpleDateFormat format = new SimpleDateFormat("yyMMdd_HHmmss");
			// String nowStr = format.format(new Date());

			if (qbb != null) {

				log.info("##################" + qbb.getRespCode());

				respcode = qbb.getRespCode();
				message = qbb.getReservedPrivate4();
				if (StringFormat.isNull(respcode).equalsIgnoreCase("00")) {// ��Ӧ�ɹ�
					String balanceAmount = qbb.getBalanceAmount();

					System.out.println("Ӧ�ò�ѯ���Ϊ��" + balanceAmount);
					log.info("Ӧ�ò�ѯ���Ϊ��" + balanceAmount);

					bundle.putString("Amount", balanceAmount);
					response.setBundle(bundle);
					response.setTargetActivityID(ActivityID.map
							.get("ACTIVITY_ID_SearchBalanceActivity"));

				} else {
					ResultRespBean b = new ResultRespBean();
					
					b.setMessage(qbb.getReservedPrivate4());
					bundle.putSerializable("ResultRespBean", b);
					response.setBundle(bundle);
					response.setTargetActivityID(ActivityID.map
							.get("ACTIVITY_ID_CANCELRESULT"));
				}
				Controller.session.remove("APP_ID");
			} else {
				ResultRespBean b = new ResultRespBean();
				if (!message.equals("")) {
					b.setMessage(message);
				} else {
					b.setMessage("�����������ӣ�");
				}
				
				bundle.putSerializable("ResultRespBean", b);
				response.setBundle(bundle);
				response.setTargetActivityID(ActivityID.map
						.get("ACTIVITY_ID_CANCELRESULT"));
			}
		}
		setResponse(response);
	}
}
