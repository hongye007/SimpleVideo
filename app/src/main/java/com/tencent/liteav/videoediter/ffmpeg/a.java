package com.tencent.liteav.videoediter.ffmpeg;

import com.tencent.liteav.basic.log.*;
import java.io.*;
import com.tencent.liteav.videoediter.ffmpeg.jni.*;

public class a
{
    private String a;
    private FFMediaInfo b;
    
    public int a(final String a) {
        if (a == null) {
            TXCLog.e("TXFFMediaRetriever", "setDataSource: path can't be null!");
            return -1;
        }
        if (!new File(a).exists()) {
            TXCLog.e("TXFFMediaRetriever", "setDataSource: file isn't exists!");
            return -1;
        }
        if (a.equals(this.a)) {
            return 0;
        }
        this.a = a;
        this.b = TXFFMediaInfoJNI.getMediaInfo(this.a);
        if (this.b != null) {
            TXCLog.i("TXFFMediaRetriever", "setDataSource: media info = " + this.b.toString());
            return 0;
        }
        TXCLog.e("TXFFMediaRetriever", "setDataSource: get media info fail!");
        return -1;
    }
    
    public int a() {
        if (this.a == null || this.b == null) {
            TXCLog.e("TXFFMediaRetriever", "getRotation: you must set path first!");
            return 0;
        }
        TXCLog.i("TXFFMediaRetriever", " getRotation -> " + this.b.rotation);
        return this.b.rotation;
    }
    
    public int b() {
        if (this.a == null || this.b == null) {
            TXCLog.e("TXFFMediaRetriever", "getVideoWidth: you must set path first!");
            return 0;
        }
        TXCLog.i("TXFFMediaRetriever", " getVideoWidth -> " + this.b.width);
        return this.b.width;
    }
    
    public int c() {
        if (this.a == null || this.b == null) {
            TXCLog.e("TXFFMediaRetriever", "getVideoHeight: you must set path first!");
            return 0;
        }
        TXCLog.i("TXFFMediaRetriever", " getVideoHeight -> " + this.b.height);
        return this.b.height;
    }
    
    public float d() {
        if (this.a == null || this.b == null) {
            TXCLog.e("TXFFMediaRetriever", "getVideoFPS: you must set path first!");
            return 0.0f;
        }
        TXCLog.i("TXFFMediaRetriever", " getFPS -> " + this.b.fps);
        return this.b.fps;
    }
    
    public long e() {
        if (this.a == null || this.b == null) {
            TXCLog.e("TXFFMediaRetriever", "getVideoBitrate: you must set path first!");
            return 0L;
        }
        TXCLog.i("TXFFMediaRetriever", " getVideoBitrate -> " + this.b.videoBitrate);
        return this.b.videoBitrate;
    }
    
    public long f() {
        if (this.a == null || this.b == null) {
            TXCLog.e("TXFFMediaRetriever", "getVideoDuration: you must set path first!");
            return 0L;
        }
        TXCLog.i("TXFFMediaRetriever", " getVideoDuration -> " + this.b.videoDuration);
        return this.b.videoDuration;
    }
    
    public int g() {
        if (this.a == null || this.b == null) {
            TXCLog.e("TXFFMediaRetriever", "getSampleRate: you must set path first!");
            return 0;
        }
        TXCLog.i("TXFFMediaRetriever", " getSampleRate -> " + this.b.sampleRate);
        return this.b.sampleRate;
    }
    
    public long h() {
        if (this.a == null || this.b == null) {
            TXCLog.e("TXFFMediaRetriever", "getAudioDuration: you must set path first!");
            return 0L;
        }
        TXCLog.i("TXFFMediaRetriever", " getAudioDuration -> " + this.b.audioDuration);
        return this.b.audioDuration;
    }
}
