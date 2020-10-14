//
// Created by 周文业 on 2020/5/12.
//

#ifndef SIMPLEVIDEO_INIT_EDITOR_H
#define SIMPLEVIDEO_INIT_EDITOR_H

#include <jni.h>
#include <MediaHelper.h>
#include <MediaEditor.h>
#include <MediaTranscoder.h>

static jclass jni_editor_class = NULL;
static const char *jni_editor_class_name = "com/simple/player/editor/MediaHelper";

static jint media_editor(JNIEnv *env, jobject thiz, jobject model) {
    jint result = -1;
    jclass clazz = env->GetObjectClass(model);

    jfieldID jid_o_url = env->GetFieldID(clazz,"originFile","Ljava/lang/String;");
    jstring j_o_url = static_cast<jstring>(env->GetObjectField(model, jid_o_url));
    const char * o_url = env->GetStringUTFChars(j_o_url,0);

    jfieldID jid_t_url = env->GetFieldID(clazz,"targetFile","Ljava/lang/String;");
    jstring j_t_url = static_cast<jstring>(env->GetObjectField(model, jid_t_url));
    const char * t_url = env->GetStringUTFChars(j_t_url,0);

    jfieldID jid_start = env->GetFieldID(clazz,"startTime","J");
    jlong j_start = env->GetLongField(model,jid_start);

    jfieldID jid_end = env->GetFieldID(clazz,"endTime","J");
    jlong j_end = env->GetLongField(model,jid_end);

//    MediaEditor mediaEditor(o_url,t_url,j_start,j_end);
//    result = mediaEditor.transcodeVideo();
    MediaTranscoder helper(o_url,t_url);
    helper.timeClip(static_cast<int>(j_start), static_cast<int>(j_end));
    result = helper.transcode();
    return result;
}

static JNINativeMethod jni_editor_methods[]{
        {"jni_processVideo", "(Lcom/simple/player/editor/MediaEditModel;)I", (void *) media_editor}
};

static jint registerMediaEditor(JNIEnv *env) {
    // 注册媒体类方法
    jni_editor_class = env->FindClass(jni_editor_class_name);
    if (jni_editor_class == NULL) {
        return -1;
    }
    if (env->RegisterNatives(jni_editor_class, jni_editor_methods,
                             sizeof(jni_editor_methods) / sizeof(jni_editor_methods[0])) <
        0) {
        return -1;
    }
    return 0;
}

#endif //SIMPLEVIDEO_INIT_EDITOR_H
