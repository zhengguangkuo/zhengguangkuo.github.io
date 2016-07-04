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
	private String respcode = "";// ���ڴ����Ӧ��
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
		/* ��ȡ����,�������� */
		Response response = new Response();
		try {
			SignInBean signin = signin();
			if (signin != null) {

				respcode = signin.getRespCode();
				if (respcode.equals("00")) {// ��Ӧ�ɹ�
					respcode = "ǩ���ɹ�";
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
					
					//��ȡ�ն���Կ
					PinPadDriver driver = (PinPadDriver) Class.forName((String) Controller.session.get("PinPadDriver")).newInstance();
					
					byte[] arr = signin.getReservedPrivate3().getBytes("ISO-8859-1");
					String TMK    ;
					String tCheck ; 
					String key = StrUtil.ByteToHexString(arr, "");
					if(key.length() == 150){			// ��ʾ��Ӳ���豸��һ��ǩ������Ҫ������й�������Կ:tmk(���ڽ���pik��mak����Կ), pik(���ڼ�������), mak(���ڼ��ܱ���)
						String[] keysArray = new String[]{key.substring(0, 50) , key.substring(50, 100) , key.substring(100, key.length()) };
						for(String keyBea1 : keysArray){
							if(keyBea1.substring(0, 2).equals("00")){		// 00��ʾtmk��Կ�����ӿ��ĵ� -- �ն���Կ������
								TMK = keyBea1.substring(2, 34);    //16λ��Կ����
								tCheck = keyBea1.substring(34, keyBea1.length());   //8λУ��ֵ
								//У��
								Map<String, String> readM = new HashMap<String, String>();
								SysConfig.readProperties(readM);    
								String TMK_appear1 = DES.DES_3(TMK.substring(0, 16), readM.get("cdkey"), 1);  	// �õ�����   1��ʾ����
								String TMK_appear2 = DES.DES_3(TMK.substring(16, 32), readM.get("cdkey"), 1);	// cdkey��һ�����ģ���������tmk��Կ��
								String TMK_appear = TMK_appear1 + TMK_appear2;									// �õ�����
								String jiaoyan_tmk = DES.DES_3("0000000000000000", TMK_appear, 0);				// 0��ʾ����
								if(jiaoyan_tmk.equals(tCheck)){  //У��ɹ�
//									byte[] arryOldMasterKey = new byte[]{(byte)0x38 ,(byte)0x38 ,(byte)0x38 ,(byte)0x38
//											,(byte)0x38 ,(byte)0x38 ,(byte)0x38 ,(byte)0x38
//											,(byte)0x38 ,(byte)0x38 ,(byte)0x38 ,(byte)0x38
//											,(byte)0x38 ,(byte)0x38 ,(byte)0x38 ,(byte)0x38};
									byte[] arryOldMasterKey = StrUtil.HexStringToByte("38383838383838383838383838383838", ""); // wizarpos��tmk��Կ��
									byte[] arrayNewMasterKey = StrUtil.HexStringToByte(TMK_appear, "") ; 
									String masterKey = StrUtil.ByteToHexString(arrayNewMasterKey, " ");
									// ����tmk��Կ
									int res = driver.pinpadUpdateMasterKey(arryOldMasterKey, arryOldMasterKey.length, arrayNewMasterKey , arrayNewMasterKey.length);
									if(res == 0){
										SysConfig.writeProperties("masterKey", masterKey);
										
										Log.i("info", "-----------------------------> �洢�� �µ�tmk��ֵΪ ��" + masterKey );
										
										parseUserKey(driver, keysArray);
									
									}
								}else{  //У��ʧ��
									respcode = "ǩ��ʧ��" ;
								}
							}
						}
					}else if(key.length() == 100){ // ��ʾ��ͨǩ������Ҫ������й������Կ:pik(���ڼ�������), mak(���ڼ��ܱ���)
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
						if (map.get("MESSAGE") != null) { // ��Ӧ������������ȡ��ѯ�����3��
							respcode = map.get("MESSAGE");
						}
						respcode = "ǩ��ʧ��";
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
				bean.setMessage("ǩ��ʧ��");
				bundle.putSerializable("ResultRespBean", bean);
				response.setBundle(bundle);
				setResponse(response);
			}

		} catch (Exception e) {
			response.setError(true);
			Bundle bundle = new Bundle();
			ResultRespBean bean = new ResultRespBean();
			bean.setMessage("ǩ��ʧ��");
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
			
			if(keyBean2.substring(0, 2).equals("01")){		// 01��ʾMAC_KEY�����ӿ��ĵ� -- �ն���Կ������
				MAC_KEY = keyBean2.substring(2, 34);     //16λ��Կ����
				mCheck = keyBean2.substring(34, keyBean2.length());   //8λУ��ֵ
				int r1 = driver.PinPadSelectKey(1);						// ��ŵ�tmk��1����
				byte[] macKEY = StrUtil.HexStringToByte(MAC_KEY, "");
				int r2 = driver.PinpadUpdateUserKey( 1 , macKEY , macKEY.length);
				//У��
				int r3 = driver.PinPadSelectKey(1);
				byte[] byt = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
				byte[] result = new byte[8];
				int r4 = driver.PinpadEncryptString(byt, 8 , result);
				String finalRe = StrUtil.ByteToHexString(result, "");
				if(!(finalRe.equals(mCheck))){   //У��ʧ��
					respcode = "ǩ��ʧ��" ;
				}
			}else if(keyBean2.substring(0, 2).equals("02")){
				PIN_KEY = keyBean2.substring(2, 34);     //16λ��Կ����
				pCheck = keyBean2.substring(34, keyBean2.length());   	//8λУ��ֵ
				int r1 = driver.PinPadSelectKey(0);						// ��ŵ�tmk��0����
				byte[] pinKEY = StrUtil.HexStringToByte(PIN_KEY, "");
				int r2 = driver.PinpadUpdateUserKey( 0 , pinKEY , pinKEY.length);
				//У��
				int r3 = driver.PinPadSelectKey(0);
				byte[] byt = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
				byte[] result = new byte[8];
				int r4 = driver.PinpadEncryptString(byt, 8 , result);
				String finalRe = StrUtil.ByteToHexString(result, "");	
				if(!(finalRe.equals(pCheck))){   //У��ʧ��
					respcode = "ǩ��ʧ��" ;
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
	 * ǩ��
	 * 
	 * @return SignInBean�����
	 */
	private SignInBean signin() {
		byte[] pinKey = new byte[25];
		byte[] mainKey = new byte[25];
		byte[] transMainKey = new byte[25];
		
		SignInBean signin = new SignInBean();
		signin.setMsgId("0800");
		signin.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// �ܿ����ն˱�ʶ��41
		signin.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// �ܿ�����ʶ��42
		String str2 = PackUtil.fillField(auditnum, 6, true, "0");
		signin.setSysTraceAuditNum(str2);// �ܿ���ϵͳ���ٺ�11
		signin.setReservedPrivate2("00"+DbHelp.getBatchNum()+"001");//60�Զ�����
		SignInBean respBean = new SignInBean();
		IsoCommHandler comm = new IsoCommHandler();
		respBean = (SignInBean) comm.sendIsoMsg(signin); // ��������ͱ���
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
