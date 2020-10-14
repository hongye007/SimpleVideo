package com.tencent.liteav.audio.impl.Record;

import java.lang.ref.*;
import com.tencent.liteav.basic.log.*;
import java.util.*;
import com.tencent.liteav.basic.util.*;

public class a implements Runnable
{
    private WeakReference<c> a;
    private int b;
    private int c;
    private int d;
    private boolean e;
    private Thread f;
    private byte[] g;
    
    public void a() {
        this.e = false;
        final long currentTimeMillis = System.currentTimeMillis();
        if (this.f != null && this.f.isAlive() && Thread.currentThread().getId() != this.f.getId()) {
            try {
                this.f.join();
            }
            catch (Exception ex) {
                TXCLog.e("AudioCenter:TXCAudioBGMRecord", "record stop Exception: " + ex.getMessage());
            }
        }
        TXCLog.i("AudioCenter:TXCAudioBGMRecord", "stop record cost time(MS): " + (System.currentTimeMillis() - currentTimeMillis));
        this.f = null;
    }
    
    private void a(final byte[] array, final int n, final long n2) {
        c c = null;
        synchronized (this) {
            if (null != this.a) {
                c = this.a.get();
            }
        }
        if (null != c) {
            c.onAudioRecordPCM(array, n, n2);
        }
        else {
            TXCLog.e("AudioCenter:TXCAudioBGMRecord", "onRecordPcmData:no callback");
        }
    }
    
    private void b() {
        c c = null;
        synchronized (this) {
            if (null != this.a) {
                c = this.a.get();
            }
        }
        if (null != c) {
            c.onAudioRecordStart();
        }
        else {
            TXCLog.e("AudioCenter:TXCAudioBGMRecord", "onRecordStart:no callback");
        }
    }
    
    private void c() {
        c c = null;
        synchronized (this) {
            if (null != this.a) {
                c = this.a.get();
            }
        }
        if (null != c) {
            c.onAudioRecordStop();
        }
        else {
            TXCLog.e("AudioCenter:TXCAudioBGMRecord", "onRecordStop:no callback");
        }
    }
    
    @Override
    public void run() {
        if (!this.e) {
            TXCLog.w("AudioCenter:TXCAudioBGMRecord", "audio record: abandom start audio sys record thread!");
            return;
        }
        this.b();
        final int b = this.b;
        final int c = this.c;
        final int d = this.d;
        final int n = 1024 * c * d / 8;
        Arrays.fill(this.g = new byte[n], (byte)0);
        long n2 = 0L;
        final long currentTimeMillis = System.currentTimeMillis();
        while (this.e && !Thread.interrupted()) {
            if ((System.currentTimeMillis() - currentTimeMillis) * b * c * d / 8L / 1000L - n2 < n) {
                try {
                    Thread.sleep(10L);
                }
                catch (InterruptedException ex) {}
            }
            else {
                n2 += this.g.length;
                this.a(this.g, this.g.length, TXCTimeUtil.getTimeTick());
            }
        }
        this.c();
    }
}
