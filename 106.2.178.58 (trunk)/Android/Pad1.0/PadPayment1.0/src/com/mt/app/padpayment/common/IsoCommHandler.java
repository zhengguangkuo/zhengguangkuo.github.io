package com.mt.app.padpayment.common;

import java.lang.reflect.Method;

import android.util.Log;

import com.mt.android.message.iso.IsoHandle;
import com.mt.android.message.iso.util.StrUtil;
import com.mt.android.message.mdo.MessageBody;
import com.mt.android.message.mdo.MessageHeader;
import com.mt.android.protocol.ProtocolTask;
import com.mt.android.protocol.manager.ExecutorManager;
import com.mt.android.protocol.util.ProtocolRespCode;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.sys.util.StringUtil;
import com.mt.app.padpayment.message.iso.trans.SignInBean;
import com.mt.app.padpayment.tools.SystemUtil;

public class IsoCommHandler {  
	public int errorCode = 0;
	public  MessageBody sendIsoMsg(MessageBody body) {
		MessageBody respbody = null;
		IsoHandle isoHandle = new IsoHandle();
		MessageHeader header = new MessageHeader();
		byte[] bLen = new byte[2];
		byte[] bRespBuf = null;
		boolean macflg = true;
		int iLen = 0;

		macflg = hasMac(body);
		byte[] isoinfo = isoHandle.Pack(header, body);

		String aa = StrUtil.ByteToHexString(isoinfo, " ");
		if (macflg) {
			byte[] macVal = SystemUtil.setMacVal(isoinfo);
			System.arraycopy(macVal, 0, isoinfo, isoinfo.length-8, 8);
		}

		ProtocolTask task = new ProtocolTask();
		task.setProtocolId("TCP_SHORT_1");
		task.setReqMsg(isoinfo);
		String reqHex = StrUtil.ByteToHexString(isoinfo, " ");
		System.out.println("请求报文:" + reqHex);
		ExecutorManager.dotask(task);

		try{
			respbody = (MessageBody)body.getClass().newInstance();		
			this.errorCode = task.getRespCode();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		if (task.getRespCode() < 0) {
			return null;
		}

		if(macflg){
			if(!SystemUtil.isAvailable(task.getRespMsg())){
				task.setRespCode(ProtocolRespCode.MAC_CHECK_FAILED);
				task.setErrorMsg("MAC校验失败");
				this.errorCode = ProtocolRespCode.MAC_CHECK_FAILED;
				return null;
			}
		}
		
		System.arraycopy(task.getRespMsg(), 0, bLen, 0, 2);
		iLen = StringUtil.byteToInt(bLen);
		bRespBuf = new byte[iLen-11];
		String ttt = StrUtil.ByteToHexString(task.getRespMsg(), " ");
		Log.i("receive message", ttt);
		System.arraycopy(task.getRespMsg(), 13, bRespBuf, 0, iLen - 11);
		ttt = StrUtil.ByteToHexString(bRespBuf, " ");
		Log.i("vaild message", ttt);
		
		
		
		isoHandle.Unpack(bRespBuf);
		
		
		
		isoHandle.Iso2Obj(respbody);
		
		

		return respbody;
	}
	private  boolean hasMac(MessageBody body) {
		Class cla = body.getClass();
		Method[] ma = cla.getDeclaredMethods();// 获取所有声明的方法数组

		for (int i = 0; i < ma.length; i++) {
			Method method = ma[i];

			if (method.getName().equalsIgnoreCase("setMessageAuthentCode")) {
				try {
					method.invoke(body, "11111111");// 为Mac字段域设置一个默认值
					return true;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return false;
	}
}
