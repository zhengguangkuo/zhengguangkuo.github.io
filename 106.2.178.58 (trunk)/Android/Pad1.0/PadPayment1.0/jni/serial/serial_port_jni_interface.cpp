#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <dlfcn.h>
#include <semaphore.h>
#include <unistd.h>
#include <errno.h>

#include <jni.h>

#include "hal_sys_log.h"
#include "serial_port_jni_interface.h"
#include "serial_port_interface.h"

const char* g_pJNIREG_CLASS = "com/wizarpos/apidemo/jniinterface/SerialPortInterface";

typedef struct serail_port_hal_interface
{
	serial_port_open         open;
	serial_port_close        close;
	serial_port_read         read;
	serial_port_write        write;
	serial_port_set_baudrate set_baudrate;
	serial_port_flush_io     flush_io;
	int   portHandle;
	void* pSoHandle;
}SERIAL_PORT_HAL_INSTANCE;

static SERIAL_PORT_HAL_INSTANCE* g_pSerialPortInstance = NULL;

int native_serial_port_open(JNIEnv* env, jclass obj)
{
	const char* SERIAL_DEVICE_NAME = "/dev/s3c2410_serial2";

	hal_sys_info("native_serial_port_open() is called");
	if(g_pSerialPortInstance == NULL)
	{
		void* pHandle = dlopen("libwizarposHAL.so", RTLD_LAZY);
		if (!pHandle)
		{
			hal_sys_error("%s\n", dlerror());
			return -1;
		}

		g_pSerialPortInstance = new SERIAL_PORT_HAL_INSTANCE();
		g_pSerialPortInstance->portHandle = -1;
		g_pSerialPortInstance->open = (serial_port_open)dlsym(pHandle, "esp_open");
		if(g_pSerialPortInstance->open == NULL)
		{
			hal_sys_error("can't find serial_port_open");
			goto serial_port_init_clean;
		}

		g_pSerialPortInstance->close = (serial_port_close)dlsym(pHandle, "esp_close");
		if(g_pSerialPortInstance->close == NULL)
		{
			hal_sys_error("can't find serial_port_close");
			goto serial_port_init_clean;
		}

		g_pSerialPortInstance->read = (serial_port_read)dlsym(pHandle, "esp_read");
		if(g_pSerialPortInstance->read == NULL)
		{
			hal_sys_error("can't find serial_port_read");
			goto serial_port_init_clean;
		}

		g_pSerialPortInstance->write = (serial_port_write)dlsym(pHandle, "esp_write");
		if(g_pSerialPortInstance->write == NULL)
		{
			hal_sys_error("can't find serial_port_write");
			goto serial_port_init_clean;
		}

		g_pSerialPortInstance->set_baudrate = (serial_port_set_baudrate)dlsym(pHandle, "esp_set_baudrate");
		if(g_pSerialPortInstance->set_baudrate == NULL)
		{
			hal_sys_error("can't find serial_port_set_baudrate");
			goto serial_port_init_clean;
		}

		g_pSerialPortInstance->flush_io = (serial_port_flush_io)dlsym(pHandle, "pinpad_calculate_pin_block");
		if(g_pSerialPortInstance->flush_io == NULL)
		{
			hal_sys_error("can't find serial_port_flush_io");
			goto serial_port_init_clean;
		}

		g_pSerialPortInstance->pSoHandle = pHandle;
		g_pSerialPortInstance->portHandle = g_pSerialPortInstance->open((char *)SERIAL_DEVICE_NAME);
	}
	return g_pSerialPortInstance->portHandle;
serial_port_init_clean:
	if(g_pSerialPortInstance != NULL)
	{
		delete g_pSerialPortInstance;
		g_pSerialPortInstance = NULL;
	}
	return -1;
}

int native_serial_port_close(JNIEnv* env, jclass obj)
{
	int nResult = -1;
	if(g_pSerialPortInstance == NULL)
		return -1;
	nResult = g_pSerialPortInstance->close(g_pSerialPortInstance->portHandle);
	dlclose(g_pSerialPortInstance->pSoHandle);
	delete g_pSerialPortInstance;
	g_pSerialPortInstance = NULL;

	return nResult;
}

int native_serial_port_read(JNIEnv* env, jclass obj, jbyteArray pDataBuffer, jint offset, jint nExpectedDataLength, jint nTimeout_MS)
{
	int nResult = -1;
	if(g_pSerialPortInstance == NULL)
		return -1;
	jbyte* pData = env->GetByteArrayElements(pDataBuffer, NULL);
	nResult = g_pSerialPortInstance->read(g_pSerialPortInstance->portHandle, (unsigned char *)(pData+offset), nExpectedDataLength, nTimeout_MS);
	env->ReleaseByteArrayElements(pDataBuffer, pData, 0);
	return nResult;
}

int native_serial_port_write(JNIEnv* env, jclass obj, jbyteArray pDataBuffer, jint nDataLength)
{
	int nResult = -1;
	if(g_pSerialPortInstance == NULL)
		return -1;
	jbyte* pData = env->GetByteArrayElements(pDataBuffer, NULL);
	nResult = g_pSerialPortInstance->write(g_pSerialPortInstance->portHandle, (unsigned char *)pData, nDataLength);
	env->ReleaseByteArrayElements(pDataBuffer, pData, 0);
	return nResult;
}

int native_serial_port_set_baudrate(JNIEnv* env, jclass obj, jint nBaudrate)
{
	int nResult = -1;
	if(g_pSerialPortInstance == NULL)
		return -1;
	nResult = g_pSerialPortInstance->set_baudrate(g_pSerialPortInstance->portHandle, nBaudrate);

	return nResult;
}

int native_serial_port_flush_io(JNIEnv* env, jclass obj)
{
	int nResult = -1;
	if(g_pSerialPortInstance == NULL)
		return -1;
	nResult = g_pSerialPortInstance->flush_io(g_pSerialPortInstance->portHandle);

	return nResult;
}

static JNINativeMethod g_Methods[] =
{
	{"open",			"()I",	    (void*)native_serial_port_open},
	{"close",			"()I",		(void*)native_serial_port_close},
	{"read",			"([BIII)I",	(void*)native_serial_port_read},
	{"write",			"([BI)I",	(void*)native_serial_port_write},
	{"set_baudrate",	"(I)I",		(void*)native_serial_port_set_baudrate},
	{"flush_io",		"()I",		(void*)native_serial_port_flush_io},
};

const char* serial_port_get_class_name()
{
	return g_pJNIREG_CLASS;
}

JNINativeMethod* serial_port_get_methods(int* pCount)
{
	*pCount = sizeof(g_Methods) /sizeof(g_Methods[0]);
	return g_Methods;
}
