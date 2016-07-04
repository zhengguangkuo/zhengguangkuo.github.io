/*
 * pinpad_jni_interface.cpp
 *
 *  Created on: 2012-8-8
 *      Author: yaomaobiao
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <dlfcn.h>
#include <semaphore.h>
#include <unistd.h>
#include <errno.h>

#include <jni.h>

#include "hal_sys_log.h"
#include "pinpad_jni_interface.h"
#include "pinpad_interface.h"

#define DEBUG	1

#if DEBUG
#include "prove/pinpad_test.h"
#endif

static const char* g_pJNIREG_CLASS = "com/wizarpos/apidemo/jniinterface/PinpadInterface";

typedef struct pinpad_hal_interface
{
	pinpad_open open;
	pinpad_close close;
	pinpad_show_text show_text;
	pinpad_select_key select_key;
	pinpad_encrypt_string encrypt_string;
	pinpad_calculate_pin_block calculate_pin_block;
	pinpad_calculate_mac calculate_mac;
	pinpad_update_user_key update_user_key;
	pinpad_set_pin_length set_pin_length;
	void* pSoHandle;
}PINPAD_HAL_INSTANCE;

static PINPAD_HAL_INSTANCE* g_pPinpadInstance = NULL;

int native_pinpad_open(JNIEnv* env, jclass obj)
{
	int nResult = 0;
	hal_sys_info("native_printer_open() is called");
	if(g_pPinpadInstance == NULL)
	{
		void* pHandle = dlopen("libwizarposHAL.so", RTLD_LAZY);
		if (!pHandle)
		{
			hal_sys_error("%s\n", dlerror());
			return -1;
		}

		g_pPinpadInstance = new PINPAD_HAL_INSTANCE();

		g_pPinpadInstance->open = (pinpad_open)dlsym(pHandle, "pinpad_open");
		if(g_pPinpadInstance->open == NULL)
			goto pinpad_init_clean;

		g_pPinpadInstance->close = (pinpad_close)dlsym(pHandle, "pinpad_close");
		if(g_pPinpadInstance->close == NULL)
			goto pinpad_init_clean;

		g_pPinpadInstance->show_text = (pinpad_show_text)dlsym(pHandle, "pinpad_show_text");
		if(g_pPinpadInstance->show_text == NULL)
			goto pinpad_init_clean;

		g_pPinpadInstance->select_key = (pinpad_select_key)dlsym(pHandle, "pinpad_select_key");
		if(g_pPinpadInstance->select_key == NULL)
			goto pinpad_init_clean;

		g_pPinpadInstance->encrypt_string = (pinpad_encrypt_string)dlsym(pHandle, "pinpad_encrypt_string");
		if(g_pPinpadInstance->encrypt_string == NULL)
			goto pinpad_init_clean;

		g_pPinpadInstance->calculate_pin_block = (pinpad_calculate_pin_block)dlsym(pHandle, "pinpad_calculate_pin_block");
		if(g_pPinpadInstance->calculate_pin_block == NULL)
			goto pinpad_init_clean;

		g_pPinpadInstance->calculate_mac = (pinpad_calculate_mac)dlsym(pHandle, "pinpad_calculate_mac");
		if(g_pPinpadInstance->calculate_mac == NULL)
			goto pinpad_init_clean;

		g_pPinpadInstance->update_user_key = (pinpad_update_user_key)dlsym(pHandle, "pinpad_update_user_key");
		if(g_pPinpadInstance->update_user_key == NULL)
			goto pinpad_init_clean;

		g_pPinpadInstance->set_pin_length = (pinpad_set_pin_length)dlsym(pHandle, "pinpad_set_pin_length");
		if(g_pPinpadInstance->set_pin_length == NULL)
			goto pinpad_init_clean;

		g_pPinpadInstance->pSoHandle = pHandle;
		nResult = g_pPinpadInstance->open();
	}
	return nResult;
pinpad_init_clean:
	if(g_pPinpadInstance != NULL)
	{
		delete g_pPinpadInstance;
		g_pPinpadInstance = NULL;
	}
	return -1;
}

int native_pinpad_close(JNIEnv* env, jclass obj)
{
	int nResult = -1;
	if(g_pPinpadInstance == NULL)
		return -1;
	nResult = g_pPinpadInstance->close();
	dlclose(g_pPinpadInstance->pSoHandle);
	delete g_pPinpadInstance;
	g_pPinpadInstance = NULL;

	return nResult;
}

int native_pinpad_show_text(JNIEnv* env, jclass obj, jint nLineIndex, jbyteArray arryText, jint nLength, jint nFlagSound)
{
	int nResult = -1;
	if(g_pPinpadInstance == NULL)
		return -1;

	//typedef int (*pinpad_show_text)(int nLineIndex, char* strText, int nLength, int nFlagSound);
	if(arryText == NULL)
		nResult = g_pPinpadInstance->show_text(nLineIndex, NULL, 0, nFlagSound);
	else
	{
		jbyte* pText = env->GetByteArrayElements(arryText, 0);
		nResult = g_pPinpadInstance->show_text(nLineIndex, (char*)pText, nLength, nFlagSound);
		env->ReleaseByteArrayElements(arryText, pText, 0);
	}
	return nResult;
}

int native_pinpad_select_key(JNIEnv* env, jclass obj, jint nKeyType, jint nMasterKeyID, jint nUserKeyID, jint nAlgorith)
{
	int nResult = -1;
	if(g_pPinpadInstance == NULL)
		return -1;

	nResult = g_pPinpadInstance->select_key(nKeyType, nMasterKeyID, nUserKeyID, nAlgorith);
	return nResult;
}

int native_pinpad_encrypt_string(JNIEnv* env, jclass obj, jbyteArray arryPlainText, jint nTextLength, jbyteArray arryCipherTextBuffer)
{
	int nResult = -1;
	if(g_pPinpadInstance == NULL)
		return -1;

	if(arryPlainText == NULL || arryCipherTextBuffer == NULL)
		return -1;

	//typedef int (*pinpad_encrypt_string)(unsigned char* pPlainText, int nTextLength, unsigned char* pCipherTextBuffer, int nCipherTextBufferLength);

	jbyte* pPlainText = env->GetByteArrayElements(arryPlainText, 0);
	jbyte* pCipherTextBuffer = env->GetByteArrayElements(arryCipherTextBuffer, 0);
	jint nCipherTextBufferLength = env->GetArrayLength(arryCipherTextBuffer);

	nResult = g_pPinpadInstance->encrypt_string((unsigned char*)pPlainText, nTextLength, (unsigned char*)pCipherTextBuffer, nCipherTextBufferLength);

	env->ReleaseByteArrayElements(arryPlainText, pPlainText, 0);
	env->ReleaseByteArrayElements(arryCipherTextBuffer, pCipherTextBuffer, 0);
	return nResult;
}

int native_pinpad_calculate_pin_block(JNIEnv* env, jclass obj, jbyteArray arryASCIICardNumber, jint nCardNumberLength, jbyteArray arryPinBlockBuffer, jint nTimeout_MS, jint nFlagSound)
{
	int nResult = -1;
	if(g_pPinpadInstance == NULL)
		return -1;

	if(arryASCIICardNumber == NULL || arryPinBlockBuffer == NULL)
		return -1;

	//typedef int (*pinpad_calculate_pin_block)(unsigned char* pASCIICardNumber, int nCardNumberLength, unsigned char* pPinBlockBuffer, int nPinBlockBufferLength, int nTimeout_MS, int nFlagSound);

	jbyte* pASCIICardNumber = env->GetByteArrayElements(arryASCIICardNumber, 0);
	jbyte* pPinBlockBuffer = env->GetByteArrayElements(arryPinBlockBuffer, 0);
	int nPinBlockBufferLength = env->GetArrayLength(arryPinBlockBuffer);

	nResult = g_pPinpadInstance->calculate_pin_block((unsigned char*)pASCIICardNumber, nCardNumberLength,
			(unsigned char*)pPinBlockBuffer, nPinBlockBufferLength, nTimeout_MS, nFlagSound);

#if DEBUG
	pASCIICardNumber[nCardNumberLength] = 0;
	TEST_ansi_98_pin_block((char*)pASCIICardNumber);
#endif

	env->ReleaseByteArrayElements(arryASCIICardNumber, pASCIICardNumber, 0);
	env->ReleaseByteArrayElements(arryPinBlockBuffer, pPinBlockBuffer, 0);

	return nResult;
}

int native_pinpad_calculate_mac(JNIEnv* env, jclass obj, jbyteArray arryData, jint nDataLength, jint nMACFlag, jbyteArray arryMACOutBuffer)
{
	int nResult = -1;
	if(g_pPinpadInstance == NULL)
		return -1;

	if(arryData == NULL || arryMACOutBuffer == NULL)
		return -1;

	//typedef int (*pinpad_calculate_mac)(unsigned char* pData, int nDataLength, int nMACFlag, unsigned char* pMACOutBuffer, int nMACOutBufferLength);

	jbyte* pData = env->GetByteArrayElements(arryData, 0);
	jbyte* pMACOutBuffer = env->GetByteArrayElements(arryMACOutBuffer, 0);
	int nMACOutBufferLength = env->GetArrayLength(arryMACOutBuffer);

	nResult = g_pPinpadInstance->calculate_mac((unsigned char*)pData, nDataLength, nMACFlag, (unsigned char*)pMACOutBuffer, nMACOutBufferLength);

#if DEBUG
	TEST_cal_mac((unsigned char*)pData, nDataLength, nMACFlag);
#endif

	env->ReleaseByteArrayElements(arryData, pData, 0);
	env->ReleaseByteArrayElements(arryMACOutBuffer, pMACOutBuffer, 0);

	return nResult;
}

int native_pinpad_update_user_key(JNIEnv* env, jclass obj, jint nMasterKeyID, jint nUserKeyID, jbyteArray arryCipherNewUserKey, jint nCipherNewUserKeyLength)
{
	int nResult = -1;
	if(g_pPinpadInstance == NULL)
		return -1;

	if(arryCipherNewUserKey == NULL)
		return -1;

	//typedef int (*pinpad_update_user_key)(int nMasterKeyID, int nUserKeyID, unsigned char* pCipherNewUserKey, int nCipherNewUserKeyLength);
	jbyte* pCipherNewUserKey = env->GetByteArrayElements(arryCipherNewUserKey, 0);
	nResult = g_pPinpadInstance->update_user_key(nMasterKeyID, nUserKeyID, (unsigned char*)pCipherNewUserKey, nCipherNewUserKeyLength);
	env->ReleaseByteArrayElements(arryCipherNewUserKey, pCipherNewUserKey, 0);

	return nResult;
}

int native_pinpad_set_pin_length(JNIEnv* env, jclass obj, jint nLength, jint nFlag)
{
	int nResult = -1;
	if(g_pPinpadInstance == NULL)
		return -1;
	nResult = g_pPinpadInstance->set_pin_length(nLength, nFlag);
	return nResult;
}

static JNINativeMethod g_Methods[] =
{
	{"PinpadOpen",					"()I",									(void*)native_pinpad_open},
	{"PinpadClose",					"()I",									(void*)native_pinpad_close},
	{"PinpadShowText",				"(I[BII)I",								(void*)native_pinpad_show_text},
	{"PinpadSelectKey",				"(IIII)I",								(void*)native_pinpad_select_key},
	{"PinpadEncryptString",			"([BI[B)I",								(void*)native_pinpad_encrypt_string},
	{"PinpadCalculatePinBlock",		"([BI[BII)I",							(void*)native_pinpad_calculate_pin_block},
	{"PinpadCalculateMac",			"([BII[B)I",							(void*)native_pinpad_calculate_mac},
	{"PinpadUpdateUserKey",			"(II[BI)I",								(void*)native_pinpad_update_user_key},
	{"PinpadSetPinLength",			"(II)I",								(void*)native_pinpad_set_pin_length},
};

const char* pinpad_get_class_name()
{
	return g_pJNIREG_CLASS;
}

JNINativeMethod* pinpad_get_methods(int* pCount)
{
	*pCount = sizeof(g_Methods) /sizeof(g_Methods[0]);
	return g_Methods;
}
