package com.tencent.liteav.videoencoder;

import org.json.*;

public class TXSVideoEncoderParam
{
    public int width;
    public int height;
    public int fps;
    public int gop;
    public int encoderProfile;
    public int encoderMode;
    public boolean enableBFrame;
    public Object glContext;
    public boolean realTime;
    public boolean annexb;
    public boolean appendSpsPps;
    public boolean fullIFrame;
    public boolean syncOutput;
    public boolean enableEGL14;
    public boolean enableBlackList;
    public boolean record;
    public long baseFrameIndex;
    public long baseGopIndex;
    public int streamType;
    public boolean bMultiRef;
    public int bitrate;
    public boolean bLimitFps;
    public int encodeType;
    public boolean forceSetBitrateMode;
    public JSONArray encFmt;
    
    public TXSVideoEncoderParam() {
        this.width = 0;
        this.height = 0;
        this.fps = 20;
        this.gop = 3;
        this.encoderProfile = 1;
        this.encoderMode = 1;
        this.enableBFrame = false;
        this.glContext = null;
        this.realTime = false;
        this.annexb = false;
        this.appendSpsPps = true;
        this.fullIFrame = false;
        this.syncOutput = false;
        this.enableEGL14 = false;
        this.enableBlackList = true;
        this.record = false;
        this.baseFrameIndex = 0L;
        this.baseGopIndex = 0L;
        this.streamType = 0;
        this.bMultiRef = false;
        this.bitrate = 0;
        this.bLimitFps = false;
        this.encodeType = 0;
        this.forceSetBitrateMode = false;
        this.encFmt = null;
    }
}
