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
	private String respcode = "";// 用于存放响应码
	private boolean bError = false;// 用于标记本次请求是否产生了Error
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

		// 如果是消费撤销和积分撤销
		if (Controller.session.get("type") != null
				&& (Controller.session.get("type").equals("consume") || Controller.session
						.get("type").equals("credit"))) {
			// 从界面上获得主管的密码
			AdminPassReqBean bean = (AdminPassReqBean) getRequest().getData();
			String pass = bean.getPassWord();
			DbHandle handle = new DbHandle();
			Map<String, String> map = handle.rawQueryOneRecord(
					"select PASSWORD from TBL_ADMIN where PASSWORD=?  and LIMITS = 2",
					new String[] { pass });
			if (map.size() != 0) {
				response.setTargetActivityID(ActivityID.map
						.get("ACTIVITY_ID_VOUCHERS"));
			} else { // 密码错误 返回当前界面
				response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
				response.setError(true);
			}
		} else if (Controller.session.get("type") != null // 如果是支付退货
				&& Controller.session.get("type").equals("backGoods")) {
			// 从界面上获得主管的密码
			AdminPassReqBean bean = (AdminPassReqBean) getRequest().getData();
			String pass = bean.getPassWord();
			DbHandle handle = new DbHandle();
			Map<String, String> map = handle.rawQueryOneRecord(
					"select PASSWORD from TBL_ADMIN where PASSWORD=?  and LIMITS = 2",
					new String[] { pass });
			if (map.size() != 0) {
				response.setTargetActivityID(ActivityID.map
						.get("ACTIVITY_ID_SwipCardActivity"));
			} else { // 密码错误 返回当前界面
				response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
				response.setError(true);
			}
		} else if (Controller.session.get("type") != null // 如果是支付查询
				&& Controller.session.get("type").equals("consumeCheck")) {
			// 从界面上获得卡的密码
			AdminPassReqBean bean = (AdminPassReqBean) getRequest().getData();
			String pass = bean.getPassWord();
			byte[] btpass = StrUtil.HexStringToByte(pass, "");
			QueryBalanceBean queryBalance = new QueryBalanceBean();
			queryBalance.setMsgId("0200");// 余额查询
			queryBalance
					.setTrack2(Controller.session.get("CardNum").toString());// 机卡卡号
			queryBalance.setProcessCode("310000"); // 交易处理码
			queryBalance.setSysTraceAuditNum(TransSequence
					.getSysTraceAuditNum());// 受卡方系统跟踪号
			queryBalance.setDateExpired(null); // 卡有效期
			queryBalance.setServiceEntryMode("071"); // 服务点输入方式码
			queryBalance.setServiceConditionMode("00"); // 服务点条件码
			queryBalance.setServicePINCaptureCode("06"); // 服务点PIN获取码
			queryBalance.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// 受卡机终端标识码41
			queryBalance.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// 受卡方标识码42
			queryBalance.setCurrencyTransCode("156"); // 交易货币代码
			
			try{
//				queryBalance.setPinData(new String(btpass, "ISO-8859-1"));// 个人标识码数据
				queryBalance.setPinData(pass);// 个人标识码数据
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			queryBalance.setSecurityRelatedControl("2600000000000000"); // 安全控制信息
			queryBalance.setReservedPrivate1(Controller.session.get("APP_ID")
					.toString()); // 应用标识
			queryBalance.setReservedPrivate2("01"+DbHelp.getBatchNum()+"000"); // 批次号
			queryBalance.setMessageAuthentCode("11111111"); // mac

			log.info("----------发送begin----------");
			log.info("当前用户：");
			log.info("交互类型：");
			log.info("交互时间：");
			log.info("商户名称：");
			log.info("发送报文：");
			log.info("-----------发送end---------");

			// 发送请求
			IsoCommHandler comm = new IsoCommHandler();
			QueryBalanceBean qbb = (QueryBalanceBean) comm
					.sendIsoMsg(queryBalance);

			log.info("----------回应begin----------");
			log.info("当前用户：");
			log.info("交互类型：");
			log.info("交互时间：");
			log.info("商户名称：");
			log.info("发送报文：");
			log.info("-----------回应end---------");
			// SimpleDateFormat format = new SimpleDateFormat("yyMMdd_HHmmss");
			// String nowStr = format.format(new Date());

			if (qbb != null) {

				log.info("##################" + qbb.getRespCode());

				respcode = qbb.getRespCode();
				message = qbb.getReservedPrivate4();
				if (StringFormat.isNull(respcode).equalsIgnoreCase("00")) {// 响应成功
					String balanceAmount = qbb.getBalanceAmount();

					System.out.println("应用查询余额为：" + balanceAmount);
					log.info("应用查询余额为：" + balanceAmount);

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
					b.setMessage("请检查网络连接！");
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
