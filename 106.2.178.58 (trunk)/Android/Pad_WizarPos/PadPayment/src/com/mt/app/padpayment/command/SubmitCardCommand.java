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
 * 提交卡号和请求类型的command
 * 
 * @author dw
 * 
 */
public class SubmitCardCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(SubmitCardCommand.class);
	private boolean bError = false;// 用于标记本次请求是否产生了Error
	private String sTotalSize = "-1";
	private String respcode = "";// 用于存放响应码
	private Bundle bundle = new Bundle();
	private Response response = new Response();
	private String resultMsg = ""; // 响应码对应的消息
	private String cardError = ""; // 卡号错误
	private boolean isUser = true;// 是否是合法用户

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

		// 从首页进入 刷卡后在go()方法中要实现的逻辑
		/* 获取卡号,交易类型 */
		try {
			// 清理之前交易数据
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
				// doCouponsRequest(readcard);// 优惠券列表请求

				ReadCardReqBean readcard = (ReadCardReqBean) getRequest()
						.getData();

				AppReqBean reqBean = new AppReqBean();
				if (readcard != null) {

					Controller.session.put("CardNum", readcard.getCardNum()
							.toString());
					reqBean.setBaseCardNo(readcard.getCardNum());
					reqBean.setMerchantCode(DbHelp.getCardAcceptIdentcode());// 商户编号
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
					appList = new ArrayList<ResponseBean>(pList);// 应用列表LIST
				}

				if (isUser) {
					List<ResponseBean> cList = DispatchRequest.doHttpRequest(
							Constants.USR_COUPONS, request,
							CouponsListRespBean.class);

					ArrayList<ResponseBean> couponsList = null;

					if (cList != null) {
						couponsList = new ArrayList<ResponseBean>(cList);// 优惠券列表LIST
					}
					if (appList == null && couponsList == null) {
						isUser = false;
						ResultRespBean result = new ResultRespBean();
						result.setMessage("请检查网络连接！");
						bundle.putSerializable("ResultRespBean", result);

					} else {
						// 将对象数组传入到下一个Activity
						bundle.putSerializable("CARDBINDAPPLIST", appList);// 基卡所绑定的支付应用列表
						bundle.putSerializable("COUPONSLIST", couponsList);// 基卡所绑定的优惠券列表

					}

				} else {

					ResultRespBean result = new ResultRespBean();
					result.setMessage("此卡号未绑定！");
					bundle.putSerializable("ResultRespBean", result);
				}
			}

			// 消费撤销和积分撤销 优惠券兑换撤销 刷卡后在go()方法中要实现的逻辑
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
					// 消费撤销发送请求到后台
					if (Controller.session.get("type") != null
							&& Controller.session.get("type").equals("consume")) {
						sendRequest(cardN, vouch);
					} else if (Controller.session.get("type") != null
							&& Controller.session.get("type").equals("credit")) {// 积分撤销发送请求
						sendRequestScore(cardN, vouch);
					} else if (Controller.session.get("type") != null
							&& Controller.session.get("type").equals("coupon")) {// 兑换撤销发送请求
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
			// 积分消费 积分查询刷卡后在go()方法中要实现的逻辑
			if (Controller.session.get("succForward") != null
					&& (Controller.session.get("succForward").equals(
							ActivityID.map
									.get("ACTIVITY_ID_CreditsConsumeActivity")) || Controller.session
							.get("succForward")
							.equals(ActivityID.map
									.get("ACTIVITY_ID_CreditsQueryActivity")))) {

				ArrayList<CreditQueryResBean> creditList = new ArrayList<CreditQueryResBean>();// 积分列表LIST
				
				ReadCardReqBean readcard = (ReadCardReqBean) getRequest()
						.getData();

				if (readcard != null) {
					Controller.session.put("CardNum", readcard.getCardNum()
							.toString());
					Request request = new Request();
					//设置请求数据 卡号和商户号
					CreditReqBean creditReqBean = new CreditReqBean();
					creditReqBean.setBaseCardNo(readcard.getCardNum());//基卡卡号
					creditReqBean.setMerchantCode(DbHelp.getCardAcceptIdentcode());//商户号
					request.setData(creditReqBean);
					//发送请求
					List<ResponseBean> respList = DispatchRequest.doHttpRequest(
							Constants.USR_CREDITS, request, CreditQueryResBean.class);
					
					
					if (respList != null) {//收到应答
						
						for (int i = 0; i <respList.size(); i++) {
							creditList.add((CreditQueryResBean)respList.get(i));
						}						
						// 将对象数组传入到下一个Activity
						bundle.putSerializable("creditList", creditList);// 积分列表
					} else {
						response.setError(true);
						ResultRespBean bean = new ResultRespBean();
						bean.setMessage("请检查网络连接！");
						bundle.putSerializable("ResultRespBean", bean);
						response.setBundle(bundle);
					}
					
				} else {
					response.setError(true);
					ResultRespBean bean = new ResultRespBean();
					bean.setMessage("卡号不能为空！");
					bundle.putSerializable("ResultRespBean", bean);
					response.setBundle(bundle);
				}
				

			}

			/*
			 * // 积分消费 积分查询刷卡后在go()方法中要实现的逻辑 if
			 * (Controller.session.get("succForward") != null &&
			 * (Controller.session.get("succForward").equals( ActivityID.map
			 * .get("ACTIVITY_ID_CreditsConsumeActivity")) || Controller.session
			 * .get("succForward") .equals(ActivityID.map
			 * .get("ACTIVITY_ID_CreditsQueryActivity")))) {
			 * 
			 * ArrayList<CreditQueryResBean> creditList = new
			 * ArrayList<CreditQueryResBean>();// 积分列表LIST int currentLoad =
			 * 0;// 用于存放当前下载的 ReadCardReqBean readcard = (ReadCardReqBean)
			 * getRequest() .getData();
			 * 
			 * if (readcard != null) { Controller.session.put("CardNum",
			 * readcard.getCardNum() .toString()); } // 循环读取积分列表 int begin = 1,
			 * end = 8; int iTotalSize = 0; boolean bRun = true; // 查询指定卡号的积分列表
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
			 * - currentLoad); } } else if (bError) {// 报文出错,查询错误代码对照表 bRun =
			 * false; bError = true; List<Map<String, String>> list = new
			 * DbHandle().select( "TBL_RESPONSE_CODE", new String[] { "MESSAGE"
			 * }, "RESP_CODE", new String[] { respcode }, null, null, null); if
			 * (list.size() != 0) { resultMsg = list.get(0).get("MESSAGE"); } }
			 * else {// 正常结束 bRun = true; break; } }
			 * 
			 * // 将对象数组传入到下一个Activity bundle.putSerializable("creditList",
			 * creditList);// 积分列表
			 * 
			 * }
			 */
			// 应用 查询 刷卡后在go()方法中要实现的逻辑
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
					reqBean.setMerchantCode(DbHelp.getCardAcceptIdentcode());// 商户编号
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
							result.setMessage("此卡号未绑定！");
							bundle.putSerializable("ResultRespBean", result);
						}
					}
					ArrayList<ResponseBean> appList = new ArrayList<ResponseBean>(
							cList);// 应用列表LIST
					bundle.putSerializable("CARDBINDAPPLIST", appList);// 基卡所绑定的应用列表

				}
				int[] flags = new int[1];
				flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
				response.setFlags(flags);

				// 将对象数组传入到下一个Activity

			}

			// 优惠券列表查询
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

				// 查询优惠券列表并传入到下一界面
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

				result.setMessage("您输入的卡号和该交易卡号不一致！");

				bundle.putSerializable("ResultRespBean", result);
				response.setBundle(bundle);
				response.setTargetActivityID(ActivityID.map
						.get("ACTIVITY_ID_CANCELRESULT"));
			} else {
				if (!isUser) { // 不是有效用户

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
				result.setMessage("交易失败！");
			}
			bundle.putSerializable("ResultRespBean", result);
			response.setBundle(bundle);
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_CANCELRESULT"));

		}

		setResponse(response);
	}

	// 消费撤销发送请求
	private void sendRequest(String num, String vou) {
		// 从流水表里取出 （消费撤销）
		DbHandle handle = new DbHandle();
		/*
		 * String[] columns = new String[]{"CARD_NO", "TRANS_AMOUNT",
		 * "DATE_EXPIRED", "SERVICE_ENTRY_MODE", "SERVICE_CONDITION_MODE",
		 * "SERVICE_PIN_CAPTURE_CODE", "RET_REFER_NUM", "AUTHOR_IDENT_RESP",
		 * "CURRENCY_TRANS_CODE", "PIN_DATA","SECURITY_RELATED_CONTROL",
		 * "RESERVED_PRIVATE1", "SYS_TRACE_AUDIT_NUM", "LOCAL_TRANS_DATE_1",
		 * "RESERVED_PRIVATE2"}; //这三个字段是要改变的，要成为旧的信息字段
		 */
		List<Map<String, String>> list = handle.select("TBL_FlOW",
				DbInfoImpl.FieldNames[1],
				"TRACK2 =? and SYS_TRACE_AUDIT_NUM=?",
				new String[] { num, vou }, null, null, null);
		if (list.size() != 0) {
			ConsumeRepealBean bean = new ConsumeRepealBean();
			bean.setMsgId("0200");
			bean.setTrack2(num);// 设置卡号
			bean.setProcessCode("200000"); // 设置交易处理码
			bean.setCardNo(list.get(0).get("CARD_NO_1"));//卡号
			bean.setTransAmount(list.get(0).get("TRANS_AMOUNT")); // 交易金额
			bean.setDiscountAmount(list.get(0).get("DISCOUNT_AMOUNT")); // 附加金额
			bean.setSysTraceAuditNum(TransSequence.getSysTraceAuditNum()); // 受卡方系统跟踪号（本次交易的流水号）
			bean.setDateExpired(list.get(0).get("DATE_EXPIRED_1").equals("") ? null
					: list.get(0).get("DATE_EXPIRED_1")); // 卡有效期
			bean.setServiceEntryMode(list.get(0).get("SERVICE_ENTRY_MODE")); // 服务点输入方式码
			bean.setServiceConditionMode("00"); // 服务点条件码
			bean.setServicePINCaptureCode(list.get(0)
					.get("SERVICE_PIN_CAPTURE_CODE").equals("") ? null : list
					.get(0).get("SERVICE_PIN_CAPTURE_CODE")); // 服务点PIN获取码
			bean.setRetReferNum(list.get(0).get("RET_REFER_NUM_1")); // 检索参考号
			bean.setAuthorIdentResp(list.get(0).get("AUTHOR_IDENT_RESP")
					.equals("") ? null : list.get(0).get("AUTHOR_IDENT_RESP")); // 授权码
			bean.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent()); // 设置受卡机终端标识码
			String caic = DbHelp.getCardAcceptIdentcode();
			bean.setCardAcceptIdentcode(PackUtil
					.fillField(caic, 15, false, " ")); // 设置受卡方标识码
			bean.setCurrencyTransCode(list.get(0).get("CURRENCY_TRANS_CODE")); // 交易货币代码（人民币的货币代码）
//			bean.setPinData(list.get(0).get("PIN_DATA").equals("") ? null
//					: list.get(0).get("PIN_DATA"));// 个人标识码数据
//			bean.setSecurityRelatedControl(list.get(0).get(
//					"SECURITY_RELATED_CONTROL")); // 安全控制信息
			//bean.setSecurityRelatedControl("2600000000000000");
			bean.setPinData(null);
			bean.setSecurityRelatedControl(null);
			bean.setOrganId(list.get(0).get("ORGAN_ID"));// 发券机构标识
			bean.setReservedPrivate1(list.get(0).get("RESERVED_PRIVATE1")); // 应用标识
			bean.setSwapCode(list.get(0).get("SWAP_CODE_1").equals("") ? null
					: list.get(0).get("SWAP_CODE_1"));// 兑换码
			bean.setReservedPrivate2("23" + DbHelp.getBatchNum() + "000"); // 设置批次号
			StringBuilder sb = new StringBuilder();
			sb.append(list.get(0).get("RESERVED_PRIVATE2").substring(2, 8)); // 原批次号
			sb.append(list.get(0).get("SYS_TRACE_AUDIT_NUM")); // 原流水号
			bean.setOriginalMessage(sb.toString());// 原始信息域
			bean.setMessageAuthentCode("11111111"); // mac

			// 将交易放入临时流水表中
			handle.insertObject("TBL_TMPFlOW", bean);

			handle.update("TBL_TMPFlOW", new String[] { "USER_ID" },
					new String[] { Controller.session.get("userID") + "" },
					"SYS_TRACE_AUDIT_NUM = ?",
					new String[] { bean.getSysTraceAuditNum() });

			// 打包并发送报文
			IsoCommHandler comm = new IsoCommHandler();
			ConsumeRepealBean respbean = (ConsumeRepealBean) comm
					.sendIsoMsg(bean);
			if (comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT) {
				handle.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // 交易失败，将临时流水表中数据转存到冲正表
				handle.update("TBL_REVERSAL", new String[] { "FLUSH_OCUNT",
						"FLUSH_RESULT" }, new String[] { "0", "-1" },
						"SYS_TRACE_AUDIT_NUM = ?",
						new String[] { bean.getSysTraceAuditNum() });
			}
			/**MAC校验失败 **/
			if (comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED) {
				handle.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // 交易失败，将临时流水表中数据转存到冲正表
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

				if (StringFormat.isNull(respcode).equalsIgnoreCase("00")) { // 响应成功
					this.resultMsg = "撤销成功";
					// handle.delete("TBL_FlOW", "SYS_TRACE_AUDIT_NUM", new
					// String[]{respbean.getSysTraceAuditNum()});
				} else { // 响应失败
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
				resultMsg = "请检查网络连接！";
				result.setMessage("请检查网络连接！");
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

	// 优惠券兑换撤销
	private void sendRequestCoupon(String num, String vou) {
		// 从流水表里取出 （兑换撤销）
		DbHandle handle = new DbHandle();

		List<Map<String, String>> list = handle.select("TBL_FlOW",
				DbInfoImpl.FieldNames[1],
				"TRACK2 =? and SYS_TRACE_AUDIT_NUM=?",
				new String[] { num, vou }, null, null, null);
		if (list.size() != 0) {
			CouponsConvertRepealBean bean = new CouponsConvertRepealBean();
			bean.setMsgId("0200");
			bean.setTrack2(num);// 设置卡号
			bean.setProcessCode("021000"); // 设置交易处理码
			bean.setTransAmount(list.get(0).get("TRANS_AMOUNT")); // 交易金额
			bean.setDiscountAmount(list.get(0).get("DISCOUNT_AMOUNT")); // 附加金额
			bean.setSysTraceAuditNum(TransSequence.getSysTraceAuditNum()); // 受卡方系统跟踪号（本次交易的流水号）
			bean.setRetReferNum(list.get(0).get("RET_REFER_NUM_1")); // 检索参考号
			bean.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent()); // 设置受卡机终端标识码
			String caic = DbHelp.getCardAcceptIdentcode();
			bean.setCardAcceptIdentcode(PackUtil
					.fillField(caic, 15, false, " ")); // 设置受卡方标识码
			bean.setOrganId(list.get(0).get("ORGAN_ID"));// 发券机构标识
			bean.setSwapCode(list.get(0).get("SWAP_CODE_1").equals("") ? null
					: list.get(0).get("SWAP_CODE_1"));// 兑换码
			bean.setCouponsAdvertId(list.get(0).get("COUPONS_ADVERT_ID"));// 发券方标识
			bean.setReservedPrivate2("63" + DbHelp.getBatchNum() + "000"); // 设置批次号
			StringBuilder sb = new StringBuilder();
			sb.append(list.get(0).get("RESERVED_PRIVATE2").substring(2, 8)); // 原批次号
			sb.append(list.get(0).get("SYS_TRACE_AUDIT_NUM")); // 原流水号
			bean.setOriginalMessage(sb.toString());// 原始信息域
			bean.setMessageAuthentCode("11111111"); // mac

			// 将交易放入临时流水表中
			handle.insertObject("TBL_TMPFlOW", bean);

			handle.update("TBL_TMPFlOW", new String[] { "USER_ID" },
					new String[] { Controller.session.get("userID") + "" },
					"SYS_TRACE_AUDIT_NUM = ?",
					new String[] { bean.getSysTraceAuditNum() });

			// 打包并发送报文
			IsoCommHandler comm = new IsoCommHandler();
			CouponsConvertRepealBean respbean = (CouponsConvertRepealBean) comm
					.sendIsoMsg(bean);
			if (comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT) {
				handle.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // 交易失败，将临时流水表中数据转存到冲正表
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

				if (StringFormat.isNull(respcode).equalsIgnoreCase("00")) { // 响应成功
					this.resultMsg = "撤销成功";
					// handle.delete("TBL_FlOW", "SYS_TRACE_AUDIT_NUM", new
					// String[]{respbean.getSysTraceAuditNum()});
				} else { // 响应失败
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
				resultMsg = "请检查网络连接！";
				result.setMessage("请检查网络连接！");
				bundle.putSerializable("ResultRespBean", result);
				response.setBundle(bundle);
			}

		}
	}

	// 积分撤销交易发送请求
	private void sendRequestScore(String cardNum, String vouchNum) {
		// 把需要的字段从流水表中找出 （积分撤销）
		DbHandle handle = new DbHandle();
		Map<String, String> map = handle.selectOneRecord("TBL_FlOW",
				DbInfoImpl.FieldNames[1],
				"TRACK2 =? and SYS_TRACE_AUDIT_NUM=?", new String[] { cardNum,
						vouchNum }, null, null, null);
		if (map.size() != 0) {
			ScoreCancelBean bean = new ScoreCancelBean();
			bean.setTrack2(cardNum);
			bean.setMsgId("0200"); // 设置交易类型
			bean.setProcessCode("300000"); // 设置交易处理码
			bean.setTransAmount(map.get("TRANS_AMOUNT_1")); // 交易金额
			bean.setSysTraceAuditNum(TransSequence.getSysTraceAuditNum()); // 受卡方系统跟踪号（本次交易的流水号）
			bean.setDateExpired(map.get("DATE_EXPIRED").equals("") ? null : map
					.get("DATE_EXPIRED")); // 卡有效期
			bean.setServiceEntryMode(map.get("SERVICE_ENTRY_MODE")); // 服务点输入方式码
			bean.setServiceConditionMode("00"); // 服务点条件码
			bean.setServicePINCaptureCode(map.get("SERVICE_PIN_CAPTURE_CODE")
					.equals("") ? null : map.get("SERVICE_PIN_CAPTURE_CODE")); // 服务点PIN获取码
			bean.setRetReferNum(map.get("RET_REFER_NUM_1")); // 检索参考号
			bean.setCardAcceptTermIdent(map.get("CARD_ACCEPT_TERM_IDENT")); // 设置受卡机终端标识码
			bean.setCardAcceptIdentcode(map.get("CARD_ACCEPT_IDENTCODE")); // 设置受卡方标识码
			bean.setCurrencyTransCode(map.get("CURRENCY_TRANS_CODE")); // 交易货币代码（人民币的货币代码）
			bean.setOrganId(map.get("ORGAN_ID"));// 发券机构标识
			bean.setPinData(map.get("PIN_DATA").equals("") ? null : map
					.get("PIN_DATA"));// 个人标识码数据
			bean.setSecurityRelatedControl(map.get("SECURITY_RELATED_CONTROL")
					.equals("") ? null : map.get("SECURITY_RELATED_CONTROL")); // 安全控制信息
			bean.setCouponsAdvertId(map.get("COUPONS_ADVERT_ID"));// 发卷对象标识

			bean.setReservedPrivate2("53" + DbHelp.getBatchNum() + "000"); // 设置批次号
			StringBuilder sb = new StringBuilder();
			sb.append(map.get("RESERVED_PRIVATE2").substring(2, 8)); // 原批次号
			sb.append(map.get("SYS_TRACE_AUDIT_NUM")); // 原流水号
			bean.setOriginalMessage(sb.toString());// 原始信息域
			bean.setMessageAuthentCode("11111111"); // mac

			// 将交易放入临时流水表中
			handle.insertObject("TBL_TMPFlOW", bean);
			handle.update("TBL_TMPFlOW", new String[] { "USER_ID" },
					new String[] { Controller.session.get("userID") + "" },
					"SYS_TRACE_AUDIT_NUM = ?",
					new String[] { bean.getSysTraceAuditNum() });
			// 打包并发送报文
			IsoCommHandler comm = new IsoCommHandler();
			ScoreCancelBean respbean = (ScoreCancelBean) comm.sendIsoMsg(bean);

			if (comm.errorCode == ProtocolRespCode.PROTOCOL_READ_TIMEOUT) {
				bError = true;
				response.setError(true);
				Bundle bundle = new Bundle();
				ResultRespBean result = new ResultRespBean();
				result.setMessage("未收到应答！");
				bundle.putSerializable("ResultRespBean", result);
				response.setBundle(bundle);
				handle.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // 交易失败，将临时流水表中数据转存到冲正表
				handle.update("TBL_REVERSAL", new String[] { "FLUSH_OCUNT",
						"FLUSH_RESULT" }, new String[] { "0", "-1" },
						"SYS_TRACE_AUDIT_NUM = ?",
						new String[] { bean.getSysTraceAuditNum() });
			}
			/**MAC校验失败 **/
			if (comm.errorCode == ProtocolRespCode.MAC_CHECK_FAILED) {
				handle.transferTable("TBL_TMPFlOW", "TBL_REVERSAL"); // 交易失败，将临时流水表中数据转存到冲正表
				handle.update("TBL_REVERSAL", new String[]{"FLUSH_OCUNT", "FLUSH_RESULT"}, new String[]{"0","-1"}, "SYS_TRACE_AUDIT_NUM = ?", new String[] { bean.getSysTraceAuditNum() });
			} 
			if (respbean != null) {
				resultMsg = respbean.getReservedPrivate4();
				handle.updateRespObject("TBL_TMPFlOW", bean,
						"SYS_TRACE_AUDIT_NUM = ?", // 更新临时流水表
						new String[] { bean.getSysTraceAuditNum() });

				handle.transferTable("TBL_TMPFlOW", "TBL_FlOW"); // 交易成功将临时流水表中数据转存到流水表
				if (respbean.getRespCode().equals("00")) {
					resultMsg = "撤销成功";
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
				resultMsg = "请检查网络连接！";
				result.setMessage("请检查网络连接！");
				bundle.putSerializable("ResultRespBean", result);
				response.setBundle(bundle);
			}

		}
	}

	/**
	 * 查询应用列表
	 * 
	 * @param start
	 *            起始记录
	 * @param end
	 *            结束记录
	 * @return AppListRespBean结果集
	 */
	/*
	 * private AppListRespBean[] queryAppList(String begin, String end,
	 * ReadCardReqBean readcard) { AppListRespBean[] appls = null; try {
	 * 
	 * LoadAppListBean loadAppListBean = new LoadAppListBean();
	 * loadAppListBean.setMsgId("0860");
	 * loadAppListBean.setCardNo(readcard.getCardNum());// 设置卡号
	 * loadAppListBean.setCardSequenceNum(GlobalParameters.g_map_para
	 * .get("cardSequenceNum"));// 应用类别 (支付类应用000)
	 * loadAppListBean.setCardAcceptTermIdent(GlobalParameters.g_map_para
	 * .get("cardAcceptTermIdent"));// 受卡机终端标识码(终端号)
	 * loadAppListBean.setCardAcceptIdentcode(GlobalParameters.g_map_para
	 * .get("cardAcceptIdentcode"));// 受卡方标识码
	 * 
	 * if (begin.length() < 2) { begin = "0" + begin; } if (end.length() < 2) {
	 * end = "0" + end; }
	 * 
	 * loadAppListBean.setReservedPrivate1(begin + end); IsoCommHandler comm =
	 * new IsoCommHandler(); LoadAppListBean bean = (LoadAppListBean) comm
	 * .sendIsoMsg(loadAppListBean);// 打包并发送报文
	 * 
	 * if (bean != null) { String respcode = bean.getRespCode(); String
	 * reservedPrivate1 = bean.getReservedPrivate1();
	 * 
	 * if (StringFormat.isNull(respcode).equalsIgnoreCase("00")) {// 响应成功 String
	 * totalCount = reservedPrivate1.substring(1, 4);// 应用总数量 sTotalSize =
	 * totalCount; int iCurrentCount = Integer.valueOf(reservedPrivate1
	 * .substring(4, 6));// 当前下载的应用数量
	 * 
	 * if (appls == null) {// 第一次请求 appls = new
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
	 * appbean.setAppIdent(new String(tmp));// 应用标识
	 * 
	 * tmp = new byte[2]; System.arraycopy(record, 4, tmp, 0, 2);
	 * appbean.setAppType(new String(tmp));// 应用类型
	 * 
	 * tmp = new byte[50]; System.arraycopy(record, 6, tmp, 0, 50);
	 * appbean.setAppName(new String(tmp, "GBK"));// 应用名称
	 * 
	 * 
	 * tmp = new byte[3]; System.arraycopy( record, 56, tmp, 0, 3);
	 * 
	 * appbean.setDiscount("000");// 折扣
	 * 
	 * appls[i] = appbean; }
	 * 
	 * } else {// 响应失败 this.bError = true; this.respcode = respcode; resultMsg =
	 * "应用列表下载失败！"; } } else { this.bError = true; this.respcode = respcode;
	 * resultMsg = "请检查网络连接！"; } } catch (Exception ex) { ex.printStackTrace();
	 * }
	 * 
	 * return appls; }
	 */

	/**
	 * 查询积分列表
	 * 
	 * @param start
	 *            起始记录
	 * @param end
	 *            结束记录
	 * @return CreditQueryResBean结果集
	 */
	@Deprecated
	private CreditQueryResBean[] queryCreditList(String begin, String end,
			ReadCardReqBean readcard) {
		CreditQueryResBean[] creditList = null;
		try {

			PointsQueryBean credits = new PointsQueryBean();

			credits.setMsgId("0200"); // 设置交易类型

			credits.setTrack2(readcard.getCardNum());// 设置卡号

			credits.setProcessCode("110001");// 设置处理码

			credits.setSysTraceAuditNum(TransSequence.getSysTraceAuditNum()); // 受卡方系统跟踪号

			credits.setDateExpired(null);// 卡有效期

			credits.setServicePINCaptureCode(null);// 服务点pin获取码

			credits.setPinData(null);// 个人标识码数据

			credits.setSecurityRelatedControl(null);// 安全控制信息

			credits.setServiceEntryMode("005");// 服务点输入方式码

			credits.setServiceConditionMode("00");// 服务点条件码

			credits.setOrganId("80000000");// 发券机构标识

			credits.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// 受卡机终端标识码41
			credits.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// 受卡方标识码42

			credits.setCurrencyTransCode("156");// 交易货币代码

			credits.setReservedPrivate2("54" + DbHelp.getBatchNum() + "000");// 批次号

			credits.setMessageAuthentCode("11111111");

			if (begin.length() < 2) {
				begin = "0" + begin;
			}
			if (end.length() < 2) {
				end = "0" + end;
			}

			credits.setReservedPrivate3(begin + end);
			IsoCommHandler comm = new IsoCommHandler();
			PointsQueryBean bean = (PointsQueryBean) comm.sendIsoMsg(credits);// 打包并发送报文

			if (bean != null) {
				String respcode = bean.getRespCode();
				String reservedPrivate3 = bean.getReservedPrivate3();
				resultMsg = bean.getReservedPrivate4();
				if (StringFormat.isNull(respcode).equalsIgnoreCase("00")) {// 响应成功
					String totalCount = reservedPrivate3.substring(1, 4);// 应用总数量
					sTotalSize = totalCount;
					int iCurrentCount = Integer.valueOf(reservedPrivate3
							.substring(4, 6));// 当前下载的应用数量

					if (creditList == null) {// 第一次请求
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
						creditBean.setIssId(new String(tmp));// 发卷对象标识

						tmp = new byte[12];
						System.arraycopy(record, 11, tmp, 0, 12);
						creditBean.setCredits(new String(tmp));// 积分余额

						tmp = new byte[12];
						System.arraycopy(record, 23, tmp, 0, 12);
						creditBean.setMoney(new String(tmp));// 可兑换余额

						tmp = new byte[40];
						System.arraycopy(record, 35, tmp, 0, 40);
						creditBean.setIssCshort(new String(tmp, "GBK"));// 发卷机构名称(简称)

						creditList[i] = creditBean;
					}

				} else {// 响应失败
					this.bError = true;
					this.respcode = respcode;

				}
			} else {
				this.bError = true;
				this.respcode = respcode;
				resultMsg = "请检查网络连接！";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return creditList;
	}

	// 请求优惠券列表(优惠券申领)
	private void doCouponsRequest2(ReadCardReqBean readcard) {

		CouponsReqBean reqBean = new CouponsReqBean();
		reqBean.setMerchantCode(DbHelp.getCardAcceptIdentcode());// 设置商户编号
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
					result.setMessage("此卡号未绑定！");
					bundle.putSerializable("ResultRespBean", result);
				}
			}
			response.setError(false);
			ArrayList<ResponseBean> arrayList = new ArrayList<ResponseBean>(
					list);
			bundle.putSerializable("gridView", arrayList);
		} else {
			ResponseBean res = new ResponseBean();
			res.setMessage("请检查网络连接");
			bError = true;
			resultMsg = "请检查网络连接";
			response.setError(true);
			response.setData(res);
		}
	}

	// 请求优惠券列表
	private void doCouponsRequest(ReadCardReqBean readcard) {

		CouponsApplyReqBean reqBean = new CouponsApplyReqBean();
		reqBean.setMerchantCode(DbHelp.getCardAcceptIdentcode());// 设置商户编号
		reqBean.setBaseCardNo(readcard.getCardNum());// 设置卡号

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
			res.setMessage("请检查网络连接");
			bError = true;
			resultMsg = "请检查网络连接";
			response.setError(true);
			response.setData(res);
		}
	}
}
