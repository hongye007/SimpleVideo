//
// Created by 周文业 on 2020/5/12.
//
#ifndef SIMPLEVIDEO_INIT_PLAYER_H
#define SIMPLEVIDEO_INIT_PLAYER_H

#include <jni.h>
#include "MediaPlayer.h"

static MediaPlayer mPlayer;

static jclass jni_media_class = NULL;
static const char *jni_media_class_name = "com/simple/player/media/DPPlayer";

static void JNICALL media_prepare(JNIEnv *env, jclass *, jstring url,
                                  jobject surface) {
    mPlayer.prepareVideo(env, url, surface);
}

static void JNICALL media_play(JNIEnv *env, jobject *) {
    mPlayer.playVideo(env);
}

static JNINativeMethod jni_media_methods[]{
        {"media_init", "(Ljava/lang/String;Landroid/view/Surface;)V", (void *) media_prepare},
        {"media_play", "()V",                                         (void *) media_play}
};

static jint registerMedia(JNIEnv *env) {
    // 注册媒体类方法
    jni_media_class = env->FindClass(jni_media_class_name);
    if (jni_media_class == NULL) {
        return -1;
    }
    if (env->RegisterNatives(jni_media_class, jni_media_methods,
                             sizeof(jni_media_methods) / sizeof(jni_media_methods[0])) <
        0) {
        return -1;
    }
    return 0;
}

#endif