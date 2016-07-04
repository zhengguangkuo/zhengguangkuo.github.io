/*
 * JniClient.c
 *
 *  Created on: 2014-4-3
 *      Author: jie.liu
 *  生成命令：
 *  javah -classpath D:\workspace\jni_test\bin\classes -d jni com.example.jni_test.JniClient
 *
 *  生成的文件名字很长，可以修改。要保证和 Android.mk 里面的一致     LOCAL_SRC_FILES:= \
 *
 */

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <dlfcn.h>
#include <jni.h>
#include <android/log.h>
#include "com_example_jni_test_jni_JniClient.h"

//so路径：/data/data/我的程序的包名/lib/我的so文件名
#define SHARE_LIB_PATCH "/system/lib/libnfc_raw.so"
//#define SHARE_LIB_PATCH "/data/data/com.example.sotest/lib/libnfc_raw.so"

#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, "com.example.jni_test", __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG  , "com.example.jni_test", __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO   , "com.example.jni_test", __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN   , "com.example.jni_test", __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR  , "com.example.jni_test", __VA_ARGS__)

/* Tag TYPE */
#define		APP_NDEF_TYPE1				(1)
#define		APP_NDEF_TYPE2				(2)
#define		APP_NDEF_TYPE3				(3)
#define		APP_NDEF_TYPE4				(4)
#define		APP_NDEF_MIFARE				(5)
#define		APP_TAG_TYPE_A              (6)
#define		APP_TAG_TYPE_B  			(7)
#define		APP_TAG_FELICA				(8)
#define		APP_TAG_JEWEL				(9)
#define		APP_TAG_MIFARE_1K			(10)
#define		APP_TAG_MIFARE_UL			(11)
#define	    APP_TAG_MIFARE_DESFIRE		(12)
#define		APP_TAG_NON_NDEF			(13)
#define		APP_TAG_15693   			(14)

char * card_type_string[] = { "APP_NDEF_NONE", "APP_NDEF_TYPE1",
		"APP_NDEF_TYPE2", "APP_NDEF_TYPE3", "APP_NDEF_TYPE4", "APP_NDEF_MIFARE",
		"APP_TAG_TYPE_A", "APP_TAG_TYPE_B", "APP_TAG_FELICA", "APP_TAG_JEWEL",
		"APP_TAG_MIFARE_1K", "APP_TAG_MIFARE_UL", "APP_TAG_MIFARE_DESFIRE",
		"APP_TAG_NON_NDEF", "APP_TAG_15693", };

int handle;
void * filehandle;
/**
 * AutoRun();
 */
jstring Java_com_example_jni_1test_jni_JniClient_AutoRun(JNIEnv *env,
		jclass thiz) {

	jstring ret = (*env)->NewStringUTF(env, "empty");
	//向logcat打印日志
	__android_log_print(ANDROID_LOG_ERROR, "com.example.jni_test",
			"_____  AutoRun  _____");
	//在dlopen（）函数以指定模式打开指定的动态链接库文件，并返回一个句柄给dlsym（）的调用进程
	void * filehandle = dlopen(SHARE_LIB_PATCH, RTLD_LAZY);
	//获取函数地址
	int (*picc_reader_open)(void) = dlsym(filehandle, "picc_reader_open");
	int (*picc_reader_close)(void) = dlsym(filehandle, "picc_reader_close");
	int (*picc_card_connect)(char *, char *, char *, char *, char *, char *,
			int) =
			dlsym(filehandle, "picc_card_connect");
	int (*picc_card_disconnect)(void) = dlsym(filehandle, "picc_card_disconnect");
	int (*picc_mifare_auth)(char, char *, char *,
			int) = dlsym(filehandle, "picc_mifare_auth");
	int (*picc_mifare_read)(int, char *) = dlsym(filehandle, "picc_mifare_read");
	int (*picc_mifare_write)(int,
			char *) = dlsym(filehandle, "picc_mifare_write");
	int (*picc_iso_apdu)(char *, int, char *,
			int *) = dlsym(filehandle, "picc_iso_apdu");

	char atq[14];
	char atq_len;
	char sak;
	char uid[10];
	char uid_len;
	char card_type = 0;
	int i;
	int status;
	int timeout = 0;

	char key[] = { 0xff, 0xff, 0xff, 0xff, 0xff, 0xff };
	char write_data[16] = { 0x12, 0x23, 0x34, 0x55, 0x66, 0x77, 0x88, 0x99,
			0xaa, 0xbb, 0xcc, 0xde, 0xff, 0x45, 0x25, 0x85 };
	char select_file[] = { 0x00, 0xa4, 0x04, 0x00, 0x07, 0x52, 0x73, 0x61, 0x20,
			0x41, 0x70, 0x70, 0x3d };
	char cpdu[300] = { 0x00 };
	int cpdu_len = 0;
	char read_data[16] = { 0x00 };

	//printf("picc_reader_open\r\n");
	//__android_log_print(ANDROID_LOG_ERROR, "com.example.jni_test",
	//		"picc_reader_open ....");
	picc_reader_open();

	while (1) {
		status = picc_card_connect(&card_type, uid, &uid_len, atq, &atq_len,
				&sak, timeout);

		if (status != 0) {
			LOGE("%s", "picccard is found!");
			sleep(1);
			continue;
		}
		LOGE("%s", "picccard connect success!");
		if (card_type
				< (sizeof(card_type_string) / sizeof(card_type_string[0]))) {

			printf("UID:: ");
			char strUid[uid_len * 2];
			int j = 0;
			for (i = 0; i < uid_len; i++) {
				sprintf(strUid + j, "%02x", uid[i]);
				j += 2;
				printf("%02x, ", uid[i]);
			}
			//		ret = (*env)->NewStringUTF(env, strUid);
			printf("\r\n");

			printf("ATQ:: ");
			for (i = 0; i < atq_len; i++) {
				printf("%02x, ", atq[i]);
			}

			if (card_type == APP_TAG_MIFARE_1K) {
				if (picc_mifare_auth(0, key, &uid[uid_len - 4], 2) == 0) {
					LOGE("%s", "picc_mifare_auth OK!!!");
				} else {
					LOGE("%s", "picc_mifare_auth faile!");
					goto PICC_card_disconnect;
				}

				if (picc_mifare_write(1, write_data) == 0) {
					LOGE("%s", "picc_mifare_write OK!!");
				} else {
					LOGE("%s", "picc_mifare_write faile!!");
					goto PICC_card_disconnect;
				}

				if (picc_mifare_read(1, read_data) == 0) {
					LOGE("%s", "picc_mifare_read OK!!");
				} else {
					LOGE("%s", "picc_mifare_read faile!!");
					goto PICC_card_disconnect;
				}
			} else {
				if (picc_iso_apdu(select_file, sizeof(select_file), cpdu,
						&cpdu_len) == 0) {
					LOGE("%s", "picc_iso_apdu OK!");
				} else {
					LOGE("%s", "picc_iso_apdu faile");
					goto PICC_card_disconnect;
				}
			}

			PICC_card_disconnect:
			LOGE("%s", "picc_card_disconnect");
			picc_card_disconnect();
			sleep(1);
		} else {
			LOGE("%s", "card is unknow!");
		}
		break;
	} //end while(1)

	picc_reader_close();
	LOGE("%s", "picc_reader_close");
	return ret;

}

jint JNICALL Java_com_example_jni_1test_jni_JniClient_picc_1open_1file(
		JNIEnv *env, jclass thiz) {
	jint ret = -200;
	filehandle = dlopen(SHARE_LIB_PATCH, RTLD_LAZY);
	return ret;
}

jint JNICALL Java_com_example_jni_1test_jni_JniClient_picc_1close_1file(
		JNIEnv *env, jclass thiz) {
	jint ret = -200;
	dlclose(filehandle);
	return ret;
}

/**
 * picc_reader_open();
 */
jint Java_com_example_jni_1test_jni_JniClient_picc_1reader_1open(JNIEnv *env,
		jclass thiz) {
	jint ret = -200;
	//向logcat打印日志
	//__android_log_print(ANDROID_LOG_ERROR, "com.example.jni_test",
	//		"_____  picc_reader_open  _____");
	//so路径：/data/data/我的程序的包名/lib/我的so文件名

	if (filehandle) {
		//函数定义
		int (*funcPtrB)(void) = NULL;
		funcPtrB = dlsym(filehandle, "picc_reader_open");

		if (funcPtrB) {
			ret = funcPtrB();
		} else {
			ret = -1;
		}

	} else {
		ret = -2;
	}
	return ret;
}

/*
 * picc_reader_close();
 */
jint Java_com_example_jni_1test_jni_JniClient_picc_1reader_1close(JNIEnv *env,
		jclass thiz) {
	jint ret = -200;
//	__android_log_print(ANDROID_LOG_ERROR, "com.example.jni_test",
//			"_____  picc_reader_close  _____");
	//so路径：/data/data/我的程序的包名/lib/我的so文件名
//	void * filehandle = dlopen(SHARE_LIB_PATCH, RTLD_LAZY);

	if (filehandle) {
		int (*funcPtrB)(void) = NULL;
		funcPtrB = dlsym(filehandle, "picc_reader_close");
		if (funcPtrB) {
			ret = funcPtrB();
		} else {
			ret = -1;
		}

	} else {
		ret = -2;
	}

	return ret;
}

/*
 * picc_card_connect(char *card_type,char *uid,char *uid_len,char *atq,char * atq_len,char *sak,int timeout);
 */
jstring Java_com_example_jni_1test_jni_JniClient_picc_1card_1connect(
		JNIEnv *env, jclass thiz, jint timeout) {

	//参数定义
	char atq[14] = { 0 };
	char atq_len;
	char sak;
	char uid[10] = { 0 };
	char uid_len;
	char card_type = 0;
	int status;

	jstring ret;

//	void * filehandle = dlopen(SHARE_LIB_PATCH, RTLD_LAZY);
	//__android_log_print(ANDROID_LOG_ERROR, "com.example.jni_test",
	//		"_____  picc_card_connect  _____");
	if (filehandle) {
		int (*funcPtrB)(char *, char *, char *, char *, char *, char *, int) =
				NULL;
		funcPtrB = dlsym(filehandle, "picc_card_connect");
		if (funcPtrB) {
			status = funcPtrB(&card_type, uid, &uid_len, atq, &atq_len, &sak,
					timeout);
			if (status == 0
					&& card_type
							< (sizeof(card_type_string)
									/ sizeof(card_type_string[0]))) {
				char uidStr[uid_len * 2];
				int m = 0;
				int n = 0;
				for (m = 0; m < uid_len; m++) {
					sprintf(uidStr + n, "%02x", uid[m]);
					n += 2;
				}

				char atqStr[atq_len * 2];
				m = 0;
				n = 0;
				for (m = 0; m < atq_len; m++) {
					sprintf(atqStr + n, "%02x", atq[m]);
					n += 2;
				}

				char sakStr[2] = { 0 };
				sprintf(sakStr, "%02x", sak);

				char returnStr[uid_len * 2 + atq_len * 2 + 2 + 2];

				sprintf(returnStr, "%s%s%s%s%s", uidStr, "_", atqStr, "_",
						sakStr);

				ret = (*env)->NewStringUTF(env, returnStr);
			} else {
				//		__android_log_print(ANDROID_LOG_ERROR, "com.example.jni_test",
				//			"card is unknow OR unconnect!");
				ret = (*env)->NewStringUTF(env, "-1");
			}

		} else {
			ret = (*env)->NewStringUTF(env, "-1");
		}
	} else {
		ret = (*env)->NewStringUTF(env, "-1");
	}

	return ret;
}

/*
 * picc_card_disconnect(void);
 */
jint Java_com_example_jni_1test_jni_JniClient_picc_1card_1disconnect(
		JNIEnv *env, jclass thiz) {
	jint ret;
//	__android_log_print(ANDROID_LOG_ERROR, "com.example.jni_test",
//			"_____  picc_card_disconnect  _____");

	if (filehandle) {
		int (*funcPtrB)(void) = NULL;
		funcPtrB = dlsym(filehandle, "picc_card_disconnect");
		if (funcPtrB) {
			ret = funcPtrB();
		} else {
			ret = -1;
		}
	} else {
		ret = -2;
	}

	return ret;
}

/*
 * picc_mifare_auth(char mode_ab,char * key,char * auth_uid,int block);
 *
 */
jint Java_com_example_jni_1test_jni_JniClient_picc_1mifare_1auth(JNIEnv *env,
		jclass thiz, jint mode_ab, jstring keyJstr, jstring uidJstr, jint block) {

	char * keyStr = NULL;
	jclass clsstring = (*env)->FindClass(env, "java/lang/String");
	jstring strencode = (*env)->NewStringUTF(env, "utf-8");
	jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes",
			"(Ljava/lang/String;)[B");
	jbyteArray barr = (jbyteArray) (*env)->CallObjectMethod(env, keyJstr, mid,
			strencode);
	jsize alen = (*env)->GetArrayLength(env, barr);
	jbyte* ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
	if (alen > 0) {
		keyStr = (char*) malloc(alen + 1);
		memset(keyStr, 0, alen + 1);
		memcpy(keyStr, ba, alen);
		keyStr[alen] = 0;
	}
	(*env)->ReleaseByteArrayElements(env, barr, ba, 0);
	int keyStrLen;
	keyStrLen = strlen(keyStr);
	char key[keyStrLen / 2];
	int m;
	for (m = 0; m < keyStrLen - 1; m += 2) {
		int hight =
				(keyStr[m] - '0') >= 10 ?
						(keyStr[m] - 'a' + 10) << 4 : (keyStr[m] - '0') << 4;
		int low =
				(keyStr[m + 1] - '0') >= 10 ?
						(keyStr[m + 1] - 'a' + 10) : (keyStr[m + 1] - '0');
		key[m / 2] = hight + low;
	}
//	LOGE("keyStr is : %s", keyStr);
//	LOGE("f is : %d", 'f');
//	LOGE("a is : %d", 'a');
//	LOGE("0 is : %d", '0');
//	LOGE("key is : %s", key);

	char * uidStr = NULL;
	clsstring = (*env)->FindClass(env, "java/lang/String");
	strencode = (*env)->NewStringUTF(env, "utf-8");
	mid = (*env)->GetMethodID(env, clsstring, "getBytes",
			"(Ljava/lang/String;)[B");
	barr = (jbyteArray) (*env)->CallObjectMethod(env, uidJstr, mid, strencode);
	alen = (*env)->GetArrayLength(env, barr);
	ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
	if (alen > 0) {
		uidStr = (char*) malloc(alen + 1);
		memset(keyStr, 0, alen + 1);
		memcpy(uidStr, ba, alen);
		uidStr[alen] = 0;
	}
	(*env)->ReleaseByteArrayElements(env, barr, ba, 0);
	int uidStrLen;
	uidStrLen = strlen(uidStr);
	char uid[20] = { 0 };
	for (m = 0; m < uidStrLen - 1; m += 2) {
		char hight =
				(uidStr[m] - '0') >= 10 ?
						(uidStr[m] - 'a' + 10) << 4 : (uidStr[m] - '0') << 4;
		char low =
				(uidStr[m + 1] - '0') >= 10 ?
						(uidStr[m + 1] - 'a' + 10) : (uidStr[m + 1] - '0');
		uid[m / 2] = hight + low;
	}

//	LOGE("uid is : %s", uidStr);

	jint ret = -1;

	if (filehandle) {
		int (*funcPtrB)(char, char *, char *, int) = NULL;
		funcPtrB = dlsym(filehandle, "picc_mifare_auth");
		if (funcPtrB) {
			LOGE("uid is : %d", mode_ab);

			LOGE("len is : %d", uidStrLen);
			LOGE("len is : %d", block);
			if (uidStrLen / 2 - 4 >= 0) {
				LOGE("uid is : %s", "funcptrb<<<<<<<<============before");
				ret = funcPtrB(mode_ab, key, &uid[uidStrLen / 2 - 4], block);
				LOGE("uid is : %s", "funcptrb<<<<<<<<============after");
			}
		} else {
			ret = -1;
		}
	} else {
		ret = -2;
	}

	if (keyStr != NULL)
		free(keyStr);
	if (uidStr != NULL)
		free(uidStr);
	return ret;
}

/*
 * picc_mifare_read(int block,char * data);
 */
jstring Java_com_example_jni_1test_jni_JniClient_picc_1mifare_1read(JNIEnv *env,
		jclass thiz, jint block, jstring b) {
	jstring ret = (*env)->NewStringUTF(env, "empty");
	char read_data[20] = { 0 };
	//__android_log_print(ANDROID_LOG_ERROR, "com.example.jni_test",
	//		"_____  picc_mifare_read  _____");

	if (filehandle) {
		int (*funcPtrB)(int, char *) = NULL;
		funcPtrB = dlsym(filehandle, "picc_mifare_read");
		if (funcPtrB) {
			if (funcPtrB(block, read_data) == 0) {
				char readStr[40] = { 0 };
				int readLen = 16;
				int m;
				int n = 0;
				for (m = 0; m < readLen; m++) {
					sprintf(readStr + n, "%02x", read_data[m]);
					n += 2;
				}
				//			LOGE("0xread is : %s", read_data);
				//			LOGE("read is : %s", readStr);
				//			LOGE("readLen is : %d", strlen(readStr));
				ret = (*env)->NewStringUTF(env, readStr);
				//			LOGE("after is : %s", "ret after");
			}
		} else {
			ret = (*env)->NewStringUTF(env, "-1");
		}
	} else {
		ret = (*env)->NewStringUTF(env, "-2");
	}
//	LOGE("after is : %s", "ret after1");
	return ret;
}

/*
 * picc_mifare_write(int block,char * data);
 */
jint Java_com_example_jni_1test_jni_JniClient_picc_1mifare_1write(JNIEnv *env,
		jclass thiz, jint block, jstring dataJstr) {
	jint ret = 0;
//	__android_log_print(ANDROID_LOG_ERROR, "com.example.jni_test",
//			"_____  picc_mifare_write  _____");
	char * dataStr = NULL;
	jclass clsstring = (*env)->FindClass(env, "java/lang/String");
	jstring strencode = (*env)->NewStringUTF(env, "utf-8");
	jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes",
			"(Ljava/lang/String;)[B");
	jbyteArray barr = (jbyteArray) (*env)->CallObjectMethod(env, dataJstr, mid,
			strencode);
	jsize alen = (*env)->GetArrayLength(env, barr);
	jbyte* ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
	if (alen > 0) {
		dataStr = (char*) malloc(alen + 1);
		memset(dataStr, 0, alen + 1);
		memcpy(dataStr, ba, alen);
		dataStr[alen] = 0;
	}
	(*env)->ReleaseByteArrayElements(env, barr, ba, 0);
	int dataStrLen;
	dataStrLen = strlen(dataStr);
	char data[dataStrLen / 2];
	int m;
	for (m = 0; m < dataStrLen - 1; m += 2) {
		int hight =
				(dataStr[m] - '0') >= 10 ?
						(dataStr[m] - 'a' + 10) << 4 : (dataStr[m] - '0') << 4;
		int low =
				(dataStr[m + 1] - '0') >= 10 ?
						(dataStr[m + 1] - 'a' + 10) : (dataStr[m + 1] - '0');
		data[m / 2] = hight + low;
	}
	//LOGE("writeData is : %s", dataStr);
//	LOGE("writeData is : %s", data);

	if (filehandle) {
		int (*funcPtrB)(int, char *) = NULL;
		funcPtrB = dlsym(filehandle, "picc_mifare_write");
		if (funcPtrB) {
			ret = funcPtrB(block, data);
		} else {
			ret = -1;
		}
	} else {
		ret = -2;
	}

	if (dataStr != NULL)
		free(dataStr);
	return ret;
}

/*
 * picc_iso_apdu(char *apdu,int apdu_len,char *cpdu,int * cpdu_len);
 */
jstring Java_com_example_jni_1test_jni_JniClient_picc_1iso_1apdu(JNIEnv *env,
		jclass thiz, jstring selectJstr) {
	char cpdu[300] = { 0x00 };
	int cpdu_len = 0;

	char * selectStr = NULL;
	jclass clsstring = (*env)->FindClass(env, "java/lang/String");
	jstring strencode = (*env)->NewStringUTF(env, "utf-8");
	jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes",
			"(Ljava/lang/String;)[B");
	jbyteArray barr = (jbyteArray) (*env)->CallObjectMethod(env, selectJstr,
			mid, strencode);
	jsize alen = (*env)->GetArrayLength(env, barr);
	jbyte* ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
	if (alen > 0) {
		selectStr = (char*) malloc(alen + 1);
		memset(selectStr, 0, alen + 1);
		memcpy(selectStr, ba, alen);
		selectStr[alen] = 0;
	}
	(*env)->ReleaseByteArrayElements(env, barr, ba, 0);
	int dataStrLen;
	dataStrLen = strlen(selectStr);
	char select_file[dataStrLen / 2];
	int m;
	for (m = 0; m < dataStrLen - 1; m += 2) {
		int hight =
				(selectStr[m] - '0') >= 10 ?
						(selectStr[m] - 'a' + 10) << 4 :
						(selectStr[m] - '0') << 4;
		int low =
				(selectStr[m + 1] - '0') >= 10 ?
						(selectStr[m + 1] - 'a' + 10) :
						(selectStr[m + 1] - '0');
		select_file[m / 2] = hight + low;
	}
	//LOGE("writeData is : %s", selectStr);
//	LOGE("writeData is : %s", select_file);

	jstring ret;
//	__android_log_print(ANDROID_LOG_ERROR, "com.example.jni_test",
	//		"_____  picc_iso_apdu  _____");

	if (filehandle) {
		int (*funcPtrB)(char *, int, char *, int *) = NULL;
		funcPtrB = dlsym(filehandle, "picc_iso_apdu");
		if (funcPtrB) {
			if (funcPtrB(select_file, sizeof(select_file), cpdu, &cpdu_len)
					== 0) {
				char readStr[cpdu_len * 2];
				int m;
				int n = 0;
				for (m = 0; m < cpdu_len; m++) {
					sprintf(readStr + n, "%02x", cpdu[m]);
					n += 2;
				}
				//			LOGE("0xread is : %s", cpdu);
				//			LOGE("read is : %s", readStr);
				//			LOGE("readLen is : %d", strlen(readStr));
				ret = (*env)->NewStringUTF(env, readStr);
			} else {
				ret = (*env)->NewStringUTF(env, "-read fail");
			}
		} else {
			ret = (*env)->NewStringUTF(env, "-1");
		}
	} else {
		ret = (*env)->NewStringUTF(env, "-2");
	}

	if (selectStr != NULL)
		free(selectStr);
	return ret;
}

