//
// Created by 周文业 on 2020/5/17.
//

#include "MediaHelper.h"

static int init_filters(AVFormatContext *ic, int videoidx, AVFilterGraph *&filter_graph,
                        AVCodecContext *dec_ctx, AVFilterContext *&buffersink_ctx,
                        AVFilterContext *&buffersrc_ctx, const char *filters_descr) {
    char args[512];
    int ret = 0;
    const AVFilter *buffersrc = avfilter_get_by_name("buffer");
    const AVFilter *buffersink = avfilter_get_by_name("buffersink");
    AVFilterInOut *outputs = avfilter_inout_alloc();
    AVFilterInOut *inputs = avfilter_inout_alloc();
    AVRational time_base = ic->streams[videoidx]->time_base;
    enum AVPixelFormat pix_fmts[] = {AV_PIX_FMT_GRAY8, AV_PIX_FMT_NONE};

    filter_graph = avfilter_graph_alloc();
    if (!outputs || !inputs || !filter_graph) {
        ret = AVERROR(ENOMEM);
        goto end;
    }

    /* buffer video source: the decoded frames from the decoder will be inserted here. */
    snprintf(args, sizeof(args),
             "video_size=%dx%d:pix_fmt=%d:time_base=%d/%d:pixel_aspect=%d/%d",
             dec_ctx->width, dec_ctx->height, dec_ctx->pix_fmt,
             time_base.num, time_base.den,
             dec_ctx->sample_aspect_ratio.num, dec_ctx->sample_aspect_ratio.den);

    ret = avfilter_graph_create_filter(&buffersrc_ctx, buffersrc, "in",
                                       args, NULL, filter_graph);
    if (ret < 0) {
        av_log(NULL, AV_LOG_ERROR, "Cannot create buffer source\n");
        goto end;
    }

    /* buffer video sink: to terminate the filter chain. */
    ret = avfilter_graph_create_filter(&buffersink_ctx, buffersink, "out",
                                       NULL, NULL, filter_graph);
    if (ret < 0) {
        av_log(NULL, AV_LOG_ERROR, "Cannot create buffer sink\n");
        goto end;
    }

    ret = av_opt_set_int_list(buffersink_ctx, "pix_fmts", pix_fmts,
                              AV_PIX_FMT_NONE, AV_OPT_SEARCH_CHILDREN);
    if (ret < 0) {
        av_log(NULL, AV_LOG_ERROR, "Cannot set output pixel format\n");
        goto end;
    }

    /*
     * Set the endpoints for the filter graph. The filter_graph will
     * be linked to the graph described by filters_descr.
     */

    /*
     * The buffer source output must be connected to the input pad of
     * the first filter described by filters_descr; since the first
     * filter input label is not specified, it is set to "in" by
     * default.
     */
    outputs->name = av_strdup("in");
    outputs->filter_ctx = buffersrc_ctx;
    outputs->pad_idx = 0;
    outputs->next = NULL;

    /*
     * The buffer sink input must be connected to the output pad of
     * the last filter described by filters_descr; since the last
     * filter output label is not specified, it is set to "out" by
     * default.
     */
    inputs->name = av_strdup("out");
    inputs->filter_ctx = buffersink_ctx;
    inputs->pad_idx = 0;
    inputs->next = NULL;

    if ((ret = avfilter_graph_parse_ptr(filter_graph, filters_descr,
                                        &inputs, &outputs, NULL)) < 0)
        goto end;

    if ((ret = avfilter_graph_config(filter_graph, NULL)) < 0)
        goto end;

    end:
    avfilter_inout_free(&inputs);
    avfilter_inout_free(&outputs);

    return ret;
}

void MediaHelper::Time_base(AVPacket *pkt, AVFormatContext *&ic, AVFormatContext *&oc) {
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

int
MediaHelper::Open_In_file(const char *infile, int &videoidx, int &audioidx, AVFormatContext *&ic) {
    int ret;
    ret = avformat_open_input(&ic, infile, 0, 0);
    if (!ic || ret < 0) {
        LOGI("avformat_open_input failed!");
        return ret;
    }
    if ((ret = avformat_find_stream_info(ic, NULL)) < 0) {
        LOGI("avformat_find_stream_info failed!");
        return ret;
    }
    for (int i = 0; i < ic->nb_streams; i++) {
        if (ic->streams[i]->codecpar->codec_type == AVMEDIA_TYPE_VIDEO)
            videoidx = i;
        if (ic->streams[i]->codecpar->codec_type == AVMEDIA_TYPE_AUDIO)
            audioidx = i;
    }
    if (videoidx == -1) {
        LOGI("Codec find failed!");
        return -1;
    }
    if (audioidx == -1) {
        LOGI("Codec find failed!");
        return -1;
    }
    av_dump_format(ic, 0, infile, 0);
    return 0;
}

int
MediaHelper::Open_out_file(const char *outfile, int &videoidx, int &audioidx, AVStream *videoStream,
                           AVStream *audioStream, AVCodecContext *&encodec_video,
                           AVCodecContext *&encodec_audio, AVCodecContext *decodec_video,
                           AVCodecContext *decodec_audio, AVFormatContext *&ic,
                           AVFormatContext *&oc) {
    avformat_alloc_output_context2(&oc, NULL, NULL, outfile);
    if (!oc) {
        LOGI("avformat_alloc_output_context2");
    }
    if (videoidx != -1) {
        AVCodecParameters *codecpar_video = ic->streams[videoidx]->codecpar;
        AVCodec *enc_video = avcodec_find_encoder(codecpar_video->codec_id);
        if (!enc_video) {
            LOGI("Find Decoder failed!");
        }
        LOGI("codec name: %s", enc_video->name);
        encodec_video = avcodec_alloc_context3(enc_video);
        encodec_video->bit_rate = decodec_video->bit_rate;                       //压缩比特率  越大文件越大
        encodec_video->codec_type = AVMEDIA_TYPE_VIDEO;
        encodec_video->width = codecpar_video->width;               //视频宽
        encodec_video->height = codecpar_video->height;             //视频高
        encodec_video->sample_aspect_ratio = decodec_video->sample_aspect_ratio;
        encodec_video->time_base = {1, 30};                   //时间基 设置可能会出现警告
        encodec_video->framerate = {30, 1};                   //设置帧率 (和时间基准相反  可能被抛弃)
        encodec_video->gop_size = decodec_video->gop_size;                         //设置画面组大小(关键帧 多少帧一帧,I贞)
        //encodec_video->max_b_frames = 30;                     //设置b帧(b帧越多压缩率越高)
        encodec_video->pix_fmt = enc_video->pix_fmts[0];
        encodec_video->codec_id = AV_CODEC_ID_H264;
        encodec_video->flags |= AV_CODEC_FLAG_GLOBAL_HEADER;  //AV_CODEC_FLAG2_LOCAL_HEADER;//在每个关键帧放置全局标题，而不是在extradata中。
        encodec_video->thread_count = 4;                      //ffmpeg新转码函数支持多线程 配置线程个数
        if (encodec_video->codec_id == AV_CODEC_ID_H264) {
            //encodec->qmin = 10;
            //encodec->qmax = 51;
            //encodec->qcompress = 0.6;
        }
        if (encodec_video->codec_id == AV_CODEC_ID_MPEG2VIDEO)
            encodec_video->max_b_frames = 2;
        if (encodec_video->codec_id == AV_CODEC_ID_MPEG1VIDEO)
            encodec_video->mb_decision = 2;

        int ret = avcodec_open2(encodec_video, enc_video, NULL);
        if (ret < 0) {
            LOGI("avcodec_open2");
        }

        videoStream = avformat_new_stream(oc, NULL);
        // 解码用  avcodec_parameters_copy ，编码用  avcodec_parameters_from_context
        avcodec_parameters_copy(videoStream->codecpar, codecpar_video);
        videoStream->codecpar->codec_tag = 0;
    }
    if (audioidx != -1) {
        AVCodecParameters *codecpar_audio = ic->streams[audioidx]->codecpar;
        AVCodec *enc_audio = avcodec_find_encoder(codecpar_audio->codec_id);
        if (!enc_audio) {
            LOGI("Find Decoder failed!");
        }
        LOGI("codec name: %s", enc_audio->name);
        encodec_audio = avcodec_alloc_context3(enc_audio);
//        encodec_audio->codec_type = AVMEDIA_TYPE_AUDIO;
//        encodec_audio->bit_rate = codecpar_audio->bit_rate;
        encodec_audio->channels = decodec_audio->channels;
        encodec_audio->channel_layout = av_get_channel_layout_nb_channels(
                decodec_audio->channel_layout);
        encodec_audio->sample_rate = decodec_audio->sample_rate;
        encodec_audio->sample_fmt = enc_audio->sample_fmts[0];
        encodec_audio->time_base = {1, encodec_audio->sample_rate};
        encodec_audio->thread_count = 4;                      //ffmpeg新转码函数支持多线程 配置线程个数

//        int ret = avcodec_open2(encodec_audio, enc_audio, NULL);
//        if (ret < 0) {
//            LOGI("avcodec_open2");
//        }

        audioStream = avformat_new_stream(oc, NULL);
        avcodec_parameters_copy(audioStream->codecpar, codecpar_audio);
        audioStream->codecpar->codec_tag = 0;

//        audioStream->time_base.den = encodec_audio->sample_rate;
//        audioStream->time_base.num = 1;
//        if (oc->oformat->flags & AVFMT_GLOBALHEADER)
//            encodec_audio->flags |= AV_CODEC_FLAG_GLOBAL_HEADER;
    }
    av_dump_format(oc, 0, outfile, 1);
    int ret = avio_open(&oc->pb, outfile, AVIO_FLAG_WRITE);
    if (ret < 0) {
        LOGI("avio_open");
    }
    ret = avformat_write_header(oc, NULL);
    if (ret < 0) {
        LOGI("avformat_write_header");
    }
    return 0;
}

int MediaHelper::open_decodec(AVCodecContext *&decodec, AVFormatContext *ic, int streamidx,
                              AVCodec *&dec) {
    dec = avcodec_find_decoder(ic->streams[streamidx]->codecpar->codec_id);
    if (!dec) {
        LOGI("Find Decoder failed!");
        return -1;
    }
    decodec = avcodec_alloc_context3(dec);
    if (avcodec_parameters_to_context(decodec, ic->streams[streamidx]->codecpar) < 0) {
        LOGI("Copy stream failed!");
        return -1;
    }
    // 打开解码器
    if (avcodec_open2(decodec, dec, NULL) != 0) {
        LOGI("Open codec failed!");
        return -1;
    }
    return true;
}

int MediaHelper::transcode() {
    int videoidx = -1;                  //视频索引标志
    int audioidx = -1;                  //音频索引标志
    AVFormatContext *ic = nullptr;      //输入上下文
    AVFormatContext *oc = nullptr;      //输出上下文
    AVStream *videoStream = nullptr;    //给输出上下文添加的视频流
    AVStream *audioStream = nullptr;    //给输出上下文添加的音频流
    // 视频转码
    AVCodecContext *decodec_video = nullptr;    //解码器上下文-视频
    AVCodecContext *encodec_video = nullptr;    //编解码器上下文-视频
    AVCodec *dec_video = nullptr;               //解码器-视频
    // 音频转码
    AVCodecContext *decodec_audio = nullptr;    //解码器上下文-音频
    AVCodecContext *encodec_audio = nullptr;    //编解码器上下文-音频
    AVCodec *dec_audio = nullptr;                   //解码器-音频

    Open_In_file(this->inputFile, videoidx, audioidx, ic);

    open_decodec(decodec_video, ic, videoidx, dec_video);
    open_decodec(decodec_audio, ic, audioidx, dec_audio);

    Open_out_file(this->outputFile, videoidx, audioidx, videoStream, audioStream, encodec_video,
                  encodec_audio, decodec_video, decodec_audio, ic, oc);


    const char *filter_descr = "scale=78:24,transpose=cclock";
    AVFilterContext *buffersrc_ctx, *buffersink_ctx;
    AVFilterGraph *filterGraph;
    init_filters(ic, videoidx, filterGraph, decodec_video, buffersink_ctx, buffersrc_ctx,
                 filter_descr);

    while (true) {
        AVPacket *in_pkt = av_packet_alloc();
        av_init_packet(in_pkt);
        AVFrame *dec_frame = av_frame_alloc();
        if (av_read_frame(ic, in_pkt) < 0) {
            break;
        }
//        if (in_pkt->stream_index == videoidx) {
//            int iRes;
//            if ((iRes = avcodec_send_packet(decodec_video, in_pkt)) != 0) {
//                LOGI("Send video stream packet failed! ret == %d", iRes);
//                continue;
//            }
//            while (iRes >= 0) {
//                iRes = avcodec_receive_frame(decodec_video, dec_frame);
//                if (iRes == AVERROR(EAGAIN) || iRes == AVERROR_EOF) {
//                    break;
//                } else if (iRes < 0) {
//                    LOGI("Receive video frame failed! ret == %d", iRes);
//                    exit(0);
//                }
//                avcodec_send_frame(encodec_video,dec_frame);
//                AVPacket * out_pkt = av_packet_alloc();
//                while (iRes >= 0) {
//                    iRes = avcodec_receive_packet(encodec_video, out_pkt);
//                    if (iRes == AVERROR(EAGAIN) || iRes == AVERROR_EOF)
//                        continue;
//                    else if (iRes < 0) {
//                        LOGI("Error during encoding video\n");
//                        exit(1);
//                    }
//                    Time_base(out_pkt, ic, oc);
//                    av_write_frame(oc, out_pkt);
//                    LOGI("**********************");
//                    LOGI("Write video packet %3ld(size=%5d)\n", out_pkt->pts, out_pkt->size);
//                }
//                av_packet_free(&out_pkt);
//            }
//        }
//        if (in_pkt->stream_index == audioidx) {
//            Time_base(in_pkt, ic, oc);
//            av_write_frame(oc, in_pkt);
//        }

        Time_base(in_pkt, ic, oc);
        av_write_frame(oc, in_pkt);
        av_frame_free(&dec_frame);
        av_packet_free(&in_pkt);
    }
    av_write_trailer(oc);
    return 0;
}

MediaHelper::MediaHelper(const char *input, const char *output) {
    this->inputFile = input;
    this->outputFile = output;
}
