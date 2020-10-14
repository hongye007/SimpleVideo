//
// Created by 周文业 on 2020/5/17.
//

#ifndef SIMPLEVIDEO_MEDIAHELPER_H
#define SIMPLEVIDEO_MEDIAHELPER_H

#ifdef __cplusplus
extern "C" {
#endif
#include <libavformat/avformat.h>
#include <libswscale/swscale.h>
#include <libswresample/swresample.h>
#include <libavutil/audio_fifo.h>
#include <libavfilter/avfilter.h>
#include <libavutil/opt.h>
#include <libavfilter/buffersrc.h>
#include <libavfilter/buffersink.h>
#ifdef __cplusplus
}
#endif

#include <JniLog.h>
#include<queue>

class MediaHelper {
private:
    const char *inputFile;
    const char *outputFile;
private:
    void Time_base(AVPacket *pkt, AVFormatContext *&ic, AVFormatContext *&oc);

    int Open_In_file(const char *infile, int &videoidx, int &audioidx, AVFormatContext *&ic);

    int Open_out_file(const char *outfile, int &videoidx, int &audioidx, AVStream *videoStream,
                      AVStream *audioStream, AVCodecContext *&encodec_video,
                      AVCodecContext *&encodec_audio,AVCodecContext *decodec_video,
                      AVCodecContext *decodec_audio, AVFormatContext *&ic, AVFormatContext *&oc);

    int open_decodec(AVCodecContext *&decodec, AVFormatContext *ic, int streamidx, AVCodec *&dec);

public:
    MediaHelper(const char *input, const char *output);
    int transcode();


};


#endif //SIMPLEVIDEO_MEDIAHELPER_H
