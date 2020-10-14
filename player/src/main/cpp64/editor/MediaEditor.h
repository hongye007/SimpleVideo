//
// Created by 周文业 on 2020/5/12.
//

#ifndef SIMPLEVIDEO_MEDIAEDITOR_H
#define SIMPLEVIDEO_MEDIAEDITOR_H

#include <string>
#include <JniLog.h>
#include <FFmpegCommon.h>

typedef struct FilteringContext1 {
    AVFilterContext *buffersink_ctx;
    AVFilterContext *buffersrc_ctx;
    AVFilterGraph *filter_graph;
} FilteringContext1;

typedef struct StreamContext1 {
    AVCodecContext *dec_ctx;
    AVCodecContext *enc_ctx;
    AVAudioFifo *fifo;
    int64_t pts_audio;
} StreamContext1;

class MediaEditor {
private:

    const char *inputFile, *outputFile;

public:
    MediaEditor(const char *input, const char *output);
    int start();
};

#endif //SIMPLEVIDEO_MEDIAEDITOR_H
