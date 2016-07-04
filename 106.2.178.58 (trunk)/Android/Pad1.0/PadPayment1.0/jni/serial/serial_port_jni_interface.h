#ifndef SERIAL_PORT_JNI_INTERFACE_H_
#define SERIAL_PORT_JNI_INTERFACE_H_

const char* serial_port_get_class_name();
JNINativeMethod* serial_port_get_methods(int* pCount);

#endif /* SERIAL_PORT_JNI_INTERFACE_H_ */
