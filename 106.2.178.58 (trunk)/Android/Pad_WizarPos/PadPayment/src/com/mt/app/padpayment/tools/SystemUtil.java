package com.mt.app.padpayment.tools;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.mt.android.message.iso.util.StrUtil;
import com.mt.app.padpayment.common.DES;
import com.wizarpos.apidemo.activity.PinPadDriver;

public class SystemUtil {
	private static long lastClickTime; // �ϴε��ʱ��

	public static boolean isAvailable(byte[] srcmsg){
    	byte[] btResult = setMacVal(srcmsg);
    	String sCulMacResult = StrUtil.ByteToHexString(btResult, "");
        byte[] srcMacVal = new byte[8];
        System.arraycopy(srcmsg, srcmsg.length-8, srcMacVal, 0, 8);
    	String macCheck = StrUtil.ByteToHexString(srcMacVal, "");
    	
    	boolean bResult = false;
    	
    	if(sCulMacResult.equalsIgnoreCase(macCheck)){
    		bResult = true;
    	}
    	
    	return bResult;
	}
	/**
	 * 
	 * @param srcmsg ��Ҫ����Mac����ı���
	 * @return Mac������
	 */
	
	public static byte[] setMacVal(byte[] srcmsg){
		byte[] btsrc = new byte[srcmsg.length - 8 - 13];
		
		System.arraycopy(srcmsg, 13, btsrc, 0, btsrc.length);
		String data = StrUtil.ByteToHexString(btsrc, "");
		
		
		//�0
		if(data.length() % 16 != 0){
		   int count =  data.length() % 16;
		   count = 16 - count;
		   
		   for(int z = 0; z < count; z ++){
			   data = data+"0";   
		   }
		}
		
		List<String> list = new ArrayList<String>();
		
		for(int i = 0; i < data.length(); i = i + 16){	
			String temp = data.substring(i, i + 16);
			list.add(temp);
		}
		
		String[] dataArr = new String[list.size()];
	    list.toArray(dataArr);
		
		String s1 = dataArr[0];
		String vector = "0000000000000000";
		String temp = DES.xOr(s1, vector);
		
		for(int k = 1; k < dataArr.length; k++){
			temp = DES.xOr(temp, dataArr[k]);
		}
		
		byte[] arryPlainText = StrUtil.HexStringToByte(temp, "");
		byte[] btyResult = new byte[8];
		PinPadDriver driver = new PinPadDriver();
		driver.PinPadSelectKey(1);
		int iRet = driver.PinpadEncryptString(arryPlainText, arryPlainText.length, btyResult);
		
		return btyResult;
	}
	public static byte[] genMac(byte[] macKey, byte[] mainKey, byte[] macData) {
		int iOff, iPos, iLen, i;
		byte[] sMacTmp = new byte[100];
		byte[] sTmp = new byte[8];
		byte[] sKey1 = new byte[8];
		byte[] sKey2 = new byte[8];
		byte[] sMacData = null;
		int iKeyLen = 16;

		iLen = (8 - (macData.length % 8)) + macData.length;
		sMacData = new byte[iLen];
		System.arraycopy(macData, 0, sMacData, 0, macData.length);

		/* sMacData����ӦΪ8�ı���, ����ĩβ��0x00���� */
		for (i = 0; i < (iLen - macData.length); i++) {
			sMacData[iLen - i - 1] = 0x00;
		}

		iKeyLen = macKey.length;

		if (iKeyLen > 16) {

			byte[] tmpMacKey = new byte[16];
			sKey1 = desdecrept(macKey, mainKey);// ʹ������Կ��mackey���н���

			System.arraycopy(macKey, 16, tmpMacKey, 0, 16);
			sKey2 = desdecrept(tmpMacKey, mainKey);// ʹ������Կ��mackey���н���

			iOff = 0;

			for (iPos = 0; iPos < iLen; iPos += iOff) {
				iOff = ((iLen - iPos) >= 8) ? 8 : iLen - iPos;

				for (i = 0; i < iOff; i++) {
					sMacTmp[i] ^= macData[i + iPos];
				}

				sTmp = desencrept(sMacTmp, sKey1);// ����
				System.arraycopy(sTmp, 0, sMacTmp, 0, 8);
			}

			sTmp = desdecrept(sMacTmp, sKey2);// ����
			sMacTmp = desencrept(sTmp, sKey1);// ����

		} else {
			sKey1 = desdecrept(macKey, mainKey);// ʹ������Կ��mackey���н���

			for (iPos = 0; iPos < iLen; iPos += iOff) {
				iOff = ((iLen - iPos) >= 8) ? 8 : iLen - iPos;

				for (i = 0; i < iOff; i++) {
					sMacTmp[i] ^= macData[i + iPos];
				}

				sTmp = desencrept(sMacTmp, sKey1);// ����
				System.arraycopy(sTmp, 0, sMacTmp, 0, 8);

			}
		}
		return sMacTmp;
	}

	private static byte[] desencrept(byte[] srcbyte, byte[] deskey) {

		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(deskey);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
			return cipher.doFinal(srcbyte);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	private static byte[] desdecrept(byte[] src, byte[] deskey) {

		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(deskey);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
			return cipher.doFinal(src);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/**
	 * ��ֹ����������ж����ε������Ƿ����500����
	 * @return
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 500) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
}
