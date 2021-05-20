#ifndef VSOMEIP_NATIVELOG_H
#define VSOMEIP_NATIVELOG_H
#include <android/log.h>

#define TAG "VSOMEIP-JNI"

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

#endif //VSOMEIP_NATIVELOG_H
