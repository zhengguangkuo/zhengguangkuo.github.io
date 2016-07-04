/*
 * msr_imp.c
 *
 *  Created on: 2012-7-17
 *      Author: yaomaobiao
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include <semaphore.h>
#include <unistd.h>
#include <errno.h>
#include <dlfcn.h>


#include "msr_impl.h"
#include "hal_sys_log.h"

static MSR_HAL_INSTANCE* g_pMsrHALInstance = NULL;

int msr_module_init()
{
	char *pError = NULL;

	if(g_pMsrHALInstance != NULL)
	{
		hal_sys_error("g_pMsrHALInstance != NULL\n");
		return -1;
	}

	g_pMsrHALInstance = new MSR_HAL_INSTANCE();

	void* pHandle = dlopen("libwizarposHAL.so", RTLD_LAZY);
	if (!pHandle)
	{
		hal_sys_error("%s\n", dlerror());
	    return -1;
	}

	g_pMsrHALInstance->open = (MSR_OPEN)dlsym(pHandle, "msr_open");
	if(g_pMsrHALInstance->open == NULL)
		goto msr_module_init_clean;

	g_pMsrHALInstance->close = (MSR_CLOSE)dlsym(pHandle, "msr_close");
	if(g_pMsrHALInstance->close == NULL)
		goto msr_module_init_clean;

	g_pMsrHALInstance->register_notifier = (MSR_REGISTER_NOTIFIER)dlsym(pHandle, "msr_register_notifier");
	if(g_pMsrHALInstance->register_notifier == NULL)
		goto msr_module_init_clean;

	g_pMsrHALInstance->unregister_notifier = (MSR_UNREGISTER_NOTIFIER)dlsym(pHandle, "msr_unregister_notifier");
	if(g_pMsrHALInstance->unregister_notifier == NULL)
		goto msr_module_init_clean;

	g_pMsrHALInstance->get_track_error = (MSR_GET_TRACK_ERROR)dlsym(pHandle, "msr_get_track_error");
	if(g_pMsrHALInstance->get_track_error == NULL)
		goto msr_module_init_clean;

	g_pMsrHALInstance->get_track_data_length = (MSR_GET_TRACK_DATA_LENGTH)dlsym(pHandle, "msr_get_track_data_length");
	if(g_pMsrHALInstance->get_track_data_length == NULL)
		goto msr_module_init_clean;

	g_pMsrHALInstance->get_track_data = (MSR_GET_TRACK_DATA)dlsym(pHandle, "msr_get_track_data");
	if(g_pMsrHALInstance->get_track_data == NULL)
		goto msr_module_init_clean;

	g_pMsrHALInstance->pHandle = pHandle;

	sem_init(&(g_pMsrHALInstance->sem), 0, 0);

	hal_sys_info("msr_module_init() before g_pMsrHALInstance->open()!");


	return g_pMsrHALInstance->open();

msr_module_init_clean:
	return -1;

}

int msr_module_close()
{
	int nResult = -1;

	hal_sys_info("Enter msr_module_close()...\n");
	if(g_pMsrHALInstance == NULL)
	{
		hal_sys_error("You have not opened the module!\n");
		return -1;
	}

	if(g_pMsrHALInstance->pHandle != NULL)
	{
		g_pMsrHALInstance->close();
		nResult = dlclose(g_pMsrHALInstance->pHandle);
	}
	delete g_pMsrHALInstance;
	g_pMsrHALInstance = NULL;
	hal_sys_info("Leave msr_module_close()...\n");
	return 0;
}

static void msr_call_back(void* pUserData)
{
	MSR_HAL_INSTANCE * pCallbackInfo = (MSR_HAL_INSTANCE*)pUserData;
	sem_post(&(pCallbackInfo->sem));
	return;
}


int msr_module_poll(int nTimeout_MS)
{
	int nReturn = -1;
	int nTimeout_Sec = 0;
	struct timespec ts;

	g_pMsrHALInstance->register_notifier(msr_call_back, g_pMsrHALInstance);

	if(nTimeout_MS < 0)
		nTimeout_Sec = -1;
	else
	{
		nTimeout_Sec = nTimeout_MS % 1000 ? (nTimeout_MS / 1000 + 1) : nTimeout_MS / 1000;
		clock_gettime(CLOCK_REALTIME, &ts);
		ts.tv_sec += nTimeout_Sec;
	}
	while(1)
	{
		nReturn = nTimeout_Sec >= 0 ? sem_timedwait(&(g_pMsrHALInstance->sem), &ts)
				:sem_wait(&(g_pMsrHALInstance->sem));
		if(nReturn == -1 && errno == EINTR)
			continue;
		else
			break;
	}
	if(nReturn == -1)
	{
#if 0
		if(errno == ETIMEDOUT)
			hal_sys_info("sem_timedwait() time out\n");
		else
			hal_sys_info(strerror(errno));
#endif
		if(errno != ETIMEDOUT)
			hal_sys_info(strerror(errno));
		return -1;
	}
	return 0;
}

MSR_HAL_INSTANCE* msr_module_get_instance()
{
	return g_pMsrHALInstance;
}
