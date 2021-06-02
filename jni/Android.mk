LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE:= testmain

LOCAL_SRC_FILES := \
	sqlite3/sqlite3.c \
	testmain/src/TestUtility.cpp \
	testmain/src/Test002.cpp \
	testmain/src/Test001.cpp \
	testmain/src/Test.cpp \
	runner.c

LOCAL_C_INCLUDES += \
					$(LOCAL_PATH) \
					$(LOCAL_PATH)/sqlite3 \
					$(LOCAL_PATH)/testmain/include

LOCAL_EXPORT_C_INCLUDES := $(LOCAL_C_INCLUDES)

LOCAL_MODULE_TAGS := optional
# LOCAL_SHARED_LIBRARIES := libc
LOCAL_CFLAGS += -O3 -DHAVE_STDINT_H -DFOR_PLATFORM_ANDROID

# include $(BUILD_STATIC_LIBRARY)
include $(BUILD_SHARED_LIBRARY)
