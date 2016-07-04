package com.driver.centerm;

import android.os.Looper;
import android.util.Log;

import com.centerm.device.pinpad.GetPINCmd;
import com.centerm.device.pinpad.PinPadCallback;
import com.centerm.device.pinpad.PinPadFactory;
import com.centerm.device.pinpad.device.ExternalPinPad;
import com.centerm.device.pinpad.exception.PinPadException;
import com.centerm.mid.util.HexUtil;
import com.mt.android.message.iso.util.StrUtil;
import com.mt.app.padpayment.driver.PinPadDriver;

public class PinPadDriverForCenterm extends PinPadDriver {

	protected static final String TAG_LOG = PinPadDriverForCenterm.class.getSimpleName() ;
	private static ExternalPinPad pinPad ;
	private boolean flag = true ;
	private int keyIndex = -1 ;
	
	@Override
	public int pinpadOpen() {
		if(pinPad == null) {
			pinPad = (ExternalPinPad) PinPadFactory.getPinPad(0);			
		}
		try {
			pinPad.open();	
		} catch (PinPadException e) {
			e.printStackTrace();
			return -1 ;
		}
		
		return 0 ;
	}

	@Override
	public int pinpadClose() {
		try {
			pinPad.close ();
		} catch (PinPadException e) {
			e.printStackTrace();
			return -1 ;
		}
		return 0;
	}

	@Override
	public int pinpadCaculatePinblock(final byte[] arryASCIICardNumber, int nCardNumberLength, final byte[] arryPinBlockBuffer, int nTimeout_MS) {
		try {
			Looper.prepare() ;
			pinPad.readPin(new PinPadCallback() {
				
				@Override
				public void readPinSuccess(byte[] pinBlock) {
				     Log.d(TAG_LOG, "输入成功// pinBlock = " + HexUtil.bcd2str(pinBlock)) ;
				     for(int i=0; i<pinBlock.length; i++) {
				    	 arryPinBlockBuffer[i] = pinBlock[i] ; 				    	 
				     }
				     Looper.myLooper().quit();
//				     notify() ;
				}
				@Override
				public void readPinTimeOut() {
					Log.d(TAG_LOG, "输入超时");
				}
				@Override
				public void readPinCancel() {
					Log.d(TAG_LOG, "取消输入");
				}
				@Override
				public void readPining(int length, String msg) {
					Log.d(TAG_LOG, ""+"length... "+length);
					Log.d(TAG_LOG, ""+"msg... "+msg);
					//playMyVoice();  //播放按键声音
				}
				@Override
				public void pindPadException(String emsg) {
					Log.d(TAG_LOG, "异常："+emsg);
				}
				@Override
				public GetPINCmd getPinCmd() {
					String cardNo = new String(arryASCIICardNumber) ;
					GetPINCmd gpc = new GetPINCmd();
					gpc.setMin((byte) 0x04);      //密码输入长度控制
					gpc.setMax((byte) 0x08);      //
					cardNo = "0000"+cardNo.substring(3, 15) + "0"; //构造拉卡拉PAN计算方法
					gpc.setCardNo(cardNo.getBytes());
					//gpc.setAmt("250.00"); //显示交易金额   ,使用外置密码键盘时可传入
					return gpc;
				}
				@Override
				public void displayEnd() {
					
				}
				@Override
				public void playKeyPressVoice() {
					
				}
			}) ;
		} catch (PinPadException e) {
			e.printStackTrace() ;
			return -1 ;
		}
		
		Looper.loop() ;
		
		return 0;
	}

	@Override
	public int pinpadUpdateMasterKey(byte[] arryOldMasterKey, int nOldMasterKeyLength, byte[] arryNewMasterKey, int nCipherNewUserKeyLength) {
		try {
			pinPad.downloadTMK(StrUtil.ByteToHexString(arryNewMasterKey, ""));
		} catch (PinPadException e) {
			e.printStackTrace();
			return -1 ;
		}
		
		return 0;
	}

	@Override
	public int PinpadUpdateUserKey(int nUserKeyID, byte[] arryCipherNewUserKey, int nCipherNewUserKeyLength) {
		
		try {
			if(nUserKeyID == 0) {
				pinPad.dispersePIK(StrUtil.ByteToHexString(arryCipherNewUserKey, ""));				
			} else if(nUserKeyID == 1) {
				pinPad.clearWorkKey() ;		// 会清除mak,tdk,pik
				pinPad.disperseMAK(StrUtil.ByteToHexString(arryCipherNewUserKey, "")) ;				
			}
			pinPad.disperseTdk(nUserKeyID, StrUtil.ByteToHexString(arryCipherNewUserKey, "")) ;
		} catch (PinPadException e) {
			e.printStackTrace();
			return -1 ;
		}
		return 0;
	}

	@Override
	public int PinPadSelectKey(int iUserKeyId) {
		keyIndex = iUserKeyId ;
		return 0;
	}

	@Override
	public int PinpadEncryptString(byte[] arryPlainText, int nTextLength, byte[] arryCipherTextBuffer) {
		try {
			String enData = pinPad.encryptData(keyIndex, StrUtil.ByteToHexString(arryPlainText, "")) ;		// 3DES加密会拿指定的key对0000000000000000进行加密。
			byte[] result = StrUtil.HexStringToByte(enData, "") ;		
			for(int i=0; i<result.length; i++) {
				arryCipherTextBuffer[i] = result[i] ;
			}
			Log.i(TAG_LOG,"enData = " + enData) ;
		} catch (PinPadException e) {
			e.printStackTrace();
			return -1 ;
		}
		return 0 ;
	}

}
