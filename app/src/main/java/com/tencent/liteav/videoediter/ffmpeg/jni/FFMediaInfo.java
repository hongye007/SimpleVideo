package com.tencent.liteav.videoediter.ffmpeg.jni;

public class FFMediaInfo
{
    public int rotation;
    public int width;
    public int height;
    public float fps;
    public long videoBitrate;
    public long videoDuration;
    public int sampleRate;
    public int channels;
    public long audioBitrate;
    public long audioDuration;
    
    @Override
    public String toString() {
        return "FFMediaInfo{rotation=" + this.rotation + ", width=" + this.width + ", height=" + this.height + ", fps=" + this.fps + ", videoBitrate=" + this.videoBitrate + ", videoDuration=" + this.videoDuration + ", sampleRate=" + this.sampleRate + ", channels=" + this.channels + ", audioBitrate=" + this.audioBitrate + ", audioDuration=" + this.audioDuration + '}';
    }
}
