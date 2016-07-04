package com.driver.landi;

import static com.landicorp.pinpad.ExternalPinpadSpec.EPP_MAC_KEY_ID_END;
import static com.landicorp.pinpad.ExternalPinpadSpec.EPP_MK_ID_START;
import static com.landicorp.pinpad.ExternalPinpadSpec.EPP_PIN_KEY_ID_START;
import static com.landicorp.pinpad.ExternalPinpadSpec.EPP_TDK_ID_START;
import static com.landicorp.pinpad.KapCfg.ENC_KEY_FMT_NORMAL;
import static com.landicorp.pinpad.KapId.GSK_ID;
import static com.landicorp.pinpad.KeyCfg.MAC_KEY_CFG;
import static com.landicorp.pinpad.KeyCfg.MK_CFG;
import static com.landicorp.pinpad.KeyCfg.PIN_KEY_CFG;
import static com.landicorp.pinpad.KeyCfg.TDK_CFG;
import static com.landicorp.pinpad.KeyHandle.KS_MKSK;
import static com.landicorp.pinpad.PinEntryCfg.PEWM_GET_ONLINE_PIN;
import static com.landicorp.pinpad.PinpadDevice.MAX_PINPAD_DEVS_NUM;
import static com.landicorp.pinpad.PinpadDevice.MTEM_DEFAULT;
import static com.landicorp.pinpad.PinpadDevice.SUCCESS;
import static com.landicorp.pinpad.PinpadInfo.HW_EXT_COM;
import android.os.Looper;
import android.util.Log;

import com.landicorp.pinpad.IntWraper;
import com.landicorp.pinpad.KeyHandle;
import com.landicorp.pinpad.PinEntryCfg;
import com.landicorp.pinpad.PinEntryEvent;
import com.landicorp.pinpad.PinEntryEventListener;
import com.landicorp.pinpad.PinpadDevice;
import com.landicorp.pinpad.PinpadInfo;
import com.mt.android.message.iso.util.StrUtil;
import com.mt.app.padpayment.driver.PinPadDriver;

public class PinPadDriverForLandi extends PinPadDriver {

	private static final String TAG_LOG = PinPadDriverForLandi.class.getSimpleName() ;
	private int userKeyId = -1 ;
	private static PinpadDevice mComEpp ;
	private static final int MK_ID = EPP_MK_ID_START;
	private static final int PIN_KEY_ID = EPP_PIN_KEY_ID_START;
	private static final int MAC_KEY_ID = EPP_MAC_KEY_ID_END;
	private static final int TDK_ID_PIN = EPP_TDK_ID_START;
	private static final int TDK_ID_MAC = EPP_TDK_ID_START + 1;
	/** GSK 中 MK 的 KeyHandle 实例. */
	private static final KeyHandle MK_HANDLE_IN_GSK = new KeyHandle(GSK_ID, KS_MKSK, MK_ID);
	private static final KeyHandle PIN_KEY_HANDLE_IN_GSK = new KeyHandle(GSK_ID, KS_MKSK, PIN_KEY_ID);
	
	
	private static final byte[] DEFAULT_PIN_LEN_TYPES_LIST = { 6, 8, 10, 12, };	

	@Override
	public int pinpadOpen() {
		int ret = SUCCESS;

		IntWraper num = new IntWraper();
		PinpadInfo[] infoList = new PinpadInfo[MAX_PINPAD_DEVS_NUM];
		PinpadInfo info = null;
		int i;

		if (null != mComEpp) {
			Log.w(TAG_LOG, "mComEpp is NOT null, release it first.");
			mComEpp.closeDevice();
			mComEpp = null;
		}

		if (SUCCESS != (ret = PinpadDevice.getAllPinpadsInfo(num, infoList))) {
			Log.e(TAG_LOG, "openComEpp() : fail to get all pinpads info.");
			return ret;
		}

		for (i = 0; i < num.getIntValue(); i++) {
			info = infoList[i];

			if (null == info) {
				continue;
			}

			if (HW_EXT_COM == info.mHwType) {
				Log.d(TAG_LOG, "found info of com epp. name : " + info.mDevName);
				break;
			}
		}

		if (i == num.getIntValue()) { // 若没有找到 com epp 物理设备.
			Log.e(TAG_LOG, "fail to found com epp.");
			return -1;
		}

		if (null != info) {
			mComEpp = PinpadDevice.openDevice(info.mDevName);
			if (null == mComEpp) {
				Log.e(TAG_LOG, "fail to open com epp.");
				return -1;
			}
		}

		return 0;
	}

	@Override
	public int pinpadClose() {
		mComEpp.closeDevice() ;
		return 0;
	}

	@Override
	public int pinpadCaculatePinblock(byte[] arryASCIICardNumber, int nCardNumberLength, final byte[] arryPinBlockBuffer, int nTimeout_MS) {
		int ret = 0;
		Looper.prepare() ;
		final Looper currentLooper = Looper.myLooper() ;
		Log.d(TAG_LOG, "phase forward : load_keys -> do_pin_entry_with_pin_key_1.");
		byte[] cardNumber = StrUtil.HexStringToByte(new String(arryASCIICardNumber), "") ;
		// pin_key_1
		PinEntryCfg cfg = new PinEntryCfg(PEWM_GET_ONLINE_PIN, PIN_KEY_HANDLE_IN_GSK, cardNumber, DEFAULT_PIN_LEN_TYPES_LIST);

		if (SUCCESS != (ret = beepAndDisp(mComEpp, "enter pin, pek_1", ""))) {
			Log.e(TAG_LOG, "fail to beep and disp before pin entry.");
			return ret;
		}

		if (SUCCESS != (ret = mComEpp.startPinEntry(new PinEntryEventListener() {
			@Override
			public int onPinEntryEvent(PinEntryEvent event) {
				Log.i("pinblock:::---->", "onPinEntryEvent");
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < event.mPinBlock.length; i++) {
					String value = StrUtil.ByteToHexString(new byte[] { event.mPinBlock[i] }, "");
					sb.append(value.substring(1));
				}
				byte[] block = StrUtil.HexStringToByte(sb.toString(), "");
				for(int i=0; i<block.length; i++) {
					arryPinBlockBuffer[i] = block[i] ;
				}
				Log.i("pinblock:::---->", StrUtil.ByteToHexString(arryPinBlockBuffer, ""));
				currentLooper.quit() ;
				return 0;
			}
		}, cfg))) {
			Log.e(TAG_LOG, "fail to start onlien pin etnry with pin_key_1.");
			return ret;
		}

		Log.d(TAG_LOG, "please enter PIN on pinpad '" + mComEpp.getName() + "'");
		Looper.loop() ;
		return ret;
	}

	@Override
	public int pinpadUpdateMasterKey(byte[] arryOldMasterKey, int nOldMasterKeyLength, byte[] arryNewMasterKey, int nCipherNewUserKeyLength) {
		int ret = SUCCESS;
		KeyHandle mkHandle = new KeyHandle(GSK_ID, KS_MKSK, MK_ID);

		if (SUCCESS != (ret = mComEpp.switchKapToWorkMode(GSK_ID))) {
			Log.e(TAG_LOG, "fail to switch GSK to work mode.");
			return ret;
		}

		if (SUCCESS != (ret = mComEpp.loadPlainTextKey(mkHandle, MK_CFG, arryNewMasterKey))) {
			Log.e(TAG_LOG, "fail to load MK.");
			return ret;
		}

		return ret ;
	}

	@Override
	public int PinpadUpdateUserKey(int nUserKeyID, byte[] arryCipherNewUserKey, int nCipherNewUserKeyLength) {
		if(userKeyId == 0) {			// pin
			loadPinKey(arryCipherNewUserKey) ;
			loadEncedTdk(TDK_ID_PIN, arryCipherNewUserKey) ;			
		} else if(userKeyId == 1) {		// mac
			loadEncedMacKey(arryCipherNewUserKey) ;
			loadEncedTdk(TDK_ID_MAC, arryCipherNewUserKey) ;						
		}
		return 0;
	}

	@Override
	public int PinPadSelectKey(int iUserKeyId) {
		userKeyId = iUserKeyId ;
		return 0;
	}

	@Override
	public int PinpadEncryptString(byte[] arryPlainText, int nTextLength, byte[] arryCipherTextBuffer) {
		if(userKeyId == 0) {
			encTrackData(TDK_ID_PIN, arryPlainText, arryCipherTextBuffer) ;			
		} else if(userKeyId == 1) {
			encTrackData(TDK_ID_MAC, arryPlainText, arryCipherTextBuffer) ;						
		}
		return 0;
	}

	
	private int beepAndDisp(PinpadDevice pinpad, String line1, String line2) {
		int ret = SUCCESS;

		if (SUCCESS != (ret = pinpad.pinpadBeep(200))) {
			Log.e(TAG_LOG, "fail to beep pinpad.");
			return ret;
		}

		if (SUCCESS != (ret = pinpad.dispPinpad(1, line1))) {
			Log.e(TAG_LOG, "fail to display str on com pinpad.");
			return ret;
		}

		if (SUCCESS != (ret = pinpad.dispPinpad(2, line2))) {
			Log.e(TAG_LOG, "fail to display str on com pinpad.");
			return ret;
		}

		return ret;
	}	
	
	private int loadEncedMacKey(byte[] encedMacKey) {
		int ret = SUCCESS;
		KeyHandle mkHandle = new KeyHandle(GSK_ID, KS_MKSK, MK_ID); // srcKey
		KeyHandle macKeyHandle = new KeyHandle(GSK_ID, KS_MKSK, MAC_KEY_ID); // dstKey
		if (SUCCESS != (ret = mComEpp.loadEncKey(mkHandle, macKeyHandle, ENC_KEY_FMT_NORMAL, MAC_KEY_CFG, encedMacKey))) {
			Log.e(TAG_LOG, "fail to load enced mac key.");
			return ret;
		}
		return ret;
	}	
	
	private int loadPinKey(byte[] encedPinKey) {
		int ret = SUCCESS;
		KeyHandle mkHandle = new KeyHandle(GSK_ID, KS_MKSK, MK_ID);	// srcKey
		KeyHandle pinKeyHandle = new KeyHandle(GSK_ID, KS_MKSK, PIN_KEY_ID);// dstKey
		if (SUCCESS != (ret = mComEpp.loadEncKey(mkHandle, pinKeyHandle, ENC_KEY_FMT_NORMAL, PIN_KEY_CFG, encedPinKey))) {
			Log.e(TAG_LOG, "fail to load enced pin key.");
			return ret;
		}
		return ret;
	}	
	
	/**
	 * 下载密文 TDK.
	 */
	private int loadEncedTdk(int tdkId, byte[] encedTdk) {
		int ret = SUCCESS;
		// srcKey
		KeyHandle mkHandle = new KeyHandle(GSK_ID, KS_MKSK, MK_ID);
		// dstKey
		KeyHandle tdkHandle = new KeyHandle(GSK_ID, KS_MKSK, tdkId);
		if (SUCCESS != (ret = mComEpp.loadEncKey(mkHandle, tdkHandle, ENC_KEY_FMT_NORMAL, TDK_CFG, encedTdk))) {
			Log.e(TAG_LOG, "fail to load enced tdk.");
			return ret;
		}
		return ret;
	}	
	
	/** 加密磁道数据. */
	private int encTrackData(int keyNumber, byte[] arryPlainText, byte[] result) {
		int ret = SUCCESS;
		// dstKey
		KeyHandle tdkHandle = new KeyHandle(GSK_ID, KS_MKSK, keyNumber);
		IntWraper outLen = new IntWraper();
		byte[] outFromPp = new byte[arryPlainText.length];

		if (SUCCESS != (ret = mComEpp.encryptMagTrackData(tdkHandle, MTEM_DEFAULT, true, arryPlainText, outLen, outFromPp))) {
			Log.e(TAG_LOG, "fail to enc track data.");
			return ret;
		}
		
		for(int i=0; i<outFromPp.length; i++) {
			result[i] = outFromPp[i] ;
		}
		
		Log.i("tdk加密后结果---->encryptMagTrackData", StrUtil.ByteToHexString(outFromPp, ""));

		return ret;
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
