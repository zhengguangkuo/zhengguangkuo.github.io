package com.mt.app.padpayment.command;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mt.android.db.DbHandle;
import com.mt.android.message.iso.util.StrUtil;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.application.PadPaymentApplication;
import com.mt.app.padpayment.common.DES;
import com.mt.app.padpayment.common.DbHelp;
import com.mt.app.padpayment.common.GlobalParameters;
import com.mt.app.padpayment.common.IsoCommHandler;
import com.mt.app.padpayment.message.iso.trans.SignInBean;
import com.mt.app.padpayment.responsebean.ResultRespBean;
import com.mt.app.padpayment.tools.PackUtil;
import com.mt.app.padpayment.tools.SysConfig;
import com.mt.app.padpayment.tools.SystemUtil;
import com.mt.app.padpayment.tools.TransSequence;
import com.wizarpos.apidemo.activity.PinPadDriver;

public class ToSignInCommand extends AbstractCommand {
	DbHandle dbhandle = new DbHandle();
	TransSequence trans = new TransSequence();
	SystemUtil sysutil = new SystemUtil();
	private static Logger log = Logger.getLogger(ToSignInCommand.class);
	private String respcode = "";// 用于存放响应码
	String auditnum = trans.getSysTraceAuditNum();

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
		/* 获取卡号,交易类型 */
		Response response = new Response();
		try {
			SignInBean signin = signin();
			if (signin != null) {

				respcode = signin.getRespCode();
				if (respcode.equals("00")) {// 响应成功
					respcode = "签到成功";
					dbhandle.delete("TBL_MANAGE", null, null);
					dbhandle.insert("TBL_MANAGE", 
							new String[]{"SYS_TRACE_AUDIT_NUM" , "LOCAL_TRANS_TIME" , "LOCAL_TRANS_DATE" ,
						    "ACQ_INST_IDENT_CODE" , "RET_REFER_NUM" , "RESP_CODE" , "CARD_ACCEPT_TERM_IDENT",
						    "CARD_ACCEPT_IDENTCODE" , "CARD_ACCEPT_LOCAL" , "RESERVED_PRIVATE2"}, 
							new String[]{signin.getSysTraceAuditNum() , signin.getLocalTransTime()
							 , signin.getLocalTransDate() , signin.getAcqInstIdentCode() 
							 , signin.getRetReferNum() , signin.getRespCode() 
							 , signin.getCardAcceptTermIdent() , signin.getCardAcceptIdentcode()
							 , signin.getCardAcceptLocal() , signin.getReservedPrivate2()});
					
				} else {
					if (!signin.getReservedPrivate4().equals("")) {
						respcode = signin.getReservedPrivate4();
					} else {
						Map<String, String> map = dbhandle
								.rawQueryOneRecord(
										"select MESSAGE from TBL_RESPONSE_CODE where RESP_CODE = ?",
										new String[] { respcode });
						if (map.get("MESSAGE") != null) { // 对应的列数，这里取查询结果第3列
							respcode = map.get("MESSAGE");
						}
						respcode = "签到失败";
					}

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
			} else {
 				response.setError(true);
				Bundle bundle = new Bundle();
				ResultRespBean bean = new ResultRespBean();
				bean.setMessage("签到失败");
				bundle.putSerializable("ResultRespBean", bean);
				response.setBundle(bundle);
				setResponse(response);
			}

		} catch (Exception e) {
			response.setError(true);
			Bundle bundle = new Bundle();
			ResultRespBean bean = new ResultRespBean();
			bean.setMessage("签到失败");
			bundle.putSerializable("ResultRespBean", bean);
			response.setBundle(bundle);
			setResponse(response);
			e.printStackTrace();
		}
	}
	
	private void parseUserKey(PinPadDriver driver ,String[] arrayStr){
		
		String MAC_KEY , PIN_KEY;
		String mCheck , pCheck ;
		
		for(String keyBean2 : arrayStr){
			
			if(keyBean2.substring(0, 2).equals("01")){
				MAC_KEY = keyBean2.substring(2, 34);     //16位密钥密文
				mCheck = keyBean2.substring(34, keyBean2.length());   //8位校验值
				int r1 = driver.PinPadSelectKey(1);
				byte[] macKEY = StrUtil.HexStringToByte(MAC_KEY, "");
				int r2 = driver.PinpadUpdateUserKey( 1 , macKEY , macKEY.length);
				//校验
				int r3 = driver.PinPadSelectKey(1);
				byte[] byt = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
				byte[] result = new byte[8];
				int r4 = driver.PinpadEncryptString(byt, 8 , result);
				String finalRe = StrUtil.ByteToHexString(result, "");
				if(!(finalRe.equals(mCheck))){   //校验失败
					respcode = "签到失败" ;
				}
			}else if(keyBean2.substring(0, 2).equals("02")){
				PIN_KEY = keyBean2.substring(2, 34);     //16位密钥密文
				pCheck = keyBean2.substring(34, keyBean2.length());   //8位校验值
				int r1 = driver.PinPadSelectKey(0);
				byte[] pinKEY = StrUtil.HexStringToByte(PIN_KEY, "");
				int r2 = driver.PinpadUpdateUserKey( 0 , pinKEY , pinKEY.length);
				//校验
				int r3 = driver.PinPadSelectKey(0);
				byte[] byt = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
				byte[] result = new byte[8];
				int r4 = driver.PinpadEncryptString(byt, 8 , result);
				String finalRe = StrUtil.ByteToHexString(result, "");	
				if(!(finalRe.equals(pCheck))){   //校验失败
					respcode = "签到失败" ;
				}
			}
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
	 * 签到
	 * 
	 * @return SignInBean结果集
	 */
	private SignInBean signin() {
		byte[] pinKey = new byte[25];
		byte[] mainKey = new byte[25];
		byte[] transMainKey = new byte[25];
		
		SignInBean signin = new SignInBean();
		signin.setMsgId("0800");
		signin.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// 受卡机终端标识码41
		signin.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// 受卡方标识码42
		String str2 = PackUtil.fillField(auditnum, 6, true, "0");
		signin.setSysTraceAuditNum(str2);// 受卡方系统跟踪号11
		signin.setReservedPrivate2("00"+DbHelp.getBatchNum()+"001");//60自定义域
		SignInBean respBean = new SignInBean();
		IsoCommHandler comm = new IsoCommHandler();
		respBean = (SignInBean) comm.sendIsoMsg(signin); // 打包并发送报文
		if (respBean != null) {
			if (respBean.getRespCode().equals("00")) {
				System.arraycopy(respBean.getReservedPrivate3().getBytes(), 0,
						pinKey, 0, 25);
				// System.arraycopy(respBean.getReservedPrivate3().getBytes(),
				// 25,
				// mainKey, 0, 25);

				if (respBean.getReservedPrivate3().getBytes().length > 75) {
					System.arraycopy(respBean.getReservedPrivate3().getBytes(),
							50, transMainKey, 0, 25);
				}
			} 
		} 
		Controller.signin = false;
		return respBean;
	}
}
