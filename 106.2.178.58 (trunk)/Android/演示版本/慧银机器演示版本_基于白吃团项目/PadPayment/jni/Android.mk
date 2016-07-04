LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_CFLAGS = -Wno-psabi
LOCAL_MODULE := wizarpos_printer
LOCAL_SRC_FILES += printer/hal_sys_log.c

LOCAL_SRC_FILES += printer/jni_register.cpp 
LOCAL_SRC_FILES += printer/printer_jni_interface.cpp 

LOCAL_LDLIBS    := -llog
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_CFLAGS = -Wno-psabi
LOCAL_MODULE    := wizarpos_pinpad
LOCAL_SRC_FILES += pinpad/hal_sys_log.c
LOCAL_C_INCLUDES += $(LOCAL_PATH)/pinpad
LOCAL_SRC_FILES += pinpad/jni_register.cpp 
LOCAL_SRC_FILES += pinpad/pinpad_jni_interface.cpp 
LOCAL_SRC_FILES += pinpad/prove/DES.cpp
LOCAL_SRC_FILES += pinpad/prove/pinpad_utility.cpp
LOCAL_SRC_FILES += pinpad/prove/pinpad_test.cpp

LOCAL_LDLIBS := -lm -llog
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_CFLAGS = -Wno-psabi
LOCAL_MODULE    := wizarpos_smartcard
LOCAL_SRC_FILES += smartcard/hal_sys_log.c
LOCAL_C_INCLUDES += $(LOCAL_PATH)/smartcard

LOCAL_SRC_FILES += smartcard/smart_card_jni_interface.cpp 
LOCAL_SRC_FILES += smartcard/smart_card_jni_register.cpp
LOCAL_SRC_FILES += smartcard/smart_card_event.cpp 

LOCAL_LDLIBS := -lm -llog
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_CFLAGS = -Wno-psabi
LOCAL_MODULE    := wizarpos_contactlesscard
LOCAL_SRC_FILES += contactlesscard/hal_sys_log.c
LOCAL_C_INCLUDES += $(LOCAL_PATH)/contactlesscard

LOCAL_SRC_FILES += contactlesscard/contactless_card_jni_register.cpp 
LOCAL_SRC_FILES += contactlesscard/contactless_card_jni_interface.cpp 
LOCAL_SRC_FILES += contactlesscard/contactless_card_event.cpp

LOCAL_LDLIBS := -lm -llog
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_CFLAGS = -Wno-psabi
LOCAL_MODULE := wizarpos_msr
LOCAL_SRC_FILES += msr/hal_sys_log.c 
LOCAL_C_INCLUDES += $(LOCAL_PATH)/msr

LOCAL_SRC_FILES += msr/msr_jni_interface.cpp
LOCAL_SRC_FILES += msr/msr_impl.cpp
LOCAL_SRC_FILES += msr/msr_jni_register.cpp

LOCAL_LDLIBS    := -llog

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_CFLAGS = -Wno-psabi
LOCAL_MODULE := wizarpos_serial
LOCAL_SRC_FILES += serial/hal_sys_log.c 
LOCAL_C_INCLUDES += $(LOCAL_PATH)/serial

LOCAL_SRC_FILES += serial/serial_port_jni_interface.cpp
LOCAL_SRC_FILES += serial/serial_port_jni_register.cpp

LOCAL_LDLIBS    := -llog

include $(BUILD_SHARED_LIBRARY)