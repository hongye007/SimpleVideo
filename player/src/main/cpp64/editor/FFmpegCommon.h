//
// Created by 周文业 on 2020/5/22.
//

#ifndef SIMPLEVIDEO_FFMPEGCOMMON_H
#define SIMPLEVIDEO_FFMPEGCOMMON_H
#ifdef __cplusplus
extern "C" {
#endif

#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>
#include <libavfilter/buffersink.h>
#include <libavfilter/buffersrc.h>
#include <libavutil/opt.h>
#include <libavutil/pixdesc.h>
#include <libavutil/audio_fifo.h>

#ifdef __cplusplus
};
#endif
#endif //SIMPLEVIDEO_FFMPEGCOMMON_H
