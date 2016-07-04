/*
 * contactless_card_jni_interface.cpp
 *
 *  Created on: 2012-7-24
 *      Author: yaomaobiao
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <semaphore.h>
#include <unistd.h>
#include <errno.h>
#include <dlfcn.h>

#include "hal_sys_log.h"
#include "contactless_card_interface.h"
#include "contactless_card_jni_interface.h"
#include "contactless_card_event.h"
#include "event_queue.h"

typedef struct contactless_card_hal_interface
{
	contactless_card_open 					open;
	contactless_card_close 					close;
	contactless_card_search_target_begin	search_target_begin;
	contactless_card_search_target_end		search_target_end;
	contactless_card_attach_target			attach_target;
	contactless_card_detach_target			detach_target;
	contactless_card_transmit				transmit;
	contactless_card_send_control_command	send_control_command;

	contactless_card_mc_verify_pin			mc_verify_pin;
	contactless_card_mc_read				mc_read;
	contactless_card_mc_write				mc_write;
	contactless_card_15693_read				cts_15693_read;
	contactless_card_15693_write			cts_15693_write;
	CEventQueue<CContactlessCardEvent>* pEventQueue;

	int pCardHandle;
	void* pSoHandle;
	//sem_t sem;
}SMART_CARD_HAL_INSTANCE;

static SMART_CARD_HAL_INSTANCE* g_pContactlessCard = NULL;

static void contactless_card_callback(void* pUserData, int nEvent, unsigned char* pEventData, int nEventDataLength)
{
	int nResult = -1;
	hal_sys_info("enter contactless_card_callback()...\n");
	SMART_CARD_HAL_INSTANCE* pContactlessCard = (SMART_CARD_HAL_INSTANCE*)pUserData;
	CContactlessCardEvent event(nEvent, pEventData, nEventDataLength);
	nResult = pContactlessCard->pEventQueue->push_back(event);
	hal_sys_info("leave contactless_card_callback()...");

	return;
}

jint JNICALL native_contactless_card_open(JNIEnv* env, jclass obj)
{
	void* pSoHandle = NULL;
	void* pCardHandle = NULL;
	int nErrorCode = -1;

	hal_sys_info("enter native_contactless_card_open()...%d\n",(g_pContactlessCard == NULL));
	if(g_pContactlessCard == NULL)
	{
		pSoHandle = dlopen("libwizarposHAL.so", RTLD_LAZY);
		if (!pSoHandle)
		{
			hal_sys_error("%s\n", dlerror());
		    return -1;
		}

		g_pContactlessCard = new SMART_CARD_HAL_INSTANCE();
//		sem_init(&(g_pContactlessCard->sem), 0, 0);

		g_pContactlessCard->pEventQueue = new CEventQueue<CContactlessCardEvent>();

		g_pContactlessCard->open = (contactless_card_open)dlsym(pSoHandle, "contactless_card_open");
		if(g_pContactlessCard->open == NULL)
			goto card_open_clean;

		g_pContactlessCard->close = (contactless_card_close)dlsym(pSoHandle, "contactless_card_close");
		if(g_pContactlessCard->close == NULL)
			goto card_open_clean;

		g_pContactlessCard->search_target_begin = (contactless_card_search_target_begin)dlsym(pSoHandle, "contactless_card_search_target_begin");
		if(g_pContactlessCard->search_target_begin == NULL)
			goto card_open_clean;

		g_pContactlessCard->search_target_end = (contactless_card_search_target_end)dlsym(pSoHandle, "contactless_card_search_target_end");
		if(g_pContactlessCard->search_target_end == NULL)
			goto card_open_clean;

		g_pContactlessCard->attach_target = (contactless_card_attach_target)dlsym(pSoHandle, "contactless_card_attach_target");
		if(g_pContactlessCard->attach_target == NULL)
			goto card_open_clean;

		g_pContactlessCard->detach_target = (contactless_card_detach_target)dlsym(pSoHandle, "contactless_card_detach_target");
		if(g_pContactlessCard->detach_target == NULL)
			goto card_open_clean;

		g_pContactlessCard->transmit = (contactless_card_transmit)dlsym(pSoHandle, "contactless_card_transmit");
		if(g_pContactlessCard->transmit == NULL)
			goto card_open_clean;

		g_pContactlessCard->send_control_command = (contactless_card_send_control_command)dlsym(pSoHandle, "contactless_card_send_control_command");
		if(g_pContactlessCard->send_control_command == NULL)
			goto card_open_clean;

		g_pContactlessCard->mc_verify_pin = (contactless_card_mc_verify_pin)dlsym(pSoHandle, "contactless_card_mc_verify_pin");
		if(g_pContactlessCard->mc_verify_pin == NULL)
			goto card_open_clean;


		g_pContactlessCard->mc_read = (contactless_card_mc_read)dlsym(pSoHandle, "contactless_card_mc_read");
		if(g_pContactlessCard->mc_read == NULL)
			goto card_open_clean;

		g_pContactlessCard->mc_write = (contactless_card_mc_write)dlsym(pSoHandle, "contactless_card_mc_write");
		if(g_pContactlessCard->mc_write == NULL)
			goto card_open_clean;


		g_pContactlessCard->cts_15693_read = (contactless_card_15693_read)dlsym(pSoHandle, "contactless_card_15693_read");
		if(g_pContactlessCard->cts_15693_read == NULL)
			goto card_open_clean;

		g_pContactlessCard->cts_15693_write = (contactless_card_15693_write)dlsym(pSoHandle, "contactless_card_15693_write");
		if(g_pContactlessCard->cts_15693_write == NULL)
			goto card_open_clean;

		g_pContactlessCard->pSoHandle = pSoHandle;
//		int nResult = -1;
		pCardHandle = g_pContactlessCard->open(contactless_card_callback, g_pContactlessCard, &nErrorCode);
		if(pCardHandle == NULL || nErrorCode < 0)
			goto card_open_clean;
		g_pContactlessCard->pCardHandle = (int)pCardHandle;
	}
		hal_sys_info("mc_write leave native_contactless_card_open()...\n");

	return 0;
card_open_clean:
	if(g_pContactlessCard)
	{
		if(g_pContactlessCard->pEventQueue)
			delete g_pContactlessCard->pEventQueue;
		delete g_pContactlessCard;
		g_pContactlessCard = NULL;
	}
	return -1;
}

jint JNICALL native_contactless_card_close(JNIEnv* env, jclass obj)
{
	if(g_pContactlessCard == NULL)
		return -1;

	g_pContactlessCard->close(g_pContactlessCard->pCardHandle);
	if(g_pContactlessCard->pEventQueue)
		delete g_pContactlessCard->pEventQueue;

	dlclose(g_pContactlessCard->pSoHandle);
	delete g_pContactlessCard;
	g_pContactlessCard = NULL;
	return 0;
}

jint JNICALL native_contactless_card_poll_event(JNIEnv* env, jclass obj, jint nTimeout_MS, jobject objEvent)
{
	int nResult = -1;
	CContactlessCardEvent event;
	if(g_pContactlessCard == NULL)
		return -1;
	hal_sys_info("enter native_contactless_card_poll_event()...\n");
	//sem_wait(&(g_pContactlessCard->sem));
	nResult = g_pContactlessCard->pEventQueue->pop_front(event, nTimeout_MS);

	if(nResult >= 0)
	{
		hal_sys_info("*********************************************************************\n");
		hal_sys_info("success got data!\n");
		hal_sys_info("*********************************************************************\n");
		event.explore();

		jclass clazz = env->GetObjectClass(objEvent);
		if(0 == clazz)
			return -1;

		jfieldID fidEventID = env->GetFieldID(clazz, "nEventID", "I");
		env->SetIntField(objEvent, fidEventID, event.m_nEventID);

		jfieldID fidEventDataLength = env->GetFieldID(clazz, "nEventDataLength", "I");
		env->SetIntField(objEvent, fidEventDataLength, event.m_nEventDataLength);

		jfieldID fidEventData = env->GetFieldID(clazz, "arryEventData", "[B");
		jobject mvdata = env->GetObjectField(objEvent, fidEventData);

		jbyteArray* pArray = reinterpret_cast<jbyteArray*>(&mvdata);
		jbyte* data = env->GetByteArrayElements(*pArray, NULL);
		memcpy(data, event.m_strEventData, event.m_nEventDataLength);
		env->ReleaseByteArrayElements(*pArray, data, 0);
	}
	hal_sys_info("leave native_contactless_card_poll_event()...\n");
	return nResult;
}

jint JNICALL native_contactless_card_search_target_begin(JNIEnv* env, jclass obj, jint nCardMode, jint nFlagSearchAll, jint nTimeout_MS)
{
	if(g_pContactlessCard == NULL)
		return -1;
	hal_sys_info("===========>>>handle=%d",g_pContactlessCard->pCardHandle);
	return g_pContactlessCard->search_target_begin(g_pContactlessCard->pCardHandle, nCardMode, nFlagSearchAll, nTimeout_MS);
}

jint JNICALL native_contactless_card_search_target_end(JNIEnv* env, jclass obj)
{
	int nResult = -1;
	if(g_pContactlessCard == NULL)
		return -1;
	hal_sys_info("enter native_contactless_card_search_target_end()...\n");
	nResult = g_pContactlessCard->search_target_end(g_pContactlessCard->pCardHandle);
	hal_sys_info("leave native_contactless_card_search_target_end()...\n");
	return nResult;
}

jint JNICALL native_contactless_card_attach_target(JNIEnv* env, jclass obj, jbyteArray arryATR)
{
	int nResult = -1;
	if(g_pContactlessCard == NULL)
		return -1;

	hal_sys_info("enter native_contactless_card_attach_target()...\n");
	jbyte* pData = env->GetByteArrayElements(arryATR, NULL);
	jint nATRBufferLength = env->GetArrayLength(arryATR);
	hal_sys_info("nATRBufferLength = %d\n", nATRBufferLength);
	nResult = g_pContactlessCard->attach_target(g_pContactlessCard->pCardHandle, (unsigned char*)pData, nATRBufferLength);
	env->ReleaseByteArrayElements(arryATR, pData, 0);
	hal_sys_info("leave native_contactless_card_attach_target()... nResult = %d\n", nResult);
	return nResult;
}

jint JNICALL native_contactless_card_detach_target(JNIEnv* env, jclass obj)
{
	if(g_pContactlessCard == NULL)
		return -1;
	return g_pContactlessCard->detach_target(g_pContactlessCard->pCardHandle);
}

jint JNICALL native_contactless_card_transmit(JNIEnv* env, jclass obj, jbyteArray arryAPDU, jint nAPDULength, jbyteArray arryResponse)
{
	int nResult = -1;
	if(g_pContactlessCard == NULL)
		return -1;
	hal_sys_info("enter native_contactless_card_transmit()...\n");
	jbyte* pData = env->GetByteArrayElements(arryAPDU, NULL);
	jbyte* pResponse = env->GetByteArrayElements(arryResponse, NULL);
	unsigned int nResponseBufferLength = (unsigned int)(env->GetArrayLength(arryResponse));
	hal_sys_info("nResponseBufferLength = %d\n", nResponseBufferLength);
	nResult = g_pContactlessCard->transmit(g_pContactlessCard->pCardHandle, (unsigned char*)pData, nAPDULength, (unsigned char*)pResponse, &nResponseBufferLength);
	env->ReleaseByteArrayElements(arryAPDU, pData, 0);
	env->ReleaseByteArrayElements(arryResponse, pResponse, 0);
	hal_sys_info("leave native_contactless_card_transmit()...\n");
	return nResult >= 0 ? nResponseBufferLength : nResult;
}

jint JNICALL native_contactless_card_send_control_command(JNIEnv* env, jclass obj, jint nCmdID, jbyteArray arryCmdData, jint nDataLength)
{
	int nResult = -1;

	if(g_pContactlessCard == NULL)
		return -1;

	hal_sys_info("enter native_contactless_card_send_control_command()...\n");
	jbyte* pData = env->GetByteArrayElements(arryCmdData, NULL);
	nResult = g_pContactlessCard->send_control_command(g_pContactlessCard->pCardHandle, (unsigned int)nCmdID, (unsigned char*)pData, (unsigned int)nDataLength);
	env->ReleaseByteArrayElements(arryCmdData, pData, 0);
	hal_sys_info("leave native_contactless_card_send_control_command()...\n");
	return nResult;
}


jint JNICALL native_contactless_card_mc_verify_pin(JNIEnv* env, jclass obj,jint nSectorIndex, jint nPinType, jbyteArray strPin, jint nPinLength)
{
	int nResult = -1;
	int len;
	if(g_pContactlessCard == NULL)
		return -1;

	hal_sys_info("enter native_contactless_card_mc_verify_pin()...\n");
	jbyte* pData = env->GetByteArrayElements(strPin, NULL);
	len = env->GetArrayLength(strPin);
	nResult = g_pContactlessCard->mc_verify_pin(g_pContactlessCard->pCardHandle,(unsigned int)nSectorIndex,(unsigned int)nPinType, (unsigned char*)pData, (unsigned int)nPinLength);

	env->ReleaseByteArrayElements(strPin, pData, 0);

	hal_sys_info("native verify nResult = %d\n",nResult);

	return nResult;
}

jint JNICALL native_contactless_card_mc_read(JNIEnv* env, jclass obj,jint nSectorIndex, jint nBlockIndex, jbyteArray pDataBuffer, jint nDataBufferLength)
{
	int nResult = -1;

	if(g_pContactlessCard == NULL)
		return -1;

	hal_sys_info("enter native_contactless_card_mc_read()...\n");
	jbyte* pData = env->GetByteArrayElements(pDataBuffer, NULL);
	nResult = g_pContactlessCard->mc_read(g_pContactlessCard->pCardHandle,(unsigned int)nSectorIndex,(unsigned int)nBlockIndex, (unsigned char*)pData, (unsigned int)nDataBufferLength);
	env->ReleaseByteArrayElements(pDataBuffer, pData, 0);
	hal_sys_info("leave native_contactless_card_mc_read()...\n");
	return nResult;
}

jint JNICALL native_contactless_card_mc_write(JNIEnv* env, jclass obj ,jint nSectorIndex, jint nBlockIndex, jbyteArray pData, jint nDataLength)
{
	int nResult = -1;

	if(g_pContactlessCard == NULL)
		return -1;

	hal_sys_info("enter native_contactless_card_mc_write()...\n");
	jbyte* pWriteData = env->GetByteArrayElements(pData, NULL);
	nResult = g_pContactlessCard->mc_write(g_pContactlessCard->pCardHandle,(unsigned int)nSectorIndex,(unsigned int)nBlockIndex, (unsigned char*)pWriteData, (unsigned int)nDataLength);
	env->ReleaseByteArrayElements(pData, pWriteData, 0);
	hal_sys_info("leave native_contactless_card_mc_write()...\n");
	return nResult;
}

jint JNICALL native_contactless_card_15693_read(JNIEnv* env, jclass obj,jint nBlockIndex, jint nBlockCount, jbyteArray pDataBuffer, jint nDataBufferLength)
{
	int nResult = -1;

	if(g_pContactlessCard == NULL)
		return -1;

	hal_sys_info("enter native_contactless_card_15693_read()...\n");
	jbyte* pData = env->GetByteArrayElements(pDataBuffer, NULL);
	hal_sys_info("nBlockIndex = %d ,nBlockCount = %d, nDataBufferLength = %d\n",nBlockIndex,nBlockCount,nDataBufferLength);
	nResult = g_pContactlessCard->cts_15693_read(g_pContactlessCard->pCardHandle,(unsigned int)nBlockIndex,(unsigned int)nBlockCount, (unsigned char*)pData, (unsigned int)nDataBufferLength);
	env->ReleaseByteArrayElements(pDataBuffer, pData, 0);
	hal_sys_info("leave native_contactless_card_15693_read()...\n");
	return nResult;
}

jint JNICALL native_contactless_card_15693_write(JNIEnv* env, jclass obj , jint nBlockIndex, jbyteArray pData, jint nDataLength)
{
	int nResult = -1;

	if(g_pContactlessCard == NULL)
		return -1;

	hal_sys_info("enter native_contactless_card_15693_write()...\n");
	jbyte* pWriteData = env->GetByteArrayElements(pData, NULL);
	hal_sys_info("nDataLength = %d, nBlockIndex = %d\n",nDataLength,nBlockIndex);
	nResult = g_pContactlessCard->cts_15693_write(g_pContactlessCard->pCardHandle,(unsigned int)nBlockIndex, (unsigned char*)pWriteData, (unsigned int)nDataLength);
	env->ReleaseByteArrayElements(pData, pWriteData, 0);
	hal_sys_info("leave native_contactless_card_15693_write()...\n");
	return nResult;
}
