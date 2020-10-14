package com.tencent.liteav.videoediter.ffmpeg.jni;

public class FFDecodedFrame
{
    public byte[] data;
    public long pts;
    public int flags;
    public int sampleRate;
    
    @Override
    public String toString() {
        return "FFDecodedFrame{data size=" + this.data.length + ", pts=" + this.pts + ", flags=" + this.flags + '}';
    }
}
