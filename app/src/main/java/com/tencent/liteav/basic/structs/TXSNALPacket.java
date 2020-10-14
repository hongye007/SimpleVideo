package com.tencent.liteav.basic.structs;

import java.nio.*;
import android.media.*;

public class TXSNALPacket
{
    public byte[] nalData;
    public int nalType;
    public long gopIndex;
    public long gopFrameIndex;
    public long frameIndex;
    public long refFremeIndex;
    public long pts;
    public long dts;
    public int rotation;
    public long sequenceNum;
    public long arrivalTimeMs;
    public int codecId;
    public ByteBuffer buffer;
    public MediaCodec.BufferInfo info;
    public int streamType;
    
    public TXSNALPacket() {
        this.nalData = null;
        this.nalType = -1;
        this.gopIndex = 0L;
        this.gopFrameIndex = 0L;
        this.frameIndex = 0L;
        this.refFremeIndex = 0L;
        this.pts = 0L;
        this.dts = 0L;
        this.rotation = 0;
        this.sequenceNum = 0L;
        this.arrivalTimeMs = 0L;
        this.codecId = 0;
        this.buffer = null;
        this.info = null;
        this.streamType = 2;
    }
}
