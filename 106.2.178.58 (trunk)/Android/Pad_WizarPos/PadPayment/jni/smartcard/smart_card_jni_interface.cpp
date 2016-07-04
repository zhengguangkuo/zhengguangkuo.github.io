/*
 * smart_card_jni_interface.cpp
 *
 *  Created on: 2012-7-20
 *      Author: yaomaobiao
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#include <dlfcn.h>
#include <semaphore.h>
#include <unistd.h>
#include <errno.h>

#include <jni.h>
#include "hal_sys_log.h"
#include "smart_card_interface.h"
#include "event_queue.h"
#include "smart_card_event.h"

typedef struct smart_card_hal_interface
{
	smart_card_init init;
	smart_card_terminate terminate;
	smart_card_query_max_number query_max_number;
	smart_card_query_presence query_presence;
	smart_card_open open;
	smart_card_close close;
	smart_card_power_on power_on;
	smart_card_power_off power_off;
	smart_card_set_slot_info set_slot_info;
	smart_card_transmit transmit;
	smart_card_mc_read  mc_read;
	smart_card_mc_write mc_write;
	smart_card_mc_verifiy_data	mc_verify_data;

	CEventQueue<CSmartCardEvent>* pEventQueue;

	void* pSoHandle;
}SMART_CARD_HAL_INSTANCE;

static SMART_CARD_HAL_INSTANCE* g_pSmartCardInstance = NULL;

static void smart_card_event_notify(void* pUserData, int nSlotIndex, int nEvent)
{
	int nResult = -1;
	hal_sys_info("nSlotIndex = %d nEvent  = %d\n", nSlotIndex, nEvent);
	SMART_CARD_HAL_INSTANCE* pSmartCardInstance = (SMART_CARD_HAL_INSTANCE*)pUserData;
	CSmartCardEvent event(nEvent, nSlotIndex);
	nResult = pSmartCardInstance->pEventQueue->push_back(event);
	return;
}


jint JNICALL native_smart_card_init(JNIEnv* env, jclass obj)
{
	hal_sys_info("enter native_smart_card_init!");
	int nResult = -1;
	char *pError = NULL;

	if(g_pSmartCardInstance == NULL)
	{
		void* pHandle = dlopen("libwizarposHAL.so", RTLD_LAZY);
		if (!pHandle)
		{
			hal_sys_error("%s\n", dlerror());
			return -1;
		}

		g_pSmartCardInstance = new SMART_CARD_HAL_INSTANCE();
		g_pSmartCardInstance->pEventQueue = new CEventQueue<CSmartCardEvent>();

		g_pSmartCardInstance->init = (smart_card_init)dlsym(pHandle, "smart_card_init");
		if(g_pSmartCardInstance->init == NULL)
			goto smart_card_module_init_clean;

		g_pSmartCardInstance->terminate = (smart_card_terminate)dlsym(pHandle, "smart_card_terminate");
		if(g_pSmartCardInstance->terminate == NULL)
			goto smart_card_module_init_clean;

		g_pSmartCardInstance->query_max_number = (smart_card_query_max_number)dlsym(pHandle, "smart_card_query_max_number");
		if(g_pSmartCardInstance->query_max_number == NULL)
			goto smart_card_module_init_clean;

		g_pSmartCardInstance->query_presence = (smart_card_query_presence)dlsym(pHandle, "smart_card_query_presence");
		if(g_pSmartCardInstance->query_presence == NULL)
			goto smart_card_module_init_clean;

		g_pSmartCardInstance->open = (smart_card_open)dlsym(pHandle, "smart_card_open");
		if(g_pSmartCardInstance->open == NULL)
			goto smart_card_module_init_clean;

		g_pSmartCardInstance->close = (smart_card_close)dlsym(pHandle, "smart_card_close");
		if(g_pSmartCardInstance->close == NULL)
			goto smart_card_module_init_clean;

		g_pSmartCardInstance->power_on = (smart_card_power_on)dlsym(pHandle, "smart_card_power_on");
		if(g_pSmartCardInstance->power_on == NULL)
			goto smart_card_module_init_clean;

		g_pSmartCardInstance->power_off = (smart_card_power_off)dlsym(pHandle, "smart_card_power_off");
		if(g_pSmartCardInstance->power_off == NULL)
			goto smart_card_module_init_clean;

		g_pSmartCardInstance->set_slot_info = (smart_card_set_slot_info)dlsym(pHandle, "smart_card_set_slot_info");
		if(g_pSmartCardInstance->set_slot_info == NULL)
			goto smart_card_module_init_clean;

		g_pSmartCardInstance->transmit = (smart_card_transmit)dlsym(pHandle, "smart_card_transmit");
		if(g_pSmartCardInstance->transmit == NULL)
			goto smart_card_module_init_clean;

		g_pSmartCardInstance->mc_read = (smart_card_mc_read)dlsym(pHandle, "smart_card_mc_read");
		if(g_pSmartCardInstance->mc_read == NULL)
		{
			hal_sys_error("failed in finding smart_card_mc_read()!\n");
			goto smart_card_module_init_clean;
		}

		g_pSmartCardInstance->mc_write = (smart_card_mc_write)dlsym(pHandle, "smart_card_mc_write");
		if(g_pSmartCardInstance->mc_write == NULL)
		{
			hal_sys_error("failed in finding smart_card_mc_write()!\n");
			goto smart_card_module_init_clean;
		}

		g_pSmartCardInstance->mc_verify_data = (smart_card_mc_verifiy_data)dlsym(pHandle, "smart_card_mc_verify_data");
		if(g_pSmartCardInstance->mc_verify_data == NULL)
		{
			hal_sys_error("failed in finding smart_card_mc_verifiy_data()!\n");
			goto smart_card_module_init_clean;
		}

		g_pSmartCardInstance->pSoHandle = pHandle;

		nResult = g_pSmartCardInstance->init();
	}
	return 0;

smart_card_module_init_clean:
	delete g_pSmartCardInstance->pEventQueue;
	delete g_pSmartCardInstance;
	g_pSmartCardInstance = NULL;
	return -1;
}

jint JNICALL native_smart_card_terminate(JNIEnv* env, jclass obj)
{
	int nResult = -1;
	hal_sys_info("enter native_smart_card_terminate!");
	if(g_pSmartCardInstance == NULL)
		return -1;

	nResult = g_pSmartCardInstance->terminate();
	dlclose(g_pSmartCardInstance->pSoHandle);

	if(g_pSmartCardInstance->pEventQueue)
		delete g_pSmartCardInstance->pEventQueue;
	delete g_pSmartCardInstance;

	g_pSmartCardInstance = NULL;
	return nResult;
}

jint JNICALL native_smart_card_poll_event(JNIEnv* env, jclass obj, jint nTimeout_MS, jobject objEvent)
{
	int nResult = -1;
	CSmartCardEvent event;

	if(g_pSmartCardInstance == NULL)
		return -1;

	nResult = g_pSmartCardInstance->pEventQueue->pop_front(event, nTimeout_MS);

	if(nResult >= 0)
	{
		/*
		hal_sys_info("*********************************************************************\n");
		hal_sys_info("success got data!\n");
		hal_sys_info("*********************************************************************\n");
		event.explore();
		*/

		jclass clazz = env->GetObjectClass(objEvent);
		if(0 == clazz)
			return -1;

		jfieldID fidEventID = env->GetFieldID(clazz, "nEventID", "I");
		env->SetIntField(objEvent, fidEventID, event.m_nEventID);

		jfieldID fidSlotIndex = env->GetFieldID(clazz, "nSlotIndex", "I");
		env->SetIntField(objEvent, fidSlotIndex, event.m_nSlotIndex);
	}
	return nResult;
}

jint JNICALL native_smart_card_query_max_number(JNIEnv* env, jclass obj)
{
	//hal_sys_info("enter native_smart_card_query_max_number\n");
	if(g_pSmartCardInstance == NULL)
		return -1;
	return g_pSmartCardInstance->query_max_number();
}

jint JNICALL native_smart_card_query_presence(JNIEnv* env, jclass obj, jint nSlotIndex)
{
	//hal_sys_info("enter native_smart_card_query_presence, nSlotIndex = %d\n", nSlotIndex);
	if(g_pSmartCardInstance == NULL)
		return -1;
	int nResult = g_pSmartCardInstance->query_presence(nSlotIndex);
	hal_sys_info("g_pSmartCardInstance->query_presence(nSlotIndex) return value = %d\n", nResult);
	return nResult;
}

jint JNICALL native_smart_card_open(JNIEnv* env, jclass obj, jint nSlotIndex)
{
	//hal_sys_info("enter native_smart_card_open, nSlotIndex = %d\n", nSlotIndex);
	if(g_pSmartCardInstance == NULL)
		return -1;
	int nResult = g_pSmartCardInstance->open(nSlotIndex, smart_card_event_notify, g_pSmartCardInstance);
	//hal_sys_info("leave native_smart_card_open, nSlotIndex = %d\n", nSlotIndex);
	return nResult;
}

jint JNICALL native_smart_card_close(JNIEnv* env, jclass obj, jint Handle)
{
	//hal_sys_info("enter native_smart_card_close, Handle = %d\n", Handle);
	if(g_pSmartCardInstance == NULL)
		return -1;

	return g_pSmartCardInstance->close(Handle);
}
	/*
	 * return value : >= 0 : ATR length
	 * 				  < 0 : error code
	 */
static const char* strSlotField_Short[] =
{
		"FIDI", "EGT", "WI", "WTX", "EDC", "protocol", "power", "conv", "IFSC",
};
static const char* strSlotField_Long[] =
{
		"cwt", "bwt", "nSlotInfoItem",
};

jint JNICALL native_smart_card_power_on(JNIEnv* env, jclass obj, jint Handle, jbyteArray byteArrayATR, jobject objSlotInfo)
{
	jint i = 0;
	jint nResult = -1;
	SMART_CARD_SLOT_INFO slot_info;
	if(g_pSmartCardInstance == NULL)
		return -1;

	memset(&slot_info, 0, sizeof(SMART_CARD_SLOT_INFO));

	//hal_sys_info("enter native_smart_card_power_on()...\n");
	jbyte* pElements = env->GetByteArrayElements(byteArrayATR, 0);
	unsigned int nLength = (unsigned int)env->GetArrayLength(byteArrayATR);
	nResult = g_pSmartCardInstance->power_on(Handle, (unsigned char*)pElements, &nLength, &slot_info);
	hal_sys_info("power_on() return value = %d\n", nResult);
	if(nResult >= 0)
	{
		jclass clazz = env->GetObjectClass(objSlotInfo);
		if(0 == clazz)
		{
			env->ReleaseByteArrayElements(byteArrayATR, pElements, 0);
			return -1;
		}

		unsigned char* pTemp = (unsigned char*)&slot_info;
		for(i = 0; i < sizeof(strSlotField_Short)/sizeof(strSlotField_Short[0]); i++)
		{
			jfieldID fid = env->GetFieldID(clazz, strSlotField_Short[i], "S");
			env->SetShortField(objSlotInfo, fid, (jshort)(*pTemp++));
		}
		unsigned int* pTempInt = (unsigned int*)&slot_info;
		pTempInt += 3;
		for(i = 0; i < sizeof(strSlotField_Long) / sizeof(strSlotField_Long[0]); i++)
		{
			jfieldID fid = env->GetFieldID(clazz, strSlotField_Long[i], "J");
			env->SetLongField(objSlotInfo, fid, (unsigned int)(*pTempInt++));
		}
	}
	env->ReleaseByteArrayElements(byteArrayATR, pElements, 0);
	//hal_sys_info("leave native_smart_card_power_on()...\n");
	return nResult >= 0 ? nLength : nResult;
}

jint JNICALL native_smart_card_power_off(JNIEnv* env, jclass obj, jint Handle)
{
	if(g_pSmartCardInstance == NULL)
		return -1;
	return g_pSmartCardInstance->power_off(Handle);
}

jint JNICALL native_smart_card_set_slot_info(JNIEnv* env, jclass obj, jint Handle, jobject objSlotInfo)
{
	jint i = 0;
	jint nResult = -1;
	SMART_CARD_SLOT_INFO slot_info;
	if(g_pSmartCardInstance == NULL)
		return -1;

	jclass clazz = env->GetObjectClass(objSlotInfo);
	if(0 == clazz)
		return -1;

	unsigned char* pTemp = (unsigned char*)&slot_info;
	for(i = 0; i < sizeof(strSlotField_Short)/sizeof(strSlotField_Short[0]); i++)
	{
		jfieldID fid = env->GetFieldID(clazz, strSlotField_Short[i], "S");
		*pTemp++ = (unsigned char)env->GetShortField(objSlotInfo, fid);
		//env->SetShortField(objSlotInfo, fid, (jshort)(*pTemp++));
	}
	unsigned int* pTempInt = (unsigned int*)&slot_info;
	pTempInt += 3;
	for(i = 0; i < sizeof(strSlotField_Long) / sizeof(strSlotField_Long[0]); i++)
	{
		jfieldID fid = env->GetFieldID(clazz, strSlotField_Long[i], "J");
		*pTempInt++ = (unsigned int)env->GetLongField(objSlotInfo, fid);
		//env->SetLongField(objSlotInfo, fid, (unsigned int)(*pTempInt++));
	}
	nResult = g_pSmartCardInstance->set_slot_info(Handle, &slot_info);
	return nResult;
}


/*
 * return value : >= 0 : response data length
 * 				  < 0 : error code
 */
jint JNICALL native_smart_card_transmit(JNIEnv* env, jclass obj, jint Handle, jbyteArray byteArrayAPDU, jint nAPDULength, jbyteArray byteArrayResponse)
{
	int nResult = -1;
	if(g_pSmartCardInstance == NULL)
		return -1;

	jbyte* strAPDUCmd = env->GetByteArrayElements(byteArrayAPDU, 0);
	unsigned int nAPDUCmdLength = (unsigned int)env->GetArrayLength(byteArrayAPDU);

	jbyte* strResponse = env->GetByteArrayElements(byteArrayResponse, 0);
	unsigned int nResponseBufferLength = (unsigned int)env->GetArrayLength(byteArrayResponse);

	nResult = g_pSmartCardInstance->transmit(Handle, (unsigned char*)strAPDUCmd, nAPDUCmdLength, (unsigned char*)strResponse, &nResponseBufferLength);

	env->ReleaseByteArrayElements(byteArrayAPDU, strAPDUCmd, 0);
	env->ReleaseByteArrayElements(byteArrayResponse, strResponse, 0);

	hal_sys_info("transmit return value : nResult = 0x%X\n", nResult);

	return nResult < 0 ? nResult : nResponseBufferLength;
}


jint JNICALL native_smart_card_mc_read(JNIEnv* env, jclass obj, jint Handle, jint nAreaType, jbyteArray byteArryData, jint nDataLength, jint nStartAddress)
{
	hal_sys_info("enter native_smart_card_mc_read()\n");
	int nResult = -1;
	if(g_pSmartCardInstance == NULL)
		return -1;
	jbyte* pDataBuffer = env->GetByteArrayElements(byteArryData, 0);
	nResult = g_pSmartCardInstance->mc_read(Handle, nAreaType, (unsigned char*)pDataBuffer, nDataLength, (unsigned char)nStartAddress);
	env->ReleaseByteArrayElements(byteArryData, pDataBuffer, 0);
	hal_sys_info("leave native_smart_card_mc_read() nResult = %d\n", nResult);
	return nResult;
}

jint JNICALL native_smart_card_mc_write(JNIEnv* env, jclass obj, jint Handle, jint nAreaType, jbyteArray byteArryData, jint nDataLength, jint nStartAddress)
{
	hal_sys_info("enter native_smart_card_mc_write()\n");
	int nResult = -1;
	if(g_pSmartCardInstance == NULL)
		return -1;
	jbyte* pDataBuffer = env->GetByteArrayElements(byteArryData, 0);
	nResult = g_pSmartCardInstance->mc_write(Handle, nAreaType, (unsigned char*)pDataBuffer, nDataLength, (unsigned char)nStartAddress);
	env->ReleaseByteArrayElements(byteArryData, pDataBuffer, 0);
	hal_sys_info("leave native_smart_card_mc_write() nResult = %d\n", nResult);
	return nResult;
}

jint JNICALL native_smart_card_mc_verify_data(JNIEnv* env, jclass obj, jint Handle, jbyteArray byteArryData, jint nDataLength)
{
	int nResult = -1;
	if(g_pSmartCardInstance == NULL)
		return -1;
	hal_sys_info("enter native_smart_card_mc_verify_data()\n");
	jbyte* pDataBuffer = env->GetByteArrayElements(byteArryData, 0);
	nResult = g_pSmartCardInstance->mc_verify_data(Handle, (unsigned char*)pDataBuffer, nDataLength);
	env->ReleaseByteArrayElements(byteArryData, pDataBuffer, 0);
	hal_sys_info("leave native_smart_card_mc_verify_data() nResult = %d\n", nResult);
	return nResult;

	return 0;
}

static const char* g_pJNIREG_CLASS = "com/wizarpos/apidemo/jniinterface/SmartCardInterface";


/*
 * Table of methods associated with a single class
 * Please pay attention to the signature string of the function, don't forget the ';'
 */
 //* Signature: (Lcom/huiyin/struct/EmployeeInfo;)I
static JNINativeMethod g_Methods[] =
{
	{"SmartCardInit",			"()I",																(void*)native_smart_card_init},
	{"SmartCardTerminate",		"()I",																(void*)native_smart_card_terminate},
	{"SmartCardPollEvent",		"(ILcom/wizarpos/apidemo/jniinterface/SmartCardEvent;)I",			(void*)native_smart_card_poll_event},
	{"SmartCardQueryMaxNumber",	"()I",																(void*)native_smart_card_query_max_number},
	{"SmartCardQueryPresence",	"(I)I",																(void*)native_smart_card_query_presence},
	{"SmartCardOpen",			"(I)I",																(void*)native_smart_card_open},
	{"SmartCardClose",			"(I)I",																(void*)native_smart_card_close},
	{"SmartCardPowerOn",		"(I[BLcom/wizarpos/apidemo/jniinterface/SmartCardSlotInfo;)I",		(void*)native_smart_card_power_on},
	{"SmartCardPowerOff",		"(I)I",																(void*)native_smart_card_power_off},
	{"SmartCardSetSlotInfo",	"(ILcom/wizarpos/apidemo/jniinterface/SmartCardSlotInfo;)I",		(void*)native_smart_card_set_slot_info},
	{"SmartCardTransmit",		"(I[BI[B)I",														(void*)native_smart_card_transmit},
#if 0
	{"SmartCardMCRead",			"(II[BII)I",														(void*)native_smart_card_mc_read},
	{"SmartCardMCWrite",		"(II[BII)I",														(void*)native_smart_card_mc_write},
	{"SmartCardMCVerify", 		"(I[BI)I",															(void*)native_smart_card_mc_verify_data}
#endif
};


const char* smartcard_get_class_name()
{
	return g_pJNIREG_CLASS;
}

JNINativeMethod* smartcard_get_methods(int* pCount)
{
	*pCount = sizeof(g_Methods) /sizeof(g_Methods[0]);
	return g_Methods;
}




