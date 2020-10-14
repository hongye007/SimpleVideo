package com.tencent.liteav.d;

import java.io.*;
import android.annotation.*;
import java.nio.*;
import android.media.*;
import java.util.*;

@TargetApi(16)
public class e implements Serializable
{
    private int trackId;
    private String mime;
    private int bufferIndex;
    private int rotation;
    private int frameRate;
    private int sampleRate;
    private int channelCount;
    private int audioBitRate;
    private int width;
    private int height;
    private float blurLevel;
    private ByteBuffer byteBuffer;
    private MediaCodec.BufferInfo bufferInfo;
    private boolean tailFrame;
    private long speedSampleTime;
    private long reverseSampleTime;
    private long originSampleTime;
    private List bitmapList;
    private int textureId;
    private int frameFormat;
    private float mCropOffsetRatio;
    
    public e() {
        this.bufferInfo = new MediaCodec.BufferInfo();
        this.frameFormat = 4;
    }
    
    public e(final ByteBuffer byteBuffer, final int size, final long presentationTimeUs, final int bufferIndex, final int flags, final int trackId) {
        this.bufferInfo = new MediaCodec.BufferInfo();
        this.frameFormat = 4;
        this.byteBuffer = byteBuffer;
        this.trackId = trackId;
        this.bufferInfo.flags = flags;
        this.bufferInfo.presentationTimeUs = presentationTimeUs;
        this.bufferIndex = bufferIndex;
        this.bufferInfo.size = size;
    }
    
    public e(final String mime, final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
        this.bufferInfo = new MediaCodec.BufferInfo();
        this.frameFormat = 4;
        this.mime = mime;
        this.byteBuffer = byteBuffer;
        (this.bufferInfo = new MediaCodec.BufferInfo()).set(bufferInfo.offset, bufferInfo.size, bufferInfo.presentationTimeUs, bufferInfo.flags);
    }
    
    public String a() {
        return this.mime;
    }
    
    public void a(final String mime) {
        this.mime = mime;
    }
    
    public ByteBuffer b() {
        return this.byteBuffer;
    }
    
    public int c() {
        return this.trackId;
    }
    
    public int d() {
        return this.bufferIndex;
    }
    
    public void a(final ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
    
    public void a(final int trackId) {
        this.trackId = trackId;
    }
    
    public void b(final int bufferIndex) {
        this.bufferIndex = bufferIndex;
    }
    
    public void a(final long presentationTimeUs) {
        this.bufferInfo.presentationTimeUs = presentationTimeUs;
    }
    
    public long e() {
        return this.bufferInfo.presentationTimeUs;
    }
    
    public void c(final int flags) {
        this.bufferInfo.flags = flags;
    }
    
    public void d(final int size) {
        this.bufferInfo.size = size;
    }
    
    public int f() {
        return this.bufferInfo.flags;
    }
    
    public int g() {
        return this.bufferInfo.size;
    }
    
    public int h() {
        return this.rotation;
    }
    
    public void e(final int rotation) {
        this.rotation = rotation;
    }
    
    public int i() {
        return this.frameRate;
    }
    
    public void f(final int frameRate) {
        this.frameRate = frameRate;
    }
    
    public int j() {
        return this.sampleRate;
    }
    
    public void g(final int sampleRate) {
        this.sampleRate = sampleRate;
    }
    
    public int k() {
        return this.channelCount;
    }
    
    public void h(final int channelCount) {
        this.channelCount = channelCount;
    }
    
    public int l() {
        return this.audioBitRate;
    }
    
    public void i(final int audioBitRate) {
        this.audioBitRate = audioBitRate;
    }
    
    public int m() {
        return this.width;
    }
    
    public void j(final int width) {
        this.width = width;
    }
    
    public int n() {
        return this.height;
    }
    
    public void k(final int height) {
        this.height = height;
    }
    
    public MediaCodec.BufferInfo o() {
        return this.bufferInfo;
    }
    
    @Override
    public String toString() {
        return "";
    }
    
    public boolean p() {
        return (this.f() & 0x4) != 0x0;
    }
    
    public boolean q() {
        return this.g() == 0 || this.f() == 2;
    }
    
    public boolean r() {
        return this.tailFrame;
    }
    
    public void a(final boolean tailFrame) {
        this.tailFrame = tailFrame;
    }
    
    public void a(final float blurLevel) {
        this.blurLevel = blurLevel;
    }
    
    public float s() {
        return this.blurLevel;
    }
    
    public void b(final long speedSampleTime) {
        this.speedSampleTime = speedSampleTime;
    }
    
    public long t() {
        return this.speedSampleTime;
    }
    
    public long u() {
        return this.reverseSampleTime;
    }
    
    public void c(final long reverseSampleTime) {
        this.reverseSampleTime = reverseSampleTime;
    }
    
    public long v() {
        return this.originSampleTime;
    }
    
    public void d(final long originSampleTime) {
        this.originSampleTime = originSampleTime;
    }
    
    public List w() {
        return this.bitmapList;
    }
    
    public void a(final List bitmapList) {
        this.bitmapList = bitmapList;
    }
    
    public int x() {
        return this.textureId;
    }
    
    public void l(final int textureId) {
        this.textureId = textureId;
    }
    
    public int y() {
        return this.frameFormat;
    }
    
    public void m(final int frameFormat) {
        this.frameFormat = frameFormat;
    }
}
