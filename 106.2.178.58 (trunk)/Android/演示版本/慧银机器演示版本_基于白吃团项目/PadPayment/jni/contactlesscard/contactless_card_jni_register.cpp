/*
 * contactless_card_jni_register.cpp
 *
 *  Created on: 2012-7-24
 *      Author: yaomaobiao
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#include <jni.h>

#include "hal_sys_log.h"
#include "contactless_card_jni_interface.h"


#define JNIREG_CLASS "com/wizarpos/apidemo/jniinterface/ContactlessInterface"

/*
 * Maybe, this table should be defined in the file contactless_card_jni_interface.cpp
 * and then, try to get the pointer by a public method!
 */
static JNINativeMethod g_Methods[] =
{
	{"Open",					"()I",															(void*)native_contactless_card_open},
	{"Close",					"()I",															(void*)native_contactless_card_close},
	{"PollEvent",				"(ILcom/wizarpos/apidemo/jniinterface/ContactlessEvent;)I",		(void*)native_contactless_card_poll_event},
	{"SearchTargetBegin",		"(III)I",														(void*)native_contactless_card_search_target_begin},
	{"SearchTargetEnd",			"()I",															(void*)native_contactless_card_search_target_end},
	{"AttachTarget",			"([B)I",														(void*)native_contactless_card_attach_target},
	{"DetachTarget",			"()I",															(void*)native_contactless_card_detach_target},
	{"Transmit",				"([BI[B)I",														(void*)native_contactless_card_transmit},
	{"SendControlCommand",		"(I[BI)I",														(void*)native_contactless_card_send_control_command},
	{"VerifyPinMemory",			"(II[BI)I",														(void*)native_contactless_card_mc_verify_pin},
	{"ReadMemory",				"(II[BI)I",														(void*)native_contactless_card_mc_read},
	{"WriteMemory",				"(II[BI)I",														(void*)native_contactless_card_mc_write},
	{"Read15693Memory",			"(II[BI)I",														(void*)native_contactless_card_15693_read},
	{"Write15693Memory",		"(I[BI)I",														(void*)native_contactless_card_15693_write},
};

/*
 * Register several native methods for one class
 */
static int register_native_methods(JNIEnv* env, const char* strClassName, JNINativeMethod* pMethods, int nMethodNumber)
{
	jclass clazz;
	clazz = env->FindClass(strClassName);
	if(clazz == NULL)
		return JNI_FALSE;
	if(env->RegisterNatives(clazz, pMethods, nMethodNumber) < 0)
		return JNI_FALSE;
	return JNI_TRUE;
}

/*
 * Register native methods for all class
 *
 */
static int register_native_for_all_class(JNIEnv* env)
{
	return register_native_methods(env, JNIREG_CLASS, g_Methods, sizeof(g_Methods) / sizeof(g_Methods[0]));
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved)
{
	JNIEnv* env = NULL;
	jint nResult = -1;

	if(vm->GetEnv((void**)&env, JNI_VERSION_1_4) != JNI_OK)
	{
		hal_sys_info("JNI_OnLoad(), failed in GetEnv()");
		return -1;
	}
	assert(env != NULL);

	if(!register_native_for_all_class(env))
		return -1;

	return JNI_VERSION_1_4;
}
