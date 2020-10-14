//
// Created by 周文业 on 2020/5/12.
//

#ifndef SIMPLEVIDEO_INIT_TOOL_H
#define SIMPLEVIDEO_INIT_TOOL_H

#include <JniLog.h>

static jclass jni_tool_class = NULL;
static const char *jni_tool_class_name = "com/simple/player/tools/CustomFFmpeg";

static void JNICALL native_print(JNIEnv *, jclass *) {
    LOGI("native_print");
}

static JNINativeMethod jni_tool_methods[]{
        {"printHello", "()V", (void *) native_print}
};

static jint registerMediaTool(JNIEnv *env) {
    // 注册媒体类方法
    jni_tool_class = env->FindClass(jni_tool_class_name);
    if (jni_tool_class == NULL) {
        return -1;
    }
    if (env->RegisterNatives(jni_tool_class, jni_tool_methods,
                             sizeof(jni_tool_methods) / sizeof(jni_tool_methods[0])) <
        0) {
        return -1;
    }
    return 0;
}

#endif //SIMPLEVIDEO_INIT_TOOL_H
