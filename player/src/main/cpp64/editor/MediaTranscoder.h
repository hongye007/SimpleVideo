//
// Created by 周文业 on 2020/5/22.
//

#ifndef SIMPLEVIDEO_MEDIATRANSCODER_H
#define SIMPLEVIDEO_MEDIATRANSCODER_H

#include <FFmpegCommon.h>
#include <JniLog.h>

typedef struct FilteringContext_ {
    AVFilterContext *buffersink_ctx;
    AVFilterContext *buffersrc_ctx;
    AVFilterGraph *filter_graph;
} FilteringContext;

typedef struct StreamContext_ {
    AVCodecContext *dec_ctx;
    AVCodecContext *enc_ctx;
    AVAudioFifo *fifo;
    int64_t pts_audio;
} StreamContext;

typedef struct VideoSize_ {
    bool needSizeClip;
    int x;
    int y;
    int width;
    int height;
} VideoSize;

typedef struct VideoTime_ {
    bool needTimeClip;
    int start;
    int end;
} VideoTime;

typedef struct AudioHandle_ {
    int type;
    const char *audioPath;
} AudioHandle;

class MediaTranscoder {
private:
    const char *in_file;
    const char *out_file;
private:
    int videoidx;
    int audioidx;
    AVFormatContext *ifmt_ctx;
    AVFormatContext *ofmt_ctx;
    FilteringContext *filter_ctx;
    StreamContext *stream_ctx;

    int open_input_file();

    int open_output_file(bool onlyremux);

    int init_filters(void);

    void Time_base(AVPacket *pkt, AVFormatContext *&ic, AVFormatContext *&oc);

private:
    VideoTime *videoTime = nullptr;
    VideoSize *videoSize = nullptr;

public:
    MediaTranscoder(const char *input, const char *output);

    void timeClip(int start, int end);

    void sizeClip(int x, int y, int width, int height);

    void rotation(float angle);

    int transcode();

    ~MediaTranscoder();

};


#endif //SIMPLEVIDEO_MEDIATRANSCODER_H
