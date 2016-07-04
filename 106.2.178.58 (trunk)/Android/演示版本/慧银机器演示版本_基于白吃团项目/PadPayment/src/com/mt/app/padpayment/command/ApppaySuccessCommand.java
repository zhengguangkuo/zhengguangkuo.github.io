package com.mt.app.padpayment.command;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.util.Log;

import com.mt.android.db.DbHandle;
import com.mt.android.message.iso.util.StrUtil;
import com.mt.android.protocol.util.ProtocolRespCode;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.adapter.CouponGridAdapter;
import com.mt.app.padpayment.common.DbHelp;
import com.mt.app.padpayment.common.GlobalParameters;
import com.mt.app.padpayment.common.IsoCommHandler;
import com.mt.app.padpayment.common.MathUtil;
import com.mt.app.padpayment.message.iso.trans.ConsumeBean;
import com.mt.app.padpayment.requestbean.ConsumeReqBean;
import com.mt.app.padpayment.responsebean.ResultRespBean;
import com.mt.app.padpayment.tools.MoneyUtil;
import com.mt.app.padpayment.tools.PackUtil;
import com.mt.app.padpayment.tools.TransSequence;

/**
 * 
 * 
 * @Description:����command
 * 
 * @author:dw
 * 
 * @time:2013-7-22 ����4:42:02
 */
public class ApppaySuccessCommand extends AbstractCommand {

	private static Logger log = Logger.getLogger(ApppaySuccessCommand.class);
	private String amount = "";
	Response response = new Response();
	
	private  String appDiscount = ""; //֧�����ۿ���
	private  String[]  couponsIDs ;  //�Ż�ȯ���
	private  String[]  couponsTypes ; //�Ż�ȯ����
	private  String passMima ;   //����
	
	private String  sysNum  = "";  //������ˮ��

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

		DbHandle handle = new DbHandle();
		response.setError(false);

		ConsumeReqBean reqBean = (ConsumeReqBean) getRequest().getData();
		passMima = reqBean.getAppPwd();
		appDiscount = reqBean.getAppDiscount();
		couponsIDs = reqBean.getCouponsSeId();
		couponsTypes = reqBean.getCouponsSeType();
		ConsumeBean consumeBean = doConsumeRequest(reqBean);

		if (consumeBean != null) {

			if (!consumeBean.getRespCode().equals("00")) {
				response.setError(true);
				if (!consumeBean.getReservedPrivate4().equals("")) {
					Bundle bundle = new Bundle();
					ResultRespBean result = new ResultRespBean();
					result.setMessage(consumeBean.getReservedPrivate4());
					bundle.putSerializable("ResultRespBean", result);
					response.setBundle(bundle);
				}
				/*//�Ƿ����Ż�ȯ�һ�����
				if (reqBean.isUseCoupons()) {
					if (!reqBean.getFlowNum().equals("")) {
						DbHandle db = new DbHandle();
						Map map = db.selectOneRecord("TBL_FlOW", new String[] {
								"CARD_NO", "PROCESS_CODE", "TRANS_AMOUNT",
								"SYS_TRACE_AUDIT_NUM", "AUTHOR_IDENT_RESP",
								"ADDIT_RESP_DATA", "SWAP_CODE_1",
								"COUPONS_ADVERT_ID" ,"RESERVED_PRIVATE2"}, "SYS_TRACE_AUDIT_NUM = ?",
								new String[] { reqBean.getFlowNum() }, null,
								null, null);
						if (map != null && map.size() > 0) {
							db.insert(
									"TBL_REVERSAL",
									new String[] { "MSG_ID","CARD_NO", "PROCESS_CODE",
											"TRANS_AMOUNT",
											"SYS_TRACE_AUDIT_NUM",
											"AUTHOR_IDENT_RESP",
											"ADDIT_RESP_DATA", "SWAP_CODE",
											"COUPONS_ADVERT_ID" ,"FLUSH_OCUNT","FLUSH_RESULT","RESERVED_PRIVATE2"},
									new String[] {
											"0200",
											map.get("CARD_NO") + "",
											map.get("PROCESS_CODE") + "",
											map.get("TRANS_AMOUNT") + "",
											map.get("SYS_TRACE_AUDIT_NUM") + "",
											map.get("AUTHOR_IDENT_RESP") + "",
											map.get("ADDIT_RESP_DATA") + "",
											map.get("SWAP_CODE_1") + "",
											map.get("COUPONS_ADVERT_ID") + "","0","-1",
											map.get("RESERVED_PRIVATE2")+""});
							
							db.delete("TBL_FlOW", "SYS_TRACE_AUDIT_NUM = ?",
								new String[] { reqBean.getFlowNum() });
						}
					}
				}*/
			
			}

			Bundle boudle = new Bundle();
			String respcode = consumeBean.getRespCode();
			Log.i("ApppaySuccessCommand ������Ӧ��:", respcode);

			if (response.isError()) {

				String msg = "";
				if (consumeBean.getReservedPrivate4() != null
						&& !consumeBean.getReservedPrivate4().equals("")) {
					msg = consumeBean.getReservedPrivate4();
				} else {
					List<Map<String, String>> list = handle.select(
							"TBL_RESPONSE_CODE", new String[] { "MESSAGE" },
							"RESP_CODE=?", new String[] { respcode }, null,
							null, null);
					if (list.size() != 0) {
						msg = list.get(0).get("MESSAGE");
					}
				}

				boudle.putString("msgInfo", msg);
			} else {
				boudle.putString("msgInfo",
						"���׳ɹ�,���ѽ��Ϊ" + MoneyUtil.getMoney(amount) + "Ԫ");
			}
			
			boudle.putString("sysnum", sysNum);

			response.setBundle(boudle);
		} else {
//			if (reqBean.isUseCoupons()) {
//				if (!reqBean.getFlowNum().equals("")) {
//					DbHandle db = new DbHandle();
//					Map map = db.selectOneRecord("TBL_FlOW", new String[] {
//							"CARD_NO", "PROCESS_CODE", "TRANS_AMOUNT",
//							"SYS_TRACE_AUDIT_NUM", "AUTHOR_IDENT_RESP",
//							"ADDIT_RESP_DATA", "SWAP_CODE_1",
//							"COUPONS_ADVERT_ID","RESERVED_PRIVATE2" }, "SYS_TRACE_AUDIT_NUM = ?",
//							new String[] { reqBean.getFlowNum() }, null,
//							null, null);
//					if (map != null && map.size() > 0) {
//						db.insert(
//								"TBL_REVERSAL",
//								new String[] { "MSG_ID","CARD_NO", "PROCESS_CODE",
//										"TRANS_AMOUNT",
//										"SYS_TRACE_AUDIT_NUM",
//										"AUTHOR_IDENT_RESP",
//										"ADDIT_RESP_DATA", "SWAP_CODE",
//										"COUPONS_ADVERT_ID" ,"FLUSH_OCUNT","FLUSH_RESULT","RESERVED_PRIVATE2"},
//								new String[] {
//										"0200",
//										map.get("CARD_NO") + "",
//										map.get("PROCESS_CODE") + "",
//										map.get("TRANS_AMOUNT") + "",
//										map.get("SYS_TRACE_AUDIT_NUM") + "",
//										map.get("AUTHOR_IDENT_RESP") + "",
//										map.get("ADDIT_RESP_DATA") + "",
//										map.get("SWAP_CODE_1") + "",
//										map.get("COUPONS_ADVERT_ID") + "" ,"0","-1",
//										map.get("RESERVED_PRIVATE2")+""});
//						db.delete("TBL_FlOW", "SYS_TRACE_AUDIT_NUM = ?",
//							new String[] { reqBean.getFlowNum() });
//					}
//				}
//			}
		
		
		}
		setResponse(response);
	}

	@Override
	protected void onAfterExecute() {
		super.onAfterExecute();
		Response response = getResponse();

		if (response.isError()) {
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_APPPAYSUCCESS"));
		} else {

			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_APPPAYSUCCESS"));
		}
		setResponse(response);
	}

	/**
	 * ��װ�������󣬷������󣬽�����Ӧ�����
	 * 
	 * @param reqBean
	 * @return ������Ӧ�Ķ���
	 */
	private ConsumeBean doConsumeRequest(ConsumeReqBean reqBean) {
		ConsumeBean consumeBean = new ConsumeBean();

		consumeBean.setMsgId("0200"); // MessageId��Ϣ����

		String cardNum = (String) Controller.session.get("CardNum");
		consumeBean.setTrack2(cardNum);// ���ÿ���

		consumeBean.setProcessCode("000000"); // ���״�����

		consumeBean.setTransAmount(PackUtil.fillField(reqBean.getRealSum(),
				12, true, "0")); // ���ý��׽��

		consumeBean.setDiscountAmount(PackUtil.fillField(reqBean.getVipSum(), 12, true, "0")+
				PackUtil.fillField(reqBean.getCouponsSum(), 12, true, "0")+
				PackUtil.fillField(reqBean.getSum(), 12, true, "0"));//���ӽ��

		consumeBean.setSysTraceAuditNum(TransSequence.getSysTraceAuditNum()); // �ܿ���ϵͳ���ٺ�

		consumeBean.setServiceEntryMode("071");// ��������뷽ʽ��

		consumeBean.setServiceConditionMode("00");// �����������

		consumeBean.setServicePINCaptureCode("06");// �����PIN��ȡ��
		consumeBean.setAuthorIdentResp(null);// ��Ȩ��
		consumeBean.setDateExpired(null); // ����Ч��
		consumeBean.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// �ܿ����ն˱�ʶ��(�ն˺�)

		consumeBean.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// �ܿ�����ʶ��

		consumeBean.setCurrencyTransCode("156");// ���׻��Ҵ���

		 byte[] btpass = StrUtil.HexStringToByte(passMima, "");
		 try{
			// consumeBean.setPinData(new String(btpass, "ISO-8859-1"));// ���˱�ʶ������
			 consumeBean.setPinData(passMima);// ���˱�ʶ������
			 String ss = consumeBean.getPinData();
			 byte[] gg = ss.getBytes("ISO-8859-1");
			 System.out.println("");
			}catch(Exception ex){
				ex.printStackTrace();
			}

		consumeBean.setSecurityRelatedControl("2600000000000000");// ��ȫ������Ϣ
		
		if (CouponGridAdapter.apId!=null) {
			consumeBean.setOrganId(CouponGridAdapter.apId);//���������ʶ
			CouponGridAdapter.apId = null;
		} else {
			consumeBean.setOrganId(null);//���������ʶ
		}
		
		if (CouponGridAdapter.listChecked!= null){
			consumeBean.setCouponsAdvertId(CouponGridAdapter.listChecked);//��ȯ����ʶ
			CouponGridAdapter.listChecked= null;
		} else {
			consumeBean.setCouponsAdvertId(null);//��ȯ����ʶ
		}


		consumeBean.setReservedPrivate1(reqBean.getAppId());// Ӧ�ñ�ʶ

		consumeBean.setReservedPrivate2("22"+DbHelp.getBatchNum()+"000");// ���κ�
		if (reqBean.getCouponsId()!=null) {			
			String size = reqBean.getCouponsId().length + "";
			size = PackUtil.fillField(size, 3, true, "0");
			String body = "";
			for (int i = 0; i < reqBean.getCouponsId().length; i++) {
				body = body
						+ PackUtil.fillField(reqBean.getCouponsId()[i], 30, false,
								" ");
			}
			consumeBean.setReservedPrivate3(size + body);  //�Ż�ȯ�һ���Ϣ
		} else {
			consumeBean.setReservedPrivate3(null);  //�Ż�ȯ�һ���Ϣ
		}

		consumeBean.setMessageAuthentCode("11111111");

		DbHandle db = new DbHandle();
		db.insertObject("TBL_TMPFlOW", consumeBean); // ������ʱ��ˮ��
		// �������ֶβ�����ˮ��
		db.update("TBL_TMPFlOW", new String[] { 
				"VIP_AMOUNT", "ORIGIN_AMOUNT" ,"USER_ID"},
				new String[] { reqBean.getVipSum(),
						reqBean.getSum(),Controller.session.get("userID")+""  }, "SYS_TRACE_AUDIT_NUM = ?",
				new String[] { consumeBean.getSysTraceAuditNum() });

		IsoCommHandler comm = new IsoCommHandler();
		ConsumeBean bean = (ConsumeBean) comm.sendIsoMsg(consumeBean); // ��������ͱ���
		if (comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT) {
			db.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // ����ʧ�ܣ�����ʱ��ˮ��������ת�浽������
			db.update("TBL_REVERSAL", new String[]{"FLUSH_OCUNT", "FLUSH_RESULT"}, new String[]{"0","-1"}, "SYS_TRACE_AUDIT_NUM = ?", new String[] { consumeBean.getSysTraceAuditNum() });
		} 
		/**MACУ��ʧ�� **/
		if (comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED) {
			db.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // ����ʧ�ܣ�����ʱ��ˮ��������ת�浽������
			db.update("TBL_REVERSAL", new String[]{"FLUSH_OCUNT", "FLUSH_RESULT"}, new String[]{"0","-1"}, "SYS_TRACE_AUDIT_NUM = ?", new String[] { consumeBean.getSysTraceAuditNum() });
		} 
		if (bean != null) {
			db.updateRespObject("TBL_TMPFlOW", bean, "SYS_TRACE_AUDIT_NUM = ?", // ������ʱ��ˮ��
					new String[] { bean.getSysTraceAuditNum() });

			
			amount = Integer.parseInt(bean.getTransAmount()) + "";
			db.transferTable("TBL_TMPFlOW", "TBL_FlOW"); // ���׳ɹ�����ʱ��ˮ��������ת�浽��ˮ��
			
			StringBuilder sbID = new StringBuilder();
			if(couponsIDs != null && couponsIDs.length != 0){
				for(int i = 0 ; i< couponsIDs.length ; i++){
					sbID.append(couponsIDs[i]);
					sbID.append("      ");
				}
			}
			
			StringBuilder sbT = new StringBuilder();
			if(couponsTypes != null && couponsTypes.length != 0){
				for(int i = 0 ; i<couponsTypes.length ; i++){
					sbT.append(couponsTypes[i]);
					sbT.append("      ");
				}
			}
			
			db.update("TBL_FlOW", new String[]{"APP_DISCOUNT","COUPONS_IDS","COUPONS_TYPES"}
			, new String[]{appDiscount , sbID.toString() , sbT.toString() }
			, "SYS_TRACE_AUDIT_NUM = ?", new String[]{bean.getSysTraceAuditNum()});
			
			sysNum = bean.getSysTraceAuditNum() ;

		} else {
			response.setError(true);
			Bundle bundle = new Bundle();
			
			if(comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED){
				bundle.putString("msgInfo", "MACУ��ʧ�ܣ�");
			}else if(comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT){
				bundle.putString("msgInfo", "�����������ӣ�");
			}else{
				bundle.putString("msgInfo", "�����������ӣ�");
			}

			response.setBundle(bundle);
		}

		return bean;
	}
}
