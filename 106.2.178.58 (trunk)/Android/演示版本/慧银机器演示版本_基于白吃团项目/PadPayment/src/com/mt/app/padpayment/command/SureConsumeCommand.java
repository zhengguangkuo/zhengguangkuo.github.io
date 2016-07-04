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
 * �Ż�ȯ����command
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
				ActivityID.map.get("ACTIVITY_ID_PAYSUCCESS"))||"0.00".equals(reqBean.getRealSum())) {// �Ż�ȯ�һ�
			CouponsConvertBean couponsConvert = doCouponsRequest(reqBean);
			if (couponsConvert != null) {
				reqBean.setSwapCode(couponsConvert.getSwapCode());
				if (reqBean.getRealSum().equals("0.0")||reqBean.getRealSum().equals("0.00")) {
					ResultRespBean result = new ResultRespBean();
					if (respcode.equals("")) {
						result.setMessage("�һ��ɹ���");
						
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
	 * ��װ�Ż�ȯ�һ����󣬷������󣬽�����Ӧ�����
	 * 
	 * @param reqBean
	 * @return ������Ӧ�Ķ���
	 */
	private CouponsConvertBean doCouponsRequest(ConsumeReqBean reqBean) {

		CouponsConvertBean couponsConvert = new CouponsConvertBean();

		couponsConvert.setMsgId("0200"); // ���ý�������

		String cardNum = (String) Controller.session.get("CardNum");

		couponsConvert.setTrack2(cardNum);// ���û�������

		couponsConvert.setProcessCode("020000");// ���ô�����

		couponsConvert.setTransAmount(PackUtil.fillField(reqBean.getCouponsSum(),
				12, true, "0")); // ���ý��׽��
//		couponsConvert.setTransAmount(PackUtil.fillField("0",
//				12, true, "0")); // ���ý��׽��
		couponsConvert.setDiscountAmount(PackUtil.fillField(reqBean.getVipSum(), 12, true, "0")+
				PackUtil.fillField(reqBean.getCouponsSum(), 12, true, "0")+
				PackUtil.fillField(reqBean.getSum(), 12, true, "0"));//���ӽ��
		
		couponsConvert.setSysTraceAuditNum(TransSequence.getSysTraceAuditNum()); // �ܿ���ϵͳ���ٺ�

		couponsConvert.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// �ܿ����ն˱�ʶ��41
		couponsConvert.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// �ܿ�����ʶ��42
		if (CouponGridAdapter.apId!=null) {
			couponsConvert.setOrganId(CouponGridAdapter.apId);//���������ʶ
			CouponGridAdapter.apId = null;
		}
		
		if (CouponGridAdapter.listChecked!= null){
			couponsConvert.setCouponsAdvertId(CouponGridAdapter.listChecked);//��ȯ�����ʶ
			CouponGridAdapter.listChecked= null;
		} 

		String pici = DbHelp.getBatchNum();
		couponsConvert.setReservedPrivate2("62"+PackUtil.fillField(pici, 6, true, "0")+"000"); // �������κ�
		
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
		couponsConvert.setReservedPrivate3(size + body);  //�Ż�ȯ�һ���Ϣ
		couponsConvert.setMessageAuthentCode("11111111");

		DbHandle db = new DbHandle();
		db.delete("TBL_TMPFlOW", null, null);
		db.insertObject("TBL_TMPFlOW", couponsConvert); // ������ʱ��ˮ��

		Map map2 = db.selectOneRecord("TBL_TMPFlOW",new String[]{ "RESERVED_PRIVATE2"}, "SYS_TRACE_AUDIT_NUM = ?",
				new String[] { couponsConvert.getSysTraceAuditNum() },null,null,null);
		// �������ֶβ�����ˮ��
		db.update("TBL_TMPFlOW", new String[] { 
				"VIP_AMOUNT", "ORIGIN_AMOUNT","USER_ID" },
				new String[] {  reqBean.getVipSum(),
						reqBean.getSum(),Controller.session.get("userID")+"" }, "SYS_TRACE_AUDIT_NUM = ?",
				new String[] { couponsConvert.getSysTraceAuditNum() });

		
		IsoCommHandler comm = new IsoCommHandler();
		
		CouponsConvertBean bean =(CouponsConvertBean) comm.sendIsoMsg(couponsConvert);
		if(comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT ){
			ResultRespBean result = new ResultRespBean();
			result.setMessage("δ�յ���Ӧ��");
			Bundle bundle = new Bundle();
			bundle.putSerializable("ResultRespBean", result);
			response.setBundle(bundle);
			db.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // ����ʧ�ܣ�����ʱ��ˮ��������ת�浽������
			db.update("TBL_REVERSAL", new String[]{"FLUSH_OCUNT", "FLUSH_RESULT"}, new String[]{"0","-1"}, "SYS_TRACE_AUDIT_NUM = ?", new String[] { couponsConvert.getSysTraceAuditNum() });
			
		}
		/**MACУ��ʧ�� **/
		if (comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED) {
			db.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // ����ʧ�ܣ�����ʱ��ˮ��������ת�浽������
			db.update("TBL_REVERSAL", new String[]{"FLUSH_OCUNT", "FLUSH_RESULT"}, new String[]{"0","-1"}, "SYS_TRACE_AUDIT_NUM = ?", new String[] { couponsConvert.getSysTraceAuditNum() });
		} 
		
		if (bean != null) {
			db.updateRespObject("TBL_TMPFlOW", bean, "SYS_TRACE_AUDIT_NUM = ?", // ������ʱ��ˮ��
					new String[] { bean.getSysTraceAuditNum() });
				db.transferTable("TBL_TMPFlOW", "TBL_FlOW"); // ���׳ɹ�����ʱ��ˮ��������ת�浽��ˮ��
				
				db.update("TBL_FlOW", new String[]{"COUPONS_IDS","COUPONS_TYPES"}
				, new String[]{idsStr.substring(0, idsStr.length()) , "����ȯ"}
				, "SYS_TRACE_AUDIT_NUM = ?", new String[]{bean.getSysTraceAuditNum()});
			
				if (!bean.getRespCode().equals("00")) {
					if (bean.getReservedPrivate4() != null) {
						respcode = bean.getReservedPrivate4();
					} else {
						Map<String, String> map = db
								.rawQueryOneRecord(
										"select MESSAGE from TBL_RESPONSE_CODE where RESP_CODE = ?",
										new String[] { bean.getRespCode() });
						if (map.get("MESSAGE") != null) { // ��Ӧ������������ȡ��ѯ�����3��
							respcode = map.get("MESSAGE");
						}
					}
				} else {
					flowNum =  bean.getSysTraceAuditNum();
				}
			
			  
		} else {
			ResultRespBean result = new ResultRespBean();
			if(comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED){
				result.setMessage("MacУ��ʧ�ܣ�");
				respcode = "MacУ��ʧ�ܣ�";
			}else{
				result.setMessage("�����������ӣ�");
				respcode = "�����������ӣ�";
			}
			
			
			bundle.putSerializable("ResultRespBean", result);
			Controller.session.put("succForward",ActivityID.map.get("ACTIVITY_ID_CANCELRESULT"));
			
		}
		return bean;
	}
	
}
