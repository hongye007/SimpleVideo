//
// Created by 周文业 on 2020/5/11.
//
#include <jni.h>
#include <iostream>
#include <Init_Player.h>
#include <Init_Editor.h>
#include <Init_Tool.h>

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    LOGI("startTime jni onload");
    JNIEnv *env = NULL;
    const jint result_error = -1;
    // 获取env
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return result_error;
    }
    // 注册工具类方法
    if (registerMediaTool(env) == -1) {
        return result_error;
    }
    LOGI("register tools finish");
    // 注册媒体播放器
    if (registerMedia(env) == -1) {
        return result_error;
    }
    LOGI("register meida finish");
    // 注册媒体编辑器
    if (registerMediaEditor(env) == -1) {
        return result_error;
    }
    LOGI("register editor finish");
    // 必须返回版本号
    return JNI_VERSION_1_4;
}


