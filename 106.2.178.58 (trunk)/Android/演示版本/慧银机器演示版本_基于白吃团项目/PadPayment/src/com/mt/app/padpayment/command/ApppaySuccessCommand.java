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
 * @Description:消费command
 * 
 * @author:dw
 * 
 * @time:2013-7-22 下午4:42:02
 */
public class ApppaySuccessCommand extends AbstractCommand {

	private static Logger log = Logger.getLogger(ApppaySuccessCommand.class);
	private String amount = "";
	Response response = new Response();
	
	private  String appDiscount = ""; //支付卡折扣率
	private  String[]  couponsIDs ;  //优惠券编号
	private  String[]  couponsTypes ; //优惠券类型
	private  String passMima ;   //密码
	
	private String  sysNum  = "";  //交易流水号

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
				/*//是否有优惠券兑换交易
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
			Log.i("ApppaySuccessCommand 消费响应码:", respcode);

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
						"交易成功,消费金额为" + MoneyUtil.getMoney(amount) + "元");
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
	 * 组装消费请求，发送请求，接收响应，拆包
	 * 
	 * @param reqBean
	 * @return 返回响应的对象
	 */
	private ConsumeBean doConsumeRequest(ConsumeReqBean reqBean) {
		ConsumeBean consumeBean = new ConsumeBean();

		consumeBean.setMsgId("0200"); // MessageId消息类型

		String cardNum = (String) Controller.session.get("CardNum");
		consumeBean.setTrack2(cardNum);// 设置卡号

		consumeBean.setProcessCode("000000"); // 交易处理码

		consumeBean.setTransAmount(PackUtil.fillField(reqBean.getRealSum(),
				12, true, "0")); // 设置交易金额

		consumeBean.setDiscountAmount(PackUtil.fillField(reqBean.getVipSum(), 12, true, "0")+
				PackUtil.fillField(reqBean.getCouponsSum(), 12, true, "0")+
				PackUtil.fillField(reqBean.getSum(), 12, true, "0"));//附加金额

		consumeBean.setSysTraceAuditNum(TransSequence.getSysTraceAuditNum()); // 受卡方系统跟踪号

		consumeBean.setServiceEntryMode("071");// 服务点输入方式码

		consumeBean.setServiceConditionMode("00");// 服务点条件码

		consumeBean.setServicePINCaptureCode("06");// 服务点PIN获取码
		consumeBean.setAuthorIdentResp(null);// 授权码
		consumeBean.setDateExpired(null); // 卡有效期
		consumeBean.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// 受卡机终端标识码(终端号)

		consumeBean.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// 受卡方标识码

		consumeBean.setCurrencyTransCode("156");// 交易货币代码

		 byte[] btpass = StrUtil.HexStringToByte(passMima, "");
		 try{
			// consumeBean.setPinData(new String(btpass, "ISO-8859-1"));// 个人标识码数据
			 consumeBean.setPinData(passMima);// 个人标识码数据
			 String ss = consumeBean.getPinData();
			 byte[] gg = ss.getBytes("ISO-8859-1");
			 System.out.println("");
			}catch(Exception ex){
				ex.printStackTrace();
			}

		consumeBean.setSecurityRelatedControl("2600000000000000");// 安全控制信息
		
		if (CouponGridAdapter.apId!=null) {
			consumeBean.setOrganId(CouponGridAdapter.apId);//发卷机构标识
			CouponGridAdapter.apId = null;
		} else {
			consumeBean.setOrganId(null);//发卷机构标识
		}
		
		if (CouponGridAdapter.listChecked!= null){
			consumeBean.setCouponsAdvertId(CouponGridAdapter.listChecked);//发券方标识
			CouponGridAdapter.listChecked= null;
		} else {
			consumeBean.setCouponsAdvertId(null);//发券方标识
		}


		consumeBean.setReservedPrivate1(reqBean.getAppId());// 应用标识

		consumeBean.setReservedPrivate2("22"+DbHelp.getBatchNum()+"000");// 批次号
		if (reqBean.getCouponsId()!=null) {			
			String size = reqBean.getCouponsId().length + "";
			size = PackUtil.fillField(size, 3, true, "0");
			String body = "";
			for (int i = 0; i < reqBean.getCouponsId().length; i++) {
				body = body
						+ PackUtil.fillField(reqBean.getCouponsId()[i], 30, false,
								" ");
			}
			consumeBean.setReservedPrivate3(size + body);  //优惠券兑换信息
		} else {
			consumeBean.setReservedPrivate3(null);  //优惠券兑换信息
		}

		consumeBean.setMessageAuthentCode("11111111");

		DbHandle db = new DbHandle();
		db.insertObject("TBL_TMPFlOW", consumeBean); // 插入临时流水表
		// 将其他字段插入流水表
		db.update("TBL_TMPFlOW", new String[] { 
				"VIP_AMOUNT", "ORIGIN_AMOUNT" ,"USER_ID"},
				new String[] { reqBean.getVipSum(),
						reqBean.getSum(),Controller.session.get("userID")+""  }, "SYS_TRACE_AUDIT_NUM = ?",
				new String[] { consumeBean.getSysTraceAuditNum() });

		IsoCommHandler comm = new IsoCommHandler();
		ConsumeBean bean = (ConsumeBean) comm.sendIsoMsg(consumeBean); // 打包并发送报文
		if (comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT) {
			db.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // 交易失败，将临时流水表中数据转存到冲正表
			db.update("TBL_REVERSAL", new String[]{"FLUSH_OCUNT", "FLUSH_RESULT"}, new String[]{"0","-1"}, "SYS_TRACE_AUDIT_NUM = ?", new String[] { consumeBean.getSysTraceAuditNum() });
		} 
		/**MAC校验失败 **/
		if (comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED) {
			db.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // 交易失败，将临时流水表中数据转存到冲正表
			db.update("TBL_REVERSAL", new String[]{"FLUSH_OCUNT", "FLUSH_RESULT"}, new String[]{"0","-1"}, "SYS_TRACE_AUDIT_NUM = ?", new String[] { consumeBean.getSysTraceAuditNum() });
		} 
		if (bean != null) {
			db.updateRespObject("TBL_TMPFlOW", bean, "SYS_TRACE_AUDIT_NUM = ?", // 更新临时流水表
					new String[] { bean.getSysTraceAuditNum() });

			
			amount = Integer.parseInt(bean.getTransAmount()) + "";
			db.transferTable("TBL_TMPFlOW", "TBL_FlOW"); // 交易成功将临时流水表中数据转存到流水表
			
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
				bundle.putString("msgInfo", "MAC校验失败！");
			}else if(comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT){
				bundle.putString("msgInfo", "请检查网络连接！");
			}else{
				bundle.putString("msgInfo", "请检查网络连接！");
			}

			response.setBundle(bundle);
		}

		return bean;
	}
}
