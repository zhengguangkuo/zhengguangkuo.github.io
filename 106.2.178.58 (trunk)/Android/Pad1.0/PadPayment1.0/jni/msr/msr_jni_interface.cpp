/*
 * msr_jni_interface.cpp
 *
 *  Created on: 2013-1-6
 *      Author: s990902
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <jni.h>

#include "msr_impl.h"
#include "hal_sys_log.h"

/*
 * Class:     huiyin_hal_msr_HALMsrInterface
 * Method:    msr_open
 * Signature: ()I
 */
int native_msr_open(JNIEnv * env, jclass obj)
{
	hal_sys_info("native_msr_open(), Enter\n");
	return msr_module_init();
}

/*
 * Class:     huiyin_hal_msr_HALMsrInterface
 * Method:    msr_close
 * Signature: ()I
 */
int native_msr_close(JNIEnv * env, jclass obj)
{
	return msr_module_close();
}

/*
 * Class:     huiyin_hal_msr_HALMsrInterface
 * Method:    msr_poll
 * Signature: (I)I
 */
int native_msr_poll(JNIEnv * env, jclass obj , jint nTimeout_MS)
{
	hal_sys_error("Enter msr_module_poll() \n");
	return msr_module_poll(nTimeout_MS);
}

/*
 * Class:     huiyin_hal_msr_HALMsrInterface
 * Method:    msr_get_track_error
 * Signature: (I)I
 */
int native_msr_get_track_error(JNIEnv * env, jclass obj, jint nTrackIndex)
{
	MSR_HAL_INSTANCE* pInstance =  msr_module_get_instance();
	return pInstance->get_track_error(nTrackIndex);

}

/*
 * Class:     huiyin_hal_msr_HALMsrInterface
 * Method:    msr_get_track_data_length
 * Signature: (I)I
 */
int native_msr_get_track_data_length(JNIEnv * env, jclass obj, jint nTrackIndex)
{
	MSR_HAL_INSTANCE* pInstance =  msr_module_get_instance();
	return pInstance->get_track_data_length(nTrackIndex);
}

/*
 * Class:     huiyin_hal_msr_HALMsrInterface
 * Method:    msr_get_track_data
 * Signature: (I[BI)I
 */
int native_msr_get_track_data(JNIEnv * env, jclass obj, jint nTrackIndex, jbyteArray byteArray, jint nLength)
{
	MSR_HAL_INSTANCE* pInstance = NULL;
	jint nResult = 0;
	jbyte* arrayBody = env->GetByteArrayElements(byteArray, 0);

	pInstance =  msr_module_get_instance();
	nResult = pInstance->get_track_data(nTrackIndex, (unsigned char*)arrayBody, nLength);
	if(nResult > 0)
	{
		arrayBody[nResult] = '\0';
		hal_sys_info("JNICALL Track[%d] data : %s", nTrackIndex, (char*)arrayBody);
	}
	env->ReleaseByteArrayElements(byteArray, arrayBody, 0);
	return nResult;
}



static JNINativeMethod g_Methods[] =
{
	{"msr_open",					"()I",					(void*)native_msr_open},
	{"msr_close",					"()I",					(void*)native_msr_close},
	{"msr_poll",					"(I)I",					(void*)native_msr_poll},
	{"msr_get_track_error",			"(I)I",					(void*)native_msr_get_track_error},
	{"msr_get_track_data_length",	"(I)I",					(void*)native_msr_get_track_data_length},
	{"msr_get_track_data",			"(I[BI)I",				(void*)native_msr_get_track_data},
};

static const char* g_pJNIREG_CLASS = "com/wizarpos/apidemo/jniinterface/HALMsrInterface";

const char* msr_get_class_name()
{
	return g_pJNIREG_CLASS;
}

JNINativeMethod* msr_get_methods(int* pCount)
{
	*pCount = sizeof(g_Methods) /sizeof(g_Methods[0]);
	return g_Methods;
}
