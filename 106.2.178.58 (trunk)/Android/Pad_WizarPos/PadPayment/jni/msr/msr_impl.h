/*
 * msr_impl.h
 *
 *  Created on: 2012-7-17
 *      Author: yaomaobiao
 */

#ifndef MSR_IMPL_H_
#define MSR_IMPL_H_

#include <semaphore.h>

typedef void (*MSR_NOTIFIER)(void* pUserData);

typedef int (*MSR_OPEN)(void);
typedef int (*MSR_CLOSE)(void);
typedef int (*MSR_REGISTER_NOTIFIER)(MSR_NOTIFIER notifier, void* pUserData);
typedef int (*MSR_UNREGISTER_NOTIFIER)();
typedef int (*MSR_GET_TRACK_ERROR)(int nTrackIndex);
typedef int (*MSR_GET_TRACK_DATA_LENGTH)(int nTrackIndex);
typedef int (*MSR_GET_TRACK_DATA)(int nTrackIndex, unsigned char* pTrackData, int nLength);

typedef struct msr_hal_interface
{
	MSR_OPEN					open;
	MSR_CLOSE					close;
	MSR_REGISTER_NOTIFIER		register_notifier;
	MSR_UNREGISTER_NOTIFIER		unregister_notifier;
	MSR_GET_TRACK_ERROR			get_track_error;
	MSR_GET_TRACK_DATA_LENGTH	get_track_data_length;
	MSR_GET_TRACK_DATA			get_track_data;
	void*						pHandle;
	sem_t sem;
}MSR_HAL_INSTANCE;

int msr_module_init();
int msr_module_close();
int msr_module_poll(int nTimeout_MS);
MSR_HAL_INSTANCE* msr_module_get_instance();

#endif /* MSR_IMPL_H_ */
