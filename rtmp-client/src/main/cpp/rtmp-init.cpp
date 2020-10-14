//
// Created by 周文业 on 2020/5/22.
//
#include <jni.h>
#include <rtmp.h>
#include <malloc.h>

jint throwIllegalStateException(JNIEnv *env, char *message);

extern "C"
JNIEXPORT jlong JNICALL
Java_com_simple_rtmp_RtmpClient_nativeAlloc(JNIEnv *env, jobject thiz) {
    return reinterpret_cast<jlong>(RTMP_Alloc());
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_simple_rtmp_RtmpClient_nativeOpen(JNIEnv *env, jobject thiz, jstring url_,
                                           jboolean is_publish_mode, jlong rtmp_pointer,
                                           jint send_timeout_in_ms, jint receive_timeout_in_ms) {
    const char *url = env->GetStringUTFChars(url_, NULL);
    RTMP *rtmp = (RTMP *) rtmp_pointer;
    if (rtmp == NULL) {
        throwIllegalStateException(env, "RTMP open called without allocating rtmp object");
        return -1;
    }

    RTMP_Init(rtmp);

    int ret = RTMP_SetupURL(rtmp, const_cast<char *>(url));

    if (ret != TRUE) {
        RTMP_Free(rtmp);
        return ret;
    }
    if (is_publish_mode) {
        RTMP_EnableWrite(rtmp);
    }

    ret = RTMP_Connect(rtmp, NULL);
    if (ret != TRUE) {
        RTMP_Free(rtmp);
        return ret;
    }
    ret = RTMP_ConnectStream(rtmp, 0);

    if (ret != TRUE) {
        RTMP_Free(rtmp);
        return ret;
    }
    env->ReleaseStringUTFChars(url_, url);
    return TRUE;
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_simple_rtmp_RtmpClient_nativeIsConnected(JNIEnv *env, jobject thiz, jlong rtmp_pointer) {
    RTMP *rtmp = (RTMP *) rtmp_pointer;
    if (rtmp == NULL) {
        return 0;
    }
    int connected = RTMP_IsConnected(rtmp);
    return connected ? 1 : 0;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_simple_rtmp_RtmpClient_nativeRead(JNIEnv *env, jobject thiz, jbyteArray data_, jint offset,
                                           jint size, jlong rtmp_pointer) {
    RTMP *rtmp = (RTMP *) rtmp_pointer;
    if (rtmp == NULL) {
        throwIllegalStateException(env, "RTMP open function has to be called before read");
        return -1;
    }

    int connected = RTMP_IsConnected(rtmp);
    if (!connected) {
        return -1;
    }

    char* data = static_cast<char *>(malloc(size));

    int readCount = RTMP_Read(rtmp, data, size);

    if (readCount > 0) {
        env->SetByteArrayRegion(data_, offset, readCount, reinterpret_cast<const jbyte *>(data));  // copy
    }
    free(data);
    return readCount;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_simple_rtmp_RtmpClient_nativeWrite(JNIEnv *env, jobject thiz, jbyteArray data, jint offset,
                                            jint size, jlong rtmp_pointer) {
    RTMP *rtmp = (RTMP *) rtmp_pointer;
    if (rtmp == NULL) {
        throwIllegalStateException(env, "RTMP open function has to be called before write");
        return -1;
    }

    int connected = RTMP_IsConnected(rtmp);
    if (!connected) {
        return -1;
    }

    const char *buf = static_cast<const char *>(malloc(size));
    env->GetByteArrayRegion(data, offset, size, (jbyte *) buf);
    int result = RTMP_Write(rtmp, buf, size);
    free((void *) buf);
    return result;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_simple_rtmp_RtmpClient_nativeClose(JNIEnv *env, jobject thiz, jlong rtmp_pointer) {
    RTMP *rtmp = (RTMP *) rtmp_pointer;
    if (rtmp != NULL) {
        RTMP_Close(rtmp);
        RTMP_Free(rtmp);
    }
}

jint throwIllegalStateException(JNIEnv *env, char *message) {
    jclass exception = env->FindClass("java/lang/IllegalStateException");
    return env->ThrowNew(exception, message);
}


//static jclass jni_rtmp_class = NULL;
//static const char *jni_rtmp_class_name = "com/simple/player/editor/MediaHelper";
//
//static jlong nativeAlloc(JNIEnv *env, jobject *) {
//    jlong result = -1;
//    return result;
//}
//
//static jint
//nativeOpen(JNIEnv *env, jobject *, jstring url, jboolean isPublishMode, jlong rtmpPointer,
//           jint sendTimeoutInMs, jint receiveTimeoutInMs) {
//
//}
//
//static void nativeClose(JNIEnv *env, jobject thiz, jlong rtmp_pointer) {
//
//}
//
//static jint nativeRead(JNIEnv* env, jobject thiz, jbyteArray data_,
//                       jint offset, jint size, jlong rtmpPointer) {
//}
//
//static JNINativeMethod jni_rtmp_methods[]{
//        {"nativeAlloc", "()J", (void *) nativeAlloc},
//        {"nativeOpen","(Ljava/lang/String;ZJII)I",(void*)nativeOpen},
//        {"nativeClose","(J)V",(void*)nativeClose}
//};
//
//static jint registerMediaEditor(JNIEnv *env) {
//    // 注册媒体类方法
//    jni_rtmp_class = env->FindClass(jni_rtmp_class_name);
//    if (jni_rtmp_class == NULL) {
//        return -1;
//    }
//    if (env->RegisterNatives(jni_rtmp_class, jni_rtmp_methods,
//                             sizeof(jni_rtmp_methods) / sizeof(jni_rtmp_methods[0])) <
//        0) {
//        return -1;
//    }
//    return 0;
//}

//JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
//    JNIEnv *env = NULL;
//    const jint result_error = -1;
//// 获取env
//    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
//        return result_error;
//    }
////// 注册工具类方法
////    if (registerMediaEditor(env) == -1) {
////        return result_error;
////    }
//
//    return JNI_VERSION_1_4;
//}

static int
SendCustom(RTMP *r) {
    char * kk = const_cast<char *>("custom");
    AVal val = AVC(kk);
    const AVal temp = AVC("kklk");

    RTMPPacket packet;
    char pbuf[1024], *pend = pbuf + sizeof(pbuf);
    char *enc;

    packet.m_nChannel = 0x03;    /* control channel (invoke) */
    packet.m_headerType = RTMP_PACKET_SIZE_MEDIUM;
    packet.m_packetType = 0x14;
    packet.m_nTimeStamp = 0;
    packet.m_nInfoField2 = 0;
    packet.m_hasAbsTimestamp = 0;
    packet.m_body = pbuf + RTMP_MAX_HEADER_SIZE;

    enc = packet.m_body;
    enc = AMF_EncodeString(enc, pend, &val);
    *enc++ = AMF_NULL;
    if (!enc)
        return FALSE;
    packet.m_nBodySize = enc - packet.m_body;
    return RTMP_SendPacket(r, &packet, FALSE);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_simple_rtmp_RtmpClient_nativeClick(JNIEnv *env, jobject thiz,jlong id) {
    RTMP *rtmp = (RTMP *) id;
    if (rtmp == NULL) {
        return;
    }
    SendCustom(rtmp);
}