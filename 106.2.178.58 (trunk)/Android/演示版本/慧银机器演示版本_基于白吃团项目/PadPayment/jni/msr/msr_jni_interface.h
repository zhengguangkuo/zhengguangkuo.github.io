/*
 * msr_jni_interface.h
 *
 *  Created on: 2013-1-6
 *      Author: s990902
 */

#ifndef MSR_JNI_INTERFACE_H_
#define MSR_JNI_INTERFACE_H_

const char* msr_get_class_name();
JNINativeMethod* msr_get_methods(int* pCount);

#endif /* MSR_JNI_INTERFACE_H_ */
