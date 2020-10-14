//
// Created by 周文业 on 2020/5/22.
//

#include "MediaTranscoder.h"

MediaTranscoder::MediaTranscoder(const char *input, const char *output) {
    this->in_file = input;
    this->out_file = output;
}

int MediaTranscoder::open_input_file() {
    int ret;
    unsigned int i;
    ifmt_ctx = NULL;
    if ((ret = avformat_open_input(&ifmt_ctx, this->in_file, NULL, NULL)) < 0) {
        av_log(NULL, AV_LOG_ERROR, "Cannot open input file\n");
        return ret;
    }

    if ((ret = avformat_find_stream_info(ifmt_ctx, NULL)) < 0) {
        av_log(NULL, AV_LOG_ERROR, "Cannot find stream information\n");
        return ret;
    }

    stream_ctx = static_cast<StreamContext *>(av_mallocz_array(ifmt_ctx->nb_streams,
                                                               sizeof(*stream_ctx)));
    if (!stream_ctx)
        return AVERROR(ENOMEM);

    for (i = 0; i < ifmt_ctx->nb_streams; i++) {
        AVStream *stream = ifmt_ctx->streams[i];
        AVCodec *dec = avcodec_find_decoder(stream->codecpar->codec_id);
        AVCodecContext *codec_ctx;
        if (!dec) {
            av_log(NULL, AV_LOG_ERROR, "Failed to find decoder for stream #%u\n", i);
            return AVERROR_DECODER_NOT_FOUND;
        }
        codec_ctx = avcodec_alloc_context3(dec);
        if (!codec_ctx) {
            av_log(NULL, AV_LOG_ERROR, "Failed to allocate the decoder context for stream #%u\n",
                   i);
            return AVERROR(ENOMEM);
        }
        ret = avcodec_parameters_to_context(codec_ctx, stream->codecpar);
        if (ret < 0) {
            av_log(NULL, AV_LOG_ERROR, "Failed to copy decoder parameters to input decoder context "
                                       "for stream #%u\n", i);
            return ret;
        }
        /* Reencode video & audio and remux subtitles etc. */
        if (codec_ctx->codec_type == AVMEDIA_TYPE_VIDEO
            || codec_ctx->codec_type == AVMEDIA_TYPE_AUDIO) {
            if (codec_ctx->codec_type == AVMEDIA_TYPE_VIDEO)
                codec_ctx->framerate = av_guess_frame_rate(ifmt_ctx, stream, NULL);
            /* Open decoder */
            ret = avcodec_open2(codec_ctx, dec, NULL);
            if (ret < 0) {
                av_log(NULL, AV_LOG_ERROR, "Failed to open decoder for stream #%u\n", i);
                return ret;
            }
        }
        stream_ctx[i].dec_ctx = codec_ctx;
    }
    av_dump_format(ifmt_ctx, 0, this->in_file, 0);
    return 0;
}

int MediaTranscoder::open_output_file(bool onlyremux) {
    AVStream *out_stream;
    AVStream *in_stream;
    AVCodecContext *dec_ctx, *enc_ctx;
    AVCodec *encoder;
    int ret;
    unsigned int i;

    ofmt_ctx = NULL;
    avformat_alloc_output_context2(&ofmt_ctx, NULL, NULL, this->out_file);
    if (!ofmt_ctx) {
        av_log(NULL, AV_LOG_ERROR, "Could not create output context\n");
        return AVERROR_UNKNOWN;
    }

    for (i = 0; i < ifmt_ctx->nb_streams; i++) {
        out_stream = avformat_new_stream(ofmt_ctx, NULL);
        if (!out_stream) {
            av_log(NULL, AV_LOG_ERROR, "Failed allocating output stream\n");
            return AVERROR_UNKNOWN;
        }

        in_stream = ifmt_ctx->streams[i];
        if (onlyremux) {
            /* if this stream must be remuxed */
            ret = avcodec_parameters_copy(out_stream->codecpar, in_stream->codecpar);
            if (ret < 0) {
                av_log(NULL, AV_LOG_ERROR, "Copying parameters for stream #%u failed\n", i);
                return ret;
            }
            out_stream->time_base = in_stream->time_base;
            continue;
        }

        dec_ctx = stream_ctx[i].dec_ctx;
        if (dec_ctx->codec_type == AVMEDIA_TYPE_VIDEO
            || dec_ctx->codec_type == AVMEDIA_TYPE_AUDIO) {
            /* in this example, we choose transcoding to same codec */
            encoder = avcodec_find_encoder(dec_ctx->codec_id);
            if (!encoder) {
                av_log(NULL, AV_LOG_FATAL, "Necessary encoder not found\n");
                return AVERROR_INVALIDDATA;
            }
            enc_ctx = avcodec_alloc_context3(encoder);
            if (!enc_ctx) {
                av_log(NULL, AV_LOG_FATAL, "Failed to allocate the encoder context\n");
                return AVERROR(ENOMEM);
            }
            enc_ctx->thread_count = 4;
            enc_ctx->bit_rate = dec_ctx->bit_rate;
            /* In this example, we transcode to same properties (picture size,
             * sample rate etc.). These properties can be changed for output
             * streams easily using filters */
            if (dec_ctx->codec_type == AVMEDIA_TYPE_VIDEO) {
                enc_ctx->height = dec_ctx->height;
                enc_ctx->width = dec_ctx->width;
                enc_ctx->sample_aspect_ratio = dec_ctx->sample_aspect_ratio;
                /* take first format from list of supported formats */
                if (encoder->pix_fmts)
                    enc_ctx->pix_fmt = encoder->pix_fmts[0];
                else
                    enc_ctx->pix_fmt = dec_ctx->pix_fmt;
                /* video time_base can be set to whatever is handy and supported by encoder */
                enc_ctx->time_base = av_inv_q(dec_ctx->framerate);
                av_log(NULL, AV_LOG_DEBUG, "enc_ctx->time_base=%d/%d\n", enc_ctx->time_base.num,
                       enc_ctx->time_base.den);
            } else {
                enc_ctx->sample_rate = dec_ctx->sample_rate;
                enc_ctx->channel_layout = dec_ctx->channel_layout;
                enc_ctx->channels = av_get_channel_layout_nb_channels(enc_ctx->channel_layout);
                /* take first format from list of supported formats */
                enc_ctx->sample_fmt = encoder->sample_fmts[0];
                enc_ctx->time_base = (AVRational) {1, enc_ctx->sample_rate};
                av_log(NULL, AV_LOG_DEBUG, "enc_ctx->time_base=%d/%d, "\
                        "enc_ctx->sample_fmt=%s, dec_ctx->sample_fmt=%s\n",
                       enc_ctx->time_base.num, enc_ctx->time_base.den,
                       av_get_sample_fmt_name(enc_ctx->sample_fmt),
                       av_get_sample_fmt_name(dec_ctx->sample_fmt));
            }

            if (ofmt_ctx->oformat->flags & AVFMT_GLOBALHEADER)
                enc_ctx->flags |= AV_CODEC_FLAG_GLOBAL_HEADER;

            /* Third parameter can be used to pass settings to encoder */
            ret = avcodec_open2(enc_ctx, encoder, NULL);
            if (ret < 0) {
                av_log(NULL, AV_LOG_ERROR, "Cannot open video encoder for stream #%u\n", i);
                return ret;
            }
            ret = avcodec_parameters_from_context(out_stream->codecpar, enc_ctx);
            if (ret < 0) {
                av_log(NULL, AV_LOG_ERROR,
                       "Failed to copy encoder parameters to output stream #%u\n", i);
                return ret;
            }

            out_stream->time_base = enc_ctx->time_base;
            stream_ctx[i].enc_ctx = enc_ctx;
            stream_ctx[i].fifo = av_audio_fifo_alloc(enc_ctx->sample_fmt, enc_ctx->channels,
                                                     enc_ctx->frame_size);
            stream_ctx[i].pts_audio = 0;

        } else if (dec_ctx->codec_type == AVMEDIA_TYPE_UNKNOWN) {
            av_log(NULL, AV_LOG_FATAL, "Elementary stream #%d is of unknown type, cannot proceed\n",
                   i);
            return AVERROR_INVALIDDATA;
        } else {
            /* if this stream must be remuxed */
            ret = avcodec_parameters_copy(out_stream->codecpar, in_stream->codecpar);
            if (ret < 0) {
                av_log(NULL, AV_LOG_ERROR, "Copying parameters for stream #%u failed\n", i);
                return ret;
            }
            out_stream->time_base = in_stream->time_base;
        }

    }

    av_dump_format(ofmt_ctx, 0, this->out_file, 1);
    if (!(ofmt_ctx->oformat->flags & AVFMT_NOFILE)) {
        ret = avio_open(&ofmt_ctx->pb, this->out_file, AVIO_FLAG_WRITE);
        if (ret < 0) {
            av_log(NULL, AV_LOG_ERROR, "Could not open output file '%s'", this->out_file);
            return ret;
        }
    }

    /* init muxer, write output file header */
    ret = avformat_write_header(ofmt_ctx, NULL);
    if (ret < 0) {
        av_log(NULL, AV_LOG_ERROR, "Error occurred when opening output file\n");
        return ret;
    }
    return 0;
}

static int init_filter(FilteringContext *fctx, AVCodecContext *dec_ctx,
                       AVCodecContext *enc_ctx, const char *filter_spec) {
    char args[512];
    int ret = 0;
    const AVFilter *buffersrc = NULL;
    const AVFilter *buffersink = NULL;
    AVFilterContext *buffersrc_ctx = NULL;
    AVFilterContext *buffersink_ctx = NULL;
    AVFilterInOut *outputs = avfilter_inout_alloc();
    AVFilterInOut *inputs = avfilter_inout_alloc();
    AVFilterGraph *filter_graph = avfilter_graph_alloc();
    if (!outputs || !inputs || !filter_graph) {
        ret = AVERROR(ENOMEM);
        goto end;
    }
    if (dec_ctx->codec_type == AVMEDIA_TYPE_VIDEO) {
        buffersrc = avfilter_get_by_name("buffer");
        buffersink = avfilter_get_by_name("buffersink");
        if (!buffersrc || !buffersink) {
            av_log(NULL, AV_LOG_ERROR, "filtering source or sink element not found\n");
            ret = AVERROR_UNKNOWN;
            goto end;
        }
        snprintf(args, sizeof(args),
                 "video_size=%dx%d:pix_fmt=%d:time_base=%d/%d:pixel_aspect=%d/%d",
                 dec_ctx->width, dec_ctx->height, dec_ctx->pix_fmt,
                 dec_ctx->time_base.num, dec_ctx->time_base.den,
                 dec_ctx->sample_aspect_ratio.num,
                 dec_ctx->sample_aspect_ratio.den);
        ret = avfilter_graph_create_filter(&buffersrc_ctx, buffersrc, "in",
                                           args, NULL, filter_graph);
        if (ret < 0) {
            av_log(NULL, AV_LOG_ERROR, "Cannot create buffer source\n");
            goto end;
        }

        ret = avfilter_graph_create_filter(&buffersink_ctx, buffersink, "out",
                                           NULL, NULL, filter_graph);
        if (ret < 0) {
            av_log(NULL, AV_LOG_ERROR, "Cannot create buffer sink\n");
            goto end;
        }

        ret = av_opt_set_bin(buffersink_ctx, "pix_fmts",
                             (uint8_t *) &enc_ctx->pix_fmt, sizeof(enc_ctx->pix_fmt),
                             AV_OPT_SEARCH_CHILDREN);
        if (ret < 0) {
            av_log(NULL, AV_LOG_ERROR, "Cannot set output pixel format\n");
            goto end;
        }
    } else if (dec_ctx->codec_type == AVMEDIA_TYPE_AUDIO) {
        buffersrc = avfilter_get_by_name("abuffer");
        buffersink = avfilter_get_by_name("abuffersink");
        if (!buffersrc || !buffersink) {
            av_log(NULL, AV_LOG_ERROR, "filtering source or sink element not found\n");
            ret = AVERROR_UNKNOWN;
            goto end;
        }

        if (!dec_ctx->channel_layout)
            dec_ctx->channel_layout =
                    static_cast<uint64_t>(av_get_default_channel_layout(dec_ctx->channels));
        snprintf(args, sizeof(args),
                 "time_base=%d/%d:sample_rate=%d:sample_fmt=%s:channel_layout=0x%" PRIx64,
                 dec_ctx->time_base.num, dec_ctx->time_base.den, dec_ctx->sample_rate,
                 av_get_sample_fmt_name(dec_ctx->sample_fmt),
                 dec_ctx->channel_layout);
        ret = avfilter_graph_create_filter(&buffersrc_ctx, buffersrc, "in",
                                           args, NULL, filter_graph);
        if (ret < 0) {
            av_log(NULL, AV_LOG_ERROR, "Cannot create audio buffer source\n");
            goto end;
        }

        ret = avfilter_graph_create_filter(&buffersink_ctx, buffersink, "out",
                                           NULL, NULL, filter_graph);
        if (ret < 0) {
            av_log(NULL, AV_LOG_ERROR, "Cannot create audio buffer sink\n");
            goto end;
        }

        ret = av_opt_set_bin(buffersink_ctx, "sample_fmts",
                             (uint8_t *) &enc_ctx->sample_fmt, sizeof(enc_ctx->sample_fmt),
                             AV_OPT_SEARCH_CHILDREN);
        if (ret < 0) {
            av_log(NULL, AV_LOG_ERROR, "Cannot set output sample format\n");
            goto end;
        }

        ret = av_opt_set_bin(buffersink_ctx, "channel_layouts",
                             (uint8_t *) &enc_ctx->channel_layout,
                             sizeof(enc_ctx->channel_layout), AV_OPT_SEARCH_CHILDREN);
        if (ret < 0) {
            av_log(NULL, AV_LOG_ERROR, "Cannot set output channel layout\n");
            goto end;
        }

        ret = av_opt_set_bin(buffersink_ctx, "sample_rates",
                             (uint8_t *) &enc_ctx->sample_rate, sizeof(enc_ctx->sample_rate),
                             AV_OPT_SEARCH_CHILDREN);
        if (ret < 0) {
            av_log(NULL, AV_LOG_ERROR, "Cannot set output sample rate\n");
            goto end;
        }
    } else {
        ret = AVERROR_UNKNOWN;
        goto end;
    }
    /* Endpoints for the filter graph. */
    outputs->name = av_strdup("in");
    outputs->filter_ctx = buffersrc_ctx;
    outputs->pad_idx = 0;
    outputs->next = NULL;

    inputs->name = av_strdup("out");
    inputs->filter_ctx = buffersink_ctx;
    inputs->pad_idx = 0;
    inputs->next = NULL;

    if (!outputs->name || !inputs->name) {
        ret = AVERROR(ENOMEM);
        goto end;
    }

    if ((ret = avfilter_graph_parse_ptr(filter_graph, filter_spec,
                                        &inputs, &outputs, NULL)) < 0)
        goto end;

    if ((ret = avfilter_graph_config(filter_graph, NULL)) < 0) {
        LOGI("avfilter_graph_config error : %d", ret);
        goto end;
    }

    /* Fill FilteringContext1 */
    fctx->buffersrc_ctx = buffersrc_ctx;
    fctx->buffersink_ctx = buffersink_ctx;
    fctx->filter_graph = filter_graph;

    end:
    avfilter_inout_free(&inputs);
    avfilter_inout_free(&outputs);
    return ret;
}

int MediaTranscoder::init_filters(void) {
    const char *filter_spec;
    unsigned int i;
    int ret;
    filter_ctx = static_cast<FilteringContext *>(av_malloc_array(ifmt_ctx->nb_streams,
                                                                  sizeof(*filter_ctx)));
    if (!filter_ctx)
        return AVERROR(ENOMEM);

    for (i = 0; i < ifmt_ctx->nb_streams; i++) {
        filter_ctx[i].buffersrc_ctx = NULL;
        filter_ctx[i].buffersink_ctx = NULL;
        filter_ctx[i].filter_graph = NULL;
        if (!(ifmt_ctx->streams[i]->codecpar->codec_type == AVMEDIA_TYPE_AUDIO
              || ifmt_ctx->streams[i]->codecpar->codec_type == AVMEDIA_TYPE_VIDEO))
            continue;

        if (ifmt_ctx->streams[i]->codecpar->codec_type == AVMEDIA_TYPE_VIDEO)
            filter_spec = "null"; /* passthrough (dummy) filter for video */
        else
            filter_spec = "anull"; /* passthrough (dummy) filter for audio */
        ret = init_filter(&filter_ctx[i], stream_ctx[i].dec_ctx,
                          stream_ctx[i].enc_ctx, filter_spec);
        if (ret)
            return ret;
    }
    return 0;
}

void MediaTranscoder::Time_base(AVPacket *pkt, AVFormatContext *&ic, AVFormatContext *&oc) {
    pkt->pts = av_rescale_q_rnd(pkt->pts,
                                ic->streams[pkt->stream_index]->time_base,
                                oc->streams[pkt->stream_index]->time_base,
                                (AVRounding) (AV_ROUND_NEAR_INF | AV_ROUND_PASS_MINMAX)
    );
    pkt->dts = av_rescale_q_rnd(pkt->dts,
                                ic->streams[pkt->stream_index]->time_base,
                                oc->streams[pkt->stream_index]->time_base,
                                (AVRounding) (AV_ROUND_NEAR_INF | AV_ROUND_PASS_MINMAX)
    );
    pkt->pos = -1;
    pkt->duration = av_rescale_q_rnd(pkt->duration,
                                     ic->streams[pkt->stream_index]->time_base,
                                     oc->streams[pkt->stream_index]->time_base,
                                     (AVRounding) (AV_ROUND_NEAR_INF | AV_ROUND_PASS_MINMAX)
    );
}

int MediaTranscoder::transcode() {
    bool hasFilter = 0;
    bool hasCodec = 0;
    bool onlyRemux = 1;
    int allDone = -1;
    int ret;
    AVPacket packet = {.data = NULL, .size = 0};
    AVFrame *frame = NULL;
    unsigned int stream_index;
    unsigned int i;

    /* read all packets */
    int64_t *dts_start_from = (int64_t*)malloc(sizeof(int64_t) * ifmt_ctx->nb_streams);//动态分配内存空间
    memset(dts_start_from, 0, sizeof(int64_t) * ifmt_ctx->nb_streams);//内存空间初始化
    int64_t *pts_start_from = (int64_t*)malloc(sizeof(int64_t) * ifmt_ctx->nb_streams);
    memset(pts_start_from, 0, sizeof(int64_t) * ifmt_ctx->nb_streams);

    if ((ret = open_input_file()) < 0)
        goto end;
    if ((ret = open_output_file(onlyRemux)) < 0)
        goto end;

    av_seek_frame(ifmt_ctx, -1, videoTime->start * AV_TIME_BASE, AVSEEK_FLAG_ANY);

    while (1) {
        AVStream *in_stream, *out_stream;
        ret = av_read_frame(ifmt_ctx, &packet);
        if (ret < 0)
            break;
        in_stream = ifmt_ctx->streams[packet.stream_index];
        out_stream = ofmt_ctx->streams[packet.stream_index];
        if (av_q2d(in_stream->time_base) * packet.pts > videoTime->end) {
            av_packet_unref(&packet);
            if(allDone == -1) {
                allDone = packet.stream_index;
                continue;
            }
            if(allDone == packet.stream_index) {
                continue;
            }
            break;
        }
        if (dts_start_from[packet.stream_index] == 0) {
            dts_start_from[packet.stream_index] = packet.dts;//记录裁剪初始位置的DTS  pkt.stream_index 为了区分音频流和视频流等信息
            printf("dts_start_from:\n");
        }
        if (pts_start_from[packet.stream_index] == 0) {
            pts_start_from[packet.stream_index] = packet.pts;//记录裁剪初始位置的PTS
            printf("pts_start_from:\n");
        }
        /* copy packet */ // 时间基转换函数求出新的位置下的PTS\DTS
        packet.pts = av_rescale_q_rnd(packet.pts - pts_start_from[packet.stream_index], in_stream->time_base, out_stream->time_base, (AVRounding)(AV_ROUND_NEAR_INF | AV_ROUND_PASS_MINMAX));
        packet.dts = av_rescale_q_rnd(packet.dts - dts_start_from[packet.stream_index], in_stream->time_base, out_stream->time_base, (AVRounding)(AV_ROUND_NEAR_INF | AV_ROUND_PASS_MINMAX));
        if (packet.pts < 0) {
            packet.pts = 0;
        }
        if (packet.dts < 0) {
            packet.dts = 0;
        }
        packet.duration = (int)av_rescale_q((int64_t)packet.duration, in_stream->time_base, out_stream->time_base);
        packet.pos = -1;
        printf("\n");
        ret = av_interleaved_write_frame(ofmt_ctx, &packet);
        if (ret < 0) {
            fprintf(stderr, "Error muxing packet\n");
            break;
        }
        av_packet_unref(&packet);
    }
    //释放资源
    free(dts_start_from);
    free(pts_start_from);

    av_write_trailer(ofmt_ctx);
    end:
    av_packet_unref(&packet);
//    av_frame_free(&frame);
//    if (stream_ctx) {
//        for (i = 0; i < ifmt_ctx->nb_streams; i++) {
//            avcodec_free_context(&stream_ctx[i].dec_ctx);
//            av_audio_fifo_free(stream_ctx[i].fifo);
//            if (ofmt_ctx && ofmt_ctx->nb_streams > i && ofmt_ctx->streams[i] &&
//                stream_ctx[i].enc_ctx)
//                avcodec_free_context(&stream_ctx[i].enc_ctx);
//            if (filter_ctx && filter_ctx[i].filter_graph)
//                avfilter_graph_free(&filter_ctx[i].filter_graph);
//        }
//    }
//
//    av_free(filter_ctx);
//    av_free(stream_ctx);
    avformat_close_input(&ifmt_ctx);
    if (ofmt_ctx && !(ofmt_ctx->oformat->flags & AVFMT_NOFILE))
        avio_closep(&ofmt_ctx->pb);
    avformat_free_context(ofmt_ctx);
    if (ret < 0)
        av_log(NULL, AV_LOG_ERROR, "Error occurred: %s\n", av_err2str(ret));
    return ret ? 1 : 0;
}

void MediaTranscoder::timeClip(int start, int end) {
    if (videoTime == nullptr) {
        videoTime = new VideoTime();
    }
    videoTime->needTimeClip = 1;
    videoTime->start = start;
    videoTime->end = end;
}

void MediaTranscoder::sizeClip(int x, int y, int width, int height) {
    if (videoSize == nullptr) {
        videoSize = new VideoSize();
    }
    videoSize->needSizeClip = 1;
    videoSize->x = x;
    videoSize->y = y;
    videoSize->width = width;
    videoSize->height = height;
}

void MediaTranscoder::rotation(float angle) {

}

MediaTranscoder::~MediaTranscoder() {
    if (videoTime) {
        delete videoTime;
        videoTime = nullptr;
    }
    if (videoSize) {
        delete videoSize;
        videoSize = nullptr;
    }
}
