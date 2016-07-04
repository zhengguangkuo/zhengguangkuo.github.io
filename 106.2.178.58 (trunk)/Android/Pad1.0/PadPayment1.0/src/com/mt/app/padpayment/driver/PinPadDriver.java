package com.mt.app.padpayment.driver;

public abstract class PinPadDriver {

	/**
	 * 打开密码键盘
	 * @return
	 */
	public abstract int pinpadOpen() ;
	
	/**
	 * 关闭密码键盘
	 * @return
	 */
	public abstract int pinpadClose() ;
	
	/**
	 * 计算Pinblock   
	 * @param arryASCIICardNumber    卡号的字节数组
	 * @param nCardNumberLength	卡号的长度
	 * @param arryPinBlockBuffer 用于存放计算后的Pinblock结果 	
	 * @param nTimeout_MS 超时时间
	 * @return
	 * 两种实现方式：一种是阻塞的，一种是非阻塞的(循环)
	 */
	public abstract int pinpadCaculatePinblock(byte arryASCIICardNumber[], int nCardNumberLength, byte arryPinBlockBuffer[], int nTimeout_MS) ;
	
	
	/**
	 * 更新TMK
	 * @param arryOldMasterKey 旧的tmk
	 * @param nOldMasterKeyLength	旧的TMK长度
	 * @param arryNewMasterKey	新的TMK
	 * @param nCipherNewUserKeyLength	新的TMK长度
	 * @return
	 */
	public abstract int pinpadUpdateMasterKey(byte arryOldMasterKey[], int nOldMasterKeyLength,  byte arryNewMasterKey[], int nCipherNewUserKeyLength) ;
	// 组的概念 编号概念  确定 自己用哪一组哪个编号  每个组有多少个号
	
	/**
	 * 更新PIK
	 * @param nUserKeyID	tmk所在组存放pik的编号 
	 * @param arryCipherNewUserKey		pik
	 * @param nCipherNewUserKeyLength	pik的长度
	 * @return
	 */
	public abstract int PinpadUpdateUserKey(int nUserKeyID, byte arryCipherNewUserKey[], int nCipherNewUserKeyLength) ;
	
	
	/**
	 * 选择pik的编号 	
	 * @param iUserKeyId	pik的编号 
	 * @return
	 */
	public abstract int PinPadSelectKey(int iUserKeyId) ;
	
	
	/**
	 * 加密字符串
	 * @param arryPlainText		需要加密的字符串
	 * @param nTextLength		需要加密的字符串的长度
	 * @param arryCipherTextBuffer	存放加密后的结果 m
	 * @return
	 */
	public abstract int PinpadEncryptString(byte arryPlainText[], int nTextLength, byte arryCipherTextBuffer[]) ;
}
