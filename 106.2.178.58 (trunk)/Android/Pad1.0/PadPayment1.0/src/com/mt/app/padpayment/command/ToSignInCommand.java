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
import com.mt.app.padpayment.common.DES;
import com.mt.app.padpayment.common.DbHelp;
import com.mt.app.padpayment.common.IsoCommHandler;
import com.mt.app.padpayment.driver.PinPadDriver;
import com.mt.app.padpayment.message.iso.trans.SignInBean;
import com.mt.app.padpayment.responsebean.ResultRespBean;
import com.mt.app.padpayment.tools.PackUtil;
import com.mt.app.padpayment.tools.SysConfig;
import com.mt.app.padpayment.tools.SystemUtil;
import com.mt.app.padpayment.tools.TransSequence;

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
					
					//获取终端密钥
					PinPadDriver driver = (PinPadDriver) Class.forName((String) Controller.session.get("PinPadDriver")).newInstance();
					
					byte[] arr = signin.getReservedPrivate3().getBytes("ISO-8859-1");
					String TMK    ;
					String tCheck ; 
					String key = StrUtil.ByteToHexString(arr, "");
					if(key.length() == 150){			// 表示此硬件设备第一次签到，需要向键盘中灌三个密钥:tmk(用于解密pik和mak的密钥), pik(用于加密密码), mak(用于加密报文)
						String[] keysArray = new String[]{key.substring(0, 50) , key.substring(50, 100) , key.substring(100, key.length()) };
						for(String keyBea1 : keysArray){
							if(keyBea1.substring(0, 2).equals("00")){		// 00表示tmk密钥。。接口文档 -- 终端密钥的域定义
								TMK = keyBea1.substring(2, 34);    //16位密钥密文
								tCheck = keyBea1.substring(34, keyBea1.length());   //8位校验值
								//校验
								Map<String, String> readM = new HashMap<String, String>();
								SysConfig.readProperties(readM);    
								String TMK_appear1 = DES.DES_3(TMK.substring(0, 16), readM.get("cdkey"), 1);  	// 得到明文   1表示解密
								String TMK_appear2 = DES.DES_3(TMK.substring(16, 32), readM.get("cdkey"), 1);	// cdkey是一个明文，用来解密tmk密钥的
								String TMK_appear = TMK_appear1 + TMK_appear2;									// 得到明文
								String jiaoyan_tmk = DES.DES_3("0000000000000000", TMK_appear, 0);				// 0表示加密
								if(jiaoyan_tmk.equals(tCheck)){  //校验成功
//									byte[] arryOldMasterKey = new byte[]{(byte)0x38 ,(byte)0x38 ,(byte)0x38 ,(byte)0x38
//											,(byte)0x38 ,(byte)0x38 ,(byte)0x38 ,(byte)0x38
//											,(byte)0x38 ,(byte)0x38 ,(byte)0x38 ,(byte)0x38
//											,(byte)0x38 ,(byte)0x38 ,(byte)0x38 ,(byte)0x38};
									byte[] arryOldMasterKey = StrUtil.HexStringToByte("38383838383838383838383838383838", ""); // wizarpos旧tmk密钥。
									byte[] arrayNewMasterKey = StrUtil.HexStringToByte(TMK_appear, "") ; 
									String masterKey = StrUtil.ByteToHexString(arrayNewMasterKey, " ");
									// 更新tmk密钥
									int res = driver.pinpadUpdateMasterKey(arryOldMasterKey, arryOldMasterKey.length, arrayNewMasterKey , arrayNewMasterKey.length);
									if(res == 0){
										SysConfig.writeProperties("masterKey", masterKey);
										
										Log.i("info", "-----------------------------> 存储的 新的tmk的值为 ：" + masterKey );
										
										parseUserKey(driver, keysArray);
									
									}
								}else{  //校验失败
									respcode = "签到失败" ;
								}
							}
						}
					}else if(key.length() == 100){ // 表示普通签到，需要向键盘中灌二个密钥:pik(用于加密密码), mak(用于加密报文)
						String[] keyArray2 = new String[]{key.substring(0, 50) , key.substring(50, key.length())};
						parseUserKey(driver, keyArray2);
					}
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
			
			if(keyBean2.substring(0, 2).equals("01")){		// 01表示MAC_KEY。。接口文档 -- 终端密钥的域定义
				MAC_KEY = keyBean2.substring(2, 34);     //16位密钥密文
				mCheck = keyBean2.substring(34, keyBean2.length());   //8位校验值
				int r1 = driver.PinPadSelectKey(1);						// 存放到tmk的1区域
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
				pCheck = keyBean2.substring(34, keyBean2.length());   	//8位校验值
				int r1 = driver.PinPadSelectKey(0);						// 存放到tmk的0区域
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
