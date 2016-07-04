package com.wizarpos.apidemo.jniinterface;

public class ContactlessInterface 
{
	//load *.so library for use
	static {
		System.loadLibrary("wizarpos_contactlesscard");
	}
	public static int CONTACTLESS_CARD_MODE_AUTO = 0;
	public static int CONTACTLESS_CARD_MODE_TYPE_A = 1;
	public static int CONTACTLESS_CARD_MODE_TYPE_B	= 2;
	public static int CONTACTLESS_CARD_MODE_TYPE_C	= 3;
	
	public static int RC500_COMMON_CMD_GET_READER_VERSION = 0x40;
	public static int RC500_COMMON_CMD_RF_CONTROL	= 0x38; // command data : 0x01(turn on), 0x00(turn off)
	
	/**Initialize the contactless1 card reader
	 * @return  value  == 0, error ; value != 0 , correct handle
	 * */
	public native static int Open();
	/**Close the contactless1 card reader
	 * @return value  >= 0, success ; value < 0, error code
	 * */
	public native static int Close();
	/**Start searching the contactless1 card
	 * If you set the nCardMode is auto, reader will try to activate card in type A, type B and type successively;
	 * If you set the nCardMode is type A, type B, or type C, reader only try to activate card in the specified way.
	 * @param : nCardMode : handle of this card reader 
	 * possible value :
	 * 							CONTACTLESS_CARD_MODE_AUTO = 0
	 *							CONTACTLESS_CARD_MODE_TYPE_A = 1;
	 *							CONTACTLESS_CARD_MODE_TYPE_B = 2;
	 *							CONTACTLESS_CARD_MODE_TYPE_C = 3;
	 * @param  nFlagSearchAll : 0 : signal user if we find one card in the field
	 * 							 1 : signal user only we find all card in the field
	 * @param  nTimeout_MS    : time out in milliseconds.
	 * 							 if nTimeout_MS is less then zero, the searching process is infinite.
	 * @return value  >= 0, success in starting the process; value < 0, error code
	 * */
	public native static int SearchTargetBegin(int nCardMode, int nFlagSearchAll, int nTimeout_MS);
	
	/**
	 * Stop the process of searching card.
	 * @return :value  >= 0, success in starting the process; value < 0, error code
	 * */
	
	public native static int SearchTargetEnd();
	/**
	 * Attach the target before transmitting apdu command
	 * In this process, the target(card) is activated and return ATR
	 * @param byteArrayATR ATR buffer, if you set it null, you can not get the data.
	 * @return  :value  >= 0, success in starting the process and return length of ATR; value < 0, error code
	 * */
	public native static int AttachTarget(byte byteArrayATR[]);
	/**
	 * Detach the target. If you want to send APDU again, you should attach it.
	 * @return :value  >= 0, success in starting the process; value < 0, error code
	 * */
	public native static int DetachTarget();
	/**
	 * Transmit APDU command and get the response
	 * @param byteArrayAPDU: command of APDU
	 * @param nAPDULength: length of command of APDU
	 * @param byteArrayResponse: response of command of APDU
	 * @return value  >= 0, success in starting the process; value < 0, error code
	 * */
	public native static int Transmit(byte byteArrayAPDU[], int nAPDULength, byte byteArrayResponse[]);
	
	/**
	 * Send control command.
	 * @param	nCmdID	: id of command
	 * @param	byteArrayCmdData : data associated with command, if no data, you can set it NULL
	 * @param 	nDataLength : data length of command
	 * @return  value  >= 0, success in starting the process; value < 0, error code
	 * */
	
	public native static int SendControlCommand(int nCmdID, byte byteArrayCmdData[], int nDataLength);
	
	/**
	 * @param  nTimeout_MS		: time out in milliseconds.
	 * 							 if nTimeout_MS is less then zero, the event process is infinite.
	 * @param  event			: 
	 * @return value  >= 0, success in starting the process; value < 0, error code
	 * */
	public native static int PollEvent(int nTimeout_MS, ContactlessEvent event);
	
	/**
	 * Verify pin only for MiFare one card
	 * @param[in] : unsigned int nSectorIndex : sector index
	 * @param[in] : unsigned int nPinType : 0 : A type
	 * 										1 : B type
	 * @param[in] : unsigned char* strPin : password of this pin
	 * @param[in] : unsigned int nPinLength : length of password
	 * return value : >= 0 : success
	 * */
	public native static int VerifyPinMemory(int nSectorIndex, int nPinType, byte[] strPin, int nPinLength);
	/**
	 * Read data only for MiFare one card
	 * @param[in] : unsigned int nSectorIndex : sector index
	 * @param[in] : unsigned int nBlockIndex : block index
	 * @param[out] : unsigned char* pDataBuffer : data buffer
	 * @param[in] : unsigned int nDataBufferLength : buffer length
	 * return value : >= 0 : data length
	 * 				  < 0 : error code
	 */
	public native static int ReadMemory(int nSectorIndex, int nBlockIndex, byte[] pDataBuffer, int nDataBufferLength);
	
	/**
	 * Write data only for MiFare one card
	 * @param[in] : unsigned int nSectorIndex : sector index
	 * @param[in] : unsigned int nBlockIndex : block index
	 * @param[in] : unsigned char* pData : data
	 * @param[in] : unsigned int nDataLength : data length
	 * return value : >= 0 : success
	 *                < 0 : error code
	 */
	public native static int WriteMemory(int nSectorIndex, int nBlockIndex, byte[] pData, int nDataLength);
	
	/**
	 * Read data from 15693 card
	 * @param[in] : int nHandle : handle of this card reader
	 * @param[in] : unsigned int nBlockIndex : block index
	 * @param[in] : unsigned int nBlockCount : block count
	 * @param[in] : unsigned char* pDataBuffer : data buffer for saving data
	 * @param[in] : unsigned int nDataBufferLength : length of data buffer
	 * return value : >= 0 : success
	 *                < 0 : error code
	 */
	public native static int Read15693Memory(int nBlockIndex, int nBlockCount, byte[] pData, int nDataLength);
	
	/*
	 * Write data to 15693 card
	 * @param[in] : int nHandle : handle of this card reader
	 * @param[in] : unsigned int nBlockIndex : block index
	 * @param[in] : unsigned char* pData : data
	 * @param[in] : unsigned int nDataLength : data length, max data length is 32
	 * return value : >= 0 : success
	 *                < 0 : error code
	 *
	 */
	public native static int Write15693Memory(int nBlockIndex, byte[] pData, int nDataLength);
	
	
	
	
	
}
