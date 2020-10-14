package com.tencent.liteav.videoediter.a;

import android.annotation.*;
import java.util.*;
import android.media.*;
import com.tencent.liteav.basic.log.*;
import java.io.*;

@TargetApi(16)
public class b
{
    private MediaExtractor a;
    private MediaExtractor b;
    private HashMap<Integer, MediaFormat> c;
    private MediaFormat d;
    private MediaFormat e;
    private long f;
    private boolean g;
    private boolean h;
    
    public b() {
        this.c = new HashMap<Integer, MediaFormat>();
        this.f = 0L;
        this.g = true;
        this.h = true;
    }
    
    public synchronized void a(final String s) throws IOException {
        this.f();
        (this.a = new MediaExtractor()).setDataSource(s);
        for (int trackCount = this.a.getTrackCount(), i = 0; i < trackCount; ++i) {
            final MediaFormat trackFormat = this.a.getTrackFormat(i);
            if (trackFormat != null) {
                this.c.put(i, trackFormat);
                final String string = trackFormat.getString("mime");
                if (string != null && string.startsWith("video")) {
                    this.d = trackFormat;
                    this.a.selectTrack(i);
                    this.g = false;
                }
                else if (string != null && string.startsWith("audio")) {
                    this.e = trackFormat;
                    (this.b = new MediaExtractor()).setDataSource(s);
                    this.b.selectTrack(i);
                    this.h = false;
                }
                TXCLog.i("TXMediaExtractor", "track index: " + i + ", format: " + trackFormat);
                final long long1 = trackFormat.getLong("durationUs");
                if (this.f < long1) {
                    this.f = long1;
                }
            }
        }
    }
    
    public synchronized MediaFormat a() {
        return this.d;
    }
    
    public synchronized MediaFormat b() {
        return this.e;
    }
    
    public synchronized long c() {
        return this.f;
    }
    
    public synchronized void a(final long n) {
        if (this.a != null) {
            this.a.seekTo(n, 0);
            this.g = false;
        }
        if (this.b != null && this.a != null) {
            this.b.seekTo(this.a.getSampleTime(), 0);
            this.h = false;
        }
    }
    
    public synchronized long d() {
        long n = 0L;
        if (this.a != null) {
            n = this.a.getSampleTime();
        }
        if (this.b != null && n > this.b.getSampleTime()) {
            n = this.b.getSampleTime();
        }
        return n;
    }
    
    public synchronized int a(final e e) {
        if (e == null || e.b() == null) {
            TXCLog.e("TXMediaExtractor", "frame input is invalid");
            if (e != null && e.b() != null) {
                e.d(0);
                e.c(4);
            }
            return -1;
        }
        int sampleData;
        while (true) {
            MediaExtractor mediaExtractor = null;
            if (this.g) {
                mediaExtractor = this.b;
            }
            else if (this.h) {
                mediaExtractor = this.a;
            }
            else if (this.a != null && this.b != null) {
                if (this.a.getSampleTime() > this.b.getSampleTime()) {
                    mediaExtractor = this.b;
                }
                else {
                    mediaExtractor = this.a;
                }
            }
            if (mediaExtractor == null) {
                TXCLog.e("TXMediaExtractor", "extractor = null!");
                if (e != null && e.b() != null) {
                    e.d(0);
                    e.c(4);
                }
                return -1;
            }
            sampleData = mediaExtractor.readSampleData(e.b(), 0);
            if (sampleData >= 0) {
                final long sampleTime = mediaExtractor.getSampleTime();
                final int sampleFlags = mediaExtractor.getSampleFlags();
                final int sampleTrackIndex = mediaExtractor.getSampleTrackIndex();
                if (sampleTrackIndex < this.c.size()) {
                    final MediaFormat mediaFormat = this.c.get(sampleTrackIndex);
                    if (mediaFormat != null) {
                        e.a(mediaFormat.getString("mime"));
                    }
                }
                e.a(sampleTime);
                e.c(sampleFlags);
                e.d(sampleData);
                e.b().position(0);
                mediaExtractor.advance();
                break;
            }
            if (mediaExtractor == this.a) {
                this.g = true;
            }
            else {
                this.h = true;
            }
            if (this.g && this.h) {
                e.d(0);
                e.c(4);
                break;
            }
        }
        return sampleData;
    }
    
    private synchronized void f() {
        if (this.a != null) {
            this.a.release();
            this.a = null;
        }
        if (this.b != null) {
            this.b.release();
            this.b = null;
        }
        this.c.clear();
        this.d = null;
        this.e = null;
        this.f = 0L;
        this.g = true;
        this.h = true;
    }
    
    public synchronized void e() {
        this.f();
    }
}
