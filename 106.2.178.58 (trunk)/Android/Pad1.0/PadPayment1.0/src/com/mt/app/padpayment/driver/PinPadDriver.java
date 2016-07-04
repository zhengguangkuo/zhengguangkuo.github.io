package com.mt.app.padpayment.driver;

public abstract class PinPadDriver {

	/**
	 * ���������
	 * @return
	 */
	public abstract int pinpadOpen() ;
	
	/**
	 * �ر��������
	 * @return
	 */
	public abstract int pinpadClose() ;
	
	/**
	 * ����Pinblock   
	 * @param arryASCIICardNumber    ���ŵ��ֽ�����
	 * @param nCardNumberLength	���ŵĳ���
	 * @param arryPinBlockBuffer ���ڴ�ż�����Pinblock��� 	
	 * @param nTimeout_MS ��ʱʱ��
	 * @return
	 * ����ʵ�ַ�ʽ��һ���������ģ�һ���Ƿ�������(ѭ��)
	 */
	public abstract int pinpadCaculatePinblock(byte arryASCIICardNumber[], int nCardNumberLength, byte arryPinBlockBuffer[], int nTimeout_MS) ;
	
	
	/**
	 * ����TMK
	 * @param arryOldMasterKey �ɵ�tmk
	 * @param nOldMasterKeyLength	�ɵ�TMK����
	 * @param arryNewMasterKey	�µ�TMK
	 * @param nCipherNewUserKeyLength	�µ�TMK����
	 * @return
	 */
	public abstract int pinpadUpdateMasterKey(byte arryOldMasterKey[], int nOldMasterKeyLength,  byte arryNewMasterKey[], int nCipherNewUserKeyLength) ;
	// ��ĸ��� ��Ÿ���  ȷ�� �Լ�����һ���ĸ����  ÿ�����ж��ٸ���
	
	/**
	 * ����PIK
	 * @param nUserKeyID	tmk��������pik�ı�� 
	 * @param arryCipherNewUserKey		pik
	 * @param nCipherNewUserKeyLength	pik�ĳ���
	 * @return
	 */
	public abstract int PinpadUpdateUserKey(int nUserKeyID, byte arryCipherNewUserKey[], int nCipherNewUserKeyLength) ;
	
	
	/**
	 * ѡ��pik�ı�� 	
	 * @param iUserKeyId	pik�ı�� 
	 * @return
	 */
	public abstract int PinPadSelectKey(int iUserKeyId) ;
	
	
	/**
	 * �����ַ���
	 * @param arryPlainText		��Ҫ���ܵ��ַ���
	 * @param nTextLength		��Ҫ���ܵ��ַ����ĳ���
	 * @param arryCipherTextBuffer	��ż��ܺ�Ľ�� m
	 * @return
	 */
	public abstract int PinpadEncryptString(byte arryPlainText[], int nTextLength, byte arryCipherTextBuffer[]) ;
}
