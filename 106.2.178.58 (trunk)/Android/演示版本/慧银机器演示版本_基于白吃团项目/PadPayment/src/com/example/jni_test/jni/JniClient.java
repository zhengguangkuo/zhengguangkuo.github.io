package com.example.jni_test.jni;

/*
 * 附上 picc_lib.h 和  picc_app.c 使用的参数，
 * 
 *		char atq[14];
 *		char atq_len;
 *		char sak;
 *		char uid[10];
 *		char uid_len;
 *		char card_type=0;
 *		int i;
 *		int status;
 *		int timeout=0;
 *		
 *		char key[]={0xff,0xff,0xff,0xff,0xff,0xff};
 *		char write_data[16]={0x12,0x23,0x34,0x55,0x66,0x77,0x88,0x99,0xaa,0xbb,0xcc,0xde,0xff,0x45,0x25,0x85};
 *		char select_file[]={0x00,0xa4,0x04,0x00,0x07,0x52,0x73,0x61,0x20,0x41,0x70,0x70,0x3d};
 *		char cpdu[300]={0x00};
 *		int cpdu_len=0;
 *		char read_data[16]={0x00};
 * 
 * */
public class JniClient {

	public static int version = 1;

	static {
		System.loadLibrary("JniNfc");
	}

	/*
	 * AutoRun 实现了 picc_lib.h 和 picc_app.c
	 */
	public static native String AutoRun();

	/*
	 * picc_reader_open();
	 */

	/**
	 * open so file
	 * 
	 * @return
	 */
	public static native int picc_open_file();

	/**
	 * close so file
	 * 
	 * @return
	 */
	public static native int picc_close_file();

	public static native int picc_reader_open();

	/*
	 * picc_reader_close();
	 */
	public static native int picc_reader_close();

	/*
	 * 
	 * picc_card_connect(&card_type,uid,&uid_len,atq,&atq_len,&sak,timeout);
	 */
	public static native String picc_card_connect(int timeout);

	/*
	 * picc_card_disconnect();
	 */
	public static native int picc_card_disconnect();

	/*
	 * 
	 * picc_mifare_auth(0,key,&uid[uid_len-4],1)
	 */

	public static native int picc_mifare_auth(int mode_ab, String key,
			String auth_uid, int block);

	/*
	 * 
	 * picc_mifare_read(1,read_data)
	 */
	public static native String picc_mifare_read(int block, String data);

	/*
	 * 
	 * picc_mifare_write(1,write_data)
	 */
	public static native int picc_mifare_write(int block, String data);

	/*
	 * 
	 * picc_iso_apdu(select_file,sizeof(select_file),cpdu,&cpdu_len)
	 */
	public static native String picc_iso_apdu(String select_file);
}
