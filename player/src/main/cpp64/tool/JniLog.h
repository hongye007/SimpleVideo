//
// Created by 周文业 on 2020/5/12.
//

#ifndef SIMPLEVIDEO_JNILOG_H
#define SIMPLEVIDEO_JNILOG_H

#include <android/log.h>

#define LOG_TAG "native-tag"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

#endif //SIMPLEVIDEO_JNILOG_H
