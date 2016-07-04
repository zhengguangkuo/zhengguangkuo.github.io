package com.wizarpos.apidemo.activity;

import com.mt.app.padpayment.driver.PinPadDriver;
import com.wizarpos.jni.PinPadInterface;

public class PinPadDriverForWizar extends PinPadDriver {
	/** 表示键盘中的第一组tmk，键盘中有10组 */
	private int nMasterKeyID = 2 ;			
    public int pinpadOpen(){
    	int ret = 0;
    	ret = PinPadInterface.open();
    	return ret;
    }
    
    public int pinpadClose(){
    	return PinPadInterface.close();
    }
    public int pinpadCaculateMac(byte arryData[], int nDataLength, byte arryMACOutBuffer[]){
    	int nMACFlag = 0;
        return PinPadInterface.calculateMac(arryData, nDataLength, nMACFlag, arryMACOutBuffer);
    }
    public int pinpadCaculatePinblock(byte arryASCIICardNumber[], int nCardNumberLength, byte arryPinBlockBuffer[], int nTimeout_MS){
    	int nFlagSound = 1;
    	return PinPadInterface.inputPIN(arryASCIICardNumber, nCardNumberLength, arryPinBlockBuffer, nTimeout_MS, nFlagSound);
    }
    public int pinpadUpdateMasterKey(byte arryOldMasterKey[], int nOldMasterKeyLength,  byte arryNewMasterKey[], int nCipherNewUserKeyLength){
    	
    	return PinPadInterface.updateMasterKey(nMasterKeyID, arryOldMasterKey, nOldMasterKeyLength, arryNewMasterKey, nCipherNewUserKeyLength);
    }
    public int PinpadUpdateUserKey(int nUserKeyID, byte arryCipherNewUserKey[], int nCipherNewUserKeyLength){
    	return PinPadInterface.updateUserKey(nMasterKeyID, nUserKeyID, arryCipherNewUserKey, nCipherNewUserKeyLength);
    }
    public int PinPadSelectKey(int iUserKeyId){
    	return PinPadInterface.setKey(2, nMasterKeyID, iUserKeyId, 1);
    }
    public int PinpadEncryptString(byte arryPlainText[], int nTextLength, byte arryCipherTextBuffer[]){
    	return PinPadInterface.encrypt(arryPlainText, nTextLength, arryCipherTextBuffer);
    }
}
