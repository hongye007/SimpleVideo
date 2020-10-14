//
// Created by 周文业 on 2020/5/11.
//

#ifndef SIMPLEVIDEO_MEDIAPLAYER_H
#define SIMPLEVIDEO_MEDIAPLAYER_H

#include <jni.h>
#include <android/native_window.h>
#include <android/native_window_jni.h>

#ifdef __cplusplus
extern "C" {
#endif
#include "libavcodec/avcodec.h"
#include "libavformat/avformat.h"
#include "libswscale/swscale.h"
#include "libswresample/swresample.h"
#include "libavutil/opt.h"
#include "libavutil/imgutils.h"
#ifdef __cplusplus
};
#endif

class MediaPlayer {
private:
    AVPacket *vPacket;
    AVFrame *vFrame, *pFrameRGBA;
    AVCodecParameters *avCodecParameters;
    AVCodecContext *vCodecCtx;
    SwsContext *img_convert_ctx;
    AVFormatContext *pFormatCtx;
    ANativeWindow *nativeWindow;
    ANativeWindow_Buffer windowBuffer;
    uint8_t *v_out_buffer;

    int videoindex = -1;
    int height;
    int width;
public:
    jint playVideo(JNIEnv *env);

    jint prepareVideo(JNIEnv *env, jstring url,
                      jobject surface);
};


#endif //SIMPLEVIDEO_MEDIAPLAYER_H
