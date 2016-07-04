/*
 * printer_jni_interface.cpp
 *
 *  Created on: 2012-8-3
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
#include "printer_jni_interface.h"
#include "printer_interface.h"

//#define JNIREG_CLASS "com/wizarpos/printer/PrinterInterface"
static const char* g_pJNIREG_CLASS = "com/wizarpos/apidemo/jniinterface/PrinterInterface";

typedef struct printer_hal_interface
{
	printer_open 		open;
	printer_close	 	close;
	printer_begin 		begin;
	printer_end			end;
	printer_write		write;
	void* pSoHandle;
}PRINTER_HAL_INSTANCE;

static PRINTER_HAL_INSTANCE* g_pPrinterInstance = NULL;

int native_printer_open(JNIEnv* env, jclass obj)
{
	int nResult = 0;
	hal_sys_info("native_printer_open() is called");
	if(g_pPrinterInstance == NULL)
	{
		void* pHandle = dlopen("libwizarposHAL.so", RTLD_LAZY);
		if (!pHandle)
		{
			hal_sys_error("%s\n", dlerror());
			return -1;
		}

		g_pPrinterInstance = new PRINTER_HAL_INSTANCE();

		g_pPrinterInstance->open = (printer_open)dlsym(pHandle, "printer_open");
		if(g_pPrinterInstance->open == NULL)
			goto printer_init_clean;

		g_pPrinterInstance->close = (printer_close)dlsym(pHandle, "printer_close");
		if(g_pPrinterInstance->close == NULL)
			goto printer_init_clean;

		g_pPrinterInstance->begin = (printer_begin)dlsym(pHandle, "printer_begin");
		if(g_pPrinterInstance->begin == NULL)
			goto printer_init_clean;

		g_pPrinterInstance->end = (printer_end)dlsym(pHandle, "printer_end");
		if(g_pPrinterInstance->end == NULL)
			goto printer_init_clean;

		g_pPrinterInstance->write = (printer_write)dlsym(pHandle, "printer_write");
		if(g_pPrinterInstance->write == NULL)
			goto printer_init_clean;

		g_pPrinterInstance->pSoHandle = pHandle;
		nResult = g_pPrinterInstance->open();
	}
	return nResult;
printer_init_clean:
	if(g_pPrinterInstance != NULL)
	{
		delete g_pPrinterInstance;
		g_pPrinterInstance = NULL;
	}
	return 0;//需要返回数据。
}

int native_printer_close(JNIEnv* env, jclass obj)
{
	int nResult = -1;
	hal_sys_info("native_printer_close() is called");
	if(g_pPrinterInstance != NULL)
	{
		nResult = g_pPrinterInstance->close();
		dlclose(g_pPrinterInstance->pSoHandle);
		delete g_pPrinterInstance;
		g_pPrinterInstance = NULL;
	}
	return nResult;
}

int native_printer_begin(JNIEnv* env, jclass obj)
{
	if(g_pPrinterInstance == NULL)
		return -1;
	return g_pPrinterInstance->begin();
}

int native_printer_end(JNIEnv* env, jclass obj)
{
	if(g_pPrinterInstance == NULL)
		return -1;
	return g_pPrinterInstance->end();
}

int native_printer_write(JNIEnv* env, jclass obj, jbyteArray arryData, jint nDataLength)
{
	int nResult = -1;
	if(g_pPrinterInstance == NULL)
		return -1;

	jbyte* pData = env->GetByteArrayElements(arryData, NULL);
	nResult = g_pPrinterInstance->write((unsigned char*)pData, nDataLength);
	env->ReleaseByteArrayElements(arryData, pData, 0);
	hal_sys_info("native_printer_write() return value = %d\n", nResult);
	return nResult;
}


static JNINativeMethod g_Methods[] =
{
	{"PrinterOpen",					"()I",														(void*)native_printer_open},
	{"PrinterClose",				"()I",														(void*)native_printer_close},
	{"PrinterBegin",				"()I",														(void*)native_printer_begin},
	{"PrinterEnd",					"()I",														(void*)native_printer_end},
	{"PrinterWrite",				"([BI)I",													(void*)native_printer_write},
};

const char* printer_get_class_name()
{
	return g_pJNIREG_CLASS;
}

JNINativeMethod* printer_get_methods(int* pCount)
{
	*pCount = sizeof(g_Methods) /sizeof(g_Methods[0]);
	return g_Methods;
}
