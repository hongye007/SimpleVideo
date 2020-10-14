package com.tencent.liteav.editer;

import java.util.*;
import java.util.concurrent.*;
import com.tencent.liteav.basic.log.*;
import java.nio.*;

public class AudioSWEncoder implements i
{
    private long a;
    private Queue<e> b;
    
    public AudioSWEncoder() {
        this.b = new LinkedBlockingDeque<e>();
    }
    
    @Override
    public void a() {
        this.a = this.nativeInit();
    }
    
    @Override
    public void b() {
        this.nativeUnit(this.a);
        this.b.clear();
    }
    
    @Override
    public void a(final t t) {
        this.a(this.a, t);
    }
    
    @Override
    public void a(final e e) {
        this.a(this.a, e);
    }
    
    @Override
    public e c() {
        return this.b.poll();
    }
    
    private void a(final long n, final t t) {
        TXCLog.i("AudioSWEncoder", "setEncodeParam channel:" + t.channelCount + ",sampleRate:" + t.sampleRate);
        this.nativeSetEncodeParam(n, t.channelCount, t.sampleRate, 16);
    }
    
    private void a(final long n, final e e) {
        final ByteBuffer b = e.b();
        final int g = e.g();
        if (g == 0) {
            if ((e.f() & 0x4) != 0x0) {
                final e e2 = new e();
                e2.c(e.f());
                e2.d(0);
                this.b.add(e2);
            }
            return;
        }
        final byte[] array = new byte[g];
        b.order(ByteOrder.nativeOrder());
        b.get(array);
        final byte[] nativeProcess = this.nativeProcess(n, array, g);
        final e e3 = new e();
        e3.i(e.l());
        e3.c(e.f());
        if (nativeProcess != null && nativeProcess.length > 0) {
            e3.d(nativeProcess.length);
            e3.a(ByteBuffer.wrap(nativeProcess));
            this.b.add(e3);
        }
    }
    
    public native long nativeInit();
    
    public native void nativeUnit(final long p0);
    
    public native byte[] nativeProcess(final long p0, final byte[] p1, final int p2);
    
    private native void nativeSetEncodeParam(final long p0, final int p1, final int p2, final int p3);
}
