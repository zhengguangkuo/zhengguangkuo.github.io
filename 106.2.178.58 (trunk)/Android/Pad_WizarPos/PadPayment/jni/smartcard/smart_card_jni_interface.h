/*
 * smart_card_jni_interface.h
 *
 *  Created on: 2012-7-22
 *      Author: yaomaobiao
 */

#ifndef SMART_CARD_JNI_INTERFACE_H_
#define SMART_CARD_JNI_INTERFACE_H_

const char* smartcard_get_class_name();
JNINativeMethod* smartcard_get_methods(int* pCount);

#endif /* SMART_CARD_JNI_INTERFACE_H_ */
