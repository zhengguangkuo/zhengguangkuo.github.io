package com.mt.app.padpayment.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;

import com.mt.android.db.DbHandle;
import com.mt.android.frame.smart.config.DrawGridAdapter;
import com.mt.android.frame.smart.config.DrawListViewAdapter;
import com.mt.android.protocol.util.ProtocolRespCode;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.sys.util.StringFormat;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.adapter.CouponGridAdapter;
import com.mt.app.padpayment.adapter.ListViewOneAdapter;
import com.mt.app.padpayment.common.Constants;
import com.mt.app.padpayment.common.DbHelp;
import com.mt.app.padpayment.common.DbInfoImpl;
import com.mt.app.padpayment.common.DispatchRequest;
import com.mt.app.padpayment.common.IsoCommHandler;
import com.mt.app.padpayment.message.iso.trans.ConsumeRepealBean;
import com.mt.app.padpayment.message.iso.trans.CouponsConvertRepealBean;
import com.mt.app.padpayment.message.iso.trans.PointsQueryBean;
import com.mt.app.padpayment.message.iso.trans.ScoreCancelBean;
import com.mt.app.padpayment.requestbean.AppReqBean;
import com.mt.app.padpayment.requestbean.CouponsApplyReqBean;
import com.mt.app.padpayment.requestbean.CouponsReqBean;
import com.mt.app.padpayment.requestbean.CreditReqBean;
import com.mt.app.padpayment.requestbean.ReadCardReqBean;
import com.mt.app.padpayment.responsebean.AppListRespBean;
import com.mt.app.padpayment.responsebean.CouponsApplyRespBean;
import com.mt.app.padpayment.responsebean.CouponsListRespBean;
import com.mt.app.padpayment.responsebean.CreditQueryResBean;
import com.mt.app.padpayment.responsebean.ResultRespBean;
import com.mt.app.padpayment.tools.PackUtil;
import com.mt.app.padpayment.tools.TransSequence;

/**
 * �ύ���ź��������͵�command
 * 
 * @author dw
 * 
 */
public class SubmitCardCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(SubmitCardCommand.class);
	private boolean bError = false;// ���ڱ�Ǳ��������Ƿ������Error
	private String sTotalSize = "-1";
	private String respcode = "";// ���ڴ����Ӧ��
	private Bundle bundle = new Bundle();
	private Response response = new Response();
	private String resultMsg = ""; // ��Ӧ���Ӧ����Ϣ
	private String cardError = ""; // ���Ŵ���
	private boolean isUser = true;// �Ƿ��ǺϷ��û�

	@Override
	protected void prepare() {
		log.debug("prepare");
	}

	@Override
	protected final void onBeforeExecute() {
		log.debug("onBeforeExecute");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mt.android.sys.mvc.command.AbstractCommand#go()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mt.android.sys.mvc.command.AbstractCommand#go()
	 */
	@Override
	protected void go() {

		// ����ҳ���� ˢ������go()������Ҫʵ�ֵ��߼�
		/* ��ȡ����,�������� */
		try {
			// ����֮ǰ��������
			CouponGridAdapter.list = new ArrayList();
			CouponGridAdapter.apId = null;
			CouponGridAdapter.listChecked = null;
			Controller.session.remove("checked");
			Controller.session.remove("checkedList");
			DrawGridAdapter.list.clear();
			ListViewOneAdapter.list.clear();
			DrawListViewAdapter.list.clear();

			response.setError(false);

			if (Controller.session.get("succForward") != null
					&& Controller.session.get("succForward").equals(
							ActivityID.map
									.get("ACTIVITY_ID_NoteConsumeActivity"))) {
				// doCouponsRequest(readcard);// �Ż�ȯ�б�����

				ReadCardReqBean readcard = (ReadCardReqBean) getRequest()
						.getData();

				AppReqBean reqBean = new AppReqBean();
				if (readcard != null) {

					Controller.session.put("CardNum", readcard.getCardNum()
							.toString());
					reqBean.setBaseCardNo(readcard.getCardNum());
					reqBean.setMerchantCode(DbHelp.getCardAcceptIdentcode());// �̻����
					// reqBean.setMerchantCode("123123123123214");
				}
				Request request = new Request();
				request.setData(reqBean);
				List<ResponseBean> pList = DispatchRequest.doHttpRequest(
						Constants.USR_PAY_APP, request, AppListRespBean.class);

				ArrayList<ResponseBean> appList = null;

				if (pList != null) {
					if (pList.size() > 0) {
						if (((AppListRespBean) pList.get(0)).getRespcode()
								.equals("-1")) {
							isUser = false;
						}
					}
					appList = new ArrayList<ResponseBean>(pList);// Ӧ���б�LIST
				}

				if (isUser) {
					List<ResponseBean> cList = DispatchRequest.doHttpRequest(
							Constants.USR_COUPONS, request,
							CouponsListRespBean.class);

					ArrayList<ResponseBean> couponsList = null;

					if (cList != null) {
						couponsList = new ArrayList<ResponseBean>(cList);// �Ż�ȯ�б�LIST
					}
					if (appList == null && couponsList == null) {
						isUser = false;
						ResultRespBean result = new ResultRespBean();
						result.setMessage("�����������ӣ�");
						bundle.putSerializable("ResultRespBean", result);

					} else {
						// ���������鴫�뵽��һ��Activity
						bundle.putSerializable("CARDBINDAPPLIST", appList);// �������󶨵�֧��Ӧ���б�
						bundle.putSerializable("COUPONSLIST", couponsList);// �������󶨵��Ż�ȯ�б�

					}

				} else {

					ResultRespBean result = new ResultRespBean();
					result.setMessage("�˿���δ�󶨣�");
					bundle.putSerializable("ResultRespBean", result);
				}
			}

			// ���ѳ����ͻ��ֳ��� �Ż�ȯ�һ����� ˢ������go()������Ҫʵ�ֵ��߼�
			if (Controller.session.get("succForward") != null
					&& Controller.session.get("succForward").equals(
							ActivityID.map.get("ACTIVITY_ID_CANCELRESULT"))) {
				int[] flags = new int[1];
				flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
				response.setFlags(flags);

				ReadCardReqBean readcard = (ReadCardReqBean) getRequest()
						.getData();
				if (readcard.getCardNum() != null
						&& Controller.session.get("card") != null
						&& Controller.session.get("card").equals(
								readcard.getCardNum())) {
					Controller.session.put("CardNum", readcard.getCardNum()
							.toString());
					String vouch = "";
					String cardN = "";
					if (Controller.session.get("Vouchers") != null) {
						vouch = Controller.session.get("Vouchers").toString();
					}
					if (Controller.session.get("CardNum") != null) {
						cardN = Controller.session.get("CardNum").toString();
					}
					// ���ѳ����������󵽺�̨
					if (Controller.session.get("type") != null
							&& Controller.session.get("type").equals("consume")) {
						sendRequest(cardN, vouch);
					} else if (Controller.session.get("type") != null
							&& Controller.session.get("type").equals("credit")) {// ���ֳ�����������
						sendRequestScore(cardN, vouch);
					} else if (Controller.session.get("type") != null
							&& Controller.session.get("type").equals("coupon")) {// �һ�������������
						sendRequestCoupon(cardN, vouch);
					}
					Controller.session.remove("Vouchers");
					Controller.session.remove("type");
					if (!(resultMsg.equals(""))) {

						ResultRespBean bean = new ResultRespBean();
						bean.setMessage(resultMsg);

						bundle.putSerializable("ResultRespBean", bean);
						response.setBundle(bundle);
					}

					Controller.session.remove("card");
				} else {
					cardError = "error";
				}
			}
			// �������� ���ֲ�ѯˢ������go()������Ҫʵ�ֵ��߼�
			if (Controller.session.get("succForward") != null
					&& (Controller.session.get("succForward").equals(
							ActivityID.map
									.get("ACTIVITY_ID_CreditsConsumeActivity")) || Controller.session
							.get("succForward")
							.equals(ActivityID.map
									.get("ACTIVITY_ID_CreditsQueryActivity")))) {

				ArrayList<CreditQueryResBean> creditList = new ArrayList<CreditQueryResBean>();// �����б�LIST
				
				ReadCardReqBean readcard = (ReadCardReqBean) getRequest()
						.getData();

				if (readcard != null) {
					Controller.session.put("CardNum", readcard.getCardNum()
							.toString());
					Request request = new Request();
					//������������ ���ź��̻���
					CreditReqBean creditReqBean = new CreditReqBean();
					creditReqBean.setBaseCardNo(readcard.getCardNum());//��������
					creditReqBean.setMerchantCode(DbHelp.getCardAcceptIdentcode());//�̻���
					request.setData(creditReqBean);
					//��������
					List<ResponseBean> respList = DispatchRequest.doHttpRequest(
							Constants.USR_CREDITS, request, CreditQueryResBean.class);
					
					
					if (respList != null) {//�յ�Ӧ��
						
						for (int i = 0; i <respList.size(); i++) {
							creditList.add((CreditQueryResBean)respList.get(i));
						}						
						// ���������鴫�뵽��һ��Activity
						bundle.putSerializable("creditList", creditList);// �����б�
					} else {
						response.setError(true);
						ResultRespBean bean = new ResultRespBean();
						bean.setMessage("�����������ӣ�");
						bundle.putSerializable("ResultRespBean", bean);
						response.setBundle(bundle);
					}
					
				} else {
					response.setError(true);
					ResultRespBean bean = new ResultRespBean();
					bean.setMessage("���Ų���Ϊ�գ�");
					bundle.putSerializable("ResultRespBean", bean);
					response.setBundle(bundle);
				}
				

			}

			/*
			 * // �������� ���ֲ�ѯˢ������go()������Ҫʵ�ֵ��߼� if
			 * (Controller.session.get("succForward") != null &&
			 * (Controller.session.get("succForward").equals( ActivityID.map
			 * .get("ACTIVITY_ID_CreditsConsumeActivity")) || Controller.session
			 * .get("succForward") .equals(ActivityID.map
			 * .get("ACTIVITY_ID_CreditsQueryActivity")))) {
			 * 
			 * ArrayList<CreditQueryResBean> creditList = new
			 * ArrayList<CreditQueryResBean>();// �����б�LIST int currentLoad =
			 * 0;// ���ڴ�ŵ�ǰ���ص� ReadCardReqBean readcard = (ReadCardReqBean)
			 * getRequest() .getData();
			 * 
			 * if (readcard != null) { Controller.session.put("CardNum",
			 * readcard.getCardNum() .toString()); } // ѭ����ȡ�����б� int begin = 1,
			 * end = 8; int iTotalSize = 0; boolean bRun = true; // ��ѯָ�����ŵĻ����б�
			 * 
			 * while (bRun) { CreditQueryResBean[] tmp = queryCreditList(begin +
			 * "", end + "", readcard);
			 * 
			 * for (int z = 0; z < tmp.length; z++) { creditList.add(tmp[z]); }
			 * 
			 * currentLoad = currentLoad + tmp.length; begin = begin +
			 * tmp.length; iTotalSize = Integer.valueOf(sTotalSize);
			 * 
			 * if (currentLoad < iTotalSize) { if ((currentLoad + 8) <
			 * iTotalSize) { end = begin + 8; } else { end = begin + (iTotalSize
			 * - currentLoad); } } else if (bError) {// ���ĳ���,��ѯ���������ձ� bRun =
			 * false; bError = true; List<Map<String, String>> list = new
			 * DbHandle().select( "TBL_RESPONSE_CODE", new String[] { "MESSAGE"
			 * }, "RESP_CODE", new String[] { respcode }, null, null, null); if
			 * (list.size() != 0) { resultMsg = list.get(0).get("MESSAGE"); } }
			 * else {// �������� bRun = true; break; } }
			 * 
			 * // ���������鴫�뵽��һ��Activity bundle.putSerializable("creditList",
			 * creditList);// �����б�
			 * 
			 * }
			 */
			// Ӧ�� ��ѯ ˢ������go()������Ҫʵ�ֵ��߼�
			if (Controller.session.get("succForward") != null
					&& Controller.session.get("succForward").equals(
							ActivityID.map.get("ACTIVITY_ID_SearchActivity"))) {

				ReadCardReqBean readcard = (ReadCardReqBean) getRequest()
						.getData();

				AppReqBean reqBean = new AppReqBean();
				if (readcard != null) {

					Controller.session.put("CardNum", readcard.getCardNum()
							.toString());
					reqBean.setBaseCardNo(readcard.getCardNum());
					reqBean.setMerchantCode(DbHelp.getCardAcceptIdentcode());// �̻����
				}
				Request request = new Request();
				request.setData(reqBean);
				List<ResponseBean> cList = DispatchRequest.doHttpRequest(
						Constants.USR_PAY_APP, request, AppListRespBean.class);

				if (cList != null) {
					if (cList.size() > 0) {
						if (((AppListRespBean) cList.get(0)).getRespcode()
								.equals("-1")) {
							isUser = false;
							ResultRespBean result = new ResultRespBean();
							result.setMessage("�˿���δ�󶨣�");
							bundle.putSerializable("ResultRespBean", result);
						}
					}
					ArrayList<ResponseBean> appList = new ArrayList<ResponseBean>(
							cList);// Ӧ���б�LIST
					bundle.putSerializable("CARDBINDAPPLIST", appList);// �������󶨵�Ӧ���б�

				}
				int[] flags = new int[1];
				flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
				response.setFlags(flags);

				// ���������鴫�뵽��һ��Activity

			}

			// �Ż�ȯ�б��ѯ
			if (Controller.session.get("succForward") != null
					&& Controller.session.get("succForward").equals(
							ActivityID.map
									.get("ACTIVITY_ID_CouponsInfoActivity"))) {

				ReadCardReqBean readcard = (ReadCardReqBean) getRequest()
						.getData();
				if (readcard != null) {
					Controller.session.put("CardNum", readcard.getCardNum()
							.toString());
				}
				int[] flags = new int[1];
				flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
				response.setFlags(flags);

				// ��ѯ�Ż�ȯ�б����뵽��һ����
				doCouponsRequest2(readcard);
			}

			response.setBundle(bundle);
			setResponse(response);
		} catch (Exception ex) {
			response.setError(true);
			if (resultMsg.equals("")) {
				ResultRespBean bean = new ResultRespBean();
				bean.setMessage(resultMsg);
				bundle.putSerializable("ResultRespBean", bean);
				response.setBundle(bundle);
			}

			setResponse(response);
			ex.printStackTrace();
		}

	}

	@Override
	protected void onAfterExecute() {
		// TODO Auto-generated method stub
		super.onAfterExecute();
		Response response = getResponse();

		if (response != null && !response.isError()) {

			ReadCardReqBean readcard = (ReadCardReqBean) getRequest().getData();
			if (readcard != null) {
				Controller.session.put("CardNum", readcard.getCardNum()
						.toString());
			}
			if (cardError.equals("error")) {
				ResultRespBean result = new ResultRespBean();

				result.setMessage("������Ŀ��ź͸ý��׿��Ų�һ�£�");

				bundle.putSerializable("ResultRespBean", result);
				response.setBundle(bundle);
				response.setTargetActivityID(ActivityID.map
						.get("ACTIVITY_ID_CANCELRESULT"));
			} else {
				if (!isUser) { // ������Ч�û�

					response.setTargetActivityID(ActivityID.map
							.get("ACTIVITY_ID_CANCELRESULT"));

				} else {
					response.setTargetActivityID((Integer) Controller.session
							.get("succForward"));
				}
			}
		} else {

			Bundle bundle = new Bundle();
			ResultRespBean result = new ResultRespBean();

			result.setMessage(resultMsg);
			if (result.getMessage() == null || result.getMessage().equals("")) {
				result.setMessage("����ʧ�ܣ�");
			}
			bundle.putSerializable("ResultRespBean", result);
			response.setBundle(bundle);
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_CANCELRESULT"));

		}

		setResponse(response);
	}

	// ���ѳ�����������
	private void sendRequest(String num, String vou) {
		// ����ˮ����ȡ�� �����ѳ�����
		DbHandle handle = new DbHandle();
		/*
		 * String[] columns = new String[]{"CARD_NO", "TRANS_AMOUNT",
		 * "DATE_EXPIRED", "SERVICE_ENTRY_MODE", "SERVICE_CONDITION_MODE",
		 * "SERVICE_PIN_CAPTURE_CODE", "RET_REFER_NUM", "AUTHOR_IDENT_RESP",
		 * "CURRENCY_TRANS_CODE", "PIN_DATA","SECURITY_RELATED_CONTROL",
		 * "RESERVED_PRIVATE1", "SYS_TRACE_AUDIT_NUM", "LOCAL_TRANS_DATE_1",
		 * "RESERVED_PRIVATE2"}; //�������ֶ���Ҫ�ı�ģ�Ҫ��Ϊ�ɵ���Ϣ�ֶ�
		 */
		List<Map<String, String>> list = handle.select("TBL_FlOW",
				DbInfoImpl.FieldNames[1],
				"TRACK2 =? and SYS_TRACE_AUDIT_NUM=?",
				new String[] { num, vou }, null, null, null);
		if (list.size() != 0) {
			ConsumeRepealBean bean = new ConsumeRepealBean();
			bean.setMsgId("0200");
			bean.setTrack2(num);// ���ÿ���
			bean.setProcessCode("200000"); // ���ý��״�����
			bean.setCardNo(list.get(0).get("CARD_NO_1"));//����
			bean.setTransAmount(list.get(0).get("TRANS_AMOUNT")); // ���׽��
			bean.setDiscountAmount(list.get(0).get("DISCOUNT_AMOUNT")); // ���ӽ��
			bean.setSysTraceAuditNum(TransSequence.getSysTraceAuditNum()); // �ܿ���ϵͳ���ٺţ����ν��׵���ˮ�ţ�
			bean.setDateExpired(list.get(0).get("DATE_EXPIRED_1").equals("") ? null
					: list.get(0).get("DATE_EXPIRED_1")); // ����Ч��
			bean.setServiceEntryMode(list.get(0).get("SERVICE_ENTRY_MODE")); // ��������뷽ʽ��
			bean.setServiceConditionMode("00"); // �����������
			bean.setServicePINCaptureCode(list.get(0)
					.get("SERVICE_PIN_CAPTURE_CODE").equals("") ? null : list
					.get(0).get("SERVICE_PIN_CAPTURE_CODE")); // �����PIN��ȡ��
			bean.setRetReferNum(list.get(0).get("RET_REFER_NUM_1")); // �����ο���
			bean.setAuthorIdentResp(list.get(0).get("AUTHOR_IDENT_RESP")
					.equals("") ? null : list.get(0).get("AUTHOR_IDENT_RESP")); // ��Ȩ��
			bean.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent()); // �����ܿ����ն˱�ʶ��
			String caic = DbHelp.getCardAcceptIdentcode();
			bean.setCardAcceptIdentcode(PackUtil
					.fillField(caic, 15, false, " ")); // �����ܿ�����ʶ��
			bean.setCurrencyTransCode(list.get(0).get("CURRENCY_TRANS_CODE")); // ���׻��Ҵ��루����ҵĻ��Ҵ��룩
//			bean.setPinData(list.get(0).get("PIN_DATA").equals("") ? null
//					: list.get(0).get("PIN_DATA"));// ���˱�ʶ������
//			bean.setSecurityRelatedControl(list.get(0).get(
//					"SECURITY_RELATED_CONTROL")); // ��ȫ������Ϣ
			//bean.setSecurityRelatedControl("2600000000000000");
			bean.setPinData(null);
			bean.setSecurityRelatedControl(null);
			bean.setOrganId(list.get(0).get("ORGAN_ID"));// ��ȯ������ʶ
			bean.setReservedPrivate1(list.get(0).get("RESERVED_PRIVATE1")); // Ӧ�ñ�ʶ
			bean.setSwapCode(list.get(0).get("SWAP_CODE_1").equals("") ? null
					: list.get(0).get("SWAP_CODE_1"));// �һ���
			bean.setReservedPrivate2("23" + DbHelp.getBatchNum() + "000"); // �������κ�
			StringBuilder sb = new StringBuilder();
			sb.append(list.get(0).get("RESERVED_PRIVATE2").substring(2, 8)); // ԭ���κ�
			sb.append(list.get(0).get("SYS_TRACE_AUDIT_NUM")); // ԭ��ˮ��
			bean.setOriginalMessage(sb.toString());// ԭʼ��Ϣ��
			bean.setMessageAuthentCode("11111111"); // mac

			// �����׷�����ʱ��ˮ����
			handle.insertObject("TBL_TMPFlOW", bean);

			handle.update("TBL_TMPFlOW", new String[] { "USER_ID" },
					new String[] { Controller.session.get("userID") + "" },
					"SYS_TRACE_AUDIT_NUM = ?",
					new String[] { bean.getSysTraceAuditNum() });

			// ��������ͱ���
			IsoCommHandler comm = new IsoCommHandler();
			ConsumeRepealBean respbean = (ConsumeRepealBean) comm
					.sendIsoMsg(bean);
			if (comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT) {
				handle.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // ����ʧ�ܣ�����ʱ��ˮ��������ת�浽������
				handle.update("TBL_REVERSAL", new String[] { "FLUSH_OCUNT",
						"FLUSH_RESULT" }, new String[] { "0", "-1" },
						"SYS_TRACE_AUDIT_NUM = ?",
						new String[] { bean.getSysTraceAuditNum() });
			}
			/**MACУ��ʧ�� **/
			if (comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED) {
				handle.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // ����ʧ�ܣ�����ʱ��ˮ��������ת�浽������
				handle.update("TBL_REVERSAL", new String[]{"FLUSH_OCUNT", "FLUSH_RESULT"}, new String[]{"0","-1"}, "SYS_TRACE_AUDIT_NUM = ?", new String[] { bean.getSysTraceAuditNum() });
			} 
			if (respbean != null) {
				resultMsg = respbean.getReservedPrivate4();
				handle.updateRespObject("TBL_TMPFlOW", respbean,
						"SYS_TRACE_AUDIT_NUM=?",
						new String[] { respbean.getSysTraceAuditNum() });
				String respcode = respbean.getRespCode();

				handle.updateRespObject("TBL_TMPFlOW", respbean,
						"SYS_TRACE_AUDIT_NUM=?",
						new String[] { respbean.getSysTraceAuditNum() });
				handle.transferTable("TBL_TMPFlOW", "TBL_FlOW");

				if (StringFormat.isNull(respcode).equalsIgnoreCase("00")) { // ��Ӧ�ɹ�
					this.resultMsg = "�����ɹ�";
					// handle.delete("TBL_FlOW", "SYS_TRACE_AUDIT_NUM", new
					// String[]{respbean.getSysTraceAuditNum()});
				} else { // ��Ӧʧ��
					this.bError = true;
					if (resultMsg.equals("")) {
						list = handle.select("TBL_RESPONSE_CODE",
								new String[] { "MESSAGE" }, "RESP_CODE=?",
								new String[] { respcode }, null, null, null);
						if (list != null && list.size() > 0) {
							this.resultMsg = list.get(0).get("MESSAGE");
						}
					}

				}

			} else {
				response.setError(true);
				Bundle bundle = new Bundle();
				ResultRespBean result = new ResultRespBean();
				resultMsg = "�����������ӣ�";
				result.setMessage("�����������ӣ�");
				bundle.putSerializable("ResultRespBean", result);
				response.setBundle(bundle);
			}

		}
		// bean.setTransAmount("000080000000");
		// bean.setSysTraceAuditNum("004000");
		// bean.setDateExpired("1307");
		// bean.setServiceEntryMode("121");
		// bean.setServiceConditionMode("00");
		// bean.setServicePINCaptureCode("12");
		// bean.setRetReferNum("10020202    ");
		// bean.setAuthorIdentResp("121212");
		// bean.setCurrencyTransCode("156");
		// bean.setPinData("11111111");
		// bean.setSecurityRelatedControl("0000000000101010");
		// bean.setReservedPrivate1("0041424");
		// bean.setOriginalMessage("0161101010010101010");
		// bean.setMessageAuthentCode("11111111");
	}

	// �Ż�ȯ�һ�����
	private void sendRequestCoupon(String num, String vou) {
		// ����ˮ����ȡ�� ���һ�������
		DbHandle handle = new DbHandle();

		List<Map<String, String>> list = handle.select("TBL_FlOW",
				DbInfoImpl.FieldNames[1],
				"TRACK2 =? and SYS_TRACE_AUDIT_NUM=?",
				new String[] { num, vou }, null, null, null);
		if (list.size() != 0) {
			CouponsConvertRepealBean bean = new CouponsConvertRepealBean();
			bean.setMsgId("0200");
			bean.setTrack2(num);// ���ÿ���
			bean.setProcessCode("021000"); // ���ý��״�����
			bean.setTransAmount(list.get(0).get("TRANS_AMOUNT")); // ���׽��
			bean.setDiscountAmount(list.get(0).get("DISCOUNT_AMOUNT")); // ���ӽ��
			bean.setSysTraceAuditNum(TransSequence.getSysTraceAuditNum()); // �ܿ���ϵͳ���ٺţ����ν��׵���ˮ�ţ�
			bean.setRetReferNum(list.get(0).get("RET_REFER_NUM_1")); // �����ο���
			bean.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent()); // �����ܿ����ն˱�ʶ��
			String caic = DbHelp.getCardAcceptIdentcode();
			bean.setCardAcceptIdentcode(PackUtil
					.fillField(caic, 15, false, " ")); // �����ܿ�����ʶ��
			bean.setOrganId(list.get(0).get("ORGAN_ID"));// ��ȯ������ʶ
			bean.setSwapCode(list.get(0).get("SWAP_CODE_1").equals("") ? null
					: list.get(0).get("SWAP_CODE_1"));// �һ���
			bean.setCouponsAdvertId(list.get(0).get("COUPONS_ADVERT_ID"));// ��ȯ����ʶ
			bean.setReservedPrivate2("63" + DbHelp.getBatchNum() + "000"); // �������κ�
			StringBuilder sb = new StringBuilder();
			sb.append(list.get(0).get("RESERVED_PRIVATE2").substring(2, 8)); // ԭ���κ�
			sb.append(list.get(0).get("SYS_TRACE_AUDIT_NUM")); // ԭ��ˮ��
			bean.setOriginalMessage(sb.toString());// ԭʼ��Ϣ��
			bean.setMessageAuthentCode("11111111"); // mac

			// �����׷�����ʱ��ˮ����
			handle.insertObject("TBL_TMPFlOW", bean);

			handle.update("TBL_TMPFlOW", new String[] { "USER_ID" },
					new String[] { Controller.session.get("userID") + "" },
					"SYS_TRACE_AUDIT_NUM = ?",
					new String[] { bean.getSysTraceAuditNum() });

			// ��������ͱ���
			IsoCommHandler comm = new IsoCommHandler();
			CouponsConvertRepealBean respbean = (CouponsConvertRepealBean) comm
					.sendIsoMsg(bean);
			if (comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT) {
				handle.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // ����ʧ�ܣ�����ʱ��ˮ��������ת�浽������
				handle.update("TBL_REVERSAL", new String[] { "FLUSH_OCUNT",
						"FLUSH_RESULT" }, new String[] { "0", "-1" },
						"SYS_TRACE_AUDIT_NUM = ?",
						new String[] { bean.getSysTraceAuditNum() });
			}
			if (respbean != null) {
				resultMsg = respbean.getReservedPrivate4();
				handle.updateRespObject("TBL_TMPFlOW", respbean,
						"SYS_TRACE_AUDIT_NUM=?",
						new String[] { respbean.getSysTraceAuditNum() });
				String respcode = respbean.getRespCode();

				handle.updateRespObject("TBL_TMPFlOW", respbean,
						"SYS_TRACE_AUDIT_NUM=?",
						new String[] { respbean.getSysTraceAuditNum() });
				handle.transferTable("TBL_TMPFlOW", "TBL_FlOW");

				if (StringFormat.isNull(respcode).equalsIgnoreCase("00")) { // ��Ӧ�ɹ�
					this.resultMsg = "�����ɹ�";
					// handle.delete("TBL_FlOW", "SYS_TRACE_AUDIT_NUM", new
					// String[]{respbean.getSysTraceAuditNum()});
				} else { // ��Ӧʧ��
					this.bError = true;
					if (resultMsg.equals("")) {
						list = handle.select("TBL_RESPONSE_CODE",
								new String[] { "MESSAGE" }, "RESP_CODE=?",
								new String[] { respcode }, null, null, null);
						if (list != null && list.size() > 0) {
							this.resultMsg = list.get(0).get("MESSAGE");
						}
					}

				}

			} else {
				response.setError(true);
				Bundle bundle = new Bundle();
				ResultRespBean result = new ResultRespBean();
				resultMsg = "�����������ӣ�";
				result.setMessage("�����������ӣ�");
				bundle.putSerializable("ResultRespBean", result);
				response.setBundle(bundle);
			}

		}
	}

	// ���ֳ������׷�������
	private void sendRequestScore(String cardNum, String vouchNum) {
		// ����Ҫ���ֶδ���ˮ�����ҳ� �����ֳ�����
		DbHandle handle = new DbHandle();
		Map<String, String> map = handle.selectOneRecord("TBL_FlOW",
				DbInfoImpl.FieldNames[1],
				"TRACK2 =? and SYS_TRACE_AUDIT_NUM=?", new String[] { cardNum,
						vouchNum }, null, null, null);
		if (map.size() != 0) {
			ScoreCancelBean bean = new ScoreCancelBean();
			bean.setTrack2(cardNum);
			bean.setMsgId("0200"); // ���ý�������
			bean.setProcessCode("300000"); // ���ý��״�����
			bean.setTransAmount(map.get("TRANS_AMOUNT_1")); // ���׽��
			bean.setSysTraceAuditNum(TransSequence.getSysTraceAuditNum()); // �ܿ���ϵͳ���ٺţ����ν��׵���ˮ�ţ�
			bean.setDateExpired(map.get("DATE_EXPIRED").equals("") ? null : map
					.get("DATE_EXPIRED")); // ����Ч��
			bean.setServiceEntryMode(map.get("SERVICE_ENTRY_MODE")); // ��������뷽ʽ��
			bean.setServiceConditionMode("00"); // �����������
			bean.setServicePINCaptureCode(map.get("SERVICE_PIN_CAPTURE_CODE")
					.equals("") ? null : map.get("SERVICE_PIN_CAPTURE_CODE")); // �����PIN��ȡ��
			bean.setRetReferNum(map.get("RET_REFER_NUM_1")); // �����ο���
			bean.setCardAcceptTermIdent(map.get("CARD_ACCEPT_TERM_IDENT")); // �����ܿ����ն˱�ʶ��
			bean.setCardAcceptIdentcode(map.get("CARD_ACCEPT_IDENTCODE")); // �����ܿ�����ʶ��
			bean.setCurrencyTransCode(map.get("CURRENCY_TRANS_CODE")); // ���׻��Ҵ��루����ҵĻ��Ҵ��룩
			bean.setOrganId(map.get("ORGAN_ID"));// ��ȯ������ʶ
			bean.setPinData(map.get("PIN_DATA").equals("") ? null : map
					.get("PIN_DATA"));// ���˱�ʶ������
			bean.setSecurityRelatedControl(map.get("SECURITY_RELATED_CONTROL")
					.equals("") ? null : map.get("SECURITY_RELATED_CONTROL")); // ��ȫ������Ϣ
			bean.setCouponsAdvertId(map.get("COUPONS_ADVERT_ID"));// ��������ʶ

			bean.setReservedPrivate2("53" + DbHelp.getBatchNum() + "000"); // �������κ�
			StringBuilder sb = new StringBuilder();
			sb.append(map.get("RESERVED_PRIVATE2").substring(2, 8)); // ԭ���κ�
			sb.append(map.get("SYS_TRACE_AUDIT_NUM")); // ԭ��ˮ��
			bean.setOriginalMessage(sb.toString());// ԭʼ��Ϣ��
			bean.setMessageAuthentCode("11111111"); // mac

			// �����׷�����ʱ��ˮ����
			handle.insertObject("TBL_TMPFlOW", bean);
			handle.update("TBL_TMPFlOW", new String[] { "USER_ID" },
					new String[] { Controller.session.get("userID") + "" },
					"SYS_TRACE_AUDIT_NUM = ?",
					new String[] { bean.getSysTraceAuditNum() });
			// ��������ͱ���
			IsoCommHandler comm = new IsoCommHandler();
			ScoreCancelBean respbean = (ScoreCancelBean) comm.sendIsoMsg(bean);

			if (comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT) {
				bError = true;
				response.setError(true);
				Bundle bundle = new Bundle();
				ResultRespBean result = new ResultRespBean();
				result.setMessage("δ�յ�Ӧ��");
				bundle.putSerializable("ResultRespBean", result);
				response.setBundle(bundle);
				handle.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // ����ʧ�ܣ�����ʱ��ˮ��������ת�浽������
				handle.update("TBL_REVERSAL", new String[] { "FLUSH_OCUNT",
						"FLUSH_RESULT" }, new String[] { "0", "-1" },
						"SYS_TRACE_AUDIT_NUM = ?",
						new String[] { bean.getSysTraceAuditNum() });
			}
			/**MACУ��ʧ�� **/
			if (comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED) {
				handle.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // ����ʧ�ܣ�����ʱ��ˮ��������ת�浽������
				handle.update("TBL_REVERSAL", new String[]{"FLUSH_OCUNT", "FLUSH_RESULT"}, new String[]{"0","-1"}, "SYS_TRACE_AUDIT_NUM = ?", new String[] { bean.getSysTraceAuditNum() });
			} 
			if (respbean != null) {
				resultMsg = respbean.getReservedPrivate4();
				handle.updateRespObject("TBL_TMPFlOW", bean,
						"SYS_TRACE_AUDIT_NUM = ?", // ������ʱ��ˮ��
						new String[] { bean.getSysTraceAuditNum() });

				handle.transferTable("TBL_TMPFlOW", "TBL_FlOW"); // ���׳ɹ�����ʱ��ˮ��������ת�浽��ˮ��
				if (respbean.getRespCode().equals("00")) {
					resultMsg = "�����ɹ�";
					// handle.delete("TBL_FlOW", "SYS_TRACE_AUDIT_NUM", new
					// String[]{respbean.getSysTraceAuditNum()});
				} else {
					bError = true;
					if (resultMsg.equals("")) {
						Map<String, String> codeMap = handle.selectOneRecord(
								"TBL_RESPONSE_CODE",
								new String[] { "MESSAGE" }, "RESP_CODE",
								new String[] { respbean.getRespCode() }, null,
								null, null);
						if (codeMap != null && codeMap.size() > 0) {
							resultMsg = codeMap.get("MESSAGE");
						}
					}
				}

			} else {
				bError = true;
				response.setError(true);
				Bundle bundle = new Bundle();
				ResultRespBean result = new ResultRespBean();
				resultMsg = "�����������ӣ�";
				result.setMessage("�����������ӣ�");
				bundle.putSerializable("ResultRespBean", result);
				response.setBundle(bundle);
			}

		}
	}

	/**
	 * ��ѯӦ���б�
	 * 
	 * @param start
	 *            ��ʼ��¼
	 * @param end
	 *            ������¼
	 * @return AppListRespBean�����
	 */
	/*
	 * private AppListRespBean[] queryAppList(String begin, String end,
	 * ReadCardReqBean readcard) { AppListRespBean[] appls = null; try {
	 * 
	 * LoadAppListBean loadAppListBean = new LoadAppListBean();
	 * loadAppListBean.setMsgId("0860");
	 * loadAppListBean.setCardNo(readcard.getCardNum());// ���ÿ���
	 * loadAppListBean.setCardSequenceNum(GlobalParameters.g_map_para
	 * .get("cardSequenceNum"));// Ӧ����� (֧����Ӧ��000)
	 * loadAppListBean.setCardAcceptTermIdent(GlobalParameters.g_map_para
	 * .get("cardAcceptTermIdent"));// �ܿ����ն˱�ʶ��(�ն˺�)
	 * loadAppListBean.setCardAcceptIdentcode(GlobalParameters.g_map_para
	 * .get("cardAcceptIdentcode"));// �ܿ�����ʶ��
	 * 
	 * if (begin.length() < 2) { begin = "0" + begin; } if (end.length() < 2) {
	 * end = "0" + end; }
	 * 
	 * loadAppListBean.setReservedPrivate1(begin + end); IsoCommHandler comm =
	 * new IsoCommHandler(); LoadAppListBean bean = (LoadAppListBean) comm
	 * .sendIsoMsg(loadAppListBean);// ��������ͱ���
	 * 
	 * if (bean != null) { String respcode = bean.getRespCode(); String
	 * reservedPrivate1 = bean.getReservedPrivate1();
	 * 
	 * if (StringFormat.isNull(respcode).equalsIgnoreCase("00")) {// ��Ӧ�ɹ� String
	 * totalCount = reservedPrivate1.substring(1, 4);// Ӧ�������� sTotalSize =
	 * totalCount; int iCurrentCount = Integer.valueOf(reservedPrivate1
	 * .substring(4, 6));// ��ǰ���ص�Ӧ������
	 * 
	 * if (appls == null) {// ��һ������ appls = new
	 * AppListRespBean[Integer.valueOf(totalCount)]; }
	 * 
	 * byte[] trec = reservedPrivate1.getBytes("GBK");
	 * 
	 * for (int i = 0; i < iCurrentCount; i++) { AppListRespBean appbean = new
	 * AppListRespBean(); byte[] record = new byte[59];
	 * 
	 * System.arraycopy(trec, 6 + i * 56, record, 0, 56);
	 * 
	 * byte[] tmp = new byte[4]; System.arraycopy(record, 0, tmp, 0, 4);
	 * appbean.setAppIdent(new String(tmp));// Ӧ�ñ�ʶ
	 * 
	 * tmp = new byte[2]; System.arraycopy(record, 4, tmp, 0, 2);
	 * appbean.setAppType(new String(tmp));// Ӧ������
	 * 
	 * tmp = new byte[50]; System.arraycopy(record, 6, tmp, 0, 50);
	 * appbean.setAppName(new String(tmp, "GBK"));// Ӧ������
	 * 
	 * 
	 * tmp = new byte[3]; System.arraycopy( record, 56, tmp, 0, 3);
	 * 
	 * appbean.setDiscount("000");// �ۿ�
	 * 
	 * appls[i] = appbean; }
	 * 
	 * } else {// ��Ӧʧ�� this.bError = true; this.respcode = respcode; resultMsg =
	 * "Ӧ���б�����ʧ�ܣ�"; } } else { this.bError = true; this.respcode = respcode;
	 * resultMsg = "�����������ӣ�"; } } catch (Exception ex) { ex.printStackTrace();
	 * }
	 * 
	 * return appls; }
	 */

	/**
	 * ��ѯ�����б�
	 * 
	 * @param start
	 *            ��ʼ��¼
	 * @param end
	 *            ������¼
	 * @return CreditQueryResBean�����
	 */
	@Deprecated
	private CreditQueryResBean[] queryCreditList(String begin, String end,
			ReadCardReqBean readcard) {
		CreditQueryResBean[] creditList = null;
		try {

			PointsQueryBean credits = new PointsQueryBean();

			credits.setMsgId("0200"); // ���ý�������

			credits.setTrack2(readcard.getCardNum());// ���ÿ���

			credits.setProcessCode("110001");// ���ô�����

			credits.setSysTraceAuditNum(TransSequence.getSysTraceAuditNum()); // �ܿ���ϵͳ���ٺ�

			credits.setDateExpired(null);// ����Ч��

			credits.setServicePINCaptureCode(null);// �����pin��ȡ��

			credits.setPinData(null);// ���˱�ʶ������

			credits.setSecurityRelatedControl(null);// ��ȫ������Ϣ

			credits.setServiceEntryMode("005");// ��������뷽ʽ��

			credits.setServiceConditionMode("00");// �����������

			credits.setOrganId("80000000");// ��ȯ������ʶ

			credits.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// �ܿ����ն˱�ʶ��41
			credits.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// �ܿ�����ʶ��42

			credits.setCurrencyTransCode("156");// ���׻��Ҵ���

			credits.setReservedPrivate2("54" + DbHelp.getBatchNum() + "000");// ���κ�

			credits.setMessageAuthentCode("11111111");

			if (begin.length() < 2) {
				begin = "0" + begin;
			}
			if (end.length() < 2) {
				end = "0" + end;
			}

			credits.setReservedPrivate3(begin + end);
			IsoCommHandler comm = new IsoCommHandler();
			PointsQueryBean bean = (PointsQueryBean) comm.sendIsoMsg(credits);// ��������ͱ���

			if (bean != null) {
				String respcode = bean.getRespCode();
				String reservedPrivate3 = bean.getReservedPrivate3();
				resultMsg = bean.getReservedPrivate4();
				if (StringFormat.isNull(respcode).equalsIgnoreCase("00")) {// ��Ӧ�ɹ�
					String totalCount = reservedPrivate3.substring(1, 4);// Ӧ��������
					sTotalSize = totalCount;
					int iCurrentCount = Integer.valueOf(reservedPrivate3
							.substring(4, 6));// ��ǰ���ص�Ӧ������

					if (creditList == null) {// ��һ������
						creditList = new CreditQueryResBean[Integer
								.valueOf(totalCount)];
					}

					byte[] trec = reservedPrivate3.getBytes("GBK");

					for (int i = 0; i < iCurrentCount; i++) {
						CreditQueryResBean creditBean = new CreditQueryResBean();
						byte[] record = new byte[75];

						System.arraycopy(trec, 6 + i * 75, record, 0, 75);

						byte[] tmp = new byte[11];
						System.arraycopy(record, 0, tmp, 0, 11);
						creditBean.setIssId(new String(tmp));// ��������ʶ

						tmp = new byte[12];
						System.arraycopy(record, 11, tmp, 0, 12);
						creditBean.setCredits(new String(tmp));// �������

						tmp = new byte[12];
						System.arraycopy(record, 23, tmp, 0, 12);
						creditBean.setMoney(new String(tmp));// �ɶһ����

						tmp = new byte[40];
						System.arraycopy(record, 35, tmp, 0, 40);
						creditBean.setIssCshort(new String(tmp, "GBK"));// �����������(���)

						creditList[i] = creditBean;
					}

				} else {// ��Ӧʧ��
					this.bError = true;
					this.respcode = respcode;

				}
			} else {
				this.bError = true;
				this.respcode = respcode;
				resultMsg = "�����������ӣ�";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return creditList;
	}

	// �����Ż�ȯ�б�(�Ż�ȯ����)
	private void doCouponsRequest2(ReadCardReqBean readcard) {

		CouponsReqBean reqBean = new CouponsReqBean();
		reqBean.setMerchantCode(DbHelp.getCardAcceptIdentcode());// �����̻����
		reqBean.setBaseCardNo(readcard.getCardNum());

		Request request = new Request();
		request.setData(reqBean);

		List<ResponseBean> list = DispatchRequest.doHttpRequest(
				Constants.USR_COUPONS_APPLY, request,
				CouponsApplyRespBean.class);

		if (list != null) {
			if (list.size() > 0) {
				if (((CouponsApplyRespBean) list.get(0)).getRespcode().equals(
						"-1")) {
					isUser = false;
					ResultRespBean result = new ResultRespBean();
					result.setMessage("�˿���δ�󶨣�");
					bundle.putSerializable("ResultRespBean", result);
				}
			}
			response.setError(false);
			ArrayList<ResponseBean> arrayList = new ArrayList<ResponseBean>(
					list);
			bundle.putSerializable("gridView", arrayList);
		} else {
			ResponseBean res = new ResponseBean();
			res.setMessage("������������");
			bError = true;
			resultMsg = "������������";
			response.setError(true);
			response.setData(res);
		}
	}

	// �����Ż�ȯ�б�
	private void doCouponsRequest(ReadCardReqBean readcard) {

		CouponsApplyReqBean reqBean = new CouponsApplyReqBean();
		reqBean.setMerchantCode(DbHelp.getCardAcceptIdentcode());// �����̻����
		reqBean.setBaseCardNo(readcard.getCardNum());// ���ÿ���

		Request request = new Request();
		request.setData(reqBean);

		List<ResponseBean> list = DispatchRequest.doHttpRequest(
				Constants.USR_COUPONS, request, CouponsListRespBean.class);

		if (list != null) {
			if (list.size() > 0) {
				if (((CouponsListRespBean) list.get(0)).getRespcode().equals(
						"-1")) {
					isUser = false;
				}
			}
			response.setError(false);
			ArrayList<ResponseBean> arrayList = new ArrayList<ResponseBean>(
					list);
			bundle.putSerializable("arrayList", arrayList);
		} else {
			ResponseBean res = new ResponseBean();
			res.setMessage("������������");
			bError = true;
			resultMsg = "������������";
			response.setError(true);
			response.setData(res);
		}
	}
}
